package GameEngine;

import Restaurant.Customer;
import Restaurant.Item;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CustomerManager {

    private static final String RANDOM_USER_API_URL = "https://randomuser.me/api/?inc=name";

    public Customer createCustomer(int tableIndex) {
        String customerName = fetchRandomName();
        return new Customer(customerName, 1.0f);
    }

    public String fetchRandomName() {
        HttpURLConnection connection = null;
        Scanner scanner = null;
        try {
            URL url = new URL(RANDOM_USER_API_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }

                // Debugging: Print the response
                System.out.println("JSON Response: " + response.toString());

                // Parse the JSON response using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                JsonNode resultsNode = rootNode.path("results").get(0);
                JsonNode nameNode = resultsNode.path("name");
                String firstName = nameNode.path("first").asText();
                String lastName = nameNode.path("last").asText();
                return firstName + " " + lastName;
            } else {
                System.out.println("Error fetching random name: " + responseCode);
                return "Customer" + new Random().nextInt(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Customer" + new Random().nextInt(1000);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
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
