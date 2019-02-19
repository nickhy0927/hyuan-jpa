package com.iss.platform.init;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.http.HttpStatus;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.Underline2Camel;
import com.iss.platform.system.database.entity.DataBase;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

public class InitDataBase {

	private DataSource dataSource;

	private String querySql;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void createDataBase() {
		try {
			Connection connection = dataSource.getConnection();
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

	private boolean isExistDatabase(String database) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;// 数据库结果集
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name=\"" + database + "\"";
			System.out.println(sql);
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

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	private List<DataBase> queryDataBaseList() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
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
}
