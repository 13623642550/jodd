// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.util;

import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static jodd.util.PropertiesUtil.resolveProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PropertiesUtilTest {

	@Test
	public void testResolve() throws IOException {
		Properties p = PropertiesUtil.createFromString(
				"foo=foo\n" +
						"boo.foo=*${foo}*\n" +
						"zoo=${boo.${foo}}");
		assertEquals(3, p.size());

		assertNull(p.getProperty("xxx"));
		assertEquals("foo", p.getProperty("foo"));
		assertEquals("*${foo}*", p.getProperty("boo.foo"));

		assertNull(resolveProperty(p, "xxx"));
		assertEquals("foo", resolveProperty(p, "foo"));
		assertEquals("*foo*", resolveProperty(p, "boo.foo"));
		assertEquals("*foo*", resolveProperty(p, "zoo"));

		PropertiesUtil.resolveAllVariables(p);
		assertEquals(3, p.size());
		assertEquals("foo", p.getProperty("foo"));
		assertEquals("*foo*", p.getProperty("boo.foo"));
		assertEquals("*foo*", p.getProperty("zoo"));
	}

	@Test
	public void testEscape() throws IOException {
		Properties p = PropertiesUtil.createFromString(
				"foo=foo\n" +
						"boo.foo=*\\\\${foo}*\n" +
						"zoo=\\\\${boo.\\\\${foo}}\n" +
						"doo=\\\\\\\\${foo}");
		assertEquals(4, p.size());

		assertNull(p.getProperty("xxx"));
		assertEquals("foo", p.getProperty("foo"));
		assertEquals("*\\${foo}*", p.getProperty("boo.foo"));
		assertEquals("\\${boo.\\${foo}}", p.getProperty("zoo"));
		assertEquals("\\\\${foo}", p.getProperty("doo"));

		assertNull(resolveProperty(p, "xxx"));
		assertEquals("foo", resolveProperty(p, "foo"));
		assertEquals("*${foo}*", resolveProperty(p, "boo.foo"));
		assertEquals("${boo.${foo}}", resolveProperty(p, "zoo"));
		assertEquals("\\foo", resolveProperty(p, "doo"));
	}

	@Test
	public void testNull() {

		Properties properties = new Properties();
		properties.setProperty("foo", "123");
		properties.setProperty("xyz", "q${foo}z");
		properties.setProperty("abc", "q${bar}z");

		assertEquals("q123z", resolveProperty(properties, "xyz"));
		assertEquals("qz", resolveProperty(properties, "abc"));
	}

}
