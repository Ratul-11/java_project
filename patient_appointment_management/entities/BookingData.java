package patient_appointment_management.entities;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BookingData {
    private List<Appointment> appointments;
    private static final String DATA_FOLDER = "data";
    private static final String APPOINTMENTS_FILE = "appointments.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public BookingData() {
        this.appointments = new ArrayList<>();
        createDataFolder();
        loadAppointmentsFromFile();
    }
    
    private void createDataFolder() {
        try {
            Path dataPath = Paths.get(DATA_FOLDER);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                System.out.println("Created data folder: " + dataPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error creating data folder: " + e.getMessage());
        }
    }
    
    public void saveAppointmentsToFile() {
        try {
            Path filePath = Paths.get(DATA_FOLDER, APPOINTMENTS_FILE);
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toFile()))) {
                writer.println("=== MEDICAL APPOINTMENT BOOKING SYSTEM ===");
                writer.println("Generated on: " + dateFormat.format(new Date()));
                writer.println("Total Appointments: " + appointments.size());
                writer.println("=" + "=".repeat(50));
                writer.println();
                for (int i = 0; i < appointments.size(); i++) {
                    Appointment apt = appointments.get(i);
                    writer.println("APPOINTMENT #" + (i + 1));
                    writer.println("-".repeat(30));
                    writer.println("Appointment ID: " + apt.getAppointmentId());
                    writer.println("Status: " + apt.getStatus());
                    writer.println("Created Date: " + dateFormat.format(apt.getCreatedDate()));
                    writer.println();
                    writer.println("PATIENT INFORMATION:");
                    writer.println("  Name: " + apt.getPatient().getName());
                    writer.println("  Age: " + apt.getPatient().getAge());
                    writer.println("  Gender: " + apt.getPatient().getGender());
                    writer.println("  Patient ID: " + apt.getPatient().getPatientId());
                    writer.println();
                    writer.println("DOCTOR INFORMATION:");
                    writer.println("  Doctor: Dr. " + apt.getDoctor().getName());
                    writer.println("  Specialty: " + apt.getDoctor().getSpecialty().getSpecialtyName());
                    writer.println("  Consultation Fee: $" + String.format("%.2f", apt.getDoctor().getSpecialty().getConsultationFee()));
                    writer.println();
                    writer.println("APPOINTMENT SCHEDULE:");
                    writer.println("  Date: " + apt.getTimeSlot().getDate());
                    writer.println("  Time: " + apt.getTimeSlot().getStartTime() + " - " + apt.getTimeSlot().getEndTime());
                    writer.println();
                    if (apt.getIssueDescription() != null && !apt.getIssueDescription().trim().isEmpty()) {
                        writer.println("ISSUE DESCRIPTION:");
                        writer.println("  " + apt.getIssueDescription());
                        writer.println();
                    }
                    if (!apt.getSelectedTests().isEmpty()) {
                        writer.println("MEDICAL TESTS:");
                        for (MedicalTest test : apt.getSelectedTests()) {
                            writer.println("  - " + test.getTestName() + " ($" + String.format("%.2f", test.getCost()) + ")");
                        }
                        writer.println();
                    }
                    writer.println("FINANCIAL DETAILS:");
                    writer.println("  Consultation Fee: $" + String.format("%.2f", apt.getDoctor().getSpecialty().getConsultationFee()));
                    double testTotal = apt.getSelectedTests().stream().mapToDouble(MedicalTest::getCost).sum();
                    writer.println("  Tests Total: $" + String.format("%.2f", testTotal));
                    writer.println("  TOTAL AMOUNT: $" + String.format("%.2f", apt.getTotalAmount()));
                    writer.println();
                    writer.println("=" + "=".repeat(50));
                    writer.println();
                }
                writer.println("SUMMARY REPORT");
                writer.println("-".repeat(20));
                Map<String, Long> specialtyCount = appointments.stream()
                    .collect(Collectors.groupingBy(
                        apt -> apt.getDoctor().getSpecialty().getSpecialtyName(),
                        Collectors.counting()
                    ));
                writer.println("Appointments by Specialty:");
                specialtyCount.forEach((specialty, count) -> 
                    writer.println("  " + specialty + ": " + count + " appointment(s)"));
                double totalRevenue = appointments.stream().mapToDouble(Appointment::getTotalAmount).sum();
                writer.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
                writer.println();
                writer.println("File saved on: " + dateFormat.format(new Date()));
            }
            System.out.println("Appointments saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving appointments to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadAppointmentsFromFile() {
        try {
            Path filePath = Paths.get(DATA_FOLDER, APPOINTMENTS_FILE);
            if (Files.exists(filePath)) {
                System.out.println("Found existing appointments file: " + filePath.toAbsolutePath());
                System.out.println("Note: Loading from file not implemented in this version.");
            }
        } catch (Exception e) {
            System.err.println("Error checking for existing appointments file: " + e.getMessage());
        }
    }
    
    public boolean saveAppointment(Appointment appointment) {
        appointments.add(appointment);
        saveAppointmentsToFile();
        return true;
    }
    
    public Appointment getAppointmentById(String id) {
        return appointments.stream()
            .filter(apt -> apt.getAppointmentId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public boolean updateAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId().equals(appointment.getAppointmentId())) {
                appointments.set(i, appointment);
                saveAppointmentsToFile();
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteAppointment(String id) {
        boolean removed = appointments.removeIf(apt -> apt.getAppointmentId().equals(id));
        if (removed) {
            saveAppointmentsToFile();
        }
        return removed;
    }
    
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.stream()
            .filter(apt -> apt.getPatient().getPatientId().equals(patientId))
            .collect(Collectors.toList());
    }
    
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
    
    public void backupAppointments() {
        String backupFileName = "appointments_backup_" + 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        try {
            Path backupPath = Paths.get(DATA_FOLDER, backupFileName);
            Files.copy(Paths.get(DATA_FOLDER, APPOINTMENTS_FILE), backupPath);
            System.out.println("Backup created: " + backupPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}
