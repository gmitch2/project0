package com.gavin.dealership.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.util.ConnectionFactory;

public class PaymentDAO {
	
	public static void addPayment(int userid, double amount) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "insert into dealership.payments (amount,userid) values (?,?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, amount);
			stmt.setInt(2, userid);
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
	
	public static void listPayments() {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select u.username,p.amount,p.dated from dealership.payments p join dealership.users u on u.userid=p.userid";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			res = stmt.executeQuery();
			while(res.next()) {
				System.out.println(res.getString(1)+" payed $"+res.getDouble(2)+" on "+res.getDate(3));
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
