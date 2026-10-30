package section1;

public class BinarySearchST<Key extends Comparable<Key>, Value> {
  private Key[] keys;
  private Value[] vals;
  private int size;
  private static final int MIN_CAPACITY = 8;


  public BinarySearchST() {
    size = 0;
    keys = (Key[]) new Comparable[MIN_CAPACITY];
    vals = (Value[]) new Object[MIN_CAPACITY];
  }

  private void resize(int capacity) {
    Key[] newKeys = (Key[]) new Comparable[capacity];
    Value[] newVals = (Value[]) new Object[capacity];

    System.arraycopy(keys, 0, newKeys, 0, size);
    System.arraycopy(vals, 0, newVals, 0, size);

    keys = newKeys;
    vals = newVals;
  }

  public void put(Key key, Value val) {
    int i = rank(key);
    if (i < size && keys[i].compareTo(key) == 0) {
      vals[i] = val;
      return;
    }
    if (keys.length == size) {
      resize(keys.length * 2);
    }

    System.arraycopy(keys, i, keys, i + 1, size - i);
    System.arraycopy(vals, i, vals, i + 1, size - i);

    keys[i] = key;
    vals[i] = val;

    size++;
  }

  public Value get(Key key) {
    if (isEmpty()) {
      return null;
    }
    int i = rank(key);
    if (i < size && keys[i].compareTo(key) == 0) {
      return vals[i];
    } else {
      return null;
    }
  }

  public void delete(Key key) {
    int i = rank(key);
    if (i == size || key.compareTo(keys[i]) != 0) return;
    System.arraycopy(keys, i + 1, keys, i, size - i - 1);
    System.arraycopy(vals, i + 1, vals, i, size - i - 1);
    keys[size - 1] = null;
    vals[size - 1] = null;
    size--;
    if (keys.length > MIN_CAPACITY && size == keys.length / 4) {
      resize(Math.max(keys.length / 2, MIN_CAPACITY));
    }
  }

  public boolean contains(Key key) {
    if (isEmpty()) {
      return false;
    }
    int i = rank(key);
    return i < size && key.compareTo(keys[i]) == 0;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  int size() {
    return size;
  }

  public Key min() {
    if (isEmpty()) return null;
    return keys[0];
  }

  public Key max() {
    if (isEmpty()) return null;
    return keys[size - 1];
  }

  public Key floor(Key key) {
    int i = rank(key);
    if (i < size && key.compareTo(keys[i]) == 0) return keys[i];
    if (i == 0) {
      return null;
    }
    return keys[i - 1];
  }

  public Key ceiling(Key key) {
    int i = rank(key);

    if (i == size) {
      return null;
    }
    return keys[i];
  }

  public int rank(Key key) {
    return rank(key, 0, size - 1);
  }

  private int rank(Key key, int lo, int hi) {
    if (hi < lo) return lo;
    int mid = lo + (hi - lo) / 2;
    int cmp = key.compareTo(keys[mid]);
    if (cmp < 0) return rank(key, lo, mid - 1);
    else if (cmp > 0) return rank(key, mid + 1, hi);
    else return mid;
  }

  public Key select(int k) {
    if (k < 0 || k >= size )
      return null;
    return keys[k];
  }
}
