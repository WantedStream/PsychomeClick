package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.gologInBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.gosignUpBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.goaboutBtn).setOnClickListener((v) -> {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        });

    }


}