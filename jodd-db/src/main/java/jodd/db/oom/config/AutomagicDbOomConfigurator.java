// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.oom.config;

import jodd.db.oom.DbOomManager;
import jodd.io.findfile.ClassFinder;
import jodd.db.oom.DbOomException;
import jodd.db.oom.meta.DbTable;
import jodd.util.ClassLoaderUtil;
import jodd.log.Logger;
import jodd.log.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * Auto-magically scans classpath for domain objects annotated with DbOom annotations.
 */
public class AutomagicDbOomConfigurator extends ClassFinder {

	private static final Logger log = LoggerFactory.getLogger(AutomagicDbOomConfigurator.class);

	protected final byte[] dbTableAnnotationBytes;
	protected final boolean registerAsEntities;

	public AutomagicDbOomConfigurator(boolean registerAsEntities) {
		dbTableAnnotationBytes = getTypeSignatureBytes(DbTable.class);
		this.registerAsEntities = registerAsEntities;
	}
	public AutomagicDbOomConfigurator() {
		this(true);
	}

	protected DbOomManager dbOomManager;

	protected long elapsed;

	/**
	 * Return elapsed number of milliseconds for configuration.
	 */
	public long getElapsed() {
		return elapsed;
	}

	/**
	 * Configures {@link DbOomManager} with specified class path.
	 * @see AutomagicDbOomConfigurator#configure(jodd.db.oom.DbOomManager)
	 */
	public void configure(DbOomManager dbOomManager, File[] classpath) {
		this.dbOomManager = dbOomManager;

		rulesEntries.smartMode();

		elapsed = System.currentTimeMillis();
		try {
			scanPaths(classpath);
		} catch (Exception ex) {
			throw new DbOomException("Scan classpath error", ex);
		}
		elapsed = System.currentTimeMillis() - elapsed;
		if (log.isInfoEnabled()) {
			log.info("DbOomManager configured in " + elapsed + " ms. Total entities: " + dbOomManager.getTotalNames());
		}
	}

	/**
	 * Configures {@link DbOomManager} with default class path.
	 * @see AutomagicDbOomConfigurator#configure(jodd.db.oom.DbOomManager, java.io.File[])
	 */
	public void configure(DbOomManager dbOomManager) {
		configure(dbOomManager, ClassLoaderUtil.getDefaultClasspath());
	}

	/**
	 * Scans all classes and registers only those annotated with {@link DbTable}.
	 * Because of performance purposes, classes are not dynamically loaded; instead, their
	 * file content is examined.
	 */
	@Override
	protected void onEntry(EntryData entryData) {
		String entryName = entryData.getName();
		InputStream inputStream = entryData.openInputStream();
		if (isTypeSignatureInUse(inputStream, dbTableAnnotationBytes) == false) {
			return;
		}

		Class<?> beanClass;
		try {
			beanClass = loadClass(entryName);
		} catch (ClassNotFoundException cnfex) {
			throw new DbOomException("Entry class not found: " + entryName, cnfex);
		}

		if (beanClass == null) {
			return;
		}

		DbTable dbTable = beanClass.getAnnotation(DbTable.class);
		if (dbTable == null) {
			return;
		}
		if (registerAsEntities == true) {
			dbOomManager.registerEntity(beanClass);
		} else {
			dbOomManager.registerType(beanClass);
		}
	}

}