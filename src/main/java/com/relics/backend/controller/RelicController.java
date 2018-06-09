package com.relics.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import com.relics.backend.model.Relic;
import com.relics.backend.recommender.distance.DistanceRecommender;
import com.relics.backend.recommender.user.UserReviewRecommender;
import com.relics.backend.repository.RelicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class RelicController implements BasicController {

    @Autowired
    private RelicRepository relicRepository;

    @Autowired
    private DistanceRecommender distanceRecommender;

    @Autowired
    private UserReviewRecommender recommendRelicsByUserReviews;

    @PostMapping("/relics")
    public void createNewRelic(@Valid @RequestBody Relic relic) {
        relicRepository.save(relic);
    }

    @PutMapping("/relics/{id}")
    @ResponseBody
    public ResponseEntity<Relic> updateRelicById(@PathVariable(value = "id") Long id, @Valid @RequestBody Relic newRelic) {
        Relic relic = relicRepository.findOne(id);
        if (relic == null) {
            return getNotFoundResponseEntity();
        }
        relic = updateRelic(relic, newRelic);
        relicRepository.save(relic);
        return ResponseEntity.ok(relic);
    }

    @GetMapping("/relics")
    @ResponseBody
    public List<Relic> getAllRelics() {
        return relicRepository.findAll();
    }

    @GetMapping("/relics/random/{quantity}")
    @ResponseBody
    public List<BigInteger> getRandomRelicIDs(@PathVariable(value = "quantity") Integer quantity) {
        return relicRepository.getRandomRelicIDs(quantity);
    }

    @GetMapping("/relics/recommend/distance")
    @ResponseBody
    public List<BigInteger> recommendRelicsByDistance(@RequestParam(value = "latitude") Double latitude,
                                                      @RequestParam(value = "longitude") Double longitude) {
        return distanceRecommender.recommendRelicsByDistance(latitude, longitude);
    }

    @GetMapping("/relics/recommend/reviews")
    @ResponseBody
    public List<BigInteger> recommendRelicsByUserReviews() {
        return recommendRelicsByUserReviews.recommendRelicsByUserReviews();
    }

    @JsonView(View.BasicDescription.class)
    @GetMapping("/relics/{id}")
    @ResponseBody
    public ResponseEntity<Relic> getRelicById(@PathVariable(value = "id") Long id) {
        Relic relic = relicRepository.findOne(id);
        if (relic == null)
            return getNotFoundResponseEntity();
        return ResponseEntity.ok(relic);
    }

    @GetMapping("/relics/category/{categoryName}")
    @ResponseBody
    public List<Relic> getRelicsByCategoryName(@PathVariable(value = "categoryName") String categoryName) {
        return relicRepository.findByCategory(categoryName);
    }

    @DeleteMapping("/relics/{id}")
    @ResponseBody
    public ResponseEntity<Relic> deleteRelicById(@PathVariable(value = "id") Long id) {
        Relic relic = relicRepository.findOne(id);
        if (relic == null)
            return getNotFoundResponseEntity();
        relicRepository.delete(relic);
        return ResponseEntity.ok(relic);
    }

    @GetMapping("relics/filter")
    @ResponseBody
    public List<BigInteger> getRelicItemsWithFilter(
            @RequestParam(value = "name", defaultValue = "%") String name,
            @RequestParam(value = "register", defaultValue = "%") String register,
            @RequestParam(value = "voivodeship", defaultValue = "%") String voivodeship,
            @RequestParam(value = "category", defaultValue = "%") String category,
            @RequestParam(value = "place", defaultValue = "%") String place) {
        return relicRepository.getRelicItemsWithFilter(name, register, voivodeship, category, place);
    }

    private Relic updateRelic(Relic oldRelic, Relic newRelic) {
        oldRelic.setIdentification(newRelic.getIdentification());
        return oldRelic;
    }
}
