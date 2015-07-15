package tw.edu.chit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SummerJdbcDAO extends JdbcDaoSupport{
	
	/**
	 * 隨便查
	 * @param sql
	 * @return
	 */
	public List StandardSqlQuery(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查個數字出來
	 * @param sql
	 * @return
	 */
	public int StandardSqlQueryForInt(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * 歐啦歐啦歐啦
	 * @param sql
	 * @return
	 */
	public Map StandardSqlQueryForString(String sql){
		return getJdbcTemplate().queryForMap(sql);
	}
	
	/**
	 * 隨便殺
	 * @param sql
	 */
	public void removeObj(String sql){
		getJdbcTemplate().execute(sql);
	}

}
