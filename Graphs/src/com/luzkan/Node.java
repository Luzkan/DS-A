package com.luzkan;

class Node {

    private int number;
    private int priority;

    Node(int number, int priority) {
        this.number = number;
        this.priority = priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    int getPriority() {
        return priority;
    }

    int getNumber() {
        return number;
    }
}
