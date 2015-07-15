package tw.edu.chit.dao;

import java.util.Date;
import java.util.List;

import tw.edu.chit.model.Student;
import tw.edu.chit.util.Toolket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AmsJdbcDAO extends JdbcDaoSupport {
	private static Log log = LogFactory.getLog(AmsJdbcDAO.class);

	public void Nothing() {
		
	}
	
	public List findAnyThing(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}
		
	public int updateAnyThing(String sql) {
		return getJdbcTemplate().update(sql);
	}
	
	public void executesql(String sql){
		getJdbcTemplate().execute(sql);
	}
	
	public int getRecordsCount(String sql){
		return getJdbcTemplate().queryForInt(sql);
	}

}
