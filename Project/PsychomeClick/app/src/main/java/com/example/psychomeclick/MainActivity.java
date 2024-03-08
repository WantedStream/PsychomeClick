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


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // UserProgress up = new UserProgress("");
       // String[] array = {"Verbal reasoning","Analogies"};
        //System.out.println(up.navigateTo(this,array,true));
       // Map.Entry<String, HashMap>  entry=   new AbstractMap.SimpleEntry<String, HashMap>("exmpleString", new HashMap<>());
       //up.addToPath(this,entry, array);
        Pair<String,String> emailAndPass = getUserFromShared(getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
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


        if(emailAndPass!=null){
            FirebaseManager.firebaseAuth.signInWithEmailAndPassword(emailAndPass.first,emailAndPass.second).addOnSuccessListener((authData)->{
                FirebaseManager.db.collection("Users").document(authData.getUser().getUid()).get().addOnCompleteListener(dbData->{
                    DocumentSnapshot userDataSnapshot=dbData.getResult();
                    userData=new UserData(userDataSnapshot.getString("username"),userDataSnapshot.getString("email"),userDataSnapshot.getString("phone"),userDataSnapshot.getString("userprogress"));
                    Intent intent = new Intent(this,UserActivity.class);
                    startActivity(intent);
                    finish();
                });
            });

        }

    }

    public static Pair<String,String> getUserFromShared(SharedPreferences sp){
        Gson gson = new Gson();
        String json = sp.getString("currentUser", null);
        Pair<String,String> obj = gson.fromJson(json, Pair.class);
        return obj;
    }
}