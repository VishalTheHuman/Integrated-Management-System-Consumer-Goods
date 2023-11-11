package com.example.jfk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryActions {
    private static final String INVENTORY_FILE_PATH = "../data/Inventory.txt";
    public static void addItem(String itemName, Integer price, int quantity) {
        List<String> lines = new ArrayList<>();
        boolean itemExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    itemExists = true;
                    break;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
            return;
        }

        if (itemExists) {
            System.out.println("Item already exists in the inventory.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH, true))) {
            String line = itemName + "," + price + "," + quantity;
            writer.write(line);
            writer.newLine();
            System.out.println("Item added to the inventory.");
        } catch (IOException e) {
            System.out.println("Failed to add the item to the inventory.");
        }
    }


    public static void removeItem(String itemName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    continue;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Item removed from the inventory.");
        } catch (IOException e) {
            System.out.println("Failed to update the inventory file.");
        }
    }

    public static void updateItemPrice(String itemName, Integer newPrice) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    line = parts[0] + "," + newPrice + "," + parts[2];
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Item price updated in the inventory.");
        } catch (IOException e) {
            System.out.println("Failed to update the inventory file.");
        }
    }

    public static void update(String name, Integer price, Integer quantity) {
        updateItemPrice(name, price);
        updateItemQuantity(name, quantity);
        System.out.println("Inventory updated successfully.");
    }

    public static void updateItemQuantity(String itemName, Integer newQuantity) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    line = parts[0] + "," + parts[1] + "," + newQuantity;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Item quantity updated in the inventory.");
        } catch (IOException e) {
            System.out.println("Failed to update the inventory file.");
        }
    }

    public static boolean checkAvailability(String itemName, Integer quantity) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    int availableQuantity = Integer.parseInt(parts[2].trim());
                    return availableQuantity >= quantity;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
        }

        return false;
    }

    public static Integer getItemPrice(String itemName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equalsIgnoreCase(itemName)) {
                    return Integer.parseInt(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read the inventory file.");
        }

        return 0;
    }
}
