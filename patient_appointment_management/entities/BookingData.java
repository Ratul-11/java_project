package patient_appointment_management.entities;

import java.io.*;
import java.util.Arrays;
// ...existing code...

public class BookingData {
    private static final int MAX_APPOINTMENTS = 100;
    private Appointment[] appointments = new Appointment[MAX_APPOINTMENTS];
    private int appointmentCount = 0;
    private static final String DATA_FOLDER = "patient_appointment_management" + File.separator + "data";
    private static final String APPOINTMENTS_FILE = "appointments.txt";
    
    public BookingData() {
        createDataFolder();
        loadAppointmentsFromFile();
    }
    
    private void createDataFolder() {
        File dataDir = new File(DATA_FOLDER);
        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                System.out.println("Created data folder: " + dataDir.getAbsolutePath());
            } else {
                System.err.println("Error creating data folder: " + dataDir.getAbsolutePath());
            }
        }
    }
    
    public void saveAppointmentsToFile() {
        try {
            File file = new File(DATA_FOLDER, APPOINTMENTS_FILE);
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("=== MEDICAL APPOINTMENT BOOKING SYSTEM ===");
                writer.println("Total Appointments: " + appointmentCount);
                writer.println("=" + "=".repeat(50));
                writer.println();
                for (int i = 0; i < appointmentCount; i++) {
                    Appointment apt = appointments[i];
                    writer.println("APPOINTMENT #" + (i + 1));
                    writer.println("-".repeat(30));
                    writer.println("Appointment ID: " + apt.getAppointmentId());
                    writer.println("Status: " + apt.getStatus());
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
            }
            System.out.println("Appointments saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving appointments to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadAppointmentsFromFile() {
        appointmentCount = 0;
        File file = new File(DATA_FOLDER, APPOINTMENTS_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Appointment current = null;
            Patient patient = null;
            Doctor doctor = null;
            TimeSlot timeSlot = null;
            Specialty specialty = null;
            MedicalTest[] tests = new MedicalTest[10];
            int testCount = 0;
            String status = null;
            String issueDescription = null;
            String patientName = null, patientGender = null;
            int patientAge = 0;
            String doctorName = null, doctorSpecialty = null;
            double consultationFee = 0.0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("APPOINTMENT #")) {
                    // reset for new appointment
                    testCount = 0;
                    status = null; issueDescription = null;
                    patientName = null; patientGender = null; patientAge = 0;
                    doctorName = null; doctorSpecialty = null; consultationFee = 0.0;
                } else if (line.startsWith("Appointment ID:")) {
                    // skip
                } else if (line.startsWith("Status:")) {
                    status = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Name:")) {
                    patientName = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Age:")) {
                    try { patientAge = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim()); } catch (NumberFormatException e) {}
                } else if (line.startsWith("Gender:")) {
                    patientGender = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Patient ID:")) {
                    // skip
                } else if (line.startsWith("Doctor:")) {
                    doctorName = line.substring(line.indexOf(":") + 1).replace("Dr. ", "").trim();
                } else if (line.startsWith("Specialty:")) {
                    doctorSpecialty = line.substring(line.indexOf(":") + 1).trim();
                } else if (line.startsWith("Consultation Fee:")) {
                    String feeStr = line.substring(line.indexOf("$") + 1).trim();
                    try { consultationFee = Double.parseDouble(feeStr); } catch (Exception e) {}
                } else if (line.startsWith("ISSUE DESCRIPTION:")) {
                    // next line is the description
                    issueDescription = reader.readLine();
                    if (issueDescription != null) issueDescription = issueDescription.trim();
                } else if (line.startsWith("- ")) {
                    // Medical test
                    String testLine = line.substring(2).trim();
                    int idx = testLine.lastIndexOf("($");
                    if (idx > 0 && testCount < tests.length) {
                        String testName = testLine.substring(0, idx).trim();
                        String costStr = testLine.substring(idx + 2, testLine.length() - 1);
                        double cost = 0.0;
                        try { cost = Double.parseDouble(costStr); } catch (Exception e) {}
                        tests[testCount++] = new MedicalTest(testName, cost);
                    }
                } else if (line.startsWith("TOTAL AMOUNT:")) {
                    // skip
                } else if (line.startsWith("=")) {
                    // End of appointment, build objects
                    if (patientName != null && doctorName != null && doctorSpecialty != null && appointmentCount < MAX_APPOINTMENTS) {
                        specialty = new Specialty(doctorSpecialty, consultationFee);
                        doctor = new Doctor(doctorName, specialty);
                        timeSlot = new TimeSlot(); // No date/time
                        patient = new Patient(patientName, patientAge, patientGender);
                        current = new Appointment(patient, doctor, timeSlot);
                        current.setStatus(status);
                        current.setDescription(issueDescription);
                        for (int i = 0; i < testCount; i++) current.addTest(tests[i]);
                        appointments[appointmentCount++] = current;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading appointments from file: " + e.getMessage());
        }
    }
    
    public boolean saveAppointment(Appointment appointment) {
        if (appointmentCount < MAX_APPOINTMENTS) {
            appointments[appointmentCount++] = appointment;
            saveAppointmentsToFile();
            return true;
        }
        return false;
    }
    
    public Appointment getAppointmentById(String id) {
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getAppointmentId().equals(id)) {
                return appointments[i];
            }
        }
        return null;
    }
    
    public boolean updateAppointment(Appointment appointment) {
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getAppointmentId().equals(appointment.getAppointmentId())) {
                appointments[i] = appointment;
                saveAppointmentsToFile();
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteAppointment(String id) {
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getAppointmentId().equals(id)) {
                // Shift left
                for (int j = i; j < appointmentCount - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }
                appointments[--appointmentCount] = null;
                saveAppointmentsToFile();
                return true;
            }
        }
        return false;
    }
    
    public Appointment[] getAppointmentsByPatient(String patientId) {
        Appointment[] result = new Appointment[appointmentCount];
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i].getPatient().getPatientId().equals(patientId)) {
                result[count++] = appointments[i];
            }
        }
        return Arrays.copyOf(result, count);
    }
    
    public Appointment[] getAllAppointments() {
        return Arrays.copyOf(appointments, appointmentCount);
    }
    
    public void backupAppointments() {
        String backupFileName = "appointments_backup_" + 
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".txt";
        File source = new File(DATA_FOLDER, APPOINTMENTS_FILE);
        File backup = new File(DATA_FOLDER, backupFileName);
        try (
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(backup)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            System.out.println("Backup created: " + backup.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}
