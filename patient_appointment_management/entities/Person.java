package patient_appointment_management.entities;

public abstract class Person {
    protected String name;
    protected int age;
    protected String gender;
        // protected String contactInfo; // Removed contactInfo field

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
        // public String getContactInfo() { return contactInfo; } // Removed getContactInfo method

    // Abstract method for polymorphism
    public abstract String getRole();

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
        // public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; } // Removed setContactInfo method
}
