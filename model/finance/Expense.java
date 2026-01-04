package model.finance;

import java.time.LocalDateTime;

public class Expense {
    private final double amount;
    private final String description;
    private final ExpenseType type;
    private final LocalDateTime timestamp;
    
    public Expense(double amount, String description, ExpenseType type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ExpenseType getType() {
        return type;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f TL", type.getDisplayName(), description, amount);
    }
}
