package com.booking.model;

import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    private String name;    
    private String address;
    private int rating;
    private boolean status;
    private Category category;
    private User manager;
    public Hotel() {}
    
    public Hotel(String name, String address, int rating, Category category, boolean status) {    	
    	this.name = name;
    	this.address = address;
    	this.rating = rating;
    	this.category = category;
    	this.status = false;
    }
    private Map<Long, Room> rooms = new HashMap<Long, Room>();
    public String getAddress() {
        return address;
    }

	public Category getCategory(){
    	return category;
    }

	
    
    public long getId() {
        return id;
    }

    
    public User getManager() {
		return manager;
	}

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }
    
    public Map<Long, Room> getRooms() {
		return rooms;
	}

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setCategory(Category category){
    	this.category = category;
    }

    
    
    public void setId(long id) {
        this.id = id;
    }
    
    public void setManager(User manager) {
		this.manager = manager;
	}

	public void setName(String name) {
        this.name = name;
    }
	
	public void setRating(int rating) {
        this.rating = rating;
    }

	public void setRooms(Map<Long, Room> rooms) {
		this.rooms = rooms;
	}

	@Override
    public String toString() {
    	return "Id: " + getId() + "\nName: " + getName() + "\nAddress: " + getAddress() + "\nRating: " + getRating() + "\nCategory: " + category.getName() + "\nManager: " + getManager();
    }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
