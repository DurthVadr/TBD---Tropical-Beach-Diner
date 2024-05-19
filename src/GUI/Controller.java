package GUI;

import GameEngine.GameLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private MainMenu mainMenu;
    private GameScreen gameScreen;
    private GameLogic gameLogic;

    public Controller(MainMenu mainMenu, GameScreen gameScreen, GameLogic gameLogic) {
        this.mainMenu = mainMenu;
        this.gameScreen = gameScreen;
        this.gameLogic = gameLogic;

        mainMenu.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        mainMenu.getExitGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        for (JButton button : gameScreen.getTableAreaButtons()) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameScreenAction(e);
                }
            });
        }

        for (JButton button : gameScreen.getKitchenAreaButtons()) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameScreenAction(e);
                }
            });
        }
    }

    public void startGame() {
        mainMenu.setVisible(false);
        gameScreen.setVisible(true);
        gameLogic.startNewGame();
    }

    public void exitGame() {
        System.exit(0);
    }

    private void gameScreenAction(ActionEvent e) {
        // Handle game screen actions
    }
}
