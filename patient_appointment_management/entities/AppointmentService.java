package patient_appointment_management.entities;


public class AppointmentService {
    private BookingData appointmentDAO;
    private Specialty[] specialties;
    private Doctor[] doctors;
    private MedicalTest[] availableTests;

    public AppointmentService() {
        this.appointmentDAO = new BookingData();
        initializeData();
    }

    private void initializeData() {
        specialties = new Specialty[] {
            new Specialty("Cardiology", 150.0),
            new Specialty("Dermatology", 120.0),
            new Specialty("Orthopedics", 140.0),
            new Specialty("Pediatrics", 100.0)
        };
        doctors = new Doctor[] {
            new Doctor("Assoc. Prof. Dr. Bijoy Dutta", specialties[0]),
            new Doctor("Prof. Dr. Md. Sahabuddin Khan", specialties[0]),
            new Doctor("Dr. Asif Imran Siddiqui", specialties[1]),
            new Doctor("Dr. Farzana Rahman Shathi", specialties[1]),
            new Doctor("Dr. Md. Tayef Mahmud", specialties[2]),
            new Doctor("Dr. A.H.M. Azgar Ali Chowdhury", specialties[2]),
            new Doctor("Emily Davis", specialties[3])
        };
        for (int i = 0; i < doctors.length; i++) {
            doctors[i].addTimeSlot(new TimeSlot("", "09:00", "10:00"));
            doctors[i].addTimeSlot(new TimeSlot("", "11:00", "12:00"));
            doctors[i].addTimeSlot(new TimeSlot("", "14:00", "15:00"));
        }
        availableTests = new MedicalTest[] {
            new MedicalTest("Blood Test", 50.0),
            new MedicalTest("X-Ray", 80.0),
            new MedicalTest("ECG", 60.0),
            new MedicalTest("Ultrasound", 100.0)
        };
    }

    public boolean createAppointment(Appointment appointment) {
        appointment.calculateTotal();
        return appointmentDAO.saveAppointment(appointment);
    }

    public boolean deleteAppointment(String appointmentId) {
        return appointmentDAO.deleteAppointment(appointmentId);
    }

    public Appointment[] getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public TimeSlot[] getAvailableSlots(Doctor doctor) {
        TimeSlot[] allSlots = doctor.getAvailability();
        TimeSlot[] result = new TimeSlot[allSlots.length];
        int count = 0;
        for (TimeSlot slot : allSlots) {
            if (slot.isAvailable()) {
                result[count++] = slot;
            }
        }
        return java.util.Arrays.copyOf(result, count);
    }

    public Specialty[] getSpecialties() {
        return specialties;
    }

    public Doctor[] getDoctorsBySpecialty(Specialty specialty) {
        Doctor[] result = new Doctor[doctors.length];
        int count = 0;
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialty().getSpecialtyId().equals(specialty.getSpecialtyId())) {
                result[count++] = doctor;
            }
        }
        return java.util.Arrays.copyOf(result, count);
    }

    public MedicalTest[] getAvailableTests() {
        return availableTests;
    }

    public double calculateAppointmentCost(Appointment appointment) {
        return appointment.calculateTotal();
    }
}
