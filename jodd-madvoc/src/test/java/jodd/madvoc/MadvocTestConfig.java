// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc;

import jodd.madvoc.component.ActionsManager;
import jodd.madvoc.component.ResultsManager;
import jodd.madvoc.config.MadvocConfigurator;
import jodd.madvoc.result.RawResult;
import jodd.madvoc.result.ResultsTest;
import jodd.madvoc.result.TextResult;
import jodd.petite.meta.PetiteInject;

public class MadvocTestConfig implements MadvocConfigurator {

	@PetiteInject
	ActionsManager actionsManager;
	@PetiteInject
	ResultsManager resultsManager;

	public void configure() {
		resultsManager.register(TextResult.class);
		resultsManager.register(RawResult.class);

		actionsManager.register(ResultsTest.class, "madvocEncoding", new ActionId("/textResultEncoding"));
		actionsManager.register(ResultsTest.class, "madvocRawImage", new ActionId("/madvocRawImage"));
	}
}