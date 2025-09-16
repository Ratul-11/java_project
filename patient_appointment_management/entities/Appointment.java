package patient_appointment_management.entities;


public class Appointment {
    private Patient patient;
    private Doctor doctor;
    private TimeSlot timeSlot;
    private String description;
    private MedicalTest[] tests = new MedicalTest[10];
    private int testCount = 0;
    private double total;
    private String status;

    public Appointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
        this.status = "SCHEDULED";
        calcTotal();
    }

    public Patient getPatient() { return patient; }
    // For compatibility with code expecting getAppointmentId()
    public String getAppointmentId() {
        return patient.getName() + "_" + doctor.getName() + "_" + status;
    }
    // For compatibility with code expecting getIssueDescription()
    public String getIssueDescription() {
        return description;
    }
    // For compatibility with code expecting getSelectedTests()
    public java.util.List<MedicalTest> getSelectedTests() {
        java.util.List<MedicalTest> list = new java.util.ArrayList<>();
        for (int i = 0; i < testCount; i++) list.add(tests[i]);
        return list;
    }
    // For compatibility with code expecting getTotalAmount()
    public double getTotalAmount() {
        return total;
    }
    public Doctor getDoctor() { return doctor; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public MedicalTest[] getTests() {
        MedicalTest[] arr = new MedicalTest[testCount];
        for (int i = 0; i < testCount; i++) arr[i] = tests[i];
        return arr;
    }
    public int getTestCount() { return testCount; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    // Removed getCreated(), no date/time used

    public void addTest(MedicalTest test) {
        for (int i = 0; i < testCount; i++) {
            if (tests[i] == test) return;
        }
        if (testCount < tests.length) {
            tests[testCount++] = test;
            calcTotal();
        }
    }

    public void calcTotal() {
        total = 0.0;
        if (doctor != null && doctor.getSpecialty() != null) {
            total += doctor.getSpecialty().getConsultationFee();
        }
        for (int i = 0; i < testCount; i++) {
            total += tests[i].getCost();
        }
    }
}