package com.luzkan;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static com.luzkan.Util.search;

class TwoPartsGraph {

    ArrayList<ArrayList<Integer>> biGraph;

    TwoPartsGraph(int size, int neighbours) {
        // fxSize is [1*2] times [size]
        int fxSize = (1<<size);
        biGraph = new ArrayList<>(fxSize);

        for (int i = 0; i < (fxSize); i++) {
            biGraph.add(i, new ArrayList<Integer>(neighbours));

            for (int j = 0; j < neighbours; j++) {
                int n = ThreadLocalRandom.current().nextInt(fxSize);

                // Make Unique
                while (search(biGraph.get(i), n)){
                    n = ThreadLocalRandom.current().nextInt(fxSize);
                }

                biGraph.get(i).add(j, n);
            }
        }
        LinearProgrammingConverter.lpc(fxSize, biGraph, "biGraph");
    }
}
