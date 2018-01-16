package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.services.CustomerPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private DataSource dataSource;

	public CustomerPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * This method provided as an example of transaction support across multiple inserts.
	 * 
	 * Persists a new Customer instance by inserting new Customer, Address, 
	 * and CreditCard instances. Notice the transactional nature of this 
	 * method which includes turning off autocommit at the start of the 
	 * process, and rolling back the transaction if an exception 
	 * is caught. 
	 */
	@Override
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		// creating objects
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();
		
		try {
			connect.setAutoCommit(false);
			// retrieving customer
			Customer custom = customerDAO.create(connect, customer);
			Long customerID = custom.getId();

			
			if (custom.getCreditCard() == null) {
				throw new DAOException("Each customer instance must include an instance from the CreditCard table.");
			}
			
			if (custom.getAddress() == null) {
				throw new DAOException("Each customer instance must includes an instance from the address table.");
			}
			
			CreditCard creditCards = custom.getCreditCard();
			creditCardDAO.create(connect, creditCards, customerID);
			
			Address addresses = custom.getAddress();
			addressDAO.create(connect, addresses, customerID);

			connect.commit();
			
			return custom;
		}
		catch (Exception exception) {
			connect.rollback();
			throw exception;
		}
		finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			// close the connection
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of create method

	@Override
	public Customer retrieve(Long id) throws SQLException, DAOException {
		
		// creating objects
		CustomerDaoImpl custDao = new CustomerDaoImpl();
		CreditCardDaoImpl creditDao = new CreditCardDaoImpl();
		AddressDaoImpl addDao = new AddressDaoImpl();
		
		// setting up a connection
		Connection connect = dataSource.getConnection();
		
		try{
			connect.setAutoCommit(false);
			Customer custom = custDao.retrieve(connect,id);
			
			// check if the code actually retrieve customer
			if (custom == null){
				return null;
			}
			
			// getting credit card and address
			CreditCard creditCards = creditDao.retrieveForCustomerID(connect, id);
			Address addresses = addDao.retrieveForCustomerID(connect, id);
			
			custom.setCreditCard(creditCards);
			custom.setAddress(addresses);
			
			connect.commit();
			
			return custom;
		}
		finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			// close the connection
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of retrieve method

	@Override
	public int update(Customer customer) throws SQLException, DAOException {
		
		// variable declaration
		int num = 0;
		
		// creating objects
		CustomerDaoImpl custDao = new CustomerDaoImpl();
		CreditCardDaoImpl creditDao = new CreditCardDaoImpl();
		AddressDaoImpl addDao = new AddressDaoImpl();
				
		// setting up a connection
		Connection connect = dataSource.getConnection();
				
		try {
			connect.setAutoCommit(false);
			
			// delete credit card and address instance
			creditDao.deleteForCustomerID(connect, customer.getId());
			addDao.deleteForCustomerID(connect, customer.getId());
					
			num = custDao.update(connect, customer);
			
			creditDao.create(connect, customer.getCreditCard(), customer.getId());
			addDao.create(connect, customer.getAddress(), customer.getId());
			
			connect.commit();
			
			return num;
		}
		catch (Exception exception) {
			connect.rollback();
			throw exception;
		}
		finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close the connection
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of update method

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		
		// creating objects
		CustomerDaoImpl custDao = new CustomerDaoImpl();
		CreditCardDaoImpl creditDao = new CreditCardDaoImpl();
		AddressDaoImpl addDao = new AddressDaoImpl();
				
		// setting up a connection
		Connection connect = dataSource.getConnection();
				
		try {
			connect.setAutoCommit(false);
			
			// delete credit card and address instances with the customer ID
			creditDao.deleteForCustomerID(connect, id);
			addDao.deleteForCustomerID(connect, id);
					
			int num = custDao.delete(connect, id);
					
			// Commit the changes, and returned the rows deleted.
			connect.commit();
			return num;
		}
		catch (Exception exception) {
			connect.rollback();
			throw exception;
		}
		finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close the connection
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of delete method

	@Override
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException {
		
		// creating objects
		CustomerDaoImpl custDao = new CustomerDaoImpl();
		CreditCardDaoImpl creditDao = new CreditCardDaoImpl();
		AddressDaoImpl addDao = new AddressDaoImpl();
				
		// setting up a connection
		Connection connect = dataSource.getConnection();
				
		try {
			List<Customer> cust = custDao.retrieveByZipCode(connect, zipCode);
			
			for (Customer customer : cust) {
				// create association between credit card, customer, and address
				CreditCard creditCards = creditDao.retrieveForCustomerID(connect, customer.getId());
				Address addresses = addDao.retrieveForCustomerID(connect, customer.getId());
				
				customer.setCreditCard(creditCards);
				customer.setAddress(addresses);
			} // end of for loop
			return cust;
		}
		finally {
			// close the connection
			if (connect != null && !connect.isClosed()){
				connect.close();
			}
		} // end of Java exception
	} // end of retrieveByZipCode

	@Override
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException {
		
		// creating objects
		CustomerDaoImpl custDao = new CustomerDaoImpl();
		CreditCardDaoImpl creditDao = new CreditCardDaoImpl();
		AddressDaoImpl addDao = new AddressDaoImpl();
				
		// setting up a connection
		Connection connect = dataSource.getConnection();
				
		try {
			connect.setAutoCommit(false);
			
			// create a list
			List<Customer> cust = custDao.retrieveByDOB(connect, startDate, endDate);
			
			for (Customer customer : cust) {
				// create association between customer, credit card, and address
				CreditCard creditCard = creditDao.retrieveForCustomerID(connect, customer.getId());
				customer.setCreditCard(creditCard);
				Address address = addDao.retrieveForCustomerID(connect, customer.getId());
				customer.setAddress(address);
			} // end of for loop

			connect.commit();
			return cust;
		}
		finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close the connection
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of retrieveByDOB method
}
