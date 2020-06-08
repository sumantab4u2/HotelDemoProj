package com.booking.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


@Controller
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	UserRepository users;

	@Autowired
	BookingRepository bookings;



	// GET  list of users
	@RequestMapping(method=RequestMethod.GET)

	public String index(Model model) {
		model.addAttribute("users", users.findAll());
		return "users/index";
	}

	// GET  /users/new			- the form to fill the data for a new user
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newHotel(Model model) {
		model.addAttribute("user", new User());
		return "users/create";
	}

	

	// GET /login
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model) {
		return "index";
	}

	// GET   user with 
	@RequestMapping(value="{id}", method=RequestMethod.GET) 
	public String show(@PathVariable("id") long id, Model model) throws com.booking.Exception.HotelNotFoundException {
		User user = users.findOne(id);
		if( user == null )
			throw new HotelNotFoundException();    	
		model.addAttribute("user", user);    
		model.addAttribute("bookings", getUserBookings(user.getId()));   
		return "users/show";
	}

	@RequestMapping(value="/me", method=RequestMethod.GET)
	public String showActiveProfile(Model model) throws HotelNotFoundException
	{
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();    	    
		//CustomUserDetail myUser= (CustomUserDetail) auth.getPrincipal();
		User user = users.findOne(myUser.getUser().getId());
		model.addAttribute("bookings", getUserBookings(user.getId()));    	    	
		model.addAttribute("user", user);
		return "users/show";
	}

	public Iterable<Booking> getUserBookings(long user_id)
	{
		Iterator<Booking> itbookings = bookings.findAll().iterator();
		List<Booking> bookingsList = new ArrayList<Booking>();

		while(itbookings.hasNext())
		{
			Booking b = itbookings.next();
			if(b.getUser().getId() == user_id)
				bookingsList.add(b);
		}

		return bookingsList;
	}    

	
	// GET /users/{id}/edit - form to edit user
	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable("id") long id, Model model) { 	
		User user = users.findOne(id);
		model.addAttribute("user", user);    	
		model.addAttribute("authorities", authorities.findAll());
		return "users/edit";
	}

	// POST /users/{id}        	- edit a user
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String edit(@PathVariable("id") long id, @ModelAttribute User user, Model model)
	{
		users.save(user);
		model.addAttribute("user", user);
		return "redirect:/admin";
	}	
}