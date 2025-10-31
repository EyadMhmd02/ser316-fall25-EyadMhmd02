# Black Box Testing Report - Assignment 2

**Student Name:** [Your Name]
**ASU ID:** [Your ASU ID]
**Date:** [Date]

---

## Part 1: Equivalence Partitioning

Identify equivalence partitions for the `processOrderItem()` method based on the specification.

### Input: MenuItem

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|
| EP1 | Valid | Valid menu item, available | 0.0 or promotion code |
| EP2 | Invalid | Null menu item | 3.1 |
| EP3 | | | |
| EP4 | | | |

### Input: Modifiers

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|
| EP5 | Valid | Empty/null modifiers list | Process normally |
| EP6 | Invalid | Contains invalid modifier | 2.1 |
| EP7 | | | |

### Other Partitions (add more as needed)

| Partition | Valid/Invalid | Description | Expected Return Code |
|-----------|---------------|-------------|---------------------|
| EP8 | | | |
| EP9 | | | |
| EP10 | | | |

---

## Part 2: Boundary Value Analysis

### Quantity Boundaries

| Boundary | Value | Expected Result |
|----------|-------|-----------------|
| Lower | 1 item in order | Item added successfully |
| Normal | 3 items in order | Item added successfully |
| Upper | 5 items of same ID | 5th item added successfully |
| Upper+1 | Try to add 6th item of same ID | Rejected with code 2.0 |

### Add more boundaries as needed:

---

## Part 3: Test Cases Designed

List at least 20 test cases you designed based on your EP/BVA analysis.

| Test ID | EP/BVA | Test Description | Expected Return | Expected State |
|---------|--------|------------------|-----------------|----------------|
| TC01 | EP1 | Add valid burger, no modifiers | 0.0 | 1 item, $12.99 |
| TC02 | | | | |
| TC03 | | | | |
| TC04 | | | | |
| TC05 | | | | |
| TC06 | | | | |
| TC07 | | | | |
| TC08 | | | | |
| TC09 | | | | |
| TC10 | | | | |
| TC11 | | | | |
| TC12 | | | | |
| TC13 | | | | |
| TC14 | | | | |
| TC15 | | | | |
| TC16 | | | | |
| TC17 | | | | |
| TC18 | | | | |
| TC19 | | | | |
| TC20 | | | | |

---

## Part 4: Bug Analysis

### Easter Eggs Found

List any easter egg messages you discovered:
-
-

### Implementation Results

| Implementation | Status | # Tests Passed | # Tests Failed | Major Bugs Found |
|----------------|--------|----------------|----------------|------------------|
| Order0 | Bug-free / Buggy | | | |
| Order1 | Bug-free / Buggy | | | |
| Order2 | Bug-free / Buggy | | | |
| Order3 | Bug-free / Buggy | | | |
| Order4 | Bug-free / Buggy | | | |

### Bugs Discovered

**Order0:**
- Bug 1: [Brief description] - Revealed by test: [TC##]

**Order1:**
- Bug 1: [Brief description] - Revealed by test: [TC##]

**Order2:**
- Bug 1: [Brief description] - Revealed by test: [TC##]

**Order3:**
- Bug 1: [Brief description] - Revealed by test: [TC##]

**Order4:**
- Bug 1: [Brief description] - Revealed by test: [TC##]

### Most Correct Implementation

**Answer:** Order____ appears to be the most correct implementation.

**Justification:**


---

## Part 5: Reflection

**Which testing technique was most effective for finding bugs?**


**What was the most challenging aspect of this assignment?**


