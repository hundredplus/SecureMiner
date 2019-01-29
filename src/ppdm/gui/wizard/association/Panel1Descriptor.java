package ppdm.gui.wizard.association;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ppdm.gui.wizard.*;

public class Panel1Descriptor extends WizardPanelDescriptor implements ActionListener, KeyListener {

    public static final String IDENTIFIER = "FIRST_PARTY_PANEL";
    Panel1      panel1;

    public Panel1Descriptor() {
        panel1 = new Panel1();
        panel1.addRadioActionListener(this);
        panel1.addTextChangeListener(this);
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
        setNextButton();
    }
    
    public void actionPerformed(ActionEvent e) {
        setNextButton();
    }

    private void setNextButton() {
        panel1.setProjectName();
        if (panel1.getProjectName() == null || panel1.getProjectName().equalsIgnoreCase("") || panel1.isRadioButtonSelected() == -1) {
            getWizard().setNextButtonEnabled(false);
        } else {
            getWizard().setNextButtonEnabled(true);
            if (panel1.isRadioButtonSelected() == 0) {
                Panel1.firstParty = false;
            } else if (panel1.isRadioButtonSelected() == 1) {
                Panel1.firstParty = true;
            }
        }

    }
    
    public void aboutToHidePanel(){
        panel1.setProjectName();
    }

    public void keyTyped(KeyEvent e) {        
        setNextButton();
    }

    public void keyPressed(KeyEvent e) {        
        setNextButton();
    }

    public void keyReleased(KeyEvent e) {
        panel1.setProjectName();
        setNextButton();
    }
}
