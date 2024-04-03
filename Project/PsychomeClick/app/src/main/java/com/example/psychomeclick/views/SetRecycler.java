package com.example.psychomeclick.views;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.fragments.AddSetFragment;
import com.example.psychomeclick.fragments.SetsFragment;
import com.example.psychomeclick.helpers.ChatAdapter;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SetRecycler extends RecyclerView {
    private SetAdapter adapter;
    private Fragment f;

    public SetRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SetRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SetRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(false);
        setLayoutManager(layoutManager);
        adapter=new SetAdapter(context);
        setAdapter(adapter);
    }
    public void setFragment(Fragment f){
        this.f=f;
    }

    public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetsViewHolder> {

        Context context;
        List<String> sets; // List of actual set IDs (not placeholders)
        boolean isAddSet = false; // Flag for "ADD SET" item

        public SetAdapter(Context context) {
            this.context = context;
            this.sets = new ArrayList<>();
        }

        public void addSet(String set) {
            isAddSet = true; // Set flag for "ADD SET" before adding new set
            sets.add(set);
            notifyItemInserted(sets.size() - 1); // Insert "ADD SET" item at position 0
        }

        @NonNull
        @Override
        public SetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setlayout, parent, false);

            return new SetsViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return this.sets.size();
        }

        @Override
        public void onBindViewHolder(@NonNull SetsViewHolder holder, int position) {
            String set = sets.get(position);
            holder.bind(set, isAddSet && position == 0); // Check both set and position for "ADD SET"
        }

        class SetsViewHolder extends RecyclerView.ViewHolder {
            TextView length, dateOfCreate, title;

            public SetsViewHolder(@NonNull View itemView) {
                super(itemView);
                length = itemView.findViewById(R.id.length);
                dateOfCreate = itemView.findViewById(R.id.date);
                title = itemView.findViewById(R.id.title);
            }

            void bind(String set, boolean isAddSet) {
                if (isAddSet) {
                    this.dateOfCreate.setVisibility(GONE);
                    this.length.setVisibility(GONE);
                    this.title.setText("ADD SET");
                    this.title.setTextColor(Color.GRAY);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    this.title.setLayoutParams(params);

                    ((View) title.getParent()).setOnClickListener(b -> {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("cards", "{}");
                        map.put("date", Calendar.getInstance().getTime() + "");
                        map.put("description", "");
                        map.put("public", false);
                        map.put("title", "set");
                        map.put("type", "");
                        map.put("userid", FirebaseManager.firebaseAuth.getUid());

                        FirebaseManager.db.collection("Sets").add(map).addOnCompleteListener((documentReference) -> {
                            sets.add(documentReference.getResult().getId().toString());
                            notifyItemInserted(0); // Update with new set ID at position 0
                        });
                    });

                } else {
                    FirebaseManager.db.collection("Sets").document(set).get().addOnSuccessListener((t) -> {
                        title.setText(t.get("title") + "");
                        dateOfCreate.setText(t.get("date") + "");
                        int x = JsonParser.parseString(t.get("cards") + "").getAsJsonObject().size();
                        length.setText(x + "");

                        ((View) title.getParent()).setOnClickListener((b) -> {
                            FragmentManager fm = f.getParentFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.contentFragment, AddSetFragment.newInstance(set));
                            transaction.commit();
                        });
                    });
                }
            }
        }
}}
