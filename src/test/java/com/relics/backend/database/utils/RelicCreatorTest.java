package com.relics.backend.database.utils;

import com.relics.backend.model.Relic;
import com.relics.backend.repository.CategoryRepository;
import com.relics.backend.repository.RelicRepository;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RelicCreatorTest {

    private RelicCreator objectUnderTest;

    public static final String DIRECTORY = "C:\\Relics";

    @Autowired
    RelicRepository relicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void saveRelicToDB() throws JSONException {
        objectUnderTest = new RelicCreator(DIRECTORY);
        for (Relic relic : objectUnderTest.getAllRelicsToSaveInDB(categoryRepository)) {
            try {
                relicRepository.save(relic);
            } catch (DataIntegrityViolationException ex) {
                ex.printStackTrace();
            }
        }
    }
}