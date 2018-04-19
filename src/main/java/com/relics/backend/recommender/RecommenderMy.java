package com.relics.backend.recommender;

import com.relics.backend.model.Review;
import com.relics.backend.repository.ReviewRepository;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RecommenderMy {

    DataModel dataModel;

    public void prepareDataModelFromDB(ReviewRepository reviewRepository) throws TasteException {
        try {

            FileWriter fileWriter = new FileWriter("data.txt");
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
            dataModel = new FileDataModel(new File("data.txt"));

            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood =
                    new NearestNUserNeighborhood(3, userSimilarity, dataModel);
            Recommender recommender =
                    new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity);
            Recommender cachingRecommender = new CachingRecommender(recommender);
            List<RecommendedItem> recommendations =
                    cachingRecommender.recommend(2, 10);

            System.out.println(recommendations);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}