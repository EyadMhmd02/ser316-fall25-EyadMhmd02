import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for testing the restaurant ordering system.
 * This demonstrates basic functionality and provides a simple interactive mode.
 */
public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Restaurant Order System Demo ===\n");
        
        // Initialize restaurant and table
        Restaurant restaurant = new Restaurant("The Testing Bistro");
        Table table1 = new Table(5, 2);
        table1.setServerName("Alice");
        
        // Create an order
        Order order = new Order(table1, "John Doe");
        
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Table: " + table1.getTableNumber());
        System.out.println("Server: " + table1.getServerName());
        System.out.println("\n--- Available Menu Items ---");
        restaurant.printMenu();


        // Demonstrate some basic orders
        System.out.println("\n--- Testing Basic Order ---");
        MenuItem burger = restaurant.getMenuItem("B001");
        List<String> modifiers = new ArrayList<>();
        modifiers.add("EXTRA_CHEESE");
        modifiers.add("NO_ONIONS");
        
        System.out.println("Adding burger with extra cheese and no onions...");
        double result = order.processOrderItem(burger, modifiers);
        System.out.println("Result code: " + result);
        System.out.println("Current total: $" + order.getTotalPrice());
        
        // Test item limit
        System.out.println("\n--- Testing Item Quantity Limit ---");
        MenuItem salad = restaurant.getMenuItem("S001");
        List<String> noMods = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            System.out.println("Adding salad #" + (i+1));
            result = order.processOrderItem(salad, noMods);
            System.out.println("Result: " + result);
        }
        
        System.out.println("\nFinal order total: $" + order.getTotalPrice());
        System.out.println("Items in order: " + order.getItems().size());
        
        // Interactive mode (commented out for automated testing)
        // interactiveMode(restaurant);
    }
    
    /**
     * Interactive mode for manual testing
     */
    private static void interactiveMode(Restaurant restaurant) {
        System.out.println("\n\n=== Interactive Mode ===");
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        
        System.out.print("Enter table number: ");
        int tableNum = Integer.parseInt(scanner.nextLine());
        
        Table table = new Table(tableNum, 2);
        Order order = new Order(table, customerName);
        
        while (order.getOrderStatus() == 0) {
            System.out.println("\nCurrent total: $" + order.getTotalPrice());
            System.out.println("\nOptions:");
            System.out.println("1. Add item");
            System.out.println("2. View order");
            System.out.println("3. Finalize order");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addItemInteractive(restaurant, order);
                    break;
                case "2":
                    viewOrder(order);
                    break;
                case "3":
                    order.setOrderStatus(3);
                    System.out.println("Order finalized!");
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    
    private static void addItemInteractive(Restaurant restaurant, Order order) {
        restaurant.printMenu();
        System.out.print("\nEnter item ID: ");
        String itemId = scanner.nextLine();
        
        MenuItem item = restaurant.getMenuItem(itemId);
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }
        
        System.out.println("Available modifiers: " + item.getAllowedModifiers());
        System.out.print("Enter modifiers (comma-separated, or press Enter for none): ");
        String modInput = scanner.nextLine();
        
        List<String> modifiers = new ArrayList<>();
        if (!modInput.trim().isEmpty()) {
            String[] mods = modInput.split(",");
            for (String mod : mods) {
                modifiers.add(mod.trim());
            }
        }
        
        double result = order.processOrderItem(item, modifiers);
        System.out.println("\nResult code: " + result);
        interpretResultCode(result);
    }
    
    private static void viewOrder(Order order) {
        System.out.println("\n=== Current Order ===");
        List<Order.OrderItem> items = order.getItems();
        if (items.isEmpty()) {
            System.out.println("Order is empty");
            return;
        }
        
        for (int i = 0; i < items.size(); i++) {
            Order.OrderItem item = items.get(i);
            System.out.println((i+1) + ". " + item.getMenuItem().getName() + 
                             " - Modifiers: " + item.getModifiers() + 
                             " - $" + item.getPrice());
        }
        System.out.println("\nTotal: $" + order.getTotalPrice());
    }
    
    private static void interpretResultCode(double code) {
        if (code == 0.0) {
            System.out.println("✓ Item added successfully");
        } else if (code >= 1.0 && code < 2.0) {
            int warnings = (int)Math.round((code - 1.0) * 10);
            System.out.println("⚠ Item added with " + warnings + " dietary warning(s)");
        } else if (code == 2.0) {
            System.out.println("✗ Item at maximum quantity limit");
        } else if (code == 2.1) {
            System.out.println("✗ Invalid modifier for this item");
        } else if (code == 2.2) {
            System.out.println("✗ Incompatible modifier combination");
        } else if (code == 3.0) {
            System.out.println("✗ Item unavailable");
        } else if (code == 3.1) {
            System.out.println("✗ Item not on menu");
        } else if (code == 4.0) {
            System.out.println("✗ Duplicate item");
        } else if (code == 4.1) {
            System.out.println("✗ Invalid item ID format");
        } else if (code == 5.0) {
            System.out.println("✗ Order already finalized");
        } else if (code == 5.1) {
            System.out.println("✗ Would exceed maximum order total");
        } else if (code >= 6.0 && code < 7.0) {
            int discount = (int)Math.round((code - 6.0) * 100);
            System.out.println("✓ Item added with " + discount + "% promotion!");
        }
    }
}