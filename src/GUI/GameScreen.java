package GUI;

import GameEngine.CustomerManager;
import GameEngine.GameLogic;
import GameEngine.RestaurantManager;
import GameEngine.TimeManager;
import Persistence.SaveLoadSystem;
import Restaurant.Customer;
import Restaurant.Order;
import Persistence.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

public class GameScreen extends JFrame {

    private Boolean serving=false;
    private Boolean inventoryOpened=false;
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
    private JButton inventoryButton;
    private JButton returnButton;
    private JButton serveButton;
    private JButton trashButton;

    private JLabel moneyLabel;
    private JLabel collectiveSatisfactionLabel;
    private Clip backgroundMusicClip;


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
        collectiveSatisfactionLabel = new JLabel("Collective Satisfaction: 100%");
        moneyLabel = new JLabel("Money: $0");
        updateMoney(gameLogic.getMoney());

        

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
        topPanel.add(collectiveSatisfactionLabel);
        topPanel.add(moneyLabel);
        add(topPanel, BorderLayout.NORTH);

        // Initialize TimeManager and start the timer
        timeManager.setTimerLabel(timerLabel);
        timeManager.startTimer(timeManager.getTotalTime());

        // Kitchen area setup
        JPanel kitchenPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        kitchenPanel.setPreferredSize(new Dimension(300, 250));  // Set preferred size to control expansion
        kitchenAreaButtons = new JButton[6];

        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            kitchenAreaButtons[i] = new JButton(GameLogic.MENU_ITEMS.get(i));
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

        inventoryButton = new JButton("Inventory");
        returnButton = new JButton("Return");
        serveButton = new JButton("Serve");
        trashButton = new JButton("Trash");

        trashButton.setEnabled(false);
        serveButton.setEnabled(false);
        returnButton.setEnabled(false);


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
            int tableIndex =i;
            JButton tableAreaButton = tableAreaButtons[i];
            tableAreaButton.addActionListener(e -> tableButtonClicked(tableAreaButton, tableIndex));
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

