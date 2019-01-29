package ppdm.gui.wizard.association;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ppdm.core.ErrorController;
import ppdm.gui.wizard.*;


public class Panel3Descriptor extends WizardPanelDescriptor implements ActionListener {
    
    public static final String IDENTIFIER = "DATA_PANEL";
    
    Panel3 panel3;
    
    public Panel3Descriptor() {        
        panel3 = new Panel3();       
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel3);
        panel3.addRadioActionListener(this);


        Thread  t = new Thread(){
            public void run(){
                while (!panel3.dataLoaded){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ErrorController.show(null, ex);
                    }
                }
                getWizard().setNextButtonEnabled(true);
            }
        };
        t.start();
    }
    
    public Object getNextPanelDescriptor() {
        return Panel4Descriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        if (Panel1.firstParty)
            return Panel2Descriptor.IDENTIFIER;
        else
            return Panel1Descriptor.IDENTIFIER;
    }
    
    
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToTopoLoaded();
    }      
    
    private void setNextButtonAccordingToTopoLoaded() {
        if (panel3.dataLoaded)
            getWizard().setNextButtonEnabled(true);
        else
            getWizard().setNextButtonEnabled(false);
    
    }

    public void actionPerformed(ActionEvent e) {
        setPartitionType();
    }

    public void setPartitionType(){
        panel3.partition = panel3.isRadioButtonSelected();
//        System.out.println("$$$$$$$$: " + panel3.partition);
    }

}
