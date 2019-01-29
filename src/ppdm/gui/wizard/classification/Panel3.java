/*
 * Panel2.java
 *
 * Created on October 30, 2008, 4:38 PM
 */
package ppdm.gui.wizard.classification;

import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ppdm.algo.classification.DataReader;
import ppdm.algo.classification.Id3Data;
import ppdm.core.ErrorController;
import ppdm.gui.CustomTableColumnModel;

/**
 *
 * @author  Tran Huy Duc
 */
public class Panel3 extends javax.swing.JPanel {
    Id3Data inputData;
    int     dataSize;
    boolean dataLoaded = false;
    int     partition = 0;

    /** Creates new form Panel2 */
    public Panel3() {
        initComponents();
        initTable();
    }

    private void initTable() {
//        jtblData = new JTable();
        jtblData.getSelectionModel().addListSelectionListener(new RowListener());
    }

    private class RowListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                int rowIndex = jtblData.getSelectedRow();
//                jlblSupport.setText(Integer.toString(itemList.get(rowIndex).calSupport()));
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnOpenData = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblData = new javax.swing.JTable();
        jtxtDataFile = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlblNoOfAtt = new javax.swing.JLabel();
        jlblNoOfInstances = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jlblSupport = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jcmbDataType = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

        jbtnOpenData.setText("Load...");
        jbtnOpenData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOpenDataActionPerformed(evt);
            }
        });

        jScrollPane1.setAutoscrolls(true);

        jtblData.setAutoCreateRowSorter(true);
        jtblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Attribute Name"
            }
        ));
        jScrollPane1.setViewportView(jtblData);

        jLabel1.setText("Load data from file...");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Statistics"));

        jLabel9.setText("Number of Attributes:");

        jLabel10.setText("Number of Instances:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(30, 30, 30)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNoOfInstances)
                    .addComponent(jlblNoOfAtt))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jlblNoOfAtt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jlblNoOfInstances))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Atrribute"));

        jLabel13.setText("Minimum:");

        jLabel2.setText("Maximum:");

        jLabel3.setText("Means:");

        jLabel4.setText("StdDev:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(27, 27, 27)
                        .addComponent(jlblSupport)))
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jlblSupport))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel8.setText("Type:");

        jcmbDataType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Horizontal", "Vertical" }));
        jcmbDataType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbDataTypeActionPerformed(evt);
            }
        });

        jLabel5.setText("Can deal only with nominal/binary data. Missing values is NOT accepted.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnOpenData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtDataFile, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcmbDataType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnOpenData)
                    .addComponent(jtxtDataFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jcmbDataType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jbtnOpenDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOpenDataActionPerformed
    jbtnOpenData.setEnabled(false);
    JFileChooser jfc = new JFileChooser();
    int jc = jfc.showDialog(null, "Load data ...");
    if (jc == JFileChooser.APPROVE_OPTION) {
        try {
//            jtblData.clearSelection();
//            initTable();
            File file = jfc.getSelectedFile();
            String filename = file.getAbsolutePath();
            jtxtDataFile.setText(filename);            
            inputData = DataReader.readData(filename);            
            dataSize = inputData.getNoOfData();

            jlblNoOfAtt.setText(Integer.toString(inputData.noOfAtt));
            jlblNoOfInstances.setText(Integer.toString(dataSize));

            
            String[] colName = {"No.", "Attribute Name"};
            DefaultTableModel dtm = new DefaultTableModel(colName, 0);
            for (int i = 0; i < inputData.noOfAtt; i++) {
                Object[] rowData = {i + 1, inputData.att.get(i)[0]};
                dtm.addRow(rowData);
            }
            
//            jtblData = new JTable();
            jtblData.setModel(dtm);
            CustomTableColumnModel.calcColumnWidths(jtblData);
            dataLoaded = true;
        } catch (Exception ex) {
            dataLoaded = false;
            ErrorController.show(null, ex);
        } finally {
            jbtnOpenData.setEnabled(true);
        }

    }
    jbtnOpenData.setEnabled(true);
}//GEN-LAST:event_jbtnOpenDataActionPerformed

private void jcmbDataTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbDataTypeActionPerformed
    partition = jcmbDataType.getSelectedIndex();

}//GEN-LAST:event_jcmbDataTypeActionPerformed

    public void addTopoLoadedActionListener(ActionListener l) {
        jbtnOpenData.addActionListener(l);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnOpenData;
    private javax.swing.JComboBox jcmbDataType;
    private javax.swing.JLabel jlblNoOfAtt;
    private javax.swing.JLabel jlblNoOfInstances;
    private javax.swing.JLabel jlblSupport;
    private javax.swing.JTable jtblData;
    private javax.swing.JTextField jtxtDataFile;
    // End of variables declaration//GEN-END:variables
}
