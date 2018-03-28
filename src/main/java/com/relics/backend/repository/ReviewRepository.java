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
}
