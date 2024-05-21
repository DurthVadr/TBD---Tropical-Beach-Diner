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
    private float money = 10f;
    public static final List<String> MENU_ITEMS = Arrays.asList("Meat", "Cheese", "Lettuce", "Tomato", "Dough",
            "Pepperoni");
    public static final List<Float> ITEM_PRICES = Arrays.asList(10.0f, 12.0f, 8.0f, 15.0f, 7.0f, 5.0f);

    private final GameScreen gameScreen;
    private final CustomerManager customerManager;
    private final InventoryManager inventoryManager;
    private final RestaurantManager restaurantManager;
    private final TimeManager timeManager;
    private final List<Item> standItems;

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
        this.gameScreen = gameScreen;
        this.customerManager = customerManager;
        this.inventoryManager = inventoryManager;
        this.restaurantManager = restaurantManager;
        this.timeManager = timeManager;

        this.gameScreen.setGameLogic(this);
        this.restaurantManager.setGameLogic(this);
        this.timeManager.setGameLogic(this);

        this.standItems = new ArrayList<>();
    }

    public void startNewGame() {
        System.out.println("New game started!");
        gameScreen.initialize();
        gameScreen.setVisible(true);
        startCustomerArrival();
        startSatisfactionReduction();
    }

    void startCustomerArrival() {

        int delay = timeManager.getSpecifiedTime("start");

        Timer customerTimer = new Timer();
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
                    customerTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startCustomerArrival();
                        }
                    }, delay);
                }
            }
        };
        task.run(); // Start the first customer
    }

    private void startSatisfactionReduction() {
        Timer satisfactionTimer = new Timer();
        satisfactionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!timeManager.isPaused()) {
                    SwingUtilities.invokeLater(() -> {
                        for (int i = 0; i < restaurantManager.getTableCount(); i++) {
                            Customer customer = restaurantManager.getCustomerAtTable(i);
                            if (customer != null) {
                                customerManager.reduceSatisfactionOverTime(customer, 1);
                                gameScreen.updateCustomerSatisfaction(customer, i);
                            }
                        }
                    });
                }
            }
        }, 0, 60000); // Decrease satisfaction every 60 seconds
    }


   
    public void scheduleCustomerOrder(Customer customer, int tableIndex) {
        int delay = timeManager.getSpecifiedTime("customerOrder");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> gaveOrder(customer, tableIndex));
            }
        }, delay);
    }

    private void gaveOrder(Customer customer, int tableIndex) {
        Order order = new Order(customer);
        Random random = new Random();
        int noOfItems = random.nextInt(1, 4);
        for (int i = 0; i < noOfItems; i++) {
            int itemIndex = random.nextInt(MENU_ITEMS.size());
            Item item = new Item(MENU_ITEMS.get(itemIndex));
            float itemPrice = ITEM_PRICES.get(itemIndex);
            order.addItem(item, itemPrice);
        }
        customer.setOrder(order);
        gameScreen.tableOrderGiven(customer, tableIndex, order);
    }

    public void addItemToStand(String itemName) {
        Item item = inventoryManager.getItem(itemName);
        if (!item.getName().equals("null")) {
            standItems.add(item);
            gameScreen.addItemToStand(itemName); // Use the button's text as the item
        }
    }

    public void removeItemsFromStand() {
        if (!standItems.isEmpty()) {
            while (!standItems.isEmpty()) {
                Item removedItem = standItems.remove(0);
                inventoryManager.addItem(removedItem.getName());
            }
            gameScreen.removeItemsFromStand();
        }
    }

    public void serveToTable(int tableIndex) {
        Customer customer = restaurantManager.getCustomerAtTable(tableIndex);
        Order serve = new Order();
        for (Item item : standItems) {
            serve.addItem(item);
        }
        customer.serve(serve);
        float pay = customerManager.checkOrder(customer);
        customer.setPayment(pay);
        customerEating(customer, tableIndex);
    }

    public void customerEating(Customer customer, int tableIndex) {
        int delay = timeManager.getSpecifiedTime("eatingTime");
        gameScreen.tableEating(customer, tableIndex);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> customerFinished(customer, tableIndex));
            }
        }, delay);
    }

    private void customerFinished(Customer customer, int tableIndex) {
        gameScreen.tableFinished(customer, tableIndex);
    }

    public void collectMoneyFromTable(int tableIndex) {
        Customer customer = restaurantManager.getCustomerAtTable(tableIndex);
        money += customer.getPayment();
        restaurantManager.removeCustomerFromTable(tableIndex);
        gameScreen.tableEmptied(customer, tableIndex);
    }
    
    private void tableEmptied(Customer customer, int tableIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tableEmptied'");
    }

    public float calculateCollectiveSatisfaction() {
    List<Customer> customers = customerManager.getCustomers();
    float totalSatisfaction = 0;
    for (Customer customer : customers) {
        totalSatisfaction += customer.getSatisfaction();
    }
    return totalSatisfaction / customers.size();
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
    if (money >= price) {
        inventoryManager.addItem(itemName);
        money -= price;
        gameScreen.drawInventory();
        gameScreen.updateMoney(money);
    } else {
        gameScreen.sendChatMessage("Not enough money.");
    }
}

public void trashItemsFromStand() {
    standItems.clear();
    gameScreen.removeItemsFromStand();
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


public void exitGame() {
    gameScreen.dispose();
    System.exit(0);
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
