package com.example.psychomeclick.model;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.example.psychomeclick.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseManager {
   static FirebaseFirestore db = FirebaseFirestore.getInstance();
   static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public static User currentUser=null;
        public static void addUsertoDB(User user){
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(authResultTask -> {
            if(authResultTask.isSuccessful()){
                db.collection("Users").document(authResultTask.getResult().getUser().getUid()).get().addOnCompleteListener(userTask -> {
                  //  User user = new User (userTask.getResult().getString("username"),userTask.getResult().getString("email"),userTask.getResult().getString("phone"),this.passwordEt.getText().toString(),userTask.getResult().getString("userProgress"));
                });
            }
            else{
               // Toast.makeText(getApplicationContext(),"user doesnt exist",Toast.LENGTH_SHORT).show();

            }

        });
    }
    public static void addQuestiontoDB(Uri fileUri){
        StorageReference storageRef = firebaseStorage.getReference();
        // Create a reference to the file in Firebase Storage
        StorageReference fileRef = storageRef.child("Questions/" + fileUri.getLastPathSegment());


        // Register observers to listen for the upload task
        fileRef.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
            // File successfully uploaded
            // You can get the download URL for the file
            fileRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
              String fileDownloadUrl = downloadUri.toString();
                // Do something with the download URL
            });
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnProgressListener(snapshot -> {
            // Track the progress of the upload
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            // Update your UI with the upload progress
        });
    }
}
