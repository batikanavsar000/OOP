package model.trip;

import model.accommodation.Accommodation;
import java.time.LocalDateTime;

public class AccommodationActivity extends Activity {
    private final Accommodation accommodation;
    
    public AccommodationActivity(LocalDateTime checkIn, LocalDateTime checkOut, Accommodation accommodation) {
        super("Accommodation: " + accommodation.getName(), checkIn, checkOut);
        this.accommodation = accommodation;
    }
    
    @Override
    public double calculateCost() {
        return accommodation.calculatePrice();
    }
    
    @Override
    public String getTypeName() {
        return accommodation.getTypeName();
    }
    
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    @Override
    public String toString() {
        return String.format("🛏️ %s (%.2f TL)", accommodation.toString(), calculateCost());
    }
}
