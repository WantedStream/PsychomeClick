package com.example.psychomeclick.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class FirebaseManager {
   public static FirebaseFirestore db = FirebaseFirestore.getInstance();
   public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static final String PrefLocaltion="LogIn";
     public static UserData userData;
    public static HashMap<String,Integer> QuestionMap=new HashMap<>();

    public static final ImmutableList<String> adminList = ImmutableList.of("t1", "coolusername","newusername","");

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



}
