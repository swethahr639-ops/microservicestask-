package com.atlas.authservice2.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.atlas.authservice2.dao.model.User;

@Repository
public interface UserRepository extends PersonRepository<User> {

	Optional<User> findByUserName(String username);
	boolean existsByUserName(String username);
	boolean existsByEmailid(String emailid);
	
	

}
