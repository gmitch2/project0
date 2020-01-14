package com.gavin.dealership.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Offer;
import com.gavin.dealership.pojo.User;

public class DealershipService {

	static List<Car> lot = new LinkedList<Car>();

	static Map<String, User> users = new HashMap<String, User>();
	
	static List<Offer> offers = new LinkedList<Offer>();

	public void addCar(Car car) {
		lot.add(car);
	}
	
	public void addUser(User user) {
		users.put(user.getUsername(), user);
	}

	public boolean authenticateUser(String username, String password) {
		User user = getUser(username);
		if(user==null) {
			return false;
		} else {
			return user.getPassword()==password;
		}
	}

	public User getUser(String username) {
		if(users.containsKey(username)){
			return users.get(username);
		} else {
			return null;
		}
	}

	public void removeUser(String username) {
		if(users.containsKey(username)) {
			users.remove(username);
		}
	}

	public void listCars() {
		// TODO Auto-generated method stub
		
	}

	public Car getCar(String make, String model, int year) {
		for(Car car:lot) {
			if(car.getMake().contentEquals(make) && car.getModel().equals(model) && car.getYear()==year) {
				return car;
			}
		}
		return null;
	}

	public void removeCar(String make, String model, int year) {
		Car car = getCar(make,model,year);
		if(car!=null) {
			lot.remove(car);
		}
	}

	public void listOffers() {
		// TODO Auto-generated method stub
		
	}

	public void listPayments() {
		// TODO Auto-generated method stub
		
	}

	public void addOffer(User user,Car car,int offer) {
		// TODO Auto-generated method stub
		
	}
	
	

}
