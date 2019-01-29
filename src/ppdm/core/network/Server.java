package ppdm.core.network;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import ppdm.core.Config;
import ppdm.block.ScalarProduct;
import ppdm.test.TestObject;

/**
 *
 * @author ductran
 *
 * @date Aug 29, 2008
 */
public class Server {    
    ServerSocket serverSK = null;
    Socket connection;
    InputStream inStream;
    OutputStream outStream;
    BufferedReader br;
    BufferedWriter bw;    //config Data
    public int senderID;
    public int direction;
    public int myID;
    public String configInfo;
    String data;
    public String projectID;
    public int num_party;
    public String topology = "";
    public int protocolID;
    public int vectorSize;
    public String databaseName;
    public BigInteger n;
    public BigInteger g;
    public int totalReceived = 0;
    public BigInteger[] receivedData;    
    
    ObjectOutputStream objectOutputStream;
    ObjectInputStream   objectInputStream;
    TestObject          testObject;

    public Server(int port) {
        try {

            serverSK = new ServerSocket(port);
            //wait for connection
            System.out.println("Listening...");
            connection = serverSK.accept();
            System.out.println(connection);
            System.out.println("Connected");
            
            receiveData();
            doSPP();
            close();

        } catch (Exception e) {
        }
    }

    public void close() {
        try {
//            bw.close();
//            br.close();
            serverSK.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Server() {
        this(6868);
    }

    public static void main(String[] args) {
        Server svr = new Server();
    }

    public void okieman() {
        System.out.println("Server portion: ");
    }

    public void receiveData() {        
        try {
            objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
            objectInputStream  = new ObjectInputStream(connection.getInputStream());

            String tmpData;
            while (true) {
                try {
                    testObject = (TestObject)objectInputStream.readObject();
                    tmpData = testObject.getSend();
                    System.out.println("Double:" + testObject.getRate());
                    if (tmpData.contains("@config")) {
                        processConfigInfo(tmpData);
                    } else if (tmpData.contains("@data")) {
                        processData(tmpData);
                    } else if (tmpData.contains("@end")) {
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


    private void processConfigInfo(String tmpData) {
        StringTokenizer tokenizer = new StringTokenizer(tmpData, Config.DELIMITER);
        tokenizer.nextToken(); //@config
        projectID = tokenizer.nextToken().trim();
        senderID = Integer.parseInt(tokenizer.nextToken().trim());
        direction = Integer.parseInt(tokenizer.nextToken().trim());//0: forward, 1: back
        num_party = Integer.parseInt(tokenizer.nextToken().trim());

        for (int i = 0; i < num_party; i++) {
            topology += tokenizer.nextToken() + " ";//Party ID
            topology += tokenizer.nextToken() + " ";//IP
            topology += tokenizer.nextToken() + " ";//port
        }

        protocolID = Integer.parseInt(tokenizer.nextToken().trim());
        vectorSize = Integer.parseInt(tokenizer.nextToken().trim());
        databaseName = tokenizer.nextToken().trim();

        //public key
        n = new BigInteger(tokenizer.nextToken().trim());
        g = new BigInteger(tokenizer.nextToken().trim());
        System.out.println("OK CONFIG!");
    }

    private void processData(String tmpData) {
        StringTokenizer tokenizer = new StringTokenizer(tmpData, Config.DELIMITER);
        tokenizer.nextToken(); //@data
        receivedData = new BigInteger[vectorSize];
        while (tokenizer.hasMoreTokens() && totalReceived < vectorSize) {
            receivedData[totalReceived++] = new BigInteger(tokenizer.nextToken().trim());
        }
        System.out.println("OK DATA!");
    }
    
    public void doSPP() {
        System.out.println("Do SPP!");
        try {
            //bw fai khac, fai huong den party moi
            ScalarProduct spp = new ScalarProduct();
            spp.calcualte(receivedData, n, g);
            TestObject to = new TestObject();
            to.setSend("BeginData " + spp.m_encSSP.toString() + " EndData\n");
            to.setRate(123456.789);
            objectOutputStream.writeObject(to);
            objectOutputStream.flush();
            System.out.println("Server portion: " + spp.m_portion);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
