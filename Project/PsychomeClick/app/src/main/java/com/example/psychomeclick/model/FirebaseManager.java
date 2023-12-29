package com.example.psychomeclick.model;

import android.content.Intent;
import android.widget.Toast;

import com.example.psychomeclick.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
   static FirebaseFirestore db = FirebaseFirestore.getInstance();
   static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        public static void addUsertoFirestoreAndFirebase(User user){
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
}
