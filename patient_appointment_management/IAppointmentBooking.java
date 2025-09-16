package patient_appointment_management;

import patient_appointment_management.entities.*;
import java.util.Date;

public interface IAppointmentBooking {
    boolean validatePatientInfo();
    void loadSpecialties();
    void loadDoctors(Specialty specialty);
    void checkAvailability(Doctor doctor, Date date);
    double calculateTotal();
    boolean submitAppointment();
    void resetForm();
}