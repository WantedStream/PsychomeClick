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
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

        prevbtn.setOnClickListener((b)->{
            loadQuestion(this.currentIndex-1);
            updateButtons();
        });
        backbtn.setOnClickListener((b)->{
            onBackPressed();
        });
        nextbtn.setOnClickListener((b)->{
            loadQuestion(this.currentIndex+1);
            updateButtons();
        });

        Bundle extras = getIntent().getExtras();
        questionIdList=  Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");
        answeredQuestions= FirebaseManager.userData.getSubjectQuestion(subject);
        List<Integer> indicesToRemove=new ArrayList<>();
        for (int i = 0; i < answeredQuestions.size(); i++) {
            String qId=answeredQuestions.get(i).getAsJsonArray().get(0).getAsString();
            if(!questionIdList.contains(qId)){
                indicesToRemove.add(i);
            }
        }
        for (JsonElement s: answeredQuestions
             ) {
            if(!questionIdList.contains(s.getAsJsonArray().get(0).getAsString())){
                answeredQuestions.remove(s);
            }
        }
        removeFromAnswerListInFireStore(indicesToRemove);
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
            System.out.println(currentIndex);
            updateButtons();
        });

    }

    private void updateButtons(){
        if(currentIndex==0) prevbtn.setEnabled(false);
        else prevbtn.setEnabled(true);

        if(currentIndex==questionIdList.size()-1){
            nextbtn.setEnabled(false);
        }
        else{
            nextbtn.setEnabled(true);
        }

    }
    private void loadQuestion(int index){
        if(index>answeredQuestions.size()-1){//if question hasnt been answered yet.

           prevbtn.setEnabled(false);
           backbtn.setEnabled(false);
           nextbtn.setEnabled(false);

            String qId=questionIdList.get(index);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(FirebaseManager.userData.getUserProgress(), JsonObject.class);
            JsonArray jsonArray=jsonObject.getAsJsonArray(subject);
            JsonArray singleNewQ=new JsonArray();
            singleNewQ.add(qId);
            singleNewQ.add(-1);
            jsonArray.add(singleNewQ);
            String updatedJsonString = gson.toJson(jsonObject);
            FirebaseManager.db.collection("Users").document(FirebaseManager.firebaseAuth.getUid()).update("userprogress",updatedJsonString).addOnCompleteListener((t)->{
              //  prevbtn.setEnabled(false);
                backbtn.setEnabled(true);
                //nextbtn.setEnabled(false);
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

    private void removeFromAnswerListInFireStore(List<Integer> indices){
        //NOTE:CALL ONLY ON CREATING. CALLING IT AFTER CREATION MAY CAUSE PROBLEMS.
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(FirebaseManager.userData.getUserProgress(), JsonObject.class);
        JsonArray tmpArray=jsonObject.getAsJsonArray(subject);
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < tmpArray.size(); i++) {
            if(!indices.contains(i))
                jsonArray.add(tmpArray.get(i));
        }
        while(!tmpArray.isEmpty())
            tmpArray.remove(tmpArray.get)
        String updatedJsonString = gson.toJson(jsonObject);
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
            System.out.println(currentIndex);
            updateButtons();
        });
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