package ppdm.core.crypto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import ppdm.core.Config;
import ppdm.core.math.Presentation;

/**
 *
 * @author ductran
 * 
 * @date Aug 28, 2008
 * 
 * Idea of program  based on http://en.wikipedia.org/wiki/Paillier_cryptosystem
 */
public class Paillier {
    //512-bit key. Next version, it should be generated once called.
    public BigInteger p;
    public BigInteger q;
    public BigInteger lambda;
    public BigInteger mu;
    public BigInteger n;
    public BigInteger g;

    /**
     * 
     * @param bits
     * @param certainty
     */
    public Paillier(int bits, int certainty) {
        BigInteger pLess1;
        BigInteger qLess1;
        //random generate two  prime number with </code>bits/2<code> bits
        this.p = new BigInteger(bits / 2, certainty, new Random());
        this.q = new BigInteger(bits / 2, certainty, new Random());

        pLess1 = p.subtract(BigInteger.ONE);
        qLess1 = q.subtract(BigInteger.ONE);
        this.n = p.multiply(q);
        this.lambda = (pLess1.multiply(qLess1)).divide(pLess1.gcd(qLess1));

        this.g = new BigInteger(Integer.toString(new Random().nextInt(Integer.MAX_VALUE)));
        this.mu = (LFunction(g.modPow(lambda, n.pow(2)))).modInverse(n);
    }

    public Paillier() {
        this(512, 64);
    }

    public Paillier(int bits) {
        this(bits, 64);
    }
    //use in the other party to encrypt only
    public Paillier(BigInteger n, BigInteger g) {
        this.n = n;
        this.g = g;
    }

    public Paillier(BigInteger n, BigInteger g, BigInteger lambda, 
            BigInteger mu, BigInteger p, BigInteger q) {
        this.n = n;
        this.g = g;
        this.lambda = lambda;
        this.mu = mu;
        this.p = p;
        this.q = q;
    }

    /**
     * 
     * @param values
     * @param n
     * @param g
     * @return
     */
    public ArrayList<BigInteger> encrypt(ArrayList<BigInteger> values, BigInteger n, BigInteger g) {
        ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
        for (int i = 0; i < values.size(); i++) {
            ret.add(encrypt(values.get(i), n, g));
        }
        return ret;
    }

    public ArrayList<BigInteger> encrypt(ArrayList<BigInteger> values) {
        return encrypt(values, n, g);
    }
    
    public ArrayList<BigInteger[]> encryptData(ArrayList<Double[]> input, BigInteger n, BigInteger g){
        ArrayList<BigInteger[]>    data = new ArrayList<BigInteger[]>();
        for (int i=0; i<input.size(); i++){
            BigInteger[] tmp = encrypt(Presentation.double2BigInteger(
                    input.get(i), Config.DEFAULT_NUM_DIGIT), n , g);
            data.add(tmp);
        }
        return data;
    }
    
    public ArrayList<BigInteger[]> encryptData(ArrayList<Double[]> input) {
        return encryptData(input, n, g);
    }

