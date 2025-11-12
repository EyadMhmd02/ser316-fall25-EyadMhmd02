import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Restaurant class that manages menu items and provides helper methods.
 */
public class Restaurant {

    private Map<String, MenuItem> menuById;
    private String name;
    private int menuItemCount = 0;
    
    /**
     * Creates a new restaurant
     */
    public Restaurant(String name) {
        this.name = name;
        this.menuById = new HashMap<>();
        initializeMenu();
    }
    
    /**
     * Initialize menu with default items
     */
    private void initializeMenu() {
        MenuItem burger = new MenuItem("B001", "Classic Burger", 12.99, "ENTREE");
        burger.addAllowedModifier("EXTRA_CHEESE");
        burger.addAllowedModifier("NO_CHEESE");
        burger.addAllowedModifier("EXTRA_ONIONS");
        burger.addAllowedModifier("NO_ONIONS");
        burger.addAllowedModifier("NO_TOMATOES");
        menuById.put("B001", burger);
        
        MenuItem salad = new MenuItem("S001", "Caesar Salad", 8.99, "APPETIZER");
        salad.addDietaryFlag("GLUTEN_FREE");
        salad.addAllowedModifier("EXTRA_CHEESE");
        salad.addAllowedModifier("NO_CHEESE");
        salad.addAllowedModifier("CROUTONS");
        menuById.put("S001", salad);
        
        MenuItem pasta = new MenuItem("P001", "Alfredo Pasta", 14.99, "ENTREE");
        pasta.addAllowedModifier("EXTRA_CHEESE");
        pasta.addAllowedModifier("NO_CHEESE");
        menuById.put("P001", pasta);
        
        MenuItem wings = new MenuItem("W001", "Buffalo Wings", 9.99, "APPETIZER");
        wings.addAllowedModifier("EXTRA_ONIONS");
        wings.addAllowedModifier("NO_ONIONS");
        menuById.put("W001", wings);
        
        MenuItem soda = new MenuItem("D001", "Soft Drink", 2.99, "BEVERAGE");
        menuById.put("D001", soda);
        
        MenuItem cake = new MenuItem("C001", "Chocolate Cake", 6.99, "DESSERT");
        cake.addDietaryFlag("NUT_ALLERGY");
        menuById.put("C001", cake);
    }
    
    /**
     * Gets menu item by ID
     * @param itemId item identifier
     * @return menu item or null if not found
     */
    public MenuItem getMenuItem(String itemId) {
        return menuById.get(itemId);
    }
    
    /**
     * Gets all menu items
     * @return list of all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuById.values());
    }
    
    /**
     * Adds a new menu item
     * @param item menu item to add
     */
    public void addMenuItem(MenuItem item) {
        menuById.put(item.getItemId(), item);
        menuItemCount++;
    }

    private void removeMenuItem(String itemId) {
        menuById.remove(itemId);
    }
    
    /**
     * Prints menu (poorly formatted)
     */
    public void printMenu() {
        System.out.println("Menu for " + getName());
        for (MenuItem item : menuById.values()) {
            System.out.println(item.getItemId() + " - " + item.getName() + " $" + item.getBasePrice());
        }
    }
    
    /**
     * Calculate discount
     * @param price original price
     * @param discountPercent discount percentage (0-1)
     * @return discounted price
     */
    public double calculateDiscount(double price, double discountPercent) {
        return price * discountPercent;
    }

    /**
     * Format price
     * @param price price to format
     * @return formatted price string
     */
    public String formatPrice(double price) {
        return String.format("$%.2f", price);
    }

    /**
     * Gets restaurant name
     * @return restaurant name
     */
    public String getName() {
        return name;
    }
}
