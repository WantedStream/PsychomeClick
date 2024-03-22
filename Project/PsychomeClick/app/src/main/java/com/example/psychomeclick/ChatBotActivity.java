package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.views.ChatView;
import com.example.psychomeclick.views.Message;
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
        String apiKey = "AIzaSyBOiEJFefMXc9TqHUPoG0845A5tDeZ6B7E";
        // Use a model that's applicable for your use case (see "Implement basic use cases" below)
        GenerativeModel gm = new GenerativeModel( "gemini-pro",apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content.Builder userContentBuilder = new Content.Builder();
        userContentBuilder.setRole("user");
       userContentBuilder.addText("Your name is PsychoBot. You are an ai I use in psychometric app. your role is to provide answers in a chat to help topics related to israeli hebrew psychometric questions. you can talk both in english and hebrew. note that the name of the user talking to you is "+ FirebaseManager.userData.getName()+".");
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
        cb.addText("give me an psychometric math question");
        Content userMessage=cb.build();
// Send the message
        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(userMessage);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                ChatView chatView = findViewById(R.id.chatView);

               // chatView.addMessage(resultText, true, true, Color.BLACK, R.drawable.bg);
               // chatView.addMessage(resultText, false, false, Color.BLUE, R.drawable.bg2);
               // chatView.addMessage(resultText, true, true, Color.RED, 0);
                ((EditText)  findViewById(R.id.msgText)).setText("");
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                ChatView chatView = findViewById(R.id.chatView);
                String resultText = t.getMessage();
               // chatView.addMessage(resultText, true, true, Color.BLACK, R.drawable.bg);
                //chatView.addMessage(resultText, false, false, Color.BLUE, R.drawable.bg2);
               // chatView.addMessage(resultText, true, true, Color.RED, 0);
                ((EditText)  findViewById(R.id.msgText)).setText("");
            }
        }, getMainExecutor());
        ChatView chatView = findViewById(R.id.chatView);
        chatView.addMessage(new Message("Hello", true, true, Color.BLACK, R.drawable.bg));
        chatView.addMessage(new Message("Hi there", false, false, Color.BLUE, R.drawable.bg5));
        chatView.addMessage(new Message("How are you?", true, true, Color.RED, 0)); // No image

    }
}