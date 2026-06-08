# Task 2 - ConcurrentModificationException Analysis

### Q1: What is the exact cause of ConcurrentModificationException in Java?
**Answer:** This exception occurs when a structural modification (adding, removing, or clearing elements) is made to a collection while a thread is actively iterating over it using an Iterator, enhanced for-loop, or Stream, without going through the Iterator's own methods. Java's fail-fast iterator detects that the collection's modification count (`modCount`) has changed unexpectedly during iteration and throws this error.

### Q2: What code pattern at line 142 most likely triggered this error?
**Answer:** The code most likely used an enhanced for-loop or an explicit iterator to traverse a transaction list, and inside that loop, it directly modified the list (e.g., `list.remove(transaction)` or `list.add(transaction)`) based on a filtering condition.

### Q3: Provide the minimal code change (one or two lines) that resolves this safely.
**Answer:** Use the `removeIf` method introduced in Java 8, which performs the iteration and removal safely via the iterator:
```java
// Safe single-line fix replacing the entire loop logic:
transactions.removeIf(transaction -> conditionMatches(transaction));