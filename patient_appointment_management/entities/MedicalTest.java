package patient_appointment_management.entities;

public class MedicalTest {
    private String name;
    private String type;
    private double cost;
    private String description;

    public MedicalTest(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    // For compatibility with code expecting getTestName()
    public String getTestName() { return name; }
    public String getType() { return type; }
    public double getCost() { return cost; }
    public String getDescription() { return description; }
    public void setType(String type) { this.type = type; }
    public void setCost(double cost) { this.cost = cost; }
    public void setDescription(String description) { this.description = description; }
    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}