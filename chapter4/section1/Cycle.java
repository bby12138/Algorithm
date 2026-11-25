package chapter4.section1;

import java.util.Arrays;

public class Cycle {
  private final boolean[] marked;
  private boolean hasCycle;

  public Cycle(Graph G) {
    hasCycle = false;
    marked = new boolean[G.V()];
    Arrays.fill(marked, false);
    for (int s = 0; s < G.V(); ++s) {
      if (!marked[s])
        dfs(G, s, s);
    }
  }

  private void dfs(Graph G, int v, int u) {
    marked[v] = true;
    for (int w : G.adj(v))
      if (!marked[w]) {
        dfs(G, w, v);
      } else if (w != u) {
        hasCycle = true;
      }
  }

  public boolean isHasCycle() {
    return hasCycle;
  }

}
