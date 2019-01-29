
package ppdm.algo.classification;

import java.util.ArrayList;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Jan 5, 2009
 */
public class Id3Data {
    public int                     noOfAtt;
    
    public ArrayList<String[]>     att = new ArrayList<String[]>();
    
    public ArrayList<String[]>     data = new ArrayList<String[]>();
    
    
    public int getNoOfData(){
        return data.size();
    }

    public ArrayList<String[]> getAtt() {
        return att;
    }

    public void setAtt(ArrayList<String[]> att) {
        this.att = att;
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    public int getNoOfAtt() {
        return noOfAtt;
    }

    public void setNoOfAtt(int noOfAtt) {
        this.noOfAtt = noOfAtt;
    }

    
}
