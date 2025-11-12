import java.util.ArrayList;
import java.util.List;

/**
 * Represents a restaurant order for a table.
 * Handles adding items, applying modifiers, calculating totals, and managing order status.
 */
public class Order {

    /** The table this order belongs to */
    private Table table;

    /** Customer name for the order */
    private String customerName;

    /** List of items in the order with their modifiers */
    protected List<OrderItem> items;

    /** Current total price */
    protected double totalPrice;

    /** Order status: 0=pending, 1=preparing, 2=ready, 3=delivered, 4=paid */
    protected int orderStatus;

    /** Maximum allowed order total */
    private static final double MAX_ORDER_TOTAL = 100.0;

    /** Maximum quantity of same item allowed */
    private static final int MAX_ITEM_QUANTITY = 5;

    /** Maximum total items in an order */
    private static final int MAX_TOTAL_ITEMS = 5;

    /** Modifier prices */
    private static final double MODIFIER_PRICE_HIGH = 1.50;
    private static final double MODIFIER_PRICE_MED = 1.00;
    private static final double MODIFIER_PRICE_LOW = 0.75;
    private static final double MODIFIER_DISCOUNT = 0.50;

    /** Promotion discount rates */
    private static final double APPETIZER_DISCOUNT = 0.20;
    private static final double ENTREE_DISCOUNT = 0.15;

    /** Status codes */
    private static final int FINALIZED_STATUS = 3;
    private static final int STATUS_DELIVERED = 3;
    private static final int STATUS_PAID = 4;

    /** Return codes */
    private static final double RETURN_FINALIZED = 5.0;
    private static final double RETURN_NULL_ITEM = 3.1;
    private static final double RETURN_INVALID_ID = 4.1;
    private static final double RETURN_UNAVAILABLE = 3.0;
    private static final double RETURN_INVALID_MODIFIER = 2.1;

    /** Tracks applied promotions */
    protected List<String> appliedPromotions;

    /**
     * Creates a new order for a table
     * @param table the table
     * @param customerName customer name
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public Order(Table table, String customerName) {
        Table copy = new Table(table.getTableNumber(), table.getPartySize());
        copy.setServerName(table.getServerName());
        copy.setOccupied(table.isOccupied());
        this.table = copy;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.orderStatus = 0;
        this.appliedPromotions = new ArrayList<>();
    }

    /**
     * Default constructor for testing
     */
    public Order() {
        this.table = new Table(1, 1);
        this.customerName = "";
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.orderStatus = 0;
        this.appliedPromotions = new ArrayList<>();
    }

    /**
     * Initializes order with table and customer name
     * @param table the table
     * @param customerName customer name
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public void initOrder(Table table, String customerName) {
        Table copy = new Table(table.getTableNumber(), table.getPartySize());
        copy.setServerName(table.getServerName());
        copy.setOccupied(table.isOccupied());
        this.table = copy;
        this.customerName = customerName;
        this.items.clear();
        this.totalPrice = 0.0;
        this.orderStatus = 0;
        this.appliedPromotions.clear();
    }

    /**
     * Gets the customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the table
     * @return table object
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public Table getTable() {
        Table copy = new Table(table.getTableNumber(), table.getPartySize());
        copy.setServerName(table.getServerName());
        copy.setOccupied(table.isOccupied());
        return copy;
    }

    /**
     * Gets order status
     * @return status code
     */
    public int getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets order status
     * @param status new status
     */
    public void setOrderStatus(int status) {
        this.orderStatus = status;
    }

