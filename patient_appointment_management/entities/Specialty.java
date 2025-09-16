package patient_appointment_management.entities;

public class Specialty {
    private String name;
    private String description;
    private double consultationFee;

    public Specialty(String name, double consultationFee) {
        this.name = name;
        this.consultationFee = consultationFee;
    }

    public String getName() { return name; }
    // For compatibility with code expecting getSpecialtyName()
    public String getSpecialtyName() { return name; }
    public String getDescription() { return description; }
    public double getConsultationFee() { return consultationFee; }
    public void setDescription(String description) { this.description = description; }
    public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
    @Override
    public String toString() {
        return name;
    }
}