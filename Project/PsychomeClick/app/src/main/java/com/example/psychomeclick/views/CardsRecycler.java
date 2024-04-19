package com.example.psychomeclick.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.psychomeclick.model.FirebaseManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CardsRecycler extends RecyclerView {
    private CardAdapter adapter;

    private String setId;


    public CardsRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }
    public void setSetId(String setId){
        this.setId=setId;
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
       // List<Card> cardList;
        JsonArray cardList;
        public CardAdapter(Context context) {
            this.context = context;
            this.cardList =new JsonArray();
        }

        public void addCard(JsonArray card) {
            JsonArray jsonArray;
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
            JsonArray card = (JsonArray) cardList.get(position);
            holder.bind(card);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            TextView term, meaning, imageId;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                term = itemView.findViewById(R.id.termEt);
                meaning = itemView.findViewById(R.id.meaningET);

            }

            void bind(JsonArray card) {
                if(card.get(0).isJsonNull()) term.setText("");
                else term.setText(card.get(0).getAsString());
                if(card.get(1).isJsonNull()) meaning.setText("");
                else meaning.setText(card.get(0).getAsString());
                term.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        card.set(0,new Gson().fromJson( s.toString(), JsonElement.class));
                     FirebaseManager.db.collection("Sets").document(setId).update("cards", cardList.toString()).addOnSuccessListener((d) -> {
                        });

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });

                meaning.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        card.set(1,new Gson().fromJson( s.toString(), JsonElement.class));
                        FirebaseManager.db.collection("Sets").document(setId).update("cards", cardList.toString()).addOnSuccessListener((d) -> {
                        });

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                });

            }

        }}
    }

