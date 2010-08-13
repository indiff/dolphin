package com.tan.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tan.dao.DBDao;

public class DBDaoImpl implements DBDao{

	public int delete(String query, String ... args) throws Exception {
		try {
			PreparedStatement pstmt = 
				db.getConnection().prepareStatement(query);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("记录删除失败");
		}
	}

	public int insert(String sql, String ... args) throws Exception {
		try {
			PreparedStatement pstmt = db.getConnection()
					.prepareStatement(sql);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("记录添加失败");
		}
	}

	public ResultSet select(String query, String ... args)  throws Exception  {
		try {
			PreparedStatement pstmt = db.getConnection()
					.prepareStatement(query);
			for (int i = 0; i < args.length; i++) {
				pstmt.setString(i + 1, args[i]);
			}
			return pstmt.executeQuery();
		} catch (SQLException e) {
			throw new Exception("记录查询失败");
		}
	}

	public int update(String updStr, Object ... args)  throws Exception  {
		try {
			PreparedStatement pstmt = db.getConnection()
					.prepareStatement(updStr);
			for (int i = 0; i < args.length; i++) {
				pstmt.setObject(i, args[i]);
			}
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("记录更新失败");
		}
	}

	public int update(String updStr, String... args) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
