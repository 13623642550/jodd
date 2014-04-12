// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd;

import jodd.typeconverter.TypeConverterManagerBean;
import jodd.typeconverter.impl.FileConverter;
import jodd.typeconverter.impl.FileUploadConverter;
import jodd.typeconverter.impl.FileUploadToFileTypeConverter;
import jodd.upload.FileUpload;

import java.io.File;

/**
 * Jodd UPLOAD module.
 */
public class JoddUpload {

	static {
		Jodd.module();
	}

	public void bind(TypeConverterManagerBean typeConverterManagerBean) {
		typeConverterManagerBean.register(FileUpload.class, new FileUploadConverter());

		FileConverter fileConverter = (FileConverter) typeConverterManagerBean.lookup(File.class);

		fileConverter.registerAddonConverter(new FileUploadToFileTypeConverter());
	}

}