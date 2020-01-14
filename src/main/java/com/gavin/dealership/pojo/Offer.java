package com.gavin.dealership.pojo;

import java.io.Serializable;

public class Offer implements Serializable{
	
	private Car car;
	
	private int months;
	
	private int monthlyPayment;
	
	private Customer customer;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(int monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return customer.getUsername()+" offered a bid of: $" + monthlyPayment + " for "+months+" months for " + car;
	}

	public Offer(Car car, int monthlyPayment, int months, Customer customer) {
		super();
		this.car = car;
		this.months = months;
		this.monthlyPayment = monthlyPayment;
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car == null) ? 0 : car.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + monthlyPayment;
		result = prime * result + months;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (monthlyPayment != other.monthlyPayment)
			return false;
		if (months != other.months)
			return false;
		return true;
	}

}
