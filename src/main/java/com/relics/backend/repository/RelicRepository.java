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
            "WHERE relic_categories.categories_category_name = :category\n" +
            "AND approved IS true", nativeQuery = true)
    List<Relic> findByCategory(@Param("category") String category);

    @Query(value = "SELECT id FROM public.relic\n" +
            "WHERE approved IS true\n" +
            "ORDER BY random()" +
            "LIMIT :quantity", nativeQuery = true)
    List<BigInteger> getRandomRelicIDs(@Param("quantity") Integer quantity);

    @Query(value = "SELECT id FROM public.relic WHERE approved IS true\n", nativeQuery = true)
    List<BigInteger> getAllIDs();

    @Query(value = "SELECT relic.id\n" +
            "FROM relic\n" +
            "  JOIN geographic_location ON relic.geographic_location_id = geographic_location.id\n" +
            "  JOIN relic_categories ON relic.id = relic_categories.relics_id\n" +
            "WHERE register_number LIKE %:register%\n" +
            "      AND approved IS TRUE\n" +
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
            "\t      geographic_location.longitude > :minLen and geographic_location.longitude < :maxLen and\n" +
            "\t      approved is true",
            nativeQuery = true)
    List<Relic> getRelicsByLocation(@Param("minLat") double minLat, @Param("maxLat") double maxLat,
                                    @Param("minLen") double minLen, @Param("maxLen") double maxLen);

    @Query(value = "SELECT relic.id FROM relic \n" +
                    "JOIN review on review.relic_id = relic.id\n" +
                    "JOIN relic_categories on relic.id = relic_categories.relics_id\n" +
                    "WHERE review.app_user_id = :id\n" +
                        "AND relic_categories.categories_category_name LIKE :category\n" +
                        "AND review.rating = :vote\n" +
                        "AND approved IS true",nativeQuery = true)
    List<BigInteger> getRelicsReviewdByUser(@Param("id") Long id,
                                            @Param("category") String category,
                                            @Param("vote") Integer vote);

    @Query(value = "SELECT relic.id FROM relic \n" +
            "JOIN review on review.relic_id = relic.id\n" +
            "JOIN relic_categories on relic.id = relic_categories.relics_id\n" +
            "WHERE review.app_user_id = :id\n" +
            "AND relic_categories.categories_category_name LIKE :category\n" +
            "AND approved IS true",nativeQuery = true)
    List<BigInteger> getRelicsReviewdByUserDefault(@Param("id") Long id,
                                            @Param("category") String category);

    @Query(value = "Select distinct district_name from geographic_location \n" +
            "\twhere :voivodeship = '' or voivodeship_name = :voivodeship\n" +
            "\torder by district_name", nativeQuery = true)
    List<String> getDistrictNames(@Param("voivodeship") String voivodeship);

    @Query(value = "Select distinct commune_name from geographic_location \n" +
            "\twhere :voivodeship = '' or voivodeship_name = :voivodeship and\n" +
            "\t      :districtName = '' or district_name = :districtName\n " +
            "\torder by commune_name", nativeQuery = true)
    List<String> getCommmuneNames(@Param("voivodeship") String voivodeship, @Param("districtName") String districtName);

    @Query(value = "Select distinct place_name from geographic_location \n" +
            "\twhere :voivodeship = '' or voivodeship_name = :voivodeship and\n" +
            "\t      :districtName = '' or district_name = :districtName and\n" +
            "\t      :communeName = '' or commune_name = :communeName\n" +
            "\torder by place_name", nativeQuery = true)
    List<String> getPlaceNames(@Param("voivodeship") String voivodeship, @Param("districtName") String districtName,
                               @Param("communeName") String communeName);

    @Query(value = "Select r.id from relic as r\n" +
                    "\t     join relic_categories as c on r.id = c.relics_id\n" +
                    "\t     join geographic_location as g on r.geographic_location_id = g.id\n" +
                    "\t     where r.approved = :approved\n" +
                    "\t\t       and c.categories_category_name like %:category%\n" +
                    "\t\t       and g.voivodeship_name like %:voivodeship%\n" +
                    "\t\t       and g.district_name like %:districtName%\n" +
                    "\t\t       and g.commune_name like %:communeName%\n" +
                    "\t\t       and g.place_name like %:placeName%\n" +
                    "\t\t       and r.identification like %:name% order by r.id " +
                    "\t\t       offset :offset limit 5\n", nativeQuery = true)
    List<BigInteger> getFilteredRelics(@Param("name") String name, @Param("approved") boolean approved,
                                  @Param("category") String category, @Param("voivodeship") String voivodeship,
                                  @Param("districtName") String districtName, @Param("communeName") String communeName,
                                  @Param("placeName") String placeName, @Param("offset") Integer offset);

    @Query(value = "Update relic set approved = :approved where id = :id returning true", nativeQuery = true)
    boolean updateRelicStatus(@Param("id") Long id, @Param("approved") Boolean approved);
}
