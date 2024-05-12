package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainWindow {
    JLabel GameNameLabel;
    JButton StartGameButton;
    JButton ExitGameButton;


    public MainWindow(JLabel GameNameLabel, JButton StartGameButton, JButton ExitGameButton) {
        this.GameNameLabel = GameNameLabel;
        this.StartGameButton = StartGameButton;
        this.ExitGameButton = ExitGameButton;
    }

    void startGameButtonClicked(JButton StartGameButton) {
    }

    void exitGameButtonClicked(JButton ExitGameButton) {
    }
    
}
