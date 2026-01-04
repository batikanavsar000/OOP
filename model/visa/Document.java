package model.visa;

import java.time.LocalDate;

public class Document {
    private final DocumentType type;
    private final String content;
    private final LocalDate uploadDate;
    private final LocalDate expiryDate;
    
    public Document(DocumentType type, String content) {
        this(type, content, null);
    }
    
    public Document(DocumentType type, String content, LocalDate expiryDate) {
        if (type == null) {
            throw new IllegalArgumentException("Document type cannot be empty!");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Document content cannot be empty!");
        }
        this.type = type;
        this.content = content;
        this.uploadDate = LocalDate.now();
        this.expiryDate = expiryDate;
    }
    
    public DocumentType getType() {
        return type;
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDate getUploadDate() {
        return uploadDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public boolean isExpired() {
        if (expiryDate == null) return false;
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public boolean isValid() {
        return !isExpired();
    }
    
    @Override
    public String toString() {
        String status = isExpired() ? " [EXPIRED]" : "";
        return String.format("📄 %s: %s%s", type.getDisplayName(), content, status);
    }
}
