package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.views.SetRecycler;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String setId;


    public AddSetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddSetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSetFragment newInstance(String param1) {
        AddSetFragment fragment = new AddSetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_set, container, false);
        innit(v);
        return v;

    }
    private void innit(View v){
        if(setId== SetRecycler.NEWSET)
            return;
       FirebaseManager.db.collection("Sets").document(setId).get().addOnSuccessListener((t)->{
           ((EditText) v.findViewById(R.id.titleEt)).setText(t.get("title")+"");
           ((EditText) v.findViewById(R.id.descriptionEt)).setText(t.get("description").toString());
           ((TextView) v.findViewById(R.id.date)).setText(t.get("date").toString());
           ((TextView) v.findViewById(R.id.typeEt)).setText(t.get("type").toString());
           ((Switch) v.findViewById(R.id.publicSwitch)).setChecked( Boolean.parseBoolean(t.get("public").toString()));


       });
        ;
    }
}