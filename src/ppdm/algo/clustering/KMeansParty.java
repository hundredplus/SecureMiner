
package ppdm.algo.clustering;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JTextArea;
import ppdm.algo.association.PartyID;
import ppdm.core.Party;
import ppdm.core.TimeToStr;
import ppdm.core.crypto.ElGamal;
import ppdm.core.crypto.ElGamalCipherText;
import ppdm.core.crypto.Paillier;
import ppdm.core.math.Presentation;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Oct 20, 2008
 */
public class KMeansParty extends Party {

    public ArrayList<String[]>      inputData = new ArrayList<String[]>();
    public ArrayList<Double[]>      record = new ArrayList<Double[]>();
    public ArrayList<Double[]>      centroid = new ArrayList<Double[]>();    
    public ArrayList<Double[]>      partialDistance = new ArrayList<Double[]>();
    public ArrayList<Double[]>      TotalDistance = new ArrayList<Double[]>();
    
    public Double[][]               standardDev;
    
    public int                     num_cluster;
    public int                     max_num_iteration;
    
    
    
    public KMeansWrapper           wrapper = new KMeansWrapper();
    
    boolean                         firstLoop = true;
    boolean                         currentLoopDone = false;
    
    String                          timeRunning;
    
    public void displayResults(JTextArea jtxaResult) {
        jtxaResult.setText("");
        jtxaResult.append("Total time: " + timeRunning + "\n");
        jtxaResult.append("Running information\n");
        jtxaResult.append("==================\n");        
        jtxaResult.append("Number of parties: " + fullTopo.size() + "\n");
        for (int i=0; i<fullTopo.size(); i++)
            jtxaResult.append("\t Party " + (i+1) + ": " + fullTopo.get(i).getIP() 
                    +"\t Port: "+ fullTopo.get(i).getPort() + "\n");
        jtxaResult.append("Database: " +  wrapper.databaseName + "\n");
        jtxaResult.append("Number of instances: " + record.size()+ "\n");
        jtxaResult.append("Number of clusters: " + centroid.size() + "\n");
        jtxaResult.append("\nClustering results\n");
        jtxaResult.append("==================\n\n");
        
        jtxaResult.append("\t\tCluster\n");
        jtxaResult.append("Attribute");
        for (int i=0; i<centroid.size(); i++){
            jtxaResult.append("\t" + (i+1));
        }
        jtxaResult.append("\n");
        jtxaResult.append("------------------------------------------------------------\n");
        for (int i=0; i<record.get(0).length; i++){
            jtxaResult.append(inputData.get(0)[i] + "\n");
            jtxaResult.append(" Mean\t\t");
            for (int j=0; j<centroid.size(); j++){
                jtxaResult.append( Presentation.round(centroid.get(j)[i],3) +"\t");
            }
            jtxaResult.append("\n");
            jtxaResult.append(" Std.Dev.\t\t");
            jtxaResult.append("\n\n");
        }
        jtxaResult.append("\n");
        jtxaResult.append("Number of instances in each cluster:\n");
        for (int i=0; i<wrapper.cluster.size();i++){
            jtxaResult.append((i+1)+ "\t" + wrapper.cluster.get(i).size()+"\n");
        }
        jtxaResult.append("\n");
    }

    public void startMining() throws Exception{
        long startTime = System.currentTimeMillis();
        initPara();
        while (--max_num_iteration >= 0){
            System.out.println("Start @ first: " + max_num_iteration);
            currentLoopDone = false;
            cluster();
            calculateCentroidAndDistance();
            packWrapper();
            send();            
            //wait for finish current loop
            while (!currentLoopDone) {
                if (verboseMode) {
                    Thread.sleep(100);
                }
            }
        }
        
        cluster();
        calculateCentroidAndDistance();
        long endTime = System.currentTimeMillis();
        timeRunning = TimeToStr.secondsToString((long)(endTime-startTime)/1000);
        
        processResults();                
    }

    public void initPara() {
        //get only the value into <record>
        
        
    }
    
    @Override
    public void process() throws Exception {
        receive();
        if (wrapper.tokenID != 0) {
            System.out.println("\tStart @ " + wrapper.tokenID);
            secureComputeDistance();
            send();            
        } else {//return the first party            
            //decrypt the data to get final partialDistance
            TotalDistance = elGamal.decryptData(wrapper.data);
            System.out.println("Finish loop: " + max_num_iteration);
            currentLoopDone = true;
        }
    }
    

