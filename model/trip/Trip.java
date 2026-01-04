package model.trip;

import model.Exportable;
import model.finance.Budget;
import model.location.City;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Trip implements Exportable {
    
    public enum Status {
        PLANNING("Planning"),
        CONFIRMED("Confirmed"),
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        CANCELLED("Cancelled");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Budget budget;
    private final List<ItineraryDay> itineraryDays;
    private City destination;
    private Status status;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public Trip(String name, LocalDateTime startDate, LocalDateTime endDate, Budget budget) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Trip name cannot be empty!");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be empty!");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date!");
        }
        if (budget == null) {
            throw new IllegalArgumentException("Budget must be specified!");
        }
        
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.itineraryDays = new ArrayList<>();
        this.status = Status.PLANNING;
        
        initializeItineraryDays();
    }
    
    private void initializeItineraryDays() {
        LocalDate current = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        
        while (!current.isAfter(end)) {
            itineraryDays.add(new ItineraryDay(current));
            current = current.plusDays(1);
        }
    }
    
    public void addItineraryDay(ItineraryDay day) {
        boolean exists = itineraryDays.stream()
            .anyMatch(d -> d.getDate().equals(day.getDate()));
        
        if (!exists) {
            itineraryDays.add(day);
            itineraryDays.sort(Comparator.comparing(ItineraryDay::getDate));
        }
    }
    
    public Optional<ItineraryDay> getDayByDate(LocalDate date) {
        return itineraryDays.stream()
            .filter(d -> d.getDate().equals(date))
            .findFirst();
    }
    
    public void addActivityToDay(LocalDate date, Activity activity) {
        getDayByDate(date).ifPresentOrElse(
            day -> day.addActivity(activity),
            () -> {
                ItineraryDay newDay = new ItineraryDay(date);
                newDay.addActivity(activity);
                addItineraryDay(newDay);
            }
        );
    }
    
    public double calculateTotalCost() {
        return itineraryDays.stream()
            .mapToDouble(ItineraryDay::calculateDayCost)
            .sum();
    }
    
    public long getDurationDays() {
        return ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate()) + 1;
    }
    
    public String getName() {
        return name;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public Budget getBudget() {
        return budget;
    }
    
    public List<ItineraryDay> getItineraryDays() {
        return new ArrayList<>(itineraryDays);
    }
    
    public City getDestination() {
        return destination;
    }
    
    public void setDestination(City destination) {
        this.destination = destination;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String exportToText() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("              TRIP PLAN\n");
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("Trip Name    : ").append(name).append("\n");
        
        if (destination != null) {
            sb.append("Destination  : ").append(destination.getFullName()).append("\n");
        }
        
        sb.append("Dates        : ").append(startDate.format(DATE_FORMAT))
          .append(" - ").append(endDate.format(DATE_FORMAT))
          .append(" (").append(getDurationDays()).append(" days)\n");
        sb.append("Status       : ").append(status.getDisplayName()).append("\n");
        sb.append("───────────────────────────────────────────────\n");
        
        sb.append("BUDGET STATUS:\n");
        sb.append(String.format("  Limit      : %.2f TL\n", budget.getTotalLimit()));
        sb.append(String.format("  Spent      : %.2f TL (%%%.1f)\n", 
            budget.getCurrentSpending(), budget.getSpendingPercentage()));
        sb.append(String.format("  Remaining  : %.2f TL\n", budget.getRemainingBudget()));
        sb.append("───────────────────────────────────────────────\n");
        
        sb.append("DAILY SCHEDULE:\n\n");
        for (ItineraryDay day : itineraryDays) {
            sb.append(day.toString()).append("\n");
        }
        
        sb.append("═══════════════════════════════════════════════\n");
        sb.append(String.format("TOTAL COST: %.2f TL\n", calculateTotalCost()));
        sb.append("═══════════════════════════════════════════════");
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        String dest = destination != null ? " → " + destination.getName() : "";
        return String.format("%s%s (%s - %s) [%s]", 
            name, dest,
            startDate.format(DATE_FORMAT), 
            endDate.format(DATE_FORMAT),
            status.getDisplayName());
    }
}
