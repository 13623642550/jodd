// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.type;

import jodd.typeconverter.TypeConverterManager;
import jodd.util.ReflectUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * SQL type.
 */
public abstract class SqlType<T> {

	/**
	 * Indicator for not yet resolved DB SQL type.
	 */
	public static final int DB_SQLTYPE_UNKNOWN = Integer.MAX_VALUE;

	/**
	 * Indicator for unavailable DB SQL type. Used usually when sql type is not
	 * available by JDBC meta data or when column can not be matched
	 * (due to case-mismatching).
	 */
	public static final int DB_SQLTYPE_NOT_AVAILABLE = Integer.MIN_VALUE;


	protected Class<T> sqlType;

	@SuppressWarnings({"unchecked"})
	protected SqlType() {
		this.sqlType = ReflectUtil.getGenericSupertype(this.getClass());
	}

	/**
	 * Sets prepared statement value.
	 */
	public abstract void set(PreparedStatement st, int index, T value, int dbSqlType) throws SQLException;

	/**
	 * Returns value from result set.
	 * @param rs result set
	 * @param index column index
	 * @param dbSqlType java.sql.Types hint
	 */
	public abstract T get(ResultSet rs, int index, int dbSqlType) throws SQLException;


	/**
	 * Stores value in database. Value is casted to sql type.
	 */
	public void storeValue(PreparedStatement st, int index, Object value, int dbSqlType) throws SQLException {
		T t = TypeConverterManager.convertType(value, sqlType);
		set(st, index, t, dbSqlType);
	}

	/**
	 * Reads value from database. Value is casted to destination type.
	 * @param rs result set
	 * @param index database column index
	 * @param destinationType property type
	 * @param dbSqlType hint for column sql type value
	 */
	public <E> E readValue(ResultSet rs, int index, Class<E> destinationType, int dbSqlType) throws SQLException {
		T t = get(rs, index, dbSqlType);
		return prepareGetValue(t, destinationType);
	}

	/**
	 * Once when value is read from result set, prepare it to match destination type.
	 * @param t get value
	 * @param destinationType destination type
	 */
	@SuppressWarnings({"unchecked"})
	protected <E> E prepareGetValue(T t, Class<E> destinationType) {
		if (t == null) {
			return null;
		}
		if (destinationType == null) {
			return (E) t;
		}
		return TypeConverterManager.convertType(t, destinationType);
	}

}