package model.accommodation;

import java.util.concurrent.ThreadLocalRandom;

public class Apartment extends Accommodation {
    private final double cleaningFee;
    private final int bedroomCount;
    private final boolean hasKitchen;
    
    public Apartment(int nights) {
        this("Standard Apartment", ThreadLocalRandom.current().nextDouble(1500, 5001), nights, 2, true);
    }
    
    public Apartment(String name, double nightlyRate, int nights, int bedroomCount, boolean hasKitchen) {
        super(name, nightlyRate, nights);
        this.bedroomCount = Math.max(1, bedroomCount);
        this.cleaningFee = 350.0;
        this.hasKitchen = hasKitchen;
    }
    
    @Override
    public double calculatePrice() {
        return (nightlyRate * nights) + cleaningFee;
    }
    
    @Override
    public String getTypeName() {
        return "Apartment";
    }
    
    @Override
    public double getExtraFees() {
        return cleaningFee;
    }
    
    public double getCleaningFee() {
        return cleaningFee;
    }
    
    public int getBedroomCount() {
        return bedroomCount;
    }
    
    public boolean hasKitchen() {
        return hasKitchen;
    }
    
    @Override
    public String toString() {
        String features = bedroomCount + " bedroom" + (hasKitchen ? ", kitchen" : "");
        return String.format("🏠 %s: %s (%s)\n   %d nights × %.2f TL + Cleaning: %.2f TL = %.2f TL",
            getTypeName(), name, features, nights, nightlyRate, cleaningFee, calculatePrice());
    }
}
