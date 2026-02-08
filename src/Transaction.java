public class Transaction {
    // "income" or "expense"
    public String type;
    public String date;      // YYYY-MM-DD
    public String category;  // Food, Rent, etc.
    public double amount;    // positive
    public String note;

    public Transaction(String date, String type, String category, double amount, String note) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.note = note;
    }
}
