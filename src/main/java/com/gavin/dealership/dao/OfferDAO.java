package com.gavin.dealership.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gavin.dealership.util.ConnectionFactory;
import com.gavin.dealership.util.LoggerUtil;

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
			LoggerUtil.info("added offer for "+amount);
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
			LoggerUtil.info("removed offer, offerid: "+offerid);
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
		
		String sql = "call accept_offer(?)";
		
		
		CallableStatement stmt = null;
		
		try {
			stmt = conn.prepareCall(sql);
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
