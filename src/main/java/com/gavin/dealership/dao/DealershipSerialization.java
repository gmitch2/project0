package com.gavin.dealership.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.gavin.dealership.service.DealershipService;

public class DealershipSerialization implements Serializable {

	
	public void createDealership(DealershipService dealership) {
		
		String filename = "Dealership.dat";
		
		try (FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(dealership);
			System.out.println("saved Dealership.dat");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public DealershipService readDealership() {
		String filename = "Dealership.dat";
		
		DealershipService ret = null;
		
		try (FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			ret = (DealershipService) ois.readObject();
		} catch (Exception e) {
			return new DealershipService();
		}
		
		return ret;
	}
}
