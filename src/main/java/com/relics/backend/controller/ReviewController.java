package com.relics.backend.controller;

import com.relics.backend.model.Relic;
import com.relics.backend.model.Review;
import com.relics.backend.repository.RelicRepository;
import com.relics.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ReviewController implements BasicController {

    @Autowired
    private RelicRepository relicRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/relics/{id}/review")
    public ResponseEntity<Relic> createNewReview(@Valid @RequestBody Review review, @PathVariable(value = "id") Long id) {
        Relic relic = relicRepository.findOne(id);
        if (relic == null) {
            return getNotFoundResponseEntity();
        }
        reviewRepository.save(review);
        relic.addReview(review);
        relicRepository.save(relic);
        return ResponseEntity.ok(relic);
    }

    @GetMapping("/relics/{id}/review")
    @ResponseBody
    public Set<Review> getAllReviewsByRelic(@PathVariable(value = "id") Long id) {
        Relic relic = relicRepository.findOne(id);
        if (relic == null) {
            //TODO: Think what need to be returned
        }
        System.out.println(relic.getReviews());
        return relic.getReviews();
    }

    @GetMapping("/relics/review")
    @ResponseBody
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
