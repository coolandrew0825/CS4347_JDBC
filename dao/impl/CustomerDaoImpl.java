package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerDaoImpl implements CustomerDAO
{
	// creating the SQL query for create method
	private static final String insertSQL = "INSERT INTO CUSTOMER (firstName, lastName, gender, dob, email) VALUES (?, ?, ?, ?, ?)";
	
	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		
		// checking non null ID
		if (customer.getId() != null) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}
		
		// declaring a preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			
			// filling in the question mark in the SQL statement
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			
			ps.executeUpdate();
			
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);
			
			return customer;
		}
		finally {
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of create method

	// creating the SQL query for retrieve method
	final static String retrieveSQL = "SELECT * FROM Customer WHERE id = ?";
	
	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		
		// checking the value of id
		if (id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}
		
		// declaring a preparedStatement
		PreparedStatement ps = null;
		
		try{
			// execute a query
			ps = connection.prepareStatement(retrieveSQL);
			// filling in the question mark in the SQL statement
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			Customer cust = new Customer();
			
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstName"));
			cust.setLastName(rs.getString("lastName"));
			cust.setGender(rs.getString("gender").charAt(0));
			cust.setDob(rs.getDate("dob"));
			cust.setEmail(rs.getString("email"));
			
			return cust;
		}
		finally{
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of retrieve method

	// creating the SQL query for update method
	final static String updateSQL = "UPDATE customer SET firstName = ?, lastName = ?, gender = ?, dob = ?, email = ? WHERE id = ?";
	
	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		
		// checking the value of id
		if (customer.getId() == null) {
			throw new DAOException("Trying to update Customer with NULL ID");
		}
		
		// declaring a preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(updateSQL);
			
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setLong(6, customer.getId());
			int rows = ps.executeUpdate();
			
			return rows;
		}
		finally {
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of update method

	// creating the SQL query for delete method
	final static String deleteSQL = "DELETE FROM CUSTOMER WHERE ID = ?";
	
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		
		// checking the value of id
		if (id == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}
		
		// declaring a preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(deleteSQL);
			// assigning the arguments to the SQL statement
			ps.setLong(1, id);
			int rows = ps.executeUpdate();
			
			return rows;
		}
		finally {
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of delete method

	// creating the SQL query for retrieveByZipCode method
	final static String selectZipCode = "SELECT customer.id, customer.firstName, customer.lastName, customer.gender, customer.dob, customer.email FROM customer, address WHERE address.zipcode = ? AND customer.id = address.customer_id";
	
	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		
		// declaring preparedStatement
		PreparedStatement ps = null;
		
		try{
			// execute a query
			ps = connection.prepareStatement(selectZipCode);
			// assigning the arguments to the SQL statement
			ps.setString(1, zipCode);
			ResultSet rs = ps.executeQuery();
			
			List<Customer> customers = new ArrayList<Customer>();
			
			while(rs.next() == true){
				Customer cust = new Customer();
				
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstName"));
				cust.setLastName(rs.getString("lastName"));
				cust.setGender(rs.getString("gender").charAt(0));
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				
				customers.add(cust);
			} // end of while loop
			return customers;
		}
		finally{
			// close preparedStatement
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of retrieveByZipCode method

	// creating the SQL query for retrieveDOB method
	final static String retrieveDOB = "SELECT * FROM CUSTOMER WHERE dob BETWEEN ? AND ?";
	
	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate) throws SQLException, DAOException {
		
		// declaring preparedStatement
		PreparedStatement ps = null;
		
		try {
			// execute a query
			ps = connection.prepareStatement(retrieveDOB);
			// assigning the arguments to the SQL statement
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			ResultSet rs = ps.executeQuery();
			
			List<Customer> customers = new ArrayList<Customer>();
			
			while (rs.next() == true) {
				Customer cust = new Customer();
				
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstName"));
				cust.setLastName(rs.getString("lastName"));
				cust.setGender(rs.getString("gender").charAt(0));
				cust.setDob(rs.getDate("dob"));
				cust.setEmail(rs.getString("email"));
				
				customers.add(cust);
			} // end of while loop
			return customers;
		}
		finally {
			// close preparedStatement
			if (ps != null && ps.isClosed() == false){
				ps.close();
			}
		} // end of Java exception
	} // end of retrieveByDOB method
}
