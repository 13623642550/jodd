// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RawActionTest {

	@BeforeClass
	public static void beforeClass() {
		MadvocSuite.startTomcat();
	}

	@AfterClass
	public static void afterClass() {
		MadvocSuite.stopTomcat();
	}

	@Test
	public void testRawAction() {
		HttpResponse response = HttpRequest.get("localhost:8173/raw.html").send();
		assertEquals("this is some raw direct result", response.bodyText().trim());
	}

	@Test
	public void testRawTextAction() {
		HttpResponse response = HttpRequest.get("localhost:8173/raw.text.html").send();
		assertEquals("some raw txt", response.bodyText().trim());
	}

	@Test
	public void testRawDownloadAction() {
		HttpResponse response = HttpRequest.get("localhost:8173/raw.download").send();
		assertEquals("attachment;filename=\"jodd-download.txt\"", response.header("content-disposition"));
		assertEquals("file from jodd.org!", response.bodyText().trim());
	}

}