package com.yinger.patientims.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库操作的基础类，它是其他的DAO类的父类
 * 定义了建立数据库连接和关闭数据库连接的方法
 */
public class DAOBase {
	private Connection connection;

	// 得到数据库连接
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/patientims", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	// 关闭数据库连接
	public void closeConnnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection = null;
			}
		}
	}

}
