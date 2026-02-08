public class BudgetReport {

    public static double totalIncome(Transaction[] tx) {
        double sum = 0;
        for (int i = 0; i < tx.length; i++) {
            if (tx[i].type.equals("income")) sum += tx[i].amount;
        }
        return sum;
    }

    public static double totalExpenses(Transaction[] tx) {
        double sum = 0;
        for (int i = 0; i < tx.length; i++) {
            if (tx[i].type.equals("expense")) sum += tx[i].amount;
        }
        return sum;
    }

    // CS124-style “category totals” using parallel arrays:
    // categories[] holds unique categories, totals[] holds sums
    public static int buildCategoryTotals(Transaction[] tx, String[] categories, double[] totals) {
        int used = 0;

        for (int i = 0; i < tx.length; i++) {
            if (!tx[i].type.equals("expense")) continue;

            String cat = tx[i].category;
            int idx = indexOf(categories, used, cat);

            if (idx == -1) {
                categories[used] = cat;
                totals[used] = tx[i].amount;
                used++;
            } else {
                totals[idx] += tx[i].amount;
            }
        }
        return used;
    }

    private static int indexOf(String[] arr, int used, String target) {
        for (int i = 0; i < used; i++) {
            if (arr[i].equals(target)) return i;
        }
        return -1;
    }

    public static void sortByTotalDescending(String[] categories, double[] totals, int used) {
        // Selection sort (CS124-friendly)
        for (int i = 0; i < used; i++) {
            int best = i;
            for (int j = i + 1; j < used; j++) {
                if (totals[j] > totals[best]) best = j;
            }
            // swap totals
            double tmpT = totals[i];
            totals[i] = totals[best];
            totals[best] = tmpT;

            // swap categories
            String tmpC = categories[i];
            categories[i] = categories[best];
            categories[best] = tmpC;
        }
    }

    public static Transaction[] topExpenses(Transaction[] tx, int n) {
        // Copy only expenses into an array
        int count = 0;
        for (int i = 0; i < tx.length; i++) {
            if (tx[i].type.equals("expense")) count++;
        }

        Transaction[] expenses = new Transaction[count];
        int k = 0;
        for (int i = 0; i < tx.length; i++) {
            if (tx[i].type.equals("expense")) {
                expenses[k] = tx[i];
                k++;
            }
        }

        // Sort expenses by amount desc (selection sort)
        for (int i = 0; i < expenses.length; i++) {
            int best = i;
            for (int j = i + 1; j < expenses.length; j++) {
                if (expenses[j].amount > expenses[best].amount) best = j;
            }
            Transaction tmp = expenses[i];
            expenses[i] = expenses[best];
            expenses[best] = tmp;
        }

        if (n >= expenses.length) return expenses;

        Transaction[] top = new Transaction[n];
        for (int i = 0; i < n; i++) top[i] = expenses[i];
        return top;
    }

    public static String money(double x) {
        return String.format("$%,.2f", x);
    }
}
