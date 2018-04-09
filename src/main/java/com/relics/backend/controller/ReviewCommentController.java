package com.relics.backend.controller;

import com.relics.backend.model.ReviewComment;
import com.relics.backend.repository.ReviewCommentRepository;
import com.relics.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
public class ReviewCommentController {

    @Autowired
    ReviewCommentRepository reviewCommentRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @PostMapping("/relics/review/{id}/comment")
    public void createNewReviewComment(@PathVariable(value = "id") Long id, @Valid @RequestBody ReviewComment reviewComment) {
        reviewComment.setReview(reviewRepository.findOne(id));
        reviewCommentRepository.save(reviewComment);
    }
}
