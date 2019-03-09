package com.iss.oauth.init;

public class DataSourceHolder {

//	public static String DATASOURCEID = "dataSource";

	public static final ThreadLocal<String> holder = new ThreadLocal<String>();

	public static void putDataSource(String datasource) {
		holder.set(datasource);
	}

	public static String getDataSource() {
		return holder.get();
	}
}
