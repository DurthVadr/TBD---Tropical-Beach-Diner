import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InventoryManagerTest {
    private InventoryManager inventoryManager;

    @BeforeEach
    public void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    public void testAddItem() {
        inventoryManager.addItem("Cheese");
        assertTrue(inventoryManager.checkItem("Cheese") > 0, "Inventory should have Cheese after addition");
    }

    @Test
    public void testRemoveItem() {
        inventoryManager.addItem("Cheese");
        inventoryManager.removeItem(new Item("Cheese"));
        assertEquals(0, inventoryManager.checkItem("Cheese"), "Inventory should have no Cheese after removal");
    }
}
