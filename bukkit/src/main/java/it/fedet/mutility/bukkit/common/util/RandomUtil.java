package it.fedet.mutility.common.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    private RandomUtil() {
    }

    public static int generateInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max + 1 - min) + min;
    }
}