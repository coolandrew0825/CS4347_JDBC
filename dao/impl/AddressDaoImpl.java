package cs4347.jdbcProject.ecomm.dao.impl;

// Class: CS 4347
// Project: JDBC
// Created by: Sean Wali
// Date: 4/3/2017

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class AddressDaoImpl implements AddressDAO
{
	// create a SQL query for create method
	private static final String insertSQL = "INSERT INTO Address (address1, address2, city, state, zipcode, Customer_id) VALUES (?, ?, ?, ?, ?, ?)";
	
	@Override
	public Address create(Connection connection, Address address, Long customerID) throws SQLException, DAOException {
		
		// create a preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(insertSQL);
			ps.setString(1, address.getAddress1());
			ps.setString(2, address.getAddress2());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getState());
			ps.setString(5, address.getZipcode());
			ps.setLong(6, customerID);
			ps.executeUpdate();
			
			return address;
		}
		finally {
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of create method

	// create a SQL query for retrieve method
	final static String retrieveSQL = "SELECT * FROM Address WHERE Customer_id = ?";
	
	@Override
	public Address retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		// null id exception handling
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Address with NULL ID");
		}
		
		// create a preparedStatement
		PreparedStatement ps = null;
		
		try{
			// execute a query
			ps = connection.prepareStatement(retrieveSQL);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			Address address = new Address();
			address.setAddress1(rs.getString("address1"));
			address.setAddress2(rs.getString("address2"));
			address.setCity(rs.getString("city"));
			address.setState(rs.getString("state"));
			address.setZipcode(rs.getString("zipcode"));
			return address;
		}
		finally{
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of retrieveForCustomerID method

	// create a SQL query for delete method
	final static String deleteSQL = "DELETE FROM Address WHERE Customer_id = ?";
	
	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		
		// null id exception handling
		if (customerID == null) {
			throw new DAOException("Trying to delete Address with NULL ID");
		}
		
		// create a preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);
			ps.executeUpdate();
		}
		finally {
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of deleteForCustomerID method
}