    /**
     * Gets current total price
     * @return total price
     */
    public double getTotalPrice() {
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    /**
     * Gets list of items in order
     * @return list of order items
     */
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Gets count of items with same ID (regardless of modifiers)
     * @param itemId item ID
     * @return count
     */
    public int getItemCountById(String itemId) {
        int count = 0;
        for (OrderItem oi : items) {
            if (oi.getMenuItem().getItemId().equals(itemId)) {
                count++;
            }
        }
        return count;
    }

    protected boolean areModifiersCompatible(List<String> modifiers) {
        if (modifiers.contains("NO_CHEESE")) {
            if (modifiers.contains("EXTRA_CHEESE")) {
                return false;
            } else {
            }
        }
        if (modifiers.contains("NO_ONIONS")) {
            if (modifiers.contains("EXTRA_ONIONS")) {
                return false;
            } else {
            }
        }
        return true;
    }

    protected double calculateModifierPrice(List<String> modifiers) {
        double prc = 0.0;
        for (String mod : modifiers) {
            if (mod.equals("EXTRA_CHEESE") || mod.equals("EXTRA_ONIONS") || mod.equals("SOUR_CREAM")) {
                prc += MODIFIER_PRICE_HIGH;
            }
            if (mod.equals("EXTRA_BREAD") || mod.equals("BUTTER")) {
                prc += MODIFIER_PRICE_MED;
            }
            if (mod.equals("CROUTONS")) {
                prc += MODIFIER_PRICE_LOW;
            }
            if (mod.equals("NO_CHEESE") || mod.equals("NO_ONIONS") || mod.equals("NO_TOMATOES")) {
                prc -= MODIFIER_DISCOUNT;
            }
        }
        return prc;
    }

    protected double calculatePromotion(MenuItem item, List<String> modifiers) {
        double discount = 0.0;

        if (item.getCategory().equals("APPETIZER")) {
            discount = APPETIZER_DISCOUNT;
            appliedPromotions.add("APPETIZER_SPECIAL_20");
        }

        if (item.getCategory().equals("ENTREE") && modifiers.size() >= 2) {
            discount = Math.max(discount, ENTREE_DISCOUNT);
            appliedPromotions.add("PREMIUM_ENTREE_15");
        }

        return discount;
    }

    /**
     * Processes adding an item to the order with modifiers.
     * Validates the item, applies modifiers, calculates pricing, and updates order total.
     *
     * ============================================================================
     * FULL SPECIFICATION (for Black Box Testing - Assignment 2)
     * ============================================================================
     * Students test against Order0-4.class which implement the complete specification.
     *
     * Return codes:
     * 0.0 - Item added successfully (no promotion)
     * 0.x - Item added with promotion applied (x = discount percentage, e.g., 0.20 = 20% off, 0.15 = 15% off)
     * 2.0 - Item at maximum quantity limit (cannot add more than 5 of the same itemId)
     * 2.1 - Modifier not valid for this item
     * 2.2 - Modifier combination not allowed (e.g., NO_CHEESE + EXTRA_CHEESE)
     * 3.0 - Item unavailable (out of stock or not available)
     * 3.1 - Item is null
     * 4.1 - Invalid item ID format (must be alphanumeric, cannot be null)
     * 5.0 - Order already finalized (status >= 3: delivered or paid)
     * 5.1 - Adding item would exceed maximum order total ($100)
     *
     * Processing order (checked in this sequence):
     * 1. Check if order status allows modifications (status < 3)
     * 2. Check if item is null
     * 3. Validate item ID format (alphanumeric, not null)
     * 4. Check if item is available
     * 5. Check quantity limit (max 5 items with same itemId, regardless of modifiers)
     * 6. Validate all modifiers are allowed for this item
     * 7. Check modifier compatibility
     * 8. Calculate total price with modifiers
     * 9. Check if adding item would exceed $100 maximum (before applying discount)
     * 10. Check for applicable promotions
     * 11. Add item and update total
     * 12. Return appropriate code
     *
     * Promotions:
     *  - APPETIZER_SPECIAL: 20% off any appetizer (return 0.20)
     *  - PREMIUM_ENTREE: 15% off entrees with 2 or more modifiers (return 0.15)
     *
     * ============================================================================
     * SIMPLIFIED SPECIFICATION (for TDD Implementation - Assignment 3)
     * ============================================================================
     * Students implement core validation only with these return codes:
     *
     * Return codes to implement:
     * 0.0 - Item added successfully
     * 2.0 - Item at maximum quantity limit (max 5 of same itemId)
     * 2.1 - Modifier not valid for this item
     * 3.0 - Item unavailable
     * 3.1 - Item is null
     * 4.1 - Invalid item ID format (alphanumeric, not null)
     * 5.0 - Order already finalized (status >= 3)
     *
     * Processing order (simplified sequence):
     * 1. Check if order status allows modifications (status < 3) -> return 5.0 if not
     * 2. Check if item is null -> return 3.1
     * 3. Validate item ID format (alphanumeric, not null) -> return 4.1 if invalid
     * 4. Check if item is available -> return 3.0 if not
     * 5. Check quantity limit (max 5 of same itemId) -> return 2.0 if exceeded
     * 6. Validate all modifiers are allowed for this item -> return 2.1 if not
     * 7. Calculate total price with modifiers (use calculateModifierPrice helper)
     * 8. Add item and update total
     * 9. Return 0.0
     *
     * NOT required for Assignment 3:
     * - Promotions (0.x codes) - just return 0.0 on success
     * - Incompatible modifier checking (2.2) - skip this check
     * - Max order total checking (5.1) - skip this check
     *
     * ============================================================================
     *
     * General Notes:
     *  - The modifiers parameter can be null (treated as empty list)
     *  - Item is NOT added if any error code 2.x, 3.x, 4.x, or 5.x is returned
     *
     * @param item the menu item to add (can be null)
     * @param modifiers list of modifier codes to apply (can be null or empty)
     * @return status code indicating result
     */
    public double processOrderItem(MenuItem item, List<String> modifiers) {
        // 1) Check if order status allows modifications (status < 3) -> 5.0
        if (orderStatus >= FINALIZED_STATUS) {
            return RETURN_FINALIZED;
        }

        // 2) Check if item is null -> 3.1
        if (item == null) {
            return RETURN_NULL_ITEM;
        }

        // 3) Validate item ID format (alphanumeric, not null) -> 4.1
        String itemId = item.getItemId();
        if (itemId == null || !itemId.matches("^[A-Za-z0-9]+$")) {
            return RETURN_INVALID_ID;
        }

        // 4) Check if item is available -> 3.0
        if (!item.isAvailable()) {
            return RETURN_UNAVAILABLE;
        }

        // Normalize modifiers: null treated as empty list
        List<String> mods = (modifiers == null) ? new ArrayList<>() : new ArrayList<>(modifiers);

        // 5) Check quantity limit (max 5 of same itemId) -> 2.0
        if (getItemCountById(itemId) >= MAX_ITEM_QUANTITY) {
            return 2.0;
        }

        // 6) Validate all modifiers are allowed for this item -> 2.1
        for (String mod : mods) {
            if (!item.isModifierAllowed(mod)) {
                return RETURN_INVALID_MODIFIER;
            }
        }

        // 7) Calculate total price with modifiers (use calculateModifierPrice helper)
        double price = item.getBasePrice() + calculateModifierPrice(mods);

        // 8) Add item and update total (skip promotions, compatibility, max total per simplified spec)
        items.add(new OrderItem(item, mods, price));
        totalPrice += price;
        item.reduceStock();

        // 9) Return 0.0
        return 0.0;
    }

    /**
     * Submits the order for processing
     */
    public void submitOrder() {
        orderStatus = 1;
    }

    /**
     * Marks the order as ready
     */
    public void markReady() {
        if (orderStatus < 2) {
            orderStatus = 2;
        }
    }

    /**
     * Marks the order as delivered
     */
    public void markDelivered() {
        orderStatus = STATUS_DELIVERED;
    }

    /**
     * Marks the order as paid
     */
    public void markPaid() {
        if (orderStatus >= FINALIZED_STATUS) {
            orderStatus = STATUS_PAID;
        }
    }

    /**
     * Checks if order can accept modifications based on item count, status, and total.
     * An order can be modified if:
     * - Status is 0 (pending) or 1 (preparing)
     * - Has fewer than 5 items total
     * - Total price is under maximum ($100)
     *
     * This method is useful for determining whether to show "add item" buttons in a UI
     * or to batch-validate multiple operations.
     *
     * @return true if modifications allowed, false otherwise
     */
    public boolean canModifyOrder() {
        if (orderStatus >= 2) {
            return false;
        }
        if (items.size() >= MAX_TOTAL_ITEMS) {
            return false;
        }
        if (totalPrice >= MAX_ORDER_TOTAL) {
            return false;
        }
        return true;
    }

    /**
     * Calculates bill split for multiple diners with tip.
     * Each diner pays an equal share, with rounding handled by giving the last
     * person any remaining cents.
     *
     * @param numDiners number of people splitting the bill (must be positive)
     * @param tipPercent tip percentage (0-100)
     * @return array of amounts each diner pays, or null if invalid parameters
     */
    public double[] calculateBillSplit(int numDiners, double tipPercent) {
        if (numDiners <= 0 || tipPercent < 0 || tipPercent > 100) {
            return null;
        }

        double total = getTotalPrice();
        double tipAmount = total * (tipPercent / 100.0);
        double grandTotal = total + tipAmount;
        double perPerson = grandTotal / numDiners;

        double[] splits = new double[numDiners];

        // Calculate equal split for all but last person
        for (int i = 0; i < numDiners - 1; i++) {
            splits[i] = Math.round(perPerson * 100.0) / 100.0;
        }

        // Last person gets remainder to handle rounding
        double sumSoFar = 0;
        for (int i = 0; i < numDiners - 1; i++) {
            sumSoFar += splits[i];
        }
        splits[numDiners - 1] = Math.round((grandTotal - sumSoFar) * 100.0) / 100.0;

        return splits;
    }

    public String generateOrderSummary(boolean incTotal, int disc, double tip) {
        String str = "";
        for (int i = 0; i < items.size(); i++) {
            str = str + items.get(i).getMenuItem().getName() + " - $" + items.get(i).getPrice() + "\n";
        }
        if (incTotal) {
            str = str + "Total: $" + totalPrice + "\n";
            if (disc == 1) {
                str = str + "Discount: 10%\n";
            } else if (disc == 2) {
                str = str + "Discount: 20%\n";
            } else if (disc == 3) {
                str = str + "Discount: 30%\n";
            }
        }
        if (tip > 0.0) {
            double tot = totalPrice + tip;
            str = str + "With tip: $" + tot + "\n";
        }
        return str;
    }

    public void processPayment(String type, double amount, boolean split, int numPeople) {
        if (type.equals("CASH")) {
            if (split) {
                double each = amount / numPeople;
                System.out.println("Each person pays: " + each);
            }
            System.out.println("Cash payment received");
        } else if (type.equals("CARD")) {
            System.out.println("Processing card");
            if (split) {
                System.out.println("Splitting between " + numPeople);
            }
            System.out.println("Card payment received");
        } else if (type.equals("CHECK")) {
            System.out.println("Check payment");
        }
        orderStatus = 4;
    }

    private int getItemQuantity(String id) {
        int qty = 0;
        try {
            for (OrderItem item : items) {
                if (item.getMenuItem().getItemId().equals(id)) {
                    qty++;
                }
            }
        } catch (Exception e) {
        }
        return qty;
    }

    private boolean validateOrderItemsForProcessingAndEnsureAllConstraintsAreSatisfiedIncludingQuantityLimitsAndPriceThresholds(MenuItem item, List<String> mods) {
        return true;
    }

    /**
     * Inner class representing an item in an order with modifiers
     */
    public static class OrderItem {
        private MenuItem menuItem;
        private List<String> modifiers;
        private double price;

        // SER316 TASK 2 SPOTBUGS FIX
        public OrderItem(MenuItem menuItem, List<String> modifiers, double price) {
            MenuItem copy = new MenuItem(menuItem.getItemId(), menuItem.getName(),
                    menuItem.getBasePrice(), menuItem.getCategory());
            copy.setStockCount(menuItem.getStockCount());
            copy.setAvailable(menuItem.isAvailable());
            for (String flag : menuItem.getDietaryFlags()) {
                copy.addDietaryFlag(flag);
            }
            for (String modifier : menuItem.getAllowedModifiers()) {
                copy.addAllowedModifier(modifier);
            }
            this.menuItem = copy;
            this.modifiers = new ArrayList<>(modifiers);
            this.price = price;
        }

        // SER316 TASK 2 SPOTBUGS FIX: 
        public MenuItem getMenuItem() {
            MenuItem copy = new MenuItem(menuItem.getItemId(), menuItem.getName(),
                    menuItem.getBasePrice(), menuItem.getCategory());
            copy.setStockCount(menuItem.getStockCount());
            copy.setAvailable(menuItem.isAvailable());
            for (String flag : menuItem.getDietaryFlags()) {
                copy.addDietaryFlag(flag);
            }
            for (String modifier : menuItem.getAllowedModifiers()) {
                copy.addAllowedModifier(modifier);
            }
            return copy;
        }

        public List<String> getModifiers() {
            return new ArrayList<>(modifiers);
        }

        public double getPrice() {
            return price;
        }
    }
}