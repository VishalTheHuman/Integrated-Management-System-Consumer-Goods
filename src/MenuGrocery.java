package com.example.jfk;

public class MenuGrocery {
    private String item;
    private double price;

    public MenuGrocery(String item, double price) {
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
