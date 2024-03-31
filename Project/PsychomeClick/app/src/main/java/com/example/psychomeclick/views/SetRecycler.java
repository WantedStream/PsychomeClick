package com.example.psychomeclick.views;

import android.content.Context;
import android.graphics.Color;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SetRecycler extends RecyclerView {
    private SetAdapter adapter;
    private Fragment f;
    public static final String NEWSET="NEWSET";

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

    public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetsViewHolder>{

        Context context;
        List<String> sets;
        public SetAdapter(Context context){
            this.context = context;
            this.sets = new ArrayList<>();
        }
        public void addSet(String set) {
            sets.add(set);
            notifyItemInserted(sets.size() - 1);
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
            String set=sets.get(position);
            holder.bind(set);
            ((View)holder.title.getParent()).setOnClickListener((v)->{
                FragmentManager fm= f.getParentFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, AddSetFragment.newInstance(set));
                transaction.commit();
            });
        }

        class SetsViewHolder extends RecyclerView.ViewHolder {
            TextView length,dateOfCreate,title;
            public SetsViewHolder(@NonNull View itemView) {
                super(itemView);
                length = itemView.findViewById(R.id.length);
                dateOfCreate = itemView.findViewById(R.id.date);
                title= itemView.findViewById(R.id.title);
            }
            void bind(String set) {
                if(set==NEWSET){
                    this.dateOfCreate.setVisibility(GONE);
                    this.length.setVisibility(GONE);
                    this.title.setText("ADD ITEM");
                    this.title.setTextColor(Color.GRAY);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    this.title.setLayoutParams(params);
                    setOnClickListener((b)->{});
                    return;
                }
              else{


                FirebaseManager.db.collection("Sets").document(set).get().addOnSuccessListener((t)->{
                    title.setText(t.get("title")+"");
                    dateOfCreate.setText(t.get("date").toString());
                    int x= JsonParser.parseString(t.get("cards")+"").getAsJsonObject().size();
                   length.setText( x+"");


                });
                ;
            }
            }
        }
    }
}
