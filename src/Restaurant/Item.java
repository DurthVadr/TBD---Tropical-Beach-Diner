package Restaurant;

import java.util.Objects;

public class Item {

    private String name;
    private float price;

    public Item(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }


    String type;
    Integer amount;


    public Item(String type, Integer amount) {
        this.type = type;
        this.amount = amount;
    }

    void changeAmount(Integer n) {
        this.amount += n;
    }



    // Override equals and hashCode to correctly compare items
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return Float.compare(item.price, price) == 0 &&
                name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }


}
