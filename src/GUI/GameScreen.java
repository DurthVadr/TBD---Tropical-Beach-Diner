package GUI;

import GameEngine.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {
    private GameLogic gameLogic;
    private JButton[] kitchenAreaButtons;
    private JButton[] tableAreaButtons;
    private JTextArea gameChatArea;

    public GameScreen(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Game Screen");
        setSize(800, 600);  // Adjust size for better control over layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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
