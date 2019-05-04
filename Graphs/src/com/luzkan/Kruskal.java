package com.luzkan;

// https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/

import java.util.*;
import java.lang.*;

class Kruskal {
    // Collection of all edges that will be sorted by Weight
    private ArrayList<Edge> edgeList = new ArrayList<>();
    private Graph graph;
    Kruskal(Graph graph) {
        this.graph = graph;
    }

    // The main function to construct MST using Kruskal's algorithm
    void runMode(){

        for (int n = 0; n < graph.getV(); n++) {
            edgeList.addAll(graph.getVertices().get(n));
        }

        // Step 1:  Sort all the edges in non-decreasing order of their
        // weight.  If we are not allowed to change the given graph, we
        // can create a copy of array of edges
        edgeList.sort(Comparator.comparing(Edge::getWeight));

        // Allocate memory for creating V subsets
        subset[] subsets = new subset[graph.getV()];
        for(int n=0; n<graph.getV(); ++n)
            subsets[n]=new subset();

        // Create V subsets with single elements
        for (int v = 0; v < graph.getV(); ++v){
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        // This will store the resultant MST
        Edge[] result = new Edge[graph.getV()];
        int edgeIndex = 0;
        int edgeSorted = 0;

        // Number of edges to be taken is equal to V-1
        while (edgeIndex < graph.getV() - 1){
            // Step 2: Pick the smallest edge. And increment the index for next iteration
            Edge next_edge;
            next_edge = edgeList.get(edgeSorted++);

            int x = find(subsets, next_edge.getU());
            int y = find(subsets, next_edge.getV());

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge. Else - discard
            if (x != y){
                result[edgeIndex++] = next_edge;
                Union(subsets, x, y);
            }
        }

        // Print the contents of result[] to display the built MST
        System.out.println("[Kruskal] Following are the edges in the constructed MST");
        int totalWeight = 0;
        for (edgeSorted = 0; edgeSorted < edgeIndex; ++edgeSorted) {
            System.out.println("(" + result[edgeSorted].getU() + ", " + result[edgeSorted].getV() + "): " + result[edgeSorted].getWeight());
            totalWeight += result[edgeSorted].getWeight();
        }
        System.out.println("Total Weight: " + totalWeight);
    }

    // A class to represent a subset for union-find
    class subset {
        int parent, rank;
    }

    // A utility function to find set of an element i (uses path compression technique)
    private int find(subset[] subsets, int i) {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y (uses union by rank)
    private void Union(subset[] subsets, int x, int y){
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as root and increment its rank by one
        else{
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
}
