package exception;

public class BudgetExceededException extends Exception {
    public BudgetExceededException(String message) {
        super(message);
    }
    
    public BudgetExceededException(double attempted, double remaining) {
        super(String.format("Budget exceeded! Attempted: %.2f TL, Remaining: %.2f TL", attempted, remaining));
    }
}
