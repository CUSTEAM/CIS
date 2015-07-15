package tw.edu.chit.dao.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import tw.edu.chit.dao.DAO;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.</p>
 *
 * <p><a href="BaseDAOHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseDAOHibernate extends HibernateDaoSupport implements DAO {
	
    protected final Log log = LogFactory.getLog(getClass());
    
    /**
     * @see com.neoasia.dao.DAO#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
        getHibernateTemplate().flush();
    }

    /**
     * @see com.neoasia.dao.DAO#getObject(java.lang.Class, java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public Object getObject(Class clazz, Serializable id) {
        Object o = getHibernateTemplate().get(clazz, id);
        if (o == null) {
            throw new ObjectRetrievalFailureException(clazz, id);
        }
        return o;
    }

    /**
     * @see com.neoasia.dao.DAO#getObjects(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public List getObjects(Class clazz) {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see com.neoasia.dao.DAO#removeObject(java.lang.Class, java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public void removeObject(Class clazz, Serializable id) {
        getHibernateTemplate().delete(getObject(clazz, id));
    }
    
    public void removeObject(Serializable po) {
        getHibernateTemplate().delete(po);
    }
    
    public void reload(Serializable po) {
    	getHibernateTemplate().refresh(po);
    }
    
    @SuppressWarnings("unchecked")
	public List submitQuery(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * Submit SQL update command using underlying JDBC connection of Hibernate Session.
	 * Since transaction is managed by Spring and all Manager classes are inherited from
	 * transactionManager which is of type HibernateTransactionManager (see ApplicationContext),
	 * any database update within such transaction should be executed by Hibernate supported connection.
	 * 
	 * @param sql SQL command, NOT HQL
	 */
	public void executeSQL(String sql) {
		
		Connection conn = getSession().connection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
			try {
				stmt.close();
			} catch(Exception ex) {}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Submit SQL update command using underlying JDBC connection of Hibernate Session.
	 * Since transaction is managed by Spring and all Manager classes are inherited from
	 * transactionManager which is of type HibernateTransactionManager (see ApplicationContext),
	 * any database update within such transaction should be executed by Hibernate supported connection.
	 * 
	 * @param sql  SQL command in the form of prepared statement, NOT HQL
	 * @param arg  Argument array of prepared statement
	 * @param type Array of argument type, integer constant defined in java.sql.Types
	 */
	public void executeSQL(String sql, Object[] arg, int[] type) {
		
		Connection conn = getSession().connection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			for (int i=0; i < arg.length; i++) {
				stmt.setObject(i+1, arg[i], type[i]);
			}
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	

}
