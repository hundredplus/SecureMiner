package ppdm.algo.association;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 2, 2008
 */
public class ItemSet implements Serializable {
    /** ItemSet contains an array of items */
    ArrayList<String>               item = new ArrayList<String>();
    /** Values of this itemset*/
    ArrayList<BigInteger>           values = new ArrayList<BigInteger>();
    /** Support of this itemset*/
    int                             support;
    
    /** For nominal data*/
    ArrayList<String>               strVals = new ArrayList<String>();
    
    public ItemSet(){ 
    }
    
    public ItemSet(String att, ArrayList<BigInteger> values){
        this.item.add(att);
        if (values != null){
            this.values = values;
            this.support = sum(this.values).intValue();
        }else{
            this.values = new ArrayList<BigInteger>();
//            this.support = sum(this.values).intValue();
        }            
        lexiOrder();
    }
    
    public ItemSet(String ite){
        this(ite, new ArrayList<BigInteger>());
    }
    
    /**
     * Constructor with values is multiplication of all attributes' values
     * Regarding to the lexicographic order of Attributes
     * @param att
     * @param values
     */
    public ItemSet(ArrayList<String> att, ArrayList<BigInteger>[] values){        
        this.item = att;     
        if (values == null){
            this.values = new ArrayList<BigInteger>();
        }else{
            ArrayList<BigInteger> tmp = new ArrayList<BigInteger>();
            for (int i=0; i<values[0].size();i++){
                BigInteger tmp2 = new BigInteger("1");
                for (int j=0; j<values.length;j++){
                    tmp2 = tmp2.multiply(values[j].get(i));
                }
                tmp.add(tmp2);
            }
            this.values = tmp;
        }
        lexiOrder();
    }
    
    public ItemSet(ArrayList<String> att, ArrayList<BigInteger> values){        
        this.item = att;
        this.values = values;
        lexiOrder();
    }
    
    public ItemSet(ArrayList<String> att){
        this.item = att;        
        this.values = new ArrayList<BigInteger>();
        lexiOrder();
    }
    
    /**
     * The number of attributes in this Itemset
     * @return
     */
    public int getSize(){
        return item.size();
    }


    public ArrayList<String> getItem() {
        return item;
    }

