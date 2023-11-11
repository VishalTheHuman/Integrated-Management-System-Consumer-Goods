package com.example.jfk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryActions {
    private static final String FILE_PATH = "../data/Delivery_Data.txt";

    public static void assignDelivery(String orderId, String deliveryPerson, String status) {
        DeliveryFrame delivery = new DeliveryFrame(orderId, deliveryPerson, status);
        saveDelivery(delivery);
        System.out.println("Delivery added successfully.");
    }

    public static void setStatus(String orderId, String status) {
        DeliveryFrame delivery = findDelivery(orderId);
        if (delivery != null) {
            delivery.setStatus(status);
            updateDelivery(delivery);
            System.out.println("Delivery status updated.");
        } else {
            System.out.println("Delivery not found.");
        }
    }

    public static void deleteDelivery(String orderId) {
        DeliveryFrame delivery = findDelivery(orderId);
        if (delivery != null) {
            deleteDelivery(delivery);
            System.out.println("Delivery deleted successfully.");
        } else {
            System.out.println("Delivery not found.");
        }
    }

    public static boolean deliveryStatus(String orderId) {
        DeliveryFrame delivery = findDelivery(orderId);
        if (delivery != null) {
            String status = delivery.getStatus().toLowerCase();
            System.out.println("Delivery Status: " + status);
            return status.equals("Delivered") || status.toLowerCase().equals("true");
        } else {
            System.out.println("Delivery not found.");
            return false;
        }
    }

    public static void clearTable() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("");
            System.out.println("Delivery history table cleared successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing delivery history table: " + e.getMessage());
        }
    }

    private static void saveDelivery(DeliveryFrame delivery) {
        List<DeliveryFrame> deliveryList = loadDeliveryHistory();
        if (deliveryList != null && !isOrderIdExists(delivery.getOrderId(), deliveryList)) {
            deliveryList.add(delivery);
            saveDeliveryHistory(deliveryList);
            System.out.println("Delivery saved successfully.");
        } else {
            System.out.println("Delivery with the same order ID already exists.");
        }
    }

    private static void updateDelivery(DeliveryFrame delivery) {
        List<DeliveryFrame> deliveryList = loadDeliveryHistory();
        if (deliveryList != null) {
            for (int i = 0; i < deliveryList.size(); i++) {
                if (deliveryList.get(i).getOrderId().equals(delivery.getOrderId())) {
                    deliveryList.set(i, delivery);
                    break;
                }
            }
            saveDeliveryHistory(deliveryList);
        }
    }

    private static void deleteDelivery(DeliveryFrame delivery) {
        List<DeliveryFrame> deliveryList = loadDeliveryHistory();
        if (deliveryList != null) {
            deliveryList.removeIf(d -> d.getOrderId().equals(delivery.getOrderId()));
            saveDeliveryHistory(deliveryList);
        }
    }

    private static DeliveryFrame findDelivery(String orderId) {
        List<DeliveryFrame> deliveryList = loadDeliveryHistory();
        if (deliveryList != null) {
            for (DeliveryFrame delivery : deliveryList) {
                if (delivery.getOrderId().equals(orderId)) {
                    return delivery;
                }
            }
        }
        return null;
    }

    static List<DeliveryFrame> loadDeliveryHistory() {
        List<DeliveryFrame> deliveryList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DeliveryFrame delivery = DeliveryFrame.fromText(line);
                deliveryList.add(delivery);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading delivery history: " + e.getMessage());
        }
        return deliveryList;
    }

    private static void saveDeliveryHistory(List<DeliveryFrame> deliveryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (DeliveryFrame delivery : deliveryList) {
                writer.write(delivery.toText());
                writer.newLine();
            }
            System.out.println("Delivery history saved successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving delivery history: " + e.getMessage());
        }
    }

    private static boolean isOrderIdExists(String orderId, List<DeliveryFrame> deliveryList) {
        for (DeliveryFrame delivery : deliveryList) {
            if (delivery.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
}
