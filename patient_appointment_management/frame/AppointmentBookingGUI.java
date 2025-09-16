package patient_appointment_management.frame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import patient_appointment_management.entities.*;

// Beginner-friendly Appointment Booking GUI
public class AppointmentBookingGUI extends JFrame implements ActionListener {
    private JTextField nameField, ageField;
    private JComboBox<String> genderBox;
    private JComboBox<Specialty> specialtyBox;
    private JComboBox<Doctor> doctorBox;
    private JComboBox<String> slotBox;
    private JTextArea issueArea;
    private JLabel totalLabel;
    private JButton bookButton;
    private AppointmentService service;

    public AppointmentBookingGUI() {
        super("Appointment Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(400, 420);
        setLocationRelativeTo(null);

        // Initialize components
        nameField = new JTextField();
        ageField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        specialtyBox = new JComboBox<>();
        doctorBox = new JComboBox<>();
        slotBox = new JComboBox<>();
        issueArea = new JTextArea();
        totalLabel = new JLabel("Total: $0.00");
        bookButton = new JButton("Book");

        // Set bounds for components
    // Use hardcoded y-values for each row, like the sample
    //step 0
    package patient_appointment_management.frame;
    import java.awt.*;
    import javax.swing.*;
    import java.awt.event.*;

    //step 1
    public class AppointmentBookingGUI extends JFrame implements ActionListener {
        //step 2
        private JTextField nameField, ageField;
        private JComboBox genderBox, specialtyBox, doctorBox, slotBox;
        private JTextArea issueArea;
        private JLabel totalLabel;
        private JButton bookButton;

        //step 4
        public AppointmentBookingGUI() {
            //step 4(a)
            super("Appointment Booking");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setSize(400, 420);
            setLocationRelativeTo(null);

            //step 4(b)
            nameField = new JTextField();
            ageField = new JTextField();
            genderBox = new JComboBox(new String[]{"Male", "Female", "Other"});
            specialtyBox = new JComboBox(new String[]{"Cardiology", "Dermatology", "Orthopedics", "Pediatrics"});
            doctorBox = new JComboBox(new String[]{"Dr. A", "Dr. B", "Dr. C", "Dr. D"});
            slotBox = new JComboBox(new String[]{"09:00-10:00", "11:00-12:00", "14:00-15:00"});
            issueArea = new JTextArea();
            totalLabel = new JLabel("Total: $0.00");
            bookButton = new JButton("Book");

            //step 4(c)
            int h = 28;
            addLabel("Name:", 20, 20, 80, h); addField(nameField, 110, 20, 180, h);
            addLabel("Age:", 20, 56, 80, h); addField(ageField, 110, 56, 180, h);
            addLabel("Gender:", 20, 92, 80, h); addField(genderBox, 110, 92, 180, h);
            addLabel("Specialty:", 20, 128, 80, h); addField(specialtyBox, 110, 128, 180, h);
            addLabel("Doctor:", 20, 164, 80, h); addField(doctorBox, 110, 164, 180, h);
            addLabel("Time Slot:", 20, 200, 80, h); addField(slotBox, 110, 200, 180, h);
            addLabel("Issue:", 20, 236, 80, h); addField(issueArea, 110, 236, 180, h + 20);
            addField(totalLabel, 20, 280, 150, h);
            addField(bookButton, 200, 280, 90, h);

            //step 4(d)
            bookButton.addActionListener(this);
            setVisible(true);
        }

        //step 4(e)
        private void addLabel(String text, int x, int y, int w, int h) {
            JLabel label = new JLabel(text);
            label.setBounds(x, y, w, h);
            add(label);
        }
        private void addField(JComponent comp, int x, int y, int w, int h) {
            comp.setBounds(x, y, w, h);
            add(comp);
        }

        //step 4(f)
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bookButton) {
                String pname = nameField.getText();
                String page = ageField.getText();
                String pgender = genderBox.getSelectedItem().toString();
                String spec = specialtyBox.getSelectedItem().toString();
                String doc = doctorBox.getSelectedItem().toString();
                String slot = slotBox.getSelectedItem().toString();
                String issue = issueArea.getText();
                if (pname.isEmpty() || page.isEmpty() || pgender.isEmpty() || spec.isEmpty() || doc.isEmpty() || slot.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fill all fields.");
                    return;
                }
                // Just show a message, no OOP logic
                totalLabel.setText("Total: $100.00");
                JOptionPane.showMessageDialog(this, "Booked!");
            }
        }
    }