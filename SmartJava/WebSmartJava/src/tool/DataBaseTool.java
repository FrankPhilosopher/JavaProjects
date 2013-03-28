package tool;

import java.sql.*;

public class DataBaseTool {
	private static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 加载JDBC驱动
	private static String dbURL = "jdbc:sqlserver://192.168.1.122:1433; DatabaseName=websmartjava"; // 连接服务器和数据库
	private static String userName = "sa"; // 用户名
	private static String userPwd = "icss"; // 密码
	
	static{
		try {
			Class.forName(driverName);// 加载驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

	// 获取连接
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 创建数据库连接
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 执行sql语句
	public static int executeSQL(Connection conn, String sql) {
		System.out.println("executeSQL: "+sql);
		int count = 0;
		try {
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			// 执行SQL，并获取返回行数
			count = stmt.executeUpdate(sql);
			// 关闭statement
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 执行sql查询语句
	public static ResultSet executeQuery(Connection conn, String sql) {
		System.out.println("executeQuery: "+sql);
		ResultSet rs = null;
		try {
			// 创建执行SQL的对象
			Statement stmt = conn.createStatement();
			// 执行SQL，并获取返回结果
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 释放资源
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
