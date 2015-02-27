// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.io;

import jodd.util.Bits;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UnicodeInputStreamTest {

	@Test
	public void testUtf8() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0xEFBBBF65);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(3, uis.getBOMSize());
		assertEquals("UTF-8", uis.getDetectedEncoding());
	}

	@Test
	public void testUtf16BE() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0xFEFF6565);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(2, uis.getBOMSize());
		assertEquals("UTF-16BE", uis.getDetectedEncoding());
	}

	@Test
	public void testUtf16LE() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0xFFFE6565);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(2, uis.getBOMSize());
		assertEquals("UTF-16LE", uis.getDetectedEncoding());
	}

	@Test
	public void testUtf32BE() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0x0000FEFF);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(4, uis.getBOMSize());
		assertEquals("UTF-32BE", uis.getDetectedEncoding());
	}

	@Test
	public void testUtf32LE() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0xFFFE0000);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(4, uis.getBOMSize());
		assertEquals("UTF-32LE", uis.getDetectedEncoding());
	}

	@Test
	public void testNoUtf() throws IOException {
		byte[] bytes = new byte[4];
		Bits.putInt(bytes, 0, 0x11223344);

		ByteArrayInputStream basis = new ByteArrayInputStream(bytes);
		UnicodeInputStream uis = new UnicodeInputStream(basis, null);
		uis.init();

		assertEquals(0, uis.getBOMSize());
		assertNull(uis.getDetectedEncoding());
	}
}
