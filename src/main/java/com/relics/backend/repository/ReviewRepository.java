package com.relics.backend.repository;

import com.relics.backend.model.Relic;
import com.relics.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review\n" +
            "WHERE review.relic_id = :relicId", nativeQuery = true)
    public List<Review> findAllReviewByRelicId(@Param("relicId") Long relicId);

}
