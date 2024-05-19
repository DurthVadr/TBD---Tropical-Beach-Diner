package GUI;

import GameEngine.GameLogic;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private GameLogic gameLogic;
    private JLabel titleLabel;
    private JButton startGameButton;
    private JButton loadGameButton;
    private JButton optionsButton;
    private JButton exitGameButton;


    public MainMenu(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        titleLabel = new JLabel("Tropical Beach Dinner", SwingConstants.CENTER);
        startGameButton = new JButton("Start New Game");
        loadGameButton = new JButton("Load Game");
        optionsButton = new JButton("Options");
        exitGameButton = new JButton("Exit Game");

        // Set up the panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Upper panel for logo or text
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout());
        upperPanel.add(titleLabel);

        // Center panel for buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 10)); // 3 rows, 1 column, vertical gap of 10 pixels
        centerPanel.add(startGameButton);
        centerPanel.add(loadGameButton);
        centerPanel.add(optionsButton);

        // Bottom panel for exit button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(exitGameButton);

        // Add panels to the main panel
        panel.add(upperPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Add panel to the frame
        add(panel);

        // Add action listeners
        startGameButton.addActionListener(e -> startGameButtonClicked());
        loadGameButton.addActionListener(e -> loadGameButtonClicked());
        optionsButton.addActionListener(e -> optionsButtonClicked());
        exitGameButton.addActionListener(e -> exitGameButtonClicked());
    }

    private void startGameButtonClicked() {
        gameLogic.startNewGame();
        dispose();
        new GameScreen(gameLogic).setVisible(true);
    }

    private void loadGameButtonClicked() {
        // Handle load game button action
    }

    private void optionsButtonClicked() {
        // Handle options button action
    }

    private void exitGameButtonClicked() {
        System.exit(0);
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public JButton getExitGameButton() {
        return exitGameButton;
    }

}
