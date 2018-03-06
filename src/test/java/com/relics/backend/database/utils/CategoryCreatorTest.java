package com.relics.backend.database.utils;

import com.relics.backend.model.Category;
import com.relics.backend.repository.CategoryRepository;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryCreatorTest {

    private CategoryCreator objectUnderTest;

    public static final String DIRECTORY = "C:\\Relics";

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void saveCategoryToDB() throws JSONException {
        objectUnderTest = new CategoryCreator(DIRECTORY);
        for (Category category : objectUnderTest.getAllCategoriesToSaveInDB()) {
            categoryRepository.save(category);
        }
    }
}