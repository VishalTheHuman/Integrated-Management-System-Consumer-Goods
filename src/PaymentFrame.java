package com.example.jfk;


public class PaymentFrame {
    String orderId;
    double totalAmount;
    boolean status;

    PaymentFrame(String orderId,double totalAmount,boolean status){
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    PaymentFrame(double totalAmount,String orderId,boolean status){
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}