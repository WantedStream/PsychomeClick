package com.example.psychomeclick;

import static com.example.psychomeclick.model.FirebaseManager.getUserFromShared;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.User;
import com.example.psychomeclick.model.UserProgress;
import com.google.gson.Gson;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

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
        User user = getUserFromShared(getPreferences(MODE_PRIVATE));
        System.out.println(user+"AAAAAAAAAAAA");
        if(user==null){



        findViewById(R.id.gologInBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this,LogIn.class);
            startActivity(intent);
        });
        findViewById(R.id.gosignUpBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this,SignUp.class);
            startActivity(intent);
        });
        findViewById(R.id.goenterNoUserBtn).setOnClickListener((v) -> {
            //
        });
        findViewById(R.id.goaboutBtn).setOnClickListener((v) -> {});

        }
        else{
            Intent intent = new Intent(this,UserActivity.class);
            startActivity(intent);
             finish();
        }

    }
}