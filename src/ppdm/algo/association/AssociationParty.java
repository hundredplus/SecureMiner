package ppdm.algo.association;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import ppdm.core.math.Presentation;
import ppdm.core.Party;
import javax.swing.table.DefaultTableModel;
import ppdm.block.ScalarProductElGamal;
import ppdm.core.TimeToStr;
import ppdm.core.crypto.ElGamal;
import ppdm.core.crypto.ElGamalCipherText;
import ppdm.gui.CustomTableColumnModel;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 29, 2008
 */
public class AssociationParty extends Party {

    public ArrayList<ItemSet> itemList = new ArrayList<ItemSet>();//all items and values of this party
    public double minSup;
    public double maxSup;
    public double minConfidence;
    public int num_rules;
    public int rankBy;
    public AssoWrapper wrapper = new AssoWrapper();

    public AssoWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(AssoWrapper wrapper) {
        this.wrapper = wrapper;
    }
    //ItemSet, support
    public Hashtable<ItemSet, Integer> freqItemSetHash = new Hashtable<ItemSet, Integer>();
    public ArrayList<Rule> associationRuleList = new ArrayList<Rule>();
    public ArrayList<ItemSet> prevFreqList = new ArrayList<ItemSet>();    //L_k
    public ArrayList<ItemSet> candidateList = new ArrayList<ItemSet>();   //C_k
    boolean isThisItemSetDone = false;
    private BigInteger currentSupport;
    public BigInteger totalDataSize;
    public ItemSet currentItemSet;
    long totalTime = 0;
    long encryptionTime = 0;

    public static void main(String[] args) {

        /*
        Hashtable<ItemSet, Integer> hash = new Hashtable<ItemSet, Integer>();
        ArrayList<String> al = new ArrayList<String>();
        al.add("Hello");
        al.add("World");
        ItemSet its = new ItemSet(al);
        hash.put(its, 25);
        ArrayList<String> al2 = new ArrayList<String>();

        al2.add("World");
        al2.add("Hello");
        ArrayList<BigInteger> values = new ArrayList<BigInteger>();
        values.add(new BigInteger("1"));
        ItemSet its2 = new ItemSet(al2, values);
        System.out.println(hash.get(its2));

        hash.put(its2, 8);
        Enumeration enu = hash.keys();
        while (enu.hasMoreElements()) {
            System.out.println(((ItemSet) enu.nextElement()).item.get(0));
            System.out.println(hash.get(((ItemSet) enu.nextElement())));
        }
        */
        Random rand = new Random(System.currentTimeMillis());
        for (int i=0; i<10; i++)
            System.out.println(rand.nextInt(1000));
    }

    public void init() throws Exception {
        freqItemSetHash = new Hashtable<ItemSet, Integer>();
        prevFreqList = new ArrayList<ItemSet>();
        candidateList = new ArrayList<ItemSet>();

        /* This is for Paillier
        BufferedReader br = null;
        br = new BufferedReader(new FileReader("..\\elGamal.txt"));
        BigInteger _n = new BigInteger(br.readLine());
        BigInteger _g = new BigInteger(br.readLine());
        BigInteger _lambda = new BigInteger(br.readLine());
        BigInteger _mu = new BigInteger(br.readLine());
        BigInteger _p = new BigInteger(br.readLine());
        BigInteger _q = new BigInteger(br.readLine());
        br.close();

        elGamal = new Paillier(_n, _g, _lambda, _mu, _p, _q);

        System.out.println(elGamal.n);
        System.out.println(elGamal.g);
        System.out.println(elGamal.lambda);
        System.out.println(elGamal.mu);
        System.out.println(elGamal.p);
        System.out.println(elGamal.q);

        System.out.println("Finished Init!");
         */

        elGamal = new ElGamal(256);
        wrapper.fullTopoForCache = fullTopo;
    }

