package com.gavin.dealership.pojo;

public class Customer extends User {
	
	private Car[] owned;
	
	private int remainingPayment;
	
	private int monthsToPay;
	
	private int monthlyPayment;

	public Car[] getOwned() {
		return owned;
	}

	public void setOwned(Car[] owned) {
		this.owned = owned;
	}

	public int getRemainingPayment() {
		return remainingPayment;
	}

	public void setRemainingPayment(int remainingPayment) {
		this.remainingPayment = remainingPayment;
	}

	public int getMonthsToPay() {
		return monthsToPay;
	}

	public void setMonthsToPay(int monthsToPay) {
		this.monthsToPay = monthsToPay;
	}

	public int getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(int monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Customer(String username, String password) {
		super(username, password);
	}
	
	

}
