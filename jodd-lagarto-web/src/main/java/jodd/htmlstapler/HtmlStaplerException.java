// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.htmlstapler;

import jodd.exception.UncheckedException;

/**
 * HTML stapler exception.
 */
public class HtmlStaplerException extends UncheckedException {

	public HtmlStaplerException(Throwable t) {
		super(t);
	}

	public HtmlStaplerException(String message) {
		super(message);
	}

	public HtmlStaplerException(String message, Throwable t) {
		super(message, t);
	}
}