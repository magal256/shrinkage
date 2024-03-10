package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HsqldbDataSource extends org.apache.commons.dbcp.BasicDataSource {	
	
	public HsqldbDataSource() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(super.url, "sa", "");
	}
	
	@Override
	public Connection getConnection(String user, String pass)
										throws SQLException {
		return DriverManager.getConnection(super.url, user, pass);
	}
}
