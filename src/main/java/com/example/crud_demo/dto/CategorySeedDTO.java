package com.example.crud_demo.dto;

public class CategorySeedDTO {
    private String name;
    private String imageUrl;

    public CategorySeedDTO() {
    }

    public CategorySeedDTO(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
