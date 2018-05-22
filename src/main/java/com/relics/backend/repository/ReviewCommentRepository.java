package com.relics.backend.repository;

import com.relics.backend.model.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    @Query(value = "SELECT * FROM review_comment\n" +
            "JOIN review_comments on review_comment.id = review_comments.comments_id\n" +
            "WHERE review_comments.review_id = :id", nativeQuery = true)
    List<ReviewComment> getCommentsForReview(@Param("id") Long id);
}
