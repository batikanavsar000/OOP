package model.trip;

import java.time.LocalDateTime;

public interface Schedulable {
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    
    default long getDurationMinutes() {
        return java.time.Duration.between(getStartTime(), getEndTime()).toMinutes();
    }
    
    default int getDurationHours() {
        return (int) Math.ceil(getDurationMinutes() / 60.0);
    }
}
