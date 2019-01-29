
package ppdm.algo.association;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 11, 2008
 */
public class RuleGenerator {
    ArrayList<Rule>             ruleItemSetList = new ArrayList<Rule>();
    Hashtable<ItemSet,Integer>  freqItemSetHash = new Hashtable<ItemSet, Integer>();
    double                      minConf;
    
    public RuleGenerator(Hashtable<ItemSet,Integer> freqItemSetHash, double minConf){
        this.freqItemSetHash = freqItemSetHash;
        this.minConf = minConf;
    }
    /**
     * 
     * @param freqItemSetList freq itemset with support
     */
    public void genAllRules(){
        Enumeration enu = freqItemSetHash.keys();
        while (enu.hasMoreElements()){
            ItemSet itemSet = (ItemSet)enu.nextElement();
            if (itemSet.getSize() >=2)
                genRule(itemSet);
        }
    }
    
    public void genRule(ItemSet itemSet){
        ArrayList<String>   al = new ArrayList<String>();
        for (int i=0; i<itemSet.getSize(); i++){
            al.add(itemSet.getItem().get(i));
        }
        //generate all subsets of this itemset
        BitSubsetGenerator  bitSubsetGenerator = new BitSubsetGenerator(al);
        bitSubsetGenerator.process();
        ArrayList<ItemSet>  subSet = bitSubsetGenerator.subSetList;
        for (int i=0; i<subSet.size(); i++){
            ItemSet     premise = subSet.get(i);
            premise.setSupport(freqItemSetHash.get(premise));
            double      conf = getConf(premise, itemSet);
            if (conf >= minConf){
                ItemSet     consequence = itemSet.phanBu(premise);
                consequence.setSupport(freqItemSetHash.get(consequence));
                Rule rule = new Rule(premise, consequence, conf, itemSet.support);
                insert(rule);
            }
        }
    }
    
    public double getConf(ItemSet premise, ItemSet all){        
        return (double)freqItemSetHash.get(all)/(double)freqItemSetHash.get(premise);
    }
   
    
    /**
     * Insert the rule regarding the order of confidence and support
     * @param rule
     */
    public void insert(Rule rule){
        if (ruleItemSetList.isEmpty() || ruleItemSetList.size() == 0){
            ruleItemSetList.add(rule);
            return;
        }
        if (ruleItemSetList.contains(rule))
            return;
        int insertPos = ruleItemSetList.size();
        for (int i=0; i<ruleItemSetList.size(); i++){
            //Compare confidence
            if (ruleItemSetList.get(i).confidence > rule.confidence)
                continue;
            else if (ruleItemSetList.get(i).confidence < rule.confidence){
                insertPos = i;
                break;
            } else {
                //if confidence equals, compare support
                if (ruleItemSetList.get(i).support < rule.support){
                    insertPos = i;
                    break;
                }else if (ruleItemSetList.get(i).support == rule.support){
                    //if support equals, compare length of premise (as short as possible)
                    if (ruleItemSetList.get(i).premise.getSize() > rule.premise.getSize()){
                        insertPos = i;
                        break;
                    }
                }
            }
        }
        ruleItemSetList.add(insertPos, rule);            
    }
    
//    public ArrayList<Rule> ruleCandidate(ArrayList<ItemSet> freqItemSetList){
//        
//        return null;
//    }
}
