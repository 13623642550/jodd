// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.filter;

import jodd.madvoc.ActionRequest;
import jodd.madvoc.BaseActionWrapperStack;

/**
 * Action filter stack.
 */
public class ActionFilterStack extends BaseActionWrapperStack<ActionFilter> implements ActionFilter {

	public ActionFilterStack() {
	}

	public ActionFilterStack(Class<? extends ActionFilter>... filterClasses) {
		super(filterClasses);
	}

	/**
	 * Sets filter classes.
	 */
	public void setFilters(Class<? extends ActionFilter>... filters) {
		this.wrappers = filters;
	}

	/**
	 * Returns an array of filters.
	 */
	public Class<? extends ActionFilter>[] getFilters() {
		return getWrappers();
	}


	/**
	 * Filter is not used since this is just an filter container.
	 */
	public final Object filter(ActionRequest actionRequest) throws Exception {
		return invoke(actionRequest);
	}

}