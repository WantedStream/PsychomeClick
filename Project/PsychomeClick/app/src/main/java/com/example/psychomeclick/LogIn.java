package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psychomeclick.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.SQLOutput;

public class LogIn extends AppCompatActivity {

    EditText userNameEt,emailEt,phoneEt,passwordEt,RepeatPasswordEt;

    TextView emailErrors,phoneErrors,usernameErrors,passwordErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        emailEt=findViewById(R.id.emailET);
        passwordEt=findViewById(R.id.editTextPassword);

        this.emailErrors=findViewById(R.id.emailErrorTV);
        this.emailErrors.setText("");


        this.passwordErrors=findViewById(R.id.passwordErrorTv);
        this.passwordErrors.setText("");

        findViewById(R.id.button).setOnClickListener( View -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(this.emailEt.getText().toString(),this.passwordEt.getText().toString()).addOnCompleteListener(authResultTask -> {
                if(authResultTask.isSuccessful()){
                    System.out.println("hmm");
                  db.collection("Users").document(authResultTask.getResult().getUser().getUid()).get().addOnCompleteListener(userTask -> {
                        User user = new User (userTask.getResult().getString("username"),userTask.getResult().getString("email"),userTask.getResult().getString("phone"),this.passwordEt.getText().toString(),userTask.getResult().getString("userProgress"));
                        Toast.makeText(getApplicationContext(),"welcome" +user.toString(),Toast.LENGTH_SHORT).show();
                      System.out.println(user);
                    });
                }

            });
        });
    }
}