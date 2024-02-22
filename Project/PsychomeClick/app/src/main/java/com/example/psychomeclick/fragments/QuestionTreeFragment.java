package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.psychomeclick.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionTreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionTreeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public QuestionTreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment QuestionTreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionTreeFragment newInstance(String param1) {
        QuestionTreeFragment fragment = new QuestionTreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_question_tree, container, false);
        makeUI(v);
        return v;
    }

    private void makeUI(View view){
        TreeView treeView = findViewById(R.id.idTreeView);

    }
}