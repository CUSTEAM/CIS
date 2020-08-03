package tw.edu.chit.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.struts.upload.FormFile;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.dao.CourseJdbcDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.gui.Menu;
import tw.edu.chit.gui.MenuItem;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.AdcdHistory;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.CoQuestion;
import tw.edu.chit.model.Coansw;
import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplSave;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.EpsPages;
import tw.edu.chit.model.EpsUser;
import tw.edu.chit.model.ExamDate;
import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.LiteracyType;
import tw.edu.chit.model.MailStorage;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Message;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Nabbr;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.Parameter;
import tw.edu.chit.model.PubCalendar;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.SeldConflict;
import tw.edu.chit.model.SeldCouFilter;
import tw.edu.chit.model.SeldStuFilter;
import tw.edu.chit.model.StdAdcd;
import tw.edu.chit.model.StdLoan;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Syllabu;
import tw.edu.chit.model.Syllabus;
import tw.edu.chit.model.SyncError;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.impl.exception.SeldException;
import tw.edu.chit.struts.action.course.exception.NoClassDefinedException;
import tw.edu.chit.struts.action.portfolio.FtpClient;
import tw.edu.chit.struts.action.sysadmin.sshtools.SSHClient;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class CourseManagerImpl extends BaseManager implements CourseManager {

	private CourseDAO	courseDao;
	private MemberDAO   memberDao;
	private CourseJdbcDAO	jdbcDao;

	public void setCourseDAO(CourseDAO dao) {
		this.courseDao = dao;
	}

	public void setJdbcDAO(CourseJdbcDAO jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public void setMemberDAO(MemberDAO dao) {
		this.memberDao = dao;
	}

	@SuppressWarnings("unchecked")
	public List<Csno> findCoursesBy(String csno, String csname, String csename) {
		return courseDao.getCourse(csno, csname, csename);
	}

	public void createCourseNameBy(Csno csno) {

		courseDao.saveCsno(csno);
	}

	/**
	 * delete Csno By properties for setCourseNameAction.
	 */
	public void deleteCourseNameBy(Csno csno){

		courseDao.removeCsno(csno);
	}
	
	/**
	 * 處理刪除StdLoan資料表之方法
	 * 
	 * @param stdLoan tw.edu.chit.model.StdLoan object
	 */
	public void deleteStdLoan(StdLoan stdLoan) {
		courseDao.deleteStdLoan(stdLoan);
	}

	@SuppressWarnings("unchecked")
	public List<Dtime> getDtimeForDeleteCourse(String cscode){

		return courseDao.getDtimeForDeleteCourseName(cscode);
	}

	public void updateCourseNameBy(Csno csno) {

		courseDao.saveCsno(csno);

	}

	@SuppressWarnings("unchecked")
	public List<Employee> getEmployeeForOpenCs() {
		return courseDao.getEmployeeForOpenCS();
	}

	/**
	 * search Csno By properties for setCourseNameAction.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Csno> findCourseForOpenCs() {
		return courseDao.getCsnoForOpenCs();
	}

	@SuppressWarnings("unchecked")
	public List<Clazz> getClassForOpenCs() {

		return courseDao.getClassForOpenCs();
	}

	/**
	 * get dtime list for OpenCourse search(1) bar.
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCs(Clazz[] classes, String campusInCharge2, String schoolInCharge2,
			String deptInCharge2, String departClass, String cscode,
			String techid, String term,String choseType, String open, String elearning) {

		StringBuffer strBuf=new StringBuffer("FROM Dtime d, Csno c, Clazz s, Empl e WHERE d.cscode LIKE '%"+cscode+"%' AND " +
				"d.open LIKE '"+open+"' AND d.techid=e.idno AND d.elearning LIKE '"+elearning+
				"' AND d.techid LIKE '"+techid+"%' AND d.departClass IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.departClass LIKE '"+campusInCharge2+schoolInCharge2+deptInCharge2+
				"%' AND d.departClass LIKE '"+departClass+"%' AND d.sterm LIKE '"+term+"' AND d.opt LIKE '"+choseType+"' "
				+"AND d.open LIKE '"+open+"' AND d.elearning LIKE '"+elearning+"'");
		strBuf.append(" ORDER BY d.departClass");
		String sql=strBuf.toString();

		List dtimeList=courseDao.getDtimeForOpenCs(sql);
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		Empl	 aEmpl;
		List     table = new ArrayList();
		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
			aEmpl  = (Empl)aTripple[3];
			/*
			 * if(aDtime.getOpt().equals("1")){ aDtime.setOpt2("必修"); }
			 * if(aDtime.getOpt().equals("2")){ aDtime.setOpt2("選修"); }
			 * if(aDtime.getOpt().equals("3")){ aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setTechName(aEmpl.getCname());
			aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if(aDtime.getDepartClass().equals(aClazz.getClassNo())){
				aDtime.setChiName2(aClazz.getClassName());
			}

			if(aDtime.getCscode().equals(aCsno.getCscode())){
				aDtime.setChiName2(aCsno.getChiName());
			}

			if (aDtime.getStuSelect() == null)
				aDtime.setStuSelect(new Short("0"));

			aDtime.setDepartClass2(aClazz.getClassName());
			aDtime.setOpen2(getOpen2(getOpencSize(aDtime.getOid().toString())));
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}

		return table;

	}

	/**
	 * Get Dtime table listing for OpenCourse search(1) bar.
	 * 
	 * @param classes
	 *            Array of Clazz objects
	 * @param campusInCharge2
	 *            校區代碼
	 * @param schoolInCharge2
	 *            學制代碼
	 * @param deptInCharge2
	 *            科系代碼
	 * @param departClass
	 *            班級代碼
	 * @param cscode
	 *            科目代碼
	 * @param techid
	 *            老師代碼
	 * @param term
	 *            學期
	 * @param choseType
	 * @param open
	 *            開放選修與否
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCs(Clazz[] classes, String campusInCharge2,
			String schoolInCharge2, String deptInCharge2, String departClass,
			String cscode, String techid, String term, String choseType,
			Byte open) {

		StringBuffer buffer = new StringBuffer(
				"FROM Dtime d, Csno c, Clazz s, Empl e WHERE d.cscode LIKE '%" + cscode
						+ "%' AND d.techid=e.idno AND d.techid LIKE '" + techid
						+ "%' AND d.departClass IN (");

		for (int i = 0; i < classes.length; i++) {
			buffer.append("'" + classes[i].getClassNo() + "', ");
		}
		buffer.delete(buffer.length() - 2, buffer.length() - 1);
		buffer
				.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.departClass LIKE '"
						+ campusInCharge2
						+ schoolInCharge2
						+ deptInCharge2
						+ "%' AND d.departClass LIKE '"
						+ departClass
						+ "%' AND sterm LIKE '"
						+ term
						+ "' AND opt LIKE '"
						+ choseType + "' ");
		if(open != null)
			buffer.append("AND d.open LIKE '").append(open+"'");
		buffer.append(" ORDER BY d.departClass");
		String sql = buffer.toString();

		List dtimeList = courseDao.getDtimeForOpenCs(sql);
		Object[] aTripple;
		Dtime aDtime;
		Csno aCsno;
		Clazz aClazz;
		Empl  aEmpl;
		List table = new ArrayList();
		for (int i = 0; i < dtimeList.size(); i++) {
			aTripple = (Object[]) dtimeList.get(i);
			aDtime = (Dtime) aTripple[0];
			aCsno = (Csno) aTripple[1];
			aClazz = (Clazz) aTripple[2];
			aEmpl  = (Empl)aTripple[3];
			/*
			 * if (aDtime.getOpt().equals("1")) { aDtime.setOpt2("必修"); } if
			 * (aDtime.getOpt().equals("2")) { aDtime.setOpt2("選修"); } if
			 * (aDtime.getOpt().equals("3")) { aDtime.setOpt2("通識"); } if
			 * (aDtime.getDepartClass().equals(aClazz.getClassNo())) {
			 * aDtime.setChiName2(aClazz.getClassName()); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setTechName(aEmpl.getCname());
			aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if (aDtime.getCscode().equals(aCsno.getCscode())) {
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			aDtime.setOpen2(getOpen2(getOpencSize(aDtime.getOid().toString())));
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}

		return table;

	}

	/**
	 * search for openCourse 'All'
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode, String techid, String term, String choseType, String open, String elearning) {


		StringBuffer strBuf=new StringBuffer("FROM Dtime d, Csno c, Clazz s, Empl e WHERE d.cscode LIKE '%"+cscode+"%' AND d.techid LIKE '"
				+techid+"%' AND e.idno=d.techid AND d.departClass IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.sterm LIKE '"
					  +term+"'AND opt LIKE '"+choseType+"'"+"AND d.open LIKE '"+open+"' AND d.elearning LIKE '"+elearning+"'");
		strBuf.append(" ORDER BY d.departClass");

		List dtimeList=courseDao.getDtimeForOpenCs(strBuf.toString());
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		Empl	 aEmpl;
		List<Dtime> table = new ArrayList<Dtime>();
		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
			aEmpl  = (Empl)aTripple[3];

			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setTechName(aEmpl.getCname());
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if(aDtime.getDepartClass().equals(aClazz.getClassNo())){
				aDtime.setChiName2(aClazz.getClassName());
			}

			if(aDtime.getCscode().equals(aCsno.getCscode())){
				aDtime.setChiName2(aCsno.getChiName());
			}

			if (aDtime.getStuSelect() == null)
				aDtime.setStuSelect(new Short("0"));

			aDtime.setDepartClass2(aClazz.getClassName());
			aDtime.setOpen2(getOpen2(getOpencSize(aDtime.getOid().toString())));
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}

		return table;
	}

	/**
	 * Searching for OpenCourse 'All'
	 * 
	 * @param classes
	 *            班級代碼
	 * @param cscode
	 *            科目代碼
	 * @param techid
	 *            老師代碼
	 * @param term
	 *            學期
	 * @param choseType
	 * @param open
	 *            開放選修與否
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode,
			String techid, String term, String choseType, String open) {

		StringBuffer buffer = new StringBuffer(
				"FROM Dtime d, Csno c, Clazz s, Empl e WHERE d.cscode LIKE '%" + cscode
						+ "%' AND d.techid LIKE '" + techid
						+ "%' AND e.idno=d.techid AND d.departClass IN (");

		for (int i = 0; i < classes.length; i++) {
			buffer.append("'" + classes[i].getClassNo() + "', ");
		}
		buffer.delete(buffer.length() - 2, buffer.length() - 1);
		buffer.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.sterm LIKE '"
						+ term + "'AND opt LIKE '" + choseType + "' ");
		if(open != null)
			buffer.append("AND d.open LIKE '").append(open+"'");
		buffer.append(" ORDER BY d.departClass");

		List dtimeList = courseDao.getDtimeForOpenCs(buffer.toString());
		Object[] aTripple;
		Dtime aDtime;
		Csno aCsno;
		Clazz aClazz;
		List<Dtime> table = new ArrayList<Dtime>();
		for (int i = 0; i < dtimeList.size(); i++) {
			aTripple = (Object[]) dtimeList.get(i);
			aDtime = (Dtime) aTripple[0];
			aCsno = (Csno) aTripple[1];
			aClazz = (Clazz) aTripple[2];
			/*
			 * if (aDtime.getOpt().equals("1")) { aDtime.setOpt2("必修"); } if
			 * (aDtime.getOpt().equals("2")) { aDtime.setOpt2("選修"); } if
			 * (aDtime.getOpt().equals("3")) { aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if (aDtime.getDepartClass().equals(aClazz.getClassNo())) {
				aDtime.setChiName2(aClazz.getClassName());
			}

			if (aDtime.getCscode().equals(aCsno.getCscode())) {
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			aDtime.setOpen2(getOpen2(getOpencSize(aDtime.getOid().toString())));
			table.add(aDtime);
		}

		return table;
	}

	/**
	 * get a dtime for OpenCourse.jsp edit Mode.
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForEditDtime(String oid){

		String sql="FROM Dtime d, Csno c, Clazz s WHERE d.oid="+oid+" AND c.cscode=d.cscode AND s.classNo=d.departClass";
		List dtimeList=courseDao.getDtimeForOpenCs(sql);
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;

		List<Dtime>     table = new ArrayList<Dtime>();

		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
				aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
				aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
				aDtime.setOpenName(getOpenName(aDtime.getOpen()));
				aDtime.setElearningName(getElearningName(aDtime.getElearning()));
				aDtime.setChiName2(aClazz.getClassName());
				aDtime.setChiName2(aCsno.getChiName());
				aDtime.setDepartClass2(aClazz.getClassName());
				aDtime.setOpen2(getOpen2(getOpencSize(aDtime.getOid().toString())));

			table.add(aDtime);
		}

		return table;
	}

	@SuppressWarnings("unchecked")
	public List getEmplForOpenCourse(String techid) {

		String sql="FROM Empl e WHERE e.idno='"+techid+"'";

		return courseDao.getEmplForOpenCourse(sql);
	}

	@SuppressWarnings("unchecked")
	public List getDtimeClassListForOpenCourse(String oid) {

		String sql="FROM DtimeClass d WHERE d.dtimeOid="+oid;

		return courseDao.getDtimeClassListForOpenCourse(sql);
	}

	@SuppressWarnings("unchecked")
	public List getDtimeExamListForOpenCourse(String oid) {
		/*
		 * String sql="from DtimeExam d WHERE d.dtimeOid='"+oid+"'"; List
		 * dtimeExamList=courseDao.getDtimeExamListForOpenCourse(sql); List<DtimeExam>
		 * table = new ArrayList<DtimeExam>(); DtimeExam aDtimeExam;
		 * 
		 * List <Empl>aExamEmpl=new ArrayList<Empl>(); Empl aEmpl;
		 * if(dtimeExamList.size()>0){
		 * aDtimeExam=(DtimeExam)dtimeExamList.get(0);
		 * aDtimeExam.setExamDate2(Toolket.printNativeDate(aDtimeExam.getExamDate()));
		 * if((aDtimeExam.getExamEmpl()==null)||(aDtimeExam.getExamEmpl().equals(""))){
		 * aEmpl=null; }else{ aExamEmpl=courseDao.getExamTeacher("FROM Empl
		 * WHERE idno='" +aDtimeExam.getExamEmpl()+"'");
		 * 
		 * aEmpl=(Empl)aExamEmpl.get(0);
		 * aDtimeExam.setTechName(aEmpl.getCname()); } table.add(aDtimeExam); }
		 * 
		 * 
		 * return table;
		 */
		String sql="from DtimeExam d WHERE d.dtimeOid='"+oid+"'";
		List dtimeExamList=courseDao.getDtimeExamListForOpenCourse(sql);
		List<DtimeExam> table = new ArrayList<DtimeExam>();
		DtimeExam aDtimeExam;

		Empl aEmpl;

		if(dtimeExamList.size()>0){
			for(int i=0; i<dtimeExamList.size(); i++){
				aDtimeExam=(DtimeExam)dtimeExamList.get(i);
				aDtimeExam.setExamDate2(Toolket.printNativeDate(aDtimeExam.getExamDate()));

				if(!aDtimeExam.getExamEmpl().trim().equals("")&& !aDtimeExam.getExamEmpl().equals(null)){
					List <Empl>aExamEmpl=new ArrayList<Empl>();
					aExamEmpl=courseDao.getExamTeacher("FROM Empl WHERE idno='"+aDtimeExam.getExamEmpl()+"'");
					aEmpl=(Empl)aExamEmpl.get(0);
					aDtimeExam.setTechName(aEmpl.getCname());
				}
				table.add(aDtimeExam);
			}
		}


		return table;
	}

	@SuppressWarnings("unchecked")
	public List checkCourseForOpenCourse(String cscode) {

		String sql="select oid from Csno WHERE cscode='"+cscode+"'";
		return courseDao.getEmplForOpenCourse(sql);
	}

	/**
	 * 檢查是否有這位老師
	 */
	@SuppressWarnings("unchecked")
	public List checkEmplForOpenCourse(String idno) {

		String sql="select oid from Empl WHERE idno='"+idno+"'";
		return courseDao.getEmplForOpenCourse(sql);
	}

	/**
	 * 只用來檢查班級重複開課.
	 */
	@SuppressWarnings("unchecked")
	public List checkRushCourseForOpenCourse(String departClass, String cscode, String term, String dtimeOid) {

		String sql="from Dtime WHERE cscode='"+cscode+"' AND departClass='"+departClass+
		"' AND sterm='"+term+"' AND oid<>"+dtimeOid;
		return courseDao.getEmplForOpenCourse(sql);
	}

	@SuppressWarnings("unchecked")
	public List getAclassNameForCreateDtime(String departClass){
		String sql="from Clazz WHERE classNo='"+departClass+"'";
		return courseDao.getEmplForOpenCourse(sql);
	}

	/**
	 * 得到現在學年學期
	 */
	@SuppressWarnings("unchecked")
	public String getNowBy(String termORyear) {
		String hql="FROM Parameter WHERE name='"+termORyear+"'";
		List list=(List)courseDao.StandardHqlQuery(hql);
		Parameter par;
		par=(Parameter)list.get(0);
		return par.getValue();
	}

	/**
	 * 建立修改課程
	 */
	public void updateDtime(Dtime dtime) {

		courseDao.saveDtime(dtime);
	}

	/**
	 * 檢查課目代碼是否有學生歷史成績
	 */
	@SuppressWarnings("unchecked")
	public List checkSeldForCourseName(String cscode) {

		String sql="select cscode from ScoreHist WHERE cscode='"+cscode+"'";
		return courseDao.checkSeldForCourseName(sql);
	}

	@SuppressWarnings("unchecked")
	public List hqlGetBy(String hql) {
		return courseDao.StandardHqlQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List getModifyResult(String cscode, String departClass, String sterm) {

		String sql="FROM Dtime d, Csno c, Clazz s WHERE d.cscode='"+cscode+
		"' AND d.departClass='"+departClass+"' AND d.sterm='"+sterm+
		"' AND c.cscode=d.cscode AND s.classNo=d.departClass";
		List dtimeList=courseDao.getModifyResult(sql);
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		List     table = new ArrayList();
		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
			/*
			 * if(aDtime.getOpt().equals("1")){ aDtime.setOpt2("必修"); }
			 * if(aDtime.getOpt().equals("2")){ aDtime.setOpt2("選修"); }
			 * if(aDtime.getOpt().equals("3")){ aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			aDtime.setExtrapay2(getExtrapay(aDtime.getExtrapay()));
			if(aDtime.getDepartClass().equals(aClazz.getClassNo())){
				aDtime.setChiName2(aClazz.getClassName());
			}

			if(aDtime.getCscode().equals(aCsno.getCscode())){
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}
		return table;
	}

	/**
	 * check room for ReOpenCourse..
	 */
	@SuppressWarnings("unchecked")
	public List checkDtimeClassForOpenCourse(String week, String beginOrEnd, String place, String sterm) {

		String sql="Select dc.oid, d.oid from DtimeClass dc, Dtime d WHERE d.sterm='"+sterm+
		"' AND dc.week="+week+
		" AND dc.begin>="+beginOrEnd+" AND dc.end<="+beginOrEnd+" AND dc.place='"+place+
		"' AND d.oid=dc.dtimeOid";

		return courseDao.getEmplForOpenCourse(sql);
	}

	/**
	 * 檢查教室存在
	 */
	@SuppressWarnings("unchecked")
	public List checkNabbrForOpenCs(String roomId) {

		String sql="select roomId from Nabbr WHERE roomId='"+roomId+"'";

		return courseDao.getEmplForOpenCourse(sql);
	}

	@SuppressWarnings("unchecked")
	public List getDtimeClassByOid(String oid) {

		String sql=" from DtimeClass WHERE dtimeOid='"+oid+"'";
		return courseDao.getEmplForOpenCourse(sql);
	}

	/**
	 * 處理以學號查詢Seld選課清單之方法
	 * 
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	@SuppressWarnings("unchecked")
	public List getSeldDataByStudentNo(String studentNo, String term) {
		// return courseDao.getSeldDataByStudentNo(studentNo, term);
		return jdbcDao.getSeldDataByStudentNo(studentNo, term);
	}
	
	/**
	 * 處理以學號查詢Seld選課清單之方法
	 * 
	 * @comment 不包括Dtime_class資料
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	@SuppressWarnings("unchecked")
	public List getSeldDataByStudentNo1(String studentNo, String term) {
		// return courseDao.getSeldDataByStudentNo(studentNo, term);
		return jdbcDao.getSeldDataByStudentNo1(studentNo, term);
	}

	/**
	 * 檢查同一時段教師重複授課
	 * 
	 * @return 教師編號、一周中的重複開課日/重複節次
	 */
	public List checkEmplForReOpenCourse(String techid, String week, String beginOrEnd, String sterm, String dtimeOid) {
		String sql="SELECT e.cname, d.techid, d.depart_class, cs.ClassName, c.chi_name, dc.week, dc.begin, dc.end FROM Class cs, Csno c, Dtime d, empl e, Dtime_class dc "+
		"WHERE e.idno=d.techid AND dc.Dtime_oid=d.oid AND d.techid='"+techid+"' AND dc.week="+week+" AND dc.begin <="+beginOrEnd+
		" AND dc.end >="+beginOrEnd+" AND d.sterm="+sterm+" AND d.oid<>"+dtimeOid+" AND c.cscode=d.cscode AND cs.ClassNo=d.depart_class";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 檢查同一時段教室重複遭到使用
	 * 
	 * @return 教室編號、一周中的重複開課日/重複節次
	 */
	@SuppressWarnings("unchecked")
	public List checkReOpenRoom(String place, String week, String beginOrEnd, String sterm, String dtimeOid) {
		/*
		 * String sql="FROM DtimeClass dc WHERE dc.dtimeOid<>"+dtimeOid+" AND
		 * week="+week+" AND begin>="+beginOrEnd+ " AND dc.end<="+beginOrEnd+"
		 * AND place='"+place+"'"; return courseDao.getEmplForOpenCourse(sql);
		 */
		String sql="SELECT cs.className, d.depart_class, dc.place, c.chi_name, dc.week, dc.begin, dc.end FROM Csno c, Dtime d, Dtime_class dc, Class cs"+
		" WHERE dc.Dtime_oid=d.oid AND dc.place='"+place+"' AND dc.week="+week+" AND CAST(dc.begin AS UNSIGNED)<="+beginOrEnd+
		" AND CAST(dc.end AS UNSIGNED)>="+beginOrEnd+" AND d.sterm="+sterm+" AND d.oid<>"+dtimeOid+" AND c.cscode=d.cscode AND cs.classNo=d.depart_class";

		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 檢查同一時段班級重複開課
	 */
	@SuppressWarnings("unchecked")
	public List checkReopenClass(String departClass, String week, String beginOrEnd, String sterm, String dtimeOid) {

		String sql="SELECT c.ClassName, cs.chi_name, dc.week, dc.begin, dc.end FROM Dtime d, Dtime_class dc, Class c, Csno cs WHERE d.Oid=dc.Dtime_oid AND d.cscode=cs.cscode AND d.depart_class=c.ClassNo " +
					"AND d.sterm="+sterm+" AND d.Oid<>"+dtimeOid+" AND CAST(dc.begin AS UNSIGNED)<="+beginOrEnd+" AND CAST(dc.end AS UNSIGNED)>="+beginOrEnd+" AND d.depart_class='"+departClass+"' AND dc.week="+week;

		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 實作以Oid取得Seld物件
	 * 
	 * @param oid
	 *            Seld Oid
	 * @return Seld tw.edu.chit.model.Seld object
	 */
	public Seld getSeldByOid(Integer oid) {
		return courseDao.getSeldByOid(oid);
	}

	/**
	 * 實作增新或更新Seld物件
	 * 
	 * @param seld
	 *            tw.edu.chit.model.Seld object
	 */
	public void saveOrUpdateSeld(Seld seld) {
		courseDao.saveOrUpdateSeld(seld);
	}

	/**
	 * 刪除一個開課時段
	 */
	public void removeDtimeClass(String dtimeOid) {

		String sql="DELETE FROM Dtime_class WHERE Dtime_oid="+dtimeOid;
		jdbcDao.removeDtimeClass(sql);
	}

	/**
	 * 新增或修改一個開課時段
	 */
	public void saveDtimeClass(DtimeClass dtimClass) {
		courseDao.saveDtimeClass(dtimClass);
	}

	/**
	 * 檢查排考的教師重複情形
	 */
	@SuppressWarnings("unchecked")
	public List checkDtimExamIdno(String dtimeOid, String examDate, String idno, String begin) {

		String hql="FROM DtimeExam de WHERE de.dtimeOid<>"+dtimeOid+" AND examDate='"+examDate+
		"' AND ebegin="+begin+" AND examEmpl='"+idno+"'";

		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 檢查排考的教室重複情形
	 */
	@SuppressWarnings("unchecked")
	public List checkDtimExamPlace(String dtimeOid, String examDate, String place, String begin) {

		String hql="FROM DtimeExam de WHERE de.dtimeOid<>"+dtimeOid+" AND examDate='"+examDate+
		"' AND ebegin="+begin+" AND place='"+place+"'";

		return courseDao.StandardHqlQuery(hql);
	}

	public void removeDtimeExam(String dtimeOid){
		String sql="DELETE FROM Dtime_exam WHERE Dtime_oid="+dtimeOid;
		jdbcDao.removeDtimeClass(sql);
	}

	public void saveDtimeExam(DtimeExam dtimeExam) {
		courseDao.saveDtimeExam(dtimeExam);
	}

	@SuppressWarnings("unchecked")
	public List getDtimeOidForNewDtimeClass(String departClass, String cscode, String sterm) {

		String hql="FROM Dtime WHERE departClass='"+departClass+"' AND cscode='"+cscode+
		"' AND sterm='"+sterm+"'";

		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 找學生選課資料預備刪除前檢視
	 * 
	 * @param dtimeOids
	 * @return java.util.List List of xx objects
	 */
	@SuppressWarnings("unchecked")
	public List getSeldForDeleteDtime(String[] dtimeOids) {

		StringBuffer strBuf=new StringBuffer("SELECT s.studentNo, d.cscode, st.studentName, cl.className  FROM Seld s, Dtime d, Clazz cl, Csno c, Student st WHERE s.dtimeOid=d.oid AND d.cscode=c.cscode AND s.studentNo=st.studentNo AND st.departClass=cl.classNo AND s.studentNo=st.studentNo AND s.dtimeOid IN(");
		for(int i=0; i<dtimeOids.length; i++){
			strBuf.append(dtimeOids[i]+", ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(")");

		List seldList=courseDao.StandardHqlQuery(strBuf.toString());

		Object[]fourInOne;
		Clazz clazz;
		Csno csno;
		Dtime dtime;
		Seld seld;
		Student student;
		List     table = new ArrayList();
		for(int i=0; i<seldList.size(); i++){

		}

		return table;
	}

	/**
	 * 取得排課時數
	 * 
	 * @param dtimeOid
	 */
	@SuppressWarnings("unchecked")
	public int countDtimeClassBy(String dtimeOid){
		int count=0;
		String hql="FROM DtimeClass WHERE dtimeOid="+dtimeOid;
		DtimeClass dtimeClass;
		List dtimeClasses=courseDao.StandardHqlQuery(hql);
		for(int i=0; i<dtimeClasses.size(); i++){
			dtimeClass=(DtimeClass)dtimeClasses.get(i);
			count=count+(Integer.parseInt(dtimeClass.getEnd(), 10)-Integer.parseInt(dtimeClass.getBegin(), 10))+1;
		}
		return count;
	}

	/**
	 * 處理[批次]學生選課加選時之方法, 包括新增Seld資料表,更新Dtime資料表選課人數
	 * 
	 * @commend 會一併新增一筆Adcd
	 * @commend 會一併新增一筆Regs
	 * @commend 選課人數上限問題由前端判斷,不考慮跨選問題
	 * @see CourseManagerImpl.txBatchAddSelectedSeld()
	 * @param seld tw.edu.chit.model.Seld object
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @param checkConflict 是否檢查衝堂?
	 * @throws SeldException 發生選課資料重複或衝堂時
	 */
	public void txAddSelectedSeld(Seld seld, Student student, String term,
			boolean checkConflict) throws SeldException {
		// 選課人數上限問題由前端判斷
		// 判斷Seld有無重複?
		if (processSeldCheck(seld))
			throw new SeldException("選課資料重複!!");

		if (checkConflict) {
			List<DtimeClass> dcs = findDtimeClassInfo(seld);
			for (DtimeClass dc : dcs) {
				int begin = dc.getBegin() != null ? Integer.parseInt(dc
						.getBegin()) : 0;
				int end = dc.getEnd() != null ? Integer.parseInt(dc.getEnd())
						: 0;
				for (int i = begin; i <= end; i++)
					if (!findCourseOverlay(student, term, dc.getWeek(),
							String.valueOf(i)).isEmpty())
						throw new SeldException("加選科目衝堂!!");
			}
		}

		processSeldSaveOrUpdate(seld, student);
	}

	/**
	 * 負責儲存加選時衝堂相關資訊
	 * 
	 * @param seld tw.edu.chit.model.Seld object
	 * @param member tw.edu.chit.model.Member object
	 * @param student tw.edu.chit.model.Student object
	 * @param term Term
	 */
	public void txAddSeldConflictInfo(Seld seld, Member member,
			Student student, String term) {
		SeldConflict sc = new SeldConflict();
		sc.setEmplOid(member.getOid());
		sc.setDtimeOid(seld.getDtimeOid());
		sc.setStudentNo(student.getStudentNo());
		sc.setTerm(term);
		sc.setLastModified(new Date());
		dao.saveObject(sc);
	}

	/**
	 * 處理[單一]學生選課加選時之方法, 包括新增Seld資料表,更新Dtime資料表選課人數
	 * 
	 * @commend 會一併新增一筆Adcd
	 * @commend 會一併新增一筆Regs
	 * @commend 不考慮跨選問題
	 * @see CourseManagerImpl.txBatchAddSelectedSeld()
	 * @param seld tw.edu.chit.model.Seld object
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @param isStudent 學生與否(決定是否紀錄StdAdcd資料)
	 * @param phase 梯次
	 * @throws SeldException 發生選課資料重複,加選科目衝堂或選課人數超過上限時
	 */
	public void txAddSelectedSeldForOneStudent(Seld seld, Student student,
			String term, boolean isStudent, int phase) throws SeldException {
		// 選課人數上限問題由前端判斷
		// 判斷Seld有無重複?
		if (processSeldCheck(seld))
			throw new SeldException("選課資料重複,請檢查勾選的科目是否重複加選!!");
		
		List<DtimeClass> dcs = findDtimeClassInfo(seld);
		for (DtimeClass dc : dcs) {
			int begin = dc.getBegin() != null ? Integer.parseInt(dc.getBegin())
					: 0;
			int end = dc.getEnd() != null ? Integer.parseInt(dc.getEnd()) : 0;
			for (int i = begin; i <= end; i++)
				// 加上系時間衝堂檢查
				if (!findCourseOverlay(student, term, dc.getWeek(),
						String.valueOf(i)).isEmpty())
					throw new SeldException("加選的科目與已加選之科目衝堂，或是與系時間或班會衝堂，請注意!!");
		}

		processSeldSaveOrUpdateForOneStudent(seld, student, term, isStudent, phase);
	}

	/**
	 * 處理批次學生選課加選時之方法,包括新增Seld資料表,更新Dtime資料表選課人數
	 * 
	 * @commend 會一併新增多筆Adcd
	 * @commend 會一併新增多筆Regs
	 * @commend 不考慮跨選問題
	 * @see CourseManagerImpl.txAddSelectedSeld()
	 * @param selds List of Seld objects
	 * @param term 學期
	 * @throws SeldException 發生選課資料重複
	 */
	public String txBatchAddSelectedSeld(List<Seld> selds, String term)
			throws SeldException {
		// 選課人數上限問題由前端判斷
		// 判斷Seld有無重複?
		StringBuffer buf = new StringBuffer();
		for (Seld seld : selds) {
			if (processSeldCheck(seld))
				buf.append(seld.getStudentNo() + ",");
		}
		if (StringUtils.isNotBlank(buf.toString()))
			throw new SeldException("選課資料重複學生名單:\n"
					+ StringUtils.substringBeforeLast(buf.toString(), ","));

		buf = new StringBuffer();
		for (Seld seld : selds) {
			Student student = memberDao.findStudentByStudentNo(seld
					.getStudentNo());
			buf
					.append(addSelectedSeldForOneStudent(seld, student, term,
							false));
		}
		if (StringUtils.isNotBlank(buf.toString()))
			return "選課衝堂之學生名單:\n"
					+ StringUtils.substringBeforeLast(buf.toString(), ",");
		else
			return "";
	}

	/**
	 * 判斷新增Seld時有無重複資料
	 * 
	 * @param seld tw.edu.chit.model.Seld object
	 * @return 有為true,否為false
	 */
	public boolean processSeldCheck(Seld seld) {
		List<Seld> ret = courseDao.findSeldBy(seld);
		return ret.size() > 0;
	}

	/**
	 * 課務組處理學生選課退選時之方法, 包括刪除Seld資料表,更新Dtime資料表選課人數
	 * 
	 * @commend 會一併更新多筆Adcd之Adddraw為"D"
	 * @commend 會一併刪除多筆Regs 
	 * @param studentNo 學生學號
	 * @param classNo 學生班級代碼(已升級)
	 * @param phase Phase
	 * @param isStudent 學生與否(決定是否紀錄StdAdcd資料)
	 * @param seldOids 前端選取Seld資料表之Oid列表(多筆)
	 * @exception SeldException 選課人數超過下限時
	 */
	public synchronized void txRemoveSelectedSeld(String studentNo,
			String classNo, int phase, String seldOids, boolean isStudent)
			throws SeldException {

		// 第一階段無須判斷人數下限問題, 第二三階段再行判斷
		// 選課人數下限問題判斷, 研究所*5人, 專科*25人, 大學*20人
		Csno csno = null;
		Seld seld = null;
		Dtime dtime = null;
		int seldCount = 0;
		
		if (1 != phase) {
			String[] seldOidArr = seldOids.split(",");
			int minCount = findMinCountBySchoolNo(StringUtils.substring(
					classNo, 1, 3));
			for (String d : seldOidArr) {
				try {
					seld = getSeldByOid(Integer.valueOf(d));
					dtime = courseDao.getDtimeBy(seld.getDtimeOid());
					seldCount = findSeldCountByDtimeOid(Integer
							.valueOf(dtime.getOid()));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					continue;
				}
				
				// 一年級所開的課不限制人數下限,否則高年級不能退選
				if (!"1".equals(StringUtils.substring(dtime.getDepartClass(),
						4, 5))
						&& (seldCount - (short) 1) < minCount) {
					csno = findCourseInfoByCscode(dtime.getCscode());
					throw new SeldException("科目[" + csno.getChiName()
							+ "]選課人數超過下限");
				}
			}
		}
		
		// 回傳Seld資料表內Dtime Oid Listing
		String dtimeOids = courseDao.deleteSeldByOids(seldOids);
		// 以回傳之Dtime Oid Listing更新Dtime選課人數
		/*
		 * courseDao.updateDtimeStuSelectByOids(dtimeOids, Short
		 * .valueOf((short) -1));
		 */
		// 取得先前已新增之Adcd
		String[] dtimeOidArr = dtimeOids.split(",");
		for (String dtimeOid : dtimeOidArr) {
			if (StringUtils.isNotBlank(dtimeOid)) {
				seldCount = findSeldCountByDtimeOid(Integer
						.valueOf(dtimeOid));
				courseDao.updateDtimeStdSelect(Integer.valueOf(dtimeOid), Short
						.valueOf((short) seldCount));
			}
		}
		
		// classNo已判斷term
		if (StringUtils.isNotBlank(dtimeOids)) {
			List<Adcd> aList = findExistedAdcd(studentNo, classNo, dtimeOids,
					"A");
			if (!aList.isEmpty()) {
				// 如果先前已新增,則刪除Adcd資料
				for (Adcd adcd : aList)
					courseDao.deleteAdcd(adcd);

			} else {
				// 否則新增n筆Adcd之Adddraw為"D"資料
				Adcd adcd = null;
				for (String dtimeOid : dtimeOidArr) {
					adcd = new Adcd();
					adcd.setDtimeOid(Integer.valueOf(dtimeOid.trim()));
					adcd.setStudentNo(studentNo);
					adcd.setStudepartClass(classNo);
					adcd.setAdddraw("D");
					courseDao.saveOrUpdateAdcd(adcd);
				}
			}
			
			// 學生身分才進行StdAdcd紀錄
			if (isStudent) {
				StdAdcd stdAdcd = null;
				for (String dtimeOid : dtimeOidArr) {
					stdAdcd = new StdAdcd();
					stdAdcd.setDtimeOid(Integer.valueOf(dtimeOid));
					stdAdcd.setStudentNo(studentNo);
					stdAdcd.setAdddraw("D");
					stdAdcd.setLastModified(new Date());
					courseDao.saveObject(stdAdcd);
				}
			}
			
			// 刪除Regs資料
			for (String dtimeOid : dtimeOidArr)
				courseDao.delRegsByDtimeAndStdNo(dtimeOid, studentNo);
		}
	}

	/**
	 * A non-transactional version or txRemoveSelectedSeld() Hi!!! Oscar! IF YOU
	 * MODIFIED txRemoveSelectedSeld() PLEASE DO THE SAME THING TO THIS METHOD
	 * 
	 * @param studentNo
	 * @param classNo
	 * @param seldOids
	 */
	public void removeSelectedSeld(String studentNo, String classNo,
			String seldOids, boolean isStudent) {

		// 此階段無須判斷人數下限問題, 第一二階段再行判斷
		// 選課人數下限問題未判斷, 研究所*5人, 專科*25人, 大學*20人
		// 回傳Seld資料表內Dtime Oid Listing
		String dtimeOids = courseDao.deleteSeldByOids(seldOids);
		// 以回傳之Dtime Oid Listing更新Dtime選課人數
		/*
		 * courseDao.updateDtimeStuSelectByOids(dtimeOids, Short
		 * .valueOf((short) -1));
		 */
		// 取得先前已新增之Adcd
		String[] dtimeOidArr = dtimeOids.split(",");
		for (String dtimeOid : dtimeOidArr) {
			if (StringUtils.isNotBlank(dtimeOid)) {
				Integer oid = Integer.valueOf(dtimeOid);
				int seldCount = findSeldCountByDtimeOid(oid);
				courseDao.updateDtimeStdSelect(oid, Short
						.valueOf((short) seldCount));
			}
		}

		if (StringUtils.isNotBlank(dtimeOids)) {
			List<Adcd> aList = findExistedAdcd(studentNo, classNo, dtimeOids,
					"A");
			if (!aList.isEmpty()) {
				// 如果先前已新增,則刪除Adcd資料
				for (Adcd adcd : aList) {
					courseDao.deleteAdcd(adcd);
				}
			} else {
				// 否則新增n筆Adcd之Adddraw為"D"資料
				for (String dtimeOid : dtimeOidArr) {
					Adcd adcd = new Adcd();
					adcd.setDtimeOid(Integer.valueOf(dtimeOid.trim()));
					adcd.setStudentNo(studentNo);
					adcd.setStudepartClass(classNo);
					adcd.setAdddraw("D");
					courseDao.saveOrUpdateAdcd(adcd);
				}
			}

			// 學生身分才進行StdAdcd紀錄
			if (isStudent) {
				for (String dtimeOid : dtimeOidArr) {
					StdAdcd stdAdcd = new StdAdcd();
					stdAdcd.setDtimeOid(Integer.valueOf(dtimeOid));
					stdAdcd.setStudentNo(studentNo);
					stdAdcd.setAdddraw("D");
					stdAdcd.setLastModified(new Date());
					courseDao.saveObject(stdAdcd);
				}
			}
			// 刪除Regs資料
			for (String dtimeOid : dtimeOidArr)
				courseDao.delRegsByDtimeAndStdNo(dtimeOid, studentNo);
		}
	}

	// 以dtimeOids清單產生Adcd清單
	@SuppressWarnings("unused")
	private List<Adcd> processAddAdcd(String stdNo, String classNo,
			String dtimeOids) {

		List<Adcd> adcds = new ArrayList<Adcd>();
		String[] dtimeOidArray = dtimeOids.split(",");
		for (String dtimeOid : dtimeOidArray) {
			Adcd adcd = new Adcd();
			adcd.setDtimeOid(Integer.valueOf(dtimeOid));
			adcd.setAdddraw("A");
			adcd.setStudentNo(stdNo);
			adcd.setStudepartClass(classNo);
			adcds.add(adcd);
		}
		return adcds;
	}

	/**
	 * 以dtimeOids清單取得先前已新增且Adddraw為A或D之Adcd
	 * 
	 * @param studentNo 學生學號
	 * @param classNo 班級代碼
	 * @param dtimeOids Dtime Oids
	 * @param type Adddraw Type
	 * @return java.util.List List of Adcd objects
	 */
	@SuppressWarnings("unchecked")
	public List<Adcd> findExistedAdcd(String studentNo, String classNo,
			String dtimeOids, String type) {
		StringBuffer hql = new StringBuffer("FROM Adcd a WHERE ");
		hql.append("a.studentNo = '").append(studentNo).append("' ");
		if (classNo != null)
			hql.append("AND a.studepartClass = '").append(classNo).append("' ");
		hql.append("AND a.adddraw = '").append(type).append("' ");
		hql.append("AND a.dtimeOid IN (").append(dtimeOids).append(")");
		log.info("processUpdateAdcd HQL : " + hql.toString());
		return (List<Adcd>) courseDao.StandardHqlQuery(hql.toString());
	}

	/**
	 * 實作以cscode取得Csno物件之方法
	 * 
	 * @param cscode 科目代碼
	 * @return Csno tw.edu.chit.model.Csno object
	 */
	public Csno findCourseInfoByCscode(String cscode) {
		return courseDao.getCsnoByCscode(cscode);
	}

	/**
	 * 實作依據科目代碼與班級代碼查詢開課基本資料之Service方法
	 * 
	 * @param classNo 班級代碼
	 * @param csCode 科目代碼
	 * @param sterm 學期
	 * @return Dtime seld tw.edu.chit.model.Dtime object
	 * @exception NoClassDefinedException 當查無開課紀錄時
	 */
	public Dtime findDtimeInfoByCsCodeAndClassNo(String classNo, String csCode,
			String sterm) throws NoClassDefinedException {
		try {
			return courseDao.getDtimeByCsCodeAndClassNo(classNo, csCode, sterm);
		} catch (Exception e) {
			throw new NoClassDefinedException("查無此開課紀錄!!!");
		}
	}

	/**
	 * 取得單一科目多教師授課資料
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeTeacherBy(String dtimeOid) {
		String hql=
			// "SELECT d.hours, d.teach, d.fillscore, e.cname " +
				"FROM DtimeTeacher d, Empl e" +
				" WHERE d.dtimeOid="+dtimeOid+" AND d.teachId=e.idno";
		List dtimeTeachers=courseDao.getDtimeForOpenCs(hql);
		Object[] twin;
		DtimeTeacher dtimeTeacher;
		Empl empl;
		List<DtimeTeacher> table = new ArrayList<DtimeTeacher>();
		for (int i=0; i < dtimeTeachers.size(); i++) {
			twin=(Object[])dtimeTeachers.get(i);
			dtimeTeacher=(DtimeTeacher)twin[0];
			empl=(Empl)twin[1];
			dtimeTeacher.setChiName2(empl.getCname());
			table.add(dtimeTeacher);

		}
		return table;
	}

	/**
	 * 實作以學生之Clazz資料物件查詢開課資料之Service方法
	 * 
	 * @param student
	 *            tw.edu.chit.model.Clazz object
	 * @param dtimeOid
	 *            Dtime Oid
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getOpencsByStudentClass(Clazz clazz, Integer dtimeOid) {
		return courseDao.getOpencsByStudnetClass(clazz, dtimeOid);
	}

	/**
	 * 取得選課規則
	 */
	@SuppressWarnings("unchecked")
	public List getOpencs(String dtimeOid) {
		String hql="FROM Opencs WHERE dtimeOid="+dtimeOid;
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 實作以依據老師取得開課主檔資料之Service方法
	 * 
	 * @param member
	 *            tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeByTeacher(Member member) {
		return courseDao.getDtimeByTeacher(member);
	}
	
	/**
	 * 實作以依據老師取得一科目多教師資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> findDtimeTeacherByTeacher(Member member) {
		return courseDao.getDtimeTeacherByTeacher(member);
	}
	
	/**
	 * 實作以依據老師取得開課主檔資料之Service方法
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeByTeacher(Member member, String term) {
		return courseDao.getDtimeByTeacher(member, term);
	}
	
	/**
	 * 實作以依據老師取得歷年開課主檔資料
	 * 
	 * @param member tw.edu.chit.model.Member object
	 * @param term School Term
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> getDtimeHistByTeacher(Member member, String year,
			String term) {
		return courseDao.getDtimeHistByTeacher(member, year, term);
	}

	/**
	 * 實作更新Dtime資料表內開放選修與否Open欄位之Service方法
	 * 
	 * @param dtimeListList of Map objects
	 * @param open 欲更新Dtime資料表欄位open之值
	 */
	@SuppressWarnings("unchecked")
	public void txUpdateOpenByDtimeList(List<Map> dtimeList, Byte open) {
		Dtime dtime = null;
		for (Map data : dtimeList) {
			dtime = (Dtime) courseDao.getObject(Dtime.class, Integer
					.valueOf(data.get("oid").toString()));
			dtime.setOpen(open);
			courseDao.saveDtime(dtime);
		}
	}

	/**
	 * 實作產生某班級所有學生基本選課資料之Service方法
	 * 
	 * @comment 會一併更新Dtime目前選課人數stu_select欄位(非本班之加選人數)
	 * @commend 會一併新增多筆Adcd
	 * @commend 會一併新增多筆Regs
	 * @commend 必修課程加選時毋須加入至Adcd
	 * @param dtime java.util.Map object
	 * @param students List of Student object
	 */
	@SuppressWarnings("unchecked")
	public void txCreateBaseSelectedForStudents(Map dtime,
			List<Student> students) {

		// 先做AddDelCourseData,Seld與Regs資料表刪除動作,再新增所有選課資料
		// 會回傳刪除人數,但無使用到
		// courseDao.delSeldByDtimeAndStudents(dtime, students);
		// courseDao.delAdcdByDtimeAndStudents(dtime, students);
		// courseDao.delRegsByDtimeAndStudents(dtime, students);
		txDeleteBaseSelectedForStudents(dtime, students);
		int count = 0;
		Seld seld = null;
		Regs regs = null;
		for (Student std : students) {
			seld = new Seld();
			seld.setStudentNo(std.getStudentNo());
			seld.setDtimeOid(Integer.valueOf(dtime.get("oid").toString()));
			seld.setOpt(dtime.get("opt").toString());
			seld.setCredit(Float.valueOf(dtime.get("credit").toString()));
			// 產生原班級學生基本選課資料,Adcd無需加入紀錄
			/*
			 * if (!"1".equals(dtime.getOpt())) { Adcd adcd = new Adcd();
			 * adcd.setDtimeOid(dtime.getOid());
			 * adcd.setStudentNo(std.getStudentNo());
			 * adcd.setStudepartClass(std.getDepartClass());
			 * adcd.setAdddraw("A"); courseDao.saveOrUpdateAdcd(adcd); }
			 */
			regs = new Regs();
			regs.setStudentNo(std.getStudentNo());
			regs.setDtimeOid(Integer.valueOf(dtime.get("oid").toString()));
			courseDao.saveOrUpdateSeld(seld);
			courseDao.saveOrUpdateRegs(regs);
			count++;
		}
		// 修改Dtime目前選課人數
		/*
		 * Short stuSelect = dtime.get("stu_select") == null ?
		 * Short.valueOf("0") :
		 * Short.valueOf(dtime.get("stu_select").toString()); short num =
		 * (short) (stuSelect + (short) count);
		 */
		int seldCount = findSeldCountByDtimeOid(Integer.valueOf(dtime
				.get("oid").toString()));
		courseDao.updateDtimeStdSelect(Integer.valueOf(dtime.get("oid")
				.toString()), Short.valueOf((short) seldCount));
	}

	/**
	 * 實作產生一個學生基本選課資料之Service方法 Hi Oscar, this method is analog to your
	 * counterpart -- txCreateBaseSelectedForStudents, but only apply to one
	 * student, in respond to handle 轉學生
	 * 
	 * For each Dtime-not-open-for-select: If the corresponding Seld is not
	 * existing, create one and delete associate Adcd. If the corresponding Regs
	 * is not existing, create one. Also, update select_count of Dtime
	 * 
	 * @param students
	 *            List of Student object
	 * @author James Chiang
	 */
	public void txCreateBaseSelectedForStudent(Student student, String term) {

		// 先做AddDelCourseData,Seld與Regs資料表刪除動作,再新增所有選課資料
		// 會回傳刪除人數,但無使用到
		// courseDao.delSeldByDtimeAndStudents(dtime, students);
		// courseDao.delAdcdByDtimeAndStudents(dtime, students);
		// courseDao.delRegsByDtimeAndStudents(dtime, students);
		// txDeleteBaseSelectedForStudents(dtime, students);
		// int count = 0;
		List<Dtime> dtimes = courseDao.findDtimeByClassTermOpen(student.getDepartClass(), term, false);
		Seld seld = null;
		Regs regs = null;

		for (Dtime dtime : dtimes) {

			if (!("50000".equals(dtime.getCscode()) || "T0001".equals(dtime.getCscode()) || "T0002".equals(dtime.getCscode()))) {

				if (!jdbcDao.isSeldExistingByDtimeStudent(dtime.getOid(), student.getStudentNo())) {
					seld = new Seld();
					seld.setStudentNo(student.getStudentNo());
					seld.setDtimeOid(Integer.valueOf(dtime.getOid().toString()));
					seld.setOpt(dtime.getOpt().toString());
					seld.setCredit(Float.valueOf(dtime.getCredit().toString()));
					courseDao.saveOrUpdateSeld(seld);
					jdbcDao.removeAdcdByDtimeStudent(dtime.getOid(), student.getStudentNo());
				}

				int seldCount = findSeldCountByDtimeOid(Integer.valueOf(dtime
						.getOid().toString()));
				courseDao.updateDtimeStdSelect(Integer.valueOf(dtime.getOid()
						.toString()), Short.valueOf((short) seldCount));

				if (!jdbcDao.isRegsExistingByDtimeStudent(dtime.getOid(), student.getStudentNo())) {
					regs = new Regs();
					regs.setStudentNo(student.getStudentNo());
					regs.setDtimeOid(Integer.valueOf(dtime.getOid().toString()));
					courseDao.saveOrUpdateRegs(regs);
				}
			}
		}
		// 產生原班級學生基本選課資料,Adcd無需加入紀錄
		/*
		 * if (!"1".equals(dtime.getOpt())) { Adcd adcd = new Adcd();
		 * adcd.setDtimeOid(dtime.getOid());
		 * adcd.setStudentNo(std.getStudentNo());
		 * adcd.setStudepartClass(std.getDepartClass()); adcd.setAdddraw("A");
		 * courseDao.saveOrUpdateAdcd(adcd); }
		 */

		// 修改Dtime目前選課人數
		/*
		 * Short stuSelect = dtime.get("stu_select") == null ?
		 * Short.valueOf("0") :
		 * Short.valueOf(dtime.get("stu_select").toString()); short num =
		 * (short) (stuSelect + (short) count);
		 */
	}


	/**
	 * 實作刪除某班級所有學生基本選課資料之Service方法
	 * 
	 * @comment 會一併更新Dtime目前選課人數stu_select欄位(非本班之加選人數)
	 * @commend 會一併刪除多筆Regs
	 * @commend 會一併刪除多筆Seld
	 * @param dtime java.util.Map object
	 * @param students List of Student object(該班級學生)
	 */
	public void txDeleteBaseSelectedForStudents(Map dtime,
			List<Student> students) {

		if (!students.isEmpty()) {
			courseDao.delSeldByDtimeAndStudents(dtime, students);
			// courseDao.delAdcdByDtimeAndStudents(dtime, students);
			courseDao.delRegsByDtimeAndStudents(dtime, students);
		}
		/*
		 * Short stuSelect = dtime.get("stu_select") == null ?
		 * Short.valueOf("0") : Short.valueOf((String) dtime.get("stu_select"));
		 * short count = (short) (stuSelect - (short) num) < 0 ? (short) 0 :
		 * (short) (stuSelect - (short) num);
		 */
		// 修改Dtime目前選課人數
		int seldCount = findSeldCountByDtimeOid(Integer.valueOf(dtime
				.get("oid").toString()));
		courseDao.updateDtimeStdSelect(Integer.valueOf(dtime.get("oid")
				.toString()), Short.valueOf((short) seldCount));
	}

	// 判斷open值回傳正式名稱, 預設為不開放
	private String getOpenName(Byte open) {
		switch(open.intValue()) {
			case 0:
				return "";
			case 1:
				return "開放";
			default:
				return "";
		}
	}
	// 判斷遠距課程
	public String getElearningName(String elearning) {
		if(!elearning.equals("")){
			switch(Integer.parseInt(elearning)) {
			case 0:
				return "";
			case 1:
				return "遠距";
			case 2:
				return "輔助";
			case 3:
				return "媒體";
			default:
				return "";
			}
		}else{
			return "";
		}
	}
	
	// 判斷電腦實習費
	public String getExtrapay(String extrapay) {
		switch(Integer.parseInt(extrapay)) {
			case 0:
				return "";
			case 1:
				return "<img src='images/16-cube-pc.png' border='0' title='電腦實習費'>";
			default:
				return "";
		}
	}
	// 判斷跨選
	private String getOpen2(boolean open){
		if(open){
			return "跨選";
		}
		return "";
	}
	/**
	 * 刪除一科目多教師, 根據Dtime.oid
	 */
	public void RemoveDtimeTeacherBy(String dtimeOid){
		String sql="DELETE FROM Dtime_teacher WHERE Dtime_oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * 儲存一科目多教師
	 */
	public void SaveDtimeTeacher(DtimeTeacher dtimeTeacher){
		courseDao.SaveDtimeTeacher(dtimeTeacher);
	}

	/**
	 * 刪除跨選
	 */
	public void RemoveOpencsBy(String dtimeOid) {
		String sql="DELETE FROM Opencs WHERE dtime_oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * 儲存跨選
	 */
	public void saveOpencsBy(Opencs opencs) {
		courseDao.txsaveOpencsBy(opencs);
	}
	/**
	 * 刪除考試
	 */
	public void RemoveDtimeExamBy(String dtimeOid) {
		String sql="DELETE FROM Dtime_exam WHERE dtime_oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * 刪除前的確認
	 */
	@SuppressWarnings("unchecked")
	public List getSeldBy(String dtimeOid[]) {
		StringBuffer strBuf=new StringBuffer();

		for(int i=0; i<dtimeOid.length; i++){
			strBuf.append(dtimeOid[i]+", ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		String sql="SELECT cl.Grade, cl.DeptNo, s.Oid as seldOid, d.Oid as dtimeOid, d.depart_class, d.credit, d.thour, d.opt, d.Sterm, c2.ClassName as ClassName2, st.student_no, st.student_name, " +
					"cl.ClassNo, cl.ClassName, cl.Grade, c.cscode, c.chi_name FROM Seld s, stmd st, Csno c, Dtime d, Class cl, Class c2 " +
					"WHERE s.Dtime_oid=d.Oid AND "+
					"s.student_no=st.student_no AND d.cscode=c.cscode AND cl.ClassNo=st.depart_class AND c2.ClassNo=d.depart_class AND "+
					"d.Oid IN("+strBuf.toString() +") ORDER BY st.student_no";

		return jdbcDao.StandardSqlQuery(sql);
	}

	public void RevoveSeldBy(String[] dtimeOid) {
		StringBuffer strBuf=new StringBuffer();
		for(int i=0; i<dtimeOid.length; i++){
			strBuf.append(dtimeOid[i]+", ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		//String sql="DELETE FROM Seld WHERE Dtime_oid IN("+strBuf.toString()+")";
		jdbcDao.StandardSqlRemove("");
	}

	public void removeDtimeBy(String dtimeOid) {
		String sql="DELETE FROM Dtime WHERE Oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * 實作新增教師任教中英文課程簡介之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param engName Course English Name
	 * @param chiIntro 中文課程簡介
	 * @param engIntro 英文課程簡介
	 */
	public void txAddCourseIntro(Integer dtimeOid, Integer year, Integer term,
			String departClass, String cscode, String engName, String chiIntro,
			String engIntro) {
		CourseIntroduction ci = new CourseIntroduction();
		ci.setDtimeOid(dtimeOid);
		ci.setSchoolYear(year);
		ci.setSchoolTerm(term);
		ci.setDepartClass(departClass);
		ci.setCscode(cscode);
		ci.setEngName(engName);
		ci.setChiIntro(chiIntro);
		ci.setEngIntro(engIntro);
		ci.setLastModified(new Date());
		courseDao.saveCourseIntro(ci);
	}

	/**
	 * 實作新增教師任教建議書單之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param bookSuggest 建議書單
	 */
	public void txAddCourseBookSuggest(Integer dtimeOid, Integer year,
			Integer term, String departClass, String cscode, String bookSuggest) {
		CourseIntroduction ci = new CourseIntroduction();
		ci.setDtimeOid(dtimeOid);
		ci.setSchoolYear(year);
		ci.setSchoolTerm(term);
		ci.setDepartClass(departClass);
		ci.setCscode(cscode);
		ci.setBookSuggest(bookSuggest);
		ci.setSuggestLastModified(new Date());
		courseDao.saveCourseIntro(ci);
	}

	@SuppressWarnings("unchecked")
	public List getTeachersBy(String[] dtimeOid) {
		StringBuffer strBuf=new StringBuffer();
		for(int i=0; i<dtimeOid.length; i++){
			strBuf.append(dtimeOid[i]+", ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		String sql="SELECT e.idno, e.cname, c.cscode, c.chi_name, d.depart_class, d.stu_select "+
					"FROM empl e, Csno c, Dtime d WHERE "+
					"e.idno=d.techid AND "+
					"c.cscode=d.cscode AND "+
					"d.Oid IN ("+strBuf.toString()+")";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 實作更新CourseIntroduction資料表
	 * 
	 * @param ci tw.edu.chit.model.CourseIntroduction object
	 */
	public void txUpdateCourseIntro(CourseIntroduction ci) {
		courseDao.saveCourseIntro(ci);
	}

	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Year
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public List<CourseIntroduction> getCourseIntroByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		return courseDao.getCourseIntroByDtimeOid(dtimeOid, year, term);
	}
	
	/**
	 * 取得CourseIntroduction歷年清單資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public List<CourseIntroduction> getCourseIntroHistBy(Integer year,
			Integer term, String departClass, String cscode) {
		return courseDao.getCourseIntroHistBy(year, term, departClass,
				cscode);
	}

	/**
	 * 實作以Oid取得CourseIntroduction清單資料
	 * 
	 * @param oid CourseIntroduction Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroByOid(Integer oid) {
		return courseDao.getCourseIntroByOid(oid);
	}

	/**
	 * 實作新增/更新CourseIntroduction清單資料之Service方法
	 * 
	 * @param regs tw.edu.chit.model.Regs object
	 */
	public void txSaveRegs(Regs regs) {
		courseDao.saveOrUpdateRegs(regs);
	}

	/**
	 * 實做刪除課程時同時除加退選記錄
	 */
	public void removeAdcdBy(String dtimeOid) {
		String sql="DELETE FROM AddDelCourseData WHERE dtime_oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * 實做刪除課程時同時刪除成績
	 */
	public void removeRegsBy(String dtimeOid) {
		String sql="DELETE FROM AddDelCourseData WHERE dtime_oid="+dtimeOid;
		jdbcDao.StandardSqlRemove(sql);

	}
	/**
	 * 實做由姓名或id查詢員工
	 */
	@SuppressWarnings("unchecked")
	public List getEmplBy(String idno, String cname) {
		/*
		 * String sql="SELECT * FROM empl WHERE idno LIKE '%"+idno+"%' AND cname
		 * LIKE '%"+cname+"%'"; String sql2="SELECT name FROM code5 WHERE Oid=";
		 * String hql="FROM Empl WHERE idno LIKE '%"+idno+"%' AND cname LIKE
		 * '%"+cname+"%'"; String hql1="FROM CodeEmpl WHERE idno="; List
		 * empls=courseDao.StandardHqlQuery(hql); List code5s=null; List
		 * Allinfo=new ArrayList(); CodeEmpl code5; for(int i=0; i<empls.size();
		 * i++){ aEmpl=(Empl) empls.get(i);
		 * code5s=courseDao.StandardHqlQuery(hql1+"'"+aEmpl.getUnit()+"'");
		 * 
		 * if(code5s.size()>0){ code5=(CodeEmpl) code5s.get(0);
		 * aEmpl.setUnit2(code5.getName().toString()); }
		 * 
		 * 
		 * code5s=courseDao.StandardHqlQuery(hql1+"'"+aEmpl.getPcode()+"'");
		 * 
		 * if(code5s.size()>0){ code5=(CodeEmpl) code5s.get(0);
		 * aEmpl.setPcode2(code5.getName().toString()); }
		 * 
		 * Allinfo.add(aEmpl);
		 *  } return Allinfo;
		 */
		StringBuffer hql = new StringBuffer("from Empl e ");

		if (!"".equals(cname)) {
			hql.append("and e.cname like '%" + cname + "%' ");
		}
		if (!"".equals(idno)) {
			hql.append("and e.idno like '%" + idno + "%' ");
		}


		int start = hql.indexOf(" and ");
		if (start >= 0) {
			hql.replace(start, start+5, " where ");
		}

    	return courseDao.StandardHqlQuery(hql.toString());
	}
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseIntroduction清單資料
	 * 
	 * @param dtimeOid
	 *            Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntrosByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		return courseDao.getCourseIntrosByDtimeOid(dtimeOid, year, term);
	}
	
	/**
	 * 實作以CourseIntroduction取得CourseIntroduction資料
	 * 
	 * @param dtimeOid Dtime Oid
	 * @return tw.edu.chit.model.CourseIntroduction object
	 */
	public CourseIntroduction getCourseIntroBy(CourseIntroduction ci) {
		return courseDao.getCourseIntroBy(ci);
	}

	/**
	 * 實作以Dtime Oid及學年與學期取得CourseSyllabus清單資料之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByDtimeOid(Integer dtimeOid,
			Integer year, Integer term) {
		return courseDao.getCourseSyllabusByDtimeOid(dtimeOid, year, term);
	}
	
	/**
	 * 實作以Dtime Oid及學年與學期取得CourseSyllabus清單資料
	 * 
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param year School Year
	 * @param term School Term
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusBy(String departClass,
			String cscode, Integer year, Integer term) {
		return courseDao.getCourseSyllabusByDtimeOid(departClass, cscode, year,
				term);
	}
	
	/**
	 * 實作以Oid取得CourseSyllabus清單資料
	 * 
	 * @param oid
	 *            CourseSyllabus Oid
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusByOid(Integer oid) {
		return courseDao.getCourseSyllabusByOid(oid);
	}
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus getCourseSyllabusBy(CourseSyllabus courseSyllabus) {
		return courseDao.getCourseSyllabusBy(courseSyllabus);
	}
	
	/**
	 * 實作以Oid取得CourseSyllabus清單資料
	 * 
	 * @param oid
	 *            CourseSyllabus Oid
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusByOid(Integer oid) {
		return courseDao.getCourseSyllabusByOid(oid);
	}
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus
	 *            CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusBy(CourseSyllabus courseSyllabus) {
		return courseDao.getCourseSyllabusBy(courseSyllabus);
	}
	
	/**
	 * 以CourseSyllabus取得資料
	 * 
	 * @param courseSyllabus CourseSyllabus Object
	 * @return tw.edu.chit.model.CourseSyllabus object
	 */
	public CourseSyllabus findCourseSyllabusBy1(CourseSyllabus courseSyllabus) {
		return courseDao.getCourseSyllabusBy1(courseSyllabus);
	}

	/**
	 * 實作新增教師任教課程大綱之Service方法
	 * 
	 * @param dtimeOid Dtime Oid
	 * @param year School Year
	 * @param term Depart Class
	 * @param cscode Cscode
	 * @param officeHour 輔導時間
	 * @param requisites 先修科目或先備能力
	 * @param objectives 課程目標
	 * @param topics 章節主題
	 * @param contents 內容綱要
	 * @param hours 教學時數
	 * @param weekNos 週次
	 * @param remarks 備註
	 */
	public void txAddCourseSyllabus(Integer dtimeOid, Integer year,
			Integer term, String departClass, String cscode, String officeHour,
			String requisites, String objectives, String syllabus,
			String[] topics, String[] contents, String[] hours,
			String[] weekNos,
			String[] remarks) {

		CourseSyllabus cs = new CourseSyllabus();
		cs.setDtimeOid(dtimeOid);
		cs.setSchoolYear(year);
		cs.setSchoolTerm(term);
		cs.setDepartClass(departClass);
		cs.setCscode(cscode);
		cs.setOfficeHours(officeHour);
		cs.setPrerequisites(requisites);
		cs.setObjectives(objectives);
		cs.setSyllabus(syllabus);
		cs.setLastModified(new Date());
		// 以下處理1對多關聯
		Syllabus oSyllabus = null;
		List<Syllabus> syllabusList = new ArrayList<Syllabus>();
		for (int i = 0; i < topics.length; i++) {
			if (StringUtils.isNotBlank(topics[i])
					|| StringUtils.isNotBlank(contents[i])
					|| StringUtils.isNotBlank(hours[i])
					|| StringUtils.isNotBlank(weekNos[i])
					|| StringUtils.isNotBlank(remarks[i])) {
				oSyllabus = new Syllabus();
				oSyllabus.setTopic(topics[i]);
				oSyllabus.setContent(contents[i]);
				oSyllabus.setHours(hours[i]);
				oSyllabus.setWeek(weekNos[i]);
				oSyllabus.setRemarks(remarks[i]);
				oSyllabus.setPos(Integer.valueOf(i));
				oSyllabus.setCourseSyllabus(cs);
				syllabusList.add(oSyllabus);
			}
		}
		cs.setSyllabuses(syllabusList);
		courseDao.saveCourseSyllabus(cs);
	}

	/**
	 * 實作更新CourseSyllabus資料表
	 * 
	 * @param oid CourseSyllabus Oid
	 * @param year School Year
	 * @param term School Term
	 * @param departClass Depart Class
	 * @param cscode Cscode
	 * @param officeHour 輔導時間
	 * @param requisites 先修科目或先備能力
	 * @param objectives 課程目標
	 * @param topics 章節主題
	 * @param contents 內容綱要
	 * @param hours 教學時數
	 * @param weekNos 週次
	 * @param remarks 備註
	 */
	public void txUpdateCourseSyllabus(Integer oid, Integer year, Integer term,
			String departClass, String cscode, String officeHour,
			String requisites, String objectives, String syllabus,
			String[] topics, String[] contents, String[] hours,
			String[] weekNos, String[] remarks) {		
		CourseSyllabus cs=(CourseSyllabus) hqlGetBy("FROM CourseSyllabus WHERE Oid="+oid).get(0);
		cs.setLastModified(new Date());
		cs.setSyllabus(syllabus);
		cs.setObjectives(objectives);
		cs.setOfficeHours(officeHour);
		updateObject(cs);		
		executeSql("DELETE FROM Syllabu WHERE parentOid="+cs.getOid());
		Syllabu sy;
		for(int i=0; i< contents.length; i++){			
			if(!topics[i].trim().equals("")&&!contents[i].equals("")){
				sy=new Syllabu();
				sy.setContent(contents[i]);
				sy.setHours(hours[i]);
				sy.setParentOid(cs.getOid());
				sy.setPos(i);
				sy.setRemarks(remarks[i]);
				sy.setTopic(topics[i]);
				sy.setWeek(weekNos[i]);
				updateObject(sy);
			}			
		}
	}

	/**
	 * 以發送者查詢發送訊息
	 * @param String sender 發送者ID
	 */
	@SuppressWarnings("unchecked")
	public List getMessageBy(String sender) {
		String hql="FROM Message WHERE Sender='"+sender+"'";
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 儲存訊息
	 * @param Message message 訊息物件
	 */
	public void saveMessageBy(Message message) {
		courseDao.saveObject(message);
	}

	/**
	 * 刪除訊息
	 */
	public void removeMessageBy(String oid) {
		String sql="DELETE FROM Message WHERE Oid="+oid;
		jdbcDao.StandardSqlRemove(sql);
	}

	/**
	 * search for openCourse 'All'
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode, String techid, String term, String choseType) {


		StringBuffer strBuf=new StringBuffer("FROM Dtime d, Csno c, Clazz s Empl e WHERE d.cscode LIKE '%"+cscode+"%' AND d.techid LIKE '"
				+techid+"%' AND e.idno=d.techid AND d.departClass IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.sterm LIKE '"+term+"'AND opt LIKE '"+choseType+"'");
		strBuf.append(" ORDER BY d.departClass");

		List dtimeList=courseDao.getDtimeForOpenCs(strBuf.toString());
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		Empl	 aEmpl;
		List<Dtime> table = new ArrayList<Dtime>();
		// Map aRow;
		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
			aEmpl  = (Empl)aTripple[3];
			/*
			 * aRow = new HashMap(); aRow.put("oid", aDtime.getOid());
			 * aRow.put("cscode", aDtime.getCscode()); aRow.put("csName",
			 * aCsno.getChiName()); aRow.put("deptName", aClazz.getClassName());
			 * 
			 * 
			 * if(aDtime.getOpt().equals("1")){ aDtime.setOpt2("必修"); }
			 * if(aDtime.getOpt().equals("2")){ aDtime.setOpt2("選修"); }
			 * if(aDtime.getOpt().equals("3")){ aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setTechName(aEmpl.getCname());
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if(aDtime.getDepartClass().equals(aClazz.getClassNo())){
				aDtime.setChiName2(aClazz.getClassName());
			}

			if(aDtime.getCscode().equals(aCsno.getCscode())){
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}

		return table;
	}

	/**
	 * get dtime list for OpenCourse search(1) bar.
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCs(Clazz[] classes, String campusInCharge2, String schoolInCharge2,
			String deptInCharge2, String departClass, String cscode,
			String techid, String term,String choseType) {

		StringBuffer strBuf=new StringBuffer("FROM Dtime d, Csno c, Clazz s WHERE d.cscode LIKE '%"+cscode+"%' AND d.techid LIKE '"
				+techid+"%' AND d.departClass IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");
		}
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.departClass LIKE '"+campusInCharge2+schoolInCharge2+deptInCharge2+"%' AND d.departClass LIKE '"+departClass+"%' AND sterm LIKE '"+term+"' AND opt LIKE '"+choseType+"'");
		strBuf.append(" ORDER BY d.departClass");
		String sql=strBuf.toString();

		List dtimeList=courseDao.getDtimeForOpenCs(sql);
		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		List     table = new ArrayList();
		for (int i=0; i < dtimeList.size(); i++) {
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];
			/*
			 * if(aDtime.getOpt().equals("1")){ aDtime.setOpt2("必修"); }
			 * if(aDtime.getOpt().equals("2")){ aDtime.setOpt2("選修"); }
			 * if(aDtime.getOpt().equals("3")){ aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if(aDtime.getDepartClass().equals(aClazz.getClassNo())){
				aDtime.setChiName2(aClazz.getClassName());
			}

			if(aDtime.getCscode().equals(aCsno.getCscode())){
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			// aRow.put("opt2", aDtime.getOpt2());
			table.add(aDtime);
		}

		return table;

	}

	/**
	 * Searching for OpenCourse 'All'
	 * 
	 * @param classes
	 *            班級代碼
	 * @param cscode
	 *            科目代碼
	 * @param techid
	 *            老師代碼
	 * @param term
	 *            學期
	 * @param choseType
	 * @param open
	 *            開放選修與否
	 * @return java.util.List List of Object array
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeForOpenCsAll(Clazz[] classes, String cscode,
			String techid, String term, String choseType, Byte open) {

		StringBuffer buffer = new StringBuffer(
				"FROM Dtime d, Csno c, Clazz s, Empl e WHERE d.cscode LIKE '%" + cscode
						+ "%' AND d.techid LIKE '" + techid
						+ "%' AND e.idno=d.techid AND d.departClass IN (");

		for (int i = 0; i < classes.length; i++) {
			buffer.append("'" + classes[i].getClassNo() + "', ");
		}
		buffer.delete(buffer.length() - 2, buffer.length() - 1);
		buffer.append(") AND c.cscode=d.cscode AND s.classNo=d.departClass AND d.sterm LIKE '"
						+ term + "'AND opt LIKE '" + choseType + "' ");
		if(open != null)
			buffer.append("AND d.open = ").append(open);
		buffer.append(" ORDER BY d.departClass");

		List dtimeList = courseDao.getDtimeForOpenCs(buffer.toString());
		Object[] aTripple;
		Dtime aDtime;
		Csno aCsno;
		Clazz aClazz;
		Empl  aEmpl;
		List<Dtime> table = new ArrayList<Dtime>();
		for (int i = 0; i < dtimeList.size(); i++) {
			aTripple = (Object[]) dtimeList.get(i);
			aDtime = (Dtime) aTripple[0];
			aCsno = (Csno) aTripple[1];
			aClazz = (Clazz) aTripple[2];
			aEmpl  = (Empl) aTripple[3];
			/*
			 * if (aDtime.getOpt().equals("1")) { aDtime.setOpt2("必修"); } if
			 * (aDtime.getOpt().equals("2")) { aDtime.setOpt2("選修"); } if
			 * (aDtime.getOpt().equals("3")) { aDtime.setOpt2("通識"); }
			 */
			aDtime.setOpt2(Global.CourseOpt.getProperty(aDtime.getOpt()));
			aDtime.setOpenName(getOpenName(aDtime.getOpen()));
			aDtime.setTechName(aEmpl.getCname());
			aDtime.setElearningName(getElearningName(aDtime.getElearning()));
			if (aDtime.getDepartClass().equals(aClazz.getClassNo())) {
				aDtime.setChiName2(aClazz.getClassName());
			}

			if (aDtime.getCscode().equals(aCsno.getCscode())) {
				aDtime.setChiName2(aCsno.getChiName());
			}

			aDtime.setDepartClass2(aClazz.getClassName());
			table.add(aDtime);
		}

		return table;
	}
	/**
	 * 實作取得使用者登入資訊
	 */
	@SuppressWarnings("unchecked")
	public List getWwpassBy(String username) {
		String hql="FROM WwPass WHERE username='"+username+"'";

		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 實作變更密碼
	 */
	public void updateWwpass(WwPass wwpass) {
		courseDao.saveObject(wwpass);
		courseDao.saveObject(wwpass);

	}
	/**
	 * 實作取得是否開放選修
	 */
	public boolean getOpencSize(String dtimeOId) {
		String hql="FROM Opencs WHERE dtimeOid="+dtimeOId;

		return courseDao.StandardHqlQuery(hql).size()>0;
	}

	/**
	 * 實作取得教師上課時間資料之Service方法
	 * 
	 * @param member
	 *            tw.edu.chit.model.Member object
	 * @return java.util.List List of Object array
	 */
	public List<Object[]> findTeachInfoByMember(Member member) {
		return courseDao.findTeachInfoByMember(member);
	}

	/**
	 * 實作以Dtime Oid更新選課人數上限之Service方法
	 * 
	 * @commend 以悲觀Locking鎖定Dtime物件
	 * @param dtimeOid Dtime Oid
	 * @param selectLimit 選課人數上限
	 */
	public void txUpdateDtimeSelLimit(String dtimeOid, Short selectLimit) {
		courseDao.updateDtimeSelLimit(dtimeOid, selectLimit);
	}

	/**
	 * 實做以查詢介面取得Dtime 加入使用者權限
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeBy(Clazz[] classes, String cscode, 
			String techid, String term,String choseType, String open, String elearning, String classLess, String chi_name, String cname){
		
		
		
		StringBuffer strBuf=new StringBuffer("SELECT ce.idno2, d.Select_Limit, d.techid, d.Oid, cs.ClassName, d.depart_class, c.chi_name, c.cscode, c.eng_name, e.cname, d.opt, d.credit, d.thour," +
				" d.stu_select, d.open, d.elearning, d.extrapay, d.crozz " +
				"FROM (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno) LEFT OUTER JOIN CodeEmpl ce ON ce.idno=e.unit AND ce.category='UnitTeach', Class cs, Csno c" +
				" WHERE d.depart_class IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");

		}
			strBuf.delete(strBuf.length()-2, strBuf.length()-1);
			strBuf.append(") AND c.cscode=d.cscode AND cs.classNo=d.depart_class ");

			if(!techid.trim().equals("")){// 若有指明教師
				strBuf.append(" AND d.techid='"+techid+"'");
			}
			if(!classLess.equals("")){// 若班級有指明手key關鍵字
				strBuf.append(" AND d.depart_class LIKE'"+classLess+"%'");
			}
			if(!cscode.trim().equals("")){// 若條件有指明科目關鍵字
				strBuf.append(" AND d.cscode = '"+cscode+"'");
			}
			if(!term.trim().equals("%")){// 若條件有指明學期
				strBuf.append(" AND d.sterm = '"+term+"'");
			}
			if(!elearning.trim().equals("%")){// 若條件有指定遠距
				strBuf.append(" AND d.elearning = '"+elearning+"'");
			}
			if(!open.trim().equals("%")){// 有指定開放規則
				strBuf.append(" AND d.open = '"+open+"'");
			}
			if(!choseType.trim().equals("%")){// 有指定選別
				strBuf.append(" AND opt = '"+choseType+"'");
			}			
			try{
				if(!chi_name.trim().equals("")&&cscode.equals("")){// 有指定課程中文名稱
					strBuf.append(" AND c.chi_name LIKE '"+chi_name+"%'");
				}
				if(!cname.trim().equals("")){// 有指定教師
					strBuf.append(" AND e.cname LIKE '"+cname+"%'");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		strBuf.append(" ORDER BY d.depart_class, c.cscode");
		
		List table = new ArrayList();
		Map map;		
		Object[] dtimeList;
		try{
			dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString()).toArray();
		}catch(Exception e){
			dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString().replace("d.depart_class IN() AND", "d.depart_class IN('') AND")).toArray();
			//dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString().replace("d.depart_class IN() AND", "")).toArray();
		}

		for(int i=0; i<dtimeList.length; i++){

			map = new HashMap();
			map.put("oid",((Map)dtimeList[i]).get("Oid"));
			map.put("departClass2", ((Map)dtimeList[i]).get("ClassName"));
			map.put("departClass", ((Map)dtimeList[i]).get("depart_class"));
			
			if(((Map)dtimeList[i]).get("chi_name").toString().length()>=8){
				map.put("chiName2", ((Map)dtimeList[i]).get("chi_name").toString().subSequence(0, 8)+"..");
			}else{
				map.put("chiName2", ((Map)dtimeList[i]).get("chi_name"));
			}
			map.put("chiName3", ((Map)dtimeList[i]).get("chi_name"));
			
			map.put("cscode", ((Map)dtimeList[i]).get("cscode"));
			map.put("techName", ((Map)dtimeList[i]).get("cname"));
			map.put("credit", ((Map)dtimeList[i]).get("credit"));
			map.put("thour", ((Map)dtimeList[i]).get("thour"));
			map.put("eng_name", ((Map)dtimeList[i]).get("eng_name"));
			map.put("idno2", ((Map)dtimeList[i]).get("idno2"));
			map.put("Select_Limit", ((Map)dtimeList[i]).get("Select_Limit"));
			map.put("techid", ((Map)dtimeList[i]).get("techid"));
			map.put("stuSelect", countSelect(((Map)dtimeList[i]).get("Oid").toString()));
			map.put("openName", getOpen((Boolean)((Map)dtimeList[i]).get("open")));
			map.put("opt2", Global.CourseOpt.getProperty(((Map)dtimeList[i]).get("opt").toString()));
			map.put("elearningName", getElearningName(((Map)dtimeList[i]).get("elearning").toString()));			
			try{
				map.put("open2", getCross(((Map)dtimeList[i]).get("Oid").toString()));
			}catch(Exception e){
				
			}
			
			map.put("extrapay2", getExtrapay(((Map)dtimeList[i]).get("extrapay").toString()));
			map.put("icon", 
					"<a href='/CIS/StudentSel?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='選課學生'></a>" +
					"<a href='/CIS/Print/teacher/SylDoc.do?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='綱要下載'></a>" +
					"<a href='/CIS/Print/teacher/IntorDoc.do?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='簡介下載'></a>");
			table.add(map);
		}
		return table;
	}
	/**
	 * 開放選修轉換
	 */
	public String getOpen(boolean open){
		if(open){
			return "開放";
		}else{
			return "";
		}
	}

	/**
	 * 取得跨選規則
	 */
	public String getCross(String DtimeOid){
		String sql="SELECT Oid FROM Opencs WHERE Dtime_oid="+DtimeOid;
		if(jdbcDao.StandardSqlQuery(sql).size()>0){
			return "跨選";
		}else{
			return "";
		}

	}

	/**
	 * 取得選課人數
	 */
	public Integer countSelect(String DtimeOid) {
		String sql = "SELECT COUNT(*) FROM Seld WHERE Dtime_oid='" + DtimeOid
				+ "'";
		return jdbcDao.StandardSqlQueryForInt(sql);
	}

	/**
	 * 實做以勾選介面取得Dtime
	 */
	@SuppressWarnings("unchecked")
	public List getDtimeBy(String oid){
		// String hql="FROM Dtime d, Csno c WHERE d.Oid="+oid+" AND
		// d.cscode=c.cscode";
		String hql="FROM Dtime d, Csno c, Clazz s WHERE d.oid="+oid+" AND c.cscode=d.cscode AND d.departClass=s.classNo";

		Object[] aTripple;
		Dtime    aDtime;
		Csno	 aCsno;
		Clazz    aClazz;
		List     table = new ArrayList();
		List dtimeList=courseDao.StandardHqlQuery(hql);

		for (int i=0; i < dtimeList.size(); i++){
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (Dtime)aTripple[0];
			aCsno  = (Csno)aTripple[1];
			aClazz = (Clazz)aTripple[2];

			aDtime.setDepartClass2(aClazz.getClassName());
			aDtime.setChiName2(aCsno.getChiName());

			table.add(aDtime);
		}

		return table;
	}

	/**
	 * 實作以班級代碼與學期查詢班級課表
	 * 
	 * @param classNo 班級代碼
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findCourseByClassNoAndTerm(String classNo, String term) {
		String sql = "SELECT DISTINCT c.chi_name, e.cname, d.cscode, dc.place, n.name2, "
				+ "d.Oid, d.elearning "
				+ "FROM Dtime d "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN empl e ON d.techid = e.idno "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid "
				+ "AND d.depart_class = ? "
				+ "AND d.Sterm = ? "
				+ "AND dc.week = ? "
				+ "AND CAST(dc.begin AS UNSIGNED) <= ? "
				+ "AND CAST(dc.end AS UNSIGNED) >= ?";
		String sql1 = "SELECT e.cname FROM empl e, Dtime_teacher dt "
				+ "WHERE e.idno = dt.teach_id AND dt.Dtime_oid = ? "
				+ "AND dt.teach = 1";
		
		List<Map> ret = new ArrayList<Map>();
		List<Map> list = null;
		List<Map> coTeachers = null;
		Map<String, String> content = null;
		Object[] args = null;
		StringBuffer buf = null;
		String cname = null;
		for (int weekday = 1; weekday <= 7; weekday++) { // 星期
			for (int sched = 1; sched <= 15; sched++) { // 節次
				args = new Object[] { classNo, term, weekday, sched, sched };
				list = jdbcDao.StandardSqlQuery(sql, args);
				if (!list.isEmpty()) {
					// 找到課表即查詢多教師資料
					content = list.get(0);
					content.put("elearning",
							content.get("elearning") == null ? "" : Toolket
									.getElearning(content.get("elearning")
											.charAt(0)));
					log.info(content.get("cname"));
					coTeachers = jdbcDao.StandardSqlQuery(sql1,
							new Object[] { content.get("Oid") });
					if (!coTeachers.isEmpty()) {
						cname = content.get("cname") == null ? "" : content
								.get("cname");
						buf = new StringBuffer(cname).append(" ");
						for (Map co : coTeachers) {
							buf.append(co.get("cname")).append(" ");
						}
						content.put("cname", buf.toString().trim());
					}
					ret.add(content);
				} else
					// 查無則給一個空的
					ret.add(new HashMap());
			}
		}
		return ret;
	}

	/**
	 * 實作以教師ID與學期查詢班級課表
	 * 
	 * @param idno 教師ID
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findCourseByTeacherTermWeekdaySched(String idno, String term) {
		String sql = "SELECT DISTINCT c.chi_name, cl.ClassNo, cl.ClassName, d.cscode, "
				+ "dc.place, n.name2 " 
				+ "FROM Dtime d "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid AND d.techid = ? "
				+ "AND d.Sterm = ? AND dc.week = ? "
				+ "AND CAST(dc.begin AS UNSIGNED) <= ? "
				+ "AND CAST(dc.end AS UNSIGNED) >= ?";
		String sql1 = "SELECT DISTINCT c.chi_name, cl.ClassName, cl.ClassNo, d.cscode, "
				+ "dc.place, n.name2 "
				+ "FROM Dtime_teacher dt, Dtime d "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid AND dt.Dtime_oid = d.Oid "
				+ "AND dt.teach_id = ? AND d.Sterm = ? "
				+ "AND dc.week = ? AND CAST(dc.begin AS UNSIGNED) <= ? "
				+ "AND CAST(dc.end AS UNSIGNED) >= ? AND dt.teach = 1";
		
		List<Map> ret = new ArrayList<Map>();
		List<Map> list = null;
		List<Map> coTeachers = null;
		Map<String, String> content = null;
		Object[] args = null;

		for (int weekday = 1; weekday <= 7; weekday++) { // 星期
			for (int sched = 1; sched <= 15; sched++) { // 節次
				args = new Object[] { idno, term, weekday, sched, sched };
				list = jdbcDao.StandardSqlQuery(sql, args);
				if (!list.isEmpty()) {
					content = list.get(0);
					log.info(content.get("chi_name"));
					ret.add(content);
				} else
					// 查無則給一個空的
					ret.add(new HashMap());

				coTeachers = jdbcDao.StandardSqlQuery(sql1, new Object[] {
						idno, term, weekday, sched, sched });
				if (!coTeachers.isEmpty()) {
					// 不多減一個會位置錯亂
					ret.remove(ret.size() - 1);
					content = coTeachers.get(0);
					log.info(content.get("chi_name"));
					ret.add(content);
				}
			}
		}
		return ret;
	}

	/**
	 * 實作以Class No查詢部制代碼之Service方法
	 * 
	 * @param classNo 班級代碼
	 * @return 部制代碼,D為日間,N為夜間,H為學院
	 */
	public String findSchoolTypeByClassNo(String classNo) {
		return courseDao.getSchoolTypeByClassNo(classNo);
	}

	/**
	 * 實作以Dtime Oid查詢選課學生清單
	 * 
	 * @param dtimeOid
	 *            Dtime Oid
	 * @return java.util.List List of Student objects
	 */
	public List<Student> findSeldStudentByDtimeOid(Integer dtimeOid) {
		return courseDao.getSeldStudentByDtimeOid(dtimeOid);
	}

	private void processSeldSaveOrUpdate(Seld seld, Student student) {

		courseDao.saveOrUpdateSeld(seld);
		// 取得先前已新增之Adcd
		List<Adcd> adcds = findExistedAdcd(student.getStudentNo(), seld
				.getDepartClass(), seld.getDtimeOid().toString(), "D");
		if (!adcds.isEmpty()) {
			// 如果先前已新增,則刪除Adcd資料
			for (Adcd adcd : adcds)
				courseDao.deleteAdcd(adcd);
		} else {
			// 否則新增一筆Adcd之Addraw為"A"資料
			Adcd adcd = new Adcd();
			adcd.setDtimeOid(seld.getDtimeOid());
			adcd.setStudentNo(student.getStudentNo().toUpperCase());
			adcd.setStudepartClass(student.getDepartClass());
			adcd.setAdddraw("A");
			courseDao.saveOrUpdateAdcd(adcd);
		}

		Regs regs = new Regs();
		regs.setStudentNo(student.getStudentNo().toUpperCase());
		regs.setDtimeOid(seld.getDtimeOid());
		courseDao.saveOrUpdateRegs(regs);
		int seldCount = findSeldCountByDtimeOid(seld.getDtimeOid());
		courseDao.updateDtimeStdSelect(seld.getDtimeOid(), Short
				.valueOf((short) seldCount));
		// 更新Dtime資料表選課人數+1
		/*
		 * courseDao.updateDtimeStuSelectByOids(seld.getDtimeOid().toString(),
		 * Short.valueOf((short) 1));
		 */
	}

	// 第二階段要避免多人同時加選同一科目時,選課人數超過上限問題
	private synchronized void processSeldSaveOrUpdateForOneStudent(Seld seld,
			Student student, String term, boolean isStudent, int phase)
			throws SeldException {

		Dtime dtime = (Dtime) courseDao.getObject(Dtime.class, seld
				.getDtimeOid());
		int seldCount = findSeldCountByDtimeOid(dtime.getOid());
		if (1 != phase) { // 第一階段不管上限問題
			if ((seldCount + (short) 1) > dtime.getSelectLimit()) {
				Csno csno = findCourseInfoByCscode(dtime.getCscode());
				throw new SeldException("科目[" + csno.getChiName() + "]選課人數超過上限");
			}
		}
		courseDao.saveOrUpdateSeld(seld);
		String deartClass = student.getDepartClass();
		// 第三階段不需年級再升級
		if (!"3".equals(String.valueOf(phase)) && "1".equals(term))
			deartClass = Toolket.processClassNoUp(deartClass);

		// 加選課程之班級與學生相同且為必修則不加入Adcd
		if (!deartClass.equals(dtime.getDepartClass())
				|| !"1".equals(seld.getOpt())) {
			// 取得先前已新增之Adcd
			List<Adcd> adcds = findExistedAdcd(student.getStudentNo(), seld
					.getDepartClass(), seld.getDtimeOid().toString(), "D");
			if (!adcds.isEmpty()) {
				// 如果先前已新增,則刪除Adcd資料
				for (Adcd adcd : adcds)
					courseDao.deleteAdcd(adcd);
			} else {
				// 否則新增一筆Adcd之Addraw為"A"資料
				Adcd adcd = new Adcd();
				adcd.setDtimeOid(seld.getDtimeOid());
				adcd.setStudentNo(student.getStudentNo());
				adcd.setStudepartClass(deartClass);
				adcd.setAdddraw("A");
				courseDao.saveOrUpdateAdcd(adcd);
			}
		}

		if (isStudent) {
			StdAdcd stdAdcd = new StdAdcd();
			stdAdcd.setDtimeOid(seld.getDtimeOid());
			stdAdcd.setStudentNo(student.getStudentNo());
			stdAdcd.setAdddraw("A");
			stdAdcd.setLastModified(new Date());
			courseDao.saveObject(stdAdcd);
		}

		Regs regs = new Regs();
		regs.setStudentNo(student.getStudentNo());
		regs.setDtimeOid(seld.getDtimeOid());
		courseDao.saveOrUpdateRegs(regs);
		courseDao.updateDtimeStdSelect(dtime.getOid(), Short
				.valueOf((short) seldCount));
		// 更新Dtime資料表選課人數+1
		/*
		 * courseDao.updateDtimeStuSelectByOids(seld.getDtimeOid().toString(),
		 * Short.valueOf((short) 1));
		 */
	}

	@SuppressWarnings("unchecked")
	public List getBuilding() {
		String hql="FROM Nabbr WHERE name2 IS NOT NULL GROUP BY name2";
		return courseDao.StandardHqlQuery(hql);
	}

	@SuppressWarnings("unchecked")
	public List getRoom(String roomid, String no1) {

		String hql="FROM Nabbr WHERE no1 LIKE'%"+no1+"%' AND roomId LIKE'%"+roomid+"%'";

		return courseDao.StandardHqlQuery(hql);
	}

	public void saveNabbr(Nabbr nabbr) {
		courseDao.saveNabbr(nabbr);
	}
	
	/**
	 * 儲存StdLoan物件
	 * 
	 * @param stdLoan
	 */
	public void saveStdLoan(StdLoan stdLoan) {
		courseDao.saveStdLoan(stdLoan);
	}

	@SuppressWarnings("unchecked")
	public List checkReRoom(String roomId) {
		String hql="FROM Nabbr WHERE roomId='"+roomId+"'";
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 實做以班級代碼取得學生
	 */
	@SuppressWarnings("unchecked")
	private List getStmdBy(String departClass) {
		String sql="SELECT student_no FROM stmd WHERE depart_class LIKE '"+departClass+"%'";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 實做以學號取得選課資訊
	 */
	@SuppressWarnings("unchecked")
	private List getSeldBy(String studentNo, String sterm) {				
		String sql="SELECT s.Dtime_Oid, d.cscode, cs1.ClassName, c.chi_name, s.student_no, cs2.className as className2,st.student_name, ds.week, ds.begin, ds.end, d.thour "+
				   "FROM Seld s, Dtime_class ds, Dtime d, Csno c, Class cs1, Class cs2, stmd st "+
				   "WHERE s.Dtime_oid=d.Oid AND ds.Dtime_oid=s.Dtime_oid AND s.student_no='"+studentNo+"' AND sterm='"+sterm+"' AND "+
				   "c.cscode=d.cscode AND d.depart_class=cs1.ClassNo AND st.depart_class=cs2.ClassNo AND st.student_no=s.student_no ORDER BY st.student_no";
		List list=jdbcDao.StandardSqlQuery(sql);
		// 2007/12/17 追加 getOtherSeld
		/*2015/9/22發現衝堂查核不需再虛擬加選周一第4節5000與T0001，那之前幾年為何沒人發現？
		 * List otherSeld=getOtherSeld(studentNo, sterm);
		if(otherSeld.size()>0){
			list.addAll(otherSeld);
		}*/
		return list;
	}
	
	/**
	 * 取得不產生選課, 但是要查衝堂的課 5000, T0001
	 * @return
	 */
	private List getOtherSeld(String studentNo, String sterm){
		String departClass=ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'");
		String sql="SELECT d.Oid as Dtime_Oid, d.cscode, cs1.ClassName, c.chi_name, st.student_no, " +
		"cs2.className as className2, st.student_name, ds.week, ds.begin, " +
		"ds.end, d.thour FROM Dtime_class ds, Dtime d, Csno c, Class cs1, Class cs2, stmd st WHERE " +
		"st.student_no='"+studentNo+"' "+
		"AND d.depart_class='"+departClass+"' "+
		"AND d.sterm='"+sterm+"' "+
		// 目前2門
		"AND d.cscode IN('50000', 'T0001' )" +
		"ANd c.cscode=d.cscode AND d.depart_class=cs1.ClassNo AND st.depart_class=cs2.ClassNo AND ds.dtime_oid=d.Oid";
		return jdbcDao.StandardSqlQuery(sql);
	}
	

	/**
	 * 檢查同時段重複選課 2007/12/17 不產生基本選課科目檢查
	 */
	public List checkReSeld(String departClass, String sterm) {
		Map map;
		// 錯誤清單
		List checkReSelds=new ArrayList();
		// 所有管轄範圍內的學生
		Object stmds[]=getStmdBy(departClass).toArray();
		for(int i=0; i<stmds.length; i++){			
			try{
				Object seld[]=getSeldBy(((Map)stmds[i]).get("student_no").toString(), sterm).toArray();				
				// 以學生選課的數目為比較次數的基本值
				for(int j=0; j<seld.length; j++){
					Object reSeld[]=checkReSelds(((Map)seld[j]).get("student_no").toString(),
												((Map)seld[j]).get("Dtime_Oid").toString(),
												((Map)seld[j]).get("week").toString(),
												((Map)seld[j]).get("begin").toString(),
												((Map)seld[j]).get("end").toString(), sterm).toArray();
					// 若查詢結果長度>0
					if(reSeld.length>0){
						System.out.println((Map)seld[j]);
						// 將比對標的裝入錯誤清單
						for(int k=0; k<reSeld.length; k++){
							map = new HashMap();
							map.put("cscode",((Map)seld[j]).get("cscode"));
							map.put("chi_name",((Map)seld[j]).get("chi_name"));
							map.put("ClassName",((Map)seld[j]).get("ClassName"));
							map.put("chi_name",((Map)seld[j]).get("chi_name"));
							map.put("student_no",((Map)seld[j]).get("student_no"));
							map.put("className2",((Map)seld[j]).get("className2"));
							map.put("student_name",((Map)seld[j]).get("student_name"));
							map.put("week",changeWeek(((Map)seld[j]).get("week").toString()));
							map.put("begin",changeClass(((Map)seld[j]).get("begin").toString()));
							map.put("end", changeClass(((Map)seld[j]).get("end").toString()));
							map.put("box", "<img src='images/16-cube-bug.png'>");
							// 將比對對象裝入錯誤清單
							map.put("cscode2",((Map)reSeld[k]).get("cscode"));
							map.put("chi_name2",((Map)reSeld[k]).get("chi_name"));
							map.put("ClassName2",((Map)reSeld[k]).get("ClassName"));
							map.put("chi_name2",((Map)reSeld[k]).get("chi_name"));
							map.put("className22",((Map)reSeld[k]).get("className2"));
							map.put("week2",changeWeek(((Map)reSeld[k]).get("week").toString()));
							map.put("begin2",changeClass(((Map)reSeld[k]).get("begin").toString()));
							map.put("end2", changeClass(((Map)reSeld[k]).get("end").toString()));
							map.put("Oid", changeClass(((Map)reSeld[k]).get("Oid").toString()));
							map.put("box", "<img src='images/16-cube-bug.png'>");
							checkReSelds.add(map);
						}
					}
				}

			}catch(DataAccessException e){
				e.printStackTrace();
				return checkReSelds;
			}
		}
		return checkReSelds;
	}

	/**
	 * 學生衝堂檢查
	 * 
	 * @param studentNo
	 * @param DtimeOid
	 * @param week
	 * @param begin
	 * @param end
	 * @param sterm
	 * @return 衝堂明細清單
	 */
	@SuppressWarnings("unchecked")
	public List checkReSelds(String studentNo, String DtimeOid, String week, String begin, String end, String sterm){		
		
		return jdbcDao.StandardSqlQuery("SELECT s.Oid, s.Dtime_Oid, d.cscode, cs1.ClassName, c.chi_name, cs2.className as className2, ds.week, ds.begin, ds.end, d.thour "+
		"FROM Seld s, Dtime_class ds, Dtime d, Csno c, Class cs1, Class cs2, stmd st "+
		"WHERE s.Dtime_oid=d.Oid AND ds.Dtime_oid=s.Dtime_oid AND s.student_no='"+studentNo+"' AND sterm='"+sterm+"' AND "+
		"c.cscode=d.cscode AND d.depart_class=cs1.ClassNo AND st.depart_class=cs2.ClassNo AND st.student_no=s.student_no AND " +
		"d.Oid<>"+DtimeOid+" AND ds.week='"+week+"' AND (ds.begin <="+end+" AND ds.end >="+begin+"" +")");
	}

	@SuppressWarnings("unchecked")
	public List findCourseByName(String campus, String school, String courseName) {
		List resultList = new ArrayList();
		String Sterm = Toolket.getSysParameter("School_term");
		String times = "";

		if(campus.trim().equalsIgnoreCase("All")) {
			campus = "";
		}
		if(school.trim().equalsIgnoreCase("All")) {
			school = "";
		}

		String hql = "Select c From Csno c Where chiName Like '%" + courseName + "%'";
		List<Csno> csnoList = courseDao.StandardHqlQuery(hql);
		if(csnoList.isEmpty()) {
			return resultList;
		}
		hql = "Select d, c.chiName As chiName From Dtime d," +
		" Csno c Where d.sterm='" + Sterm
		+ "' And d.departClass Like '" + campus.trim()
		+ school.trim() + "%' And c.cscode=d.cscode And d.cscode in (";
		Csno csno;
		for(Iterator<Csno> csIter=csnoList.iterator(); csIter.hasNext();) {
			csno = csIter.next();
			hql = hql + "'" + csno.getCscode();
			if(csIter.hasNext()) {
				hql = hql + "', ";
			} else {
				hql = hql + "')";
			}
		}
		hql = hql + " Order By d.departClass";

		log.debug("QueryByCourseName->hql:" + hql);

		List dtList =  courseDao.StandardHqlQuery(hql);
		if(dtList.isEmpty()) {
			return resultList;
		}

		Object[] dtObj = new Object[3];
		String counts = null;
		for(Iterator dtIter=dtList.iterator(); dtIter.hasNext();) {
			dtObj = (Object[])dtIter.next();
			Dtime dtime = (Dtime)dtObj[0];
			String chiName = dtObj[1].toString();
			String techName;
			if(!dtime.getTechid().equals("")) {
				List<Empl> emplList = this.getEmplBy(dtime.getTechid(), "");
				if(!emplList.isEmpty()){
					techName = ((Empl)emplList.get(0)).getCname();
				}else{
					techName = "";
				}
			} else {
				techName = "";
			}
			times = "";

			Map dtMap = new HashMap();

			hql = "Select dc From DtimeClass dc Where dtimeOid=" + dtime.getOid();
			List<DtimeClass> dcList = courseDao.StandardHqlQuery(hql);

			dtMap.put("depClass", dtime.getDepartClass());
			dtMap.put("depClassName", Toolket.getClassFullName(dtime.getDepartClass()));
			dtMap.put("csCode", dtime.getCscode());
			dtMap.put("chiName", chiName);
			dtMap.put("techName", techName);
			dtMap.put("optName", Toolket.getCourseOpt(dtime.getOpt()));
			dtMap.put("credit", dtime.getCredit());
			dtMap.put("thour", dtime.getThour());
			dtMap.put("Oid", dtime.getOid());
			
			// 要即時計算
			counts = String.valueOf(countSelect(dtime.getOid().toString()));
			// dtMap.put("selected", dtime.getStuSelect());
			dtMap.put("selected", counts);
			dtMap.put("opened", dtime.getOpen().toString());

			for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
				DtimeClass dc = dcIter.next();
				times = times + "[" + changeWeek(dc.getWeek().toString()) + ":" ;
				if(dc.getBegin().equals(dc.getEnd())) {
					times = times + dc.getBegin();
				} else {
					times = times + dc.getBegin() + "-"	+ dc.getEnd();
				}
				times = times + "]";
				// if(dcIter.hasNext()) {
				// times = times + "; ";
				// }
			}
			dtMap.put("times", times);
			resultList.add(dtMap);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List findCourseByWeekTime(String campus, String school, String fweek, String period) {
		List resultList = new ArrayList();
		String Sterm = Toolket.getSysParameter("School_term");
		String times = "";

		if(campus.trim().equalsIgnoreCase("All")) {
			campus = "";
		}
		if(school.trim().equalsIgnoreCase("All")) {
			school = "";
		}

		if(period.trim().equals("")) period="1";
		
		String sql = "Select * From Dtime_class Where week = " + fweek + " And " + period
						+ " between CAST(begin As UNSIGNED) And CAST(end As UNSIGNED)";
		List dcList = jdbcDao.StandardSqlQuery(sql);
		if(dcList.isEmpty()) {
			return resultList;
		}

		String hql = "Select d, c.chiName As chiName From Dtime d," +
				" Csno c Where d.sterm='" + Sterm
				+ "' And d.departClass Like '" + campus.trim()
				+ school.trim() + "%' And c.cscode=d.cscode And d.oid in (";

		DtimeClass dc;

		for(Iterator dcIter=dcList.iterator(); dcIter.hasNext();) {
			Map dcMap = (Map)dcIter.next();
			hql = hql +  dcMap.get("Dtime_oid");
			if(dcIter.hasNext()) {
				hql = hql + ", ";
			} else {
				hql = hql + ")";
			}
		}
		hql = hql + " Order By d.departClass";

		log.debug("QueryByWeekTime->hql:" + hql);

		List dtList =  courseDao.StandardHqlQuery(hql);
		if(dtList.isEmpty()) {
			return resultList;
		}

		Object[] dtObj = new Object[3];
		for(Iterator dtIter=dtList.iterator(); dtIter.hasNext();) {
			dtObj = (Object[])dtIter.next();
			Dtime dtime = (Dtime)dtObj[0];
			String chiName = dtObj[1].toString();
			String techName = "";
			if(!dtime.getTechid().equals("")) {
				List<Empl> emplList = this.getEmplBy(dtime.getTechid(), "");
				if(!emplList.isEmpty())
					techName = ((Empl)emplList.get(0)).getCname();
			} else {
				techName = "";
			}
			times = "";

			Map dtMap = new HashMap();

			hql = "Select dc From DtimeClass dc Where dtimeOid=" + dtime.getOid();
			dcList = courseDao.StandardHqlQuery(hql);

			dtMap.put("depClass", dtime.getDepartClass());
			dtMap.put("depClassName", Toolket.getClassFullName(dtime.getDepartClass()));
			dtMap.put("chiName", chiName);
			dtMap.put("techName", techName);
			dtMap.put("optName", Toolket.getCourseOpt(dtime.getOpt()));
			dtMap.put("credit", dtime.getCredit());
			dtMap.put("thour", dtime.getThour());
			dtMap.put("selected", dtime.getStuSelect());
			dtMap.put("opened", dtime.getOpen().toString());
			dtMap.put("Oid", dtime.getOid());
			for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
				dc = dcIter.next();
				times = times + "[" + changeWeek(dc.getWeek().toString()) + ":" ;
				if(dc.getBegin().equals(dc.getEnd())) {
					times = times + dc.getBegin();
				} else {
					times = times + dc.getBegin() + "-"	+ dc.getEnd();
				}
				times = times + "]";
				// if(dcIter.hasNext()) {
				// times = times + "; ";
				// }
			}
			dtMap.put("times", times);

			resultList.add(dtMap);
		}

		return resultList;
	}

	/**
	 * 取得所有開課主檔基本資料
	 * 
	 * @commend 回傳值以Map結構
	 * @commend open為NULL時不作開放與否判斷
	 * @param classes
	 *            Array of Clazz objects
	 * @param term
	 *            學期
	 * @param opt
	 *            選別
	 * @param open
	 *            開放與否
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getDtimeForOpenCsAll(Clazz[] classes, String term,
			String opt, Byte open) {

		Object[] params = null;
		StringBuffer sql = new StringBuffer(
				"SELECT d.oid, s.ClassName, s.ClassNo, c.cscode, "
						+ "c.chi_name, d.opt, d.credit, d.thour, "
						+ "d.stu_select, d.Select_Limit, d.open, e.cname "
						+ "FROM Csno c, Class s, Dtime d "
						+ "LEFT JOIN empl e ON d.techid = e.idno "
						+ "WHERE c.cscode = d.cscode "
						+ "AND s.classNo = d.depart_class "
						+ "AND d.sterm = ? " + "AND d.depart_class IN (");

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < classes.length; i++) {
			buf.append("'").append(classes[i].getClassNo()).append("',");
		}

		sql.append(StringUtils.substringBeforeLast(buf.toString(), ","))
				.append(") ");
		if (!"ALL".equals(opt)) {
			sql.append("AND d.opt = ? ");
			if (open != null) {
				sql.append("AND d.open = ? ");
				params = new Object[] { term, opt, open };
			} else {
				params = new Object[] { term, opt };
			}
		} else {
			if(open != null) {
				sql.append("AND d.open = ? ");
				params = new Object[] { term, open };
			} else {
				params = new Object[] { term };
			}
		}
		sql.append("ORDER BY d.depart_Class");
		log.info("SQL Commad : " + sql.toString());

		List<Map> list = jdbcDao.StandardSqlQuery(sql.toString(), params);
		for (int i = 0; i < list.size(); i++) {
			Map content = list.get(i);
			content.put("opt2", Global.CourseOpt.getProperty((String) content
					.get("opt")));
			content.put("openName",
					(Boolean) content.get("open") == false ? "不開放" : "開放");
			String cscode = (String) content.get("cscode");
			// 班會,通識課程與系時間不允許選擇
			if ("50000".equals(cscode) || "T0001".equals(cscode)
					|| "T0002".equals(cscode))
				content.put("canChoose", "1");
			else
				content.put("canChoose", "0");
		}

		return list;
	}

	/**
	 * 取得開課主檔基本資料
	 * 
	 * @author Oscar Wei
	 * @commend open為NULL時不作開放與否判斷
	 * @param classes
	 *            Array of Clazz objects
	 * @param campusInCharge2
	 *            校區代碼
	 * @param schoolInCharge2
	 *            學制代碼
	 * @param deptInCharge2
	 *            科系代碼
	 * @param departClass
	 *            班級代碼
	 * @param term
	 *            學期
	 * @param opt
	 *            選別
	 * @param open
	 *            開放與否
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getDtimeForOpenCs(Clazz[] classes, String campusInCharge2,
			String schoolInCharge2, String deptInCharge2, String departClass,
			String term, String opt, Byte open) {

		Object[] params = null;
		StringBuffer sql = new StringBuffer(
				"SELECT d.oid, s.ClassName, s.ClassNo, c.cscode, d.depart_class, "
						+ "c.chi_name, d.opt, d.credit, d.thour, "
						+ "d.stu_select, d.Select_Limit, d.open, e.cname "
						+ "FROM Csno c, Class s, Dtime d "
						+ "LEFT JOIN empl e ON d.techid = e.idno "
						+ "WHERE c.cscode = d.cscode "
						+ "AND s.classNo = d.depart_class "
						+ "AND d.depart_class LIKE ? "
						+ "AND d.depart_class LIKE ? AND d.sterm = ? "
						+ "AND d.depart_class IN (");

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < classes.length; i++) {
			buf.append("'").append(classes[i].getClassNo()).append("',");
		}

		sql.append(StringUtils.substringBeforeLast(buf.toString(), ","))
				.append(") ");
		if (!"ALL".equalsIgnoreCase(opt)) {
			sql.append("AND d.opt = ? ");
			if (open != null) {
				sql.append("AND d.open = ? ");
				params = new Object[] {
						campusInCharge2 + schoolInCharge2 + deptInCharge2 + "%",
						departClass + "%", term, opt, open };
			} else {
				params = new Object[] {
						campusInCharge2 + schoolInCharge2 + deptInCharge2 + "%",
						departClass + "%", term, opt };
			}
		} else {
			if (open != null) {
				sql.append("AND d.open = ? ");
				params = new Object[] {
						campusInCharge2 + schoolInCharge2 + deptInCharge2 + "%",
						departClass + "%", term, open };
			} else {
				params = new Object[] {
						campusInCharge2 + schoolInCharge2 + deptInCharge2 + "%",
						departClass + "%", term };
			}
		}
		sql.append("ORDER BY d.depart_Class, d.cscode, d.opt");
		log.info("SQL Commad : " + sql.toString());

		List<Map> list = jdbcDao.StandardSqlQuery(sql.toString(), params);
		for (int i = 0; i < list.size(); i++) {
			Map content = list.get(i);
			content.put("opt2", Global.CourseOpt.getProperty((String) content
					.get("opt")));
			content.put("openName",
					(Boolean) content.get("open") == false ? "不開放" : "開放");
			content.put("adcdList", findAdcdByDtimeOid(Integer.valueOf(content
					.get("oid").toString())));
			// 即時計算選課人數
			content.put("stu_select",
					countSelect(((Integer) content.get("oid")).toString()));
			String cscode = (String) content.get("cscode");
			// 班會,通識課程與系時間不允許選擇
			if ("50000".equals(cscode) || "T0001".equals(cscode)
					|| "T0002".equals(cscode))
				content.put("canChoose", "1");
			else
				content.put("canChoose", "0");
		}

		return list;
	}

	/**
	 * 以班級代碼取得校區代碼之Service方法
	 * 
	 * @param classNo
	 *            班級代碼
	 * @return 校區代碼
	 */
	public String findCampusNoByClassNo(String classNo) {
		return courseDao.getCampusNoByClassNo(classNo);
	}

	/**
	 * 以Dtime Oid取得Seld目前選課人數之Service方法
	 * 
	 * @param dtimeOid
	 *            Dtime Oid
	 * @return int 目前選課人數
	 */
	public int findSeldCountByDtimeOid(Integer dtimeOid) {
		return courseDao.getSeldCountByDtimeOid(dtimeOid);
	}

	/**
	 * 以Dtime Oid取得加退選紀錄之Service方法
	 * 
	 * @param dtimeOid
	 *            Dtime Oid
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> findAdcdByDtimeOid(Integer dtimeOid) {
		return courseDao.getAdcdByDtimeOid(dtimeOid);
	}

	/**
	 * 以學生學號取得加退選紀錄之Service方法
	 * 
	 * @param studentNo
	 *            學生學號
	 * @param term
	 *            學期
	 * @return java.util.List List of Adcd objects
	 */
	public List<Adcd> findAdcdByStudentNo(String studentNo, String term) {
		return courseDao.getAdcdByStudentNo(studentNo, term);
	}

	/**
	 * 以學生學號取得加退選紀錄之Service方法
	 * 
	 * @param studentNo
	 *            學生學號
	 * @param term
	 *            學期
	 * @return java.util.List List of Adcd objects
	 */
	public List<StdAdcd> findStudentAdcdByStudentNo(String studentNo,
			String term) {
		return courseDao.getStudentAdcdByStudentNo(studentNo, term);
	}

	private String changeWeek(String week){
		switch(Integer.parseInt(week)) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 7:
			return "日";
		default:
			return "?";
		}

	}

	private String changeClass(String classes){

		if(classes.equals("011")){
			classes="N1";
		}
		if(classes.equals("012")){
			classes="N2";
		}
		if(classes.equals("013")){
			classes="N3";
		}
		if(classes.equals("014")){
			classes="N4";
		}

		return classes;
	}

	/**
	 * 實做以中文名稱取得課程
	 */
	@SuppressWarnings("unchecked")
	public List getCsnoBy(String chiName){
		String hql="FROM Csno WHERE chiName LIKE '"+chiName+"%'";
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 實做以課程代碼取得課程名稱.
	 */
	@SuppressWarnings("unchecked")
	public List getCsnameBy(String cscode) {
		String hql="FROM Csno WHERE cscode LIKE '"+cscode+"%'";

		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 實做以教師姓名取得教師id
	 */
	@SuppressWarnings("unchecked")
	public List getTeacherBy(String cname) {
		String hql="FROM Empl WHERE cname LIKE '"+cname+"%'";
		return courseDao.StandardHqlQuery(hql);
	}

	public Dtime findDtimeBy(Integer oid) {
		return courseDao.getDtimeBy(oid);
	}

	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 * 
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findStudentSeldCourse(String studentNo, String term) {
		String sql = "SELECT DISTINCT d.oid, c.chi_name, e.cname, d.opt, d.credit, "
				+ "d.thour, d.stu_select, dc.week, dc.begin, dc.end, n.name2, "
				+ "cl.ClassName, n.room_id, e.oid AS eoid, d.depart_class, d.cscode "
				+ "FROM Seld s, Csno c, Class cl, Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.oid = dc.Dtime_oid "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "LEFT JOIN empl e ON d.techid = e.idno "
				+ "WHERE d.oid = s.Dtime_oid AND d.cscode = c.cscode "
				+ "AND d.depart_class = cl.ClassNo "
				+ "AND s.student_no = ? AND d.Sterm = ? "
				+ "ORDER BY d.oid, dc.week, dc.begin";
		return jdbcDao.StandardSqlQuery(sql, new Object[] { studentNo, term });
	}
	
	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 * 
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findStudentSeldCourse1(String studentNo, String term) {
		String sql = "SELECT DISTINCT d.oid, c.chi_name, e.cname, d.opt, d.credit, "
				+ "d.thour, d.stu_select, dc.week, dc.begin, dc.end, n.name2, "
				+ "cl.ClassName, n.room_id, d.depart_class, d.cscode "
				+ "FROM Seld s, Csno c, Class cl, Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "LEFT JOIN empl e ON d.techid = e.idno "
				+ "WHERE d.oid = s.Dtime_oid AND d.cscode = c.cscode "
				+ "AND d.depart_class = cl.ClassNo "
				+ "AND s.student_no = ? AND d.Sterm = ? "
				+ "ORDER BY d.oid, dc.week, dc.begin";
		return jdbcDao.StandardSqlQuery(sql, new Object[] { studentNo, term });
	}

	/**
	 * 查詢開課選修資料
	 * 
	 * @commend 包括低年級課程資料
	 * @param campusNo Campus
	 * @param schoolNo School
	 * @param deptNo Department
	 * @param grade Grade
	 * @param classNo Student Class No
	 * @param term Term
	 * @return java.util.List List of Map array
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpencsByCondition(String campusNo, String schoolNo,
			String deptNo, String grade, String classNo, String term) {
		List<String> params = new LinkedList<String>();
		params.add(term);
		params.add(campusNo);
		
		// 研究生可以跨校區與跨部制選課
		boolean isMaster = Toolket.isMasterClass(classNo);
		if (isMaster) {
			params.clear();
			params.add(term);
			params.add(grade);
			String sqlCommand = "SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, "
					+ "e.cname, d.opt, d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
					+ "dc.end, n.room_id, d.Select_Limit, cl.Grade, "
					+ "d.cscode, d.depart_class, c.eng_name, d.elearning, c.literacyType "
					+ "FROM Csno c, Class cl, Dtime d LEFT JOIN empl e ON d.techid = e.idno "
					+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
					+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
					+ "WHERE d.cscode = c.cscode AND d.depart_class = cl.ClassNo "
					+ "AND d.sterm = ? AND cl.Grade <= ? AND d.opt = '2' "
					+ "AND cl.SchoolNo IN ('1G', '8G', 'CG', 'DG', 'FG', 'HG') "
					+ "AND c.cscode NOT IN ('50000', 'T0001', 'T0002') "
					+ "ORDER BY d.depart_class";
			List<Map> list = jdbcDao.StandardSqlQuery(sqlCommand, params
					.toArray());
			return list;
		}
		
		// 二技等學生跨選規則年級需+2,因跨選是設定為3或4年級
		String upGrade = ""; // 只針對跨選規則所用
		if (ArrayUtils.contains(IConstants.BACHELOR_2_CLASS, schoolNo))
			upGrade = String.valueOf(Integer.parseInt(grade) + 2); // grade已處理過升級

		StringBuffer sql = new StringBuffer(
				"SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
						+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
						+ "dc.end, n.room_id, d.Select_Limit, cl.Grade, "
						+ "d.cscode, d.depart_class, c.eng_name, d.elearning, c.literacyType "
						+ "FROM Opencs op, Csno c, Dtime d "
						+ "LEFT JOIN empl e ON d.techid = e.idno "
						+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
						+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
						+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
						+ "WHERE d.cscode = c.cscode "
						+ "AND d.oid = op.Dtime_oid AND d.oid = dc.Dtime_oid "
						+ "AND d.sterm = ? "
						+ "AND (op.`Cidno` = '*' OR op.`Cidno` = ?) ");
		
		// 進修推廣部不允許跨部制
		String schoolType = courseDao.getSchoolTypeBySchoolNo(schoolNo);
		if (!"All".equals(schoolNo) && StringUtils.isNotBlank(schoolNo)) {
			if ("N".equals(schoolType))
				sql.append("AND (op.`Sidno` = ?) ");
			else
				sql.append("AND (op.Sidno = '*' OR op.`Sidno` = ?) ");
			params.add(schoolNo);
		}

		if (!"All".equals(deptNo) && StringUtils.isNotBlank(deptNo)) {
			sql.append("AND (op.`Didno` = '*' OR op.`Didno` = ?) ");
			params.add(deptNo);
		}
		
		if (!"All".equals(upGrade) && StringUtils.isNotBlank(upGrade)) {
			// 二技及二專等學生要跨選是設定3或4年級計算
			sql.append("AND (op.`Grade` = '*' OR op.`Grade` <= ?) ");
			params.add(upGrade);
		} else if (!"All".equals(grade) && StringUtils.isNotBlank(grade)) {
			// 非二技及二專等學生還是要以現在年級計算
			sql.append("AND (op.`Grade` = '*' OR op.`Grade` <= ?) ");
			params.add(grade);
		}

		// sql.append("AND (d.opt = '3' OR cl.ClassName like '%通識課程%') ");
		// sql.append("GROUP BY d.Oid, dc.week, dc.begin, dc.end ");
		sql.append("ORDER BY d.Oid, dc.week, dc.begin, d.opt");
		log.info("SQL Commad 1: " + sql);
		log.info("SQL Params 1: " + params);
		List<Map> list = jdbcDao.StandardSqlQuery(sql.toString(), params
				.toArray());
		
		// 本系<=Grade之必選修科目
		sql = new StringBuffer(
				"SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
						+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
						+ "dc.end, n.room_id, d.Select_Limit, d.cscode, "
						+ "d.depart_class, c.eng_name, d.elearning, c.literacyType "
						+ "FROM Csno c, Class cl, Dtime d "
						+ "LEFT JOIN empl e on d.techid = e.idno "
						+ "LEFT JOIN Dtime_class dc ON d.oid = dc.Dtime_oid "
						+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
						+ "WHERE d.cscode = c.cscode "
						+ "AND d.depart_class = cl.ClassNo AND d.sterm = ? "
						+ "AND d.cscode NOT IN ('50000', 'T0001') ");
		String temp = campusNo + schoolNo + deptNo;
		StringBuffer b = new StringBuffer();
		for (int i = 1; i <= Integer.parseInt(grade); i++) {
			b.append("d.depart_class LIKE '").append(temp).append(i).append("_'");
			b.append(" OR ");
		}

		// 加入通識課程
		if (Integer.parseInt(grade) >= 2) {
			temp = campusNo + schoolNo + "02_' ";
			b.append("d.depart_class LIKE '").append(temp);
			sql.append("AND (").append(b.toString()).append(") ");
		} else if (!grade.equals("0") && Integer.parseInt(grade) < 2){
			// 因為會有122A學生加退選,所以grade會為0
			temp = StringUtils.substringBeforeLast(b.toString(), "OR");
			sql.append("AND (").append(temp).append(") ");
		}

		// sql.append("GROUP BY d.Oid, dc.week, dc.begin, dc.end ");
		sql.append("ORDER BY d.Oid, dc.week, dc.begin, d.opt");
		log.info("SQL Commad 2: " + sql);
		params.clear();
		params.add(term);
		log.info("SQL Params 2: " + params);
		List<Map> list1 = jdbcDao.StandardSqlQuery(sql.toString(), params
				.toArray());
		if ("1".equals(term)) {
			classNo = Toolket.processClassNoUp(classNo);
		}
		
		// excludedSelfClassOpt排除學生班級所有科目及其他必修科目(因為上面查詢結果已包含了)
		list.addAll(excludedSelfClassOpt(list1, classNo));
		return list;
	}
	
	/**
	 * 查詢開課選修資料
	 * 
	 * @commend 包括低年級課程資料
	 * @param campusNo Campus
	 * @param schoolNo School
	 * @param deptNo Department
	 * @param grade Grade
	 * @param classNo Student Class No
	 * @param term Term
	 * @return java.util.List List of Map array
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpencsByCondition1(String campusNo, String schoolNo,
			String deptNo, String grade, String classNo, String term) {
		List<String> params = new LinkedList<String>();
		params.add(term);
		params.add(campusNo);
		
		// 研究生可以跨校區與跨部制選課
		boolean isMaster = Toolket.isMasterClass(classNo);
		if (isMaster) {
			params.clear();
			params.add(term);
			params.add(grade);
			String sqlCommand = "SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, "
					+ "e.cname, d.opt, d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
					+ "dc.end, n.room_id, d.Select_Limit, cl.Grade, "
					+ "d.cscode, d.depart_class, c.eng_name, d.elearning "
					+ "FROM Csno c, Class cl, Dtime d LEFT JOIN empl e ON d.techid = e.idno "
					+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
					+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
					+ "WHERE d.cscode = c.cscode AND d.depart_class = cl.ClassNo "
					+ "AND d.sterm = ? AND cl.Grade <= ? AND d.opt = '2' "
					+ "AND cl.SchoolNo IN ('1G', '8G', 'CG', 'DG', 'FG', 'HG') "
					+ "AND c.cscode NOT IN ('50000', 'T0001', 'T0002') "
					+ "ORDER BY d.depart_class";
			List<Map> list = jdbcDao.StandardSqlQuery(sqlCommand, params
					.toArray());
			return list;
		}
		
		// 二技等學生跨選規則年級需+2,因跨選是設定為3或4年級
		String upGrade = ""; // 只針對跨選規則所用
		if (ArrayUtils.contains(IConstants.BACHELOR_2_CLASS, schoolNo))
			upGrade = String.valueOf(Integer.parseInt(grade) + 2); // grade已處理過升級

		StringBuffer sql = new StringBuffer(
				"SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
						+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
						+ "dc.end, n.room_id, d.Select_Limit, cl.Grade, "
						+ "d.cscode, d.depart_class, c.eng_name, d.elearning "
						+ "FROM Opencs op, Csno c, Dtime d "
						+ "LEFT JOIN empl e ON d.techid = e.idno "
						+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
						+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
						+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
						+ "WHERE d.cscode = c.cscode "
						+ "AND d.oid = op.Dtime_oid AND d.oid = dc.Dtime_oid "
						+ "AND d.sterm = ? "
						+ "AND (op.`Cidno` = '*' OR op.`Cidno` = ?) ");
		
		// 進修推廣部不允許跨部制
		String schoolType = courseDao.getSchoolTypeBySchoolNo(schoolNo);
		if (!"All".equals(schoolNo) && StringUtils.isNotBlank(schoolNo)) {
			if ("N".equals(schoolType))
				sql.append("AND (op.`Sidno` = ?) ");
			else
				sql.append("AND (op.Sidno = '*' OR op.`Sidno` = ?) ");
			params.add(schoolNo);
		}

		if (!"All".equals(deptNo) && StringUtils.isNotBlank(deptNo)) {
			sql.append("AND (op.`Didno` = '*' OR op.`Didno` = ?) ");
			params.add(deptNo);
		}
		
		if (!"All".equals(upGrade) && StringUtils.isNotBlank(upGrade)) {
			// 二技及二專等學生要跨選是設定3或4年級計算
			sql.append("AND (op.`Grade` = '*' OR op.`Grade` <= ?) ");
			params.add(upGrade);
		} else if (!"All".equals(grade) && StringUtils.isNotBlank(grade)) {
			// 非二技及二專等學生還是要以現在年級計算
			sql.append("AND (op.`Grade` = '*' OR op.`Grade` <= ?) ");
			params.add(grade);
		}

		// sql.append("AND (d.opt = '3' OR cl.ClassName like '%通識課程%') ");
		// sql.append("GROUP BY d.Oid, dc.week, dc.begin, dc.end ");
		sql.append("ORDER BY d.Oid, dc.week, dc.begin, d.opt");
		log.info("SQL Commad 1: " + sql);
		log.info("SQL Params 1: " + params);
		List<Map> list = jdbcDao.StandardSqlQuery(sql.toString(), params
				.toArray());
		
		// 本系<=Grade之必選修科目
		sql = new StringBuffer(
				"SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
						+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
						+ "dc.end, n.room_id, d.Select_Limit, d.cscode, "
						+ "d.depart_class, c.eng_name, d.elearning "
						+ "FROM Csno c, Class cl, Dtime d "
						+ "LEFT JOIN empl e on d.techid = e.idno "
						+ "LEFT JOIN Dtime_class dc ON d.oid = dc.Dtime_oid "
						+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
						+ "WHERE d.cscode = c.cscode "
						+ "AND d.depart_class = cl.ClassNo AND d.sterm = ? "
						+ "AND d.cscode NOT IN ('50000', 'T0001') ");
		String temp = campusNo + schoolNo + deptNo;
		StringBuffer b = new StringBuffer();
		for (int i = 1; i <= Integer.parseInt(grade); i++) {
			b.append("d.depart_class LIKE '").append(temp).append(i).append("_'");
			b.append(" OR ");
		}

		// 加入通識課程
		if (Integer.parseInt(grade) >= 2) {
			temp = campusNo + schoolNo + "02_' ";
			b.append("d.depart_class LIKE '").append(temp);
			sql.append("AND (").append(b.toString()).append(") ");
		} else if (!grade.equals("0") && Integer.parseInt(grade) < 2){
			// 因為會有122A學生加退選,所以grade會為0
			temp = StringUtils.substringBeforeLast(b.toString(), "OR");
			sql.append("AND (").append(temp).append(") ");
		}

		// sql.append("GROUP BY d.Oid, dc.week, dc.begin, dc.end ");
		sql.append("ORDER BY d.Oid, dc.week, dc.begin, d.opt");
		log.info("SQL Commad 2: " + sql);
		params.clear();
		params.add(term);
		log.info("SQL Params 2: " + params);
		List<Map> list1 = jdbcDao.StandardSqlQuery(sql.toString(), params
				.toArray());
		if ("1".equals(term)) {
			classNo = Toolket.processClassNoUp(classNo);
		}
		
		// excludedSelfClassOpt排除學生班級所有科目及其他必修科目(因為上面查詢結果已包含了)
		list.addAll(excludedSelfClassOpt(list1, classNo));
		return list;
	}
	
	/**
	 * 查詢開課選修資料
	 * 
	 * @commend 第二,三階段無跨選規則
	 * @param campusNo Campus
	 * @param schoolNo School
	 * @param deptNo Department
	 * @param grade Grade
	 * @param classNo Student Class No
	 * @param term Term
	 * @return java.util.List List of Map array
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpencsByCondition4Phase2And3(String campusNo,
			String schoolNo, String deptNo, String grade, String classNo,
			String term) {
		List<String> params = new LinkedList<String>();
		params.add(term);
		params.add(campusNo);
		params.add(deptNo);
		
		// 二技等學生跨選規則年級需+2,因跨選是設定為3或4年級
		// String upGrade = ""; // 只針對跨選規則所用
		if (ArrayUtils.contains(IConstants.BACHELOR_2_CLASS, schoolNo)) {
			String upGrade = String.valueOf(Integer.parseInt(grade) + 2); // grade已處理過升級
			params.add(upGrade);
		} else
			params.add(grade);

		// 一般生與碩士生不可跨選課程
		String sqlCommand = "SELECT d.oid AS DtimeOID, cl.ClassName, c.chi_name, "
				+ "e.cname, d.opt, d.credit, d.thour, d.stu_select, d.Select_Limit, "
				+ "d.depart_class, c.eng_name, d.elearning, d.cscode "
				+ "FROM Dtime d LEFT JOIN empl e ON d.techid = e.idno, Csno c, Class cl "
				+ "WHERE d.cscode = c.cscode AND d.depart_class = cl.ClassNo "
				+ "AND d.sterm = ? AND cl.CampusNo = ? AND cl.DeptNo = ? AND cl.Grade <= ? "
				+ "AND cl.SchoolNo NOT IN ('1G', '8G', 'CG', 'DG', 'FG', 'HG') "
				+ "AND c.cscode NOT IN ('50000', 'T0001', 'T0002') "
				+ "ORDER BY d.depart_class";
		if (Toolket.isMasterClass(classNo))
			sqlCommand = "SELECT d.oid AS DtimeOID, cl.ClassName, c.chi_name, "
					+ "e.cname, d.opt, d.credit, d.stu_select, d.Select_Limit, "
					+ "d.depart_class, c.eng_name, d.elearning, d.cscode "
					+ "FROM Dtime d LEFT JOIN empl e ON d.techid = e.idno, Csno c, Class cl "
					+ "WHERE d.cscode = c.cscode AND d.depart_class = cl.ClassNo "
					+ "AND d.sterm = ? AND cl.CampusNo = ? AND cl.DeptNo = ? AND cl.Grade <= ? "
					+ "AND cl.SchoolNo IN ('1G', '8G', 'CG', 'DG', 'FG', 'HG') "
					+ "AND c.cscode NOT IN ('50000', 'T0001', 'T0002') "
					+ "ORDER BY d.depart_class";
		
		List<Map> list = jdbcDao.StandardSqlQuery(sqlCommand, params.toArray());
		
		// 本系<=Grade之必選修科目
//		sql = new StringBuffer(
//				"SELECT DISTINCT d.oid, cl.ClassName, c.chi_name, e.cname, d.opt, "
//						+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
//						+ "dc.end, n.room_id, d.Select_Limit, d.cscode, "
//						+ "d.depart_class, c.eng_name, d.elearning "
//						+ "FROM Csno c, Class cl, Dtime d "
//						+ "LEFT JOIN empl e on d.techid = e.idno "
//						+ "LEFT JOIN Dtime_class dc ON d.oid = dc.Dtime_oid "
//						+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
//						+ "WHERE d.cscode = c.cscode "
//						+ "AND d.depart_class = cl.ClassNo AND d.sterm = ? "
//						+ "AND d.cscode NOT IN ('50000', 'T0001') ");
//		String temp = campusNo + schoolNo + deptNo;
//		StringBuffer b = new StringBuffer();
//		for (int i = 1; i <= Integer.parseInt(grade); i++) {
//			b.append("d.depart_class LIKE '").append(temp).append(i).append("_'");
//			b.append(" OR ");
//		}
//
//		// 加入通識課程
//		if (Integer.parseInt(grade) >= 2) {
//			temp = campusNo + schoolNo + "02_' ";
//			b.append("d.depart_class LIKE '").append(temp);
//			sql.append("AND (").append(b.toString()).append(") ");
//		} else if (!grade.equals("0") && Integer.parseInt(grade) < 2){
//			// 因為會有122A學生加退選,所以grade會為0
//			temp = StringUtils.substringBeforeLast(b.toString(), "OR");
//			sql.append("AND (").append(temp).append(") ");
//		}
//
//		// sql.append("GROUP BY d.Oid, dc.week, dc.begin, dc.end ");
//		sql.append("ORDER BY d.Oid, dc.week, dc.begin, d.opt");
//		log.info("SQL Commad 2: " + sql);
//		params.clear();
//		params.add(term);
//		log.info("SQL Params 2: " + params);
//		List<Map> list1 = jdbcDao.StandardSqlQuery(sql.toString(), params
//				.toArray());
//		if ("1".equals(term)) {
//			classNo = Toolket.processClassNoUp(classNo);
//		}
//		list.addAll(excludedSelfClassOpt(list1, classNo));
		return list;
	}

	/**
	 * Delete and re-generate a student's AddDelCouseData based on his Seld
	 * 
	 * @author James F. Chiang
	 */
	public void txRegenerateAdcdByStudentTerm(Student student, String term) {

		jdbcDao.delAdcdByStudentTerm(student.getStudentNo(), term);
		List<Seld> selds = courseDao.findSeldByStudentNo(student.getStudentNo());
		List<Dtime> dtimesNotOpen = courseDao.findDtimeByClassTermOpen(student.getDepartClass(), term, false);
		String oidNotOpen = "|";
		for (Dtime aDtime : dtimesNotOpen) {
			oidNotOpen += aDtime.getOid().toString() + "|";
		}
		
		Dtime dtime = null;
		Adcd adcd = null;
		String oidSelected = "|";
		for (Seld seld : selds) {
			try {
				dtime = courseDao.getDtimeBy(seld.getDtimeOid());
				if (term.equals(dtime.getSterm())) {
					oidSelected += dtime.getOid().toString() + "|";
					if (oidNotOpen.indexOf("|" + dtime.getOid().toString() + "|") == -1) {
						// The course is not in the list of Dtimes for student's
						// class which are not open-for-select
						// Add an 'A' entry in AddDelCourseData
						adcd = new Adcd();
						adcd.setAdddraw("A");
						adcd.setDtimeOid(dtime.getOid());
						adcd.setStudentNo(student.getStudentNo());
						adcd.setStudepartClass(student.getDepartClass());
						courseDao.saveObject(adcd);
					}
				}
			} catch(Exception e) {}
		}
		// Check if any Dtime-not-open in his class not selected by the student
		// Add a 'D' entry in AddDelCourseData
		String courseNo;
		for (Dtime aDtime : dtimesNotOpen) {
			if (oidSelected.indexOf("|" + aDtime.getOid().toString() + "|") == -1) {
				courseNo = aDtime.getCscode();
				if (!("50000".equals(courseNo) || "T0001".equals(courseNo) || "T0002".equals(courseNo))) {
					// 班會, 系時間, 通識課程 are skipped for they are NOT courses or
					// acted as only a space-holder
					// in schedule to preserve the time
					adcd = new Adcd();
					adcd.setAdddraw("D");
					adcd.setDtimeOid(aDtime.getOid());
					adcd.setStudentNo(student.getStudentNo());
					adcd.setStudepartClass(student.getDepartClass());
					courseDao.saveObject(adcd);
				}
			}
		}
	}

	/**
	 * 本學年成績 score:總成績, score1:平時, score2:期中, score3:期末 cscode:50000,
	 * T0001不列入計算
	 */
	@SuppressWarnings("unchecked")
	public List getBothStermSeldBy(String studentNo, String sterm, String studentClass) {
		String sql="SELECT d.cscode, d.Oid, cl.ClassName, c.chi_name, d.elearning, d.thour, d.credit, s.score1, s.score2, s.score3, s.score, d.opt FROM " +
		"Seld s, Dtime d, Csno c, Class cl " +
		"WHERE d.cscode NOT IN('50000', 'T0001') AND s.Dtime_oid=d.oid AND d.cscode=c.cscode AND " +
		"s.student_no='"+studentNo+"' AND d.Sterm='"+sterm+"' AND cl.ClassNo=d.depart_class";

		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal=Calendar.getInstance();
		long OpenViewMid=0;
		long OpenViewEnd=0;
		long OpenViewGra=0;
		
		String mySchool=ezGetMap("SELECT SUBSTRING(name, 1, 2)as cname FROM code5 WHERE category='SchoolType' AND idno='"+
				studentClass.substring(0, 2)+"'").get("cname").toString();
		
		try {
			cal.setTime(df.parse(getOpenViewTimeForScore(mySchool, "1").get("OpenViewMid").toString()));// 期中考開放時間
			OpenViewMid=cal.getTime().getTime();			
			cal.setTime(df.parse(getOpenViewTimeForScore(mySchool, "2").get("OpenViewEnd").toString()));// 期末考開放時間
			OpenViewEnd=cal.getTime().getTime();
			cal.setTime(df.parse(getOpenViewTimeForScore(mySchool, "3").get("OpenViewGra").toString()));// 畢業考開放時間
			OpenViewGra=cal.getTime().getTime();
		} catch (ParseException e) {
			
			cal.setTime(new Date());// 期中考開放時間
			OpenViewMid=cal.getTime().getTime();			
			cal.setTime(new Date());// 期末考開放時間
			OpenViewEnd=cal.getTime().getTime();
			cal.setTime(new Date());// 畢業考開放時間
			OpenViewGra=cal.getTime().getTime();
		}
		Date date=new Date();// 現在時間
		String school;// 課程年級
		String grade;// 學制畢業年級
		String tmp;// 暫存
		
		Map map;
		List table=new ArrayList();
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();

		for(int i=0; i<obj.length; i++){
			tmp=ezGetString("SELECT depart_class FROM Dtime d, Class c WHERE d.depart_class=c.ClassNo AND d.Oid="+((Map)obj[i]).get("Oid")+" limit 1");
			
			school=tmp.substring(2, 3);
			grade=tmp.substring(4, 5);
			if(grade.equals("G")){
				grade="2";
			}
			if(school.equals("G")){
				school="2";
			}

			map=new HashMap();
			map.put("chi_name",((Map)obj[i]).get("chi_name"));
			map.put("cscode",((Map)obj[i]).get("cscode"));
			map.put("credit",((Map)obj[i]).get("credit"));
			map.put("thour",((Map)obj[i]).get("thour"));
			map.put("ClassName",((Map)obj[i]).get("ClassName").toString());

			if(OpenViewMid<date.getTime()){ // 若期中考開放日期大於等於現在日期
				map.put("score2",((Map)obj[i]).get("score2")); // 裝期中成績
			}

			// 非畢業班
			if((OpenViewEnd<date.getTime())&&(!school.equals("grade"))){ // 若期末考開放日期大於等於現在日期
				try{
					map.put("score",roundOff(Float.parseFloat(((Map)obj[i]).get("score").toString()), 0)); // 裝期末成績
				}catch(Exception e){
					map.put("score", ((Map)obj[i]).get("score")); // 裝期末成績
				}				
				map.put("score3",((Map)obj[i]).get("score3"));
				map.put("score1",((Map)obj[i]).get("score1"));
			}
			// 畢業班
			if((Integer.parseInt(grade)>=Integer.parseInt(school))&&OpenViewGra<date.getTime()){
				map.put("score",((Map)obj[i]).get("score")); // 裝畢業成績
				try{
					map.put("score",roundOff(Float.parseFloat(((Map)obj[i]).get("score").toString()), 0)); // 裝期末成績
				}catch(Exception e){
					map.put("score", ((Map)obj[i]).get("score")); // 裝畢業成績
				}
				map.put("score3",((Map)obj[i]).get("score3"));
				map.put("score1",((Map)obj[i]).get("score1"));
			}
			map.put("opt",Global.CourseOpt.getProperty(((Map)obj[i]).get("opt").toString()));

			table.add(map);
		}
		school=null;// 課程年級
		grade=null;// 學制畢業年級
		tmp=null;// 暫存
		map=null;
		obj=null;
		mySchool=null;
		return table;
	}

	/**
	 * 以學生學號與學期查詢選課資料之Service方法
	 * 
	 * @param studentNo 學生學號
	 * @param term 學期
	 * @return java.util.List List of objects
	 */
	public int findMinCountBySchoolNo(String schoolNo) {
		return courseDao.getMinCountBySchoolNo(schoolNo);
	}

	/**
	 * Delete and re-generate all students' AddDelCouseData based on their
	 * individual Seld within a class
	 * 
	 * @author James F. Chiang
	 */
	public void txRegenerateAdcdByClassTerm(String classNo, String term) {

		List<Dtime> dtimesNotOpen = courseDao.findDtimeByClassTermOpen(classNo, term, false);
		List<Student> students = memberDao.findStudentsByClazz(classNo);

		for (Student student : students) {

			jdbcDao.delAdcdByStudentTerm(student.getStudentNo(), term);
			List<Seld> selds = courseDao.findSeldByStudentNo(student.getStudentNo());
			// List<Dtime> dtimesNotOpen =
			// courseDao.findDtimeByClassTermOpen(student.getDepartClass(),
			// term, false);
			String oidNotOpen = "|";
			for (Dtime aDtime : dtimesNotOpen) {
				oidNotOpen += aDtime.getOid().toString() + "|";
			}
			
			Dtime dtime;
			Adcd adcd = null;
			String oidSelected = "|";
			for (Seld seld : selds) {
				try {
					dtime = courseDao.getDtimeBy(seld.getDtimeOid());
					if (term.equals(dtime.getSterm())) {
						oidSelected += dtime.getOid().toString() + "|";
						if (oidNotOpen.indexOf("|" + dtime.getOid().toString() + "|") == -1) {
							// The course is not in the list of Dtimes for
							// student's class which are not open for select
							// Add an 'A' entry in AddDelCourseData
							adcd = new Adcd();
							adcd.setAdddraw("A");
							adcd.setDtimeOid(dtime.getOid());
							adcd.setStudentNo(student.getStudentNo());
							adcd.setStudepartClass(classNo);
							courseDao.saveObject(adcd);
						}
					}
				} catch(Exception e) {}
			}
			// Check if any Dtime-not-opened in his class not selected by the
			// student
			// Add a 'D' entry in AddDelCourseData
			String courseNo;
			for (Dtime aDtime : dtimesNotOpen) {
				if (oidSelected.indexOf("|" + aDtime.getOid().toString() + "|") == -1) {
					courseNo = aDtime.getCscode();
					if (!("50000".equals(courseNo) || "T0001".equals(courseNo) || "T0002".equals(courseNo))) {
						// 班會, 系時間, 通識課程 are skipped for they are NOT courses or
						// acted as only a space-holder
						// in schedule to preserve the time
						adcd = new Adcd();
						adcd.setAdddraw("D");
						adcd.setDtimeOid(aDtime.getOid());
						adcd.setStudentNo(student.getStudentNo());
						adcd.setStudepartClass(classNo);
						courseDao.saveObject(adcd);
					}
				}
			}
		}
	}

	/**
	 * 實作以學生與學期查詢班級課表
	 * 
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findCourseByStudentTermWeekdaySched(Student student,
			String term) {
		String sql = "SELECT DISTINCT c.chi_name, cl.ClassName, d.cscode, "
				+ "dc.place, n.name2 FROM Seld s, Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid AND d.oid = s.Dtime_oid "
				+ "AND s.student_no = ? AND d.Sterm = ? AND dc.week = ? "
				+ "AND CAST(dc.begin AS UNSIGNED) <= ? "
				+ "AND CAST(dc.end AS UNSIGNED) >= ?";
		
		List<Map> ret = new ArrayList<Map>();
		List<Map> list = null;
		Map<String, String> map = null;
		StringBuffer chiName = null, cscode = null, place = null, name2 = null;
		Object[] args = null;
		
		for (int weekday = 1; weekday <= 7; weekday++) { // 星期
			for (int sched = 1; sched <= 15; sched++) { // 節次
				args = new Object[] { student.getStudentNo(), term, weekday,
						sched, sched };
				list = jdbcDao.StandardSqlQuery(sql, args);
				if (!list.isEmpty()) {
					if (list.size() > 1) {
						// 處理衝堂課表
						map = new HashMap<String, String>();
						chiName = new StringBuffer();
						cscode = new StringBuffer();
						place = new StringBuffer();
						name2 = new StringBuffer();
						for (Map content : list) {
							log.info(content.get("chi_name"));
							chiName.append(content.get("chi_name") + ", ");
							cscode.append(content.get("cscode") + ", ");
							place
									.append((StringUtils
											.isNotBlank((String) content
													.get("place")) ? content
											.get("place") : "")
											+ ", ");
							name2
									.append((StringUtils
											.isNotBlank((String) content
													.get("name2")) ? content
											.get("name2") : "")
											+ ", ");
						}
						map.put("chi_name", StringUtils.substringBeforeLast(
								chiName.toString(), ","));
						map.put("cscode", StringUtils.substringBeforeLast(
								cscode.toString(), ","));
						map.put("place", StringUtils.substringBeforeLast(place
								.toString(), ","));
						map.put("name2", StringUtils.substringBeforeLast(name2
								.toString(), ","));
						ret.add(map);
					} else {
						Map<String, String> content = list.get(0);
						log.info(content.get("chi_name"));
						ret.add(content);
					}
				} else
					// 查無則給一個空的
					ret.add(new HashMap());
			}
		}
		return ret;
	}

	/**
	 * 實作以學生與學期查詢所屬班會與系時間
	 * 
	 * @param student tw.edu.chit.model.Student object
	 * @param term 學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findT0001And50000ByStudent(Student student, String term) {
		String sql = "SELECT DISTINCT c.chi_name, cl.ClassName, d.cscode, "
				+ "dc.place, n.name2, dc.week, dc.begin, dc.end, d.oid "
				+ "FROM Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid "
				+ "AND d.cscode IN ('50000', 'T0001') "
				+ "AND d.depart_class = ? " + "AND d.sterm = ?";
		return jdbcDao.StandardSqlQuery(sql, new Object[] {
				student.getDepartClass(), term });
	}
	
	/**
	 * 取得特定時間之班會與系時間作衝堂檢查
	 * 
	 * @param student
	 *            tw.edu.chit.model.Student object
	 * @param term
	 *            學期
	 * @param week
	 *            星期
	 * @param node
	 *            節次
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findT0001And50000ByStudentAndWeek(Student student,
			String term, Integer week, String node) {
		String sql = "SELECT DISTINCT * "
				+ "FROM Dtime d "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Csno c ON d.cscode = c.cscode "
				+ "LEFT JOIN Class cl ON d.depart_class = cl.ClassNo "
				+ "LEFT JOIN Nabbr n ON n.room_id = dc.place "
				+ "WHERE d.Oid = dc.Dtime_oid "
				+ "AND d.cscode IN ('50000', 'T0001') "
				+ "AND d.depart_class = ? AND d.sterm = ? AND dc.week = ? "
				+ "AND CAST(dc.begin AS UNSIGNED) <= ? AND CAST(dc.end AS UNSIGNED) >= ?";
		return jdbcDao.StandardSqlQuery(sql, new Object[] {
				student.getDepartClass(), term, week, node, node });
	}

	/**
	 * 
	 * @param student
	 * @param term
	 * @param week
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findCourseOverlay(Student student, String term,
			Integer week, String node) {
		String sql = "SELECT * "
				+ "FROM Seld s, Dtime d, Dtime_class dc, Csno c "
				+ "WHERE d.Oid = s.Dtime_oid AND d.Oid = dc.Dtime_oid "
				+ "AND d.cscode = c.cscode AND s.student_no = ? "
				+ "AND d.Sterm = ? AND dc.week = ? "
				+ "AND CAST(dc.begin AS UNSIGNED) <= ? "
				+ "AND CAST(dc.end AS UNSIGNED) >= ?";
		List<Map> T0001And50000 = findT0001And50000ByStudentAndWeek(student,
				term, week, node);
		List<Map> ret = jdbcDao.StandardSqlQuery(sql, new Object[] {
				student.getStudentNo(), term, week, node, node });
		ret.addAll(T0001And50000);
		return ret;
	}

	public List<DtimeClass> findDtimeClassInfo(Seld seld) {
		return courseDao.getDtimeClassInfo(seld);
	}

	/**
	 * 實做以學號取得學生共讀幾年,有哪幾年 已不再使用
	 */
	public List getSchoolYearListBy(String studentNo) {
		String sql="SELECT school_year FROM ScoreHist WHERE student_no='"+studentNo+"' GROUP BY school_year ORDER BY school_year DESC";
		Map map;
		List table=new ArrayList();
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();

		for(int i=0; i<obj.length; i++){
			map=new HashMap();
			map.put("school_year", ((Map)obj[i]).get("school_year"));
			table.add(map);
		}
		return table;
	}

	/**
	 * 取得學生學年學期成績
	 */
	@SuppressWarnings("unchecked")
	public List getScoreHistBy(String studentNo, String schoolYear) {
		String sql="SELECT cs.ClassName, c.cscode, s.evgr_type, s.school_year, s.school_term, s.student_no, s.score, s.opt, s.credit, c.chi_name "+
		   "FROM ScoreHist s LEFT OUTER JOIN Class cs ON cs.ClassNo=s.stdepart_class, Csno c WHERE student_no='"+studentNo+"' AND s.cscode=c.cscode AND s.school_year = '"+schoolYear+"' " +
		   "ORDER BY s.school_year, s.school_term";
		
		List list=jdbcDao.StandardSqlQuery(sql);
		for(int i=0; i<list.size(); i++){
			try{
				((Map)list.get(i)).put("evgr_type", getScoreType(((Map)list.get(i)).get("evgr_type").toString()));
				((Map)list.get(i)).put("opt1", ((Map)list.get(i)).get("opt"));
				((Map)list.get(i)).put("opt", Global.CourseOpt.getProperty(((Map)list.get(i)).get("opt").toString()));
			}catch(Exception e){
				((Map)list.get(i)).put("evgr_type", "資料有誤");
				((Map)list.get(i)).put("opt1", "資料有誤");
				((Map)list.get(i)).put("opt", "資料有誤");
			}		
		}
		return list;
	}

	/**
	 * 實做取得本學期教科書
	 */
	@SuppressWarnings("unchecked")
	public List getMyBookBy(String studentNo, String year, String sterm) {
		String sql = "SELECT d.Oid, cs.chi_name, co.BookSuggest, e.cname "
				+ "FROM Seld s, Csno cs, Dtime d, CourseIntroduction co, empl e WHERE "
				+ "d.techid=e.idno AND co.Dtime_oid=d.Oid AND s.Dtime_oid=d.Oid AND cs.cscode=d.cscode AND "
				+ "s.student_no='" + studentNo + "' AND d.Sterm='" + sterm
				+ "' AND co.SchoolYear = '" + year + "' AND co.SchoolTerm = '"
				+ sterm + "'";
		List table=new ArrayList();
		Map map;
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();

		for (int i = 0; i < obj.length; i++) {
			map = new HashMap();
			map.put("chi_name", ((Map) obj[i]).get("chi_name"));
			String bookSuggest = (String) ((Map) obj[i]).get("BookSuggest");
			bookSuggest = bookSuggest != null ?  bookSuggest.replaceAll("\\n", "").replaceAll("\\r",
					"") : "";
			map.put("BookSuggest", bookSuggest);
			map.put("cname", ((Map) obj[i]).get("cname"));
			map.put("oid", ((Map) obj[i]).get("oid"));

			table.add(map);
		}

		return table;
	}

	/**
	 * 以班級代碼與學期查詢通識課程資訊之Service方法
	 * 
	 * @param departClass
	 *            班級代碼(只包括校區+部制代碼)
	 * @param term
	 *            學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findLiteracyClass(String departClass, String term) {
		String sql = "SELECT cl.ClassNo, cl.ClassName, c.cscode, c.chi_name, e.cname, d.opt, "
				+ "d.credit, d.thour, d.stu_select, dc.week, dc.begin, dc.end, "
				+ "n.room_id, n.name2, d.Oid "
				+ "FROM Csno c, Class cl, Dtime d "
				+ "LEFT JOIN empl e ON d.techid = e.idno "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
				+ "WHERE d.cscode = c.cscode "
				+ "AND d.depart_class = cl.ClassNo "
				+ "AND d.sterm = ? AND d.depart_class LIKE ? "
				+ "ORDER BY dc.week";
		return jdbcDao
				.StandardSqlQuery(sql, new Object[] { term, departClass });
	}

	/**
	 * 以班級代碼與學期查詢開放選修課程資訊之Service方法
	 * 
	 * @param departClass
	 *            班級代碼(只包括校區+部制代碼)
	 * @param term
	 *            學期
	 * @return java.util.List List of Map objects
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findOpenCourse(String departClass, String term) {
		List<String> params = new LinkedList<String>();
		params.add(term);
		String sql = "SELECT d.Oid, cl.ClassNo, cl.ClassName, c.cscode, c.chi_name, e.cname, "
				+ "d.opt, d.credit, d.thour, d.stu_select, dc.week, dc.begin, "
				+ "dc.end, n.room_id, n.name2 "
				+ "FROM Csno c, Class cl, Dtime d "
				+ "LEFT JOIN empl e ON d.techid = e.idno "
				+ "LEFT JOIN Dtime_class dc ON d.Oid = dc.Dtime_oid "
				+ "LEFT JOIN Nabbr n ON dc.place = n.room_id "
				+ "WHERE d.cscode = c.cscode "
				+ "AND d.depart_class = cl.ClassNo AND d.open = '1' "
				+ "AND d.sterm = '" + term + "' ";
		if (!"All".equalsIgnoreCase(departClass)) {
			sql += "AND d.depart_class LIKE " + departClass;
			params.add(departClass);
		}
		sql += "GROUP BY d.oid ORDER BY d.depart_class, dc.week";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 課程大綱列表及比例取得基本規則 "50000", "T0001", "T0002" 不列入查核範圍
	 * 2007/5/11 台北日間部課務組要求 "專題、服務教育、書報討論、軍訓" 不列入計算
	 * 2007/5/14 台北日間部課務組要求未指定授課教師的課程也必須列入計算 
	 * 2007/9/5 台北日間部課務組要求 "體育", "軍訓", "服務教育" 不列入計算
	 * 2008/2/1 台北日間部課務組要求 "體育", "軍訓", "服務教育" 列入計算
	 * 2008/5/7 "體育", "軍訓", "服務教育" 不列入計算
	 * 2010/3/15 新增過濾代碼
	 */
	public List getCheckGist(String departClass, String sterm) {
		
		//String classes[]=new String[]{"50000", };
		String classes=
		"'TB071', 'TB072', 'TA622', 'TA621','TCAM1','TCAM2'," +
		"'TM121','TM122','TN0G1','TN0G2','TG991','TG992'," +
		"'GE009', 'GE008','GB042','GB041','GB015', 'GB016', " +
		"'GB017', 'GB018', 'GC009', 'GC010', 'GC011', 'GC012'";
		//String chi_name="&&c.chi_name NOT LIKE '%論文%' &&c.chi_name NOT LIKE '%書報%'&&c.chi_name NOT LIKE '%專題%'";
		String sql;		
		
		sql=" SELECT d.Introduction, LENGTH(d.syllabi)as syl, LENGTH(d.syllabi_sub)as syl_sub, d.Oid, " +
		"cl.ClassName, c.chi_name, e.cname FROM " +
		"Dtime d, Csno c, empl e, Class cl WHERE " +
		"cl.ClassNo=d.depart_class AND e.idno=d.techid AND " +
		"d.cscode=c.cscode AND d.Sterm='"+sterm+"' " +
		"AND d.depart_class LIKE '"+departClass+"%' AND c.cscode NOT IN("+classes+")";
		//+chi_name+" )ORDER BY d.cscode";
		
		List list=ezGetBy(sql);
		
		int Introduction, syllabi, syllabi_sub;
		int x;
		
		
		Map m;
		int j,k;
		StringBuilder sb;
		for(int i=0; i<list.size(); i++){			
			j=0;
			k=0;
			sb=new StringBuilder();			
			if(((Map)list.get(i)).get("Introduction")!=null){
				
				try {
					m=parseIntr(((Map)list.get(i)).get("Introduction").toString());
					j=m.get("chi").toString().length();
					k=m.get("eng").toString().length();
					((Map)list.get(i)).put("chi", j);
					((Map)list.get(i)).put("eng", k);
					if(j==0)sb.append("中/");
					if(k==0)sb.append("英/");
				}catch(Exception e){
					sb.append("中/英/");
				}
				
			
			
			
			}else{
				((Map)list.get(i)).put("chi", 0);
				((Map)list.get(i)).put("eng", 0);
			}
			
			((Map)list.get(i)).put("intr", "<a href='/CIS/Print/teacher/IntorDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"'>檢視</a>");
			
			if(((Map)list.get(i)).get("syl")!=null){
			if(Integer.parseInt(((Map)list.get(i)).get("syl").toString())<1){sb.append("大/");}
			}else{sb.append("大/");}
			
			if(((Map)list.get(i)).get("syl_sub")!=null){
			if(Integer.parseInt(((Map)list.get(i)).get("syl_sub").toString())<1){sb.append("週");}}
			else{sb.append("週");}
			
			
			
			((Map)list.get(i)).put("syllabi", ((Map)list.get(i)).get("syl"));
			((Map)list.get(i)).put("syl_sub", ((Map)list.get(i)).get("syl_sub"));
			((Map)list.get(i)).put("err", sb);
			((Map)list.get(i)).put("syl", "<a href='/CIS/Print/teacher/SylDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"'>檢視</a>");			
		}		
		//return sortListByKey(list, "err");
		return list;
	}

	/**
	 * 以category查詢科系資料之Service方法
	 * 
	 * @param category
	 *            分類
	 * @return tw.edu.chit.model.DepartmentInfo objects
	 */
	public DepartmentInfo findDepartmentInfoByCategory(String category) {
		return courseDao.getDepartmentInfoByCategory(category);
	}

	/**
	 * 實做教師排課批次查核
	 */
	public List getReOpTechBy(String departClass, String sterm) {
		//已開學所以暫無此需求
		String sql1="SELECT d.Oid, d.techid, ds.week, ds.begin, ds.end FROM Dtime d, Dtime_class ds "+
				   "WHERE " +
				   "d.Oid=ds.Dtime_oid AND " +
				   "d.depart_class LIKE'"+departClass+"%' AND " +
				   "d.Sterm='"+sterm+"'";

		return null;
	}

	/**
	 * 判斷成績型態
	 */
	private String getScoreType(String scoreType){

			switch(Integer.parseInt(scoreType)){
				case 1:
					return "正常選課";
				case 2:
					return "隨班附讀";
				case 3:
					return "暑修";
				case 4:
					return "跨校取得";
				case 5:
					return "待補修";
				case 6:
					return "抵免";
				default:
					return "異常";
			}
		}

	/**
	 * 實做簡易開課人數查詢
	 */
	public List EzGetStuSel(String sterm, String departClass, String cscode) {
		String sql="SELECT st.student_name, s.student_no, cl.ClassName FROM Dtime d, Seld s, Class cl, Csno cs, stmd st "+
				   "WHERE d.Oid=s.Dtime_oid AND " +
				   "d.depart_class=cl.ClassNo AND cs.cscode=d.cscode AND " +
				   "st.student_no=s.student_no AND d.depart_class LIKE '"+departClass+"%' AND " +
				   "d.Sterm='"+sterm+"' AND d.cscode LIKE'"+cscode+"%'";

		List list=new ArrayList();
		Map map;
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();

		for(int i=0; i<obj.length; i++){
			map=new HashMap();
			map.put("ClassName", ((Map)obj[i]).get("ClassName"));
			map.put("student_no", ((Map)obj[i]).get("student_no"));
			map.put("student_name", ((Map)obj[i]).get("student_name"));
			list.add(map);
		}

		return list;
	}

	public List CountDepartClass(String departClass, String grade) {
		String sql="SELECT COUNT(*) co, c.ClassNo FROM stmd s, Class c WHERE "+
				   "c.ClassNo=s.depart_class AND " +
				   "c.Grade<"+grade+" AND " +
				   "s.depart_class LIKE '"+departClass+"%' AND " +
				   "c.DeptNo<>'G' " +
				   "GROUP BY s.depart_class";

		Map map;
		List table=new ArrayList();
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();
		for(int i=0; i<obj.length; i++){
			map=new HashMap();

			map.put("co", ((Map)obj[i]).get("co"));
			map.put("ClassNo", ((Map)obj[i]).get("ClassNo"));
			// 10人以下的班級不算班級
			if(Integer.parseInt(((Map)obj[i]).get("co").toString())>5&& ((Map)obj[i]).get("ClassNo").toString().length()>5){
				table.add(map);
			}
		}

		return table;
	}

	public List CountDepartClass() {
		String sql="SELECT COUNT(*) co FROM stmd GROUP BY depart_class";

		Map map;
		List table=new ArrayList();
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();
		for(int i=0; i<obj.length; i++){
			map=new HashMap();

			map.put("co", ((Map)obj[i]).get("co"));
			table.add(map);
		}

		return table;
	}
	
	/**
	 * 學分數查核
	 */
	public List getCheckCredit(String departClass, String sterm, String minimum) {
		/*String sql="SELECT s.student_no, s.student_name, c.ClassNo, c.ClassName  FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND " +
				   "depart_class LIKE '"+departClass+"%'";

		List table=new ArrayList();
		Map map;
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();		
		List selds;
		
		for(int i=0; i<obj.length; i++){
			
			float credit=0.0f;
			float thour=0.0f;
			
			selds=jdbcDao.StandardSqlQuery("SELECT d.credit, d.thour FROM Dtime d, Seld s WHERE d.Oid=s.Dtime_oid AND d.Sterm='"+sterm+"' " +
					"AND s.student_no='"+((Map)obj[i]).get("student_no")+"'" );
			
			for(int j=0; j<selds.size(); j++){
				credit=credit+Float.parseFloat(((Map)selds.get(j)).get("credit").toString());
				thour=thour+Float.parseFloat(((Map)selds.get(j)).get("thour").toString());
			}
			
			map=new HashMap();
			// Object
			// obj1[]=countCredit(((Map)obj[i]).get("student_no").toString(),
			// sterm).toArray();
			// List objs=countCredit(((Map)obj[i]).get("student_no").toString(),
			// sterm);
			
			if(credit<Integer.parseInt(minimum)){

				map.put("student_no", ((Map)obj[i]).get("student_no"));
				map.put("student_name", ((Map)obj[i]).get("student_name"));
				map.put("ClassNo", ((Map)obj[i]).get("ClassNo"));
				map.put("ClassName", ((Map)obj[i]).get("ClassName"));
				if (selds.size()>0){
					
					// map.put("creditLess", ((Map)obj1[0]).get("credit"));
					// map.put("thourLess", ((Map)obj1[0]).get("hours"));
					// map.put("creditLess", ((Map)objs.get(0)).get("credit"));
					// map.put("thourLess", ((Map)objs.get(0)).get("hours"));
					map.put("creditLess", credit);
					map.put("thourLess", thour);
					
				}
				table.add(map);
			}
		}*/		
		
		return jdbcDao.StandardSqlQuery("SELECT st.ident_remark, css.name as occur_status, st.student_no,st.student_name,c.ClassNo, c.ClassName,IFNULL((SELECT SUM(d.credit) FROM Seld s, Dtime d WHERE "
		+ "s.Dtime_oid=d.Oid AND s.student_no=st.student_no),0)as cnt,sce.max, sce.min FROM stmd st LEFT OUTER JOIN CODE_STMD_STATUS css ON css.id=st.occur_status, Class c,"
		+ "SYS_CALENDAR_ELECTIVE sce WHERE c.ClassNo=st.depart_class AND sce.depart=c.SchoolType AND "
		+ "sce.grade=c.Grade AND st.depart_class LIKE'"+departClass+"%'HAVING cnt>sce.max OR cnt<sce.min ORDER BY c.ClassNo,st.student_no;");
	}
	
	/*
	 * private List countCredit(String studentNo, String sterm){ String
	 * sql="SELECT * FROM Dtime d, Seld s " + "WHERE d.Oid=s.Dtime_oid AND
	 * d.Sterm='"+sterm+"' " + "AND s.student_no='"+studentNo+"'";
	 * System.out.println(studentNo); List table=new ArrayList(); Map map;
	 * Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray(); float credit=0.0f;
	 * float hours=0.0f; for(int i=0; i<obj.length; i++){
	 * credit=credit+Float.parseFloat(((Map)obj[i]).get("credit").toString());
	 * hours=hours+Float.parseFloat(((Map)obj[i]).get("thour").toString()); }
	 * map=new HashMap();
	 * 
	 * map.put("credit", credit); map.put("hours", hours); table.add(map);
	 * return table; }
	 */

	public List getExamDate() {
		// TODO 中止開發試務系統
		String hql="FROM ExamDate";
		String sql="";
		List list=courseDao.StandardHqlQuery(hql);
		ExamDate examDate;
		for(int i=0; i<list.size(); i++){
			examDate=(ExamDate)list.get(i);
		}



		return null;
	}

	// 將衝堂學生紀錄,但允許加選課程
	private String addSelectedSeldForOneStudent(Seld seld, Student student,
			String term, boolean isStudent) throws SeldException {

		StringBuffer ret = new StringBuffer();
		List<DtimeClass> dcs = findDtimeClassInfo(seld);
		int begin = 0, end = 0;
		for (DtimeClass dc : dcs) {
			begin = dc.getBegin() != null ? Integer.parseInt(dc.getBegin()) : 0;
			end = dc.getEnd() != null ? Integer.parseInt(dc.getEnd()) : 0;
			for (int i = begin; i <= end; i++)
				if (!findCourseOverlay(student, term, dc.getWeek(),
						String.valueOf(i)).isEmpty()) {
					ret.append(student.getStudentName() + "["
							+ student.getStudentNo() + "],");
					break;
				} else
					ret.append("");
		}

		// 3代表第三階段選課,如果不是3會將學生班級年級+1
		processSeldSaveOrUpdateForOneStudent(seld, student, term, isStudent, 3);
		return ret.toString();
	}

	public List getSeldBy(String dtimeOid) {

		String sql="SELECT st.sex, d.depart_class, d.credit, d.thour, d.opt, c2.ClassName as ClassName2, st.student_no, st.student_name, cl.ClassNo, cl.ClassName, " +
					"s.Oid, c.cscode, c.chi_name FROM Seld s, stmd st, Csno c, Dtime d, Class cl, Class c2 WHERE s.Dtime_oid=d.Oid AND "+
					"s.student_no=st.student_no AND d.cscode=c.cscode AND cl.ClassNo=st.depart_class AND c2.ClassNo=d.depart_class AND "+
					"d.Oid="+dtimeOid+" ORDER BY st.student_no";

		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 實做取得報部系所代碼
	 */
	public List getPecode9() {
		String sql="Select * FROM Pecode9";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 定義取得報部學制代碼
	 */
	public List getPecode11(){
		String sql="SELECT * FROM Pecode11";
			return jdbcDao.StandardSqlQuery(sql);
	}

	public List CountStudentBy(String departClass) {
		departClass = "___" + departClass + "__";
		String sql="SELECT c.ClassName, COUNT(*) FROM Class c, stmd st "+
				   "WHERE c.ClassNo=st.depart_class AND c.ClassNo LIKE '"+departClass+"' "+
				   "GROUP BY c.ClassNo";
		return jdbcDao.StandardSqlQuery(sql);
	}

	public List getJustBy(String departClass, String studentNo, String studentName, String docNo, String begin, String end) {
		String sql="SELECT cl.ClassName, d.no, st.sex, st.student_name, d.student_no, c.name, s.name as n1, d.cnt1, s1.name as n2, d.cnt2, d.ddate " +
				"FROM Class cl, stmd st, subs s, code2 c, desd d LEFT OUTER JOIN subs s1 ON s1.no=d.kind2 WHERE cl.ClassNo=d.depart_class AND " +
				"st.student_no=d.student_no AND d.reason=c.no AND s.no=d.kind1 AND d.student_no LIKE '"+studentNo+"%' AND " +
				"st.student_no LIKE '"+studentNo+"%' AND " +
				"d.depart_class LIKE '"+departClass+"%' AND d.no LIKE '"+docNo+"%' AND " +
				"st.student_name LIKE '"+studentName+"%' AND d.ddate>='"+begin+"' AND d.ddate<='"+end+"'";

		List obj=jdbcDao.StandardSqlQuery(sql);
		Map map;
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		for(int i=0; i<obj.size(); i++){
			map=new HashMap();
			
			
			if(((Map)obj.get(i)).get("sex").equals("1")){
				map.put("sex", "男");
			}else{
				map.put("sex", "女");
			}
			try {
				date = (Date)df.parse(((Map)obj.get(i)).get("ddate").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("ddate", Toolket.printNativeDate(date));
			map.put("ClassName", ((Map)obj.get(i)).get("ClassName"));

			((Map)obj.get(i)).putAll(map);
		}

		return obj;
	}

	/**
	 * 實做取得最近一次成績轉檔學年學期
	 */
	public Map getFiledRecord() {
		String sql="SELECT Value FROM Parameter WHERE Name='Filed_year'";
		String sql1="SELECT Value FROM Parameter WHERE Name='Filed_term'";
		Object obj1[]=jdbcDao.StandardSqlQuery(sql).toArray();
		Object obj2[]=jdbcDao.StandardSqlQuery(sql1).toArray();

		Map map=new HashMap();
		map.put("filedYear", ((Map)obj1[0]).get("Value"));
		map.put("filedTerm", ((Map)obj2[0]).get("Value"));

		return map;
	}

	/**
	 * 取得學生上學期成績
	 */
	public List getFiledStermSeldBy(String studentNo, String school_year, String school_term, String studentClass) {
		String sql="SELECT c.chi_name, s.credit, s.score, s.opt, s.cscode " +
				   "FROM ScoreHist s, Csno c WHERE s.cscode " +
				   "NOT IN('50000', 'T0001') AND s.cscode=c.cscode AND " +
				   "s.student_no='"+studentNo+"' AND " +
				   "s.school_term='"+school_term+"' AND " +
				   "s.school_year='"+school_year+"' " +
				   "ORDER BY s.opt";

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		long OpenViewMid = 0;
		long OpenViewEnd = 0;
		long OpenViewGra = 0;
		String mySchool=ezGetMap("SELECT SUBSTRING(name, 1, 2)as cname FROM code5 WHERE category='SchoolType' AND idno='"+
				studentClass.substring(0, 2)+"'").get("cname").toString();
		try {
			OpenViewMid= sdf.parse(getOpenViewTimeForScore(mySchool, "1").get("OpenViewMid").toString()).getTime();
			OpenViewEnd=sdf.parse(getOpenViewTimeForScore(mySchool, "2").get("OpenViewEnd").toString()).getTime();// 期末考開放時間
			OpenViewGra=sdf.parse(getOpenViewTimeForScore(mySchool, "3").get("OpenViewGra").toString()).getTime();// 畢業考開放時間

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date date=new Date();

		Map map;
		List table=new ArrayList();
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();

		for(int i=0; i<obj.length; i++){
			map=new HashMap();
			map.put("chi_name",((Map)obj[i]).get("chi_name"));
			map.put("credit",((Map)obj[i]).get("credit"));

			map.put("score1",((Map)obj[i]).get("score1"));
			map.put("cscode", ((Map)obj[i]).get("cscode"));
			map.put("score2",((Map)obj[i]).get("score2"));// 裝期中成績
			map.put("score",((Map)obj[i]).get("score")); // 裝期末成績
			map.put("score3",((Map)obj[i]).get("score3"));

			map.put("opt",Global.CourseOpt.getProperty(((Map)obj[i]).get("opt").toString()));

			table.add(map);
			}
		return table;
	}

	/**
	 * 取得學生當學年/歷年成績資料
	 */
	public List getScoreHistBy(String studentNo, String year, String term) {
		String sql = "SELECT c.chi_name, c.cscode, s.* "
				+ "FROM ScoreHist s, Csno c WHERE s.cscode "
				+ "NOT IN('50000', 'T0001') AND s.cscode = c.cscode AND "
				+ "s.student_no = '" + studentNo + "' AND "
				+ "s.school_term = '" + term + "' AND " + "s.school_year = '"
				+ year + "' " + "ORDER BY s.cscode DESC, s.opt";
		/*
		List table = new ArrayList();
		List l = jdbcDao.StandardSqlQuery(sql);
		for (int i = 0; i < l.size(); i++) {
			Map map = new HashMap();
			map.put("chi_name", ((Map)l.get(i)).get("chi_name"));
			map.put("cscode", ((Map)l.get(i)).get("cscode"));
			map.put("credit", ((Map)l.get(i)).get("credit"));
			map.put("score", ((Map)l.get(i)).get("score"));
			map.put("score1", ((Map)l.get(i)).get("score1"));
			map.put("cscode", ((Map)l.get(i)).get("cscode"));
			map.put("score2", ((Map)l.get(i)).get("score2")); // 期中成績
			map.put("score3", ((Map)l.get(i)).get("score3"));
			map.put("opt", Global.CourseOpt.getProperty(((Map) l.get(i)).get("opt").toString()));
			// map.put("opt1", ((Map) obj[i]).get("opt"));
			table.add(map);
		}
		*/
		return jdbcDao.StandardSqlQuery(sql);
		//return table;
	}

	/**
	 * 查選爆的課
	 */
	public List getOverSelect(String departClass, String term) {
		String sql1="SELECT d.Select_Limit, d.Oid as dOid, d.credit, cd.name, cl.ClassName, cl.ClassNo, cs.cscode, cs.chi_name, e.cname, co.Oid "+
		   "FROM Dtime d LEFT OUTER JOIN CourseIntroduction co ON d.Oid=co.Dtime_oid, "+
		   "Csno cs, empl e, Class cl, code5 cd "+
		   "WHERE " +
		   "cd.category='dept' AND " +
		   "cd.idno=cl.DeptNo AND " +
		   "cl.ClassNo=d.depart_class AND " +
		   "e.idno=d.techid AND (" +
		   "d.depart_class LIKE'"+departClass+"%') AND " +
		   "d.Sterm='"+term+"' AND " +
		   "d.cscode=cs.cscode"	;
		Object obj[]=jdbcDao.StandardSqlQuery(sql1).toArray();
		Map map;
		List table=new ArrayList();
		for(int i=0; i<obj.length; i++){
			String sql2="SELECT COUNT(*) as co1 FROM Seld WHERE Dtime_oid="+((Map)obj[i]).get("dOid");
			int count=jdbcDao.StandardSqlQueryForInt(sql2);

			if(count>Integer.parseInt(((Map)obj[i]).get("Select_Limit").toString())){
				map=new HashMap();
				map.put("name", ((Map)obj[i]).get("name"));
				map.put("ClassName", ((Map)obj[i]).get("ClassName"));
				map.put("ClassNo", ((Map)obj[i]).get("ClassNo"));
				map.put("cscode", ((Map)obj[i]).get("cscode"));
				map.put("chi_name", ((Map)obj[i]).get("chi_name"));
				map.put("cname", ((Map)obj[i]).get("cname"));
				map.put("credit", ((Map)obj[i]).get("credit"));
				map.put("Select_Limit", ((Map)obj[i]).get("Select_Limit"));
				map.put("count_now", count);

				table.add(map);
			}
		}

		return table;
	}

	/**
	 * 批次開課所用的查詢方法 (含有使用者權限)
	 */
	public List getDtimForBatch(Clazz[] classes, String cscode,
			String techid, String term,String choseType, String open, String elearning, String classLess){
		StringBuffer strBuf=new StringBuffer("SELECT ce.idno2, d.Select_Limit, d.techid, d.Oid, cs.ClassName, d.depart_class, c.chi_name, c.cscode, c.eng_name, e.cname, d.opt, d.credit, d.thour," +
				" d.stu_select, d.open, d.elearning, d.extrapay, d.crozz " +
				"FROM (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno) LEFT OUTER JOIN CodeEmpl ce ON ce.idno=e.unit, Class cs, Csno c" +
				" WHERE d.depart_class IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");

		}

			strBuf.delete(strBuf.length()-2, strBuf.length()-1);
			strBuf.append(") AND c.cscode=d.cscode AND cs.classNo=d.depart_class ");
			if(!techid.trim().equals("")){
				strBuf.append(" AND d.techid='"+techid+"'");
			}
			if(!classLess.equals("")){
				strBuf.append(" AND d.depart_class LIKE'"+classLess+"%'");
			}
			if(!cscode.trim().equals("")){
				strBuf.append(" AND d.cscode = '"+cscode+"'");
			}
			if(!term.trim().equals("%")){
				strBuf.append(" AND d.sterm = '"+term+"'");
			}
			if(!elearning.trim().equals("%")){
				strBuf.append(" AND d.elearning = '"+elearning+"'");
			}
			if(!open.trim().equals("%")){
				strBuf.append(" AND d.open = '"+open+"'");
			}
			if(!choseType.trim().equals("%")){
				strBuf.append(" AND opt = '"+choseType+"'");
			}

			strBuf.append(" ORDER BY d.depart_class, c.cscode");
		List table = new ArrayList();
		Map map;
		Object[] dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString()).toArray();
		for(int i=0; i<dtimeList.length; i++){
			map = new HashMap();
			map.put("oid",((Map)dtimeList[i]).get("Oid"));
			map.put("departClass2", ((Map)dtimeList[i]).get("ClassName"));
			map.put("departClass", ((Map)dtimeList[i]).get("depart_class"));
			map.put("chiName2", ((Map)dtimeList[i]).get("chi_name"));
			map.put("cscode", ((Map)dtimeList[i]).get("cscode"));
			map.put("techName", ((Map)dtimeList[i]).get("cname"));
			map.put("credit", ((Map)dtimeList[i]).get("credit"));
			map.put("thour", ((Map)dtimeList[i]).get("thour"));
			map.put("eng_name", ((Map)dtimeList[i]).get("eng_name"));
			map.put("idno2", ((Map)dtimeList[i]).get("idno2"));
			map.put("Select_Limit", ((Map)dtimeList[i]).get("Select_Limit"));
			map.put("techid", ((Map)dtimeList[i]).get("techid"));
			map.put("opt2", Global.CourseOpt.getProperty(((Map)dtimeList[i]).get("opt").toString()));
			map.put("opt", ((Map)dtimeList[i]).get("opt").toString());
			map.put("open", ((Map)dtimeList[i]).get("open"));
			map.put("open2", getCross(((Map)dtimeList[i]).get("Oid").toString()));
			map.put("elearning", ((Map)dtimeList[i]).get("elearning").toString());
			map.put("teacherS", getDtimeTeacherBy(((Map)dtimeList[i]).get("Oid").toString())); // 一科目多教師
			// map.put("open2", ((Map)dtimeList[i]).get("crozz"));
			map.put("extrapay2", getExtrapay(((Map)dtimeList[i]).get("extrapay").toString()));
			map.put("opencsList", getOpencs(((Map)dtimeList[i]).get("Oid").toString()));
			table.add(map);
		}

		return table;
	}

	/**
	 * 實做以查詢介面取得Dtime 無權限參考
	 */

	public List getDtimeBy(String cscode, String techid, String term,String choseType, String open, String elearning, String classLess, String chi_name, String cname){

		StringBuffer strBuf=new StringBuffer("SELECT ce.idno2, d.Select_Limit, d.techid, d.Oid, cs.ClassName, " +
				"d.depart_class, c.chi_name, c.cscode, c.eng_name, e.cname, d.opt, d.credit, d.thour," +
				" d.stu_select, d.open, d.elearning, d.extrapay, d.crozz " +
				"FROM (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno) " +
				"LEFT OUTER JOIN CodeEmpl ce ON ce.idno=e.unit, Class cs, Csno c" +
				" WHERE ");

			strBuf.append("c.cscode=d.cscode AND cs.classNo=d.depart_class ");
			
			// 若有指明教師

			// 若班級有指明手key關鍵字
			if(!classLess.equals("")){
				strBuf.append(" AND d.depart_class LIKE'"+classLess+"%'");
			}
			// 若條件有指明科目關鍵字
			if(!cscode.trim().equals("")){
				strBuf.append(" AND d.cscode = '"+cscode+"'");
			}
			// 若條件有指明學期
			if(!term.trim().equals("%")){
				strBuf.append(" AND d.sterm = '"+term+"'");
			}
			// 若條件有指定遠距
			if(!elearning.trim().equals("%")){
				strBuf.append(" AND d.elearning = '"+elearning+"'");
			}
			
			// 若條件有指定遠距
			if(!techid.trim().equals("")){
				strBuf.append(" AND d.techid = '"+techid+"'");
			}
			// 有指定開放規則
			if(!open.trim().equals("%")){
				strBuf.append(" AND d.open = '"+open+"'");
			}
			// 有指定選別
			if(!choseType.trim().equals("%")){
				strBuf.append(" AND opt = '"+choseType+"'");
			}
			if(!chi_name.trim().equals("")&&cscode.equals("")){// 有指定課程中文名稱
				strBuf.append(" AND c.chi_name LIKE '"+chi_name+"%'");
			}
			
			if(!cname.trim().equals("")){// 有指定教師
				strBuf.append(" AND e.cname LIKE '"+cname+"%'");
			}			
			strBuf.append(" ORDER BY d.depart_class, c.cscode");
			
			//System.out.println(strBuf);
			
		List table = new ArrayList();
		Map map;
		
		Object[] dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString()).toArray();

		for(int i=0; i<dtimeList.length; i++){

			map = new HashMap();
			map.put("oid",((Map)dtimeList[i]).get("Oid"));
			map.put("departClass2", ((Map)dtimeList[i]).get("ClassName"));
			map.put("departClass", ((Map)dtimeList[i]).get("depart_class"));
			if(((Map)dtimeList[i]).get("chi_name").toString().length()>=8){
				map.put("chiName2", ((Map)dtimeList[i]).get("chi_name").toString().subSequence(0, 8)+"..");
			}else{
				map.put("chiName2", ((Map)dtimeList[i]).get("chi_name"));
			}
			map.put("chiName3", ((Map)dtimeList[i]).get("chi_name"));
			map.put("cscode", ((Map)dtimeList[i]).get("cscode"));
			map.put("techName", ((Map)dtimeList[i]).get("cname"));
			map.put("credit", ((Map)dtimeList[i]).get("credit"));
			map.put("thour", ((Map)dtimeList[i]).get("thour"));
			map.put("eng_name", ((Map)dtimeList[i]).get("eng_name"));
			map.put("idno2", ((Map)dtimeList[i]).get("idno2"));
			map.put("Select_Limit", ((Map)dtimeList[i]).get("Select_Limit"));
			map.put("techid", ((Map)dtimeList[i]).get("techid"));
			// map.put("stuSelect", ((Map)dtimeList[i]).get("stu_select"));
			map.put("stuSelect", countSelect(((Map)dtimeList[i]).get("Oid").toString()));
			map.put("openName", getOpen((Boolean)((Map)dtimeList[i]).get("open")));
			map.put("opt2", Global.CourseOpt.getProperty(((Map)dtimeList[i]).get("opt").toString()));
			map.put("elearningName", getElearningName(((Map)dtimeList[i]).get("elearning").toString()));
			// map.put("open2",
			// getOpen2(getOpencSize(((Map)dtimeList[i]).get("Oid").toString())));
			// map.put("open2", ((Map)dtimeList[i]).get("crozz"));
			map.put("open2", getCross(((Map)dtimeList[i]).get("Oid").toString()));
			map.put("extrapay2", getExtrapay(((Map)dtimeList[i]).get("extrapay").toString()));
			map.put("icon", 
					"<a href='/CIS/StudentSel?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='選課學生'></a>" +
					"<a href='/CIS/Print/teacher/SylDoc.do?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='綱要下載'></a>" +
					"<a href='/CIS/Print/teacher/IntorDoc.do?Oid="+((Map)dtimeList[i]).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='簡介下載'></a>");
			table.add(map);
		}

		return table;
	}

	public List getDtimeByHql(String oid) {
		String hql="FROM Dtime WHERE oid="+oid;
		return courseDao.StandardHqlQuery(hql);
	}

	/**
	 * 取得期中、期末、畢業 成績開放查詢時間
	 */
	
	public Map getOpenViewTimeForScore(String schoolId, String type){
		String sql1="SELECT view_date FROM optime1 WHERE depart='"+schoolId+"' AND level='"+type+"'";
		String sql2="SELECT view_date FROM optime1 WHERE depart='"+schoolId+"' AND level='"+type+"'";
		String sql3="SELECT view_date FROM optime1 WHERE depart='"+schoolId+"' AND level='"+type+"'";

		Map map=new HashMap();
		Object obj1[]=jdbcDao.StandardSqlQuery(sql1).toArray();
		Object obj2[]=jdbcDao.StandardSqlQuery(sql2).toArray();
		Object obj3[]=jdbcDao.StandardSqlQuery(sql3).toArray();

		map.put("OpenViewMid",((Map)obj1[0]).get("view_date"));
		map.put("OpenViewEnd",((Map)obj2[0]).get("view_date"));
		map.put("OpenViewGra",((Map)obj3[0]).get("view_date"));
		
		return map;
	}

	/**
	 * 取得未做的教學評量(期中)
	 */
	public List getCoAnswBy(String studentNo, String DtimeOid, String Sterm) {
		// 取得已做的期中評量
		String sql1="SELECT Dtime_oid FROM Coansw WHERE student_no='"+studentNo+"' AND type='M'";
		Object obj1[]=jdbcDao.StandardSqlQuery(sql1).toArray();
		String dtimeOid[] = new String[obj1.length];
		for(int i=0; i<obj1.length; i++){
			dtimeOid[i]=((Map)obj1[i]).get("Dtime_oid").toString();
		}

		// 取得未做的期中評量 (因為必需對教師做評量，因此沒有對"techid"做"LEFT OUTER JOIN"處理!)
		StringBuffer buf=new StringBuffer(
					"SELECT d.Oid, d.cscode, e.cname, c.chi_name " +
					"FROM Dtime d, Seld s, empl e, Csno c " +
					"WHERE "+
					"e.idno=d.techid AND "+
					"d.cscode=c.cscode AND "+
					"d.Sterm='"+Sterm+"' AND "+
					"d.Oid=s.Dtime_oid AND "+
					"s.student_no='"+studentNo+"'");

					if(obj1.length>0){
						buf.append(" AND "+
								   "d.Oid NOT IN(");

						for(int i=0; i<dtimeOid.length; i++){
							buf.append("'"+dtimeOid[i]+"',");
						}
						buf.delete(buf.length()-1, buf.length()-0);
						buf.append(")");
					}
		Object obj2[]=jdbcDao.StandardSqlQuery(buf.toString()).toArray();
		List table=new ArrayList();
		Map map;
		for(int i=0; i<obj2.length; i++){
			map=new HashMap();
			map.put("Oid", ((Map)obj2[i]).get("Oid"));
			map.put("cscode", ((Map)obj2[i]).get("cscode"));
			map.put("cname", ((Map)obj2[i]).get("cname"));
			map.put("chi_name", ((Map)obj2[i]).get("chi_name"));

			table.add(map);
		}
		return table;
	}

	/**
	 * 取得問題及選項
	 */
	public List getQuestionBy(String type) {
		// 取得問題
		String sql="SELECT Oid, options, textValue FROM CoQuestion WHERE type='"+type+"' ORDER BY sequence";
		Object obj[]=jdbcDao.StandardSqlQuery(sql).toArray();
		List table=new ArrayList();
		Map map;
		for(int i=0; i<obj.length; i++){
			map=new HashMap();
			// 取得問題的子選項
			String hql="FROM CoQuestion " +
					   "WHERE parentOid="+((Map)obj[i]).get("Oid")+" ORDER BY sequence";
			CoQuestion coquestion;
			List list=(List)courseDao.StandardHqlQuery(hql);
			List tableBuf=new ArrayList();
			Map mapBuf;
			for(int j=0; j<list.size(); j++){
				mapBuf=new HashMap();
				coquestion=(CoQuestion) list.get(j);
				mapBuf.put("options", coquestion.getOptions());
				mapBuf.put("parentOid", coquestion.getParentOid());
				mapBuf.put("value", coquestion.getValue());

				tableBuf.add(mapBuf);
			}

			map.put("question", ((Map)obj[i]).get("options"));
			map.put("textValue", ((Map)obj[i]).get("textValue"));
			map.put("option", tableBuf);

			table.add(map);

		}
		return table;
	}

	public void saveCoansw(Coansw coansw) {
		courseDao.saveObject(coansw);
	}
	
	public void saveRcact(Rcact rcact) {
		courseDao.saveObject(rcact);
	}

	/**
	 * 一般SQL查詢
	 * @param sql
	 * @return List of Map
	 */
	public List ezGetBy(String sql) {

		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 篩選
	 */
	public void doFilter(List chair, String dtimeOid, String type) {

		for(int i=0; i<chair.size(); i++){
			// 刪Regs
			jdbcDao.StandardSqlRemove("DELETE FROM Regs WHERE Dtime_oid='"+((Map)chair.get(i)).get("dtimeOid").toString()+"' AND " +
									  "student_no='"+((Map)chair.get(i)).get("student_no").toString()+"'");
			// 刪Adcd
			jdbcDao.StandardSqlRemove("DELETE FROM AddDelCourseData WHERE Dtime_oid='"+((Map)chair.get(i)).get("dtimeOid").toString()+"' AND " +
									  "student_no='"+((Map)chair.get(i)).get("student_no").toString()+"'");
			// 刪Seld
			jdbcDao.StandardSqlRemove("DELETE FROM Seld WHERE Oid='"+((Map)chair.get(i)).get("seldOid").toString()+"'");

			// 儲存槓龜名單
			SeldStuFilter stu=new SeldStuFilter();
			stu.setCscode((String) ((Map)chair.get(i)).get("cscode"));
			stu.setCsdepartClass((String) ((Map)chair.get(i)).get("depart_class"));
			stu.setNote("");
			stu.setDtimeOid(Integer.parseInt(dtimeOid));
			stu.setStdepartClass((String) ((Map)chair.get(i)).get("ClassNo"));
			stu.setSterm((String) ((Map)chair.get(i)).get("Sterm"));
			stu.setStudentNo((String) ((Map)chair.get(i)).get("student_no"));
			stu.setType(type);
			courseDao.saveObject(stu);

			// 有槓龜過的課
			Integer studentSel=jdbcDao.StandardSqlQueryForInt("SELECT COUNT(*) FROM Dtime d, Seld s WHERE d.Oid=s.Dtime_oid AND d.Oid='"+dtimeOid+"'");
			Dtime dtime = courseDao.getDtimeBy(Integer.parseInt(dtimeOid));
			dtime.setStuSelect(Short.parseShort(studentSel.toString()));
			courseDao.saveDtime(dtime);

		}
		SeldCouFilter cou=new SeldCouFilter();

		List list=courseDao.StandardHqlQuery("FROM Dtime WHERE oid="+dtimeOid);
		Dtime dtime=(Dtime)list.get(0);



		cou.setCredit(dtime.getCredit());
		cou.setCscode(dtime.getCscode());
		cou.setDepartClass(dtime.getDepartClass());
		cou.setElearning(dtime.getElearning());
		cou.setCrozz(dtime.getCrozz());
		cou.setDtimeOid(Integer.parseInt(dtimeOid));
		cou.setOpen(dtime.getOpen());
		cou.setSelectLimit(dtime.getSelectLimit());
		cou.setOpt(dtime.getOpt());
		cou.setSterm(dtime.getSterm());
		cou.setStuSelect(dtime.getStuSelect());
		cou.setTechid(dtime.getTechid());
		cou.setThour(dtime.getThour());
		cou.setType(type);
		cou.setExtrapay(dtime.getExtrapay());
		courseDao.saveObject(cou);

		if(type.trim().equals("D")){

			// 刪Dtime_class
			jdbcDao.StandardSqlRemove("DELETE FROM Dtime_class WHERE Dtime_oid='"+dtimeOid+"'");
			// 刪Dtime_teacher
			jdbcDao.StandardSqlRemove("DELETE FROM Dtime_teacher WHERE Dtime_oid='"+dtimeOid+"'");
			// 刪Opencs
			jdbcDao.StandardSqlRemove("DELETE FROM Opencs WHERE Dtime_oid='"+dtimeOid+"'");
			// 刪Dtime
			jdbcDao.StandardSqlRemove("DELETE FROM Dtime WHERE Oid='"+dtimeOid+"'");

		}
	}
	
	/**
	 * 不明
	 * @param list
	 * @param departClass
	 * @return
	 */
	private List<Map> excludedSelfClassOpt(List<Map> list, String departClass) {
		List<Map> ret = new LinkedList<Map>();
		for (Map map : list) {
			if (!departClass.equals((String) map.get("depart_class")))
				ret.add(map);
			else if(!"1".equals((String) map.get("opt")))
				ret.add(map);
		}
		return ret;
	}
	
	/**
	 * 取得篩選課程
	 * @param String cscode
	 */
	public List getCouFilter(Clazz[] classes, String cscode, String techid, String term, String choseType, String open, String elearning, String classLess) {
		StringBuffer strBuf=new StringBuffer("SELECT d.Dtime_oid, d.type, ce.idno2, d.Select_Limit, d.techid, d.Oid, cs.ClassName, d.depart_class, c.chi_name, c.cscode, c.eng_name, e.cname, d.opt, d.credit, d.thour," +
				" d.stu_select, d.open, d.elearning, d.extrapay, d.crozz " +
				"FROM (Seld_couFilter d LEFT OUTER JOIN empl e ON d.techid=e.idno) LEFT OUTER JOIN CodeEmpl ce ON ce.idno=e.unit AND ce.category='UnitTeach', Class cs, Csno c" +
				" WHERE d.depart_class IN (");

		for(int i=0; i<classes.length; i++){
			strBuf.append("'"+classes[i].getClassNo()+"', ");

		}

		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		strBuf.append(") AND c.cscode=d.cscode AND cs.classNo=d.depart_class ");

		if(!techid.trim().equals("")){// 若有指明教師
			strBuf.append(" AND d.techid='"+techid+"'");
		}
		if(!classLess.equals("")){// 若班級有指明手key關鍵字
			strBuf.append(" AND d.depart_class LIKE'"+classLess+"%'");
		}
		if(!cscode.trim().equals("")){// 若條件有指明科目關鍵字
			strBuf.append(" AND d.cscode = '"+cscode+"'");
		}
		if(!term.trim().equals("%")){// 若條件有指明學期
			strBuf.append(" AND d.sterm = '"+term+"'");
		}
		if(!elearning.trim().equals("%")){// 若條件有指定遠距
			strBuf.append(" AND d.elearning = '"+elearning+"'");
		}
		if(!open.trim().equals("%")){// 有指定開放規則
			strBuf.append(" AND d.open = '"+open+"'");
		}
		if(!choseType.trim().equals("%")){// 有指定選別
			strBuf.append(" AND opt = '"+choseType+"'");
		}

		strBuf.append(" ORDER BY d.type, d.depart_class, c.cscode");
		
		System.out.println(strBuf);
		List table = new ArrayList();
		Map map;
		Object[] dtimeList;
		try{
			dtimeList=jdbcDao.StandardSqlQuery(strBuf.toString()).toArray();
		}catch(Exception e){
			return null;
		}
		

		for(int i=0; i<dtimeList.length; i++){

			map = new HashMap();
			map.put("oid",((Map)dtimeList[i]).get("Oid"));
			map.put("dtimeOid",((Map)dtimeList[i]).get("Dtime_oid"));
			map.put("departClass2", ((Map)dtimeList[i]).get("ClassName"));
			map.put("departClass", ((Map)dtimeList[i]).get("depart_class"));
			map.put("chiName2", ((Map)dtimeList[i]).get("chi_name"));
			map.put("cscode", ((Map)dtimeList[i]).get("cscode"));
			map.put("techName", ((Map)dtimeList[i]).get("cname"));
			map.put("credit", ((Map)dtimeList[i]).get("credit"));
			map.put("thour", ((Map)dtimeList[i]).get("thour"));
			map.put("eng_name", ((Map)dtimeList[i]).get("eng_name"));
			map.put("idno2", ((Map)dtimeList[i]).get("idno2"));
			map.put("Select_Limit", ((Map)dtimeList[i]).get("Select_Limit"));
			map.put("techid", ((Map)dtimeList[i]).get("techid"));
			map.put("type", chFilterType(((Map)dtimeList[i]).get("type").toString()));
			map.put("stuSelect", countFilterSelect(((Map)dtimeList[i]).get("Dtime_oid").toString()));
			map.put("openName", getOpen((Boolean)((Map)dtimeList[i]).get("open")));
			map.put("opt2", Global.CourseOpt.getProperty(((Map)dtimeList[i]).get("opt").toString()));
			map.put("elearningName", getElearningName(((Map)dtimeList[i]).get("elearning").toString()));
			
			try{
				map.put("open2", getCross(((Map)dtimeList[i]).get("Oid").toString()));
			}catch(Exception e){
				
			}
			
			// map.put("extrapay2",
			// getExtrapay(((Map)dtimeList[i]).get("extrapay").toString()));
			// map.put("icon", "<img src='images/ico_file_excel1.png' border='0'
			// title='選課人頭'>");
			table.add(map);
		}

		return table;
	}

	/**
	 * 取人數
	 * 
	 * @param DtimeOid
	 * @return
	 */
	private Integer countFilterSelect(String DtimeOid) {
		String sql = "SELECT COUNT(*) FROM Seld_stuFilter WHERE Dtime_oid='" + DtimeOid
				+ "'";
		return jdbcDao.StandardSqlQueryForInt(sql);
	}


	/**
	 * 取得篩選過的課程
	 */
	public List SeldCouFilter(String oid) {
		String hql="FROM SeldCouFilter d, Csno c, Clazz s WHERE d.oid="+oid+" AND c.cscode=d.cscode AND d.departClass=s.classNo";
		Object[] aTripple;
		SeldCouFilter    aDtime;
		//Csno	 aCsno;
		//Clazz    aClazz;
		List     table = new ArrayList();
		List dtimeList=courseDao.StandardHqlQuery(hql);
		for (int i=0; i < dtimeList.size(); i++){
			aTripple = (Object[])dtimeList.get(i);
			aDtime = (SeldCouFilter)aTripple[0];
			//aCsno  = (Csno)aTripple[1];
			//aClazz = (Clazz)aTripple[2];
			table.add(aDtime);
		}
		return table;
	}

	/**
	 * 雙重功能取得篩選人頭 true:取得單一人頭 false:取得那個人頭被篩選的課
	 */

	public List getSeldStuFilterBy(String dtimeOid[], boolean b) {
		StringBuffer strBuf=new StringBuffer();

		for(int i=0; i<dtimeOid.length; i++){
			strBuf.append(dtimeOid[i]+", ");
		}
		// System.out.println(strBuf.length());
		strBuf.delete(strBuf.length()-2, strBuf.length()-1);
		String sql="SELECT s.Oid as seldOid, d.Dtime_oid as dtimeOid, d.depart_class, d.credit, d.thour, d.opt, d.Sterm, c2.ClassName as ClassName2, st.student_no, st.student_name, " +
					"cl.ClassNo, cl.ClassName, cl.Grade, c.cscode, c.chi_name FROM Seld_stuFilter s, stmd st, Csno c, Seld_couFilter d, Class cl, Class c2 " +
					"WHERE s.Dtime_oid=d.Dtime_oid AND "+
					"s.student_no=st.student_no AND d.cscode=c.cscode AND cl.ClassNo=st.depart_class AND c2.ClassNo=d.depart_class AND ";

		if(b==true){
			sql=sql+"d.Dtime_oid IN("+strBuf.toString() +") GROUP BY st.student_no ORDER BY st.student_no";
		}else{
			sql=sql+"st.student_no='"+strBuf+"'";
		}

		return jdbcDao.StandardSqlQuery(sql);
	}


	/**
	 * 轉篩選代碼
	 * 
	 * @param type
	 * @return
	 */

	private String chFilterType(String type){
		if(type.trim().equals("F")){
			type="篩選";
		}
		if(type.trim().equals("D")){
			type="不足";
		}
		if(type.trim().equals("O")){
			type="刪除";
		}
		return type;
	}

	/**
	 * 實作非篩選作業刪Dtime 記錄一個O
	 */
	public void removeDtime(String dtimeOid) {
		String hql="from Dtime WHERE oid='"+dtimeOid+"'";

		Dtime dtime=(Dtime)courseDao.StandardHqlQuery(hql).get(0);
		SeldCouFilter seldCouFilter=new SeldCouFilter();

		seldCouFilter.setCredit(dtime.getCredit());
		seldCouFilter.setCrozz(dtime.getCrozz());
		seldCouFilter.setCscode(dtime.getCscode());
		seldCouFilter.setDepartClass(dtime.getDepartClass());
		seldCouFilter.setDtimeOid(dtime.getOid());
		seldCouFilter.setElearning(dtime.getElearning());
		seldCouFilter.setExtrapay(dtime.getExtrapay());
		seldCouFilter.setOpen(dtime.getOpen());
		seldCouFilter.setOpt(dtime.getOpt());
		seldCouFilter.setSelectLimit(dtime.getSelectLimit());
		seldCouFilter.setSterm(dtime.getSterm());
		seldCouFilter.setStuSelect(dtime.getStuSelect());
		seldCouFilter.setTechid(dtime.getTechid());
		seldCouFilter.setThour(dtime.getThour());
		seldCouFilter.setType("O"); //

		//courseDao.saveObject(seldCouFilter);

		String hql1="FROM Seld WHERE dtimeOid='"+dtimeOid+"'";
		List students=courseDao.StandardHqlQuery(hql1);
		Seld seld;
		for(int i=0; i<students.size(); i++){
			SeldStuFilter seldStuFilter=new SeldStuFilter();
			seld=(Seld)students.get(i);
			seldStuFilter.setCscode(seld.getCscode());
			// seldStuFilter.setCsdepartClass(seld.getDepartClass());
			seldStuFilter.setDtimeOid(seld.getDtimeOid());
			// seldStuFilter.setNote("");
			seldStuFilter.setStdepartClass(seld.getStdepartClass());
			// seldStuFilter.setSterm(seld.)
			seldStuFilter.setStudentNo(seld.getStudentNo());
			seldStuFilter.setType("O");// 代碼 O = O...`'"rz

			courseDao.saveObject(seldStuFilter);
		}

		// 刪Regs
		jdbcDao.StandardSqlRemove("DELETE FROM Regs WHERE Dtime_oid='"+dtimeOid+"'");
		// 刪Adcd
		jdbcDao.StandardSqlRemove("DELETE FROM AddDelCourseData WHERE Dtime_oid='"+dtimeOid+"'");
		// 刪Seld
		jdbcDao.StandardSqlRemove("DELETE FROM Seld WHERE Oid='"+dtimeOid+"'");
		// 刪Dtime_class
		jdbcDao.StandardSqlRemove("DELETE FROM Dtime_class WHERE Dtime_oid='"+dtimeOid+"'");
		// 刪Dtime_teacher
		jdbcDao.StandardSqlRemove("DELETE FROM Dtime_teacher WHERE Dtime_oid='"+dtimeOid+"'");
		// 刪Opencs
		jdbcDao.StandardSqlRemove("DELETE FROM Opencs WHERE Dtime_oid='"+dtimeOid+"'");
		// 刪Dtime
		jdbcDao.StandardSqlRemove("DELETE FROM Dtime WHERE Oid='"+dtimeOid+"'");

	}

	/**
	 * 實作復原篩選
	 */
	public void rollbackDtime(String dtimeOid) {
		String hql="FROM SeldCouFilter WHERE dtimeOid='"+dtimeOid+"'";
		List rolDtime=courseDao.StandardHqlQuery(hql);
		SeldCouFilter dtimeR;

		for(int i=0; i<rolDtime.size(); i++){
			dtimeR=(SeldCouFilter) rolDtime.get(i);

			Dtime dtime=new Dtime();
			dtime.setCredit(dtimeR.getCredit());
			dtime.setCrozz(dtimeR.getCrozz());
			dtime.setCscode(dtimeR.getCscode());
			dtime.setDepartClass(dtimeR.getDepartClass());
			dtime.setElearning(dtimeR.getElearning());
			dtime.setExtrapay(dtimeR.getExtrapay());

			if(dtimeR.getType().equals("F")){
				dtime.setOid(dtimeR.getDtimeOid());
			}

			// dtime.setOid(dtimeR.getDtimeOid());
			dtime.setOpen(dtimeR.getOpen());
			dtime.setOpt(dtimeR.getOpt());
			dtime.setSelectLimit(dtimeR.getSelectLimit());
			dtime.setSterm(dtimeR.getSterm());
			dtime.setStuSelect((short)(Short.parseShort(countSelect(dtimeOid).toString())+Short.parseShort(countFilterSelect(dtimeOid).toString())));
			dtime.setTechid(dtimeR.getTechid());
			dtime.setThour(dtimeR.getThour());
			dtime.setCoansw(0f);
			// 還原開課記錄
			try{
				courseDao.saveObject(dtime);
			}catch(Exception e){
				continue;
			}

			String hqlTmp="FROM SeldStuFilter WHERE dtimeOid='"+dtimeOid+"'";
			List seldTmp=courseDao.StandardHqlQuery(hqlTmp);
			SeldStuFilter seldR;
			for(int j=0; j<seldTmp.size(); j++){
				seldR=(SeldStuFilter) seldTmp.get(j);
				Seld seld=new Seld();

				if(dtimeR.getType().equals("F")){
					seld.setDtimeOid(seldR.getDtimeOid());
				}else{
					seld.setDtimeOid(dtime.getOid());
				}

				seld.setDtimeOid(dtime.getOid());


				seld.setStudentNo(seldR.getStudentNo());
				seld.setCscode(dtimeR.getCscode());
				seld.setDepartClass(dtime.getDepartClass());
				seld.setOpt(dtime.getOpt());
				seld.setCredit(dtime.getCredit());
				// 還原選課記錄
				courseDao.saveObject(seld);
			}
			// 刪除篩選課程
			jdbcDao.StandardSqlRemove("DELETE FROM Seld_couFilter WHERE Dtime_oid='"+dtimeOid+"'");
			// 刪除篩選學生
			jdbcDao.StandardSqlRemove("DELETE FROM Seld_stuFilter WHERE Dtime_oid='"+dtimeOid+"'");
		}

	}

	/**
	 * 實作取得教師任教課程
	 */
	public List getDtimeBy(String techId, String sterm) {
		String sql="SELECT d.Oid, d.cscode, c.chi_name, cl.ClassName FROM Dtime d, Csno c, Class cl WHERE  cl.ClassNo=d.depart_class AND "+
				   "d.cscode=c.cscode AND "+
				   "d.Sterm='"+sterm+"' AND "+
				   "d.techid='"+techId+"'";

		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 取得申論題型
	 */
	// 期中評量專用部份
	public Map getQuestColumn(String type){
		String sql="SELECT * FROM CoQuestion WHERE type='"+type+"'";
		Map map;

		List tmp=jdbcDao.StandardSqlQuery(sql);
		List intColumn = new ArrayList();
		List txtColumn=new ArrayList();

		for(int i=0; i<tmp.size(); i++){

			if(((Map)tmp.get(i)).get("textValue").equals("Y")){

				txtColumn.add( ((Map)tmp.get(i)) );
			}else{

				intColumn.add( ((Map)tmp.get(i)) );
			}
		}

		map=new HashMap();
		map.put("intColumn", intColumn);
		map.put("txtColumn", txtColumn);

		return map;
	}

	public List getQuestCount(String type) {

		return jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='"+type+"' AND value IS NOT NULL GROUP BY value");
	}

	public List getCoanswBy(String DtimeOid) {

		return jdbcDao.StandardSqlQuery("SELECT answer, Dtime_oid FROM Coansw WHERE Dtime_oid='"+DtimeOid+"'");
	}

	public List getRat(){
		return jdbcDao.StandardSqlQuery("SELECT value FROM CoQuestion WHERE value IS NOT NULL GROUP BY value");
	}

	/**
	 * 教學評量 (課程/分數) 平均值清單
	 */
	// TODO 改寫中
	public List getCoansw4TechBy(String techid) {

		

		return null;
	}

	/**
	 * 取得教師任教的體育課程
	 */
	public List getSport(String techid, String sterm) {
		String sql="SELECT d.Oid FROM "+
				   "Csno c, Dtime d "+
				   "WHERE "+
				   "c.chi_name LIKE '%體育%' AND "+
				   "d.cscode=c.cscode AND "+
				   "d.Sterm='"+sterm+"' AND "+
				   "d.techid='"+techid+"'";
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * 取得評分清單
	 */
	public List myStudents(List myDtime) {

		Map myMap;
		List myStudents=new ArrayList();
		for(int i=0; i<myDtime.size(); i++){
			// 取得學生資訊
			String sql="SELECT s.student_no, s.Oid, s.score, s.score1, s.score2, s.score3, c.ClassName, st.student_name " +
					   "FROM Seld s, Class c, stmd st " +
					   "WHERE s.student_no=st.student_no AND st.depart_class=c.ClassNo AND s.Dtime_oid='"+((Map)myDtime.get(i)).get("Oid")+"' ORDER BY s.student_no";

			// 取得課程資訊
			List myCs=jdbcDao.StandardSqlQuery("SELECT cl.ClassName, c.chi_name FROM Dtime d, Csno c, Class cl " +
										   	   "WHERE d.cscode=c.cscode AND d.depart_class=cl.ClassNo " +
										   	   "AND d.Oid='"+((Map)myDtime.get(i)).get("Oid")+"'");
			myMap=new HashMap();

			myMap.put("dtimeOid", ((Map)myDtime.get(i)).get("Oid"));
			myMap.put("ClassName", ((Map)myCs.get(0)).get("ClassName"));
			myMap.put("chi_name", ((Map)myCs.get(0)).get("chi_name"));
			myMap.put("students", jdbcDao.StandardSqlQuery(sql));

			myStudents.add(myMap);
		}
		return myStudents;
	}

	public void updateObject(Object obj) {
		courseDao.saveObject(obj);
	}

	public List getSeld4SportBy(String dtimeOid) {
		return jdbcDao.
		StandardSqlQuery("SELECT s.student_no, s.Oid, s.score, s.score1, s.score2, s.score3, c.ClassName, "+
		"st.student_name FROM Seld s, Class c, stmd st WHERE s.student_no=st.student_no AND "+
		"st.depart_class=c.ClassNo AND s.Dtime_oid='"+dtimeOid+"' ORDER BY st.student_no");
	}

	public Map getdtimeBy(String dtimeOid) {
		
		return ezGetMap("SELECT d.depart_class, d.Oid, d.opt, d.thour, d.credit, cl.ClassName, c.chi_name, e.cname FROM "
				+ "Csno c, Class cl, Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE "
				+ "d.cscode=c.cscode AND cl.ClassNo=d.depart_class AND d.Oid="+dtimeOid);
	}

	public List getFiltDep(List list) {		
		StringBuilder sb=new StringBuilder("SELECT st.depart_class, c.ClassName FROM Seld_stuFilter s, stmd st, Class c " +
			"WHERE s.student_no=st.student_no AND st.depart_class=c.ClassNo AND s.Dtime_oid IN(");
		for(int i=0; i<list.size(); i++){
			sb.append("'"+((Map)list.get(i)).get("dtimeOid")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());		
		sb.append(")GROUP BY st.depart_class ORDER BY st.depart_class");		
		return jdbcDao.StandardSqlQuery(sb.toString());
	}

	public List getFiltStu(String departClass) {		
		return jdbcDao.StandardSqlQuery(
		"SELECT c.ClassName, st.student_no, st.student_name, cs.chi_name, s.type " +
		"FROM Seld_stuFilter s, stmd st, Class c, Seld_couFilter d, Csno cs " +
		"WHERE s.student_no=st.student_no AND st.depart_class=c.ClassNo " +
		"AND d.Dtime_oid=s.Dtime_oid AND cs.cscode=d.cscode " +
		"AND c.ClassNo='"+departClass+"' ORDER BY st.student_no"
		);
	}

	public Integer countCoansw(String dtimeOid) {
		return jdbcDao.StandardSqlQueryForInt("SELECT COUNT(*) FROM Coansw WHERE Dtime_oid='"+dtimeOid+"'");
	}


	/**
	 * 教學評量 (課程/分數) 平均值清單 for 教務處人員
	 */
	public List getCoansw4Empl(String techid, String classNo, String term) {		
		//int schoolTerm=getSchoolTerm();
		List list=jdbcDao.StandardSqlQuery("SELECT d.Oid, d.techid, d.elearning, e.cname, cs.chi_name, cl.ClassName, cs.cscode, cl.ClassNo, d.elearning " +
		"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno cs, Class cl WHERE  d.cscode NOT IN('50000', 'T0001', 'T0002') AND " +
		"cs.cscode=d.cscode AND cl.ClassNo=d.depart_class AND d.Sterm='" +term+"' AND "+
		"d.depart_class LIKE '"+classNo+"%' AND d.techid LIKE '"+techid+"%' ORDER BY d.depart_class, e.idno");		
		List all=new ArrayList();
		List no;
		List el;
		
		for(int i=0; i< list.size(); i++){			
			if(((Map)list.get(i)).get("elearning").equals("0")){
				no=getCoans(jdbcDao.StandardSqlQuery("SELECT cs.chi_name, d.Oid, c.ClassName, c.ClassNo, cs.cscode, d.techid FROM Dtime d, Class c, Csno cs " +
				"WHERE d.techid LIKE'"+((Map)list.get(i)).get("techid")+"%' AND d.elearning='0' AND d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
				"Sterm='"+term+"' AND d.depart_class ='"+((Map)list.get(i)).get("ClassNo")+"' AND d.Oid="+((Map)list.get(i)).get("Oid")), "0");
				all.addAll(no);
			}else{				
				el=getCoans(jdbcDao.StandardSqlQuery("SELECT cs.chi_name, d.Oid, c.ClassName, c.ClassNo, cs.cscode, d.techid FROM Dtime d, Class c, Csno cs " +
				//"WHERE d.techid LIKE'"+((Map)list.get(i)).get("techid")+"%' AND (d.elearning='1' OR d.elearning='2' OR d.elearning='3') AND " +
				"WHERE d.techid LIKE'"+((Map)list.get(i)).get("techid")+"%' AND d.elearning!='0' AND " +
				"d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
				"Sterm='"+term+"' AND d.depart_class ='"+((Map)list.get(i)).get("ClassNo")+"'AND d.Oid="+((Map)list.get(i)).get("Oid")), "1");
				all.addAll(el);
			}
		}
		no=null;
		el=null;
		return all;
	}	
	
	/**
	 * 不及格統計
	 */
	public List geTtriposBy(String departClass, String sterm) {

		List list=jdbcDao.StandardSqlQuery("SELECT d.cscode, d.depart_class, d.Oid, c.ClassName, cs.chi_name, e.cname " +
		"FROM Dtime d, Csno cs, Class c, empl e WHERE " +
		"d.cscode=cs.cscode AND d.depart_class=c.ClassNo AND d.techid=e.idno AND " +
		"d.Sterm='"+sterm+"' AND d.depart_class LIKE '"+departClass+"%' AND d.cscode !='50000'");
		Map map;
		List table=new ArrayList();
		float tmp;
		int total;
		int pass=60;
		for(int i=0; i<list.size(); i++){
			
			total=countSelect(((Map)list.get(i)).get("Oid").toString());
			
			if(total==0){
				continue;
			}
			map=new HashMap();
			map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
			map.put("cscode", ((Map)list.get(i)).get("cscode"));
			map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
			map.put("cname", ((Map)list.get(i)).get("cname"));map.put("stuSelect", total);
			
			map.put("midNon", ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score2 IS NULL"));
			tmp=ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score2<"+pass);			
			map.put("midCount", tmp);
			try{
				map.put("midavg", roundOff((tmp/total)*100, 2));
			}catch(Exception e){
				map.put("midavg", 0);
			}
			
			map.put("endNon", ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score3 IS NULL"));
			tmp=ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score3<"+pass);
			map.put("endCount", tmp);			
			try{
				map.put("endavg", roundOff((tmp/total)*100, 2));
			}catch(Exception e){
				map.put("endavg", 0);
			}
			
			map.put("scoreNon", ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score IS NULL"));
			tmp=ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"' AND score<"+pass);
			map.put("scoreCount", tmp);			
			try{
				map.put("scoreavg", roundOff((tmp/total)*100, 2));
			}catch(Exception e){
				map.put("scoreavg", 0);
			}
			
			table.add(map);
		}

		return table;
	}

	/**
	 * 取得暑修評分清單
	 */
	public List mySunStudents(List mySdtime) {

		Map myMap;
		List myStudents=new ArrayList();

		for(int i=0; i<mySdtime.size(); i++){
			// 取得學生資訊
			String sql="SELECT s.*, c.ClassName, st.student_name " +
					   "FROM Sseld s, Class c, stmd st " +
					   "WHERE s.student_no=st.student_no AND st.depart_class=c.ClassNo AND s.csdepart_class='"+((Map)mySdtime.get(i)).get("depart_class")+"' " +
					   "AND s.cscode='"+((Map)mySdtime.get(i)).get("cscode")+"' ORDER BY s.student_no";

			// 取得課程資訊
			List myCs=jdbcDao.StandardSqlQuery("SELECT cl.name, c.chi_name, d.depart_class, d.cscode, d.Oid FROM Sdtime d, Csno c, Sabbr cl " +
										   	   "WHERE d.cscode=c.cscode AND d.depart_class=cl.no " +
										   	   "AND d.depart_class='"+((Map)mySdtime.get(i)).get("depart_class")+"' AND d.cscode='"+((Map)mySdtime.get(i)).get("cscode")+"'");

			myMap=new HashMap();
			// myMap.put("dtimeOid", ((Map)mySdtime.get(i)).get("Oid"));
			myMap.put("dtimeOid", ((Map)myCs.get(0)).get("Oid"));
			myMap.put("depart_class", ((Map)mySdtime.get(i)).get("depart_class"));
			myMap.put("cscode", ((Map)mySdtime.get(i)).get("cscode"));
			myMap.put("ClassName", ((Map)myCs.get(0)).get("name"));
			myMap.put("chi_name", ((Map)myCs.get(0)).get("chi_name"));
			myMap.put("students", jdbcDao.StandardSqlQuery(sql));

			myStudents.add(myMap);
		}

		return myStudents;
	}

	/**
	 * 以學號查詢選課資料
	 * @param studentNo 學號
	 * @param term 學期
	 * @return java.util.List List of Seld objects
	 */
	public List<Seld> findSeldByStudentNoAndTerm(String studentNo, String term) {
		return courseDao.findSeldByStudentNoAndTerm(studentNo, term);
	}

	public void executeSql(String sql){
		jdbcDao.executeSql(sql);
	}

	public List getStudentInfo(String type, Map stForm) {
		StringBuffer strbuf=new StringBuffer("SELECT ClassName, name, student_no, student_name, "+type+".Oid " +
				"FROM ("+type+" LEFT OUTER JOIN code5 ON "+type+".occur_status=code5.idno AND code5.category='Status')" +
				"LEFT OUTER JOIN Class ON Class.ClassNo="+type+".depart_class ");

		StringBuffer strbuf1=new StringBuffer();
		if(!stForm.get("sex").equals("")){
			strbuf1.append("sex ='"+stForm.get("sex")+"' AND ");
		}

		if(!stForm.get("studentName").equals("")){
			strbuf1.append("student_name LIKE '%"+stForm.get("studentName")+"%' AND ");
		}
		if(!stForm.get("studentNo").equals("")){
			strbuf1.append("student_no LIKE '"+stForm.get("studentNo")+"%' AND ");
		}
		if(!stForm.get("classNo").equals("")){
			strbuf1.append("depart_class LIKE '"+stForm.get("classNo")+"%' AND ");
		}
		if(!stForm.get("idno").equals("")){
			strbuf1.append(type+".idno LIKE '"+stForm.get("idno")+"%' AND ");
		}
		// 各種狀態
		if(!stForm.get("occur_graduate_no").equals("")){
			strbuf1.append("occur_graduate_no LIKE '"+stForm.get("occur_graduate_no")+"%' AND ");
		}
		if(!stForm.get("occur_status").equals("")){
			strbuf1.append("occur_status = '"+stForm.get("occur_status")+"' AND "); // 變更狀態
		}
		if(!stForm.get("occur_date").equals("")){
			strbuf1.append("occur_date LIKE '"+stForm.get("occur_date")+"%' AND "); // 變更日期
		}
		if(!stForm.get("occur_cause").equals("")){
			strbuf1.append("occur_cause = '"+stForm.get("occur_cause")+"' AND "); // 變更原因
		}
		if(!stForm.get("occur_year").equals("")){
			strbuf1.append("occur_year = '"+stForm.get("occur_year")+"' AND "); // 變更學年
		}
		if(!stForm.get("occur_term").equals("")){
			strbuf1.append("occur_term = '"+stForm.get("occur_term")+"' AND "); // 變更學期
		}
		// 電話
		if(!stForm.get("telephone").equals("")){
			strbuf1.append("telephone LIKE '"+stForm.get("telephone")+"%' AND "); // 連絡電話
		}
		if(!stForm.get("CellPhone").equals("")){
			strbuf1.append("CellPhone LIKE '"+stForm.get("CellPhone")+"%' AND ");
		}
		// 個人資訊
		if(!stForm.get("birthday").equals("")){
			strbuf1.append("birthday LIKE '"+stForm.get("birthday")+"%' AND ");
		}
		if(!stForm.get("entrance").equals("")){
			strbuf1.append("entrance LIKE '"+stForm.get("entrance")+"%' AND ");
		}
		if(!stForm.get("gradyear").equals("")){
			strbuf1.append("gradyear LIKE '"+stForm.get("gradyear")+"%' AND ");
		}
		if(!stForm.get("ident").equals("")){
			strbuf1.append("ident LIKE '"+stForm.get("ident")+"%' AND ");
		}
		if(!stForm.get("divi").equals("")){
			strbuf1.append("divi LIKE '"+stForm.get("divi")+"%' AND ");
		}
		if(!stForm.get("birth_province").equals("")){
			strbuf1.append("birth_province LIKE '"+stForm.get("birth_province")+"%' AND ");
		}
		if(!stForm.get("birth_county").equals("")){
			strbuf1.append("birth_county LIKE '"+stForm.get("birth_county")+"%' AND ");
		}
		if(!stForm.get("parent_name").equals("")){
			strbuf1.append("parent_name LIKE '%"+stForm.get("parent_name")+"%' AND ");
		}
		if(!stForm.get("gradu_status").equals("")){
			strbuf1.append("gradu_status LIKE '"+stForm.get("gradu_status")+"%' AND ");
		}

		// 畢業學校 Start
		if(stForm.get("schl_code").equals("")&&!stForm.get("schl_name").equals("")){
			strbuf1.append("schl_name LIKE '%"+stForm.get("schl_name")+"%' AND ");
		}
		if(!stForm.get("schl_code").equals("")&&stForm.get("schl_name").equals("")){
			strbuf1.append("schl_code LIKE '"+stForm.get("schl_code")+"%' AND ");
		}
		if(!stForm.get("schl_code").equals("")&&!stForm.get("schl_name").equals("")){
			strbuf1.append("(schl_code LIKE '"+stForm.get("schl_code")+"%' OR ");
			strbuf1.append("schl_name LIKE '%"+stForm.get("schl_name")+"%') AND ");
		}

		
		if(!stForm.get("ExtraStatus").equals("")){
			strbuf1.append("ExtraStatus LIKE '"+stForm.get("ExtraStatus")+"%' AND ");
		}
		if(!stForm.get("ExtraDept").equals("")){
			strbuf1.append("ExtraDept LIKE '%"+stForm.get("ExtraDept")+"%' AND ");
		}
		

		// 住址
		if(!stForm.get("curr_post").equals("")){
			strbuf1.append("curr_post LIKE '%"+stForm.get("curr_post")+"%' AND ");
		}
		if(!stForm.get("curr_addr").equals("")){
			strbuf1.append("curr_addr LIKE '%"+stForm.get("curr_addr")+"%' AND ");
		}
		if(!stForm.get("perm_post").equals("")){
			strbuf1.append("perm_post LIKE '%"+stForm.get("perm_post")+"%' AND ");
		}
		if(!stForm.get("perm_addr").equals("")){
			strbuf1.append("perm_addr LIKE '%"+stForm.get("perm_addr")+"%' AND ");
		}

		if(strbuf1.length()>0){
			strbuf.append("WHERE ");
			strbuf.append(strbuf1);
			strbuf.delete(strbuf.length()-4, strbuf.length());
			strbuf.append("ORDER BY depart_class, student_no");
		}
		return jdbcDao.StandardSqlQuery(strbuf.toString());
	}

	public List getGmark(String studentNo){
		return jdbcDao.StandardSqlQuery("SELECT g.*, c1.name as occur_status_name " +
		"FROM (Gmark g LEFT OUTER JOIN code5 c1 ON c1.category='Status' AND c1.idno=g.occur_status) " +
		"LEFT OUTER JOIN code5 c ON c.idno=g.occur_cause AND c.category='Cause' WHERE student_no='"+studentNo+"'");
	}

	public Integer getSchoolYear(){

		return jdbcDao.getJdbcTemplate().queryForInt("SELECT Value FROM Parameter WHERE name='School_year'");
	}

	public Integer getSchoolTerm(){

		return jdbcDao.getJdbcTemplate().queryForInt("SELECT Value FROM Parameter WHERE name='School_term'");
	}

	/**
	 * 新增加退選紀錄
	 * @param dtimeOid Dtime Oid
	 * @param studentNo Student No
	 * @param idno Employee IDNO
	 */
	public void txSaveAdcdHistory(Integer dtimeOid, String studentNo,
			String idno, String adddraw) {
		AdcdHistory ah = new AdcdHistory();
		ah.setDtimeOid(dtimeOid);
		ah.setStudentNo(studentNo);
		ah.setAdddraw(adddraw);
		ah.setModified(idno);
		ah.setLastModified(new Date());
		courseDao.saveObject(ah);
	}

	/**
	 * SQL取1筆資料
	 * @param sql
	 */
	public Map ezGetMap(String sql) {
		try{
			return jdbcDao.ezGetMap(sql);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * SQL取1個字段
	 * @param sql
	 */
	public String ezGetString(String sql) {
		return jdbcDao.ezGetString(sql);
	}

	/**
	 * 西元民國隨便轉	 * 
	 * @param someday  西元年或民國年隨便1天 '/', '-' 都可接受
	 * @return 傳西元進來丟民國, 傳民國進來丟西元 ex: in 96/11/5 out 2007/11/5; in 2007-11-5 out 96-11-5
	 */
	public String convertDate (String someday){
	    SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat d2 = new SimpleDateFormat("/MM/dd");
	    Calendar cal = Calendar.getInstance();
	    
	    StringBuilder sb=new StringBuilder(someday);
	    for(int i=0; i<sb.length(); i++){
	    	
	    	try{
	    	      Integer.parseInt(sb.charAt(i)+"");	    	      
	    	    }catch(NumberFormatException e){
	    	      sb.replace(i, i+1, "-");
	    	    }
	    }
	    
	    someday=sb.toString();
	    try {
	    	try{
	    		cal.setTime(d1.parse(someday));
	    	}catch(Exception e){
	    		d1 = new SimpleDateFormat("yyyy-MM-dd");
	    	    d2 = new SimpleDateFormat("-MM-dd");
	    	   // cal = Calendar.getInstance();
	    	    cal.setTime(d1.parse(someday));
	    	}
	    	if(cal.get(Calendar.YEAR)>1492){
	    	  cal.add(Calendar.YEAR,-1911);
	    	}else{
	    	cal.add(Calendar.YEAR,+1911);
	      }
	      return Integer.toString(cal.get(Calendar.YEAR))+d2.format(cal.getTime());
	    } catch (Exception e) {
	      // e.printStackTrace();
	      return "日期格式有誤";
	    }
	}
	    

	/**
	 * 隨便取一個數值
	 */
	public Integer ezGetInt(String sql){
		return jdbcDao.StandardSqlQueryForInt(sql);
	}
	

	/**
	 * 取得導師評量
	 * 2010/1/1增加導師評量
	 */
	public void getTutorQuestion(HttpSession session, UserCredential user){				
		List nList=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4tutor WHERE type='M' ORDER BY sequence");// 導師題型
		List nDisplay=new ArrayList();
		Map map;		
		//若沒做過才裝
		if(ezGetInt("SELECT COUNT(*)FROM Coansw4tutor WHERE student_no='"+user.getMember().getAccount()+"'")<1){
			for(int i=0; i<nList.size(); i++){
				map=new HashMap();
				map.put("Oid", ((Map)nList.get(i)).get("Oid") );
				map.put("type", ((Map)nList.get(i)).get("type") );
				map.put("options", ((Map)nList.get(i)).get("options") );
				map.put("value", ((Map)nList.get(i)).get("value") );
				map.put("subPlay", jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4tutor WHERE parentOid='"+((Map)nList.get(i)).get("Oid")+"' ORDER BY sequence"));
				nDisplay.add(map);
			}
			session.setAttribute("tutorQuestion", nDisplay);
			try{
				Map totur=ezGetMap("SELeCT e.cname, c.ClassName FROM ClassInCharge cc, empl e, Class c " +
						"WHERE c.ClassNo=cc.ClassNo AND e.Oid=cc.EmpOid ANd cc.ModuleOids LIKE '|86|%' AND c.ClassNo='"+user.getStudent().getDepartClass()+"' LIMIT 1");
				session.setAttribute("myTotur", totur);
				session.setAttribute("openCoans", true);
			}catch(Exception e){
				
			}			
		}else{
			session.removeAttribute("tutorQuestion");
			session.removeAttribute("myTotur");
		}
	}

	/**
	 * 教學評量(沒有教師的課程將不列表)
	 * 2010/4/28非"遠距"即為一般課程
	 * 學生選課低於2門不阻擋2009/12/28
	 * 2010/5/11遠距、輔助要使用遠距問卷
	 * 2011/11/1新增自我評量
	 */
	
	public void setCoansFoRm(Date date, HttpSession session, UserCredential user, Date coanswStart, Date coanswEnd){
		//System.out.println(user.getMember().getPriority());
		if(!user.getMember().getPriority().equals("C")){
			return;
		}
		String studentNo=user.getStudent().getStudentNo();
		String student_no=user.getStudent().getStudentNo();	
		
		//不在時間範圍內
		if(!(date.getTime()>=coanswStart.getTime() && date.getTime()<=coanswEnd.getTime())){
			return;
		}
		
		//選多少門課(應做自我評量題數與實做題數)
		int s=ezGetInt("SELECT COUNT(*)FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND s.student_no='"+student_no+"' " +
			"AND Sterm='"+getSchoolTerm()+"' AND (d.techid<>'' || d.techid is not null)");
		
		if(s>0){//有選課的人才做
			int x=ezGetInt("SELECT COUNT(*)FROM Coansw WHERE student_no='"+studentNo+"'");//教學評量已做題數			
			//int y=ezGetInt("SELECT COUNT(*)FROM Coansw4self WHERE student_no='"+studentNo+"'");//自評已做題數
			
			//判斷是否有做:教學評量x, 自評y, 導師評ezGetInt
			//if((x<s || y<s || ezGetInt("SELECT COUNT(*)FROM Coansw4tutor WHERE student_no='"+student_no+"'")<1)){
			if((x<s || ezGetInt("SELECT COUNT(*)FROM Coansw4tutor WHERE student_no='"+student_no+"'")<1)){	
				//找尋教學評量
				StringBuilder sb=new StringBuilder("SELECT d.Oid, d.opt, d.credit, cs.chi_name, cl.ClassName, e.cname, d.elearning FROM " +
				"Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Seld s, Csno cs, Class cl WHERE " +
				"s.Dtime_oid=d.Oid AND cs.cscode=d.cscode AND cl.ClassNo=d.depart_class AND " +
				"s.student_no='"+student_no+"' AND d.Sterm='"+getSchoolTerm()+"' ");
				
				// 做過的課程要過濾
				if(x>0){
					sb.append("AND d.Oid NOT IN(");
					sb.append("SELECT Dtime_oid FROM Coansw WHERE student_no='"+studentNo+"')");
				}
				
				List list=jdbcDao.StandardSqlQuery(sb.toString());
				
				List myQuestion=new ArrayList();
				Map map;
				for(int i=0; i<list.size(); i++){
					map=new HashMap();
					map.put("dtimeOid", ((Map)list.get(i)).get("Oid"));
					map.put("opt", Global.CourseOpt.getProperty(((Map)list.get(i)).get("opt").toString()));
					map.put("credit", ((Map)list.get(i)).get("credit"));
					map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
					map.put("cname", ((Map)list.get(i)).get("cname"));				
					if(((Map)list.get(i)).get("elearning").equals("1")||((Map)list.get(i)).get("elearning").equals("2")){//遠距或輔助					
						map.put("question", getElearningQuestion());//提供遠距問卷
					}else{
						map.put("question", getNormalQuestion());//提供一般遠距問卷
					}
					myQuestion.add(map);
				}
				
				
				//追加自評
				//sb=new StringBuilder("SELECT d.Oid, d.opt, d.credit, cs.chi_name, cl.ClassName, e.cname, d.elearning FROM " +
						//"Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Seld s, Csno cs, Class cl WHERE " +
						//"s.Dtime_oid=d.Oid AND cs.cscode=d.cscode AND cl.ClassNo=d.depart_class AND " +
						//"s.student_no='"+student_no+"' AND d.Sterm='"+getSchoolTerm()+"' ");
						// 做過的課程要過濾
				//if(y>0){
					//sb.append("AND d.Oid NOT IN(");
					//sb.append("SELECT Dtime_oid FROM Coansw4self WHERE student_no='"+studentNo+"')");
				//}
				
				//list=jdbcDao.StandardSqlQuery(sb.toString());
				
				//List mySeQuestion=new ArrayList();
				/*
				for(int i=0; i<list.size(); i++){
					map=new HashMap();
					map.put("dtimeOid", ((Map)list.get(i)).get("Oid"));
					map.put("opt", Global.CourseOpt.getProperty(((Map)list.get(i)).get("opt").toString()));
					map.put("credit", ((Map)list.get(i)).get("credit"));
					map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
					map.put("cname", ((Map)list.get(i)).get("cname"));				
					if(((Map)list.get(i)).get("elearning").equals("1")||((Map)list.get(i)).get("elearning").equals("2")){//遠距或輔助		
						map.put("question_se", getElearningSelfQuestion());//提供遠距自評問卷
					}else{
						map.put("question_se", getNormalSelfQuestion());//提供一般自評問卷
					}
					mySeQuestion.add(map);
				}
				*/
				
				session.setAttribute("myQuestion", myQuestion);
				//session.setAttribute("mySeQuestion", mySeQuestion);
				session.setAttribute("openCoans", true);
				getTutorQuestion(session, user);//也判斷導師評量
			}else{
				session.removeAttribute("myQuestion");
				session.removeAttribute("mySeQuestion");
				session.removeAttribute("openCoans");
			}
		}
	}
	  

	/**
	 * 取得一般課程題目
	 */
	private List getNormalQuestion(){
		List nList=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND textValue='0' ORDER BY sequence");// 一般課程
		List nDisplay=new ArrayList();
		Map map;
		for(int i=0; i<nList.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)nList.get(i)).get("Oid") );
			map.put("type", ((Map)nList.get(i)).get("type") );
			map.put("options", ((Map)nList.get(i)).get("options") );
			map.put("value", ((Map)nList.get(i)).get("value") );
			map.put("subPlay", jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE parentOid='"+((Map)nList.get(i)).get("Oid")+"' ORDER BY sequence"));
			nDisplay.add(map);
		}
		return nDisplay;
	}	

	/**
	 * 取得遠距程題目
	 */
	private List getElearningQuestion(){
		List eList=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND textValue='1' ORDER BY sequence");// 一般課程
		List nDisplay=new ArrayList();
		Map map;
		for(int i=0; i<eList.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)eList.get(i)).get("Oid") );
			map.put("type", ((Map)eList.get(i)).get("type") );
			map.put("options", ((Map)eList.get(i)).get("options") );
			map.put("value", ((Map)eList.get(i)).get("value") );
			map.put("subPlay", jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE parentOid='"+((Map)eList.get(i)).get("Oid")+"' ORDER BY sequence"));
			nDisplay.add(map);
		}
		return nDisplay;
	}
	

	/**
	 * 取得一般課程自評題目
	 */
	private List getNormalSelfQuestion(){
		List nList=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4self WHERE type='M' AND textValue='0' ORDER BY sequence");// 一般課程
		List nDisplay=new ArrayList();
		Map map;
		for(int i=0; i<nList.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)nList.get(i)).get("Oid") );
			map.put("type", ((Map)nList.get(i)).get("type") );
			map.put("options", ((Map)nList.get(i)).get("options") );
			map.put("value", ((Map)nList.get(i)).get("value") );
			map.put("subPlay", jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4self WHERE parentOid='"+((Map)nList.get(i)).get("Oid")+"' ORDER BY sequence"));
			nDisplay.add(map);
		}
		return nDisplay;
	}	

	/**
	 * 取得遠距課程自評題目
	 */
	private List getElearningSelfQuestion(){
		List eList=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4self WHERE type='M' AND textValue='1' ORDER BY sequence");// 一般課程
		List nDisplay=new ArrayList();
		Map map;
		for(int i=0; i<eList.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)eList.get(i)).get("Oid") );
			map.put("type", ((Map)eList.get(i)).get("type") );
			map.put("options", ((Map)eList.get(i)).get("options") );
			map.put("value", ((Map)eList.get(i)).get("value") );
			map.put("subPlay", jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion4self WHERE parentOid='"+((Map)eList.get(i)).get("Oid")+"' ORDER BY sequence"));
			nDisplay.add(map);
		}
		return nDisplay;
	}
	/**
	 * 取得教學評量細節
	 */
	public List getCoans(List DtimeOid, String elearning){
		NumberFormat nf = NumberFormat.getInstance();
	    nf.setMaximumFractionDigits(1);
	    List coquests;
	    
	    /* 當行政人員修改課程屬性時，不能以課程屬性核對題目
	     * if(elearning.equals("0")){
	    	coquests=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND textValue='0' ORDER BY sequence");
	    }else{
	    	coquests=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND (textValue='1'||textValue='2') ORDER BY sequence");
	    }*/
	    if(DtimeOid.size()>10){
	    	coquests=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND (textValue='1'||textValue='2') ORDER BY sequence");
	    }else{	    	
	    	coquests=jdbcDao.StandardSqlQuery("SELECT * FROM CoQuestion WHERE type='M' AND textValue='0' ORDER BY sequence");
	    }
		
		List list=new ArrayList();
		Map map;
		
		for(int i=0; i<DtimeOid.size(); i++){
			// 要先知道題目長度並做好計算容器
			float queSize[]=new float[coquests.size()];
			List myCoansw=jdbcDao.StandardSqlQuery("SELECT * FROM Coansw WHERE (answer NOT LIKE '%111%') AND Dtime_oid="+((Map)DtimeOid.get(i)).get("Oid"));
			//System.out.println("SELECT * FROM Coansw WHERE answer NOT LIKE '%1%' AND Dtime_oid="+((Map)DtimeOid.get(i)).get("Oid"));
			for(int j=0; j<myCoansw.size(); j++){
				String answer=((Map)myCoansw.get(j)).get("answer").toString();
				// 散裝並加總
				for(int k=0; k<queSize.length; k++){
					//五分制和百分制的關鍵點在此
					queSize[k]=queSize[k]+Integer.parseInt( answer.substring(k, k+1))*20;	
				}
			}
			
			map=new HashMap();
			map.put("ClassName", ((Map)DtimeOid.get(i)).get("ClassName"));
			map.put("chi_name", ((Map)DtimeOid.get(i)).get("chi_name"));
			//map.put("cscode", ((Map)DtimeOid.get(i)).get("cscode"));
			map.put("Oid", ((Map)DtimeOid.get(i)).get("Oid"));
			map.put("techid", ((Map)DtimeOid.get(i)).get("techid"));
			
			Map dtime=ezGetMap("SELECT d.techid, d.opt, d.credit, d.elearning, d.depart_class, d.cscode, e.cname " +
					"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno " +
					"WHERE d.Oid="+((Map)DtimeOid.get(i)).get("Oid"));
			
			map.put("opt", Toolket.getCourseOpt( dtime.get("opt").toString()));
			map.put("credit", dtime.get("credit"));
			map.put("elearning", getElearningName(dtime.get("elearning").toString()));
			
			map.put("cscode", dtime.get("cscode"));
			map.put("depart_class", dtime.get("depart_class"));
			map.put("cname", dtime.get("cname"));
			map.put("sumAns", myCoansw.size());
			
			float total=0f; // 某科總分
			List score=new ArrayList();// 某科的細節
			Map sMap;
			BigDecimal big;
			
			for(int j=0; j<coquests.size(); j++){
				sMap=new HashMap();
				sMap.put("options", ((Map)coquests.get(j)).get("options"));
				
				try{
					big = new BigDecimal(queSize[j]/myCoansw.size());
					sMap.put("score", big.setScale(1,BigDecimal.ROUND_UP));	
				}catch(NumberFormatException e){
					sMap.put("score", "0");
				}
				total=total+getCoanswTotle(queSize[j], myCoansw.size());
				score.add(sMap);
			}
			
			map.put("score", score);
			// TODO 此處能證明是以加分進行運算
			// big = new BigDecimal(total/coquests.size());
			// map.put("total", big.setScale(1,BigDecimal.ROUND_UP));
			total=total/coquests.size();
			map.put("total", roundOff(total, 1));
			
			Map content_per=ezGetMap("SELECT content_per FROM CoanswReview WHERE SchoolYear='"+getSchoolYear()+"' AND " +
					"SchoolTerm='"+getSchoolTerm()+"' AND cscode='"+dtime.get("cscode")+"' AND depart_class='"+dtime.get("depart_class")+"' AND " +
					"techid='"+dtime.get("techid")+"'");
			
			if(content_per!=null){
				map.put("content_per", content_per.get("content_per"));
			}
			
			if(myCoansw.size()>0)
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * 算分數
	 * 
	 * @param dtimeOid
	 * @param quests
	 * @return
	 */
	private Float getCoanswTotle(float queSize, int myCoansw){
		return queSize/myCoansw;
	}
	
	/**
	 * 將float四捨五入至小數第n位
	 */
	public float roundOff(float f, int n){
		try{
			BigDecimal b=new BigDecimal(f);	
			return b.setScale(n,BigDecimal.ROUND_HALF_UP).floatValue();
		}catch(NumberFormatException e){
			return 0;
		}		
	}
	
	/**
	 * 查詢Dtime物件
	 * 
	 * @param d
	 *            tw.edu.chit.model.Dtime Object
	 * @param orderField
	 *            Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Dtime> findDtimeBy(Dtime d, String orderField) {
		return courseDao.getDtimeBy(d, orderField);
	}
	
	/**
	 * 查詢Dtime物件
	 * 
	 * @param d
	 *            tw.edu.chit.model.Dtime Object
	 * @param orderField
	 *            Order Field
	 * @return java.util.List List of tw.edu.chit.model.Dtime Objects
	 */
	public List<Object> findDtimeCsnoBy(Dtime d, String orderField) {
		return courseDao.getDtimeCsnoBy(d, orderField);
	}
	
	/**
	 * 班級衝堂查詢
	 * 
	 * @param departClass
	 *            開課班級
	 * @param week
	 *            星期[]
	 * @param begin
	 *            開始節次[]
	 * @param end
	 *            結束節次[]
	 * @param sterm
	 *            學期
	 * @param dtimeOid
	 *            用以比對的1筆開課
	 * @return 衝堂的結果
	 */
	public List checkReopenClass(String departClass, String week[], String begin[], String end[], String sterm, String dtimeOid){
		
		List checkClassReOpen=new ArrayList();
		List checkTmp;

		for(int i=0; i<week.length; i++){
			if(!week[i].trim().equals("")&& !begin[i].trim().equals("")&& !end[i].trim().equals("")){
				for(int j=Integer.parseInt(begin[i]); j<=Integer.parseInt(end[i]); j++){
					checkTmp=checkReopenClass(departClass, week[i], j+"", sterm, dtimeOid);

					if(checkTmp.size()>0){
						checkClassReOpen.addAll(checkTmp);
					}
				}
			}
		}

		return checkClassReOpen;
	}
	
	/**
	 * 教室衝堂查詢
	 * 
	 * @param place
	 *            教室[]
	 * @param week
	 *            星期[]
	 * @param begin
	 *            開始節次[]
	 * @param end
	 *            結束節次[]
	 * @param sterm
	 *            學期
	 * @param dtimeOid
	 *            用以比對的1筆開課
	 * @return 重複使用的結果
	 */
	public List checkReOpenRoom(String place[], String week[], String begin[],
								 String end[], String sterm, String dtimeOid){
		
		List checkReOpenRoom=new ArrayList();
		List checkTmp;
		for(int i=0; i<week.length; i++){
			if(week[i]!=""&& begin[i]!=""&& end[i]!=""){
				for(int j=Integer.parseInt(begin[i]); j<=Integer.parseInt(end[i]); j++){
					checkTmp=checkReOpenRoom(place[i], week[i], j+"", sterm, dtimeOid);

					if(checkTmp.size()>0){
						checkReOpenRoom.addAll(checkTmp);
					}
				}
			}
		}
		return checkReOpenRoom;
	}
	
	/**
	 * 教師衝堂查詢
	 * 
	 * @param week
	 *            星期[]
	 * @param begin
	 *            開始節次[]
	 * @param end
	 *            結束節次[]
	 * @param sterm
	 *            學期
	 * @param dtimeOid
	 *            用以比對的1筆開課
	 * @return 重複排課的結果
	 */
	public List checkEmplReOpen(String techid, String week[], String begin[],
								 String end[], String sterm, String dtimeOid){
		List checkEmplReOpen=new ArrayList();
		List checkTmp;
		
		for(int i=0; i<week.length; i++){
			
			if(!week[i].trim().equals("")&& !begin[i].trim().equals("")&& !end[i].trim().equals("")){
			
				for(int j=Integer.parseInt(begin[i]); j<=Integer.parseInt(end[i]); j++){
					checkTmp=checkEmplForReOpenCourse(techid, week[i], String.valueOf(j), sterm, dtimeOid);
					if(checkTmp.size()>0){
						checkEmplReOpen.addAll(checkTmp);
					}
				}
			}
		}
		return checkEmplReOpen;
	}
	
	/**
	 * 取得某部制的班 11, 12, 13, 14, 15, 16, 17, 18, 19, 1A...
	 */
	public List getDepartClassByType(String schoolType) {
		Iterator it=jdbcDao.StandardSqlQuery("SELECT idno FroM code5 WHERE category='SchoolType' AND name LIKE'"+schoolType+"%'").iterator();
		
		StringBuilder sb=new StringBuilder("SELECT ClassNo FROM Class WHERE ClassNo LIKE");
		while(it.hasNext()){
			sb.append("'"+((Map)it.next()).get("idno")+"%' OR ClassNo LIKE " );
		}
		sb.delete(sb.length()-16, sb.length());
		// sb.append("GROUP BY ClassNo");
		return jdbcDao.StandardSqlQuery(sb.toString());
	}
	
	/**
	 * 取得所有部制
	 */
	public List getAllSchoolType() {
		/*
		 * Iterator it=ezGetBy("SELECT name as schoolName, sequence as seq, idno
		 * as id FROM code5 WHERE " + "category='SchoolType' GROUP BY
		 * name").iterator(); List allType=new ArrayList(); Map map;
		 * while(it.hasNext()){ map=new HashMap(); map.putAll(((Map)it.next()));
		 * allType.add(map); } return allType;
		 */
		List list=jdbcDao.StandardSqlQuery("SELECT name, idno FROM code5 WHERE " +
		"category='SchoolType' GROUP BY name ORDER BY sequence");
		List allType=new ArrayList();
		Map map;
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			String name=((Map)list.get(i)).get("name").toString();
			map.put("name", name.substring(2, name.length()));
			map.put("idno", ((Map)list.get(i)).get("idno"));
			allType.add(map);
		}
		return allType;
	}
	
	public List getCoansw4Empl(List departClasses, String term) {		
		StringBuilder sb=new StringBuilder("SELECT d.Oid, d.elearning, d.techid, e.cname, cs.chi_name, cl.ClassName, cs.cscode, cl.ClassNo, d.elearning " +
		"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno cs, Class cl WHERE cs.chi_name NOT LIKE'%專題%' AND cs.chi_name NOT LIKE'%論文%' AND "+
		"cs.cscode=d.cscode AND cl.ClassNo=d.depart_class AND d.Sterm='"+term+"' AND d.cscode NOT IN('50000', 'T0001', 'T0002') " +
		"AND d.depart_class IN (");
		for(int i=0; i<departClasses.size(); i++){
			sb.append("'"+((Map)departClasses.get(i)).get("ClassNo")+"', " );
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")ORDER BY d.depart_class, e.idno");
		
		List list=new ArrayList();
		
		try{
			list=jdbcDao.StandardSqlQuery(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List all=new ArrayList();
		for(int i=0; i< list.size(); i++){
			
			if(((Map)list.get(i)).get("elearning").equals("0")){				
				List no=getCoans(jdbcDao.StandardSqlQuery("SELECT cs.chi_name, d.Oid, c.ClassName, c.ClassNo, cs.cscode, d.techid FROM Dtime d, Class c, Csno cs " +
				"WHERE d.techid LIKE'"+((Map)list.get(i)).get("techid")+"%' AND d.elearning='0' AND d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
				"d.Sterm='"+term+"' AND d.depart_class ='"+((Map)list.get(i)).get("ClassNo")+"' AND d.Oid="+((Map)list.get(i)).get("Oid")), "0");
				all.addAll(no);
			}else{
				List el=getCoans(jdbcDao.StandardSqlQuery("SELECT cs.chi_name, d.Oid, c.ClassName, c.ClassNo, cs.cscode, d.techid FROM Dtime d, Class c, Csno cs " +
				"WHERE d.techid LIKE'"+((Map)list.get(i)).get("techid")+"%' AND d.elearning!='0' AND d.depart_class=c.ClassNo AND cs.cscode=d.cscode AND " +
				"d.Sterm='"+term+"' AND d.depart_class ='"+((Map)list.get(i)).get("ClassNo")+"'AND d.Oid="+((Map)list.get(i)).get("Oid")), "1");
				all.addAll(el);
			}			
		}
		
		return all;
	}
	
	/**
	 * 查詢Nabbr物件
	 * 
	 * @param d
	 *            tw.edu.chit.model.Nabbr Object
	 * @return tw.edu.chit.model.Nabbr Object
	 */
	public Nabbr findNabbrBy(Nabbr n) {
		return courseDao.getNabbrBy(n);
	}
	
	/**
	 * 取得某班的詳細資料
	 * 
	 * @param classno
	 * @param type
	 *            area:校區, scho:學制, dept:系, grad:年級, name... TODO 常待擴充
	 */
	public String getClassInfo(String departClass, char type){
		switch (type){
		case 'a':// 取校區
			return "";
			
		case 's':// 取學制
			String schoolType=ezGetString("SELECT name FROM code5 WHERE category='SchoolType' AND " +
					"idno='"+departClass.substring(0, 2)+"'");
			return schoolType.subSequence(2, schoolType.length()).toString();
			
		case 'd':// 取系
			return "";
			
		case 'g':// 取年級
			return"";
			
		case 'n':// 取名稱
			return"";
			
		default:
			return "?";			
		}
	}
	
	/**
	 * 阿拉伯數字轉中文大寫
	 * 
	 * @param input
	 *            阿拉伯文
	 * @param sim
	 *            是否小寫？
	 * @return 中文
	 */
	public String numtochinese(String input, boolean sim){
		String[] hanzi= {"零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖"};
		String[] unit = {"", "拾", "佰", "仟"};
		if(sim){
			hanzi=new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
			unit = new String[]{"", "十", "百", "千"};
		}
		
		int value = 0;
        int exponent = 0;
        boolean hasZero = false;
        
        StringBuilder chineseDigit = new StringBuilder();
        
        value = Integer.parseInt(input);
        exponent = 3;
        int year;
        for(; exponent >= 0; exponent--){
            
            
            int divisor = (int)Math.pow(10, exponent);
            int result = value / divisor;
            if(result != 0){
                chineseDigit.append(hanzi[result]);
                chineseDigit.append(unit[exponent]);
                hasZero = false;
            }else if(result == 0 && !hasZero){
                chineseDigit.append(hanzi[0]);
                hasZero = true;
            }else{
                // do nothing
            }
            value %= divisor;
        }
        if(chineseDigit.substring(chineseDigit.length() - 1).equals("零")){
        	chineseDigit=chineseDigit.deleteCharAt(chineseDigit.length() - 1);
        }
        if(chineseDigit.subSequence(0, 1).equals("零")){
        	chineseDigit.delete(0, 1);
        }
        if(chineseDigit.length()==3 && chineseDigit.subSequence(0, 1).equals("壹")){
        	chineseDigit.delete(0, 1);
        }
        
        year=Integer.parseInt(input);
        
        if(year<=10&&chineseDigit.length()==2 && chineseDigit.subSequence(1, 2).equals("拾")){
        	chineseDigit.delete(0, 1);
        }
        return chineseDigit.toString();
	}
	
	/**
	 * 得到1個長度為length的亂數
	 */
	public int getArandom(int length){
		return new Integer((int)(Math.random()*length));
	}
	
	/**
	 * Adcd中del or add腦費的
	 * 
	 * @param type
	 *            A=加選, D=退選
	 */
	public boolean getExtrapay4Adcd(String studentNO, String sterm, char type) {
		if(ezGetInt("SELECT COUNT(*) FROM AddDelCourseData s, Dtime d " +
				"WHERE s.Dtime_oid=d.Oid AND s.Adddraw='"+type+"' AND d.extrapay<>'0' AND " +
				"d.Sterm='"+sterm+"' AND s.student_no='"+studentNO+"'")>0){
			return true;
		}			
		return false;
	}
	
	/**
	 * Seld中有實習費
	 */
	public boolean getExtrapay4Seld(String studentNO, String sterm) {
		
		if(ezGetInt("SELECT COUNT(*) FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND d.extrapay<>'0' AND " +
				"d.Sterm='"+sterm+"' AND s.student_no='"+studentNO+"'")>0){
			return true;
		}			
		return false;
	}
	
	public List getStudyPath(String ClassNo, int SchoolYear){
		Clazz clazz=(Clazz)hqlGetBy("FROM Clazz WHERE ClassNo LIKE '"+ClassNo+"'").get(0);			
		// 本班每人歷年應修
		
		//Integer schoolYear=Integer.parseInt(getSchoolYear().toString());
		Map map;
		List list=new ArrayList();		
		int grade=Integer.parseInt(clazz.getGrade()); // 現在年級
		Integer tmpGrade=1; // 暫存年級
		//String classes[]=new String[SchoolYear-(SchoolYear-grade)+1];
		int tmp=0;
		for(int i=SchoolYear-grade+1; i<=SchoolYear; i++){
			StringBuffer bClassNo=new StringBuffer(clazz.getClassNo());
			bClassNo.setCharAt(4, tmpGrade.toString().charAt(0));			
			tmpGrade++;
			//classes[tmp]=bClassNo.toString();
			
			map=new HashMap();
			map.put("SchoolYear", i);
			map.put("ClassNo", bClassNo.toString());
			tmp++;
			list.add(map);
		}
		//System.out.println(list.size());
		return list;
	}
	
	/**
	 * 尋找歷年開課
	 * 
	 * @param ClassNo
	 *            某個範圍
	 * @return
	 */
	public List getSaveDtime4One(String ClassNo, List classes, int SchoolYear, boolean filed) {

		// 目前班級
		List allDtimes=new ArrayList();// 應修課程
		String schoolTerm=(getSchoolTerm().toString());
		//System.out.println(classes.size());
		for(int i=0; i<classes.size(); i++){			
			allDtimes.addAll(jdbcDao.StandardSqlQuery("SELECT sd.Oid, sd.cscode, c.chi_name, sd.credit, sd.opt, sd.school_year, sd.school_term " +
			"FROM Savedtime sd, Csno c WHERE c.cscode=sd.cscode AND sd.opt='1' AND sd.cscode NOT IN('50000', 'T0001', '99999') AND " +
			"sd.depart_class='"+((Map)classes.get(i)).get("ClassNo")+"' AND sd.school_year='"+((Map)classes.get(i)).get("SchoolYear")+"' GROUP BY sd.cscode ORDER BY sd.school_year, sd.school_term, sd.cscode"));			
		}		
		
		// 追加本學期開課
		if(!filed){
			List list=jdbcDao.StandardSqlQuery("SELECT sd.Oid, sd.cscode, c.chi_name, sd.credit, sd.opt FROM Dtime sd, Csno c WHERE " +
						"c.cscode=sd.cscode AND sd.opt='1' AND sd.cscode NOT IN('50000', 'T0001', '99999') AND " +
						"sd.depart_class='"+ClassNo+"' AND sd.Sterm='"+schoolTerm+"'");
			Map map;
			for(int i=0; i<list.size(); i++){
				map=new HashMap();
				map.put("Oid",((Map)list.get(i)).get("Oid"));
				map.put("cscode",((Map)list.get(i)).get("cscode"));
				map.put("chi_name",((Map)list.get(i)).get("chi_name"));
				map.put("credit",((Map)list.get(i)).get("credit"));
				map.put("opt",((Map)list.get(i)).get("opt"));
				map.put("school_year", SchoolYear);
				map.put("school_term", schoolTerm);
				map.put("now", true);				
				allDtimes.add(map);
			}	
		}
		
			
		return allDtimes;
	}
	
	/**
	 * 
	 * @param dCscode
	 *            歷年代碼
	 * @param dCredit
	 *            歷年學分
	 * @param dOpt
	 *            歷年選別
	 * @param sCscode
	 *            實得代碼
	 * @param sCredit
	 *            實得學分
	 * @param sOpt
	 *            實得選別
	 * @return
	 */
	private boolean blanceCredit(String dCscode, String dCredit, String dOpt, String sCscode, String sCredit, String sOpt, float score, int pa){
		if(dCscode.equals(sCscode) && dCredit.equals(sCredit) && dOpt.equals(sOpt) && score>=pa){			
			return true;
		}		
		return false;
	}
	
	/**
	 * 利用班及代碼或學號來判斷及格標準
	 * 
	 * @param classNo
	 * @param studentNo
	 * @return 標準
	 */
	public int getPassScore(String classNo, String studentNo){
		int pa=60;
		try{
			if(classNo.subSequence(2, 3).equals("G")){
				pa=70;
			}
		}catch(Exception e){
			if(studentNo.indexOf("G")>-1){
				pa=70;
			}
		}
		return pa;
	}
	
	private float getNotScore(String student_no, String opt){
		try{			
			return Float.parseFloat(ezGetString("SELECT SUM(d.credit) FROM ScoreHist sh, ScoreHistNote sn, Savedtime d " +
					"WHERE sh.Oid=sn.scoreHistOid AND sn.SavedtimeOid=d.Oid AND d.opt='"+opt+"' AND student_no='"+student_no+"'"));
		}catch(Exception e){
			return 0;
		}		
	}
	
	/**
	 * 取得學生應畢業學分
	 * @param StudentNo
	 * @return
	 */
	public int getCreditPa(String StudentNo){
		Map std=jdbcDao.ezGetMap("SELECT s.entrance, c.SchoolNo, c.DeptNo FROM " +
				"stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.student_no='"+StudentNo+"'");
		String entrance=std.get("entrance").toString();
		if(entrance.length()>4){
			entrance=entrance.substring(0, 1);
		}else{
			entrance=entrance.substring(0, 2);
		}
		try{			
			return jdbcDao.StandardSqlQueryForInt("SELECT SUM(opt1+opt2+opt3)FROM StmdGrdeCredit WHERE SchoolNo='"+std.get("SchoolNo")+"' " +
					"AND DeptNo='"+std.get("DeptNo")+"' AND start_year>='"+entrance+"' AND end_year<='"+entrance+"'");
		}catch(Exception e){
			return 0;
		}		
	}
	
	/**
	 * 取得過濾後的歷年應修
	 * 
	 * @param allDtimes 歷年開課清單 - line: 6002
	 * @param classNo 班級範圍(ex 164 or 142 or 空白)
	 * @param studentNo 或學生學號(ex 961g3506 or 空白)
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param filed 系統目前是否已建立歷年, 若已建立歷年則將範圍推至最高學年度
	 * @return
	 */
	public List getMyBadIdea(List allDtimes, String classNo, String studentNo, String schoolYear, String schoolTerm, boolean filed){
		int pa=60;// 設定及格標
		if(classNo.subSequence(2, 3).equals("G")){
			pa=70;
		}
		
		Map map;//結果的分析
		//Map okMap;//裝比對結果
		// 找範圍內的學生
		List studentList=jdbcDao.StandardSqlQuery("SELECT s.depart_class, s.student_no, s.student_name, c.className, s.occur_status, s.occur_graduate_no, " +
				"s.ident_remark FROM stmd s LEFT OUTER JOIN Class c ON c.ClassNo=s.depart_class " +
				"WHERE s.depart_class LIKE '"+classNo+"%' AND s.student_no LIKE'"+studentNo+"%'");		
		List list=new ArrayList(); // 用來裝最後所有資訊的
		List myScoreHist;
		List DtimeMatches=new ArrayList();
		List ScoreMatches=new ArrayList();
		// 以學生為迴圈
		for(int i=0; i<studentList.size(); i++){
			String student_no=((Map)studentList.get(i)).get("student_no").toString();
			//預先得到該生的已折免課程
			int myPa=0;
			float opt1=getNotScore(student_no, "1");
			float opt2=getNotScore(student_no, "2");
			float opt3=getNotScore(student_no, "3");			
			
			map=new HashMap();
			map.put("totalCredit", ezGetInt("SELECT sum(credit) FROM ScoreHist WHERE student_no='"+student_no+"' AND (score>="+pa+" OR score='' OR score is null)"));
			map.put("CreditPa", getCreditPa(student_no));
			//System.out.println(getCreditPa(student_no));
			map.put("pa", pa);// 及格標準
			map.put("trance", (((Map)studentList.get(i)).get("student_no").toString().subSequence(0, 2))); // 第幾級
			map.put("student_no", student_no); // 學號
			map.put("student_name", ((Map)studentList.get(i)).get("student_name")); // 姓名
			map.put("occur_status", ((Map)studentList.get(i)).get("occur_status")); // 狀態
			map.put("occur_graduate_no", ((Map)studentList.get(i)).get("occur_graduate_no")); // 畢業號
			map.put("ident_remark", ((Map)studentList.get(i)).get("ident_remark")); // 備註
			if(filed){
				//System.out.println("SELECT SUM(d.credit) FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND s.student_no='"+student_no+"' AND d.Sterm='"+schoolTerm+"'");
				map.put("thisTerm", ezGetInt("SELECT SUM(d.credit) FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND s.student_no='"+student_no+"' AND d.Sterm='"+schoolTerm+"'"));
			}
			// 找此生的歷史課程成績 - 大於等於及格標、空白、null 都視為pa, 另外..已做註解的代表已折..所以也不列(aND shn.note is null)
			List myDtime=jdbcDao.StandardSqlQuery("SELECT s.stdepart_class as depart_class, shn.SavedtimeOid, shn.ScoreHistOid, shn.note, shn.officeDate, shn.Oid as shnOid, " +
					"s.Oid, s.score, s.cscode, c.chi_name, s.credit, s.opt, s.school_year, s.school_term, evgr_type FROM Csno c, ScoreHist s " +
					"LEFT OUTER JOIN ScoreHistNote shn ON s.Oid=shn.ScoreHistOid WHERE s.opt='1' AND s.cscode=c.cscode" +
					" AND s.student_no='"+student_no+"' AND s.cscode!='99999' AND (s.score>='"+pa+"' OR s.evgr_type='6') AND " +
					"shn.note is null GROUP BY s.cscode ORDER BY s.school_year, s.school_term, s.cscode DESC"); //反向排序取唯一
			
			/** 若本學期成績尚未建檔追加學生本學期選課
			if(!filed){				
				myDtime.addAll(jdbcDao.StandardSqlQuery(
				"SELECT s.Oid, s.score, d.cscode, c.chi_name, d.credit, d.opt, d.depart_class FROM Seld s, Dtime d, Csno c WHERE s.opt='1' AND " +
				"d.Oid=s.Dtime_oid AND s.student_no='"+student_no+"' AND c.cscode=d.cscode AND d.cscode!='99999' AND d.Sterm='"+schoolTerm+"'"));
			}
			*/
			
			// 用此生應修的課程做迴圈比對他的歷史
			List dtimes=new ArrayList();
			List allDtimeSave=new ArrayList(); // 留一份歷年應修下來有用
			
			for(int j=0; j<allDtimes.size(); j++){
				allDtimeSave.addAll(allDtimes);
				// System.out.println(allDtimes.get(j));
				// 若 ScoreHistNote 沒做過記錄的allDtimes，就是要比對的標的				
				if(ezGetInt("SELECT COUNT(*) FROM ScoreHist sh, ScoreHistNote shn WHERE shn.ScoreHistOid=sh.Oid AND " +
						"sh.student_no='"+((Map)studentList.get(i)).get("student_no")+"' AND " +
								"sh.cscode='"+((Map)allDtimeSave.get(j)).get("cscode")+"'")<1){					
					// 每次要比的基準
					String cscode=""; //課代
					String opt="";
					String credit="";				
					if(((Map)allDtimeSave.get(j))!=null){						
						cscode=((Map)allDtimeSave.get(j)).get("cscode").toString();
						opt=((Map)allDtimeSave.get(j)).get("opt").toString(); // 選別
						credit=((Map)allDtimeSave.get(j)).get("credit").toString(); // 學分						
					}
					
					// 學生的歷史成績做迴圈比對應修
					for(int k=0; k<myDtime.size(); k++){
						
						// 建立比較標的
						String myCscode="";
						String myOpt="";
						String myCredit="";
						Float myScore=0.0f;
						
						if(((Map)myDtime.get(k))!=null){
							myCscode=((Map)myDtime.get(k)).get("cscode").toString().trim();// 課代
							myOpt=((Map)myDtime.get(k)).get("opt").toString().trim();// 選別
							myCredit=((Map)myDtime.get(k)).get("credit").toString().trim();// 學分
							
							try{
								if(!((Map)myDtime.get(k)).get("evgr_type").equals("6")){// 不為折(因為沒成績)
									myScore=Float.parseFloat(((Map)myDtime.get(k)).get("score").toString());// 成績
								}
							}catch(Exception e){
								// System.out.println(((Map)myDtime.get(k)));
							}							
							
							float score=0;							
							if(((Map)myDtime.get(k)).get("score")!=null)
							score=Float.parseFloat(((Map)myDtime.get(k)).get("score").toString().trim());
							//比對歷史成績和歷史課程標的，並且查看註記
							if(blanceCredit(cscode, credit, opt, myCscode, myCredit, myOpt, score, pa)){
								
								DtimeMatches.add(allDtimeSave.get(j));
								ScoreMatches.add(myDtime.get(k));
								
								myDtime.set(k, null);
								allDtimeSave.set(j, null);
								myPa=myPa+1;// 自動核對數+1
								// 必修加總
								if(opt.equals("1")){
									opt1=opt1+Float.parseFloat(credit);
								}								
							}							
						}
					}
					if(allDtimeSave.get(j)!=null){
						String sql="SELECT COUNT(*) FROM ScoreHistNote shn, ScoreHist sh WHERE " +
						"shn.SCoreHistOid=sh.Oid AND sh.student_no='"+student_no+"' AND shn.SavedtimeOid='"+((Map)allDtimeSave.get(j)).get("Oid")+"'";
						
						if(ezGetInt(sql)==0){
							dtimes.add(allDtimeSave.get(j));
						}
					}
				}else{// 若有做記錄的就是折免
					myPa=myPa+1;
					// TODO 加上他
					
					
				}
			}
			
			List myDtimes=new ArrayList();
				
			for(int j=0; j<myDtime.size(); j++){
				if(myDtime.get(j)!=null){					
					if( ((Map)myDtime.get(j)).get("school_year")==null ){
						((Map)myDtime.get(j)).put("school_year", schoolYear);
						((Map)myDtime.get(j)).put("school_term", schoolTerm);
						((Map)myDtime.get(j)).put("now", true);						
					}
					myDtimes.add(myDtime.get(j));
				}
			}
			
			map.put("noMg", dtimes); // 無法折掉的課
			map.put("myCs", myDtimes); // 無法被折掉的課
			map.put("DtimeMatches", DtimeMatches);
			map.put("ScoreMatches", ScoreMatches);
			
			
			//if(filed){//已建檔
				//map.put("opt1", opt1+ezGetInt("SELECT sum(credit)FROM ScoreHist WHERE student_no='"+student_no+"' AND " +
						//"school_year='"+schoolYear+"' AND school_term='"+schoolTerm+"' AND opt='1' AND score>="+pa));
				//map.put("opt2", opt2+countCredit(student_no, "2", pa, ""));
				//map.put("opt3", opt3+countCredit(student_no, "3", pa, ""));
			//}else{
				map.put("opt1", opt1);			
				map.put("opt11", countCredit(student_no, "1", pa, schoolTerm));			
				map.put("opt2", opt2+countCredit(student_no, "2", pa, ""));
				map.put("opt21", countCredit(student_no, "2", pa, schoolTerm));			
				map.put("opt3", opt3+countCredit(student_no, "3", pa, ""));
				map.put("opt31", countCredit(student_no, "3", pa, schoolTerm));
			//}
			
			map.put("myPa", myPa);
			myScoreHist=ezGetBy("SELECT school_year, school_term, student_no FROM ScoreHist WHERE student_no='"+student_no+"' group by school_year, school_term");			
			for(int j=0; j<myScoreHist.size(); j++){
				((Map)myScoreHist.get(j)).put("score", jdbcDao.StandardSqlQuery("SELECT s.stdepart_class as depart_class, shn.ScoreHistOid, shn.note, shn.Oid as shnOid, s.school_year, " +
						"s.Oid, s.cscode, s.school_term, c.chi_name, s.credit, s.score, c5.name as opt, c51.name as evgr_type " +
						"FROM ScoreHist s LEFT OUTER JOIN ScoreHistNote shn ON s.Oid=shn.ScoreHistOid, Csno c, code5 c5, code5 c51 WHERE c.cscode=s.cscode AND s.opt=c5.idno AND c5.category='CourseOpt' AND " +
						"c51.idno=s.evgr_type AND c51.category='CourseType' AND student_no='"+student_no+"' AND s.cscode!='99999' AND s.school_year='"+((Map)myScoreHist.get(j)).get("school_year")+"' AND " +
						"s.school_term='"+((Map)myScoreHist.get(j)).get("school_term")+"' ORDER BY cscode"
				));							
			}			
			map.put("myScoreHist", myScoreHist);
			
			list.add(map);
		}
		return list;
	}	
	
	/**
	 * 本學期或歷年某選別已得學分
	 * 
	 * @param student_no
	 * @param opt
	 * @param pa
	 * @param schoolTerm
	 * @return
	 */
	private float countCredit(String student_no, String opt, int pa, String schoolTerm){
		// TODO 本學期的課程重複		
		float resoult=0;
		List list;
		try{
			// 歷年
			if(schoolTerm.equals("")){				
				list=ezGetBy("SELECT credit FROM ScoreHist WHERE student_no='"+student_no+"' AND opt='"+opt+"' AND " +
						// "(score>="+pa+" OR score IS NULL OR score='') GROUP BY
						// cscode");//大於及格標 或(空白、null)均屬得到成績
						"(score>="+pa+" OR evgr_type='6') GROUP BY cscode");// 大於及格標
						// 或(空白、null)均屬得到成績
			}else{// 學期中
				list=ezGetBy("SELECT d.credit FROM Seld s, Dtime d WHERE d.Oid=s.Dtime_oid AND d.Sterm='"+schoolTerm+"' AND " +
						"s.student_no='"+student_no+"' AND s.opt='"+opt+"' GROUP BY d.cscode");				
			}
		}catch(Exception e){
			e.printStackTrace();
			return resoult;
		}
		
		for(int i=0; i<list.size(); i++){
			if(((Map)list.get(i)).get("credit")!=null)			
				resoult=resoult+Float.parseFloat(((Map)list.get(i)).get("credit").toString());
		}
		
		return resoult;
	}
	
	
	
	/**
	 * 取得教職員 任何狀態下的
	 */
	public List getEmpl(Map map, String table, String type) {
		
		StringBuilder sb=new StringBuilder("SELECT e.*, c1.name as unitName, " +
		"c2.name as pcodeName, c3.name as categoryName, c4.name as statusName FROM(((("+table+" e " +
		"LEFT OUTER JOIN CodeEmpl c1 ON e.unit=c1.idno AND (c1.category='Unit'OR " +
		"c1.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl c2 ON e.pcode=c2.idno)LEFT OUTER JOIN CodeEmpl c3 ON e.category=c3.idno AND " +
		"c3.category='EmpCategory')LEFT OUTER JOIN CodeEmpl c4 ON c4.category='EmpStatus' AND " +
		"c4.idno=e.Status) WHERE ");		
		
		// System.out.println("type="+type);
		
		if(!type.equals("")){// 若有進階選項
			
			sb.append("e.idno LIKE '"+map.get("idno")+"%' AND ");
			sb.append("e.cname LIKE '"+map.get("cname")+"%' AND ");
			
			if(!map.get("unit").equals("")){sb.append("e.unit = '"+map.get("unit")+"' AND ");}
			// if(!map.get("Director").equals("")){sb.append("e.Director =
			// '"+map.get("Director")+"' AND ");}//兼大頭
			if(!map.get("Director").equals("")){// 兼大頭
				if(map.get("Director").equals("%")){//
					sb.append("(e.Director LIKE '"+map.get("Director")+"%' AND e.Director IS NOT NULL AND e.Director<>'') AND ");
				}else{
					sb.append("e.Director = '"+map.get("Director")+"' AND ");
				}
			}
			
			if(!map.get("Status").equals("")){sb.append("e.Status = '"+map.get("Status")+"' AND ");}
			if(!map.get("Degree").equals("")){sb.append("e.Degree = '"+map.get("Degree")+"' AND ");}
			if(!map.get("category").equals("")){sb.append("e.category = '"+map.get("category")+"' AND ");}
			if(!map.get("sex").equals("")){sb.append("e.sex = '"+map.get("sex")+"' AND ");}
			
			
			
			// if(!map.get("Tutor").equals("")){sb.append("e.Tutor =
			// '"+map.get("Tutor")+"' AND ");}//兼導師
			if(!map.get("Tutor").equals("")){// 兼導師
				if(map.get("Tutor").equals("%")){//
					sb.append("(e.Tutor LIKE '"+map.get("Tutor")+"%' AND e.Tutor IS NOT NULL AND e.Tutor<>'') AND ");
				}else{
					sb.append("e.Tutor = '"+map.get("Tutor")+"' AND ");
				}
			}
			
			if(!map.get("StatusCause").equals("")){sb.append("e.StatusCause = '"+map.get("StatusCause")+"' AND ");}
			
			if(!map.get("ename").equals("")){sb.append("e.ename LIKE '"+map.get("ename")+"%' AND ");}
			if(!map.get("pzip").equals("")){sb.append("e.pzip LIKE '"+map.get("pzip")+"%' AND ");}
			if(!map.get("czip").equals("")){sb.append("e.czip LIKE '"+map.get("czip")+"%' AND ");}
			if(!map.get("sname").equals("")){sb.append("e.sname LIKE '"+map.get("sname")+"%' AND ");}
			if(!map.get("caddr").equals("")){sb.append("e.caddr LIKE '"+map.get("caddr")+"%' AND ");}
			if(!map.get("insno").equals("")){sb.append("e.insno LIKE '"+map.get("insno")+"%' AND ");}
			if(!map.get("pcode").equals("")){sb.append("e.pcode LIKE '"+map.get("pcode")+"%' AND ");}
			if(!map.get("paddr").equals("")){sb.append("e.paddr LIKE '"+map.get("paddr")+"%' AND ");}
			if(!map.get("Email").equals("")){sb.append("e.Email LIKE '"+map.get("Email")+"%' AND ");}
			if(!map.get("CellPhone").equals("")){sb.append("e.CellPhone LIKE '"+map.get("CellPhone")+"%' AND ");}
			if(!map.get("Remark").equals("")){sb.append("e.Remark LIKE '"+map.get("Remark")+"%' AND ");}
			if(!map.get("telephone").equals("")){sb.append("e.telephone LIKE '"+map.get("telephone")+"%' AND ");}
			// 日期類
			
			if(!map.get("TeachStartDate").equals("")){sb.append("e.TeachStartDate LIKE '"+map.get("TeachStartDate")+"%' AND ");}
			if(!map.get("StartDate").equals("")){sb.append("e.StartDate LIKE '"+map.get("StartDate")+"%' AND ");}
			if(!map.get("bdate").equals("")){sb.append("e.bdate LIKE '"+map.get("bdate")+"%' AND ");}
			if(!map.get("EndDate").equals("")){sb.append("e.EndDate LIKE '"+map.get("EndDate")+"%' AND ");}
			if(!map.get("Adate").equals("")){sb.append("e.Adate LIKE '"+map.get("Adate")+"%' AND ");}
			
		}else{
			
			// 若為快速搜尋
			sb.append("e.idno LIKE '"+map.get("fsidno")+"%' AND ");
			sb.append("e.cname LIKE '"+map.get("fscname")+"%' AND ");			
		}
		sb.delete(sb.length()-4, sb.length());//絕對不要自作聰明刪這行或改前2行
		sb.append("ORDER BY e.unit");
		List list=jdbcDao.StandardSqlQuery(sb.toString());
		List empls=new ArrayList();
		Map aEmpl;
		for(int i=0; i<list.size(); i++){
			aEmpl=((Map)list.get(i));			
			if(((Map)list.get(i)).get("sex").equals("1")){
				aEmpl.put("sex1", "男");
			}else{
				aEmpl.put("sex1", "女");
			}			
			aEmpl.put("unitName", ((Map)list.get(i)).get("unitName"));
			aEmpl.put("pcodeName", ((Map)list.get(i)).get("pcodeName"));
			aEmpl.put("categoryName", ((Map)list.get(i)).get("categoryName"));
			aEmpl.put("statusName", ((Map)list.get(i)).get("statusName"));
			empls.add(aEmpl);
		}
		//System.out.println(sb);
		return empls;
	}
	
	/**
	 * 刪除1個po
	 */
	public void removeObj(Object po) {
		courseDao.removeObject(po);		
	}
	
	/**
	 * 身份證字號驗證
	 */
	public boolean checkIdno(String id) {
		System.out.println(id.length());
		if(id.length()!=10)return true;
		id.toUpperCase();                        //轉大寫
        char first = id.charAt(0);              //取出第一個字母         
        /*A-Z的對應數字*/
        int idum[] = {10,11,12,13,14,15,16,17,34,18,19,20,21,22,35,23,24,25,26,27,28,29,32,30,31,33};
        
        /*轉成11碼的字串,'A'=65；substring:從index:1開始取String*/
        id = idum[first - 'A']+id.substring(1);       

        /*把第一碼放到sum中,'0'=48*/
        int sum = id.charAt(0)-'0';
        
        /*取2-10的總合*/
        for(int i=1;i<10;i++)
        sum +=id.charAt(i)*(10-i);
        /*10-加總的尾數 = 第11碼*/
        int checksum = (10-(sum%10))%10;
        if (checksum == id.charAt(10)-'0')
        	return true;
        else
        	return false;
	}
	
	
	/**
	 * 同步Empl
	 */
	public boolean syncEmpl(Empl empl, String mode) {
		if(getLocalHost().indexOf(ezGetString("SELECT Value FROM Parameter WHERE name='host'"))<0){			
			return false;
		}		
		boolean b=true;
		// 取host
		StringBuilder u=new StringBuilder(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE name='Sync_host'"));
		// 重點: 必取得encode以知道本機的編碼，去了同步機才知道要用什麼轉
		u.append("sync/Synchronize?encode="+System.getProperty("file.encoding")+"&sql=");
		StringBuilder sb=new StringBuilder("");		
		URL url;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		if(mode.equals("U")){// 更新模式
			sb.append("UPDATE+empl+SET+");			
			sb.append("idno='"+empl.getIdno()+"',");
			sb.append("cname='"+empl.getCname()+"',");
			sb.append("insno='"+empl.getInsno()+"',");
			sb.append("ename='"+empl.getEname()+"',");
			sb.append("sex='"+empl.getSex()+"',");			
			sb.append("unit='"+empl.getUnit()+"',");
			
			if(empl.getCategory().equals("2")){// 專兼職
				sb.append("fulltime='2',");
			}else{
				sb.append("fulltime='1',");
			}			
			
			sb.append("pcode='"+empl.getPcode()+"',");
			sb.append("sname='"+empl.getSname()+"',");
			sb.append("telephone='"+empl.getTelephone()+"',");
			sb.append("pzip='"+empl.getPzip()+"',");
			
			if(empl.getPaddr()!=null){// 長度超過50字無法容納
				empl.setPaddr(empl.getPaddr().replace(" ", ""));
				if(empl.getPaddr().length()>50){
					sb.append("paddr='"+empl.getPaddr().substring(0, 50)+"',");
				}else{
					sb.append("paddr='"+empl.getPaddr()+"',");
				}
			}
			
			sb.append("czip='"+empl.getCzip()+"',");
			
			if(empl.getCaddr()!=null){// 長度超過50字無法容納
				empl.setCaddr(empl.getCaddr().replace(" ", ""));
				if(empl.getCaddr().length()>50){
					sb.append("caddr='"+empl.getCaddr().substring(0, 50)+"',");
				}else{
					sb.append("caddr='"+empl.getCaddr()+"',");
				}
			}
			
			if(empl.getBdate()!=null){
				sb.append("bdate='"+convertDate(sf.format(empl.getBdate()))+"',");
			}
			if(empl.getEndDate()!=null){
				sb.append("ddate='"+convertDate(sf.format(empl.getEndDate()))+"',");
			}
			if(empl.getAdate()!=null){
				sb.append("adate='"+convertDate(sf.format(empl.getAdate()))+"' ");				
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append("WHERE+idno='"+empl.getIdno()+"';");				
		}
		
		if(mode.equals("D")){// 刪除人員
			sb.append("DELETE+FROM+empl+WHERE+idno='"+empl.getIdno()+"';");
		}
		
		if(mode.equals("A")){// 新增人員
			sb.append("INSERT+INTO+empl+(idno,cname,insno,ename,sex,bdate,unit,fulltime,pcode,sname,telephone" +
			",pzip,paddr,czip,caddr,adate,ddate)VALUES(");
			
			sb.append("'"+empl.getIdno()+"',");
			sb.append("'"+empl.getCname()+"',");
			sb.append("'"+empl.getInsno()+"',");
			sb.append("'"+empl.getEname()+"',");
			sb.append("'"+empl.getSex()+"',");
			
			if(empl.getBdate()!=null){
				sb.append("'"+convertDate(sf.format(empl.getBdate()))+"',");
			}else{
				sb.append("'',");
			}
						
			sb.append("'"+empl.getUnit()+"',");			
			
			if(empl.getCategory().equals("2")){// 專兼職
				sb.append("'2',");
			}else{
				sb.append("'1',");
			}			
			
			sb.append("'"+empl.getPcode()+"',");
			sb.append("'"+empl.getSname()+"',");
			sb.append("'"+empl.getTelephone()+"',");
			sb.append("'"+empl.getPzip()+"',");
			sb.append("'"+empl.getPaddr()+"',");
			sb.append("'"+empl.getCzip()+"',");
			sb.append("'"+empl.getCaddr()+"',");			
			
			if(empl.getAdate()!=null){
				sb.append("'"+convertDate(sf.format(empl.getAdate()))+"',");
			}else{
				sb.append("'',");
			}
			
			if(empl.getEndDate()!=null){
				sb.append("'"+convertDate(sf.format(empl.getEndDate()))+"',");
			}else{
				sb.append("'',");
			}			
			
			sb.delete(sb.length()-1, sb.length());
			sb.append(");");
			
		}
		
		try {
			u.append(sb.toString().replace(" ", "+"));
			url = new URL(u.toString());
			url.openStream();
			
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
			SyncError se=new SyncError();
			se.setErrorMsg(sb.toString());
			courseDao.saveObject(se);// 將失敗的sql存下
		}
		return b;
	}
	
	
	/**
	 * 同步Dempl
	 */
	public boolean syncDEmpl(DEmpl empl, String mode) {
		if(getLocalHost().indexOf(ezGetString("SELECT Value FROM Parameter WHERE name='host'"))<0){			
			return false;
		}
		boolean b=true;
		// 取host
		StringBuilder u=new StringBuilder(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE name='Sync_host'"));
		u.append("sync/Synchronize?sql=");
		StringBuilder sb;
		
		URL url;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(mode.equals("U")){// 更新模式
			sb=new StringBuilder("UPDATE+dempl+SET+");
			
			sb.append("idno='"+empl.getIdno()+"',");
			sb.append("cname='"+empl.getCname()+"',");
			sb.append("insno='"+empl.getInsno()+"',");
			sb.append("ename='"+empl.getEname()+"',");
			sb.append("sex='"+empl.getSex()+"',");
			sb.append("reason='"+empl.getStatusCause()+"',");
			sb.append("unit='"+empl.getUnit()+"',");
			
			if(empl.getCategory().equals("2")){// 專兼職
				sb.append("fulltime='2',");
			}else{
				sb.append("fulltime='1',");
			}			
			
			sb.append("pcode='"+empl.getPcode()+"',");
			sb.append("sname='"+empl.getSname()+"',");
			sb.append("telephone='"+empl.getTelephone()+"',");
			sb.append("pzip='"+empl.getPzip()+"',");
			
			if(empl.getPaddr()!=null){// 長度超過50字無法容納
				empl.setPaddr(empl.getPaddr().replace(" ", ""));
				if(empl.getPaddr().length()>50){
					sb.append("paddr='"+empl.getPaddr().substring(0, 50)+"',");
				}else{
					sb.append("paddr='"+empl.getPaddr()+"',");
				}
			}
			
			sb.append("czip='"+empl.getCzip()+"',");
			
			
			if(empl.getCaddr()!=null){// 長度超過50字無法容納
				empl.setCaddr(empl.getCaddr().replace(" ", ""));
				if(empl.getCaddr().length()>50){
					sb.append("caddr='"+empl.getCaddr().substring(0, 50)+"',");
				}else{
					sb.append("caddr='"+empl.getCaddr()+"',");
				}
			}
			
			if(empl.getBdate()!=null){
				sb.append("bdate='"+convertDate(sf.format(empl.getBdate()))+"',");
			}
			if(empl.getEndDate()!=null){
				sb.append("ddate='"+convertDate(sf.format(empl.getEndDate()))+"',");
			}
			if(empl.getAdate()!=null){
				sb.append("adate='"+convertDate(sf.format(empl.getAdate()))+"'");
				
			}
			if(sb.substring(sb.length()).equals(",")){// 若最終碼為"," 則刪除
				sb.delete(sb.length()-1, sb.length());
			}
			sb.append("WHERE+idno='"+empl.getIdno()+"';");
		
			u.append(sb.toString().replace(" ", "+"));
			try {
				url = new URL(u.toString());
				url.openStream();
			} catch (Exception e) {
				b=false;
				// e.printStackTrace();
				SyncError se=new SyncError();
				se.setErrorMsg(sb.toString());
				courseDao.saveObject(se);// 存下sql
			}			
		}
		
		if(mode.equals("D")){// 刪除人員
			sb=new StringBuilder("DELETE+FROM+dempl+WHERE+idno='"+empl.getIdno()+"';");
			u.append(sb.toString().replace(" ", "+"));
			try {
				url = new URL(u.toString());
				url.openStream();
			} catch (Exception e) {
				// e.printStackTrace();
				b=false;
				SyncError se=new SyncError();
				se.setErrorMsg(sb.toString());
				courseDao.saveObject(se);// 存下sql
			}	
		}
		
		if(mode.equals("A")){// 新增人員
			sb=new StringBuilder("INSERT+INTO+dempl+(idno,cname,insno,ename,sex,bdate,unit,fulltime,pcode,sname,telephone" +
			",pzip,paddr,czip,caddr,adate,ddate, reason)VALUES(");
			
			sb.append("'"+empl.getIdno()+"',");
			sb.append("'"+empl.getCname()+"',");
			sb.append("'"+empl.getInsno()+"',");
			sb.append("'"+empl.getEname()+"',");
			sb.append("'"+empl.getSex()+"',");			
			
			if(empl.getBdate()!=null){
				sb.append("'"+convertDate(sf.format(empl.getBdate()))+"',");
			}else{
				sb.append("'',");
			}
			
						
			sb.append("'"+empl.getUnit()+"',");			
			
			if(empl.getCategory().equals("2")){// 專兼職
				sb.append("'2',");
			}else{
				sb.append("'1',");
			}			
			
			sb.append("'"+empl.getPcode()+"',");
			sb.append("'"+empl.getSname()+"',");
			sb.append("'"+empl.getTelephone()+"',");
			sb.append("'"+empl.getPzip()+"',");
			sb.append("'"+empl.getPaddr()+"',");
			sb.append("'"+empl.getCzip()+"',");
			sb.append("'"+empl.getCaddr()+"'");
			
			if(empl.getAdate()!=null){
				sb.append(",'"+convertDate(sf.format(empl.getAdate()))+"'");
			}else{
				sb.append(",''");
			}
			if(empl.getEndDate()!=null){
				sb.append(",'"+convertDate(sf.format(empl.getEndDate()))+"'");
			}else{
				sb.append(",''");
			}			
			sb.append("'"+empl.getStatusCause()+"'");
			sb.append(");");
			u.append(sb.toString().replace(" ", "+"));
			try {
				url = new URL(u.toString());
				url.openStream();
			} catch (Exception e) {
				// e.printStackTrace();
				b=false;
				SyncError se=new SyncError();
				se.setErrorMsg(sb.toString());
				courseDao.saveObject(se);// 存下sql
			}
		}		
		return b;
	}
	
	
	/**
	 * 同步連線測試
	 */
	public boolean syncTest(){
		// 測是否為正式機
		if(!testOnlineServer()){
			return false;
		}
		// 若是正式機則進行以下區塊
		try {
			StringBuilder u=new StringBuilder(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE name='Sync_host'"));
			u.append("sync/SyncTest");
			URL url = new URL(u.toString());
			url.openStream();
		} catch (Exception e) {// 連線失敗
			return false;
		}		
		return true;
	}
	
	
	/**
	 * 取得本機ip 2008-08-01停用
	 */
	public String getLocalHost() {
		Enumeration netInterfaces;
		String address="0.0.0.0";
		// InetAddress ip = null;
		// getLocalHost();
		
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			// 試用本機所有網路資源是否和主機設定相同
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
				// System.out.println(ni.getName());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return address;
	}
	
	public Savedtime findSavedtimeBy(Integer oid) {
		return (Savedtime) courseDao.getObject(Savedtime.class, oid);
	}
	
	/**
	 * 測試是否為正式伺服器
	 */
	public boolean testOnlineServer(){		
		//Enumeration netInterfaces;
		try {			
			Enumeration e1 = (Enumeration) NetworkInterface.getNetworkInterfaces();			
			while (e1.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) e1.nextElement();
				// System.out.print(ni.getName());
				// System.out.print(": ");
				Enumeration e2 = ni.getInetAddresses();
				while (e2.hasMoreElements()) {
					InetAddress ia = (InetAddress) e2.nextElement();
					if (ia instanceof Inet6Address)
						continue; // omit IPv6 address
					// System.out.print(ia.getHostAddress());
					//if (e2.hasMoreElements()) {
						// System.out.print(", ");
					//}
					if(ezGetInt("SELECT COUNT(*)FROM  Parameter WHERE name='host' AND value LIKE'%"+ia.getHostAddress()+"%'")>0){
						return true;					
					} 
				}
				// System.out.print("\n");
			}
			/*
			 * netInterfaces = NetworkInterface.getNetworkInterfaces();
			 * InetAddress ip = null; while (netInterfaces.hasMoreElements()) {
			 * NetworkInterface ni=(NetworkInterface)
			 * netInterfaces.nextElement();
			 * 
			 * ip = (InetAddress) ni.getInetAddresses().nextElement();
			 * //System.out.println("ip="+ip.getHostName());
			 * //ystem.out.println(ni.getName()+"="+ni.getInetAddresses().nextElement());
			 * if(ezGetInt("SELECT COUNT(*)FROM Parameter WHERE name='host' AND
			 * value LIKE'%"+ip.getHostAddress()+"%'")>0){ return true; } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		return false;
		
	}
	
	/**
	 * a, s, t, p
	 */
	public List getPageList(String type){
		
		//String sql="";
		// 校內開放連結
		if(type.equals("a")){
			return jdbcDao.StandardSqlQuery("SELECT * FROM PageLink WHERE Type='a'");
		}
		// 公用查詢
		if(type.equals("p")){
			return jdbcDao.StandardSqlQuery("SELECT * FROM PageLink WHERE Type='p'");
		}// 公用查詢
		if(type.equals("o")){
			return jdbcDao.StandardSqlQuery("SELECT * FROM PageLink WHERE Type='o'");
		}
		// 學生功能導覽
		if(type.equals("s")){
			List tmp=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='7' ORDER BY Icon DESC");
			Map map;
			List list=new ArrayList();
			for(int i=0; i<tmp.size(); i++){
				map=new HashMap();				
				map.put("Oid", ((Map)tmp.get(i)).get("Oid"));
				map.put("Label", ((Map)tmp.get(i)).get("Label"));
				map.put("Icon", ((Map)tmp.get(i)).get("Icon"));
				map.put("subMenu", jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='"+((Map)tmp.get(i)).get("Oid")+"'"));
				list.add(map);
			}
			tmp=null;
			return list;
		}
		// 教師功能導覽
		if(type.equals("t")){
			List tmp=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='8' ORDER BY Icon DESC");
			Map map;
			List list=new ArrayList();
			for(int i=0; i<tmp.size(); i++){
				map=new HashMap();				
				map.put("Oid", ((Map)tmp.get(i)).get("Oid"));
				map.put("Label", ((Map)tmp.get(i)).get("Label"));
				map.put("Icon", ((Map)tmp.get(i)).get("Icon"));
				map.put("subMenu", jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid<>'0' AND ParentOid='"+((Map)tmp.get(i)).get("Oid")+"'"));
				list.add(map);
			}
			tmp=null;
			return list;
		}
		// 教師功能導覽
		if(type.equals("stf")){
			List tmp=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE Oid NOT IN" +
					"('8', '7', '9', '10', '10000', '20000', '30000', '40000', '50000', '60000', '80000') " +
					"AND ParentOid='0' ORDER BY Icon DESC");
			Map map;
			List list=new ArrayList();
			for(int i=0; i<tmp.size(); i++){
				map=new HashMap();				
				map.put("Oid", ((Map)tmp.get(i)).get("Oid"));
				map.put("Label", ((Map)tmp.get(i)).get("Label"));
				map.put("Icon", ((Map)tmp.get(i)).get("Icon"));
				map.put("subMenu", jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid<>'0' AND ParentOid='"+((Map)tmp.get(i)).get("Oid")+"'"));
				list.add(map);
			}
			tmp=null;
			return list;
		}
		
		return null;
	}
	
	/**
	 * 取聘書
	 */
	public List getContract(Map map){	
		List oldContract=ezGetBy("SELECT p7.name as p7name, e.cname, c.name, ec.* FROM " +
				"Empl_contract ec, empl e, CodeEmpl c, Pecode p7 " +
				"WHERE p7.type='pccode7' AND p7.id=ec.level AND e.idno=ec.idno AND c.idno=e.unit AND c.category='UnitTeach' AND " +
				"ec.idno LIKE '"+map.get("idno")+"%' AND ec.partime='"+map.get("category")+"' AND " +
				"ec.start_date LIKE '"+map.get("start_date")+"%' AND " +
				"ec.end_date LIKE '"+map.get("end_date")+"%' AND " +
				"ec.type LIKE '"+map.get("type")+"%' AND " +
				"ec.level LIKE '"+map.get("level")+"%' AND " +
				"ec.markup LIKE '"+map.get("markup")+"%' AND " +
				"c.idno LIKE'"+map.get("unit")+"%' " +
				"ORDER BY c.sequence");
		
		Date startDate;
		Date endDate;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c;
		for(int i=0; i<oldContract.size(); i++){
			try{
				startDate=sf.parse(convertDate(((Map)oldContract.get(i)).get("start_date").toString()));
				endDate=sf.parse(convertDate(((Map)oldContract.get(i)).get("end_date").toString()));
			}catch(Exception e){
				startDate=new Date();
				endDate=new Date();
			}
			
			c=Calendar.getInstance();
			c.setTime(startDate);
			((Map)oldContract.get(i)).put("startYear", c.get(Calendar.YEAR));
			((Map)oldContract.get(i)).put("startMonth", c.get(Calendar.MONTH)+1);
			((Map)oldContract.get(i)).put("startDay", c.get(Calendar.DAY_OF_MONTH));
			c.setTime(endDate);
			((Map)oldContract.get(i)).put("endYear", c.get(Calendar.YEAR));
			((Map)oldContract.get(i)).put("endMonth", c.get(Calendar.MONTH)+1);
			((Map)oldContract.get(i)).put("endDay", c.get(Calendar.DAY_OF_MONTH));
			
			
			
			
		}
		
		
		return oldContract;
	}
	
	/**
	 * 新聘書
	 */
	public List getNewContract(Map map){
		
		Date date;
		Calendar c;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Map aLicence;
		List newContract=ezGetBy("SELECT e.cname, c.name, ec.* FROM Empl_contract ec, empl e, CodeEmpl c " +
				"WHERE e.idno=ec.idno AND c.idno=e.unit AND c.category='UnitTeach' AND " +
				"ec.idno LIKE '"+map.get("idno")+"%' AND ec.partime='"+map.get("category")+"' AND " +
				"ec.start_date LIKE '"+map.get("start_date")+"%' AND " +
				"ec.end_date LIKE '"+map.get("end_date")+"%' AND " +
				"ec.type LIKE '"+map.get("type")+"%' AND " +
				"ec.level LIKE '"+map.get("level")+"%' AND " +
				"ec.markup LIKE '"+map.get("markup")+"%' AND " +
				"c.idno LIKE'"+map.get("unit")+"%' " +
				"ORDER BY c.sequence");
		
		for(int i=0; i<newContract.size(); i++){			
			aLicence=ezGetMap("SELECT * FROM Empl_licence WHERE idno='"+((Map)newContract.get(i)).get("idno")+"' ORDER BY sequence limit 1");
			c=Calendar.getInstance();
			try {
				c.setTime(sf.parse(((Map)newContract.get(i)).get("end_date").toString()));				
				c.add(Calendar.YEAR,+2);
				((Map)newContract.get(i)).put("start_date", ((Map)newContract.get(i)).get("end_date"));
				((Map)newContract.get(i)).put("end_date", sf.format(c.getTime()));
			} catch (ParseException e) {
				((Map)newContract.get(i)).put("start_date", ((Map)newContract.get(i)).get("end_date"));
				((Map)newContract.get(i)).put("end_date", sf.format(c.getTime()));
			}			
			try{
				((Map)newContract.get(i)).put("licence_no", aLicence.get("licence_no"));
				((Map)newContract.get(i)).put("markup", aLicence.get("markup"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}				
		return newContract;
	}
	
	/**
	 * 轉po中的西元或民國
	 * 
	 * @param map :
	 *            單為empl, dempl, EmplSave 三種型態的po可進入
	 * @return 轉好日期的empl, dempl, EmplSave
	 */
	public Map getADorCD(Map map){
		// CourseManager manager = (CourseManager) getBean("courseManager");
		try{// 生日
			map.put("bdate", convertDate(map.get("bdate").toString()));
		}catch(Exception e){
			map.put("bdate", "");
		}		
		try{// 到職日
			map.put("StartDate", convertDate(map.get("StartDate").toString()));
		}catch(Exception e){
			map.put("StartDate", "");
		}		
		try{// 離職日
			map.put("EndDate", convertDate(map.get("EndDate").toString()));
		}catch(Exception e){
			map.put("EndDate", "");
		}
		try{// 任教職日
			map.put("TeachStartDate", convertDate(map.get("TeachStartDate").toString()));
		}catch(Exception e){
			map.put("TeachStartDate", "");
		}
		try{// 初到職日
			map.put("Adate", convertDate(map.get("Adate").toString()));
		}catch(Exception e){
			map.put("TeachStartDate", "");
		}
		return map;
	}
	
	/**
	 * 換1個empl_save 回來
	 * 
	 * @param map
	 * @return
	 */
	public EmplSave getEmplSave(Map map, boolean newEmplSave){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		EmplSave emplSave;
		if(newEmplSave){
			emplSave=new EmplSave();
		}else{
			emplSave=(EmplSave)hqlGetBy("FROM EmplSave WHERE idno='"+map.get("idno")+"'").get(0);
		}
		// System.out.println(map.get("bdate"));
		try {
			emplSave.setBdate(sf.parse((String)map.get("bdate")));
		} catch (ParseException e) {
			emplSave.setBdate(null);
		}
		
		emplSave.setUnit((String)map.get("unit"));
		emplSave.setCaddr((String)map.get("caddr"));
		emplSave.setCategory((String)map.get("category"));
		emplSave.setCellPhone((String)map.get("CellPhone"));
		emplSave.setCname((String)map.get("cname"));
		emplSave.setCzip((String)map.get("czip"));
		emplSave.setDegree((String)map.get("Degree"));
		emplSave.setEmail((String)map.get("Email"));
		emplSave.setEname((String)map.get("ename"));
		
		try {
			emplSave.setEndDate(sf.parse((String)map.get("EndDate")));
		} catch (ParseException e) {
			emplSave.setEndDate(null);
		}
		
		emplSave.setDirector((String)map.get("Director"));
		emplSave.setIdno((String)map.get("idno"));
		emplSave.setInsno((String)map.get("insno"));
		emplSave.setPaddr((String)map.get("paddr"));
		emplSave.setPcode((String)map.get("pcode"));
		emplSave.setPzip((String)map.get("pzip"));
		emplSave.setRemark((String)map.get("Remark"));
		emplSave.setSex((String)map.get("sex"));
		emplSave.setSname((String)map.get("sname"));
		
		try {
			emplSave.setStartDate(sf.parse((String)map.get("StartDate")));
		} catch (Exception e) {
			emplSave.setStartDate(null);
		}
		
		try {
			emplSave.setAdate(sf.parse((String)map.get("Adate")));
		} catch (Exception e) {
			emplSave.setAdate(null);
		}
		
		emplSave.setStatus((String)map.get("Status"));
		emplSave.setStatusCause((String)map.get("StatusCause"));
		
		try {
			emplSave.setTeachStartDate(sf.parse((String)map.get("TeachStartDate")));
		} catch (Exception e) {
			emplSave.setTeachStartDate(null);
		}		
		emplSave.setTelephone((String)map.get("telephone"));
		emplSave.setTutor((String)map.get("Tutor"));
		emplSave.setUnit((String)map.get("unit"));
		
		return emplSave;
	}
	
	/**
	 * 文字變日期(西元→西元)
	 */
	public Date getADtoAD(String ad) {
		
		Date result=null;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try{			
			result=sf.parse(ad);
		}catch(Exception e){
			sf=new SimpleDateFormat("yyyy/MM/dd");
			try {
				result=sf.parse(ad);
			} catch (Exception e1) {				
				result=new Date();
			}
		}
		return result;
	}
	
	/**
	 * 文字變日期(民國→西元→民國→隨便)
	 */
	public Date getROCYtoAD(String rocy){
		rocy=convertDate(rocy);
		Date result=null;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try{			
			result=sf.parse(rocy);
		}catch(Exception e){
			sf=new SimpleDateFormat("yyyy/MM/dd");
			try {
				result=sf.parse(rocy);
			} catch (Exception e1) {				
				result=new Date();
			}
		}
		return result;
	}
	
	/**
	 * 實做刪除教師研究成果資料
	 */
	public void removeRcTableBy(String Table, String RcactOid) {
		String sql="DELETE FROM "+Table+" WHERE Oid="+RcactOid;
		jdbcDao.StandardSqlRemove(sql);
	}
	
	/**
	 * 查詢Gmark物件
	 * 
	 * @param gmark
	 * @return
	 */
	public List<Gmark> findGMarkBy(Gmark gmark) {
		return courseDao.getGMarkBy(gmark);
	}
	
	/**
	 * 查詢StdLoan物件
	 * 
	 * @param stdLoan 
	 * @return List of StdLoan Objects
	 */
	public List<StdLoan> findStdLoanBy(StdLoan stdLoan) {
		return courseDao.getStdLoanBy(stdLoan);
	}
	
	public List<Dtime> findDtimesBy(Dtime dtime) {
		return courseDao.getDtimesBy(dtime);
	}
	
	public List<LiteracyType> findLiteracyTypesBy(LiteracyType literacyType)
			throws DataAccessException {
		return courseDao.getLiteracyTypesBy(literacyType);
	}
	
	/**
	 * 去他媽不知道在搞什麼
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException {
		return courseDao.getSQLWithCriteria(entity, expression);
	}
	
	/**
	 * 寄件備份
	 */
	public void saveMail(String real_from, String send_from, String subject, InternetAddress[] address, String content, Date send_date, String real_address){
		try{					
			MailStorage ms=new MailStorage();
			ms.setContent(content);
			ms.setRealAddress(real_address);
			ms.setRealDate(new Date());
			ms.setRealFrom(real_from);
			ms.setSendFrom(send_from);
			StringBuilder sb=new StringBuilder();
			for(int i=0; i<address.length; i++){sb.append(address[i].getPersonal()+address[i].getAddress()+", ");
			}
			ms.setReceiver(sb.toString());
			ms.setRealDate(new Date());
			ms.setSubject(subject);
			ms.setSendDate(send_date);
			updateObject(ms);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	
	/**
	 * 系統郵件傳送
	 * @param username 登入郵件伺服器帳號
	 * @param password 登入郵件伺服器密碼
	 * @param smtpServer 郵件伺服器
	 * @param dsplEmail 顯示寄件信箱
	 * @param dsplName 顯示寄件人
	 * @param dsplDate 顯示寄件日期
	 * @param subject 主旨
	 * @param content 內容
	 * @param address 收件人(們)
	 * @param files 夾檔(們)
	 * 當SMTP套件被更換時，修改此處即可全面更新
	 */
	public boolean sendMail(
			String username, String password, String smtpServer,
			String dsplEmail, String dsplName, Date dsplDate, 
			String subject, String content, InternetAddress address[], 
			FileDataSource files[]){
			
			List list=new ArrayList();		
			for(int i=0; i<address.length; i++){
				if(address[i]!=null && !address[i].equals(""))
				if(validateEmail(address[i].getAddress())){
					list.add(address[i]);
				}
			}
			Collection c=new ArrayList();
			c.addAll(list);			
			try{
				MultiPartEmail email=new HtmlEmail();
				email.setCharset("big5");				
				email.setAuthentication(username, password);
				email.setHostName(smtpServer);
				email.setFrom(dsplEmail, dsplName);
				email.setSentDate(dsplDate);
				email.setSubject(subject);
				if(!content.trim().equals(""))
				email.setMsg(content);
				email.setTo(c);
				
				if(files!=null)
				if(files.length>0){
					EmailAttachment att=new EmailAttachment();
					String fileName;
					for(int i=0; i<files.length; i++){
						if(files[i]!=null)
						try{
							att = new EmailAttachment();
							att.setPath(files[i].getFile().getAbsolutePath());
							att.setDisposition(EmailAttachment.ATTACHMENT);
							fileName=new String(MimeUtility.encodeText(files[i].getFile().getName(), "big5", "B"));
							att.setName(fileName);						
							email.attach(att);
						}catch(Exception e){}//finally{}
					}
				}			
				email.send();			
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}		
			return true;	
	}
	
	/**
	 * 簡易即時寄送郵件
	 */
	public boolean sendMailSimple(String smtp_account, String smtp_password, String smtp_server, 
			String from_email, String from_name, String subject, String content, String members, FileDataSource files[]){		
		
		
		try{
		
		if (members.trim().equals("ALL")){
			members="";
			List eadd=ezGetBy("SELECT Email FROM empl");
			for(int i=0; i<eadd.size(); i++){
				try{
					members=members+((Map)eadd.get(i)).get("Email").toString()+",";
				}catch(Exception e1){
					continue;
				}
				
			}
		}else{
			members=members.replace(";", ",");
			members=members.replace(" ", ",");			
		}
		
				
		String emails[];		
		
			MultiPartEmail email=new HtmlEmail();
			email.setCharset("big5");				
			email.setAuthentication(smtp_account, smtp_password);
			email.setHostName(smtp_server);
			email.setFrom(from_email, from_name);
			email.setSubject(subject);
			if(!content.trim().equals("")){email.setMsg(content);}			
			email.addCc(from_email);			
			emails=members.split(",");
			for(int i=0; i<emails.length; i++){
				try{
					email.addBcc(emails[i]);
					//email.addTo(emails[i]);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}				
			}
			
			if(files!=null)
				if(files.length>0){
					EmailAttachment att=new EmailAttachment();
					String fileName;
					for(int i=0; i<files.length; i++){
						if(files[i]!=null)
						try{
							att = new EmailAttachment();
							att.setPath(files[i].getFile().getAbsolutePath());
							att.setDisposition(EmailAttachment.ATTACHMENT);
							fileName=new String(MimeUtility.encodeText(files[i].getFile().getName(), "big5", "B"));
							att.setName(fileName);						
							email.attach(att);
						}catch(Exception e){
							e.printStackTrace();
							return false;
						}//finally{}
					}
				}			
				email.send();
				saveMailSimple(from_email, from_email, subject, members, content, new Date(), from_email);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void saveMailSimple(String real_from, String send_from, String subject, String address, String content, Date send_date, String real_address){
		try{					
			MailStorage ms=new MailStorage();
			ms.setContent(content);
			ms.setRealAddress(real_address);
			ms.setRealDate(send_date);
			ms.setRealFrom(real_from);
			ms.setSendFrom(send_from);			
			ms.setReceiver(address);
			ms.setRealDate(send_date);
			ms.setSubject(subject);
			ms.setSendDate(send_date);
			updateObject(ms);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 電子郵件格式驗證
	 * @param email
	 * @return
	 */
	public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailPattern = "^([\\w]+)(([-\\.][\\w]+)?)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return email.matches(emailPattern);
    }
	
	/**
	 * 要伺候某1位學生的相關同仁
	 */
	public List getEmpl(String student_no){
		List list=ezGetBy("SELECT e.cname, e.Email FROM " +
		"stmd st, ClassInCharge c, empl e, UnitBelong u " +
		"WHERE (e.Email<>'' AND e.Email IS NOT NULL) AND " +
		"e.Oid=u.EmpOid AND u.UnitNO IN('B1', 'B3', 'B5', 'B4') AND " +
		"e.Oid=c.EmpOid AND c.ModuleOids LIKE '%|1|%' AND " +
		"st.depart_class=c.ClassNo AND st.student_no='"+student_no+"'");
		if(list.isEmpty()){			
			list=ezGetBy("SELECT e.cname, e.Email FROM " +
			"Gstmd st, ClassInCharge c, empl e, UnitBelong u " +
			"WHERE (e.Email<>'' AND e.Email IS NOT NULL) AND " +
			"e.Oid=u.EmpOid AND u.UnitNO IN('B1', 'B3', 'B5', 'B4') AND " +
			"e.Oid=c.EmpOid AND c.ModuleOids LIKE '%|1|%' AND " +
			"st.depart_class=c.ClassNo AND st.student_no='"+student_no+"'");			
		}		
		return list;
	}
	
	/**
	 * 取得行政人員目前的線上服務佇列
	 * @param idno
	 * @return
	 */
	public List getMyOnlineWork(String idno){//在校生
		List list=ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, Gstmd st, " +
		"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
		"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
		"c.ClassNo=st.depart_class AND (os.status='W' OR os.status='O') AND c.ModuleOids<>'|87|' AND e.idno='"+idno+"'");
		list.addAll(ezGetBy("SELECT os.*, st.student_name FROM OnlineService4Reg os, stmd st, " +//離校生
		"empl e, ClassInCharge c, UnitBelong u WHERE e.Oid=u.EmpOid AND u.UnitNO " +
		"IN('B1', 'B3', 'B5', 'B4') AND os.student_no=st.student_no AND e.Oid=c.EmpOid AND " +
		"c.ClassNo=st.depart_class AND (os.status='W' OR os.status='O') AND c.ModuleOids<>'|87|' AND e.idno='"+idno+"'"));		
		return list;
	}
	
	/**
	 * 取得為系統服務的郵件伺服器資訊
	 * TODO 目前服務均被停止
	 */
	public Map getCISMailServerInfo() {
		Map map=new HashMap();		
		map.put("username", ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'"));
		map.put("password", ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'"));
		map.put("mailServer", ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND Category='smtp'"));
		map.put("mailAddress", ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress' AND Category='smtp'"));
		return map;
	}
	
	/**
	 * 給特定人員功能
	 * 學生為學號，職員為身份證字號
	 */
	public Menu putPrivateMenu(String idno, Menu menu) {
		Module module;
		Menu subMenu=new Menu();
		MenuItem item;
		List myModule=jdbcDao.StandardSqlQuery("SELECT um.ModuleOid FROM User_Module um " +
				"WHERE um.Idno='"+idno+"'");//所有
		if(myModule.size()<1){
			return menu;//立即斷開
		}
		List list=jdbcDao.StandardSqlQuery("SELECT m.* FROM Module m, User_Module um " +
				"WHERE m.Oid=um.ModuleOid AND um.Idno='"+idno+"' AND m.ParentOid='0' ORDER BY m.Sequence");//大項
		List submenu;
		StringBuilder sb=new StringBuilder("AND Oid IN(");
		for(int i=0; i<myModule.size(); i++){
			sb.append("'"+((Map)myModule.get(i)).get("ModuleOid")+"', ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")");
		for(int i=0; i<list.size(); i++){
			module=new Module();
			module.setAction(((Map)list.get(i)).get("Action").toString());
			module.setLabel(((Map)list.get(i)).get("Label").toString());
			module.setLevel(Integer.parseInt(((Map)list.get(i)).get("Level").toString()));
			module.setName(((Map)list.get(i)).get("Name").toString());
			module.setOid(Integer.parseInt(((Map)list.get(i)).get("Oid").toString()));
			module.setParentOid(Integer.parseInt(((Map)list.get(i)).get("ParentOid").toString()));
			module.setSequence(Integer.parseInt(((Map)list.get(i)).get("Sequence").toString()));			
			MenuItem pmenu=new MenuItem(module);					
			submenu=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='"+module.getOid()+"'"+sb.toString());			
			
			for(int j=0; j<submenu.size(); j++){
				module=new Module();
				module.setAction(((Map)submenu.get(j)).get("Action").toString());
				if(((Map)submenu.get(j)).get("Icon")!=null){
					module.setIcon(((Map)submenu.get(j)).get("Icon").toString());
				}
				module.setLabel(((Map)submenu.get(j)).get("Label").toString());
				module.setLevel(Integer.parseInt(((Map)submenu.get(j)).get("Level").toString()));
				module.setName(((Map)submenu.get(j)).get("Name").toString());
				module.setOid(Integer.parseInt(((Map)submenu.get(j)).get("Oid").toString()));
				module.setParentOid(Integer.parseInt(((Map)submenu.get(j)).get("ParentOid").toString()));
				module.setSequence(Integer.parseInt(((Map)submenu.get(j)).get("Sequence").toString()));
				item=new MenuItem(module);				
				subMenu.addItem(item);
			}			
			pmenu.setSubMenu(subMenu);			
			menu.addItem(pmenu);
		}			
		return menu;
	}
	
	/**
	 * 有優先權的功能
	 * @param priority
	 * @param menu
	 * @return
	 */
	public Menu putPriorityUniteMenu(String priority, Menu menu) {
		if(menu==null){
			menu=new Menu();
		}
		Module module;
		Module subModule;
		Menu subMenu;
		MenuItem item;
		List myModule=jdbcDao.StandardSqlQuery("SELECT um.ModuleOid FROM Unite_Module um, Module m " +
				"WHERE m.Oid=um.ModuleOid AND um.Priority='"+priority+"'");// 小於0的方優先
		if(myModule.size()<1){
			return menu;//立即斷開
		}
		
		List list=jdbcDao.StandardSqlQuery("SELECT m.* FROM Module m, Unite_Module um " +
				"WHERE m.Oid=um.ModuleOid AND um.Priority='"+priority+"' AND m.ParentOid='0' AND m.sequence<0 ORDER BY m.Sequence");//大項
		
		List submenu;
		
		StringBuilder sb=new StringBuilder("AND Oid IN(");
		for(int i=0; i<myModule.size(); i++){
			sb.append("'"+((Map)myModule.get(i)).get("ModuleOid")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());
		sb.append(") ORDER BY Sequence");		
		
		for(int i=0; i<list.size(); i++){
			subMenu=new Menu();
			module=new Module();
			module.setAction(((Map)list.get(i)).get("Action").toString());
			module.setLabel(((Map)list.get(i)).get("Label").toString());
			module.setLevel(Integer.parseInt(((Map)list.get(i)).get("Level").toString()));
			module.setName(((Map)list.get(i)).get("Name").toString());
			module.setOid(Integer.parseInt(((Map)list.get(i)).get("Oid").toString()));
			module.setParentOid(Integer.parseInt(((Map)list.get(i)).get("ParentOid").toString()));
			module.setSequence(Integer.parseInt(((Map)list.get(i)).get("Sequence").toString()));			
		
			MenuItem pmenu=new MenuItem(module);					
			submenu=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='"+module.getOid()+"'"+sb.toString());			
			
			for(int j=0; j<submenu.size(); j++){
				
				subModule=new Module();
				
				subModule.setAction(((Map)submenu.get(j)).get("Action").toString());
				if(((Map)submenu.get(j)).get("Icon")!=null){
					subModule.setIcon(((Map)submenu.get(j)).get("Icon").toString());
				}
				
				subModule.setLabel(((Map)submenu.get(j)).get("Label").toString());
				subModule.setLevel(Integer.parseInt(((Map)submenu.get(j)).get("Level").toString()));
				subModule.setName(((Map)submenu.get(j)).get("Name").toString());
				subModule.setOid(Integer.parseInt(((Map)submenu.get(j)).get("Oid").toString()));
				subModule.setParentOid(Integer.parseInt(((Map)submenu.get(j)).get("ParentOid").toString()));
				subModule.setSequence(Integer.parseInt(((Map)submenu.get(j)).get("Sequence").toString()));
				
				item=new MenuItem(subModule);				
				subMenu.addItem(item);
			}
			
			pmenu.setSubMenu(subMenu);
			menu.addItem(pmenu);
		}
		return menu;
	}
	
	
	/**
	 * 給予某種身份的人功能
	 * 身份別C=學生, A=職員, L=校友, F=囧?
	 */
	public Menu putUniteMenu(String priority, Menu menu) {
		if(menu==null){
			menu=new Menu();
		}
		Module module;
		Module subModule;
		Menu subMenu;
		MenuItem item;
		
		//第1層
		List myModule=jdbcDao.StandardSqlQuery("SELECT um.ModuleOid FROM Unite_Module um, Module m " +
				"WHERE m.Oid=um.ModuleOid AND um.Priority='"+priority+"'");
		
		if(myModule.size()<1){
			return menu;//立即斷開
		}
		
		List list=jdbcDao.StandardSqlQuery("SELECT m.* FROM Module m, Unite_Module um " +
				"WHERE m.Oid=um.ModuleOid AND um.Priority='"+priority+"' AND m.ParentOid='0' ORDER BY m.Sequence");//大項
		
		List submenu;
		
		StringBuilder sb=new StringBuilder("AND Oid IN(");
		for(int i=0; i<myModule.size(); i++){
			sb.append("'"+((Map)myModule.get(i)).get("ModuleOid")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());
		sb.append(") ORDER BY Sequence");
		
		
		
		for(int i=0; i<list.size(); i++){
			subMenu=new Menu();
			module=new Module();
			module.setAction(((Map)list.get(i)).get("Action").toString());
			module.setLabel(((Map)list.get(i)).get("Label").toString());
			module.setLevel(Integer.parseInt(((Map)list.get(i)).get("Level").toString()));
			module.setName(((Map)list.get(i)).get("Name").toString());
			module.setOid(Integer.parseInt(((Map)list.get(i)).get("Oid").toString()));
			module.setParentOid(Integer.parseInt(((Map)list.get(i)).get("ParentOid").toString()));
			module.setSequence(Integer.parseInt(((Map)list.get(i)).get("Sequence").toString()));			
			
			MenuItem pmenu=new MenuItem(module);					
			
			//System.out.println("SELECT * FROM Module WHERE ParentOid='"+module.getOid()+"'"+sb.toString());
			submenu=jdbcDao.StandardSqlQuery("SELECT * FROM Module WHERE ParentOid='"+module.getOid()+"'"+sb.toString());			
			for(int j=0; j<submenu.size(); j++){
				
				subModule=new Module();
				
				subModule.setAction(((Map)submenu.get(j)).get("Action").toString());
				if(((Map)submenu.get(j)).get("Icon")!=null){
					subModule.setIcon(((Map)submenu.get(j)).get("Icon").toString());
				}
				
				subModule.setLabel(((Map)submenu.get(j)).get("Label").toString());
				subModule.setLevel(Integer.parseInt(((Map)submenu.get(j)).get("Level").toString()));
				subModule.setName(((Map)submenu.get(j)).get("Name").toString());
				subModule.setOid(Integer.parseInt(((Map)submenu.get(j)).get("Oid").toString()));
				subModule.setParentOid(Integer.parseInt(((Map)submenu.get(j)).get("ParentOid").toString()));
				subModule.setSequence(Integer.parseInt(((Map)submenu.get(j)).get("Sequence").toString()));
				
				item=new MenuItem(subModule);				
				subMenu.addItem(item);
			}			
			pmenu.setSubMenu(subMenu);
			menu.addItem(pmenu);
		}
		//System.out.println(menu.getItems().size());
		return menu;
	}
	
	/**
	 * 取得連合服務中心各單位輸入的服務資訊
	 */
	public String getSchInfo(String student_no) {
		StringBuilder sb=new StringBuilder();
		sb.append("<br><br><br><br><br><br><br><br><br><br>");
		Map c=new HashMap();
		//System.out.println("SELECT c.CampusNo, d.* FROM dept d, Class c, stmd s WHERE " +
				//"d.no=c.Dept AND c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
		try{
			c=jdbcDao.ezGetMap("SELECT c.CampusNo, d.* FROM dept d, Class c, stmd s WHERE " +
					"d.no=c.Dept AND c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
		}catch(Exception e){
			c=jdbcDao.ezGetMap("SELECT c.CampusNo, d.* FROM dept d, Class c, Gstmd s WHERE " +
					"d.no=c.Dept AND c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
		}		
		
		if(c==null){
			c=jdbcDao.ezGetMap("SELECT c.CampusNo, d.* FROM dept d, Class c, Gstmd s WHERE d.no=c.Dept AND c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
		}
		if(c==null){
			c=jdbcDao.ezGetMap("SELECT c.CampusNo, d.* FROM dept d, Class c, Gstmd s WHERE d.no=c.Dept AND c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
		}
		if(c.get("CampusNo").equals("1")){
			sb.append(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolPost_Taipei'"));//台北郵編
			sb.append(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolAddress_Taipei' AND category='school_address'"));//台北地址
			sb.append("<br>");
			String dept=c.get("school_name").toString();
			//System.out.println(dept);
			if(dept.indexOf("日")!=-1 || dept.equals("碩士班")){
				try{
					sb.append(jdbcDao.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B1'"));
				}catch(Exception e){
					sb.append("日間部註冊組, 分機125、126, 傳真02-2782-7249(08:00~16:20)");
				}				
			}		
			if(dept.indexOf("進修部")!=-1 || dept.indexOf("在職")!=-1 || dept.indexOf("夜")!=-1){
				try{
					sb.append(jdbcDao.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B3'"));
				}catch(Exception e){
					sb.append("進修部教務組, 分機136、163(14:30~21:30)");
				}				
			}		
			if(dept.indexOf("進修專校")!=-1||dept.indexOf("學院")!=-1){
				try{
					sb.append(jdbcDao.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B5'"));
				}catch(Exception e){
					sb.append("進修學院/專校教務組, 分機225、231, 傳真02-2782-2872(08:00~18:30)");
				}				
			}	
		}else{
			sb.append(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolPost_Hsinchu'"));//新竹郵編
			sb.append(jdbcDao.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolAddress_Taipei' AND category='school_address'"));//新竹地址
			try{
				sb.append(jdbcDao.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B4'"));
			}catch(Exception e){
				sb.append("<br>教務組, 分機225、231, 傳真02-2783-7172(08:20~21:30)");
			}
		}		
		return sb.toString();
	}

	public List checkDelStd(List stds) {		
		String studentNo="";
		List list=new ArrayList();
		for(int i=0; i<stds.size(); i++){
			
			if(stds.get(i).getClass()==Student.class){				
				studentNo=((Student)stds.get(i)).getStudentNo();			
			}else{
				studentNo=((Graduate)stds.get(i)).getStudentNo();
			}			
			Map map=new HashMap();
			map.put("student_no", studentNo);
			//選課
			map.put("Seld", jdbcDao.StandardSqlQuery("SELECT s.Oid, s.cscode, c.chi_name FROM Seld s, Dtime d, Csno c " +
					"WHERE s.Dtime_oid=d.Oid AND d.cscode=c.cscode AND s.student_no='"+studentNo+"'"));
			//加退選
			map.put("adcd", jdbcDao.StandardSqlQuery("SELECT * FROM AddDelCourseData a WHERE a.Student_no='"+studentNo+"'"));
			//帳密
			map.put("wwpass", jdbcDao.StandardSqlQuery("SELECT * FROM wwpass w WHERE username='"+
					studentNo+"'"));
			//轉班記錄
			map.put("Tran", jdbcDao.StandardSqlQuery("SELECT * FROM Tranno t WHERE student_no='"+
					studentNo+"'"));			
			//個人備註
			map.put("Gmark", jdbcDao.StandardSqlQuery("SELECT * FROM Gmark t WHERE student_no='"+
					studentNo+"'"));			
			//個人備註1
			map.put("Rmark", jdbcDao.StandardSqlQuery("SELECT * FROM Rmark r WHERE r.student_no='"+
					studentNo+"'"));
			//歷年成績			
			map.put("ScoreHist", jdbcDao.StandardSqlQuery("SELECT s.*, c.chi_name FROM ScoreHist s, Csno c WHERE s.cscode=c.cscode AND s.student_no='"+
					studentNo+"'"));
			//歷年獎懲
			map.put("just", jdbcDao.StandardSqlQuery("SELECT * FROM Just j WHERE j.Student_no='"+studentNo+"'"));
			list.add(map);
		}		
		return list;
	}
	
	/**
	 * 從外部建立基本網站
	 */
	public void checkNewPortfolio(String Uid, String siteName, String siteDescript, String path) {
		if(jdbcDao.StandardSqlQueryForInt("SELECT COUNT(*)FROM Eps_user WHERE Uid='"+Uid+"'")<1){
			
			if(siteName.trim().equals("")){
				siteName="我的e-Portfolio";
			}
			if(siteDescript.trim().equals("") ){
				siteDescript="歡迎參觀我的網站";
			}
			//System.out.println("impl="+path);
			
			//建立網站
			EpsUser user=new EpsUser();
			user.setBanner(ezGetString("SELECT template FROM Eps_template WHERE Oid='14'"));//系統預設值14
			user.setFootStyle(12);//footer
			user.setHeaderStyle(13);//header
			user.setSiteDescript(siteDescript);
			user.setSiteName(siteName);
			user.setPath(path);
			user.setCounter(0);
			user.setTemplate(ezGetString("SELECT template FROM Eps_template WHERE Oid='1'"));//1號模版
			user.setUid(Uid);
			//建立首頁
			//executeSql("DELETE FROM Eps_pages WHERE Uid='"+Uid+"'");//建立前確認
			EpsPages ep=new EpsPages();
			//System.out.println(ezGetString("SELECT template FROM Eps_template WHERE type='P'"));
			ep.setContent(ezGetString("SELECT template FROM Eps_template WHERE type='P'"));
			ep.setUid(Uid);
			ep.setEditime(new Date());
			ep.setTitle("index");
			updateObject(user);
			updateObject(ep);
			//System.out.println("UPDATE Eps_pages SET content='"+ezGetString("SELECT template FROM Eps_template WHERE type='P'")+"' WHERE Oid="+ep.getOid());
			executeSql("UPDATE Eps_pages SET content='"+ezGetString("SELECT template FROM Eps_template WHERE type='P'")+"' WHERE Oid="+ep.getOid());
		}
		
	}
	
	/**
	 * 以List中的key進行排序
	 */
	public List sortListByKey(List list, final String key) {
		Collections.sort(list, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String s1 = o1.get(key).toString();
                String s2 = o2.get(key).toString();
                if (s1 == null && s2 == null) {
                    return 0;
                } else if (s1 == null) {
                    return -1;
                } else if (s2 == null) {
                    return 1;
                } else {
                    return s1.compareTo(s2);
                }
            }
        });
		return list;
	}
	
	public List sortListByKeyDESC(List list, final String key){
		
		Collections.sort(list, new Comparator<Map>() {
            public int compare(Map o1, Map o2) {
                String s1 = o1.get(key).toString();
                String s2 = o2.get(key).toString();
                if (s1 == null && s2 == null) {
                    return 0;
                } else if (s1 == null) {
                    return -1;
                } else if (s2 == null) {
                    return 1;
                } else {
                    return s2.compareTo(s1);
                }
            }
        });
		return list;
	}
	
	/**
	 * 學習歷程網址1
	 */
	public String myPortfolioUrl(String Uid) {		
		return ezGetString("SELECT Value FROM Parameter WHERE name='HTTP' AND category='portfolio'")+"myPortfolio?path="+ezGetString("SELECT path FROM Eps_user WHERE Uid='"+Uid+"'");
	}
	
	/**
	 * 檔案上傳伺服器
	 */
	public String myPortfolioFtp() {
		return ezGetString("SELECT Value FROM Parameter WHERE name='FTPServer' AND category='portfolio'");
	}
	
	/**
	 * 檔案瀏覽路徑
	 */
	public String myPortfolioFtpClient() {
		return ezGetString("SELECT Value FROM Parameter WHERE name='FTPClient' AND category='portfolio'");
	}
	
	/**
	 * 上傳路徑
	 */
	public String FTPServerPath() {		
		return ezGetString("SELECT Value FROM Parameter WHERE name='FTPServerPath' AND category='portfolio'");
	}
	
	/**
	 * 以身份別取得帳號
	 */
	public String getUid(UserCredential c) {
		//String Uid;
		System.out.println(c.getMember().getPriority());
		
		
		return null;
	}
	
	/**
	 * 沒有建立時所要做的動作
	 */
	public boolean portfolioExist(String Uid) {
		if(ezGetInt("SELECT COUNT(*)FROM Eps_user WHERE Uid='"+Uid+"'")>0){			
			return true;
		}
		return false;
	}
	
	/**
	 * 取得1位學生的學程資格
	 */
	public List CsGroup4One(String student_no, Map aStudent, boolean staff) {		
		int schoolYear=getSchoolYear();//現在學年	
		//TODO 實際年度的正確度待驗證
		try{//入學年取得
			StringBuilder sb=new StringBuilder(aStudent.get("entrance").toString());
			aStudent.put("inYear", sb.delete(sb.length()-2, sb.length()).toString());
		}catch(Exception e){//取得錯誤立即給予最近4年
			aStudent.put("inYear", schoolYear-4);
		}
		
		Map sMap=aStudent;
		aStudent=new HashMap();
		
		aStudent.put("student_no", sMap.get("student_no"));
		aStudent.put("depart_class", sMap.get("depart_class"));
		aStudent.put("SchoolNo", sMap.get("SchoolNo"));
		
		if(sMap.get("inYear").equals("")||sMap.get("inYear")==null){
			aStudent.put("inYear", schoolYear-4);
		}else{
			aStudent.put("inYear", sMap.get("inYear"));
		}		
		aStudent.put("DeptNo", sMap.get("DeptNo"));		
		//此生及格標
		int pa=getPassScore(aStudent.get("depart_class").toString(), aStudent.get("student_no").toString());		
		//所有學程
		List biGroup=ezGetBy("SELECT cg.*, cg.Oid as cgOid, cgr.*, cgr.major as major1 FROM CsGroup cg, CsGroupRule cgr WHERE " +
				"cg.Oid=cgr.group_oid AND cgr.schoolNo='"+aStudent.get("SchoolNo")+"' AND " +
				"(cg.entrance>"+aStudent.get("inYear")+" OR cg.entrance='' OR cg.entrance is null) AND " +
				"(cgr.entrance>"+aStudent.get("inYear")+" OR cgr.entrance='' OR cgr.entrance is null) GROUP BY cg.Oid");//大圈		
		//學生成績
		List myScore=ezGetBy("SELECT sh.evgr_type, sh.score, sh.credit, sh.cscode, sh.opt, c.DeptNo " +
				"FROM ScoreHist sh, Class c WHERE c.ClassNo=sh.stdepart_class AND " +
				"sh.cscode !='99999' AND sh.student_no='"+aStudent.get("student_no")+"' AND sh.score>="+pa);
		
		List smallGroupMajor;//必修小圈
		List smallGroupMinor;//選修小圈	
		
		for(int i=0; i<biGroup.size(); i++){//跑大圈
			float opt1=0; //必修已得
			float opt2=0; //選修已得
			float outOpt=0; //外系已得
			
			float major=0;
			float minor=0;//選修應修
			
			//取專業必修
			smallGroupMajor=ezGetBy("SELECT cgs.*, c.chi_name, c5.name as deptName FROM " +
					"CsGroupSet cgs, Csno c, code5 c5 WHERE " +
					"cgs.cscode=c.cscode AND cgs.opt='1' AND " +
					"c5.category='Dept' AND c5.idno=cgs.deptNo AND " +
					"cgs.group_oid='"+((Map)biGroup.get(i)).get("cgOid")+"'");
			
			for(int j=0; j<smallGroupMajor.size(); j++){
				((Map)smallGroupMajor.get(j)).put("igot", false);//預設取得為 false
				major=Float.parseFloat(((Map)smallGroupMajor.get(j)).get("credit").toString());//預先轉學程學分
				//多重抵免因此不移除得到的
				for(int k=0; k<myScore.size(); k++){
					float myScoreCredit=Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());//預先轉成績學分					
					if(!((Map)myScore.get(k)).get("evgr_type").equals("6")){//非抵免
						if(						
							((Map)myScore.get(k)).get("cscode").equals(((Map)smallGroupMajor.get(j)).get("cscode"))&&//代碼相同
							((Map)myScore.get(k)).get("DeptNo").equals(((Map)smallGroupMajor.get(j)).get("deptNo"))&&//系所相同
							myScoreCredit>=major//學分數大於等於
						){	
							opt1=opt1+major; //專業必修++
							((Map)smallGroupMajor.get(j)).put("igot", true);//設為true
							//計算外系課程
							if(!aStudent.get("DeptNo").equals(((Map)smallGroupMajor.get(j)).get("deptNo"))){//外系課程
								//必修外系不算20140618
								outOpt=outOpt+major;//外系課程++
							}
						}						
						
					}else{//抵免課程不比開課系						
						if(						
							((Map)myScore.get(k)).get("cscode").equals(((Map)smallGroupMajor.get(j)).get("cscode"))&&//代碼相同即可
							myScoreCredit>=major//學分數大於等於
						){						
							//System.out.println("必修+1");
							opt1=opt1+major;//專業必修++
							((Map)smallGroupMajor.get(j)).put("igot", true);
							//計算外系課程
							if(!aStudent.get("DeptNo").equals(((Map)smallGroupMajor.get(j)).get("deptNo"))){//外系課程
								//必修外系不算20140618
								outOpt=outOpt+major;//外系課程++
							}
						}
					}				
				}
			}
			
			
			
			//取專業選修
			smallGroupMinor=ezGetBy("SELECT cgs.*, c.chi_name, c5.name as deptName FROM " +
					"CsGroupSet cgs, Csno c, code5 c5 WHERE " +
					"cgs.cscode=c.cscode AND cgs.opt='2' AND " +
					"c5.category='Dept' AND c5.idno=cgs.deptNo AND " +
					"cgs.group_oid='"+((Map)biGroup.get(i)).get("cgOid")+"'");
			
			for(int j=0; j<smallGroupMinor.size(); j++){
				((Map)smallGroupMinor.get(j)).put("igot", false);
				
				minor=Float.parseFloat(((Map)smallGroupMinor.get(j)).get("credit").toString());//預先轉學程學分
				//多重抵免因此不移除得到的
				for(int k=0; k<myScore.size(); k++){
					float myScoreCredit=Float.parseFloat(((Map)myScore.get(k)).get("credit").toString());//預先轉成績學分
					if(							
							((Map)myScore.get(k)).get("cscode").equals(((Map)smallGroupMinor.get(j)).get("cscode"))&&//代碼相同
							((Map)myScore.get(k)).get("DeptNo").equals(((Map)smallGroupMinor.get(j)).get("deptNo"))&&//系所相同
							myScoreCredit>=minor//學分數大於等於
					){	
						//System.out.println("選修+1");
						opt2=opt2+minor;//專業必修++
						((Map)smallGroupMinor.get(j)).put("igot", true);
						//計算外系課程
						if(!aStudent.get("DeptNo").equals(((Map)smallGroupMinor.get(j)).get("deptNo"))){//外系課程
							outOpt=outOpt+minor;//外系課程++
						}
					}
				}
			}
			
			((Map)biGroup.get(i)).put("smallGroupMajor", smallGroupMajor);
			((Map)biGroup.get(i)).put("smallGroupMinor", smallGroupMinor);			
			((Map)biGroup.get(i)).put("opt1", opt1);
			((Map)biGroup.get(i)).put("opt2", opt2);
			((Map)biGroup.get(i)).put("optOut", outOpt);
			((Map)biGroup.get(i)).put("student_no", student_no);
			//System.out.println(major);
			//((Map)biGroup.get(i)).put("major", major);//必修應修
			//((Map)biGroup.get(i)).put("major", minor);//選修應修
			//((Map)biGroup.get(i)).put("optOut", outOpt);//外系排除
		}
		
		return biGroup;		
	}
	
	/**
	 * SSH修改遠端主機使用者密碼進行單一簽入
	 * 條件1: 遠端已建立sheelscript
	 * 條件2: 要有root權限
	 */
	public boolean SSHChangeMailPassword4One(String host, String account, 
			String password, String newAccount, String newPassword, String ChAccount,
			String ChPassword) {
		
		try {
			// 設定連線方式
			//TODO 其它主機們
			System.out.println(host);
			SSHClient ssh=new SSHClient(host, account, password);			
			
			System.out.println("以 "+account+" 帳號登入成功");
			System.out.println("準備更換使用者: "+newAccount+", 密碼為 "+newPassword);
			ssh.switchUser(newAccount, newPassword);
			System.out.println("已更換使用者: "+newAccount);			
			System.out.println("準備更新帳號: "+ChAccount+", 密碼為: "+ChPassword);
			ssh.execute("./cp "+ChAccount+" "+ChPassword);		
			ssh.close();			
			
			//bot.switchUser("CISADMIN", "chit!@#", "bash-3.2# ");
			System.out.println("結束連線");
			
			if(ssh.getLastOutput().indexOf("successfully")<0){//更新失敗
				System.out.println("更改未生效, 因為密碼不符合系統規定");
				return false;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		
		return true;
	}
	
	public void clearPo(Object po){		
		courseDao.clearOne(po);
	}
	
	/**
	 * 以學期取得一名學生的選課
	 */
	public List getCs4Std(String student_no, String Sterm) {
		
		List list=ezGetBy("SELECT s.student_no, st.student_name, c.chi_name, " +
				"c.cscode, d.opt, d.credit, d.thour, e.cname, d.Oid " +
				"FROM empl e, Seld s, Dtime d, Csno c, stmd st WHERE e.idno=d.techid AND " +
				"d.Oid=s.Dtime_oid AND st.student_no=s.student_no AND c.cscode=d.cscode AND " +
				"s.student_no='"+student_no+"' AND d.Sterm='"+Sterm+"'");
		for(int i=0; i<list.size(); i++){
			
			((Map)list.get(i)).put("stdSelected", ezGetInt(" " +
					"SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+((Map)list.get(i)).get("Oid")+"'")
			);
		}		
		return list;
	}
	
	/**
	 * 尋找在職或離職員工
	 */
	public Map getEmplOrDempl(String idno) {
		Map map=jdbcDao.ezGetMap("SELECT * FROM empl WHERE idno LIKE'"+idno+"%'");
		if(map==null){
			map=jdbcDao.ezGetMap("SELECT * FROM Dempl WHERE idno LIKE'"+idno+"%'");
		}
		return map;
	}
	
	/**
	 * 裁切照片並暫存server
	 */
	public boolean uploadImage2APServer(FormFile uplFile, String baseDir, String idno){
		//System.out.println(uplFile.getContentType());
		if(uplFile.getContentType().indexOf("image")<0){
			return false;
		}
		String newFileName="";
		InputStream stream=null;
		OutputStream bos;
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		File savePath = new File(baseDir);			
		// 資料夾不存在立即建立
		if (!savePath.exists()) {
			savePath.mkdir();
		}		
		
		try{
			stream = uplFile.getInputStream();			
			newFileName=baseDir+"/"+idno+".jpg";
			bos=new FileOutputStream(newFileName);
			uplFile.getInputStream();			
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
		}catch(Exception e){			
			e.printStackTrace();
			bos=null;
			return false;
		}
		
		BufferedImage srcImage, destImage;
		double width, height;
		Graphics2D g2d;		
		int size=139;		
		try {
			srcImage = ImageIO.read(new File(newFileName));
			width=srcImage.getWidth();
		    height=srcImage.getHeight(); 
			if(width>height){
				return false;
			}
			if(width>size){			    	
				double y=size/width;
				height=height*y;	
		    	destImage = new BufferedImage(size, (int) height, BufferedImage.TYPE_INT_RGB);
			    g2d = (Graphics2D) destImage.getGraphics();			    
			    g2d.scale(y, y);		    
			    g2d.drawImage(srcImage,0,0,null);			
			    ImageIO.write(destImage, "jpg", new File(newFileName));
			    g2d.dispose();
			    srcImage.flush();
			    destImage.flush();
			    System.gc();
			}else{				
			    srcImage.flush();			    
			    System.gc();
				return false;
			}		
		}catch (IOException e1) {
			bos=null;
			g2d=null;
		    System.gc();
		    destImage=null;
		    srcImage=null;
			e1.printStackTrace();
			return false;
		}		
		return true;		
	}
	
	/**
	 * 將已裁切學生照片上傳至Ftp
	 * TODO 目前沒有FTP伺服器
	 */
	public boolean uploadImage2FTPServer(String baseDir, String fileName){		
		String FTPHost=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='host'");
		String username=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='username'");
		String password=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='password'");
		String ServerDir=ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='folder'");
		
		//System.out.println("fileName="+fileName);
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");			
			ftp.connect();
			ftp.setLocalDir(baseDir+"/");
			ftp.setServerDir(ServerDir+"/");						
			ftp.setBinaryTransfer(true);
			ftp.put(fileName, true);
			ftp.disconnect();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			//ftp.disconnect();
			return false;
		}		
	}
	
	/**
	 * 會議/法規資料上傳
	 * server_dir=FN_Name
	 * fileName 目前檔案名稱
	 */
	public boolean uploadFileFTPServer(FormFile uplFile, String baseDir, String fileName, String server_tmp, String ftpDir){		
		InputStream stream=null;
		OutputStream bos;
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		File savePath = new File(baseDir);			
		// 資料夾不存在立即建立
		if (!savePath.exists()) {
			savePath.mkdir();
		}		
		
		try{
			stream = uplFile.getInputStream();
			bos=new FileOutputStream(baseDir+"/"+uplFile.getFileName());
			uplFile.getInputStream();			
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
		}catch(Exception e){			
			e.printStackTrace();
			bos=null;
			return false;
		}
		
		String FTPHost= ""; 
		String username= "";
		String password= "";
		//String ServerDir= "/home/kk/ftp/File/"+FN_Name;
		String ServerDir="/home/mysql/cisftp/LawMeeting/"+ftpDir+"/";
		//System.out.println(ServerDir);
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");
			ftp.connect();
			
			ftp.setLocalDir(baseDir);
			ftp.setServerDir(ServerDir);						
			ftp.setBinaryTransfer(true);
			ftp.putFile(uplFile.getFileName(), "", true);		//false	
			return true;
		}catch(Exception e){
			e.printStackTrace();
			//ftp.disconnect();
			return false;
		}		
	}
	
	/**
	 * 會議/法規資料下載
	 * 資料位置URL=fileName
	 * 檔案名稱=DataName
	 * APServer位置 = baseDir
	 * 
	 */
	public boolean DownloadFileFTPServer(String fileName, String DataName, String baseDir){				
		String FTPHost= "";  
		String username= "";
		String password= "";
		String ServerDir= fileName;
		
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");
			ftp.connect();
			
			ftp.setLocalDir(baseDir);
			ftp.setServerDir(ServerDir+"/");						
			ftp.setBinaryTransfer(true);
			
			ftp.getFile(DataName, false);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();			
			return false;
		}		
	}
	
	/**
	 * 中文轉換星期幾
	 */
	public String ChWeekOfDay(int w, String front){
		if(front==null)front="";		
		String week[]={"", "日", "一","二","三","四","五","六"};	
		return front+week[w];
	}
	
	public String getWeekChar(int index){		
		String week[]={"","一","二","三","四","五","六","日"};		
		return week[index];
	}
	
	/**
	 * 將陣列元素轉換西元或民國格式
	 */
	public List changeListDate(List list, String[] key) {
		for(int i=0; i<list.size(); i++){			
			for(int j=0; j<key.length; j++){				
				if(((Map)list.get(i)).get(key[j])!=null)
				try{
					((Map)list.get(i)).put(key[j], convertDate( ((Map)list.get(i)).get(key[j]).toString()        ));
				}catch(Exception e){
					e.printStackTrace();
				}				
			}
		}		
		return list;
	}
	
	/**
	 * 畢業證共用資訊
	 * 2011/1/21要求改用阿拉伯數字
	 */
	public Map getADiplomaInfo(String student_no){
		
		Map map;
		Map aboutClass;
		try{
			map=jdbcDao.ezGetMap("SELECT * FROM stmd WHERE student_no='"+student_no+"' LIMIT 1");
		}catch(Exception e){
			map=jdbcDao.ezGetMap("SELECT * FROM Gstmd WHERE student_no='"+student_no+"' LIMIT 1");
		}
		
		try{
			aboutClass=jdbcDao.ezGetMap("SELECT c5.name as deptName, c.ClassName FROM stmd s, code5 c5, Class c WHERE " +
					"s.depart_class=c.ClassNo AND c5.idno=c.DeptNo AND c5.category='Dept'AND student_no='"+map.get("student_no")+"'");
		}catch(Exception e){
			aboutClass=jdbcDao.ezGetMap("SELECT c5.name as deptName, c.ClassName FROM Gstmd s, code5 c5, Class c WHERE " +
					"s.depart_class=c.ClassNo AND c5.idno=c.DeptNo AND c5.category='Dept'AND student_no='"+map.get("student_no")+"'");
		}		
		
		if(map==null){
			return null;
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//格式化生日
		Calendar c=Calendar.getInstance();
		
		Map student=new HashMap();
		student.put("student_no", map.get("student_no"));
		student.put("student_name", map.get("student_name"));
		student.put("idno", map.get("idno"));
		
		if(getSchoolTerm()>1){
			student.put("month", "6");
		}else{
			student.put("month", "1");
		}
		
		c.setTime(new Date());
		student.put("year", c.get(Calendar.YEAR)-1911);
		try{
			student.put("showDeptName", ezGetString("SELECT fname FROM dept WHERE no='"+map.get("depart_class").toString().substring(0, 4)+"'"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			c.setTime(sf.parse(map.get("birthday").toString()));
		} catch (ParseException e) {
			// 沒有輸入生日的情形
			//c.setTime(new Date());
		}
		
		student.put("birth_year", String.valueOf(c.get(Calendar.YEAR)-1911));
		student.put("birth_month", String.valueOf(c.get(Calendar.MONTH)+1));
		student.put("birth_day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
		
		student.put("occur_year",  map.get("occur_year"));
		student.put("occur_term",  map.get("occur_term"));
		
		student.put("deptName", aboutClass.get("deptName"));
		
		if(map.get("divi")!=null && !map.get("divi").equals("")){			
			student.put("diviName", "　　　"+jdbcDao.ezGetString("SELECT c5.name FROM code5 c5 WHERE " +
					"c5.category='Group' AND c5.idno='"+map.get("divi")+"'"));
		}else{
			student.put("diviName", "　　　組");
		}
		
		student.put("title", getDegreeType(jdbcDao.ezGetString("SELECT DeptNo FROM Class WHERE " +
		"ClassNo='"+map.get("depart_class")+"'").charAt(0), map.get("depart_class").toString()));
		
		student.put("years", getGradyear(aboutClass.get("ClassName").toString()));
		
		return student;
	}
	
	private String getDegreeType(char dept, String depart_class){		
		//2010/10/15日間部註冊要求增加11G7
		if(depart_class.toString().indexOf("1327")>-1 || depart_class.toString().indexOf("11G7")>-1){
			return "管理學";
		}
		
		if(depart_class.toString().indexOf("G5")>-1){
			return "建築學";
		}
		
		//20116/2進修部要求改健康科技研究所為理學
		if(depart_class.toString().indexOf("GH")>-1){
			return "理學";
		}
		
		switch (dept){
			case '1': return "工學";			
			case '2': return "工學";
			case '3': return "工學";
			case '4': return "管理學";
			case '5': return "工學";
			case '6': return "工學";
			case '7': return "商學";
			case '8': return "商學";
			case '9': return "商學";
			case 'A': return "工學";
			case 'B': return "工學";
			case 'C': return "商學";
			case 'D': return "商學";
			case 'E': return "工學";
			case 'F': return "農學";
			case 'G': return "教育學";
			case 'H': return "工學";
			case 'I': return "管理學";
			case 'J': return "管理學";
			case 'K': return "商學";
			case 'V': return "工學";
			case 'N': return "商學";
		default:
			return "未定義";
		}
	}
	
	/**
	 * 取組別
	 * @param divi
	 * @return
	 */
	private String getDivi(String divi){		
		String result="";		
		if(divi!=null && divi.trim().length()>0){
			
			result=ezGetString("SELECT name FROM code5 WHERE idno='"+divi+"' AND category='Group'");
		}		
		return result;
	}
	
	/**
	 * 取學制
	 * TODO 早期以動態運算方式取得學制, 現已在班級資料表中記載
	 * @param classNo
	 * @return
	 */	
	private String getGradyear(String SchoolName){
		Map map=new HashMap();
		if(SchoolName.indexOf("四技")!=-1){
			return "四";
		}
		if(SchoolName.indexOf("二技")!=-1){
			return "二";
		}
		if(SchoolName.indexOf("二專")!=-1){
			return "二";
		}
		if(SchoolName.indexOf("碩")!=-1){
			return "二";
		}
		if(SchoolName.indexOf("學院")!=-1){
			return "二";
		}
		if(SchoolName.indexOf("專校")!=-1){
			return "二";
		}
		
		if(SchoolName.indexOf("四")!=-1){
			return "四";
		}
		
		if(SchoolName.indexOf("二")!=-1){
			return "二";
		}		
		return "某";
	}
	
	/**
	 * 電子郵件格式驗證
	 * @param address
	 * @return
	 */
	public boolean validateEmail(String address){		
		String regex="[a-zA-Z][\\w_]+@\\w+(\\.\\w+)+";  
		Pattern p=Pattern.compile(regex);  
		Matcher m=p.matcher(address);		
		return m.matches();   
	}
	
	/**
	 * 取得行事曆
	 */
	public List getSelfCalendar(Date start, Date end, String account) throws ParseException{
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		Calendar c=Calendar.getInstance();
		List list=new ArrayList();
		List subList;
		Map map;
		
		c.setTime(start);		
		//SimpleDateFormat sf1=new SimpleDateFormat("yyy年M月d日");		
		Date d;
		while(!c.getTime().after(end)){
			//System.out.println( sf.format(end) );
			map=new HashMap();			
			//公共與個人1次取得
			subList=ezGetBy("SELECT * FROM Calendar WHERE (account='"+account+"'||account='all')AND "+"begin LIKE'"+sf.format(c.getTime())+"%' ORDER BY begin");			
			map.put("cs", subList);			
			//日期資訊
			d=sf.parse(convertDate(sf.format(c.getTime())));
			map.put("realDate", sf.format(c.getTime()));			
			map.put("day", c.get(Calendar.DAY_OF_MONTH));
			map.put("month", c.get(Calendar.MONTH)+1);			
			map.put("myWeekDay", ChWeekOfDay(c.get(Calendar.DAY_OF_WEEK), null));
			map.put("weekDay", c.get(Calendar.DAY_OF_WEEK));
			
			list.add(map);
			c.add(Calendar.DAY_OF_MONTH, 1);			
		}		
		return list;
	}
	
	/**
	 * 將FormFile轉FileDataSource
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public FileDataSource FormFile2FileDataSource(FormFile file){		
		byte[] buffer = new byte[8192];
		int bytesRead = 0;		
		String fileHome="/home/tmp/";		
		File home=new File(fileHome);
		home.mkdirs();
		
		File f1=new File(fileHome+file.getFileName());
		try{
			InputStream stream  =  file.getInputStream();			 
			OutputStream bos=new FileOutputStream(f1);	 
			file.getInputStream();
			
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			FileDataSource f=new FileDataSource(f1);
			stream.close();
			bos.close();		
			return f;
		}catch(Exception e){
			return null;
		}finally{
			home.delete();
		}		
	}
	
	/**
	 * 星期轉換
	 */
	public String WeekOfDay(int w){	
		switch (w){
			case 1: return "7";
			case 2: return "1";
			case 3: return "2";
			case 4: return "3";
			case 5: return "4";
			case 6: return "5";
			case 7: return "6";		
			default: return "?";
		}
	}
	
	/**
	 * 儲存新行事曆
	 */
	public void saveNewCalendar(String account, PubCalendar c){
		//自己不用
		if(!c.getAccount().equals(account)){
			PubCalendar n;
			n=new PubCalendar();
			n.setAccount(account);
			n.setColor(c.getColor());			
			n.setMembers(c.getMembers());
			n.setName(c.getName());
			n.setNote(c.getNote());
			n.setPlace(c.getPlace());			
			n.setType(c.getType());
			n.setNo(c.getNo());
			n.setSender(c.getSender());			
			n.setBegin(c.getBegin());
			n.setEnd(c.getEnd());			
			updateObject(n);
		}	
	}
	
	/**
	 * 分析字串中的同仁
	 * empls=分析出的同仁
	 * lost=無法分析的同仁
	 * @return
	 */
	public Map analyseEmpl(String members){
		String members1=new String(members);
		members1=members1.replaceAll("[a-zA-Z\\s\\p{Punct}‘’]*", "");//刪除英文字母, 標點
		members1=members1.replaceAll("、", "");//刪除, 標點(正規表示不支援中文標點)
		members1=members1.replaceAll("，", "");//刪除, 標點
		members1=members1.replaceAll("；", "");//刪除, 標點
		
		//移除坑爹的職稱加在姓後面才名字
		//List jobList=ezGetBy("SELECT name FROM CodeEmpl c WHERE c.category IN('EmplRoleDirector', 'EmplRoleStaff', 'TeacherRole')ORDER BY Oid DESC");
		//不想理
		//for(int i=0; i<jobList.size(); i++){
			//members1=members1.replaceAll(((Map)jobList.get(i)).get("name").toString(), "");
		//}
		List list=ezGetBy("SELECT e.idno, e.cname, e.Email, c.name as uname FROM empl e LEFT OUTER JOIN CodeEmpl c ON e.unit=c.idno AND " +
				"(c.category='UnitTeach' OR c.category='Unit') WHERE e.Email IS NOT NULL AND e.Email!=''");//只以教職員為範本
		List tmp=new ArrayList();
		for(int i=0; i<list.size(); i++){
			
			if(members1.indexOf(((Map)list.get(i)).get("cname").toString())>=0){//分析出這個人了				
				tmp.add(list.get(i));
				members1=members1.replaceAll( ((Map)list.get(i)).get("cname").toString(), "");//刪除已分析字元
			}
			//if(((Map)list.get(i)).get("nickname")!=null){//分析別名
				//if(members1.indexOf(((Map)list.get(i)).get("nickname").toString())>=0){//分析出這個人了
					//tmp.add(list.get(i));
					//members1=members1.replaceAll( ((Map)list.get(i)).get("nickname").toString(), "");//刪除已分析字元
				//}
			//}
		}
		
		Map map=new HashMap();
		map.put("empls", tmp);
		map.put("lost", members1);
		return map;
	}
	
	public List analyseEmplToList(String members){
		String members1=new String(members);
		members1=members1.replaceAll("[a-zA-Z\\s\\p{Punct}‘’]*", "");//刪除英文字母, 標點
		members1=members1.replaceAll("、", "");//刪除, 標點(正規表示不支援中文標點)
		members1=members1.replaceAll("，", "");//刪除, 標點
		members1=members1.replaceAll("；", "");//刪除, 標點
		List list=ezGetBy("SELECT e.idno, e.cname, e.Email FROM empl e WHERE e.Email IS NOT NULL AND e.Email!=''");//只以教職員為範本
		List tmp=new ArrayList();
		for(int i=0; i<list.size(); i++){			
			if(members1.indexOf(((Map)list.get(i)).get("cname").toString())>=0){//分析出這個人了				
				tmp.add(list.get(i));
				members1=members1.replaceAll( ((Map)list.get(i)).get("cname").toString(), "");//刪除已分析字元
			}
		}
		list=null;
		return tmp;
	}
	
	/**
	 * 寄送校園行事曆邀請
	 */
	public Map createPubCalendar(String members, String account, String beginDate, String beginTime, String place, String name, String note, PubCalendar c, FormFile addFile){		
		account=ezGetString("SELECT cname FROM empl WHERE idno='"+account+"'");
		members=members+", "+account;//加上自己		
		Date adate=new Date();		
		Map map=getCISMailServerInfo();
		InternetAddress addr;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(beginTime.equals("")||beginTime==null){
			beginTime="00:00";
		}
		
		/*
		if(endTime.equals("")||endTime==null){
			endTime="00:00";
			Calendar ca=Calendar.getInstance();
			ca.setTime(c.getBegin());
			ca.add(Calendar.DAY_OF_YEAR, 1);
			endDate=sf.format(ca.getTime());
		}
		*/
		
		
		StringBuilder sb=new StringBuilder("<p>您好</p>");
		sb.append("您已被"+account+"邀請參加「"+name+"」。<br>");
		sb.append("於"+beginDate+" "+beginTime+"， 至 "+place+"。<br><br>");
		sb.append(note+"<br><br>");
		sb.append("受邀者包括: "+members.substring(0, members.length()-4));
		
		int s=0;		
		List tmp=(List) ((Map)analyseEmpl(members)).get("empls");		
		if(tmp.size()>0){
			InternetAddress address[] = new InternetAddress[tmp.size()];
			for(int i=0; i<tmp.size(); i++){				
				try{
					addr=new InternetAddress(((Map)tmp.get(i)).get("Email").toString(), ((Map)tmp.get(i)).get("cname").toString(), "BIG5");
					address[i]=addr;					
					saveNewCalendar(((Map)tmp.get(i)).get("idno").toString(), c);
					s=s+1;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			//處理附件夾檔
			FileDataSource[] file;
			if(addFile!=null){
				file=new FileDataSource[]{FormFile2FileDataSource(addFile)};
			}else{
				file=null;
			}
			
			sendMail(
			map.get("username").toString(), 
			map.get("password").toString(), 
			map.get("mailServer").toString(), 
			map.get("mailAddress").toString(), 
			"校園行事曆", 
			adate, 
			name, 
			sb.toString(), 
			address, 
			file);
			
			try {
				saveMail(account, sf.format(adate), "行事曆通知: "+name, address, sb.toString(), sf.parse(beginDate), map.get("mailAddress").toString());
			} catch (ParseException e) {
				//e.printStackTrace();
			}
		}	
		
		map=new HashMap();
		map.put("count", s);
		map.put("lost", ((Map)analyseEmpl(members)).get("lost"));//無法分析的同仁
		return map;
	}
	
	/**
	 * 刪除行事曆
	 */
	public Map deleteCalendar(String members, String account, String date, String time, String place, String name, String note){		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		account=ezGetString("SELECT cname FROM empl WHERE idno='"+account+"'");
		members=members+", "+account;
		String members1=new String(members);//要先過濾名單中不具意義的字元
		members1=members1.replaceAll("[a-zA-Z\\s\\p{Punct}‘’]*", "");//刪除英文字母, 標點
		members1=members1.replaceAll("、", "");//刪除, 標點
		members1=members1.replaceAll("，", "");//刪除, 標點
		members1=members1.replaceAll("；", "");//刪除, 標點
		
		List jobList=ezGetBy("SELECT name FROM CodeEmpl c WHERE c.category IN('EmplRoleDirector', 'EmplRoleStaff', 'TeacherRole')ORDER BY Oid DESC");
		
		for(int i=0; i<jobList.size(); i++){
			members1=members1.replaceAll(((Map)jobList.get(i)).get("name").toString(), "");
		}		
		
		Date adate=new Date();
		List list=ezGetBy("SELECT idno, nickname, cname, Email FROM empl WHERE Email IS NOT NULL AND Email!=''");//只以教職員為範本
		//寄送取消通知
		Map map=getCISMailServerInfo();
		InternetAddress addr;
		
		StringBuilder sb=new StringBuilder("<p>您好</p>");
		sb.append(account+"已取消「"+name+"」。<br>");
		sb.append("於"+date+" "+time+"， 在 "+place+" 的活動。<br><br>");
		sb.append(note+"<br><br>");
		sb.append("受邀者包括: "+members.substring(0, members.length()-4));
		
		int s=0;
		
		List tmp=new ArrayList();
		for(int i=0; i<list.size(); i++){			
			if(members1.indexOf(((Map)list.get(i)).get("cname").toString())>=0){//分析出這個人了
				tmp.add(list.get(i));
				members1=members1.replaceAll( ((Map)list.get(i)).get("cname").toString(), "");//刪除已分析字元
			}
			if(((Map)list.get(i)).get("nickname")!=null){//分析別名
				if(members1.indexOf(((Map)list.get(i)).get("nickname").toString())>=0){//分析出這個人了
					tmp.add(list.get(i));
					members1=members1.replaceAll( ((Map)list.get(i)).get("nickname").toString(), "");//刪除已分析字元
				}
			}
		}
		
		if(tmp.size()>0){
			InternetAddress address[] = new InternetAddress[tmp.size()];
			for(int i=0; i<tmp.size(); i++){				
				try{
					addr=new InternetAddress(((Map)tmp.get(i)).get("Email").toString(), ((Map)tmp.get(i)).get("cname").toString(), "BIG5");
					address[i]=addr;					
					//saveNewCalendar(((Map)tmp.get(i)).get("idno").toString(), c);
					s=s+1;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			//轉檔嗎？			
			sendMail(
			map.get("username").toString(), 
			map.get("password").toString(), 
			map.get("mailServer").toString(), 
			map.get("mailAddress").toString(), 
			"校園行事曆", 
			adate, 
			"行事曆取消通知"+name, 
			sb.toString(), 
			address, 
			null);
			
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				saveMail(account, sf.format(adate), "行事曆取消通知: "+name, address, sb.toString(), sf.parse(date), map.get("mailAddress").toString());
			} catch (ParseException e) {
				//e.printStackTrace();
			}
		}
		
		map=new HashMap();
		map.put("count", s);
		map.put("lost", members1);		
		return map;
	}
	
	/**
	 * 重複選課查核 (歷年) 
	 */
	public List checkReSelected(String classLess){
		
		Map map;
		List tmp;
		List list;			
		list=ezGetBy("SELECT st.student_no, st.student_name, cl.ClassName " +
				"FROM stmd st, Class cl WHERE st.depart_class=cl.ClassNo AND st.depart_class LIKE '"+classLess+"%'");
		List reSelecTmp;
		List reSelect=new ArrayList();
		for(int i=0; i<list.size(); i++){
			//取比對標準(一個人的成績和計次，排序為小、為前)
			tmp=ezGetBy("SELECT COUNT(*) as countTime, sh.cscode, sh.score, c.chi_name FROM " +
					"ScoreHist sh, Csno c WHERE c.cscode=sh.cscode AND student_no='"+((Map)list.get(i)).get("student_no")+"' AND (sh.score>=60 OR sh.score IS NULL) " +
					//"AND sh.evgr_type='1' " +
					"GROUP BY sh.cscode ORDER BY sh.school_year");
			
			for(int j=0; j<tmp.size(); j++){
				try{//若個數不為1，且不為操行99999，且及格
					
					if(((Map)tmp.get(j)).get("score")==null){//TODO 及格標
						((Map)tmp.get(j)).put("score", "60");
					}
					
					if(!((Map)tmp.get(j)).get("countTime").toString().equals("1")&& //多於1
					!((Map)tmp.get(j)).get("cscode").toString().equals("99999")&& //不為99999
					
					Float.parseFloat(((Map)tmp.get(j)).get("score").toString())>=60.0f){ //若第1次的成績大於及格標
						//特殊規則
						if(((Map)tmp.get(j)).get("chi_name").toString().indexOf("專題")<0){
							
							reSelecTmp=ezGetBy("SELECT c.`chi_name`, sh.student_no, sh.score, sh.school_year, sh.school_term, sh.cscode, c5.name " +
									"FROM code5 c5, ScoreHist sh, Csno c WHERE c5.category='CourseType' AND c5.idno=sh.evgr_type AND c.cscode=sh.cscode AND " +
									"sh.student_no='"+((Map)list.get(i)).get("student_no")+"' AND c.cscode='"+((Map)tmp.get(j)).get("cscode")+"'");
							
							for(int k=0; k<reSelecTmp.size(); k++){
								map=new HashMap();
								map.put("student_no", ((Map)list.get(i)).get("student_no"));
								map.put("student_name", ((Map)list.get(i)).get("student_name"));
								map.put("name", ((Map)reSelecTmp.get(k)).get("name"));
								map.put("chi_name", ((Map)reSelecTmp.get(k)).get("chi_name"));
								map.put("score", ((Map)reSelecTmp.get(k)).get("score"));
								map.put("school_year", ((Map)reSelecTmp.get(k)).get("school_year"));
								map.put("school_term", ((Map)reSelecTmp.get(k)).get("school_term"));
								map.put("cscode", ((Map)reSelecTmp.get(k)).get("cscode"));
								map.put("ClassName", ((Map)reSelecTmp.get(k)).get("ClassName"));
								reSelect.add(map);
							}
						}							
					}					
				}catch(Exception e){
					e.printStackTrace();
				}					
			}				
		}
		return reSelect;
	}
	
	/**
	 * 重複選課查核 (學期) 
	 */
	public List checkReSelectedNow(String classLess, String year, String term){
		Map map;
		List<Map>tmplist;
		List<Map>tmp;
		List<Map>list;
		List<Map>SelectException=new ArrayList();
		tmp=ezGetBy("SELECT c.ClassName, s.student_no, s.student_name FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.depart_class LIKE'"+classLess+"%'");
		
		String cscode;
		//取一人選課
		for(int i=0; i<tmp.size(); i++){	
			list=ezGetBy("SELECT * FROM Seld s, Csno c, Dtime d WHERE s.Dtime_oid=d.Oid AND "
			+ "c.cscode=d.cscode AND d.Sterm='"+term+"' AND s.student_no='"+tmp.get(i).get("student_no")+"'");
			
			for(int j=0; j<list.size(); j++){
				cscode=list.get(j).get("cscode").toString();
				
				//查核當期與當期的重複
				for(int k=0; k<list.size(); k++){//選課列表
					if( list.get(k).get("cscode").equals(cscode) && !list.get(k).get("Oid").equals(list.get(j).get("Oid"))   ){							
						map=new HashMap();
						map.put("student_no", ((Map)tmp.get(i)).get("student_no"));
						map.put("ClassName", ((Map)tmp.get(i)).get("ClassName"));
						map.put("student_name", ((Map)tmp.get(i)).get("student_name"));
						map.put("cscode", cscode);
						map.put("chi_name", "當期重複修: "+((Map)list.get(j)).get("chi_name"));
						map.put("school_year", year);
						map.put("school_term", term);							
						SelectException.add(map);
					}
				}
				
				//改寫上面的SQL
				tmplist=ezGetBy("SELECT * FROM ScoreHist WHERE student_no='"+tmp.get(i).get("student_no")+"' AND " +
						"cscode='"+list.get(j).get("cscode")+"' AND (evgr_type='6' OR score>=60)");
				
				
				if(tmplist.size()>0){					
					map=new HashMap();
					map.put("student_no", tmp.get(i).get("student_no"));
					map.put("student_name", tmp.get(i).get("student_name"));
					map.put("ClassName", tmp.get(i).get("ClassName"));
					map.put("cscode", cscode);						
					map.put("chi_name", "過去曾修: "+((Map)list.get(j)).get("chi_name")+", 在"+reSelected(tmplist));						
					map.put("school_year", year);
					map.put("school_term", term);
					SelectException.add(map);
				}
			}				
		}
		return SelectException;
	}
	
	private String reSelected(List tmplist){
		StringBuilder sb=new StringBuilder();		
		for(int i=0; i<tmplist.size(); i++){
			sb.append(((Map)tmplist.get(i)).get("school_year")+"-"+((Map)tmplist.get(i)).get("school_term")+", "+((Map)tmplist.get(i)).get("score")+"分. ");
		}		
		return sb.toString();
	}
	
	/**
	 * 判斷變更密碼
	 */
	public boolean checkPassUpdate(String username){
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String update;
		Date date;
		try{
			update=ezGetString("SELECT updated FROM wwpass WHERE username='"+username+"'");
			date=sf.parse(update);		
		}catch(Exception e){
			return true;
		}
		Calendar cNow=Calendar.getInstance();
		Calendar cUpd=Calendar.getInstance();
		cNow.setTime(new Date());
		cUpd.setTime(date);
		
		cUpd.add(Calendar.DAY_OF_YEAR, 180);	
		if(cNow.getTime().before(cUpd.getTime())){
			return false;
		}		
		return true;
	}
	
	/**
	 * 檢查教師任課時數上限
	 * type:0 一般課程，不做任何加成
	 * type:1 實習課程，(時數-學分)/2+學分
	 * type:3 專題課程，(班級人數/6)四捨五入)
	 */
	public boolean checkDtimeReserve(String Oid, String idno, String year, String term, float hours, float credit, int selimit, String type) {
		int limit;
		int now=sumDtimeReserve(Oid, idno, year, term);
		try{limit=sumEmplLimit(idno);}catch(Exception e){limit=0;}		
		if(type.equals("0")){if(now+hours>limit){return false;}}		
		return true;
	}
	
	/**
	 * 計算教師任教上限
	 */
	public int sumDtimeReserve(String Oid, String idno, String year, String term) {
		String sql;
		if(Oid==null){sql="SELECT SUM(d.thour) FROM Dtime_reserve_ready d WHERE d.techid='"+idno+"' AND year="+year+" AND term="+term;}else{
		sql="SELECT SUM(d.thour) FROM Dtime_reserve_ready d WHERE d.Oid!="+Oid+" AND d.techid='"+idno+"' AND year="+year+" AND term="+term;}
		try{return Integer.parseInt(jdbcDao.ezGetString(sql));}catch(Exception e){return 0;}
	}

	public int sumEmplLimit(String idno) {
		try{return Integer.parseInt(jdbcDao.ezGetString("SELECT time+time_over FROM Empl_techlimit e WHERE e.idno='"+idno+"'"));}
		catch(Exception e){return 0;}
	}
	
	/**
	 * 檢查課程規劃中的教室衝堂 
	 */
	public List checkRoom(String place, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term){		
		String sql="SELECT cs.className, d.depart_class, dc.place, c.chi_name, dc.week, dc.begin, dc.end FROM Csno c, " +
		"Dtime_reserve_ready d, Dtime_reserve_class dc, Class cs WHERE dc.Dtime_reserve_ready_oid=d.oid AND dc.place='"+place+"' AND dc.week="+week+" AND CAST(dc.begin AS UNSIGNED)<="+beginOrEnd+
		" AND CAST(dc.end AS UNSIGNED)>="+beginOrEnd+" AND d.term="+sterm+" AND d.oid<>"+reserveOid+" AND c.cscode=d.cscode AND " +
				"cs.classNo=d.depart_class AND d.year='"+year+"' AND d.term='"+term+"'";
		try{return ezGetBy(sql);}catch(Exception e){return null;}
	}

	/**
	 * 檢查課程規劃中的教師衝堂 
	 */
	public List checkTeacher(String techid, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term){		
		String sql="SELECT d.techid, d.depart_class, cs.ClassName, c.chi_name, dc.week, dc.begin, dc.end FROM Class cs, Csno c, " +
		"Dtime_reserve_ready d, Dtime_reserve_class dc WHERE dc.Dtime_reserve_ready_oid=d.oid AND d.techid='"+techid+"' " +
		"AND dc.week="+week+" AND dc.begin <="+beginOrEnd+" AND dc.end >="+beginOrEnd+" AND d.term="+sterm+" AND d.Oid<>"+reserveOid+
		" AND c.cscode=d.cscode AND cs.ClassNo=d.depart_class AND d.year='"+year+"' AND d.term='"+term+"'";
		try{return ezGetBy(sql);}catch(Exception e){return null;}
	}
	
	/**
	 * 檢查課程規劃中的固定排課節次衝堂 
	 */
	public List checkClass(String techid, String week, String beginOrEnd, String sterm, String reserveOid, String year, String term){		
		String sql="SELECT d.techid, d.depart_class, cs.ClassName, c.chi_name, dc.week, dc.begin, dc.end FROM Class cs, Csno c, " +
		"Dtime_reserve_ready d, Dtime_reserve_class dc WHERE dc.Dtime_reserve_ready_oid=d.oid AND d.techid='"+techid+"' " +
		"AND dc.week="+week+" AND dc.begin <="+beginOrEnd+" AND dc.end >="+beginOrEnd+" AND d.term="+sterm+" AND d.Oid<>"+reserveOid+
		" AND c.cscode=d.cscode AND cs.ClassNo=d.depart_class AND d.year='"+year+"' AND d.term='"+term+"'";
		try{return ezGetBy(sql);}catch(Exception e){return null;}
	}
	
	/**
	 * 選課清單
	 * @param studentNo
	 * @param request
	 * @return
	 */
	public List getMyCs(String studentNo, String term){		
		List myCs=ezGetBy("SELECT d.Oid, d.Select_Limit, s.Oid as sOid, d.Oid, c.ClassName, cs.chi_name, d.opt, d.credit, d.thour, d.cscode  FROM Seld s, Dtime d, Class c, Csno cs WHERE " +
		"d.cscode=cs.cscode AND d.depart_class=c.ClassNo AND s.Dtime_oid=d.Oid AND d.Sterm='"+term+"' AND s.student_no='"+studentNo+"'");		
		Map map;
		List list=new ArrayList();
		for(int i=0; i<myCs.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)myCs.get(i)).get("Oid"));
			map.put("sOid", ((Map)myCs.get(i)).get("sOid"));
			map.put("Dtime_oid", ((Map)myCs.get(i)).get("Oid"));
			map.put("cscode", ((Map)myCs.get(i)).get("cscode"));
			map.put("ClassName", ((Map)myCs.get(i)).get("ClassName"));
			map.put("chi_name", ((Map)myCs.get(i)).get("chi_name"));
			map.put("opt", Global.CourseOpt.getProperty(((Map)myCs.get(i)).get("opt").toString()));
			map.put("thour", ((Map)myCs.get(i)).get("thour"));
			map.put("credit", ((Map)myCs.get(i)).get("credit"));	
			map.put("Select_Limit", ((Map)myCs.get(i)).get("Select_Limit"));	
			map.put("stdSelect", ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid="+((Map)myCs.get(i)).get("Oid")));
			map.put("dtimeClass", ezGetBy("SELECT DISTINCT ds.week, ds.begin, ds.end, SUBSTRING(ds.place, 2)as place, n.name2 as name, c.name as buildName " +
			"FROM Dtime_class ds LEFT OUTER JOIN Nabbr n ON n.room_id=ds.place LEFT OUTER JOIN code5 c  ON c.category='building' AND c.idno=n.building " +
			"WHERE ds.Dtime_oid='"+((Map)myCs.get(i)).get("Oid")+"'"));
			list.add(map);
		}
		return list;
	}
	
	public void saveWwpass(String username, String password, String priority){
		WwPass w=new WwPass();
		w.setUsername(username);
		w.setPassword(password);
		w.setPriority(priority);
		w.setUpdated(new Date());
		w.setInformixPass(password);
	}
	
	/**
	 * 取學生學分
	 */
	public Map getStdCredit(String student_no){
		
		int pass=60;
		if(student_no.indexOf("G")>-1){
			pass=70;//碩蛋
		}
		Map map=new HashMap();		
		map.put("opt1", ezGetString("SELECT SUM(credit) FROM ScoreHist s WHERE opt='1' AND " +
		"s.student_no='"+student_no+"' AND score>="+pass));
		map.put("opt2", ezGetString("SELECT SUM(credit) FROM ScoreHist s WHERE opt='2' AND " +
		"s.student_no='"+student_no+"' AND score>="+pass));
		map.put("opt3", ezGetString("SELECT SUM(credit) FROM ScoreHist s WHERE opt='3' AND " +
		"s.student_no='"+student_no+"' AND score>="+pass));
		//map.put("CampusNo", ezGetMap("SELECT CampusNo, SchoolNo, "));
		
		
		return map;
	}
	
	/**
	 * 解析簡介
	 * @return
	 */
	public Map parseIntr(String str){
		if(str==null||str.equals("")){return null;}
		
		Map map=new HashMap();	
		String a[] = str.split("#ln;");		
		if(a.length<1){
			map.put("eng", "");
			map.put("chi", "");
			map.put("book", "");
			return map;
		}		
		try{map.put("eng", a[1]);}catch(Exception e){map.put("eng", "無資料");}		
		try{map.put("chi", a[0]);}catch(Exception e){map.put("chi", "無資料");}		
		try{map.put("book", a[2]);}catch(Exception e){map.put("book", "無資料");}		
		return map;
	}
	
	/**
	 * 解析大綱
	 * @return
	 */
	public Map parseSyl(String str){
		if(str==null||str.equals("")){return null;}
		Map map=new HashMap();		
		String a[] = str.split("#ln;");		
		if(a.length<1){
			map.put("obj", " ");
			map.put("syl", " ");
			map.put("pre", " ");
			return map;
		}
		try{map.put("obj", a[0]);}catch(Exception e){map.put("obj", "無資料");}
		try{map.put("pre", a[1]);}catch(Exception e){map.put("pre", "無資料");}
		try{map.put("syl", a[2]);}catch(Exception e){map.put("syl", "無資料");}				
			
		return map;
	}
	
	/**
	 * 解析每週綱要
	 * 連續的週資料每筆後面必須追加#ln;
	 * 迴圈要-1
	 * @return
	 */
	public List parseSyls(String str){
		
		Map map;
		List list=new ArrayList();
		try{
			String a[] = str.split("#ln");		
			
			int cnt=0;
				
			for(int i=0; i<a.length-1; i=i+4){
				map=new HashMap();
				try{
					if(i==0){
						map.put("week", a[i]);
					}else{
						map.put("week", a[i].substring(1));
					}
					map.put("topic", a[i+1].substring(1));
					map.put("content", a[i+2].substring(1));
					map.put("hours", a[i+3].substring(1));
					list.add(map);
				}catch(Exception e){
					continue;
				}
			}
		}catch(Exception e){
			map=new HashMap();
			map.put("week", "");
			map.put("topic", "");
			map.put("content", "資料尚未編輯或已毀損");
			map.put("hours", "");
			list.add(map);
		}		
		return list;
	}
	
	/**
	 * 轉換報表中的符號
	 * @param str
	 * @return
	 */
	public String replaceChar4XML(String str){
		try{
			str=str.replaceAll("&", "&amp;");
			str=str.replaceAll("<", "&lt;");
			str=str.replaceAll(">", "&gt;");
			str=str.replaceAll("\"", "&quot;");
			str=str.replaceAll("", "\\n");
			
			//str=str.replaceAll(",", "&cedil;");
			str=str.replaceAll("'", "&apos;");
		}catch(NullPointerException e){
			return str;
		}
		
		return str;
	}
	
	/**
	 * 算文號
	 * @param student_no
	 * @return
	 */
	public String getEntrNo(String student_no){
		
		int num=Integer.parseInt(student_no.substring(student_no.length()-3, student_no.length()));
		String dum=student_no.substring(0, student_no.length()-3);
		
		
		List list=ezGetBy("SELECT * FROM entrno WHERE " +
				"first_stno LIKE '"+dum+"%' AND " +
				"LENGTH(first_stno)="+student_no.length());
		
		int begin;
		int end;
		for(int i=0; i<list.size(); i++){
			
			begin=Integer.parseInt(((Map)list.get(i)).get("first_stno").toString().substring(student_no.length()-3, student_no.length()));
			end=Integer.parseInt(((Map)list.get(i)).get("second_stno").toString().substring(student_no.length()-3, student_no.length()));
			
			if(num>=begin&&num<=end){
				if(!((Map)list.get(i)).get("permission_no").equals("")){
					return ((Map)list.get(i)).get("permission_no").toString();
				}else{
					return "";
				}
			}
		}		
		return "";
	}
	
	/**
	 * 以教師查詢課程和上課時間地點
	 * @param techid
	 * @param sterm
	 * @return
	 */
	public List getDtime(String techid, String sterm){
		
		List list=ezGetBy("SELECT d.Oid, cs.chi_name, c.ClassName, cs.cscode FROM Dtime d, Csno cs, Class c WHERE " +
				"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Sterm='"+sterm+"' AND d.techid='"+techid+"'");
		
		List dtimeclass;
		StringBuilder sb;
		for(int i=0;i <list.size(); i++){
			sb=new StringBuilder();
			dtimeclass=ezGetBy("SELECT * FROM Dtime_class WHERE Dtime_oid="+((Map)list.get(i)).get("Oid"));
			for(int j=0; j<dtimeclass.size(); j++){
				
				try{
					sb.append("週"+getWeekChar(Integer.parseInt(((Map)dtimeclass.get(j)).get("week").toString())));
				}catch(Exception e){
					sb.append(", ");
				}
				
				
				sb.append("第"+((Map)dtimeclass.get(j)).get("begin")+"~");
				sb.append(((Map)dtimeclass.get(j)).get("end")+"節, ");
			}
			if(sb.length()>1){
				sb.delete(sb.length()-2, sb.length());
			}
			((Map)list.get(i)).put("dtimeClass", sb);
		}		
		return list;
	}
	
	/**
	 * 以系助理身份查詢課程
	 */
	public List getDtimeByAssistant(String classes, String sterm){
		List list=ezGetBy("SELECT d.Oid, cs.chi_name, c.ClassName, cs.cscode FROM Dtime d, Csno cs, Class c WHERE " +
				"c.ClassNo=d.depart_class AND cs.cscode=d.cscode AND d.Sterm='"+sterm+"' AND d.depart_class IN"+classes);
		
		List dtimeclass;
		StringBuilder sb;
		for(int i=0;i <list.size(); i++){
			sb=new StringBuilder();
			dtimeclass=ezGetBy("SELECT * FROM Dtime_class WHERE Dtime_oid="+((Map)list.get(i)).get("Oid"));
			for(int j=0; j<dtimeclass.size(); j++){
				
				try{
					sb.append("週"+getWeekChar(Integer.parseInt(((Map)dtimeclass.get(j)).get("week").toString())));
				}catch(Exception e){
					sb.append(", ");
				}
				
				
				sb.append("第"+((Map)dtimeclass.get(j)).get("begin")+"~");
				sb.append(((Map)dtimeclass.get(j)).get("end")+"節, ");
			}
			if(sb.length()>1){
				sb.delete(sb.length()-2, sb.length());
			}
			((Map)list.get(i)).put("dtimeClass", sb);
		}		
		return list;
	}
	
	public void SetAqForm(HttpSession session, HttpServletRequest request){
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		//判斷為導師數量改為3
		int n=2;
		if(ezGetInt("SELECT COUNT(*) FROM ClassInCharge c WHERE c.ModuleOids LIKE '%86%' AND c.EmpOid='"+c.getMember().getOid()+"'")>0){
			n=3;
		}
		if(ezGetInt("SELECT COUNT(*)FROM AQ_anser WHERE idno='"+c.getMember().getIdno()+"'")<n){//101/11/8修正為2
			//初入			
			session.setAttribute("dept", ezGetString("SELECT c.idno2 FROM CodeEmpl c, empl e WHERE e.unit=c.idno AND e.idno='"+c.getMember().getIdno()+"'"));
			session.setAttribute("depts", ezGetBy("SELECT * FROM AQ_unit WHERE id='Q'"));
			session.setAttribute("myduty", ezGetString("SELECT Director FROM empl WHERE idno='"+c.getMember().getIdno()+"'"));
			
			//本系免重做
			List qlist=ezGetBy("SELECT * FROM AQ_anser WHERE uid='P' AND idno='"+c.getMember().getIdno()+"'");
			if(qlist.size()<1){
				request.setAttribute("mydept", ezGetBy("SELECT * FROM AQ_unit WHERE id='P'"));
			}

			//外系免重做
			qlist.addAll(ezGetBy("SELECT * FROM AQ_anser WHERE uid='Q' AND idno='"+c.getMember().getIdno()+"'"));
			
			
			//已作答問卷
			qlist.addAll(ezGetBy("SELECT * FROM AQ_anser WHERE uid NOT IN('P', 'Q') AND idno='"+c.getMember().getIdno()+"'"));
			for(int i=0; i<qlist.size(); i++){
				//系用
				if(((Map)qlist.get(i)).get("uid2")!=null){
					((Map)qlist.get(i)).put("name", ezGetString("SELECT name FROM AQ_unit WHERE id2='"+((Map)qlist.get(i)).get("uid2")+"' LIMIT 1"));
				}else{
					((Map)qlist.get(i)).put("name", ezGetString("SELECT name FROM AQ_unit WHERE id='"+((Map)qlist.get(i)).get("uid")+"' AND id2 IS NULL LIMIT 1"));
				}
			}			
			request.setAttribute("qlist", qlist);			
			StringBuilder sb=new StringBuilder("SELECT * FROM AQ_unit WHERE id NOT IN('P', 'Q', ");
			StringBuilder sb1=new StringBuilder("SELECT * FROM AQ_unit WHERE id='P' AND id2 NOT IN(");
			for(int i=0; i<qlist.size(); i++){			
				sb.append("'"+((Map)qlist.get(i)).get("uid")+"', ");
				sb1.append("'"+((Map)qlist.get(i)).get("uid2")+"', ");
			}		
			sb.delete(sb.length()-2, sb.length());
			sb.append(")ORDER BY Oid");
			sb1.delete(sb1.length()-2, sb1.length());
			sb1.append(")");
			//System.out.println(sb);
			request.setAttribute("allunit", ezGetBy(sb.toString()));
			try{
				request.setAttribute("other", ezGetBy(sb1.toString()));
			}catch(Exception e){
				request.setAttribute("other", ezGetBy("SELECT * FROM AQ_unit WHERE id='Q'"));
			}
			session.setAttribute("tqst", true);
			session.setAttribute("aqnum", n);
			return;
		}			
		session.setAttribute("tqst", false);
	}
	
}