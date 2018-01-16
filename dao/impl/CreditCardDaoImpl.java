package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class CreditCardDaoImpl implements CreditCardDAO
{
    // insertSQL method
	private static final String insertSQL = "INSERT INTO creditcard (name, ccNumber, expDate, securityCode, Customer_id) VALUES (?, ?, ?, ?, ?)";
	
	@Override
	public CreditCard create(Connection connection, CreditCard creditCard, Long customerID) throws SQLException, DAOException {
		
		// Initializes a prepared statement
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(insertSQL);
            // Creates new CreditCard object
			ps.setString(1, creditCard.getName());
			ps.setString(2, creditCard.getCcNumber());
			ps.setString(3, creditCard.getExpDate());
			ps.setString(4, creditCard.getSecurityCode());
			ps.setLong(5, customerID);
			ps.executeUpdate(); // Executes insertSQL

			return creditCard;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // End insertSQL method

	final static String retrieveSQL = "SELECT * FROM creditcard WHERE Customer_id = ?";
	
	@Override
	public CreditCard retrieveForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
        // Checks to see if customerID exists or not
		if (customerID == null) {
			throw new DAOException("Trying to retrieve creditcard with NULL ID");
		}
		
		PreparedStatement ps = null;
		
		try{
			ps = connection.prepareStatement(retrieveSQL);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery(); // Executes retrieveSQL
			if (!rs.next()) {
				return null; // Empty result set
			}

			CreditCard card = new CreditCard();
            // Fills CreditCard object from result set
			card.setName(rs.getString("name"));
			card.setCcNumber(rs.getString("ccNumber"));
			card.setExpDate(rs.getString("expDate"));
			card.setSecurityCode(rs.getString("securityCode"));
			
			return card;
		}
		finally{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // End of retrieveForCustomerID method
	}

    // deleteSQL method
	final static String deleteSQL = "DELETE FROM creditcard WHERE Customer_id = ?";
	
	@Override
	public void deleteForCustomerID(Connection connection, Long customerID) throws SQLException, DAOException {
		
		if (customerID == null) {
			throw new DAOException("Trying to delete creditcard with NULL ID");
		}
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);
			ps.executeUpdate(); // Executes deleteSQL
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // End of deleteForCustomerID method
}
