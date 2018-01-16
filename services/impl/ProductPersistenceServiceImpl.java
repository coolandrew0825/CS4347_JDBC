package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.ProductPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private DataSource dataSource;

	public ProductPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Override
	public Product create(Product product) throws SQLException, DAOException {
		// initialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit to false
			connect.setAutoCommit(false);
			Product pro = productDAO.create(connect, product);
			connect.commit();
			return pro;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of create

	@Override
	public Product retrieve(Long id) throws SQLException, DAOException {
		// initialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit to false
			connect.setAutoCommit(false);
			Product pro = productDAO.retrieve(connect, id);
			connect.commit();
			return pro;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of retrieve

	@Override
	public int update(Product product) throws SQLException, DAOException {
		// intialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit to false
			connect.setAutoCommit(false);
			int num = productDAO.update(connect, product);
			connect.commit();
			return num;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of update

	@Override
	public int delete(Long id) throws SQLException, DAOException {
		// initialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit to false
			connect.setAutoCommit(false);
			int num = productDAO.delete(connect, id);
			connect.commit();
			return num;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of delete

	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException {
		// initialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit to false
			connect.setAutoCommit(false);
			Product pro = productDAO.retrieveByUPC(connect, upc);
			connect.commit();
			return pro;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of retrieve by UPC

	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException {
		// initialize product dao
		ProductDAO productDAO = new ProductDaoImpl();

		// setting up connection
		Connection connect = dataSource.getConnection();

		try {
			// set auto commit false
			connect.setAutoCommit(false);
			List<Product> pro = productDAO.retrieveByCategory(connect, category);
			connect.commit();
			return pro;

		} catch (Exception exception) {
			connect.rollback();
			throw exception;
		} finally {
			if (connect != null) {
				connect.setAutoCommit(true);
			}
			if (connect != null && !connect.isClosed()) {
				connect.close();
			}
		} // end of Java exception
	} // end of of retrieve by category
}