    public void packWrapper(){
        elGamal = new ElGamal();
        wrapper.partialTopo = fullTopo;
        wrapper.data = elGamal.encryptData(partialDistance);
        wrapper.tokenID = 0;
        wrapper.p = elGamal.p;
        wrapper.g = elGamal.g;
        wrapper.y = elGamal.y;
    }
    /**
     * Init the first cluster. This is equal to choosing randomly centroids.
     */
    public void initCluster(){
        wrapper.cluster = new ArrayList<ArrayList<Integer>>(num_cluster);
        //Set first <num_cluster> into cluster to avoid empty
        for (int i=0; i<num_cluster; i++){
            ArrayList<Integer> tmp = new ArrayList<Integer>();
            tmp.add(i);
            wrapper.cluster.add(tmp);
        }
        Random rd = new Random();
        for (int i=num_cluster; i<record.size(); i++){
            int ind = rd.nextInt(num_cluster);
            wrapper.cluster.get(ind).add(i);//Then assign randomly
        }
    }
    /**
     * to cluster all the points
     */
    public void cluster() {
        if (firstLoop){            
            firstLoop = false;
            //init cluster
            initCluster();
        }else{
            //clear cluster and initilize
            wrapper.cluster = new ArrayList<ArrayList<Integer>>();
            for (int i=0; i<num_cluster; i++){
                ArrayList<Integer> cl = new ArrayList<Integer>();
                wrapper.cluster.add(cl);
            }
            //to assign new cluster
            for (int i=0; i<TotalDistance.size(); i++){
                Double[] dis = TotalDistance.get(i);
                int clusterIndex = getClosetCentroid(dis);
                //add this point to corresponding cluster.
                wrapper.cluster.get(clusterIndex).add(i);
            }
        }
    }
      
    public void calculateCentroidFromCluster(){
        centroid = new ArrayList<Double[]>();
        for (int i=0; i< wrapper.cluster.size(); i++){            
            ArrayList<Integer> indexList = wrapper.cluster.get(i);
            Double[] center = new Double[record.get(0).length];
            //init
            for (int t = 0; t<center.length; t++){
                center[t] = 0.0;
            }
            for (int j =0; j<indexList.size(); j++){
                Double[] point = record.get(indexList.get(j));
                for (int k=0; k<center.length; k++){
                    center[k] += point[k]/(double)indexList.size();
                }
            }
            centroid.add(center);
        }
    }
    
    //this is for vertical
    public void calculateCentroidAndDistance(){
        calculateCentroidFromCluster();
        partialDistance = new ArrayList<Double[]>();
        for (int i=0; i<record.size(); i++){
            Double[] onePoint = new Double[num_cluster];
            for (int j=0; j<num_cluster; j++){
                onePoint[j]=(getSquareEuclideDis(record.get(i), centroid.get(j)));
            }
            partialDistance.add(onePoint);
            
         }
    }
 
    public Double getSquareEuclideDis(Double[] num1, Double[] num2) {
        Double tmp = 0.0;
        for (int i = 0; i < num1.length; i++) {
            tmp = tmp + Math.pow(num1[i] - num2[i], 2);
        }
        return tmp;
    }


    public void receive() throws Exception {
        objectInputStream = new ObjectInputStream(connection.getInputStream());
        // Receive config and data
        this.setWrapper((KMeansWrapper) objectInputStream.readObject());
        objectInputStream.close();
    }
    

    public int getClosetCentroid(Double[] distance) {
        double minVal = Double.MAX_VALUE;
        int min = 0;
        for (int i=0; i<distance.length; i++){
            if (minVal > distance[i]){
                minVal = distance[i];
                min = i;
            }
        }
        return min;
    }

    public void secureComputeDistance() {
        calculateCentroidAndDistance();
        elGamal = new ElGamal(wrapper.p,wrapper.g,wrapper.y);
        ArrayList<ElGamalCipherText[]> tmp = elGamal.encryptData(partialDistance);
        for (int i=0; i<tmp.size(); i++){
            ElGamalCipherText[] onePoint = tmp.get(i);
            ElGamalCipherText[] data = wrapper.data.get(i);
            for (int j=0; j<onePoint.length; j++){
                data[j] = data[j].multiply(onePoint[j]);                
            }
            wrapper.data.set(i, data);
        }
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
    
    /**
     * convert from String to Double
     * @throws java.lang.Exception
     */
    public void convertData() throws Exception{
        record = new ArrayList<Double[]>();
        for (int i=1; i < inputData.size(); i++){
            String[] tmp1 = inputData.get(i);
            Double[] tmp2 = new Double[tmp1.length];
            for (int j=0; j<tmp1.length;j++){
                tmp2[j] = Double.parseDouble(tmp1[j]);
            }
            record.add(tmp2);
        }
    }
    
    public void setWrapper(KMeansWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public void processResults() {
        calculateStdDev();
        System.out.println("Finish here....");
    }

    public void calculateStdDev() {
        //numofAtt x numOfCluster
        standardDev = new Double[inputData.get(0).length][num_cluster];
        for (int i=0; i<inputData.get(0).length; i++){
            for (int j=0; j<num_cluster; j++){
                Double tmp = 0.0;
//                tmp += record.get(i)[]
            }
        }
    }
}
