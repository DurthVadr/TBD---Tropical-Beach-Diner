import GUI.Controller;
import GUI.GameScreen;
import GUI.MainMenu;
import GameEngine.CustomerManager;
import GameEngine.GameLogic;
import GameEngine.InventoryManager;
import GameEngine.RestaurantManager;
import GameEngine.TimeManager;

import java.sql.Time;

public class Main {
    public static void main(String[] args) {

        // Initialize the GUI components
        MainMenu mainMenu = new MainMenu();

        // Show the main menu initially
        mainMenu.setVisible(true);
    }
}
