package com.booking.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.booking.model.Room;
import com.booking.model.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

	User findAll();

	User save();

	User findOne(long id);
	
}