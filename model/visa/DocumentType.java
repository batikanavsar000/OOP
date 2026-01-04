package model.visa;

public enum DocumentType {
    PASSPORT("Passport", true),
    PHOTO("Biometric Photo", true),
    BANK_STATEMENT("Bank Statement", true),
    FLIGHT_TICKET("Flight Ticket Reservation", false),
    HOTEL_RESERVATION("Hotel Reservation", false),
    INSURANCE("Travel Health Insurance", true),
    DS160_FORM("DS-160 Form (US)", true),
    EMPLOYMENT_LETTER("Employment Letter", false),
    INVITATION_LETTER("Invitation Letter", false);
    
    private final String displayName;
    private final boolean mandatory;
    
    DocumentType(String displayName, boolean mandatory) {
        this.displayName = displayName;
        this.mandatory = mandatory;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isMandatory() {
        return mandatory;
    }
}
