package com.example.psychomeclick.recyclers;

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

/**
 * The type Set recycler.
 */
public class SetRecycler extends RecyclerView {
    private SetAdapter adapter;
    private Fragment f;

    private boolean canDelete;

    /**
     * Instantiates a new Set recycler.
     *
     * @param context the context
     */
    public SetRecycler(@NonNull Context context) {
        super(context);
        init(context);
    }

    /**
     * Instantiates a new Set recycler.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SetRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Instantiates a new Set recycler.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
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

    /**
     * Set fragment.
     *
     * @param f the f
     */
    public void setFragment(Fragment f){
        this.f=f;
    }

    /**
     * Sets can delete.
     *
     * @param canDelete the can delete
     */
    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    /**
     * The type Set adapter.
     */
    public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetsViewHolder> {

        /**
         * The Context.
         */
        Context context;
        /**
         * The Sets.
         */
        List<CardSet> sets;

        /**
         * Instantiates a new Set adapter.
         *
         * @param context the context
         */
        public SetAdapter(Context context) {
            this.context = context;
            this.sets = new ArrayList<>();
        }

        /**
         * Add set.
         *
         * @param set the set
         */
        public void addSet(CardSet set) {
            sets.add(set);
            adapter.notifyDataSetChanged();
          smoothScrollToPosition(adapter.getItemCount() - 1);
        }

        /**
         * Remove all sets.
         */
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
            holder.bind(set);
        }

        /**
         * The type Sets view holder.
         */
        class SetsViewHolder extends RecyclerView.ViewHolder {
            /**
             * The Length.
             */
            TextView length, /**
             * The Date of create.
             */
            dateOfCreate, /**
             * The Title.
             */
            title;
            /**
             * The Delete btn.
             */
            Button deleteBtn;

            /**
             * Instantiates a new Sets view holder.
             *
             * @param itemView the item view
             */
            public SetsViewHolder(@NonNull View itemView) {
                super(itemView);
                length = itemView.findViewById(R.id.length);
                dateOfCreate = itemView.findViewById(R.id.date);
                title = itemView.findViewById(R.id.title);
                deleteBtn=itemView.findViewById(R.id.deletebtn);
                if(!canDelete)
                    deleteBtn.setVisibility(GONE);
            }

            /**
             * Bind.
             *
             * @param set the set
             */
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
