package com.gavin.dealership.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {
	
	private List<Car> owned = new ArrayList<Car>();
	
	private int remainingPayment;
	
	private int monthsToPay;
	
	private int monthlyPayment;
	
	public void addCar(Car car) {
		owned.add(car);
	}

	public List<Car> getOwned() {
		return owned;
	}

	public void setOwned(List<Car> owned) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + monthlyPayment;
		result = prime * result + monthsToPay;
		result = prime * result + ((owned == null) ? 0 : owned.hashCode());
		result = prime * result + remainingPayment;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (monthlyPayment != other.monthlyPayment)
			return false;
		if (monthsToPay != other.monthsToPay)
			return false;
		if (owned == null) {
			if (other.owned != null)
				return false;
		} else if (!owned.equals(other.owned))
			return false;
		if (remainingPayment != other.remainingPayment)
			return false;
		return true;
	}

	public int makePayment() {
		if(this.remainingPayment==0) {
			this.remainingPayment=0;
			this.monthsToPay=0;
			this.monthlyPayment=0;
			return 0;
		} else {
			int updatedRemaining=this.remainingPayment-monthlyPayment;
			if(updatedRemaining<0) {
				this.remainingPayment=0;
			} else {
				this.remainingPayment=updatedRemaining;
			}
			if(this.monthsToPay>0) {
				this.monthsToPay-=1;
			} else {
				this.monthsToPay=0;
			}
		}
		return this.remainingPayment;
	}

	
	
	

}
