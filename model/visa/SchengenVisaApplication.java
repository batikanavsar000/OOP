package model.visa;

import exception.MissingDocumentException;
import java.util.Arrays;
import java.util.List;

public class SchengenVisaApplication extends VisaApplication {
    
    private static final double BASE_FEE_EUR = 80.0;
    private static final double EUR_TO_TRY = 37.0;
    
    private final String targetCountry;
    
    public SchengenVisaApplication(String applicantName) {
        this(applicantName, "Germany");
    }
    
    public SchengenVisaApplication(String applicantName, String targetCountry) {
        super(applicantName);
        this.targetCountry = targetCountry;
    }
    
    @Override
    public double calculateFee() {
        return BASE_FEE_EUR * EUR_TO_TRY;
    }
    
    @Override
    public String getVisaTypeName() {
        return "Schengen Visa (" + targetCountry + ")";
    }
    
    @Override
    public List<DocumentType> getRequiredDocuments() {
        return Arrays.asList(
            DocumentType.PASSPORT,
            DocumentType.PHOTO,
            DocumentType.INSURANCE,
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
        
        if (!hasDocument(DocumentType.INSURANCE)) {
            throw new MissingDocumentException(DocumentType.INSURANCE);
        }
        
        if (!hasDocument(DocumentType.BANK_STATEMENT)) {
            throw new MissingDocumentException(DocumentType.BANK_STATEMENT);
        }
        
        if (!hasDocument(DocumentType.PHOTO)) {
            throw new MissingDocumentException(DocumentType.PHOTO);
        }
        
        System.out.println("✅ Schengen visa documents complete!");
        setStatus(Status.SUBMITTED);
    }
    
    public String getTargetCountry() {
        return targetCountry;
    }
}
