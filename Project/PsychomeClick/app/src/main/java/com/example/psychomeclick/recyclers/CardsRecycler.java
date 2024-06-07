package com.example.psychomeclick.recyclers;

import static com.example.psychomeclick.fragments.EditSetFragment.disableEditText;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.Card;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Cards recycler.
 */
public class CardsRecycler extends RecyclerView {
    private CardAdapter adapter;

    private String setId;

    private boolean canEdit;

    /**
     * Instantiates a new Cards recycler.
     *
     * @param context the context
     */
    public CardsRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }

    /**
     * Set set id.
     *
     * @param setId the set id
     */
    public void setSetId(String setId){
        this.setId=setId;
    }

    /**
     * Instantiates a new Cards recycler.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public CardsRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Instantiates a new Cards recycler.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
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

    /**
     * Sets can edit.
     *
     * @param canEdit the can edit
     */
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    /**
     * The type Card adapter.
     */
    public class CardAdapter extends RecyclerView.Adapter<CardsRecycler.CardAdapter.CardViewHolder> {

        /**
         * The Context.
         */
        Context context;
        /**
         * The Card list.
         */
        List<Card> cardList;

        /**
         * Instantiates a new Card adapter.
         *
         * @param context the context
         */
        public CardAdapter(Context context) {
            this.context = context;
            this.cardList =new ArrayList<Card>();
        }

        /**
         * Add card.
         *
         * @param card the card
         */
        public void addCard(Card card) {
            cardList.add(card);
            adapter.notifyDataSetChanged();
            if (canEdit) {
                updateCardsInDb(cardList,setId);
            }
            smoothScrollToPosition(adapter.getItemCount() - 1);
        }

        /**
         * Remove card.
         *
         * @param card the card
         */
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

        /**
         * The type Card view holder.
         */
        class CardViewHolder extends RecyclerView.ViewHolder {
            /**
             * The Term.
             */
            EditText term, /**
             * The Meaning.
             */
            meaning;
            /**
             * The Number tv.
             */
            TextView numberTv, /**
             * The Remove tv.
             */
            removeTv;
            /**
             * The Card.
             */
            Card card;


            /**
             * Instantiates a new Card view holder.
             *
             * @param itemView the item view
             */
            public CardViewHolder(@NonNull View itemView) {
                super(itemView);
                term = itemView.findViewById(R.id.termEt);
                meaning = itemView.findViewById(R.id.meaningET);
                numberTv=itemView.findViewById(R.id.numberTv);
                removeTv=itemView.findViewById(R.id.removeCard);
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
                    removeTv.setOnClickListener((b)->{
                        if(this.card!=null)
                        removeCard(card);
                    });
                }
                else{
                    removeTv.setVisibility(GONE);
                }


            }

            /**
             * Bind.
             *
             * @param card the card
             */
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

        FirebaseManager.db.collection("Sets").document(setId).update("cards", allcards.toString()).addOnSuccessListener((d) -> {

        });
    }
    }

