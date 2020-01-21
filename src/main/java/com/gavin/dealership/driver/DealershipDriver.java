package com.gavin.dealership.driver;

import java.util.Scanner;

import com.gavin.dealership.dao.CarDAO;
import com.gavin.dealership.dao.DealershipSerialization;
import com.gavin.dealership.dao.OfferDAO;
import com.gavin.dealership.dao.PaymentDAO;
import com.gavin.dealership.dao.UserDAO;
import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Employee;
import com.gavin.dealership.pojo.User;
import com.gavin.dealership.service.DealershipService;
import com.gavin.dealership.util.LoggerUtil;

public class DealershipDriver {
	
	private static Scanner sc = new Scanner(System.in);
	
	private static DealershipSerialization ds = new DealershipSerialization();

	private static DealershipService dealership = ds.readDealership();
	
	private transient static User currentUser;
	
	public static void main(String[] args) {
		String option = "";

		do {
			System.out.println("System Options:");
			System.out.println("[1] Login");
			System.out.println("[2] Add employee user");
			System.out.println("[3] Add customer user");
			System.out.println("[4] Delete user");
			System.out.println("[5] Exit");

			option = sc.next();
			performSystemAction(option);
		} while (!"5".equals(option));
		
		ds.createDealership(dealership);
		LoggerUtil.info("Dealership.dat was updated");
	}

	private static void performSystemAction(String option) {
		switch(option) {
		case "3":
			System.out.println("Username: ");
			String username = sc.next().trim();
			System.out.println("Password: ");
			String password = sc.next().trim();
			dealership.addUser(new Customer(username,password),false);
			break;
		case "2":
			System.out.println("Username: ");
			username = sc.next().trim();
			System.out.println("Password: ");
			password = sc.next().trim();
			dealership.addUser(new Employee(username,password),true);
			break;
		case "1":
			System.out.println("Username: ");
			username = sc.next().trim();
			System.out.println("Password: ");
			password = sc.next().trim();
			if(dealership.authenticateUser(username,password)) {
				System.out.println("Login successful!");
				currentUser = UserDAO.getUser(username);
				if(currentUser instanceof Customer) {
					String customerOption = "";

					do {
						System.out.println("Customer options:");
						System.out.println("[1] View cars on lot");
						System.out.println("[2] Make offer");
						System.out.println("[3] View my cars");
						System.out.println("[4] Remaining balance");
						System.out.println("[5] Make payment");
						System.out.println("[6] Logout");

						customerOption = sc.next();
						performCustomerAction(customerOption);
					} while (!"6".equals(customerOption));
				} else if (currentUser instanceof Employee) {
						String employeeOption = "";

						do {
							System.out.println("Employee Options:");
							System.out.println("[1] View cars on lot");
							System.out.println("[2] Add car to lot");
							System.out.println("[3] Remove car from lot");
							System.out.println("[4] Accept or reject offers");
							System.out.println("[5] View all payments");
							System.out.println("[6] Logout");

							employeeOption = sc.next();
							performEmployeeAction(employeeOption);
						} while (!"6".equals(employeeOption));
				}
			} else {
				System.out.println("Login failed");
			}
			break;
		case "4":
			System.out.println("Username: ");
			username = sc.next();
			dealership.removeUser(username);
			break;
		case "5":
			System.out.println("Goodbye");
			break;
		}
	}

	private static void performEmployeeAction(String employeeOption) {
		switch(employeeOption) {
		case "1":
			CarDAO.listCars();
			break;
		case "2":
			System.out.println("What year is the car?");
			int year = sc.nextInt();
			System.out.println("What is the make of the car");
			String make = sc.next();
			System.out.println("What is the model of the car");
			String model = sc.next();
			System.out.println("What is the cost of the car");
			int cost = sc.nextInt();
			dealership.addCar(new Car(make,model,year,cost));
			break;
		case "3":
			System.out.println("What year is the car?");
			year = sc.nextInt();
			System.out.println("What is the make of the car");
			make = sc.next();
			System.out.println("What is the model of the car");
			model = sc.next();
			dealership.removeCar(make,model,year);
			break;
		case "4":
			OfferDAO.listOffers();
			System.out.println("Would you like to accept (a) or reject (r) an offer or neither (n)?");
			String offerInput = sc.next();
			switch(offerInput){
			case "a":
				System.out.println("Which offer would you like to accept? (type offer ID)");
				int offerid = sc.nextInt();
				((Employee)currentUser).acceptOffer(offerid);
				break;
			case "r":
				System.out.println("Which offer would you like to reject? (type offer ID)");
				offerid = sc.nextInt();
				dealership.rejectOffer(offerid);
				break;
			default:
				break;
			}
			break;
		case "5":
			PaymentDAO.listPayments();
			break;
		case "6":
			System.out.println("Logging out");
			break;
		
		}
		
	}

	private static void performCustomerAction(String option) {
		switch(option) {
		case "1":
			CarDAO.listCars();
			break;
		case "2":
			CarDAO.listCars();
			System.out.println("Which car are you making an offer on? (Please type the Car ID)");
			int carid = sc.nextInt();
			System.out.println("How much are you offering?");
			double amount = sc.nextDouble();
			OfferDAO.addOffer(carid,UserDAO.getUserId(currentUser.getUsername()),amount);
			break;
		case "3":
			((Customer)currentUser).getOwned();
			break;
		case "4":
			System.out.println(((Customer)currentUser).getRemainingBalance());
			break;
		case "5":
			((Customer)currentUser).makePayment();
			break;
		case "6":
			System.out.println("Logging out.");
			break;
		}
	}

}
