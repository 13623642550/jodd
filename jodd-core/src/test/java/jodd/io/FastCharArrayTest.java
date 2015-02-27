// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.io;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FastCharArrayTest {

	@Test
	public void testFcat() throws IOException {
		FastCharArrayWriter fcaw = new FastCharArrayWriter();

		fcaw.write(65);
		fcaw.write(new char[]{'a', 'z', 'r'});
		fcaw.write(new char[]{'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'}, 4, 3);

		char[] result = fcaw.toCharArray();
		char[] expected = new char[]{'A', 'a', 'z', 'r', 'g', 'h', 'j'};

		assertTrue(Arrays.equals(expected, result));
	}

	@Test
	public void testFcatSingle() throws IOException {
		FastCharArrayWriter fcaw = new FastCharArrayWriter();

		fcaw.write(73);
		fcaw.write(74);
		fcaw.write(75);
		fcaw.write(76);
		fcaw.write(77);

		char[] result = fcaw.toCharArray();
		char[] expected = new char[]{73, 74, 75, 76, 77};

		assertTrue(Arrays.equals(expected, result));
	}

	@Test
	public void testWriteTo() throws IOException {
		FastCharArrayWriter fcaw = new FastCharArrayWriter(2);
		fcaw.write("Hello");
		fcaw.write(' ');
		fcaw.write("World");
		fcaw.write('!');

		StringWriter sw = new StringWriter();
		fcaw.writeTo(sw);

		assertEquals("Hello World!", sw.toString());
	}

}
