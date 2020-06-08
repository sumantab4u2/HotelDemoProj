package com.booking.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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
@RequestMapping(value="/bookings")
@SessionAttributes({"booking", "numberRooms","roomType"})
public class BookingController {

	@Autowired
	BookingRepository bookings;

	@Autowired
	HotelRepository hotels;

	@Autowired
	RoomRepository rooms;

	@Autowired
	UserRepository users;

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/rooms", method=RequestMethod.GET, produces={"text/plain","application/json"})
	public @ResponseBody Iterable<Room> getRoomTypes(){
		return (Iterable<Room>) rooms.findAll();
	}

	@RequestMapping(value="/new/{hotel_id}", method=RequestMethod.GET, produces={"text/plain","application/json"})
	public @ResponseBody Booking bookRoomJSON(@PathVariable("hotel_id") long hotel_id){

		int numberRooms = 2;
		long roomType = 1;
		Booking booking = new Booking();
		booking.setBegin_date(new Date(1448713320000L));
		booking.setEnd_date(new Date(1449145320000L));
		List<Date> dates = getDates(booking);

			
		Map<Long,Room> roomsFromHotel = (Map<Long, Room>) hotels.getRooms();
		List<Room> rooms_available = new ArrayList<Room>();
		int counter = 1;
		for(Long entry : roomsFromHotel.keySet())
		{
			Room r = roomsFromHotel.get(entry);
			Map<Date, Long> room_bookings = r.getDays_reserved();
			boolean found = false;
			Iterator<Date> itDates = dates.iterator();

			while(itDates.hasNext()){
				Date day = itDates.next();
				if(room_bookings.get(day) != null){
					found = true;
					break;
				}
			}	
			if(!found && r.getPrice() == rt && counter <= numberRooms)
			{						
				rooms_available.add(r);
				for(Date date: dates)
					room_bookings.put(date, booking.getId());
				counter++;
			}
			else if(counter > numberRooms)
				break;
		}
		Set<Room> roomsBooking = new HashSet<Room>(rooms_available);
		booking.setRooms(roomsBooking);
		bookings.save(booking);

		return booking;
	}

	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newBooking(Model model)
	{
		model.addAttribute("booking", new Booking());
		model.addAttribute("roomTypes", rooms.findAll());
		return "bookings/create";
	}

	

	@RequestMapping(value="/search", method=RequestMethod.GET, produces={"text/plain","application/json"})
	public @ResponseBody Iterable<Room> searchRoomsJSON(Date checkin, Date checkout, String rooms, long roomType)
	{		
		int numberRooms = Integer.parseInt(rooms);
		Booking booking = new Booking();
		booking.setBegin_date(checkin);
		booking.setEnd_date(checkout);

		Room rt = rooms.findOne(rooms);
		List<Room> rooms_available = new ArrayList<Room>();
		List<Date> dates = getDates(booking);
		Iterator<Hotel> ithotels = hotels.findAll().iterator();

		while(ithotels.hasNext()){
			Hotel hotel = ithotels.next();
			Map<Long, Room> rooms_map = hotel.getRooms();
			int counter = 0;
			Room currentRoom = null;
			for(Entry<Long, Room> room : rooms_map.entrySet()){
				Room r = room.getValue();
				Map<Date, Long> room_bookings = r.getDays_reserved();
				boolean found = false;
				Iterator<Date> itDates = dates.iterator();

				while(itDates.hasNext()){
					Date day = itDates.next();
					if(room_bookings.get(day) != null){
						found = true;
						break;
					}
				}	

				if(!found && r.getType().getDescription().equals(rt.getDescription()))
				{						
					counter++;
					currentRoom = r;
				}
			}
			if(counter >= numberRooms)
				rooms_available.add(currentRoom);
		}
		return rooms_available;
	}

	private List<Date> getDates(Booking booking){

		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(booking.getBegin_date());

		while (calendar.getTime().getTime() <= booking.getEnd_date().getTime()){
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);       
		}      
		return dates;
	}
	}


