package chapter3.section1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
  private final TreeMap<Key, Value> st = new TreeMap<>();

  // 基本操作
  public void put(Key key, Value val) {
    requireNonNull(key, "put");
    if (val == null) {
      st.remove(key);
    } else {
      st.put(key, val);
    }
  }

  public Value get(Key key) {
    requireNonNull(key, "get");
    return st.get(key);
  }

  public void delete(Key key) {
    requireNonNull(key, "delete");
    st.remove(key);
  }

  public void remove(Key key) {
    delete(key);
  }

  public boolean contains(Key key) {
    requireNonNull(key, "contains");
    return st.containsKey(key);
  }

  public int size() {
    return st.size();
  }

  public boolean isEmpty() {
    return st.isEmpty();
  }

  // 有序操作
  public Key min() {
    if (isEmpty()) throw new NoSuchElementException("min() with empty symbol table");
    return st.firstKey();
  }

  public Key max() {
    if (isEmpty()) throw new NoSuchElementException("min() with empty symbol table");
    return st.lastKey();
  }

  public Key floor(Key key) {
    requireNonNull(key, "floor");
    Key k = st.floorKey(key);
    if (k == null) throw new NoSuchElementException("floor() argument too small");
    return k;
  }

  public Key ceiling(Key key) {
    requireNonNull(key, "ceiling");
    Key k = st.ceilingKey(key);
    if (k == null) throw new NoSuchElementException("ceiling() argument too large");
    return k;
  }

  // 小于 key 的键数量，范围 [0, size]
  // 注意：headMap(key).size() 是 O(n)，TreeMap 不维护子树大小
  public int rank(Key key) {
    requireNonNull(key, "rank");
    return st.headMap(key).size();
  }

  // 第 k 小（0-based）
  // 注意：O(n)，需要遍历 keySet
  public Key select(int k) {
    if (k < 0 || k >= size())
      throw new IllegalArgumentException("select() index out of range: " + k);
    int i = 0;
    for (Key key : st.keySet()) {
      if (i++ == k) return key;
    }
    throw new IllegalStateException("unreachable");
  }

  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("deleteMin() with empty symbol table");
    st.pollFirstEntry();
  }

  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("deleteMax() with empty symbol table");
    st.pollLastEntry();
  }

  // ---------- 范围与遍历 ----------

  public Iterable<Key> keys() {
    return st.keySet();          // 升序视图，惰性，推荐
  }

  public Iterable<Key> keys(Key lo, Key hi) {
    requireNonNull(lo, "keys(lo, hi)");
    requireNonNull(hi, "keys(lo, hi)");
    if (lo.compareTo(hi) > 0) return java.util.Collections.emptyList();
    return st.subMap(lo, true, hi, true).keySet();
  }

  public int size(Key lo, Key hi) {
    requireNonNull(lo, "size(lo, hi)");
    requireNonNull(hi, "size(lo, hi)");
    if (lo.compareTo(hi) > 0) return 0;
    return st.subMap(lo, true, hi, true).size();
  }

  @Override
  public Iterator<Key> iterator() {
    return st.keySet().iterator();
  }

  private static void requireNonNull(Object key, String method) {
    if (key == null)
      throw new IllegalArgumentException("calls " + method + "() with null key");
  }
}
