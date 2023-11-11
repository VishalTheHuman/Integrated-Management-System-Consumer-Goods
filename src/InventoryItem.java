package com.example.jfk;

public class InventoryItem {
    private String item;
    private Integer price;
    private Integer quantity;

    public InventoryItem(String item, Integer price, Integer quantity) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
