package ui.custom.button;

import javax.swing.JButton;
import java.awt.event.ActionListener;

public class FinishGameButton extends JButton {

    public FinishGameButton(final ActionListener actionListener) {
        super("Finalizar Jogo");
        this.addActionListener(actionListener);
    }
}
