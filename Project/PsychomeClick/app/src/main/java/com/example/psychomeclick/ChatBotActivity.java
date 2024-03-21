package com.example.psychomeclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.reactivestreams.Publisher;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        userContentBuilder.addText("Hello, I have 2 dogs in my house.");
        Content userContent = userContentBuilder.build();

        Content.Builder modelContentBuilder = new Content.Builder();
        modelContentBuilder.setRole("model");
        modelContentBuilder.addText("Great to meet you. What would you like to know?");
        Content modelContent = userContentBuilder.build();

        List<Content> history = Arrays.asList(userContent, modelContent);

// Initialize the chat
        ChatFutures chat = model.startChat(history);

// Create a new user message
      Content.Builder cb= new Content.Builder();
        cb.setRole("user");
        cb.addText("How many paws are in my house?");
        Content userMessage=cb.build();
// Send the message
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(userMessage);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                System.out.println(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);

    }
}