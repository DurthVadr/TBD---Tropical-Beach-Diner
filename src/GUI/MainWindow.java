package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    JLabel GameNameLabel;
    JButton StartGameButton;
    JButton ExitGameButton;


    public MainWindow(JLabel GameNameLabel, JButton StartGameButton, JButton ExitGameButton) {
        this.GameNameLabel = GameNameLabel;
        this.StartGameButton = StartGameButton;
        this.ExitGameButton = ExitGameButton;

        JPanel panel = new JPanel();
        panel.add(this.GameNameLabel);
        panel.add(this.StartGameButton);
        panel.add(this.ExitGameButton);
        add(panel);

        this.StartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameButtonClicked(StartGameButton);
            }
        });

        this.ExitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGameButtonClicked(ExitGameButton);
            }
        });
    }

    void startGameButtonClicked(JButton StartGameButton) {
    }

    void exitGameButtonClicked(JButton ExitGameButton) {
    }
    
}
