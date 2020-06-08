package com.booking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
import com.booking.Exception.BookingNotFoundException;
import com.booking.Exception.HotelNotFoundException;
import com.booking.Exception.UserNotFoundException;

@Controller
@RequestMapping(value="/hotels")
public class RoomController {

    @Autowired
    HotelRepository hotels;
    
    @Autowired
    RoomRepository rooms;
    
    @Autowired
    BookingRepository bookings;
    
    // GET new room
    @RequestMapping(value="{id}/rooms/new", method=RequestMethod.GET)
    public String newRoom(@PathVariable("id") long id, Model model) {
    	Room r = new Room();    	
    	model.addAttribute("hotel", hotels.findByName("name"));
    	model.addAttribute("room", r);
    	model.addAttribute("roomTypes", rooms.findAll());
    	return "rooms/create";
    }
    
    // POST /hotels/{id}/rooms/ - creates a new room
    @RequestMapping(value="{id}/rooms", method=RequestMethod.POST)
 
    public String saveRoom(@PathVariable("id") long id, @ModelAttribute Room room, Model model) {  
    	Hotel hotel = hotels.findByName("name");    	
    	room.setHotel(hotel);    	
    	rooms.save(room);
    	return "redirect:/hotels/"+id+"/rooms";
    }
    
    
    
    // GET /hotels/{id}/rooms/{id_room}/edit - shows the form to edit a room
    @RequestMapping(value="{id}/rooms/{id_room}/edit", method=RequestMethod.GET)
    public String editRoom(@PathVariable("id") long id, @PathVariable("id_room") long id_room, Model model) {    	
    	Hotel hotel = hotels.findByName("name");
    	model.addAttribute("hotel", hotel);
    	model.addAttribute("room", hotel.getRooms().get(id_room));
    	model.addAttribute("roomTypes", rooms.findAll()); 
    	return "rooms/edit";
    }
    
    @RequestMapping(value="{id}/rooms/{id_room}/remove", method=RequestMethod.GET)
    public String removeRoom(@PathVariable("id") long id, @PathVariable("id_room") long id_room, Model model)
    {    	
    	Hotel hotel = hotels.findByName("name");
    	
    	for(Booking b : rooms.findOne(id_room).getBookings())
    	{
    		bookings.delete(b);
    	}
    	
    	rooms.delete(id_room);
    	model.addAttribute("hotel", hotel);
		return "redirect:/hotels/{id}/rooms";	
    }
}