    public void startMining() throws Exception {
        long startTime = System.currentTimeMillis();

        completedCurrentProject = false;
        init();
        genFreqItemSet();
        RuleGenerator rg = new RuleGenerator(freqItemSetHash, minConfidence);
        rg.genAllRules();
        associationRuleList = rg.ruleItemSetList;
        completedCurrentProject = true;

        long endTime = System.currentTimeMillis();

        totalTime = endTime - startTime;
//        String timeRunning = TimeToStr.secondsToString((long)(endTime-startTime)/1000);
//        System.out.println("Total time:" + timeRunning);

        write2Log();
    }

    public void write2Log() throws IOException {
        FileWriter fWriter = new FileWriter("..\\log.txt", true);
        String totalTimeRunning = TimeToStr.secondsToString((long) (totalTime) / 1000);
//        String totalTimeEnc = TimeToStr.secondsToString((long)(encryptionTime)/1000);

        fWriter.write("--------------\n");
        fWriter.write(totalTime + "\n");
        fWriter.write(totalTimeRunning + "\n");
//        fWriter.write(totalTimeEnc + "\n");
        fWriter.write("--------------\n");

        fWriter.flush();
        fWriter.close();
    }

    public void displayResults(javax.swing.JTable jtblReults) {
        int real_num_rules = Math.min(num_rules, associationRuleList.size());
        String[] colName = {"Rule", "Premise (Support)", "Consequence (Support)", "Confidence", "Support"};
        DefaultTableModel dtm = new DefaultTableModel(colName, 0);
        for (int i = 0; i < real_num_rules; i++) {
            Rule rl = associationRuleList.get(i);
            Object[] rowData = {i + 1,
                rl.premise.toString(true),
                rl.consequence.toString(true),
                Presentation.round(rl.confidence, 3),
                rl.support};
            dtm.addRow(rowData);
        }
        jtblReults.setModel(dtm);
        CustomTableColumnModel.calcColumnWidths(jtblReults);
    }

    public void genFreqItemSet() throws Exception {
        large_1_ItemSet();
        while (!prevFreqList.isEmpty() && prevFreqList.size() >= 2) {
            candidateList = aprioriGen(prevFreqList);
            prevFreqList = new ArrayList<ItemSet>();    //clear to store new List
            if (!candidateList.isEmpty()) {
                for (int i = 0; i < candidateList.size(); i++) {
                    ItemSet itemSet = candidateList.get(i);
//                    for (int j=0; j< itemSet.item.size();j++){
//                        System.out.print(itemSet.item.get(j) + "\t");
//                    }
//                    System.out.println();
                    miningItemSet(itemSet);
                }
            }
        }
        System.out.println("Total num of freq: " + freqItemSetHash.size());
    }

    public void large_1_ItemSet() throws Exception {
        prevFreqList = new ArrayList<ItemSet>();
        for (int i = 0; i < fullTopo.size(); i++) {
            ArrayList<String> al = fullTopo.get(i).attribute;
            for (int j = 0; j < al.size(); j++) {
                ItemSet itemSet = new ItemSet(al.get(j));
                miningItemSet(itemSet);
            }
        }
    }

    public void miningItemSet(ItemSet itemSet) throws Exception {
        currentItemSet = itemSet;
        ArrayList<PartyID> pid = preparePartialTopo(itemSet);
        if (pid.size() == 1) {
            itemSet = belongOneParty(itemSet);
        } else {
            isThisItemSetDone = false;
            belongManyParties(pid);
            while (!isThisItemSetDone) {
                if (verboseMode) {
                    Thread.sleep(100);
                }
            }
//            itemSet.setSupport(wrapper.data.get(0).intValue());
            itemSet.setSupport(currentSupport.intValue());
        }
        addToFreqList(itemSet);
    }

