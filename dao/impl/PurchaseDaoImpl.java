package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class PurchaseDaoImpl implements PurchaseDAO {

	final static String insertSQL = "INSERT INTO PURCHASE (purchaseDate, purchaseAmount, Customer_id, Product_id) VALUES (?, ?, ?, ?)";

	@Override
	public Purchase create(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// handling exception when the ID is null
		if (purchase.getId() != null) {
			throw new DAOException("Trying to insert Purchase with NON-NULL ID");
		}

		// initialize the statement
		PreparedStatement ps = null;
		try {
			// return the content of the empty space
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, purchase.getPurchaseDate());
			ps.setDouble(2, purchase.getPurchaseAmount());
			ps.setLong(3, purchase.getCustomerID());
			ps.setLong(4, purchase.getProductID());
			ps.executeUpdate();

			// REQUIREMENT: Copy the auto-increment primary key to the purchase
			// ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			purchase.setId((long) lastKey);

			return purchase;

			// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // end of create

	final static String retrieveSQL = "SELECT * FROM Purchase WHERE id = ?";

	@Override
	public Purchase retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// handling exception when the ID is null
		if (id == null) {
			throw new DAOException("Trying to retrieve Purchase with NULL ID");
		}

		// initialize the statement
		PreparedStatement ps = null;
		try {
			// return the content of the empty space
			ps = connection.prepareStatement(retrieveSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			// if there's nothing on the next, just return nothing
			if (!rs.next()) {
				return null;
			}

			// get the information from the database, and return it to Purchase class
			Purchase pur = new Purchase();
			pur.setId(rs.getLong("id"));
			pur.setPurchaseDate(rs.getDate("purchaseDate"));
			pur.setPurchaseAmount(rs.getDouble("purchaseAmount"));
			pur.setCustomerID(rs.getLong("Customer_id"));
			pur.setProductID(rs.getLong("Product_id"));
			return pur;

		// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // end of retrieve

	final static String updateSQL = "UPDATE Purchase SET purchaseDate = ?, purchaseAmount = ?, Customer_id = ?, Product_id = ? WHERE id = ?";

	@Override
	public int update(Connection connection, Purchase purchase) throws SQLException, DAOException {
		// handling exception when the ID is null
		if (purchase.getId() == null) {
			throw new DAOException("Trying to update purchase with NULL ID");
		}

		// initialize the statement
		PreparedStatement ps = null;

		try {
			// return the content of the empty space
			ps = connection.prepareStatement(updateSQL);
			ps.setDate(1, purchase.getPurchaseDate());
			ps.setDouble(2, purchase.getPurchaseAmount());
			ps.setLong(3, purchase.getCustomerID());
			ps.setLong(4, purchase.getProductID());
			ps.setLong(5, purchase.getId());
			int rows = ps.executeUpdate();
			return rows;
		// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // end of update

	final static String deleteSQL = "DELETE FROM Purchase WHERE Id = ?";

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// handling exception when the ID is null
		if (id == null) {
			throw new DAOException("Trying to delete purchase with NULL ID");
		}
		// initialize the statement
		PreparedStatement ps = null;
		try {
			// return the content of the empty space
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);
			int rows = ps.executeUpdate();
			return rows;
		// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	} // end of delete

	final static String selectCustomerID = "SELECT Purchase.id, Purchase.purchaseDate, Purchase.purchaseAmount, Purchase.Customer_id, Purchase.Product_id FROM purchase, customer WHERE Purchase.Customer_id = ? AND Purchase.Customer_id = Customer.id";

	@Override
	public List<Purchase> retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
		// handling exception
		if (customerID < 0) {
			throw new DAOException("trying to retrieve customer with no ID");
		}

		List<Purchase> purchase = new ArrayList<Purchase>();

		// declaring preparedStatement
		PreparedStatement ps = null;

		try {
			// passing the SQL statement to the database
			ps = connection.prepareStatement(selectCustomerID);
			// assigning the arguments to the SQL statement
			ps.setLong(1, customerID);
			// executing the SQL statement
			ResultSet rs = ps.executeQuery();

			// get the information from the database, and return it to Purchase class
			while (rs.next() == true) {
				Purchase pur = new Purchase();
				pur.setId(rs.getLong("id"));
				pur.setPurchaseDate(rs.getDate("purchaseDate"));
				pur.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				pur.setCustomerID(rs.getLong("Customer_id"));
				pur.setProductID(rs.getLong("Product_id"));
				purchase.add(pur);
			} // end of while loop
			return purchase;
		// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of retrieveForCustomerID

	final static String selectProductID = "SELECT Purchase.id, Purchase.purchaseDate, Purchase.purchaseAmount, Purchase.Customer_id, Purchase.Product_id FROM purchase, product WHERE Purchase.Product_id = ? AND purchase.Product_id = product.id";

	@Override
	public List<Purchase> retrieveForProductID(Connection connection, Long productID)
			throws SQLException, DAOException {
		List<Purchase> purchase = new ArrayList<Purchase>();

		// handling exception
		if (productID < 0) {
			throw new DAOException("Trying to retrieve product with Null ID");
		}

		// declaring preparedStatement
		PreparedStatement ps = null;

		try {
			// passing the SQL statement to the database
			ps = connection.prepareStatement(selectProductID);
			// assigning the arguments to the SQL statement
			ps.setLong(1, productID);
			// executing the SQL statement
			ResultSet rs = ps.executeQuery();

			// get the information from the database, and return it to Purchase class
			while (rs.next() == true) {
				Purchase pur = new Purchase();
				pur.setId(rs.getLong("id"));
				pur.setPurchaseDate(rs.getDate("purchaseDate"));
				pur.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				pur.setCustomerID(rs.getLong("Customer_id"));
				pur.setProductID(rs.getLong("Product_id"));
				purchase.add(pur);

			} // end of while loop
			return purchase;
		// if the statement is empty or if it's close then do nothing
		} finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} // end of Java exception
	} // end of retrieveForProductID

	final static String summary = "SELECT MIN(purchaseAmount), MAX(purchaseAmount), AVG(purchaseAmount) FROM purchase WHERE Customer_id = ?";

	@Override
	public PurchaseSummary retrievePurchaseSummary(Connection connection, Long customerID)
			throws SQLException, DAOException {
		// declare the list 
		List<Purchase> list = new ArrayList<Purchase>();
		list = retrieveForCustomerID(connection, customerID);
		// create purchaseSum object 
		PurchaseSummary purchaseSum = new PurchaseSummary();

		// declare variable 
		double min = 100000;
		double max = 0;
		double sum = 0;
		double count = 0;
		
		// get min and Max 
		for (Purchase p : list) {
			if (p.getPurchaseAmount() < min) {
				min = p.getPurchaseAmount();
			}
			if (p.getPurchaseAmount() > max) {
				max = p.getPurchaseAmount();
			}
			sum += p.getPurchaseAmount();
			count++;
		}
		// result 
		double avg = sum / count;
		purchaseSum.avgPurchase = (float) avg;
		purchaseSum.maxPurchase = (float) max;
		purchaseSum.minPurchase = (float) min;
		return purchaseSum;
	}
}
