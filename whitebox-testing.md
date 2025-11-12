# White Box Testing Report - Assignment 3

**Student Name:** Eyad
**ASU ID:** EyadMhmd02
**Date:** [Fill in current date]

---

## Part 1: Control Flow Graph for canModifyOrder()

### Graph Description

**Control Flow Graph for canModifyOrder() (lines 348-359 in Order.java):**

```
START
  │
  ▼
┌─────────────────┐
│  N1: Line 349   │
│ if (orderStatus │
│    >= 2)        │
└──┬──────────┬───┘
   │ T        │ F
   ▼          ▼
┌─────────┐  ┌─────────────────┐
│ E1:     │  │  N2: Line 352    │
│ return  │  │ if (items.size()  │
│ false   │  │  >= MAX_TOTAL_    │
│         │  │  ITEMS)           │
└──┬──────┘  └──┬───────────┬───┘
   │            │ T         │ F
   │            ▼           ▼
   │        ┌─────────┐  ┌─────────────────┐
   │        │ E3:     │  │  N3: Line 355   │
   │        │ return  │  │ if (totalPrice   │
   │        │ false   │  │  >= MAX_ORDER_  │
   │        │         │  │  TOTAL)          │
   │        └──┬──────┘  └──┬───────────┬───┘
   │           │            │ T         │ F
   │           │            ▼           ▼
   │           │        ┌─────────┐  ┌──────────┐
   │           │        │ E5:      │  │  N4:     │
   │           │        │ return   │  │ return   │
   │           │        │ false    │  │ true     │
   │           │        │          │  │ (E6)     │
   │           │        └──┬───────┘  └─────┬────┘
   │           │           │                │
   └───────────┴───────────┴────────────────┘
                    │
                    ▼
                 EXIT
```

**Node Descriptions:**
- **N1** (Line 349): Decision node checking if `orderStatus >= 2`
- **N2** (Line 352): Decision node checking if `items.size() >= MAX_TOTAL_ITEMS` (5)
- **N3** (Line 355): Decision node checking if `totalPrice >= MAX_ORDER_TOTAL` (100.0)
- **N4** (Line 358): Return statement returning `true`

**Edge Descriptions:**
- **E1**: N1(T) → return false (line 350)
- **E2**: N1(F) → N2 (line 352)
- **E3**: N2(T) → return false (line 353)
- **E4**: N2(F) → N3 (line 355)
- **E5**: N3(T) → return false (line 356)
- **E6**: N3(F) → N4 → return true (line 358)

### Node Coverage Sequences

List the sequences needed for complete node coverage:

**Sequence 1 (S1):**
- **Path:** [N1(T) via E1] → return false
- **Purpose:** Cover node N1 when orderStatus >= 2
- **Test case:** `testCanModifyOrder_StatusAtLeast2_ReturnsFalse()`

**Sequence 2 (S2):**
- **Path:** [N1(F) via E2, N2(T) via E3] → return false
- **Purpose:** Cover nodes N1, N2 when status < 2 but items.size() >= 5
- **Test case:** `testCanModifyOrder_ItemsAtLimit_ReturnsFalse()`

**Sequence 3 (S3):**
- **Path:** [N1(F) via E2, N2(F) via E4, N3(T) via E5] → return false
- **Purpose:** Cover nodes N1, N2, N3 when status < 2, items < 5, but totalPrice >= 100.0
- **Test case:** `testCanModifyOrder_TotalAtOrAboveMax_ReturnsFalse()`

**Sequence 4 (S4):**
- **Path:** [N1(F) via E2, N2(F) via E4, N3(F) via E6, N4] → return true
- **Purpose:** Cover all nodes N1, N2, N3, N4 when all conditions are false (happy path)
- **Test case:** `testCanModifyOrder_AllConditionsFalse_ReturnsTrue()`

### Edge Coverage Sequences

List the sequences needed for complete edge coverage:

**Sequence 1 (S1):**
- **Edges covered:** E1
- **Test case:** `testCanModifyOrder_StatusAtLeast2_ReturnsFalse()`

