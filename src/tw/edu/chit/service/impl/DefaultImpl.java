package tw.edu.chit.service.impl;

import java.util.List;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.dao.CourseJdbcDAO;

public class DefaultImpl extends BaseManager{
	
	private CourseDAO	courseDao;	
	private CourseJdbcDAO	jdbcDao;
	
	public void setCourseDAO(CourseDAO dao) {
		this.courseDao = dao;
	}

	public void setJdbcDAO(CourseJdbcDAO jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public String sqlGetStr(String sql) {
		return jdbcDao.ezGetString(sql);
	}
	
	public List sqlGetList(String sql){
		return jdbcDao.StandardSqlQuery(sql);
	}

}
