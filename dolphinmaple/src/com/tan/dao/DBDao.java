package com.tan.dao;

import java.sql.ResultSet;

import com.tan.biz.DB;


/**
 * DB interface
 * */
public interface DBDao {
	
	DB db = new DB();
	
	/**
	 * insert the record
	 * */
	int insert(String sql, String ... args) throws Exception ;
	/**
	 * delete the record
	 * */
	int delete(String query, String ... args) throws Exception;
	/**
	 * update the record
	 * */	
	int update(String updStr, String ... args) throws Exception;
	/**s
	 * select records
	 * */	
	ResultSet select(String query, String ... args) throws Exception;
}
