package com.example.jfk;

import java.io.*;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.List;

public class EmployeeActions {
    private static final String EMPLOYEE_FILE_PATH = "../data/Employee_Data.txt";
    public static void addEmployee(String empID, String Name, Integer salary) {
        List<String> lines = new ArrayList<>();
        boolean itemExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(empID)) {
                    itemExists = true;
                    break;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the employee file.");
            return;
        }

        if (itemExists) {
            System.out.println("Employee already exists in the employee.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH, true))) {
            String line = empID + "," + Name + "," + salary;
            writer.write(line);
            writer.newLine();
            System.out.println("Employee added to the Employee File.");
        } catch (IOException e) {
            System.out.println("Failed to add the employee to the Employee File.");
        }
    }


    public static void removeEmployee(String empID) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(empID)) {
                    continue; // Skip the line for the item to be removed
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the employee file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Employee removed from the employee file.");
        } catch (IOException e) {
            System.out.println("Failed to update the employee file.");
        }
    }

    public static void updateName(String empID, String newName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(empID)) {
                    line = parts[0] + "," + newName + "," + parts[2];
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the employee file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Employee updated in the employee File.");
        } catch (IOException e) {
            System.out.println("Failed to update the employee file.");
        }
    }

    public static void update(String empID, String name, Integer Salary) {
        // Call the necessary methods to update the inventory
        updateName(empID, name);
        updateSalary(empID, Salary);

        System.out.println("Employee updated successfully.");
    }

    public static void updateSalary(String empID, Integer salary) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(empID)) {
                    line = parts[0] + "," + parts[1] + "," + salary;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the employee file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Employee updated in the employee file.");
        } catch (IOException e) {
            System.out.println("Failed to update the employee file.");
        }
    }
}
