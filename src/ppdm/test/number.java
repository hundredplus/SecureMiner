
package ppdm.test;

import java.math.BigInteger;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 18, 2008
 */
public class number {
    public static void main(String[] args){
        double g = 12.45664456;
        long l = (long) (g*1000);
        BigInteger k = BigInteger.valueOf(l);
        
        System.out.println(k.divide(new BigInteger("1000")));
    }
}
