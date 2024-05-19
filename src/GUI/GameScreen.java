package GUI;

import GameEngine.GameLogic;
import GameEngine.TimeManager;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {
    private GameLogic gameLogic;
    private JButton[] kitchenAreaButtons;
    private JButton[] tableAreaButtons;
    private JTextArea gameChatArea;

    private TimeManager timeManager;
    private JLabel timerLabel;
    private JLabel satisfactionLabel;
    private JLabel moneyLabel;


    public GameScreen(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Game Screen");
        setSize(1280, 720);  // Adjust size for better control over layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Initialize labels
        timerLabel = new JLabel("Time: 05:00");
        satisfactionLabel = new JLabel("Satisfaction: 100%");
        moneyLabel = new JLabel("Money: $0");

        // Top panel for timer, satisfaction, and money labels
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
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

        // Layout management
        getContentPane().setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(kitchenPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
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
