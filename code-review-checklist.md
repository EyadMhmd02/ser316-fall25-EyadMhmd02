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
| 1 | Main.java | 22 | FD | Uses `Order1` which does not exist; should be `Order`. Causes compilation failure. | Critical |
| 2 | Main.java | 149-176 | FD | `interpretResultCode()` expects promotion codes in the 6.x range, conflicting with the spec in `Order.java` which defines promotions as 0.x. Mismatched protocol leads to incorrect messaging. | High |
| 3 | Restaurant.java | 11-13 | CS | Poor variable names: `m`, `n`, and `x` are non-descriptive. Rename to `menuById`, `name`, and `menuItemCount`. | Low |
| 4 | Restaurant.java | 116-121 | CG | `formatPrice()` builds strings via repeated concatenation and does not format to two decimals. Prefer `String.format("$%.2f", price)`. | Medium |
| 5 | Restaurant.java | 87-89 | CG | `removeMenuItem()` is private and never used (dead code). Remove or add tests/callers. | Low |
| 6 | Order.java | 267-269 | FD | `processOrderItem()` is a stub that always returns 0.0; none of the documented validations or updates are implemented. | Critical |
| 7 | Order.java | 137-151 | CG | `areModifiersCompatible()` contains empty `else` branches and redundant checks; simplify with early returns. | Low |
| 8 | Order.java | 352-371 | CG | `generateOrderSummary()` uses inefficient string concatenation in a loop and vague parameters (`disc`, `incTotal`). Use `StringBuilder` and clearer naming. | Low |
| 9 | Order.java | 374-391 | FD | `processPayment()` sets status to PAID without validating amount or matching total; missing basic payment validation and error handling. | High |
| 10 | MenuItem.java | 151-153 | CG | `setStockCount(int)` allows negative values; add validation to prevent invalid stock. | Low |
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
