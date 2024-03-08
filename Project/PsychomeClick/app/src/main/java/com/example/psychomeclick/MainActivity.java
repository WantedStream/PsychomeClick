package com.example.psychomeclick;

import static com.example.psychomeclick.model.FirebaseManager.PrefLocaltion;

import static com.example.psychomeclick.model.FirebaseManager.userData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;

import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.UserData;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String,String> emailAndPass = getUserFromShared(getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
        findViewById(R.id.gologInBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);
        });
        findViewById(R.id.gosignUpBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this,SignUp.class);
            startActivity(intent);
        });
        findViewById(R.id.goenterNoUserBtn).setOnClickListener((v) -> {
        });
        findViewById(R.id.goaboutBtn).setOnClickListener((v) -> {});

        FirebaseManager.db.collection("Questions").get().addOnCompleteListener((t)->{
            for (DocumentSnapshot doc: t.getResult().getDocuments()) {
                FirebaseManager.QuestionMap.put(doc.getId(),(Integer.parseInt(doc.get("correctAnswer").toString())));
            }
            String email=emailAndPass.get("email"),password=emailAndPass.get("password");
            if(email!=null&&password!=null){
                FirebaseManager.firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener((authData)->{
                    FirebaseManager.db.collection("Users").document(authData.getUser().getUid()).get().addOnCompleteListener(dbData->{
                        DocumentSnapshot userDataSnapshot=dbData.getResult();
                        userData=new UserData(userDataSnapshot.getString("username"),userDataSnapshot.getString("email"),userDataSnapshot.getString("phone"),userDataSnapshot.getString("userprogress"));
                        Intent intent = new Intent(this,UserActivity.class);
                        startActivity(intent);
                        finish();
                    });
                });

            }
        });







    }

    public static Map<String,String> getUserFromShared(SharedPreferences sp){
        Gson gson = new Gson();
        String json = sp.getString("currentUser", null);
        Map<String,String> obj = gson.fromJson(json, LinkedHashMap.class);
        return obj;
    }
}