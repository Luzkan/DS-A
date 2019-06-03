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


param n := 8;

param : E : a :=
  1 3 7
  1 5 3
  2 6 4
  3 4 3
  3 7 4
  4 8 2
  5 6 4
  5 7 4
;

end;