package com.example.fetchapi.model;

public class Cart {
    private int id;
    private String name;
    private String brand;
    private String category;
    private String quantity;

    public Cart(String brand, String category, int id, String name, String quantity) {
        this.brand = brand;
        this.category = category;
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Cart() {

    }

    public Cart(String name, String brand, String category, String s) {
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.quantity = s;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
