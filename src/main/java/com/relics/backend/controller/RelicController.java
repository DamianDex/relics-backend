package com.relics.backend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import com.relics.backend.distance.utils.BufferParallel;
import com.relics.backend.distance.utils.DistancePrimaryOperations;
import com.relics.backend.distance.utils.RelicsInFrameQueryParallel;
import com.relics.backend.model.Relic;
import com.relics.backend.model.RouteBuffer;
import com.relics.backend.model.RouteBufferResult;
import com.relics.backend.recommender.distance.DistanceRecommender;
import com.relics.backend.recommender.user.UserReviewRecommender;
import com.relics.backend.repository.RelicRepository;
import com.relics.backend.security.LoginUtils;
import com.vividsolutions.jts.geom.Polygon;
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
import java.util.concurrent.*;

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

    @Autowired
    private LoginUtils loginUtils;

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

    @JsonView(View.BasicDescription.class)
    @PostMapping("relics/route-buffer")
    @ResponseBody
    public RouteBufferResult getRelicsInDistanceFromRoute(@Valid @RequestBody RouteBuffer routeBuffer) {
        final double[][] routeArray = routeBuffer.getRouteArray();
        final double bufferSize = routeBuffer.getBuffer();
        DistancePrimaryOperations dp = new DistancePrimaryOperations();
        double[] frame = dp.getSearchArea(routeArray, bufferSize);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<List<Relic>> futureRelics = executor.submit(new RelicsInFrameQueryParallel(frame, relicRepository));
        Future<Polygon> futureBuffer = executor.submit(new BufferParallel(routeArray, bufferSize));
        executor.shutdown();
        List<Relic> frameRelics = null;
        Polygon buffer = null;
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
            frameRelics = futureRelics.get();
            buffer = futureBuffer.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        frameRelics = dp.filterRelicsByBuffer(frameRelics, buffer);
        double[][] bufferPoints = dp.getBufferPoints(buffer);
        return new RouteBufferResult(frameRelics, bufferPoints);
    }

    @GetMapping("/my-reviews")
    @ResponseBody
    public List<BigInteger> getRelicsReviewdByUser(
    @RequestParam(value = "vote", defaultValue = "") Integer vote,
    @RequestParam(value = "category", defaultValue = "%") String category){
        Long userId = loginUtils.getLoggedUser().getId();
        if(vote == null){
            return relicRepository.getRelicsReviewdByUserDefault(userId,category);
        }
        return relicRepository.getRelicsReviewdByUser(userId,category,vote);
    }

    @GetMapping("admin/districts")
    @ResponseBody
    public List<String> getDistrictNames(
            @RequestParam(value = "voivodeship", defaultValue = "") String voivodeship) {
        return relicRepository.getDistrictNames(voivodeship);
    }

    @GetMapping("admin/communes")
    @ResponseBody
    public List<String> getCommuneNames(
            @RequestParam(value = "voivodeship", defaultValue = "") String voivodeship,
            @RequestParam(value = "districtName", defaultValue = "") String districtName) {
        return relicRepository.getCommmuneNames(voivodeship, districtName);
    }

    @GetMapping("admin/places")
    @ResponseBody
    public List<String> getPlaces(
            @RequestParam(value = "voivodeship", defaultValue = "") String voivodeship,
            @RequestParam(value = "districtName", defaultValue = "") String districtName,
            @RequestParam(value = "communeName", defaultValue = "") String communeName) {
        return relicRepository.getPlaceNames(voivodeship, districtName, communeName);
    }


    private Relic updateRelic(Relic oldRelic, Relic newRelic) {
        oldRelic.setIdentification(newRelic.getIdentification());
        return oldRelic;
    }
}
