package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.psychomeclick.fragments.AddQuestionFragment;
import com.example.psychomeclick.fragments.QuestionListFragment;

/**
 * The type Admin activity.
 */
public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.insertQuestionBtn).setOnClickListener((v) ->{
            changeColorOfButton((Button) v);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new AddQuestionFragment());
            transaction.commit();
        });
        findViewById(R.id.listQuestionBtn).setOnClickListener((v) ->{
            changeColorOfButton((Button) v);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new QuestionListFragment());
            transaction.commit();
        });

        ((ImageButton) findViewById(R.id.returnToMainMenuBtn)).setOnClickListener((t)->{
            Intent intent = new Intent(this,UserActivity.class);
            startActivity(intent);
            finish();
        });

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, new AddQuestionFragment());
        transaction.commit();

        changeColorOfButton(findViewById(R.id.insertQuestionBtn));
    }


    /**
     * Change color of button.
     *
     * @param b the b
     */
    public void changeColorOfButton(Button b){
        this.findViewById(R.id.plus_layout).setBackground(null);
        this.findViewById(R.id.question_layout).setBackground(null);

        LinearLayout parent = (LinearLayout) b.getParent();
        parent.setBackgroundColor(Color.MAGENTA);
    }
}