package exception;

import model.visa.DocumentType;

public class MissingDocumentException extends Exception {
    private final DocumentType missingType;
    
    public MissingDocumentException(String message) {
        super(message);
        this.missingType = null;
    }
    
    public MissingDocumentException(DocumentType missingType) {
        super("Missing document: " + missingType.getDisplayName());
        this.missingType = missingType;
    }
    
    public DocumentType getMissingType() {
        return missingType;
    }
}
