package GameEngine;

import GUI.GameScreen;
import Restaurant.Customer;

import java.util.HashMap;
import java.util.Map;

public class RestaurantManager {



    private GameLogic gameLogic;
    private GameScreen gameScreen;

    private Map<Integer, Customer> tableCustomerMap;
    private static final int TOTAL_TABLES = 6;

    public RestaurantManager() {
        this.tableCustomerMap = new HashMap<>();
    }
    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.gameScreen = gameLogic.getGameScreen();
    }
    // Add a customer to a specific table
    public void addCustomerToTable(int tableIndex, Customer customer) {
        if (tableIndex < 0 || tableIndex > TOTAL_TABLES) {
            throw new IllegalArgumentException("Invalid table number");
        }
        tableCustomerMap.put(tableIndex, customer);
        System.out.println("Customer added to table " + tableIndex);
        gameScreen.tableNewCustomer(customer, tableIndex);
        gameLogic.scheduleCustomerOrder(customer, tableIndex);
    }

    // Find an available table
    public int findAvailableTable() {
        for (int i = 0; i < TOTAL_TABLES; i++) {
            if (!isTableOccupied(i)) {
                return i;
            }
        }
        return -1; // No available table
    }

    // Remove a customer from a specific table
    public void removeCustomerFromTable(int tableNumber) {
        if (tableCustomerMap.containsKey(tableNumber)) {
            tableCustomerMap.remove(tableNumber);
            System.out.println("Customer left the table " + tableNumber);
        }
    }

    // Get the customer at a specific table
    public Customer getCustomerAtTable(int tableNumber) {
        return tableCustomerMap.get(tableNumber);
    }

    // Check if a table is occupied
    public boolean isTableOccupied(int tableNumber) {
        return tableCustomerMap.containsKey(tableNumber);
    }

    // Get the total number of tables
    public int getTotalTables() {
        return TOTAL_TABLES;
    }



    // Example method to print the status of all tables
    public void printTableStatus() {
        for (int i = 1; i <= TOTAL_TABLES; i++) {
            Customer customer = tableCustomerMap.get(i);
            if (customer != null) {
                System.out.println("Table " + i + ": Occupied by " + customer.getName());
            } else {
                System.out.println("Table " + i + ": Empty");
            }
        }
    }
}
