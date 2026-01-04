package model.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Activity implements Schedulable {
    protected final String description;
    protected final LocalDateTime startTime;
    protected final LocalDateTime endTime;
    
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
    public Activity(String description, LocalDateTime startTime, LocalDateTime endTime) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Activity description cannot be empty!");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time must be specified!");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time!");
        }
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public abstract double calculateCost();
    
    public abstract String getTypeName();
    
    public String getTimeRange() {
        return startTime.format(TIME_FORMAT) + " - " + endTime.format(TIME_FORMAT);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s (%.2f TL)", 
            getTimeRange(), getTypeName(), description, calculateCost());
    }
}
