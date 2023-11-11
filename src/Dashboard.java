/**
 * Sample Skeleton for 'Dashboard.fxml' Controller Class
 */

package com.example.jfk;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class Dashboard {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    void adminClick(MouseEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Admin.fxml");
    }

    @FXML
    void canteenClick(MouseEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Canteen.fxml");
    }

    @FXML
    void deliveryClick(MouseEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Delivery.fxml");
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void groceryClick(MouseEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Grocery.fxml");
    }

    @FXML
    void inventoryClick(MouseEvent event) throws IOException {
        Main m= new Main();
        m.changeScene("../fxml/Inventory.fxml");
    }

    @FXML
    void menuClick(MouseEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Menu.fxml");
    }

    @FXML
    void signOut(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Login.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
