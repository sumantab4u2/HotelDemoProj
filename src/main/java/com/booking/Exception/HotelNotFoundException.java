package com.booking.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Hotel Not Available") 
public class HotelNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

}