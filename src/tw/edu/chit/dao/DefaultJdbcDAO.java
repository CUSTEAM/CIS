package tw.edu.chit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author JOHN
 */
public class DefaultJdbcDAO extends JdbcDaoSupport {

	/**
	 * 以SQL查詢取得List
	 * @param sql 標準SQL
	 * @return SQL查詢結果
	 */
	public List StandardSqlQuery(String sql) {
		return getJdbcTemplate().queryForList(sql);		
	}
	
	/**
	 * 執行sql
	 * @param sql 標準SQL
	 */
	public void exQuery(String sql){		
		getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 以SQL查詢取得1個數值
	 * @param sql 標準SQL
	 * @return 1個數值
	 */
	public Integer sqlGetInt(String sql){		
		return getJdbcTemplate().queryForInt(sql);
	}	
	
	/**
	 * 以SQL查詢取得1筆Map資料
	 * @param sql
	 * @return 1筆map型態的資料
	 */
	public Map sqlGetMap(String sql){		
		return getJdbcTemplate().queryForMap(sql+" LIMIT 1");
	}
	
	/**
	 * 通用查詢1個字串
	 * @param sql
	 * @return java.lang.String
	 */
	public String sqlGetString(String sql){
		return (String)getJdbcTemplate().queryForObject(sql+" LIMIT 1", java.lang.String.class);
	}
}
