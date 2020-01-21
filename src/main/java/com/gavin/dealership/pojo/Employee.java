package com.gavin.dealership.pojo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.dao.CarDAO;
import com.gavin.dealership.dao.OfferDAO;
import com.gavin.dealership.dao.UserDAO;
import com.gavin.dealership.util.ConnectionFactory;

public class Employee extends User implements Serializable {

	
	
	public Employee(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}
	
	public void acceptOffer(int offerid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String offersql = "select * from dealership.offers o where o.offerid=?";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(offersql);
			stmt.setInt(1, offerid);
			res = stmt.executeQuery();
			if(res.next()) {
				int userid = res.getInt("userid");
				double amount = res.getDouble("amount");
				int carid = res.getInt("carid");
				
				Customer customer = (Customer)UserDAO.getUser(userid);
				customer.setMonthlyPayment(customer.getMonthlyPayment()+(amount/24.0));
				customer.setRemainingBalance(customer.getRemainingBalance()+amount);
				
				OfferDAO.acceptOffer(offerid);
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
	
}
