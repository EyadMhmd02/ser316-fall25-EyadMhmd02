import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Black-box test for processOrderItem method.
 * Students will expand this test suite with comprehensive test cases.
 * This parameterized structure allows testing all Order implementations with the same tests.
 */
public class BlackBox {

    private Order order;
    private Restaurant restaurant;

    // Provide the list of classes to test
    @SuppressWarnings("unchecked")
    static Stream<Class<? extends Order>> orderClassProvider() {
        return (Stream<Class<? extends Order>>) Stream.of(
//                Order.class,
                Order0.class,
                Order1.class,
                Order2.class,
                Order3.class,
                Order4.class
        );
    }

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant("Test Restaurant");
    }

    // Helper method to create order instance from class
    private Order createOrder(Class<? extends Order> clazz) throws Exception {
        Constructor<? extends Order> constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    /**
     * SAMPLE TEST - Students should create many more like this
     * Tests successful addition of a basic item with no modifiers
     * This is testing the valid equivalence partition for a basic order
     */
    @ParameterizedTest
    @MethodSource("orderClassProvider")
    public void testBasicItemAddition(Class<? extends Order> orderClass) throws Exception {
        order = createOrder(orderClass);
        Table table = new Table(1, 2);
        order.initOrder(table, "Test Customer");

        MenuItem burger = restaurant.getMenuItem("B001");
        List<String> noModifiers = new ArrayList<>();

        double result = order.processOrderItem(burger, noModifiers);

        // Should return 0.0 for successful addition
        assertEquals(0.0, result, 0.01,
                "Expected successful addition (0.0) for " + orderClass.getSimpleName());

        // Should have 1 item in order
        assertEquals(1, order.getItems().size(),
                "Should have 1 item in order for " + orderClass.getSimpleName());

        // Total price should equal burger price
        assertEquals(12.99, order.getTotalPrice(), 0.01,
                "Total should equal burger price for " + orderClass.getSimpleName());

        // Order status should still be pending
        assertEquals(0, order.getOrderStatus(),
                "Order status should be pending for " + orderClass.getSimpleName());
    }

    /**
     * SAMPLE TEST - Tests the quantity limit boundary
     * Students should expand with more boundary tests
     */
    @ParameterizedTest
    @MethodSource("orderClassProvider")
    public void testQuantityLimitBoundary(Class<? extends Order> orderClass) throws Exception {
        order = createOrder(orderClass);
        Table table = new Table(1, 2);
        order.initOrder(table, "Test Customer");

        MenuItem salad = restaurant.getMenuItem("S001");

        // Add 5 salads (should all succeed)
        // Note: Salad is APPETIZER, so returns 0.20 (20% promotion)
        for (int i = 0; i < 5; i++) {
            double result = order.processOrderItem(salad, new ArrayList<>());
            assertTrue(result >= 0.0 && result < 1.0,
                    "Adding salad " + (i+1) + " should succeed (0.0-0.99) for " + orderClass.getSimpleName());
        }

        // 6th salad should hit quantity limit
        double result = order.processOrderItem(salad, new ArrayList<>());
        assertEquals(2.0, result, 0.01,
                "Should return 2.0 for quantity limit for " + orderClass.getSimpleName());
    }

    /**
     * SAMPLE TEST - Tests invalid modifier
     * Students should create more tests for different error conditions
     */
    @ParameterizedTest
    @MethodSource("orderClassProvider")
    public void testInvalidModifier(Class<? extends Order> orderClass) throws Exception {
        order = createOrder(orderClass);
        Table table = new Table(1, 2);
        order.initOrder(table, "Test Customer");

        MenuItem soda = restaurant.getMenuItem("D001");
        List<String> modifiers = new ArrayList<>();
        modifiers.add("EXTRA_CHEESE"); // Not allowed on soda

        double result = order.processOrderItem(soda, modifiers);

        // Should return 2.1 for invalid modifier
        assertEquals(2.1, result, 0.01,
                "Should return 2.1 for invalid modifier for " + orderClass.getSimpleName());

        // Item should not be added
        assertEquals(0, order.getItems().size(),
                "Item should not be added for " + orderClass.getSimpleName());
    }

}