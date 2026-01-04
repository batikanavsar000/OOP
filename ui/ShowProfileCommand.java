package ui;

import model.user.Preference;
import model.user.User;

public class ShowProfileCommand implements Command {
    private final User user;

    public ShowProfileCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        InputHelper.printHeader("USER PROFILE");
        
        System.out.println(user.getSummary());
        
        System.out.println("\n📝 OPTIONS");
        InputHelper.printSubSeparator();
        System.out.println("1. Add preference");
        System.out.println("2. Update profile information");
        System.out.println("0. Go back");
        
        int choice = InputHelper.readIntInRange("Your choice", 0, 2);
        
        switch (choice) {
            case 1 -> addPreference();
            case 2 -> updateProfile();
        }
        
        InputHelper.printSeparator();
    }
    
    private void addPreference() {
        System.out.println("\n🏷️ PREFERENCE CATEGORIES");
        System.out.println("1. Culture & History");
        System.out.println("2. Nature & Adventure");
        System.out.println("3. Beach & Sea");
        System.out.println("4. City & Shopping");
        System.out.println("5. Gastronomy");
        System.out.println("6. Nightlife");
        System.out.println("7. Relaxation & SPA");
        System.out.println("8. Sports & Activities");
        
        int choice = InputHelper.readIntInRange("Select category", 1, 8);
        
        Preference.Category category = switch (choice) {
            case 1 -> Preference.Category.CULTURE;
            case 2 -> Preference.Category.NATURE;
            case 3 -> Preference.Category.BEACH;
            case 4 -> Preference.Category.CITY;
            case 5 -> Preference.Category.FOOD;
            case 6 -> Preference.Category.NIGHTLIFE;
            case 7 -> Preference.Category.RELAXATION;
            case 8 -> Preference.Category.SPORTS;
            default -> Preference.Category.CULTURE;
        };
        
        String tag = InputHelper.readString("Preference tag (e.g.: Ancient History, Diving)");
        int priority = InputHelper.readIntInRange("Priority", 1, 5);
        
        user.addPreference(new Preference(tag, category, priority));
        System.out.println("✅ Preference added: " + tag);
    }
    
    private void updateProfile() {
        System.out.println("\n📝 PROFILE UPDATE");
        
        String newName = InputHelper.readStringWithDefault(
            "New full name", user.getProfile().getFullName());
        String newEmail = InputHelper.readStringWithDefault(
            "New email", user.getProfile().getEmail());
        String newPhone = InputHelper.readString("Phone number (optional)");
        
        user.getProfile().setFullName(newName);
        user.getProfile().setEmail(newEmail);
        if (!newPhone.isEmpty()) {
            user.getProfile().setPhone(newPhone);
        }
        
        System.out.println("✅ Profile updated!");
    }

    @Override
    public String getDescription() {
        return "View my profile";
    }
}
