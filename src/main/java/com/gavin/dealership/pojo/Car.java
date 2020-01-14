package com.gavin.dealership.pojo;

public class Car {
	
	private String make;
	
	private String model;
	
	private int year;
	
	private int cost;

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Car(String make, String model, int year, int cost) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "" + year + " " + make + " " + model + " Costs: $" + cost;
	}
	

}
