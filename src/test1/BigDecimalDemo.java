package test1;

import java.math.BigDecimal;

public class BigDecimalDemo {

    private static final BigDecimal TEN_CENT = new BigDecimal("0.1");

    public static int getAmount(BigDecimal total) {
        int count = 0;
        for (BigDecimal per = TEN_CENT; total.compareTo(per) >= 0; per = per.add(TEN_CENT)) {
            total = total.subtract(per);
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        //若用double或者float代替BigDecimal，产生结果为count为3，还剩0.399999999
        //        System.out.println(getAmount(new BigDecimal("1.0")));

        double d1 = 1.235;
        double d2 = 1.0;
        System.out.println(PreciseCompute.add(d1, d2));
        System.out.println(PreciseCompute.divide(d1, d2));
    }
}
