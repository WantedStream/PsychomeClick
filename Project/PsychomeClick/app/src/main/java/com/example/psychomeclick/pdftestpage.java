package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class pdftestpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftestpage);

        WebView webview1 = (WebView) findViewById(R.id.webview1);
        webview1.getSettings().setJavaScriptEnabled(true);
        String filepath = getIntent().getStringExtra("link");


        webview1.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filepath);


    }
}