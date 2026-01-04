package model.accommodation;

public abstract class Accommodation {
    protected final double nightlyRate;
    protected final int nights;
    protected final String name;
    
    public Accommodation(String name, double nightlyRate, int nights) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Accommodation name cannot be empty!");
        }
        if (nightlyRate < 0) {
            throw new IllegalArgumentException("Nightly rate cannot be negative!");
        }
        if (nights < 1) {
            throw new IllegalArgumentException("Must be at least 1 night stay!");
        }
        this.name = name;
        this.nightlyRate = nightlyRate;
        this.nights = nights;
    }
    
    public abstract double calculatePrice();
    
    public abstract String getTypeName();
    
    public abstract double getExtraFees();
    
    public double getNightlyRate() {
        return nightlyRate;
    }
    
    public int getNights() {
        return nights;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s (%d nights × %.2f TL = %.2f TL)",
            getTypeName(), name, nights, nightlyRate, calculatePrice());
    }
}
