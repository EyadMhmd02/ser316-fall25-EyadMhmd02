import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * STARTER Test Suite for Assignment 3: White-Box Testing
 *
 * This file contains white-box tests for methods in Order.java (NOT processOrderItem).
 * These tests are designed using white-box techniques like:
 * - Control Flow Graph (CFG) analysis
 * - Statement/branch coverage
 * - Path coverage
 *
 * ASSIGNMENT 3 TEST FILES:
 * 1. WhiteBoxTestStarter.java (this file) - White-box tests for helper methods
 *    - Students: Add tests for canModifyOrder(), calculateBillSplit(), etc.
 *    - Use CFG analysis and coverage tools (JaCoCo)
 *
 * 2. ProcessOrderItemTest.java (students create this) - Tests for YOUR implementation
 *    - Test your own processOrderItem() implementation (simplified spec)
 *    - Use TDD: write tests first, then implement
 *    - Cover return codes: 0.0, 2.0, 2.1, 3.0, 3.1, 4.1, 5.0
 */
public class WhiteBoxTestStarter {

    private Order order;
    private Table table;
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        order = new Order();
        table = new Table(1, 4);
        order.initOrder(table, "Test Customer");
        restaurant = new Restaurant("Test Restaurant");
    }

    // ========================================================================
    // WHITE-BOX TESTS for canModifyOrder() method
    // ========================================================================
    //
    // Control Flow Graph Analysis:
    // Nodes:
    //   N1: Line 349 - if (orderStatus >= 2)
    //   N2: Line 352 - if (items.size() >= MAX_TOTAL_ITEMS)
    //   N3: Line 355 - if (totalPrice >= MAX_ORDER_TOTAL)
    //   N4: Line 358 - return true
    //
    // Edges:
    //   E1: N1(T) -> return false (line 350)
    //   E2: N1(F) -> N2 (line 352)
    //   E3: N2(T) -> return false (line 353)
    //   E4: N2(F) -> N3 (line 355)
    //   E5: N3(T) -> return false (line 356)
    //   E6: N3(F) -> N4 -> return true (line 358)
    //
    // Sequences for Complete Coverage:
    //   S1: [N1(T) via E1] -> return false
    //   S2: [N1(F) via E2, N2(T) via E3] -> return false
    //   S3: [N1(F) via E2, N2(F) via E4, N3(T) via E5] -> return false
    //   S4: [N1(F) via E2, N2(F) via E4, N3(F) via E6, N4] -> return true
    // ========================================================================

    /**
     * White-box test for canModifyOrder() - Sequence S4
     * 
     * Covers: [N1(F) via E2, N2(F) via E4, N3(F) via E6, N4] -> return true
     * Nodes: N1, N2, N3, N4
     * Edges: E2, E4, E6
     * 
     * Preconditions: orderStatus < 2, items.size() < 5, totalPrice < 100.0
     */
    @Test
    public void testCanModifyOrder_AllConditionsFalse_ReturnsTrue() {
        // Arrange: Order with status=0, 0 items, $0 total
        // (already set up in setUp())

        // Act
        boolean result = order.canModifyOrder();

        // Assert
        assertTrue(result, "Should allow modifications when status=0, no items, $0 total");
    }

    /**
     * White-box test for canModifyOrder() - Sequence S1
     * 
     * Covers: [N1(T) via E1] -> return false
     * Nodes: N1
     * Edges: E1
     * 
     * Preconditions: orderStatus >= 2
     */
    @Test
    public void testCanModifyOrder_StatusAtLeast2_ReturnsFalse() {
        // Arrange: Set order status to 2 (ready)
        order.setOrderStatus(2);
        
        // Act
        boolean result = order.canModifyOrder();
        
        // Assert
        assertFalse(result, "Should not allow modifications when status >= 2");
        
        // Test with status 3 (delivered)
        order.setOrderStatus(3);
        result = order.canModifyOrder();
        assertFalse(result, "Should not allow modifications when status = 3");
        
        // Test with status 4 (paid)
        order.setOrderStatus(4);
        result = order.canModifyOrder();
        assertFalse(result, "Should not allow modifications when status = 4");
    }

    /**
     * White-box test for canModifyOrder() - Sequence S2
     * 
     * Covers: [N1(F) via E2, N2(T) via E3] -> return false
     * Nodes: N1, N2
     * Edges: E2, E3
     * 
     * Preconditions: orderStatus < 2, items.size() >= 5
     */
    @Test
    public void testCanModifyOrder_ItemsAtLimit_ReturnsFalse() {
        // Arrange: Ensure status < 2
        order.setOrderStatus(0);
        
        // Add 5 items to reach MAX_TOTAL_ITEMS limit
        MenuItem burger = restaurant.getMenuItem("B001");
        for (int i = 0; i < 5; i++) {
            order.processOrderItem(burger, new ArrayList<>());
        }
        
        // Verify we have 5 items
        assertEquals(5, order.getItems().size(), "Should have 5 items");
        
        // Act
        boolean result = order.canModifyOrder();
        
        // Assert
        assertFalse(result, "Should not allow modifications when items.size() >= 5");
    }

    /**
     * White-box test for canModifyOrder() - Sequence S3
     * 
     * Covers: [N1(F) via E2, N2(F) via E4, N3(T) via E5] -> return false
     * Nodes: N1, N2, N3
     * Edges: E2, E4, E5
     * 
     * Preconditions: orderStatus < 2, items.size() < 5, totalPrice >= 100.0
     */
    @Test
    public void testCanModifyOrder_TotalAtOrAboveMax_ReturnsFalse() {
        // Arrange: Ensure status < 2, items.size() < 5, but totalPrice >= 100.0
        // Note: With MAX_TOTAL_ITEMS=5 and available menu items, it's difficult to
        // naturally reach $100. We use reflection to set totalPrice for testing purposes.
        order.setOrderStatus(0);
        
        // Add a few items (but keep count < 5)
        MenuItem burger = restaurant.getMenuItem("B001");
        MenuItem pasta = restaurant.getMenuItem("P001");
        
        order.processOrderItem(burger, new ArrayList<>());
        order.processOrderItem(pasta, new ArrayList<>());
        
        // Use reflection to set totalPrice to 100.0 to test the boundary condition
        // This is necessary because with MAX_TOTAL_ITEMS=5, we cannot naturally reach $100
        // with the available menu items and modifiers
        try {
            java.lang.reflect.Field totalPriceField = Order.class.getDeclaredField("totalPrice");
            totalPriceField.setAccessible(true);
            totalPriceField.setDouble(order, 100.0);
        } catch (Exception e) {
            fail("Could not set totalPrice via reflection: " + e.getMessage());
        }
        
        // Verify preconditions for S3
        assertTrue(order.getOrderStatus() < 2, "Status should be < 2");
        assertTrue(order.getItems().size() < 5, "Items should be < 5");
        assertTrue(order.getTotalPrice() >= 100.0, "Total should be >= 100.0");
        
        // Act
        boolean result = order.canModifyOrder();
        
        // Assert
        assertFalse(result, "Should not allow modifications when totalPrice >= 100.0");
    }

}
