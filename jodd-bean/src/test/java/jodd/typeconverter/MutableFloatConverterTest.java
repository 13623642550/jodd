// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter;

import jodd.mutable.MutableFloat;
import jodd.typeconverter.impl.MutableFloatConverter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MutableFloatConverterTest {

	@Test
	public void testConversion() {
		MutableFloatConverter mutableFloatConverter = (MutableFloatConverter) TypeConverterManager.lookup(MutableFloat.class);

		assertNull(mutableFloatConverter.convert(null));

		assertEquals(new MutableFloat(1.73f), mutableFloatConverter.convert(new MutableFloat(1.73f)));
		assertEquals(new MutableFloat(1), mutableFloatConverter.convert(Integer.valueOf(1)));
		assertEquals(new MutableFloat(1.73f), mutableFloatConverter.convert(Double.valueOf(1.73D)));
		assertEquals(new MutableFloat(1.73f), mutableFloatConverter.convert(" 1.73 "));
		assertEquals(new MutableFloat(1.73f), mutableFloatConverter.convert(new BigDecimal("1.73")));

		try {
			mutableFloatConverter.convert("aaaa");
			fail();
		} catch (TypeConversionException ignore) {
		}
	}
}

