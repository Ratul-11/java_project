package patient_appointment_management;

import java.util.Date;
import patient_appointment_management.entities.*;

public interface IAppointmentBooking {
    boolean validatePatientInfo();
    void loadSpecialties();
    void loadDoctors(Specialty specialty);
    void checkAvailability(Doctor doctor, Date date);
    double calculateTotal();
    boolean submitAppointment();
    void resetForm();
}