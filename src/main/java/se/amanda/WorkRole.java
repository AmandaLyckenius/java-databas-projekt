package se.amanda;

import java.sql.Date;

public class WorkRole {
    private int roleId;
    private String title;
    private String description;
    private double salary;
    private java.sql.Date creationDate;

    public WorkRole(int roleId, String title, String description, double salary, Date creationDate) {
        this.roleId = roleId;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }

    public WorkRole(String title, String description, double salary, Date creationDate) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.creationDate = creationDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getSalary() {
        return salary;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "WorkRole details: " +
                "\n-------------------" +
                "\nRoleId: " + roleId +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nSalary: " + salary +
                "\nCreationDate: " + creationDate;
    }
}
