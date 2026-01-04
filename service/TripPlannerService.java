package service;

import exception.BudgetExceededException;
import exception.InvalidDateException;
import model.accommodation.Accommodation;
import model.finance.Budget;
import model.finance.ExpenseType;
import model.location.Attraction;
import model.location.City;
import model.transport.TransportOption;
import model.trip.*;
import model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TripPlannerService {
    
    public Trip planCustomTrip(User user, String tripName, double budgetLimit,
                                LocalDate startDate, LocalDate endDate,
                                TransportOption transportOption, Accommodation accommodation) 
            throws InvalidDateException, BudgetExceededException {
        
        InvalidDateException.validateDateRange(startDate, endDate);
        
        System.out.println("\n>> Creating trip plan...");
        
        Budget budget = new Budget(budgetLimit);
        
        LocalDateTime start = LocalDateTime.of(startDate, LocalTime.of(9, 0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(18, 0));
        Trip trip = new Trip(tripName, start, end, budget);
        
        TransportActivity transportActivity = new TransportActivity(start, transportOption);
        budget.addExpense(transportActivity.calculateCost(), 
            "Transport: " + transportOption.getRouteInfo(), ExpenseType.TRANSPORT);
        
        trip.getDayByDate(startDate).ifPresent(day -> day.addActivity(transportActivity));
        
        AccommodationActivity accommodationActivity = new AccommodationActivity(start, end, accommodation);
        budget.addExpense(accommodationActivity.calculateCost(), 
            "Accommodation: " + accommodation.getName(), ExpenseType.ACCOMMODATION);
        
        trip.getDayByDate(startDate).ifPresent(day -> day.addActivity(accommodationActivity));
        
        user.addTrip(trip);
        
        System.out.println("✅ Trip plan created!");
        System.out.printf("   Total Cost: %.2f TL\n", budget.getCurrentSpending());
        System.out.printf("   Remaining Budget: %.2f TL\n", budget.getRemainingBudget());
        
        return trip;
    }
    
    public Trip planCustomTrip(User user, String tripName, double budgetLimit,
                                TransportOption transportOption, Accommodation accommodation) {
        try {
            LocalDate start = LocalDate.now().plusDays(5);
            LocalDate end = start.plusDays(accommodation.getNights());
            return planCustomTrip(user, tripName, budgetLimit, start, end, transportOption, accommodation);
        } catch (InvalidDateException | BudgetExceededException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return null;
        }
    }
    
    public boolean addSightseeingToTrip(Trip trip, Attraction attraction, 
                                        LocalDateTime startTime, int durationHours) {
        try {
            SightseeingActivity activity = new SightseeingActivity(startTime, durationHours, attraction);
            
            if (!trip.getBudget().canAfford(activity.calculateCost())) {
                System.err.println("❌ Insufficient budget: " + attraction.getName());
                return false;
            }
            
            trip.getBudget().addExpense(activity.calculateCost(), 
                "Sightseeing: " + attraction.getName(), ExpenseType.ACTIVITY);
            
            LocalDate date = startTime.toLocalDate();
            trip.addActivityToDay(date, activity);
            
            System.out.println("✅ Activity added: " + attraction.getName());
            System.out.printf("   Cost: %.2f TL | Remaining: %.2f TL\n",
                activity.calculateCost(), trip.getBudget().getRemainingBudget());
            
            return true;
            
        } catch (BudgetExceededException e) {
            System.err.println("❌ Budget exceeded: " + e.getMessage());
            return false;
        }
    }
    
    public void setTripDestination(Trip trip, City destination) {
        trip.setDestination(destination);
        System.out.println("✅ Destination set: " + destination.getFullName());
    }
    
    public void confirmTrip(Trip trip) {
        trip.setStatus(Trip.Status.CONFIRMED);
        System.out.println("✅ Trip confirmed: " + trip.getName());
    }
    
    public void cancelTrip(Trip trip) {
        trip.setStatus(Trip.Status.CANCELLED);
        System.out.println("⚠️ Trip cancelled: " + trip.getName());
    }
    
    public void printTripSummary(Trip trip) {
        System.out.println(trip.exportToText());
    }
}
