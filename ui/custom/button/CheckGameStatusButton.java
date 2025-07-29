package ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;
    


public class CheckGameStatusButton extends JButton {

    public CheckGameStatusButton(final ActionListener actionListener) {
        super("Verificar Status");
        this.addActionListener(actionListener);
    }
    
    
}
