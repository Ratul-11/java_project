package patient_appointment_management.entities;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class TimeSlot {
    private String slotId;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int availableSlots;
    private int totalSlots;
    private boolean isBooked;
    
    public TimeSlot(Date date, LocalTime startTime, LocalTime endTime) {
        this.slotId = UUID.randomUUID().toString();
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalSlots = 1;
        this.availableSlots = 1;
        this.isBooked = false;
    }
    
    // Getters
    public String getSlotId() { return slotId; }
    public Date getDate() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
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