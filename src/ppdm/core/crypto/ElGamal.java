
package ppdm.core.crypto;

import java.math.*;
import java.security.SecureRandom;
import java.util.*;
import ppdm.core.Config;
import ppdm.core.math.Presentation;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 16, 2009
 */
public class ElGamal {

    /**
     *
     */
    public BigInteger p, g, y, x;
    
    /**
     *
     */
    public ElGamal(int bitLengthVal) {
        Random rnd = new SecureRandom();
        p = BigInteger.probablePrime(bitLengthVal, rnd);
        x = (new BigInteger(bitLengthVal, rnd)).mod(p);
        g = new BigInteger("2");
        y = g.modPow(x, p);
    }

    /**
     *
     */
    public ElGamal() {
        this(512);
    }

    /**
     * use in the other parties to encrypt only
     */
    public ElGamal(BigInteger p, BigInteger g, BigInteger y){
        this.g = g;
        this.p  = p;
        this.y = y;
    }
    
    /**
     */
    public ElGamalCipherText encrypt(BigInteger m) {
        //k must be RELATIVELY PRIME to (p-1)
        BigInteger k;
        do
            k = (new BigInteger(p.bitLength(), new SecureRandom())).mod(p);
        while ((k.gcd(p.subtract(BigInteger.ONE))).compareTo(BigInteger.ONE) != 0 );

        return new ElGamalCipherText(g.modPow(k, p), (m.multiply(y.modPow(k, p))).mod(p));
    }

    /**
     * 
     * @param m
     * @return
     */
    public ElGamalCipherText[] encrypt(BigInteger[] m){
        ElGamalCipherText[] res = new ElGamalCipherText[m.length];
        for (int i=0; i<m.length; i++){
            res[i] = encrypt(m[i]);
        }
        return res;
    }


    public ArrayList<ElGamalCipherText> encrypt(ArrayList<BigInteger> values) {
        ArrayList<ElGamalCipherText> ret = new ArrayList<ElGamalCipherText>();
        for (int i = 0; i < values.size(); i++) {
            ret.add(encrypt(values.get(i)));
        }
        return ret;
    }

    public ArrayList<ElGamalCipherText[]> encryptData(ArrayList<Double[]> input){
        ArrayList<ElGamalCipherText[]>    data = new ArrayList<ElGamalCipherText[]>();
        for (int i=0; i<input.size(); i++){
            ElGamalCipherText[] tmp = encrypt(Presentation.double2BigInteger(
                    input.get(i), Config.DEFAULT_NUM_DIGIT));
            data.add(tmp);
        }
        return data;
    }

    /**
     * @param 
     * @return 
     */
    public BigInteger decrypt(ElGamalCipherText egct) {
        return (egct.a.modInverse(p)).modPow(x, p).multiply(egct.b).mod(p);
    }

    public BigInteger[] decrypt(ElGamalCipherText[] egct) {
        BigInteger[] ret = new BigInteger[egct.length];
        for (int i=0; i< egct.length; i++){
            ret[i] = decrypt(egct[i]);
        }
        return ret;
    }

    public BigInteger[] decrypt(ArrayList<ElGamalCipherText> egct){
        ElGamalCipherText[] ect = new ElGamalCipherText[egct.size()];
        for (int i=0; i< egct.size(); i++){
            ect[i] = egct.get(i);
        }
        return decrypt(ect);
    }

    public ArrayList<Double[]> decryptData(ArrayList<ElGamalCipherText[]> input){
        ArrayList<Double[]>    data = new ArrayList<Double[]>();
        for (int i=0; i<input.size(); i++){
            BigInteger[] tmp = decrypt(input.get(i));
            Double[]       tmp1 = new Double[tmp.length];
            for (int j=0; j<tmp.length; j++){
                tmp1[j]=(tmp[j].doubleValue()/Math.pow(10, Config.DEFAULT_NUM_DIGIT));
            }
            data.add(tmp1);
        }
        return data;
    }

    public static void main(String args[]) {
        ElGamal eg = new ElGamal();
        ElGamalCipherText elct1 = eg.encrypt(new BigInteger("23"));
        ElGamalCipherText elct2 = eg.encrypt(new BigInteger("100"));
        System.out.println(eg.p);
        System.out.println(elct1);
        System.out.println(elct2);

        System.out.println(eg.decrypt(elct1));
        System.out.println(eg.decrypt(elct2));

        ElGamalCipherText elct3 = elct1.multiply(elct2, eg.p);

        System.out.println(eg.decrypt(elct3));
    }

    public BigInteger getG() {
        return g;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    
}