
package Restaurant;

import java.lang.reflect.Array;

public class Inventory {
    ArrayList<Item> items = new ArrayList<Item>();


    public Inventory(ArrayList<Item> items) {
        this.items = items;
    }
    
    Item buyItem(Item item) {
        items.add(item);
        return item;
    }

    Item takeItem(Item item) {
        items.remove(item);
        return item;
    }
}
