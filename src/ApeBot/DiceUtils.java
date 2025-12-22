package ApeBot;

import java.util.*;

public class DiceUtils {
    // ordine crescente (31 ... 65, doppie 11..66, poi 21)
    private static final int[] ORDER = {
            31,32,41,42,43,51,52,53,54,61,62,63,64,65,
            11,22,33,44,55,66,
            21
    };

    private static final Map<Integer,Integer> rankMap = new HashMap<>();
    static {
        for (int i = 0; i < ORDER.length; ++i) rankMap.put(ORDER[i], i+1);
    }

    public static int packDice(int d1, int d2) {
        int a = Math.max(d1, d2);
        int b = Math.min(d1, d2);
        return a*10 + b;
    }

    public static int rankOf(int packed) {
        return rankMap.getOrDefault(packed, 0);
    }
    public static int valueOf(int rank) {
    	if(rank<0||rank>ORDER.length) {
    		return 0;
    	}
    	return ORDER[rank-1];
    }
    public static int[] allPossibleValues() {
        return Arrays.copyOf(ORDER, ORDER.length);
    }

    public static boolean declaredAllowed(int declaredValue, int previousDeclaredRank) {
        int r = rankOf(declaredValue);
        return r > previousDeclaredRank;
    }

    public static int roll(Random rnd) {
        int d1 = rnd.nextInt(6) + 1;
        int d2 = rnd.nextInt(6) + 1;
        return packDice(d1, d2);
    }
}
