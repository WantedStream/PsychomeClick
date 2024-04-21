package com.example.psychomeclick;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.Question;
import com.example.psychomeclick.model.UserData;
import com.example.psychomeclick.views.BorderTogglingButton;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TestActivity extends AppCompatActivity {

    List<String> questionIdList;
    String subject;
    JsonArray answeredQuestions;
    int currentIndex=0;
    ImageButton backbtn,prevbtn,nextbtn;
    BorderTogglingButton img1,img2,img3,img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        innitView();

        Bundle extras = getIntent().getExtras();
        questionIdList=  Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");

        //remove removed question from users answer list
       JsonObject userProgressJson= FirebaseManager.userData.getJsonProgress();
        answeredQuestions =userProgressJson.getAsJsonArray(subject);

        if(answeredQuestions!=null&&!answeredQuestions.isEmpty()){
            Iterator<JsonElement> iterator = answeredQuestions.iterator();
            while (iterator.hasNext()) {
                JsonElement e=iterator.next();
                if (!questionIdList.contains(e.getAsJsonArray().get(0).getAsString())) {
                    iterator.remove();
                }
            }
        }
        else{
            JsonArray firstQuestionifEmpty=new JsonArray();
            firstQuestionifEmpty.add(questionIdList.get(0));
            firstQuestionifEmpty.add("-1");
            JsonArray totalArray=new JsonArray();
            totalArray.add(firstQuestionifEmpty);
            userProgressJson.add(subject,totalArray);
            answeredQuestions =userProgressJson.getAsJsonArray(subject);
            System.out.println("ADAD UPDATED"+answeredQuestions);
        }

        updateUserProg(userProgressJson);

    }
    private void updateUserProg(JsonObject userProgressJson){

        String updatedJsonString = FirebaseManager.userData.getGson().toJson(userProgressJson);
        System.out.println(userProgressJson +"->>>>>>>>"+ updatedJsonString);
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
            prevbtn.setEnabled(true);
            nextbtn.setEnabled(true);
            backbtn.setEnabled(true);
            FirebaseManager.userData.setUserProgress(updatedJsonString);
            System.out.println(updatedJsonString);
            System.out.println(FirebaseManager.userData.getUserProgress());
            if(answeredQuestions.size()==0){
                currentIndex=0;
            }
            else{
                currentIndex=answeredQuestions.size()-1;
            }
            loadQuestion(currentIndex);
            updateButtons();
            System.out.println(FirebaseManager.userData.getUserProgress());
        });
    }


        private void innitView(){
        prevbtn= findViewById(R.id.prevbtn);backbtn= findViewById(R.id.gobackbtn);nextbtn = findViewById(R.id.nextbtn);
        img1=(BorderTogglingButton) findViewById(R.id.img1);img2=(BorderTogglingButton) findViewById(R.id.img2);img3=(BorderTogglingButton) findViewById(R.id.img3);img4=(BorderTogglingButton) findViewById(R.id.img4);

        prevbtn.setOnClickListener((b)->{loadQuestion(this.currentIndex-1);updateButtons();});
        backbtn.setOnClickListener((b)->{ Intent intent = new Intent(this,UserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            this.finish();});
        nextbtn.setOnClickListener((b)->{loadQuestion(this.currentIndex+1);updateButtons();});
        img1.setOnBorderToggledListener((isOn)-> {
            if(isOn)
            {disableAll(img1);} updateUserAnswer();
        });
        img2.setOnBorderToggledListener((isOn)-> {
            if(isOn)
            {disableAll(img2);} updateUserAnswer();
        });
        img3.setOnBorderToggledListener((isOn)-> {
            if(isOn)
            {disableAll(img3);} updateUserAnswer();
        });
        img4.setOnBorderToggledListener((isOn)-> {
            if(isOn)
            {disableAll(img4);} updateUserAnswer();
        });
    }
    private void disableAll(BorderTogglingButton except){
        this.img1.disable();
        this.img2.disable();
        this.img3.disable();
        this.img4.disable();
        except.enable();

        System.out.println("SADDDDDDDDDDDDDDafdsvxzXC");
        if(FirebaseManager.QuestionMap.get(this.questionIdList.get(currentIndex))==getSelectedAnswer()){
            except.setBorderColor(Color.GREEN);
        }


    }
    private void updateButtons(){
        if(currentIndex==0){ prevbtn.setEnabled(false); prevbtn.setVisibility(View.INVISIBLE);}
        else {prevbtn.setEnabled(true); prevbtn.setVisibility(View.VISIBLE);};

        if(currentIndex==questionIdList.size()-1){
            nextbtn.setEnabled(false);
            nextbtn.setVisibility(View.INVISIBLE);
        }
        else{
            nextbtn.setEnabled(true);
            nextbtn.setVisibility(View.VISIBLE);
        }
    }
    private void loadQuestion(int index){
        if(index>answeredQuestions.size()-1){//if question hasnt been answered yet.

           prevbtn.setEnabled(false);
           backbtn.setEnabled(false);
           nextbtn.setEnabled(false);

            String xId=questionIdList.get(index);
            JsonObject jsonObject = FirebaseManager.userData.getGson().fromJson(FirebaseManager.userData.getUserProgress(), JsonObject.class);
            JsonArray jsonArray=jsonObject.getAsJsonArray(subject);
            JsonArray singleNewQ=new JsonArray();
            singleNewQ.add(xId);
            singleNewQ.add(-1);
            jsonArray.add(singleNewQ);
            String updatedJsonString = FirebaseManager.userData.getGson().toJson(jsonObject);
            FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
                prevbtn.setEnabled(true);
                nextbtn.setEnabled(true);
                backbtn.setEnabled(true);

                answeredQuestions.add(singleNewQ);
                currentIndex=index;
                putImagesInImageViews(singleNewQ);


                updateButtons();

            });
        }
        else{
            JsonArray question =answeredQuestions.get(index).getAsJsonArray();
            currentIndex=index;
            putImagesInImageViews(question);
            updateButtons();
        }

    }
    private void updateUserAnswer(){
        Gson gson = FirebaseManager.userData.getGson();
        JsonObject jsonObject = gson.fromJson(FirebaseManager.userData.getUserProgress(), JsonObject.class);
        System.out.println(jsonObject.getAsJsonArray(subject));
        System.out.println(FirebaseManager.userData.getUserProgress());
        JsonArray jsonQuestion=jsonObject.getAsJsonArray(subject).getAsJsonArray().get(currentIndex).getAsJsonArray();

        JsonElement newElement =gson.toJsonTree(getSelectedAnswer());
        jsonQuestion.set(1,newElement);
        String updatedJsonString = FirebaseManager.userData.getGson().toJson(jsonObject);
        FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
            this.answeredQuestions.get(currentIndex).getAsJsonArray().set(1,newElement);
            FirebaseManager.userData.updateUserProgress(updatedJsonString);
        });
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
        System.out.println(question);
        int answer=question.get(1).getAsInt();
        switch (answer){
            case 1:disableAll(img1);break;
            case 2:disableAll(img2);break;
            case 3:disableAll(img3);break;
            case 4:disableAll(img4);break;
        }//cant use getSelected answer because get selected answer looks at what box is toggled, however no box is toggled yet...

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

    private int getSelectedAnswer(){
        if(img1.isOn())
            return 1;
        else if(img2.isOn())
          return 2;
        else if(img3.isOn())
            return 3;
        else if(img4.isOn())
            return 4;
        else
         return   -1;
    }
}