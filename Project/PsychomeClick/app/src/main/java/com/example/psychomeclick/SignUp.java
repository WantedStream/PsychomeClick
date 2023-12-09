package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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

        findViewById(R.id.logInLinkbt).setOnClickListener(v -> {Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);
            finish();});
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
            System.out.println("aaaaaa");

            if(username.trim().isEmpty()||email.trim().isEmpty()||phone.trim().isEmpty()||password.trim().isEmpty()||pass2.trim().isEmpty())
            {
                Toast.makeText(getApplicationContext(),"all fields are required",Toast.LENGTH_SHORT).show();
                System.out.println("a");
                return;
            }
            System.out.println(username.trim()+"aaaaa");

            if(!password.equals(pass2)) {
                this.passwordErrors.setText("passwords arent the same!");
                return;
            }
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {


                        if (!task.isSuccessful()) {
                            String str = (task.getException() + "").substring((task.getException() + "").lastIndexOf(":") + 1);
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException exception) {
                                this.passwordErrors.setText(exception.getReason());
                                this.passwordErrors.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException exception) {
                                this.emailErrors.setText(exception.getMessage());
                                this.emailErrors.requestFocus();
                            } catch (FirebaseAuthUserCollisionException exception) {
                                this.emailErrors.setText(exception.getMessage());
                                this.emailErrors.requestFocus();
                            } catch (Exception exception) {
                                System.out.println(exception.getMessage());
                            }
                        }
                        else{
                            HashMap<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("userprogress", "{}");
                            db.collection("Users").document(task.getResult().getUser().getUid()).set(user).addOnCompleteListener(t-> {

                                Toast.makeText(getApplicationContext(),"user created",Toast.LENGTH_SHORT).show();
                                task.getResult().getUser().sendEmailVerification();

                                 Intent intent = new Intent(this,LogIn.class);
                                startActivity(intent);
                                  finish();
                        });
                        }
                    });




        });

    }



}