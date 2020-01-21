package com.gavin.dealership.pojo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gavin.dealership.dao.PaymentDAO;
import com.gavin.dealership.dao.UserDAO;
import com.gavin.dealership.util.ConnectionFactory;

public class Customer extends User implements Serializable {
	
	private List<Car> owned = new ArrayList<Car>();
	
	private double remainingBalance;
	
	private int monthsToPay;
	
	private double monthlyPayment;
	
	public void addCar(Car car) {
		owned.add(car);
	}

	public void getOwned() {
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.cars c where c.owned_by = ?";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, UserDAO.getUserId(this.getUsername()));
			res = stmt.executeQuery();
			while(res.next()) {
				System.out.println("["+res.getInt("carid")+"] "+res.getInt("year")+" "+res.getString("make")+" "+res.getString("model"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setOwned(List<Car> owned) {
		this.owned = owned;
	}

	public double getRemainingBalance() {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.username = ?";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		double remainingBalance = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, this.getUsername());
			res = stmt.executeQuery();
			if(res.next()) {
				remainingBalance = res.getDouble("remaining_balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return remainingBalance;
	}

	public void setRemainingBalance(double remainingBalance) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "update dealership.users set remaining_balance=? where username=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, remainingBalance);
			stmt.setString(2, this.getUsername());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public int getMonthsToPay() {
		return monthsToPay;
	}

	public void setMonthsToPay(int monthsToPay) {
		this.monthsToPay = monthsToPay;
	}

	public double getMonthlyPayment() {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.username = ?";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		double monthlyPayment = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, this.getUsername());
			res = stmt.executeQuery();
			if(res.next()) {
				monthlyPayment = res.getDouble("monthly_payment");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return monthlyPayment;
	}

	public void setMonthlyPayment(double monthlyPayment) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "update dealership.users set monthly_payment=? where username=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, monthlyPayment);
			stmt.setString(2, this.getUsername());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Customer(String username, String password) {
		super(username, password);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		double result = super.hashCode();
		result = prime * result + monthlyPayment;
		result = prime * result + monthsToPay;
		result = prime * result + ((owned == null) ? 0 : owned.hashCode());
		result = prime * result + remainingBalance;
		return (int)result;
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
		if (remainingBalance != other.remainingBalance)
			return false;
		return true;
	}

	public double makePayment() {
		if(this.getRemainingBalance()==0) {
			this.setRemainingBalance(0);
			this.monthsToPay=0;
			this.setMonthlyPayment(0);
			return 0;
		} else {
			double updatedRemaining=this.getRemainingBalance()-this.getMonthlyPayment();
			if(updatedRemaining<0) {
				this.setRemainingBalance(0);
			} else {
				this.setRemainingBalance(updatedRemaining);
			}
			if(this.monthsToPay>0) {
				this.monthsToPay-=1;
			} else {
				this.monthsToPay=0;
			}
		}
		
		PaymentDAO.addPayment(UserDAO.getUserId(this.getUsername()),this.getMonthlyPayment());
		
		return this.getRemainingBalance();
	}

	
	
	

}
