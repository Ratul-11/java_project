package patient_appointment_management;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import patient_appointment_management.frame.AppointmentBookingGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new AppointmentBookingGUI().setVisible(true);
        });
    }
}