/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package com.example.jfk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    public URL location;

    @FXML
    public Text invalid;

    @FXML
    public Button loginButton;

    @FXML
    public PasswordField password;

    @FXML
    public TextField username;

    @FXML
    void loginCheck(ActionEvent event) throws IOException {
        Main m = new Main();
        loginIDandPassword check = new loginIDandPassword();
        String usernameTxt = username.getText();
        String passwordTxt = password.getText();
        System.out.println("**************************");
        System.out.println("Username: " + usernameTxt);
        System.out.println("Password: " + passwordTxt);
        System.out.println("**************************");
        if (check.passwordMatch(usernameTxt, passwordTxt)) {
            System.out.println("Matched");
            invalid.setVisible(false);
            m.changeScene("../fxml/Dashboard.fxml");
        } else {
            invalid.setVisible(true);
            System.out.println("Not Matched");
            username.setText("");
            password.setText("");
        }
    }

    @FXML
    void initialize() {
        assert invalid != null : "fx:id=\"invalid\" was not injected: check your FXML file 'Login.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Login.fxml'.";
    }
}
