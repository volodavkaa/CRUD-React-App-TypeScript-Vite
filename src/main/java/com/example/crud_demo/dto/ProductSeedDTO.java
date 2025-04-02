package com.example.crud_demo.dto;

public class ProductSeedDTO {
    private String name;
    private String description;
    private double price;
    private String categoryName;
    private String imageUrl;

    public ProductSeedDTO() {
    }

    public ProductSeedDTO(String name, String description, double price, String categoryName, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
