package com.example.psychomeclick.recyclers;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.fragments.EditQuestionFragment;
import com.example.psychomeclick.model.FirebaseManager;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * The type Question list adapter.
 */
public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.DataViewHolder>{
    private List<String> dataList;
    private FragmentManager fragmentManager;

    /**
     * Instantiates a new Question list adapter.
     *
     * @param fragmentManager the fragment manager
     * @param dataList        the data list
     */
    public QuestionListAdapter(FragmentManager fragmentManager, List<String> dataList) {
        this.dataList = dataList;
        this.fragmentManager=fragmentManager;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_question_item, parent, false);

        return new QuestionListAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.textView.setText(data);
        holder.btn.setOnClickListener((v)->{
            FragmentManager fm = fragmentManager;
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, EditQuestionFragment.newInstance(data));
            transaction.commit();
        });
        StorageReference fileRef = FirebaseManager.firebaseStorage.getReference().child("QuestionStorage/" +data+"/images"+0);
        FirebaseManager.loadImage(fileRef,holder.imageView,holder.btn.getContext());
        System.out.println(getItemCount());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * The type Data view holder.
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Text view.
         */
        TextView textView;
        /**
         * The Image view.
         */
        ImageView imageView;
        /**
         * The Btn.
         */
        Button btn;

        /**
         * Instantiates a new Data view holder.
         *
         * @param itemView the item view
         */
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println(itemView);
            textView = itemView.findViewById(R.id.qid);
            imageView = itemView.findViewById(R.id.qimg);
            btn=itemView.findViewById(R.id.editbtn);


        }
    }

}
