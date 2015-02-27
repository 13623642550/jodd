// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.connection;

import jodd.db.DbSqlException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource connection provider. Note that data source implementation might
 * not support <code>dataSource.getConnection(username, password)</code>. 
 */
public class DataSourceConnectionProvider implements ConnectionProvider {

	private final DataSource dataSource;
	private final String username;
	private final String password;


	public DataSourceConnectionProvider(String jndiName) {
		this(jndiName, null, null);
	}

	public DataSourceConnectionProvider(String jndiName, String user, String pass) {
		try {
			InitialContext initialContext = new InitialContext();

			this.dataSource = (DataSource) initialContext.lookup(jndiName);
		} catch (NamingException nex) {
			throw new DbSqlException("Invalid JNDI datasource name: " + jndiName, nex);
		}
		this.username = user;
		this.password = pass;
	}

	public DataSourceConnectionProvider(DataSource dataSource) {
		this.dataSource = dataSource;
		username = password = null;
	}

	public DataSourceConnectionProvider(DataSource dataSource, String user, String pass) {
		this.dataSource = dataSource;
		this.username = user;
		this.password = pass;
	}

	public void init() {}

	public Connection getConnection() {
		try {
			if (username != null || password != null) {
				return dataSource.getConnection(username, password);
			} else {
				return dataSource.getConnection();
			}
		} catch (SQLException sex) {
			throw new DbSqlException("Invalid datasource connection", sex);
		}
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException sex) {
			// ignore
		}
	}

	public void close() {}
}
