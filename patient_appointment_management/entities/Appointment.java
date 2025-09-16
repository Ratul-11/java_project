package patient_appointment_management.entities;

import java.util.*;

public class Appointment {
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private TimeSlot timeSlot;
    private String issueDescription;
    private List<MedicalTest> selectedTests;
    private double totalAmount;
    private String status;
    private Date createdDate;
    
    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        this.appointmentId = UUID.randomUUID().toString();
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        this.selectedTests = new ArrayList<>();
        this.status = "SCHEDULED";
        this.createdDate = new Date();
        calculateTotal();
    }
    
    // Getters and Setters
    public String getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public String getIssueDescription() { return issueDescription; }
    public List<MedicalTest> getSelectedTests() { return selectedTests; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public Date getCreatedDate() { return createdDate; }
    
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void addTest(MedicalTest test) {
        selectedTests.add(test);
        calculateTotal();
    }
    
    public void removeTest(MedicalTest test) {
        selectedTests.remove(test);
        calculateTotal();
    }
    
    public double calculateTotal() {
        totalAmount = doctor.getSpecialty().getConsultationFee();
        for (MedicalTest test : selectedTests) {
            totalAmount += test.getCost();
        }
        return totalAmount;
    }
}