import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    // Reads CSV into an array of Transaction
    // CS124-style: first count lines, then allocate array, then fill it.
    public static Transaction[] readTransactions(String filePath) throws IOException {
        int count = countDataLines(filePath);
        Transaction[] tx = new Transaction[count];

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String header = br.readLine(); // skip header

        int i = 0;
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            // Simple split (notes should not contain commas)
            String[] parts = line.split(",", -1);
            if (parts.length < 5) continue;

            String date = parts[0].trim();
            String type = parts[1].trim().toLowerCase();
            String category = parts[2].trim();
            String amountStr = parts[3].trim();
            String note = parts[4].trim();

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                continue;
            }
            if (amount < 0) amount = -amount;

            tx[i] = new Transaction(date, type, category, amount, note);
            i++;
        }
        br.close();

        // If we skipped bad lines, shrink array
        if (i < tx.length) {
            Transaction[] smaller = new Transaction[i];
            for (int k = 0; k < i; k++) smaller[k] = tx[k];
            return smaller;
        }

        return tx;
    }

    private static int countDataLines(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int lines = 0;

        String header = br.readLine(); // skip header
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) lines++;
        }
        br.close();
        return lines;
    }
}
