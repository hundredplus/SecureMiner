
package ppdm.algo.association;

import java.io.Serializable;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 12, 2008
 */
public class Rule implements Serializable {
    ItemSet             premise;
    ItemSet             consequence;
    double              confidence;
    int                 support;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public ItemSet getConsequence() {
        return consequence;
    }

    public void setConsequence(ItemSet consequence) {
        this.consequence = consequence;
    }

    public ItemSet getPremise() {
        return premise;
    }

    public void setPremise(ItemSet premise) {
        this.premise = premise;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }
    
    public Rule(){
    }
    
    public Rule(ItemSet premise, ItemSet consequence, double confidence, int support) {
        this.premise = premise;
        this.consequence = consequence;
        this.confidence = confidence;
        this.support = support;
    }

    @Override
    public boolean equals(Object rule) {
        
        if ((rule == null) || !(rule.getClass().equals(this.getClass()))) {
            return false;
        }
        
        if (!this.premise.equals(((Rule)rule).premise) 
                || !this.consequence.equals(((Rule)rule).consequence)){
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.premise != null ? this.premise.hashCode() : 0);
        hash = 71 * hash + (this.consequence != null ? this.consequence.hashCode() : 0);
        return hash;
    }
    
}
