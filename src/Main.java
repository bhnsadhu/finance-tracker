import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <transactions.csv>");
            System.out.println("CSV header: date,type,category,amount,note");
            return;
        }

        String filePath = args[0];
        Transaction[] tx;

        try {
            tx = CsvReader.readTransactions(filePath);
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
            return;
        }

        if (tx.length == 0) {
            System.out.println("No transactions found. Check your CSV.");
            return;
        }

        double income = BudgetReport.totalIncome(tx);
        double expenses = BudgetReport.totalExpenses(tx);
        double net = income - expenses;
        double savingsRate = (income > 0) ? (net / income) : 0;

        System.out.println("==== Personal Budget Analyzer (CS124 Java) ====");
        System.out.println("Transactions loaded: " + tx.length);
        System.out.println();

        System.out.println("Summary:");
        System.out.println("  Total Income:   " + BudgetReport.money(income));
        System.out.println("  Total Expenses: " + BudgetReport.money(expenses));
        System.out.println("  Net Savings:    " + BudgetReport.money(net));
        System.out.println("  Savings Rate:   " + String.format("%.1f%%", savingsRate * 100.0));
        System.out.println();

        // Category totals (parallel arrays)
        String[] categories = new String[200];  // big enough for beginner project
        double[] totals = new double[200];

        int used = BudgetReport.buildCategoryTotals(tx, categories, totals);
        BudgetReport.sortByTotalDescending(categories, totals, used);

        System.out.println("Expenses by Category:");
        for (int i = 0; i < used; i++) {
            System.out.println("  " + categories[i] + ": " + BudgetReport.money(totals[i]));
        }
        System.out.println();

        System.out.println("Top 5 Expenses:");
        Transaction[] top = BudgetReport.topExpenses(tx, 5);
        for (int i = 0; i < top.length; i++) {
            Transaction t = top[i];
            System.out.println("  " + t.date + " | " + t.category + " | " +
                    BudgetReport.money(t.amount) +
                    (t.note.equals("") ? "" : " | " + t.note));
        }
    }
}
