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
public class RecursiveSubsetGenerator extends AbstractSubsetGenerator {

    public RecursiveSubsetGenerator(List list) {
        super(list);
    }

    public void process() {
        ArrayList list = new ArrayList();
        doGenerate(list, 0);
    }

    protected void doGenerate(ArrayList list, int index) {
        if (index >= myList.size()) {
            ArrayList<String> item = new ArrayList<String>();
            for (int k = 0; k < list.size(); k++) {
                item.add((String) list.get(k));
            }
            if (!item.isEmpty() && item.size() != 0 && item.size()<myList.size())
                subSetList.add(new ItemSet(item));
        } else {
            list.add(myList.get(index));
            doGenerate(list, index + 1);
            list.remove(list.size() - 1);
            doGenerate(list, index + 1);
        }
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            list.add("ant");
            list.add("cat");
            list.add("dog");
            list.add("penguin");
            list.add("lemur");

            RecursiveSubsetGenerator sgr = new RecursiveSubsetGenerator(list);

            sgr.process();
            System.out.println();
        } catch (Exception ex) {
            Logger.getLogger(RecursiveSubsetGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
