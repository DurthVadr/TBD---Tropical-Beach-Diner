package Restaurant;

public class Customer {

   private String name;
    private Float satisfaction;


    public Customer(String name, Float satisfaction) {
        this.name = name;
        this.satisfaction = satisfaction;
    }
    public float checkOrder(Item item, Order order) {
        boolean itemFound = false;
        float total = 0.0f;

        for (Item orderedItem : order.getItems()) {
            if (orderedItem.equals(item)) {
                itemFound = true;
                total += orderedItem.getPrice();
            }
        }
        if (!itemFound) {
            // Decrease satisfaction if the item not found
            this.satisfaction -= 0.1; // Decrease by 10% (can be modified)
            if (this.satisfaction < 0) {
                this.satisfaction = 0;
            }
        }
    }

    void giveOrder(Order order) {
        //TODO: Implement
        
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }
}
