public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer("Cemil", 1.0f); //initial satisfaction can be 1 or 0 ?

        Item burger = new Item("Big Mac", 1);
        Item fries = new Item("Large Fries", 1);
        Item coke = new Item("Coke", 2);

        List<Item> itemsOrdered = new ArrayList<>();
        itemsOrdered.add(burger);
        itemsOrdered.add(fries);
        itemsOrdered.add(coke);

        System.out.println("Customer Name: " + customer.getName());
    }
}
