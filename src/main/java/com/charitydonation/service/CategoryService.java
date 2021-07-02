package com.charitydonation.service;

import com.charitydonation.entity.Category;
import com.charitydonation.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {return categoryRepository.findAll(); }
}
