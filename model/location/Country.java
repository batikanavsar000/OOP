package model.location;

public class Country {
    private final String name;
    private final String code;
    private final VisaType visaType;
    
    public enum VisaType {
        NONE("No Visa Required"),
        SCHENGEN("Schengen Visasi"),
        US("USA Visasi"),
        UK("UK Visa"),
        OTHER("Other Visa");
        
        private final String displayName;
        
        VisaType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public Country(String name, String code, VisaType visaType) {
        this.name = name;
        this.code = code.toUpperCase();
        this.visaType = visaType;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCode() {
        return code;
    }
    
    public VisaType getVisaType() {
        return visaType;
    }
    
    public boolean requiresVisa() {
        return visaType != VisaType.NONE;
    }
    
    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
    
    public static Country turkey() {
        return new Country("Turkey", "TR", VisaType.NONE);
    }
    
    public static Country germany() {
        return new Country("Germany", "DE", VisaType.SCHENGEN);
    }
    
    public static Country france() {
        return new Country("France", "FR", VisaType.SCHENGEN);
    }
    
    public static Country usa() {
        return new Country("United States of America", "US", VisaType.US);
    }
    
    public static Country italy() {
        return new Country("Italy", "IT", VisaType.SCHENGEN);
    }
}
