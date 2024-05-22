package Tests;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.CustomerManager;
import Restaurant.Customer;
import Restaurant.Item;
import Restaurant.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class CustomerManagerTest {
    private CustomerManager customerManager;

    @BeforeEach
    public void setUp() {
        customerManager = new CustomerManager();
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = customerManager.createCustomer(0);
        assertNotNull(customer, "Customer should not be null");
        assertNotEquals("", customer.getName(), "Customer name should not be empty");
    }

    @Test
    public void testFetchRandomName() {
        String name = customerManager.fetchRandomName();
        assertNotNull(name, "Name should not be null");
        assertTrue(name.contains(" "), "Name should contain a space between first and last name");
    }

    @Test
    public void testCheckOrder() {
        Customer customer = new Customer("John Doe", 1.0f);
        customer.setOrder(new Order(customer));
        customer.getOrder().addItem(new Item("Cheese", 10.0f), 10.0f);
        customer.serve(new Order());
        customer.getServed().addItem(new Item("Cheese", 10.0f));

        float totalPrice = customerManager.checkOrder(customer);
        assertEquals(20.0f, totalPrice, "Total price should account for the ordered item price");
    }
}
