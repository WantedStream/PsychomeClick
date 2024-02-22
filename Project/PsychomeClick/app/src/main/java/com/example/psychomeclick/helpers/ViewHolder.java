package com.example.psychomeclick.helpers;

import android.content.res.Configuration;
import android.view.View;
import android.widget.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.example.psychomeclick.R;

import dev.bandb.graphview.AbstractGraphAdapter;
import dev.bandb.graphview.graph.Graph;
import dev.bandb.graphview.graph.GraphView;
import dev.bandb.graphview.graph.Node;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerConfiguration;
import dev.bandb.graphview.layouts.tree.BuchheimWalkerLayoutManager;
import dev.bandb.graphview.layouts.tree.TreeEdgeDecoration;

public class ViewHolder {
    private void setupGraphView(View v) {
        GraphView graphView = v.findViewById(R.id.recycler);

        // example tree
        final Graph graph = new Graph();
        final Node node1 = new Node("Parent");
        final Node node2 = new Node("Child 1");
        final Node node3 = new Node("Child 2");

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);

        // you can set the graph via the constructor or use the adapter.setGraph(Graph) method

        Adapter adapter = new AbstractGraphAdapter<GraphView>(graph) {

            @Override
            public GraphView onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            public void onBindViewHolder(GraphView.ViewHolder viewHolder, Object data, int position) {
                ((SimpleViewHolder) viewHolder).textView.setText(data.toString());
            }
        };
        graphView.setAdapter(adapter);

        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));
    }

    class SimpleViewHolder extends GraphView.ViewHolder {
        TextView textView;

        SimpleViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
