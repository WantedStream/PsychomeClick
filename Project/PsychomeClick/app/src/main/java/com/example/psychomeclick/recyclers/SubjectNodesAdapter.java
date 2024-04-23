package com.example.psychomeclick.recyclers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;
import com.example.psychomeclick.TestActivity;
import com.example.psychomeclick.model.Node;
import com.example.psychomeclick.views.PercentageRingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SubjectNodesAdapter extends RecyclerView.Adapter<SubjectNodesAdapter.DataViewHolder> {
    private List<Node> dataList;
    private Stack<List<Node>> formerLists;
    public SubjectNodesAdapter(List<Node> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_node_layout, parent, false);
        this.formerLists=new Stack<>();
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Node data = dataList.get(position);
        holder.textView.setText(data.getName());
        holder.percentageRingView.setPercentage(data.getSubPercents());
        // Update the click listener of the percentageRingView
        holder.percentageRingView.setOnClickListener((v) -> {
            Node[] nodes = data.getNodes(); // Assuming getNodes() returns a list of nodes

            if(nodes!=null){
                updateRecyclerView(Arrays.asList(nodes));
            } else if (data.getQuestionList()!=null&&data.getQuestionList().length>0) {
                Intent intent = new Intent(holder.itemView.getContext(), TestActivity.class);
                intent.putExtra("questionList", data.getQuestionList());
                intent.putExtra("subject",data.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().getApplicationContext().startActivity(intent);
            }
            else{
                Toast.makeText(holder.itemView.getContext(), "no questions yet!", Toast.LENGTH_SHORT).show();

            }
        });
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

    private void updateRecyclerView(List<Node> nodes) {
        putToFormerList(dataList);
        dataList.clear(); // Clear the existing list
        dataList.addAll(nodes); // Add the new nodes to the list
        notifyDataSetChanged(); // Notify the adapter about the change
    }
    public void backToFormerNodes() {
        if(formerLists.empty())
            return;
        dataList.clear(); // Clear the existing list
        dataList.addAll(this.formerLists.pop()); // Add the new nodes to the list
        notifyDataSetChanged();

    }
    private void putToFormerList(List<Node> nodes){
        List<Node> tmp=new ArrayList<>();
        for (Node node:nodes) tmp.add(node);
        if(!tmp.isEmpty())
       this.formerLists.push(tmp);
    }
}