    public void addToFreqList(ItemSet itemSet) {
        if (partition == 0) {//vertical
            if (((double) itemSet.support / (double) dataSize) >= minSup
                    && ((double) itemSet.support / (double) dataSize) <= maxSup) {
                freqItemSetHash.put(itemSet, itemSet.support);
                prevFreqList.add(itemSet);
                //            System.out.println("this item added with support: " + itemSet.support );
            }
        } else {//horizontal
            if (((double) itemSet.support / (double) totalDataSize.intValue()) >= minSup
                    && ((double) itemSet.support / (double) totalDataSize.intValue()) <= maxSup) {
                freqItemSetHash.put(itemSet, itemSet.support);
                prevFreqList.add(itemSet);
                //            System.out.println("this item added with support: " + itemSet.support );
            }
        }
    }

    /**
     * 
     * @param itemset large k-itemsets
     * @return large k+1-itemsets
     */
    public ArrayList<ItemSet> aprioriGen(ArrayList<ItemSet> preList) {
        ArrayList<ItemSet> C_k = new ArrayList<ItemSet>();
        for (int i = 0; i < preList.size() - 1; i++) {
            for (int j = i + 1; j < preList.size(); j++) {
                ItemSet its = ItemSet.joinItemSet(preList.get(i), preList.get(j));
                if (its == null) {
                    continue;
                }
                //prune
                ArrayList<ItemSet> subItemSet = new ArrayList<ItemSet>();
                for (int k = 0; k < its.getSize(); k++) {
                    ItemSet tmp = new ItemSet();
                    for (int h = 0; h < its.getSize(); h++) {
                        if (h != k) {
                            tmp.add(its.getItem().get(h));
                        }
                    }
                    subItemSet.add(tmp);
                }
                boolean addable = true;
                for (int h = 0; h < subItemSet.size(); h++) {
                    if (subItemSet.get(h).containedBy(preList) == -1) {
                        addable = false;
                        break;
                    }
                }
                if (addable) {
                    C_k.add(its);
                }
            }
        }
        return C_k;
    }

    /**
     * Given an itemset, return the partialTopo that contains this itemset
     * @param itemset
     * @return
     */
    public ArrayList<PartyID> preparePartialTopo(ItemSet itemset) {
        /** Add only the first party and the parties who have attributes in the <itemset> */
        ArrayList<PartyID> pIDList = new ArrayList<PartyID>();
        for (int i = 0; i < fullTopo.size(); i++) {
            PartyID tmpTopo = new PartyID();
            tmpTopo.setIP(fullTopo.get(i).getIP());
            tmpTopo.setPort(fullTopo.get(i).getPort());
            tmpTopo.setID(fullTopo.get(i).getID());
            ArrayList<String> tmpAtt = (ArrayList<String>) fullTopo.get(i).getAttribute();
            for (int j = 0; j < tmpAtt.size(); j++) {
                if (itemset.contains(tmpAtt.get(j))) {
                    tmpTopo.attribute.add(tmpAtt.get(j));
                }
            }
            //add into current topo if it is first party or parties contain at least one item in this itemset
            if (i == 0 || !tmpTopo.getAttribute().isEmpty()) {
                pIDList.add(tmpTopo);
            }
        }
        return pIDList;
    }

    public ItemSet belongOneParty(ItemSet itemset) throws Exception {
        ArrayList<String> its = itemset.getItem();
        ArrayList<BigInteger> values = new ArrayList<BigInteger>();
        for (int i = 0; i < its.size(); i++) {
            int index = itemList.indexOf(new ItemSet(its.get(i)));
            if (index == -1) {
                throw new Exception("The attribute '" + its.get(i) + "' doesn't belong to the assigned party");
            } else {
                if (values.isEmpty()) {
                    for (int t = 0; t < itemList.get(index).getValues().size(); t++) {
                        values.add(itemList.get(index).getValues().get(t));
                    }
                } else {
                    ArrayList<BigInteger> tmp = itemList.get(index).getValues();

                    for (int h = 0; h < values.size(); h++) {
                        values.set(h, values.get(h).multiply(tmp.get(h)));
                    }
                }
            }
        }
        ItemSet ret = new ItemSet(its);
        ret.setValues(values);
        ret.setSupport();
        return ret;
    }

