package com.tan.excel.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import com.tan.bean.Kubun;

/**
 * 
 * @author dolphin
 *
 * 2010-4-30 上午10:44:24
 */
public class DBUtil {
	
	private static final String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://192.168.3.202;databaseName=pswg";
	private static final String USER = "sa";
	private static final String PASSWORD = "safehitech0555!@";

	
	// 加载 Driver
	static {
		try {
			Class.forName(driverClassName).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Getter
	@Setter
	private   Connection connection;
	
	private PreparedStatement pstmt ;
	
	private ResultSet rs ;
	
	private Properties props;
	
	
	/**
	 * initialize the connection
	 */
	public void init() {
		if (connection != null) {
			return;
		}
		props = new Properties();
		props.setProperty("user", USER);
		props.setProperty("password", PASSWORD);
		try {
			connection = DriverManager.getConnection(URL, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Dispose the result set.
	 * Dispose the prepared statement.
	 * Dispose the conneciton.
	 */
	public void dispose() {
		try {
			if (rs !=  null && !rs.isClosed()) {
				rs.close();
				rs = null;
			}
			if (pstmt !=  null && !pstmt.isClosed()) {
				pstmt.close();
				pstmt = null;
			}
			if (connection != null && !connection.isClosed()){
				connection.close();
				connection = null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertKubun(Kubun kubun) {
		this.init();
		try {
			pstmt =  connection.prepareStatement(
						"INSERT INTO PSWG.dbo.KUBUN_MST (KBN_KEY,KBN_CODE,KBN_NAME,KBN_INFO_CLASS,KBN_INFO,KBN_FIELD1,KBN_FIELD2,KBN_FIELD3) VALUES (?,?,?,?,?,?,?,?)");
			pstmt.setString(1, kubun.getKey());
			pstmt.setString(2, kubun.getCode());
			pstmt.setString(3, "KBN_NAME");
			pstmt.setString(4, "KBN_INFO_CLASS");
			pstmt.setString(5, "KBN_INFO");
			pstmt.setInt(6, 1);
			pstmt.setInt(7, 1);
			pstmt.setInt(8, 1);
//			pstmt.setString(7, "KBN_FIELD2");
//			pstmt.setString(8, "KBN_FIELD3");
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
	
	/**
	 * 查看所有表中数据.
	 * @param tableName
	 */
	public void select(String tableName) {
		this.init();
		try {
			pstmt = connection.prepareStatement("SELECT * FROM " + tableName);
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			printColumns(columnCount, meta);
			meta = null;
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					Object o = rs.getObject(i);
					if (i == 1) print(o, '\t', (short) 0);
					print(o, '\t', (short) 1);
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
	
	
	private void printColumns(int columnCount, ResultSetMetaData meta) {
		for (int i = 1; i <= columnCount; i++) {
			try {
				if (i == 1) {
					System.err.print(meta.getColumnName(i));
				} else {
					System.err.print('\t' + meta.getColumnName(i));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.err.println();
	}

	public void selectTables() {
		this.init();
		try {
			pstmt = connection.prepareStatement("SELECT * FROM LicenseSubmitInfo");
//			pstmt = connection.prepareStatement("SELECT * FROM fn_listextendedproperty(NULL,'SCHEMA','dbo','table', NULL,NULL,NULL)");
//			pstmt = connection.prepareStatement("SELECT * FROM fn_listextendedproperty(" + null + " ,'SCHEMA','dbo','table'," + null + " ," + null + " ," + null + " )");
			rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			printColumns(columnCount, meta);
			meta = null;
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					Object o = rs.getObject(i);
					if (i == 1) print(o, '\t', (short) 0);
					print(o, '\t', (short) 1);
				}
				System.out.println();
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
	
	private void print(Object o, char indent, short count) {
		for (short i = 0 ; i < count; i++) {
			System.out.print(indent);
		}
		System.out.print(String.valueOf(o));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DBUtil db = new DBUtil();
//		db.insertKubun();
//		db.select("pswg.sys.tables");
//		db.select("sys.all_columns");
//		db.select("fn_listextendedproperty(null,'SCHEMA','dbo','table',null,null,null)");
//		db.selectTables();
		db.selectTables();
		
//		try {
//			List<Kubun> kubuns = ReadKubunUtil.readKubunExcel(DBUtil.class.getResource("kubun.xls").getFile());
//			for (Kubun kubun : kubuns) {
//				System.out.println(kubun);
//			}
//			System.out.println("一共有: " + kubuns.size() + "条记录");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
