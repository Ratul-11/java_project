package patient_appointment_management.entities;

import java.time.LocalTime;
import java.util.*;

public class Doctor {
    private String doctorId;
    private String name;
    private Specialty specialty;
    private String qualifications;
    private int experience;
    private List<TimeSlot> availability;
    
    public Doctor(String doctorId, String name, Specialty specialty) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
        this.availability = new ArrayList<>();
    }
    

    public String getDoctorId() { return doctorId; }
    public String getName() { return name; }
    public Specialty getSpecialty() { return specialty; }
    public String getQualifications() { return qualifications; }
    public int getExperience() { return experience; }
    public List<TimeSlot> getAvailability() { return availability; }
    
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }
    public void setExperience(int experience) { this.experience = experience; }
    
    public void addTimeSlot(TimeSlot slot) {
        if (slot != null && !availability.contains(slot)) {
            availability.add(slot);
        }
    }

    public void removeTimeSlot(TimeSlot slot) {
        availability.remove(slot);
    }

    public boolean isAvailable(Date date, LocalTime time) {
        for (TimeSlot slot : availability) {
            if (slot.getDate().equals(date) && slot.getStartTime().equals(time) && slot.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Dr. %s (%s)", name, specialty.getSpecialtyName());
    }
}