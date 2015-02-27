// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.jerry;

import java.util.Map;

/**
 * Callback for Jerry <code>form()</code> method.
 */
public interface JerryFormHandler {

	/**
	 * Invoked for each matched form.
	 */
	void onForm(Jerry form, Map<String, String[]> parameters);

}