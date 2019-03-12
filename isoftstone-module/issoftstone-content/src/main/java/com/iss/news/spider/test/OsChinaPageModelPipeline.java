package com.iss.news.spider.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

/**
 * @author Hyuan
 */
public class OsChinaPageModelPipeline implements PageModelPipeline<OsChianTitle> {

	@Override
	public void process(OsChianTitle t, Task task) {
		File file = new File("d:/blog-text/");
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = file.getAbsolutePath() + "/" + t.getTitle().trim() + ".txt";
		File f = new File(path);
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			// true表示可以追加新内容
			fw = new FileWriter(f.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(t.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(t);
	}

}
