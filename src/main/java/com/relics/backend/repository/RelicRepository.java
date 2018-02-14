package com.relics.backend.repository;

import com.relics.backend.model.Relic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelicRepository extends JpaRepository<Relic, Long> {

    @Query(value = "SELECT * FROM relic\n" +
            "JOIN relic_categories ON relic.id = relic_categories.relic_id\n" +
            "WHERE relic_categories.categories_category_name = :category", nativeQuery = true)
    public List<Relic> findByCategory(@Param("category") String category);

}
