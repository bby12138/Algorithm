package chapter1.section3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<item> implements Iterable<item> {
  private final Node sentinel;
  private int size;

  private class Node {
    item val;
    Node next;

    Node(item val, Node next) {
      this.next = next;
      this.val = val;
    }
  }

  private class LisrIterator implements Iterator<item> {
    private Node p;

    public LisrIterator() {
      p = sentinel.next;
    }

    @Override
    public boolean hasNext() {
      return p != null;
    }

    @Override
    public item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      item val = p.val;
      p = p.next;
      return val;
    }
  }

  public Bag() {
    sentinel = new Node(null, null);
    size = 0;
  }

  public void add(item val) {
    sentinel.next = new Node(val, sentinel.next);
    size++;
  }

  public boolean isEmpty() {
    return sentinel.next == null;
  }

  public int size() {
    return size;
  }

  @Override
  public Iterator<item> iterator() {
    return new LisrIterator();
  }

  public static void main(String[] args) {
    Bag<Integer> bag = new Bag<>();
    for (int i = 0; i < 10; ++i) {
      bag.add(i);
    }

    for (int i : bag) {
      System.out.print(i);
      System.out.print(" ");
    }
    System.out.println();
  }
}
