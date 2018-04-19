package com.relics.backend.database.utils;

import com.relics.backend.recommender.RecommenderMy;
import com.relics.backend.repository.ReviewRepository;
import org.apache.mahout.cf.taste.common.TasteException;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommenderTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private RecommenderMy objectUnderTest;

    @Test
    public void saveCategoryToDB() throws JSONException {
        objectUnderTest = new RecommenderMy();
        try {
            objectUnderTest.prepareDataModelFromDB(reviewRepository);
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}