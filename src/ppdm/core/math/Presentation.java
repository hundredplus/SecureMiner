
package ppdm.core.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 13, 2008
 */
public class Presentation {

    public static BigDecimal round(double db, int no) {
        return ((new BigDecimal(db)).setScale(no, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * It will multiply the double with a value first to make it <long>
     * @param list
     * @return
     */
    public static BigInteger[] double2BigInteger(Double[] list, Integer pow) {
        BigInteger[] ret = new BigInteger[list.length];
        Double exp = Math.pow(10, pow);
        for (int i = 0; i < list.length; i++) {
            long lg = (long)(list[i] * exp);
            ret[i]=(BigInteger.valueOf(lg));
        }
        return ret;
    }
}
