// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.petite;

import jodd.petite.mix.Big;
import jodd.petite.mix.Big2;
import jodd.petite.mix.Small;
import jodd.petite.scope.ProtoScope;
import jodd.petite.scope.SingletonScope;
import jodd.petite.scope.ThreadLocalScope;
import org.junit.Test;
import static org.junit.Assert.*;

public class MixScopeTest {

	@Test
	public void testPrototypeInSingleton() {
		Small.instanceCounter = 0;

		PetiteContainer pc = new PetiteContainer();
		pc.getConfig().setWireScopedProxy(true);
		pc.getConfig().setDetectMixedScopes(true);

		pc.registerPetiteBean(Big.class, "big", SingletonScope.class, null, false);
		pc.registerPetiteBean(Small.class, "small", ProtoScope.class, null, false);

		Big big = (Big) pc.getBean("big");

		Small small1 = big.getSmall();
		Small small2 = big.getSmall();

		assertSame(small1, small2);				// factory !!!

		assertEquals(1, Small.instanceCounter);

		assertEquals("small 2", small1.name());	// calling any method of small will create a new (target) instance of 'small'

		assertEquals("small 3", small2.name());

		assertEquals(3, Small.instanceCounter);

		assertFalse(small1.toString().equals(small2.toString()));

		assertEquals(5, Small.instanceCounter);
	}

	@Test
	public void testPrototypeInSingleton2() {
		Small.instanceCounter = 0;

		PetiteContainer pc = new PetiteContainer();
		pc.getConfig().setWireScopedProxy(true);
		pc.getConfig().setDetectMixedScopes(true);

		pc.registerPetiteBean(Big2.class, "big", SingletonScope.class, null, false);
		pc.registerPetiteBean(Small.class, "small", ProtoScope.class, null, false);

		Big2 big = (Big2) pc.getBean("big");

		Small small1 = big.getSmall();
		Small small2 = big.getSmall();

		assertSame(small1, small2);				// factory !!!

		assertEquals(1, Small.instanceCounter);

		assertEquals("small 2", small1.name());	// calling any method of small will create a new (target) instance of 'small'

		assertEquals("small 3", small2.name());

		assertEquals(3, Small.instanceCounter);

		assertFalse(small1.toString().equals(small2.toString()));

		assertEquals(5, Small.instanceCounter);
	}

	@Test
	public void testSingleFactoryInstance() {

		Small.instanceCounter = 0;

		PetiteContainer pc = new PetiteContainer();
		pc.getConfig().setWireScopedProxy(true);
		pc.getConfig().setDetectMixedScopes(true);

		pc.registerPetiteBean(Big.class, "big", SingletonScope.class, null, false);
		pc.registerPetiteBean(Big.class, "big2", SingletonScope.class, null, false);
		pc.registerPetiteBean(Small.class, "small", ProtoScope.class, null, false);

		Big big = (Big) pc.getBean("big");

		Small small1 = big.getSmall();
		Small small2 = big.getSmall();

		assertSame(small1, small2);				// factory !!!

		assertEquals(1, Small.instanceCounter);

		Big big2 = (Big) pc.getBean("big2");

		Small small3 = big2.getSmall();
		Small small4 = big2.getSmall();

		assertSame(small3, small4);				// factory !!!

		assertSame(small1, small4);
	}

	@Test
	public void testThreadLocalScopeInSingleton() {

		Small.instanceCounter = 0;

		PetiteContainer pc = new PetiteContainer();
		pc.getConfig().setWireScopedProxy(true);
		pc.getConfig().setDetectMixedScopes(true);

		pc.registerPetiteBean(Big.class, "big", SingletonScope.class, null, false);
		pc.registerPetiteBean(Small.class, "small", ThreadLocalScope.class, null, false);

		final Big big = (Big) pc.getBean("big");

		Small small1 = big.getSmall();
		Small small2 = big.getSmall();

		assertSame(small1, small2);

		// one 'small' instance is created for the factory wrapper.
		assertEquals(1, Small.instanceCounter);

		// on next small method call, new 'small' instance will be created,
		// from the	ThreadLocal scope factory
		assertEquals("small 2", small1.name());

		// no 'small' instance will be created, the same instance from above will be used
		assertEquals("small 2", small2.name());

		// create new thread
		Thread thread = new Thread() {
			@Override
			public void run() {
				Small small3 = big.getSmall();

				assertEquals(2, Small.instanceCounter);

				assertEquals("small 3", small3.name());
				assertEquals("small 3", small3.name());

				assertEquals(3, Small.instanceCounter);

			}
		};

		thread.start();

		try {
			thread.join();
		} catch (InterruptedException ignore) {
		}

		assertEquals(3, Small.instanceCounter);

	}
}
