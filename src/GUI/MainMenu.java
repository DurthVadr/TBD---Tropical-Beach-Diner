package GUI;

import GameEngine.GameLogic;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private GameLogic gameLogic;
    private JLabel GameNameLabel;
    private JButton StartGameButton;
    private JButton ExitGameButton;

    public MainMenu(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        GameNameLabel = new JLabel("Tropical Beach Dinner", SwingConstants.CENTER);
        StartGameButton = new JButton("Start Game");
        ExitGameButton = new JButton("Exit Game");

        // Set up the panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(GameNameLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(StartGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(ExitGameButton);

        // Add panel to the frame
        add(panel);

        // Add action listeners
        StartGameButton.addActionListener(e -> startGameButtonClicked());
        ExitGameButton.addActionListener(e -> exitGameButtonClicked());
    }

    private void startGameButtonClicked() {
        gameLogic.startNewGame();
        dispose();
        new GameScreen(gameLogic).setVisible(true);
    }

    private void exitGameButtonClicked() {
        System.exit(0);
    }

    public JButton getStartGameButton() {
        return StartGameButton;
    }

    public JButton getExitGameButton() {
        return ExitGameButton;
    }
}
