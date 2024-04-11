package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.FirebaseManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String setId;

     EditText titleEt,descriptionEt,typeEt;
     Switch publicSwitch;

    public EditSetFragment() {
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
    public static EditSetFragment newInstance(String param1) {
        EditSetFragment fragment = new EditSetFragment();
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
        View v=inflater.inflate(R.layout.fragment_edit_set, container, false);
        innit(v);
        return v;

    }
    private void innit(View v){
            addListeners(v);
            v.findViewById(R.id.cardRecycler);
    }
    private void addListeners(View v){
        titleEt=((EditText) v.findViewById(R.id.titleEt));descriptionEt= v.findViewById(R.id.descriptionEt);typeEt= v.findViewById(R.id.typeEt);publicSwitch=(Switch) v.findViewById(R.id.publicSwitch);
        FirebaseManager.db.collection("Sets").document(setId).get().addOnSuccessListener((t)->{
            ((TextView) v.findViewById(R.id.date)).setText(t.get("date")+"");
            publicSwitch.setChecked( Boolean.parseBoolean(t.get("public")+""));
            descriptionEt.setText(t.get("description")+"");
            typeEt.setText(t.get("type")+"");
            titleEt.setText(t.get("title")+"");

        });
        titleEt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FirebaseManager.db.collection("Sets").document(setId).update("title",s.toString());
            }public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        descriptionEt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FirebaseManager.db.collection("Sets").document(setId).update("description",s.toString());
            }public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        typeEt.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                FirebaseManager.db.collection("Sets").document(setId).update("type",s.toString());
            }public void beforeTextChanged(CharSequence s, int start, int count, int after) {}public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        publicSwitch.setOnCheckedChangeListener((b,isChecked)->{
            FirebaseManager.db.collection("Sets").document(setId).update("public",isChecked+"");

        });
    }
}