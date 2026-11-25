package chapter4.section1;

import chapter3.section1.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SymbolGraph {
  private final ST<String, Integer> st;
  private final String[] keys;
  private final Graph G;

  public SymbolGraph(String stream, String sp) throws FileNotFoundException {
    st = new ST<String, Integer>();
    Scanner in = new Scanner(new File(stream));
    while (in.hasNextLine()) {
      String[] a = in.nextLine().split(sp);

      for (int i = 0; i < a.length; ++i) {
        if (!st.contains((a[i])))
          st.put(a[i], st.size());
      }
    }
    keys = new String[st.size()];

    for (String name : st.keys())
      keys[st.get(name)] = name;

    G = new Graph(st.size());
    in = new Scanner(new File(stream));
    while (in.hasNextLine()) {
      String[] a = in.nextLine().split(sp);
      int v = st.get(a[0]);
      for (int i = 1; i < a.length; ++i) {
        G.addEdge(v, st.get(a[i]));
      }
    }
  }

  public boolean contains(String s) {
    return st.contains(s);
  }

  public int index(String s) {
    return st.get(s);
  }

  public String name(int v) {
    return keys[v];
  }

  public Graph G() {
    return G;
  }

  public static void main(String[] args) throws FileNotFoundException {
    String filename = args[0];
    String delim = args[1];
    SymbolGraph sg = new SymbolGraph(filename, delim);

    Graph G = sg.G();

    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String source = in.nextLine().trim();
      if (source.isEmpty()) continue;
      if (!sg.contains(source)) {
        System.out.println(source + " not in database.");
        continue;
      }
      int s = sg.index(source);
      for (int w : G.adj(s)) {
        System.out.println("    " + sg.name(w));
      }
    }

  }
}