package tw.edu.chit.dao.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.util.IConstants;

public class StudAffairDAOImpl extends BaseDAOHibernate implements StudAffairDAO {

	public List submitQuery(String hql) {
		return getHibernateTemplate().find(hql);
	}
	
	public List submitQuery(String hql, Object[] parameters) {
		return getHibernateTemplate().find(hql, parameters);
	}

	public void removeDilg(Dilg dilg) {
		removeObject(dilg);
	}

	public void removeJust(Just just) {
		removeObject(just);
	}
	
	public void removeDesd(Desd desd) {
		removeObject(desd);
	}
	public int removeAnyThing(String hql) {
		return getHibernateTemplate().bulkUpdate(hql);
	}
	
	public List<Dilg> findDilgByStudentNo(String studentNo) {
		return (List<Dilg>)(getHibernateTemplate().find("From Dilg Where studentNo='" + studentNo + "' Order by ddate"));
	}

	public List<Seld> findSeldByStudentNo(String studentNo, String sterm) {
		
		
		//System.out.println("Select s, d.cscode From Seld s, Dtime d Where s.studentNo='" + studentNo
				//+ "' And s.dtimeOid=d.oid And d.sterm='" + sterm + "' order by d.cscode");
		
		
		List seldList = getHibernateTemplate().find("Select s, d.cscode From Seld s, Dtime d Where s.studentNo='" + studentNo
				+ "' And s.dtimeOid=d.oid And d.sterm='" + sterm + "' order by d.cscode");
		
		Object[] objs;
		List<Seld> retList = new ArrayList();
		//System.out.println("Leo00");
		if(seldList.size() > 0) {
			//System.out.println("Leo01");
			//log.debug("findSeldByStudentNo->seldList.size():" + seldList.size() );
			Seld seld;
			Csno csno;
			String courseName;
			String cscode;
			
			
			for(int l=0; l<seldList.size(); l++){
				Iterator myobjs =seldList.iterator();
				objs = (Object[]) myobjs.next();
				cscode = objs[0].toString();				
				//System.out.println(cscode);
			}
			
			for(Iterator seldIter = seldList.iterator(); seldIter.hasNext();) {
				//System.out.println(seldIter.next());
				objs = (Object[]) seldIter.next();
				//System.out.println("objs="+objs);
				cscode = objs[1].toString();
				//System.out.println("cscode="+cscode);
				seld = (Seld)objs[0];
				seld.setCscode(cscode);
				
				csno = this.findNameOfCourse(cscode);
				//System.out.println(csno!=null);
				if(csno != null) {
					//System.out.println("0001");
					courseName = csno.getChiName();
					seld.setCscodeName(csno.getChiName());
				} else {
					//System.out.println("0002");
					courseName = "";
					seld.setCscodeName("");
				}
				retList.add(seld);
				//log.debug("findSeldByStudentNo->cscode,Name:" + seld.getCscode() + "," + courseName );				
			}

		}
		
		return retList;
	}
	
	public List<Seld> findSeldByStudentNoAndCscode(String studentNo,
			String cscode, String sterm) {
		List seldList = getHibernateTemplate().find(
				"SELECT s FROM Seld s, Dtime d WHERE s.studentNo = '"
						+ studentNo + "' AND d.cscode = '" + cscode
						+ "' AND s.dtimeOid = d.oid AND d.sterm = '" + sterm
						+ "' ORDER BY d.cscode");

		List<Seld> retList = new ArrayList<Seld>();
		if (!seldList.isEmpty()) {
			for (Iterator seldIter = seldList.iterator(); seldIter.hasNext();) {
				Seld seld = (Seld) seldIter.next();
				seld.setCscode(cscode);
				Csno csno = this.findNameOfCourse(cscode);
				if (csno != null) {
					seld.setCscodeName(csno.getChiName());
				} else {
					seld.setCscodeName("");
				}
				retList.add(seld);
			}
		}

		return retList;
	}
	
	public Csno findNameOfCourse(String cscode) {
		List<Csno> csnoList = getHibernateTemplate().find("From Csno c Where cscode=?", cscode);
		if(csnoList.size() > 0) return csnoList.get(0);
		return null;
	}
	
	public List<Code2> findCode2List(String code) {
		List<Code2> codeList;
		if(code.equals("")) {
			codeList = getHibernateTemplate().find("From Code2 c2");
		} else {
			codeList = getHibernateTemplate().find("From Code2 c2 Where no=?", code);
		}
		
		return codeList;
	}
	
	
	/**
	 * 查詢轉帳資料
	 * 
	 * @param dipost tw.edu.chit.models.Dipost Object
	 * @return java.util.List List of tw.edu.chit.models.Dipost Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Dipost> getDipostsBy(Dipost dipost) {
		return getHibernateTemplate().findByExample(dipost);
	}
	
	/**
	 * 查詢Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 * @return java.util.List List of tw.edu.chit.models.Dilg Objects
	 */
	@SuppressWarnings("unchecked")
	public List<Dilg> getDilgBy(Dilg dilg) {
		Session session = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		return session.createCriteria(Dilg.class).add(Example.create(dilg))
				.addOrder(Order.asc("ddate")).list();
	}
	
