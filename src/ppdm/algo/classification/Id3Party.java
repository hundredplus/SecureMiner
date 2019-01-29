
package ppdm.algo.classification;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTextArea;
import ppdm.algo.association.PartyID;
import ppdm.core.Party;
import ppdm.core.crypto.ElGamal;
import ppdm.core.crypto.ElGamalCipherText;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Nov 20, 2008
 */
public class Id3Party extends Party {
    
    public Id3Data inputData = new Id3Data();
    String[] class_att;
    public Id3Wrapper wrapper = new Id3Wrapper();
    public BigInteger[] sendData;
    boolean currentValueDone = false;
    BigInteger[] finalData;
    BigInteger[] data4oneValue;
    
    public void startMining() {
        preProcess();
        //start time
        
        //
    }
    
    public void preProcess(){
        class_att = inputData.getAtt().get(inputData.getNoOfAtt()-1);
        elGamal = new ElGamal();
        wrapper.p = elGamal.p;
        wrapper.g = elGamal.g;
        wrapper.y = elGamal.y;
        wrapper.partialTopo = fullTopo;
        
    }
    
    public void ID3(Id3Data data) throws Exception{
        //if att_list is empty
        if (data.noOfAtt == 0){
            //select the class with #transaction is max
            return;
        }
        
        //if all data in the same class. Should implement Yao's protocol next version
        if (true){
            return;
        }
        
        //determine the best att
        int bes_att_index = selectBestAtt(data);        
        //then spilit and recursively call this function
        
        
    }
    
    public int selectBestAtt(Id3Data data) throws Exception{
        BigInteger[] infoGainList = new BigInteger[inputData.noOfAtt];
        for (int i=0; i<inputData.noOfAtt; i++){            
            infoGainList[i]= computeOneAtt(data, i);            
        }
        //choose the maximum index
        int max_index = 0;
        for (int j=0; j<inputData.noOfAtt; j++){
            if (infoGainList[j].compareTo(infoGainList[max_index])==1){
                max_index = j;
            }
        }
        return max_index;
    }
    
    public Id3Data[] spilitData(Id3Data data, int att_index) throws Exception{
        String[] att = data.getAtt().get(att_index);
        Id3Data[] ret = new Id3Data[att.length-1];
        int data_index = -1;
        
        for (int i=0; i<data.getNoOfData(); i++){
            String[] aRecord = data.getData().get(i);
            String[] newRecord = new String[aRecord.length-1];
            for (int j=0; j<aRecord.length; j++){
                if (j < att_index){
                    newRecord[j] = aRecord[j];                    
                }else if (j>att_index){
                    newRecord[j-1] = aRecord[j];
                }else{
                    data_index = isAt(aRecord[j], att) - 1;
                }
                
            }
            
            if (data_index == -1){
                throw new Exception("Wrong data.");
            }else{
                ret[data_index].getData().add(newRecord);
            }
        }
        
        //compactify all data
        for (int i=0; i < data.getNoOfAtt(); i++){
            if (i != att_index){
                for (int j=0; j<ret.length; j++){
                    ret[j].getAtt().add(data.getAtt().get(i));
                }
                
            }
        }
        
        for (int j=0; j<ret.length; j++){
                    ret[j].setNoOfAtt(data.getNoOfAtt()-1);
        }
        
        return ret;
    }
    
    /**
     * 
     * @param data
     * @param i attribute ith
     * @throws java.lang.Exception
     */
    public BigInteger computeOneAtt(Id3Data data, int i) throws Exception{
        ArrayList<String[]> tmpAtt = data.getAtt();
        ArrayList<String[]> tmpData = data.getData();
        
        String[] att = tmpAtt.get(i);
        BigInteger[] tmp = new BigInteger[class_att.length];
        BigInteger infoGain = new BigInteger("0");
        
        //jth value
        for (int j=1; j<att.length; j++){
            currentValueDone = false;
            tmp = computeOneValue(data, i, j);
            sendData = tmp;
            
            wrapper.att_index = i;
            wrapper.value_index = j;
            packWrapper();
            send();
            
            //wait for finish current loop
            while (!currentValueDone) {
                if (verboseMode) {
                    Thread.sleep(100);
                }
            }
            
            BigInteger deminator = new BigInteger("0");
            BigInteger up = new BigInteger("0");
            for (int h=0; h<data4oneValue.length; h++){
                deminator = deminator.add(data4oneValue[h]);
                up = up.add(data4oneValue[h].pow(2));
            }                        
            infoGain = infoGain.add(up.divide(deminator));                    
        }
        return infoGain;
    }
    
    public void packWrapper(){
        
        wrapper.data = elGamal.encrypt(sendData);
        wrapper.tokenID = 0;

    }
    
    /**
     * 
     * @param data
     * @param i attribute index
     * @param j value index of the attribute i
     */
    public BigInteger[] computeOneValue(Id3Data data, int i, int j) throws Exception{
        //calculate #transaction for every class        
        BigInteger[] no_of_trans = new BigInteger[class_att.length-1];
        for (int k=0; k<data.getNoOfData(); k++){
            //if the value is what we care
            if (data.getData().get(k)[i].equalsIgnoreCase(data.getAtt().get(i)[j])){
                int index = isAt(data.getData().get(k)[data.getNoOfAtt()-1], class_att)-1;
                if (index != -1){
                    no_of_trans[index].add(BigInteger.ONE);
                }else{
                    throw new Exception("Wrong data!");
                }
            }
        }
        return no_of_trans;
    }
    
    
    
    /**
     * check if a string is in the array of string
     * @param sub
     * @param total
     * @return
     */
    public int isAt(String sub, String[] total){
        for (int i=0; i< total.length; i++){
            if (sub.equalsIgnoreCase(total[i]))
                return i;
        }
        return -1;
    }

    public void displayResults(JTextArea jtxaResult) {
        
    }

    @Override
    public void process() throws Exception {
        receive();
        if (wrapper.tokenID != 0) {
            System.out.println("\tStart @ " + wrapper.tokenID);
            secureCompute();
            send();            
        } else {//return the first party            
            //decrypt the data to get final partialDistance
            data4oneValue = elGamal.decrypt(wrapper.data);
            currentValueDone = true;
        }
    }

    public void receive() throws Exception {
        objectInputStream = new ObjectInputStream(connection.getInputStream());
        // Receive config and data
        this.setWrapper((Id3Wrapper) objectInputStream.readObject());
        objectInputStream.close();
    }
    
    public void setWrapper(Id3Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void send() throws Exception {
        wrapper.tokenID = (wrapper.tokenID + 1) % wrapper.partialTopo.size();
        PartyID topo = wrapper.partialTopo.get(wrapper.tokenID);

        clientSK = new Socket(topo.IP, topo.port);
        objectOutputStream = new ObjectOutputStream(clientSK.getOutputStream());
        objectOutputStream.writeObject(wrapper);
        objectOutputStream.flush();

        objectOutputStream.close();
        clientSK.close();
    }

    public void secureCompute() throws Exception{
        //compute
        elGamal = new ElGamal(wrapper.p,wrapper.g,wrapper.y);
        //get local data
        class_att = inputData.getAtt().get(inputData.getNoOfAtt()-1);
        BigInteger[] localData = computeOneValue(inputData, wrapper.att_index, wrapper.value_index);
        ElGamalCipherText[] localDataEn = elGamal.encrypt(localData);
        
        for (int i=0; i<localData.length; i++){
            wrapper.data[i] = wrapper.data[i].multiply(localDataEn[i]); 
        }
        
    }



}
