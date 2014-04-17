package cn.wen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private Connection conn = null;

	public Connection getConnection() {

		try {
			/* 1 加载数据库驱动 */
			Class.forName(ConfigUtil.CLASS_NAME);
			/* 组装成链接URL */
			String url = ConfigUtil.DATABASE_URL + "//" + ConfigUtil.SERVER_IP
					+ ":" + ConfigUtil.SERVER_PORT + "/"
					+ ConfigUtil.DATABASE_SID;
			try {
				// 生成链接
				//System.out.println(url);	
				conn = DriverManager.getConnection(url, ConfigUtil.USERNAME,ConfigUtil.PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
