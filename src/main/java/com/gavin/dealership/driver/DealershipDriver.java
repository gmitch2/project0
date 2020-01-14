package com.gavin.dealership.driver;

import java.util.Scanner;

import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Employee;
import com.gavin.dealership.pojo.User;
import com.gavin.dealership.service.DealershipService;

public class DealershipDriver {
	
	private static Scanner sc = new Scanner(System.in);
	
	private static DealershipService dealership = new DealershipService();
	
	private static User currentUser;
	
	public static void main(String[] args) {
		String option = "";

		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] Login");
			System.out.println("[2] Add employee user");
			System.out.println("[3] Add customer user");
			System.out.println("[4] Delete user");
			System.out.println("[5] Exit");

			option = sc.nextLine();
			performSystemAction(option);
		} while (!"5".equals(option));
	}

	private static void performSystemAction(String option) {
		switch(option) {
		case "3":
			System.out.println("Username: ");
			String username = sc.nextLine();
			System.out.println("Password: ");
			String password = sc.nextLine();
			dealership.addUser(new Customer(username,password));
			break;
		case "2":
			System.out.println("Username: ");
			username = sc.nextLine();
			System.out.println("Password: ");
			password = sc.nextLine();
			dealership.addUser(new Employee(username,password));
			break;
		case "1":
			System.out.println("Username: ");
			username = sc.nextLine();
			System.out.println("Password: ");
			password = sc.nextLine();
			if(dealership.authenticateUser(username,password)) {
				System.out.println("Login successful!");
				currentUser = dealership.getUser(username);
				if(currentUser instanceof Customer) {
					String customerOption = "";

					do {
						System.out.println("What would you like to do?");
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
							System.out.println("What would you like to do?");
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
		case "5":
			System.out.println("Goodbye");
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
			String make = sc.nextLine();
			System.out.println("What is the model of the car");
			String model = sc.nextLine();
			System.out.println("What is the cost of the car");
			int cost = sc.nextInt();
			dealership.addCar(new Car(make,model,year,cost));
			break;
		case "3":
			System.out.println("What year is the car?");
			year = sc.nextInt();
			System.out.println("What is the make of the car");
			make = sc.nextLine();
			System.out.println("What is the model of the car");
			model = sc.nextLine();
			dealership.removeCar(make,model,year);
			break;
		case "4":
			dealership.listOffers();
			System.out.println("Would you like to accept (a) or reject (r) an offer?");
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
		case "2":
			System.out.println("What year is the car?");
			int year = sc.nextInt();
			System.out.println("What is the make of the car");
			String make = sc.nextLine();
			System.out.println("What is the model of the car");
			String model = sc.nextLine();
			System.out.println("What is your offer?");
			int offer = sc.nextInt();
			Car car = dealership.getCar(make, model, year);
			dealership.addOffer(currentUser,car,offer);
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
