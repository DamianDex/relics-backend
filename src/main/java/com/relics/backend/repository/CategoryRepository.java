package com.relics.backend.repository;

import com.relics.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "SELECT category_name FROM category\n" +
            "ORDER BY category_name", nativeQuery = true)
    List<String> findAllCategoriesNames();

/*    @Query(value = "SELECT categories_category_name FROM relic_categories\n" +
            "WHERE relics_id = :id",nativeQuery = true)
    List<String> getCategoryForRelicId(@Param("id") Long id);*/
/*    @Query(value = "SELECT category_name FROM category\n" +
            "JOIN relic_categories ON category.category_name = relic_categories.categories_category_name\n" +
            "WHERE relic_categories.relics_id = :id",nativeQuery = true)
    String getCategoryForRelicId(@Param("id") Long id);*/

}
