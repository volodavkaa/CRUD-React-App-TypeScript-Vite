package com.example.crud_demo.mapper;

import com.example.crud_demo.dto.CategorySeedDTO;
import com.example.crud_demo.model.Category;

public class CategoryMapper {
    public static Category toEntity(CategorySeedDTO dto, String localImagePath) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setImagePath(localImagePath);
        return category;
    }
}
