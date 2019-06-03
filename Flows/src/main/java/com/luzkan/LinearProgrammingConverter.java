package com.luzkan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

class LinearProgrammingConverter {
        static void lpc(int k, ArrayList<ArrayList<Integer>> graph, String task) {
        File f = new File("matchmax.mod");
        try {
            FileWriter fileWriter = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fileWriter);
            pw.print("/* Nodes Number */\n" +
                    "param n, integer, >= 2;\n" +

                    "/* Nodes Sets */\n" +
                    "set V, default {1..n};\n" +

                    "/* Arc Sets */\n" +
                    "set E, within V cross V;\n" +

                    "/* Capacity of arc (i,j) */\n" +
                    "param a{(i,j) in E}, > 0;\n" +

                    "/* Source Node */\n" +
                    "param s, symbolic, in V, default 1;\n" +

                    "/* Sink Node */\n" +
                    "param t, symbolic, in V, != s, default n;\n" +

                    "/* Elementary flow through arc (i,j) (to be found) */\n" +
                    "var x{(i,j) in E}, >= 0, <= a[i,j];\n" +

                    "/* Total flow from Source to Sink */\n" +
                    "var flow, >= 0;\n" +

                    "/* Conservation constraint node[i] for node i */\n" +
                    "s.t. node{i in V}:\n" +

                    "/* Summary flow into node i through all ingoing arcs */\n" +
                    "sum{(j,i) in E} x[j,i] + (if i = s then flow)\n" +
                    "=\n" +
                    "sum{(i,j) in E} x[i,j] + (if i = t then flow);\n" +
                    "/* Summary flow from node i through all outgoing arcs */\n" +

                    "/* Objective is to maximize the total flow */\n" +
                    "maximize obj: flow;\n" +
                    "solve;\n" +
                    "printf{1..56} \"=\"; printf \"\\n\";\n" +
                    "printf \"Maximum flow from node %s to node %s is %g\\n\\n\", s, t, flow;\n" +
                    "\n" +
                    "data;\n\n");
                pw.println();
            if(task.equals("biGraph")){
                pw.printf("param n := %d;\n", k+3);
                pw.println();
                pw.println("param : E : a :=");
                for (int j = 0; j < graph.size(); j++) {
                    for (Integer i : graph.get(j)) {
                        pw.printf("  %d %d %d\n", j + 2, i + 2, 1);
                    }
                }
                for (int j = 0; j < graph.size(); j++) {
                    pw.printf("  %d %d %d\n", 1, j + 2, 1);
                    pw.printf("  %d %d %d\n", j + 2, 1027, 1);
                }
            }else if(task.equals("HyperCube")){
                pw.printf("param n := %d;\n", ((1 << k)));
                pw.println();
                pw.println("param : E : a :=");
                for (int i = 0; i < ((1 << k) - 1); i++) {
                    for (int j = 0; j < k; j++) {
                        if (graph.get(i).get(j) != 0) {
                            int s = i + ((1 << j)) + 1;
                            pw.printf("  %d %d %d\n", i + 1, s, graph.get(i).get(j));
                        }
                    }
                }
            }
            pw.println(";\n");
            pw.print("end;");
            pw.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
