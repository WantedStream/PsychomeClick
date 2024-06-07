package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.CardSet;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.recyclers.SetRecycler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllSetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllSetsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * The Search et.
     */
    EditText searchEt;

    /**
     * Instantiates a new All sets fragment.
     */
    public AllSetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllSetsFragment.
     */
// TODO: Rename and change types and number of parameters
    public static AllSetsFragment newInstance(String param1, String param2) {
        AllSetsFragment fragment = new AllSetsFragment();
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
        View v= inflater.inflate(R.layout.fragment_all_sets, container, false);
        innitView(v);
        return v;
    }

    /**
     * Innit view.
     *
     * @param v the v
     */
    public void innitView(View v){
        searchEt=v.findViewById(R.id.searchEt);
        doSearch("",v);
        v.findViewById(R.id.searchBtn).setOnClickListener((b)->{
            doSearch(searchEt.getText().toString(),v);
        });
    }
    private void doSearch(String str,View v){
        SetRecycler setRecycler=(SetRecycler)v.findViewById(R.id.setAllRecycler);
        setRecycler.setFragment(this);
        setRecycler.setCanDelete(false);
        FirebaseManager.db.collection("Sets").get().addOnSuccessListener((t)-> {
            SetRecycler.SetAdapter adapter= (SetRecycler.SetAdapter) setRecycler.getAdapter();
            adapter.removeAllSets();
            t.getDocuments().forEach((documentSnapshot) -> {
                if(documentSnapshot.get("title").toString().contains(str)&&(Boolean)documentSnapshot.get("public"))
                    ((SetRecycler.SetAdapter) setRecycler.getAdapter()).addSet(new CardSet(documentSnapshot.getId(),documentSnapshot.get("title")+"",documentSnapshot.get("date")+"",Boolean.parseBoolean(documentSnapshot.get("public")+""),documentSnapshot.get("description")+"",documentSnapshot.get("cards")+"",documentSnapshot.get("userId")+""));
            });


        });
    }
}