**Sequence 2 (S2):**
- **Edges covered:** E2, E3
- **Test case:** `testCanModifyOrder_ItemsAtLimit_ReturnsFalse()`

**Sequence 3 (S3):**
- **Edges covered:** E2, E4, E5
- **Test case:** `testCanModifyOrder_TotalAtOrAboveMax_ReturnsFalse()`

**Sequence 4 (S4):**
- **Edges covered:** E2, E4, E6
- **Test case:** `testCanModifyOrder_AllConditionsFalse_ReturnsTrue()`

**Note:** All four sequences together cover all 6 edges (E1-E6) and all 4 nodes (N1-N4) for complete coverage.


---

## Part 2: Code Coverage with JaCoCo

### Initial Coverage for Order.java

**Before adding tests:**
- **Line Coverage:** [Run `gradle jacocoTestReport` and check report - fill in percentage]
- **Branch Coverage:** [Run `gradle jacocoTestReport` and check report - fill in percentage]

**Instructions:** Run `./gradlew build`, then `./gradlew test`, then `./gradlew jacocoTestReport`. Open `build/reports/jacoco/test/html/index.html` and check the Order.java coverage.

### Coverage for calculateBillSplit()

**Before additional tests:**
- **Branch Coverage:** [Check initial coverage for calculateBillSplit() method in JaCoCo report]

**After reaching 80% branch coverage:**
- **Branch Coverage:** [Target: 80%+ - fill in after running jacocoTestReport]
- **Tests added:**
  1. `testCalculateBillSplit_InvalidNumDiners_ReturnsNull()` - Tests numDiners <= 0 branch
  2. `testCalculateBillSplit_NegativeTip_ReturnsNull()` - Tests tipPercent < 0 branch
  3. `testCalculateBillSplit_TipOver100_ReturnsNull()` - Tests tipPercent > 100 branch
  4. `testCalculateBillSplit_SingleDiner_ReturnsCorrectAmount()` - Tests numDiners = 1 (no loop)
  5. `testCalculateBillSplit_TwoDiners_EqualSplit()` - Tests numDiners = 2 (one loop iteration)
  6. `testCalculateBillSplit_MultipleDiners_HandlesRounding()` - Tests numDiners = 3+ (multiple iterations)
  7. `testCalculateBillSplit_ZeroTip_NoTipAdded()` - Tests tipPercent = 0 boundary
  8. `testCalculateBillSplit_Tip100Percent_DoublesTotal()` - Tests tipPercent = 100 boundary
  9. `testCalculateBillSplit_LargeGroup_HandlesMultiplePeople()` - Tests numDiners = 5+ (multiple loop iterations)

**Total tests added for calculateBillSplit():** 9 test methods

### Final Overall Coverage

- **Line Coverage:** [Fill in after running jacocoTestReport - check overall Order.java coverage]
- **Branch Coverage:** [Fill in after running jacocoTestReport - check overall Order.java coverage]

**Note:** To view the report, run `./gradlew jacocoTestReport` and open `build/reports/jacoco/test/html/index.html` in your browser. Navigate to the Order class to see detailed coverage metrics.

---

## Part 3: processOrderItem() Implementation

### Test-Driven Development Process

**Number of tests from BlackBox assignment:** 9 tests (copied and adapted from ProcessOrderItemBlackBoxTest.java)

**Tests created in ProcessOrderItemTest.java:**
1. `testSuccess_NoModifiers_Returns0_0_AddsItemAndUpdatesTotal()` - Tests successful addition
2. `testSuccess_NullModifiers_TreatedAsEmpty()` - Tests null modifiers handling
3. `testQuantityLimit_MaxFive_Returns2_0OnSixth()` - Tests quantity limit (return code 2.0)
4. `testInvalidModifier_Returns2_1_DoesNotAdd()` - Tests invalid modifier (return code 2.1)
5. `testUnavailableItem_Returns3_0()` - Tests unavailable item (return code 3.0)
6. `testNullItem_Returns3_1()` - Tests null item (return code 3.1)
7. `testInvalidItemIdFormat_Returns4_1()` - Tests invalid item ID format (return code 4.1)
8. `testOrderFinalized_Returns5_0()` - Tests finalized order (return code 5.0)
9. `testPriceIncludesModifierCosts()` - Tests modifier price calculation

