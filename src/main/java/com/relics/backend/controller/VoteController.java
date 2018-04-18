package com.relics.backend.controller;

import com.relics.backend.model.Review;
import com.relics.backend.model.Vote;
import com.relics.backend.repository.ReviewRepository;
import com.relics.backend.repository.VoteRepository;
import com.relics.backend.security.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class VoteController implements BasicController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    LoginUtils loginUtils;

    @PostMapping("/review/{id}/vote")
    public void createNewVote(@Valid @RequestBody Vote vote, @PathVariable(name = "id") Long id) {
        vote.setApplicationUser(loginUtils.getLoggedUser());
        Review review = reviewRepository.findOne(id);
        review.addVote(vote);
        voteRepository.save(vote);
    }
}
