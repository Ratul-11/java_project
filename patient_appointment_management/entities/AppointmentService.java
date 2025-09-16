package patient_appointment_management.entities;

import java.time.LocalTime;
import java.util.*;

public class AppointmentService {
    private BookingData appointmentDAO;
    private List<Specialty> specialties;
    private List<Doctor> doctors;
    private List<MedicalTest> availableTests;
    
    public AppointmentService() {
        this.appointmentDAO = new BookingData();
        initializeData();
    }
    
    private void initializeData() {
        specialties = Arrays.asList(
            new Specialty("1", "Cardiology", 150.0),
            new Specialty("2", "Dermatology", 120.0),
            new Specialty("3", "Orthopedics", 140.0),
            new Specialty("4", "Pediatrics", 100.0)
        );
        doctors = Arrays.asList(
            new Doctor("1", "John Smith", specialties.get(0)),
            new Doctor("2", "Sarah Johnson", specialties.get(1)),
            new Doctor("3", "Michael Brown", specialties.get(2)),
            new Doctor("4", "Emily Davis", specialties.get(3))
        );
        for (Doctor doctor : doctors) {
            doctor.addTimeSlot(new TimeSlot(new Date(), LocalTime.of(9, 0), LocalTime.of(10, 0)));
            doctor.addTimeSlot(new TimeSlot(new Date(), LocalTime.of(11, 0), LocalTime.of(12, 0)));
            doctor.addTimeSlot(new TimeSlot(new Date(), LocalTime.of(14, 0), LocalTime.of(15, 0)));
        }
        availableTests = Arrays.asList(
            new MedicalTest("1", "Blood Test", 50.0),
            new MedicalTest("2", "X-Ray", 80.0),
            new MedicalTest("3", "ECG", 60.0),
            new MedicalTest("4", "Ultrasound", 100.0)
        );
    }
    public boolean createAppointment(Appointment appointment) {
        appointment.calculateTotal();
        return appointmentDAO.saveAppointment(appointment);
    }
    public boolean deleteAppointment(String appointmentId) {
        return appointmentDAO.deleteAppointment(appointmentId);
    }
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }
    public List<TimeSlot> getAvailableSlots(Doctor doctor, Date date) {
        return doctor.getAvailability().stream()
            .filter(TimeSlot::isAvailable)
            .collect(ArrayList::new, (list, slot) -> list.add(slot), (list1, list2) -> list1.addAll(list2));
    }
    public List<Specialty> getSpecialties() {
        return new ArrayList<>(specialties);
    }
    public List<Doctor> getDoctorsBySpecialty(Specialty specialty) {
        return doctors.stream()
            .filter(doctor -> doctor.getSpecialty().getSpecialtyId().equals(specialty.getSpecialtyId()))
            .collect(ArrayList::new, (list, doctor) -> list.add(doctor), (list1, list2) -> list1.addAll(list2));
    }
    public List<MedicalTest> getAvailableTests() {
        return new ArrayList<>(availableTests);
    }
    public double calculateAppointmentCost(Appointment appointment) {
        return appointment.calculateTotal();
    }
}
