package com.iss.news.spider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class DownloadPicture implements PageProcessor {
	static List<String> urls;
	static List<String> names;

	public void setUrls(List<String> urls) {
		DownloadPicture.urls = urls;
	}

	public void setNames(List<String> names) {
		DownloadPicture.names = names;
	}

	/**
	 * 下载图片 author:bruce_q 2017-2-5 20:47
	 *
	 * @param urlList
	 * @param nameList
	 */
	private void downloadPicture(ArrayList<String> urlList, ArrayList<String> nameList, String key) {
		URL url = null;
		for (int i = 0; i < urlList.size(); i++) {
			try {
				url = new URL(urlList.get(i));
				DataInputStream dataInputStream = new DataInputStream(url.openStream());
				String imageName = i + ".jpg";
				File file = new File("d:\\pic\\" + key); // 设置下载路径
				if (!file.isDirectory()) {
					file.mkdirs();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(
						new File("d:\\pic\\" + key + "\\" + imageName.trim()));
				byte[] buffer = new byte[1024];
				int length;
				while ((length = dataInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, length);
				}
				dataInputStream.close();
				fileOutputStream.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void process(Page page) {
		List<String> url_list = new ArrayList<>();
		List<String> name_list = new ArrayList<>();
		JSONObject jsonObject = (JSONObject) JSONObject.parse(page.getRawText());
		JSONArray data = (JSONArray) jsonObject.get("data");
		for (int i = 0; i < data.size(); i++) {
			String url = (String) data.getJSONObject(i).get("thumbURL");
			String name = (String) data.getJSONObject(i).get("fromPageTitleEnc");
			if (url != null) {
				url_list.add(url);
				name_list.add(name);
			}
		}
		setUrls(url_list);
		setNames(name_list);
	}

	public static void main(String[] args) {
		String key = "紧身美女"; // 百度图片 关键词
		DownloadPicture downloadPicture = new DownloadPicture();
		ArrayList<String> nameList = new ArrayList<>();
		ArrayList<String> urlList = new ArrayList<>();
		for (int i = 0; i < 20; i++) { // 控制爬取页数，一页30张图片
			String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&queryWord=" + key + "&word="
					+ key + "&pn=" + i * 3 + "0";
			Spider.create(new DownloadPicture()).addUrl(url).run();
			urlList.addAll(urls);
			nameList.addAll(names);
		}
		downloadPicture.downloadPicture(urlList, nameList, key);
	}

	@Override
    public Site getSite() {
        return Site.me();
    }
}
