package section1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SequentialSearchST<Key, Value> implements Iterable<Key> {

  private class STIterator implements Iterator<Key> {
    private Node current;

    public STIterator() {
      current = sentinel.next;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Key next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Key currentKey = current.key;
      current = current.next;
      return currentKey;
    }
  }

  private class Node {
    Key key;
    Value val;
    Node next;

    public Node(Key key, Value val, Node next) {
      this.key = key;
      this.val = val;
      this.next = next;
    }
  }

  private Node sentinel;
  private int size;


  @Override
  public Iterator<Key> iterator() {
    return new STIterator();
  }

  public SequentialSearchST() {
    this.size = 0;
    this.sentinel = new Node(null, null, null);
  }

  public void put(Key key, Value val) {
    if (key == null)
      throw new IllegalArgumentException("Key cannot be null");
    if (val == null) {
      delete(key);
      return;
    }
    for (Node x = sentinel.next; x != null; x = x.next) {
      if (key.equals(x.key)) {
        x.val = val;
        return;
      }
    }
    sentinel.next = new Node(key, val, sentinel.next);
    size++;
  }

  public Value get(Key key) {
    if (key == null)
      throw new IllegalArgumentException("Key cannot be null");

    for (Node x = sentinel.next; x != null; x = x.next) {
      if (key.equals(x.key)) {
        return x.val;
      }
    }
    return null;
  }

  public void delete(Key key) {
    if (key == null)
      throw new IllegalArgumentException("Key cannot be null");

    for (Node x = sentinel; x.next != null; x = x.next) {
      if (key.equals(x.next.key)) {
        x.next = x.next.next;
        size--;
        return;
      }
    }
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size;
  }

  public Iterable<Key> keys() {
    return this;
  }
}
