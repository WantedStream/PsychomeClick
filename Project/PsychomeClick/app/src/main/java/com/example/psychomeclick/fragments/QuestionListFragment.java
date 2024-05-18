package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.recyclers.QuestionListAdapter;
import com.example.psychomeclick.model.FirebaseManager;

import java.util.Arrays;
import java.util.Set;

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

        makeContent(view);
        return view;
    }

    private void makeContent(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerquestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,true));

        // Initialize data
        Set<String> dataset = FirebaseManager.QuestionMap.keySet();
        String[] strArr=new String[dataset.size()];
        dataset.toArray(strArr);
        // Initialize adapter
        System.out.println(dataset);
        recyclerView.setAdapter(new QuestionListAdapter(getParentFragmentManager(), Arrays.asList(strArr)));
    }

    //must pass tv and not string because getting the string from the text view before it has been set to id will give error
}