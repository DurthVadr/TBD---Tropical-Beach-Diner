package GameEngine;

import Restaurant.Customer;
import Restaurant.Item;
import Restaurant.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomerManager {
    private static final List<String> MENU_ITEMS = Arrays.asList("Burger", "Cheeseburger", "Salad", "Pizza", "Vegan Pizza");
    private static final List<Float> ITEM_PRICES = Arrays.asList(10.0f, 12.0f, 8.0f, 15.0f, 7.0f);

    public void addCustomer(Customer customer) {
        // Placeholder for adding a customer
        System.out.println("Customer added!");
    }

    public void removeCustomer(Customer customer) {
        // Placeholder for removing a customer
        System.out.println("Customer removed!");
    }


    public float checkOrder(String item, Order order, Customer customer) {
        boolean itemFound = false;
        float total = 0.0f;

        for (Item orderedItem : order.getItems()) {
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

    public Customer createCustomer(int tableIndex) {
        int customerCount = new Random().nextInt(4) + 1; // 1 to 4 customers
        return new Customer("C" + (tableIndex + 1), 1.0f);
    }

    public float checkOrder(Customer customer) {

        List<Item> orderItems = new ArrayList<>(customer.getOrder().getItems());
        List<Item> servedItems = new ArrayList<>(customer.getServed().getItems());

        float totalPrice = customer.getOrder().getTotalPrice();

        for (Item servedItem : servedItems) {
            boolean itemMatched = false;
            for (Item orderItem : orderItems) {
                if (servedItem.getName().equals(orderItem.getName())) {
                    totalPrice += orderItem.getPrice();
                    orderItems.remove(orderItem);
                    itemMatched = true;
                    break;
                }
            }
            if (!itemMatched) {
                totalPrice = (float) (totalPrice*0.5);
                customer.setSatisfaction(customer.getSatisfaction() - 0.1f);
            }
        }

        if (!orderItems.isEmpty()) {
            totalPrice = (float) (totalPrice*0.4);
            // Handle the case where some order items are not served
            // For example, you might want to decrease customer satisfaction
            customer.setSatisfaction(customer.getSatisfaction() - 0.1f * orderItems.size());
        }
        return totalPrice;
    }

}
