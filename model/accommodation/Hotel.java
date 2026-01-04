package model.accommodation;

import java.util.concurrent.ThreadLocalRandom;

public class Hotel extends Accommodation {
    private final int starRating;
    private final double serviceFee;
    private final boolean hasBreakfast;
    
    public Hotel(int nights) {
        this("Standard Hotel", ThreadLocalRandom.current().nextDouble(2000, 8001), nights, 4, true);
    }
    
    public Hotel(String name, double nightlyRate, int nights, int starRating, boolean hasBreakfast) {
        super(name, nightlyRate, nights);
        this.starRating = Math.min(5, Math.max(1, starRating));
        this.serviceFee = 200.0;
        this.hasBreakfast = hasBreakfast;
    }
    
    @Override
    public double calculatePrice() {
        return (nightlyRate * nights) + serviceFee;
    }
    
    @Override
    public String getTypeName() {
        return "⭐".repeat(starRating) + " Hotel";
    }
    
    @Override
    public double getExtraFees() {
        return serviceFee;
    }
    
    public int getStarRating() {
        return starRating;
    }
    
    public double getServiceFee() {
        return serviceFee;
    }
    
    public boolean hasBreakfast() {
        return hasBreakfast;
    }
    
    @Override
    public String toString() {
        String breakfast = hasBreakfast ? " (Breakfast included)" : "";
        return String.format("🏨 %s: %s%s\n   %d nights × %.2f TL + Service: %.2f TL = %.2f TL",
            getTypeName(), name, breakfast, nights, nightlyRate, serviceFee, calculatePrice());
    }
}
