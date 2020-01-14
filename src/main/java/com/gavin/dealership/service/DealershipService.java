package com.gavin.dealership.service;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Offer;
import com.gavin.dealership.pojo.User;

public class DealershipService implements Serializable {

	private List<Car> lot = new LinkedList<Car>();

	private Map<String, User> users = new HashMap<String, User>();
	
	private List<Offer> offers = new LinkedList<Offer>();
	
	public DealershipService() {
		super();
	}

	public DealershipService(List<Car> lot, Map<String, User> users, List<Offer> offers) {
		super();
		this.lot = lot;
		this.users = users;
		this.offers = offers;
	}

	public List<Car> getLot() {
		return lot;
	}

	public void setLot(List<Car> lot) {
		this.lot = lot;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

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
			return user.getPassword().equals(password);
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
		for(Car c:lot) {
			System.out.println(c);
		}
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
		for(Offer o:offers) {
			System.out.println(o);
		}
	}

	public void listPayments() {
		for(Entry<String,User> e: users.entrySet()) {
			if(e.getValue() instanceof Customer) {
				Customer c = (Customer) e.getValue();
				System.out.println(c.getUsername()+"'s Monthly Payment: "+c.getMonthlyPayment());
			}
		}
	}

	public void addOffer(User user,Car car,int monthlyPayment,int months) {
		offers.add(new Offer(car,monthlyPayment,months,(Customer)user));
	}

	public void acceptOffer(String username, Car car, int monthlyPayment, int months) {
		
		Customer customer = (Customer)users.get(username);
		customer.addCar(car);
		customer.setMonthlyPayment(customer.getMonthlyPayment()+monthlyPayment);
		customer.setMonthsToPay(months);
		customer.setRemainingPayment(customer.getRemainingPayment()+(monthlyPayment*months));
		for(Offer o:offers) {
			if(o.getCar().equals(car) && o.getCustomer().equals(customer) && o.getMonthlyPayment()==monthlyPayment && o.getMonths()==months) {
				offers.remove(o);
			}
		}
		lot.remove(car);
		
	}
	
	public void rejectOffer(String username, Car car, int monthlyPayment, int months) {
		Customer customer = (Customer)users.get(username);
		for(Offer o:offers) {
			if(o.getCar().equals(car) && o.getCustomer().equals(customer) && o.getMonthlyPayment()==monthlyPayment && o.getMonths()==months) {
				offers.remove(o);
			}
		}
	}
	
	public int calculateMonthlyPayment() {
		int total = 0;
		for(Entry<String,User> e: users.entrySet()) {
			if(e.getValue() instanceof Customer) {
				Customer c = (Customer) e.getValue();
				total+=c.getMonthlyPayment();
			}
		}
		return total;
	}
	
	public int makePayment(Customer customer) {
		return customer.makePayment();
	}
	
	

}
