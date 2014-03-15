// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc;

import jodd.exception.UncheckedException;
import jodd.petite.scope.SessionScope;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		HelloActionTest.class,
		SimpleTest.class,
		RawActionTest.class,
		UrlActionTest.class,
		OneTwoActionTest.class,
		IntcptActionTest.class,
		RestActionTest.class,
		FilterTest.class,
		SessionScopeTest.class,
		AlphaTest.class,
		ArgsTest.class,
})
public class MadvocSuite {

	public static boolean isSuite;

	/**
	 * Starts Tomcat after the suite.
	 */
	@BeforeClass
	public static void beforeClass() {
		isSuite = true;
		startTomcat();
	}

	/**
	 * Stop Tomcat after the suite.
	 */
	@AfterClass
	public static void afterSuite() {
		isSuite = false;
		stopTomcat();
	}

	// ---------------------------------------------------------------- tomcat

	private static TomcatTestServer server;

	/**
	 * Starts Tomcat.
	 */
	public static void startTomcat() {
		if (server != null) {
			return;
		}
		server = new TomcatTestServer();
		try {
			server.start();
			System.out.println("Tomcat test server started");
		} catch (Exception e) {
			throw new UncheckedException(e);
		}
	}

	/**
	 * Stops Tomcat if not in the suite.
	 */
	public static void stopTomcat() {
		if (server == null) {
			return;
		}
		if (isSuite) {	// don't stop tomcat if it we are still running in the suite!
			return;
		}
		try {
			server.stop();
		} catch (Exception ignore) {
		} finally {
			System.out.println("Tomcat test server stopped");
			server = null;
		}
	}

}