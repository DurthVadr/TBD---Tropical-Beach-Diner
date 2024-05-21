package Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Customer customer;
    private List<String> items;
    private float totalPrice;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0f;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addItem(String item, float price) {
        items.add(item);
        totalPrice += price;
    }

    public List<String> getItems() {
        return items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
