package com.luzkan;

// https://www.geeksforgeeks.org/prims-mst-for-adjacency-list-representation-greedy-algo-6/

class Prim {

    private Graph graph;
    private Integer[] keyValue;
    private Boolean[] mstset;

    Prim(Graph graph) {
        this.graph = graph;
        keyValue = new Integer[graph.getV()];
        mstset = new Boolean[graph.getV()];
    }

    void runMode(){
        // Stores total amount of weight of all edges to display it after Prim is done
        int totalWeight = 0;

        // Stores the parents of a vertex
        int[] parent = new int[graph.getV()];

        // Initialize as INF and include the source vertex in mstset
        initializeSingleSource(0);
        mstset[0] = true;

        // PriorityQueue
        PriorityQueue queue = new PriorityQueue();
        for (int n = 0; n < graph.getV(); n++)
            queue.insert(n, keyValue[n]);

        // Loops until the PriorityQueue is not empty
        while (!queue.isEmpty()) {
            // Extracts a node with min key value
            Node u = queue.top();

            // Include that node into mstset
            mstset[u.getNumber()] = true;

            // For all adjacent vertex of the extracted vertex V
            for (Edge iterator : graph.getVertices().get(u.getNumber())) {
                //System.out.println("[Debug] Pop: " + queue.top().getNumber() + ", Iterator: " iterator.getV);

                // If V is in PriorityQueue
                if (!mstset[iterator.getV()]) {

                    // If the key value of the adjacent vertex is more than the extracted key
                    // update the key value of adjacent vertex to update first remove and add the updated vertex
                    if (keyValue[iterator.getV()] > iterator.getWeight()) {
                        parent[iterator.getV()] = iterator.getU();
                        keyValue[iterator.getV()] = iterator.getWeight();
                        queue.priority(iterator.getV(), keyValue[iterator.getV()]);
                        totalWeight += iterator.getWeight();
                        System.out.println("(" + parent[iterator.getV()] + ", " + iterator.getV() + "): " + iterator.getWeight());
                    }
                }
            }
            queue.pop();
        }
        System.out.println("Total Weight: " + totalWeight);
    }

    private void initializeSingleSource(int start) {
        // Initialize with infinitely max keyValue, eg. {MAX_VALUE, MAX_VALUE, MAX_VALUE...}
        for (int i = 0; i < graph.getV(); i++) {
            mstset[i] = false;
            keyValue[i] = Integer.MAX_VALUE;
        }

        // Starting vertex have value 0 (because we are already here), now: {0, MAX_VALUE, MAX_VALUE, MAX_VALUE...}
        keyValue[start] = 0;
    }
}