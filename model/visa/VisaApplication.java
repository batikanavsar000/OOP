package model.visa;

import exception.MissingDocumentException;
import model.Exportable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class VisaApplication implements Exportable {
    
    public enum Status {
        DRAFT("Draft"),
        SUBMITTED("Submitted"),
        PROCESSING("Processing"),
        APPROVED("Approved"),
        REJECTED("Rejected");
        
        private final String displayName;
        
        Status(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    protected final String applicantName;
    protected final List<Document> documents;
    protected final LocalDate applicationDate;
    protected Status status;
    
    public VisaApplication(String applicantName) {
        if (applicantName == null || applicantName.isBlank()) {
            throw new IllegalArgumentException("Applicant name cannot be empty!");
        }
        this.applicantName = applicantName.trim();
        this.documents = new ArrayList<>();
        this.applicationDate = LocalDate.now();
        this.status = Status.DRAFT;
    }
    
    public void addDocument(Document doc) {
        if (doc == null) {
            throw new IllegalArgumentException("Document cannot be null!");
        }
        documents.removeIf(d -> d.getType() == doc.getType());
        documents.add(doc);
    }
    
    public Optional<Document> getDocument(DocumentType type) {
        return documents.stream()
            .filter(d -> d.getType() == type)
            .findFirst();
    }
    
    public boolean hasDocument(DocumentType type) {
        return getDocument(type).isPresent();
    }
    
    public List<Document> getDocuments() {
        return new ArrayList<>(documents);
    }
    
    public String getApplicantName() {
        return applicantName;
    }
    
    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public abstract void validateDocuments() throws MissingDocumentException;
    
    public abstract double calculateFee();
    
    public abstract String getVisaTypeName();
    
    public abstract List<DocumentType> getRequiredDocuments();
    
    public List<DocumentType> getMissingDocuments() {
        List<DocumentType> missing = new ArrayList<>();
        for (DocumentType required : getRequiredDocuments()) {
            if (!hasDocument(required)) {
                missing.add(required);
            }
        }
        return missing;
    }
    
    @Override
    public String exportToText() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("         VISA APPLICATION\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("Visa Type    : ").append(getVisaTypeName()).append("\n");
        sb.append("Applicant    : ").append(applicantName).append("\n");
        sb.append("Application  : ").append(applicationDate).append("\n");
        sb.append("Status       : ").append(status.getDisplayName()).append("\n");
        sb.append("Fee          : ").append(String.format("%.2f", calculateFee())).append("\n");
        sb.append("───────────────────────────────────────\n");
        sb.append("Uploaded Documents:\n");
        if (documents.isEmpty()) {
            sb.append("  (No documents uploaded yet)\n");
        } else {
            for (Document doc : documents) {
                sb.append("  ").append(doc.toString()).append("\n");
            }
        }
        List<DocumentType> missing = getMissingDocuments();
        if (!missing.isEmpty()) {
            sb.append("───────────────────────────────────────\n");
            sb.append("⚠️ Missing Documents:\n");
            for (DocumentType type : missing) {
                sb.append("  • ").append(type.getDisplayName()).append("\n");
            }
        }
        sb.append("═══════════════════════════════════════");
        return sb.toString();
    }
}
