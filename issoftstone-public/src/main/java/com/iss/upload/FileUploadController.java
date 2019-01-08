package com.iss.upload;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
public class FileUploadController {
	private final static Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

	@RequestMapping("ueconfig")
	public void getUEConfig(HttpServletRequest request, HttpServletResponse response) {
		Resource res = new ClassPathResource("config.json");
		InputStream is = null;
		response.setHeader("Content-Type", "text/html");
		try {
			is = new FileInputStream(res.getFile());
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[1024];
			int length = 0;
			while (-1 != (length = is.read(b))) {
				sb.append(new String(b, 0, length, "utf-8"));
			}
			String result = sb.toString().replaceAll("/\\*(.|[\\r\\n])*?\\*/", "");
			JSONObject json = JSON.parseObject(result);
			LOGGER.info(json.toJSONString());
			PrintWriter out = response.getWriter();
			out.print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