**Implementation challenges:**
1. **Understanding the simplified specification:** The JavaDoc contains both full and simplified specifications. I had to carefully identify which return codes to implement (0.0, 2.0, 2.1, 3.0, 3.1, 4.1, 5.0) and which features to skip (promotions 0.x, incompatible modifiers 2.2, max order total 5.1).

2. **Validation order:** The method must check conditions in the exact sequence specified: order status → null item → item ID format → availability → quantity limit → modifier validation → calculate price → add item. Getting the order wrong would cause incorrect return codes.

3. **Modifier price calculation:** Using the existing `calculateModifierPrice()` helper method correctly to add modifier costs to the base price.

4. **Stock management:** Ensuring `item.reduceStock()` is called only when the item is successfully added to the order.

**All tests passing:** Yes

The implementation follows the simplified specification exactly:
- Returns 0.0 on successful addition
- Returns 2.0 when quantity limit (5 items with same ID) is reached
- Returns 2.1 when invalid modifier is provided
- Returns 3.0 when item is unavailable
- Returns 3.1 when item is null
- Returns 4.1 when item ID format is invalid (non-alphanumeric)
- Returns 5.0 when order status >= 3 (finalized)
- Properly calculates price with modifiers
- Updates total and reduces stock on success

---

## Part 4: Reflection

**How did white-box testing differ from black-box testing?**

White-box testing differs from black-box testing in several key ways:

1. **Knowledge of Internal Structure:** White-box testing requires access to and understanding of the source code, including control flow, decision points, and internal logic. Black-box testing treats the system as a "black box" and tests only based on specifications and expected behavior without knowing internal implementation.

2. **Coverage Focus:** White-box testing aims for structural coverage (statement coverage, branch coverage, path coverage) by analyzing control flow graphs and ensuring all code paths are exercised. Black-box testing focuses on functional coverage, testing equivalence partitions, boundary values, and use cases based on requirements.

3. **Test Design:** In white-box testing, I designed tests based on the CFG analysis, identifying specific nodes and edges to cover. For `canModifyOrder()`, I created tests targeting each sequence (S1-S4) to ensure complete node and edge coverage. In black-box testing, I designed tests based on input/output specifications and equivalence classes.

4. **Tools Used:** White-box testing benefits from code coverage tools like JaCoCo to measure coverage metrics. Black-box testing relies more on test case design techniques and specification-based testing.

5. **Debugging:** White-box testing can identify which specific lines or branches are not covered, making it easier to target missing test cases. Black-box testing might miss internal paths that aren't exposed through external behavior.

6. **Test Granularity:** White-box tests can be very granular, testing individual branches and decision points. Black-box tests are typically more coarse-grained, testing complete scenarios and use cases.

**Which approach do you find more effective? Why?**

Both approaches are valuable and complement each other, but I find **a combination of both** to be most effective:

**White-box testing is more effective for:**
- Ensuring code coverage and finding untested code paths
- Identifying dead code or unreachable branches
- Debugging specific issues when you know the code structure
- Testing edge cases that might not be obvious from specifications
- Validating that all error conditions are properly handled

**Black-box testing is more effective for:**
- Testing from the user's perspective and real-world scenarios
- Validating that the system meets requirements and specifications
- Finding integration issues and unexpected interactions
- Testing without needing to understand complex implementation details
- Ensuring the system behaves correctly at the API/interface level

**Why a combination works best:**
- White-box testing ensures thorough coverage of internal logic but might miss specification misunderstandings
- Black-box testing validates external behavior but might miss hidden code paths
- Together, they provide comprehensive testing: white-box ensures internal correctness, black-box ensures external correctness
- In practice, I used black-box testing first (Assignment 2) to understand expected behavior, then white-box testing (Assignment 3) to ensure complete coverage of the implementation

For this assignment, white-box testing was particularly valuable for `canModifyOrder()` because it helped identify all four distinct code paths and ensure each was properly tested, which might have been missed with only black-box testing.
