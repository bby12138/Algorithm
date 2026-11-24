package chapter4.section1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Scanner;

public class BreadthFirstPaths {
  private final boolean[] marked;
  private final int[] edgeTo;
  private final int s;

  public BreadthFirstPaths(Graph G, int s) {
    this.s = s;
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];
    bfs(G, s);
  }

  private void bfs(Graph G, int s) {
    Queue<Integer> queue = new ArrayDeque<>();
    marked[s] = true;
    queue.offer(s);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int w : G.adj(v))
        if (!marked[w]) {
          marked[w] = true;
          queue.offer(w);
          edgeTo[w] = v;
        }
    }
  }

  public boolean hasPathTo(int v) {
    return marked[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Deque<Integer> path = new ArrayDeque<>();
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
      BreadthFirstPaths search = new BreadthFirstPaths(G, s);
      for (int v = 0; v < G.V(); ++v) {
        System.out.print(s + " to " + v + ": ");
        if (search.hasPathTo(v))
          for (int w : search.pathTo(v)) {
            if (w == s) System.out.print(s);
            else System.out.print("-" + w);
          }
        System.out.println();
      }
    }
  }

}
