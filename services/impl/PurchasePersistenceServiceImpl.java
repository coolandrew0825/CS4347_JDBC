package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

//Class: CS 4347
//Project: JDBC
//Created by: Chien-Chi Liu (Andy)
//Date: 4/3/2017

public class PurchasePersistenceServiceImpl implements PurchasePersistenceService {
	private DataSource dataSource;
	
	// constructor 
	public PurchasePersistenceServiceImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Purchase create(Purchase purchase) throws SQLException, DAOException {
		// creating objects
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// create purchase DAO 
			Purchase pur = purchaseDAO.create(connect, purchase);
			connect.commit();
			return pur;
		// handling exception 
		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			// close connection 
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of create

	@Override
	public Purchase retrieve(Long id) throws SQLException, DAOException {
		// creating objects
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// create purchase DAO 
			Purchase pur = purchaseDAO.retrieve(connect, id);
			connect.commit();
			return pur;
		}
		// handling exception 
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
	} // end of retrieve

	@Override
	public int update(Purchase purchase) throws SQLException, DAOException {
		// creating objects
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// declare variable 
			int num = purchaseDAO.update(connect, purchase);
			connect.commit();
			return num;
		// handling exception 
		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close the exception 
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of update

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		// creating objects
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// declare variable 
			int num = purchaseDAO.delete(connect, id);

			// Commit the changes, and returned the rows deleted.
			connect.commit();
			return num;
		// handling exception 
		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close the connection 
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of delete

	@Override
	public List<Purchase> retrieveForCustomerID(Long customerID) throws SQLException, DAOException {
		// creating objects
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// declaring purchase list from retrieving puchaseDAO by customerID
			List<Purchase> pur = purchaseDAO.retrieveForCustomerID(connect, customerID);
			connect.commit();
			return pur;
		}
		// handling exception 
		catch (Exception ex){
			connect.rollback();
			throw ex;
		}
		finally {
			if (connect != null){
				connect.setAutoCommit(true);
			}
			// close the connection 
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of method

	@Override
	public PurchaseSummary retrievePurchaseSummary(Long customerID) throws SQLException, DAOException {
		// creating objects
		PurchaseDaoImpl purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// create summary objects 
			PurchaseSummary summary = purchaseDAO.retrievePurchaseSummary(connect, customerID);
			connect.commit();
			return summary;
		}
		// handling exception 
		catch (Exception ex){
			connect.rollback();
			throw ex;
		}
		finally {
			if (connect != null)
				connect.setAutoCommit(true);
			// close connection 
			if (connect != null && !connect.isClosed())
				connect.close();
		} // end of Java exception
	} // end of retrievePurchaseSummary

	@Override
	public List<Purchase> retrieveForProductID(Long productID) throws SQLException, DAOException {
		// creating objects
		PurchaseDAO purchaseDAO = new PurchaseDaoImpl();

		// setting up a connection
		Connection connect = dataSource.getConnection();

		try {
			connect.setAutoCommit(false);
			// declaring purchase list from retrieving productDAO by productID
			List<Purchase> pur = purchaseDAO.retrieveForProductID(connect, productID);
			connect.commit();
			return pur;
		} 
		// handling exception
		catch (Exception ex){
			connect.rollback();
			throw ex;
		}
		finally {
			if (connect != null){
				connect.setAutoCommit(true);
			}
			// close the connection 
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	}
}
