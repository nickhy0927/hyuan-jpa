package com.baidu.code.parse;

import java.net.URL;
import java.util.List;

import com.baidu.code.modal.GeneratorConfiguration;

public class InitFile {

	public static void init(URL url) {
		try {
			List<GeneratorConfiguration> list = DomParser.startParse(url);
			for (GeneratorConfiguration configure : list) {
				Class<?> clazz = Class.forName(configure.getClazz());
				configure.setEntityName(clazz.getSimpleName());
				FileGenerator.writeFile(configure, clazz.getSimpleName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
