// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.introspector;

import java.util.HashMap;
import java.util.Map;

/**
 * Default {@link jodd.introspector.Introspector introspector} that caches all class descriptors.
 * It can examine either <b>accessible</b> or <b>supported</b> fields/methods/constructors.
 * <p>
 * It simply caches <b>all</b> class descriptors.
 */
public class CachingIntrospector implements Introspector {

	protected final Map<Class, ClassDescriptor> cache;
	protected final boolean scanAccessible;
	protected final boolean enhancedProperties;
	protected final boolean includeFieldsAsProperties;
	protected final String[] propertyFieldPrefix;

	/**
	 * Default constructor.
	 */
	public CachingIntrospector() {
		this(true, true, true, null);
	}

	/**
	 * Creates new caching {@link Introspector}. It may scan
	 * <b>accessible</b> or <b>supported</b> fields, methods or
	 * constructors.
	 */
	public CachingIntrospector(boolean scanAccessible, boolean enhancedProperties, boolean includeFieldsAsProperties, String[] propertyFieldPrefix) {
		this.cache = new HashMap<Class, ClassDescriptor>();
		this.scanAccessible = scanAccessible;
		this.enhancedProperties = enhancedProperties;
		this.includeFieldsAsProperties = includeFieldsAsProperties;
		this.propertyFieldPrefix = propertyFieldPrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	public ClassDescriptor lookup(Class type) {
		ClassDescriptor cd = cache.get(type);
		if (cd != null) {
			cd.increaseUsageCount();
			return cd;
		}
		cd = describeClass(type);
		cache.put(type, cd);
		return cd;
	}

	/**
	 * {@inheritDoc}
	 */
	public ClassDescriptor register(Class type) {
		ClassDescriptor cd = describeClass(type);
		cache.put(type, cd);
		return cd;
	}

	/**
	 * Describes a class by creating a new instance of {@link ClassDescriptor}
	 * that examines all accessible methods and fields.
	 */
	protected ClassDescriptor describeClass(Class type) {
		return new ClassDescriptor(type, scanAccessible, enhancedProperties, includeFieldsAsProperties, propertyFieldPrefix);
	}

	/**
	 * {@inheritDoc}
	 */
	public void reset() {
		cache.clear();
	}

}