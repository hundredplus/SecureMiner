
package ppdm.algo.association;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 13, 2008
 */
public abstract class AbstractSubsetGenerator {

    ArrayList<ItemSet> subSetList = new ArrayList<ItemSet>();
    List myList;

    public AbstractSubsetGenerator(List list) {
        myList = list;
    }

    public void setList(List list) {
        myList = list;
    }

    public abstract void process();
}
