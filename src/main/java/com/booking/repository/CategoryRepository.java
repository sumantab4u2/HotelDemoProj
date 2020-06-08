package com.booking.repository;

import org.springframework.data.repository.CrudRepository;

import com.booking.model.Category;


public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	Category findByName(String name);

	Object findAll();
}
