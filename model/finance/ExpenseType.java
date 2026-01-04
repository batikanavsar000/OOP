package model.finance;

public enum ExpenseType {
    TRANSPORT("Transport"),
    ACCOMMODATION("Accommodation"),
    FOOD("Food"),
    ACTIVITY("Activity"),
    VISA("Visa"),
    OTHER("Other");
    
    private final String displayName;
    
    ExpenseType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
