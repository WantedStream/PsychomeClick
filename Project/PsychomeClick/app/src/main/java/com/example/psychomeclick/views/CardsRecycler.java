package com.example.psychomeclick.views;

import static com.example.psychomeclick.fragments.EditSetFragment.disableEditText;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.fragments.EditSetFragment;
import com.example.psychomeclick.model.Card;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import io.grpc.internal.JsonUtil;

public class CardsRecycler extends RecyclerView {
    private CardAdapter adapter;

    private String setId;

    private boolean canEdit;
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

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public class CardAdapter extends RecyclerView.Adapter<CardsRecycler.CardAdapter.CardViewHolder> {

        Context context;
        List<Card> cardList;
        public CardAdapter(Context context) {
            this.context = context;
            this.cardList =new ArrayList<Card>();
        }

        public void addCard(Card card) {
            cardList.add(card);
            adapter.notifyDataSetChanged();
           updateCardsInDb(cardList,setId);
            smoothScrollToPosition(adapter.getItemCount() - 1);
        }
        public void removeCard(Card card) {
            cardList.remove(card);
            adapter.notifyDataSetChanged();
            updateCardsInDb(cardList,setId);
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
            Card card = (Card) cardList.get(position);
            holder.bind(card);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            EditText term, meaning;
            TextView numberTv;
            Card card;

            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                term = itemView.findViewById(R.id.termEt);
                meaning = itemView.findViewById(R.id.meaningET);
                numberTv=itemView.findViewById(R.id.numberTv);

                if(canEdit){
                    term.setOnFocusChangeListener((v,hasFocus)->{
                        if(!hasFocus&&card!=null){
                            card.setTerm(term.getText().toString());
                            updateCardsInDb(cardList,setId);
                        }
                    });
                    meaning.setOnFocusChangeListener((v,hasFocus)->{
                        if(!hasFocus&&card!=null){
                            card.setMeaning(meaning.getText().toString());
                            updateCardsInDb(cardList,setId);
                        }
                    });
                }


            }

            void bind(Card card) {
                numberTv.setText(cardList.indexOf(card)+"");
                 term.setText(card.getTerm());
                 meaning.setText(card.getMeaning());
                if(!canEdit){
                    disableEditText(term);
                    disableEditText(meaning);
                    return;
                }
                this.card=card;

            }


        }}
    private static void updateCardsInDb(List<Card> cardList,String setId){
        JsonArray allcards = new JsonArray();
        cardList.forEach((e)->{
            JsonArray singleCard = new JsonArray();
            singleCard.add(e.getTerm());
            singleCard.add(e.getMeaning());
            allcards.add(singleCard);
        });
        System.out.println(allcards);
        FirebaseManager.db.collection("Sets").document(setId).update("cards", allcards.toString()).addOnSuccessListener((d) -> {

        });
    }
    }

