package com.booking.repository;

import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import com.booking.model.Hotel;
import com.booking.model.Room;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
	
	Hotel findByName(String string);

	Hotel findAll();

	 Room getRooms();

	void save(Hotel hotel);

	Hotel findOne(String string);
	
	
}