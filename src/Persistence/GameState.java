package Persistence;
import Restaurant.Customer;
import Restaurant.Item;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Item, Integer> inventory;
    private Map<Integer, Customer> customers;
    private float money;
    private int timeRemaining;

    // Getters and setters for each field
    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Item, Integer> inventory) {
        this.inventory = inventory;
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<Integer, Customer> customers) {
        this.customers = customers;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
