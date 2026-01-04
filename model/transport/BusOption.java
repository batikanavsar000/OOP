package model.transport;

import java.util.concurrent.ThreadLocalRandom;

public class BusOption extends TransportOption {
    private final boolean hasWifi;
    private final boolean hasTV;
    
    public BusOption(String from, String to) {
        this(from, to, ThreadLocalRandom.current().nextDouble(800, 2001));
    }
    
    public BusOption(String from, String to, double basePrice) {
        super(from, to, basePrice);
        this.hasWifi = true;
        this.hasTV = true;
    }
    
    @Override
    public double calculateTotalCost() {
        return Math.round(basePrice * 100.0) / 100.0;
    }
    
    @Override
    public String getTypeName() {
        return "Bus";
    }
    
    @Override
    public int getEstimatedDurationHours() {
        return 8;
    }
    
    public boolean hasWifi() {
        return hasWifi;
    }
    
    public boolean hasTV() {
        return hasTV;
    }
    
    @Override
    public String toString() {
        String features = (hasWifi ? "WiFi" : "") + (hasTV ? ", TV" : "");
        return String.format("🚌 Bus: %s (%.2f TL, ~%d hours) [%s]",
            getRouteInfo(), calculateTotalCost(), getEstimatedDurationHours(), features);
    }
}
