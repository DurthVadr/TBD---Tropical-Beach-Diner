package GameEngine;

import GUI.GameScreen;

import javax.swing.*;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {
    private final GameScreen gameScreen;
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;

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

    private RestaurantManager restaurantManager;
    private TimeManager timeManager;

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
    public void exitGame() {
        // Placeholder for starting a new game
        System.out.println("Game exited!");
    }

    public void endGame() {
        // Placeholder for ending a game
        System.out.println("Game ended!");
    }

    public void pauseGame() {
        // Placeholder for pausing a game
        System.out.println("Game paused!");
    }

    public void resumeGame() {
        // Placeholder for resuming a game
        System.out.println("Game resumed!");
    }


    private void startCustomerArrival() {
        System.out.println("In Start Customer");
        Timer customerTimer = new Timer();
        customerTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!timeManager.isPaused()) {
                    System.out.println("In swing utilities Customer");

                    SwingUtilities.invokeLater(() -> gameScreen.addCustomerToTable());
                }
            }
        }, 0, 5000); // Customers arrive every 10 seconds
    }

    
}
