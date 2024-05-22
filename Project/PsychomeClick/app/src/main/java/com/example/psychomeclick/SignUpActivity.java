package com.example.psychomeclick;

import static com.example.psychomeclick.model.FirebaseManager.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.HashMap;



public class SignUpActivity extends AppCompatActivity {

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

        findViewById(R.id.logInLinkbt).setOnClickListener(v -> {Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();});
        addBetterButtonFunction();

        findViewById(R.id.back).setOnClickListener((b)->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void addBetterButtonFunction(){
        findViewById(R.id.signupBT).setOnClickListener(e -> {
            this.emailErrors.setText("");this.phoneErrors.setText("");this.usernameErrors.setText("");this.passwordErrors.setText("");
            String username=userNameEt.getText().toString(),email=emailEt.getText().toString(),phone=phoneEt.getText().toString(),password=passwordEt.getText().toString(),pass2=RepeatPasswordEt.getText().toString();
            if(username.trim().isEmpty()||email.trim().isEmpty()||phone.trim().isEmpty()||password.trim().isEmpty()||pass2.trim().isEmpty())
            {
                Toast.makeText(getApplicationContext(),"all fields are required",Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isBad=false;
            if(!username.matches("^[a-zA-Z0-9_]{8,20}$")){
                this.usernameErrors.setText("name must be 8-20 characters long,and contain only letters,numbers and underscores ");
                isBad=true;
            }



            try {
                    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(phone, "IL");
                    if (!phoneUtil.isValidNumber(phoneNumber)) {
                        this.phoneErrors.setText("Invalid Phone Number");
                        isBad=true;

                    }
             } catch (NumberParseException phoneError) {
                this.phoneErrors.setText("Invalid Phone Number Format");
                isBad=true;
            }


            if(!password.equals(pass2)) {
                this.passwordErrors.setText("passwords arent the same!");
                isBad=true;
            }

            if(isBad==true){
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(task -> {
                HashMap<String, Object> user = new HashMap<>();
                user.put("username", username);user.put("email", email);user.put("phone", phone);user.put("userprogress", "{}");
                db.collection("Users").document(task.getUser().getUid()).set(user).addOnSuccessListener(t-> {
                    task.getUser().sendEmailVerification().addOnSuccessListener((a)->{Toast.makeText(getApplicationContext(),"user created ,email verification sent",Toast.LENGTH_SHORT).show();});
                    Intent myIntent = new Intent(this, LogInActivity.class);
                    startActivity(myIntent);
                });

                        }).addOnFailureListener((errorTask)->{
                //String str = (errorTask.getMessage() + "").substring((errorTask.getMessage() + "").lastIndexOf(":") + 1);
                  //  Toast.makeText(getApplicationContext(),errorTask.toString(),Toast.LENGTH_SHORT).show();

                    try {
                    throw errorTask;
                } catch (FirebaseAuthWeakPasswordException exception) {
                    this.passwordErrors.setText(exception.getReason());
                    this.passwordErrors.requestFocus();
                } catch (FirebaseAuthInvalidCredentialsException exception) {
                    this.emailErrors.setText(exception.getMessage());
                    this.emailErrors.requestFocus();
                } catch (FirebaseAuthUserCollisionException exception) {
                    this.emailErrors.setText(exception.getMessage());
                    this.emailErrors.requestFocus();

                } catch (Throwable ex) {
                    throw new RuntimeException(ex);
                }

                });






        });

    }



}