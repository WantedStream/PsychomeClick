package com.example.psychomeclick;

import static com.example.psychomeclick.model.Constants.adminList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.psychomeclick.fragments.BookFragment;
import com.example.psychomeclick.fragments.SimulationsFragment;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.fragments.GeneralFragment;
import com.example.psychomeclick.fragments.RandomFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.LinkedHashMap;

public class UserActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        createStuff();

        findViewById(R.id.signoutbutton).setOnClickListener((t)->{
            FirebaseManager.userData=null;
            FirebaseManager.firebaseAuth.signOut();
            deleteUserFromShared(this.getSharedPreferences(FirebaseManager.PrefLocaltion,MODE_PRIVATE));
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.aibtn).setOnClickListener((t)->{
            Intent intent = new Intent(this, ChatBotActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.general).setOnClickListener((t)->{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new GeneralFragment());
            transaction.commit();
        });
        findViewById(R.id.tests).setOnClickListener((t)->{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new SimulationsFragment());
            transaction.commit();
        });
        findViewById(R.id.bookbtn).setOnClickListener((t)->{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new BookFragment());
            transaction.commit();
        });
    }

    @Override
   public void onStart() {

        super.onStart();
    }
    public void createStuff(){
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(userTask -> {
            String name=userTask.getResult().getString("username").toString();
            ((TextView) findViewById(R.id.WelcomeText)).setText("welcome " +name);
        });

        if(!adminList.contains(FirebaseManager.userData.getName())){
            findViewById(R.id.goToAdminPage).setVisibility(View.GONE);
        }
        findViewById(R.id.goToAdminPage).setOnClickListener((v)->{  Intent intent = new Intent(this,AdminActivity.class);
            startActivity(intent);
            finish();});

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, new GeneralFragment());
        transaction.commit();

    }



    public static void deleteUserFromShared(SharedPreferences sp){
        SharedPreferences.Editor prefsEditor = sp.edit();
        Gson gson = new Gson();
        LinkedHashMap<String,String> map= new LinkedHashMap<>();
        map.put("email","");
        map.put("password","");
        String json = gson.toJson(map);
        prefsEditor.putString("currentUser", json);
        prefsEditor.commit();
    }
}