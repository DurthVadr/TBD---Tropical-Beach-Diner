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

    private CardLayout cardLayout;
    private JPanel centerPanel;

    private JButton pauseButton;
    private JPanel itemListPanel;


    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.customerManager = gameLogic.getCustomerManager();
        this.timeManager=gameLogic.getTimeManager();
        this.restaurantManager=gameLogic.getRestaurantManager();
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
        JPanel pauseMenuPanel = new JPanel();
        pauseMenuPanel.setLayout(new BoxLayout(pauseMenuPanel, BoxLayout.Y_AXIS));


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

        // Initialize CardLayout and Center Panel
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Stand panel setup
        JPanel standPanel = new JPanel();
        standPanel.setLayout(new BorderLayout());
        standPanel.setPreferredSize(new Dimension(200, 250));

        JLabel standLabel = new JLabel("Stand");
        standLabel.setHorizontalAlignment(SwingConstants.CENTER);
        standPanel.add(standLabel, BorderLayout.NORTH);

        itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane itemScrollPane = new JScrollPane(itemListPanel);
        standPanel.add(itemScrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton inventoryButton = new JButton("Inventory");
        JButton returnButton = new JButton("Return");
        JButton serveButton = new JButton("Serve");
        JButton trashButton = new JButton("Trash");


        // Add buttons to the button panel
        buttonPanel.add(inventoryButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(serveButton);
        buttonPanel.add(trashButton);

        // Add the button panel to the standPanel at the SOUTH position
        standPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add both panels to the centerPanel
        centerPanel.add(standPanel, "StandPanel");
        centerPanel.add(pauseMenuContainer, "PauseMenuPanel");


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
        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            JButton kitchenAreaButton = kitchenAreaButtons[i];
            kitchenAreaButton.addActionListener(e -> kitchenAreaButtonClicked(kitchenAreaButton));
        }
        inventoryButton.addActionListener(e -> inventoryButtonClicked());
        returnButton.addActionListener(e -> returnButtonClicked());
        serveButton.addActionListener(e -> serveButtonClicked());
        trashButton.addActionListener(e -> trashButtonClicked());


// Layout management
        getContentPane().setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(kitchenPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);  // Add centerPanel to the CENTER
        add(scrollPane, BorderLayout.SOUTH);

        // Ensure visibility and force repaint
        setVisible(true);
        revalidate();
        repaint();


    }

    private void trashButtonClicked() {
    }

    private void serveButtonClicked() {
    }


    private void inventoryButtonClicked() {
        
    }

    private void kitchenAreaButtonClicked(JButton kitchenAreaButton) {
        addItemToStand(kitchenAreaButton.getText());  // Use the button's text as the item
    }


    private void addItemToStand(String item) {
        JLabel itemLabel = new JLabel(item);
        itemListPanel.add(itemLabel);
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private void returnButtonClicked() {
        if (itemListPanel.getComponentCount() > 0) {
            itemListPanel.remove(0);
            itemListPanel.revalidate();
            itemListPanel.repaint();
        }
    }


    private void tableButtonClicked(JButton tableAreaButton) {

        if (tableAreaButton.getBackground().equals(TABLE_EMPTY_COLOR)){
            sendChatMessage("Table is empty.\n");
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
    }

    public void orderGiven(Customer customer, int tableIndex, Order order) {
        StringBuilder orderMessage = new StringBuilder("Order: ");
        for (String item : order.getItems()) {
            orderMessage.append(item).append(" ");
        }
        JOptionPane.showMessageDialog(this, orderMessage.toString(), "Customer Order", JOptionPane.INFORMATION_MESSAGE);
        orderMessage= new StringBuilder(orderMessage.toString() + " at " + customer.getName() + "\n");
        sendChatMessage(orderMessage.toString());
        tableAreaButtons[tableIndex].setText(customer.getName() + " - " + orderMessage.toString());
    }


    private void saveButtonClicked() {
        //A save system
    }


    private void resumeButtonClicked() {
        timeManager.resumeTimer();
        pauseButton.setText("Pause");
        // Hide the pause menu and return to the stand panel
        cardLayout.show(centerPanel, "StandPanel");
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
            // Show the pause menu
            cardLayout.show(centerPanel, "PauseMenuPanel");
            timeManager.setPaused(true);
        }
    }

    private void returnMenuButtonClicked() {
        gameLogic.exitGame();
        dispose(); // Close the game screen
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true); // Show the main menu
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
