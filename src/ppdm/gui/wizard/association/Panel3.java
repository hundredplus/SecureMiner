/*
 * Panel2.java
 *
 * Created on October 30, 2008, 4:38 PM
 */
package ppdm.gui.wizard.association;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import ppdm.algo.association.AssoData;
import ppdm.algo.association.ItemSet;
import ppdm.core.ErrorController;
import ppdm.gui.CustomTableColumnModel;

/**
 *
 * @author  Tran Huy Duc
 */
public class Panel3 extends javax.swing.JPanel {

    ArrayList<ItemSet> itemList;
    int dataSize;
    boolean dataLoaded = false;
    int partition =0;

    /** Creates new form Panel2 */
    public Panel3() {
        initComponents();

        ImageIcon icon = new ImageIcon(ppdm.core.Config.LOGO_PATH);
        jlblIcon.setIcon(icon);
        jPanelStep.add(jlblIcon);

        init();
    }

    private void init() {
        jtblData.getSelectionModel().addListSelectionListener(new RowListener());
    }

    private class RowListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                int rowIndex = jtblData.getSelectedRow();
                jlblSupport.setText(Integer.toString(itemList.get(rowIndex).calSupport()));
            }
        }
    }


    public int isRadioButtonSelected() {
        if (jRadioBtnVertical.isSelected()) return 0;
        if (jRadioBtnHorizontal.isSelected()) return 1;
        return -1;
    }

    public void addRadioActionListener(ActionListener l) {
        jRadioBtnVertical.addActionListener(l);
        jRadioBtnHorizontal.addActionListener(l);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtDataFile = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jbtnOpenData = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jRadioBtnVertical = new javax.swing.JRadioButton();
        jRadioBtnHorizontal = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblData = new javax.swing.JTable();
        jbtnView = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlblNoOfAtt = new javax.swing.JLabel();
        jlblNoOfInstances = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jlblSupport = new javax.swing.JLabel();
        jPanelStep = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlblIcon = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32767, 600));
        setPreferredSize(new java.awt.Dimension(775, 450));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jPanel3.setPreferredSize(new java.awt.Dimension(583, 332));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Data Configuration");

        jLabel20.setText("Data Location:");

        jbtnOpenData.setText("Browse...");
        jbtnOpenData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOpenDataActionPerformed(evt);
            }
        });

        jLabel8.setText("Dataset Partition:");

        buttonGroup1.add(jRadioBtnVertical);
        jRadioBtnVertical.setSelected(true);
        jRadioBtnVertical.setText("Vertical");
        jRadioBtnVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioBtnVerticalActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioBtnHorizontal);
        jRadioBtnHorizontal.setText("Horizontal");

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

        jbtnView.setText("View");
        jbtnView.setEnabled(false);
        jbtnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnViewActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(51, 102, 255));
        jSeparator2.setPreferredSize(new java.awt.Dimension(0, 1));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Statistics"));

        jLabel9.setText("Number of Attributes:");

        jLabel10.setText("Number of Instances:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNoOfInstances)
                    .addComponent(jlblNoOfAtt)))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNoOfAtt)
                    .addComponent(jLabel9))
                .addGap(13, 13, 13)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNoOfInstances)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Atrribute"));

        jLabel13.setText("Support:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel13)
                .addGap(27, 27, 27)
                .addComponent(jlblSupport)
                .addContainerGap(135, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jlblSupport)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtDataFile, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnOpenData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnView, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(28, 28, 28)
                                .addComponent(jRadioBtnVertical)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioBtnHorizontal)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jtxtDataFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnView)
                    .addComponent(jbtnOpenData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jRadioBtnVertical)
                    .addComponent(jRadioBtnHorizontal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanelStep.setBackground(new java.awt.Color(255, 255, 255));
        jPanelStep.setPreferredSize(new java.awt.Dimension(185, 450));

        jSeparator3.setForeground(new java.awt.Color(51, 102, 255));
        jSeparator3.setPreferredSize(new java.awt.Dimension(0, 1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setText("Steps");

        jLabel6.setText("1. Project Name");

        jLabel7.setText("2*. Parties Configuration");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel11.setText("3. Data Configuration");

        jLabel12.setText("4. Connection Settings");

        jLabel14.setText("5*. Association Rule ");

        jlblIcon.setPreferredSize(new java.awt.Dimension(185, 140));

        jLabel3.setText(" and Party Type ");

        jLabel15.setText("Configuration");

        jSeparator4.setForeground(new java.awt.Color(51, 102, 255));
        jSeparator4.setPreferredSize(new java.awt.Dimension(0, 1));

        jLabel16.setText("*: For Coordinator Only");

        javax.swing.GroupLayout jPanelStepLayout = new javax.swing.GroupLayout(jPanelStep);
        jPanelStep.setLayout(jPanelStepLayout);
        jPanelStepLayout.setHorizontalGroup(
            jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStepLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStepLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15))
                    .addComponent(jLabel14))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(54, 54, 54))
            .addComponent(jlblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel12)
                    .addComponent(jLabel4))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanelStepLayout.setVerticalGroup(
            jPanelStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStepLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(8, 8, 8)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jlblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jTextArea2.setRows(5);
        jTextArea2.setText("This appilcation deals with binary data only.\n\nIf you have nominal data,go to Tools>Data>Conveter to convert into binary data first.");
        jScrollPane3.setViewportView(jTextArea2);

        jLabel17.setText("Description:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelStep, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelStep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(230, 230, 230)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

private void jbtnOpenDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOpenDataActionPerformed
    jbtnOpenData.setEnabled(false);
    JFileChooser jfc = new JFileChooser();
    int jc = jfc.showDialog(null, "Load data ...");
    if (jc == JFileChooser.APPROVE_OPTION) {
        try {
            File file = jfc.getSelectedFile();
            String filename = file.getAbsolutePath();
            jtxtDataFile.setText(filename);
            itemList = AssoData.readNumericData(filename);
            dataSize = itemList.get(0).getValuesSize();

//            jlblNoOfAtt.setText(Integer.toString(itemList.size()));
//            jlblNoOfInstances.setText(Integer.toString(dataSize));
//
//            String[] colName = {"No.", "Attribute Name"};
//            DefaultTableModel dtm = new DefaultTableModel(colName, 0);
//            for (int i = 0; i < itemList.size(); i++) {
//                Object[] rowData = {i + 1, itemList.get(i).getItem().get(0)};
//                dtm.addRow(rowData);
//            }
//            jtblData.setModel(dtm);
//            CustomTableColumnModel.calcColumnWidths(jtblData);
            dataLoaded = true;
            jbtnView.setEnabled(dataLoaded);
        } catch (Exception ex) {
            dataLoaded = false;
            ErrorController.show(null, ex);
        } finally {
            jbtnOpenData.setEnabled(true);
        }

    }
    jbtnOpenData.setEnabled(true);
}//GEN-LAST:event_jbtnOpenDataActionPerformed

private void jRadioBtnVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioBtnVerticalActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jRadioBtnVerticalActionPerformed

private void jbtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnViewActionPerformed
    try{
        jlblNoOfAtt.setText(Integer.toString(itemList.size()));
        jlblNoOfInstances.setText(Integer.toString(dataSize));

        String[] colName = {"No.", "Attribute Name"};
        DefaultTableModel dtm = new DefaultTableModel(colName, 0);
        for (int i = 0; i < itemList.size(); i++) {
            Object[] rowData = {i + 1, itemList.get(i).getItem().get(0)};
            dtm.addRow(rowData);
        }
        jtblData.setModel(dtm);
        CustomTableColumnModel.calcColumnWidths(jtblData);
    } catch (Exception ex) {
        ErrorController.show(null, ex);
    }

}//GEN-LAST:event_jbtnViewActionPerformed

    public void addTopoLoadedActionListener(ActionListener l) {
        jbtnOpenData.addActionListener(l);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelStep;
    private javax.swing.JRadioButton jRadioBtnHorizontal;
    private javax.swing.JRadioButton jRadioBtnVertical;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton jbtnOpenData;
    private javax.swing.JButton jbtnView;
    private javax.swing.JLabel jlblIcon;
    private javax.swing.JLabel jlblNoOfAtt;
    private javax.swing.JLabel jlblNoOfInstances;
    private javax.swing.JLabel jlblSupport;
    private javax.swing.JTable jtblData;
    private javax.swing.JTextField jtxtDataFile;
    // End of variables declaration//GEN-END:variables
}
