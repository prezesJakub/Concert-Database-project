package com.example.concertmanager.config;

import com.example.concertmanager.entity.Category;
import com.example.concertmanager.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void init() {
        List<String> defaultCategories = List.of(
                "Koncert", "Mecz", "Wydarzenie kulturalne", "Stand-up", "Konferencja", "Teatr"
        );

        for(String categoryName : defaultCategories) {
            categoryRepository.findByName(categoryName)
                    .orElseGet(() -> categoryRepository.save(new Category(categoryName)));
        }
    }
}
