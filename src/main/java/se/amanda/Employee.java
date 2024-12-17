package se.amanda;

public class Employee {
    private int employeeId;
    private String name;
    private String email;
    private String password;
    private WorkRole workRole;

    public Employee(int employeeId, String name, String email, String password, WorkRole workRole) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.workRole = workRole;
    }

    @Override
    public String toString() {
        return "Employee details: " +
                "\n--------------------" +
                "\nEmployeeId=" + employeeId +
                "\nName: " + name +
                "\nEmail:" + email +
                "\nPassword: " + password +
                 "\n" + workRole;
    }
}
