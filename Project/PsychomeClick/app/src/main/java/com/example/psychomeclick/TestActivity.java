package com.example.psychomeclick;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonArray;

import java.util.Arrays;
import java.util.List;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class TestActivity extends AppCompatActivity {

    List<String> questionIdList;
    String subject;
    JsonArray answeredQuestions;
    int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bundle extras = getIntent().getExtras();
        questionIdList=  Arrays.asList(extras.getStringArray("questionList"));
        subject=extras.getString("subject");
        answeredQuestions= FirebaseManager.userData.getSubjectQuestion(subject);
        currentIndex=answeredQuestions.size()-1;
        loadQuestion(currentIndex);

        findViewById(R.id.prevbtn).setOnClickListener((b)->{
            loadQuestion(this.currentIndex-1);
        });
        findViewById(R.id.gobackbtn).setOnClickListener((b)->{
            onBackPressed();
        });
    }

    private void loadQuestion(int index){
        if(index<0){
            return;
        }
        JsonArray question =answeredQuestions.get(index).getAsJsonArray();
        for (int i = 0; i <= 4; i++) {
            StorageReference fileRef0 = FirebaseManager.firebaseStorage.getReference().child("QuestionStorage/" +question.get(0).getAsString()+"/images"+i);
            System.out.println(question.get(0));
            switch(i){
                case 0:             loadImage(fileRef0,findViewById(R.id.img0),this);break;
                case 1:            loadImage(fileRef0,findViewById(R.id.img1),this);break;
                case 2:            loadImage(fileRef0,findViewById(R.id.img2),this);break;
                case 3:             loadImage(fileRef0,findViewById(R.id.img3),this);break;
                case 4:             loadImage(fileRef0,findViewById(R.id.img4),this);break;
            }
        }
        currentIndex=index;
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