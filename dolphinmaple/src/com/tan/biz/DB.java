package com.tan.biz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tan.dao.DBDao;
import com.tan.dao.impl.DBDaoImpl;

/**
 * 
 * DB 处理
 * @author tanyuanji
 */
public class DB {
	
	private static final String USERNAME = "C012K013";
	
	private static final String PASSWORD = "WAMNET";
	
	private static final String URL = "jdbc:oracle:thin:@WAMNET-SVR:1521:WAMNET";
	
	private static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";
	
	private Connection conn ;
	
	public void commit() throws Exception{
		if (conn == null) return;
		else {
			try {
				conn.commit();
			} catch (SQLException e) {
				throw new Exception("提交失败");
			}
		}
	}
	
	public void rollback()  throws Exception{
		if (conn == null) return;
		else {
			try {
				conn.rollback();
			} catch (SQLException e) {
				throw new Exception("回滚失败");
			}
		}
	}
	
	public Connection getConnection() throws Exception{
		try {
			Class.forName(CLASSNAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			throw new Exception("数据库连接失败");
		} catch (SQLException e) {
			throw new Exception("数据库连接失败");
		}
		return conn;
	}
	
	public void close() throws Exception {
		if (conn != null) {
			try { conn.close(); 
			} catch (SQLException e) {
				throw new Exception("数据库连接释放失败");
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String sql = "SELECT SYSDATE FROM DUAL WHERE ?";
//		DB db = new DB();
//		Connection conn = db.getConnection();
//		try {
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println("right");
		
		DBDao dao = new DBDaoImpl();
		ResultSet rs = dao.select(sql, " 1 = 1");
		while (rs.next()) {System.out.println(rs.getString(1));}
	}

}
