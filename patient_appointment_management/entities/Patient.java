package patient_appointment_management.entities;

import java.util.UUID;

public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String contactInfo;
    
    public Patient(String name, int age, String gender) {
        this.patientId = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    

    public String getPatientId() { return patientId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getContactInfo() { return contactInfo; }
    
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    
    public boolean isValid() {
        return name != null && !name.isBlank() && age > 0 && gender != null && !gender.isBlank();
    }

    @Override
    public String toString() {
        return String.format("%s (%d, %s)", name, age, gender);
    }
}