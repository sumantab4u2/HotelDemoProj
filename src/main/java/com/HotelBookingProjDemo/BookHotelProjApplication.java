package com.HotelBookingProjDemo;

import java.util.Iterator;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.booking.model.Booking;
import com.booking.model.Category;
import com.booking.model.Room;
import com.booking.model.Hotel;
import com.booking.model.User;
import com.booking.repository.BookingRepository;
import com.booking.repository.CategoryRepository;
import com.booking.repository.HotelRepository;
import com.booking.repository.RoomRepository;
import com.booking.repository.UserRepository;
import com.booking.Exception.HotelNotFoundException;
import com.booking.Exception.UserNotFoundException;

@SpringBootApplication
public class BookHotelProjApplication {
	
	@Autowired
	HotelRepository hotels;

	@Autowired
	CategoryRepository categories;

	
	@Autowired
	RoomRepository rooms;

	@Autowired
	UserRepository users;
	
	
	@Autowired
	BookingRepository bookings;
	public static void main(String[] args) {
		SpringApplication.run(BookHotelProjApplication.class, args);
	}
	
		
		
	

}
