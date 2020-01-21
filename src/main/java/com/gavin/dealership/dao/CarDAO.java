package com.gavin.dealership.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.pojo.Car;
import com.gavin.dealership.util.ConnectionFactory;

public class CarDAO {
	
	public static void addCar(Car car) {
		Connection conn = ConnectionFactory.getConnection();

		String sql = "insert into dealership.cars (make,model,year,price) values (?,?,?,?)";

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, car.getMake());
			stmt.setString(2, car.getModel());
			stmt.setInt(3, car.getYear());
			stmt.setDouble(4, car.getCost());
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
	
	public static void removeCar(int carid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "delete from dealership.cars c where c.carid = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, carid);
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
	
	public static Car getCar(int carid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select * from dealership.cars c where c.carid = ?";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		Car ret = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, carid);
			res = stmt.executeQuery();
			if(res.next()) {
				ret = new Car(res.getString("make"),res.getString("model"),res.getInt("year"),res.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public static void listCars() {
		Connection conn = ConnectionFactory.getConnection();

		String sql = "select * from dealership.cars c where c.owned_by=0";

		PreparedStatement stmt;

		ResultSet res = null;
		try {
			stmt = conn.prepareStatement(sql);
			res = stmt.executeQuery();
			while (res.next()) {
				System.out.println("[" + res.getString(1) + "] " + res.getInt("year") + " " + res.getString("make")
						+ " " + res.getString("model") + " Costs: $" + res.getDouble("price"));
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
	
	public static void changeOwner(int carid,int userid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "update dealership.cars set owned_by=? where carid=?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userid);
			stmt.setInt(2, carid);
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
	
}
