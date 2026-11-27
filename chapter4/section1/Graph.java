package chapter4.section1;

import chapter1.section3.Bag;

import java.util.Scanner;

public class Graph {
  private final int V;
  private int E;
  private Bag<Integer>[] adj;

  @SuppressWarnings("unchecked")
  public Graph(int V) {
    this.V = V;
    this.E = 0;
    adj = (Bag<Integer>[]) new Bag[V];
    for (int v = 0; v < V; ++v) {
      adj[v] = new Bag<>();
    }
  }

  public Graph(Scanner in) {
    this(in.nextInt());
    int E = in.nextInt();

    for (int i = 0; i < E; ++i) {
      int v = in.nextInt();
      int w = in.nextInt();
      addEdge(v, w);
    }
  }

  public int V() {
    return V;
  }

  public int E() {
    return E;
  }

  void addEdge(int v, int w) {
    adj[v].add(w);
    adj[w].add(v);
    E++;
  }

  public Iterable<Integer> adj(int v) {
    return adj[v];
  }

  public String toString() {
    StringBuilder s = new StringBuilder(V + " vertices " + E + " edges\n");
    for (int v = 0; v < V; ++v) {
      s.append(v).append(": ");
      for (int w : this.adj[v])
        s.append(w).append(" ");
      s.append("\n");
    }
    return s.toString();
  }

  public static void main(String[] args) {
    Scanner in  = new Scanner(System.in);
    Graph graph = new Graph(in);
    System.out.println(graph);
  }
}
