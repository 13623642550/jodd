// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter;

import jodd.typeconverter.impl.DoubleArrayConverter;
import org.junit.Test;

import static jodd.typeconverter.TypeConverterTestHelper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DoubleArrayConverterTest {

	@Test
	public void testConversion() {
		DoubleArrayConverter doubleArrayConverter = (DoubleArrayConverter) TypeConverterManager.lookup(double[].class);

		assertNull(doubleArrayConverter.convert(null));

		assertEq(arrd(1.73), doubleArrayConverter.convert(Double.valueOf(1.73)));
		assertEq(arrd(1.73, 10.22), doubleArrayConverter.convert(arrd(1.73, 10.22)));
		assertEq(arrd(1.0, 7.0, 3.0), doubleArrayConverter.convert(arri(1, 7, 3)));
		assertEq(arrd(1.0, 7.0, 3.0), doubleArrayConverter.convert(arrl(1, 7, 3)));
		assertEq(arrd(1.0, 7.0, 3.0), doubleArrayConverter.convert(arrf(1, 7, 3)));
		assertEq(arrd(1.0, 0.0, 1.0), doubleArrayConverter.convert(arrl(true, false, true)));
		assertEq(arrd(1.0, 7.0, 3.0), doubleArrayConverter.convert(arrb(1, 7, 3)));
		assertEq(arrd(1.0, 7.0, 3.0), doubleArrayConverter.convert(arrs(1, 7, 3)));
		assertEq(arrd(1.73, 10.22), doubleArrayConverter.convert(arrs("1.73", "10.22")));
		assertEq(arrd(1.73, 10.22), doubleArrayConverter.convert(arrs(" 1.73 ", " 10.22 ")));
		assertEq(arrd(1.73, 10), doubleArrayConverter.convert(arro("1.73", Integer.valueOf(10))));
		assertEq(arrd(1.73, 10), doubleArrayConverter.convert("1.73 \n 10"));
	}

	private void assertEq(double[] arr1, double[] arr2) {
		assertEquals(arr1.length, arr2.length);
		for (int i = 0; i < arr1.length; i++) {
			assertEquals(arr1[i], arr2[i], 0.0001);
		}
	}
}

