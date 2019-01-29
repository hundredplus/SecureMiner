
package ppdm.block;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import ppdm.core.crypto.Paillier;
import ppdm.test.AutoGenerateData;

/**
 *
 * @author ductran
 *
 * @date Aug 29, 2008
 */
public class ScalarProduct {
    
    public ArrayList<BigInteger> computeSSP(Paillier pail, ArrayList<BigInteger> myPrivateData, ArrayList<BigInteger> receivedData, boolean lastParty) {
        BigInteger[] privateData = new BigInteger[myPrivateData.size()];
        for (int i=0; i<privateData.length;i++){
            privateData[i] = myPrivateData.get(i);
        }
        BigInteger[] data;
        if (receivedData == null || receivedData.isEmpty())
            data = null;
        else{
            data = new BigInteger[receivedData.size()];
            for (int i = 0; i < data.length; i++) {
                data[i] = receivedData.get(i);
            }
        }                
        BigInteger[] ret         = computeSSP(pail, privateData, data, lastParty);
        ArrayList<BigInteger> res = new ArrayList<BigInteger>();
        for (int i=0; i<ret.length;i++){
            res.add(ret[i]);
        }
        return res;
    }
    
    /**
     * 
     * @param pail
     * @param myPrivateData
     * @param receivedData
     * @param lastParty
     * @return the encrypted value(s)
     */
    public BigInteger[] computeSSP(Paillier pail, BigInteger[] myPrivateData, BigInteger[] receivedData, boolean lastParty){
        BigInteger[]    res;
        if (lastParty){
            res = new BigInteger[1];            
            if (receivedData == null) {
                res[0] = new BigInteger("0");
                for (int i = 0; i < myPrivateData.length; i++) {
                    res[0] = res[0].add(myPrivateData[i]);
                }
//                System.out.println("RES: " + res[0]);
//                System.out.println("Pail_n: " + pail.n);
//                System.out.println("Pail_g: " + pail.g);
                res[0] = pail.encrypt(res[0]);
            } else {
                res[0] = new BigInteger("1");
                for (int i = 0; i < receivedData.length; i++){
                    res[0] = (res[0].multiply(receivedData[i].modPow(myPrivateData[i],
                            pail.getN().pow(2)))).mod(pail.getN().pow(2));
                }
//                System.out.println("RES: " + res[0]);
            }
        }else{
            res = new BigInteger[myPrivateData.length];
            if (receivedData == null){
                res = pail.encrypt(myPrivateData);
            }else{
                for (int i = 0; i < res.length; i++) {
                    res[i] = receivedData[i].modPow(myPrivateData[i], pail.getN().pow(2));
                    //The following part should be changed when applying pre-encrypted or cache
                    res[i] = res[i].multiply(pail.encrypt(BigInteger.ZERO));
                    res[i] = res[i].mod(pail.getN().pow(2));
                }
            }
        }
        
        return res;
    }
    
    
    
    public BigInteger m_encSSP = new BigInteger("1",10);
    public BigInteger m_portion;  
    public void calcualte(BigInteger[] receivedVector, BigInteger n, BigInteger g ){
        AutoGenerateData agd = new AutoGenerateData(receivedVector.length);
        for (int i=0; i< receivedVector.length; i++){
            m_encSSP = (m_encSSP.multiply(receivedVector[i].modPow(agd.getPrivateVectorData()[i],n.pow(2)))).mod(n.pow(2));
//            m_encSSP = m_encSSP.mod(n);
        }
        
        Random random = new Random();
        m_portion = new BigInteger(Integer.toString(random.nextInt(receivedVector.length)));
        
        Paillier pail = new Paillier(n, g);
        m_encSSP = (m_encSSP.multiply(pail.encrypt((n.pow(2)).subtract(m_portion)))).mod(n.pow(2));
//        m_encSSP = m_encSSP.mod(n);
    }

}