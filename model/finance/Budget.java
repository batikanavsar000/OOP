package model.finance;

import exception.BudgetExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Budget {
    private final double totalLimit;
    private double currentSpending;
    private final List<Expense> expenses;
    private final ExchangeRateProvider exchangeProvider;
    
    public Budget(double totalLimit) {
        if (totalLimit <= 0) {
            throw new IllegalArgumentException("Budget limit must be positive!");
        }
        this.totalLimit = totalLimit;
        this.currentSpending = 0;
        this.expenses = new ArrayList<>();
        this.exchangeProvider = new StaticExchangeRateProvider();
    }
    
    public void addExpense(Expense expense) throws BudgetExceededException {
        validateExpense(expense.getAmount());
        expenses.add(expense);
        currentSpending += expense.getAmount();
    }
    
    public void addExpense(double amount, String description) throws BudgetExceededException {
        addExpense(amount, description, ExpenseType.OTHER);
    }
    
    public void addExpense(double amount, String description, ExpenseType type) throws BudgetExceededException {
        addExpense(new Expense(amount, description, type));
    }
    
    private void validateExpense(double amount) throws BudgetExceededException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive!");
        }
        if (currentSpending + amount > totalLimit) {
            throw new BudgetExceededException(amount, getRemainingBudget());
        }
    }
    
    public boolean canAfford(double amount) {
        return currentSpending + amount <= totalLimit;
    }
    
    public double getRemainingBudget() {
        return totalLimit - currentSpending;
    }
    
    public double getCurrentSpending() {
        return currentSpending;
    }
    
    public double getTotalLimit() {
        return totalLimit;
    }
    
    public List<Expense> getExpenses() {
        return new ArrayList<>(expenses);
    }
    
    public double getSpendingPercentage() {
        return (currentSpending / totalLimit) * 100;
    }
    
    public Map<ExpenseType, Double> getExpensesByCategory() {
        return expenses.stream()
            .collect(Collectors.groupingBy(
                Expense::getType,
                Collectors.summingDouble(Expense::getAmount)
            ));
    }
    
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("           BUDGET REPORT\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append(String.format("Total Limit   : %.2f TL\n", totalLimit));
        sb.append(String.format("Spent         : %.2f TL (%%%.1f)\n", currentSpending, getSpendingPercentage()));
        sb.append(String.format("Remaining     : %.2f TL\n", getRemainingBudget()));
        sb.append("───────────────────────────────────────\n");
        
        if (!expenses.isEmpty()) {
            sb.append("By Category:\n");
            getExpensesByCategory().forEach((type, amount) -> 
                sb.append(String.format("  • %s: %.2f TL\n", type.getDisplayName(), amount))
            );
            sb.append("───────────────────────────────────────\n");
            sb.append("All Expenses:\n");
            for (Expense e : expenses) {
                sb.append("  ").append(e.toString()).append("\n");
            }
        } else {
            sb.append("No expenses yet.\n");
        }
        
        sb.append("═══════════════════════════════════════");
        return sb.toString();
    }
}
