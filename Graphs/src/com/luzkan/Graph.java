package com.luzkan;

import java.util.ArrayList;

class Graph {

    // Each Vertex have its own ID and a List of Edges it is linked with
    // That's why we create a List of Vertices that have another List in it
    private ArrayList<ArrayList<Edge>> vertices = new ArrayList<>();
    private int e;
    private int v;

    // Graph creation method is in Main
    Graph(int totalV) {
        v = totalV;
        e = 0;

        // Add those vertices to the list with (at this moment) empty list of edges
        for (int i = 0; i < v; i++) {
            vertices.add(i, new ArrayList<>());
        }
    }

    // Add Edges to given vertex
    void addWeightEdge(int u, int v, int weight) {
        Edge edge = new Edge(u, v, weight);
        vertices.get(u).add(edge);
        e++;
    }

    void addEdge(int u, int v) {
        Edge edge = new Edge(u, v);
        vertices.get(u).add(edge);
        e++;
    }

    ArrayList<ArrayList<Edge>> getVertices() {
        return vertices;
    }

    int getV() {
        return v;
    }

}
