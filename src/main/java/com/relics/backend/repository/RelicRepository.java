package com.relics.backend.repository;

import com.relics.backend.model.Relic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface RelicRepository extends JpaRepository<Relic, Long> {

    @Query(value = "SELECT * FROM public.relic\n" +
            "JOIN relic_categories ON relic.id = relic_categories.relic_id\n" +
            "WHERE relic_categories.categories_category_name = :category", nativeQuery = true)
    List<Relic> findByCategory(@Param("category") String category);

    @Query(value = "SELECT id FROM public.relic\n" +
            "ORDER BY random()" +
            "LIMIT :quantity", nativeQuery = true)
    List<BigInteger> getRandomRelicIDs(@Param("quantity") Integer quantity);

    @Query(value = "SELECT id FROM public.relic\n", nativeQuery = true)
    List<BigInteger> getAllIDs();
}
