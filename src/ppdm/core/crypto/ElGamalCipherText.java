
package ppdm.core.crypto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 23, 2009
 */
public class ElGamalCipherText implements Serializable {

    public BigInteger a;
    public BigInteger b;

    public ElGamalCipherText(BigInteger c1Val, BigInteger c2Val) {
        a = c1Val;
        b = c2Val;
    }

    public ElGamalCipherText(int c1Val, int c2Val) {
        a = new BigInteger("" + c1Val);
        b = new BigInteger("" + c2Val);
    }

    public ElGamalCipherText(String c1Val, String c2Val) {
        a = new BigInteger(c1Val);
        b = new BigInteger(c2Val);
    }

    public ElGamalCipherText multiply(ElGamalCipherText egct) {
        return new ElGamalCipherText(this.a.multiply(egct.a), this.b.multiply(egct.b));
    }

    public ElGamalCipherText multiply(ElGamalCipherText egct, BigInteger m) {
        return new ElGamalCipherText((this.a.multiply(egct.a)).mod(m), (this.b.multiply(egct.b)).mod(m));
    }

    public String toString() {
        return "(" + a.toString() + ", " + b.toString() + ")";
    }
}
