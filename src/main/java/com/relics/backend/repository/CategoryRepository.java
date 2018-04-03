package com.relics.backend.repository;

import com.relics.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "SELECT category_name FROM category\n" +
            "ORDER BY category_name", nativeQuery = true)
    List<String> findAllCategoriesNames();
}
