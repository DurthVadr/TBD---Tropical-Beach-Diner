package GUI;

import GameEngine.GameLogic;
import GameEngine.TimeManager;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {

    private GameLogic gameLogic;
    private MainMenu mainMenu;
    private boolean isPaused = false;
    private JButton[] kitchenAreaButtons;
    private JButton[] tableAreaButtons;
    private JTextArea gameChatArea;

    private TimeManager timeManager;

    private JButton pauseButton;
    private JPanel pauseMenuPanel;


    public GameScreen(GameLogic gameLogic, MainMenu mainMenu) {
        this.gameLogic = gameLogic;
        this.mainMenu = mainMenu; // Initialize MainMenu reference
        initUI();
    }
    private void initUI() {

        setTitle("Tropical Beach Dinner - Game Screen");
        setSize(1280, 720);  // Adjust size for better control over layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize labels
        JLabel timerLabel = new JLabel("Time: 05:00");
        JLabel satisfactionLabel = new JLabel("Satisfaction: 100%");
        JLabel moneyLabel = new JLabel("Money: $0");

        // Initialize the pause button
        pauseButton = new JButton("Pause");

        // Initialize the pause menu panel
        pauseMenuPanel = new JPanel();
        pauseMenuPanel.setLayout(new BoxLayout(pauseMenuPanel, BoxLayout.Y_AXIS));
        pauseMenuPanel.setVisible(false); // Initially hidden

        JButton resumeButton = new JButton("Resume");
        JButton saveButton = new JButton("Save");
        JButton returnMenuButton = new JButton("Menu");
        JButton quitButton = new JButton("Quit");
        pauseMenuPanel.add(resumeButton);
        pauseMenuPanel.add(saveButton);
        pauseMenuPanel.add(returnMenuButton);
        pauseMenuPanel.add(quitButton);

        // Center the pause menu in the frame
        JPanel pauseMenuContainer = new JPanel(new GridBagLayout());
        pauseMenuContainer.add(pauseMenuPanel);

        // Top panel for timer, satisfaction, and money labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        topPanel.add(pauseButton); // Add the pause button to the top panel
        topPanel.add(timerLabel);
        topPanel.add(satisfactionLabel);
        topPanel.add(moneyLabel);
        add(topPanel, BorderLayout.NORTH);

        // Initialize TimeManager and start the timer
        timeManager = new TimeManager(timerLabel);
        timeManager.startTimer(300);


        // Color definition
        Color lightBrown = new Color(205, 133, 63); // This is a light brown color (RGB)


        // Kitchen area setup
        JPanel kitchenPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        kitchenPanel.setPreferredSize(new Dimension(300, 250));  // Set preferred size to control expansion
        kitchenAreaButtons = new JButton[6];
        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            kitchenAreaButtons[i] = new JButton("ING " + (i + 1));
            kitchenAreaButtons[i].setPreferredSize(new Dimension(100, 50));  // Set preferred button size
            kitchenPanel.add(kitchenAreaButtons[i]);
        }

        // Restaurant area setup
        JPanel tablePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        tablePanel.setPreferredSize(new Dimension(300, 250));  // Similar to kitchen panel
        tableAreaButtons = new JButton[6];
        for (int i = 0; i < tableAreaButtons.length; i++) {
            tableAreaButtons[i] = new JButton("Table " + (i + 1));
            tableAreaButtons[i].setBackground(lightBrown);
            tableAreaButtons[i].setOpaque(true);
            tableAreaButtons[i].setBorderPainted(false);
            tableAreaButtons[i].setPreferredSize(new Dimension(100, 50));  // Ensure buttons are not oversized
            tablePanel.add(tableAreaButtons[i]);
        }

        // Game chat area setup
        gameChatArea = new JTextArea(5, 20);
        gameChatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameChatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 200));  // Control height of the chat area


        // Add action listeners
        pauseButton.addActionListener(e -> pauseButtonClicked());
        resumeButton.addActionListener(e -> resumeButtonClicked());
        saveButton.addActionListener(e -> saveButtonClicked());
        returnMenuButton.addActionListener(e -> returnMenuButtonClicked());
        quitButton.addActionListener(e -> quitButtonClicked());


        // Layout management
        getContentPane().setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(kitchenPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
        add(pauseMenuContainer, BorderLayout.CENTER); // Add the pause menu container to the center

        // Ensure visibility and force repaint
        setVisible(true);
        revalidate();
        repaint();


    }

    private void returnMenuButtonClicked() {
        gameLogic.exitGame();
        dispose(); // Close the game screen
        mainMenu.setVisible(true); // Show the main menu
    }

    private void saveButtonClicked() {
        //A save system
    }

    private void resumeButtonClicked() {
        //timeManager.resetTimer(); // Reset the timer to avoid multiple tasks running
        timeManager.resumeTimer();
        pauseButton.setText("Pause");
        pauseMenuPanel.setVisible(false);
        isPaused = false;
    }

    private static void quitButtonClicked() {
        System.exit(0);
    }

    private void pauseButtonClicked() {
        if (isPaused) {
            resumeButtonClicked();
        } else {
            timeManager.pauseTimer();
            pauseButton.setText("Resume");
            pauseMenuPanel.setVisible(true); // Show the pause menu
            isPaused = true;
        }
    }

    public JButton[] getKitchenAreaButtons() {
        return kitchenAreaButtons;
    }

    public JButton[] getTableAreaButtons() {
        return tableAreaButtons;
    }

    public void displayMessage(String message) {
        gameChatArea.append(message + "\n");
    }
    
}
