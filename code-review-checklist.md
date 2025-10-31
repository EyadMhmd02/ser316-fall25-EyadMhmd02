# Code Review Checklist

**Reviewer Name:** [Eyad]
**Date:** [30 OCT]
**Branch:** Review

## Instructions
Review ALL source files in the project and identify defects using the categories below. Log at least 7 defects total:
- At least 1 from CS (Coding Standards)
- At least 1 from CG (Code Quality/General)
- At least 1 from FD (Functional Defects)
- Remaining can be from any category

## Review Categories

- **CS**: Coding Standards (naming conventions, formatting, style violations)
- **CG**: Code Quality/General (design issues, code smells, maintainability)
- **FD**: Functional Defects (logic errors, incorrect behavior, bugs)
- **MD**: Miscellaneous (documentation, comments, other issues)

## Defect Log

| Defect ID | File | Line(s) | Category | Description | Severity |
|-----------|------|---------|----------|-------------|----------|
| 1 | Order.java | 241-290 | MD | The JavaDoc for `processOrderItem()` is excessively long and describes internal logic instead of external behavior. This violates documentation best practices. | Medium |
| 2 | Restaurant.java | 13 | CS | Variable name `x` is unclear and doesn’t follow standard naming conventions. It should be renamed to something descriptive like `menuItemCount`. | Low |
| 3 | Restaurant.java | 108-113 | CG | The `formatPrice() method performs inefficient string concatenation. Should use `String.format("$%.2f", price)` for better readability and precision. | Medium |
| 4 | Restaurant.java | 98 | CG | The private method removeMenuItem()` is never used. This introduces dead code and potential confusion. It should be removed or tested. | Low |
| 5 | Restaurant.java | 121 | FD | `calculateDiscount() performs raw multiplication without validation. If `discountPercent > 1`, it will incorrectly increase price. Validation is required. | High |
| 6 | MenuItem.java | 40-45 | CS | Inconsistent indentation and missing space before braces in `addAllowedModifier() reduces readability. | Low |
| 7 | Order.java | 210 | CG | Multiple nested conditionals make the method hard to read. Suggest refactoring with early returns or helper methods. | Medium |
| 8 | Restaurant.java | 85 | MD | `printMenu() lacks proper formatting and does not align prices or categories clearly for users. | Low |
| 9 | Table.java | 32 | FD | `assignServer() does not validate if the table is already assigned. Could lead to overwriting server assignment. | High |
| 10 | Order.java | 152 | CG | No unit test coverage for helper methods like `validateModifier()`, making debugging harder. | Medium |
**Severity Levels:**
- **Critical**: Causes system failure, data corruption, or security issues
- **High**: Major functional defect or significant quality issue
- **Medium**: Moderate issue affecting maintainability or minor functional problem
- **Low**: Minor style issue or cosmetic problem

## Example Entry

| Defect ID | File | Line(s) | Category | Description | Severity |
|-----------|------|---------|----------|-------------|----------|
| 1 | Order.java | 65 | MD | Method initOrder() is missing JavaDoc documentation | Low |

## Notes
- Be specific with line numbers
- Provide clear, actionable descriptions
- Consider: readability, maintainability, correctness, performance, security
- Focus on issues that impact code quality or functionality
