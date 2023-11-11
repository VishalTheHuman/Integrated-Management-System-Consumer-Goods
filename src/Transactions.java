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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Transactions {

    @FXML
    private TableColumn<Transaction, Double> Amount;

    @FXML
    private TableColumn<Transaction, String> OrderID;

    @FXML
    private TableColumn<Transaction, Boolean> Status;

    @FXML
    public TableView<Transaction> TranTab;

    @FXML
    private TextField amount;

    @FXML
    private TextField orderID;

    @FXML
    private CheckBox paid;

    @FXML
    void add(ActionEvent event) {
        if (!amount.getText().isEmpty() && !orderID.getText().isEmpty()) {
            TransactionHandler.addTransaction(new Transaction(orderID.getText(), Double.parseDouble(amount.getText()), paid.isSelected()));
        }
        amount.setText("");
        orderID.setText("");
        paid.setSelected(false);
        populateInventoryTable();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Admin.fxml");
    }

    @FXML
    void remove(ActionEvent event) {
        TransactionHandler.removeTransaction(orderID.getText());
        amount.setText("");
        orderID.setText("");
        paid.setSelected(false);
        populateInventoryTable();
    }

    @FXML
    void update(ActionEvent event) {
        TransactionHandler.updateTransaction(orderID.getText(), Double.parseDouble(amount.getText()), paid.isSelected());
        amount.setText("");
        orderID.setText("");
        paid.setSelected(false);
        populateInventoryTable();
    }

    @FXML
    void initialize() {
        assert Amount != null : "fx:id=\"Amount\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert OrderID != null : "fx:id=\"OrderID\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert Status != null : "fx:id=\"Status\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert TranTab != null : "fx:id=\"TranTab\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert amount != null : "fx:id=\"amount\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert orderID != null : "fx:id=\"orderID\" was not injected: check your FXML file 'Transactions.fxml'.";
        assert paid != null : "fx:id=\"paid\" was not injected: check your FXML file 'Transactions.fxml'.";

        OrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        Status.setCellValueFactory(new PropertyValueFactory<>("Paid"));

        populateInventoryTable();
    }

    void populateInventoryTable() {
        try (BufferedReader reader = new BufferedReader(new FileReader("../data/Transactions.txt"))) {
            ObservableList<Transaction> transactionItems = FXCollections.observableArrayList();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String orderID = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    boolean paid = Boolean.parseBoolean(parts[2].trim());
                    transactionItems.add(new Transaction(orderID, amount, paid));
                }
            }
            TranTab.setItems(transactionItems);
            System.out.println("Inventory table populated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while populating the inventory table: " + e.getMessage());
        }
    }
}
