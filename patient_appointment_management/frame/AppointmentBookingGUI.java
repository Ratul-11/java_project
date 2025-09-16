package patient_appointment_management.frame;

import patient_appointment_management.*;
import patient_appointment_management.entities.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.List;

public class AppointmentBookingGUI extends JFrame implements IAppointmentBooking {
    // GUI Components
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderComboBox;
    private JComboBox<Specialty> specialtyDropdown;
    private JComboBox<Doctor> doctorDropdown;
    private JComboBox<TimeSlot> timeSlotDropdown;
    private JTextArea issueTextArea;
    private JPanel testsPanel;
    private JLabel totalAmountLabel;
    private JButton submitButton;
    private JButton resetButton;
    private JButton showAppointmentsButton;
    private JButton deleteAppointmentButton;
    
    // Service layer
    private AppointmentService appointmentService;
    private List<JCheckBox> testCheckboxes;
    
    public AppointmentBookingGUI() {
        this.appointmentService = new AppointmentService();
        this.testCheckboxes = new ArrayList<>();
        initializeComponents();
        setupEventHandlers();
        createLayout();
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        setTitle("Medical Appointment Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize components
        nameField = new JTextField(20);
        ageField = new JTextField(10);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        specialtyDropdown = new JComboBox<>();
        doctorDropdown = new JComboBox<>();
        timeSlotDropdown = new JComboBox<>();
        issueTextArea = new JTextArea(4, 30);
        issueTextArea.setWrapStyleWord(true);
        issueTextArea.setLineWrap(true);
        testsPanel = new JPanel(new GridLayout(0, 2));
        totalAmountLabel = new JLabel("Total: $0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton = new JButton("Submit Appointment");
        resetButton = new JButton("Reset Form");
        showAppointmentsButton = new JButton("Show Appointments");
        deleteAppointmentButton = new JButton("Delete Appointment");
        
        loadSpecialties();
        loadAvailableTests();
    }
    
    private void createLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Patient Information Section
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(ageField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(genderComboBox, gbc);
        
        // Appointment Details Section
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Specialty:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(specialtyDropdown, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(doctorDropdown, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Time Slot:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(timeSlotDropdown, gbc);
        
        // Issue Description
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Issue Description:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JScrollPane(issueTextArea), gbc);
        
        // Medical Tests
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Medical Tests:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(new JScrollPane(testsPanel), gbc);
        
        // Total Amount
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(totalAmountLabel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(showAppointmentsButton);
        buttonPanel.add(deleteAppointmentButton);
        
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        specialtyDropdown.addActionListener(e -> onSpecialtySelected());
        doctorDropdown.addActionListener(e -> onDoctorSelected());
        timeSlotDropdown.addActionListener(e -> calculateTotal());
        submitButton.addActionListener(e -> submitAppointment());
        resetButton.addActionListener(e -> resetForm());
        showAppointmentsButton.addActionListener(e -> showAppointments());
        deleteAppointmentButton.addActionListener(e -> deleteAppointment());
    }
    
    private void showAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments found.", "Appointments", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create a table to display appointments
        String[] columnNames = {"ID", "Patient", "Doctor", "Specialty", "Date/Time", "Status", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Populate table with appointment data
        for (Appointment apt : appointments) {
            Object[] rowData = {
                apt.getAppointmentId().substring(0, 8) + "...",
                apt.getPatient().getName() + " (" + apt.getPatient().getAge() + ")",
                apt.getDoctor().getName(),
                apt.getDoctor().getSpecialty().getSpecialtyName(),
                apt.getTimeSlot().getStartTime() + "-" + apt.getTimeSlot().getEndTime(),
                apt.getStatus(),
                "$" + String.format("%.2f", apt.getTotalAmount())
            };
            tableModel.addRow(rowData);
        }
        
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        // Create dialog to show appointments
        JDialog dialog = new JDialog(this, "All Appointments", true);
        dialog.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.add(new JLabel("Total Appointments: " + appointments.size()));
        
        dialog.add(infoPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void deleteAppointment() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        if (appointments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments found to delete.", "Delete Appointment", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create a list of appointment descriptions for selection
        String[] appointmentDescriptions = new String[appointments.size()];
        for (int i = 0; i < appointments.size(); i++) {
            Appointment apt = appointments.get(i);
            appointmentDescriptions[i] = String.format("%s - %s - Dr. %s (%s) - $%.2f",
                apt.getPatient().getName(),
                apt.getTimeSlot().getStartTime() + "-" + apt.getTimeSlot().getEndTime(),
                apt.getDoctor().getName(),
                apt.getStatus(),
                apt.getTotalAmount()
            );
        }
        
        // Show selection dialog
        String selectedAppointment = (String) JOptionPane.showInputDialog(
            this,
            "Select appointment to delete:",
            "Delete Appointment",
            JOptionPane.QUESTION_MESSAGE,
            null,
            appointmentDescriptions,
            appointmentDescriptions[0]
        );
        
        if (selectedAppointment != null) {
            // Find the selected appointment
            int selectedIndex = -1;
            for (int i = 0; i < appointmentDescriptions.length; i++) {
                if (appointmentDescriptions[i].equals(selectedAppointment)) {
                    selectedIndex = i;
                    break;
                }
            }
            
            if (selectedIndex >= 0) {
                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this appointment?\n\n" + selectedAppointment,
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    Appointment appointmentToDelete = appointments.get(selectedIndex);
                    boolean success = appointmentService.deleteAppointment(appointmentToDelete.getAppointmentId());
                    
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Appointment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete appointment.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean validatePatientInfo() {
        try {
            return !nameField.getText().trim().isEmpty() &&
                   !ageField.getText().trim().isEmpty() &&
                   Integer.parseInt(ageField.getText()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public void loadSpecialties() {
        List<Specialty> specialties = appointmentService.getSpecialties();
        specialtyDropdown.removeAllItems();
        for (Specialty specialty : specialties) {
            specialtyDropdown.addItem(specialty);
        }
    }
    
    @Override
    public void loadDoctors(Specialty specialty) {
        List<Doctor> doctors = appointmentService.getDoctorsBySpecialty(specialty);
        doctorDropdown.removeAllItems();
        for (Doctor doctor : doctors) {
            doctorDropdown.addItem(doctor);
        }
        if (!doctors.isEmpty()) {
            onDoctorSelected();
        }
    }
    
    @Override
    public void checkAvailability(Doctor doctor, Date date) {
        List<TimeSlot> slots = appointmentService.getAvailableSlots(doctor, date);
        timeSlotDropdown.removeAllItems();
        for (TimeSlot slot : slots) {
            timeSlotDropdown.addItem(slot);
        }
        calculateTotal();
    }
    
    private void onSpecialtySelected() {
        Specialty selectedSpecialty = (Specialty) specialtyDropdown.getSelectedItem();
        if (selectedSpecialty != null) {
            loadDoctors(selectedSpecialty);
        }
    }
    
    private void onDoctorSelected() {
        Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem();
        if (selectedDoctor != null) {
            checkAvailability(selectedDoctor, new Date());
        }
    }
    
    private void loadAvailableTests() {
        List<MedicalTest> tests = appointmentService.getAvailableTests();
        testsPanel.removeAll();
        testCheckboxes.clear();
        
        for (MedicalTest test : tests) {
            JCheckBox checkbox = new JCheckBox(test.getTestName() + " ($" + test.getCost() + ")");
            checkbox.addActionListener(e -> calculateTotal());
            testCheckboxes.add(checkbox);
            testsPanel.add(checkbox);
        }
    }
    
    @Override
    public double calculateTotal() {
        double total = 0.0;
        
        // Add consultation fee
        Specialty selectedSpecialty = (Specialty) specialtyDropdown.getSelectedItem();
        if (selectedSpecialty != null) {
            total += selectedSpecialty.getConsultationFee();
        }
        
        // Add test costs
        List<MedicalTest> allTests = appointmentService.getAvailableTests();
        for (int i = 0; i < testCheckboxes.size() && i < allTests.size(); i++) {
            if (testCheckboxes.get(i).isSelected()) {
                total += allTests.get(i).getCost();
            }
        }
        
        totalAmountLabel.setText("Total: $" + String.format("%.2f", total));
        return total;
    }
    
    @Override
    public boolean submitAppointment() {
        if (!validatePatientInfo()) {
            JOptionPane.showMessageDialog(this, "Please fill in all patient information correctly.");
            return false;
        }
        
        if (doctorDropdown.getSelectedItem() == null || timeSlotDropdown.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor and time slot.");
            return false;
        }
        
        try {
            Patient patient = new Patient(
                nameField.getText(),
                Integer.parseInt(ageField.getText()),
                genderComboBox.getSelectedItem().toString()
            );
            
            Doctor selectedDoctor = (Doctor) doctorDropdown.getSelectedItem();
            TimeSlot selectedTimeSlot = (TimeSlot) timeSlotDropdown.getSelectedItem();
            
            Appointment appointment = new Appointment(patient, selectedDoctor, selectedTimeSlot);
            appointment.setIssueDescription(issueTextArea.getText());
            
            // Add selected tests
            List<MedicalTest> allTests = appointmentService.getAvailableTests();
            for (int i = 0; i < testCheckboxes.size() && i < allTests.size(); i++) {
                if (testCheckboxes.get(i).isSelected()) {
                    appointment.addTest(allTests.get(i));
                }
            }
            
            boolean success = appointmentService.createAppointment(appointment);
            if (success) {
                // Show success message with file save confirmation
                String message = String.format(
                    "Appointment booked successfully!\n\n" +
                    "Patient: %s\n" +
                    "Doctor: Dr. %s\n" +
                    "Specialty: %s\n" +
                    "Time: %s-%s\n" +
                    "Total Amount: $%.2f\n\n" +
                    "Appointment details saved to: data/appointments.txt",
                    patient.getName(),
                    selectedDoctor.getName(),
                    selectedDoctor.getSpecialty().getSpecialtyName(),
                    selectedTimeSlot.getStartTime(),
                    selectedTimeSlot.getEndTime(),
                    appointment.getTotalAmount()
                );
                
                JOptionPane.showMessageDialog(this, message, "Appointment Confirmed", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
            return success;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error booking appointment: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public void resetForm() {
        nameField.setText("");
        ageField.setText("");
        genderComboBox.setSelectedIndex(0);
        if (specialtyDropdown.getItemCount() > 0) {
            specialtyDropdown.setSelectedIndex(0);
        }
        issueTextArea.setText("");
        for (JCheckBox checkbox : testCheckboxes) {
            checkbox.setSelected(false);
        }
        calculateTotal();
    }
}