        playBackgroundMusic("assets\\game_background.wav");
    }

    private void trashButtonClicked() {
        serving = false;
        // Disable kitchen buttons
        for (JButton kitchenButton : kitchenAreaButtons) {
            kitchenButton.setEnabled(true);
        }
        inventoryButton.setEnabled(true);
        serveButton.setEnabled(false);
        returnButton.setEnabled(false);
        trashButton.setEnabled(false);
        gameLogic.trashItemsFromStand();
    }

    private void serveButtonClicked() {
        serving = true;
        // Disable kitchen buttons
        for (JButton kitchenButton : kitchenAreaButtons) {
            kitchenButton.setEnabled(false);
        }
        inventoryButton.setEnabled(false);
        returnButton.setEnabled(false);
        // Add other control buttons here if necessary
    }

    private void inventoryButtonClicked() {
        if (inventoryOpened){
            for (int i = 0; i < kitchenAreaButtons.length; i++) {
                kitchenAreaButtons[i].setText(GameLogic.MENU_ITEMS.get(i));
            }
            revalidate();
            repaint();
        }
        else {
            drawInventory();
        }
        inventoryOpened=!inventoryOpened;
    }

    public void drawInventory() {
        List<Integer> numbers = gameLogic.inventoryOpened();
        for (int i = 0; i < kitchenAreaButtons.length; i++) {
            String txt ="<html>" + GameLogic.MENU_ITEMS.get(i)+"<br>Inventory: "+numbers.get(i)+"<br>Buy for $"+GameLogic.ITEM_PRICES.get(i)+"</html>";
            kitchenAreaButtons[i].setText(txt);
        }
        revalidate();
        repaint();
    }

    private void kitchenAreaButtonClicked(JButton kitchenAreaButton) {
        if(inventoryOpened){

            String htmlString = kitchenAreaButton.getText();

            // Extract itemName
            int startIndex = htmlString.indexOf(">") + 1;
            int endIndex = htmlString.indexOf("<br>");
            String itemName = htmlString.substring(startIndex, endIndex);

            // Extract price
            String pricePrefix = "Buy for $";
            startIndex = htmlString.lastIndexOf(pricePrefix) + pricePrefix.length();
            endIndex = htmlString.indexOf("</html>");
            String itemPriceString = htmlString.substring(startIndex, endIndex);
            // Cast itemPrice to float
            float itemPrice = Float.parseFloat(itemPriceString.trim());
            gameLogic.buyItem(itemName,itemPrice);
        }
        else{
            String itemName = kitchenAreaButton.getText();
            gameLogic.addItemToStand(itemName);
        }
    }

    public void addItemToStand(String item) {
        trashButton.setEnabled(true);
        serveButton.setEnabled(true);
        returnButton.setEnabled(true);

        JLabel itemLabel = new JLabel(item);
        itemListPanel.add(itemLabel);
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private void returnButtonClicked() {
        trashButton.setEnabled(false);
        serveButton.setEnabled(false);
        returnButton.setEnabled(false);
        gameLogic.removeItemsFromStand();
    }


    private void tableButtonClicked(JButton tableAreaButton, int tableIndex) {

        String msg="";
        if (tableAreaButton.getBackground().equals(TABLE_EMPTY_COLOR)){
            msg="Table is empty.\n";
            sendChatMessage(msg);
        } else if (tableAreaButton.getBackground().equals(TABLE_THINKING_COLOR)) {
            Customer customer=restaurantManager.getCustomerAtTable(tableIndex);
            msg=customer.getName()+" thinking";
            sendChatMessage(msg);
        }else if (tableAreaButton.getBackground().equals(TABLE_ORDERED_COLOR)) {
            if (serving){
                gameLogic.serveToTable(tableIndex);
                trashButtonClicked();
            }
            else {
                Customer customer = restaurantManager.getCustomerAtTable(tableIndex);
                msg = customer.getName() + " ordered: " + customer.getOrder().getOrderString();
                sendChatMessage(msg);
            }
        }else if (tableAreaButton.getBackground().equals(TABLE_EATING_COLOR)) {
            Customer customer = restaurantManager.getCustomerAtTable(tableIndex);
            Order order = customer.getOrder();
            Order served = customer.getServed();
            msg = customer.getName()+" ordered: "+order.getOrderString()+"\n   Received: "+served.getOrderString();
            sendChatMessage(msg);
        }else if (tableAreaButton.getBackground().equals(TABLE_WAITING_TO_LEAVE_COLOR)) {
            gameLogic.collectMoneyFromTable(tableIndex);
        }

    }

    public void tableNewCustomer(Customer customer, int tableIndex) {
        JButton tableButton = tableAreaButtons[tableIndex];
        tableButton.setBackground(TABLE_THINKING_COLOR);
        tableButton.setText(
                "<html>" + customer.getName() + "<br>Satisfaction: " + customer.getSatisfaction() * 100 + "%</html>");
        sendChatMessage(customer.getName() + " have arrived.\n");
    }

    public void updateCustomerSatisfaction(Customer customer, int tableIndex) {
        JButton tableButton = tableAreaButtons[tableIndex];
        tableButton.setText(
                "<html>" + customer.getName() + "<br>Satisfaction: " + customer.getSatisfaction() * 100 + "%</html>");
        updateCollectiveSatisfaction();
    }

    private void updateCollectiveSatisfaction() {
        float totalSatisfaction = gameLogic.calculateCollectiveSatisfaction();
        collectiveSatisfactionLabel.setText("Collective Satisfaction: " + totalSatisfaction * 100 + "%");
        revalidate();
        repaint();
    }

    public void tableOrderGiven(Customer customer, int tableIndex, Order order) {
        String orderMessage = customer.getName()+" Order: "+order.getOrderString();
        sendChatMessage(orderMessage);
        String txt ="<html>" + customer.getName()+"<br>Ordered</html>";

        tableAreaButtons[tableIndex].setText(txt);
        tableAreaButtons[tableIndex].setBackground(TABLE_ORDERED_COLOR);
    }


    private void saveButtonClicked() {
        GameState gameState = gameLogic.createGameState();
        SaveLoadSystem saveLoadSystem = new SaveLoadSystem();
        saveLoadSystem.saveGame(gameState, "savegame.dat");
        sendChatMessage("Game saved successfully.");
    }

    private void resumeButtonClicked() {
        playBackgroundMusic("assets/game_background.wav");
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

        stopBackgroundMusic();

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
    public void sendChatMessage(String text){
        if (!text.endsWith("\n")){
            text=text+"\n";
        }
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

    public void removeItemsFromStand() {
        if (itemListPanel.getComponentCount() > 0) {
            itemListPanel.removeAll();
            itemListPanel.revalidate();
            itemListPanel.repaint();
        }
    }

    public void tableEating(Customer customer, int tableIndex) {
        Order order = customer.getOrder();
        Order served = customer.getServed();
        String msg = customer.getName()+" ordered: "+order.getOrderString()+"\n   Received: "+served.getOrderString();
        sendChatMessage(msg);
        String txt ="<html>" + customer.getName()+"<br>Eating</html>";
        tableAreaButtons[tableIndex].setText(txt);
        tableAreaButtons[tableIndex].setBackground(TABLE_EATING_COLOR);
    }

    public void tableFinished(Customer customer, int tableIndex) {
        String msg = customer.getName()+" ready to pay";
        sendChatMessage(msg);
        String txt ="<html>" + customer.getName()+"<br>Finished</html>";

        tableAreaButtons[tableIndex].setText(txt);
        tableAreaButtons[tableIndex].setBackground(TABLE_WAITING_TO_LEAVE_COLOR);
    }

    public void updateMoney(float money) {
        moneyLabel.setText("Money: $" +money);
        revalidate();
        repaint();

    }

    public void tableEmptied(Customer customer, int tableIndex) {
        String msg = customer.getName()+" paid "+customer.getPayment()+" and left";
        sendChatMessage(msg);
        tableAreaButtons[tableIndex].setText("Table "+tableIndex+1);
        tableAreaButtons[tableIndex].setBackground(TABLE_EMPTY_COLOR);
    }


    private void playBackgroundMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }
}
