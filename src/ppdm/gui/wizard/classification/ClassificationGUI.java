package ppdm.gui.wizard.classification;

import ppdm.algo.classification.Id3Party;
import ppdm.gui.wizard.*;

public class ClassificationGUI {
    
    public Id3Party id3Party;

    public Id3Party getId3Party() {
        return id3Party;
    }

    public void setId3Party(Id3Party id3Party) {
        this.id3Party = id3Party;
    }
    
    public ClassificationGUI() throws Exception{
        Wizard wizard = new Wizard();
        wizard.getDialog().setTitle("New Classifiaction Project");
        
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
            id3Party = new Id3Party();
            id3Party.firstParty = Panel1.isFirstParty();
            id3Party.fullTopo = ((Panel2)descriptor2.getPanelComponent()).fullTopo;            
            id3Party.inputData = ((Panel3)descriptor3.getPanelComponent()).inputData;
            id3Party.dataSize = ((Panel3)descriptor3.getPanelComponent()).dataSize;
            id3Party.partition = ((Panel3)descriptor3.getPanelComponent()).partition;
            id3Party.port = ((Panel4)descriptor4.getPanelComponent()).port;
            id3Party.autoStopServer = ((Panel4)descriptor4.getPanelComponent()).autoStopServer;
            
            id3Party.verboseMode = ((Panel5)descriptor5.getPanelComponent()).verboseMode; 

        }
        
    }
}
