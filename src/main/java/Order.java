import java.util.ArrayList;
import java.util.Arrays;
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
    
    /** Modifier string constants */
    private static final String MODIFIER_EXTRA_CHEESE = "EXTRA_CHEESE";
    private static final String MODIFIER_EXTRA_ONIONS = "EXTRA_ONIONS";
    private static final String MODIFIER_SOUR_CREAM = "SOUR_CREAM";
    private static final String MODIFIER_EXTRA_BREAD = "EXTRA_BREAD";
    private static final String MODIFIER_BUTTER = "BUTTER";
    private static final String MODIFIER_CROUTONS = "CROUTONS";
    private static final String MODIFIER_NO_CHEESE = "NO_CHEESE";
    private static final String MODIFIER_NO_ONIONS = "NO_ONIONS";
    private static final String MODIFIER_NO_TOMATOES = "NO_TOMATOES";

    /** Status codes */
    private static final int STATUS_READY = 2;
    private static final int FINALIZED_STATUS = 3;
    private static final int STATUS_DELIVERED = 3;
    private static final int STATUS_PAID = 4;

    /** Return codes */
    private static final double RETURN_SUCCESS = 0.0;
    private static final double RETURN_QUANTITY_LIMIT = 2.0;
    private static final double RETURN_INVALID_MODIFIER = 2.1;
    private static final double RETURN_UNAVAILABLE = 3.0;
    private static final double RETURN_NULL_ITEM = 3.1;
    private static final double RETURN_INVALID_ID = 4.1;
    private static final double RETURN_FINALIZED = 5.0;

    /** Tracks applied promotions */
    protected List<String> appliedPromotions;
    
    /** List of available promotion strategies */
    protected List<Promotion> availablePromotions;

    /**
     * Creates a new order for a table
     * @param table the table
     * @param customerName customer name
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public Order(Table table, String customerName) {
        this.table = copyTable(table);
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
        this.orderStatus = 0;
        this.appliedPromotions = new ArrayList<>();
        this.availablePromotions = initializePromotions();
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
        this.availablePromotions = initializePromotions();
    }

    /**
     * Initializes order with table and customer name
     * @param table the table
     * @param customerName customer name
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public void initOrder(Table table, String customerName) {
        this.table = copyTable(table);
        this.customerName = customerName;
        this.items.clear();
        this.totalPrice = 0.0;
        this.orderStatus = 0;
        this.appliedPromotions.clear();
        this.availablePromotions = initializePromotions();
    }

    /**
     * Gets the customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Creates a defensive copy of a Table object.
     * @param original the table to copy
     * @return a new Table instance with copied values
     */
    private Table copyTable(Table original) {
        Table copy = new Table(original.getTableNumber(), original.getPartySize());
        copy.setServerName(original.getServerName());
        copy.setOccupied(original.isOccupied());
        return copy;
    }
    
    /**
     * Gets the table
     * @return table object
     */
    // SER316 TASK 2 SPOTBUGS FIX
    public Table getTable() {
        return copyTable(table);
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
        for (OrderItem orderItem : items) {
            if (orderItem.getMenuItem().getItemId().equals(itemId)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if modifier combinations are compatible.
     * NOTE: This method is not used in the simplified specification implementation
     * but is kept for compatibility with black box test classes (Order0-4).
     * 
     * @param modifiers list of modifiers to check
     * @return true if modifiers are compatible, false otherwise
     */
    protected boolean areModifiersCompatible(List<String> modifiers) {
        if (modifiers.contains(MODIFIER_NO_CHEESE) && modifiers.contains(MODIFIER_EXTRA_CHEESE)) {
            return false;
        }
        if (modifiers.contains(MODIFIER_NO_ONIONS) && modifiers.contains(MODIFIER_EXTRA_ONIONS)) {
            return false;
        }
        return true;
    }

    protected double calculateModifierPrice(List<String> modifiers) {
        double modifierPrice = 0.0;
        for (String modifier : modifiers) {
            if (modifier.equals(MODIFIER_EXTRA_CHEESE) || modifier.equals(MODIFIER_EXTRA_ONIONS) || modifier.equals(MODIFIER_SOUR_CREAM)) {
                modifierPrice += MODIFIER_PRICE_HIGH;
            }
            if (modifier.equals(MODIFIER_EXTRA_BREAD) || modifier.equals(MODIFIER_BUTTER)) {
                modifierPrice += MODIFIER_PRICE_MED;
            }
            if (modifier.equals(MODIFIER_CROUTONS)) {
                modifierPrice += MODIFIER_PRICE_LOW;
            }
            if (modifier.equals(MODIFIER_NO_CHEESE) || modifier.equals(MODIFIER_NO_ONIONS) || modifier.equals(MODIFIER_NO_TOMATOES)) {
                modifierPrice -= MODIFIER_DISCOUNT;
            }
        }
        return modifierPrice;
    }

    /**
     * Initializes the list of available promotion strategies.
     * 
     * @return list of promotion strategies
     */
    protected List<Promotion> initializePromotions() {
        return new ArrayList<>(Arrays.asList(
            new AppetizerPromotion(),
            new EntreePromotion()
        ));
    }
    
    /**
     * Calculates the best applicable promotion discount for an item.
     * Iterates through all available promotion strategies and applies
     * the one with the highest discount rate.
     * 
     * This refactored method uses the Strategy pattern to make the
     * promotion system extensible. New promotions can be added by:
     * 1. Creating a new class implementing the Promotion interface
     * 2. Adding it to the initializePromotions() method
     * 
     * No other code needs to be modified.
     * 
     * @param item the menu item being ordered
     * @param modifiers list of modifiers applied to the item
     * @return the highest discount percentage (0.0 to 1.0) from applicable promotions
     */
    protected double calculatePromotion(MenuItem item, List<String> modifiers) {
        double bestDiscount = 0.0;
        Promotion bestPromotion = null;
        
        // Iterate through all available promotions to find the best one
        for (Promotion promotion : availablePromotions) {
            if (promotion.isApplicable(item, modifiers)) {
                double discount = promotion.calculateDiscount(item, modifiers);
                if (discount > bestDiscount) {
                    bestDiscount = discount;
                    bestPromotion = promotion;
                }
            }
        }
        
        // Track the applied promotion
        if (bestPromotion != null) {
            appliedPromotions.add(bestPromotion.getPromotionCode());
        }
        
        return bestDiscount;
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
            return RETURN_QUANTITY_LIMIT;
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

        // 9) Return success code
        return RETURN_SUCCESS;
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
        if (orderStatus >= STATUS_READY) {
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

    /**
     * Generates a formatted order summary string
     * @param incTotal whether to include total in summary
     * @param disc discount code (1=10%, 2=20%, 3=30%)
     * @param tip tip amount
     * @return formatted order summary string
     */
    public String generateOrderSummary(boolean incTotal, int disc, double tip) {
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            summary.append(items.get(i).getMenuItem().getName())
                   .append(" - $")
                   .append(items.get(i).getPrice())
                   .append("\n");
        }
        if (incTotal) {
            summary.append("Total: $").append(totalPrice).append("\n");
            if (disc == 1) {
                summary.append("Discount: 10%\n");
            } else if (disc == 2) {
                summary.append("Discount: 20%\n");
            } else if (disc == 3) {
                summary.append("Discount: 30%\n");
            }
        }
        if (tip > 0.0) {
            double totalWithTip = totalPrice + tip;
            summary.append("With tip: $").append(totalWithTip).append("\n");
        }
        return summary.toString();
    }

    /**
     * Processes payment for the order
     * @param type payment type (CASH, CARD, CHECK)
     * @param amount payment amount
     * @param split whether to split payment
     * @param numPeople number of people for split
     */
    public void processPayment(String type, double amount, boolean split, int numPeople) {
        if (type.equals("CASH")) {
            if (split) {
                double amountPerPerson = amount / numPeople;
                System.out.println("Each person pays: " + amountPerPerson);
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
        orderStatus = STATUS_PAID;
    }


    /**
     * Inner class representing an item in an order with modifiers
     */
    public static class OrderItem {
        private MenuItem menuItem;
        private List<String> modifiers;
        private double price;

        /**
         * Creates a defensive copy of a MenuItem object.
         * @param original the menu item to copy
         * @return a new MenuItem instance with copied values
         */
        private static MenuItem copyMenuItem(MenuItem original) {
            MenuItem copy = new MenuItem(original.getItemId(), original.getName(),
                    original.getBasePrice(), original.getCategory());
            copy.setStockCount(original.getStockCount());
            copy.setAvailable(original.isAvailable());
            for (String flag : original.getDietaryFlags()) {
                copy.addDietaryFlag(flag);
            }
            for (String modifier : original.getAllowedModifiers()) {
                copy.addAllowedModifier(modifier);
            }
            return copy;
        }

        // SER316 TASK 2 SPOTBUGS FIX
        public OrderItem(MenuItem menuItem, List<String> modifiers, double price) {
            this.menuItem = copyMenuItem(menuItem);
            this.modifiers = new ArrayList<>(modifiers);
            this.price = price;
        }

        // SER316 TASK 2 SPOTBUGS FIX: 
        public MenuItem getMenuItem() {
            return copyMenuItem(menuItem);
        }

        public List<String> getModifiers() {
            return new ArrayList<>(modifiers);
        }

        public double getPrice() {
            return price;
        }
    }
}