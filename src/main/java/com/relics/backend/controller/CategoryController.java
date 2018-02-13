package com.relics.backend.controller;

import com.relics.backend.model.Category;
import com.relics.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController implements BasicController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/category")
    public void createNewCategory(@Valid @RequestBody Category category) {
        categoryRepository.save(category);
    }
}
