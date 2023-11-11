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
import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Delivery {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox deliveredActions;

    @FXML
    private CheckBox deliveredStatus;

    @FXML
    private Text deliveredText;

    @FXML
    private Text deliveredText1;

    @FXML
    private TextField deliveryPerson;

    @FXML
    private TextField orderIDAction;

    @FXML
    private TextField orderIDStatus;

    @FXML
    private TableView<DeliveryFrame> deliveryTable;

    @FXML
    private TableColumn<DeliveryFrame, String> tabPerson;

    @FXML
    private TableColumn<DeliveryFrame, String> tabStatus;

    @FXML
    private TableColumn<DeliveryFrame, String> taborderId;

    @FXML
    void assign(ActionEvent event) {
        if (!Objects.equals(orderIDAction.getText(), "") && !Objects.equals(deliveryPerson.getText(), "")) {
            String status = deliveredActions.isSelected() ? "Delivered" : "Not Delivered";
            DeliveryActions.assignDelivery(orderIDAction.getText(), deliveryPerson.getText(), status);
            orderIDAction.setText("");
            deliveryPerson.setText("");
            deliveredActions.setSelected(false);
        }
        populateTable();
        deliveredText.setVisible(false);
        deliveredText1.setVisible(false);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Dashboard.fxml");
    }

    @FXML
    void remove(ActionEvent event) throws IOException{
        if(orderIDAction.getText()!=null){
            DeliveryActions.deleteDelivery(orderIDAction.getText());
            orderIDAction.setText("");
        }
        deliveredText.setVisible(false);
        deliveredText1.setVisible(false);
        populateTable();
    }

    @FXML
    void checkStatus(ActionEvent event) {
        if (orderIDStatus.getText() != null) {
            String status = DeliveryActions.deliveryStatus(orderIDStatus.getText()) ? "Delivered" : "Not Delivered";
            if (status.equals("Delivered")) {
                deliveredText.setVisible(true);
                deliveredText1.setVisible(false);
            } else {
                deliveredText.setVisible(false);
                deliveredText1.setVisible(true);
            }
        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        String status = deliveredStatus.isSelected() ? "Delivered" : "Not Delivered";
        DeliveryActions.setStatus(orderIDStatus.getText(), status);
        deliveredText.setVisible(false);
        deliveredText1.setVisible(false);
        populateTable();
    }

    @FXML
    void initialize() {
        assert deliveredActions != null : "fx:id=\"deliveredActions\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert deliveredStatus != null : "fx:id=\"deliveredStatus\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert deliveredText != null : "fx:id=\"deliveredText\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert deliveredText1 != null : "fx:id=\"deliveredText1\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert deliveryPerson != null : "fx:id=\"deliveryPerson\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert orderIDAction != null : "fx:id=\"orderIDAction\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert orderIDStatus != null : "fx:id=\"orderIDStatus\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert tabPerson != null : "fx:id=\"tabPerson\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert tabStatus != null : "fx:id=\"tabStatus\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert taborderId != null : "fx:id=\"taborderId\" was not injected: check your FXML file 'Delivery.fxml'.";
        assert deliveryTable != null : "fx:id=\"deliveryTable\" was not injected: check your FXML file 'Delivery.fxml'.";
        taborderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        tabPerson.setCellValueFactory(new PropertyValueFactory<>("DeliveryPerson"));
        tabStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        populateTable();
    }
    private void populateTable() {
        ObservableList<DeliveryFrame> deliveryList = FXCollections.observableArrayList();
        List<DeliveryFrame> loadedDeliveryList = DeliveryActions.loadDeliveryHistory();
        if (loadedDeliveryList != null) {
            deliveryList.addAll(loadedDeliveryList);
        }
        deliveryTable.setItems(deliveryList);
    }
}
