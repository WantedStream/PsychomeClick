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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;


import java.util.ArrayList;

public class CardsRecycler extends RecyclerView {
    private CardAdapter adapter;
    private Fragment f;

    public CardsRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CardsRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardsRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(false);
        setLayoutManager(layoutManager);
        adapter=new CardsRecycler.CardAdapter(context);
        setAdapter(adapter);
    }
    public void setFragment(Fragment f){
        this.f=f;
    }

    public class CardAdapter extends RecyclerView.Adapter<CardsRecycler.CardAdapter.CardViewHolder> {

        Context context;
        List<CardSet> sets;
        public CardAdapter(Context context) {
            this.context = context;
            this.sets = new ArrayList<>();
        }

        public void addSet(CardSet set) {
            sets.add(set);
            adapter.notifyDataSetChanged();
            smoothScrollToPosition(adapter.getItemCount() - 1);
        }

        @NonNull
        @Override
        public CardsRecycler.CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_layout, parent, false);

            return new CardsRecycler.CardAdapter.CardViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return this.sets.size();
        }

        @Override
        public void onBindViewHolder(@NonNull CardsRecycler.CardAdapter.CardViewHolder holder, int position) {
            CardSet set = sets.get(position);
            holder.bind(set);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView length, dateOfCreate, title;
            Button deleteBtn;
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            void bind(CardSet set) {

            }
        }

    }}

