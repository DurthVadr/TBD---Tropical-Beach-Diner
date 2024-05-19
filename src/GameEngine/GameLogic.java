package GameEngine;

public class GameLogic {
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;
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
}
