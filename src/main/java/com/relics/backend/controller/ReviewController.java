package com.relics.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import com.relics.backend.model.Review;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(value = "http://localhost:3000")
public class ReviewController implements BasicController {

    private final DataConverter dataConverter = new DataConverter();

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LoginUtils loginUtils;

    @PostMapping("/relics/review")
    public void createNewReview(@Valid @RequestBody Review review) {
        String formattedDate = dataConverter.convertDateToString();
        review.setDate(formattedDate);
        review.setAppUser(loginUtils.getLoggedUser());
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

    @GetMapping("/relics/{id}/review/avg")
    @ResponseBody
    public Double getAvgRating(@PathVariable(value = "id") Long id) {
        return reviewRepository.getAvgRating(id);
    }

    @GetMapping("relics/{id}/review/count")
    @ResponseBody
    public Integer getRatingCount(@PathVariable(value = "id") Long id) {
        return reviewRepository.findAllReviewByRelicId(id).size();
    }

    @GetMapping("relics/review/ranking/{quantity}")
    @ResponseBody
    public List<BigInteger> getTopRankedRelicIDs(@PathVariable(value = "quantity") Integer quantity) {
        return reviewRepository.getTopRankedRelicIDs(quantity);
    }

    @GetMapping("relics/review/ranking/{quantity}/filter")
    @ResponseBody
    public List<BigInteger> getTopRankedRelicsIDsWithFilter(@PathVariable(value = "quantity") Integer quantity,
                                                            @RequestParam(value = "category", defaultValue = "%") String category,
                                                            @RequestParam(value = "voivodeship", defaultValue = "%") String voivodeship) {
        return reviewRepository.getTopRankedRelicsIDsWithFilter(quantity, category, voivodeship);
    }

    @GetMapping("/relics/review")
    @ResponseBody
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
