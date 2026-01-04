package model.transport;

public abstract class TransportOption {
    protected final String from;
    protected final String to;
    protected final double basePrice;
    
    public TransportOption(String from, String to, double basePrice) {
        if (from == null || from.isBlank()) {
            throw new IllegalArgumentException("Departure location cannot be empty!");
        }
        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Arrival location cannot be empty!");
        }
        if (basePrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        this.from = from.trim();
        this.to = to.trim();
        this.basePrice = basePrice;
    }
    
    public abstract double calculateTotalCost();
    
    public abstract String getTypeName();
    
    public abstract int getEstimatedDurationHours();
    
    public String getFrom() {
        return from;
    }
    
    public String getTo() {
        return to;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public String getRouteInfo() {
        return from + " → " + to;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s (%.2f TL, ~%d hours)", 
            getTypeName(), getRouteInfo(), calculateTotalCost(), getEstimatedDurationHours());
    }
}
