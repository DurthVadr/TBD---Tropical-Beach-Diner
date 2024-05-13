package Restaurant;

public class Item {
    String type;
    Integer amount;


    public Item(String type, Integer amount) {
        this.type = type;
        this.amount = amount;
    }

    changeAmount(Integer n) {
        this.amount += n;
    }

}
