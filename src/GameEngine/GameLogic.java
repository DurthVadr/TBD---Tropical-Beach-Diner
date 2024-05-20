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

    //What are these and why they are here?
//<<<<<<< HEAD
    public void exitGame() {
        // Placeholder for starting a new game
        System.out.println("Game exited!");
    }

//What are these and why they are here?
//=======

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

    

    
//What are these and why they are here?
// >>>>>>> b192ceb (Test Folder and example test and another logic starts)
}
