package com.example.fetchapi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("sku_id")
    private int id;

    @SerializedName("sku_name")
    private String name;

    @SerializedName("sku_code")
    private String sku;

    private String brand;
    private String category;
    private String description;

    @SerializedName("ptr")
    private double priceToRetailer;

    @SerializedName("tax_name")
    private String gst;

    private String quantity = "0";
    private int image;

    public Product(String brand, String category, String description, String gst, int id, String name, double priceToRetailer, String sku) {
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.gst = gst;
        this.id = id;
        this.name = name;
        this.priceToRetailer = priceToRetailer;
        this.sku = sku;
        this.image = 0;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
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

    public double getPriceToRetailer() {
        return priceToRetailer;
    }

    public void setPriceToRetailer(double priceToRetailer) {
        this.priceToRetailer = priceToRetailer;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }


    public Product() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
