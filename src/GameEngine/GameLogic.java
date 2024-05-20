package GameEngine;

import GUI.GameScreen;
import Restaurant.Customer;

import javax.swing.*;
import java.sql.Time;
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
        gameScreen.setCustomerManager(this.customerManager);
        gameScreen.setTimeManager(this.timeManager);
        gameScreen.initialize();
        gameScreen.setVisible(true);
        startCustomerArrival();
    }

    //What are these and why they are here?
//<<<<<<< HEAD
    public void exitGame() {
        // Placeholder for starting a new game
        System.out.println("Game exited!");
    }

//What are these and why they are here?
//=======

    private void startCustomerArrival() {
        System.out.println("In Start Customer");
        Timer customerTimer = new Timer();
        customerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!timeManager.isPaused()) {
                    SwingUtilities.invokeLater(() -> {
                        int tableIndex = gameScreen.findAvailableTable();
                        if (tableIndex != -1) {
                            Customer customer = customerManager.createCustomer(tableIndex);
                            gameScreen.addCustomerToTable(customer, tableIndex);
                        }
                    });
                }
            }
        }, 0, 1000); // Customers arrive every 5 seconds
    }

    
//What are these and why they are here?
// >>>>>>> b192ceb (Test Folder and example test and another logic starts)
}
