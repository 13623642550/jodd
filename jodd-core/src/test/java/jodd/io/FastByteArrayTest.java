// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.io;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class FastByteArrayTest {

	@Test
	public void testFbat() throws IOException {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();

		fbaos.write(173);
		fbaos.write(new byte[]{1, 2, 3});
		fbaos.write(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 4, 3);

		byte[] result = fbaos.toByteArray();
		byte[] expected = new byte[]{(byte) 173, 1, 2, 3, 5, 6, 7};

		assertTrue(Arrays.equals(expected, result));
	}

	@Test
	public void testFbat2() throws IOException {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream(2);

		fbaos.write(173);
		fbaos.write(new byte[]{1, 2, 3});
		fbaos.write(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 4, 3);

		byte[] result = fbaos.toByteArray();
		byte[] expected = new byte[]{(byte) 173, 1, 2, 3, 5, 6, 7};

		assertTrue(Arrays.equals(expected, result));
	}

	@Test
	public void testFbatSingle() throws IOException {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream(2);

		fbaos.write(73);
		fbaos.write(74);
		fbaos.write(75);
		fbaos.write(76);
		fbaos.write(77);

		byte[] result = fbaos.toByteArray();
		byte[] expected = new byte[]{73, 74, 75, 76, 77};

		assertTrue(Arrays.equals(expected, result));
	}
}
