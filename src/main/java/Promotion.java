import java.util.List;

/**
 * Interface for promotion/discount strategies.
 * This allows the system to support different types of promotions
 * without modifying existing code (Open/Closed Principle).
 */
public interface Promotion {
    
    /**
     * Checks if this promotion applies to the given menu item and modifiers.
     * 
     * @param item the menu item being ordered
     * @param modifiers list of modifiers applied to the item
     * @return true if this promotion applies, false otherwise
     */
    boolean isApplicable(MenuItem item, List<String> modifiers);
    
    /**
     * Calculates the discount percentage (0.0 to 1.0) for this promotion.
     * 
     * @param item the menu item being ordered
     * @param modifiers list of modifiers applied to the item
     * @return discount percentage (e.g., 0.20 for 20% off)
     */
    double calculateDiscount(MenuItem item, List<String> modifiers);
    
    /**
     * Gets the promotion code/name for tracking purposes.
     * 
     * @return promotion identifier string
     */
    String getPromotionCode();
}

