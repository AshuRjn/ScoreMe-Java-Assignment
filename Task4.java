import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    private DataSource dataSource;

    public List<ReportEntry> fetchMonthlyReport(String accountId, int month, int year) throws SQLException {
        List<ReportEntry> entries = new ArrayList<>();

        // FIX: Implemented Try-With-Resources for automatic resource closure in correct order (ResultSet -> PreparedStatement -> Connection)
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM report_entries WHERE account_id = ? AND MONTH(entry_date) = ? AND YEAR(entry_date) = ?"
             )) {

            ps.setString(1, accountId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entries.add(mapRow(rs));
                }
            }
        }
        return entries;
    }

    private ReportEntry mapRow(ResultSet rs) throws SQLException {
        return new ReportEntry();
    }
}