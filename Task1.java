import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanAccountService {
    public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
        // FIX: Initialized ArrayList to avoid NullPointerException
        List<LoanAccount> result = new ArrayList<>();

        if (accounts == null) {
            return result;
        }

        for (LoanAccount account : accounts) {
            if (account != null) {
                // FIX: Added null check for dueDate to handle restructured accounts safely
                if (account.getDueDate() != null && account.getDueDate().before(new Date())) {
                    // FIX: Precision-safe check for double outstanding balance to filter out zero balance correctly
                    if (account.getOutstandingBalance() > 0.0) {
                        result.add(account);
                    }
                }
            }
        }
        return result;
    }
}