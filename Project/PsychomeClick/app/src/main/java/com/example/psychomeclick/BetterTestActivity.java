package com.example.psychomeclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BetterTestActivity extends AppCompatActivity {

    ImageButton backbtn,prevbtn,nextbtn;
    ImageView img0;
    BorderTogglingButton img1,img2,img3,img4;

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

        img1.setOnBorderToggledListener(b->{
            JsonArray qtmp= userQuestions.get(currentIndex).getAsJsonArray();
            if(b==true){
                qtmp.set(1,new JsonPrimitive(1));
                img2.disable();
                img3.disable();
                img4.disable();
                if(FirebaseManager.QuestionMap.get(qtmp.get(0).getAsString())==qtmp.get(1).getAsInt())
                    img1.setBorderColor(Color.GREEN);
                else
                    img1.setBorderColor(Color.RED);
            }
            else{
                qtmp.set(1, new JsonPrimitive(-1));
            }
            updateUserProgress(false);
        });
        img2.setOnBorderToggledListener(b->{
            JsonArray qtmp= userQuestions.get(currentIndex).getAsJsonArray();
            if(b==true){
                qtmp.set(1,new JsonPrimitive(2));
                img1.disable();
                img3.disable();
                img4.disable();
                if(FirebaseManager.QuestionMap.get(qtmp.get(0).getAsString())==qtmp.get(1).getAsInt())
                    img2.setBorderColor(Color.GREEN);
                else
                    img2.setBorderColor(Color.RED);
            }
            else{
                qtmp.set(1, new JsonPrimitive(-1));
            }
            updateUserProgress(false);
        });
        img3.setOnBorderToggledListener(b->{
            JsonArray qtmp= userQuestions.get(currentIndex).getAsJsonArray();
            if(b==true){
                qtmp.set(1,new JsonPrimitive(3));
                img1.disable();
                img2.disable();
                img4.disable();
                if(FirebaseManager.QuestionMap.get(qtmp.get(0).getAsString())==qtmp.get(1).getAsInt())
                    img3.setBorderColor(Color.GREEN);
                else
                    img3.setBorderColor(Color.RED);
            }
            else{
                qtmp.set(1, new JsonPrimitive(-1));
            }
            updateUserProgress(false);
        });
        img4.setOnBorderToggledListener(b->{
            JsonArray qtmp= userQuestions.get(currentIndex).getAsJsonArray();
            if(b==true){
                qtmp.set(1,new JsonPrimitive(4));
                img1.disable();
                img2.disable();
                img3.disable();
                if(FirebaseManager.QuestionMap.get(qtmp.get(0).getAsString())==qtmp.get(1).getAsInt())
                    img4.setBorderColor(Color.GREEN);
                else
                    img4.setBorderColor(Color.RED);
            }
            else{
                qtmp.set(1, new JsonPrimitive(-1));
            }
            updateUserProgress(false);
        });





        Bundle extras = getIntent().getExtras();
        List<String>  allQuestions=Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");


        userQuestions = FirebaseManager.userData.getSubjectQuestion(subject);

        if(userQuestions==null){
            userQuestions=new JsonArray();
        }


        JsonArray newUserQuestions=new JsonArray();
        for (int i = 0; i < allQuestions.size(); i++) {
            String currentQuestionId=allQuestions.get(i);
            JsonArray question= deleteFromList(currentQuestionId,userQuestions);
            System.out.println(question);
            if(question==null){
                question= new JsonArray();
                question.add(currentQuestionId);
                question.add(-1);
            }
                newUserQuestions.add(question);
        }

        userQuestions=newUserQuestions;

        currentIndex=userQuestions.size()-1;

        updateUserProgress(true);

    }

    public JsonArray deleteFromList(String qid,JsonArray list){
        for (JsonElement q:list) {
            if(q.getAsJsonArray().get(0).getAsString().equals(qid)){
                list.remove(q);
                return q.getAsJsonArray();
            }
            System.out.println(q.getAsJsonArray().get(0).getAsString()+"=="+qid);
        }
        return null;
    }
    public void updateUserProgress(Boolean updateImages) {
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

            if(updateImages){
                updateToggleBoxes();
                updateImages();
            }

        });
    }

    public void prevQuestion(){
        currentIndex=currentIndex-1;
        updateToggleBoxes();
        updateButtonStatus();
        updateImages();

    }
    public void nextQuestion(){
          currentIndex=currentIndex+1;
          updateToggleBoxes();
          updateButtonStatus();
          updateImages();
    }
    public void updateToggleBoxes(){
        img1.disable();
        img2.disable();
        img3.disable();
        img4.disable();
        switch (userQuestions.get(currentIndex).getAsJsonArray().get(1).getAsInt()){
            case 1:img1.toggleBorder();break;
            case 2:img2.toggleBorder();break;
            case 3:img3.toggleBorder();break;
            case 4:img4.toggleBorder();break;
        }
    }
    public void updateButtonStatus(){
        if(currentIndex>0){
            prevbtn.setVisibility(View.VISIBLE);
        }
        else{
            prevbtn.setVisibility(View.INVISIBLE);
        }

        if(currentIndex==userQuestions.size()-1){
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