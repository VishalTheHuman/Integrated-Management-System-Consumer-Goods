package com.example.jfk;

public class EmployeeFrame {
    private String id;
    private String name;
    private Integer salary;

    public EmployeeFrame(String id, String name, Integer salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }
    public String getEmployeeID() {
        return id;
    }

    public String getEmployeeName() {
        return name;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
