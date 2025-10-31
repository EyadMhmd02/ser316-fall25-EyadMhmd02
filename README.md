# Restaurant Order Management System - Testing Assignment

**Course:** SER316 - Software Quality Assurance

---

## Overview

This project is a restaurant ordering system designed for learning software testing techniques including:
- Black-box testing with equivalence partitioning and boundary value analysis
- White-box testing with control flow graphs and code coverage
- Code review and static analysis

## System Requirements

- **Java:** JDK 18 or higher
- **Build Tool:** Gradle 8.x
- **IDE:** IntelliJ IDEA, Eclipse, or VS Code

## Getting Started

### Build and Run

```bash
# Build the project -- this will fail that is ok
./gradlew clean build
gradle clean build

# Run tests -- this will fail that is ok
gradle test

# Run the demo application -- should not fail
./gradlew run
gradle run
```

### Generate Coverage Report

```bash
gradle jacocoTestReport
open build/reports/jacoco/index.html
```

### Run Static Analysis

```bash
# Checkstyle (uncomment configuration in build.gradle first)
gradle checkstyleMain checkstyleTest

# SpotBugs (uncomment configuration in build.gradle first)
gradle spotbugsMain spotbugsTest
```

## Key Classes

### Order
Main class for processing orders. See `Order.java` for complete method documentation.

**Key Method:** `processOrderItem(MenuItem item, List<String> modifiers)`
- See JavaDoc in Order.java for full specification including all return codes, validation rules, and processing order

### MenuItem
Menu items with base price, category, dietary flags, and allowed modifiers.
- See JavaDoc in MenuItem.java for details

### Table
Restaurant table representation with capacity and status.

## Testing

### Black-Box Testing
Test the `processOrderItem()` method based ONLY on its JavaDoc specification.

**Multiple implementations available for testing:**
- Order1.java - Might contain bugs (test to find them!)
- Order0.java - Might contain bugs (test to find them!)
- Order2.java - Might contain bugs (test to find them!)
- Order3.java - Might contain bugs (test to find them!)
- Order4.java - Might contain bugs (test to find them!)

## Sample Menu Items

Available in Restaurant.java initialization:
- **B001:** Classic Burger ($12.99, ENTREE)
- **S001:** Caesar Salad ($8.99, APPETIZER)
- **P001:** Alfredo Pasta ($14.99, ENTREE)

See Restaurant.java for complete menu.

## Tips for Success

1. **Read the JavaDoc carefully** - The specification is the contract
2. **Test boundaries** - Off-by-one errors are common
3. **Verify state changes** - Check return codes AND system state (items list, total price, order status)
4. **Think about edge cases** - null values, empty lists, invalid inputs
5. **Use descriptive test names** - Clearly indicate what is being tested

---

**Version:** 2.0
**Last Updated:** October 2025
