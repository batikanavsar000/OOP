package ui;

import model.user.User;
import service.RecommendationService;
import service.WeatherService;

import java.util.List;

public class RecommendTripCommand implements Command {
    private final User currentUser;
    private final WeatherService weatherService;
    private final RecommendationService recommendationService;

    public RecommendTripCommand(User currentUser) {
        this.currentUser = currentUser;
        this.weatherService = new WeatherService();
        this.recommendationService = new RecommendationService();
    }

    @Override
    public void execute() {
        InputHelper.printHeader("DESTINATION RECOMMENDATION SYSTEM");

        double budget = InputHelper.readPositiveDouble("Your total budget (TRY)");
        int days = InputHelper.readIntInRange("How many days are you planning", 1, 60);

        double dailyBudget = budget / days;

        System.out.println("\n📊 ANALYSIS RESULTS");
        InputHelper.printSubSeparator();
        System.out.printf("Total Budget     : %.0f TL\n", budget);
        System.out.printf("Trip Duration    : %d days\n", days);
        System.out.printf("Daily Capacity   : %.0f TL\n", dailyBudget);
        
        String category = determineCategory(dailyBudget);
        String weatherCategory = getWeatherCategory(dailyBudget);
        
        System.out.println("\n🎯 RECOMMENDATIONS FOR YOU: " + category);
        InputHelper.printSubSeparator();

        recommendationService.printRecommendations(budget, days);

        System.out.println("\n" + weatherService.getWeatherRecommendation(weatherCategory));

        if (!currentUser.getPreferences().isEmpty()) {
            System.out.println("\n💡 BASED ON YOUR PREFERENCES:");
            InputHelper.printSubSeparator();
            List<RecommendationService.Destination> personalized = 
                recommendationService.recommendByPreferences(currentUser, dailyBudget);
            
            int count = 0;
            for (RecommendationService.Destination dest : personalized) {
                if (count++ >= 3) break;
                System.out.println(dest.toString());
                System.out.println();
            }
        }

        InputHelper.printSeparator();
    }

    private String determineCategory(double dailyBudget) {
        if (dailyBudget >= 12000) {
            return "LUXURY WORLD TOURS";
        } else if (dailyBudget >= 7000) {
            return "CULTURE AND COMFORT ROUTES";
        } else if (dailyBudget >= 4000) {
            return "TROPICAL GETAWAYS";
        } else if (dailyBudget >= 2500) {
            return "EXOTIC AND BALANCED ROUTES";
        } else {
            return "BUDGET ADVENTURES";
        }
    }
    
    private String getWeatherCategory(double dailyBudget) {
        if (dailyBudget >= 12000) return "ELIT";
        if (dailyBudget >= 7000) return "KULTUR";
        if (dailyBudget >= 4000) return "TROPIKAL";
        return "EKONOMIK";
    }

    @Override
    public String getDescription() {
        return "Get destination recommendation";
    }
}
