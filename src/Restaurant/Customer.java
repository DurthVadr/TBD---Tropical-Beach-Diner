package Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Customer {
    private Order order;
    private Order served;
    private String name;
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