package ui;

import exception.MissingDocumentException;
import model.user.User;
import model.visa.*;
import service.VisaService;

public class PlanVisaCommand implements Command {
    private final VisaService visaService;
    private final User currentUser;

    public PlanVisaCommand(VisaService visaService, User currentUser) {
        this.visaService = visaService;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        InputHelper.printHeader("VISA APPLICATION");
        
        showExistingVisas();
        
        System.out.println("\n📋 Supported country codes:");
        System.out.println("   🇺🇸 USA: US");
        System.out.println("   🇪🇺 Schengen: DE, FR, IT, ES, NL, BE, AT, CH, GR, PT...");
        InputHelper.printSubSeparator();

        String country = InputHelper.readString("Country code (e.g.: US, DE, FR)").toUpperCase();
        String name = InputHelper.readString("Applicant's full name");

        VisaApplication app = visaService.createApplication(country, name);

        if (app == null) {
            System.out.println("\n⚠️ Automatic system is not yet available for this country.");
            return;
        }

        System.out.println("\n" + app.exportToText());
        
        System.out.println("\n📎 DOCUMENT UPLOAD SIMULATION");
        InputHelper.printSubSeparator();

        if (InputHelper.readYesNo("Upload passport")) {
            String passportNo = InputHelper.readString("Passport number");
            app.addDocument(new Document(DocumentType.PASSPORT, passportNo));
            System.out.println("✅ Passport added.");
        }

        if (InputHelper.readYesNo("Upload biometric photo")) {
            app.addDocument(new Document(DocumentType.PHOTO, "photo.jpg"));
            System.out.println("✅ Photo added.");
        }

        if (InputHelper.readYesNo("Upload bank statement")) {
            app.addDocument(new Document(DocumentType.BANK_STATEMENT, "bank_statement.pdf"));
            System.out.println("✅ Bank statement added.");
        }

        if (country.equals("US")) {
            if (InputHelper.readYesNo("Upload DS-160 Form")) {
                app.addDocument(new Document(DocumentType.DS160_FORM, "DS160-CONFIRMED"));
                System.out.println("✅ DS-160 Form added.");
            }
        } else {
            if (InputHelper.readYesNo("Upload travel insurance")) {
                app.addDocument(new Document(DocumentType.INSURANCE, "Allianz Travel Insurance"));
                System.out.println("✅ Insurance added.");
            }
        }

        InputHelper.printHeader("APPLICATION RESULT");
        
        try {
            app.validateDocuments();
            
            app.setStatus(VisaApplication.Status.APPROVED);
            currentUser.addVisaApplication(app);
            
            System.out.println("\n🎉 CONGRATULATIONS!");
            System.out.println("   Your application is complete and APPROVED!");
            System.out.printf("   Visa Fee: %.2f TL\n", app.calculateFee());
            System.out.println("   Status: " + app.getStatus().getDisplayName());
            System.out.println("\n✅ Now " + getDestinationInfo(country) + " you can plan your trip!");
            
        } catch (MissingDocumentException e) {
            System.out.println("\n❌ APPLICATION INCOMPLETE!");
            System.out.println("   " + e.getMessage());
            System.out.println("\n📝 Missing documents:");
            for (DocumentType type : app.getMissingDocuments()) {
                System.out.println("   • " + type.getDisplayName());
            }
            System.out.println("\n⚠️ Upload all documents and try again.");
        }
        
        InputHelper.printSeparator();
    }
    
    private void showExistingVisas() {
        var visas = currentUser.getVisaApplications();
        if (!visas.isEmpty()) {
            System.out.println("\n📋 Your Current Visas:");
            InputHelper.printSubSeparator();
            for (var visa : visas) {
                String statusIcon = visa.getStatus() == VisaApplication.Status.APPROVED ? "✅" : "⏳";
                System.out.println("   " + statusIcon + " " + visa.getVisaTypeName() + " - " + visa.getStatus().getDisplayName());
            }
            System.out.println();
        }
    }
    
    private String getDestinationInfo(String countryCode) {
        if (countryCode.equals("US") || countryCode.equals("USA")) {
            return "the USA";
        }
        return "Europe (Schengen area)";
    }

    @Override
    public String getDescription() {
        return "Apply for visa";
    }
}
