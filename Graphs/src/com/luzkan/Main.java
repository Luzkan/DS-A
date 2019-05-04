package com.luzkan;

import java.util.Scanner;

public class Main {

    // Weighted Graph I used for testing:
    // 9 14 0 1 4 0 7 8 1 2 8 1 7 11 6 7 1 2 3 7 2 5 4 2 8 2 3 4 9 3 5 14 4 5 10 5 6 2 6 7 1 6 8 6 7 8 7
    // Undirected Unweighted Graph I used for testing:
    // 5 5 0 1 1 2 2 0 3 4 0 3
    private static int mode = 0;
    public static void main(String[] args) {
        runProgram(mode);
    }

    private static void runProgram(int test){
        switch(test){
            case 1: {
                priorityQueue();
            }
            case 2: {
                dijkstra();
            }
            case 3:{
                kruskal();
            }
            case 4:{
                prim();
            }
            case 5:{
                stronglyConnectedComponents();
            }
            default: {
                System.out.print("\nPlease choose program mode: ");
                Scanner s = new Scanner(System.in);
                mode = s.nextInt();
                runProgram(mode);
            }
        }
        System.out.println("Process finished successfully.\nProgram restarts.\n");
        runProgram(mode);
    }

    private static Graph getGraph(boolean directed, boolean weighted) {
        // Scan for the desired amount of Vertices and Edges in whole Graph(V,E) and Create
        Scanner s = new Scanner(System.in);
        System.out.print("Vertices and Edges amount:");
        int totalV = s.nextInt();
        int totalE = s.nextInt();
        Graph graph = new Graph(totalV);

        // Setup edge connections between Vertices (u,v,w -> starting v, ending v, weight of e)
        if(weighted){
            for (int n = 0; n < totalE; n++) {
                int u, v, w;
                u = s.nextInt();
                v = s.nextInt();
                w = s.nextInt();

                // Add Edge and it's reverse to get undirected graph
                graph.addWeightEdge(u, v, w);
                if (!directed) graph.addWeightEdge(v, u, w);
            }
        }else{
            for (int n = 0; n < totalE; n++) {
                int u, v;
                u = s.nextInt();
                v = s.nextInt();

                // Add Edge and it's reverse to get undirected graph
                graph.addEdge(u, v);
                if (!directed) graph.addEdge(v, u);
            }
        }
        return graph;
    }


    private static void priorityQueue() {
        // Create and run
        PriorityQueue pq = new PriorityQueue();
        pq.runMode();
    }

    private static void dijkstra() {
        // Create a graph using Graph creation Method and run
        Graph graph = getGraph(false, true);
        Dijkstra ds = new Dijkstra(graph);
        ds.runMode();
    }

    private static void kruskal() {
        Graph graph = getGraph(true, true);
        Kruskal kk = new Kruskal(graph);
        kk.runMode();
    }

    private static void prim() {
        Graph graph = getGraph(true, true);
        Prim pr = new Prim(graph);
        pr.runMode();
    }

    private static void stronglyConnectedComponents() {
        Graph graph = getGraph(true, false);
        SCC scc = new SCC(graph);
        scc.runMode();
    }
}
