// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.meta;

import jodd.util.AnnotationDataReader;
import jodd.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

/**
 * Action method annotation reader.
 */
public class ActionAnnotation<A extends Annotation> extends AnnotationDataReader<A, ActionAnnotationData<A>> {

	public ActionAnnotation(Class<A> annotationClass) {
		super(annotationClass, Action.class);
	}

	/**
	 * Need to override to make java compiler happy.
	 */
	@Override
	public ActionAnnotationData<A> readAnnotationData(AccessibleObject accessibleObject) {
		return super.readAnnotationData(accessibleObject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ActionAnnotationData<A> createAnnotationData(A annotation) {

		ActionAnnotationData<A> ad = new ActionAnnotationData<A>(annotation);

		ad.value = readString(annotation, "value");

		ad.extension = readString(annotation, "extension");

		ad.alias = readString(annotation, "alias");

		ad.method = readString(annotation, "method");

		ad.async = readBoolean(annotation, "async");

		return ad;
	}

	/**
	 * Reads string element from the annotation. Converts
	 * empty strings to <code>null</code>.
	 */
	private String readString(A annotation, String name) {
		String value = readStringElement(annotation, name);

		if (StringUtil.isEmpty(value)) {
			value = null;
		}

		return value;
	}

	/**
	 * Reads boolean element from the annotation.
	 */
	private boolean readBoolean(A annotation, String name) {
		Boolean value = (Boolean) readElement(annotation, name);
		if (value == null) {
			return false;
		}
		return value.booleanValue();
	}

}