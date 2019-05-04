package com.luzkan;

// https://www.geeksforgeeks.org/priority-queue-class-in-java-2/
// https://www.hackerearth.com/practice/notes/heaps-and-priority-queues/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Scanner;

public class PriorityQueue {

    // Array nodeList is a list of (Number, Priority)
    private ArrayList<Node> nodeList = new ArrayList<>();
    private int size = 0;

    // Reduce console noise (for other modes of the program)
    private boolean silent = true;

    // Hashmap to give each entered number it's priority (link)
    private HashMap<Integer, Integer> map = new HashMap<>();

    public ArrayList<Node> getQueue() {
        return nodeList;
    }

    public int getSize() {
        return size;
    }

    private int parent(int i) {
        // Insert a 0-bit at i's left
        return i >> 1;
    }

    private int left(int i) {
        // Insert a 0-bit at i's right
        return i << 1;
    }

    private int right(int i) {
        // Insert a 0-bit at i's right plus one
        return (i << 1) + 1;
    }

    // Naive approach would be O(nlogn) - insert and then sort. Instead we can heapify to make it O(logn)
    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        if (l < size && nodeList.get(l).getPriority() < nodeList.get(smallest).getPriority()) {
            smallest = l;
        }
        if (r < size && nodeList.get(r).getPriority() < nodeList.get(smallest).getPriority()) {
            smallest = r;
        }
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    void insert(int number, int priority) {
        map.put(number, size);
        Node n = new Node(number, priority);
        size++;
        nodeList.add(size - 1, n);
        decreaseNumber(size - 1, priority);
        if(!silent) System.out.println("[Insert] (" + number + ", " + priority + ")");
    }

    boolean isEmpty() {
        return size == 0;
    }

    void pop() {
        if (isEmpty()) {
            System.out.println("[Pop] List is empty");
            return;
        }
        Node max = nodeList.get(0);
        nodeList.set(0, nodeList.get(size - 1));
        nodeList.remove(size - 1);
        size--;
        heapify(0);
        if(!silent) System.out.println("[Pop] (" + max.getNumber() + ", " + max.getPriority() + ")");
        map.remove(max.getNumber());
    }

    Node top() {
        if (isEmpty()) {
            System.out.println("[Top] List is empty");
            return null;
        } else {
            if(!silent) System.out.println("[Top] (" + nodeList.get(0).getNumber() + ", " + nodeList.get(0).getPriority() + ")");
            return nodeList.get(0);
        }
    }

    private void print() {
        System.out.print("Num/Prior: \t{ ");
        for (Node n : nodeList) {
            System.out.print("(" + n.getNumber() + ", " + n.getPriority() + ") ");
        }
        System.out.print("}\nNum/Order: \t");
        System.out.println(map.toString());
    }

    private void swap(int i, int j) {
        map.replace(nodeList.get(i).getNumber(), j);
        map.replace(nodeList.get(j).getNumber(), i);
        Collections.swap(nodeList, i, j);
    }

    private void decreaseNumber(int i, int priority) {
        if (priority > nodeList.get(i).getPriority()) {
            return;
        }
        nodeList.get(i).setPriority(priority);
        while (i >= 0 && nodeList.get(parent(i)).getPriority() > nodeList.get(i).getPriority()) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    void priority(int number, int priority) {
        if (map.get(number) != null) {
            int index = map.get(number);
            decreaseNumber(index, priority);
        }
    }

    public boolean contains(int number) {
        return map.get(number) != null;
    }

    void runMode() {
        Scanner s = new Scanner(System.in);
        System.out.println("[Priority Queue]\nAvailable Commands: 'insert <x> <p>', 'empty', 'top', 'pop', 'priority <x> <p>', 'print'");
        System.out.print("Please input desired number of actions: ");
        int actionsLimit = s.nextInt();

        // Turn off silent mode to print Top / Pop
        silent = false;

        System.out.println("Scanning for actions:");
        for (int n = 0; n < actionsLimit; n++) {
            String str = s.next();
            switch (str) {
                case "insert": {
                    int x = s.nextInt();
                    int p = s.nextInt();
                    insert(x, p);
                    break;
                }
                case "empty": {
                    System.out.println(isEmpty());
                    break;
                }
                case "top": {
                    top();
                    break;
                }
                case "pop": {
                    pop();
                    break;
                }
                case "priority": {
                    int x = s.nextInt();
                    int p = s.nextInt();
                    priority(x, p);
                    break;
                }
                case "print": {
                    print();
                    break;
                }
                default: {
                    System.err.println("Couldn't find an option for this input.");
                    n--;
                    break;
                }
            }
        }
    }
}
