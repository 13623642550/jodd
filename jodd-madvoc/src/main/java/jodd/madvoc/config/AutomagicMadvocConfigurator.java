// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.config;

import jodd.introspector.ClassDescriptor;
import jodd.introspector.ClassIntrospector;
import jodd.introspector.MethodDescriptor;
import jodd.io.findfile.ClassFinder;
import jodd.madvoc.MadvocException;
import jodd.madvoc.WebApplication;
import jodd.madvoc.component.ActionsManager;
import jodd.madvoc.component.MadvocConfig;
import jodd.madvoc.component.ResultsManager;
import jodd.madvoc.meta.ActionAnnotation;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.result.ActionResult;
import jodd.util.ClassLoaderUtil;
import jodd.util.ReflectUtil;
import jodd.petite.meta.PetiteInject;
import jodd.log.Logger;
import jodd.log.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Default Madvoc configurator uses auto-magic to configure {@link WebApplication}.
 * It searches the class path for all classes which names ends with 'Action' and 'Result'
 * suffixes. Each such class will be loaded and introspected to determine
 * if it represents valid Madvoc entity and then registered into the web application.
 * <p>
 * Action class is scanned for the {@link MadvocAction}. All public methods with {@link Action}
 * are registered as Madvoc actions.
 */
public class AutomagicMadvocConfigurator extends ClassFinder implements MadvocConfigurator {

	private static final Logger log = LoggerFactory.getLogger(AutomagicMadvocConfigurator.class);

	@PetiteInject
	protected MadvocConfig madvocConfig;

	@PetiteInject
	protected ActionsManager actionsManager;

	@PetiteInject
	protected ResultsManager resultsManager;

	protected String actionClassSuffix;         // default action class suffix, for class path search
	protected String resultClassSuffix;         // default action result class suffix, for class path search
	protected long elapsed;

	public AutomagicMadvocConfigurator() {
		actionClassSuffix = "Action";
		resultClassSuffix = "Result";
		elapsed = 0;
	}

	/**
	 * Configures web application from system classpath
	 * @see #configure(java.io.File[])
	 */
	public void configure() {
		configure(ClassLoaderUtil.getDefaultClasspath());
	}

	/**
	 * Configures web application from specified classpath. The whole process is done in the following steps:
	 * <ol>
	 * <li>scanning web application classpath</li>
	 * <li>invoking external configurations, if exist</li>
	 * <li>applying defaults</li>
	 * </ol>
	 * @see #configure()
	 */
	public void configure(File[] classpath) {
		elapsed = System.currentTimeMillis();

		try {
			scanPaths(classpath);
		} catch (Exception ex) {
			throw new MadvocException("Scan classpath error", ex);
		}
		elapsed = System.currentTimeMillis() - elapsed;
		log.info("Madvoc configured in " + elapsed + " ms. Total actions: " + actionsManager.getActionsCount());
	}


	/**
	 * Parses class name that matches madvoc-related names.
	 */
	@Override
	protected void onEntry(EntryData entryData) {
		String entryName = entryData.getName();
		if (entryName.endsWith(actionClassSuffix) == true) {
			try {
				onActionClass(entryName);
			} catch (ClassNotFoundException cnfex) {
				throw new MadvocException("Invalid Madvoc action class: " + entryName, cnfex);
			}
		} else if (entryName.endsWith(resultClassSuffix) == true) {
			try {
				onResultClass(entryName);
			} catch (ClassNotFoundException cnfex) {
				throw new MadvocException("Invalid Madvoc result class: " + entryName, cnfex);
			}
		}
	}

	// ---------------------------------------------------------------- class check

	/**
	 * Determines if class should be examined for Madvoc annotations.
	 * Array, anonymous, primitive, interfaces and so on should be
	 * ignored. Sometimes, checking may fail due to e.g. <code>NoClassDefFoundError</code>;
	 * we should continue searching anyway.
	 */
	public boolean checkClass(Class clazz) {
		try {
			if (clazz.isAnonymousClass()) {
				return false;
			}
			if (clazz.isArray() || clazz.isEnum()) {
				return false;
			}
			if (clazz.isInterface()) {
				return false;
			}
			if (clazz.isLocalClass()) {
				return false;
			}
			if ((clazz.isMemberClass() ^ Modifier.isStatic(clazz.getModifiers()))) {
				return false;
			}
			if (clazz.isPrimitive()) {
				return false;
			}
			int modifiers = clazz.getModifiers();
			if (Modifier.isAbstract(modifiers)) {
				return false;
			}
			return true;
		} catch (Throwable ignore) {
			return false;
		}
	}

	// ---------------------------------------------------------------- handlers

	/**
	 * Builds action configuration on founded action class.
	 * Action classes are annotated with {@link jodd.madvoc.meta.MadvocAction} annotation.
	 */
	@SuppressWarnings("NonConstantStringShouldBeStringBuffer")
	protected void onActionClass(String className) throws ClassNotFoundException {
		Class<?> actionClass = loadClass(className);

		if (actionClass == null) {
			return;
		}

		if (checkClass(actionClass) == false) {
			return; 
		}

		if (actionClass.getAnnotation(MadvocAction.class) == null) {
			return;
		}

		ClassDescriptor cd = ClassIntrospector.lookup(actionClass);

		MethodDescriptor[] allMethodDescriptors = cd.getAllMethodDescriptors();
		for (MethodDescriptor methodDescriptor : allMethodDescriptors) {
			if (!methodDescriptor.isPublic()) {
				continue;
			}
			// just public methods
			Method method = methodDescriptor.getMethod();

			boolean hasAnnotation = false;
			for (ActionAnnotation<?> actionAnnotation : madvocConfig.getActionAnnotationInstances()) {
				if (actionAnnotation.hasAnnotation(method)) {
					hasAnnotation = true;
					break;
				}
			}
			if (hasAnnotation == false) {
				continue;
			}
			actionsManager.register(actionClass, method);
		}
	}

	/**
	 * Loads madvoc result from founded {@link jodd.madvoc.result.ActionResult} instance.
	 */
	@SuppressWarnings({"unchecked"})
	protected void onResultClass(String className) throws ClassNotFoundException {
		Class resultClass = loadClass(className);

		if (resultClass == null) {
			return;
		}

		if (checkClass(resultClass) == false) {
			return;
		}
		if (ReflectUtil.isTypeOf(resultClass, ActionResult.class) == true) {
			resultsManager.register(resultClass);
		}
	}

}
