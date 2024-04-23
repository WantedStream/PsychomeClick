package com.example.psychomeclick.fragments;

import static com.example.psychomeclick.helpers.QuestionLocationHelper.AddQuestionLocation;
import static com.example.psychomeclick.helpers.QuestionLocationHelper.ChangeQuestionLocation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.psychomeclick.R;
import com.example.psychomeclick.helpers.QuestionLocationHelper;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddQuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView currentImage;
    private LinkedHashMap<Integer,Uri> imageMap=new LinkedHashMap<>();
    private ImageView image0,image1,image2,image3,image4;
    private RadioGroup rGroup;
    private Spinner subjectSpinner;
   private final ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uris -> {
               this.currentImage.setImageURI(uris);
                this.imageMap.put(this.currentImage.getId(),uris);
            });

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddQuestionFragment newInstance(String param1, String param2) {
        AddQuestionFragment fragment = new AddQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_question, container, false);
        innit(view);
        return view;
    }

    private void innit(View v) {
         image0=(ImageView) v.findViewById(R.id.questingImg);
         image1 = (ImageView) v.findViewById(R.id.firstAnswerImg);
         image2 = (ImageView) v.findViewById(R.id.secondImageAnswer);
         image3 = (ImageView) v.findViewById(R.id.thirdAnswerImg);
         image4 =(ImageView) v.findViewById(R.id.fourthAnswerImg);
        rGroup = (RadioGroup) v.findViewById(R.id.answerRadios);
        subjectSpinner=(Spinner) v.findViewById(R.id.subjectSpinner);

        FirebaseManager.db.collection("SubjectTree").get().addOnSuccessListener((task2)->{
            DocumentSnapshot tree= task2.getDocuments().get(0);
            DocumentSnapshot subjectsArrayDoc= task2.getDocuments().get(1);
            List<String> subjects = (List<String>) subjectsArrayDoc.get("subjects");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item,subjects);
            subjectSpinner.setAdapter(adapter);
            subjectSpinner.setSelection(0);
            addListeners(v);
        });

    }
    private void chooseImage(ImageView img){
        // Registers a photo picker activity launcher in multi-select mode.
        this.currentImage=img;
       pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
              .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
             .build());
    }

    private void addListeners(View v){
        ((Button) v.findViewById(R.id.addQuestionBtn)).setOnClickListener((btn) -> {
            chooseImage(image0);
        });
        ((Button) v.findViewById(R.id.addFirstAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage(image1);
        });
        ((Button) v.findViewById(R.id.addSecondAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage(image2);
        });
        ((Button) v.findViewById(R.id.addThirdAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage(image3);
        });
        ((Button) v.findViewById(R.id.addFourthAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage(image4);
        });
        ((Button) v.findViewById(R.id.addToStorage)).setOnClickListener((btn) ->{


            AtomicBoolean isOk = new AtomicBoolean(true); // Using AtomicBoolean

            imageMap.forEach((key, value) -> {
                if (value == null) {
                    isOk.set(false);
                }
            });
            if(imageMap.size()<5||!isOk.get()){
                Toast.makeText(getActivity(), "all 5 images must be put",
                        Toast.LENGTH_LONG).show();
                return;
            }
            addQuestiontoDB(((RadioButton)v.findViewById(rGroup.getCheckedRadioButtonId())).getText().toString(),subjectSpinner.getSelectedItem().toString());
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, new QuestionListFragment());
            transaction.commit();
        });
    }

    public void addQuestiontoDB(String correctAnswer,String subject){
        int[] counter=new int[1];
        HashMap<String, Object> q = new HashMap<>();
        q.put("correctAnswer",correctAnswer);
        DocumentReference qdocument = FirebaseManager.db.collection("Questions").document();
        //setCorrectAnswer
        qdocument.set(q).addOnSuccessListener(tsk-> {
            FirebaseManager.QuestionMap.put(qdocument.getId(),Integer.parseInt(correctAnswer));

            //sets the 5 images
            counter[0]=0;//used for last
            int index=0;
            for(Map.Entry<Integer,Uri> entry : this.imageMap.entrySet()) {

                StorageReference fileRef = FirebaseManager.firebaseStorage.getReference().child("QuestionStorage/" +qdocument.getId()+"/images"+ index);
                index++;
                fileRef.putFile(entry.getValue()).addOnSuccessListener(taskSnapshot -> {
                    counter[0]=counter[0]+1;
                    System.out.println(counter[0]);
                    if(counter[0]==5){
                        //set subject
                        FirebaseManager.db.collection("SubjectTree").document("SubjectTreeDoc").get().addOnSuccessListener((t)-> {
                            String newjs = AddQuestionLocation(qdocument.getId(), t.get("tree").toString(), subject);
                            FirebaseManager.db.collection("SubjectTree").document("SubjectTreeDoc").update("tree", newjs).addOnSuccessListener((t2) -> {
                                FragmentManager fm = getParentFragmentManager();
                                FragmentTransaction transaction = fm.beginTransaction();
                                transaction.replace(R.id.contentFragment, new QuestionListFragment());
                                transaction.commit();
                            });
                        });
                    }
                });

            }

        });
    }
}