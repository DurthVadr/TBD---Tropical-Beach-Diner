package GameEngine;

import Restaurant.Customer;
import Restaurant.Item;


import java.util.ArrayList;
import java.util.List;
import com.github.javafaker.Faker;


public class CustomerManager {

    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/?inc=name";

    public Customer createCustomer(int tableIndex) {
        String customerName = fetchRandomName();
        return new Customer(customerName, 1.0f);
    }

    public String fetchRandomName() {
        Faker faker = new Faker();

        String name = faker.name().fullName();
        return name;
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
                totalPrice = (float) (totalPrice * 0.5);
                customer.setSatisfaction(customer.getSatisfaction() - 0.1f);
            }
        }

        if (!orderItems.isEmpty()) {
            totalPrice = (float) (totalPrice * 0.4);
            customer.setSatisfaction(customer.getSatisfaction() - 0.1f * orderItems.size());
        }
        return totalPrice;
    }

    public void reduceSatisfactionOverTime(Customer customer, float amount) {
        customer.setSatisfaction(customer.getSatisfaction() - amount);
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            customers.add(createCustomer(i));
        }
        return customers;
    }
}
