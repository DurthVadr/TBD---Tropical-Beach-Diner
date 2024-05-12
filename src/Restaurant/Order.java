package Restaurant;

public class Order {
    ArrayList<Item> orderSpecifics = new ArrayList<Item>();
    Float price;


    public Order(ArrayList<Item> orderSpecifics, Float price) {
        this.orderSpecifics = orderSpecifics;
        this.price = price;
    }
    
}
