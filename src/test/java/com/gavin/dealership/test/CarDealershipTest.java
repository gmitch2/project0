package com.gavin.dealership.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Offer;
import com.gavin.dealership.pojo.User;
import com.gavin.dealership.service.DealershipService;

public class CarDealershipTest {
	
	private DealershipService dealership;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dealership = new DealershipService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addUserTest() {
		User u = new User("gavin","123");
		dealership.addUser(u);
		assertEquals(dealership.getUser("gavin"),u);
	}
	
	@Test
	public void addCarTest() {
		Car car = new Car("Toyota","Prius",2015,5000);
		dealership.addCar(car);
		assertEquals(dealership.getCar("Toyota", "Prius", 2015),car);
	}
	
	@Test
	public void authenticateUserTest() {
		User u = new User("gavin","123");
		dealership.addUser(u);
		assertEquals(dealership.authenticateUser("gavin", "123"),true);
	}
	
	@Test
	public void authenticateUserFailTest() {
		User u = new User("gavin","123");
		dealership.addUser(u);
		assertEquals(dealership.authenticateUser("gavin", "1234"),false);
	}
	
	@Test
	public void addOfferTest() {
		Car car = new Car("Toyota","Prius",2015,5000);
		Customer customer = new Customer("gavin","123");
		int monthlyPayment = 500;
		int months = 10;
		dealership.addOffer(customer, car, monthlyPayment, months);
		List<Offer> offers = dealership.getOffers();
		List<Offer> offersTest = new LinkedList<Offer>();
		offersTest.add(new Offer(car,monthlyPayment,months,customer));
		assertEquals(offers,offersTest);
	}
	
	@Test
	public void acceptOfferTest() {
		Customer customer = new Customer("gavin","123");
		Car car = new Car("toyota","prius",2015,5000);
		dealership.addUser(customer);
		dealership.addOffer(customer, car, 500, 10);
		dealership.acceptOffer("gavin", car, 500, 10);
		assertEquals(dealership.getOffers().size(),0);
	}
	
	@Test
	public void rejectOfferTest() {
		Customer customer = new Customer("gavin","123");
		Car car = new Car("toyota","prius",2015,5000);
		dealership.addUser(customer);
		dealership.addOffer(customer, car, 500, 10);
		dealership.rejectOffer("gavin", car, 500, 10);
		assertEquals(dealership.getOffers().size(),0);
	}
	
	@Test
	public void makePaymentTest() {
		Customer customer = new Customer("gavin","123");
		Car car = new Car("toyota","prius",2015,5000);
		dealership.addUser(customer);
		dealership.addOffer(customer, car, 500, 10);
		dealership.acceptOffer("gavin", car, 500, 10);
		dealership.makePayment(customer);
		assertEquals(customer.getRemainingPayment(),4500);
	}
	
	@Test
	public void overPayingTest() {
		Customer customer = new Customer("gavin","123");
		Car car = new Car("toyota","prius",2015,5000);
		dealership.addUser(customer);
		dealership.addOffer(customer, car, 500, 10);
		dealership.acceptOffer("gavin", car, 500, 10);
		for(int i=0; i<11;i++) {
			dealership.makePayment(customer);
		}
		assertEquals(customer.getRemainingPayment(),0);
	}
	
	@Test
	public void totalMonthlyPaymentTest() {
		Customer customer = new Customer("gavin","123");
		Car car = new Car("toyota","prius",2015,5000);
		Car car2 = new Car("honda","accord",2016,10000);
		dealership.addUser(customer);
		dealership.addOffer(customer, car, 500, 10);
		dealership.addOffer(customer, car2, 1000, 10);
		dealership.acceptOffer("gavin", car, 500, 10);
		dealership.acceptOffer("gavin", car2, 1000, 10);
		assertEquals(dealership.calculateMonthlyPayment(),1500);
	}
	

}
