// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter;

import jodd.mutable.MutableByte;
import jodd.typeconverter.impl.MutableByteConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class MutableByteConverterTest {

	@Test
	public void testConversion() {
		MutableByteConverter mutableByteConverter = (MutableByteConverter) TypeConverterManager.lookup(MutableByte.class);

		assertNull(mutableByteConverter.convert(null));

		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert(new MutableByte((byte) 1)));
		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert(Integer.valueOf(1)));
		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert(Short.valueOf((short) 1)));
		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert(Double.valueOf(1.0D)));
		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert("1"));
		assertEquals(new MutableByte((byte) 1), mutableByteConverter.convert(" 1 "));

		try {
			mutableByteConverter.convert("a");
			fail();
		} catch (TypeConversionException ignore) {
		}
	}
}

