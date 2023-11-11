/**
 * Sample Skeleton for 'Inventory.fxml' Controller Class
 */

package com.example.jfk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Inventory {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField item;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity; 

    @FXML
    private TableView<InventoryItem> InventoryTable;

    @FXML
    private TableColumn<InventoryItem, String> itemTab;

    @FXML
    private TableColumn<InventoryItem, Integer> quantityTab;

    @FXML
    private TableColumn<InventoryItem, Integer> priceTab;

    @FXML
    void add(ActionEvent event) {
        if(item.getText()!="" && quantity.getText()!="" && price.getText()!=""){
            InventoryActions.addItem(item.getText(),Integer.parseInt(price.getText()),Integer.parseInt(quantity.getText()));
        }
        price.setText("");
        quantity.setText("");
        item.setText("");
        populateInventoryTable();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Dashboard.fxml");
    }

    @FXML
    void remove(ActionEvent event) {
        if(item.getText()!=""){
            InventoryActions.removeItem(item.getText());
        }
        item.setText("");
        price.setText("");
        quantity.setText("");
        populateInventoryTable();
    }

    @FXML
    void update(ActionEvent event) {
        if(item.getText()!=""){
            if(!quantity.getText().equals("") && !price.getText().equals("")){
               InventoryActions.update(item.getText(),Integer.parseInt(price.getText()),Integer.parseInt(quantity.getText()));
            }else if(!quantity.getText().equals("")){
                InventoryActions.updateItemQuantity(item.getText(),Integer.parseInt(quantity.getText()));
            }else if(!price.getText().equals("")){
                InventoryActions.updateItemPrice(item.getText(),Integer.parseInt(price.getText()));
            }
        }
        populateInventoryTable();
        item.setText("");
        price.setText("");
        quantity.setText("");
    }

    @FXML
    void initialize() {
        assert item != null : "fx:id=\"item\" was not injected: check your FXML file 'Inventory.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'Inventory.fxml'.";
        assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'Inventory.fxml'.";

        itemTab.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityTab.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceTab.setCellValueFactory(new PropertyValueFactory<>("price"));
        populateInventoryTable();
    }

    void populateInventoryTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader("../data/Inventory.txt"))) {
            ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String itemName = parts[0].trim();
                    Integer price = Integer.parseInt(parts[1].trim());
                    Integer quantity = Integer.parseInt(parts[2].trim());
                    inventoryItems.add(new InventoryItem(itemName, price, quantity));
                }
            }
            InventoryTable.setItems(inventoryItems);
            System.out.println("Inventory table populated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while populating the inventory table: " + e.getMessage());
        }
    }
}
