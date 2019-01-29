
package ppdm.algo.association;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import ppdm.core.crypto.ElGamalCipherText;
import ppdm.core.network.Wrapper;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 6, 2008
 */
public class AssoWrapper extends Wrapper implements Serializable {

    
    public ArrayList<ElGamalCipherText>    data;
    public ArrayList<String>        attributeName;   

    /**
     * for cache-table version
     */
    public HashMap<ItemSet,ArrayList> cacheTable = new HashMap();

    public int                      tokenID;
    public String                   projectID;
    public String                   projectName;
    public ArrayList<PartyID>       partialTopo;

    public ArrayList<PartyID>       fullTopoForCache;
    public String                   databaseName;

    public BigInteger               p;
    public BigInteger               g;
    public BigInteger               y;

    public int                      percentDone;
    public boolean                  projectStart;
    public boolean                  projectDone;

    public int                      partition;

    public boolean                  cacheRequest;
    
    
}
