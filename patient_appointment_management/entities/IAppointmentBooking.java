package patient_appointment_management.entities;


public interface IAppointmentBooking {
    boolean validatePatientInfo();
    void loadSpecialties();
    void loadDoctors(Specialty specialty);
    void checkAvailability(Doctor doctor);
    double calculateTotal();
    boolean submitAppointment();
    void resetForm();
}
