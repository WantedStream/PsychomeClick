package com.example.psychomeclick.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.psychomeclick.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
   private final ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uris -> {
               this.currentImage.setImageURI(uris);
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
        createStuff(view);
        return view;
    }

    private void createStuff(View v) {
        //try{
          //  Bitmap bm = BitmapFactory.decodeResource( getResources(), R.drawable.aa);
          //  String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

          //  File file = new File(extStorageDirectory, "photo1.PNG");
          //  FileOutputStream outStream = new FileOutputStream(file);
           // bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
          //  outStream.flush();
           // outStream.close();
       // }
        //catch(Exception e){
        //    System.out.println(e+"     ERRROR!!!!!!!!!!!!");
       // }

        ((Button) v.findViewById(R.id.addQuestionBtn)).setOnClickListener((btn) -> {
            chooseImage((ImageView) v.findViewById(R.id.questingImg));
        });
        ((Button) v.findViewById(R.id.addFirstAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage((ImageView) v.findViewById(R.id.firstAnswerImg));
        });
        ((Button) v.findViewById(R.id.addSecondAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage((ImageView) v.findViewById(R.id.secondImageAnswer));
        });
        ((Button) v.findViewById(R.id.addThirdAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage((ImageView) v.findViewById(R.id.thirdAnswerImg));
        });
        ((Button) v.findViewById(R.id.addFourthAnswerBtn)).setOnClickListener((btn) -> {
            chooseImage((ImageView) v.findViewById(R.id.fourthAnswerImg));
        });
    }
    private void chooseImage(ImageView img){
        // Registers a photo picker activity launcher in multi-select mode.
        this.currentImage=img;
       pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
              .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
             .build());
    }


}