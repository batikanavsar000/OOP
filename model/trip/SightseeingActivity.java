package model.trip;

import model.location.Attraction;
import java.time.LocalDateTime;

public class SightseeingActivity extends Activity {
    private final Attraction attraction;
    
    public SightseeingActivity(LocalDateTime start, Attraction attraction) {
        super(
            "Sightseeing: " + attraction.getName(),
            start,
            start.plusHours(attraction.getEstimatedHours())
        );
        this.attraction = attraction;
    }
    
    public SightseeingActivity(LocalDateTime start, int durationHours, Attraction attraction) {
        super(
            "Sightseeing: " + attraction.getName(),
            start,
            start.plusHours(durationHours)
        );
        this.attraction = attraction;
    }
    
    @Override
    public double calculateCost() {
        return attraction.getEntryFee();
    }
    
    @Override
    public String getTypeName() {
        return "Sightseeing";
    }
    
    public Attraction getAttraction() {
        return attraction;
    }
    
    @Override
    public String toString() {
        String feeStr = attraction.isFree() ? "Free" : String.format("%.2f TL", calculateCost());
        return String.format("[%s] 🏛️ %s (%s)", getTimeRange(), attraction.getName(), feeStr);
    }
}
