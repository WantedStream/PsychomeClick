package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.UserData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
            logIn(this.emailEt.getText().toString(),this.passwordEt.getText().toString(),this);
        });
    }
    public static void logIn(String email,String password, Activity activity){
        Context context=activity.getApplicationContext();
        FirebaseManager.firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResultTask -> {
            FirebaseManager.db.collection("Users").document(authResultTask.getUser().getUid()).get().addOnCompleteListener(userTask -> {
                    Toast.makeText(context.getApplicationContext(),"welcome " +userTask.getResult().get("username").toString(),Toast.LENGTH_SHORT).show();
                    FirebaseManager.userData=new UserData(userTask.getResult().get("username").toString(),userTask.getResult().get("email").toString(),userTask.getResult().get("phone").toString(),userTask.getResult().get("userprogress").toString());
                    saveShareRef(email,password,activity.getSharedPreferences(FirebaseManager.PrefLocaltion,MODE_PRIVATE));
                    Intent intent = new Intent(context,UserActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                   activity.finish();
                });
            }).addOnFailureListener((failureT)->{
            Toast.makeText(context.getApplicationContext(),"user doesn't exist",Toast.LENGTH_SHORT).show();
        });

}
    public static void saveShareRef(String email,String password, SharedPreferences sp){
            SharedPreferences.Editor prefsEditor = sp.edit();
            Gson gson = new Gson();
            LinkedHashMap<String,String> map= new LinkedHashMap<>();
            map.put("email",email);
            map.put("password",password);
        String json = gson.toJson(map);
            prefsEditor.putString("currentUser", json);
            prefsEditor.commit();
    }


}