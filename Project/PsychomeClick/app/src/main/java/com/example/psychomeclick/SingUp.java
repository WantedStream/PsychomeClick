package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SingUp extends AppCompatActivity {

    EditText userNameEt,emailEt,phoneEt,passwordEt,RepeatPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        userNameEt=findViewById(R.id.userNameET);
        emailEt=findViewById(R.id.emailET);
        phoneEt=findViewById(R.id.editTextPhone);
        passwordEt=findViewById(R.id.editTextPassword);
        RepeatPasswordEt=findViewById(R.id.editTextPasswordRepeat2);

        findViewById(R.id.signinBT).setOnClickListener(view -> {
            
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String,Object>  user = new HashMap<>();
            user.put("username",userNameEt.getText().toString());
            user.put("email",emailEt.getText().toString());
            user.put("phone",phoneEt.getText().toString());
            user.put("password",passwordEt.getText().toString());
            user.put("userprogress","{}");

            db.collection("Users").document().set(user);

            db.collection("Users").document("ZOvyUClsrQdipgLNMv0n").get().addOnCompleteListener(t -> {
                if (t.isSuccessful()){
                    t.getResult().get("");
                }
            });
        });



    }
}