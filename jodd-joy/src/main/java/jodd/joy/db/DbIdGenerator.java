// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.joy.db;

import jodd.db.oom.DbEntityDescriptor;
import jodd.db.oom.DbOomManager;
import jodd.db.oom.DbOomQuery;
import jodd.mutable.MutableLong;
import jodd.petite.meta.PetiteBean;
import jodd.log.Logger;
import jodd.log.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static jodd.db.oom.DbOomQuery.query;

/**
 * Database in-memory next ID generator.
 * It finds the last id (max) for every entity on first use.
 * Then it just keeps the count in the memory.
 */
@PetiteBean
public class DbIdGenerator {

	private static final Logger log = LoggerFactory.getLogger(DbIdGenerator.class);

	protected Map<Class<?>, MutableLong> entityIdsMap = new HashMap<Class<?>, MutableLong>();

	/**
	 * Resets all stored data.
	 */
	public synchronized void reset() {
		entityIdsMap.clear();
	}

	/**
	 * Returns next ID for given entity type.
	 * On the first call, it finds the max value of all IDs and stores it.
	 * On later calls, stored id is incremented and returned.
	 */
	public synchronized long nextId(Class entityType) {
		MutableLong lastId = entityIdsMap.get(entityType);
		if (lastId == null) {
			DbOomManager dbOomManager = DbOomManager.getInstance();

			DbEntityDescriptor ded = dbOomManager.lookupType(entityType);
			String tableName = ded.getTableName();
			String idColumn = ded.getIdColumnName();

			DbOomQuery dbOomQuery = query("select max(" + idColumn + ") from " + tableName);

			long lastLong = dbOomQuery.autoClose().executeCount();

			if (log.isDebugEnabled()) {
				log.debug("Last id for " + entityType.getName() + " is " + lastLong);
			}

			lastId = new MutableLong(lastLong);

			entityIdsMap.put(entityType, lastId);
		}

		lastId.value++;
		return lastId.value;
	}

}