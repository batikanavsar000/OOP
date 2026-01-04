package ui;

import model.trip.Trip;
import model.user.User;

public class ShowBudgetCommand implements Command {
    private final User user;

    public ShowBudgetCommand(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        InputHelper.printHeader("MY TRIPS AND BUDGET STATUS");
        
        System.out.println("\n👤 " + user.getName());
        System.out.println("   Total trips: " + user.getTripCount());
        System.out.printf("   Total spending: %.2f TL\n", user.getTotalSpending());
        InputHelper.printSubSeparator();

        if (user.getTrips().isEmpty()) {
            System.out.println("\n⚠️ No trips planned yet.");
            System.out.println("   Select '1' from the main menu to create a new trip.");
            InputHelper.printSeparator();
            return;
        }

        int count = 1;
        for (Trip trip : user.getTrips()) {
            System.out.println("\n📍 TRIP #" + count++);
            System.out.println(trip.exportToText());
        }
        
        System.out.println("\n📊 OVERALL SUMMARY");
        InputHelper.printSubSeparator();
        
        double totalBudget = user.getTrips().stream()
            .mapToDouble(t -> t.getBudget().getTotalLimit())
            .sum();
        double totalSpent = user.getTotalSpending();
        double totalRemaining = totalBudget - totalSpent;
        
        System.out.printf("   Total Budget    : %.2f TL\n", totalBudget);
        System.out.printf("   Total Spent     : %.2f TL\n", totalSpent);
        System.out.printf("   Total Remaining : %.2f TL\n", totalRemaining);
        
        if (totalBudget > 0) {
            double percentage = (totalSpent / totalBudget) * 100;
            System.out.printf("   Usage Rate      : %%%.1f\n", percentage);
            
            int filled = (int) (percentage / 5);
            int empty = 20 - filled;
            System.out.print("   [");
            System.out.print("█".repeat(Math.max(0, filled)));
            System.out.print("░".repeat(Math.max(0, empty)));
            System.out.println("]");
        }
        
        InputHelper.printSeparator();
    }

    @Override
    public String getDescription() {
        return "Show my trips and budget";
    }
}
