package com.example.jfk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static final String CANTEEN_FILE_PATH = "../data/Canteen_Menu.txt";
    private static final String GROCERY_FILE_PATH = "../data/Grocery_Menu.txt";

    @FXML
    private CheckBox canteenChk;

    @FXML
    private CheckBox groceryChk;

    @FXML
    private TextField itemName;

    @FXML
    private TextField price;

    @FXML
    private Text invalid;

    @FXML
    private TableView<MenuItem> canteenMenuTable;

    @FXML
    private TableColumn<MenuItem, String> canteenItem;

    @FXML
    private TableColumn<MenuItem, Integer> canteenPrice;

    @FXML
    private TableView<MenuItem> groceryMenuTable;

    @FXML
    private TableColumn<MenuItem, String> groceryItem;

    @FXML
    private TableColumn<MenuItem, Integer> groceryPrice;

    private ObservableList<MenuItem> canteenItems;
    private ObservableList<MenuItem> groceryItems;

    @FXML
    void add(ActionEvent event) {
        if (!itemName.getText().isEmpty() && !price.getText().isEmpty()) {
            int itemPrice;
            try {
                itemPrice = Integer.parseInt(price.getText());
            } catch (NumberFormatException e) {
                invalid.setVisible(true);
                return;
            }
            if (canteenChk.isSelected()) {
                if (!menuFileContainsItem(CANTEEN_FILE_PATH, itemName.getText())) {
                    MenuItem menuItem = new MenuItem(itemName.getText(), itemPrice);
                    saveMenuItem(CANTEEN_FILE_PATH, menuItem);
                    canteenItems.add(menuItem);
                    invalid.setVisible(false);
                } else {
                    invalid.setVisible(true);
                    return;
                }
            }
            if (groceryChk.isSelected()) {
                if (!menuFileContainsItem(GROCERY_FILE_PATH, itemName.getText())) {
                    MenuItem menuItem = new MenuItem(itemName.getText(), itemPrice);
                    saveMenuItem(GROCERY_FILE_PATH, menuItem);
                    groceryItems.add(menuItem);
                    invalid.setVisible(false);
                } else {
                    invalid.setVisible(true);
                }
            }
        } else {
            invalid.setVisible(true);
        }
        populateCanteenMenuTable();
        populateGroceryMenuTable();
        clearInputFields();
    }

    @FXML
    void remove(ActionEvent event) {
        if (!itemName.getText().isEmpty()) {
            if (canteenChk.isSelected()) {
                removeMenuItem(CANTEEN_FILE_PATH, itemName.getText());
                canteenItems.removeIf(item -> item.getName().equals(itemName.getText()));
            }
            if (groceryChk.isSelected()) {
                removeMenuItem(GROCERY_FILE_PATH, itemName.getText());
                groceryItems.removeIf(item -> item.getName().equals(itemName.getText()));
            }
        } else {
            invalid.setVisible(true);
        }
        populateCanteenMenuTable();
        populateGroceryMenuTable();
        clearInputFields();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Main main = new Main();
            main.changeScene("../fxml/Dashboard.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getPrice(ActionEvent event) {
        if (!itemName.getText().isEmpty()) {
            if (canteenChk.isSelected()) {
                int price = (int) getItemPrice(CANTEEN_FILE_PATH, itemName.getText());
                if (price != -1) {
                    this.price.setText(String.valueOf(price));
                    invalid.setVisible(false);
                    return;
                }
            }
            if (groceryChk.isSelected()) {
                int price = (int) getItemPrice(GROCERY_FILE_PATH, itemName.getText());
                if (price != -1) {
                    this.price.setText(String.valueOf(price));
                    invalid.setVisible(false);
                    return;
                }
            }
            invalid.setVisible(true);
        }
    }
    @FXML
    void slot(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Slot.fxml");
    }

    @FXML
    void update(ActionEvent event) {
        if (!itemName.getText().isEmpty() && !price.getText().isEmpty()) {
            int itemPrice;
            try {
                itemPrice = Integer.parseInt(price.getText());
            } catch (NumberFormatException e) {
                invalid.setVisible(true);
                return;
            }
            if (canteenChk.isSelected()) {
                if (updateMenuItem(CANTEEN_FILE_PATH, itemName.getText(), itemPrice)) {
                    canteenItems.forEach(item -> {
                        if (item.getName().equals(itemName.getText())) {
                            item.setPrice(itemPrice);
                        }
                    });
                    invalid.setVisible(false);
                } else {
                    invalid.setVisible(true);
                }
            }
            if (groceryChk.isSelected()) {
                if (updateMenuItem(GROCERY_FILE_PATH, itemName.getText(), itemPrice)) {
                    groceryItems.forEach(item -> {
                        if (item.getName().equals(itemName.getText())) {
                            item.setPrice(itemPrice);
                        }
                    });
                    invalid.setVisible(false);
                } else {
                    invalid.setVisible(true);
                }
            }
        } else {
            invalid.setVisible(true);
        }
        populateCanteenMenuTable();
        populateGroceryMenuTable();
        clearInputFields();
    }

    private void clearInputFields() {
        itemName.setText("");
        price.setText("");
        canteenChk.setSelected(false);
        groceryChk.setSelected(false);
    }

    @FXML
    void initialize() {
        canteenItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        canteenPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        groceryItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        groceryPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        canteenItems = FXCollections.observableArrayList();
        groceryItems = FXCollections.observableArrayList();

        populateCanteenMenuTable();
        populateGroceryMenuTable();

        canteenMenuTable.setItems(canteenItems);
        groceryMenuTable.setItems(groceryItems);
    }

    private void populateCanteenMenuTable() {
        List<MenuItem> canteenMenuItems = loadMenuItemsFromFile(CANTEEN_FILE_PATH);
        canteenItems.clear();
        canteenItems.addAll(canteenMenuItems);
    }

    private void populateGroceryMenuTable() {
        List<MenuItem> groceryMenuItems = loadMenuItemsFromFile(GROCERY_FILE_PATH);
        groceryItems.clear();
        groceryItems.addAll(groceryMenuItems);
    }

    private List<MenuItem> loadMenuItemsFromFile(String filePath) {
        List<MenuItem> menuItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim()); // Parse price as double
                    MenuItem menuItem = new MenuItem(name, price);
                    menuItems.add(menuItem);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading menu items: " + e.getMessage());
        }
        return menuItems;
    }


    private void saveMenuItem(String filePath, MenuItem menuItem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(menuItem.getName() + "," + menuItem.getPrice());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving menu item: " + e.getMessage());
        }
    }

    private boolean menuFileContainsItem(String filePath, String itemName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    if (name.equalsIgnoreCase(itemName)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while searching for menu item: " + e.getMessage());
        }
        return false;
    }

    private void removeMenuItem(String filePath, String itemName) {
        List<MenuItem> menuItems = loadMenuItemsFromFile(filePath);
        menuItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        saveMenuItemsToFile(filePath, menuItems);
    }

    private boolean updateMenuItem(String filePath, String itemName, int price) {
        List<MenuItem> menuItems = loadMenuItemsFromFile(filePath);
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.setPrice(price);
                saveMenuItemsToFile(filePath, menuItems);
                return true;
            }
        }
        return false;
    }

    private void saveMenuItemsToFile(String filePath, List<MenuItem> menuItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (MenuItem menuItem : menuItems) {
                writer.write(menuItem.getName() + "," + menuItem.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving menu items: " + e.getMessage());
        }
    }

    private double getItemPrice(String filePath, String itemName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    if (name.equalsIgnoreCase(itemName)) {
                        return Double.parseDouble(parts[1].trim()); // Parse price as double
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while searching for menu item: " + e.getMessage());
        }
        return -1.0; // Return -1.0 if item price is not found
    }
}
