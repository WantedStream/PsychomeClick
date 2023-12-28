package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psychomeclick.model.Constants;
import com.example.psychomeclick.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private int current=0;
    private static final String[] topics = {"", "כללי", "סימולציות",
            "אקראי"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
       FirebaseUser user = firebaseAuth.getCurrentUser();
        firestore.collection("Users").document(user.getUid()).get().addOnCompleteListener(userTask -> {
            String name=userTask.getResult().getString("username").toString();
            ((TextView) findViewById(R.id.WelcomeText)).setText("welcome " +name);
        });


        Fragment fragment = new ScreenContentFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();


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
    // if(isAdmin(User)){
//
       // };




    private boolean isAdmin(FirebaseUser user){
        return Constants.adminList.contains(user.getUid());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if(this.current==position)
            return;
        this.current=position;


        Intent intent=null;
        switch(topics[position]){

            case "כללי": intent = new Intent(this,UserActivity.class);
                System.out.println("כללי");
                startActivity(intent);
                finish();
            break;
            case "סימולציות" : intent = new Intent(this,UserActivity.class);
                System.out.println("סימולציות");
                startActivity(intent);
                finish();
            break;
            case "אקראי": intent = new Intent(this,UserActivity.class);
                System.out.println("אקראי");
                startActivity(intent);
                finish();
            break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}