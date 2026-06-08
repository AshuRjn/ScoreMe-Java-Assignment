import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BankStatementBatchProcessor {

    // FIX: Changed primitive int to AtomicInteger to prevent race conditions across 10 threads
    private final AtomicInteger processedCount = new AtomicInteger(0);

    public void process(List<StatementRecord> records) {
        if (records == null) return;

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (StatementRecord record : records) {
            executor.submit(() -> {
                processRecord(record);
                // FIX: Using atomic thread-safe increment operation
                processedCount.incrementAndGet();
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getProcessedCount() {
        // FIX: Returning the atomic integer value as primitive int
        return processedCount.get();
    }

    private void processRecord(StatementRecord record) {}
}