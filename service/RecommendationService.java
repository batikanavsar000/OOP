package service;

import model.user.Preference;
import model.user.User;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {
    
    public static class Destination {
        private final String name;
        private final String country;
        private final String description;
        private final double minBudget;
        private final String category;
        private final boolean visaRequired;
        
        public Destination(String name, String country, String description, 
                          double minBudget, String category, boolean visaRequired) {
            this.name = name;
            this.country = country;
            this.description = description;
            this.minBudget = minBudget;
            this.category = category;
            this.visaRequired = visaRequired;
        }
        
        public String getName() { return name; }
        public String getCountry() { return country; }
        public String getDescription() { return description; }
        public double getMinBudget() { return minBudget; }
        public String getCategory() { return category; }
        public boolean isVisaRequired() { return visaRequired; }
        
        @Override
        public String toString() {
            String visa = visaRequired ? " 📋 Visa Required" : " ✅ No Visa";
            return String.format("• %s, %s\n  %s\n  💰 Min. daily: %.0f TL%s",
                name, country, description, minBudget, visa);
        }
    }
    
    private final List<Destination> destinations;
    
    public RecommendationService() {
        destinations = new ArrayList<>();
        initializeDestinations();
    }
    
    private void initializeDestinations() {
        destinations.add(new Destination("Dubai", "UAE", "Luxury shopping and desert safari", 15000, "LUXURY", true));
        destinations.add(new Destination("Maldives", "Maldives", "Tropical paradise and overwater bungalows", 20000, "LUXURY", true));
        destinations.add(new Destination("Miami", "USA", "Beaches and American lifestyle", 12000, "LUXURY", true));
        
        destinations.add(new Destination("Rome", "Italy", "Ancient history and art", 8000, "CULTURE", true));
        destinations.add(new Destination("Paris", "France", "Romance and museums", 9000, "CULTURE", true));
        destinations.add(new Destination("Tokyo", "Japan", "Tradition and technology", 10000, "CULTURE", true));
        destinations.add(new Destination("Athens", "Greece", "Ancient Greek heritage", 6000, "CULTURE", true));
        
        destinations.add(new Destination("Bali", "Indonesia", "Tropical peace and temples", 5000, "TROPICAL", false));
        destinations.add(new Destination("Phuket", "Thailand", "Exotic beaches", 4000, "TROPICAL", false));
        destinations.add(new Destination("Zanzibar", "Tanzania", "Africa's tropical paradise", 5500, "TROPICAL", true));
        
        destinations.add(new Destination("Sofia", "Bulgaria", "Affordable Europe", 2500, "BUDGET", false));
        destinations.add(new Destination("Belgrade", "Serbia", "Historic Balkan city", 2800, "BUDGET", false));
        destinations.add(new Destination("Tbilisi", "Georgia", "Caucasian culture and cuisine", 2000, "BUDGET", false));
        destinations.add(new Destination("Skopje", "North Macedonia", "Ottoman heritage", 2200, "BUDGET", false));
        
        destinations.add(new Destination("Cappadocia", "Turkey", "Balloon tour and fairy chimneys", 3000, "ADVENTURE", false));
        destinations.add(new Destination("Nepal", "Nepal", "Himalaya trekking", 3500, "ADVENTURE", false));
    }
    
    public List<Destination> recommendByBudget(double dailyBudget) {
        List<Destination> recommendations = new ArrayList<>();
        
        for (Destination dest : destinations) {
            if (dest.getMinBudget() <= dailyBudget) {
                recommendations.add(dest);
            }
        }
        
        recommendations.sort((a, b) -> Double.compare(b.getMinBudget(), a.getMinBudget()));
        
        return recommendations;
    }
    
    public List<Destination> recommendByPreferences(User user, double dailyBudget) {
        List<Destination> budgetFiltered = recommendByBudget(dailyBudget);
        List<Destination> recommendations = new ArrayList<>();
        
        for (Preference pref : user.getPreferences()) {
            String tag = pref.getTag().toUpperCase();
            for (Destination dest : budgetFiltered) {
                if (matchesPreference(dest, tag) && !recommendations.contains(dest)) {
                    recommendations.add(dest);
                }
            }
        }
        
        if (recommendations.isEmpty()) {
            return budgetFiltered.stream().limit(5).toList();
        }
        
        return recommendations;
    }
    
    private boolean matchesPreference(Destination dest, String preference) {
        return switch (preference) {
            case "TARİH", "TARIH", "HISTORY", "KULTUR", "KÜLTÜR" -> 
                dest.getCategory().equals("CULTURE");
            case "PLAJ", "DENİZ", "DENIZ", "BEACH" -> 
                dest.getCategory().equals("TROPICAL");
            case "MACERA", "ADVENTURE", "DOĞA", "DOGA" -> 
                dest.getCategory().equals("ADVENTURE");
            case "LÜKS", "LUKS", "LUXURY" -> 
                dest.getCategory().equals("LUXURY");
            default -> false;
        };
    }
    
    public void printRecommendations(double totalBudget, int days) {
        double dailyBudget = totalBudget / days;
        
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("          DESTINATION RECOMMENDATIONS");
        System.out.println("═══════════════════════════════════════════════");
        System.out.printf("Total Budget: %.0f TL | Duration: %d days\n", totalBudget, days);
        System.out.printf("Daily Budget: %.0f TL\n", dailyBudget);
        System.out.println("───────────────────────────────────────────────");
        
        String category;
        if (dailyBudget >= 12000) {
            category = "LUXURY ROUTES";
        } else if (dailyBudget >= 7000) {
            category = "CULTURE AND COMFORT";
        } else if (dailyBudget >= 4000) {
            category = "TROPICAL GETAWAY";
        } else {
            category = "BUDGET ADVENTURE";
        }
        
        System.out.println("\n🎯 RECOMMENDED ROUTE TYPE: " + category);
        System.out.println("───────────────────────────────────────────────\n");
        
        List<Destination> recs = recommendByBudget(dailyBudget);
        int count = 0;
        for (Destination dest : recs) {
            if (count++ >= 5) break;
            System.out.println(dest.toString());
            System.out.println();
        }
        
        System.out.println("═══════════════════════════════════════════════");
    }
}
