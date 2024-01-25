package com.example.psychomeclick.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Question> questionList;

    ImageView question,first,second,third,fourth;
    public QuestionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionListFragment newInstance(String param1, String param2) {
        QuestionListFragment fragment = new QuestionListFragment();
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



       View view = inflater.inflate(R.layout.fragment_question_list, container, false);
        questionList=FirebaseManager.getAllQuestions();
        createStuff(view);

        return view;
    }


    private void voidAddQuestionsToTable(){

    }


    private void updateTB(TableLayout tb,View view){
        tb.removeAllViews();
        int questionIndex=0;
        List<Question> copylist= new ArrayList<>(this.questionList);

        for(int i=0;i<5;i++){
            TableRow row=new TableRow(view.getContext());
            for (int j = 0; j < 8; j++) {
                if(questionIndex==copylist.size())
                    break;
                Question question=copylist.get(questionIndex);
                TextView tv=new TextView(view.getContext());
                tv.setText(question.getRightAnswer());
                tv.setTextColor(Color.WHITE);
                row.addView(tv);

                List<Map.Entry<Bitmap, Byte>> questionImages=question.getImages();
                ImageView imageView1 = new ImageView(view.getContext());
                imageView1.setImageBitmap(questionImages.get(0));
                //
                ImageView imageView2 = new ImageView(view.getContext());
                imageView2.setImageBitmap(questionImages.get(1));
                //
                ImageView imageView3 = new ImageView(view.getContext());
                imageView3.setImageBitmap(questionImages.get(2));
                //
                ImageView imageView4 = new ImageView(view.getContext());
                imageView4.setImageBitmap(questionImages.get(3));
                //
                ImageView imageView5 = new ImageView(view.getContext());
                imageView5.setImageBitmap(questionImages.get(4));

                row.addView(tv);
                row.addView(imageView1);
                row.addView(imageView2);
                row.addView(imageView3);
                row.addView(imageView4);
                row.addView(imageView5);
                questionIndex++;
            }
            tb.addView(row);
        }
    }

    private void createStuff(View view){
        TableLayout tb = view.findViewById(R.id.questionTable);
        updateTB(tb,view);
    }
}