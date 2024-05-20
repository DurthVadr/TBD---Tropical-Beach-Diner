package Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class Customer {
    private Order order;
    private String name;
    private Float satisfaction;



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

    }