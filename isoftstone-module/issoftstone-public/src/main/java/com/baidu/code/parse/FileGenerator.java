package com.baidu.code.parse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.baidu.code.modal.GeneratorConfiguration;
import com.google.common.collect.Lists;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 文件生成器
 */
public class FileGenerator {

	private static String sf = "%s/%s/%s%s";

	/**
	 * 获取freemarker配置信息
	 *
	 * @return
	 * @throws IOException
	 */
	private static Configuration getConfig() throws IOException {
		Configuration cfg = new Configuration();
		String ftlPath = FileGenerator.class.getResource("/META-INF/template").getPath();
		cfg.setDirectoryForTemplateLoading(new File(ftlPath)); // 需要文件夹绝对路径
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

	private static String getPackages(GeneratorConfiguration configuration) {
		String javaFilePackageStr = configuration.getPkg();
		return javaFilePackageStr.replace(".", "/");
	}

	public static void writeFile(GeneratorConfiguration configuration, String domainObjectName) throws Exception {
		Map<String, Object> root = new HashMap<>();
		root.put("domainObjectName", domainObjectName);
		root.put("pathSuffix", configuration.getPathSuffix());
		root.put("desc", configuration.getDesc());
		root.put("desc", configuration.getDesc());
		root.put("packages", configuration.getPkg());
		createJavaFile(root, configuration, "Dao.java", "modalDao.ftl", "dao");
		createJavaFile(root, configuration, "Service.java", "modalService.ftl", "service");
		createJavaFile(root, configuration, "ServiceImpl.java", "modalServiceImpl.ftl", "service/impl");
		createJavaFile(root, configuration, "Controller.java", "modalController.ftl", "controller");
		createPageFile(root, configuration, "Create.jsp", "modalCreate.ftl", domainObjectName.toLowerCase());
		createPageFile(root, configuration, "Edit.jsp", "modalEdit.ftl", domainObjectName.toLowerCase());
		createPageFile(root, configuration, "List.jsp", "modalList.ftl", domainObjectName.toLowerCase());
	}

	/**
	 * 生成dao接口
	 *
	 * @param root
	 * @throws Exception
	 */
	private static void createJavaFile(Map<String, Object> root, GeneratorConfiguration configuration,
			String suffix, String template, String packages) throws Exception {
		Configuration cfg = getConfig();
		String projectPath = configuration.getSrcPath();
		String domainObjectName = getDomainObjectName(configuration.getEntityName());
		String packagePath = getPackages(configuration) + "/" + domainObjectName + "/" + packages;
		writeSingleFile(cfg, root, template, projectPath, packagePath, configuration.getEntityName(), suffix);
	}
	
	private static void createPageFile(Map<String, Object> root, GeneratorConfiguration configuration,
			String suffix, String template, String packages) throws Exception {
		Configuration cfg = getConfig();
		String projectPath = configuration.getPagePath();
		String className = configuration.getClazz();
		Class<?> clazz = Class.forName(className);
		Field[] fields = clazz.getDeclaredFields();
		List<String> fieldList = Lists.newArrayList();
		for (Field field : fields) fieldList.add(field.getName());
		root.put("columns", fieldList);
		String uncapitalize = StringUtils.uncapitalize(configuration.getEntityName());
		writeSingleFile(cfg, root, template, projectPath, uncapitalize, uncapitalize, suffix);
	}

	private static String getDomainObjectName(String domainObjectName) {
		return domainObjectName.toLowerCase();
	}

	/**
	 * @param cfg
	 * @param root
	 * @param template
	 * @param projectPath
	 * @param packagePath
	 * @param domainObjectName
	 * @param suffix
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static boolean writeSingleFile(Configuration cfg, Map<String, Object> root, String template,
			String projectPath, String packagePath, String domainObjectName, String suffix) throws IOException {
		Writer out = null;
		Template temp = cfg.getTemplate(template);
		File file = new File(String.format(sf, projectPath, packagePath, domainObjectName, suffix));
		System.out.println("全路径是：" + file.getAbsolutePath());
		try {
			if (!file.getParentFile().exists()) {
				if (!file.getParentFile().mkdirs()) {
					System.out.println("创建目标文件所在目录失败！");
					return false;
				}
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileWriter(file);
			temp.process(root, out);
			out.flush();
			System.out.println(String.format("创建单个文件：%s成功!", file.getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(String.format("写入%s失败!", file.getPath()));
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return true;
	}
}
