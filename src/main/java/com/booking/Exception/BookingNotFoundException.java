package com.booking.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Booking Not Available") 
public class BookingNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

}