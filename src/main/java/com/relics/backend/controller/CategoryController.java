package com.relics.backend.controller;

import com.relics.backend.model.Category;
import com.relics.backend.repository.CategoryRepository;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CategoryController implements BasicController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    RelicRepository relicRepository;

    @PostMapping("/category")
    public void createNewCategory(@Valid @RequestBody Category category) {
        categoryRepository.save(category);
    }

    @GetMapping("/category")
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAllCategoriesNames();
    }

/*    @GetMapping("/category/{id}")
    @ResponseBody
    public String getCategoryForRelicId(@PathVariable(value = "id") Long id) {
        String categories = categoryRepository.getCategoryForRelicId(id);

            System.out.println(categories);

        return categoryRepository.getCategoryForRelicId(id);
    }*/
}
