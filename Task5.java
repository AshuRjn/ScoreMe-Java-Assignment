import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class DocumentValidator {

    // FIX: Added SLF4J Logger to manage production logging professionally
    private static final Logger log = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {
        try {
            if (doc == null) {
                // FIX: Throwing specific IllegalArgumentException for expected validation failure
                throw new IllegalArgumentException("Document is null");
            }
            String content = doc.extractContent();
            if (content == null || content.isEmpty()) {
                // FIX: Throwing specific IllegalArgumentException for business rule validation
                throw new IllegalArgumentException("Empty content");
            }
            return runValidationRules(content);

        } catch (IllegalArgumentException e) {
            // FIX: Logging expected validation failure at WARN level without dumping entire stack trace
            log.warn("Validation failure: {}", e.getMessage());
            return new ValidationResult(false); // FIX: Returning invalid result instead of null to prevent NPE downstream
        } catch (Exception e) {
            // FIX: Logging unexpected critical system errors with full stack trace at ERROR level
            log.error("Unexpected runtime exception during validation", e);
            return new ValidationResult(false);
        }
    }

    public void validateBatch(List<Document> docs) {
        if (docs == null) return;

        for (Document doc : docs) {
            try {
                ValidationResult r = validate(doc);
                // FIX: Added safe null check before accessing isValid()
                if (r != null && r.isValid()) {
                    saveResult(r);
                }
            } catch (Exception e) {
                // FIX: Stopped silent swallowing, logging the batch processing failures appropriately
                log.error("Error processing document in batch flow", e);
            }
        }
    }

    private ValidationResult runValidationRules(String content) { return new ValidationResult(true); }
    private void saveResult(ValidationResult r) {}
}