package patient_appointment_management.entities;


public class TimeSlot {
    private String date; // e.g. "2025-09-16"
    private String startTime; // e.g. "09:00"
    private String endTime;   // e.g. "10:00"
    private int availableSlots;
    private int totalSlots;
    private boolean isBooked;

    public TimeSlot() {
        this("", "", "");
    }
    public TimeSlot(String date, String startTime, String endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalSlots = 1;
        this.availableSlots = 1;
        this.isBooked = false;
    }

    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public int getAvailableSlots() { return availableSlots; }
    public int getTotalSlots() { return totalSlots; }
    public boolean isBooked() { return isBooked; }

    public boolean bookSlot() {
        if (availableSlots > 0) {
            availableSlots--;
            if (availableSlots == 0) {
                isBooked = true;
            }
            return true;
        }
        return false;
    }

    public void cancelSlot() {
        if (availableSlots < totalSlots) {
            availableSlots++;
            isBooked = false;
        }
    }

    public boolean isAvailable() {
        return availableSlots > 0 && !isBooked;
    }

    @Override
    public String toString() {
        return startTime + "-" + endTime + " (Available: " + availableSlots + "/" + totalSlots + ")";
    }
}