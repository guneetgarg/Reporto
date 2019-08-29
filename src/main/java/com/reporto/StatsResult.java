package com.reporto;

public class StatsResult {
    public static int passed = 0;
    public static int failed = 0;
    public static int skipped = 0;
    public static int ignored = 0;
    public static int retried = 0;
    public static int total = 0;

    public static int getTotal() {
        return passed + failed + skipped;
    }
}
