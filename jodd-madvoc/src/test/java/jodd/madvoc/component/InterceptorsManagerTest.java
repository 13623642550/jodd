// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.component;

import jodd.madvoc.MadvocException;
import jodd.madvoc.MadvocTestCase;
import jodd.madvoc.interceptor.*;
import jodd.petite.PetiteContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InterceptorsManagerTest extends MadvocTestCase {

	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpand() {
		InterceptorsManager im = new InterceptorsManager();
		im.madvocConfig = new MadvocConfig();
		im.madvocConfig.defaultInterceptors = new Class[]{ServletConfigInterceptor.class};

		Class<? extends ActionInterceptor>[] in = new Class[]{
				EchoInterceptor.class,
				DefaultWebAppInterceptors.class
		};
		Class<? extends ActionInterceptor>[] out = im.expand(in);
		assertEquals(2, out.length);
		assertEquals(EchoInterceptor.class, out[0]);
		assertEquals(ServletConfigInterceptor.class, out[1]);
	}

	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpand2() {
		InterceptorsManager im = new InterceptorsManager();
		im.madvocConfig = new MadvocConfig();
		im.madvocConfig.defaultInterceptors = new Class[]{EchoInterceptor.class, LogEchoInterceptor.class, ServletConfigInterceptor.class};

		Class<? extends ActionInterceptor>[] in = new Class[]{
				AnnotatedPropertyInterceptor.class,
				DefaultWebAppInterceptors.class,
				EchoInterceptor.class
		};

		Class<? extends ActionInterceptor>[] out = im.expand(in);
		assertEquals(5, out.length);
		assertEquals(AnnotatedPropertyInterceptor.class, out[0]);
		assertEquals(EchoInterceptor.class, out[1]);
		assertEquals(LogEchoInterceptor.class, out[2]);
		assertEquals(ServletConfigInterceptor.class, out[3]);
		assertEquals(EchoInterceptor.class, out[4]);
	}

	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpandStack() {
		InjectorsManager injectorsManager = new InjectorsManager();
		injectorsManager.scopeDataResolver = new ScopeDataResolver();
		injectorsManager.madvocConfig = new MadvocConfig();
		injectorsManager.madpc = new PetiteContainer();
		injectorsManager.createInjectors();

		InterceptorsManager im = new InterceptorsManager();
		im.contextInjectorComponent = new ContextInjectorComponent();
		im.contextInjectorComponent.injectorsManager = injectorsManager;
		im.contextInjectorComponent.madvocController = new MadvocController();
		im.contextInjectorComponent.scopeDataResolver = injectorsManager.scopeDataResolver;
		im.contextInjectorComponent.madpc = injectorsManager.madpc;
		im.madvocConfig = injectorsManager.madvocConfig;
		im.madvocConfig.defaultInterceptors = new Class[]{EchoInterceptor.class, ServletConfigInterceptor.class};

		Class<? extends ActionInterceptor>[] in = new Class[]{
				TestStack.class,
				DefaultWebAppInterceptors.class,
				EchoInterceptor.class
		};

		Class<? extends ActionInterceptor>[] out = im.expand(in);
		assertEquals(7, out.length);
		assertEquals(AnnotatedPropertyInterceptor.class, out[0]);
		assertEquals(LogEchoInterceptor.class, out[1]);

		assertEquals(EchoInterceptor.class, out[2]);
		assertEquals(ServletConfigInterceptor.class, out[3]);

		assertEquals(EchoInterceptor.class, out[4]);
		assertEquals(ServletConfigInterceptor.class, out[5]);

		assertEquals(EchoInterceptor.class, out[6]);
	}
	
	
	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpandConfigurableStack() {
		InjectorsManager injectorsManager = new InjectorsManager();
		injectorsManager.scopeDataResolver = new ScopeDataResolver();
		injectorsManager.madvocConfig = new MadvocConfig();
		injectorsManager.madpc = new PetiteContainer();

		InterceptorsManager im = new InterceptorsManager();
		im.contextInjectorComponent = new ContextInjectorComponent();
		im.contextInjectorComponent.injectorsManager = injectorsManager;
		im.contextInjectorComponent.madvocController = new MadvocController();
		im.contextInjectorComponent.scopeDataResolver = injectorsManager.scopeDataResolver;
		im.contextInjectorComponent.madpc = injectorsManager.madpc;
		im.madvocConfig = injectorsManager.madvocConfig;
		im.madvocConfig.defaultInterceptors = new Class[]{EchoInterceptor.class, ServletConfigInterceptor.class};

		injectorsManager.madpc.defineParameter(
				TestConfigurableStack.class.getName() + ".interceptors",
				AnnotatedPropertyInterceptor.class.getName() + "," +
				ServletConfigInterceptor.class.getName() + "," +
				LogEchoInterceptor.class.getName()
		);

		injectorsManager.createInjectors();

		Class<? extends ActionInterceptor>[] in = new Class[] {
			TestConfigurableStack.class,
			TestConfigurableStack2.class,
			EchoInterceptor.class
		};

		Class<? extends ActionInterceptor>[] out = im.expand(in);
		assertEquals(6, out.length);		// 3 + 2 + 1

		// assert: TestConfigurableStack => defined in madpc
		assertEquals(AnnotatedPropertyInterceptor.class, out[0]);
		assertEquals(ServletConfigInterceptor.class, out[1]);
		assertEquals(LogEchoInterceptor.class, out[2]);

		//assert: TestConfigurableStack2 => madvocConfig.defaultInterceptors
		assertEquals(EchoInterceptor.class, out[3]);
		assertEquals(ServletConfigInterceptor.class, out[4]);
		assertEquals(EchoInterceptor.class, out[5]);
	}
	
	
	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpandSelf() {
		InterceptorsManager im = new InterceptorsManager();
		im.madvocConfig = new MadvocConfig();

		im.madvocConfig.setDefaultInterceptors(new Class[]{
				EchoInterceptor.class,
				DefaultWebAppInterceptors.class    // cyclic dependency
		});

		Class<? extends ActionInterceptor>[] in = new Class[]{
				EchoInterceptor.class,
				DefaultWebAppInterceptors.class
		};
		try {
			Class<? extends ActionInterceptor>[] out = im.expand(in);
			fail();
		} catch (MadvocException ignore) {
		} catch (Exception ignored) {
			fail();
		}
	}


	@SuppressWarnings({"unchecked"})
	@Test
	public void testExpandStack2() {
		InjectorsManager injectorsManager = new InjectorsManager();
		injectorsManager.scopeDataResolver = new ScopeDataResolver();
		injectorsManager.madvocConfig = new MadvocConfig();
		injectorsManager.madpc = new PetiteContainer();
		injectorsManager.createInjectors();

		InterceptorsManager im = new InterceptorsManager();
		im.contextInjectorComponent = new ContextInjectorComponent();
		im.contextInjectorComponent.injectorsManager = injectorsManager;
		im.contextInjectorComponent.madvocController = new MadvocController();
		im.contextInjectorComponent.scopeDataResolver = injectorsManager.scopeDataResolver;
		im.contextInjectorComponent.madpc = injectorsManager.madpc;
		im.madvocConfig = injectorsManager.madvocConfig;
		im.madvocConfig.defaultInterceptors = new Class[]{EchoInterceptor.class, ServletConfigInterceptor.class, Test2Stack.class};

		Class<? extends ActionInterceptor>[] in = new Class[]{
				DefaultWebAppInterceptors.class,
		};

		Class<? extends ActionInterceptor>[] out = im.expand(in);
		assertEquals(4, out.length);
		assertEquals(EchoInterceptor.class, out[0]);
		assertEquals(ServletConfigInterceptor.class, out[1]);

		assertEquals(AnnotatedPropertyInterceptor.class, out[2]);
		assertEquals(LogEchoInterceptor.class, out[3]);

	}

	// ---------------------------------------------------------------- util

	public static class TestStack extends ActionInterceptorStack {
		@SuppressWarnings({"unchecked"})
		public TestStack() {
			super(new Class[]{Test2Stack.class, DefaultWebAppInterceptors.class});
		}
	}

	public static class Test2Stack extends ActionInterceptorStack {
		@SuppressWarnings({"unchecked"})
		public Test2Stack() {
			super(new Class[]{AnnotatedPropertyInterceptor.class, LogEchoInterceptor.class});
		}
	}
	
	public static class TestConfigurableStack extends ActionInterceptorStack {
	}

	public static class TestConfigurableStack2 extends ActionInterceptorStack {
		public TestConfigurableStack2() {
			super(DefaultWebAppInterceptors.class);
		}
	}
}
