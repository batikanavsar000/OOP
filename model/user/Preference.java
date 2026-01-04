package model.user;

public class Preference {
    
    public enum Category {
        CULTURE("Culture & History"),
        NATURE("Nature & Adventure"),
        BEACH("Beach & Sea"),
        CITY("City & Shopping"),
        FOOD("Gastronomy"),
        NIGHTLIFE("Nightlife"),
        RELAXATION("Relaxation & SPA"),
        SPORTS("Sports & Activities");
        
        private final String displayName;
        
        Category(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private final String tag;
    private final Category category;
    private int priority;
    
    public Preference(String tag) {
        this(tag, Category.CULTURE, 3);
    }
    
    public Preference(String tag, Category category) {
        this(tag, category, 3);
    }
    
    public Preference(String tag, Category category, int priority) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Preference tag cannot be empty!");
        }
        this.tag = tag.trim();
        this.category = category != null ? category : Category.CULTURE;
        this.priority = Math.min(5, Math.max(1, priority));
    }
    
    public String getTag() {
        return tag;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = Math.min(5, Math.max(1, priority));
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s] (Priority: %d)", tag, category.getDisplayName(), priority);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Preference that = (Preference) obj;
        return tag.equalsIgnoreCase(that.tag);
    }
    
    @Override
    public int hashCode() {
        return tag.toLowerCase().hashCode();
    }
}
