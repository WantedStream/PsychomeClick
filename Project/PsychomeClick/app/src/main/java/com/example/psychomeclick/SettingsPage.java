package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsPage extends AppCompatActivity {

    EditText emailEditText,usernameEditText,phoneEditText;
    private FirebaseUser currentUser;
    private FirebaseFirestore currentDB;

    private String finalUsernameStr,finalPhoneStr,finalEmailStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        this.emailEditText=findViewById(R.id.emailET);
        this.usernameEditText=findViewById(R.id.usernameET);
        this.phoneEditText=findViewById(R.id.phoneET);
        this.currentDB = FirebaseFirestore.getInstance();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reloadPage();

    }
    private void resetEditTexts(){
        this.emailEditText.setText("");
        this.usernameEditText.setText("");
        this.phoneEditText.setText("");
    }
    private void setHints(String emailStr,String usernameStr,String phonestr){
        this.emailEditText.setHint(task.getResult().getString("email"));
        this.usernameEditText.setHint(task.getResult().getString("username"));
        this.phoneEditText.setHint(task.getResult().getString("phone"));
    }
    private void reloadPage() {

        this.currentDB.collection("Users").document(this.currentUser.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                resetEditTexts();


                findViewById(R.id.updateBtn).setOnClickListener((t)-> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    String newemail=this.emailEditText.getText().toString();
                    if(newemail.trim().equals(""))
                        newemail=this.emailEditText.getHint().toString();
                    user.verifyBeforeUpdateEmail(newemail).addOnCompleteListener((etask)->{
                    if(etask.isSuccessful()){
                        String newemail2=this.emailEditText.getText().toString();
                        if(newemail2.trim().equals(""))
                            newemail2=this.emailEditText.getHint().toString();

                        db.collection("Users").document(user.getUid()).update("email" ,  newemail2).addOnCompleteListener((co)->{
                            if(co.isSuccessful()){

                                Toast.makeText(getApplicationContext(),"user updated",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"to make any changes, email must be registered",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener((taskSendVerfication) -> {reloadPage();});
                    }
                    reloadPage();
                             });




                });

            }else{
                Toast.makeText(getApplicationContext(),"no user currently logged in",Toast.LENGTH_LONG).show();


            }

        });
    }
}