package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class ProductDaoImpl implements ProductDAO
{
	private static final String insertSQL = "INSERT INTO PRODUCT (prodName, prodDescription, prodCategory, prodUPC) VALUES (?, ?, ?, ?)";

	@Override
	public Product create(Connection connection, Product product) throws SQLException, DAOException {
		
		// check if product it is not null
		if (product.getId() != null) {
			throw new DAOException("Trying to insert Product with NON-NULL ID");
		}
		// create prepared statement
		PreparedStatement ps = null;
		
		try {
			// set up connect with prepared statement using insertSQL
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.executeUpdate();
			
			// retrieves the auto generated primary key
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			product.setId((long) lastKey);
			
			return product;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	final static String retrieveSQL = "SELECT * FROM Product WHERE id = ?";
	
	@Override
	public Product retrieve(Connection connection, Long id) throws SQLException, DAOException {
		// chcek if id is null
		if (id == null) {
			throw new DAOException("Trying to retrieve Product with NULL ID");
		}
		
		// create prepared statement
		PreparedStatement ps = null;
		
		try{
			ps = connection.prepareStatement(retrieveSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			// retrieves the product information that was selected from the result set
			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setProdName(rs.getString("prodName"));
			product.setProdDescription(rs.getString("prodDescription"));
			product.setProdCategory(rs.getInt("prodCategory"));
			product.setProdUPC(rs.getString("prodUPC"));
			return product;
		}
		finally{
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	final static String updateSQL = "UPDATE Product SET prodName = ?, prodDescription = ?, prodCategory = ?, prodUPC = ? WHERE id = ?";
	
	@Override
	public int update(Connection connection, Product product) throws SQLException, DAOException {
		// check if given products id is null
		if (product.getId() == null) {
			throw new DAOException("Trying to update Product with NULL ID");
		}
		// create prepared statement
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, product.getProdName());
			ps.setString(2, product.getProdDescription());
			ps.setInt(3, product.getProdCategory());
			ps.setString(4, product.getProdUPC());
			ps.setLong(5, product.getId());
			// retrieve number of rows that were affected by the update
			int rows = ps.executeUpdate();
			
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	final static String deleteSQL = "DELETE FROM Product WHERE ID = ?";
	
	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// check if id is null
		if (id == null) {
			throw new DAOException("Trying to delete Product with NULL ID");
		}
		// create prepared statement
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);
			// retrieve number of rows affected
			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveByCategorySQL = "SELECT * FROM Product WHERE prodCategory = ?";
	
	@Override
	public List<Product> retrieveByCategory(Connection connection, int category) throws SQLException, DAOException {
		// create prepared statement
		PreparedStatement ps = null;
		// initialize list of products to be returned
		List<Product> result = new ArrayList<Product>();
		try {
			ps = connection.prepareStatement(retrieveByCategorySQL);
			ps.setInt(1,category);
			ResultSet rs = ps.executeQuery();
			
			// retrieve all products in the result set
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setProdName(rs.getString("prodName"));
				product.setProdDescription(rs.getString("prodDescription"));			
				product.setProdCategory(rs.getInt("prodCategory"));
				product.setProdUPC(rs.getString("prodUPC"));
				result.add(product);
			}
			// return the list of products
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	private static final String retrieveByUPCSQL = "SELECT * FROM Product WHERE prodUPC = ?";	
	
	@Override
	public Product retrieveByUPC(Connection connection, String upc) throws SQLException, DAOException {
		// create prepared statement
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(retrieveByUPCSQL);
			ps.setString(1,upc);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {
				return null;
			}
			
			Product product = new Product();
			product.setId(rs.getLong("id"));
			product.setProdName(rs.getString("prodName"));
			product.setProdDescription(rs.getString("prodDescription"));			
			product.setProdCategory(rs.getInt("prodCategory"));
			product.setProdUPC(rs.getString("prodUPC"));
			// return product retrieved by upc
			return product;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
}
