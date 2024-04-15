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
import com.example.psychomeclick.model.Card;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

public class CardsRecycler extends RecyclerView {
    private CardAdapter adapter;

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

    public class CardAdapter extends RecyclerView.Adapter<CardsRecycler.CardAdapter.CardViewHolder> {

        Context context;
        List<Card> cardList;
        public CardAdapter(Context context) {
            this.context = context;
            this.cardList = new ArrayList<>();
        }

        public void addCard(Card card) {
            cardList.add(card);
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
            return this.cardList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull CardsRecycler.CardAdapter.CardViewHolder holder, int position) {
            Card set = cardList.get(position);
            holder.bind(set);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView term, meaning, imageId;
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                term=itemView.findViewById(R.id.termEt);
                meaning=itemView.findViewById(R.id.meaningET);

            }

            void bind(Card card) {
                term.setText(card.getTerm());
                meaning.setText(card.getMeaning());
            }
        }

    }}

