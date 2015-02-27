// Copyright (c) 2003-present, Jodd Team (jodd.org). All Rights Reserved.

package jodd.joy.jspp;

import jodd.io.FileNameUtil;
import jodd.io.FileUtil;
import jodd.log.Logger;
import jodd.log.LoggerFactory;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.MadvocException;
import jodd.madvoc.result.ServletDispatcherResult;
import jodd.util.StringUtil;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

/**
 * Servlet dispatcher result with JSP preprocessor.
 * Use it as base class for custom version of
 * {@link jodd.madvoc.result.ServletDispatcherResult}. It works
 * only for <b>exploded</b> deployed web apps; in other case
 * convert files manually before the deployment. Also, in production
 * convert all files manually or on application startup.
 */
public abstract class ServletDispatcherResultWithJspp extends ServletDispatcherResult {

	private static final Logger log = LoggerFactory.getLogger(ServletDispatcherResultWithJspp.class);

	protected String macrosPath = "jspp";
	protected Jspp jspp;

	/**
	 * Converts jsp name to new target name.
	 */
	protected String convertToNewName(String jspFile) {
		String extension = FileNameUtil.getExtension(jspFile);

		jspFile = StringUtil.substring(jspFile, 0, -extension.length() - 1);

		return jspFile + "-jspp." + extension;
	}

	@Override
	protected String processTarget(ActionRequest actionRequest, String target) {
		ServletContext servletContext = actionRequest.getHttpServletRequest().getServletContext();

		String jspPath = servletContext.getRealPath(target);

		if (jspPath == null) {
			throw new MadvocException("Real path not found: " + target);
		}
		File jspFile = new File(jspPath);
		File targetFile = new File(convertToNewName(jspPath));

		target = convertToNewName(target);

		if (!targetFile.exists() || FileUtil.isNewer(jspFile, targetFile)) {
			if (log.isDebugEnabled()) {
				log.debug("JSPP target: " + target);
			}

			if (jspp == null) {
				jspp = createJspp(servletContext);
			}

			try {
				preprocess(jspFile, targetFile);
			} catch (IOException ioex) {
				throw new JsppException(ioex);
			}

		} else {
			if (log.isDebugEnabled()) {
				log.debug("JSPP ok: " + target);
			}
		}

		return target;
	}

	/**
	 * Creates new JSPP instance. Instance is created lazy, on first access.
	 */
	protected Jspp createJspp(ServletContext servletContext) {
		Jspp jspp = new Jspp();

		String rootFolder = servletContext.getRealPath("/");

		jspp.setJsppMacroFolder(new File(rootFolder, macrosPath));

		return jspp;
	}

	/**
	 * {@link jodd.joy.jspp.Jspp Pre-process} JSP file and writes new content
	 * to target file.
	 */
	protected void preprocess(File jspFile, File targetFile) throws IOException {
		String jsp = FileUtil.readString(jspFile);

		String newJsp = jspp.process(jsp);

		FileUtil.writeString(targetFile, newJsp);
	}

}