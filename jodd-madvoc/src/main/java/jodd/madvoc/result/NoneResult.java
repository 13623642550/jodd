// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.result;

import jodd.madvoc.ActionRequest;

/**
 * None result processing, for direct outputs.
 */
public class NoneResult extends BaseActionResult<String> {
	
	public static final String NAME = "none";
	
	public NoneResult() {
		super(NAME);
	}

	/**
	 * Executes result on given action result value.
	 */
	public void render(ActionRequest actionRequest, String resultValue) throws Exception {
		// none, direct output
	}
}