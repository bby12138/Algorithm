package section1;

public class Ex1114 {
  public static int lg(int N) {
    long num = 1;
    int res = 0;
    while (num <= N){
      num *= 2;
      res++;
    }
    return res - 1;
  }

  public static void main(String[] args) {
    System.out.println(Integer.MAX_VALUE);
    System.out.println(lg(Integer.MAX_VALUE));
  }
}
