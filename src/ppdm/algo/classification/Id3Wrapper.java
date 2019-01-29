
package ppdm.algo.classification;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import ppdm.algo.association.PartyID;
import ppdm.core.crypto.ElGamalCipherText;
import ppdm.core.network.Wrapper;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 17, 2008
 */
public class Id3Wrapper extends Wrapper implements Serializable {

    public int                      tokenID;
    public ElGamalCipherText[]             data;
  
    public int                      att_index;
    public int                      value_index;

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

}
