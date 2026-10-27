package section1;

public class Ex1111 {
  public static void printArray(boolean[][] a) {
    System.out.print("  ");
    for (int i = 0; i < a[0].length; ++i)
      System.out.print(i + " ");
    System.out.println();
    for (int i = 0; i < a.length; ++i) {
      System.out.print(i + " ");
      for (int j = 0; j < a[i].length; ++j) {
        if (a[i][j])
          System.out.print("* ");
        else System.out.print("  ");
      }
      System.out.println();
    }

  }

  public static void main(String[] args) {
    boolean[][] a = {
        {true, false, true},
        {false, true, false},
        {true, true, false}
    };
    printArray(a);
  }
}
