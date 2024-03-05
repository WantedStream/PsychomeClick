package com.example.psychomeclick.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;

import java.util.List;

public class SubjectNodesAdapter extends RecyclerView.Adapter<SubjectNodesAdapter.DataViewHolder> {
    private List<String> dataList;

    public SubjectNodesAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_node_layout, parent, false);
        System.out.println("aaa");
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.textView.setText(data);
        System.out.println(data+"aaa");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}