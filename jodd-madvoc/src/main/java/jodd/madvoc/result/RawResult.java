// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.result;

import jodd.io.StreamUtil;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.In;
import jodd.madvoc.component.MadvocConfig;
import jodd.servlet.ServletUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Raw results directly writes byte context to the output.
 * Content type and charset encoding (e.g. set by Madvoc) is ignored
 * and new values should be set here. Output is closed after writing.
 */
public class RawResult extends BaseActionResult<RawResultData> {

	@In(scope = ScopeType.CONTEXT)
	protected MadvocConfig madvocConfig;

	public void render(ActionRequest actionRequest, RawResultData resultValue) throws IOException {
		if (resultValue == null) {
			return;
		}

		HttpServletResponse response = actionRequest.getHttpServletResponse();

		// reset content type and prepare response
		// since we are using MadvocResponseWrapper, the charset will be reset as well.
		ServletUtil.prepareResponse(response, resultValue.getDownloadFileName(), resultValue.getMimeType(), resultValue.getContentLength());

		// write out
		InputStream contentInputStream = resultValue.getContentInputStream();
		OutputStream out = response.getOutputStream();

		StreamUtil.copy(contentInputStream, out);

		out.flush();

		StreamUtil.close(contentInputStream);
	}
}
