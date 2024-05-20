package Restaurant;

public class Order {

    private Customer customer;
    Float price;

    public Order(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    
}
