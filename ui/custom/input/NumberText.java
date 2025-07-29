package ui.custom.input;

import static service.EventEnum.CLEAR_SPACE;

import service.EventEnum;
import service.EventListener;
import model.Space;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberText extends JTextField implements EventListener {

    private final Space space;

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.BOLD, 20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());

        if(space.isFixed()) {
            this.setText(space.getActual().toString());
        }
        
        this.getDocument().addDocumentListener(new DocumentListener() {

            private void updateSpace() {
                if(getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }
                
                space.setActual(Integer.parseInt(getText()));      
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSpace();
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateSpace();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                updateSpace();
            }
        });
    }

    @Override
    public void update(final EventEnum event) {
        if(event.equals(CLEAR_SPACE) && (this.isEnabled())) {
            this.setText("");
        }
    }
}
