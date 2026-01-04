package ui;

import model.user.Preference;
import model.user.Profile;
import model.user.User;
import service.RecommendationService;
import service.TripPlannerService;
import service.VisaService;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConsoleMenu {
    private final Map<Integer, Command> commands;
    private final User currentUser;
    private boolean running;

    public ConsoleMenu() {
        this.commands = new LinkedHashMap<>();
        this.running = true;

        this.currentUser = initializeUser();

        TripPlannerService tripService = new TripPlannerService();
        VisaService visaService = new VisaService();

        commands.put(1, new CreateTripCommand(tripService, currentUser));
        commands.put(2, new PlanVisaCommand(visaService, currentUser));
        commands.put(3, new ShowBudgetCommand(currentUser));
        commands.put(4, new RecommendTripCommand(currentUser));
        commands.put(5, new ShowProfileCommand(currentUser));
    }

    private User initializeUser() {
        Profile profile = new Profile("User", "user@email.com");
        User user = new User(profile);
        
        user.addPreference(new Preference("History", Preference.Category.CULTURE, 5));
        user.addPreference(new Preference("Beach", Preference.Category.BEACH, 4));
        
        return user;
    }

    public void start() {
        printWelcome();
        
        while (running) {
            printMenu();
            
            try {
                int choice = InputHelper.readInt("\n👉 Your choice");
                
                if (choice == 0) {
                    exit();
                    break;
                }
                
                Command command = commands.get(choice);
                if (command != null) {
                    System.out.println();
                    command.execute();
                    InputHelper.pressEnterToContinue();
                } else {
                    System.out.println("⚠️ Invalid choice! Please select one of the menu options.");
                }
                
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║     🌍  WORLD TRAVEL PLANNER  🌍                      ║");
        System.out.println("║                                                           ║");
        System.out.println("║     Plan your trips easily!                    ║");
        System.out.println("║     • Transportation and accommodation booking                    ║");
        System.out.println("║     • Visa application tracking                                 ║");
        System.out.println("║     • Budget management                                      ║");
        System.out.println("║     • Destination recommendations                               ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("👋 Welcome, " + currentUser.getName() + "!");
    }

    private void printMenu() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│            MAIN MENU                   │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  1. ✈️  Plan New Trip           │");
        System.out.println("│  2. 📋 Apply for Visa            │");
        System.out.println("│  3. 💰 My Trips & Budget          │");
        System.out.println("│  4. 🎯 Get Destination Suggestion        │");
        System.out.println("│  5. 👤 My Profile                       │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  0. 🚪 Exit                          │");
        System.out.println("└───────────────────────────────────────┘");
    }

    private void exit() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║   🌟 Have a great journey! Goodbye! 🌟                       ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        running = false;
    }

    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}
