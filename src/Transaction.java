package com.example.jfk;

import javafx.scene.control.TextField;

public class Transaction {
    private String orderID;
    private Double amount;
    private Boolean paid;

    public Transaction(String orderID, Double amount, Boolean paid) {
        this.orderID = orderID;
        this.amount = amount;
        this.paid = paid;
    }
    public Transaction(TextField orderID, TextField amount, Boolean paid) {
        this.orderID = orderID.getText();
        this.amount = Double.parseDouble(amount.getText());
        this.paid = paid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
