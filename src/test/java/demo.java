import com.reporto.StatsResult;

public class demo {
    public static void main(String[] args) {
        StatsResult.failed = 5;
        StatsResult.skipped = 4;
        StatsResult.passed = 6;


        float percentage = (float) (StatsResult.failed  / StatsResult.getTotal());

        String str = String.format("%2.02f", percentage);
        System.out.println(str);

      /*  System.out.println(StatsResult.getTotal());


        System.out.println(StatsResult.failed);

        int ss = StatsResult.failed;
        int s = StatsResult.getTotal();
        System.out.println((float)ss / s);*/
    }
}
