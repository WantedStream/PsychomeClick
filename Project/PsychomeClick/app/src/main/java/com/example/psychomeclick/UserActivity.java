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

import com.example.psychomeclick.fragments.SimulationsFragment;
import com.example.psychomeclick.model.Constants;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.GeneralFragment;
import com.example.psychomeclick.fragments.RandomFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private int current=0;
    private static final String[] topics = {"", "כללי", "סימולציות",
            "אקראי"};
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

        ;
        String s =getIntent().getStringExtra("selectboxfrag");
        Fragment fragment = s!=null&&s.equals("Simulations") ? new SimulationsFragment(): new GeneralFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();

        setSelectBox();
    }

    private void setSelectBox(){
        Spinner spinner=findViewById(R.id.spinnerTopics);
        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                topics);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(ad);
    }
    private boolean isAdmin(FirebaseUser user){
        return adminList.contains(user.getUid());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if(this.current==position)
            return;
        this.current=position;


        Fragment fragment=null;

        switch(topics[position]){

            case "כללי": fragment = new GeneralFragment();
            break;
            case "סימולציות" : fragment = new SimulationsFragment();
            break;
            case "אקראי": fragment = new RandomFragment();
            break;

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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