    /**
     * This is called only by the first party.
     * @param itemset
     */
    public void belongManyParties(ArrayList<PartyID> pid) throws Exception {

        /** some fields */
        wrapper.partialTopo = pid;
        wrapper.tokenID = 0;//initial
        wrapper.p = elGamal.getP();
        wrapper.g = elGamal.getG();
        wrapper.y = elGamal.getY();

        wrapper.partition = partition;

        wrapper.attributeName = new ArrayList();

        if (true)//moi sua, dung cho doc encrypted data tu file
        {            
            //open encrypted 0, 1 file to read
            Random rand = new Random(System.currentTimeMillis());
            int lineNO = rand.nextInt(1000);
            BigInteger encryptedVal = null;

            if (!pid.get(0).attribute.isEmpty()) {
            //Create an Itemset and get its SP.
            ItemSet is = new ItemSet(pid.get(0).attribute);
            is = belongOneParty(is);

            if (partition == 0){
            ArrayList<BigInteger> vals = is.getValues();
            for (int i=0; i<vals.size();i++){
            lineNO = rand.nextInt(998)+1;
            if (vals.get(i).compareTo(BigInteger.ZERO) == 0){
            encryptedVal = new BigInteger(readLine("..\\zero.txt", lineNO));
            }else{
            encryptedVal = new BigInteger(readLine("..\\one.txt", lineNO));
            }
            wrapper.data.add(null);
            //TO DO
            //wrapper.data.add(encryptedVal);
            }

            }
            else {
            BigInteger tmp = new BigInteger(Integer.toString(is.getSupport()));
            //TO DO
            //wrapper.data = new ArrayList<BigInteger>();
            wrapper.data.add(elGamal.encrypt(tmp));
            tmp = new BigInteger(Integer.toString(dataSize));
            wrapper.data.add(elGamal.encrypt(tmp));
            }
            } else {
            wrapper.data = null;
            }            
            
        }

        if (false) //phan cu~, encrypted data truc tiep
        /************************************************************************/
        {
            /**
             * Add data to <data> field if have
             * for the first party
             */
            if (!pid.get(0).attribute.isEmpty()) {
                //Create an Itemset and get its SP.
                ItemSet is = new ItemSet(pid.get(0).attribute);
                is = belongOneParty(is);

                //            long startEnc = System.currentTimeMillis();
                if (partition == 0) {//vertical

                    //Nen co If here de xem neu ton tai cache thi ko encrypt nua
                    //Neu ko co thi lam binh thuong nhu duoi

                    wrapper.data = elGamal.encrypt(is.getValues());

                    //*********************************************
                    // Get attribute                    
                    wrapper.attributeName = is.item;
//                    //and save to file
//                    cacheFile(wrapper);
//                    //*********************************************
//
//                    updateCacheTable(wrapper);

//                    //update cache-table
//                    if (wrapper.cacheTable.get(is) == null){//if itemset is not in the map
//                        ArrayList<Integer> al = new ArrayList<Integer>();
//                        al.add(wrapper.tokenID);
//                        wrapper.cacheTable.put(is, al);
//                    }else{//if in the map
//                        ArrayList<Integer> alist =(ArrayList)wrapper.cacheTable.get(is);
//                        if (!alist.contains(wrapper.tokenID)){
//                            alist.add(wrapper.tokenID);
//                            wrapper.cacheTable.put(is, alist);
//                        }
//                    }

                    //And save cache-table to file
//                    saveCacheTable(wrapper);

                } else {//horizontal
                    BigInteger tmp = new BigInteger(Integer.toString(is.getSupport()));
                    wrapper.data = new ArrayList<ElGamalCipherText>();
                    wrapper.data.add(elGamal.encrypt(tmp));
                    tmp = new BigInteger(Integer.toString(dataSize));
                    wrapper.data.add(elGamal.encrypt(tmp));

                    // Them phan save to file here

                    ///
                }
                //            long endEnc = System.currentTimeMillis();
                //
                //            encryptionTime += endEnc - startEnc;

                //            System.out.println("Time: " + encryptionTime);
            } else {
                wrapper.data = null;
            }
        }

        this.setWrapper(wrapper);

        /** send to the next party */
        send();
    }

