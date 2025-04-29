package org.example.prpraktikum8.EmployeeTable;

public class Employee {
    private int employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String hireDate;
    private String jobID;
    private double salary;
    private Double commissionPct;
    private int managerID;
    private int departmentID;

    public Employee(){

    }

    public Employee(int employeeID, String firstName, String lastName, String email, String phoneNumber, String hireDate, String jobID, double salary, Double commissionPct, int managerID, int departmentID) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.jobID = jobID;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.managerID = managerID;
        this.departmentID = departmentID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getJobID() {
        return jobID;
    }

    public double getSalary() {
        return salary;
    }

    public Double getCommissionPct() {
        return commissionPct;
    }

    public int getManagerID() {
        return managerID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setCommissionPct(Double commissionPct) {
        this.commissionPct = commissionPct;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }
}
