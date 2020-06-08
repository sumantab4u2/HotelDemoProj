package com.booking.repository;

import org.springframework.data.repository.CrudRepository;

import com.booking.controller.rooms;
import com.booking.model.Room;


public interface RoomRepository extends CrudRepository<Room, Long> {

	Room findAll();

	Room findOne(long roomType);

	void save(Room room);

	void delete(long id_room);
	
	
}