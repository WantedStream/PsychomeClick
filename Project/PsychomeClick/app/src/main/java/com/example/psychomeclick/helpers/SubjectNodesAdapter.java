package com.example.psychomeclick.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.model.Node;
import com.example.psychomeclick.views.PercentageRingView;

import java.util.List;

public class SubjectNodesAdapter extends RecyclerView.Adapter<SubjectNodesAdapter.DataViewHolder> {
    private List<Node> dataList;

    public SubjectNodesAdapter(List<Node> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_node_layout, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Node data = dataList.get(position);
        holder.textView.setText(data.getName());
        holder.percentageRingView.setPercentage(30);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        PercentageRingView percentageRingView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            System.out.println(itemView);
            textView = itemView.findViewById(R.id.subjectTV);
            percentageRingView = itemView.findViewById(R.id.percentage);
            //percentageRingView.setOnClickListener();
        }
    }
}