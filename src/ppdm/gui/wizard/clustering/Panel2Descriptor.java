package ppdm.gui.wizard.clustering;

import ppdm.core.ErrorController;
import ppdm.gui.wizard.*;


public class Panel2Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "TOPO_PANEL";
    
    Panel2 panel2;
    
    public Panel2Descriptor() {        
        panel2 = new Panel2();       
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
        
        Thread  t = new Thread(){
            public void run(){
                while (!panel2.topoLoaded){
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
        return Panel3Descriptor.IDENTIFIER;
    }
    
    public Object getBackPanelDescriptor() {
        return Panel1Descriptor.IDENTIFIER;
    }
    
    
    public void aboutToDisplayPanel() {
        setNextButtonAccordingToTopoLoaded();
    }      
    
    private void setNextButtonAccordingToTopoLoaded() {
        if (panel2.topoLoaded)
            getWizard().setNextButtonEnabled(true);
        else
            getWizard().setNextButtonEnabled(false);
    
    }
}
