package com.example.psychomeclick.fragments;

import static com.example.psychomeclick.model.Constants.QUESTION_IMAGE_COUNT;
import static com.example.psychomeclick.model.FirebaseManager.insertToQuestionCells;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
        createStuff(view);

        return view;
    }




    private  Queue<LinearLayout> createTB(TableLayout tb,View view){
        tb.removeAllViews();
        int questionIndex=0;
        Queue<LinearLayout> qlist= new LinkedList<LinearLayout>();
        for(int i=0;i<5;i++){
            TableRow row=new TableRow(view.getContext());
            for (int j = 0; j < 8; j++) {
                LinearLayout celllayout = new LinearLayout(view.getContext());
                celllayout.setOrientation(LinearLayout.VERTICAL);
                TextView tv=new TextView(view.getContext());
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(30);

                celllayout.addView(tv);

                    ImageView imgV= new ImageView(view.getContext());
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(900, 1000);
                    imgV.setLayoutParams(params);

                    celllayout.addView(imgV);

                Button  b = new Button(view.getContext());
                b.setText("edit");
                b.setOnClickListener((v)->{
                    FragmentManager fm = getParentFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.contentFragment, new AddQuestionFragment());
                    transaction.commit();
                });
                
                celllayout.addView(b);
                celllayout.setBackgroundColor(Color.BLUE);

                questionIndex++;
                row.addView(celllayout);
                qlist.add(celllayout);


            }
            tb.addView(row);

        }
        return qlist;
    }
    private void updateTBitems( Queue<LinearLayout> qlist){
        insertToQuestionCells(qlist,this.getContext());
    }

    private void createStuff(View view){
        TableLayout tb = view.findViewById(R.id.questionTable);
        updateTBitems(createTB(tb,view));

    }
}