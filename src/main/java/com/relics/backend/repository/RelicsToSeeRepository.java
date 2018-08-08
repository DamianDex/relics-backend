package com.relics.backend.repository;

import com.relics.backend.model.RelicsToSee;
import com.relics.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface RelicsToSeeRepository extends JpaRepository<RelicsToSee, Long> {

    @Query(value="SELECT relic_to_see FROM relics_to_see\n" +
                    "WHERE relic_id = :id\n" +
                    "AND app_user_id = :userId",nativeQuery = true)
    public Boolean checkIfUserWantToSeeRelic(@Param("id") Long id,
                                            @Param("userId") Long userId);

    @Query(value = "SELECT DISTINCT relic.id\n" +
            "FROM relic\n" +
            "  JOIN geographic_location ON relic.geographic_location_id = geographic_location.id\n" +
            "  JOIN relic_categories ON relic.id = relic_categories.relics_id\n" +
            "  JOIN relics_to_see ON relic.id = relics_to_see.relic_id\n" +
            "WHERE relics_to_see.app_user_id = :userId\n" +
            "      AND geographic_location.place_name LIKE :place\n" +
            "      AND relic_categories.categories_category_name LIKE :category\n" +
            "      AND relics_to_see.relic_to_see IS true\n" +
            "      AND approved IS true", nativeQuery = true)
    List<BigInteger> getRelicsToSeeByUser(@Param("userId") Long userId, @Param("category") String category,
                                             @Param("place") String place);

    @Query(value = "SELECT * FROM public.relics_to_see\n" +
            "WHERE relic_id = :relicId\n" +
            "AND app_user_id = :userId", nativeQuery = true)
    RelicsToSee getRelicToSee(@Param("relicId") Long relicId, @Param("userId") Long userId);
}
