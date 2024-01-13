package com.example.psychomeclick.model;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.psychomeclick.LogIn;
import com.example.psychomeclick.R;
import com.example.psychomeclick.UserActivity;
import com.example.psychomeclick.fragments.AddQuestionFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class FirebaseManager {
   static FirebaseFirestore db = FirebaseFirestore.getInstance();
   static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static User currentUser=null;
        public static void logIn(String email,String password, Context context){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(authResultTask -> {
            if(authResultTask.isSuccessful()){
                db.collection("Users").document(authResultTask.getResult().getUser().getUid()).get().addOnCompleteListener(userTask -> {
                    Toast.makeText(context.getApplicationContext(),"welcome " +userTask.getResult().get("username").toString(),Toast.LENGTH_SHORT).show();
                    currentUser=new User(userTask.getResult().get("username").toString(),userTask.getResult().get("email").toString(),userTask.getResult().get("phone").toString(),userTask.getResult().get("username").toString(),userTask.getResult().get("userprogress").toString());
                    Intent intent = new Intent(context,UserActivity.class);
                     context.startActivity(intent);
                    ((Activity) context).finish();
                });
            }
            else{
                Toast.makeText(context.getApplicationContext(),"user doesnt exist",Toast.LENGTH_SHORT).show();

            }

        });


    }
    public static void addQuestiontoDB(String correctAnswer, LinkedHashMap<Integer,Uri> imageMap,Fragment f, Fragment to){
        StorageReference storageRef = firebaseStorage.getReference();
        Context context=f.getContext();
        HashMap<String, Object> q = new HashMap<>();
        q.put("correctAnswer",correctAnswer);
       DocumentReference qdocument = db.collection("Questions").document();
        qdocument.set(q).addOnSuccessListener(tsk-> {

            int x=0;
            for(Map.Entry<Integer,Uri> entry : imageMap.entrySet()) {

                StorageReference fileRef = storageRef.child("QuestionStorage/" +qdocument.getId()+"/images"+x);
                x++;
                System.out.println(entry.getKey()+"   "+entry.getValue());
                fileRef.putFile(entry.getValue()).addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(context.getApplicationContext(),"question added",Toast.LENGTH_SHORT).show();

                    if(to!=null){
                        FragmentManager fm =  ((Fragment) f).getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, to);
                        transaction.commit();
                    }

                }).addOnFailureListener(exception -> {
                    Toast.makeText(context.getApplicationContext(),"problem." +
                            "",Toast.LENGTH_SHORT).show();
                }).addOnProgressListener(snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                });
            }
        });
    }

}
