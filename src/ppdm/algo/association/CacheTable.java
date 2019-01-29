
package ppdm.algo.association;

import java.util.ArrayList;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 10, 2009
 */
public class CacheTable {
    public  ArrayList<ItemSet>   itemSetList;
    public  ArrayList<String>   partyIDList;

    public CacheTable(){
        itemSetList = new ArrayList<ItemSet>();
        partyIDList = new ArrayList<String>();
    }
}
