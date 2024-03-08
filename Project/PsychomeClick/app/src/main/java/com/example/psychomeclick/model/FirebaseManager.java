package com.example.psychomeclick.model;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.*;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.bumptech.glide.signature.ObjectKey;
import com.example.psychomeclick.LogIn;
import com.example.psychomeclick.R;
import com.example.psychomeclick.UserActivity;
import com.example.psychomeclick.fragments.AddQuestionFragment;
import com.example.psychomeclick.fragments.QuestionListFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class FirebaseManager {
   public static FirebaseFirestore db = FirebaseFirestore.getInstance();
   public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static final String PrefLocaltion="LogIn";
    public static UserData userData;
    public static HashMap<String,Integer> QuestionMap=new HashMap<>();

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
                        FragmentManager fm = f.getParentFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, new QuestionListFragment());
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


    public static void insertToQuestionCells(Queue<LinearLayout> qlist,Context c){
        StorageReference storageRef=firebaseStorage.getReference();
        db.collection("Questions").get().addOnSuccessListener((getCollectTask)->{
            for (DocumentSnapshot document:getCollectTask.getDocuments()) {
                String questionId = document.getId();
                StorageReference fileRef = storageRef.child("QuestionStorage/" +questionId+"/images"+0);
               setQuestionCell(fileRef,qlist.remove(),c);
            }
            while(!qlist.isEmpty())qlist.remove().setVisibility(View.GONE);
        });


    }

    public static void loadImage(@NonNull StorageReference imageRef, @NonNull ImageView imageView, Context c) {
        Glide.with(c)
                .load(imageRef)
                .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                            .into(imageView);

    }
    public static void saveImage(@NonNull StorageReference imageRef, @NonNull ImageView imageView,String name, Context c) {

        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

                       imageRef.child(name).putBytes(data).addOnSuccessListener(taskSnapshot -> {
                            })
                       .addOnFailureListener(exception -> {
                                // Handle any errors getting the download URL
                                Log.e("FirebaseStorage", "Error getting download URL: " + exception.getMessage());

                        });


    }
    public static void setQuestionCell(@NonNull StorageReference storageRef,LinearLayout cellayout,Context c){
       String path= storageRef.getPath();
        int begin=path.indexOf('/',1),end=path.indexOf('/',begin+1);
        ( (TextView)cellayout.getChildAt(0)).setText(storageRef.getPath().substring(begin+1,end));
            loadImage(storageRef, (ImageView) cellayout.getChildAt(1),c);

    }


}
