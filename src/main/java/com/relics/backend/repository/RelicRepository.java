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

    @Query(value = "SELECT relic.id\n" +
            "FROM relic\n" +
            "  JOIN geographic_location ON relic.geographic_location_id = geographic_location.id\n" +
            "  JOIN relic_categories ON relic.id = relic_categories.relics_id\n" +
            "WHERE register_number LIKE %:register%\n" +
            "      AND identification LIKE %:name%\n" +
            "      AND geographic_location.voivodeship_name LIKE :voivodeship\n" +
            "      AND geographic_location.place_name LIKE %:place%\n" +
            "      AND relic_categories.categories_category_name LIKE :category\n" +
            "LIMIT 5", nativeQuery = true)
    List<BigInteger> getRelicItemsWithFilter(@Param("name") String name, @Param("register") String register,
                                             @Param("voivodeship") String voivodeship, @Param("category") String category,
                                             @Param("place") String place);

    @Query(value = "Select * from relic \n" +
            "\tjoin geographic_location on relic.geographic_location_id = geographic_location.id\n" +
            "\twhere geographic_location.latitude > :minLat and geographic_location.latitude < :maxLat and\n" +
            "\t      geographic_location.longitude > :minLen and geographic_location.longitude < :maxLen",
            nativeQuery = true)
    List<Relic> getRelicsByLocation(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                    @Param("minLen") double minLen, @Param("maxLen") double maxLen);
}
