package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

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

           List<String> emailErrors= new ArrayList<>(),phoneErrors= new ArrayList<>(),usernameErrors=new ArrayList<>(),passwordErrors=new ArrayList<>();
           if(!isValidEmail(emailEt.getText().toString())){
               emailErrors.add("email is Invalid!");
            }
            if(!isValidPhone(phoneEt.getText().toString())){
                phoneErrors.add("phone is Invalid!");
            }
            if(!isValidUsername(userNameEt.getText().toString())){
                usernameErrors.add("username is Invalid!");
            }
            if(!isValidPassword(passwordEt.getText().toString())){
                passwordErrors.add("password is Invalid!");
            }
            if(passwordEt.getText().toString()!=RepeatPasswordEt.getText().toString()){
                passwordErrors.add("Passwords arent the same");
            }
            fieldExistsInDocument(db.collection("Users").get(),"email",emailEt.getText().toString());

          // db.collection("Users").document("ZOvyUClsrQdipgLNMv0n").get().addOnCompleteListener(t -> {
          //     if (t.isSuccessful()){
           //        t.getResult().get("");
            //   }
           // });
        });



    }
    private boolean isValidEmail(String email){
        Pattern p = Pattern.compile("^[a-zA-z][a-zA-Z0-9+_.-]+@[a-zA-Z][a-zA-Z0-9]+.[a-zA-z]+$");
        Matcher m = p.matcher(email);
       return m.matches();
    }
    private boolean isValidPhone(String phone){
        Pattern p = Pattern.compile("^0(?:[234689]|5[0-689]|7[246789])(?![01])(\\d{7})$\n");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    private boolean isValidUsername(String username){
        Pattern p = Pattern.compile("");
        Matcher m = p.matcher(username);
        return m.matches();
    }
    private boolean isValidPassword(String password){
        Pattern p = Pattern.compile("");
        Matcher m = p.matcher(password);
        return m.matches();
    }



    private boolean fieldExistsInDocument(String collection,String field,String testStr,FirebaseFirestore db){
        db.collection("Users").get().addOnCompleteListener(t->{
            if(!t.isSuccessful()){
                return;
            }
            for (DocumentSnapshot doc:
                 t.getResult().getDocuments()) {
                if(doc.get("phonenumber").equals(phoneEt.getText().toString())){

                }
            }

        });


        db.collection("Users").whereEqualTo(field,testStr).get().addOnCompleteListener(t -> {



            if (t.getResult().getDocuments().isEmpty()){
                HashMap<String,Object>  user = new HashMap<>();
                user.put("username",userNameEt.getText().toString());
                user.put("email",emailEt.getText().toString());
                user.put("phone",phoneEt.getText().toString());
                user.put("password",passwordEt.getText().toString());
                user.put("userprogress","{}");

                db.collection("Users").document().set(user);
            }
            System.out.println(t.getResult().getDocuments().isEmpty());
        });

        return errors;
    }

    private boolean fieldExistsInDocument(CollectionReference collection, String field, String testStr, FirebaseFirestore db){

        collection.whereEqualTo(field,testStr).get().addOnCompleteListener(t -> {
            if (t.getResult().getDocuments().isEmpty()){
                
                HashMap<String,Object>  user = new HashMap<>();
                user.put("username",userNameEt.getText().toString());
                user.put("email",emailEt.getText().toString());
                user.put("phone",phoneEt.getText().toString());
                user.put("password",passwordEt.getText().toString());
                user.put("userprogress","{}");

                db.collection("Users").document().set(user);
            }
            System.out.println(t.getResult().getDocuments().isEmpty());
        });

        return errors;
    }
    private boolean addUser(FirebaseFirestore db){

        HashMap<String,Object>  user = new HashMap<>();
        user.put("username",userNameEt.getText().toString());
        user.put("email",emailEt.getText().toString());
        user.put("phone",phoneEt.getText().toString());
        user.put("password",passwordEt.getText().toString());
        user.put("userprogress","{}");

        db.collection("Users").document().set(user);
    }
}