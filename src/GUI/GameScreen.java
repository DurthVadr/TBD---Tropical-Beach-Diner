package GUI;

import GameEngine.CustomerManager;
import GameEngine.GameLogic;
import GameEngine.RestaurantManager;
import GameEngine.TimeManager;
import Restaurant.Customer;
import Restaurant.Order;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class GameScreen extends JFrame {

    private GameLogic gameLogic;
    private TimeManager timeManager;
    private CustomerManager customerManager;

    private RestaurantManager restaurantManager;
    private JButton[] kitchenAreaButtons;
    private JButton[] tableAreaButtons;
    private JTextArea gameChatArea;

    private Timer customerTimer;
    private JButton pauseButton;
    private JPanel pauseMenuPanel;


    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.customerManager = gameLogic.getCustomerManager();
        this.timeManager=gameLogic.getTimeManager();
        this.restaurantManager=gameLogic.getRestaurantManager();
    }

    public void setRestaurantManager(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }
    private final Color TABLE_EMPTY_COLOR = new Color(144, 238, 144);
    private final Color TABLE_THINKING_COLOR = new Color(255, 255, 109);
    private final Color TABLE_ORDERED_COLOR = new Color(0, 199, 255);
    private final Color TABLE_EATING_COLOR = new Color(255, 255, 30);
    private final Color TABLE_WAITING_TO_LEAVE_COLOR = new Color(70, 255, 46);




    public GameScreen(){
    }

    public void initialize() {
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
        timeManager.setTimerLabel(timerLabel);
        timeManager.startTimer(300);

        // Kitchen area setup
        JPanel kitchenPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        kitchenPanel.setPreferredSize(new Dimension(300, 250));  // Set preferred size to control expansion
        kitchenAreaButtons = new JButton[6];
        String[] buttonNames = {"Meat", "Cheese", "Lettuce", "Tomato", "Dough", "Pepperoni"};  // Button names
        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            kitchenAreaButtons[i] = new JButton(buttonNames[i]);
            kitchenPanel.add(kitchenAreaButtons[i]);
        }
            

        // Restaurant area setup
        JPanel tablePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        tablePanel.setPreferredSize(new Dimension(300, 250));  // Similar to kitchen panel
        tableAreaButtons = new JButton[6];
        for (int i = 0; i < tableAreaButtons.length; i++) {
            tableAreaButtons[i] = new JButton("Table " + (i + 1));
            tableAreaButtons[i].setBackground(TABLE_EMPTY_COLOR);
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

        for (int i = 0; i < tableAreaButtons.length; i++) {
            JButton tableAreaButton = tableAreaButtons[i];
            tableAreaButton.addActionListener(e -> tableButtonClicked(tableAreaButton));
        }



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

    private void tableButtonClicked(JButton tableAreaButton) {

        if (tableAreaButton.getBackground().equals(TABLE_EMPTY_COLOR)){
            sendChatMessage("Table is empty");
        } else if (tableAreaButton.getBackground().equals(TABLE_THINKING_COLOR)) {
            String a="";
        }else if (tableAreaButton.getBackground().equals(TABLE_ORDERED_COLOR)) {
            String a="";
        }else if (tableAreaButton.getBackground().equals(TABLE_EATING_COLOR)) {
            String a="";
        }else if (tableAreaButton.getBackground().equals(TABLE_WAITING_TO_LEAVE_COLOR)) {
            String a="";
        }

    }

    public void addCustomerToTable(Customer customer, int tableIndex) {
        JButton tableButton = tableAreaButtons[tableIndex];
        tableButton.setBackground(TABLE_THINKING_COLOR);
        tableButton.setText(customer.getName());
        sendChatMessage(customer.getName() + " have arrived.\n");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> showCustomerOrder(customer, tableIndex));
            }
        }, 10000); // 10 seconds delay for customers to think
    }

    private void showCustomerOrder(Customer customer, int tableIndex) {
        Order order = customerManager.generateRandomOrder(customer);
        StringBuilder orderMessage = new StringBuilder("Order: ");
        for (String item : order.getItems()) {
            orderMessage.append(item).append(" ");
        }
        JOptionPane.showMessageDialog(this, orderMessage.toString(), "Customer Order", JOptionPane.INFORMATION_MESSAGE);
        gameChatArea.append(orderMessage.toString() + " at " + customer.getName() + "\n");
        tableAreaButtons[tableIndex].setText(customer.getName() + " - " + orderMessage.toString());
    }

    private void returnMenuButtonClicked() {
        gameLogic.exitGame();
        dispose(); // Close the game screen
        MainMenu mainMenu = new MainMenu();
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
        timeManager.setPaused(false);
    }

    private static void quitButtonClicked() {
        System.exit(0);
    }

    private void pauseButtonClicked() {
        if (timeManager.isPaused()) {
            resumeButtonClicked();
        } else {
            timeManager.pauseTimer();
            pauseButton.setText("Resume");
            pauseMenuPanel.setVisible(true); // Show the pause menu
            timeManager.setPaused(true);
        }
    }

    private void sendChatMessage(String text){
        gameChatArea.append(text);
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
