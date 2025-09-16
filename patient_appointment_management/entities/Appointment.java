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
        if (test != null && !selectedTests.contains(test)) {
            selectedTests.add(test);
            updateTotalAmount();
        }
    }

    public void removeTest(MedicalTest test) {
        if (selectedTests.remove(test)) {
            updateTotalAmount();
        }
    }

    public double calculateTotal() {
        updateTotalAmount();
        return totalAmount;
    }

    private void updateTotalAmount() {
        double sum = 0.0;
        if (doctor != null && doctor.getSpecialty() != null) {
            sum += doctor.getSpecialty().getConsultationFee();
        }
        for (MedicalTest test : selectedTests) {
            sum += test.getCost();
        }
        totalAmount = sum;
    }
}