package model.transport;

import java.util.concurrent.ThreadLocalRandom;

public class TrainOption extends TransportOption {
    
    public enum TrainClass {
        ECONOMY("Economy", 1.0),
        BUSINESS("Business", 1.5),
        FIRST("First Class", 2.0);
        
        private final String displayName;
        private final double multiplier;
        
        TrainClass(String displayName, double multiplier) {
            this.displayName = displayName;
            this.multiplier = multiplier;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public double getMultiplier() {
            return multiplier;
        }
    }
    
    private final TrainClass trainClass;
    
    public TrainOption(String from, String to) {
        this(from, to, ThreadLocalRandom.current().nextDouble(600, 1501), TrainClass.ECONOMY);
    }
    
    public TrainOption(String from, String to, double basePrice, TrainClass trainClass) {
        super(from, to, basePrice);
        this.trainClass = trainClass;
    }
    
    @Override
    public double calculateTotalCost() {
        return Math.round(basePrice * trainClass.getMultiplier() * 100.0) / 100.0;
    }
    
    @Override
    public String getTypeName() {
        return "Train (" + trainClass.getDisplayName() + ")";
    }
    
    @Override
    public int getEstimatedDurationHours() {
        return 5;
    }
    
    public TrainClass getTrainClass() {
        return trainClass;
    }
    
    @Override
    public String toString() {
        return String.format("🚄 %s: %s (%.2f TL, ~%d hours)",
            getTypeName(), getRouteInfo(), calculateTotalCost(), getEstimatedDurationHours());
    }
}
