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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        this.emailEditText=findViewById(R.id.emailET);
        this.usernameEditText=findViewById(R.id.usernameET);
        this.phoneEditText=findViewById(R.id.phoneET);

        reloadPage();

    }
    private void reloadPage() {
        this.currentDB = FirebaseFirestore.getInstance();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentDB.collection("Users").document(this.currentUser.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                this.emailEditText.setText("");
                this.usernameEditText.setText("");
                this.phoneEditText.setText("");

                this.emailEditText.setHint(task.getResult().getString("email"));
                this.usernameEditText.setHint(task.getResult().getString("username"));
                this.phoneEditText.setHint(task.getResult().getString("phone"));

                findViewById(R.id.updateBtn).setOnClickListener((t)-> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();


                    String newname=this.usernameEditText.getText().toString(),newemail=this.emailEditText.getText().toString(), newphone=this.phoneEditText.getText().toString();

                    if(newname.trim().equals(""))
                        newname=this.usernameEditText.getHint().toString();

                    if(newemail.trim().equals(""))
                        newemail=this.emailEditText.getHint().toString();

                    if(newphone.trim().equals(""))
                        newphone=this.phoneEditText.getHint().toString();
                    user.verifyBeforeUpdateEmail(newemail).addOnCompleteListener((etask)->{
                        
                    });
                    db.collection("Users").document(user.getUid()).update("username" ,   newname,"email" ,  newemail,"phone" ,  newphone).addOnCompleteListener((co)->{
                        if(co.isSuccessful()){


                            Toast.makeText(getApplicationContext(),"user updated",Toast.LENGTH_SHORT).show();

                        }
                    });
                    reloadPage();
                             });

            }else{
                //no user current logged in
                Toast.makeText(getApplicationContext(),"no user currently logged in",Toast.LENGTH_LONG).show();

            }

        });
    }
}