    @Override
    public void process() throws Exception {
        receive();

        //process data
        doSSP();

        //tokenID == 0 means finishing this itemset.
        if (!isThisItemSetDone) {
            send();
        }
    }

    public void receive() throws Exception {
        objectInputStream = new ObjectInputStream(connection.getInputStream());
        // Receive config and data
        this.setWrapper((AssoWrapper) objectInputStream.readObject());
        objectInputStream.close();

//        System.out.println("Receive: " + wrapper.tokenID);


        //write encrypted data to file for cache        
        if (wrapper.data != null) {
            if (wrapper.tokenID != 0) {
                cacheFile(wrapper);
                //update the cache-table
                updateCacheTable(wrapper);
            }
        }
        //Save cache to file
        saveCacheTable(wrapper);
    }

    /**
     * 
     * @param wrapper
     */
    public void send() {

        //cache file and update cache-table
        if (wrapper.data != null && wrapper.data.size() != 1) {
            //and save to file
            cacheFile(wrapper);
            //*********************************************
            updateCacheTable(wrapper);
        }
        updateStatus();
        //Get next party tokenID
        int nextTokenID = (wrapper.tokenID + 1) % wrapper.partialTopo.size();
        //check if next party is active or not
        //if activate then send
        wrapper.tokenID = (wrapper.tokenID + 1) % wrapper.partialTopo.size();
        PartyID topo = wrapper.partialTopo.get(wrapper.tokenID);
//            System.out.println("Size: " + wrapper.partialTopo.size());
//            System.out.println("Send To: " + wrapper.tokenID);

        try {
            wrapper.cacheRequest = false;
            clientSK = new Socket(topo.IP, topo.port);
//            clientSK.setSoTimeout(10000);
            objectOutputStream = new ObjectOutputStream(clientSK.getOutputStream());
            objectOutputStream.writeObject(wrapper);
            objectOutputStream.flush();
            objectOutputStream.close();
            clientSK.close();
            /*
            System.out.println("Next tokenID      : " + (wrapper.partialTopo.get(nextTokenID)).ID);
            System.out.println("Next Party Status(0:down, 1:on): " + wrapper.fullTopoForCache.get((wrapper.partialTopo.get(nextTokenID)).ID).on_off);
            if (wrapper.fullTopoForCache.get((wrapper.partialTopo.get(nextTokenID)).ID).on_off == 0) {//the next party is inactive
            System.out.println("Party "
            + wrapper.partialTopo.get(nextTokenID).ID
            + " is down.");
            //looking into the cache-table
            ItemSet iSet = new ItemSet(wrapper.partialTopo.get(nextTokenID).attribute);
            ArrayList<Integer> al;
            if ((al=wrapper.cacheTable.get(iSet))!=null){
            System.out.print("Found a cache for itemset: " + iSet.toString(false)
            + " at party: ");
            for (int i=0; i<al.size();i++){
            System.out.print(al.get(i) + ", ");
            }
            System.out.print("\n");
            }else{
            System.out.print("A cache for itemset: " + iSet.toString(false)
            + " was NOT found.\n");
            }
            //read the cache data if it is the same site
            //or request from the corresponding site
            //send to the right site
            }
             */
        } catch (Exception ex) {
            //if cant connect to next party
            System.out.println("Next tokenID      : " + (wrapper.partialTopo.get(nextTokenID)).ID);
            System.out.println("Party " + wrapper.partialTopo.get(nextTokenID).ID + " is down.");
            //looking into the cache-table
            ItemSet iSet = new ItemSet(wrapper.partialTopo.get(nextTokenID).attribute);
            ArrayList<Integer> al;
            if ((al = wrapper.cacheTable.get(iSet)) != null) {
                try {
                    System.out.print("Found a cache for itemset: " + iSet.toString(false) + " at party: ");
                    for (int i = 0; i < al.size(); i++) {
                        System.out.print(al.get(i) + ", ");
                    }
                    System.out.print("\n");


                    //get that party
                    PartyID pid = null;
                    for (int j = 0; j < fullTopo.size(); j++) {
                        if (al.get(0) == fullTopo.get(j).ID) {
                            pid = fullTopo.get(j);
                            break;
                        }
                    }
                    //and connect to it
                    if (pid != null) {
                        System.out.print("Connect to: " + pid.ID);
                        wrapper.cacheRequest = true;
                        //Should inform target party that connect for cache.
                        clientSK = new Socket(pid.IP, pid.port);

                        objectOutputStream = new ObjectOutputStream(clientSK.getOutputStream());
                        objectOutputStream.writeObject(wrapper);
                        objectOutputStream.flush();
                        objectOutputStream.close();
                        clientSK.close();
                    }
                } catch (UnknownHostException ex1) {
                    Logger.getLogger(AssociationParty.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(AssociationParty.class.getName()).log(Level.SEVERE, null, ex1);
                }

            } else {
                System.out.print("A cache for itemset: " + iSet.toString(false) + " was NOT found.\n");
                currentSupport = new BigInteger("0");
                isThisItemSetDone = true;
            }
            //read the cache data if it is the same site
            //or request from the corresponding site
            //send to the right site

        }

        /*

        System.out.println("Next tokenID      : " + (wrapper.partialTopo.get(nextTokenID)).ID);
        System.out.println("Next Party Status(0:down, 1:on): " + wrapper.fullTopoForCache.get((wrapper.partialTopo.get(nextTokenID)).ID).on_off);
        if (wrapper.fullTopoForCache.get((wrapper.partialTopo.get(nextTokenID)).ID).on_off == 0) {//the next party is inactive
        System.out.println("Party "
        + wrapper.partialTopo.get(nextTokenID).ID
        + " is down.");
        //looking into the cache-table
        ItemSet iSet = new ItemSet(wrapper.partialTopo.get(nextTokenID).attribute);
        ArrayList<Integer> al;
        if ((al=wrapper.cacheTable.get(iSet))!=null){
        System.out.print("Found a cache for itemset: " + iSet.toString(false)
        + " at party: ");
        for (int i=0; i<al.size();i++){
        System.out.print(al.get(i) + ", ");
        }
        System.out.print("\n");
        }else{
        System.out.print("A cache for itemset: " + iSet.toString(false)
        + " was NOT found.\n");
        }

        //read the cache data if it is the same site


        //or request from the corresponding site


        //send to the right site


        }

         */
    }

    public void doSSP() throws Exception {
        if (wrapper.cacheRequest = false) {

            if (partition == 0) {//vertical
                //            System.out.println("Do SSP: " + wrapper.tokenID);
                if (wrapper.tokenID == 0) {
                    System.out.println(wrapper.data.get(0).toString());
                    //Decrypt to get support
                    if (wrapper.data.size() == 1) {
                        currentSupport = elGamal.decrypt(wrapper.data.get(0));
                    } else {
                        BigInteger[] tmp = elGamal.decrypt(wrapper.data);
                        currentSupport = new BigInteger("0");
                        for (int i = 0; i < tmp.length; i++) {
                            currentSupport = currentSupport.add(tmp[i]);
                        }
                    }

                    //                currentSupport = elGamal.decrypt(wrapper.data.get(0));

                    System.out.println((new ItemSet(wrapper.attributeName)).toString(false)
                            + " Support: " + currentSupport);
                    isThisItemSetDone = true;
                } else {
                    //create an ItemSet for this party
                    ItemSet itemset = new ItemSet(wrapper.partialTopo.get(wrapper.tokenID).attribute);
                    itemset = belongOneParty(itemset);
                    elGamal = new ElGamal(wrapper.p, wrapper.g, wrapper.y);
                    ScalarProductElGamal spe = new ScalarProductElGamal();
                    if (wrapper.tokenID + 1 == wrapper.partialTopo.size()) {
                        //compute the SSP
                        wrapper.data = spe.computeSSP(elGamal, itemset.getValues(), wrapper.data, true);
                    } else {
                        wrapper.data = spe.computeSSP(elGamal, itemset.getValues(), wrapper.data, false);
                    }


                    //Add all items to
                    //*********************************************
                    // Get attribute
                    //wrapper.attributeName = new ArrayList();
                    for (int i = 0; i < itemset.item.size(); i++) {
                        wrapper.attributeName.add(itemset.item.get(i));
                    }
                    //*********************************************
                }
            } else {//horizontal
                if (wrapper.tokenID == 0) {
                    //Decrypt to get support
                    currentSupport = elGamal.decrypt(wrapper.data.get(0));
                    totalDataSize = elGamal.decrypt(wrapper.data.get(1));
                    isThisItemSetDone = true;
                } else {
                    //creat an ItemSet for this party
                    ItemSet itemset = new ItemSet(wrapper.partialTopo.get(wrapper.tokenID).attribute);
                    itemset = belongOneParty(itemset);
                    elGamal = new ElGamal(wrapper.p, wrapper.g, wrapper.y);
                    //compute the temp results
                    wrapper.data = computeData(elGamal, itemset.getSupport(), dataSize, wrapper.data);
                }
            }

        } else { //cache request
            //Reading data from Cache
        }
    }

    /**
     * this is for Horizontal Data only
     * @param elGamal
     * @param support
     * @param dataSize
     * @param data
     * @return
     */
    public ArrayList<ElGamalCipherText> computeData(ElGamal elGamal, int support,
            int dataSize, ArrayList<ElGamalCipherText> data) {
        BigInteger sup = new BigInteger(Integer.toString(support));
        BigInteger size = new BigInteger(Integer.toString(dataSize));
        ElGamalCipherText supEl = elGamal.encrypt(sup);
        ElGamalCipherText sizeEl = elGamal.encrypt(size);
        supEl = supEl.multiply(data.get(0));
        sizeEl = sizeEl.multiply(data.get(1));
        ArrayList<ElGamalCipherText> ret = new ArrayList<ElGamalCipherText>();
        ret.add(supEl);
        ret.add(sizeEl);
        return ret;
    }

    public String readLine(String filename, int lineNo) throws Exception {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(filename));
        String line = null;
        for (int i = 0; i < lineNo; i++) {
            line = br.readLine();
        }
        br.close();
        return line;
    }

