package ppdm.test;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 13, 2008
 */
import java.util.*;
import java.util.ArrayList;
import ppdm.algo.association.ItemSet;

/**
 * Illustrate subset generation via recursion
 * and by counting in binary and masking/shifting.
 * Neither method is demonstrably superior to the others.
 * <P>
 * Also shows abstract classes, use of inheritance to
 * process subsets, and timing in Java
 * <P>
 * @author Owen Astrachan
 *
 */
public class Subset {
    

     /**
     * Generate all subsets, the list of elements from
     * which subsets are generated is set via setList
     * or at construction time.
     * Each different generator will generate all subsets
     * via the method <code>process</code> and process subsets
     * by that methods processor parameter.
     */
    static abstract class SubsetGenerator {

        static ArrayList<ItemSet> subSetList = new ArrayList<ItemSet>();
        static ArrayList<String> item = new ArrayList<String>();
        
        List myList;

        public SubsetGenerator(List list) {
            myList = list;
        }

        public void setList(List list) {
            myList = list;
        }

        public abstract void process();
    }

    /**
     * Generate all subsets recursively.
     */
    static class RecursiveSubsetGenerator extends SubsetGenerator {

        public RecursiveSubsetGenerator(List list) {
            super(list);
        }

        public void process() {
            ArrayList list = new ArrayList();
//	    myThing.startAll();
            doGenerate(list, 0);
//	    myThing.finishAll();
        }

        protected void doGenerate(ArrayList list, int index) {
            if (index >= myList.size()) {
                for (int k = 0; k < list.size(); k++) {
                    item.add((String)list.get(k));
                }
                subSetList.add(new ItemSet(item));
            } else {
                list.add(myList.get(index));
                doGenerate(list, index + 1);
                list.remove(list.size() - 1);
                doGenerate(list, index + 1);
            }
        }
    }

    /**
     * Generate all subsets via bit patterns.
     */
    static class BitSubsetGenerator extends SubsetGenerator {

        public BitSubsetGenerator(List list) {
            super(list);
        }

        public void process() {
            int size = 1 << myList.size();
//	    myThing.startAll();
            for (int k = 0; k < size; k++) {
                doProcess(k);
            }
//	    myThing.finishAll();
        }

        protected void doProcess(int bits) {
            item = new ArrayList<String>();
            int size = myList.size();
            for (int k = 0; k < size; k++) {
                if ((bits & 1) == 1) {
                    item.add((String)myList.get(k));
                }
                bits = bits >> 1;
            }
            subSetList.add(new ItemSet(item));
        }
    }


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("ant");
        list.add("cat");
        list.add("dog");
        list.add("penguin");
        list.add("lemur");

//	SubsetGenerator sgr = new RecursiveSubsetGenerator(list);
        SubsetGenerator sgb = new BitSubsetGenerator(list);        

//        sgr.process(st);
        sgb.process();
         System.out.println();
    }
}