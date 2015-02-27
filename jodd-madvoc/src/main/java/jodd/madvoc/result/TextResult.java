// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.result;

import jodd.io.StreamUtil;
import jodd.madvoc.ActionRequest;
import jodd.util.MimeTypes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Text result returns a result value, i.e. a string.
 * Useful for JSON responses, when resulting string is built
 * in the action.
 */
public class TextResult extends BaseActionResult<String> {

	public static final String NAME = "text";

	public TextResult() {
		super(NAME);
	}

	public void render(ActionRequest actionRequest, String resultValue) throws Exception {
		HttpServletResponse response = actionRequest.getHttpServletResponse();

		String encoding = response.getCharacterEncoding();
		response.setContentType(MimeTypes.MIME_TEXT_PLAIN);
		response.setCharacterEncoding(encoding);

		byte[] data = resultValue.getBytes(encoding);
		response.setContentLength(data.length);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(data);
		} finally {
			StreamUtil.close(out);
		}
	}
}