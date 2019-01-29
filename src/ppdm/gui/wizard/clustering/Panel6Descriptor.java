package ppdm.gui.wizard.clustering;


import ppdm.gui.wizard.*;


public class Panel6Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "READY_PANEL";
    
    Panel6 panel6;
    
    public Panel6Descriptor() {
        
        panel6 = new Panel6();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel6);
        
    }

    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    public Object getBackPanelDescriptor() {
        return Panel5Descriptor.IDENTIFIER;
    }
    
    
    public void aboutToDisplayPanel() {
          panel6.setReadyButton(true);
          panel6.setStopButton(false);
          if (Panel1.firstParty)
              panel6.setText4ReadyButton("Start Mining");
          else
              panel6.setText4ReadyButton("Ready");
//        panel3.setProgressValue(0);
//        panel3.setProgressText("Connecting to Server...");

        getWizard().setNextButtonEnabled(false);
        getWizard().setBackButtonEnabled(true);
        
    }
    
    public void displayingPanel() {
/*
            Thread t = new Thread() {

            public void run() {

                try {
                    Thread.sleep(2000);
                    panel4.setProgressValue(25);
                    panel3.setProgressText("Server Connection Established");
                    Thread.sleep(500);
                    panel3.setProgressValue(50);
                    panel3.setProgressText("Transmitting Data...");
                    Thread.sleep(3000);
                    panel3.setProgressValue(75);
                    panel3.setProgressText("Receiving Acknowledgement...");
                    Thread.sleep(1000);
                    panel3.setProgressValue(100);
                    panel3.setProgressText("Data Successfully Transmitted");

                    getWizard().setNextButtonEnabled(true);
                    getWizard().setBackButtonEnabled(true);

                } catch (InterruptedException e) {
                    
                    panel3.setProgressValue(0);
                    panel3.setProgressText("An Error Has Occurred");
                    
                    getWizard().setBackButtonEnabled(true);
                }

            }
        };

        t.start();*/
    }    
            
}
