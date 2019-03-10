package com.baidu.code.parse;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baidu.code.modal.GeneratorConfiguration;
import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class DomParser {

	public static List<GeneratorConfiguration> startParse(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		if (url == null) {
			System.out.println("获取配置文件路径无法找到...");
		}
		List<GeneratorConfiguration> configures = Lists.newArrayList();
		Document document = reader.read(new File(url.getFile()));
		Element root = document.getRootElement();
		String srcPath = root.attribute("srcPath").getValue();
		List<Element> elements = root.elements();
		for (Element element : elements) {
			GeneratorConfiguration configure = new GeneratorConfiguration();
			Attribute attribute = element.attribute("clazz");
			String clazz = attribute.getValue();
			String pkgs = element.element("pkgs").getText();
			String pagePath = element.element("pagePath").getText();
			String desc = element.element("desc").getText();
			String pathSuffix = element.attribute("pathSuffix").getValue();
			Element parentAlilasEle = element.element("parentAlilas");
			String parentAlilas = parentAlilasEle.attribute("alias").getValue();
			String aliasPackage = parentAlilasEle.getText();
			configure.setClazz(clazz);
			configure.setAliasPackage(aliasPackage);
			configure.setParentAlilas(parentAlilas);
			configure.setPathSuffix(pathSuffix);
			configure.setPkg(pkgs);
			configure.setPagePath(pagePath);
			configure.setDesc(desc);
			configure.setSrcPath(srcPath);
			configures.add(configure);
		}
		return configures;
	}
}
