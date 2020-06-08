package com.booking.repository;

import org.springframework.data.repository.CrudRepository;
import com.booking.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {

	void save(Booking booking);

	Booking findOne(long booking_id);

	void delete(Booking b);
	
	
}
