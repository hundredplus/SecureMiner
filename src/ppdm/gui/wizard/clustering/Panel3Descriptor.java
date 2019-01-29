package ppdm.gui.wizard.clustering;

import ppdm.core.ErrorController;
import ppdm.gui.wizard.*;


public class Panel3Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "DATA_PANEL";
    
    Panel3 panel3;
    
    public Panel3Descriptor() {        
        panel3 = new Panel3();       
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel3);
        
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
}
