package ppdm.gui.wizard.association;

import ppdm.algo.association.AssociationParty;
import ppdm.gui.wizard.*;

public class AssoGUI {
    
    AssociationParty assoParty;

    public AssociationParty getAssoParty() {
        return assoParty;
    }

    public void setAssoParty(AssociationParty assoParty) {
        this.assoParty = assoParty;
    }
    
    public AssoGUI(){
        Wizard wizard = new Wizard();
        wizard.getDialog().setTitle("New Association Rule Project");
        
        WizardPanelDescriptor descriptor1 = new Panel1Descriptor();
        wizard.registerWizardPanel(Panel1Descriptor.IDENTIFIER, descriptor1);

        WizardPanelDescriptor descriptor2 = new Panel2Descriptor();
        wizard.registerWizardPanel(Panel2Descriptor.IDENTIFIER, descriptor2);

        WizardPanelDescriptor descriptor3 = new Panel3Descriptor();
        wizard.registerWizardPanel(Panel3Descriptor.IDENTIFIER, descriptor3);
        
        WizardPanelDescriptor descriptor4 = new Panel4Descriptor();
        wizard.registerWizardPanel(Panel4Descriptor.IDENTIFIER, descriptor4);
        
        WizardPanelDescriptor descriptor5 = new Panel5Descriptor();
        wizard.registerWizardPanel(Panel5Descriptor.IDENTIFIER, descriptor5);
        
        wizard.setCurrentPanel(Panel1Descriptor.IDENTIFIER);
        
        int ret = wizard.showModalDialog();
        
        //0=Finish,1=Cancel,2=Error
        if (ret == 0){
            assoParty = new AssociationParty();
            assoParty.firstParty = Panel1.isFirstParty();
            assoParty.wrapper.projectName = ((Panel1)descriptor1.getPanelComponent()).projectName;
            
            assoParty.fullTopo = ((Panel2)descriptor2.getPanelComponent()).fullTopo;
            
            assoParty.itemList = ((Panel3)descriptor3.getPanelComponent()).itemList;
            assoParty.dataSize = ((Panel3)descriptor3.getPanelComponent()).dataSize;
            assoParty.partition = ((Panel3)descriptor3.getPanelComponent()).partition;
//            System.out.println("$$$$$$$$: " + assoParty.partition);
            
            assoParty.port = ((Panel4)descriptor4.getPanelComponent()).port;
            assoParty.autoStopServer = ((Panel4)descriptor4.getPanelComponent()).autoStopServer;
            
            assoParty.minSup = ((Panel5)descriptor5.getPanelComponent()).minSup;
            assoParty.maxSup = ((Panel5)descriptor5.getPanelComponent()).maxSup;
            assoParty.minConfidence = ((Panel5)descriptor5.getPanelComponent()).minConfidence;
            assoParty.num_rules = ((Panel5)descriptor5.getPanelComponent()).num_rules;
            assoParty.rankBy = ((Panel5)descriptor5.getPanelComponent()).rankBy;
            assoParty.verboseMode = ((Panel5)descriptor5.getPanelComponent()).verboseMode;            
        }
    }
}
