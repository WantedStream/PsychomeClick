package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;
import kotlin.text.Regex;

public class SignUp extends AppCompatActivity {

    EditText userNameEt,emailEt,phoneEt,passwordEt,RepeatPasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNameEt=findViewById(R.id.userNameET);
        emailEt=findViewById(R.id.emailET);
        phoneEt=findViewById(R.id.editTextPhone);
        passwordEt=findViewById(R.id.editTextPassword);
        RepeatPasswordEt=findViewById(R.id.editTextPasswordRepeat2);

        findViewById(R.id.signupBT).setOnClickListener(view -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
           List<String> emailErrors = checkEmail(emailEt.getText().toString(),db);

            for(String errorStr : emailErrors){
                System.out.println(errorStr);
            }

            HashMap<String,Object>  user = new HashMap<>();
            user.put("username",userNameEt.getText().toString());
            user.put("email",emailEt.getText().toString());
            user.put("phone",phoneEt.getText().toString());
            user.put("password",passwordEt.getText().toString());
            user.put("userprogress","{}");

            db.collection("Users").document().set(user);

           // db.collection("Users").document("ZOvyUClsrQdipgLNMv0n").get().addOnCompleteListener(t -> {
            //        if (t.isSuccessful()){
            //       t.getResult().get("");
            //    }
            //});
        });



    }
    private List<String> checkEmail(String email,FirebaseFirestore db){
        List<String> errors= new ArrayList<>();
        Pattern p = Pattern.compile("^[a-zA-z][a-zA-Z0-9+_.-]+@[a-zA-Z][a-zA-Z0-9]+.[a-zA-z]+$");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if(b==false){
            errors.add("email is invalid!");

        }

          db.collection("Users").whereEqualTo("email",email).get().addOnCompleteListener(t -> {
            if (!t.getResult().isEmpty()){
                errors.add("This email is already taken!");
                System.out.println("TAKEEEEEEEN" +" email" + " " +email);
            }
            else{
                System.out.println("not taken");
            }
        });

        return errors;
    }
}