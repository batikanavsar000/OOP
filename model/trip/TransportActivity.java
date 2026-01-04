package model.trip;

import model.transport.TransportOption;
import java.time.LocalDateTime;

public class TransportActivity extends Activity {
    private final TransportOption transportOption;
    
    public TransportActivity(LocalDateTime start, TransportOption transportOption) {
        super(
            "Transport: " + transportOption.getRouteInfo(),
            start,
            start.plusHours(transportOption.getEstimatedDurationHours())
        );
        this.transportOption = transportOption;
    }
    
    @Override
    public double calculateCost() {
        return transportOption.calculateTotalCost();
    }
    
    @Override
    public String getTypeName() {
        return transportOption.getTypeName();
    }
    
    public TransportOption getTransportOption() {
        return transportOption;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s (%.2f TL)",
            getTimeRange(), transportOption.toString(), calculateCost());
    }
}
