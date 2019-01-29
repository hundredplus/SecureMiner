package ppdm.algo.association;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 13, 2008
 */
public class BitSubsetGenerator extends AbstractSubsetGenerator {

    public BitSubsetGenerator(List list) {
        super(list);
    }

    public void process() {
        int size = 1 << myList.size();
        for (int k = 0; k < size; k++) {
            doProcess(k);
        }
    }

    protected void doProcess(int bits) {
        ArrayList<String> item = new ArrayList<String>();
        int size = myList.size();
        for (int k = 0; k < size; k++) {
            if ((bits & 1) == 1) {
                item.add((String) myList.get(k));
            }
            bits = bits >> 1;
        }
        if (!item.isEmpty() && item.size() != 0 && item.size()<myList.size())
            subSetList.add(new ItemSet(item));
    }
    
    public static void main(String[] args) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            list.add("ant");
            list.add("cat");
            list.add("dog");
            list.add("penguin");
            list.add("lemur1");
            list.add("lemur2");
            list.add("lemur3");
            list.add("lemur4");
            list.add("lemur5");
            list.add("lemur6");
            list.add("lemur7");
            list.add("lemur8");
            list.add("lemur9");
            list.add("lemur10");
            list.add("lemur11");
            list.add("lemur12");
            list.add("lemur13");
            list.add("lemur14");
            list.add("lemur15");
            list.add("lemur16");
            BitSubsetGenerator sgb = new BitSubsetGenerator(list);
            sgb.process();
            System.out.println();
        } catch (Exception ex) {
            Logger.getLogger(BitSubsetGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
