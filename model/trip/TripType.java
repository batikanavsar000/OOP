package model.trip;

public enum TripType {
    DOMESTIC("Domestic Trip", false, null),
    EUROPE("Europe Trip", true, "SCHENGEN"),
    USA("USA Trip", true, "US");

    private final String displayName;
    private final boolean requiresVisa;
    private final String visaType;

    TripType(String displayName, boolean requiresVisa, String visaType) {
        this.displayName = displayName;
        this.requiresVisa = requiresVisa;
        this.visaType = visaType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean requiresVisa() {
        return requiresVisa;
    }

    public String getVisaType() {
        return visaType;
    }
}
