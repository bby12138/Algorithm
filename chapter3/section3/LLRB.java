package chapter3.section3;

public class LLRB<Key extends Comparable<Key>, Value> {
  private static final boolean RED = true;
  private static final boolean BLACK = false;

  private class Node {
    Key key;
    Value val;
    Node left, right;
    int N;
    boolean color;

    Node(Key key, Value val, int N, boolean color) {
      this.key = key;
      this.val = val;
      this.N = N;
      this.color = color;
    }
  }

  private Node root;

  private boolean isRed(Node x) {
    if (x == null) return false;
    return x.color == RED;
  }

  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    x.N = h.N;
    h.N = 1 + size(h.left) + size(h.right);
    return x;
  }

  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    x.N = h.N;
    h.N = 1 + size(h.left) + size(h.right);
    return x;
  }

  private void flipColors(Node h) {
    h.color = !h.color;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
  }

  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) return 0;
    return x.N;
  }

  public void put(Key key, Value val) {
    root = put(root, key, val);
    root.color = BLACK;
  }

  private Node put(Node h, Key key, Value val) {
    if (h == null) return new Node(key, val, 1, RED);
    int cmp = key.compareTo(h.key);
    if (cmp < 0) h.left = put(h.left, key, val);
    else if (cmp > 0) h.right = put(h.right, key, val);
    else h.val = val;

    if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right)) flipColors(h);

    h.N = 1 + size(h.left) + size(h.right);
    return h;
  }

  public Value get(Key key) {
    return get(root, key);
  }

  private Value get(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) return get(x.left, key);
    else if (cmp > 0) return get(x.right, key);
    else return x.val;
  }

  public Key min() {
    if (root == null) return null;
    return min(root).key;
  }

  private Node min(Node x) {
    if (x.left == null) return x;
    return min(x.left);
  }

  public Key floor(Key key) {
    Node x = floor(root, key);
    if (x == null) return null;
    return x.key;
  }

  private Node floor(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp == 0) return x;
    else if (cmp < 0) return floor(x.left, key);
    Node t = floor(x.right, key);
    if (t != null) return t;
    else return x;
  }

  public Key max() {
    if (root == null) return null;
    return max(root).key;
  }

  private Node max(Node x) {
    if (x.right == null) return x;
    return max(x.right);
  }

  public Key ceiling(Key key) {
    Node x = ceiling(root, key);
    if (x == null) return null;
    return x.key;
  }

  private Node ceiling(Node x, Key key) {
    if (x == null) return null;
    int cmp = key.compareTo(x.key);
    if (cmp == 0) return x;
    if (cmp > 0) return ceiling(x.right, key);
    Node t = ceiling(x.left, key);
    if (t != null) return t;
    else return x;
  }


  public Key select(int k) {
    if (k < 0 || k >= size(root))
      throw new IllegalArgumentException("Invalid rank: " + k);
    return select(root, k).key;
  }

  private Node select(Node x, int k) {
    if (x == null) return null;
    int t = size(x.left);
    if (t > k) return select(x.left, k);
    else if (t < k) return select(x.right, k - t - 1);
    else return x;
  }

  public int rank(Key key) {
    return rank(key, root);
  }

  private int rank(Key key, Node x) {
    if (x == null) return 0;
    int cmp = key.compareTo(x.key);
    if (cmp < 0) return rank(key, x.left);
    else if (cmp > 0) return 1 + rank(key, x.right) + size(x.left);
    return size(x.left);
  }

  private Node moveRedLeft(Node h) {
    flipColors(h);
    if (isRed(h.right.left)) {
      h.right = rotateRight(h.right);
      h = rotateLeft(h);
      flipColors(h);
    }
    return h;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  private Node balance(Node h) {
    if (isRed(h.right)) h = rotateLeft(h);
    if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
    if (isRed(h.left) && isRed(h.right)) flipColors(h);

    h.N = 1 + size(h.left) + size(h.right);
    return h;
  }

  public void deleteMin() {
    if (isEmpty()) return;
    if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
    root = deleteMin(root);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node deleteMin(Node h) {
    if (h.left == null) return null;
    if (!isRed(h.left) && !isRed(h.left.left))
      h = moveRedLeft(h);
    h.left = deleteMin(h.left);
    return balance(h);
  }

  private Node moveRedRight(Node h) {
    flipColors(h);
    if (isRed(h.left.left)) {
      h = rotateRight(h);
      flipColors(h);
    }
    return h;
  }

  public void deleteMax() {
    if (isEmpty()) return;
    if (!isRed(root.left) && !isRed(root.right))
      root.color = RED;
    root = deleteMax(root);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node deleteMax(Node h) {
    if (isRed(h.left)) h = rotateRight(h);
    if (h.right == null) return null;
    if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
    h.right = deleteMax(h.right);
    return balance(h);
  }

  public void delete(Key key) {
    if (isEmpty() || get(key) == null)
      return;
    if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
    root = delete(root, key);
    if (!isEmpty()) root.color = BLACK;
  }

  private Node delete(Node h, Key key) {
    if (key.compareTo(h.key) < 0) {
      if (!isRed(h.left) && !isRed(h.left.left))
        h = moveRedLeft(h);
      h.left = delete(h.left, key);
    } else {
      if (isRed(h.left))
        h = rotateRight(h);
      if (key.compareTo(h.key) == 0 && (h.right == null))
        return null;
      if (!isRed(h.right) && !isRed(h.right.left))
        h = moveRedRight(h);
      if (key.compareTo(h.key) == 0) {
        h.val = get(h.right, min(h.right).key);
        h.key = min(h.right).key;
        h.right = deleteMin(h.right);
      } else h.right = delete(h.right, key);
    }
    return balance(h);
  }


  public static void main(String[] args) {
    LLRB<Integer, String> tree = new LLRB<>();

    // 1. 插入测试
    System.out.println("===== 插入测试 =====");
    int[] keys = {5, 3, 7, 2, 4, 6, 8, 1, 9};

    for (int key : keys) {
      tree.put(key, "Value-" + key);
    }

    System.out.println("Size: " + tree.size());
    System.out.println("Min: " + tree.min());
    System.out.println("Max: " + tree.max());

    // 2. 查找测试
    System.out.println("\n===== 查找测试 =====");
    System.out.println("get(5): " + tree.get(5));
    System.out.println("get(9): " + tree.get(9));
    System.out.println("get(10): " + tree.get(10));

    // 3. 有序操作测试
    System.out.println("\n===== 有序操作测试 =====");
    System.out.println("floor(5): " + tree.floor(5));
    System.out.println("floor(10): " + tree.floor(10));
    System.out.println("ceiling(5): " + tree.ceiling(5));
    System.out.println("ceiling(0): " + tree.ceiling(0));
    System.out.println("rank(5): " + tree.rank(5));
    System.out.println("select(4): " + tree.select(4));

    // 4. 删除最小值
    System.out.println("\n===== deleteMin 测试 =====");
    tree.deleteMin();

    System.out.println("Size: " + tree.size());
    System.out.println("Min: " + tree.min());

    // 5. 删除最大值
    System.out.println("\n===== deleteMax 测试 =====");
    tree.deleteMax();

    System.out.println("Size: " + tree.size());
    System.out.println("Max: " + tree.max());

    // 6. 删除指定键
    System.out.println("\n===== delete 测试 =====");
    tree.delete(5);
    tree.delete(3);
    tree.delete(7);

    System.out.println("Size: " + tree.size());
    System.out.println("get(5): " + tree.get(5));

    // 7. 检查剩余键的有序性
    System.out.println("\n===== 最终有序遍历 =====");
    for (int i = 0; i < tree.size(); i++) {
      System.out.print(tree.select(i) + " ");
    }
    System.out.println();

    // 8. 删除所有剩余节点
    System.out.println("\n===== 清空测试 =====");
    while (!tree.isEmpty()) {
      tree.deleteMin();
    }

    System.out.println("isEmpty: " + tree.isEmpty());
    System.out.println("Size: " + tree.size());
  }
}
