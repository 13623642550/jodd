// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.oom;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbId;
import jodd.db.oom.meta.DbMapTo;
import jodd.db.oom.meta.DbTable;
import jodd.db.oom.naming.ColumnNamingStrategy;
import jodd.db.oom.naming.TableNamingStrategy;
import jodd.db.type.SqlType;
import jodd.introspector.PropertyDescriptor;

/**
 * Meta-data resolving utils.
 */
public class DbMetaUtil {

	/**
	 * Resolves table name from a type. If type is annotated, table name
	 * will be read from annotation value. If this value is empty or if
	 * type is not annotated, table name will be set to wildcard pattern '*'
	 * (to match all tables).
	 */
	public static String resolveTableName(Class<?> type, TableNamingStrategy tableNamingStrategy) {
		String tableName = null;
		DbTable dbTable = type.getAnnotation(DbTable.class);
		if (dbTable != null) {
			tableName = dbTable.value().trim();
		}
		if ((tableName == null) || (tableName.length() == 0)) {
			tableName = tableNamingStrategy.convertEntityNameToTableName(type);
		} else {
			if (!tableNamingStrategy.isStrictAnnotationNames()) {
				tableName = tableNamingStrategy.applyToTableName(tableName);
			}
		}
		return tableName;
	}

	/**
	 * Resolves schema name from a type. Uses default schema name if not specified.
	 */
	public static String resolveSchemaName(Class<?> type, String defaultSchemaName) {
		String schemaName = null;
		DbTable dbTable = type.getAnnotation(DbTable.class);
		if (dbTable != null) {
			schemaName = dbTable.schema().trim();
		}
		if ((schemaName == null) || (schemaName.length() == 0)) {
			schemaName = defaultSchemaName;
		}
		return schemaName;
	}

	/**
	 * Returns <code>true</code> if class is annotated with <code>DbTable</code> annotation.
	 */
	public static boolean resolveIsAnnotated(Class<?> type) {
		DbTable dbTable = type.getAnnotation(DbTable.class);
		return dbTable != null;
	}

	/**
	 * Resolves column descriptor from property. If property is annotated value will be read
	 * from annotation. If property is not annotated, then property will be ignored
	 * if entity is annotated. Otherwise, column name is generated from the property name.
	 */
	public static DbEntityColumnDescriptor resolveColumnDescriptors(
			DbEntityDescriptor dbEntityDescriptor,
			PropertyDescriptor property,
			boolean isAnnotated,
			ColumnNamingStrategy columnNamingStrategy) {

		String columnName = null;
		boolean isId = false;
		Class<? extends SqlType> sqlTypeClass = null;

		// read ID annotation

		DbId dbId = null;

		if (property.getFieldDescriptor() != null) {
			dbId = property.getFieldDescriptor().getField().getAnnotation(DbId.class);
		}
		if (dbId == null && property.getReadMethodDescriptor() != null) {
			dbId = property.getReadMethodDescriptor().getMethod().getAnnotation(DbId.class);
		}
		if (dbId == null && property.getWriteMethodDescriptor() != null) {
			dbId = property.getWriteMethodDescriptor().getMethod().getAnnotation(DbId.class);
		}

		if (dbId != null) {
			columnName = dbId.value().trim();
			sqlTypeClass = dbId.sqlType();
			isId = true;
		} else {
			DbColumn dbColumn = null;

			if (property.getFieldDescriptor() != null) {
				dbColumn = property.getFieldDescriptor().getField().getAnnotation(DbColumn.class);
			}
			if (dbColumn == null && property.getReadMethodDescriptor() != null) {
				dbColumn = property.getReadMethodDescriptor().getMethod().getAnnotation(DbColumn.class);
			}
			if (dbColumn == null && property.getWriteMethodDescriptor() != null) {
				dbColumn = property.getWriteMethodDescriptor().getMethod().getAnnotation(DbColumn.class);
			}

			if (dbColumn != null) {
				columnName = dbColumn.value().trim();
				sqlTypeClass = dbColumn.sqlType();
			} else {
				if (isAnnotated == true) {
					return null;
				}
			}
		}

		if ((columnName == null) || (columnName.length() == 0)) {
			columnName = columnNamingStrategy.convertPropertyNameToColumnName(property.getName());
		} else {
			if (!columnNamingStrategy.isStrictAnnotationNames()) {
				columnName = columnNamingStrategy.applyToColumnName(columnName);
			}
		}
		if (sqlTypeClass == SqlType.class) {
			sqlTypeClass = null;
		}

		return new DbEntityColumnDescriptor(
				dbEntityDescriptor, columnName, property.getName(), property.getType(), isId, sqlTypeClass);
	}

	/**
	 * Resolves mapped types from {@link jodd.db.oom.meta.DbMapTo} annotation.
	 */
	public static Class[] resolveMappedTypes(Class type) {
		DbMapTo dbMapTo = (DbMapTo) type.getAnnotation(DbMapTo.class);
		if (dbMapTo == null) {
			return null;
		}
		return dbMapTo.value();
	}
}