import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        int w = 2;
        int h = 3;

        System.out.println(solution(w, h));
    }

    public static long solution(int w, int h) {
        int gcd = BigInteger.valueOf(w).gcd(BigInteger.valueOf(h)).intValue();
        return ((long) w * (long) h) - ((((long) w / gcd) + ((long) h / gcd) - 1) * gcd);
    }
}
