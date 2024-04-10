package com.example.psychomeclick.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.CardSet;
import com.example.psychomeclick.model.FirebaseManager;
import com.example.psychomeclick.views.SetRecycler;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetsFragment newInstance(String param1, String param2) {
        SetsFragment fragment = new SetsFragment();
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
        View v= inflater.inflate(R.layout.fragment_sets, container, false);
        innitView(v);
        return v;
    }

    public void innitView(View v){
            SetRecycler setRecycler=(SetRecycler)v.findViewById(R.id.setRecycler);
            setRecycler.setFragment(this);
            FirebaseManager.db.collection("Sets").get().addOnSuccessListener((t)-> {
                t.getDocuments().forEach((documentSnapshot) -> {
                    ((SetRecycler.SetAdapter) setRecycler.getAdapter()).addSet(new CardSet(documentSnapshot.getId(),documentSnapshot.get("title")+"",documentSnapshot.get("date")+"",Boolean.parseBoolean(documentSnapshot.get("public")+""),documentSnapshot.get("description")+"",documentSnapshot.get("cards")+"",documentSnapshot.get("userId")+""));
                });
                //  ((SetRecycler.SetAdapter)setRecycler.getAdapter()).addSet(setRecycler.NEWSET);
                v.findViewById(R.id.addSet).setOnClickListener((b) -> {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("cards", "{}");

                    map.put("date", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()) + "");
                    map.put("description", "");
                    map.put("public", false);
                    map.put("title", "new card set #"+setRecycler.getAdapter().getItemCount());
                    map.put("type", "");
                    map.put("userid", FirebaseManager.firebaseAuth.getUid());
                    FirebaseManager.db.collection("Sets").add(map).addOnSuccessListener((d) -> {
                       ((SetRecycler.SetAdapter) (setRecycler.getAdapter())).addSet(new CardSet(d.getId(),map.get("title")+"",map.get("date")+"",Boolean.parseBoolean(map.get("public")+""),map.get("description")+"",map.get("cards")+"",map.get("userId")+""));
                    });
                });


            });
    }
}