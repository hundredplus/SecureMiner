package ppdm.gui.wizard.clustering;

import ppdm.algo.clustering.KMeansParty;
import ppdm.gui.wizard.*;

public class KMeansGUI {
    
    KMeansParty kMeansParty;

    public KMeansParty getKMeansParty() {
        return kMeansParty;
    }

    public void setKMeansParty(KMeansParty kMeansParty) {
        this.kMeansParty = kMeansParty;
    }

    
    
    public KMeansGUI() throws Exception{
        Wizard wizard = new Wizard();
        wizard.getDialog().setTitle("New Clustering Project");
        
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
            kMeansParty = new KMeansParty();
            kMeansParty.firstParty = Panel1.isFirstParty();
            kMeansParty.fullTopo = ((Panel2)descriptor2.getPanelComponent()).fullTopo;            
            kMeansParty.inputData = ((Panel3)descriptor3.getPanelComponent()).inputData;
            kMeansParty.dataSize = ((Panel3)descriptor3.getPanelComponent()).dataSize;
            kMeansParty.partition = ((Panel3)descriptor3.getPanelComponent()).partition;
            kMeansParty.port = ((Panel4)descriptor4.getPanelComponent()).port;
            kMeansParty.autoStopServer = ((Panel4)descriptor4.getPanelComponent()).autoStopServer;
            
            kMeansParty.max_num_iteration = ((Panel5)descriptor5.getPanelComponent()).num_loop;
            kMeansParty.num_cluster = ((Panel5)descriptor5.getPanelComponent()).num_cluster;
            kMeansParty.verboseMode = ((Panel5)descriptor5.getPanelComponent()).verboseMode; 
            
            kMeansParty.convertData();
        }
        
    }
}