	/**
	 * 
	 * @param studCounseling
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StudCounseling> getStudCounselingBy(
			StudCounseling studCounseling) throws DataAccessException {
		return getHibernateTemplate().findByExample(studCounseling);
	}
	
	@SuppressWarnings("unchecked")
	public List<StdOpinionSuggestion> getStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos) throws DataAccessException {
		return getHibernateTemplate().findByExample(sos);
	}
	
	@SuppressWarnings("unchecked")
	public List<StdOpinionSuggestion> getStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos, Date d) throws DataAccessException {

		Calendar to = Calendar.getInstance();
		to.setTime(d);

		Calendar from = (Calendar) to.clone();
		from.add(Calendar.MINUTE, -10);

		String hql = "FROM StdOpinionSuggestion a WHERE a.schoolYear = ? "
				+ "AND a.schoolTerm = ? AND a.studentOid = ? "
				+ "AND a.lastModified BETWEEN ? AND ? ";
		return getHibernateTemplate().find(
				hql,
				new Object[] { sos.getSchoolYear(), sos.getSchoolTerm(),
						sos.getStudentOid(), from.getTime(), to.getTime() });
	}
	
    public Session getHibernateSession() {
		Session session = getHibernateTemplate().getSessionFactory()
		.getCurrentSession();
		return session;
    }
    
    public void saveOrUpdateStudDocUpload(StudDocUpload upload) throws Exception{
		Session session = getCISBLOBSession(new String[]{});
		Transaction tx  = null;
		try{
			tx  = session.beginTransaction();
			session.saveOrUpdate(upload);
			tx.commit();
		}catch (Exception e){
			if(tx != null){
				try{
					tx.rollback();
				}catch (HibernateException he){
					throw new Exception("saveOrupdate attach file of student Absence doc error!" + he);
				}
			}
			throw e;
		}finally{
			try{
				closeCISBLOBSession(session);
			}catch (HibernateException he){
				he.printStackTrace();
				throw new Exception("saveOrupdate attach file of student Absence doc error!");
			}
		}

		closeCISBLOBSession(session);
    }

    public void removeStudDocUpload(StudDocUpload upload) throws Exception{
		Session session = getCISBLOBSession(new String[]{});
		Transaction tx  = null;
		try{
			tx  = session.beginTransaction();
			session.delete(upload);
			tx.commit();
		}catch (Exception e){
			if(tx != null){
				try{
					tx.rollback();
				}catch (HibernateException he){
					throw new Exception("remove attach file of student Absence doc error!" + he);
				}
			}
			throw e;
		}finally{
			try{
				closeCISBLOBSession(session);
			}catch (HibernateException he){
				he.printStackTrace();
				throw new Exception("remove attach file of student Absence doc error!" + he);
			}
		}
		//closeCISBLOBSession(session);
    }

    public void removeStudPublicDocUpload(StudPublicDocUpload upload) throws Exception{
		Session session = getCISBLOBSession(new String[]{});
		Transaction tx  = null;
		try{
			tx  = session.beginTransaction();
			session.delete(upload);
			tx.commit();
		}catch (Exception e){
			if(tx != null){
				try{
					tx.rollback();
				}catch (HibernateException he){
					throw new Exception("remove attach file of student Public doc error!" + he);
				}
			}
			throw e;
		}finally{
			try{
				closeCISBLOBSession(session);
			}catch (HibernateException he){
				he.printStackTrace();
				throw new Exception("remove attach file of student Public doc error!" + he);
			}
		}
		//closeCISBLOBSession(session);
    }

    public Session getCISBLOBSession(String[] resources){
    	
		Configuration cfg = new Configuration()
		.addResource("tw/edu/chit/model/StudDocUpload.hbm.xml")
	    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    .setProperty("hibernate.connection.url", IConstants.photoDB)
	    .setProperty("hibernate.connection.username", "root")
	    .setProperty("hibernate.connection.password", "spring")
	    ;
		for(int i = 0; i < resources.length; i++){
			cfg.addResource(resources[i]);
		}
		org.hibernate.SessionFactory factory = cfg.buildSessionFactory();
		//org.hibernate.SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = factory.openSession();
		return session;
    }
    
    public void closeCISBLOBSession(Session session){
    	org.hibernate.SessionFactory factory = session.getSessionFactory();
		session.flush();
		session.clear();
		session.close();
		factory.close();   	
    }
}
