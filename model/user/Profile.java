package model.user;

import java.time.LocalDate;

public class Profile {
    private String fullName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String nationality;
    
    public Profile(String fullName, String email) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Please enter a valid email address!");
        }
        this.fullName = fullName.trim();
        this.email = email.trim().toLowerCase();
        this.nationality = "TR";
    }
    
    public Profile(String fullName, String email, String phone, LocalDate birthDate) {
        this(fullName, email);
        this.phone = phone;
        this.birthDate = birthDate;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public void setFullName(String fullName) {
        if (fullName != null && !fullName.isBlank()) {
            this.fullName = fullName.trim();
        }
    }
    
    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email.trim().toLowerCase();
        }
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    
    public int getAge() {
        if (birthDate == null) return 0;
        return LocalDate.now().getYear() - birthDate.getYear();
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s)", fullName, email);
    }
}
