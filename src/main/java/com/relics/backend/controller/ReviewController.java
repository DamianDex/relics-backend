package com.relics.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import com.relics.backend.model.Review;
import com.relics.backend.repository.RelicRepository;
import com.relics.backend.repository.ReviewRepository;
import com.relics.backend.security.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
public class ReviewController implements BasicController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LoginUtils loginUtils;

    @PostMapping("/relics/review")
    public void createNewReview(@Valid @RequestBody Review review) {
        review.setAppUser(loginUtils.getLoggedUser().getId());
        reviewRepository.save(review);
    }

    @GetMapping(value = "relics/{id}/review/{userId}")
    @ResponseBody
    public Boolean checkIfUserReviewRelic(@PathVariable(value = "id") Long id, @PathVariable(value = "userId") Long userid) {
        return false;
    }

    @GetMapping("/relics/{id}/review")
    @ResponseBody
    @JsonView(View.BasicDescription.class)
    public List<Review> getAllReviewsByRelicId(@PathVariable(value = "id") Long id) {
        return reviewRepository.findAllReviewByRelicId(id);
    }

    @GetMapping("/relics/review")
    @ResponseBody
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
