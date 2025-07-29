package ui.custom.screen;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Space;
import service.BoardService;
import service.NotifierService;
import ui.custom.button.CheckGameStatusButton;
import ui.custom.button.FinishGameButton;
import ui.custom.button.ResetGameButton;
import ui.custom.frame.MainFrame;
import ui.custom.input.NumberText;
import ui.custom.panel.MainPanel;
import ui.custom.panel.SudokuSector;

import static javax.swing.JOptionPane.*;
import static service.EventEnum.CLEAR_SPACE;

public class MainScreen {
    
    private final static Dimension DIMENSION = new Dimension(610, 650);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton resetGameButton;
    private JButton finishGameButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(DIMENSION);
        JFrame mainFrame = new MainFrame(DIMENSION, mainPanel);
        for(var row = 0; row < 9; row+=3) {
            var endRow = row + 2;
            for(var col = 0; col < 9; col+=3) {
                var endCol = col + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), col, endCol, row, endRow);
                var sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResetGameButton(mainPanel);
        addFinishGameButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces, 
                                            final int initCol, final int endCol, 
                                            final int initRow, final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for(var row = initRow; row <= endRow; row++) {
            for(var col = initCol; col <= endCol; col++) {
                spaceSector.add(spaces.get(col).get(row));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(field -> notifierService.subscribe(CLEAR_SPACE, field));
        return new SudokuSector(fields);
    }

    private void addResetGameButton(final JPanel mainPanel) {
        resetGameButton = new ResetGameButton(e -> {
            var dialogResult = showConfirmDialog(
                mainPanel,
                "Tem certeza que deseja reiniciar o jogo?", 
                "Reiniciar jogo", 
                YES_NO_OPTION, 
                QUESTION_MESSAGE
            );
            
            if(dialogResult == YES_OPTION) {
                boardService.resetBoard();
                notifierService.notify(CLEAR_SPACE);
            }
        });

        mainPanel.add(resetGameButton);
    }

    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButton(e -> {
            if(boardService.gameIsFinished()) {
                showMessageDialog(
                    null,
                    "Parabéns! Você completou o jogo com sucesso!",
                    "Finalizar jogo",
                    INFORMATION_MESSAGE
                );
                resetGameButton.setEnabled(false);
                finishGameButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
            } else {
                showMessageDialog(
                    null,
                    "O jogo ainda não foi concluído. Continue jogando!",
                    "Finalizar jogo",
                    INFORMATION_MESSAGE
                );
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch(gameStatus) {
                case NON_STARTED -> "O jogo ainda não foi iniciado";
                case INCOMPLETE -> "O jogo está incompleto " + (hasErrors ? "e possui erros" : "");
                case COMPLETE -> "Parabéns! Você completou o jogo com sucesso!";
                default -> "O jogo não foi finalizado.";
            };

            showMessageDialog(
                null,
                message,
                "Verificar status do jogo",
                INFORMATION_MESSAGE
            );
        });
        mainPanel.add(checkGameStatusButton);
    }   
}
