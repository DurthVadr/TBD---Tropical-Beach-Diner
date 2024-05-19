package GUI;

import GameEngine.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreen extends JFrame {
    private GameLogic gameLogic;
    private JButton[] tableAreaButtons;
    private JButton[] kitchenAreaButtons;

    public GameScreen(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        initUI();
    }

    private void initUI() {
        setTitle("Tropical Beach Dinner - Game Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize buttons (example setup, adjust as needed)
        tableAreaButtons = new JButton[10]; // Example size
        kitchenAreaButtons = new JButton[5]; // Example size

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 5)); // Example layout

        for (int i = 0; i < tableAreaButtons.length; i++) {
            tableAreaButtons[i] = new JButton("Table " + (i + 1));
            tablePanel.add(tableAreaButtons[i]);
        }

        JPanel kitchenPanel = new JPanel();
        kitchenPanel.setLayout(new GridLayout(1, 5)); // Example layout

        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            kitchenAreaButtons[i] = new JButton("Kitchen " + (i + 1));
            kitchenPanel.add(kitchenAreaButtons[i]);
        }

        setLayout(new BorderLayout());
        add(tablePanel, BorderLayout.CENTER);
        add(kitchenPanel, BorderLayout.SOUTH);
    }

    public JButton[] getTableAreaButtons() {
        return tableAreaButtons;
    }

    public JButton[] getKitchenAreaButtons() {
        return kitchenAreaButtons;
    }

}
