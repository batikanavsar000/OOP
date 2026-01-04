package model.accommodation;

import java.util.concurrent.ThreadLocalRandom;

public class Hostel extends Accommodation {
    private final boolean isPrivateRoom;
    private final int bedCount;
    
    public Hostel(int nights) {
        this("Standard Hostel", ThreadLocalRandom.current().nextDouble(300, 1001), nights, false, 6);
    }
    
    public Hostel(String name, double nightlyRate, int nights, boolean isPrivateRoom, int bedCount) {
        super(name, nightlyRate, nights);
        this.isPrivateRoom = isPrivateRoom;
        this.bedCount = isPrivateRoom ? 1 : Math.max(2, bedCount);
    }
    
    @Override
    public double calculatePrice() {
        double multiplier = isPrivateRoom ? 1.5 : 1.0;
        return nightlyRate * nights * multiplier;
    }
    
    @Override
    public String getTypeName() {
        return isPrivateRoom ? "Hostel (Private Room)" : "Hostel (Dormitory)";
    }
    
    @Override
    public double getExtraFees() {
        return 0;
    }
    
    public boolean isPrivateRoom() {
        return isPrivateRoom;
    }
    
    public int getBedCount() {
        return bedCount;
    }
    
    @Override
    public String toString() {
        String roomType = isPrivateRoom ? "Private room" : bedCount + " person room";
        return String.format("🛏️ %s: %s (%s)\n   %d nights × %.2f TL = %.2f TL",
            getTypeName(), name, roomType, nights, nightlyRate, calculatePrice());
    }
}
