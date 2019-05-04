package com.luzkan;

// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
// https://brilliant.org/wiki/dijkstras-short-path-finder/

import java.util.ArrayList;
import java.util.Scanner;

class Dijkstra {

    private ArrayList<Integer> spt = new ArrayList<>();
    private PriorityQueue pq = new PriorityQueue();
    private Integer[] prev;
    private Integer[] distance;
    private Graph graph;

    Dijkstra(Graph graph) {
        this.graph = graph;
        prev = new Integer[graph.getV()];
        distance = new Integer[graph.getV()];
    }

    void runMode() {
        // Choose starting Vertex to perform Dijkstra
        Scanner s = new Scanner(System.in);
        System.out.print("\nStarting vertex: ");
        int start = s.nextInt();

        // Start counting time to print dijkstra time execution
        long startTime = System.nanoTime();

        // Assign 0 to starting vertex and INF to all the others
        initializeSingleSource(start);

        // Fill Priority Queue with vertices from Graph and assigned distances
        for (int i = 0; i < graph.getV(); i++) {
            pq.insert(i, distance[i]);
        }
        System.out.println("\n[Dijkstra] Prep Done. Performing search.");

        while (!pq.isEmpty()) {
            // Pick the vertex with highest priority/distance value (on first iteration it's the starting one)
            int vertTop = pq.top().getNumber();

            // Add it to the Shortest Path Set
            spt.add(vertTop);

            // Look for adjacent vertices of chosen vertex and update their distance values
            for (Edge edge : graph.getVertices().get(vertTop)) {
                relax(edge);
            }
            pq.pop();

            System.out.println("(" + vertTop + ", " + distance[vertTop] + ")");
            printPath(vertTop);
        }
        System.err.println("Time elapsed: " + (System.nanoTime() - startTime)/1000000 + " milliseconds.\n");
    }

    private void printPath(int u) {
        System.err.print("(" + u + ", " + distance[u] + ")" + " ");
        if (prev[u] != null) {
            printPath(prev[u]);
        } else {
            System.err.println();
        }
    }

    private void initializeSingleSource(int start) {
        // Initialize with infinitely max distance, eg. {MAX_VALUE, MAX_VALUE, MAX_VALUE...}
        for (int i = 0; i < graph.getV(); i++) {
            distance[i] = Integer.MAX_VALUE;
            prev[i] = null;
        }

        // Starting vertex have value 0 (because we are already here), now: {0, MAX_VALUE, MAX_VALUE, MAX_VALUE...}
        distance[start] = 0;
    }

    private void relax(Edge edge) {
        // Reminder: Starting & Ending point of an edge
        int u = edge.getU();
        int v = edge.getV();
        //System.out.println("[DEBUG] (u = " + u + ", v = " + v + "), dist: (u = " + distance[u] + ", v = " + distance[v] + "), edgeW: (" + edge.getWeight() + ")");

        // If Distance is not updated from max or shorter path has been found
        if (distance[v] > distance[u] + edge.getWeight()) {
            // New Distance for that vertex is [all the distance traveled so far + this edge distance]
            distance[v] = distance[u] + edge.getWeight();
            pq.priority(v, distance[v]);
            prev[v] = u;
        }
    }
}
