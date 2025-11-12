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

    // ========================================================================
    // WHITE-BOX TESTS for calculateBillSplit() method
    // ========================================================================
    //
    // CFG Analysis for calculateBillSplit():
    // Nodes:
    //   N1: if (numDiners <= 0 || tipPercent < 0 || tipPercent > 100)
    //   N2: return null
    //   N3: Calculate total, tip, grandTotal, perPerson
    //   N4: for loop (i = 0 to numDiners - 1)
    //   N5: for loop (i = 0 to numDiners - 1) - calculate sumSoFar
    //   N6: Calculate last person's split
    //   N7: return splits
    //
    // Branches to cover for 80% branch coverage:
    //   B1: numDiners <= 0 -> return null
    //   B2: tipPercent < 0 -> return null
    //   B3: tipPercent > 100 -> return null
    //   B4: numDiners > 0 && tipPercent >= 0 && tipPercent <= 100 -> continue
    //   B5: Different numbers of diners (1, 2, 3+) to cover loop branches
    //   B6: Edge cases: tipPercent = 0, tipPercent = 100, rounding scenarios
    // ========================================================================

    /**
     * Test calculateBillSplit() - Invalid input: numDiners <= 0
     * Covers branch: numDiners <= 0 -> return null
     */
    @Test
    public void testCalculateBillSplit_InvalidNumDiners_ReturnsNull() {
        // Arrange: Add some items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        
        // Act & Assert: Test numDiners = 0
        assertNull(order.calculateBillSplit(0, 15.0), "Should return null for numDiners = 0");
        
        // Act & Assert: Test numDiners < 0
        assertNull(order.calculateBillSplit(-1, 15.0), "Should return null for numDiners < 0");
        assertNull(order.calculateBillSplit(-5, 15.0), "Should return null for numDiners < 0");
    }

    /**
     * Test calculateBillSplit() - Invalid input: tipPercent < 0
     * Covers branch: tipPercent < 0 -> return null
     */
    @Test
    public void testCalculateBillSplit_NegativeTip_ReturnsNull() {
        // Arrange: Add some items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        
        // Act & Assert
        assertNull(order.calculateBillSplit(2, -1.0), "Should return null for tipPercent < 0");
        assertNull(order.calculateBillSplit(2, -10.0), "Should return null for tipPercent < 0");
    }

    /**
     * Test calculateBillSplit() - Invalid input: tipPercent > 100
     * Covers branch: tipPercent > 100 -> return null
     */
    @Test
    public void testCalculateBillSplit_TipOver100_ReturnsNull() {
        // Arrange: Add some items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        
        // Act & Assert
        assertNull(order.calculateBillSplit(2, 101.0), "Should return null for tipPercent > 100");
        assertNull(order.calculateBillSplit(2, 150.0), "Should return null for tipPercent > 100");
    }

    /**
     * Test calculateBillSplit() - Single diner (no loop iterations)
     * Covers branch: numDiners = 1, loop doesn't execute
     */
    @Test
    public void testCalculateBillSplit_SingleDiner_ReturnsCorrectAmount() {
        // Arrange: Add items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        double tipPercent = 15.0;
        double expectedTip = expectedTotal * (tipPercent / 100.0);
        double expectedGrandTotal = expectedTotal + expectedTip;
        
        // Act
        double[] splits = order.calculateBillSplit(1, tipPercent);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(1, splits.length, "Should have 1 element for 1 diner");
        assertEquals(expectedGrandTotal, splits[0], 0.01, "Single diner should pay the full amount with tip");
    }

    /**
     * Test calculateBillSplit() - Two diners (one loop iteration)
     * Covers branch: numDiners = 2, loop executes once
     */
    @Test
    public void testCalculateBillSplit_TwoDiners_EqualSplit() {
        // Arrange: Add items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        double tipPercent = 20.0;
        double expectedTip = expectedTotal * (tipPercent / 100.0);
        double expectedGrandTotal = expectedTotal + expectedTip;
        double expectedPerPerson = expectedGrandTotal / 2.0;
        
        // Act
        double[] splits = order.calculateBillSplit(2, tipPercent);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(2, splits.length, "Should have 2 elements for 2 diners");
        assertEquals(expectedPerPerson, splits[0], 0.01, "First person should pay half");
        assertEquals(expectedPerPerson, splits[1], 0.01, "Second person should pay half");
        // Verify total matches
        assertEquals(expectedGrandTotal, splits[0] + splits[1], 0.01, "Total should match grand total");
    }

    /**
     * Test calculateBillSplit() - Multiple diners (3+) with rounding
     * Covers branch: numDiners > 2, loop executes multiple times, rounding handled
     */
    @Test
    public void testCalculateBillSplit_MultipleDiners_HandlesRounding() {
        // Arrange: Add items to create a total that will cause rounding issues
        MenuItem burger = restaurant.getMenuItem("B001");
        MenuItem pasta = restaurant.getMenuItem("P001");
        order.processOrderItem(burger, new ArrayList<>());
        order.processOrderItem(pasta, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        double tipPercent = 18.0;
        double expectedTip = expectedTotal * (tipPercent / 100.0);
        double expectedGrandTotal = expectedTotal + expectedTip;
        
        // Act: Split between 3 diners
        double[] splits = order.calculateBillSplit(3, tipPercent);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(3, splits.length, "Should have 3 elements for 3 diners");
        
        // Verify first two people pay equal rounded amounts
        assertEquals(splits[0], splits[1], 0.01, "First two people should pay equal amounts");
        
        // Verify total matches (last person gets remainder)
        double sum = splits[0] + splits[1] + splits[2];
        assertEquals(expectedGrandTotal, sum, 0.01, "Total of all splits should equal grand total");
    }

    /**
     * Test calculateBillSplit() - Edge case: tipPercent = 0
     * Covers branch: tipPercent = 0 (boundary condition)
     */
    @Test
    public void testCalculateBillSplit_ZeroTip_NoTipAdded() {
        // Arrange: Add items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        
        // Act
        double[] splits = order.calculateBillSplit(2, 0.0);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(2, splits.length, "Should have 2 elements for 2 diners");
        assertEquals(expectedTotal / 2.0, splits[0], 0.01, "Each person should pay half without tip");
        assertEquals(expectedTotal / 2.0, splits[1], 0.01, "Each person should pay half without tip");
        assertEquals(expectedTotal, splits[0] + splits[1], 0.01, "Total should equal order total (no tip)");
    }

    /**
     * Test calculateBillSplit() - Edge case: tipPercent = 100
     * Covers branch: tipPercent = 100 (boundary condition)
     */
    @Test
    public void testCalculateBillSplit_Tip100Percent_DoublesTotal() {
        // Arrange: Add items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        double expectedGrandTotal = expectedTotal * 2.0; // 100% tip doubles it
        
        // Act
        double[] splits = order.calculateBillSplit(2, 100.0);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(2, splits.length, "Should have 2 elements for 2 diners");
        assertEquals(expectedGrandTotal / 2.0, splits[0], 0.01, "Each person should pay half with 100% tip");
        assertEquals(expectedGrandTotal / 2.0, splits[1], 0.01, "Each person should pay half with 100% tip");
        assertEquals(expectedGrandTotal, splits[0] + splits[1], 0.01, "Total should equal doubled amount");
    }

    /**
     * Test calculateBillSplit() - Large number of diners
     * Covers branch: numDiners > 3, multiple loop iterations
     */
    @Test
    public void testCalculateBillSplit_LargeGroup_HandlesMultiplePeople() {
        // Arrange: Add items to order
        MenuItem burger = restaurant.getMenuItem("B001");
        order.processOrderItem(burger, new ArrayList<>());
        double expectedTotal = order.getTotalPrice();
        double tipPercent = 15.0;
        double expectedTip = expectedTotal * (tipPercent / 100.0);
        double expectedGrandTotal = expectedTotal + expectedTip;
        
        // Act: Split between 5 diners
        double[] splits = order.calculateBillSplit(5, tipPercent);
        
        // Assert
        assertNotNull(splits, "Should not return null for valid input");
        assertEquals(5, splits.length, "Should have 5 elements for 5 diners");
        
        // Verify first 4 people pay equal rounded amounts
        for (int i = 0; i < 4; i++) {
            assertEquals(splits[0], splits[i], 0.01, "First 4 people should pay equal amounts");
        }
        
        // Verify total matches (last person gets remainder)
        double sum = 0;
        for (double split : splits) {
            sum += split;
        }
        assertEquals(expectedGrandTotal, sum, 0.01, "Total of all splits should equal grand total");
    }

}
