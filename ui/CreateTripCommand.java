package ui;

import exception.BudgetExceededException;
import exception.InvalidDateException;
import model.accommodation.*;
import model.location.Attraction;
import model.transport.*;
import model.trip.Trip;
import model.trip.TripType;
import model.user.User;
import service.TripPlannerService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateTripCommand implements Command {
    private final TripPlannerService plannerService;
    private final User currentUser;

    public CreateTripCommand(TripPlannerService plannerService, User currentUser) {
        this.plannerService = plannerService;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        InputHelper.printHeader("NEW TRIP PLANNING");

        TripType tripType = selectTripType();
        if (tripType == null) return;
        
        if (!checkVisaRequirement(tripType)) {
            return;
        }

        String tripName = InputHelper.readString("Trip name");
        double budget = InputHelper.readPositiveDouble("Total budget (TRY)");
        
        System.out.println("\n📅 DATE INFORMATION");
        InputHelper.printSubSeparator();
        LocalDate startDate = InputHelper.readFutureDate("Start date");
        LocalDate endDate = InputHelper.readFutureDate("End date");
        
        if (endDate.isBefore(startDate) || endDate.equals(startDate)) {
            System.out.println("⚠️ End date must be after start date!");
            return;
        }
        
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        System.out.println("📌 Trip duration: " + days + " days");

        TransportOption transport = selectTransport(tripType);
        if (transport == null) return;
        
        Accommodation accommodation = selectAccommodation((int) days);
        if (accommodation == null) return;

        try {
            Trip trip = plannerService.planCustomTrip(
                currentUser, tripName, budget, startDate, endDate, transport, accommodation
            );
            
            if (trip != null) {
                addOptionalActivities(trip, startDate, endDate);
                
                System.out.println("\n" + trip.exportToText());
            }
            
        } catch (InvalidDateException | BudgetExceededException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        
        InputHelper.printSeparator();
    }

    private TripType selectTripType() {
        System.out.println("\n🌍 TRIP TYPE SELECTION");
        InputHelper.printSubSeparator();
        System.out.println("1. 🏠 Domestic Trip");
        System.out.println("2. 🇪🇺 Europe Trip (Schengen Visa Required)");
        System.out.println("3. 🇺🇸 USA Trip (US Visa Required)");
        System.out.println("0. ❌ Cancel");
        
        int choice = InputHelper.readIntInRange("Your choice", 0, 3);
        
        return switch (choice) {
            case 1 -> TripType.DOMESTIC;
            case 2 -> TripType.EUROPE;
            case 3 -> TripType.USA;
            default -> null;
        };
    }
    
    private boolean checkVisaRequirement(TripType tripType) {
        if (!tripType.requiresVisa()) {
            System.out.println("\n✅ " + tripType.getDisplayName() + " no visa required.");
            return true;
        }
        
        String visaType = tripType.getVisaType();
        boolean hasVisa = currentUser.hasApprovedVisa(visaType);
        
        if (!hasVisa) {
            System.out.println("\n" + "═".repeat(50));
            System.out.println("❌ VISA ERROR!");
            System.out.println("═".repeat(50));
            
            if (tripType == TripType.EUROPE) {
                System.out.println("⚠️ Approved SCHENGEN VISA is required for Europe trip!");
                System.out.println("\n📋 What you need to do:");
                System.out.println("   1. Select 'Apply for Visa' from the main menu");
                System.out.println("   2. Enter one of the Schengen country codes (e.g.: DE, FR, IT)");
                System.out.println("   3. Upload the required documents");
                System.out.println("   4. You can plan your trip after visa approval");
            } else if (tripType == TripType.USA) {
                System.out.println("⚠️ Approved US VISA is required for USA trip!");
                System.out.println("\n📋 What you need to do:");
                System.out.println("   1. Select 'Apply for Visa' from the main menu");
                System.out.println("   2. Enter 'US' as the country code");
                System.out.println("   3. Upload required documents including DS-160 form");
                System.out.println("   4. You can plan your trip after visa approval");
            }
            
            System.out.println("\n💡 Tip: Complete your visa application first!");
            System.out.println("═".repeat(50));
            return false;
        }
        
        System.out.println("\n✅ " + tripType.getDisplayName() + " your visa is approved. You may proceed!");
        return true;
    }

    private TransportOption selectTransport(TripType tripType) {
        System.out.println("\n🚀 TRANSPORTATION SELECTION");
        InputHelper.printSubSeparator();
        
        if (tripType == TripType.EUROPE || tripType == TripType.USA) {
            System.out.println("✈️ Flight is recommended for international trips.");
            System.out.println("1. ✈️ Flight");
            
            int choice = InputHelper.readIntInRange("Your choice", 1, 1);
            
            String from = InputHelper.readString("Departure city (e.g.: Istanbul)");
            String to = InputHelper.readString("Arrival city (e.g.: Paris, New York)");
            
            FlightOption flight = new FlightOption(from, to);
            System.out.println("\n📋 " + flight.toString());
            return flight;
        }
        
        System.out.println("1. ✈️ Flight");
        System.out.println("2. 🚌 Bus");
        System.out.println("3. 🚄 Train");
        
        int choice = InputHelper.readIntInRange("Your choice", 1, 3);
        
        String from = InputHelper.readString("Departure city");
        String to = InputHelper.readString("Arrival city");

        TransportOption transport = switch (choice) {
            case 1 -> new FlightOption(from, to);
            case 2 -> new BusOption(from, to);
            case 3 -> new TrainOption(from, to);
            default -> null;
        };
        
        if (transport != null) {
            System.out.println("\n📋 " + transport.toString());
        }
        
        return transport;
    }

    private Accommodation selectAccommodation(int nights) {
        System.out.println("\n🏨 ACCOMMODATION SELECTION (" + nights + " nights)");
        InputHelper.printSubSeparator();
        System.out.println("1. 🏨 Hotel (Luxury, breakfast included)");
        System.out.println("2. 🏠 Apartment (With kitchen, spacious)");
        System.out.println("3. 🛏️ Hostel (Budget-friendly)");
        
        int choice = InputHelper.readIntInRange("Your choice", 1, 3);

        Accommodation accommodation = switch (choice) {
            case 1 -> new Hotel(nights);
            case 2 -> new Apartment(nights);
            case 3 -> new Hostel(nights);
            default -> null;
        };
        
        if (accommodation != null) {
            System.out.println("\n📋 " + accommodation.toString());
        }
        
        return accommodation;
    }

    private void addOptionalActivities(Trip trip, LocalDate start, LocalDate end) {
        System.out.println();
        if (!InputHelper.readYesNo("Would you like to add sightseeing activities?")) {
            return;
        }
        
        boolean addMore = true;
        while (addMore) {
            String placeName = InputHelper.readString("Place name (e.g.: Eiffel Tower)");
            String description = InputHelper.readString("Short description");
            double fee = InputHelper.readDouble("Entry fee (TRY, 0 if free)");
            int hours = InputHelper.readIntInRange("Estimated visit duration (hours)", 1, 8);
            
            Attraction attraction = new Attraction(placeName, description, fee, hours);
            
            System.out.println("Which day? (1-" + trip.getDurationDays() + ")");
            int dayNumber = InputHelper.readIntInRange("Day", 1, (int) trip.getDurationDays());
            LocalDate activityDate = start.plusDays(dayNumber - 1);
            LocalDateTime activityTime = activityDate.atTime(10, 0);
            
            plannerService.addSightseeingToTrip(trip, attraction, activityTime, hours);
            
            addMore = InputHelper.readYesNo("Would you like to add another activity?");
        }
    }

    @Override
    public String getDescription() {
        return "Plan new trip";
    }
}
