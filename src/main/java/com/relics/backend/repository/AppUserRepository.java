package com.relics.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.relics.backend.model.User;

@Repository
public interface AppUserRepository extends JpaRepository<User, Long> {

	@Query(value = "INSERT INTO users.users(\"username\", \"password\") "
			+ "VALUES(:username, :password)", nativeQuery = true)
	void addUser(@Param("username") String username, @Param("password") String password);

	@Query(value = "SELECT * FROM users.users WHERE username = :username", 
			nativeQuery = true)
	User getUser(@Param("username") String username);

	@Query(value = "SELECT * FROM users.users WHERE username = :username "
			+ "and enabled = true", nativeQuery = true)
	User getEnabledUser(@Param("username") String username);

}
