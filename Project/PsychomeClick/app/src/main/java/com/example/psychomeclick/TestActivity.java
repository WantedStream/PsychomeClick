package com.example.psychomeclick;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.Question;
import com.example.psychomeclick.model.UserData;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TestActivity extends AppCompatActivity {

    List<String> questionIdList;
    String subject;
    JsonArray answeredQuestions;
    int currentIndex=0;
    Button backbtn,prevbtn,nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        prevbtn= findViewById(R.id.prevbtn);backbtn= findViewById(R.id.gobackbtn);nextbtn = findViewById(R.id.nextbtn);

        prevbtn.setOnClickListener((b)->{loadQuestion(this.currentIndex-1);updateButtons();});
        backbtn.setOnClickListener((b)->{onBackPressed();});
        nextbtn.setOnClickListener((b)->{loadQuestion(this.currentIndex+1);updateButtons();});

        Bundle extras = getIntent().getExtras();
        questionIdList=  Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");


        //remove removed question from users answer list
       JsonObject userProgressJson= FirebaseManager.userData.getJsonProgress();
        answeredQuestions =userProgressJson.getAsJsonArray(subject);
        System.out.println(answeredQuestions);
        Iterator<JsonElement> iterator = answeredQuestions.iterator();
        while (iterator.hasNext()) {
            JsonElement e=iterator.next();
            if (!questionIdList.contains(e.getAsJsonArray().get(0).getAsString())) {
                iterator.remove();
            }
        }
        System.out.println(answeredQuestions);
        String updatedJsonString = FirebaseManager.userData.getGson().toJson(userProgressJson);
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
            prevbtn.setEnabled(true);
            nextbtn.setEnabled(true);
            backbtn.setEnabled(true);

            if(answeredQuestions.size()==0){
                currentIndex=0;
            }
            else{
                currentIndex=answeredQuestions.size()-1;
            }
            loadQuestion(currentIndex);
            updateButtons();
        });

    }


    private void updateButtons(){
        if(currentIndex==0) prevbtn.setEnabled(false);
        else prevbtn.setEnabled(true);

        if(currentIndex==answeredQuestions.size()-1){
            nextbtn.setEnabled(false);
        }
        else{
            nextbtn.setEnabled(true);
        }
        System.out.println(currentIndex+" CURRENT INDEx");
        System.out.println(questionIdList.size());
    }
    private void loadQuestion(int index){
        if(index>answeredQuestions.size()-1){//if question hasnt been answered yet.

           prevbtn.setEnabled(false);
           backbtn.setEnabled(false);
           nextbtn.setEnabled(false);

            String qId=questionIdList.get(index);
            JsonObject jsonObject = FirebaseManager.userData.getGson().fromJson(FirebaseManager.userData.getUserProgress(), JsonObject.class);
            JsonArray jsonArray=jsonObject.getAsJsonArray(subject);
            JsonArray singleNewQ=new JsonArray();
            singleNewQ.add(qId);
            singleNewQ.add(-1);
            jsonArray.add(singleNewQ);
            String updatedJsonString = FirebaseManager.userData.getGson().toJson(jsonObject);
            FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
                prevbtn.setEnabled(true);

                answeredQuestions.add(singleNewQ);
                putImagesInImageViews(singleNewQ);
                currentIndex=index;

                updateButtons();
            });
        }
        else{
            JsonArray question =answeredQuestions.get(index).getAsJsonArray();
            putImagesInImageViews(question);
            currentIndex=index;
            updateButtons();
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


    private void putImagesInImageViews(JsonArray question){
        for (int i = 0; i <= 4; i++) {
            StorageReference fileRef0 = FirebaseManager.firebaseStorage.getReference().child("QuestionStorage/" +question.get(0).getAsString()+"/images"+i);
            switch(i){
                case 0:             loadImage(fileRef0,findViewById(R.id.img0),this);break;
                case 1:            loadImage(fileRef0,findViewById(R.id.img1),this);break;
                case 2:            loadImage(fileRef0,findViewById(R.id.img2),this);break;
                case 3:             loadImage(fileRef0,findViewById(R.id.img3),this);break;
                case 4:             loadImage(fileRef0,findViewById(R.id.img4),this);break;
            }
        }
    }
}