package com.example.jfk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class loginIDandPassword {
    public static final String LOGIN_FILE_PATH = "../data/LoginData.txt";

    public boolean doesExist(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    System.out.println("User is Found");
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while checking user existence: " + e.getMessage());
        }

        System.out.println("User is not Found");
        return false;
    }

    public boolean passwordMatch(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    String storedPassword = parts[1].trim();
                    if (storedPassword.equals(password)) {
                        System.out.println("Password Matches");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while checking password: " + e.getMessage());
        }
        System.out.println("Password doesn't Match");
        return false;
    }

    public static void addUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH, true))) {
            String line = username + "," + password;
            writer.write(line);
            writer.newLine();
            System.out.println("User added successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding user: " + e.getMessage());
        }
    }

    public static boolean updatePassword(String username, String newPassword) {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    line = parts[0] + "," + newPassword;
                    userFound = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating password: " + e.getMessage());
            return false;
        }

        if (!userFound) {
            System.out.println("No user found with the specified username.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Password updated successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while updating password: " + e.getMessage());
            return false;
        }
    }

    public static boolean removeUser(String username) {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    userFound = true;
                    continue;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while removing user: " + e.getMessage());
            return false;
        }

        if (!userFound) {
            System.out.println("No user found with the specified username.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Username removed successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while removing user: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateUserID(String username, String newUserID) {
        List<String> lines = new ArrayList<>();
        boolean userFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    line = newUserID + "," + parts[1];
                    userFound = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating user ID: " + e.getMessage());
            return false;
        }

        if (!userFound) {
            System.out.println("No user found with the specified username.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOGIN_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("User ID updated successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while updating user ID: " + e.getMessage());
            return false;
        }
    }
}
