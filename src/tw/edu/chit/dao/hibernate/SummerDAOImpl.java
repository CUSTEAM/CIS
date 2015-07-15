package tw.edu.chit.dao.hibernate;

import org.apache.log4j.Logger;

import tw.edu.chit.dao.SummerDAO;

public class SummerDAOImpl extends BaseDAOHibernate implements SummerDAO {
	
	protected static final Logger log = Logger.getLogger(CourseDAOImpl.class);
	
	public void saveObject(Object po){
		getHibernateTemplate().save(po);
		getHibernateTemplate().flush();
	}

}
