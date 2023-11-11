/**
 * Sample Skeleton for 'Grocery.fxml' Controller Class
 */

package com.example.jfk;

import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Grocery {
    private static final String CART_FILE_PATH = "../data/Cart.txt";
    private static final String GROCERY_FILE_PATH = "../data/Grocery_Menu.txt";

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="CartTab"
    private TableView<Cart> CartTab; // Value injected by FXMLLoader

    @FXML // fx:id="amount"
    private TextField amount; // Value injected by FXMLLoader

    @FXML // fx:id="delivery"
    private CheckBox delivery; // Value injected by FXMLLoader

    @FXML // fx:id="deliveryPerson"
    private TextField deliveryPerson; // Value injected by FXMLLoader

    @FXML // fx:id="item"
    private TextField item; // Value injected by FXMLLoader

    @FXML // fx:id="itemCart"
    private TableColumn<Cart, String> itemCart; // Value injected by FXMLLoader

    @FXML // fx:id="itemMenu"
    private TableColumn<MenuGrocery, String> itemMenu; // Value injected by FXMLLoader

    @FXML // fx:id="method"
    private TextField method; // Value injected by FXMLLoader

    @FXML // fx:id="orderId"
    private TextField orderId; // Value injected by FXMLLoader

    @FXML // fx:id="price"
    private TextField price; // Value injected by FXMLLoader

    @FXML // fx:id="priceCart"
    private TableColumn<Cart, Double> priceCart; // Value injected by FXMLLoader

    @FXML // fx:id="priceMenu"
    private TableColumn<MenuGrocery, Double> priceMenu;

    @FXML // fx:id="menuTab"
    private TableView<MenuGrocery> menuTab; // Value injected by FXMLLoader

    @FXML // fx:id="quantity"
    private TextField quantity; // Value injected by FXMLLoader

    @FXML // fx:id="quantityCart"
    private TableColumn<Cart, Double> quantityCart; // Value injected by FXMLLoader

    @FXML // fx:id="status"
    private TextField status; // Value injected by FXMLLoader

    @FXML
    private RadioButton cash;

    @FXML
    private RadioButton upi;

    @FXML
    private RadioButton ewallet;
    @FXML
    private Label selectOneOption;

    public ObservableList<MenuGrocery> groceryItems = FXCollections.observableArrayList();

    private ObservableList<Cart> cartItems = FXCollections.observableArrayList();
    @FXML
    void add(ActionEvent event) {
        String itemName = item.getText();
        double itemQuantity = Double.parseDouble(quantity.getText());
        double itemPrice = Double.parseDouble(price.getText());

        // Check if the item already exists in the cart
        boolean itemExists = false;
        for (Cart cartItem : cartItems) {
            if (cartItem.getItem().equals(itemName)) {
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            Cart cartItem = new Cart(itemName, itemQuantity, itemPrice);
            cartItems.add(cartItem);
            saveCartToFile();
        } else {
            // Item already exists in the cart, display a message or handle it as needed
            System.out.println("Item already exists in the cart.");
        }
        item.setText("");
        price.setText("");
        quantity.setText("");
        calculateTotalPrice();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("../fxml/Dashboard.fxml");
    }

    @FXML
    void checkStatus(ActionEvent event) {
        if(!orderId.getText().equals("")){
            if(DeliveryActions.deliveryStatus(orderId.getText())){
                status.setText("Yes");
            }else{
                status.setText("No");
            }
        }else{
            status.setText("No");
        }
    }

    @FXML
    void payButton(ActionEvent event) {
        if (upi.isSelected() && cash.isSelected() && ewallet.isSelected()) {
            selectOneOption.setVisible(true);
        } else if (upi.isSelected() && cash.isSelected()) {
            selectOneOption.setVisible(true);
        } else if (cash.isSelected() && ewallet.isSelected()) {
            selectOneOption.setVisible(true);
        } else if (ewallet.isSelected() && upi.isSelected()) {
            selectOneOption.setVisible(true);
        } else {
            selectOneOption.setVisible(false);
            if (upi.isSelected()) {
                showUPIConfirmation();
            }
            if (ewallet.isSelected()) {
                showEwalletConfirmation();
            }
            if (!orderId.getText().equals("")) {
                TransactionHandler.addTransaction(new Transaction(orderId.getText(), Double.parseDouble(amount.getText()), true));
                if (delivery.isSelected() && !deliveryPerson.getText().equals("")) {
                    DeliveryActions.assignDelivery(orderId.getText(), deliveryPerson.getText(), String.valueOf(false));
                }
                orderId.setText("");
                amount.setText("");
                deliveryPerson.setText("");
                clearCartFile();
                populateCartTable();
            }
        }

    }
    private static boolean isConfirmationShown = false;
    private void showUPIConfirmation() {
        if (!isConfirmationShown) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("UPI Payment Confirmation");
            alert.setHeaderText(null);
            DialogPane dialogPane = new DialogPane();
            ImageView imageView = new ImageView(new Image("../assets/UPI.png"));
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            dialogPane.setContent(imageView);
            alert.setDialogPane(dialogPane);
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(closeButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == closeButton) {
                isConfirmationShown = true;  // Update the flag
                System.out.println("UPI Pop-Up Closed");
            }
        }
    }

    private void showEwalletConfirmation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("E-wallet Login");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter your username:");

        Optional<String> usernameResult = dialog.showAndWait();

        if (usernameResult.isPresent()) {
            String username = usernameResult.get();

            Canteen.PasswordDialog passwordDialog = new Canteen.PasswordDialog();
            Optional<String> passwordResult = passwordDialog.showAndWait();

            if (passwordResult.isPresent()) {
                String password = passwordResult.get();

                if (!username.isEmpty() && !password.isEmpty()) {
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    System.out.println("Performing actions for e-wallet payment...");

                    // Rest of the code here after successful login and payment actions

                    return; // Exit the method after successful payment
                }
            }
        }

        // If the code reaches this point, either username or password is empty, or the dialog was canceled
        System.out.println("Payment canceled or login information incomplete.");
    }

    // Custom PasswordDialog class extending TextInputDialog to show a password input field
    static class PasswordDialog extends TextInputDialog {
        public PasswordDialog() {
            super();
            setTitle("E-wallet Login");
            setHeaderText(null);
            setContentText("Enter your password:");

            // Replace the default "OK" button with "Pay" button
            ButtonType payButton = new ButtonType("Pay", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().setAll(payButton);

            // Set the pay button to be disabled until both username and password fields are not empty
            Node payButtonNode = getDialogPane().lookupButton(payButton);
            payButtonNode.setDisable(true);

            // Add a listener to enable the pay button when both username and password fields are not empty
            getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                    payButtonNode.setDisable(newValue.trim().isEmpty()));
        }
    }


    @FXML
    void remove(ActionEvent event) {
        String itemName = item.getText();
        removeItemFromCart(itemName);
        saveCartToFile();
        populateCartTable();
        item.setText("");
        price.setText("");
        quantity.setText("");
        calculateTotalPrice();
    }
    void clearCartFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(CART_FILE_PATH))) {

        } catch (IOException e) {
            System.out.println("An error occurred while saving the cart items: " + e.getMessage());
        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        if(!orderId.getText().isEmpty()){
            if(status.getText().toLowerCase().equals("yes")){
                DeliveryActions.setStatus(orderId.getText(), String.valueOf(true).toLowerCase());
            }else if(status.getText().toLowerCase().equals("no")){
                DeliveryActions.setStatus(orderId.getText(), String.valueOf(false).toLowerCase());
            }
        }
        orderId.setText("");
        status.setText("");
    }
    @FXML
    void clearCart(ActionEvent event) {
        cartItems.clear();
        saveCartToFile();
    }
    private void saveCartToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CART_FILE_PATH))) {
            for (Cart cartItem : cartItems) {
                writer.println(cartItem.getItem() + "," + cartItem.getQuantity() + "," + cartItem.getPrice());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the cart items: " + e.getMessage());
        }
    }

    @FXML
    void update(ActionEvent event) {
        String itemName = item.getText();
        double itemQuantity = Double.parseDouble(quantity.getText());
        double itemPrice = Double.parseDouble(price.getText());
        updateItemInCart(itemName, itemQuantity, itemPrice);
        saveCartToFile();
        populateCartTable();
        item.setText("");
        price.setText("");
        quantity.setText("");
        calculateTotalPrice();
    }
    private void updateItemInCart(String itemName, double quantity, double price) {
        for (Cart cartItem : cartItems) {
            if (cartItem.getItem().equals(itemName)) {
                cartItem.setQuantity(quantity);
                cartItem.setPrice(price);
                break;
            }
        }
    }
    private void removeItemFromCart(String itemName) {
        List<Cart> updatedCartItems = new ArrayList<>();
        for (Cart cartItem : cartItems) {
            if (!cartItem.getItem().equals(itemName)) {
                updatedCartItems.add(cartItem);
            }
        }
        cartItems.clear();
        cartItems.addAll(updatedCartItems);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert CartTab != null : "fx:id=\"CartTab\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert amount != null : "fx:id=\"amount\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert delivery != null : "fx:id=\"delivery\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert deliveryPerson != null : "fx:id=\"deliveryPerson\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert item != null : "fx:id=\"item\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert itemCart != null : "fx:id=\"itemCart\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert itemMenu != null : "fx:id=\"itemMenu\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert method != null : "fx:id=\"method\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert orderId != null : "fx:id=\"orderId\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert priceCart != null : "fx:id=\"priceCart\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert priceMenu != null : "fx:id=\"priceMenu\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert quantityCart != null : "fx:id=\"quantityCart\" was not injected: check your FXML file 'Grocery.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'Grocery.fxml'.";

        priceMenu.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemMenu.setCellValueFactory(new PropertyValueFactory<>("item"));
        itemCart.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityCart.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCart.setCellValueFactory(new PropertyValueFactory<>("price"));

        groceryItems = FXCollections.observableArrayList();
        cartItems = FXCollections.observableArrayList();

        clearCartFile();
        populateCanteenMenuTable();
        populateCartTable();
        calculateTotalPrice();
    }
    private void populateGroceryMenuTable() {
        List<MenuGrocery> groceryMenuItems = loadMenuItemsFromFile(GROCERY_FILE_PATH);
        groceryItems.clear();
        groceryItems.addAll(groceryMenuItems);
    }

    private List<MenuGrocery> loadMenuItemsFromFile(String filePath) {
        List<MenuGrocery> menuItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim()); // Parse price as double
                    MenuGrocery menuItem = new MenuGrocery(name, price);
                    menuItems.add(menuItem);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading menu items: " + e.getMessage());
        }
        return menuItems;
    }
    private void populateCanteenMenuTable() {
        List<MenuGrocery> groceryMenuItems = loadMenuItemsFromFile(GROCERY_FILE_PATH);
        groceryItems.clear();
        groceryItems.addAll(groceryMenuItems);
        menuTab.setItems(groceryItems);
    }

    private void populateCartTable() {
        loadCartFromFile();
        CartTab.setItems(cartItems);
    }
    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Cart cartItem : cartItems) {
            double quantity = cartItem.getQuantity();
            double price = cartItem.getPrice();
            totalPrice += quantity * price;
        }
        amount.setText(Double.toString(totalPrice));
        return totalPrice;
    }



    private void loadCartFromFile() {
        cartItems.clear();
        try (Scanner scanner = new Scanner(new FileReader(CART_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double quantity = Double.parseDouble(parts[1].trim());
                    double price = Double.parseDouble(parts[2].trim());
                    Cart cartItem = new Cart(name, quantity, price);
                    cartItems.add(cartItem);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the cart items: " + e.getMessage());
        }
    }

}
