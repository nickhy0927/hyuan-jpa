package com.iss.platform.init;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 默认数据源，也就是主库
	private DataSource masterDataSource;
	
	private InitDataBase initDataBase;
	
	// 保存动态创建的数据源
	public static final Map<Object, Object> targetDataSources = Maps.newConcurrentMap();

	@Override
	protected DataSource determineTargetDataSource() {
		// 根据数据库选择方案，拿到要访问的数据库
		String dataSourceName = determineCurrentLookupKey();
		if ("dataSource".equals(dataSourceName)) {
			// 访问默认主库
			return masterDataSource;
		}
		// 根据数据库名字，从已创建的数据库中获取要访问的数据库
		Log4jdbcProxyDataSource dataSource = (Log4jdbcProxyDataSource) targetDataSources.get(dataSourceName);
		return dataSource;
	}
	
	@Override
	protected String determineCurrentLookupKey() {
		String dataSourceName = DataSourceHolder.getDataSource();
		if (dataSourceName == null || dataSourceName == "dataSource") {
			// 默认的数据源名字
			dataSourceName = "dataSource";
		}
		log.debug("use datasource : " + dataSourceName);
		return dataSourceName;
	}

	/**
	 * 该方法重写为空，因为AbstractRoutingDataSource类中会通过此方法将，targetDataSources变量中保存的数据源交给resolvedDefaultDataSource变量
	 * 在本方案中动态创建的数据源保存在了本类的targetDataSource变量中。如果不重写该方法为空，会因为targetDataSources变量为空报错
	 * 如果仍然想要使用AbstractRoutingDataSource类中的变量保存数据源，则需要在每次数据源变更时，调用此方法来为resolvedDefaultDataSource变量更新
	 */
	@Override
	public void afterPropertiesSet() {
	}

	public void setMasterDataSource(DataSource masterDataSource) {
		this.masterDataSource = masterDataSource;
	}
	
	public void initDataSource() {
		initDataBase.createDataBase();
		setTargetDataSources(targetDataSources);
	}
	
	public void setInitDataBase(InitDataBase initDataBase) {
		this.initDataBase = initDataBase;
	}

}
