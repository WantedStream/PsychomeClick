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

/**
 * The type Firebase manager.
 */
public class FirebaseManager {
    /**
     * The constant db.
     */
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    /**
     * The constant firebaseAuth.
     */
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    /**
     * The constant firebaseStorage.
     */
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    /**
     * The constant PrefLocaltion.
     */
    public static final String PrefLocaltion="LogIn";
    /**
     * The constant userData.
     */
    public static UserData userData;
    /**
     * The constant QuestionMap.
     */
    public static HashMap<String,Integer> QuestionMap=new HashMap<>();

    /**
     * The constant adminList.
     */
    public static final ImmutableList<String> adminList = ImmutableList.of("t1", "coolusername","newusername","");

    /**
     * Load image.
     *
     * @param imageRef  the image ref
     * @param imageView the image view
     * @param c         the c
     */
    public static void loadImage(@NonNull StorageReference imageRef, @NonNull ImageView imageView, Context c) {
        Glide.with(c)
                .load(imageRef)
                .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                            .into(imageView);

    }

    /**
     * Save image.
     *
     * @param imageRef  the image ref
     * @param imageView the image view
     * @param name      the name
     * @param c         the c
     */
    public static void saveImage(@NonNull StorageReference imageRef, @NonNull ImageView imageView,String name, Context c) {

        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

                       imageRef.child(name).putBytes(data).addOnSuccessListener(taskSnapshot -> {
                            })
                       .addOnFailureListener(exception -> {
                                //  errors getting URL
                                Log.e("FirebaseStorage", "Error getting download URL: " + exception.getMessage());

                        });


    }



}
