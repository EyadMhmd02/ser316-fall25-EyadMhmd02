import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessOrderItemTest {

    private Order order;
    private Restaurant restaurant;
    private Table table;

    @BeforeEach
    public void setUp() {
        order = new Order();
        restaurant = new Restaurant("Test R");
        table = new Table(1, 2);
        order.initOrder(table, "Test Customer");
    }

    @Test
    public void testSuccess_NoModifiers_Returns0_0_AddsItemAndUpdatesTotal() {
        MenuItem burger = restaurant.getMenuItem("B001");
        double result = order.processOrderItem(burger, new ArrayList<>());
        assertEquals(0.0, result, 0.001);
        assertEquals(1, order.getItems().size());
        assertEquals(12.99, order.getTotalPrice(), 0.01);
    }

    @Test
    public void testSuccess_NullModifiers_TreatedAsEmpty() {
        MenuItem pasta = restaurant.getMenuItem("P001");
        double result = order.processOrderItem(pasta, null);
        assertEquals(0.0, result, 0.001);
        assertEquals(1, order.getItems().size());
        assertEquals(14.99, order.getTotalPrice(), 0.01);
    }

    @Test
    public void testQuantityLimit_MaxFive_Returns2_0OnSixth() {
        MenuItem salad = restaurant.getMenuItem("S001");
        for (int i = 0; i < 5; i++) {
            double r = order.processOrderItem(salad, new ArrayList<>());
            assertEquals(0.0, r, 0.001);
        }
        double result = order.processOrderItem(salad, new ArrayList<>());
        assertEquals(2.0, result, 0.001);
        assertEquals(5, order.getItemCountById("S001"));
    }

    @Test
    public void testInvalidModifier_Returns2_1_DoesNotAdd() {
        MenuItem soda = restaurant.getMenuItem("D001");
        List<String> mods = Arrays.asList("EXTRA_CHEESE");
        double result = order.processOrderItem(soda, mods);
        assertEquals(2.1, result, 0.001);
        assertEquals(0, order.getItems().size());
    }

    @Test
    public void testUnavailableItem_Returns3_0() {
        MenuItem wings = restaurant.getMenuItem("W001");
        wings.setStockCount(0);
        double result = order.processOrderItem(wings, new ArrayList<>());
        assertEquals(3.0, result, 0.001);
        assertEquals(0, order.getItems().size());
    }

    @Test
    public void testNullItem_Returns3_1() {
        double result = order.processOrderItem(null, new ArrayList<>());
        assertEquals(3.1, result, 0.001);
        assertEquals(0, order.getItems().size());
    }

    @Test
    public void testInvalidItemIdFormat_Returns4_1() {
        MenuItem tmp = new MenuItem("B-01", "Bad ID", 5.00, "APPETIZER");
        double result = order.processOrderItem(tmp, new ArrayList<>());
        assertEquals(4.1, result, 0.001);
        assertEquals(0, order.getItems().size());
    }

    @Test
    public void testOrderFinalized_Returns5_0() {
        order.setOrderStatus(3);
        MenuItem burger = restaurant.getMenuItem("B001");
        double result = order.processOrderItem(burger, new ArrayList<>());
        assertEquals(5.0, result, 0.001);
        assertEquals(0, order.getItems().size());
    }

    @Test
    public void testPriceIncludesModifierCosts() {
        MenuItem burger = restaurant.getMenuItem("B001");
        List<String> mods = Arrays.asList("EXTRA_CHEESE", "EXTRA_ONIONS");
        double result = order.processOrderItem(burger, mods);
        assertEquals(0.0, result, 0.001);
        assertEquals(1, order.getItems().size());
        assertEquals(12.99 + 3.00, order.getTotalPrice(), 0.01);
    }
}