    public void cacheFile(AssoWrapper aWrapper) {
        ArrayList<ElGamalCipherText> data;
        data = aWrapper.data;

        ArrayList<String> att = aWrapper.attributeName;
        String fileName = "D:\\Data\\Cache\\" + aWrapper.partialTopo.get(aWrapper.tokenID).ID + "\\";

        ItemSet items = new ItemSet(att);
        System.out.println(items.toString(false) + " cache at:  "
                + aWrapper.partialTopo.get(aWrapper.tokenID).ID);
        for (int i = 0; i < att.size() - 1; i++) {
            fileName += att.get(i) + "&";
        }

        fileName += att.get(att.size() - 1) + ".txt";
        try {
            FileWriter fWriter = new FileWriter(fileName, false);
            for (int i = 0; i < data.size(); i++) {
                fWriter.write(data.get(i).toString() + "\n");
            }
            fWriter.flush();
            fWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(AssociationParty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readCacheFile(ItemSet is, AssoWrapper aWrapper) {
        ArrayList<ElGamalCipherText> data;
        data = aWrapper.data;

        ArrayList<String> att = aWrapper.attributeName;
        String fileName = "D:\\Data\\Cache\\" + aWrapper.partialTopo.get(aWrapper.tokenID).ID + "\\";

        ItemSet items = new ItemSet(att);
        System.out.println(items.toString(false) + " cache at:  "
                + aWrapper.partialTopo.get(aWrapper.tokenID).ID);
        for (int i = 0; i < att.size() - 1; i++) {
            fileName += att.get(i) + "&";
        }

        fileName += att.get(att.size() - 1) + ".txt";
        try {
            FileWriter fWriter = new FileWriter(fileName, false);
            for (int i = 0; i < data.size(); i++) {
                fWriter.write(data.get(i).toString() + "\n");
            }
            fWriter.flush();
            fWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(AssociationParty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCacheTable(AssoWrapper aWrapper) {
        if (!aWrapper.cacheTable.isEmpty()) {

            String fileName = "D:\\Data\\Cache\\" + aWrapper.partialTopo.get(aWrapper.tokenID).ID
                    + "\\cache-table.txt";
            try {
                FileWriter fWriter = new FileWriter(fileName, false);

                Iterator mapEntries = aWrapper.cacheTable.entrySet().iterator();
                while (mapEntries.hasNext()) {
                    Map.Entry entry = (Map.Entry) mapEntries.next();
                    ItemSet is = (ItemSet) entry.getKey();
                    ArrayList<Integer> IDList = (ArrayList) entry.getValue();

                    fWriter.write(is.toString(false) + "::");
                    for (int i = 0; i < IDList.size(); i++) {
                        fWriter.write(IDList.get(i) + ",");
                    }
                    fWriter.write("\n");

                }
                fWriter.flush();
                fWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(AssociationParty.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateCacheTable(AssoWrapper aWrapper) {
        ItemSet is = new ItemSet(aWrapper.attributeName);
        //update cache-table
        if (wrapper.cacheTable.get(is) == null) {//if itemset is not in the map
            ArrayList<Integer> al = new ArrayList<Integer>();
            al.add(aWrapper.partialTopo.get(aWrapper.tokenID).ID);
            wrapper.cacheTable.put(is, al);
        } else {//if in the map
            ArrayList<Integer> alist = (ArrayList) wrapper.cacheTable.get(is);
            if (!alist.contains(aWrapper.partialTopo.get(aWrapper.tokenID).ID)) {
                alist.add(aWrapper.partialTopo.get(aWrapper.tokenID).ID);
                wrapper.cacheTable.put(is, alist);
            }
        }
    }

    //update the (in)active status
    public void updateStatus() {

        //update party status: (in)active
        for (int i = 0; i < wrapper.fullTopoForCache.size(); i++) {
            if (wrapper.partialTopo.get(wrapper.tokenID).ID
                    == wrapper.fullTopoForCache.get(i).ID) {
                System.out.println("Status of " + i + ":" + this.on_off);
                wrapper.fullTopoForCache.get(i).on_off = this.on_off;
                break;
            }
        }
    }

    /**
     * 
     * @return HashMap: ItemSet is key, a list of parties is value.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public HashMap<ItemSet, ArrayList> readCacheTable() throws FileNotFoundException, IOException {
        HashMap<ItemSet, ArrayList> cacheT = new HashMap();

        String fileName = "D:\\Data\\Cache\\"
                + wrapper.partialTopo.get(wrapper.tokenID).ID + "\\cache-table.txt";

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine().trim()) != null) {
            StringTokenizer sTokenizer = new StringTokenizer(line, "::");

            String itemset = sTokenizer.nextToken().trim();
            StringTokenizer sTokenizer1 = new StringTokenizer(itemset, ",");
            ArrayList<String> is = new ArrayList<String>();
            while (sTokenizer1.hasMoreTokens()) {
                is.add(sTokenizer1.nextToken().trim());
            }
            ItemSet is2 = new ItemSet(is);

            String al = sTokenizer.nextToken().trim();
            StringTokenizer sTokenizer2 = new StringTokenizer(al, ",");
            ArrayList<Integer> al2 = new ArrayList<Integer>();
            while (sTokenizer2.hasMoreTokens()) {
                al2.add(Integer.parseInt(sTokenizer2.nextToken().trim()));
            }

            cacheT.put(is2, al2);
        }

        return cacheT;
    }

    /**
     * Check if cache for an itemset is available here or not
     * @param is
     */
    public boolean isCacheAvailable(ItemSet is) {
        return (wrapper.cacheTable.containsKey(is));

    }
}
