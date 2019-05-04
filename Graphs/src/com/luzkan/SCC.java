package com.luzkan;

// https://www.geeksforgeeks.org/strongly-connected-components/

import java.util.*;

// This class represents a directed graph using adjacency list representation
class SCC {
    private Graph graph;

    SCC(Graph graph) {
        this.graph = graph;
    }

    // A recursive function to print DFS starting from v
    private void DFSUtil(Graph gr, int v, boolean[] visited) {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v + " ");

        // Recur for all the vertices adjacent to this vertex
        for (Edge edge : gr.getVertices().get(v)){
            if (!visited[edge.getV()])
                DFSUtil(gr, edge.getV(), visited);
        }
    }

    // Function that returns reverse (or transpose) of this graph
    private Graph getTranspose() {
        Graph g = new Graph(graph.getV());
        for (int v = 0; v < graph.getV(); v++) {
            // Recur for all the vertices adjacent to this vertex
            for (Edge edge : graph.getVertices().get(v)) g.addEdge(edge.getV(), edge.getU());
        }
        return g;
    }

    private void fillOrder(int v, boolean[] visited, Stack<Integer> stack) {
        // Mark the current node as visited and print it
        visited[v] = true;

        // Recur for all the vertices adjacent to this vertex
        for (Edge edge : graph.getVertices().get(v)){
            if (!visited[edge.getV()])
                fillOrder(edge.getV(), visited, stack);
        }

        // All vertices reachable from v are processed by now, push v to Stack
        stack.push(v);
    }

    // The main function that finds and prints all strongly
    // connected components
    void runMode() {
        long startTime = System.nanoTime();
        Stack<Integer> stack = new Stack<>();

        // Mark all the vertices as not visited (For first DFS)
        boolean[] visited = new boolean[graph.getV()];
        for (int i = 0; i < graph.getV(); i++)
            visited[i] = false;

        // Fill vertices in stack according to their finishing times
        for (int i = 0; i < graph.getV(); i++)
            if (!visited[i])
                fillOrder(i, visited, stack);

        // Create a reversed graph
        Graph gr = getTranspose();

        // Mark all the vertices as not visited (For second DFS)
        for (int i = 0; i < graph.getV(); i++)
            visited[i] = false;

        // Now process all vertices in order defined by Stack
        while (!stack.empty()) {
            // Pop a vertex from stack
            int v = stack.pop();

            // Print Strongly connected component of the popped vertex
            if (!visited[v]) {
                DFSUtil(gr, v, visited);
                System.out.println();
            }
        }
        System.err.println("Time elapsed: " + (System.nanoTime() - startTime)/1000000 + " milliseconds.\n");
    }
}