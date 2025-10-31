import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    public void setUp() {
        order = new Order();
        table = new Table(1, 4);
        order.initOrder(table, "Test Customer");
    }

    // ========================================================================
    // SAMPLE TEST - White-box test for canModifyOrder() method
    // ========================================================================

    /**
     * White-box test for canModifyOrder() - Path 1
     *
     * CFG Analysis:
     * - Node 1: Check orderStatus >= 2
     * - Node 2: Check items.size() >= MAX_TOTAL_ITEMS
     * - Node 3: Check totalPrice >= MAX_ORDER_TOTAL
     * - Node 4: return true
     *
     * This test covers the path where all conditions are false (happy path).
     * Coverage: All branches taken (false, false, false -> true)
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


}
