package com.example.psychomeclick.fragments;

import static com.example.psychomeclick.helpers.QuestionLocationHelper.ChangeQuestionLocation;
import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;
import static com.example.psychomeclick.model.FirebaseManager.firebaseAuth;
import static com.example.psychomeclick.model.FirebaseManager.firebaseStorage;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.helpers.QuestionLocationHelper;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EditQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String qid;
    private ImageView currentlySelectedImage;
    private ImageView questingImg,imageView1,imageView2,imageView3,imageView4;

    private RadioGroup rGroup;
    private Spinner subjectSpinner;
    final ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uris -> {
                if(uris!=null) {
                    currentlySelectedImage.setImageURI(uris);
                    int imageNum=0;
                    if(currentlySelectedImage==imageView1){
                        imageNum=1;

                    }
                    else if(currentlySelectedImage==imageView2){
                        imageNum=2;

                    }
                    else if(currentlySelectedImage==imageView3){
                        imageNum=3;

                    }
                    else if(currentlySelectedImage==imageView4){
                        imageNum=4;
                    }
                    FirebaseManager.saveImage(firebaseStorage.getReference().child("QuestionStorage/" + qid), currentlySelectedImage, "images" +imageNum, getContext());
                }
                });
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EditQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditQuestionFragment newInstance(String param1) {
        EditQuestionFragment fragment = new EditQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public EditQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qid = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_edit_question, container, false);
       // makeStuff(v);
        innit(v);
        return v;
    }

    private void innit(View v){
        ((TextView) v.findViewById(R.id.idTV)).setText(qid);
        questingImg=v.findViewById(R.id.questingImg);
        imageView1=v.findViewById(R.id.answer1);
        imageView2=v.findViewById(R.id.answer2);
        imageView3=v.findViewById(R.id.answer3);
        imageView4=v.findViewById(R.id.answer4);
        rGroup= (RadioGroup)v.findViewById(R.id.answerRadios);
        subjectSpinner= (Spinner) v.findViewById(R.id.subjectSpinner);
        insertData(v);

    }
    private void insertData(View v){
        FirebaseManager.db.collection("Questions").document(qid).get().addOnSuccessListener((t)-> {
            Map<String, Object> data = t.getData();
            StorageReference storageRef = firebaseStorage.getReference();
            StorageReference fileRef0 = storageRef.child("QuestionStorage/" +qid+"/images"+0);
            StorageReference fileRef1 = storageRef.child("QuestionStorage/" +qid+"/images"+1);
            StorageReference fileRef2 = storageRef.child("QuestionStorage/" +qid+"/images"+2);
            StorageReference fileRef3 = storageRef.child("QuestionStorage/" +qid+"/images"+3);
            StorageReference fileRef4 = storageRef.child("QuestionStorage/" +qid+"/images"+4);

            FirebaseManager.loadImage(fileRef0, questingImg,this.getContext());
            FirebaseManager.loadImage(fileRef1, imageView1,this.getContext());
            FirebaseManager.loadImage(fileRef2, imageView2,this.getContext());
            FirebaseManager.loadImage(fileRef3, imageView3,this.getContext());
            FirebaseManager.loadImage(fileRef4, imageView4,this.getContext());

            int correctAnswer=Integer.parseInt(t.get("correctAnswer").toString());
            switch (correctAnswer)  {
                case 1: rGroup.check(R.id.a1);break;
                case 2: rGroup.check(R.id.a2);break;
                case 3: rGroup.check(R.id.a3);break;
                case 4: rGroup.check(R.id.a4);break;
            }

            FirebaseManager.db.collection("SubjectTree").get().addOnSuccessListener((task2)->{
                DocumentSnapshot tree= task2.getDocuments().get(0);
                DocumentSnapshot subjectsArrayDoc= task2.getDocuments().get(1);
                List<String> subjects = (List<String>) subjectsArrayDoc.get("subjects");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item,subjects);
                subjectSpinner.setAdapter(adapter);
                int index=subjects.indexOf(QuestionLocationHelper.findQuestionLocation(qid,tree.getString("tree")));
                subjectSpinner.setSelection(index);
                addListeners(v);
            });
        });

    }
    private  void chooseImage(ImageView img){
        this.currentlySelectedImage=img;
        pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }
    private void addListeners(View v){
        questingImg.setOnClickListener((b)->{
            chooseImage((ImageView)b);
        });
        imageView1.setOnClickListener((b)->{
            chooseImage((ImageView)b);
        });
        imageView2.setOnClickListener((b)->{
            chooseImage((ImageView)b);
        });
        imageView3.setOnClickListener((b)->{
            chooseImage((ImageView)b);
        });
        imageView4.setOnClickListener((b)->{
            chooseImage((ImageView)b);
        });
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                    FirebaseManager.db.collection("Questions").document(qid).update("correctAnswer",checkedRadioButton.getText()).addOnSuccessListener((t)->{

                   });

            }
        });
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                subjectSpinner.setEnabled(false);
                String selectedSubject = (String) parentView.getItemAtPosition(position);
                FirebaseManager.db.collection("SubjectTree").document("SubjectTreeDoc").get().addOnSuccessListener((t)->{
                    String newjs=ChangeQuestionLocation(qid, t.get("tree").toString(), selectedSubject);
                    FirebaseManager.db.collection("SubjectTree").document("SubjectTreeDoc").update("tree",newjs).addOnSuccessListener((t2)->{
                        subjectSpinner.setEnabled(true);
                    });
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}
    /*private void makeStuff(View v){
        ((TextView) v.findViewById(R.id.idTV)).setText(id);
        insertQuestionImages (id,v.findViewById(R.id.questingImg),v.findViewById(R.id.parametersLayout),v.findViewById(R.id.imagesLayout),this.getContext());
        (v).findViewById(R.id.resetBtn).setOnClickListener((b)->{
            reset();
        });
        (v).findViewById(R.id.backBtn).setOnClickListener((b)->{
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new QuestionListFragment());
            transaction.commit();
        });*/
     //   v.findViewById(R.id.applyBtn).setOnClickListener((b)->{
     //       changeQuestionImages(v);
      //      changeTextImages(v);
       //     FragmentManager fm = getParentFragmentManager();
        //    FragmentTransaction transaction = fm.beginTransaction();
        //    transaction.replace(R.id.contentFragment, new QuestionListFragment());
        //    transaction.commit();
       // });
     //   v.findViewById(R.id.questingImg).setOnClickListener((img)->{
      //      chooseImage((ImageView) img);
       // });



    /*public void insertQuestionImages(String id,ImageView questionImg ,LinearLayout tvsLayout, LinearLayout imagesLayout, Context c){
        FirebaseManager.db.collection("Questions").document(id).get().addOnSuccessListener((t)->{
            Map<String,Object> data = t.getData();
            StorageReference storageRef=firebaseStorage.getReference();

            for (Map.Entry<String,Object> entry:data.entrySet()) {
                LinearLayout tlayout = new LinearLayout(c);

                TextView tv = new TextView(c);
                tv.setText(entry.getKey()+":");

                EditText et = new EditText(c);
                int textSize=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, c.getResources().getDisplayMetrics());
                et.setTextSize(textSize);
                tv.setTextSize(textSize);
                et.setHint(entry.getValue()+"");

                tlayout.addView(tv);
                tlayout.addView(et);



                tvsLayout.addView(tlayout);

            }

            StorageReference fileRef0 = storageRef.child("QuestionStorage/" +id+"/images"+0);

            FirebaseManager.loadImage(fileRef0, questionImg,c);

            int height=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics());
            int width=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, c.getResources().getDisplayMetrics());

            for(int i=1;i<QUESTION_IMAGE_COUNT;i++){
                StorageReference fileRef = storageRef.child("QuestionStorage/" +id+"/images"+i);
                ImageView imageView = new ImageView(c);

                ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(height, width);
                imageView.setLayoutParams(params3);
                FirebaseManager.loadImage(fileRef, imageView,c);
                imageView.setOnClickListener((view)->{
                });

                imagesLayout.addView(imageView);
            }
        });

    }*/





    /*private void changeQuestionImages(View v){
       LinearLayout imagesView=v.findViewById(R.id.imagesLayout);
        FirebaseManager.saveImage(firebaseStorage.getReference().child("QuestionStorage/" +id),(ImageView)v.findViewById(R.id.questingImg) ,"images"+0,getContext());

        for (int i = 1; i < QUESTION_IMAGE_COUNT; i++) {
            FirebaseManager.saveImage(firebaseStorage.getReference().child("QuestionStorage/" +id),(ImageView) imagesView.getChildAt(i-1),"images"+i,getContext());
        }

    }
    private void changeTextImages(View v){
        LinearLayout textsView=v.findViewById(R.id.parametersLayout);
        FirebaseManager.db.collection("Questions").document(id).get().addOnSuccessListener((t)->{
            Map<String, Object> updates = new HashMap<>();
            for (int i = 0; i < textsView.getChildCount(); i++) {
               LinearLayout layout=(LinearLayout) (textsView.getChildAt(i));
                EditText et = (EditText) layout.getChildAt(1);
                String value=et.getText().toString();
                if(value.trim().equals("")){
                    value=et.getHint()+"";
                }
                String field=((TextView)layout.getChildAt(0)).getText().toString();
                field=field.substring(0,field.length()-1);
                ((DocumentSnapshot) t).getReference().update(field, value);
            }
        });
    }*/

