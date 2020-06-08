package com.booking.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Room Not Available")
public class RoomNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

}