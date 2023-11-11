package com.example.jfk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Employee {
    @FXML
    private TableColumn<EmployeeFrame, String> EmpID;

    @FXML
    private TableView<EmployeeFrame> EmployeeTab;

    @FXML
    private TableColumn<EmployeeFrame, String> Name;

    @FXML
    private TableColumn<EmployeeFrame, Integer> Salary;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField employeeName;

    @FXML
    private TextField salary;

    private final static String FILE_PATH = "../data/Employee_Data.txt";

    @FXML
    void add(ActionEvent event) {
        if (!employeeID.getText().isEmpty() && !employeeName.getText().isEmpty() && !salary.getText().isEmpty()) {
            try {
                String id = employeeID.getText();
                String name = employeeName.getText();
                Integer empSalary = Integer.parseInt(salary.getText());
                EmployeeActions.addEmployee(id, name, empSalary);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for salary: " + e.getMessage());
            }
        }
        clearFields();
        populateTable();
    }

    @FXML
    void remove(ActionEvent event) {
        if (!employeeID.getText().isEmpty()) {
            String id = employeeID.getText();
            EmployeeActions.removeEmployee(id);
        }
        clearFields();
        populateTable();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Main m = new Main();
            m.changeScene("../fxml/Admin.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update(ActionEvent event) {
        String id = employeeID.getText();
        String name = employeeName.getText();
        String salaryValue = salary.getText();
        try {
            if (!id.isEmpty()) {
                if (!name.isEmpty() && !salaryValue.isEmpty()) {
                    Integer empSalary = Integer.parseInt(salaryValue);
                    EmployeeActions.update(id, name, empSalary);
                } else if (!name.isEmpty()) {
                    EmployeeActions.updateName(id, name);
                } else if (!salaryValue.isEmpty()) {
                    Integer empSalary = Integer.parseInt(salaryValue);
                    EmployeeActions.updateSalary(id, empSalary);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for salary: " + e.getMessage());
        }
        clearFields();
        populateTable();
    }

    @FXML
    void initialize() {
        assert EmpID != null : "fx:id=\"EmpID\" was not injected: check your FXML file 'Employee.fxml'.";
        assert EmployeeTab != null : "fx:id=\"EmployeeTab\" was not injected: check your FXML file 'Employee.fxml'.";
        assert Name != null : "fx:id=\"Name\" was not injected: check your FXML file 'Employee.fxml'.";
        assert Salary != null : "fx:id=\"Salary\" was not injected: check your FXML file 'Employee.fxml'.";
        assert employeeID != null : "fx:id=\"employeeID\" was not injected: check your FXML file 'Employee.fxml'.";
        assert employeeName != null : "fx:id=\"employeeName\" was not injected: check your FXML file 'Employee.fxml'.";
        assert salary != null : "fx:id=\"salary\" was not injected: check your FXML file 'Employee.fxml'.";
        EmpID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        Salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        populateTable();
    }

    private void populateTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            ObservableList<EmployeeFrame> employees = FXCollections.observableArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    Integer salary = Integer.parseInt(parts[2].trim());
                    employees.add(new EmployeeFrame(id, name, salary));
                }
            }
            EmployeeTab.setItems(employees);
        } catch (IOException e) {
            System.out.println("An error occurred while populating the table: " + e.getMessage());
        }
    }

    private void clearFields() {
        employeeID.clear();
        employeeName.clear();
        salary.clear();
    }
}
