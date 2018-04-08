package com.relics.backend.repository;

import com.relics.backend.model.UserTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTypesRepository extends JpaRepository<UserTypes, Long>{

    @Query(value = "SELECT * FROM user_types WHERE code = :code", nativeQuery = true)
    UserTypes getUserTypeByCode(@Param("code") String code);
}
