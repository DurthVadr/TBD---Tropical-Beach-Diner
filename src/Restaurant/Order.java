package Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private List<Item> items;
    private String name;
    private Customer customer;
    private float totalPrice=0.0f;


    public Order(){
        this.items = new ArrayList<>();
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void addItem(Item item, float price) {
        items.add(item);
        totalPrice += price;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public String getOrderString(){
        StringBuilder orderString = new StringBuilder("");
        for (Item item : items) {
            orderString.append(item.getName()).append(" ");
        }
        return orderString.toString();
    }
    public float getTotalPrice() {
        return totalPrice;
    }
}
