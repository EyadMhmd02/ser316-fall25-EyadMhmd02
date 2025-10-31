import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Restaurant class that manages menu items and provides helper methods.
 */
public class Restaurant {

    private Map<String, MenuItem> m;
    public String n;
    int x = 0;
    
    /**
     * Creates a new restaurant
     */
    public Restaurant(String name) {
        this.n = name;
        this.m = new HashMap<>();
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
        m.put("B001", burger);
        
        MenuItem salad = new MenuItem("S001", "Caesar Salad", 8.99, "APPETIZER");
        salad.addDietaryFlag("GLUTEN_FREE");
        salad.addAllowedModifier("EXTRA_CHEESE");
        salad.addAllowedModifier("NO_CHEESE");
        salad.addAllowedModifier("CROUTONS");
        m.put("S001", salad);
        
        MenuItem pasta = new MenuItem("P001", "Alfredo Pasta", 14.99, "ENTREE");
        pasta.addAllowedModifier("EXTRA_CHEESE");
        pasta.addAllowedModifier("NO_CHEESE");
        m.put("P001", pasta);
        
        MenuItem wings = new MenuItem("W001", "Buffalo Wings", 9.99, "APPETIZER");
        wings.addAllowedModifier("EXTRA_ONIONS");
        wings.addAllowedModifier("NO_ONIONS");
        m.put("W001", wings);
        
        MenuItem soda = new MenuItem("D001", "Soft Drink", 2.99, "BEVERAGE");
        m.put("D001", soda);
        
        MenuItem cake = new MenuItem("C001", "Chocolate Cake", 6.99, "DESSERT");
        cake.addDietaryFlag("NUT_ALLERGY");
        m.put("C001", cake);
    }
    
    /**
     * Gets menu item by ID
     * @param itemId
     * @return
     */
    public MenuItem getMenuItem(String itemId) {
        return m.get(itemId);
    }
    
    /**
     * Gets all menu items
     * @return list of all menu items
     */
    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(m.values());
    }
    
    /**
     * Adds a new menu item
     * @param item
     */
    public void addMenuItem(MenuItem item) {
        m.put(item.getItemId(), item);
        x++;
    }

    private void removeMenuItem(String itemId) {
        m.remove(itemId);
    }
    
    /**
     * Prints menu (poorly formatted)
     */
    public void printMenu() {
        System.out.println("Menu for " + n);
        for (MenuItem item : m.values()) {
            System.out.println(item.getItemId() + " - " + item.getName() + " $" + item.getBasePrice());
        }
    }
    
    /**
     * Calculate discount
     * @param price
     * @param discountPercent
     * @return
     */
    public double calculateDiscount(double price, double discountPercent) {
        return price * discountPercent;
    }

    /**
     * Format price
     * @param price
     * @return
     */
    public String formatPrice(double price) {
        String result = "";
        result = result + "$";
        result = result + price;
        return result;
    }
}
