package com.luzkan;

class Edge {

    private int u;
    private int v;
    private int weight;

    Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }

    int getU() {
        return u;
    }

    int getV() {
        return v;
    }

    int getWeight() {
        return weight;
    }
}
