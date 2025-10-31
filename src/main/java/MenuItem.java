import java.util.ArrayList;
import java.util.List;

/**
 * Represents a menu item in the restaurant system.
 * Each item has a base price and can have modifiers applied.
 */
public class MenuItem {
    
    private String itemId;
    private String name;
    private double basePrice;
    private String category; // APPETIZER, ENTREE, DESSERT, BEVERAGE
    private boolean available;
    private List<String> dietaryFlags; // VEGETARIAN, VEGAN, GLUTEN_FREE, DAIRY_FREE, NUT_ALLERGY
    private List<String> allowedModifiers;
    private int stockCount;
    
    /**
     * Constructor for a menu item
     * @param itemId unique identifier
     * @param name display name
     * @param basePrice base price in dollars
     * @param category category of item
     */
    public MenuItem(String itemId, String name, double basePrice, String category) {
        this.itemId = itemId;
        this.name = name;
        this.basePrice = basePrice;
        this.category = category;
        this.available = true;
        this.dietaryFlags = new ArrayList<>();
        this.allowedModifiers = new ArrayList<>();
        this.stockCount = 100;
    }
    
    /**
     * Gets the item ID
     * @return item ID
     */
    public String getItemId() {
        return itemId;
    }
    
    /**
     * Gets the item name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets base price
     * @return base price
     */
    public double getBasePrice() {
        return basePrice;
    }
    
    /**
     * Gets category
     * @return category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Checks if item is available
     * @return true if available
     */
    public boolean isAvailable() {
        return available && stockCount > 0;
    }
    
    /**
     * Sets availability
     * @param available availability status
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    /**
     * Gets dietary flags
     * @return list of dietary flags
     */
    public List<String> getDietaryFlags() {
        return new ArrayList<>(dietaryFlags);
    }
    
    /**
     * Adds a dietary flag
     * @param flag dietary flag to add
     */
    public void addDietaryFlag(String flag) {
        if (!dietaryFlags.contains(flag)) {
            dietaryFlags.add(flag);
        }
    }
    
    /**
     * Gets allowed modifiers
     * @return list of allowed modifiers
     */
    public List<String> getAllowedModifiers() {
        return new ArrayList<>(allowedModifiers);
    }
    
    /**
     * Adds an allowed modifier
     * @param modifier modifier to allow
     */
    public void addAllowedModifier(String modifier) {
        if (!allowedModifiers.contains(modifier)) {
            allowedModifiers.add(modifier);
        }
    }
    
    /**
     * Checks if a modifier is allowed
     * @param modifier modifier to check
     * @return true if allowed
     */
    public boolean isModifierAllowed(String modifier) {
        return allowedModifiers.contains(modifier);
    }
    
    /**
     * Gets stock count
     * @return current stock
     */
    public int getStockCount() {
        return stockCount;
    }
    
    /**
     * Reduces stock by 1
     */
    public void reduceStock() {
        if (stockCount > 0) {
            stockCount--;
        }
    }
    
    /**
     * Sets stock count
     * @param count new stock count
     */
    public void setStockCount(int count) {
        this.stockCount = count;
    }
}
