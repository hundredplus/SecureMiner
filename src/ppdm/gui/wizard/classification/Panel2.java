/*
 * Panel2.java
 *
 * Created on October 30, 2008, 4:38 PM
 */

package ppdm.gui.wizard.classification;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ppdm.algo.association.PartyID;
import ppdm.algo.association.Topology;
import ppdm.core.ErrorController;
import ppdm.gui.CustomTableColumnModel;

/**
 *
 * @author  Tran Huy Duc
 */
public class Panel2 extends javax.swing.JPanel {

    ArrayList<PartyID>  fullTopo;
    boolean             topoLoaded = false;
    
    /** Creates new form Panel2 */
    public Panel2() {
        initComponents();
        init();
    }

    private void init() {
        jtblTopo.getSelectionModel().addListSelectionListener(new RowListener());
    }
    
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                onRowClick();
            }
        }
    }
    
    public void onRowClick() {
//        int rowIndex = jtblTopo.getSelectionModel().getLeadSelectionIndex();
        int rowIndex = jtblTopo.getSelectedRow();
//        Integer party = Integer.toString((Integer)jtblTopo.getValueAt(rowIndex, 0));
//        System.out.println(party);
        ArrayList<String> att = fullTopo.get(rowIndex).getAttribute();

        String[] colName = {"No.", "Attribute"};
        DefaultTableModel dtm = new DefaultTableModel(colName, 0);

        for (int i = 0; i < att.size(); i++) {
            Object[] rowData = {i + 1, att.get(i)};
            dtm.addRow(rowData);
        }
        jtblAttribute.setModel(dtm);
        CustomTableColumnModel.calcColumnWidths(jtblAttribute);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnOpenTopo = new javax.swing.JButton();
        jscrpTopo = new javax.swing.JScrollPane();
        jtblTopo = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblAttribute = new javax.swing.JTable();
        jtxtTopoFile = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();

        jbtnOpenTopo.setText("Load...");
        jbtnOpenTopo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOpenTopoActionPerformed(evt);
            }
        });

        jscrpTopo.setAutoscrolls(true);

        jtblTopo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Party", "IP", "Port"
            }
        ));
        jtblTopo.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jtblTopoComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jtblTopoComponentShown(evt);
            }
        });
        jscrpTopo.setViewportView(jtblTopo);

        jtblAttribute.setAutoCreateRowSorter(true);
        jtblAttribute.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Attributes"
            }
        ));
        jScrollPane3.setViewportView(jtblAttribute);

        jLabel1.setText("Load topology and data information from file...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addContainerGap(290, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jbtnOpenTopo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtxtTopoFile, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jscrpTopo, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnOpenTopo)
                    .addComponent(jtxtTopoFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)
                    .addComponent(jscrpTopo, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jtblTopoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jtblTopoComponentResized
// TODO add your handling code here:
}//GEN-LAST:event_jtblTopoComponentResized

private void jtblTopoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jtblTopoComponentShown
// TODO add your handling code here:
}//GEN-LAST:event_jtblTopoComponentShown

private void jbtnOpenTopoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOpenTopoActionPerformed
    jbtnOpenTopo.setEnabled(false);
    JFileChooser jfc = new JFileChooser();
    int jc = jfc.showDialog(null, "Load topology ...");
    if (jc == JFileChooser.APPROVE_OPTION) {
        try {
            File file = jfc.getSelectedFile();
            String filename = file.getAbsolutePath();
            jtxtTopoFile.setText(filename);
            fullTopo = Topology.readTopology(filename);

            String[] colName = {"Party", "IP", "Port"};
            DefaultTableModel dtm = new DefaultTableModel(colName, 0);

            for (int i = 0; i < fullTopo.size(); i++) {
                PartyID pid = fullTopo.get(i);
                Object[] rowData = {i + 1, pid.IP, pid.port};
                dtm.addRow(rowData);
            }
            jtblTopo.setModel(dtm);
            CustomTableColumnModel.calcColumnWidths(jtblTopo);
            topoLoaded = true;
        } catch (Exception ex) {
            topoLoaded = false;
            ErrorController.show(null, ex); 
        } finally {
            jbtnOpenTopo.setEnabled(true);
        }
    }
    jbtnOpenTopo.setEnabled(true);
}//GEN-LAST:event_jbtnOpenTopoActionPerformed

public void addTopoLoadedActionListener(ActionListener l) {
        jbtnOpenTopo.addActionListener(l);        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnOpenTopo;
    private javax.swing.JScrollPane jscrpTopo;
    private javax.swing.JTable jtblAttribute;
    private javax.swing.JTable jtblTopo;
    private javax.swing.JTextField jtxtTopoFile;
    // End of variables declaration//GEN-END:variables

}
