import java.util.Random;


public class rng {
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    public static void main(String[] args) {
        int v1 = -469180308;
        int v2 = -815028669;
        long seed = guessSeed(v1, v2);

        Random rand = new Random(seed);
        System.out.println(rand.nextInt());
        System.out.println(rand.nextInt());
    }
    
    private static long guessSeed(int v1, int v2) {
        System.out.printf("v1 = 0x%08x, v2 = 0x%08x\n", v1, v2);
        return guessSeed(maskToLong(v1), maskToLong(v2));
    }

    private static long guessSeed(long v1, long v2) {
        for (int i = 0; i < 65536; i++) {
            long guess = v1 * 65536 + i;
            if ((((guess * multiplier + addend) & mask) >>> 16) == v2) {
                return (guess ^ multiplier) & mask;
            }
        }
        return -1;
    }

    /**
     * Convert an int to a long in a way that preserves all the bits (negative numbers/twos complement issues).
     */
    private static long maskToLong(int n) {
        return n & 0x00000000ffffffffL;
    }
}
