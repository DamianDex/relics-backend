package com.relics.backend.recommender.user;

import com.relics.backend.model.Review;
import com.relics.backend.repository.ReviewRepository;
import com.relics.backend.security.LoginUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.assertj.core.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserReviewRecommender {

    public static final String USER_DATA_MODEL_CACHE_FILE = "./src/main/resources/cache/user-review-cache.txt";

    private DataModel dataModel;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    LoginUtils loginUtils;

    private void prepareDataModelFromDB() throws TasteException {
        try {
            FileWriter fileWriter = new FileWriter(USER_DATA_MODEL_CACHE_FILE);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            List<Review> reviews = reviewRepository.findAll();
            for (Review review : reviews) {
                Long userId = review.getAppUser().getId();
                Long relicId = review.getRelic().getId();
                Integer rating = review.getRating();

                try {
                    Preconditions.checkNotNull(userId);
                    Preconditions.checkNotNull(relicId);
                    Preconditions.checkNotNull(rating);
                } catch (NullPointerException e) {
                    continue;
                }

                String entryToDataModel = userId + ","
                        + relicId + ","
                        + rating + "\n";
                printWriter.write(entryToDataModel);
            }
            printWriter.close();

            dataModel = new FileDataModel(new File(USER_DATA_MODEL_CACHE_FILE));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BigInteger> recommendRelicsByUserReviews() {
        List<BigInteger> results = new ArrayList<>(3);

        try {
            prepareDataModelFromDB();

            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(3, userSimilarity, dataModel);
            Recommender recommender =
                    new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity);
            Recommender cachingRecommender = new CachingRecommender(recommender);
            List<RecommendedItem> recommendations =
                    cachingRecommender.recommend(loginUtils.getLoggedUser().getId(), 3);

            if (recommendations.size() < 3) {
                return Collections.emptyList();
            }

            for (RecommendedItem item : recommendations.subList(0, 3)) {
                results.add(BigInteger.valueOf(item.getItemID()));
            }

        } catch (TasteException e) {
            e.printStackTrace();
        }

        return results;
    }
}