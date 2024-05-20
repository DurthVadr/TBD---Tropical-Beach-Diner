package GUI;

import GameEngine.*;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private JButton startGameButton;
    private JButton exitGameButton;


    public MainMenu() {
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Main Menu");
        setSize(1280, 720); // Set window size to 1280x720 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize components
        JLabel titleLabel = new JLabel("Tropical Beach Dinner", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font for the title
        titleLabel.setPreferredSize(new Dimension(1280, 130));

        startGameButton = new JButton("Start New Game");
        startGameButton.setPreferredSize(new Dimension(340, 100));
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setPreferredSize(new Dimension(340, 100));
        JButton optionsButton = new JButton("Options");
        optionsButton.setPreferredSize(new Dimension(340, 100));
        exitGameButton = new JButton("Exit Game");
        exitGameButton.setPreferredSize(new Dimension(340, 100));

        // Set up the layout
        setLayout(new BorderLayout());

        // Upper panel for the title
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setPreferredSize(new Dimension(getWidth(), 130));
        upperPanel.add(titleLabel, BorderLayout.CENTER);

        // Center panel for the main buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setPreferredSize(new Dimension(getWidth(), 460));
        centerPanel.add(Box.createVerticalGlue()); // Add vertical glue to center buttons vertically
        centerPanel.add(createButtonPanel(startGameButton));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing between buttons
        centerPanel.add(createButtonPanel(loadGameButton));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(createButtonPanel(optionsButton));
        centerPanel.add(Box.createVerticalGlue());

        // Bottom panel for the exit button with padding
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel exitButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30)); // Add padding
        exitButtonPanel.add(exitGameButton);
        bottomPanel.add(exitButtonPanel, BorderLayout.EAST);

        // Add panels to the main frame
        add(upperPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        startGameButton.addActionListener(e -> startGameButtonClicked());
        loadGameButton.addActionListener(e -> loadGameButtonClicked());
        optionsButton.addActionListener(e -> optionsButtonClicked());
        exitGameButton.addActionListener(e -> exitGameButtonClicked());
    }

    private void startGameButtonClicked() {


        // Initialize the game components
        CustomerManager customerManager = new CustomerManager();
        InventoryManager inventoryManager = new InventoryManager();
        RestaurantManager restaurantManager = new RestaurantManager();
        TimeManager timeManager = new TimeManager();
        GameScreen gameScreen = new GameScreen();



        // Initialize the game logic
        GameLogic gameLogic = new GameLogic(customerManager, inventoryManager, restaurantManager, timeManager, gameScreen);
        dispose();
        gameLogic.startNewGame();
    }

    private void loadGameButtonClicked() {
//        SaveLoadSystem saveLoadSystem = new SaveLoadSystem();
//        if (saveLoadSystem.loadGame()) {
//            CustomerManager customerManager = new CustomerManager();
//            InventoryManager inventoryManager = new InventoryManager();
//            RestaurantManager restaurantManager = new RestaurantManager();
//            TimeManager timeManager = new TimeManager();
//            GameScreen gameScreen = new GameScreen();
//
//            GameLogic gameLogic = new GameLogic(customerManager, inventoryManager, restaurantManager, timeManager, gameScreen);
//            dispose();
//            gameLogic.loadGame(saveLoadSystem.getLoadedGameState());
//        } else {
//            JOptionPane.showMessageDialog(this, "No saved game found!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
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

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(button);
        return panel;
    }
}
