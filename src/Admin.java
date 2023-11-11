package com.example.jfk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Admin {

    @FXML
    private TextField New;

    @FXML
    private Text invalid;

    @FXML
    private TextField password;
    @FXML
    private TableView<LoginFrame> loginData;

    @FXML
    private TextField username;
    @FXML
    private TableColumn<LoginFrame, String> passwordTab;
    @FXML
    private TableColumn<LoginFrame, String> usernameTab;

    @FXML
    void addUser(ActionEvent event) {
        loginIDandPassword chk = new loginIDandPassword();
        if (chk.doesExist(username.getText())) {
            invalid.setVisible(true);
        } else {
            invalid.setVisible(false);
            chk.addUser(username.getText(), password.getText());
        }
        username.setText("");
        password.setText("");
        New.setText("");
        populateTable();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Dashboard.fxml");
    }

    @FXML
    void removeUser(ActionEvent event) {
        loginIDandPassword chk = new loginIDandPassword();
        if (chk.doesExist(username.getText())) {
            loginIDandPassword.removeUser(username.getText());
            invalid.setVisible(false);
        } else {
            invalid.setVisible(true);
        }
        username.setText("");
        password.setText("");
        New.setText("");
        populateTable();
    }

    @FXML
    void updatePassworrd(ActionEvent event) {
        loginIDandPassword chk = new loginIDandPassword();
        if (chk.doesExist(username.getText())) {
            loginIDandPassword.updatePassword(username.getText(), New.getText());
            invalid.setVisible(false);
        } else {
            invalid.setVisible(true);
        }
        username.setText("");
        password.setText("");
        New.setText("");
        populateTable();
    }

    @FXML
    void updateUsername(ActionEvent event) {
        loginIDandPassword chk = new loginIDandPassword();
        if (chk.doesExist(username.getText())) {
            loginIDandPassword.updateUserID(username.getText(), New.getText());
            invalid.setVisible(false);
        } else {
            invalid.setVisible(true);
        }
        username.setText("");
        password.setText("");
        New.setText("");
        populateTable();
    }

    @FXML
    void transactions(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Transactions.fxml");
    }

    @FXML
    void employee(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Employee.fxml");
    }

    @FXML
    void initialize() {
        assert invalid != null : "fx:id=\"invalid\" was not injected: check your FXML file 'Admin.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Admin.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Admin.fxml'.";
        assert New != null : "fx:id=\"New\" was not injected: check your FXML file 'Admin.fxml'.";

        usernameTab.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordTab.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        populateTable();
    }

    private void populateTable() {
        loginData.getItems().clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(loginIDandPassword.LOGIN_FILE_PATH))) {
            List<LoginFrame> loginFrames = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    LoginFrame loginFrame = new LoginFrame(username, password);
                    loginFrames.add(loginFrame);
                }
            }
            loginData.getItems().addAll(loginFrames);
        } catch (IOException e) {
            System.out.println("An error occurred while reading login data: " + e.getMessage());
        }
    }
}