    public BigInteger encrypt(BigInteger m, BigInteger n, BigInteger g) {
        if (false){
            Random rand = new Random(30385);
            int lineNO = rand.nextInt(1000);
            BigInteger encryptedVal = null;
            lineNO = rand.nextInt(998) + 1;
            if (m.compareTo(BigInteger.ZERO) == 0) {
                try {
                    encryptedVal = new BigInteger(readLine("..\\zero.txt", lineNO));
                } catch (Exception ex) {
                    Logger.getLogger(Paillier.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (m.compareTo(BigInteger.ONE) == 1){
                try {
                    encryptedVal = new BigInteger(readLine("..\\one.txt", lineNO));
                } catch (Exception ex) {
                    Logger.getLogger(Paillier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return encryptedVal;
        }else{
            Random rand = new Random();
            BigInteger r = new BigInteger(Integer.toString(rand.nextInt(Integer.MAX_VALUE)));
            BigInteger tmp1 = g.modPow(m, n.pow(2));
            BigInteger tmp2 = r.modPow(n, n.pow(2));
            BigInteger tmp3 = (tmp1.multiply(tmp2)).mod(n.pow(2));
            return tmp3;
        }
    }
    
    
    public Double encrypt(Double m, BigInteger n, BigInteger g) {
        Random rand = new Random();
        Double n1 = n.doubleValue();
        Double g1 = g.doubleValue();
        BigInteger r = new BigInteger(Integer.toString(rand.nextInt(Integer.MAX_VALUE)));
        Double r1 = r.doubleValue();
        Double n2 = Math.pow(n1, 2);
        System.out.println(n1);
        System.out.println(r1);
        
        //g.modPow(m, n.pow(2));
        Double tmp1 = Math.pow(g1, m);        
        tmp1 = tmp1 % n2;
        System.out.println(tmp1);
        //r.modPow(n, n.pow(2));
        Double tmp2 = Math.pow(r1,n1)%n2;
//        tmp2 = tmp2 % n2;        
        System.out.println(tmp2);
        Double tmp3 = tmp1 * tmp2; 
        System.out.println(tmp3);
        tmp3 = tmp3 % n2;
        System.out.println(tmp3);
        return tmp3;
    }

    public Double encrypt(Double m) {
        return this.encrypt(m, n, g);
    }
    
    public BigInteger encrypt(BigInteger m) {
        return encrypt(m, n, g);
    }

    public BigInteger[] encrypt(BigInteger[] vec, BigInteger n, BigInteger g) {
        if (vec == null) {
            return null;
        }
        BigInteger[] tmp = new BigInteger[vec.length];
        for (int i = 0; i < vec.length; i++) {
            tmp[i] = encrypt(vec[i], n, g);
        }
        return tmp;
    }

    public BigInteger[] encrypt(BigInteger[] vec) {
        return encrypt(vec, n, g);
    }

    public BigInteger decrypt(BigInteger c) {
        return (LFunction(c.modPow(lambda, n.pow(2))).multiply(mu)).mod(n);
    }
    
    public BigInteger[] decrypt(BigInteger[] vec) {
         BigInteger[] tmp = new BigInteger[vec.length];
         for (int i=0; i<vec.length; i++){
             tmp[i] = decrypt(vec[i]);
         }
         return tmp;
    }
    
    public ArrayList<BigInteger> decrypt(ArrayList<BigInteger> values) {
        ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
        for (int i = 0; i < values.size(); i++) {
            ret.add(decrypt(values.get(i)));
        }
        return ret;
    }
    
    
    public ArrayList<Double[]> decryptData(ArrayList<BigInteger[]> input){
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

    private BigInteger LFunction(BigInteger u) {
        return u.subtract(BigInteger.ONE).divide(n);
    }

    public static void main(String[] args) {

        /* This part is to generate the pre-encrypted vectors

        FileWriter fWriter = null;
        FileWriter fWriter1 = null;
        FileWriter fWriter2 = null;
        Paillier pl = new Paillier();
        try {
            fWriter = new FileWriter("..\\pail.txt", false);
            fWriter.write(pl.n + "\n");
            fWriter.write(pl.g + "\n");
            fWriter.write(pl.lambda + "\n");
            fWriter.write(pl.mu + "\n");
            fWriter.write(pl.p + "\n");
            fWriter.write(pl.q + "\n");
            fWriter.flush();

            fWriter1 = new FileWriter("..\\zero.txt", false);
            fWriter2 = new FileWriter("..\\one.txt", false);
            for (int i = 0; i < 1000; i++) {
                BigInteger zero = pl.encrypt(BigInteger.ZERO);
                BigInteger one = pl.encrypt(BigInteger.ONE);
                fWriter1.write(zero + "\n");
                fWriter2.write(one + "\n");
                fWriter1.flush();
                fWriter2.flush();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Paillier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fWriter.close();
                fWriter1.close();
                fWriter2.close();
            } catch (IOException ex) {
                Logger.getLogger(Paillier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */

        Paillier pl = new Paillier(1024);
        ArrayList<BigInteger> ar = new ArrayList<BigInteger>();
        BigInteger[] arr = new BigInteger[1000000];
        int[] test = new int[1000000];
        Random rnd = new Random();
        System.out.println("Start...");
        for (int i=0; i<100000; i++){
            if (i%1000 == 0) System.out.println(i);
            BigInteger n1 = pl.encrypt(new BigInteger(128,rnd));
            
            ar.add(n1);
            

//        BigInteger n2 = pl.encrypt(new BigInteger("12"));
        
//        System.out.println(n1);
//        System.out.println(n2);

//        BigInteger n3 = n1.multiply(n2);
//        System.out.println(pl.decrypt(n3));

//        BigInteger n4 = n1.pow(12);
//        System.out.println(pl.decrypt(n4));
        }
        System.out.println("End");
    }

    public BigInteger getG() {
        return g;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public BigInteger getLambda() {
        return lambda;
    }

    public void setLambda(BigInteger lambda) {
        this.lambda = lambda;
    }

    public BigInteger getMu() {
        return mu;
    }

    public void setMu(BigInteger mu) {
        this.mu = mu;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public String readLine(String filename, int lineNo) throws Exception{
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(filename));
        String line = null;
        for (int i=0; i<lineNo; i++){
            line = br.readLine();
        }
        br.close();
        return line;
    }
        
}
