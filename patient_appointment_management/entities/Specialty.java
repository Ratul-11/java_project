package patient_appointment_management.entities;

public class Specialty {
    private String specialtyId;
    private String specialtyName;
    private String description;
    private double consultationFee;
    
    public Specialty(String specialtyId, String specialtyName, double consultationFee) {
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.consultationFee = consultationFee;
    }
    

    public String getSpecialtyId() { return specialtyId; }
    public String getSpecialtyName() { return specialtyName; }
    public String getDescription() { return description; }
    public double getConsultationFee() { return consultationFee; }
    
    public void setDescription(String description) { this.description = description; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
    
    @Override
    public String toString() {
        return specialtyName;
    }
}