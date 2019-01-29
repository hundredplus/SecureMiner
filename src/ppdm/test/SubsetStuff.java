package ppdm.test;

import java.util.*;

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


public class SubsetStuff
{
    /**
     * Class to process one subset. In general, subset generators
     * will call startAll, generate all subsets, then call finishAll.
     * The doOne method will be called for each element in some
     * subset, the finishOne method should be called after finishing
     * all elements in some subset.
     *
     */
    static abstract class OneSubsetProcessor{
	double myTime;
	
	public void startAll(){
	    myTime = System.currentTimeMillis();
	}
	
	public void finishAll(){
	    myTime = System.currentTimeMillis() - myTime;
	}
	public double getTime(){
	    return myTime/1000;
	}

	/**
	 * Called by subset generator with each element
	 * in one subset. To process a complete subset,
	 * methods doOne() and finishOne() should work
	 * together, e.g., finishOne() called after processing
	 * all elements.
	 */
	public abstract void doOne(Object o);
	public abstract void finishOne();

    }

    /**
     * Generate all subsets, the list of elements from
     * which subsets are generated is set via setList
     * or at construction time.
     * Each different generator will generate all subsets
     * via the method <code>process</code> and process subsets
     * by that methods processor parameter.
     */
    
    static abstract class SubsetGenerator{
	List myList;

	public SubsetGenerator(List list){
	    myList = list;
	}
	
	public void setList(List list){
	    myList = list;
	}

	public abstract void process(OneSubsetProcessor thing);
    }

    /**
     * Generate all subsets recursively.
     */
    static class RecursiveSubsetGenerator extends SubsetGenerator {

	OneSubsetProcessor myThing;
	public RecursiveSubsetGenerator(List list){
	    super(list);
	}
	
	public void process(OneSubsetProcessor thing){
	    myThing = thing;
	    ArrayList list = new ArrayList();
	    myThing.startAll();
	    doGenerate(list,0);
	    myThing.finishAll();
	}
	
	protected void doGenerate(ArrayList list, int index){
	    if (index >= myList.size()){
		for(int k=0; k < list.size(); k++){
		    myThing.doOne(list.get(k));
		}
		myThing.finishOne();
	    }
	    else {
		list.add(myList.get(index));
		doGenerate(list,index+1);
		list.remove(list.size()-1);
		doGenerate(list, index+1);		
	    }
	}
    }

    /**
     * Generate all subsets via bit patterns.
     */
    static class BitSubsetGenerator extends SubsetGenerator {

	OneSubsetProcessor myThing;
	
	public BitSubsetGenerator(List list){
	    super(list);
	}
	
	public void process(OneSubsetProcessor thing){
	    myThing = thing;
	    int size = 1 << myList.size();
	    myThing.startAll();
	    for(int k=0; k < size; k++){
		doProcess(k);
	    }
	    myThing.finishAll();
	}
	
	protected void doProcess(int bits){
	    int size = myList.size();
	    for(int k=0; k < size; k++){
		if ( (bits & 1) == 1){
		    myThing.doOne(myList.get(k));
		}
		bits = bits >> 1;
	    }
	    myThing.finishOne();
	}
    }
    

    /**
     * Counts subsets, note that doOne() does nothing.
     */
    static class Counter extends OneSubsetProcessor{
	int myCount;
	public Counter(){
	    myCount = 0;
	}
	public void startAll(){
	    super.startAll();
	    myCount = 0;
	}
	public void doOne(Object o){

	}
	public void finishOne(){
	    myCount++;
	}
    }

    /**
     * Prints subsets.
     */
    static class Printer extends OneSubsetProcessor{
	public void doOne(Object o){
	    System.out.print(o+" ");
	}
	public void finishOne(){
	    System.out.println();
	}
    }

    public static void main(String[] args){

	String[] array = {
	    "ant", "cat", "dog", "penguin", "lemur",
//	    "lion", "horse", "giraffe", "goat", "monkey",
//	    "zebra", "yak", "cougar", "tapir", "mongoose",
//	    "tiger", "kangaroo", "leopard", "cheetah", "ostrich",
//	    "antelope", "narwhal", "unicorn"
	};
	List list = Arrays.asList(array);
	SubsetGenerator sgr = new RecursiveSubsetGenerator(list);
	SubsetGenerator sgb = new BitSubsetGenerator(list);	
	OneSubsetProcessor st = new Printer();
	Counter ct = new Counter();
	
	sgr.process(ct);
	sgb.process(st);
	System.out.println("count = " + ct.myCount);
	System.out.println("time = "+ct.getTime());
    }

}
