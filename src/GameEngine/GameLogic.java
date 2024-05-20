package GameEngine;

import java.sql.Time;

public class GameLogic {
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
                     RestaurantManager restaurantManager, TimeManager timeManager) {
        this.customerManager = customerManager;
        this.inventoryManager = inventoryManager;
        this.restaurantManager = restaurantManager;
        this.timeManager = timeManager;
    }

    public void startNewGame() {
        // Placeholder for starting a new game
        System.out.println("New game started!");
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

    

    
}
