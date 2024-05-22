package Tests;

import static org.junit.jupiter.api.Assertions.*;

import GUI.GameScreen;
import GameEngine.*;
import Restaurant.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLogicTest {
    private GameLogic gameLogic;
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;
    private RestaurantManager restaurantManager;
    private TimeManager timeManager;
    private GameScreen gameScreen;

    @BeforeEach
    public void setUp() {
        customerManager = new CustomerManager();
        inventoryManager = new InventoryManager();
        restaurantManager = new RestaurantManager();
        timeManager = new TimeManager();
        gameScreen = new GameScreen();
        gameLogic = new GameLogic(customerManager, inventoryManager, restaurantManager, timeManager, gameScreen);
    }

    @Test
    public void testStartNewGame() {
        assertDoesNotThrow(() -> gameLogic.startNewGame());
    }

    @Test
    public void testAddItemToStand() {
        gameLogic.addItemToStand("Cheese");
        assertEquals(1, gameLogic.getStandItems().size(), "Stand should have one item after adding");
    }

    @Test
    public void testServeToTable() {
        Customer customer = new Customer("Test Customer", 1.0f);
        restaurantManager.addCustomerToTable(0, customer);
        gameLogic.serveToTable(0);
        assertNotNull(customer.getServed(), "Customer should have a served order");
    }
}
