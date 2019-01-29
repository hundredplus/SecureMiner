package ppdm.gui.wizard.association;

import ppdm.gui.wizard.*;

public class Panel5Descriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "PARAMETER_PANEL";
    Panel5 panel5;

    public Panel5Descriptor() {

        panel5 = new Panel5();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel5);

    }

    public Object getNextPanelDescriptor() {
        return FINISH;
    }

    public Object getBackPanelDescriptor() {
        return Panel4Descriptor.IDENTIFIER;
    }

    public void aboutToDisplayPanel() {
        getWizard().setNextButtonEnabled(false);
        getWizard().setBackButtonEnabled(true);
    }

    public void aboutToHidePanel() {
        panel5.setMinSup();
        panel5.setMaxSup();
        panel5.setMinConfidence();
        panel5.setNum_rules();
        panel5.setVerboseMode();
        panel5.setRankBy();
    }
}
