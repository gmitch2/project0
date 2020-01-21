package com.gavin.dealership.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.util.ConnectionFactory;

public class OfferDAO {
	
	public static void addOffer(int carid, int userid,double amount) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "insert into dealership.offers (carid,userid,amount) values (?,?,?)";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, carid);
			stmt.setInt(2, userid);
			stmt.setDouble(3, amount);
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
	
	public static void removeOffer(int offerid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "delete from dealership.offers o where o.offerid = ?";
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offerid);
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
	
	public static void listOffers(){
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "select o.offerid,u.username,c.make,c.model,c.year,o.amount from dealership.offers o join dealership.users u on u.userid=o.userid join dealership.cars c on c.carid=o.carid where o.offer_status=0";
		
		PreparedStatement stmt = null;
		
		ResultSet res = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			res = stmt.executeQuery();
			while (res.next()) {
				System.out.println("["+res.getInt("offerid")+"] "+res.getString(2)+" offers $"+res.getDouble(6)+" for "+res.getInt(5)+" "+res.getString(3)+" "+res.getString(4));
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
	
	public static void acceptOffer(int offerid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "update dealership.offers set offer_status=-1 where carid=(select carid from dealership.offers o where o.offerid=?)";
		
		String sql2 = "update dealership.offers set offer_status=1 where offerid=?";
		
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offerid);
			stmt.executeUpdate();
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, offerid);
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
	
	public static void rejectOffer(int offerid) {
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "update dealership.offers set offer_status=-1 where offerid=?";
		
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offerid);
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
