package com.example.jfk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final String CART_FILE_PATH = "../data/Cart.txt";

    private String item;
    private double quantity;
    private double price;

    public Cart(String item, double quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return quantity * price;
    }

    public static List<Cart> loadCartFromFile() {
        List<Cart> cartItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String itemName = parts[0].trim();
                    double itemQuantity = Double.parseDouble(parts[1].trim());
                    double itemPrice = Double.parseDouble(parts[2].trim());
                    Cart cartItem = new Cart(itemName, itemQuantity, itemPrice);
                    cartItems.add(cartItem);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading cart items: " + e.getMessage());
        }
        return cartItems;
    }

    public static void saveCartToFile(List<Cart> cartItems) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE_PATH))) {
            for (Cart cartItem : cartItems) {
                writer.write(cartItem.getItem() + "," + cartItem.getQuantity() + "," + cartItem.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving cart items: " + e.getMessage());
        }
    }
}
