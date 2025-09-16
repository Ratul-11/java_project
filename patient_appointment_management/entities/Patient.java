package patient_appointment_management.entities;

public class Patient extends Person {
    public Patient(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public String getRole() {
        return "Patient";
    }

    // For compatibility with code expecting getPatientId()
    public String getPatientId() {
        return getName() + "_" + getAge() + "_" + getGender();
    }
}