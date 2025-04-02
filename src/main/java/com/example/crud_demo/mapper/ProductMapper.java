package com.example.crud_demo.mapper;

import com.example.crud_demo.dto.ProductSeedDTO;
import com.example.crud_demo.model.Category;
import com.example.crud_demo.model.Product;

public class ProductMapper {
    public static Product toEntity(ProductSeedDTO dto, Category category, String localImagePath) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImagePath(localImagePath);
        product.setCategory(category);
        return product;
    }
}
