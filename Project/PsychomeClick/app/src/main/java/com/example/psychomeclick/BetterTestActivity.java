package com.example.psychomeclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.views.BorderTogglingButton;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BetterTestActivity extends AppCompatActivity {

    ImageButton backbtn,prevbtn,nextbtn;
    ImageView img0;
    BorderTogglingButton img1,img2,img3,img4;
    List<String> allQuestions;
    JsonArray userQuestions;
    String subject;
    int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_better_test);
        backbtn=findViewById(R.id.gobackbtn);
        prevbtn=findViewById(R.id.prevbtn);
        nextbtn=findViewById(R.id.nextbtn);

        prevbtn.setOnClickListener((b)->{
            prevQuestion();
        });
        nextbtn.setOnClickListener((b)->{
            nextQuestion();
        });
        backbtn.setOnClickListener((b)->{
            Intent intent = new Intent(this,UserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            this.finish();
        });

        img0=findViewById(R.id.img0);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        img4=findViewById(R.id.img4);

        Bundle extras = getIntent().getExtras();
        allQuestions=Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");


        userQuestions = FirebaseManager.userData.getSubjectQuestion(subject);

        if(userQuestions==null){
            userQuestions=new JsonArray();
        }
        else{
            //remove removed question from users answer list
            Iterator<JsonElement> iterator = userQuestions.iterator();
            while (iterator.hasNext()) {
                JsonElement e=iterator.next();
                if (!allQuestions.contains(e.getAsJsonArray().get(0).getAsString())) {
                    iterator.remove();
                }
            }
            //reposition stuff for order matching
            for (int i = 0; i < userQuestions.size(); i++) {

            }

        }


        //first time user opened it.
        if(userQuestions.isEmpty()){
            JsonArray newQuestion= new JsonArray();
            newQuestion.add(allQuestions.get(0));
            newQuestion.add(-1);
            userQuestions.add(newQuestion);
            currentIndex=0;
        }

        updateUserProgress();

    }
    public void updateUserProgress() {
        JsonObject newProgress= FirebaseManager.userData.getJsonProgress().getAsJsonObject();
        newProgress.add(subject, userQuestions);
        backbtn.setVisibility(View.INVISIBLE);
        prevbtn.setVisibility(View.INVISIBLE);
        nextbtn.setVisibility(View.INVISIBLE);
        img1.setEnabled(false);
        img2.setEnabled(false);
        img3.setEnabled(false);
        img4.setEnabled(false);
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",newProgress.toString()).addOnSuccessListener((t)->{
            backbtn.setVisibility(View.VISIBLE);
            prevbtn.setVisibility(View.VISIBLE);
            nextbtn.setVisibility(View.VISIBLE);
            img1.setEnabled(true);
            img2.setEnabled(true);
            img3.setEnabled(true);
            img4.setEnabled(true);
            FirebaseManager.userData.setUserProgress(newProgress.toString());
            updateButtonStatus();
            updateImages();
        });
    }

    public void prevQuestion(){
        currentIndex=currentIndex-1;
        updateButtonStatus();
        updateImages();
    }
    public void nextQuestion(){
            currentIndex=currentIndex+1;
            if(currentIndex==userQuestions.size()){
                JsonArray jsonElement= new JsonArray();
                jsonElement.add(allQuestions.get(currentIndex));
                jsonElement.add(-1);
                userQuestions.add(jsonElement);
                updateUserProgress();
            }
            else{
                updateButtonStatus();
                updateImages();
            }
    }
    public void updateButtonStatus(){
        if(currentIndex>0){
            prevbtn.setVisibility(View.VISIBLE);
        }
        else{
            prevbtn.setVisibility(View.INVISIBLE);
        }

        if(currentIndex==allQuestions.size()-1){
            nextbtn.setVisibility(View.INVISIBLE);
        }
        else{
            nextbtn.setVisibility(View.VISIBLE);
        }

    }
        public void updateImages() {
            for (int i = 0; i <= 4; i++) {
                StorageReference fileRef0 = FirebaseManager.firebaseStorage.getReference().child("QuestionStorage/" +userQuestions.get(currentIndex).getAsJsonArray().get(0).getAsString()+"/images"+i);
                switch(i){
                    case 0:             loadImage(fileRef0,findViewById(R.id.img0),this);break;
                    case 1:            loadImage(fileRef0,findViewById(R.id.img1),this);break;
                    case 2:            loadImage(fileRef0,findViewById(R.id.img2),this);break;
                    case 3:             loadImage(fileRef0,findViewById(R.id.img3),this);break;
                    case 4:             loadImage(fileRef0,findViewById(R.id.img4),this);break;
                }
            }
        }

    private static void loadImage(@NonNull StorageReference imageRef, @NonNull ImageView imageView, Context c) {
        Glide.with(c)
                .load(imageRef)
                .signature(new ObjectKey(Long.toString(System.currentTimeMillis())))
                .into(imageView);
        int height=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics());
        int width=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics());

        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));

    }
}