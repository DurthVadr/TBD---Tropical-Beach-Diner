package Restaurant;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private float price;
    private int quantity;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
