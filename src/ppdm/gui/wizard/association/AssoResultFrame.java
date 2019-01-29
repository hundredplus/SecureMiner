/*
 * AssoResultFrame.java
 *
 * Created on November 10, 2008, 9:42 AM
 */

package ppdm.gui.wizard.association;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import ppdm.algo.association.AssociationParty;
import ppdm.algo.association.Rule;
import ppdm.core.ErrorController;
import ppdm.core.math.Presentation;

/**
 *
 * @author  Tran Huy Duc
 */
public class AssoResultFrame extends javax.swing.JInternalFrame {
    AssociationParty assoParty;
    private volatile Thread miningThread;
    boolean complete;
    String      oldLine="";    

    boolean firstClick;
    /** Creates new form AssoResultFrame */
    public AssoResultFrame(AssociationParty party) {
        this.assoParty = party;     
        initComponents();
        Thread t = new Thread(){
          public void run() {              
              while (!assoParty.completedCurrentProject){
                    try {
                        Thread.sleep(100);
                        changeUI();
                    } catch (Exception ex) {
                        //Logger.getLogger(AssoResultFrame.class.getName()).log(Level.SEVERE, null, ex);
                        //throw new Exception(ex.toString());
                    }
                  
              }
          }  
        };
        t.start();
    }
    
    public void changeUI(){
        if (assoParty.currentItemSet != null && !assoParty.currentItemSet.toString(false).equalsIgnoreCase(oldLine))
            jtxaLog.append(assoParty.currentItemSet.toString(false) + "\n");
//        if (assoParty.connection != null)
//            jtxaLog.append(assoParty.connection.toString() + "\n");
        oldLine = assoParty.currentItemSet.toString(false);
        jlblStatus.setText(assoParty.status);
    }
    
    /** Creates new form AssoResultFrame */
    public AssoResultFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxaLog = new javax.swing.JTextArea();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblReults = new javax.swing.JTable();
        jbtnStart = new javax.swing.JButton();
        jbtnStop = new javax.swing.JButton();
        jbtnDeactivate = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jlblStatus = new javax.swing.JLabel();
        jpbarTotalProgress = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("New Project");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 153, 255)));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Candidate Itemsets"));

        jtxaLog.setColumns(20);
        jtxaLog.setRows(5);
        jScrollPane1.setViewportView(jtxaLog);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Rules"));

        jScrollPane2.setAutoscrolls(true);

        jtblReults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rule", "Premise (Support)", "Consequence (Support)", "Confidence", "Support"
            }
        ));
        jScrollPane2.setViewportView(jtblReults);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
        );

        jbtnStart.setText("Start");
        jbtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnStartActionPerformed(evt);
            }
        });

        jbtnStop.setText("Stop");
        jbtnStop.setEnabled(false);
        jbtnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnStopActionPerformed(evt);
            }
        });

        jbtnDeactivate.setText("Deactivate");
        jbtnDeactivate.setEnabled(false);
        jbtnDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeactivateActionPerformed(evt);
            }
        });

        jbtnSave.setText("Save Results");
        jbtnSave.setEnabled(false);
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jbtnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnStop, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jbtnDeactivate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap())
        );

        jlblStatus.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jlblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpbarTotalProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpbarTotalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        jbtnSave.setEnabled(false);
        JFileChooser jfc = new JFileChooser();
        int jc = jfc.showDialog(null, "Save...");
        if (jc == JFileChooser.APPROVE_OPTION) {
            PrintWriter outFile = null;
            try {
                File file = jfc.getSelectedFile();
                String filename = file.getAbsolutePath();
                outFile = new PrintWriter(filename);

                int real_num_rules = Math.min(assoParty.num_rules, assoParty.associationRuleList.size());
                outFile.println("Total number of rules: " + real_num_rules);
                for (int i = 0; i < real_num_rules; i++) {
                    Rule rl = assoParty.associationRuleList.get(i);
                    outFile.print(i + 1 + ". ");
                    outFile.print(rl.getPremise().toString(true));
                    outFile.print(" ===> ");
                    outFile.print(rl.getConsequence().toString(true));
                    outFile.println(" Conf: " + Presentation.round(rl.getConfidence(), 3) + " Support: " + rl.getSupport());
                    outFile.flush();
                }
            } catch (FileNotFoundException ex) {
                ErrorController.fileNotFound(jlblStatus, ex.toString());
            } finally {
                outFile.close();
                jbtnSave.setEnabled(true);
            }
        }
        jbtnSave.setEnabled(true);
}//GEN-LAST:event_jbtnSaveActionPerformed

    private void jbtnDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeactivateActionPerformed
        // TODO add your handling code here:
        assoParty.deactivate();
        jbtnDeactivate.setEnabled(false);
}//GEN-LAST:event_jbtnDeactivateActionPerformed

    private void jbtnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnStopActionPerformed
        try{
            assoParty.stopServer = true;
            assoParty.stopServer();
            jlblStatus.setText("Server stopped");
        }catch (Exception ex){
            ErrorController.show(jlblStatus, ex);
        }finally {
            jbtnStart.setEnabled(true);
            jbtnStop.setEnabled(false);
            jbtnDeactivate.setEnabled(false);
        }
}//GEN-LAST:event_jbtnStopActionPerformed

    private void jbtnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnStartActionPerformed
        //Start the server first
        startServer();
        //if first party, start mining
        if (assoParty.firstParty){
            startMining();
        }

    }//GEN-LAST:event_jbtnStartActionPerformed

