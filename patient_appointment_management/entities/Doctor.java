package patient_appointment_management.entities;


public class Doctor extends Person {
    private Specialty specialty;
    private String qualifications;
    private int experience;
    private TimeSlot[] availability = new TimeSlot[10];
    private int slotCount = 0;

    public Doctor(String name, Specialty specialty) {
        super(name, 0, null);
        this.specialty = specialty;
    }

    public Specialty getSpecialty() { return specialty; }
    public String getQualifications() { return qualifications; }
    public int getExperience() { return experience; }
    public TimeSlot[] getAvailability() {
        return java.util.Arrays.copyOf(availability, slotCount);
    }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }
    public void setExperience(int experience) { this.experience = experience; }
    public void addTimeSlot(TimeSlot slot) {
        if (slot != null && !containsSlot(slot) && slotCount < availability.length) {
            availability[slotCount++] = slot;
        }
    }
    private boolean containsSlot(TimeSlot slot) {
        for (int i = 0; i < slotCount; i++) {
            if (availability[i].equals(slot)) return true;
        }
        return false;
    }
    public void removeTimeSlot(TimeSlot slot) {
        for (int i = 0; i < slotCount; i++) {
            if (availability[i].equals(slot)) {
                for (int j = i; j < slotCount - 1; j++) {
                    availability[j] = availability[j + 1];
                }
                availability[--slotCount] = null;
                break;
            }
        }
    }
    // Removed isAvailable(Date, LocalTime) for beginner simplicity
    @Override
    public String getRole() {
        return "Doctor";
    }
}