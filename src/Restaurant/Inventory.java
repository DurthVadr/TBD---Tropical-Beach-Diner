
package Restaurant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {


    private Map<Item, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public boolean hasItem(Item item) {
        return items.getOrDefault(item, 0) > 0;
    }

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void removeItem(Item item) {
        if (hasItem(item)) {
            items.put(item, items.get(item) - 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory:\n");
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey().getName()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    Item buyItem(Item item) {
        //items.add(item);
        return item;
    }

    Item takeItem(Item item) {
        //items.remove(item);
        return item;
    }
}
