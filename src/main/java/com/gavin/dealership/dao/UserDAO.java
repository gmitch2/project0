package com.gavin.dealership.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.pojo.Customer;
import com.gavin.dealership.pojo.Employee;
import com.gavin.dealership.pojo.User;
import com.gavin.dealership.util.ConnectionFactory;
import com.gavin.dealership.util.LoggerUtil;

public class UserDAO {
	
	public static User getUser(String username) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.username = ?";
		
		PreparedStatement stmt;
		
		User ret = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			res = stmt.executeQuery();
			if(res.next()) {
				if(res.getBoolean(4)) {
					ret = new Employee(res.getString("username"),res.getString("user_pass"));
				} else {
					ret = new Customer(res.getString("username"),res.getString("user_pass"));
					((Customer)ret).setMonthlyPayment(res.getDouble("monthly_payment"));
					((Customer)ret).setRemainingBalance(res.getDouble("remaining_balance"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static User getUser(int userid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.userid = ?";
		
		PreparedStatement stmt;
		
		User ret = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			res = stmt.executeQuery();
			if(res.next()) {
				if(res.getBoolean(4)) {
					ret = new Employee(res.getString("username"),res.getString("user_pass"));
				} else {
					ret = new Customer(res.getString("username"),res.getString("user_pass"));
					((Customer)ret).setMonthlyPayment(res.getDouble("monthly_payment"));
					((Customer)ret).setRemainingBalance(res.getDouble("remaining_balance"));
				}
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
		
		return ret;
	}
	
	public static int getUserId(String username) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.username = ?";
		
		PreparedStatement stmt;
		
		int ret = 0;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			res = stmt.executeQuery();
			if(res.next()) {
				ret = res.getInt("userid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static String getUserName(int userid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.users u where u.userid = ?";
		
		PreparedStatement stmt;
		
		String ret = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			res = stmt.executeQuery();
			if(res.next()) {
				ret = res.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	
	public static void removeUser(String username) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "delete from dealership.users u where u.username = ?";
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.executeUpdate();
			LoggerUtil.info("removed user "+username);
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
	
	public static void addUser(User user, boolean isEmployee) {
		Connection conn = ConnectionFactory.getConnection();

		String sql = "insert into dealership.users (username, user_pass,is_employee) values (?,?,?)";

		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setBoolean(3, isEmployee);
			stmt.executeUpdate();
			LoggerUtil.info("added user "+user.getUsername());
		} catch (SQLException e) {
			System.out.println("Failed to create user");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
