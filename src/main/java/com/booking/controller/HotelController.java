package com.booking.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping(value="/hotels")
public class HotelController {

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

	// Find list of hotels 		
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("hotels", hotels.findAll());
		return "hotels";
	}

	
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newHotel(Model model) {
		model.addAttribute("hotel", new Hotel());
		model.addAttribute("categories", categories.findAll());
		return "hotels/create";
	}

	

	
	@RequestMapping(value="{id}", method=RequestMethod.GET) 	
	public String show(@PathVariable("id") long id, Model model) {
		Hotel hotel = hotels.findOne("name");
		if( hotel == null )
			throw new HotelNotFoundException();
		
		model.addAttribute("booking", new Booking());
		model.addAttribute("hotel", hotel );
		model.addAttribute("reply", new Comment());
		model.addAttribute("users", users.findAll());
		model.addAttribute("roomTypes", roomTypes.findAll());
		
		Map<Long, Room> rmap = hotel.getRooms();
		Map<RoomType, Room> rttemp = new HashMap<RoomType, Room>();
		
		for(Room r : rmap.values())
		{
			rttemp.put(r.getType(), r);
		}
		
		model.addAttribute("hotelRoomTypes", rttemp);		
		return "hotels/show";
	}

	// GET  /hotels/{id}.json 		- the hotel with identifier {id}
	@RequestMapping(value="{id}", method=RequestMethod.GET, produces={"text/plain","application/json"})
	public @ResponseBody Hotel showJSON(@PathVariable("id") long id, Model model) {
		Hotel hotel = hotels.findOne(id);
		if( hotel == null )
			throw new HotelNotFoundException();
		return hotel;
	}

	@RequestMapping(value="{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable("id") long id, Model model) { 	
		Hotel hotel = hotels.findOne(id);
		model.addAttribute("hotel", hotel);    	
		model.addAttribute("categories", categories.findAll());
		model.addAttribute("users", users.findAll());
		return "hotels/edit";
	}

	// POST /hotels/{id} 	 	- update the hotel with identifier {id}	
	@RequestMapping(value="{id}", method=RequestMethod.POST)
	public String editSave(@PathVariable("id") long id, @ModelAttribute("hotel") Hotel hotel) {
		hotel.setStatus(false);
		hotels.save(hotel);
		return "redirect:/hotels/{id}";
	}

	// GET  /hotels/{id}/remove 	- removes the hotel with identifier {id}
	@RequestMapping(value="{id}/remove", method=RequestMethod.GET)
	public String remove(@PathVariable("id") long id, Model model)
	{		
		for(Room r : hotels.findOne(id).getRooms().values())
		{
			for(Booking b : r.getBookings())
			{
				bookings.delete(b);
			}
		}
		
		hotels.delete(hotels.findOne(id));
		return "redirect:/hotels";
	} 
	
		
		
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String searchHotel(Model model, @RequestParam("searchString") String searchString)
	{
		Iterator<Hotel> ithotels = hotels.findAll().iterator();
    	List<Hotel> hotelsList = new ArrayList<Hotel>();	
    	    	
    	while(ithotels.hasNext())
    	{
    		Hotel h = ithotels.next();
    		if(h.getName().toLowerCase().contains(searchString.toLowerCase()))
    			hotelsList.add(h);
    	}				
    	
    	model.addAttribute("hotels", hotelsList);
    	return "hotels/index";
	}
}
