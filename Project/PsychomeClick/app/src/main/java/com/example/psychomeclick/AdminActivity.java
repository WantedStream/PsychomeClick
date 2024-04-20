package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.psychomeclick.fragments.AddQuestionFragment;
import com.example.psychomeclick.fragments.QuestionListFragment;
import com.example.psychomeclick.fragments.SubjectTreeFragment;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.insertQuestionBtn).setOnClickListener((v) ->{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new AddQuestionFragment());
            transaction.commit();
        });
        findViewById(R.id.listQuestionBtn).setOnClickListener((v) ->{
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new QuestionListFragment());
            transaction.commit();
        });

       // findViewById(R.id.treeSubjectBtn).setOnClickListener((t)->{
        //    FragmentManager fm = getSupportFragmentManager();
        //    FragmentTransaction transaction = fm.beginTransaction();
        //    transaction.replace(R.id.contentFragment, new SubjectTreeFragment());
        //    transaction.commit();
       // });

        ((ImageButton) findViewById(R.id.returnToMainMenuBtn)).setOnClickListener((t)->{
            Intent intent = new Intent(this,UserActivity.class);
            startActivity(intent);
            finish();
        });

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, new AddQuestionFragment());
        transaction.commit();
    }
}