package ppdm.block;

import java.io.*;
import java.math.*;
import java.net.*;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import ppdm.algo.association.PartyID;
import ppdm.core.Config;
import ppdm.core.Party;
import ppdm.core.crypto.Paillier;
import ppdm.core.network.*;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 8, 2008
 */
public class SSPParty extends Party {

    public BufferedReader br;
    public BufferedWriter bw;
    public InputStream inStream;
    public OutputStream outStream;
    public int senderID;
    public int receiverID;
    public String receiverIP;
    public int receiverPort;
    public int num_party;
    public String[][] topology;
    public int myID = 0;
    public int direction;
    public String projectID;
    public int protocolID;
    public int dataSize;
    public String databaseName;
    public BigInteger[] receivedData;
    public BigInteger[] privateData;
    public BigInteger[] mySendData;
    public BigInteger myPortion;
    public Paillier pail;

    public SSPParty() {
        super();
        status = "SSPParty created!";
        System.out.println("SSPParty created!");
    }

    public SSPParty(int myID) {
        this.myID = myID;
        System.out.println("SSPParty created!");
    }

    public void process() {
        receive();
        doSSP();
        if (myID != 1 || direction != 1) {
            connect();
        } else {
            status = "Finish project: " + projectID + ". Server listening...";
            System.out.println("Finish project: " + projectID + ". Server listening...");
        }        
    }

