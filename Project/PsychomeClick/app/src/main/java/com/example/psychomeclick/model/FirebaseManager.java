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

import com.example.psychomeclick.LogIn;
import com.example.psychomeclick.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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
    public static void addQuestiontoDB(String correctAnswer,ImageView question, ImageView first, ImageView sec, ImageView third, ImageView forth,Context context){
        StorageReference storageRef = firebaseStorage.getReference();


        HashMap<String, Object> q = new HashMap<>();
        q.put("correctAnswer",correctAnswer);
       DocumentReference qdocument = db.collection("Questions").document();
        qdocument.set(q).addOnSuccessListener(tsk-> {

            // Create a reference to the file in Firebase Storage
            StorageReference fileRef = storageRef.child("QuestionStorage/" +qdocument.getId());

            Bitmap bitmap = ((BitmapDrawable) question.getDrawable()).getBitmap();

            // Convert the Bitmap to a Uri
            Uri imageUri = getImageUri(this, bitmap);
            // Register observers to listen for the upload task
            fileRef.putFile(first.getDrawable()).addOnSuccessListener(taskSnapshot -> {
                // File successfully uploaded

                Toast.makeText(context.getApplicationContext(),"question added",Toast.LENGTH_SHORT).show();

            }).addOnFailureListener(exception -> {
                // Handle unsuccessful uploads
            }).addOnProgressListener(snapshot -> {
                // Track the progress of the upload
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                // Update your UI with the upload progress
            });
        });






    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}
