package ppdm.gui.wizard.classification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ppdm.gui.wizard.*;

public class Panel1Descriptor extends WizardPanelDescriptor implements ActionListener {

    public static final String IDENTIFIER = "FIRST_PARTY_PANEL";
    Panel1      panel1;

    public Panel1Descriptor() {
        panel1 = new Panel1();
        panel1.addRadioButtonActionListener(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel1);
    }

    public Object getNextPanelDescriptor() {
        if (panel1.isRadioButtonSelected() == 0) {
            return Panel3Descriptor.IDENTIFIER;
        } else {
            return Panel2Descriptor.IDENTIFIER;
        }
                
    }

    public Object getBackPanelDescriptor() {
        return null;
    }

    public void aboutToDisplayPanel() {
        setNextButtonAccordingToRadioButton();
    }
    
    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToRadioButton();
    }

    private void setNextButtonAccordingToRadioButton() {
        if (panel1.isRadioButtonSelected() != -1) {
            getWizard().setNextButtonEnabled(true);
            if (panel1.isRadioButtonSelected() == 0) {
                Panel1.firstParty = false;
            } else if (panel1.isRadioButtonSelected() == 1) {
                Panel1.firstParty = true;
            }
            
        } else {
            getWizard().setNextButtonEnabled(false);
        }
    }
}
