package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

        addBetterButtonFunction();

    }


    private void addBetterButtonFunction(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();

        findViewById(R.id.signupBT).setOnClickListener(e -> {
            this.emailErrors.setText("");
            this.phoneErrors.setText("");
            this.usernameErrors.setText("");
            this.passwordErrors.setText("");
            String username=userNameEt.getText().toString(),email=emailEt.getText().toString(),phone=phoneEt.getText().toString(),password=passwordEt.getText().toString(),pass2=RepeatPasswordEt.getText().toString();
            if(!password.equals(pass2)) {
                this.passwordErrors.setText("passwords arent the same!");
                return;
            }
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(t -> {
            HashMap<String, Object> user = new HashMap<>();
            user.put("username", username);
            user.put("email", email);
            user.put("phone", phone);
            user.put("userprogress", "{}");
            try {
                db.collection("Users").document(t.getResult().getUser().getUid()).set(user);
                Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_SHORT).show();

            }
            catch (Exception exception){
                this.emailErrors.setText((exception+"").substring((exception+"").lastIndexOf(":")+1)+"!");

            }
        });
        });
    }



}