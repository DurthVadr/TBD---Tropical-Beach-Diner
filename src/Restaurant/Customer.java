package Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Customer {
    private Order order;
    private String name;
    private Float satisfaction;
    private static final List<String> MENU_ITEMS = Arrays.asList("Burger", "Pasta", "Salad", "Pizza", "Soup");
    private static final List<Float> ITEM_PRICES = Arrays.asList(10.0f, 12.0f, 8.0f, 15.0f, 7.0f);

    public Customer() {
        this.name = "Default Name";
        this.satisfaction = 1.0f; // Default satisfaction level
    }

    public Customer(String name, float satisfaction) {
        this.name = name;
        this.satisfaction = satisfaction;
        generateRandomOrder();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void receiveItem(Item item) {
        // Logic for the customer to receive the item
        System.out.println("Customer received: " + item.getName());
    }

    public Order getOrder() {
        return order;
    }

    public float checkOrder(String item, Order order) {
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
            this.satisfaction -= 0.1f; // Decrease by 10% (can be modified)
            if (this.satisfaction < 0) {
                this.satisfaction = 0;
            }
        }
        return total;
    }

    void giveOrder(Order order) {
        //TODO: Implement
    }

    public Order generateRandomOrder() {
        Random random = new Random();
        Order order = new Order(this);
        int itemsCount = random.nextInt(3) + 1; // 1 to 3 items
        for (int i = 0; i < itemsCount; i++) {
            int itemIndex = random.nextInt(MENU_ITEMS.size());
            order.addItem(MENU_ITEMS.get(itemIndex), ITEM_PRICES.get(itemIndex));
        }
        this.order = order;
        return order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }
}
