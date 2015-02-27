// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc;

import jodd.madvoc.component.ActionsManager;
import jodd.madvoc.config.ManualMadvocConfigurator;
import jodd.madvoc.interceptor.EchoInterceptor;
import jodd.madvoc.result.TextResult;
import jodd.madvoc.tst.BooAction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ManualRegistrationTest {

	public static class ManualRegistration extends ManualMadvocConfigurator {
		public void configure() {
			result(TextResult.class);
			action()
					.path("/hello")
					.mapTo(BooAction.class, "foo1")
					.bind();

			action()
					.path("/world")
					.mapTo(BooAction.class, "foo2")
					.interceptBy(EchoInterceptor.class)
					.bind();

			interceptor(EchoInterceptor.class).setPrefixIn("====> ");
		}
	}

	@Test
	public void testManualAction() {
		Madvoc madvoc = new Madvoc();
		madvoc.setMadvocConfiguratorClass(ManualRegistration.class);
		madvoc.startNewWebApplication(null);

		ActionsManager actionsManager = madvoc.getWebApplication().getComponent(ActionsManager.class);

		assertEquals(2, actionsManager.getActionsCount());

		ActionConfig actionConfig = actionsManager.lookup("/hello", "GET");
		assertNotNull(actionConfig);
		assertEquals(BooAction.class, actionConfig.getActionClass());
		assertEquals("foo1", actionConfig.actionClassMethod.getName());

		actionConfig = actionsManager.lookup("/world", "GET");
		assertNotNull(actionConfig);
		assertEquals(BooAction.class, actionConfig.getActionClass());
		assertEquals("foo2", actionConfig.actionClassMethod.getName());
	}
}