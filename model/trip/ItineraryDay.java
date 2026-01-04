package model.trip;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItineraryDay {
    private final LocalDate date;
    private final List<Activity> activities;
    private String note;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE");
    
    public ItineraryDay(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be empty!");
        }
        this.date = date;
        this.activities = new ArrayList<>();
        this.note = "";
    }
    
    public void addActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null!");
        }
        activities.add(activity);
        activities.sort(Comparator.comparing(Activity::getStartTime));
    }
    
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    
    public double calculateDayCost() {
        return activities.stream()
            .mapToDouble(Activity::calculateCost)
            .sum();
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public List<Activity> getActivities() {
        return new ArrayList<>(activities);
    }
    
    public int getActivityCount() {
        return activities.size();
    }
    
    public boolean isEmpty() {
        return activities.isEmpty();
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note != null ? note : "";
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("📅 ").append(date.format(DATE_FORMAT)).append("\n");
        
        if (activities.isEmpty()) {
            sb.append("   (No activities planned)\n");
        } else {
            for (Activity activity : activities) {
                sb.append("   • ").append(activity.toString()).append("\n");
            }
            sb.append(String.format("   ─────────────────────────\n"));
            sb.append(String.format("   Daily Total: %.2f TL\n", calculateDayCost()));
        }
        
        if (!note.isEmpty()) {
            sb.append("   📝 Note: ").append(note).append("\n");
        }
        
        return sb.toString();
    }
}