    public void setItem(ArrayList<String> item) {
        this.item = item;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {        
        this.support = support;
    }
    
    public void setSupport() {
        if (this.values == null) return;
        this.support = sum(this.values).intValue();
    }
    
    public int calSupport(){
        return sum(this.values).intValue();
    }

    public int getValuesSize(){
        return values.size();
    }
    
    public ArrayList<BigInteger> getValues(){
        return values;
    }
    
    public void setValues(ArrayList<BigInteger> values){
        this.values = values;
    }

    public ArrayList<String> getStrVals() {
        return strVals;
    }

    public void setStrVals(ArrayList<String> strVals) {
        this.strVals = strVals;
    }
    
    
    
    public int indexOf(String att){
        if (att == null) return -1;
        return item.indexOf(att);        
    }
    
    /**
     * If this itemset contains a specific item
     * @param it
     * @return
     */
    public boolean contains(String att){
        if (att == null) return false;
        if (item.indexOf(att) == -1) return false;
        else return true;
    }
    
    public boolean contains(ArrayList<String> atts){
        if (atts == null) return false;
        for (int i=0; i<atts.size();i++){
            if (!contains(atts.get(i))) return false;
        }
        return true;
    }
    
    public boolean contains(ItemSet is){
        if (is == null) return false;
        if (contains(is.item)) return true;
        else return false;
    }
    
    public int containedBy(ArrayList<ItemSet> itemList){
        if (itemList == null ||itemList.isEmpty()) return -1;
        for (int i=0; i<itemList.size();i++){
            if (this.equals(itemList.get(i))) return i;
        }
        return -1;
    }
    
    public void add(String att){
        if (att == null) return;
        if (this.contains(att)) return;
        this.item.add(att);
        this.item = lexiOrder(this.item);
    }
    
    public void add(ArrayList<String> atts){
        if (atts == null) return;
        for (int i=0; i< atts.size();i++)
            add(atts.get(i));
    }
    
    /**
     * Lexicographic order of an ArrayList
     * @param strs
     * @return
     */
    public static ArrayList<String> lexiOrder(ArrayList<String> strs){
        if (strs == null) return null;
        ArrayList<String> tmp = new ArrayList<String>();
        String[]    res = new String[strs.size()];
        for (int i=0; i<res.length; i++){
            res[i] = strs.get(i);
        }
        for (int i=0; i < res.length - 1; i++){
            for (int j=i+1; j<res.length;j++){
                if (res[i].compareTo(res[j])>0){
                    String tmp2 = res[i];
                    res[i] = res[j];
                    res[j] = tmp2;                    
                }
            }
        }
        for (int i=0; i<res.length;i++){
            tmp.add(res[i]);                   
        }
        return tmp;
    }
    
    public void lexiOrder(){
        this.item = lexiOrder(this.item);
    }
    
    /**
     * Check if two itemsets are joinable.
     * Then join if can
     * @param it1
     * @param it2
     * @return <ItemSet> or null if can not join
     */
    public static ItemSet joinItemSet(ItemSet it1, ItemSet it2){
        if (it1.equals(it2)) return null;
        if (it1.getSize() != it2.getSize()) return  null;    
        ArrayList<String> strs = new ArrayList<String>();        
        for (int k = 0; k < it1.getSize()-1; k++) {
            if (!it1.getItem().get(k).equals(it2.getItem().get(k))) {
                return null;
            }else{
                strs.add(it1.getItem().get(k));
            }
        }
        strs.add(it1.getItem().get(it1.getSize()-1));
        strs.add(it2.getItem().get(it2.getSize()-1));
        strs = lexiOrder(strs);
        
        return new ItemSet(strs);
    }

    /**
     * Tests if two item sets are equal regarding of position of items
     * 
     * @param itemSet another item set
     * @return true if this item set contains the same items as the given one
     */
    @Override
    public boolean equals(Object itemSet) {

        if ((itemSet == null) || !(itemSet.getClass().equals(this.getClass()))) {
            return false;
        }
        if (this.item.size() != ((ItemSet) itemSet).item.size()) {
            return false;
        }
        for (int i = 0; i < item.size(); i++) {
            if (!this.item.get(i).equals(((ItemSet) itemSet).item.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.item != null ? this.item.hashCode() : 0);
        return hash;
    }
    
    public BigInteger sum(ArrayList<BigInteger> values){
        BigInteger ret = new BigInteger("0");
        for (int i=0; i<values.size();i++){
            ret = ret.add(values.get(i));
        }
        return ret;
    }
    
    /**
     * Find a phanBu of an itemset
     * @param partialItemSet the part of this ItemSet
     * @return phanBu = this - partialItemSet
     */
    public ItemSet phanBu(ItemSet partialItemSet) {
        if (!this.contains(partialItemSet)) return null;
        ItemSet ret = new ItemSet();
        for (int i = 0; i < this.getSize(); i++) {
            if (!partialItemSet.contains(this.getItem().get(i))) {
                ret.add(this.getItem().get(i));
            }
        }
        return ret;
    }

    public void print(boolean withSupport){
        for (int i = 0; i < this.item.size(); i++) {
            System.out.print(this.item.get(i) + " ");
        }
        
        if (withSupport){
            System.out.print("(" + this.support + ") ");
        }
            
    }
    
    public String toString(boolean withSupport){
        String ret = "";

        for (int i = 0; i < this.item.size(); i++) {
            ret += this.item.get(i);
            if (i!=this.item.size()-1) ret += ", ";                
        }

        if (withSupport){
            ret += " (" + this.support + ")";
        }
        return ret;
    }
}
