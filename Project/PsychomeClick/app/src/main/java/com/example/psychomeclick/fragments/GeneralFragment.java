package com.example.psychomeclick.fragments;

import static android.content.ContentValues.TAG;
import static com.example.psychomeclick.model.FirebaseManager.db;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.psychomeclick.R;
import com.example.psychomeclick.recyclers.SubjectNodesAdapter;
import com.example.psychomeclick.model.Node;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Instantiates a new General fragment.
     */
    public GeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralFragment.
     */
// TODO: Rename and change types and number of parameters
    public static GeneralFragment newInstance(String param1, String param2) {
        GeneralFragment fragment = new GeneralFragment();
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
        View v= inflater.inflate(R.layout.fragment_general, container, false);
        makeStuff(v);
        return v;
    }

    /**
     * Make stuff.
     *
     * @param v the v
     */
    public void makeStuff(View v){
        RecyclerView recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Node> dataList = new ArrayList<>();

        SubjectNodesAdapter adapter = new SubjectNodesAdapter(dataList);
        recyclerView.setAdapter(adapter);
        Button upbtn=  v.findViewById(R.id.backToFormerNode);
        upbtn.setVisibility(View.INVISIBLE);
        upbtn.setOnClickListener((b)->{
            adapter.backToFormerNodes();
        });
        adapter.setUpButton(upbtn);
        db.collection("SubjectTree")
                .get()
                .addOnCompleteListener((task)->{
                        if (task.isSuccessful()) {
                            Gson gson = new Gson();
                            Node node = gson.fromJson( task.getResult().getDocuments().get(0).get("tree").toString(), Node.class);
                            for(Node n: node.getNodes()){
                                dataList.add(n);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting tree: ", task.getException());
                        }
                    }
                );
    }
}