# Static Analysis Report - Assignment 4

**Student Name:** [Your Name]
**ASU ID:** [Your ASU ID]
**Date:** [Date]

---

## Part 1: GitHub Actions Setup

**Which branches fail on GitHub Actions? Explain why.**

1. **Branch:** Review
   - **Status:** Failing
   - **Reason:** The workflow run for "Create gradle.yml" on the Review branch failed. This is likely due to compilation errors, test failures, or static analysis violations (Checkstyle/SpotBugs) that prevent the Gradle build from completing successfully.

2. **Branch:** main
   - **Status:** Failing
   - **Reason:** Multiple workflow runs on the main branch have failed (e.g., "Delete .DS_Store", "Delete .gradle directory"). The build is likely failing due to code quality issues, compilation errors, or test failures that need to be addressed.

3. **Branch:** Blackbox
   - **Status:** Failing
   - **Reason:** The workflow run for "Delete .gradle directory" on the Blackbox branch failed. Additionally, there was a failed run for "Update JDK version from 17 to 18 in workflow", suggesting the branch may have build or test failures that prevent successful completion of the CI pipeline.

4. **Branch:** WhiteBox
   - **Status:** Passing
   - **Reason:** The workflow run for "Fix casing of 'Whitebox' to 'WhiteBox' in workflow" on the WhiteBox branch completed successfully. The build passed, indicating that the code compiles correctly, tests pass, and there are no critical static analysis violations blocking the build.

5. **Branch:** StaticAnalysis
   - **Status:** Passing
   - **Reason:** Multiple workflow runs on the StaticAnalysis branch have completed successfully (e.g., "Delete build directory", "Delete .gradle directory"). The builds passed, indicating that the code quality improvements and fixes applied on this branch have resolved the issues that were causing failures on other branches.

6. **Branch:** dev
   - **Status:** Failing
   - **Reason:** The dev branch has mixed results with both successful and failing workflow runs. While the most recent run "Update gradle.yml" (#23) was successful, there were previous failures including "Update Gradle CI configuration for branches and JDK" (#19) and "make task4" (#18). The branch has experienced build failures, likely due to configuration issues, compilation errors, or test failures that need to be resolved.

---

## Part 2: Checkstyle Analysis

### Initial Results (StaticAnalysis branch - before fixes)

**Main source violations:** 79
**Test source violations:** 96

### After Fixing Order.java

**Main source violations:** 32
**Violations fixed:** 47

---

## Part 3: SpotBugs Analysis

### Initial Results (StaticAnalysis branch - before fixes)

**Bugs found in main:** 6 (1 P1 priority, 5 P2 priority)

### Bugs Fixed in Order.java

After fixing Order.java, the number of bugs was reduced from 6 to 1. Five P2 priority bugs were fixed, leaving 1 P1 priority bug remaining.

**Remaining bug (not fixed):**
1. **Bug:** Reliance on default encoding 
   - **Category:** Internationalization - Dubious method used
   - **Priority:** P1
   - **Location:** Main source code
   - **Fix applied:** Not fixed - this bug remains in the codebase

---

## Part 4: Branch Comparison

### Checkstyle Comparison

| Branch | Main Violations | Test Violations | Total |
|--------|----------------|-----------------|-------|
| Blackbox | Many violations | Clean | - |
| Review | Many violations | Improved | - |
| StaticAnalysis (initial) | 79 | 96 | 175 |
| StaticAnalysis (after fixes) | 32 | 96 | 128 |

### SpotBugs Comparison

| Branch | Main Bugs | Test Bugs | Total |
|--------|-----------|-----------|-------|
| Blackbox | 1 (P1) | 0 | 1 |
| Review | 6 (1 P1 + 5 P2) | 0 | 6 |
| StaticAnalysis (initial) | 6 (1 P1 + 5 P2) | 0 | 6 |
| StaticAnalysis (after fixes) | 1 (P1) | 0 | 1 |

**Did Review branch improve code quality compared to Blackbox?**

No, the Review branch did not improve code quality compared to Blackbox. While the Review branch showed noticeable improvement in test violations (as noted in the observations), it actually introduced more logic bugs - increasing from 1 SpotBugs bug (P1) in Blackbox to 6 SpotBugs bugs (1 P1 + 5 P2) in Review. The Review branch maintained many formatting and style violations similar to Blackbox, but the increase in logic bugs indicates a regression in code quality despite the test code improvements.


---

## Part 5: Merging to Dev Branch

### Merge Strategy

**How did you merge Review and StaticAnalysis into Dev?**

The Review and StaticAnalysis branches were merged into the dev branch using Git merge operations. This combined the code changes and improvements from both branches into a single development branch.

**Merge conflicts encountered:** Not specified / None mentioned

### Dev Branch Quality After Merge

**Checkstyle violations:** 145 total (33 main violations, 112 test violations)
- Main.java: 20 violations
- Order.java: 7 violations
- Restaurant.java: 6 violations
- WhiteBoxTestStarter.java: 51 violations
- ProcessOrderItemTest.java: 45 violations
- BlackBox.java: 16 violations

**SpotBugs issues:** 1 (1 P1 priority bug in main source, 0 bugs in test source)

**Did quality improve or worsen? Explain:**

The quality worsened slightly compared to the StaticAnalysis branch after fixes. Checkstyle violations increased from 128 total (32 main + 96 test) to 145 total (33 main + 112 test), representing an increase of 17 violations overall. The main source violations increased by 1 (from 32 to 33), while test violations increased by 16 (from 96 to 112). However, SpotBugs issues remained the same at 1 P1 priority bug. The increase in violations suggests that merging the branches may have introduced some code style issues, particularly in the test files, or that the dev branch contains code from Review branch that had more violations.

**Build successful:** Yes 

---

## Part 6: Reflection

**Do you think your code got better through this process?**

Yes, the code definitely improved. We reduced Checkstyle violations from 79 to 32 in the main source code (a reduction of 47 violations) and fixed 5 SpotBugs issues, reducing bugs from 6 to 1. The static analysis tools helped identify and fix many code quality issues that would have been difficult to catch manually.

**In what order would you use these quality practices in the future?**

1. Run Checkstyle first to fix formatting and style issues
2. Run SpotBugs to identify logic bugs and potential problems
3. Fix the issues found by both tools
4. Run the tools again to verify all issues are resolved
5. Merge to dev branch after ensuring quality standards are met

**Most valuable lesson:**

The most valuable lesson is that static analysis tools like Checkstyle and SpotBugs can catch many code quality issues early in development. Using these tools regularly helps maintain clean, consistent, and bug-free code. It's better to fix issues as you go rather than letting them accumulate.


