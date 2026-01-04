package model.finance;

public enum Currency {
    TRY("₺", "Turkish Lira"),
    USD("$", "US Dollar"),
    EUR("€", "Euro");
    
    private final String symbol;
    private final String displayName;
    
    Currency(String symbol, String displayName) {
        this.symbol = symbol;
        this.displayName = displayName;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String format(double amount) {
        return String.format("%s %.2f", symbol, amount);
    }
}
