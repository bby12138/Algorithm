package chapter4.section1;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class DegreesOfSeparation {
  public static void main(String[] args) throws FileNotFoundException {
    SymbolGraph sg = new SymbolGraph(args[0], args[1]);
    Graph G = sg.G();
    String source = args[2];

    if (!sg.contains(source)) {
      System.out.println(source + "not in database.");
      return;
    }

    int s = sg.index(source);
    BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String sink = in.nextLine().trim();
      if (sg.contains(sink)) {
        int t = sg.index(sink);
        if (bfs.hasPathTo(t))
          for (int w : bfs.pathTo(t))
            System.out.println("  " + sg.name(w));
        else
          System.out.println("Not connected");
      } else System.out.println("Not in database");

    }
  }
}
