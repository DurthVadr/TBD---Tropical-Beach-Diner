package Restaurant;

import java.io.Serializable;

public class Customer implements Serializable {
    private Order order;
    private Order served;
    private final String name;
    private Float satisfaction;
    private Float payment;

    public void serve(Order served){
        this.served=served;
    }

    public Customer(String name, float satisfaction) {
        this.name = name;
        this.satisfaction = satisfaction;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public Order getServed() {
        return served;
    }

    public String getName() {
        return name;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }

    public void receiveItem(Item item) {
        // Logic for the customer to receive the item
        System.out.println("Customer received: " + item.getName());
    }

    public void setPayment(float pay) {
        this.payment=pay;
    }

    public float getPayment() {
        return payment;
    }
}