package model.transport;

import java.util.concurrent.ThreadLocalRandom;

public class FlightOption extends TransportOption {
    private static final double BAGGAGE_FEE = 500.0;
    private static final double TAX_RATE = 0.18;
    
    private final double baggageFee;
    private final double tax;
    private final boolean directFlight;
    
    public FlightOption(String from, String to) {
        this(from, to, ThreadLocalRandom.current().nextDouble(3000, 8001), true);
    }
    
    public FlightOption(String from, String to, double basePrice, boolean directFlight) {
        super(from, to, basePrice);
        this.directFlight = directFlight;
        this.baggageFee = BAGGAGE_FEE;
        this.tax = basePrice * TAX_RATE;
    }
    
    @Override
    public double calculateTotalCost() {
        return Math.round((basePrice + baggageFee + tax) * 100.0) / 100.0;
    }
    
    @Override
    public String getTypeName() {
        return directFlight ? "Direct Flight" : "Connecting Flight";
    }
    
    @Override
    public int getEstimatedDurationHours() {
        return directFlight ? 3 : 6;
    }
    
    public double getBaggageFee() {
        return baggageFee;
    }
    
    public double getTax() {
        return tax;
    }
    
    public boolean isDirectFlight() {
        return directFlight;
    }
    
    @Override
    public String toString() {
        return String.format("✈️ %s: %s\n   Base: %.2f TL + Baggage: %.2f TL + Tax: %.2f TL = %.2f TL",
            getTypeName(), getRouteInfo(), basePrice, baggageFee, tax, calculateTotalCost());
    }
}
