package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.psychomeclick.fragments.SimulationsFragment;

public class pdftestpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftestpage);

        WebView webview1 = (WebView) findViewById(R.id.webview1);
        webview1.getSettings().setJavaScriptEnabled(true);
        String pdf = getIntent().getStringExtra("link");

        System.out.println(pdf);

        webview1.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        findViewById(R.id.backbtn).setOnClickListener((v)->{
            Intent myIntent = new Intent(this, UserActivity.class);
            myIntent.putExtra("selectboxfrag","Simulations");
            startActivity(myIntent);
            finish();
        });
    }
}