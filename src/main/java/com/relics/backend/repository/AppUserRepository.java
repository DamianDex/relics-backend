package com.relics.backend.repository;

import com.relics.backend.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<ApplicationUser, Long> {

	@Query(value = "INSERT INTO application_user(\"username\", \"password\", \"uuid\") "
			+ "VALUES(:username, :password, :uuid)", nativeQuery = true)
	void addUser(@Param("username") String username, @Param("password") String password, @Param("uuid") String uuid);

	@Query(value = "SELECT * FROM application_user WHERE username = :username",
			nativeQuery = true)
    ApplicationUser getUser(@Param("username") String username);

	@Query(value = "SELECT * FROM application_user WHERE username = :username "
			+ "and enabled = true", nativeQuery = true)
    ApplicationUser getEnabledUser(@Param("username") String username);
	
	@Query(value = "SELECT EXISTS "
			+ "(SELECT true FROM application_user WHERE username = :username)", nativeQuery=true)
	boolean userExists(@Param("username") String username);

}
