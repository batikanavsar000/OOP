package model.user;

import model.trip.Trip;
import model.visa.VisaApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {
    private final Profile profile;
    private final List<Preference> preferences;
    private final List<Trip> trips;
    private final List<VisaApplication> visaApplications;
    
    public User(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile cannot be empty!");
        }
        this.profile = profile;
        this.preferences = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.visaApplications = new ArrayList<>();
    }
    
    public void addTrip(Trip trip) {
        if (trip != null && !trips.contains(trip)) {
            trips.add(trip);
        }
    }
    
    public void removeTrip(Trip trip) {
        trips.remove(trip);
    }
    
    public Optional<Trip> getTripByName(String name) {
        return trips.stream()
            .filter(t -> t.getName().equalsIgnoreCase(name))
            .findFirst();
    }
    
    public List<Trip> getTrips() {
        return new ArrayList<>(trips);
    }
    
    public int getTripCount() {
        return trips.size();
    }
    
    public void addVisaApplication(VisaApplication app) {
        if (app != null) {
            visaApplications.add(app);
        }
    }
    
    public List<VisaApplication> getVisaApplications() {
        return new ArrayList<>(visaApplications);
    }
    
    public boolean hasApprovedSchengenVisa() {
        return visaApplications.stream()
            .anyMatch(v -> v.getVisaTypeName().contains("Schengen") && 
                          v.getStatus() == VisaApplication.Status.APPROVED);
    }
    
    public boolean hasApprovedUsVisa() {
        return visaApplications.stream()
            .anyMatch(v -> v.getVisaTypeName().contains("US Visa") && 
                          v.getStatus() == VisaApplication.Status.APPROVED);
    }
    
    public boolean hasApprovedVisa(String visaType) {
        if (visaType == null) return true;
        return switch (visaType.toUpperCase()) {
            case "SCHENGEN" -> hasApprovedSchengenVisa();
            case "US", "USA" -> hasApprovedUsVisa();
            default -> false;
        };
    }
    
    public void addPreference(Preference preference) {
        if (preference != null && !preferences.contains(preference)) {
            preferences.add(preference);
        }
    }
    
    public void removePreference(Preference preference) {
        preferences.remove(preference);
    }
    
    public List<Preference> getPreferences() {
        return new ArrayList<>(preferences);
    }
    
    public boolean hasPreference(String tag) {
        return preferences.stream()
            .anyMatch(p -> p.getTag().equalsIgnoreCase(tag));
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public String getName() {
        return profile.getFullName();
    }
    
    public double getTotalSpending() {
        return trips.stream()
            .mapToDouble(t -> t.getBudget().getCurrentSpending())
            .sum();
    }
    
    @Override
    public String toString() {
        return String.format("User: %s | Trips: %d | Preferences: %d",
            profile.getFullName(), trips.size(), preferences.size());
    }
    
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("         USER PROFILE\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("Full Name  : ").append(profile.getFullName()).append("\n");
        sb.append("Email      : ").append(profile.getEmail()).append("\n");
        sb.append("───────────────────────────────────────\n");
        sb.append("Trips      : ").append(trips.size()).append(" total\n");
        sb.append("Visa Apps  : ").append(visaApplications.size()).append(" total\n");
        sb.append("Preferences: ");
        if (preferences.isEmpty()) {
            sb.append("(Not specified)\n");
        } else {
            sb.append("\n");
            for (Preference p : preferences) {
                sb.append("  • ").append(p.getTag()).append("\n");
            }
        }
        sb.append("═══════════════════════════════════════");
        return sb.toString();
    }
}
