package patient_appointment_management.entities;

public class MedicalTest {
    private String testId;
    private String testName;
    private String testType;
    private double cost;
    private String description;
    
    public MedicalTest(String testId, String testName, double cost) {
        this.testId = testId;
        this.testName = testName;
        this.cost = cost;
    }
    
    // Getters and Setters
    public String getTestId() { return testId; }
    public String getTestName() { return testName; }
    public String getTestType() { return testType; }
    public double getCost() { return cost; }
    public String getDescription() { return description; }
    
    public void setTestType(String testType) { this.testType = testType; }
    public void setCost(double cost) { this.cost = cost; }
    public void setDescription(String description) { this.description = description; }
    
    @Override
    public String toString() {
        return testName + " ($" + cost + ")";
    }
}