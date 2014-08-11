// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.typeconverter;

import jodd.typeconverter.impl.FloatArrayConverter;
import org.junit.Test;

import static jodd.typeconverter.TypeConverterTestHelper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FloatArrayConverterTest {

	@Test
	public void testConversion() {
		FloatArrayConverter floatArrayConverter = (FloatArrayConverter) TypeConverterManager.lookup(float[].class);

		assertNull(floatArrayConverter.convert(null));

		assertEq(arrf((float) 1.73), floatArrayConverter.convert(Float.valueOf((float) 1.73)));
		assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.convert(arrf((float) 1.73, (float) 10.22)));
		assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.convert(arrd(1.73, 10.22)));
		assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.convert(arrf(1.73f, 10.22f)));
		assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.convert(arri(1, 7, 3)));
		assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.convert(arrl(1, 7, 3)));
		assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.convert(arrb(1, 7, 3)));
		assertEq(arrf((float) 1.0, (float) 7.0, (float) 3.0), floatArrayConverter.convert(arrs(1, 7, 3)));
		assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.convert(arrs("1.73", "10.22")));
		assertEq(arrf((float) 1.73, (float) 10.22), floatArrayConverter.convert(arrs(" 1.73 ", " 10.22 ")));
		assertEq(arrf((float) 1.73, 10), floatArrayConverter.convert(arro("1.73", 10)));
		assertEq(arrf((float) 1.73, 10), floatArrayConverter.convert("1.73 \n 10"));
	}

	private void assertEq(float[] arr1, float[] arr2) {
		assertEquals(arr1.length, arr2.length);
		for (int i = 0; i < arr1.length; i++) {
			assertEquals(arr1[i], arr2[i], 0.0001);
		}
	}

}


