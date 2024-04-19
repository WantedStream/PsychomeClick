package com.example.psychomeclick.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.fragments.EditSetFragment;
import com.example.psychomeclick.model.CardSet;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class SetRecycler extends RecyclerView {
    private SetAdapter adapter;
    private Fragment f;

    private boolean canDelete;
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

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetsViewHolder> {

        Context context;
        List<CardSet> sets;
        boolean isAddSet = false; // Flag for "ADD SET" item

        public SetAdapter(Context context) {
            this.context = context;
            this.sets = new ArrayList<>();
        }

        public void addSet(CardSet set) {// Set flag for "ADD SET" before adding new set
            sets.add(set);
            adapter.notifyDataSetChanged();
          smoothScrollToPosition(adapter.getItemCount() - 1);
        }
        public void removeAllSets() {
            sets= new ArrayList<>();
            adapter.notifyDataSetChanged();
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
            CardSet set = sets.get(position);
            holder.bind(set); // Check both set and position for "ADD SET"
        }

        class SetsViewHolder extends RecyclerView.ViewHolder {
            TextView length, dateOfCreate, title;
            Button deleteBtn;
            public SetsViewHolder(@NonNull View itemView) {
                super(itemView);
                length = itemView.findViewById(R.id.length);
                dateOfCreate = itemView.findViewById(R.id.date);
                title = itemView.findViewById(R.id.title);
                deleteBtn=itemView.findViewById(R.id.deletebtn);
                if(!canDelete)
                    deleteBtn.setVisibility(GONE);
            }

            void bind(CardSet set) {

                        title.setText(set.getTitle() + "");
                        dateOfCreate.setText(set.getDate());
                        int x = JsonParser.parseString(set.getCards()).getAsJsonArray().size();
                        length.setText(x + "");

                        ((View) title.getParent()).setOnClickListener((b) -> {
                            FragmentManager fm = f.getParentFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.contentFragment, EditSetFragment.newInstance(set.getId()));
                            transaction.commit();
                        });
                        deleteBtn.setOnClickListener((b)->{
                            FirebaseManager.db.collection("Sets").document(set.getId()).delete().addOnSuccessListener((d) -> {
                                adapter.sets.remove(set);
                                adapter.notifyDataSetChanged();
                            });
                        });
                }
            }

}}
