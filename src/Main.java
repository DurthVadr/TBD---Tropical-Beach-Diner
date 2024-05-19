import GUI.Controller;
import GUI.GameScreen;
import GUI.MainMenu;
import GameEngine.CustomerManager;
import GameEngine.GameLogic;
import GameEngine.InventoryManager;
import GameEngine.RestaurantManager;
import GameEngine.TimeManager;

public class Main {
    public static void main(String[] args) {
        // Initialize the game components
        CustomerManager customerManager = new CustomerManager();
        InventoryManager inventoryManager = new InventoryManager();
        RestaurantManager restaurantManager = new RestaurantManager(customerManager, inventoryManager);
        TimeManager timeManager = new TimeManager();

        // Initialize the game logic
        GameLogic gameLogic = new GameLogic(customerManager, inventoryManager, restaurantManager, timeManager);

        // Initialize the GUI components
        MainMenu mainMenu = new MainMenu(gameLogic);
        GameScreen gameScreen = new GameScreen(gameLogic);
        Controller controller = new Controller(mainMenu, gameScreen, gameLogic);

        // Start the game
        controller.startGame();
    }
}
