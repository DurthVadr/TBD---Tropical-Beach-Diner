package GameEngine;

import Restaurant.Customer;
import Restaurant.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerManager {

    public Customer createCustomer(int tableIndex) {
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
