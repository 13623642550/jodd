// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.meta;

import jodd.madvoc.path.ActionNamingStrategy;
import jodd.util.AnnotationDataReader;

import java.lang.annotation.Annotation;

/**
 * {@link Action} annotation elements.
 */
public class ActionAnnotationData<A extends Annotation> extends AnnotationDataReader.AnnotationData<A> {

	protected String value;
	protected String extension;
	protected String alias;
	protected String method;
	protected boolean async;
	protected Class<? extends ActionNamingStrategy> path;

	protected ActionAnnotationData(A annotation) {
		super(annotation);
	}

	public String getValue() {
		return value;
	}

	public String getExtension() {
		return extension;
	}

	public String getAlias() {
		return alias;
	}

	public String getMethod() {
		return method;
	}

	public boolean isAsync() {
		return async;
	}

	public Class<? extends ActionNamingStrategy> getPath() {
		return path;
	}
}