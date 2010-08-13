package com.tan.db;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * DB操作类
 * 
 * @author: Dolphin
 * @date: 2010.03.30
 */
public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// private static final String DRIVER = "org.gjt.mm.mysql.Driver";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	private static SoftReference<Connection> reference = new SoftReference<Connection>(
			getConnection());
	private static final String URL = "jdbc:mysql://localhost:3306/qdao";
	private static final String USER = "root";
	private static final String PASSWORD = "tanyuanji";

	// private static final String URL =
	// "jdbc:mysql://localhost:3306/udb_dolphin?useUnicode=true&amp;characterEncoding=GBK";
	// private static final String USER = "dolphin";
	// private static final String PASSWORD = "includemain";

	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String sql = null;

	private String regex = null;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public static Connection getConnection() {
		if (reference != null) {
			return reference.get();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private Connection connect() {
		Connection connection = reference.get();
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean create() {
		if (!sql.trim().toLowerCase().matches("^create table.*")) {
			return false;
		}
		try {
			pstmt = connect().prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		close();
		closeConnection(connect());
		return true;
	}

	public boolean matches() {
		if (sql == null)
			return false;
		if (regex == null)
			return false;
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(sql);
		return m.matches();
	}

	public boolean drop() {
		setRegex("^drop table.*$");
		if (!matches()) {
			return false;
		}
		try {
			pstmt = connect().prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		close();
		closeConnection(connect());
		return true;
	}

	public void insertBlob(String name, String password, InputStream pic) {
		try {
			pstmt = connect().prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setBinaryStream(3, pic, (int) pic.available());
			pstmt.executeUpdate();
			pic.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		close();
		closeConnection(connect());
	}

	public InputStream selectImage(String id) {
		InputStream in = null;
		try {
			pstmt = connect().prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				in = rs.getBinaryStream(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		closeConnection(connect());
		return in;
	}
	
	public List<String> showTables() {
		List<String> tables = new ArrayList<String>();
		try {
			pstmt = connect().prepareStatement("SHOW TABLES");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		closeConnection(connect());
		return tables;
	}

	private void close() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
