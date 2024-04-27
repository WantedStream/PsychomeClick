package com.example.psychomeclick.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.GraphView;
import dev.bandb.graphview.graph.Node;
import dev.bandb.graphview.layouts.GraphLayoutManager;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;

public class GraphAdapter extends AbstractGraphAdapter{
    @NonNull
    @Override
    public NodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nodelayout, parent, false);
        return new NodeHolder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((NodeHolder) holder).button.setText(position);
    }
    public class NodeHolder extends RecyclerView.ViewHolder {
        protected Button button;
        public NodeHolder(@NonNull View itemView) {super(itemView);button=itemView.findViewById(R.id.subjectbtn);}
    }

    public static void setupGraphView(View v) {
        RecyclerView recycler = v.findViewById(R.id.recycler);

        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(100)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        GraphLayoutManager graphLayoutManager=  new BuchheimWalkerLayoutManager(v.getContext(), configuration);
        //recycler.setLayoutManager(graphLayoutManager);
        //recycler.addItemDecoration(new TreeEdgeDecoration());


        Graph graph = new Graph();
        Node node1 = new Node("Parent");
        Node node2 = new Node("Child 1");
        Node node3 = new Node("Child 2");
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);

        GraphAdapter adapter = new GraphAdapter();
        adapter.submitGraph(graph);
        recycler.setAdapter(adapter);


        GraphView graphView = new GraphView(v.getContext());
        graphView.setAdapter(adapter);
        ((LinearLayout)v.findViewById(R.id.graphLinearLayout)).addView(graphView);
    }
}
