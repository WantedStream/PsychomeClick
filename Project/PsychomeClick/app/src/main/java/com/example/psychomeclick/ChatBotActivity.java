package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.psychomeclick.model.FirebaseManager;

import com.example.psychomeclick.views.ChatView;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        findViewById(R.id.backbtn).setOnClickListener((b)->{
            Intent intent = new Intent(this,UserActivity.class);
            startActivity(intent);
            finish();
        });

        String apiKey = "AIzaSyBOiEJFefMXc9TqHUPoG0845A5tDeZ6B7E";
        GenerativeModel gm = new GenerativeModel( "gemini-pro",apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content.Builder userContentBuilder = new Content.Builder();
        userContentBuilder.setRole("user");
       userContentBuilder.addText("Your name is PsychoBot. You are an ai I use in psychometric app. your role is to provide answers in a chat to help topics related to the Psychometric Entrance Test (PET) in israel and its questions(The Psychometric Entrance Test (PET) – commonly known in Hebrew as \"ha-Psikhometri\" – is a standardized test that serves as an entrance exam for institutions of higher education in Israel. The PET covers three areas: quantitative reasoning, verbal reasoning and English language. It is administered by the National Institute for Testing and Evaluation (NITE) and plays a considerable role in the admissions process. A score combining students' performance on the PET with the average score of their high school matriculation tests (aka Bagrut) has been found to be a highly predictive indicator of students' academic performance in their first year of higher education.). this is a test. you can talk both in english and hebrew, but each message will be only in hebrew or english, and try to make short messages. note that the name of the user talking to you is "+ FirebaseManager.userData.getName()+".");
        Content userContent = userContentBuilder.build();

        Content.Builder modelContentBuilder = new Content.Builder();
        modelContentBuilder.setRole("model");
       modelContentBuilder.addText("Hello "+FirebaseManager.userData.getName()+". how may I be of assistance to you?");
        Content modelContent = modelContentBuilder.build();

       List<Content> history = Arrays.asList(userContent, modelContent);
        ChatFutures chat = model.startChat(history);

// Create a new user message
      Content.Builder cb= new Content.Builder();
        cb.setRole("user");
        cb.addText("who are you?");
        addInitialMessage(chat,cb.build());


        ((Button)findViewById(R.id.sendusermsg)).setOnClickListener((B)->{
            String userMessageText = ((EditText) findViewById(R.id.msgText)).getText().toString().trim();
            if (!userMessageText.isEmpty()) {
                ((ChatView) findViewById(R.id.chatView)).addMessage(userMessageText, false, false, 0);

                Content.Builder userContentBuilder2 = new Content.Builder();
                userContentBuilder2.setRole("user");
                userContentBuilder2.addText(userMessageText);

                ListenableFuture<GenerateContentResponse> response2 = chat.sendMessage(userContentBuilder2.build());

                Futures.addCallback(response2, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        ChatView chatView = findViewById(R.id.chatView);
                        chatView.addMessage(resultText, true, true, R.drawable.robot_foreground);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        String resultText = t.getMessage();
                        // Handle failure
                    }
                }, getMainExecutor());
                ((EditText) findViewById(R.id.msgText)).setText("");
            }
        });
    }
    public void addInitialMessage(ChatFutures chat, Content msg){
        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(msg);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                ((EditText)  findViewById(R.id.msgText)).setText("");
                ChatView chatView = findViewById(R.id.chatView);

                // Example usage:

                chatView.addMessage(resultText, true, true, R.drawable.robot_foreground);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                String resultText = t.getMessage();
                ((EditText)  findViewById(R.id.msgText)).setText("");
            }
        }, getMainExecutor());
    }

}