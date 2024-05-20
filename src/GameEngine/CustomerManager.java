package GameEngine;

import Restaurant.Customer;
import Restaurant.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomerManager {
    private static final List<String> MENU_ITEMS = Arrays.asList("Burger", "Pasta", "Salad", "Pizza", "Soup");
    private static final List<Float> ITEM_PRICES = Arrays.asList(10.0f, 12.0f, 8.0f, 15.0f, 7.0f);

    public void addCustomer(Customer customer) {
        // Placeholder for adding a customer
        System.out.println("Customer added!");
    }

    public void removeCustomer(Customer customer) {
        // Placeholder for removing a customer
        System.out.println("Customer removed!");
    }

    public Order generateRandomOrder(Customer customer) {
        Random random = new Random();
        Order order = new Order(customer);
        int itemsCount = random.nextInt(3) + 1; // 1 to 3 items
        for (int i = 0; i < itemsCount; i++) {
            int itemIndex = random.nextInt(MENU_ITEMS.size());
            order.addItem(MENU_ITEMS.get(itemIndex), ITEM_PRICES.get(itemIndex));
        }
        return order;
    }

    public float checkOrder(String item, Order order, Customer customer) {
        boolean itemFound = false;
        float total = 0.0f;

        for (String orderedItem : order.getItems()) {
            if (orderedItem.equals(item)) {
                itemFound = true;
                total += ITEM_PRICES.get(MENU_ITEMS.indexOf(orderedItem));
            }
        }

        if (!itemFound) {
            // Decrease satisfaction if the item not found
            float satisfaction = customer.getSatisfaction();
            satisfaction -= 0.1f; // Decrease by 10% (can be modified)
            if (satisfaction < 0) {
                satisfaction = 0;
            }
            customer.setSatisfaction(satisfaction);
        }
        return total;
    }

}
