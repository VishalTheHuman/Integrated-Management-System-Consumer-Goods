package com.example.jfk;

public class DeliveryFrame {
    private String orderId;
    private String deliveryPerson;
    private String status;

    public DeliveryFrame(String orderId, String deliveryPerson, String status) {
        this.orderId = orderId;
        this.deliveryPerson = deliveryPerson;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toText() {
        return orderId + "," + deliveryPerson + "," + status;
    }

    public static DeliveryFrame fromText(String text) {
        String[] parts = text.split(",");
        String orderId = parts[0];
        String deliveryPerson = parts[1];
        String status = parts[2];
        return new DeliveryFrame(orderId, deliveryPerson, status);
    }
}
