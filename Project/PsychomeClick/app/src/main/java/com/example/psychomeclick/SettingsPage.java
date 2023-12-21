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

        reloadPage();

    }
    private void reloadPage() {
        this.currentDB = FirebaseFirestore.getInstance();
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentDB.collection("Users").document(this.currentUser.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult() != null){
                System.out.println(task.getResult());
                this.emailEditText.setHint(task.getResult().getString("email"));
                this.usernameEditText.setHint(task.getResult().getString("username"));
                this.phoneEditText.setHint(task.getResult().getString("phone"));

                findViewById(R.id.updateBtn).setOnClickListener((t)-> {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(!this.emailEditText.getText().toString().trim().equals(""))
                        db.collection("Users").document(user.getUid()).update("age" , 38).addOnCompleteListener((co)->{
                            Toast.makeText(getApplicationContext(),"user updated",Toast.LENGTH_SHORT).show();
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