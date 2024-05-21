package Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {
    private Inventory inventory;
    private List<Customer> customers;

    public Restaurant() {
        this.inventory = new Inventory();
        this.customers = new ArrayList<>();
    }

    public Customer createCustomer(String name, float satisfaction) {
        Customer newCustomer = new Customer(name, satisfaction);
        customers.add(newCustomer);
        return newCustomer;
    }

    public Order createOrder(Customer customer) {
        if (customer != null) {
            Order newOrder = new Order(customer);
            customer.setOrder(newOrder);
            return newOrder;
        }
        return null;
    }

    public void openInventory() {
        // Logic to display or manage inventory
        // For example, printing out the inventory items
        System.out.println(inventory);
    }

    public List<Item> prepareOrder(Item... items) {
        List<Item> preparedItems = new ArrayList<>();
        for (Item item : items) {
            // Check if item is available in inventory and prepare it
            if (inventory.hasItem(item)) {
                inventory.removeItem(item);
                preparedItems.add(item);
            }
        }
        return preparedItems;
    }

    public float serve(Item item, Customer customer) {
        if (item != null && customer != null) {
            // Logic to serve the item to the customer
            customer.receiveItem(item);
            return item.getPrice(); // Return the price for billing purposes
        }
        return 0;
    }

    // Getters and setters for inventory and customers if needed
    public Inventory getInventory() {
        return inventory;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
