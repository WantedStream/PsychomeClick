package com.example.psychomeclick.model;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

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

    public static final String PrefLocaltion="LogIn";
    public static User currentUser=null;
        public static void logIn(String email,String password, Activity activity){
            Context context=activity.getApplicationContext();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(authResultTask -> {
            if(authResultTask.isSuccessful()){
                db.collection("Users").document(authResultTask.getResult().getUser().getUid()).get().addOnCompleteListener(userTask -> {
                    Toast.makeText(context.getApplicationContext(),"welcome " +userTask.getResult().get("username").toString(),Toast.LENGTH_SHORT).show();
                    User tmpuser=new User(userTask.getResult().get("username").toString(),userTask.getResult().get("email").toString(),userTask.getResult().get("phone").toString(),userTask.getResult().get("userprogress").toString());
                    saveShareRefCurrent(tmpuser,true,activity.getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
                    Intent intent = new Intent(context,UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.finish();
                });
            }
            else{
                Toast.makeText(context.getApplicationContext(),"user doesnt exist",Toast.LENGTH_SHORT).show();

            }

        });


    }

    public static void saveShareRefCurrent(User user,boolean save,SharedPreferences sp){
        currentUser=user;
        if(save) {
            SharedPreferences.Editor prefsEditor = sp.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            prefsEditor.putString("currentUser", json);
            prefsEditor.commit();

        }

    }
  public static User getUserFromShared(SharedPreferences sp){
        Gson gson = new Gson();
        String json = sp.getString("currentUser", null);
        User obj = gson.fromJson(json, User.class);
        return obj;
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
    public static void signUp(){

    }

}
