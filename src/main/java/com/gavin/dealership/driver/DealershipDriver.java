package com.gavin.dealership.driver;

import java.util.Scanner;

import com.gavin.dealership.dao.DealershipSerialization;
import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Employee;
import com.gavin.dealership.pojo.User;
import com.gavin.dealership.service.DealershipService;
import com.gavin.dealership.util.LoggerUtil;

public class DealershipDriver {
	
	private static Scanner sc = new Scanner(System.in);
	
	private static LoggerUtil log = new LoggerUtil();
	
	private static DealershipSerialization ds = new DealershipSerialization();

	private static DealershipService dealership = ds.readDealership();
	
	private static User currentUser;
	
	public static void main(String[] args) {
		String option = "";

		do {
			System.out.println("System Options:");
			System.out.println("[1] Login");
			System.out.println("[2] Add employee user");
			System.out.println("[3] Add customer user");
			System.out.println("[4] Delete user");
			System.out.println("[5] Get total monthly payment");
			System.out.println("[6] Exit");

			option = sc.nextLine();
			performSystemAction(option);
		} while (!"6".equals(option));
		
		ds.createDealership(dealership);
	}

	private static void performSystemAction(String option) {
		switch(option) {
		case "3":
			System.out.println("Username: ");
			String username = sc.nextLine().trim();
			System.out.println("Password: ");
			String password = sc.nextLine().trim();
			dealership.addUser(new Customer(username,password));
			break;
		case "2":
			System.out.println("Username: ");
			username = sc.nextLine().trim();
			System.out.println("Password: ");
			password = sc.nextLine().trim();
			dealership.addUser(new Employee(username,password));
			break;
		case "1":
			System.out.println("Username: ");
			username = sc.nextLine().trim();
			System.out.println("Password: ");
			password = sc.nextLine().trim();
			if(dealership.authenticateUser(username,password)) {
				System.out.println("Login successful!");
				currentUser = dealership.getUser(username);
				if(currentUser instanceof Customer) {
					String customerOption = "";

					do {
						System.out.println("Customer options:");
						System.out.println("[1] View cars on lot");
						System.out.println("[2] Make offer");
						System.out.println("[3] View my cars");
						System.out.println("[4] Remaining payments");
						System.out.println("[5] Logout");

						customerOption = sc.nextLine();
						performCustomerAction(customerOption);
					} while (!"5".equals(customerOption));
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

							employeeOption = sc.nextLine();
							performEmployeeAction(employeeOption);
						} while (!"6".equals(employeeOption));
				}
			} else {
				System.out.println("Login failed");
			}
			break;
		case "4":
			System.out.println("Username: ");
			username = sc.nextLine();
			dealership.removeUser(username);
			break;
		case "6":
			System.out.println("Goodbye");
			break;
		case "5":
			System.out.println(dealership.calculateMonthlyPayment());
			break;
		}
	}

	private static void performEmployeeAction(String employeeOption) {
		switch(employeeOption) {
		case "1":
			dealership.listCars();
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
			dealership.listOffers();
			System.out.println("Would you like to accept (a) or reject (r) an offer?");
			String offerInput = sc.next();
			switch(offerInput){
			case "a":
				System.out.println("Username of offer: ");
				String username = sc.next();
				System.out.println("Make: ");
				make = sc.next();
				System.out.println("Model: ");
				model = sc.next();
				System.out.println("Year: ");
				year = sc.nextInt();
				System.out.println("Monthly Payment: ");
				int monthlyPayment = sc.nextInt();
				System.out.println("Months: ");
				int months = sc.nextInt();
				dealership.acceptOffer(username,dealership.getCar(make, model, year),monthlyPayment,months);
				break;
			case "r":
				System.out.println("Username of offer: ");
				username = sc.next();
				System.out.println("Make: ");
				make = sc.next();
				System.out.println("Model: ");
				model = sc.next();
				System.out.println("Year: ");
				year = sc.nextInt();
				System.out.println("Monthly Payment: ");
				monthlyPayment = sc.nextInt();
				System.out.println("Months: ");
				months = sc.nextInt();
				dealership.rejectOffer(username, dealership.getCar(make, model, year), monthlyPayment, months);
				break;
			}
			break;
		case "5":
			dealership.listPayments();
			break;
		case "6":
			System.out.println("Logging out");
			break;
		
		}
		
	}

	private static void performCustomerAction(String option) {
		switch(option) {
		case "1":
			dealership.listCars();
			break;
		case "2":
			System.out.println("What year is the car?");
			int year = sc.nextInt();
			System.out.println("What is the make of the car");
			String make = sc.next();
			System.out.println("What is the model of the car");
			String model = sc.next();
			System.out.println("What is your offer of a monthly payment?");
			int monthlyPayment = sc.nextInt();
			System.out.println("For how many months?");
			int months = sc.nextInt();
			Car car = dealership.getCar(make, model, year);
			dealership.addOffer(currentUser,car,monthlyPayment,months);
			break;
		case "3":
			System.out.println(((Customer)currentUser).getOwned());
			break;
		case "4":
			System.out.println(((Customer)currentUser).getRemainingPayment());
			break;
		case "5":
			System.out.println("Goodbye");
			break;
		}
	}

}
