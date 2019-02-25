package com.iss.log;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LogReader {
	private static long lastTime;

	public static void main(String[] args) {
		File file = new File("D:\\logs\\deploy\\deploy.log");
		// 首先文件的最近一次修改时间戳
		lastTime = file.lastModified();
		// 定时任务，每秒来判断一下文件是否发生变动，即判断lastModified是否改变
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (file.lastModified() > lastTime) {
					System.out.println("file update! time : " + file.lastModified());
					lastTime = file.lastModified();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);

		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}