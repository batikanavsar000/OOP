package service;

import model.visa.SchengenVisaApplication;
import model.visa.UsVisaApplication;
import model.visa.VisaApplication;

import java.util.Arrays;
import java.util.List;

public class VisaService {
    
    private static final List<String> SCHENGEN_COUNTRIES = Arrays.asList(
        "DE", "FR", "IT", "ES", "NL", "BE", "AT", "CH", "GR", "PT", "SE", "NO", "DK", "FI", "PL", "CZ"
    );
    
    public VisaApplication createApplication(String countryCode, String applicantName) {
        if (countryCode == null || countryCode.isBlank()) {
            System.out.println("❌ Country code not specified!");
            return null;
        }
        
        if (applicantName == null || applicantName.isBlank()) {
            System.out.println("❌ Applicant name not specified!");
            return null;
        }
        
        String code = countryCode.toUpperCase().trim();
        
        if (SCHENGEN_COUNTRIES.contains(code)) {
            String countryName = getCountryName(code);
            System.out.println("✅ Creating Schengen visa application: " + countryName);
            return new SchengenVisaApplication(applicantName, countryName);
        }
        
        if (code.equals("US") || code.equals("USA")) {
            System.out.println("✅ Creating US visa application...");
            return new UsVisaApplication(applicantName);
        }
        
        System.out.println("⚠️ Automatic visa system is not yet available for this country: " + code);
        System.out.println("   Supported countries: US, " + String.join(", ", SCHENGEN_COUNTRIES));
        return null;
    }
    
    private String getCountryName(String code) {
        return switch (code) {
            case "DE" -> "Germany";
            case "FR" -> "France";
            case "IT" -> "Italy";
            case "ES" -> "Spain";
            case "NL" -> "Netherlands";
            case "BE" -> "Belgium";
            case "AT" -> "Austria";
            case "CH" -> "Switzerland";
            case "GR" -> "Greece";
            case "PT" -> "Portugal";
            case "SE" -> "Sweden";
            case "NO" -> "Norway";
            case "DK" -> "Denmark";
            case "FI" -> "Finland";
            case "PL" -> "Poland";
            case "CZ" -> "Czech Republic";
            default -> code;
        };
    }
    
    public void printSupportedCountries() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("       SUPPORTED COUNTRIES");
        System.out.println("═══════════════════════════════════════");
        System.out.println("\n🇺🇸 US Visa:");
        System.out.println("   • US - United States of America");
        System.out.println("\n🇪🇺 Schengen Visa:");
        for (String code : SCHENGEN_COUNTRIES) {
            System.out.println("   • " + code + " - " + getCountryName(code));
        }
        System.out.println("═══════════════════════════════════════\n");
    }
    
    public double calculateVisaFee(String countryCode) {
        String code = countryCode.toUpperCase().trim();
        
        if (code.equals("US") || code.equals("USA")) {
            return 185.0 * 34.5;
        }
        
        if (SCHENGEN_COUNTRIES.contains(code)) {
            return 80.0 * 37.0;
        }
        
        return 0;
    }
}
