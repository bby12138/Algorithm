package chapter4;


import edu.princeton.cs.algs4.Stack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;


public class DepthFirstPaths {
  private final boolean[] marked;
  private int count;
  private final int[] edgeTo;
  private int s;

  public DepthFirstPaths(Graph G, int s) {
    marked = new boolean[G.V()];
    Arrays.fill(marked, false);
    edgeTo = new int[G.V()];
    this.s = s;
    count = 0;
    dfs(G, s);
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;
    count++;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
        dfs(G, w);
      }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public boolean marked(int w) {
    return marked[w];
  }

  public int count() {
    return count;
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Stack<Integer> path = new Stack<>();
    for (int x = v; x != s; x = edgeTo[x])
      path.push(x);
    path.push(s);
    return path;
  }

  public static void main(String[] args) throws IOException {
    Path path = Path.of(args[0]);
    int s = Integer.parseInt(args[1]);
    try (Scanner in = new Scanner(path)) {
      Graph G = new Graph(in);
      DepthFirstPaths search = new DepthFirstPaths(G, s);
      for (int v = 0; v < G.V(); ++v) {
        System.out.print(s + " to " + v + ": ");
        if (search.hasPathTo(v))
          for (int x : search.pathTo(v)) {
            if (x == s) System.out.print(x);
            else System.out.print("-" + x);
          }
        System.out.println();
      }
    }
  }

}
