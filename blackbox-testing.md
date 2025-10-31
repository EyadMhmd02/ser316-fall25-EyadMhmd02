# Black Box Testing Report - Assignment 2

**Student Name:** [Eyad Ghanem]
**ASU ID:** [Your ASU ID]
**Date:** [30 OCT]

---

## Part 1: Equivalence Partitioning

Identify equivalence partitions for the `processOrderItem()` method based on the specification.

### Input: MenuItem

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|
| EP1 | Valid | Valid menu item, available | 0.0 or promotion code |
| EP2 | Invalid | Null menu item | 3.1 |
| EP3	Invalid	Unknown item ID (not on menu)	2.0
EP4	Invalid	Item unavailable or disabled	2.1


### Input: Modifiers

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|
| EP5	Valid	Valid or empty modifier list	0.0

| EP6	Invalid	Contains invalid modifier	2.1
  EP7	Valid	Allowed modifier combination (e.g. “NO_CHEESE”, “EXTRA_ONIONS”)	0.0

### Other Partitions (add more as needed)

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|

| Partition | Valid/Invalid | Description                                | Expected Return Code |
| --------- | ------------- | ------------------------------------------ | -------------------- |
| EP8       | Valid         | Valid dietary flag (e.g. “GLUTEN_FREE”)    | 0.15                 |
| EP9       | Invalid       | Conflict between dietary flag and modifier | 5.1                  |
| EP10      | Valid         | No dietary flag present                    | 0.0                  |


---

## Part 2: Boundary Value Analysis
| Boundary  | Value                  | Expected Result                |
| --------- | ---------------------- | ------------------------------ |
| Lower     | 1 item                 | Accepted normally              |
| Normal    | 3 items                | Accepted normally              |
| Upper     | 5 items                | Accepted normally              |
| Upper + 1 | 6 items                | Rejected – exceeds limit (2.0) |
| Extreme   | 0 or negative quantity | Invalid – rejected (3.0)       |

### Quantity Boundaries

| Boundary | Value | Expected Result |
|----------|-------|-----------------|
| Lower | 1 item in order | Item added successfully |
| Normal | 3 items in order | Item added successfully |
| Upper | 5 items of same ID | 5th item added successfully |
| Upper+1 | Try to add 6th item of same ID | Rejected with code 2.0 |

### Add more boundaries as needed:
| Boundary  | Value  | Expected Result                |
| --------- | ------ | ------------------------------ |
| Lower     | 0.0    | Invalid (3.1)                  |
| Normal    | 100.00 | Accepted                       |
| Upper     | 500.00 | Discount/promo triggered (4.1) |
| Upper + 1 | 501.00 | Invalid – rejected (5.0)       |

---

## Part 3: Test Cases Designed

List at least 20 test cases you designed based on your EP/BVA analysis.

| Test ID | EP/BVA | Test Description | Expected Return | Expected State |
|---------|--------|------------------|-----------------|----------------|
| Test ID | EP/BVA | Test Description                                 | Expected Return | Expected State                    |
| ------- | ------ | ------------------------------------------------ | --------------- | --------------------------------- |
| TC01    | EP1    | Add valid burger (B001) with no modifiers        | 0.0             | Added successfully                |
| TC02    | EP2    | Add null menu item                               | 3.1             | Rejected                          |
| TC03    | EP3    | Add item not on menu                             | 2.0             | Rejected                          |
| TC04    | EP4    | Add unavailable item                             | 2.1             | Rejected                          |
| TC05    | EP5    | Add valid salad with empty modifier list         | 0.0             | Added successfully                |
| TC06    | EP6    | Add burger with invalid modifier “ADD_PINEAPPLE” | 2.1             | Rejected                          |
| TC07    | EP7    | Add pasta with “EXTRA_CHEESE”                    | 0.0             | Added successfully                |
| TC08    | EP8    | Add gluten-free salad                            | 0.15            | Added with dietary discount       |
| TC09    | EP9    | Add nut-allergy item with “ADD_NUTS”             | 5.1             | Rejected                          |
| TC10    | EP10   | Add dessert with no dietary flags                | 0.0             | Added normally                    |
| TC11    | BVA    | Quantity = 1                                     | 0.0             | Accepted                          |
| TC12    | BVA    | Quantity = 5                                     | 0.0             | Accepted                          |
| TC13    | BVA    | Quantity = 6                                     | 2.0             | Rejected                          |
| TC14    | BVA    | Quantity = -1                                    | 3.0             | Rejected                          |
| TC15    | BVA    | Order total = 0.0                                | 3.1             | Invalid                           |
| TC16    | BVA    | Order total = 500.0                              | 4.1             | Discount applied                  |
| TC17    | BVA    | Order total = 501.0                              | 5.0             | Rejected                          |
| TC18    | EP/BVA | Valid gluten-free item with valid modifier       | 0.15            | Added successfully                |
| TC19    | EP/BVA | Invalid dietary + invalid modifier combination   | 5.1             | Rejected                          |
| TC20    | EP/BVA | Multiple items including invalid ID              | 2.2             | Partial process, warning returned |


---

## Part 4: Bug Analysis
| Implementation | Status   | # Tests Passed | # Tests Failed | Major Bugs Found                             |
| -------------- | -------- | -------------- | -------------- | -------------------------------------------- |
 |  Order0         | Buggy    | 13             | 7              | Incorrect discount logic                     |
| Order1         | Buggy    | 15             | 5              | Fails quantity >5 check                      |
| Order2         | Buggy    | 17             | 3              | W rong handling of dietary flag               |
 | Order3         | Bug-Free | 20             | 0              | None                                         |
| Order4         | Buggy    | 18             | 2              | “EGG9” message and modifier validation issue |

### Easter Eggs Found

List any easter egg messages you discovered:
-
-

### Implementation Results

| Implementation | Status   | # Tests Passed | # Tests Failed | Major Bugs Found                             |
| ------- ------ | -------- | ----------d ---- | ------ ------ | -------------- ------------------------------ |
| Order0         | Buggy    | 13             | 7               | Incorrect discount logic                     |
| Order1         | Bugg y    | 17             | 3              | Wrong handling of dietary flag               |
| Order3         | Bug-Free | 20             | 0              | None                                         |
| Order4         | Buggy    | 18             | 2              | “EGG9” message and modifier validation issue |

### Bugs Discovered

Order0:

Bug: Discount not applied for dietary items

Revealed by: TC08

Order1:

Bug: Quantity validation missing

Revealed by: TC13

Order2:

Bug: Wrong return code for dietary flag violation

Revealed by: TC09

Order3:

All tests passed; behaves as expected

Order4:

Bug: Prints “EGG9: Got me” instead of proper return code

Revealed by: TC06
### Most Correct Implementation

**Answer:** Order__3__ appears to be the most correct implementation.

**Justification:**
handled all partitions, boundaries, and return codes correctly.
It passed all 20 test cases and showed no incorrect output or debug prints.
All other implementations failed at least one boundary or dietary rule test.

---

## Part 5: Reflection

**Which testing technique was most effective for finding bugs?**
Boundary Value Analysis was the most effective because several bugs appeared
orders with 6 items or totals above 500
Testing at these extremes revealed logic and validation issues that wouldn’t be noticed with normal inputs. :)
**What was the most challenging aspect of this assignment?**

The hardest part was mapping the JavaDoc
