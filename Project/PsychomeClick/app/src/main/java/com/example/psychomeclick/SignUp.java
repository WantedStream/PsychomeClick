package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.*;

import javax.security.auth.callback.Callback;

public class SignUp extends AppCompatActivity {

    EditText userNameEt,emailEt,phoneEt,passwordEt,RepeatPasswordEt;

    TextView emailErrors,phoneErrors,usernameErrors,passwordErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEt=findViewById(R.id.userNameET);
        emailEt=findViewById(R.id.emailET);
        phoneEt=findViewById(R.id.editTextPhone);
        passwordEt=findViewById(R.id.editTextPassword);
        RepeatPasswordEt=findViewById(R.id.editTextPasswordRepeat2);

        this.emailErrors=findViewById(R.id.emailErrorTV);
        this.emailErrors.setText("");

        this.phoneErrors=findViewById(R.id.phoneErrorTv);
        this.phoneErrors.setText("");

        this.usernameErrors=findViewById(R.id.userNameErrorTV);
        this.usernameErrors.setText("");

        this.passwordErrors=findViewById(R.id.passwordErrorTV);
        this.passwordErrors.setText("");

        addButtonFunction();

    }
    private boolean isValidEmail(String email){

        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;

       // Pattern p = Pattern.compile("/^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/ \n");
        //Matcher m = p.matcher(email);

    }
    private boolean isValidPhone(String phone){
        if (phone.length()!=10)
                return false;
       if(phone.charAt(0)!='0')
           return false;
       return true;
    }
    private boolean isValidUsername(String username){
        Pattern p = Pattern.compile("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$\n");
        //Only contains alphanumeric characters, underscore and dot.
        //Underscore and dot can't be at the end or start of a username (e.g _username / username_ / .username / username.).
        //Underscore and dot can't be next to each other (e.g user_.name).
        //Underscore or dot can't be used multiple times in a row (e.g user__name / user..name).
        //Number of characters must be between 8 to 20.
        Matcher m = p.matcher(username);
        return m.matches();
    }
    private boolean isValidPassword(String password){
        Pattern p = Pattern.compile("");
        Matcher m = p.matcher(password);
        return true;
    }
    private boolean checkIfValid(String username,String email,String phone,String password,String secondPass){
        System.out.println(username+"   test");
        boolean isValid=true;
        if(!isValidEmail(email)){
            isValid=false;
            emailErrors.setText("email is Invalid!");
            System.out.println("email "+email);

        }
        if(!isValidPhone(phone)){
            isValid=false;
            phoneErrors.setText("phone is Invalid!");
            System.out.println("phone "+phone);

        }
        if(!isValidUsername(username)){
            isValid=false;
            usernameErrors.setText("8-20 charcaters long.only numbers/english characters allowed");
            System.out.println("un "+username);


        }
        if(!isValidPassword(password)){
            isValid=false;
            passwordErrors.setText("password is Invalid!");
            System.out.println("password "+ password);


        }
        else if(!password.equals(secondPass)){
            isValid=false;
            passwordErrors.setText("Passwords arent the same");
            System.out.println("password2:"+password+" "+secondPass);

        }
        System.out.println();
        return isValid;
    }
    private void addButtonFunction(){


        findViewById(R.id.signupBT).setOnClickListener(view -> {
            this.emailErrors.setText("");
            this.phoneErrors.setText("");
            this.usernameErrors.setText("");
            this.passwordErrors.setText("");

            String username=userNameEt.getText().toString(),email=emailEt.getText().toString(),phone=phoneEt.getText().toString(),password=passwordEt.getText().toString(),pass2=RepeatPasswordEt.getText().toString();
            System.out.println(username +" test3");
            boolean isValid=checkIfValid(username,email,phone,password,pass2);
            if(!isValid)
                return;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users").whereEqualTo("email",email).get().addOnCompleteListener(e -> //check if email exists
            {
                if (!e.getResult().getDocuments().isEmpty()) emailErrors.setText("email is taken!");
                db.collection("Users").whereEqualTo("username", username).get().addOnCompleteListener(u -> {//check if username exists
                    if (!u.getResult().getDocuments().isEmpty())
                        usernameErrors.setText("username is taken!");
                    else {
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("email", email);
                        user.put("phone", phone);
                        user.put("password", password);
                        user.put("userprogress", "{}");
                        db.collection("Users").document().set(user);
                    }
                });

            });

        });
    }


}