package model.visa;

import exception.MissingDocumentException;
import java.util.Arrays;
import java.util.List;

public class UsVisaApplication extends VisaApplication {
    
    private static final double BASE_FEE_USD = 185.0;
    private static final double USD_TO_TRY = 34.5;
    
    private final String visaCategory;
    
    public UsVisaApplication(String applicantName) {
        this(applicantName, "B1/B2");
    }
    
    public UsVisaApplication(String applicantName, String visaCategory) {
        super(applicantName);
        this.visaCategory = visaCategory;
    }
    
    @Override
    public double calculateFee() {
        return BASE_FEE_USD * USD_TO_TRY;
    }
    
    @Override
    public String getVisaTypeName() {
        return "US Visa (" + visaCategory + ")";
    }
    
    @Override
    public List<DocumentType> getRequiredDocuments() {
        return Arrays.asList(
            DocumentType.PASSPORT,
            DocumentType.PHOTO,
            DocumentType.DS160_FORM,
            DocumentType.BANK_STATEMENT
        );
    }
    
    @Override
    public void validateDocuments() throws MissingDocumentException {
        if (!hasDocument(DocumentType.PASSPORT)) {
            throw new MissingDocumentException(DocumentType.PASSPORT);
        }
        
        getDocument(DocumentType.PASSPORT).ifPresent(passport -> {
            if (passport.isExpired()) {
                throw new IllegalStateException("Your passport has expired!");
            }
        });
        
        if (!hasDocument(DocumentType.DS160_FORM)) {
            throw new MissingDocumentException(DocumentType.DS160_FORM);
        }
        
        if (!hasDocument(DocumentType.BANK_STATEMENT)) {
            throw new MissingDocumentException(DocumentType.BANK_STATEMENT);
        }
        
        if (!hasDocument(DocumentType.PHOTO)) {
            throw new MissingDocumentException(DocumentType.PHOTO);
        }
        
        System.out.println("✅ US visa documents complete!");
        setStatus(Status.SUBMITTED);
    }
    
    public String getVisaCategory() {
        return visaCategory;
    }
}
