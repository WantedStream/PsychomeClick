package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserProgress up = new UserProgress();
        up.printExistingJsonTree(this);
       // up.createSave(this);
        System.out.println("aaa");
        String[] array = {"Verbal reasoning","Analogies"};
        System.out.println(up.navigateTo(this,array,true));

    }
}