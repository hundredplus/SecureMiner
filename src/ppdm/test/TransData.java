
package ppdm.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;

/**
 *
 * @author ductran
 *
 * @date Sep 1, 2008
 * 
 * This class to read transaction data from a file
 */
public class TransData {
    public BigInteger[] privateData;

    public TransData(String fileName){
        try{        
            BufferedReader br = new BufferedReader(new FileReader(fileName));       
            int vectorSize = Integer.parseInt(br.readLine().substring(">>>#transactions=".length()));
            
            privateData = new BigInteger[vectorSize];
            for (int i=0; i<vectorSize; i++){
                privateData[i] = new BigInteger(br.readLine().trim());            
            }
            br.close();
        }catch(Exception e)
        {
            System.out.println(e.toString());
            System.exit(1);
        }
    }

    public BigInteger[] getPrivateData() {
        return privateData;
    }

    public void setPrivateData(BigInteger[] privateData) {
        this.privateData = privateData;
    }
    
}
