package GameEngine;

public class GameLogic {
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;
    private RestaurantManager restaurantManager;
    private TimeManager timeManager;

    public GameLogic(CustomerManager customerManager, InventoryManager inventoryManager,
                     RestaurantManager restaurantManager) {
        this.customerManager = customerManager;
        this.inventoryManager = inventoryManager;
        this.restaurantManager = restaurantManager;
    }

    public void startNewGame() {
        // Placeholder for starting a new game
        System.out.println("New game started!");
    }
    public void exitGame() {
        // Placeholder for starting a new game
        System.out.println("Game exited!");
    }
}
