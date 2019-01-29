package ppdm.gui.wizard.clustering;


import ppdm.gui.wizard.*;


public class Panel4Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "SERVER_PANEL";    
    Panel4 panel4;    
    
    public Panel4Descriptor() {        
        panel4 = new Panel4();       
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel4);        
    }
    
    public Object getNextPanelDescriptor() {
        if (Panel1.firstParty)
            return Panel5Descriptor.IDENTIFIER;
        else
            return FINISH;
    }
    
    public Object getBackPanelDescriptor() {
        return Panel3Descriptor.IDENTIFIER;
    }    
    
    public void aboutToDisplayPanel() {
        getWizard().setNextButtonEnabled(true);
    }      
   
    public void aboutToHidePanel() {
        panel4.setPort();
        panel4.setAutoStopServer();
    }    
            
}
