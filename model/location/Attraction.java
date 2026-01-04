package model.location;

public class Attraction {
    private final String name;
    private final String description;
    private final double entryFee;
    private final int estimatedHours;
    
    public Attraction(String name, String description, double entryFee, int estimatedHours) {
        this.name = name;
        this.description = description;
        this.entryFee = Math.max(0, entryFee);
        this.estimatedHours = Math.max(1, estimatedHours);
    }
    
    public Attraction(String name, String description, double entryFee) {
        this(name, description, entryFee, 2);
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getEntryFee() {
        return entryFee;
    }
    
    public int getEstimatedHours() {
        return estimatedHours;
    }
    
    public boolean isFree() {
        return entryFee == 0;
    }
    
    @Override
    public String toString() {
        String feeStr = isFree() ? "Free" : String.format("%.2f TL", entryFee);
        return String.format("%s - %s (%s, ~%d hours)", name, description, feeStr, estimatedHours);
    }
}
