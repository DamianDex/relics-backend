package com.relics.backend.repository;

import com.relics.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review\n" +
            "WHERE review.relic_id = :relicId", nativeQuery = true)
    List<Review> findAllReviewByRelicId(@Param("relicId") Long relicId);

    @Query(value = "SELECT AVG(rating) FROM review\n" +
            "WHERE review.relic_id = :relicId", nativeQuery = true)
    Double getAvgRating(@Param("relicId") Long relicId);

    @Query(value = "SELECT relic_id\n" +
            "FROM review\n" +
            "GROUP BY relic_id\n" +
            "ORDER BY AVG(rating) DESC\n" +
            "LIMIT :quantity", nativeQuery = true)
    List<BigInteger> getTopRankedRelicIDs(@Param("quantity") Integer quantity);

    @Query(value = "SELECT relic_id\n" +
            "FROM review\n" +
            "JOIN relic ON review.relic_id = relic.id\n" +
            "JOIN relic_categories ON relic.id = relic_categories.relics_id\n" +
            "JOIN geographic_location ON relic.geographic_location_id = geographic_location.id\n" +
            "WHERE relic_categories.categories_category_name LIKE :category\n" +
            "AND geographic_location.voivodeship_name LIKE :voivodeship\n" +
            "GROUP BY relic_id\n" +
            "ORDER BY AVG(rating) DESC\n" +
            "LIMIT :quantity", nativeQuery = true)
    List<BigInteger> getTopRankedRelicsIDsWithFilter(@Param("quantity") Integer quantity,
                                                     @Param("category") String category,
                                                     @Param("voivodeship") String voivodeship);

    @Query(value = "SELECT rating\n" +
            "FROM review\n" +
            "WHERE relic_id = :id\n" +
            "AND app_user_id = :userId", nativeQuery = true)
    Double getCurrentUserRating(@Param("id") Long id,
                                @Param("userId") Long userId);
}
