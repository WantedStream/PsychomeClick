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

        //  Map<String,String> emailAndPass = getUserFromShared(getSharedPreferences(PrefLocaltion,MODE_PRIVATE));
        findViewById(R.id.gologInBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        });
        findViewById(R.id.gosignUpBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });
        findViewById(R.id.goenterNoUserBtn).setOnClickListener((v) -> {
        });
        findViewById(R.id.goaboutBtn).setOnClickListener((v) -> {
        });

    }


}