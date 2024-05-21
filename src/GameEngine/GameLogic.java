package GameEngine;

import GUI.GameScreen;
import Persistence.GameState;
import Restaurant.Customer;
import Restaurant.Item;
import Restaurant.Order;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class GameLogic {

    private float money=10f;
    public static final List<String> MENU_ITEMS = Arrays.asList("Meat", "Cheese", "Lettuce", "Tomato", "Dough", "Pepperoni");
    public static final List<Float> ITEM_PRICES = Arrays.asList(10.0f, 12.0f, 8.0f, 15.0f, 7.0f,5.0f);


    private final GameScreen gameScreen;
    private final CustomerManager customerManager;
    private final InventoryManager inventoryManager;
    private final RestaurantManager restaurantManager;
    private final TimeManager timeManager;
    private List<Item> standItems;

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public GameLogic(CustomerManager customerManager, InventoryManager inventoryManager,
                     RestaurantManager restaurantManager, TimeManager timeManager, GameScreen gameScreen) {
        this.customerManager = customerManager;
        this.inventoryManager = inventoryManager;
        this.restaurantManager = restaurantManager;
        this.timeManager = timeManager;
        this.gameScreen = gameScreen;
        this.standItems = new ArrayList<>();

    }

    public void startNewGame() {
        // Placeholder for starting a new game
        System.out.println("New game started!");
        gameScreen.setGameLogic(this);
        restaurantManager.setGameLogic(this);
        gameScreen.initialize();
        gameScreen.setVisible(true);
        startCustomerArrival();
    }


    public void exitGame() {
        // Placeholder for starting a new game
        System.out.println("Game exited!");
    }

    private void startCustomerArrival() {
        System.out.println("In Start Customer");
        Timer customerTimer = new Timer();
        Random random = new Random();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!timeManager.isPaused()) {
                    SwingUtilities.invokeLater(() -> {
                        int tableIndex = restaurantManager.findAvailableTable();
                        if (tableIndex != -1) {
                            Customer customer = customerManager.createCustomer(tableIndex);
                            restaurantManager.addCustomerToTable(tableIndex, customer);
                        }
                    });
                    // Schedule the next customer
                    int delay = (random.nextInt(6) + 5) * 1000;  // Random delay between 5 and 10 seconds
                    customerTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startCustomerArrival();
                        }
                    }, delay);
                }
            }
        };
        task.run();  // Start the first customer
    }

    public void scheduleCustomerOrder(Customer customer, int tableIndex) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> gaveOrder(customer, tableIndex));
            }
        }, 2000); // 10 seconds delay for customers to think
    }

    private void gaveOrder(Customer customer, int tableIndex) {
        Order order = new Order(customer);
        Random random = new Random();
        int noOfItems=random.nextInt(1,4);
        for (int i=0;i<noOfItems;i++){
            int itemIndex = random.nextInt(MENU_ITEMS.size());
            Item item = new Item(MENU_ITEMS.get(itemIndex));
            float itemPrice = ITEM_PRICES.get(itemIndex);
            order.addItem(item, itemPrice);
        }
        customer.setOrder(order);
        gameScreen.tableOrderGiven(customer,tableIndex,order);
    }

    public void addItemToStand(String itemName) {
        Item item = inventoryManager.getItem(itemName);
        if (!item.getName().equals("null")){
            standItems.add(item);
            gameScreen.addItemToStand(itemName);  // Use the button's text as the item
        }
    }

    public void removeItemsFromStand() {
        if(!standItems.isEmpty()){
            while (!standItems.isEmpty()) {
                Item removedItem=standItems.remove(0);
                inventoryManager.addItem(removedItem.getName());
            }
            gameScreen.removeItemsFromStand();
        }
    }

    public void serveToTable(int tableIndex) {
        Customer customer = restaurantManager.getCustomerAtTable(tableIndex);

        Order serve = new Order();
        for (Item item: standItems){
            serve.addItem(item);
        }
        customer.serve(serve);
        float pay = customerManager.checkOrder(customer);
        customer.setPayment(pay);
        // Handle payment logic here, e.g., updating player's money
        // Clear stand items after serving
        customerEating(customer,tableIndex);
    }

    public void customerEating(Customer customer, int tableIndex) {
        gameScreen.tableEating(customer,tableIndex);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> customerFinished(customer, tableIndex));
            }
        }, 5000); // 10 seconds delay for customers to think
    }

    private void customerFinished(Customer customer, int tableIndex) {
        gameScreen.tableFinished(customer,tableIndex);
    }

    public void trashItemsFromStand() {
        standItems.clear();
        gameScreen.removeItemsFromStand();
    }

    public void collectMoneyFromTable(int tableIndex) {
        Customer customer = restaurantManager.getCustomerAtTable(tableIndex);
        money+=customer.getPayment();
        gameScreen.updateMoney(money);
        restaurantManager.removeCustomerFromTable(tableIndex);
        gameScreen.tableEmptied(customer, tableIndex);
    }

    public List<Integer> inventoryOpened() {
        List<Integer> numbers = new ArrayList<>();
        for (String menuItem : MENU_ITEMS) {
            Item item = new Item(menuItem);
            numbers.add(inventoryManager.checkItem(item.getName()));
        }
        return numbers;
    }

    public void buyItem(String itemName, float price) {
        if (money>=price){
            inventoryManager.addItem(itemName);
            money-=price;
            gameScreen.drawInventory();
            gameScreen.updateMoney(money);
        }
        else{
            gameScreen.sendChatMessage("Not enough money.");
        }
    }


    public float getMoney() {
        return money;
    }


    public GameState createGameState() {
        GameState gameState = new GameState();
        gameState.setInventory(inventoryManager.getInventory());
        gameState.setCustomers(restaurantManager.getTableCustomerMap());
        gameState.setMoney(money);
        gameState.setTimeRemaining(timeManager.getTotalTime());
        return gameState;
    }

    public void loadGameState(GameState gameState) {
        inventoryManager.setInventory(gameState.getInventory());
        restaurantManager.setCustomers(gameState.getCustomers());
        money = gameState.getMoney();
        timeManager.setTotalTime(gameState.getTimeRemaining());
        gameScreen.revalidate();
        gameScreen.repaint();
        startNewGame();
    }

}
