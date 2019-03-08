package com.iss;

import java.net.URL;

import com.baidu.code.parse.InitFile;

public class Test {

	public static void main(String[] args) {
		URL url = Test.class.getResource("/config.xml");
		InitFile.init(url);
	}
}
