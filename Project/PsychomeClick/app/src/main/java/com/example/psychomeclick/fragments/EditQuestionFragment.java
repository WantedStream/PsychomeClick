package com.example.psychomeclick.fragments;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;
import static com.example.psychomeclick.model.FirebaseManager.firebaseStorage;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.storage.StorageReference;

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
    private String id;


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
            id = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_edit_question, container, false);
        makeStuff(v);
        return v;
    }

    private void makeStuff(View v){
        ((TextView) v.findViewById(R.id.idTV)).setText(id);
        insertQuestionImages (id,v.findViewById(R.id.questingImg),v.findViewById(R.id.parametersLayout),v.findViewById(R.id.imagesLayout),this.getContext());

    }

    public static void insertQuestionImages(String id,ImageView questionImg ,LinearLayout tvsLayout, LinearLayout imagesLayout, Context c){
        FirebaseManager.db.collection("Questions").document(id).get().addOnSuccessListener((t)->{
            Map<String,Object> data = t.getData();
            for (Map.Entry<String,Object> entry:data.entrySet()) {
                LinearLayout tlayout = new LinearLayout(c);

                TextView tv = new TextView(c);
                tv.setText(entry.getKey()+":");

                EditText et = new EditText(c);
                et.setHint(entry.getValue()+"");

                tlayout.addView(tv);
                tlayout.addView(et);

                StorageReference storageRef=firebaseStorage.getReference();
                StorageReference fileRef0 = storageRef.child("QuestionStorage/" +id+"/images"+0);

                FirebaseManager.loadImage(fileRef0, questionImg,c);

                tvsLayout.addView(tlayout);
                for(int i=1;i<QUESTION_IMAGE_COUNT;i++){
                    StorageReference fileRef = storageRef.child("QuestionStorage/" +id+"/images"+i);
                    ImageView imageView = new ImageView(c);
                    ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(1000, 1000);
                    imageView.setLayoutParams(params3);
                    FirebaseManager.loadImage(fileRef, imageView,c);


                    imagesLayout.addView(imageView);
                }

            }
        });

    }
}