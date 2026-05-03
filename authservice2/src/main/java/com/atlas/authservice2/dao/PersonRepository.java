package com.atlas.authservice2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atlas.authservice2.dao.model.Person;

public interface PersonRepository<T extends Person> extends JpaRepository<T, Long> {
	
	

}
