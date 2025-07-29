package ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class ResetGameButton extends JButton {

    public ResetGameButton(final ActionListener actionListener) {
        super("Reiniciar Jogo");
        this.addActionListener(actionListener);
    }
}   