public void startServer(){
    try {
//        if (!PortCheck.isAvailable(assoParty.port)) return;
        jbtnStart.setEnabled(false);
        jbtnStop.setEnabled(true);
        jbtnDeactivate.setEnabled(true);
        assoParty.stopServer = false;
        assoParty.startServer(assoParty.port);
        jlblStatus.setText("Server started at port " + assoParty.port);
        
    } catch (Exception ex) {
        if (assoParty.stopServer)
            ErrorController.show(jlblStatus, ex);
    }
    
    /*
    Thread t = new Thread() {
        public void run() {
            try {
            jbtnStart.setEnabled(false);
            jbtnStop.setEnabled(true);
            assoParty.stopServer = false;
            assoParty.startServer(assoParty.port);
            jlblStatus.setText("Server started at port " + assoParty.port);
            } catch (Exception ex) {
                ErrorController.show(jlblStatus, ex);
            } finally {
                jbtnStart.setEnabled(true);
                jbtnStop.setEnabled(false);
            }
        }
    };
    t.start();
    */
}

public void startMining() {                                                
    miningThread = new Thread() {
        @Override
        public void run() {
            try {
                jlblStatus.setText("Start mining... ");
                jbtnStart.setEnabled(false);
                jbtnSave.setEnabled(false);
                jbtnStop.setEnabled(false);

                jpbarTotalProgress.setIndeterminate(true);
                
                assoParty.startMining();
                assoParty.displayResults(jtblReults);
                
                jbtnSave.setEnabled(true);
                
                jlblStatus.setText("Completed! ");
            } 
            catch (Exception ex) {
                ErrorController.show(jlblStatus, ex.getMessage());
            } finally {
                jpbarTotalProgress.setIndeterminate(false);
                jbtnStart.setEnabled(true);                
                jbtnStop.setEnabled(false);
            }
        }
    };
    miningThread.start();
}   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnDeactivate;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnStart;
    private javax.swing.JButton jbtnStop;
    private javax.swing.JLabel jlblStatus;
    private javax.swing.JProgressBar jpbarTotalProgress;
    private javax.swing.JTable jtblReults;
    private javax.swing.JTextArea jtxaLog;
    // End of variables declaration//GEN-END:variables

}
