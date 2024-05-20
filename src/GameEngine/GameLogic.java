package GameEngine;

import GUI.GameScreen;
import Restaurant.Customer;
import Restaurant.Order;

import javax.swing.*;
import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private final GameScreen gameScreen;
    private final CustomerManager customerManager;
    private final InventoryManager inventoryManager;
    private final RestaurantManager restaurantManager;
    private final TimeManager timeManager;

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
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
        }, 10000); // 10 seconds delay for customers to think
    }

    private void gaveOrder(Customer customer, int tableIndex) {
        Order order = customerManager.generateRandomOrder(customer);
        //JOptionPane.showMessageDialog(this, orderMessage.toString(), "Customer Order", JOptionPane.INFORMATION_MESSAGE);
        gameScreen.orderGiven(customer,tableIndex,order);
    }
}