    public void connect() {
        if (start) {
            status = "init...";
            System.out.println("init...");
            initialize();
            start = false;
        }
        status = "Finish @ " + myID + ". Now sending to " + receiverID;
        System.out.println("Finish @ " + myID + ". Now sending to " + receiverID);

        try {
            System.out.println("Starting connect to: " + receiverID + " @ " + receiverIP + " port: " + receiverPort);
            clientSK = new Socket(receiverIP, receiverPort);
            outStream = clientSK.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(outStream));

            System.out.println("preparing data ...");
            String sendData = Config.CONFIG_SIGNAL + " ";
            sendData += projectID + Config.SPACE;
            sendData += myID + Config.SPACE;
            sendData += direction + Config.SPACE;
            sendData += num_party + Config.SPACE;
            for (int i = 0; i < num_party; i++) {
                sendData += topology[i][0] + Config.SPACE + topology[i][1] + Config.SPACE + topology[i][2] + Config.SPACE;
            }
            sendData += protocolID + Config.SPACE;
            sendData += mySendData.length + Config.SPACE;
            sendData += databaseName + Config.SPACE;
            sendData += pail.getN() + Config.SPACE + pail.getG() + Config.SPACE + Config.ENTER;
            bw.write(sendData);
            bw.flush();

            sendData = Config.DATA_SIGNAL + " ";
            for (int i = 0; i < mySendData.length; i++) {
                sendData += mySendData[i].toString() + Config.SPACE;
            }
            sendData += Config.ENTER;
            bw.write(sendData);
            bw.flush();

            sendData = Config.END_SIGNAL + " " + Config.ENTER;
            bw.write(sendData);
            bw.flush();

            bw.close();
            clientSK.close();

            status = "Finish sending...";

        } catch (UnknownHostException ex) {
            Logger.getLogger(SSPParty.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SSPParty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * not use shared compute --> ring topology.
     */
    public BigInteger computeSSP(PartyID topo){
        
        return null;
    }

    public void receive() {
        try {
            inStream = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(inStream));

            String tmpData;
            while (true) {
                try {
                    tmpData = br.readLine();
                    if (tmpData.contains(Config.CONFIG_SIGNAL)) {
                        receiveConfigInfo(tmpData);
                    } else if (tmpData.contains(Config.DATA_SIGNAL)) {
                        receiveData(tmpData);
                    } else if (tmpData.contains(Config.END_SIGNAL)) {
                        break;
                    }
                } catch (EOFException e) {
                    System.out.println("message EOF received\n");
                } catch (IOException except) {
                    System.out.println("IO Exception raised");
                    except.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void receiveConfigInfo(String tmpData) {
        StringTokenizer tokenizer = new StringTokenizer(tmpData, Config.DELIMITER);
        tokenizer.nextToken(); //@config
        projectID = tokenizer.nextToken().trim();
        senderID = Integer.parseInt(tokenizer.nextToken().trim());
        direction = Integer.parseInt(tokenizer.nextToken().trim());//0: forward, 1: back
        num_party = Integer.parseInt(tokenizer.nextToken().trim());

        topology = new String[num_party][3];
        for (int i = 0; i < num_party; i++) {
            topology[i][0] = tokenizer.nextToken() + Config.SPACE;//Party ID
            topology[i][1] = tokenizer.nextToken() + Config.SPACE;//IP
            topology[i][2] = tokenizer.nextToken() + Config.SPACE;//port
        }

        protocolID = Integer.parseInt(tokenizer.nextToken().trim());
        if (direction == 0)
            dataSize = Integer.parseInt(tokenizer.nextToken().trim());
        else
            tokenizer.nextToken();
            
        databaseName = tokenizer.nextToken().trim();

        //public key
        BigInteger tmp_n = new BigInteger(tokenizer.nextToken().trim());
        BigInteger tmp_g = new BigInteger(tokenizer.nextToken().trim());        
       
        if (direction != 1 || senderID != 2){//not the first w/ return
            pail = new Paillier(tmp_n, tmp_g);
        }
        
        configMe();
        status = "Finish reveiving config data!";
        System.out.println("OK CONFIG!");
    }

    //TODO
    public void configMe() {
        //I'm last one
        if (direction == 0 && senderID == num_party - 1) {
            direction = 1; //back
            myID = num_party;
            receiverID = myID - 1;
        } else if (direction == 0) {
            myID = senderID + 1;
            receiverID = myID + 1;
        } else if (direction == 1 && senderID == 2) {
            myID = 1;
            //do nothing
        } else if (direction == 1) {
            myID = senderID - 1;
            receiverID = myID - 1;
        } else {
            System.out.println("Some thing wrong in configMe!");
            System.exit(1);
        }

        receiverIP = topology[receiverID - 1][1].trim();
        receiverPort = Integer.parseInt(topology[receiverID - 1][2].trim());
        System.out.println("Finish configMe!!");
    }

    public void initialize() {
        if (!start || myID != 1) return;
        Random rd = new Random();
        projectID = Integer.toString(rd.nextInt(999999));
        myID = 1;
        direction = 0;
//        num_party = 3;
//        topology = new String[][]{{"1", "127.0.0.1", "6867"},
//                    {"2", "127.0.0.1", "6868"},
//                    {"3", "155.69.148.186", "6869"}
//                }; // TODO
        protocolID = 1;
//        dataSize = VECTOR_SIZE;
//        databaseName = "auto.txt";

        receiverID = 2;
        receiverIP = topology[receiverID - 1][1].trim();
        receiverPort = Integer.parseInt(topology[receiverID - 1][2].trim());

        pail = new Paillier();//first party choose PK
        
        mySendData = new BigInteger[privateData.length];
        for (int i = 0; i < privateData.length; i++){
            mySendData[i] = pail.encrypt(privateData[i]);
        }
        
        start = false;

    }

    public void receiveData(String tmpData) {
        StringTokenizer tokenizer = new StringTokenizer(tmpData, Config.DELIMITER);
        tokenizer.nextToken(); //@data
        //receivedData: [dataSize values] or only 1 value (send back).
        BigInteger[] tmp = new BigInteger[dataSize];
      
        int total = 0;
        while (tokenizer.hasMoreTokens() &&  (total < dataSize)){
            tmp[total++] = new BigInteger(tokenizer.nextToken().trim());
        }
        if (total > 0){
            receivedData = new BigInteger[total];
            for (int i = 0; i < total; i++) {
                receivedData[i] = tmp[i];
            }            
        }
        
        System.out.println("OK DATA!");
        status = "Finish receiving data!";
    }

    public void doSSP() {
        status = "Doing main part of SPP...";
        
        //Case 1: direction = 0; myID < num_party
        //get my Data --> calculate --> send to next party
        mySendData = new BigInteger[receivedData.length];
        if (direction == 0) {
            System.out.println("case 1...");            
            for (int i = 0; i < receivedData.length; i++) {
                mySendData[i] = receivedData[i].modPow(privateData[i], pail.getN().pow(2));
            }
        }
        
        //Case 2: direction = 0; myID == num_party
        //get my Data --> calculate SSP --> choose my portion --> encrypt --> send back
        if (direction == 1 && myID == num_party) {
            System.out.println("case 2...");
            BigInteger tmp = new BigInteger("1");
            for (int i = 0; i < receivedData.length; i++) {
                tmp = (tmp.multiply(receivedData[i].modPow(privateData[i], pail.getN().pow(2)))).mod(pail.getN().pow(2));
            }

            myPortion = getMyPortion(pail.n);
            System.out.println("my portion: " + myPortion);            
            
//            Paillier pail2 = new Paillier(m_n, m_g);
            mySendData = new BigInteger[1];
            mySendData[0] = (tmp.multiply(pail.encrypt((pail.getN().pow(2)).subtract(myPortion)))).mod(pail.getN().pow(2));
            status = "Finish my part!";
        }

        //case 3: direction = 1; myID >= 2
        //choose my portion --> encrypt --> send back        
        if (direction == 1 && myID < num_party && myID > 1) {
            System.out.println("case 3...");
            myPortion = getMyPortion(pail.n);
            System.out.println("my portion: " + myPortion);        
            
//            Paillier pail3 = new Paillier(m_n, m_g);
            mySendData = new BigInteger[1];
            mySendData[0] = (receivedData[0].multiply(pail.encrypt((pail.getN().pow(2)).subtract(myPortion)))).mod(pail.getN().pow(2));
            status = "Finish my part!";

        }

        //case 4: direction = 1; senderID = 2
        //decrypt to get my portion
        if (direction == 1 && myID == 1) {
            System.out.println("case 4...");
            myPortion = pail.decrypt(receivedData[0]);
            System.out.println("my portion: " + myPortion);
            status = "Finish main part of SSP";
        //TODO
        }        
        
        System.out.println("Finish do SPP!");
    }

    public BigInteger getMyPortion(int n) {
        Random rd = new Random();
        return new BigInteger(Integer.toString(rd.nextInt(n)));
    }
    
    public BigInteger getMyPortion(BigInteger big) {
        Random rd = new Random();
        return new BigInteger(Integer.toString(rd.nextInt(big.intValue())));
    }
    

    public BigInteger getMyPortion() {
        BigInteger sum = new BigInteger("0");
        for (int i = 0; i < privateData.length; i++) {
            sum = sum.add(privateData[i]);
        }
        Random rd = new Random();
        return new BigInteger(Integer.toString(rd.nextInt(sum.intValue())));
    }

    @Override
    public void send() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
