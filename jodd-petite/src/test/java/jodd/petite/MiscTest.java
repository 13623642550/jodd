// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.petite;

import jodd.petite.data.Biz;
import jodd.petite.data.DefaultBiz;
import jodd.petite.data.DefaultBizImpl;
import jodd.petite.tst.Boo;
import jodd.petite.tst.Foo;
import jodd.petite.tst.Zoo;
import org.junit.Test;

import static jodd.petite.meta.InitMethodInvocationStrategy.POST_INITIALIZE;
import static org.junit.Assert.*;

public class MiscTest {

	@Test
	public void testOne() {
		PetiteContainer pc = new PetiteContainer();
		pc.registerPetiteBean(DefaultBizImpl.class, null, null, null, false);
		assertEquals(1, pc.getTotalBeans());

		Object bizI = pc.getBean("biz");
		assertTrue(bizI instanceof Biz);
		assertTrue(bizI instanceof DefaultBizImpl);

		pc = new PetiteContainer();
		pc.registerPetiteBean(DefaultBizImpl.class, null, null, null, false);
		pc.registerPetiteBean(DefaultBiz.class, null, null, null, false);            // override!
		assertEquals(1, pc.getTotalBeans());
		pc.registerPetiteBean(Foo.class, null, null, null, false);
		pc.registerPetitePropertyInjectionPoint("biz", "foo", null);
		pc.registerPetiteInitMethods("biz", POST_INITIALIZE, "init", "init2");

		assertEquals(2, pc.getTotalBeans());
		bizI = pc.getBean("biz");
		assertTrue(bizI instanceof Biz);
		assertFalse(bizI instanceof DefaultBizImpl);
		assertTrue(bizI instanceof DefaultBiz);

		assertNotNull(((DefaultBiz) bizI).getFoo());
		assertEquals(2, ((DefaultBiz) bizI).initCount);
	}

	@Test
	public void testTwo() {
		PetiteContainer pc = new PetiteContainer();
		pc.registerPetiteBean(DefaultBizImpl.class, null, null, null, false);
		assertEquals(1, pc.getTotalBeans());

		Object bizI = pc.getBean("biz");
		assertTrue(bizI instanceof Biz);
		assertFalse(bizI instanceof DefaultBiz);
		assertTrue(bizI instanceof DefaultBizImpl);

		//pc = new PetiteContainer();			// same container!!!
		pc.registerPetiteBean(DefaultBiz.class, null, null, null, false);            // override! instance will be removed from the scope
		assertEquals(1, pc.getTotalBeans());
		bizI = pc.getBean("biz");
		assertTrue(bizI instanceof Biz);
		assertFalse(bizI instanceof DefaultBizImpl);
		assertTrue(bizI instanceof DefaultBiz);
	}


	@Test
	public void testAdd() {
		PetiteContainer pc = new PetiteContainer();
		Foo foo = new Foo();
		pc.addBean("foo", foo);
		Foo foo2 = (Foo) pc.getBean("foo");
		assertNotNull(foo2);
		assertSame(foo, foo2);
	}

	@Test
	public void testAdd2WithCircDep() {
		Foo.instanceCounter = 0;
		PetiteContainer pc = new PetiteContainer();
		pc.registerPetiteBean(Foo.class, null, null, null, false);
		pc.registerPetiteBean(Zoo.class, null, null, null, false);

		Foo foo = (Foo) pc.getBean("foo");
		Boo boo = new Boo();
		assertNull(boo.getFoo());

		pc.addBean("boo", boo, null);
		assertNotNull(boo.getFoo());
		assertSame(foo, boo.getFoo());
		assertNotNull(boo.zoo);

		Zoo zoo = (Zoo) pc.getBean("zoo");
		assertNotNull(zoo.boo);
		assertSame(zoo, boo.zoo);        // circular dependency
		assertSame(boo, zoo.boo);

		Boo boo2 = (Boo) pc.getBean("boo");
		assertNotNull(boo2);
		assertSame(boo, boo2);
		assertFalse(boo.orders.isEmpty());
		assertEquals(6, boo.orders.size());
		assertEquals("[first, second, third, init, beforeLast, last]", boo.orders.toString());
		assertNotNull(boo2.getFoo());
		assertSame(foo, boo2.getFoo());
		assertEquals(1, boo2.getFoo().hello());
		assertEquals(1, boo2.getFoo().getCounter());

		pc.addBean("boo", boo);
		boo2 = (Boo) pc.getBean("boo");
		assertNotNull(boo2);
		assertSame(boo, boo2);
		assertNotNull(boo2.getFoo());
		assertSame(foo, boo2.getFoo());
		assertEquals(1, boo2.getFoo().hello());
		assertEquals(2, boo2.getFoo().getCounter());
		assertEquals(12, boo.orders.size());        // init methods are called again due to re-add
	}

	@Test
	public void testNoAdd2WithCircDep() {
		Foo.instanceCounter = 0;
		PetiteContainer pc = new PetiteContainer();
		pc.registerPetiteBean(Foo.class, null, null, null, false);
		pc.registerPetiteBean(Zoo.class, null, null, null, false);
		pc.registerPetiteBean(Boo.class, null, null, null, false);

		Boo boo = (Boo) pc.getBean("boo");
		Foo foo = (Foo) pc.getBean("foo");
		Zoo zoo = (Zoo) pc.getBean("zoo");

		assertNotNull(boo.getFoo());
		assertSame(foo, boo.getFoo());

		assertNotNull(zoo.boo);
		assertSame(boo, zoo.boo);
		assertSame(zoo, boo.zoo);

		Boo boo2 = (Boo) pc.getBean("boo");
		assertNotNull(boo2);
		assertSame(boo, boo2);
		assertFalse(boo.orders.isEmpty());
		assertNotNull(boo2.getFoo());
		assertSame(foo, boo2.getFoo());
		assertEquals(1, boo2.getFoo().hello());
		assertEquals(1, boo2.getFoo().getCounter());
		assertEquals("[first, second, third, init, beforeLast, last]", boo.orders.toString());
	}

}
