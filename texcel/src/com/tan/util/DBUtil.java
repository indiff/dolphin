package com.tan.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author dolphin
 *
 * 2010/05/25 17:35:31
 */
public final class DBUtil {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static ThreadLocal<Connection> handler = 
		new ThreadLocal<Connection>();
	
	/**
	 * get the connection by the thread local.
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = handler.get();
		if (conn == null) {
			Properties info = new Properties();
			info.setProperty("user", "root");
			info.setProperty("password", "tanyuanji");
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qdao?useUnicode=true", info);
				handler.set(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	
	/**
	 * close the connection.
	 */
	public static void close() {
		Connection conn = handler.get();
		if (conn != null) {
			try {
				conn.close();conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			handler.remove();
		}
	}
	
	/**
	 * begin the transaction.
	 */
	public static void beginTransaction() {
		Connection conn = handler.get();
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * commit the transaction.
	 */
	public static void commit() {
		Connection conn = handler.get();
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void rollback() {
		Connection conn = handler.get();
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		selectAll("SELECT * FROM t_life");
		command("SHOW TABLES");
	}

	
	/**
	 * select all from the table which in the sql.
	 * @param sql
	 * @throws SQLException
	 */
	private static void selectAll(String sql)   {
		Connection conn = DBUtil.getConnection();
		CallableStatement csmt;
		ResultSet rs;
		try {
			csmt = conn.prepareCall(sql);
			
			rs = csmt.executeQuery();
			
			ResultSetMetaData md = rs.getMetaData();
			int count = md.getColumnCount();
			rs = csmt.executeQuery();
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					System.out.print(i == 1 ? "" : '\t' + nvl(rs.getObject(i)));
				}
				System.out.println();
			}
			csmt.close();
			rs.close();
		} catch (SQLException e) {
			DBUtil.rollback();
			e.printStackTrace();
		} 
		rs = null;
		csmt =  null;
		DBUtil.close();
	}
	
	/**
	 * exe the command.
	 * @param cmd
	 * @throws SQLException
	 */
	private static void command(String cmd) {
		Connection conn = DBUtil.getConnection();
		CallableStatement csmt;
		ResultSet rs;
		try {
			csmt = conn.prepareCall(cmd);
			
			rs = csmt.executeQuery();
			while (rs.next()) {
				System.out.println(nvl(rs.getObject(1)));
			}
			csmt.close();
			rs.close();
		} catch (SQLException e) {
			DBUtil.rollback();
			e.printStackTrace();
		} 
		rs = null;
		csmt =  null;
		DBUtil.close();
	}
	
	private static <T> String nvl(T t) {
		String v = String.valueOf(t);
		if (v == null || v.trim().length() == 0) {
			return "";
		}
		return v;
	}
}
