package com.iss.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.aspect.anno.MultipleDataSource;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.Underline2Camel;
import com.iss.platform.system.database.entity.DataBase;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

	// 保存动态创建的数据源
	private static final Map<Object, Object> targetDataSources = Maps.newConcurrentMap();

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 默认数据源，也就是主库
	private DataSource masterDataSource;

	private String querySql;
	
	
	public static <T> List<T> resultToList(ResultSet resultSet, Class<T> clazz) {
		// 创建一个 T 类型的数组
		List<T> list = new ArrayList<>();
		try {
			// 通过反射获取对象的实例
			// 获取resultSet 的列的信息
			ResultSetMetaData metaData = resultSet.getMetaData();
			// 遍历resultSet
			while (resultSet.next()) {
				T t = clazz.getConstructor().newInstance();
				// 遍历每一列
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					// 获取列的名字
					String fName = metaData.getColumnLabel(i + 1);
					// 因为列的名字和我们EMP中的属性名是一样的，所以通过列的名字获得其EMP中属性
					Field field = null;
					String fieldName = Underline2Camel.underline2Camel(fName.toLowerCase(), true);
					try {
						field = clazz.getDeclaredField(fieldName);
					} catch (Exception e) {
						field = clazz.getSuperclass().getDeclaredField(fieldName);
					}
					// 因为属性是私有的，所有获得其对应的set 方法。set+属性名首字母大写+其他小写
					String setName = "set" + fieldName.toUpperCase().substring(0, 1) + fieldName.substring(1);
					// 因为属性的类型和set方法的参数类型一致，所以可以获得set方法
					Method setMethod = clazz.getMethod(setName, field.getType());
					// 执行set方法，把resultSet中的值传入emp中， 再继续循环传值
					Object object = resultSet.getObject(fName);
					if (object != null) {
						setMethod.invoke(t, resultSet.getObject(fName));
					}
				}
				// 把赋值后的对象 加入到list集合中
				list.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回list
		return list;
	}
	
	/**
	 * 该方法重写为空，因为AbstractRoutingDataSource类中会通过此方法将，targetDataSources变量中保存的数据源交给resolvedDefaultDataSource变量
	 * 在本方案中动态创建的数据源保存在了本类的targetDataSource变量中。如果不重写该方法为空，会因为targetDataSources变量为空报错
	 * 如果仍然想要使用AbstractRoutingDataSource类中的变量保存数据源，则需要在每次数据源变更时，调用此方法来为resolvedDefaultDataSource变量更新
	 */
	@Override
	public void afterPropertiesSet() {
	}
	
	public void createDataBase() {
		try {
			Connection connection = masterDataSource.getConnection();
			List<DataBase> dataBases = queryDataBaseList();
			for (DataBase dataBase : dataBases) {
				Statement statement = connection.createStatement();
				if (!isExistDatabase(dataBase.getDataBaseName())) {
					statement.execute("create database " + dataBase.getDataBaseName());
					statement.close();
				}
				{
					StringBuilder jdbcUrl = new StringBuilder("jdbc:mysql://");
					jdbcUrl.append(dataBase.getIp()).append(":").append(dataBase.getPort());
					jdbcUrl.append("/").append(dataBase.getDataBaseName());
					jdbcUrl.append("?useUnicode=true&").append(dataBase.getCharacterEncoding());
					DruidDataSource druidDataSource = new DruidDataSource();
					druidDataSource.setUsername(dataBase.getUsername());
					druidDataSource.setPassword(dataBase.getPassword());
					druidDataSource.setUrl(jdbcUrl.toString());
					druidDataSource.setDriverClassName(dataBase.getDriverClassName());
					druidDataSource.setMaxWait(60000);
					druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
					druidDataSource.setMinEvictableIdleTimeMillis(60000);
					druidDataSource.setValidationQuery("SELECT 'x'");
					druidDataSource.setTestWhileIdle(true);
					druidDataSource.setTestOnBorrow(false);
					druidDataSource.setTestOnReturn(false);
					Log4jdbcProxyDataSource log4jdbcProxyDataSource = new Log4jdbcProxyDataSource(druidDataSource);
					DynamicDataSource.targetDataSources.put(dataBase.getId(), log4jdbcProxyDataSource);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String determineCurrentLookupKey() {
		String dataSourceName = DataSourceHolder.DATASOURCEID;
		if (dataSourceName == null || dataSourceName == "dataSource") {
			// 默认的数据源名字
			dataSourceName = "dataSource";
		}
		log.debug("use datasource : " + dataSourceName);
		return dataSourceName;
	}

	
	@Override
	@MultipleDataSource
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

	private boolean isExistDatabase(String database) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;// 数据库结果集
		try {
			conn = masterDataSource.getConnection();
			stmt = conn.createStatement();
			String sql = "select count(*) from information_schema.schemata where schema_name=\"" + database + "\"";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				if (rs.getInt(1) == 0) {
					return false;
				} else {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			throw new ServiceException(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new ServiceException(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR),
						"mysql关闭连接失败：" + e.getMessage(), e);
			}
		}
	}

	private List<DataBase> queryDataBaseList() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = masterDataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(querySql);
			List<DataBase> list = resultToList(resultSet, DataBase.class);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new ServiceException(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR),
						"mysql关闭连接失败：" + e.getMessage(), e);
			}
		}
		return Lists.newArrayList();
	}

	public void setMasterDataSource(DataSource masterDataSource) {
		this.masterDataSource = masterDataSource;
	}
	
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	
	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		createDataBase();
		super.setTargetDataSources(DynamicDataSource.targetDataSources);
	}
}
