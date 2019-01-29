package ppdm.core;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ppdm.algo.association.PartyID;
import ppdm.core.crypto.ElGamal;

/**
 *
 * @author Tran Huy Duc
 *
 * @date Sep 8, 2008
 */
public abstract class Party extends PartyID {

    public ServerSocket         serverSK = null;
    public Socket               clientSK = null;
    public Socket               connection = null;
    public ObjectOutputStream   objectOutputStream;
    public ObjectInputStream    objectInputStream;
    public boolean              stopServer = true;
    public boolean              start = false;
    public ArrayList<PartyID>   fullTopo;
//    public Paillier             pail;
    public ElGamal              elGamal;
    public int                  dataSize;
    public String               status = "Welcome!";
    public boolean              firstParty;
    public boolean              autoStopServer;
    public boolean              completedCurrentProject;
    public int                  partition;
    public boolean              verboseMode;


    public void startServer(int port) throws Exception {
        serverSK = new ServerSocket(port);
//        serverSK.setSoTimeout(10000);
        stopServer = false;
        Thread t = new Thread(){
            public void run(){                
                while (!stopServer) {
                    try {
                        connection = serverSK.accept();
//                        System.out.println(connection);
                        process();
                        connection.close();
                    } catch (Exception ex) {
                        Logger.getLogger(Party.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        };
        t.start();
        
    }

    public void stopServer() throws IOException {

        stopServer = true;
        if (connection != null) {
            connection.close();
        }
        if (serverSK != null) {
            serverSK.close();
        }
    }

    abstract public void process() throws Exception;
    abstract public void receive() throws Exception;
    abstract public void send()     throws Exception;
}


