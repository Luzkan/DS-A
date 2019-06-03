package com.luzkan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.luzkan.Util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        run(2);
        //runToFile(1, 1);
    }

    private static void run(int task) {
        if (task == 1) {
            System.out.println("K, MaxFlow, Paths, Time");
            for (int k = 1; k <= 10; k++) {
                HyperCube graph = new HyperCube(k);
                System.out.println(EdmontsKarp(graph, 0, ((1 << k) - 1), k));
            }
        }
        if (task == 2) {
            System.out.println("K, I, MaxMatch, Time");
            for (int k = 3; k <= 10; k++) {
                for (int i = 1; i <= k; i++) {
                    TwoPartsGraph graph = new TwoPartsGraph(k, i);
                    long startTime = System.nanoTime();
                    System.out.println(k + " " + i + " " + maximumBipartiteMatching(graph) + " " + (System.nanoTime() - startTime));
                }
            }
        }
    }

    private static void runToFile(int task, int numberOfTests) throws IOException {
        File file = new File("src/main/resources/test/hypercube.csv");
        FileWriter fileWriter = new FileWriter(file);

        // Time Measurement for all the tests
        long allTestsStart, allTestsFinish;
        allTestsStart = System.nanoTime();

        if (task == 1) {
            fileWriter.append("k;max_flow;paths;time\n");

            for (int n = 0; n < numberOfTests; n++) {
                System.out.println("\nRunning test #" + n + "/" + numberOfTests);

                for (int k = 1; k <= 16; k++) {
                    HyperCube graph = new HyperCube(k);
                    String info = EdmontsKarp(graph, 0, ((1 << k) - 1), k);
                    String[] split = info.split(" ");
                    //System.out.println(Arrays.toString(split));
                    for (String s : split) {
                        fileWriter.append(s).append(";");
                    }
                    fileWriter.append("\n");
                    System.out.print(k + "... ");
                }
            }
        }

        if (task == 2) {
            fileWriter.append("k;i;matchmax;time\n");

            for (int n = 0; n < numberOfTests; n++) {
                System.out.println("\nRunning test #" + n + "/" + numberOfTests);

                for (int k = 3; k <= 10; k++) {
                    for (int i = 1; i <= k; i++) {
                        TwoPartsGraph graph = new TwoPartsGraph(k, i);
                        long startTime = System.nanoTime();
                        String info = k + " " + i + " " + maximumBipartiteMatching(graph) + " " + (System.nanoTime() - startTime);
                        String[] split = info.split(" ");
                        //System.out.println(Arrays.toString(split));
                        for (String s : split) {
                            fileWriter.append(s).append(";");
                        }
                        fileWriter.append("\n");
                    }
                    System.out.print(k + "... ");
                }
            }
        }

        allTestsFinish = System.nanoTime();
        fileWriter.flush();
        fileWriter.close();
        System.out.println("\nTest done. Took: " + ((allTestsFinish - allTestsStart) / NANO_TO_SEC) + " seconds.");
    }

    /* TASK 1 */

    // https://eduinf.waw.pl/inf/alg/001_search/0146.php
    // https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
    // https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
    // Edmonts-Karp implementation of Ford-Fulkerson algorithm (usage of BFS)
    private static String EdmontsKarp(HyperCube graph, int source, int end, int k) {

        ArrayList<ArrayList<Integer>> residualGraph;
        residualGraph = new ArrayList<>(powerOfTwo(k));
        paths = 0;

        // Start counting time
        long startTime = System.nanoTime();

        // Put the flows in
        for (int u = 0; u < powerOfTwo(k); u++) {
            residualGraph.add(u, new ArrayList<Integer>(k));
            for (int v = 0; v < k; v++) {
                residualGraph.get(u).add(v, graph.cube.get(u).get(v));
            }
        }

        ArrayList<Integer> path = new ArrayList<>(residualGraph.size());
        int maxFlow = 0;

        // Breadth-First Search
        while (breadthFirstSearch(residualGraph, source, end, k, path)) {

            // Find minimum weight on path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = end; v != source; v = path.get(v)) {
                int edge = residualGraph.get(path.get(v)).get(binlog(path.get(v)^v));
                if (edge < pathFlow) {
                    pathFlow = edge;
                }
            }

            for (int v = end; v != source; v = path.get(v)) {
                residualGraph.get(path.get(v)).set(binlog(path.get(v)^v), residualGraph.get(path.get(v)).get(binlog(path.get(v)^v)) - pathFlow);
                residualGraph.get(v).set(binlog(path.get(v)^v), residualGraph.get(v).get(binlog(path.get(v)^v)) + pathFlow);
            }

            maxFlow += pathFlow;
        }

        //System.out.println(k + ", " + maxFlow + ", " + paths + ", " + (System.nanoTime() - startTime));
        return k + " " + maxFlow + " " + paths + " " + (System.nanoTime() - startTime);
    }


    // https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
    private static int paths = 0;

    private static boolean breadthFirstSearch(ArrayList<ArrayList<Integer>> graph, int source, int end, int k, ArrayList<Integer> pi) {
        paths++;
        ArrayList<Boolean> visited = new ArrayList<>(graph.size());

        for (int n = 0; n < graph.size(); n++) {
            visited.add(n, false);
            pi.add(n, -1);
        }

        visited.set(source, true);
        pi.set(source, -1);

        ArrayList<Integer> queue = new ArrayList<>(k);
        queue.add(source);

        while (queue.size() != 0) {

            int u = queue.get(0);
            queue.remove(0);

            for (int i = 0; i < k; i++) {
                // Bitwise [OR]
                int v = (u | powerOfTwo(i));

                if (!visited.get(v) && graph.get(u).get(i) > 0) {
                    visited.set(v, true);
                    pi.set(v, u);
                    queue.add(v);
                }
            }
        }
        return visited.get(end);
    }


    /* TASK 2 */
    // Maximum Bipartite Matching
    // https://www.geeksforgeeks.org/maximum-bipartite-matching/
    private static int maximumBipartiteMatching(TwoPartsGraph graph) {

        ArrayList<Integer> matchR = new ArrayList<>(graph.biGraph.size());
        for (int i = 0; i < graph.biGraph.size(); i++) matchR.add(i, -1);
        int c = 0;

        for (int u = 0; u < graph.biGraph.size(); u++) {
            ArrayList<Boolean> visited = new ArrayList<>(graph.biGraph.size());

            for (int i = 0; i < graph.biGraph.size(); i++)
                visited.add(i, false);

            if (bipartiteMatching(graph, u, visited, matchR)) {
                c++;
            }
        }


        return c;
    }

    private static boolean bipartiteMatching(TwoPartsGraph graph, int u, ArrayList<Boolean> visited, ArrayList<Integer> matchR) {
        for (int v = 0; v < graph.biGraph.size(); v++) {
            if (search(graph.biGraph.get(u), v) && !visited.get(v)) {
                visited.set(v, true);
                if ((matchR.get(v) < 0) || bipartiteMatching(graph, matchR.get(v), visited, matchR)) {
                    matchR.set(v, u);
                    return true;
                }
            }
        }
        return false;
    }
}

