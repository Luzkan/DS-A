/* Nodes Number */
param n, integer, >= 2;
/* Nodes Sets */
set V, default {1..n};
/* Arc Sets */
set E, within V cross V;
/* Capacity of arc (i,j) */
param a{(i,j) in E}, > 0;
/* Source Node */
param s, symbolic, in V, default 1;
/* Sink Node */
param t, symbolic, in V, != s, default n;
/* Elementary flow through arc (i,j) (to be found) */
var x{(i,j) in E}, >= 0, <= a[i,j];
/* Total flow from Source to Sink */
var flow, >= 0;
/* Conservation constraint node[i] for node i */
s.t. node{i in V}:
/* Summary flow into node i through all ingoing arcs */
sum{(j,i) in E} x[j,i] + (if i = s then flow)
=
sum{(i,j) in E} x[i,j] + (if i = t then flow);
/* Summary flow from node i through all outgoing arcs */
/* Objective is to maximize the total flow */
maximize obj: flow;
solve;
printf{1..56} "="; printf "\n";
printf "Maximum flow from node %s to node %s is %g\n\n", s, t, flow;

data;


param n := 18;

param : E : a :=
  2 16 1
  16 18 1
  2 5 1
  5 18 1
  2 10 1
  10 18 1
  2 7 1
  7 18 1
  3 14 1
  14 18 1
  3 11 1
  11 18 1
  3 9 1
  9 18 1
  3 17 1
  17 18 1
  4 4 1
  4 18 1
  4 15 1
  15 18 1
  4 11 1
  11 18 1
  4 2 1
  2 18 1
  5 4 1
  4 18 1
  5 7 1
  7 18 1
  5 11 1
  11 18 1
  5 3 1
  3 18 1
  6 13 1
  13 18 1
  6 14 1
  14 18 1
  6 6 1
  6 18 1
  6 2 1
  2 18 1
  7 14 1
  14 18 1
  7 2 1
  2 18 1
  7 15 1
  15 18 1
  7 17 1
  17 18 1
  8 15 1
  15 18 1
  8 13 1
  13 18 1
  8 5 1
  5 18 1
  8 17 1
  17 18 1
  9 3 1
  3 18 1
  9 15 1
  15 18 1
  9 9 1
  9 18 1
  9 12 1
  12 18 1
  10 3 1
  3 18 1
  10 8 1
  8 18 1
  10 12 1
  12 18 1
  10 7 1
  7 18 1
  11 15 1
  15 18 1
  11 17 1
  17 18 1
  11 13 1
  13 18 1
  11 6 1
  6 18 1
  12 3 1
  3 18 1
  12 16 1
  16 18 1
  12 5 1
  5 18 1
  12 17 1
  17 18 1
  13 9 1
  9 18 1
  13 17 1
  17 18 1
  13 4 1
  4 18 1
  13 3 1
  3 18 1
  14 9 1
  9 18 1
  14 7 1
  7 18 1
  14 5 1
  5 18 1
  14 13 1
  13 18 1
  15 15 1
  15 18 1
  15 16 1
  16 18 1
  15 5 1
  5 18 1
  15 14 1
  14 18 1
  16 3 1
  3 18 1
  16 16 1
  16 18 1
  16 4 1
  4 18 1
  16 11 1
  11 18 1
  17 6 1
  6 18 1
  17 5 1
  5 18 1
  17 16 1
  16 18 1
  17 3 1
  3 18 1
  1 2 1
  1 3 1
  1 4 1
  1 5 1
  1 6 1
  1 7 1
  1 8 1
  1 9 1
  1 10 1
  1 11 1
  1 12 1
  1 13 1
  1 14 1
  1 15 1
  1 16 1
  1 17 1
;

end;