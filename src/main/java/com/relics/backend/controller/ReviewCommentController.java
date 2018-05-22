package com.relics.backend.controller;

import com.relics.backend.model.Review;
import com.relics.backend.model.ReviewComment;
import com.relics.backend.repository.ReviewCommentRepository;
import com.relics.backend.repository.ReviewRepository;
import com.relics.backend.security.LoginUtils;
import com.relics.backend.time.utils.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
public class ReviewCommentController {

    private final DataConverter dataConverter = new DataConverter();

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    LoginUtils loginUtils;

    @PostMapping("/relics/review/{id}/comment")
    public void createNewReviewComment(@PathVariable(value = "id") Long id, @Valid @RequestBody ReviewComment reviewComment) {
        String formattedDate = dataConverter.convertDateToString();
        reviewComment.setCreationDate(formattedDate);

        reviewComment.setAppUser(loginUtils.getLoggedUser());
        Review review = reviewRepository.findOne(id);
        review.addComment(reviewComment);
        reviewCommentRepository.save(reviewComment);
    }

    @GetMapping("/relics/review/{id}/comments")
    public List<ReviewComment> getCommentsForReview(@PathVariable(value = "id") Long id) {
        return reviewCommentRepository.getCommentsForReview(id);
    }
}
