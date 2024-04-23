package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PdfTestPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftestpage);

        WebView webview1 = (WebView) findViewById(R.id.webview1);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
       // String pdf = getIntent().getStringExtra("link");
            String testTime=getIntent().getStringExtra("testTime").toString();
        webview1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        // Create an Executor
        Executor executor = Executors.newSingleThreadExecutor();

        // Execute Jsoup connection in the background thread

        executor.execute(() -> {
            try {
                // Fetch the HTML content from the URL
                Document document = Jsoup.connect("https://www.nite.org.il/psychometric-entrance-test/preparation/hebrew-practice-tests/").get();
                Elements td = document.select("td");

                Element span = td.select("span:containsOwn(" + testTime+ ")").first();
                if (span != null) {
                    Element linkElement = span.parent();
                    String url = linkElement.attr("href");
                    System.out.println("Found link: " + url);
                    // ALL webview methods must be called on the SAME THREAD
                    webview1.post(() -> webview1.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url));
                } else {
                    System.out.println(testTime + " not found");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
       //
        findViewById(R.id.backbtn).setOnClickListener((v)->{
            Intent myIntent = new Intent(this, UserActivity.class);
            myIntent.putExtra("selectboxfrag","Simulations");
            startActivity(myIntent);
            finish();
        });
    }
}