package GameEngine;

import Restaurant.Item;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<Item, Integer> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
        // Add items to the inventory
        inventory.put(new Item("Meat"),5);
        inventory.put(new Item("Cheese"),5);
        inventory.put(new Item("Lettuce"),5);
        inventory.put(new Item("Tomato"),5);
        inventory.put(new Item("Dough"),5);
        inventory.put(new Item("Pepperoni"),5);
        // Add more items as needed
    }

    public Integer checkItem(String itemName) {

        for (Item item : inventory.keySet()) {
            if (item.getName().equals(itemName)) {
                return inventory.get(item);
            }
        }
        return 0;
    }

    public Item getItem(String itemName) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equals(itemName) & inventory.get(item)>0) {
                removeItem(item);
                return item;
            }
        }
        return new Item("null");
    }

    public void addItem(String itemName) {
        for (Item item : inventory.keySet()) {
            if (item.getName().equals(itemName)) {
                inventory.put(item, inventory.get(item) + 1);
                System.out.println("Item added!");
            }
        }
    }

    public void removeItem(Item item) {
        if (inventory.get(item)>0)
        {
            inventory.put(item, inventory.get(item) - 1);
            System.out.println("Item removed!");

        }
    }

}
