/**
 * Task 2: Log Analysis for ConcurrentModificationException
 * PSU Bank Client Error Diagnostic
 */
public class Task2Analysis {

    // Q1: What is the exact cause of ConcurrentModificationException in Java?
    // FIX: This exception occurs when a collection (like ArrayList) is structurally
    // modified (elements added, removed, or cleared) directly while a thread is
    // actively iterating over it using an Iterator, enhanced for-loop, or Stream.
    // Java's fail-fast iterator detects that the collection's modification count
    // (modCount) has changed unexpectedly mid-iteration and throws this error.

    // Q2: What code pattern at line 142 most likely triggered this error?
    // FIX: The code at line 142 most likely used an enhanced for-loop (for-each)
    // to traverse the transaction list, and inside that loop, it directly called
    // transactions.remove(transaction) or transactions.add(transaction) based on
    // a filtering condition, which invalidates the loop's underlying iterator state.

    // Q3: Provide the minimal code change (one or two lines) that resolves this safely.
    // FIX: The safest and most minimal solution in Java 8+ is to replace the structural
    // modification loop with the single-line collection-native removeIf() method:
    // transactions.removeIf(transaction -> conditionMatches(transaction));

}