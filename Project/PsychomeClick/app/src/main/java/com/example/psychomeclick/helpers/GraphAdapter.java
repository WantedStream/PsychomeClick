package com.example.psychomeclick.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.GraphView;
import dev.bandb.graphview.graph.Node;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration;

public class GraphAdapter {
    private void setupGraphView(View v) {
        RecyclerView recycler = v.findViewById(R.id.recycler);

        // 1. Set a layout manager of the ones described above that the RecyclerView will use.
        BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(100)
                .setSubtreeSeparation(100)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        GraphView graphView=new GraphView(v.getContext());
        graphView.setLa
        recycler.setLayoutManager(new BuchheimWalkerLayoutManager(v.getContext(), configuration));

        // 2. Attach item decorations to draw edges
        recycler.addItemDecoration(new TreeEdgeDecoration());

        // 3. Build your graph
        Graph graph = new Graph();
        Node node1 = new Node("Parent");
        Node node2 = new Node("Child 1");
        Node node3 = new Node("Child 2");

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);

        // 4. You will need a simple Adapter/ViewHolder.
        // 4.1 Your Adapter class should extend from AbstractGraphAdapter
        AbstractGraphAdapter<NodeHolder> adapter = new AbstractGraphAdapter<NodeHolder>() {
            // 4.2 ViewHolder should extend from RecyclerView.ViewHolder
            @Override
            public NodeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nodelayout, parent, false);
                return new NodeHolder(view);
            }

            @Override
            public void onBindViewHolder(NodeHolder holder, int position) {
                //holder.textView.setText(getNodeData(position).toString());
            }
        };

        adapter.submitGraph(graph);
        recycler.setAdapter(adapter);
    }
}
