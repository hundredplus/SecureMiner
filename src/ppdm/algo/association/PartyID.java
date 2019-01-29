package ppdm.algo.association;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 6, 2008
 */
public class PartyID implements Serializable {
    public  int             ID;
    public  String          IP;
    public  int             port;
    /** contains attributes */
    ArrayList<String>       attribute = new ArrayList();

    public int              on_off;
    
    public PartyID(){
         on_off = 1; //0=inactive; 1=active
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public ArrayList<String> getAttribute() {
        return attribute;
    }

    public void setAttribute(ArrayList<String> attribute) {
        this.attribute = attribute;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void activate(){
        on_off = 1;
    }

    public void deactivate(){
        on_off = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    
    
    /**
     * 
     * @param partyId Party identifier includes: IP, Port, Attribute list.
     */
//    public PartyID(String[] partyId){
//        if (partyId == null) {
//            System.err.println("Topo could not be null...");
//            System.exit(1);
//        }
//        for (int i=0; i < partyId.length; i++){
//            topo.add(partyId[i]);
//        }        
//    }
    
     

}
