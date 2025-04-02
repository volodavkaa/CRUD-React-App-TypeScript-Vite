package com.example.crud_demo;

import com.example.crud_demo.dto.CategorySeedDTO;
import com.example.crud_demo.dto.ProductSeedDTO;
import com.example.crud_demo.dto.UserSeedDTO;
import com.example.crud_demo.mapper.CategoryMapper;
import com.example.crud_demo.mapper.ProductMapper;
import com.example.crud_demo.mapper.UserMapper;
import com.example.crud_demo.model.Category;
import com.example.crud_demo.model.Product;
import com.example.crud_demo.model.User;
import com.example.crud_demo.repository.CategoryRepository;
import com.example.crud_demo.repository.ProductRepository;
import com.example.crud_demo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Component
public class SeedDataInitializer {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Тека для збереження завантажених зображень
    private final Path uploadDir = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            seedCategories();
            seedProducts();
            seedUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            try (InputStream is = getClass().getResourceAsStream("/seed/categories.json")) {
                List<CategorySeedDTO> categoryDTOs = objectMapper.readValue(is, new TypeReference<List<CategorySeedDTO>>() {});
                for (CategorySeedDTO dto : categoryDTOs) {
                    String localImagePath = (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty())
                            ? downloadImage(dto.getImageUrl())
                            : "";
                    Category category = CategoryMapper.toEntity(dto, localImagePath);
                    categoryRepository.save(category);
                }
                System.out.println("Categories seeded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void seedProducts() {
        if (productRepository.count() == 0) {
            try (InputStream is = getClass().getResourceAsStream("/seed/products.json")) {
                List<ProductSeedDTO> productDTOs = objectMapper.readValue(is, new TypeReference<List<ProductSeedDTO>>() {});
                for (ProductSeedDTO dto : productDTOs) {
                    Optional<Category> optionalCategory = categoryRepository.findByName(dto.getCategoryName());
                    if (optionalCategory.isPresent()) {
                        String localImagePath = (dto.getImageUrl() != null && !dto.getImageUrl().isEmpty())
                                ? downloadImage(dto.getImageUrl())
                                : "";
                        Product product = ProductMapper.toEntity(dto, optionalCategory.get(), localImagePath);
                        productRepository.save(product);
                    } else {
                        System.out.println("Category " + dto.getCategoryName() + " not found for product " + dto.getName());
                    }
                }
                System.out.println("Products seeded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            try (InputStream is = getClass().getResourceAsStream("/seed/users.json")) {
                List<UserSeedDTO> userDTOs = objectMapper.readValue(is, new TypeReference<List<UserSeedDTO>>() {});
                for (UserSeedDTO dto : userDTOs) {
                    User user = UserMapper.toEntity(dto);
                    userRepository.save(user);
                }
                System.out.println("Users seeded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String fileName = Paths.get(url.getPath()).getFileName().toString();
            Path targetPath = uploadDir.resolve(fileName);
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
