package tw.edu.chit.service.impl;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.BlobDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.dao.StudAffairJdbcDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.ClassScoreSummary;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Clean;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Comb1;
import tw.edu.chit.model.Comb2;
import tw.edu.chit.model.Cond;
import tw.edu.chit.model.CounselingCode;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.DilgMail;
import tw.edu.chit.model.DilgOne;
import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeStudaffair;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplOpinionSuggestion;
import tw.edu.chit.model.Examine;
import tw.edu.chit.model.ExamineRule;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Keep;
import tw.edu.chit.model.Progress;
import tw.edu.chit.model.RuleTran;
import tw.edu.chit.model.ScoreStatus;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.StdOpinionSuggestion;
import tw.edu.chit.model.StdScore;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.StudDocApply;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocExamine;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocExam;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.model.StudPublicLeave;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Subs;
import tw.edu.chit.model.TimeOffUpload;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudAffairManagerImpl extends BaseManager implements StudAffairManager {
	
	private StudAffairDAO dao;
	private StudAffairJdbcDAO jdbcDao;
	private ScoreDAO scoredao;
	private MemberDAO memberdao;
	private BlobDAO blobdao;
	private List<Map> statusList = new ArrayList();	

	public void setStudAffairDAO(StudAffairDAO dao) {
		this.dao = dao;
	}

	public void setJdbcDAO(StudAffairJdbcDAO dao) {
		this.jdbcDao = dao;
	}
	
	public void setScoreDAO(ScoreDAO dao) {
		this.scoredao = dao;
	}
	
	public void setMemberDAO(MemberDAO dao) {
		this.memberdao = dao;
	}
	
	public void setBlobDAO(BlobDAO dao) {
		this.blobdao = dao;
	}
	
	public void setRunStatus(String methodName, String step, int rcount, int total, double percentage, String isComplete) {
		Map runstatus = new HashMap();
		runstatus.put("methodName", methodName);
		runstatus.put("step", step);
		runstatus.put("rcount", rcount);
		runstatus.put("total", total);
		runstatus.put("percentage", percentage);
		runstatus.put("complete", isComplete);
		//log.debug("SetRunningStatus->methodName:" + methodName + ", step:" + step + ", percentage:" + percentage);
		Map newMap;
		if(statusList.size() > 0) {
			for(Iterator statIter = statusList.listIterator(); statIter.hasNext();) {
				newMap = (Map)statIter.next();
				if(newMap.get("methodName").toString().equals(methodName)) {
					//log.debug("remove List element:" + newMap.get("step"));
					statIter.remove();
				}
			}
		}
		statusList.add(runstatus);
		//log.debug("after remove List element size:" + statusList.size());
	}
	
	public List chkstatus() {
		return statusList;
	}
	
	/**
	 * 以學號查詢在學學生
	 * 
	 * @param String studentNo
	 * 
	 * @return Student or null
	 */
	public Student findStudentByStudentNo(String studentNo) {

		String hql = "select s from Student s ";

		if (!"".equals(studentNo)) {
			hql += "where s.studentNo = '" + studentNo + "' ";
		}
		// log.debug("=======> hql='" + hql + "'");

		List<Student> students = dao.submitQuery(hql);
		if (students.size() > 0) {
			Student student = students.get(0);
			student.setDepartClass2(Toolket.getClassFullName(student.getDepartClass()));
			return student;
		}
		return null;
	}

	public Graduate findGraduateByStudentNo(String studentNo) {

		String hql = "select g from Graduate g ";

		if (!"".equals(studentNo)) {
			hql += "where g.studentNo = '" + studentNo + "' ";
		}
		//log.debug("=======> hql='" + hql + "'");
		List<Graduate> graduates = dao.submitQuery(hql);
		if (graduates.size() > 0) {
			Graduate graduate = graduates.get(0);
			graduate.setDepartClass2(Toolket.getClassFullName(graduate.getDepartClass()));
			return graduate;
		}
		return null;
	}

	public ActionMessages modifyJustDilgScore(String studentNo, double dilgScore){
		ActionMessages errs = new ActionMessages();
		String hql = "Select j From Just j Where j.studentNo='" + studentNo + "'";
		List<Just> justs = dao.submitQuery(hql);
		double totalScore = 0d, dscore = 0d;
		Just just;
		if(!justs.isEmpty()){
			just = justs.get(0);
			totalScore = just.getTotalScore();
			dscore = just.getDilgScore();
			totalScore += dilgScore - dscore;
			if(totalScore > 95d) totalScore = 95.0d;
			just.setTotalScore(totalScore);
			just.setDilgScore(dilgScore);
			dao.saveObject(just);
		}else{
			Student stud = this.findStudentByStudentNo(studentNo);
			just = new Just();
			just.setStudentNo(studentNo);
			just.setTeacherScore(0d);
			just.setMilitaryScore(0d);
			just.setDepartClass(stud.getDepartClass());
			just.setDeptheaderScore(0d);
			just.setDesdScore(0d);
			just.setDilgScore(dilgScore);
			just.setMeetingScore(0d);
			just.setMilitaryScore(0d);
			just.setTotalScore(82d-dilgScore);
			dao.saveObject(just);
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.MessageN1", "新增[" + studentNo + "]該學生的操行成績!"));
		}
		return errs;
	}
	
	
	public ActionMessages modifyJustDesdScore(String studentNo, double desdScore){
		ActionMessages errs = new ActionMessages();		
		
		String hql = "Select j From Just j Where j.studentNo='" + studentNo + "'";		
		List<Just> justs = dao.submitQuery(hql);
		
		if(!justs.isEmpty()){
			Just just = justs.get(0);
			double totalScore = just.getTotalScore();
			totalScore += desdScore - just.getDesdScore();
			if(totalScore > 95d) totalScore = 95.0d;
			just.setTotalScore(totalScore);
			just.setDesdScore(desdScore);
			dao.saveObject(just);
		}else{
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
		}
		return errs;
	}
	
	
	public ActionMessages modifySeldDilgPeriod(String studentNo, Date ddate){
		ActionMessages errs = new ActionMessages();
		String sterm = Toolket.getSysParameter("School_term");
		
		//找出該學生該天所上的所有課程(Seld)
		String hql = "Select s,d From Seld s,DtimeClass d, Dtime dd" +
		" Where s.studentNo='" + studentNo + "'" +
		" And dd.sterm='" + sterm + "' And s.dtimeOid=dd.oid" +
		" And s.dtimeOid=d.dtimeOid And d.week=" + this.WhatIsTheWeek(ddate) + 
		" Order by s.oid";
		
		List tmpList = dao.submitQuery(hql);
		
		if(!tmpList.isEmpty()){
			Object[] objs = new Object[2];
			objs = (Object[])(tmpList.get(0));
			Seld prevSeld = (Seld)objs[0];
			
			List<Dilg> dilgList = new ArrayList<Dilg>();
			int i=0, begin=0, end=0, dcnt=0;
			
			short[] dilgs = new short[16];
			int seldOid = prevSeld.getOid();
			Seld seld = new Seld();
			
			for(Iterator tIter=tmpList.iterator(); tIter.hasNext();){
				objs = (Object[])tIter.next();
				seld = (Seld)objs[0];
				DtimeClass dtimeClass = (DtimeClass)objs[1];
				
				begin = Integer.parseInt(dtimeClass.getBegin());
				end = Integer.parseInt(dtimeClass.getEnd());

				hql = "Select d From Dilg d Where d.studentNo='" + studentNo
						+ "'" + " And d.weekDay=" + dtimeClass.getWeek();

				dilgList = dao.submitQuery(hql);
				if(seldOid != seld.getOid()){
					try {
						seld.setDilgPeriod(dcnt);
						dao.saveObject(seld);
					} catch (Exception e) {
						e.printStackTrace();
						errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e
								.toString()));

						return errs;
					}
					seldOid = seld.getOid();
					dcnt = 0;
				}

				for (Dilg dilg : dilgList) {
					for (i = 0; i < 16; i++) {
						dilgs[i] = 0;
					}
					if (dilg.getAbs0() != null)
						dilgs[0] = dilg.getAbs0();
					if (dilg.getAbs1() != null)
						dilgs[1] = dilg.getAbs1();
					if (dilg.getAbs2() != null)
						dilgs[2] = dilg.getAbs2();
					if (dilg.getAbs3() != null)
						dilgs[3] = dilg.getAbs3();
					if (dilg.getAbs4() != null)
						dilgs[4] = dilg.getAbs4();
					if (dilg.getAbs5() != null)
						dilgs[5] = dilg.getAbs5();
					if (dilg.getAbs6() != null)
						dilgs[6] = dilg.getAbs6();
					if (dilg.getAbs7() != null)
						dilgs[7] = dilg.getAbs7();
					if (dilg.getAbs8() != null)
						dilgs[8] = dilg.getAbs8();
					if (dilg.getAbs9() != null)
						dilgs[9] = dilg.getAbs9();
					if (dilg.getAbs10() != null)
						dilgs[10] = dilg.getAbs10();
					if (dilg.getAbs11() != null)
						dilgs[11] = dilg.getAbs11();
					if (dilg.getAbs12() != null)
						dilgs[12] = dilg.getAbs12();
					if (dilg.getAbs13() != null)
						dilgs[13] = dilg.getAbs13();
					if (dilg.getAbs14() != null)
						dilgs[14] = dilg.getAbs14();
					if (dilg.getAbs15() != null)
						dilgs[15] = dilg.getAbs15();

					for (i = begin; i <= end; i++) {
						if (!(dilgs[i] == 0 || dilgs[i] == 1 || dilgs[i] == 5 || dilgs[i] == 6)) {
							dcnt++;
						}
					}

				}	//End for Dilg
			}	//End for tmpList
			
			// save last record
			try {
				seld.setDilgPeriod(dcnt);
				dao.saveObject(seld);
			} catch (Exception e) {
				e.printStackTrace();
				errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e
						.toString()));

				return errs;
			}
			
		}
		
		return errs;
	}
	
	public List<Dilg> findDilgByTimeOffInfo(String ddate, String studentNo, String classInCharge){
		String hql = "Select d From Dilg d";
		if(!ddate.equals("")) {
			hql = hql + " Where ddate='" + ddate +"'";
			if(!studentNo.equals("")) {
				hql = hql + " And studentNo like '" + studentNo + "%'";
				hql = hql + " And departClass in " + classInCharge;
			} else {
				hql = hql + " And departClass in " + classInCharge;
			}
		} else if(!studentNo.equals("")) {
			hql = hql + " Where studentNo like '" + studentNo + "%'";
			hql = hql + " And departClass in " + classInCharge;
		} else {
			hql = hql + " Where departClass in " + classInCharge;
		}
		hql = hql + " Order by ddate desc, departClass desc";
		log.debug("findDilgByTimeOffInfo=====>" + classInCharge);
		log.debug("findDilgByTimeOffInfo=====>" + hql);
		List<Dilg> dilglist = (List<Dilg>)dao.submitQuery(hql);
		
		return dilglist;
	}
		
	public Dilg findDilgByStdDate(String studentNo, String tdate){
		Dilg dilg = null;
		String hql = "Select d From Dilg d";
		if(!tdate.trim().equals("") && !studentNo.equals("")) {
			hql = hql + " Where ddate='" + tdate +"'";
			hql = hql + " And studentNo = '" + studentNo + "'";
		}
		List<Dilg> dilglist = (List<Dilg>)dao.submitQuery(hql);
		if(!dilglist.isEmpty()){
			dilg = dilglist.get(0);
		}
		return dilg;
	}
	
	public List<Dilg> delStudTimeOffs(List<Dilg> selDilgs, ResourceBundle bundle) {
		List<Dilg> undelDilgs = new ArrayList<Dilg>();
		Dilg dilg = new Dilg();
		for(Iterator<Dilg> dilgIter = selDilgs.iterator(); dilgIter.hasNext();) {
			dilg = dilgIter.next();
			log.debug("====>Remove Dilg:" + dilg.getOid());
			dao.removeDilg(dilg);
		}
		return undelDilgs;
	}
	
	public ActionMessages validateStudTimeOffCreate(Map formMap, ActionMessages errors, ResourceBundle bundle) {
		String studentNo = formMap.get("studentNo").toString();
		int tfYear = Integer.parseInt(formMap.get("tfYear").toString()) + 1911;
		String tfMonth = formMap.get("tfMonth").toString();
		String tfDay = formMap.get("tfDay").toString();
		
		String hql = "Select d from Dilg d Where studentNo='" + studentNo + "' And ddate='" +
						tfYear + "-" + tfMonth + "-" + tfDay + "'";
		List dilgList = dao.submitQuery(hql);
		if(dilgList.size() > 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateTimeOffRecord"));
		}
		return errors;
	}
	
	public ActionMessages validateStudTimeOffModify(Map formMap, Dilg dilg, ActionMessages errors, ResourceBundle bundle) {
		String studentNo = formMap.get("studentNo").toString();
		int tfYear = Integer.parseInt(formMap.get("tfYear").toString()) + 1911;
		String tfMonth = formMap.get("tfMonth").toString();
		String tfDay = formMap.get("tfDay").toString();
		
		String[] ddate = dilg.getSddate().split("-");
		if(ddate.length == 3){
			if(ddate[0].equals(formMap.get("tfYear").toString())
					&& ddate[1].equals(formMap.get("tfMonth").toString())
					&& ddate[2].equals(formMap.get("tfDay").toString()))
				return errors;
		} else {
		
			String hql = "Select d from Dilg d Where studentNo='" + studentNo + "' And ddate='" +
						tfYear + "-" + tfMonth + "-" + tfDay + "'";
			List dilgList = dao.submitQuery(hql);
			if(dilgList.size() > 0) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateTimeOffRecord"));
			}
		}
		return errors;
		
	}
	
	public void createStudTimeOff(Map formMap, String departClass) {
		ActionMessages msgs = new ActionMessages();
		String studentNo = formMap.get("studentNo").toString().toUpperCase();
		int tfYear = Integer.parseInt(formMap.get("tfYear").toString()) + 1911;
		int tfMonth = Integer.parseInt(formMap.get("tfMonth").toString()) - 1;
		int tfDay = Integer.parseInt(formMap.get("tfDay").toString());
		Calendar ddate = Calendar.getInstance(); 
		ddate.set(tfYear, tfMonth, tfDay, 0, 0, 0);
		Date sddate = ddate.getTime();
		
		Dilg dilg = new Dilg();
		String hql = "From Dilg Where studentNo=? And ddate=?";
		List<Dilg> dilgs = dao.submitQuery(hql, new Object[]{studentNo, sddate});
		if(!dilgs.isEmpty()){
			dilg = dilgs.get(0);
		}else{
			dilg.setDaynite(formMap.get("daynite").toString());
			dilg.setDepartClass(departClass);
			dilg.setStudentNo(studentNo);
			dilg.setDdate(sddate);
			dilg.setWeekDay((float)WhatIsTheWeek(sddate));
		}
		String stmp = "";
		stmp = formMap.get("abs0").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs0(Short.parseShort(formMap.get("abs0").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs0(null);
		stmp = formMap.get("abs1").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs1(Short.parseShort(formMap.get("abs1").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs1(null);
		stmp = formMap.get("abs2").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs2(Short.parseShort(formMap.get("abs2").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs2(null);
		stmp = formMap.get("abs3").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs3(Short.parseShort(formMap.get("abs3").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs3(null);
		stmp = formMap.get("abs4").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs4(Short.parseShort(formMap.get("abs4").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs4(null);
		stmp = formMap.get("abs5").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs5(Short.parseShort(formMap.get("abs5").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs5(null);
		stmp = formMap.get("abs6").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs6(Short.parseShort(formMap.get("abs6").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs6(null);
		stmp = formMap.get("abs7").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs7(Short.parseShort(formMap.get("abs7").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs7(null);
		stmp = formMap.get("abs8").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs8(Short.parseShort(formMap.get("abs8").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs8(null);
		stmp = formMap.get("abs9").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs9(Short.parseShort(formMap.get("abs9").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs9(null);
		stmp = formMap.get("abs10").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs10(Short.parseShort(formMap.get("abs10").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs10(null);
		stmp = formMap.get("abs11").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs11(Short.parseShort(formMap.get("abs11").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs11(null);
		stmp = formMap.get("abs12").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs12(Short.parseShort(formMap.get("abs12").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs12(null);
		stmp = formMap.get("abs13").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs13(Short.parseShort(formMap.get("abs13").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs13(null);
		stmp = formMap.get("abs14").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs14(Short.parseShort(formMap.get("abs14").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs14(null);
		stmp = formMap.get("abs15").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs15(Short.parseShort(formMap.get("abs15").toString()));
		else if( stmp.equals("0"))
			dilg.setAbs15(null);
		
		dao.saveObject(dilg);
		
	}
	
	public void modifyStudTimeOff(Map formMap, Dilg dilg) {
		int tfYear = Integer.parseInt(formMap.get("tfYear").toString()) + 1911;
		int tfMonth = Integer.parseInt(formMap.get("tfMonth").toString()) - 1;
		int tfDay = Integer.parseInt(formMap.get("tfDay").toString());
		Calendar ddate = Calendar.getInstance(); 
		ddate.set(tfYear, tfMonth, tfDay, 0, 0, 0);
		Date sddate = ddate.getTime();
		
		dilg.setDdate(sddate);
		dilg.setWeekDay((float)WhatIsTheWeek(sddate));
		String stmp = "";
		stmp = formMap.get("abs0").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs0(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs0(null);
		stmp = formMap.get("abs1").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs1(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs1(null);
		stmp = formMap.get("abs2").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs2(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs2(null);
		stmp = formMap.get("abs3").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs3(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs3(null);
		stmp = formMap.get("abs4").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs4(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs4(null);
		stmp = formMap.get("abs5").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs5(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs5(null);
		stmp = formMap.get("abs6").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs6(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs6(null);
		stmp = formMap.get("abs7").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs7(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs7(null);
		stmp = formMap.get("abs8").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs8(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs8(null);
		stmp = formMap.get("abs9").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs9(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs9(null);
		stmp = formMap.get("abs10").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs10(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs10(null);
		stmp = formMap.get("abs11").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs11(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs11(null);
		stmp = formMap.get("abs12").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs12(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs12(null);
		stmp = formMap.get("abs13").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs13(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs13(null);
		stmp = formMap.get("abs14").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs14(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs14(null);
		stmp = formMap.get("abs15").toString().trim();
		if(!stmp.equals("") && !stmp.equals("0"))
			dilg.setAbs15(Short.parseShort(stmp));
		else if(stmp.equals("") || stmp.equals("0"))
			dilg.setAbs15(null);
		
	
		dao.saveObject(dilg);

	}

	public void modifyStudTimeOffList(Map formMap, List<Dilg> dilgList) {
		String[] abs0 = (String[])formMap.get("abs0");
		String[] abs1 = (String[])formMap.get("abs1");
		String[] abs2 = (String[])formMap.get("abs2");
		String[] abs3 = (String[])formMap.get("abs3");
		String[] abs4 = (String[])formMap.get("abs4");
		String[] abs5 = (String[])formMap.get("abs5");
		String[] abs6 = (String[])formMap.get("abs6");
		String[] abs7 = (String[])formMap.get("abs7");
		String[] abs8 = (String[])formMap.get("abs8");
		String[] abs9 = (String[])formMap.get("abs9");
		String[] abs10 = (String[])formMap.get("abs10");
		String[] abs11 = (String[])formMap.get("abs11");
		String[] abs12 = (String[])formMap.get("abs12");
		String[] abs13 = (String[])formMap.get("abs13");
		String[] abs14 = (String[])formMap.get("abs14");
		String[] abs15 = (String[])formMap.get("abs15");
		String[][] abs = {abs0,abs1,abs2,abs3,abs4,abs5,abs6,abs7,
				abs8,abs9,abs10,abs11,abs12,abs13,abs14,abs15};
		
		int i=0;
		for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
			Dilg dilg = dilgIter.next();
			String stmp = "";
			stmp = abs[0][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs0(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs0(null);
			stmp = abs[1][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs1(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs1(null);
			stmp = abs[2][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs2(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs2(null);
			stmp = abs[3][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs3(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs3(null);
			stmp = abs[4][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs4(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs4(null);
			stmp = abs[5][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs5(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs5(null);
			stmp = abs[6][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs6(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs6(null);
			stmp = abs[7][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs7(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs7(null);
			stmp = abs[8][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs8(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs8(null);
			stmp = abs[9][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs9(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs9(null);
			stmp = abs[10][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs10(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs10(null);
			stmp = abs[11][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs11(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs11(null);
			stmp = abs[12][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs12(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs12(null);
			stmp = abs[13][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs13(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs13(null);
			stmp = abs[14][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs14(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs14(null);
			stmp = abs[15][i].trim();
			if(!stmp.equals("") && !stmp.equals("0"))
				dilg.setAbs15(Short.parseShort(stmp));
			else if(stmp.equals("") || stmp.equals("0"))
				dilg.setAbs15(null);
			
			dao.saveObject(dilg);
			i++;
		}
	}

	
	public List findCscodeByClassAtDate(String departClass, String iweek, String mode){
		List dtimes = new ArrayList();
		List unsort = new ArrayList();
		Object[] dtObj;

		String[] sdate;
		int itfYear, itfMonth, itfDay;
		Calendar tfdate = Calendar.getInstance();
		tfdate.clear();
		
		String sterm = Toolket.getSysParameter("School_term");
		Dtime dtime;
		List<Csno> tmplist;
		//String hql = "select d From Dtime d, DtimeClass dc Where d.departClass='"
		//	+ departClass + "' And d.sterm='" + sterm + "' And d.oid=dc.dtimeOid And dc.week=" + iweek;
		String hql = "select d, dc.begin As begin, dc.end As end, c.chiName As chiName From Dtime d, DtimeClass dc, Csno c";
		hql = hql + " Where d.departClass='" + departClass + "' And d.sterm='" + sterm
					+ "' And d.oid=dc.dtimeOid And dc.week=" + iweek + " And c.cscode=d.cscode";
		//hql = hql + " Order by CAST(dc.begin As UNSIGNED)";
		log.debug("findCscodeByClassAtDate:hql->" + hql);
		unsort = dao.submitQuery(hql);
		log.debug("findCscodeByClassAtDate:dtimes.size()" + unsort.size());
		
		List dtSort = new ArrayList();
		String sql = "Select dc.Dtime_oid As dtoid, dc.begin As begin From Dtime d, Dtime_class dc";
		sql = sql + " Where d.depart_class='" + departClass + "' And d.Sterm='" + sterm;
		sql = sql + "' And d.Oid=dc.Dtime_oid And dc.week=" + iweek ;
		sql = sql + " Order by CAST(dc.begin As UNSIGNED)";
		log.debug("findCscodeByClassAtDate:sql->" + sql);
		
		dtSort = jdbcDao.findAnyThing(sql);
		log.debug("findCscodeByClassAtDate:dtSort.size()" + dtSort.size());
		
		//Sort Query Result by Dtime_class->begin
		String begin, ubegin;
		for (Iterator sortIter = dtSort.iterator(); sortIter.hasNext();) {
			Map oidMap = (Map)sortIter.next();
			int oid = Integer.parseInt(oidMap.get("dtoid").toString());
			begin = oidMap.get("begin").toString();
			for(Iterator dtIter = unsort.iterator(); dtIter.hasNext();) {
				dtObj = (Object[])dtIter.next();
				dtime = (Dtime)dtObj[0];
				ubegin = dtObj[1].toString();
				if(dtime.getOid() == oid && begin.equals(ubegin)) {
					dtimes.add(dtObj);
				}				
			}
		}

		//log.debug("StuTimeOff-FindSubject==> hql:" + hql);
		//log.debug("StuTimeOff-FindSubject==> size:" + dtimes.size());
		/*
		if(dtimes.size() > 0) {
			for (Iterator<Dtime> dtimeIter = dtimes.iterator(); dtimeIter.hasNext();) {
				dtime = dtimeIter.next();
				//log.debug("StuTimeOff-FindSubject==> cscode:" + dtime.getOid());
				//log.debug("StuTimeOff-FindSubject==> cscode:" + dtime.getCscode());
				tmplist = scoredao.findCourseByCode(dtime.getCscode());
				if (tmplist.size() > 0) {
					dtime.setChiName2(tmplist.get(0).getChiName());
				} else {
					dtime.setChiName2("");
				}
				//log.debug("StuTimeOff-FindSubject==> cscode:" + dtime.getCscode() + ", subjectName:" + dtime.getChiName2());
				dtime.setClassName2(Toolket.getClassFullName(dtime.getDepartClass()));
				dtime.setOpt2(Toolket.getCourseOpt(dtime.getOpt()));
			}
			
		}
		*/
		
		if(!mode.equals("")) {
			
			for (Iterator dtimeIter = dtimes.iterator(); dtimeIter.hasNext();) {
				dtObj = (Object[])dtimeIter.next();
				dtime = (Dtime)dtObj[0];
				if(mode.equals("ByClass")) {
					if(dtime.getOpen() == 1) dtimeIter.remove();
				} else if(mode.equals("BySubject")){
					if(dtime.getOpen() == 0) dtimeIter.remove();
				}
			}
			log.debug("findCscodeByClassAtDate:dtimes.size()" + dtimes.size());
		}
		return dtimes;

	}
	
	public List<Student> findStudentsByClass(String departClass) {
		String hql = "Select s From Student s Where departClass='" + departClass + "' order by studentNo";
		return dao.submitQuery(hql);
	}
	
	/**
	 * 查詢該課程之所有選課學生
	 * 
	 * @param Oid -- Dtime_oid
	 * @param departClass -- 開課班級
	 * 
	 * @param group --> 0:all 1:該開課班級之選修學生 2:非該開課班級之選修學生
	 * @return List<Seld>
	 */
	public List<Seld> findSeldForClassBook(int Oid, String departClass, int group) {
		//String hql = "Select s From Seld s, Dtime d, Student st Where ";		
		String hql = "Select s From Seld s Where ";
		hql = hql + "s.dtimeOid=" + Oid ;
		if(group == 0) {
			 hql = hql;
		} else if(group == 1) {
			hql = hql + " And s.studentNo in (Select st.studentNo From Student st Where st.departClass='" + departClass + "')";
		} else if(group == 2) {
			hql = hql + " And s.studentNo Not in (Select st.studentNo From Student st Where st.departClass='" + departClass + "')";
		}
		hql = hql + " Order by studentNo";
		//System.out.println("Leo0308_2="+hql);
		log.debug("findSeldForClassBook:hql->" + hql);
		//System.out.println("Leo0308_1="+hql);
		List<Seld> seldList = dao.submitQuery(hql);	
		//System.out.println("Leo0308_2");
		List<Student> studList= new ArrayList();		
		Student student;
		Seld seld;
		String stuName = "";
		
		
		for(Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			//System.out.println("LeoStdNo="+seld.getStudentNo());
			studList = scoredao.findStudentByStudentNO(seld.getStudentNo());
			if(!studList.isEmpty())
				seld.setStudentName(studList.get(0).getStudentName());
			else
				seldIter.remove();
		}
		
		return seldList;
	}
	
	public List<Adcd> findAdcdForClassBook(int oid,  String adddraw) {
		String hql = "Select ad From Adcd ad Where dtimeOid=" + oid + " And adddraw='" + adddraw +"'";
		return dao.submitQuery(hql);
	}
	
	public int[] calTimeOffBySubject(List<Map> classBook, int oid) {
		int[] timeOff = new int[classBook.size()];
		int iweek = 0, begin = 0, end = 0, status = 0;
		String hql = "";
		
		for(int i=0; i<timeOff.length; i++) {
			timeOff[i] = 0;
		}
		
		int count = 0;
		for(Iterator<Map> cbIter=classBook.iterator(); cbIter.hasNext();) {
			Map cbMap = cbIter.next();
			hql = "Select d From Dilg d Where ";
			hql = hql + "d.studentNo='" + cbMap.get("studentNo") + "'";
			List<Dilg> dilgList = dao.submitQuery(hql);
			//log.debug("calTimeOffBySubject->dilgList:size:hql:[" + dilgList.size() + "]"+ hql);
			
			hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + oid;
			List<DtimeClass> dcList = dao.submitQuery(hql);
			//log.debug("calTimeOffBySubject->DtimeClassList:size:hql:[" + dcList.size() + "]"+ hql);
			if(dilgList.size() > 0) {
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext(); ) {
					Dilg dilg = dilgIter.next();
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = WhatIsTheWeek(dilg.getDdate());
						
						begin = Integer.parseInt(dclass.getBegin().trim());
						end = Integer.parseInt(dclass.getEnd().trim());
						//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
						if(iweek == dclass.getWeek()) {
							for(int j=begin; j <= end; j++){
								status = 0;
								switch (j){
								case 0:
									if(dilg.getAbs0() != null)
									status = dilg.getAbs0();
									break;
								case 1:
									if(dilg.getAbs1() != null)
									status = dilg.getAbs1();
									break;
								case 2:
									if(dilg.getAbs2() != null)
									status = dilg.getAbs2();
									break;
								case 3:
									if(dilg.getAbs3() != null)
									status = dilg.getAbs3();
									break;
								case 4:
									if(dilg.getAbs4() != null)
									status = dilg.getAbs4();
									break;
								case 5:
									if(dilg.getAbs5() != null)
									status = dilg.getAbs5();
									break;
								case 6:
									if(dilg.getAbs6() != null)
									status = dilg.getAbs6();
									break;
								case 7:
									if(dilg.getAbs7() != null)
									status = dilg.getAbs7();
									break;
								case 8:
									if(dilg.getAbs8() != null)
									status = dilg.getAbs8();
									break;
								case 9:
									if(dilg.getAbs9() != null)
									status = dilg.getAbs9();
									break;
								case 10:
									if(dilg.getAbs10() != null)
									status = dilg.getAbs10();
									break;
								case 11:
									if(dilg.getAbs11() != null)
									status = dilg.getAbs11();
									break;
								case 12:
									if(dilg.getAbs12() != null)
									status = dilg.getAbs12();
									break;
								case 13:
									if(dilg.getAbs13() != null)
									status = dilg.getAbs13();
									break;
								case 14:
									if(dilg.getAbs14() != null)
									status = dilg.getAbs14();
									break;
								case 15:
									if(dilg.getAbs15() != null)
									status = dilg.getAbs15();
									break;
								
								}
								if(!(status == 0 || status == 5 || status == 6)) {
										timeOff[count]++;
								}
	
							}
						}
					}
				}
				count++;
			} else {
				timeOff[count] = 0;
				count++;
			}
		}
		return timeOff;
	}

	public int[] calPeriodTimeOffBySubject(List classBook, String dateBegin, String dateEnd, int oid) {
		int[] timeOff = new int[classBook.size()];
		int iweek = 0, begin = 0, end = 0, status = 0;
		String hql = "";
		
		for(int i=0; i<timeOff.length; i++) {
			timeOff[i] = 0;
		}
		
		int count = 0;
		for(Iterator<Map> cbIter=classBook.iterator(); cbIter.hasNext();) {
			Map cbMap = cbIter.next();
			hql = "Select d From Dilg d Where ";
			hql = hql + "d.studentNo='" + cbMap.get("studentNo") + "'";
			hql += " And ddate Between '" + dateBegin +"' And '" + dateEnd + "'";
			hql += " Order by ddate";
			List<Dilg> dilgList = dao.submitQuery(hql);
			
			hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + oid;
			List<DtimeClass> dcList = dao.submitQuery(hql);
			if(dilgList.size() > 0) {
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext(); ) {
					Dilg dilg = dilgIter.next();
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = WhatIsTheWeek(dilg.getDdate());
						
						begin = Integer.parseInt(dclass.getBegin().trim());
						end = Integer.parseInt(dclass.getEnd().trim());
						//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
						if(iweek == dclass.getWeek()) {
							for(int j=begin; j <= end; j++){
								status = 0;
								switch (j){
								case 0:
									if(dilg.getAbs0() != null)
									status = dilg.getAbs0();
									break;
								case 1:
									if(dilg.getAbs1() != null)
									status = dilg.getAbs1();
									break;
								case 2:
									if(dilg.getAbs2() != null)
									status = dilg.getAbs2();
									break;
								case 3:
									if(dilg.getAbs3() != null)
									status = dilg.getAbs3();
									break;
								case 4:
									if(dilg.getAbs4() != null)
									status = dilg.getAbs4();
									break;
								case 5:
									if(dilg.getAbs5() != null)
									status = dilg.getAbs5();
									break;
								case 6:
									if(dilg.getAbs6() != null)
									status = dilg.getAbs6();
									break;
								case 7:
									if(dilg.getAbs7() != null)
									status = dilg.getAbs7();
									break;
								case 8:
									if(dilg.getAbs8() != null)
									status = dilg.getAbs8();
									break;
								case 9:
									if(dilg.getAbs9() != null)
									status = dilg.getAbs9();
									break;
								case 10:
									if(dilg.getAbs10() != null)
									status = dilg.getAbs10();
									break;
								case 11:
									if(dilg.getAbs11() != null)
									status = dilg.getAbs11();
									break;
								case 12:
									if(dilg.getAbs12() != null)
									status = dilg.getAbs12();
									break;
								case 13:
									if(dilg.getAbs13() != null)
									status = dilg.getAbs13();
									break;
								case 14:
									if(dilg.getAbs14() != null)
									status = dilg.getAbs14();
									break;
								case 15:
									if(dilg.getAbs15() != null)
									status = dilg.getAbs15();
									break;
								
								}
								if(!(status == 0 || status == 5 || status == 6)) {
									timeOff[count]++;
								}
							}
						}
					}
				}
				count++;
			} else {
				timeOff[count] = 0;
				count++;
			}
		}
		return timeOff;
	}
	

	
	public ActionMessages createOrUpdateDilgBatch(Map StudTimeoffInit, List classBook, int begin, int end) {
		ActionMessages msgs = new ActionMessages();
		
		int tfYear = Integer.parseInt(StudTimeoffInit.get("tfYear").toString()) + 1911;
		int tfMonth = Integer.parseInt(StudTimeoffInit.get("tfMonth").toString()) - 1;
		int tfDay = Integer.parseInt(StudTimeoffInit.get("tfDay").toString());
		int iweek = Integer.parseInt(StudTimeoffInit.get("tfWeek").toString());
		String daynite = StudTimeoffInit.get("daynite").toString();
		log.debug("daynite=" + daynite);

		Calendar ddate = Calendar.getInstance(); 
		ddate.set(tfYear, tfMonth, tfDay, 0, 0, 0);
		Date sddate = ddate.getTime();
		String status;
		short num;
		boolean isTimeOff = false;
		boolean isNullDilg = false;
		
		for(Iterator cbIter = classBook.iterator(); cbIter.hasNext();){
			isTimeOff = false;
			Map cbMap = (Map)cbIter.next();
			String studentNo = cbMap.get("studentNo").toString().toUpperCase();
			List<Student> studList = scoredao.findStudentByStudentNO(studentNo);
			if(studList.isEmpty()) {
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.createOrUpdateDilgBatchErr"));
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Message.StudentNotFound", studentNo));
				return msgs;
				
			}
			/*
			for(int i = begin; i < end + 1; i++) {
				status = cbMap.get("status" + i).toString();
				if(StringUtils.isNumeric(status) && !status.trim().equals("")) {
					isTimeOff = true;
					break;
				}
			}
			
			if(!isTimeOff) continue;	//該學生沒有缺曠課資料被輸入
			*/
			if(cbMap.get("isDataChg").toString().equals("N"))
				continue;
			
			String departClass = studList.get(0).getDepartClass();
			
			String hql = "Select d from Dilg d Where studentNo='" + studentNo + "' And ddate='" +
			tfYear + "-" + (tfMonth + 1) + "-" + tfDay + "'";
			List<Dilg> dilgList = dao.submitQuery(hql);
			Dilg dilg;
			if(dilgList.size() > 0) {
				dilg = dilgList.get(0);
				isNullDilg = true;	//先將此Flag設為true,表示有可能清空該筆曠缺記錄
			} else {
				isNullDilg = false;	//新建立該筆曠缺記錄,不需要考慮是否刪除該筆紀錄
				dilg = new Dilg();
				dilg.setStudentNo(studentNo);
				dilg.setDepartClass(departClass);
				dilg.setDdate(sddate);
				dilg.setWeekDay((float)WhatIsTheWeek(sddate));
				dilg.setDaynite(daynite);
			}
			
			for(int i = begin; i < end + 1; i++) {
				num = -1;
				status = cbMap.get("status" + i).toString();
				if(StringUtils.isNumeric(status) || status.trim().equals("")) {
					if(StringUtils.isNumeric(status) && !status.trim().equals(""))
						num = Short.parseShort(status);
					
					switch (i) {
					case 0:
						if(num > 0)
							dilg.setAbs0(num);
						else
							dilg.setAbs0(null);
						break;
					case 1:
						if(num > 0)
						dilg.setAbs1(num);
						else
							dilg.setAbs1(null);
						break;
					case 2:
						if(num > 0)
						dilg.setAbs2(num);
						else
							dilg.setAbs2(null);
						break;
					case 3:
						if(num > 0)
						dilg.setAbs3(num);
						else
							dilg.setAbs3(null);
						break;
					case 4:
						if(num > 0)
						dilg.setAbs4(num);
						else
							dilg.setAbs4(null);
						break;
					case 5:
						if(num > 0)
						dilg.setAbs5(num);
						else
							dilg.setAbs5(null);
						break;
					case 6:
						if(num > 0)
						dilg.setAbs6(num);
						else
							dilg.setAbs6(null);
						break;
					case 7:
						if(num > 0)
						dilg.setAbs7(num);
						else
							dilg.setAbs7(null);
						break;
					case 8:
						if(num > 0)
						dilg.setAbs8(num);
						else
							dilg.setAbs8(null);
						break;
					case 9:
						if(num > 0)
						dilg.setAbs9(num);
						else
							dilg.setAbs9(null);
						break;
					case 10:
						if(num > 0)
						dilg.setAbs10(num);
						else
							dilg.setAbs10(null);
						break;
					case 11:
						if(num > 0)
						dilg.setAbs11(num);
						else
							dilg.setAbs11(null);
						break;
					case 12:
						if(num > 0)
						dilg.setAbs12(num);
						else
							dilg.setAbs12(null);
						break;
					case 13:
						if(num > 0)
						dilg.setAbs13(num);
						else
							dilg.setAbs13(null);
						break;
					case 14:
						if(num > 0)
						dilg.setAbs14(num);
						else
							dilg.setAbs14(null);
						break;
					case 15:
						if(num > 0)
						dilg.setAbs15(num);
						else
							dilg.setAbs15(null);
						break;					
					}
				}
				
			}
			dao.saveObject(dilg);
			
			//如果該筆曠缺資料abs0~15皆為null,表示無曠缺,則刪除這筆資料
			if(isNullDilg) {
				dilgList = dao.submitQuery(hql);
				if(dilgList.size() > 0) {
					dilg = dilgList.get(0);
					if(dilg.getAbs0() != null) continue;
					if(dilg.getAbs1() != null) continue;
					if(dilg.getAbs2() != null) continue;
					if(dilg.getAbs3() != null) continue;
					if(dilg.getAbs4() != null) continue;
					if(dilg.getAbs5() != null) continue;
					if(dilg.getAbs6() != null) continue;
					if(dilg.getAbs7() != null) continue;
					if(dilg.getAbs8() != null) continue;
					if(dilg.getAbs9() != null) continue;
					if(dilg.getAbs10() != null) continue;
					if(dilg.getAbs11() != null) continue;
					if(dilg.getAbs12() != null) continue;
					if(dilg.getAbs13() != null) continue;
					if(dilg.getAbs14() != null) continue;
					if(dilg.getAbs15() != null) continue;
					dao.removeDilg(dilg);
				}
			}
			
		}
		return msgs;

	}
	
	public ActionMessages updateSeldDilgPeriodBatch(Date sddate, List classBook){
		ActionMessages msgs = new ActionMessages();

		String studentNo = "";
		Map cbMap = new HashMap();
		for(Iterator cbIter = classBook.iterator(); cbIter.hasNext();){
			cbMap = (Map)cbIter.next();
			studentNo = cbMap.get("studentNo").toString().toUpperCase();
			if(cbMap.get("isDataChg").toString().equals("N")){
				continue;
			}else{
				msgs = this.modifySeldDilgPeriod(studentNo, sddate);
				if(!msgs.isEmpty()){
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Message.MessageN1", "批次更新[" + studentNo + "]扣考節數失敗!"));
					msgs.add(msgs);
				}

			}
			
		}
		return msgs;
	}
	
	public ActionMessages updateJustDilgScoreBatch(List classBook){
		ActionMessages msgs = new ActionMessages();
		ActionMessages errs = new ActionMessages();

		double dilgScore = 0d;
		String studentNo = "";
		Map cbMap = new HashMap();
		for(Iterator cbIter = classBook.iterator(); cbIter.hasNext();){
			cbMap = (Map)cbIter.next();
			studentNo = cbMap.get("studentNo").toString().toUpperCase();
			if(Toolket.getStudentByNo(studentNo) != null){
				if(cbMap.get("isDataChg").toString().equals("N")){
					continue;
				}else{
					try{
						dilgScore = this.calDilgScoreByStudent(studentNo, "0");
						//Leo20120308  ===========================>>>>>>>>>>>>>>>>
						errs = this.modifyJustDilgScore(studentNo, dilgScore);
						if(!errs.isEmpty()){
							msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.MessageN1", "批次更新[" + studentNo + "]操行曠缺加減分失敗!"));
							msgs.add(errs);
						}
						//=========================================>>>>>>>>>>>>>>>>
					}catch(Exception e){
						e.printStackTrace();
						msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.MessageN1", e.toString()));
					}
				}
				
			}
			
		}
		return msgs;
	}
	
	public List<Map> findDilg4ClassBook(List classBook, String sddate, int dtime_begin, int dtime_end) {
		List<Map> dilgList = new ArrayList();
		int i;
		String studentNo = "";
		String hql = "";
		
		for(Iterator cbIter=classBook.iterator(); cbIter.hasNext();) {
			Map cbMap = (Map)cbIter.next();
			studentNo = cbMap.get("studentNo").toString();
			
			hql = "Select d From Dilg d Where d.studentNo='" + studentNo;
			hql = hql + "' And ddate='" + sddate + "'";
			List<Dilg> dilgs = dao.submitQuery(hql);

			if(!dilgs.isEmpty()){
				Dilg dilg = dilgs.get(0);
				for(i=dtime_begin; i<dtime_end+1; i++){
					switch (i){
					case 0:
						if(dilg.getAbs0() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs0());
							dilgList.add(tfMap);
						}
						break;
					case 1:
						if(dilg.getAbs1() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs1());
							dilgList.add(tfMap);
						}
						break;
					case 2:
						if(dilg.getAbs2() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs2());
							dilgList.add(tfMap);
						}
						break;
					case 3:
						if(dilg.getAbs3() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs3());
							dilgList.add(tfMap);
						}
						break;
					case 4:
						if(dilg.getAbs4() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs4());
							dilgList.add(tfMap);
						}
						break;
					case 5:
						if(dilg.getAbs5() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs5());
							dilgList.add(tfMap);
						}
						break;
					case 6:
						if(dilg.getAbs6() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs6());
							dilgList.add(tfMap);
						}
						break;
					case 7:
						if(dilg.getAbs7() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs7());
							dilgList.add(tfMap);
						}
						break;
					case 8:
						if(dilg.getAbs8() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs8());
							dilgList.add(tfMap);
						}
						break;
					case 9:
						if(dilg.getAbs9() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs9());
							dilgList.add(tfMap);
						}
						break;
					case 10:
						if(dilg.getAbs10() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs10());
							dilgList.add(tfMap);
						}
						break;
					case 11:
						if(dilg.getAbs11() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs11());
							dilgList.add(tfMap);
						}
						break;
					case 12:
						if(dilg.getAbs12() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs12());
							dilgList.add(tfMap);
						}
						break;
					case 13:
						if(dilg.getAbs13() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs13());
							dilgList.add(tfMap);
						}
						break;
					case 14:
						if(dilg.getAbs14() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs14());
							dilgList.add(tfMap);
						}
						break;
					case 15:
						if(dilg.getAbs15() != null){
							Map tfMap = new HashMap();
							tfMap.put("studentNo", studentNo);
							tfMap.put("period", i);
							tfMap.put("status", dilg.getAbs15());
							dilgList.add(tfMap);
						}
						break;
					
					}
				}	//End of for
			}	//End Of if
		}
		return dilgList;
	}
	
	public List findDilgByStudentNo(String studentNo, String mode) {
		String hql ="";
		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Dilg dilg;
			
		if(mode.equalsIgnoreCase("subject")) {
			Map cbMap = new HashMap();
			cbMap.put("studentNo", studentNo);
			List cbList = new ArrayList();
			
			cbList.add(cbMap);
			
			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
			
			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid;
			int[] tfsum;
			int tfLimit, tfwarn, elearnDilg = 0;
			
			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();
				if(seld.getElearnDilg() != null){
					elearnDilg = seld.getElearnDilg();
				}else{
					elearnDilg = 0;
				}
				
				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);
				
				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}


				tfsum = this.calTimeOffBySubject(cbList, seld.getDtimeOid());
				
				Map tfMap = new HashMap();
				tfMap.put("cscode", seld.getCscode());
				tfMap.put("dtimeOid", seld.getDtimeOid());
				tfMap.put("departClass", dtime.getDepartClass());
				tfMap.put("subjectName", seld.getCscodeName());
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum[0]+elearnDilg);
				tfMap.put("elearnDilg", elearnDilg);
				if((tfsum[0]+elearnDilg) >= tfwarn && tfwarn != 0) {
					tfMap.put("warnning", "yes");
				} else {
					tfMap.put("warnning", "no");
				}
				//if(studentNo.equalsIgnoreCase("96107025")){
				//	log.debug("tfsum:" + tfsum[0] + ", elearnning:" + elearnDilg);
				//}
				dilgList.add(tfMap);
			}
		} else if(mode.equalsIgnoreCase("all")) {
			dilgList = dao.findDilgByStudentNo(studentNo);
			for(Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter.hasNext();) {
				dilg = dilgIter.next();
				dilg.setSddate(Toolket.printDate(dilg.getDdate()));
				dilg.setDeptClassName(Toolket.getClassFullName(dilg.getDepartClass()));
				dilg.setPdate(Global.defaultDateFormat.format(dilg.getDdate()));
				if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0)
					dilg.setAbsName0(Toolket.getTimeOff(dilg.getAbs0().toString()).substring(1,2));
				if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0)
					//log.debug("TimeOffQuery->Abs1:AbsName1:" + dilg.getAbs1() + ":" + Toolket.getTimeOff(dilg.getAbs1().toString()));
					dilg.setAbsName1(Toolket.getTimeOff(dilg.getAbs1().toString()).substring(1,2));
				if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0)
					dilg.setAbsName2(Toolket.getTimeOff(dilg.getAbs2().toString()).substring(1,2));
				if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0)
					dilg.setAbsName3(Toolket.getTimeOff(dilg.getAbs3().toString()).substring(1,2));
				if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0)
					dilg.setAbsName4(Toolket.getTimeOff(dilg.getAbs4().toString()).substring(1,2));
				if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0)
					dilg.setAbsName5(Toolket.getTimeOff(dilg.getAbs5().toString()).substring(1,2));
				if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0)
					dilg.setAbsName6(Toolket.getTimeOff(dilg.getAbs6().toString()).substring(1,2));
				if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0)
					dilg.setAbsName7(Toolket.getTimeOff(dilg.getAbs7().toString()).substring(1,2));
				if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0)
					dilg.setAbsName8(Toolket.getTimeOff(dilg.getAbs8().toString()).substring(1,2));
				if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0)
					dilg.setAbsName9(Toolket.getTimeOff(dilg.getAbs9().toString()).substring(1,2));
				if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0)
					dilg.setAbsName10(Toolket.getTimeOff(dilg.getAbs10().toString()).substring(1,2));
				if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0)
					dilg.setAbsName11(Toolket.getTimeOff(dilg.getAbs11().toString()).substring(1,2));
				if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0)
					dilg.setAbsName12(Toolket.getTimeOff(dilg.getAbs12().toString()).substring(1,2));
				if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0)
					dilg.setAbsName13(Toolket.getTimeOff(dilg.getAbs13().toString()).substring(1,2));
				if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0)
					dilg.setAbsName14(Toolket.getTimeOff(dilg.getAbs14().toString()).substring(1,2));
				if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0)
					dilg.setAbsName15(Toolket.getTimeOff(dilg.getAbs15().toString()).substring(1,2));

			}
		} else if(mode.equalsIgnoreCase("elearn")) {
			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
			
			for(Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();){
				Seld seld = seldIter.next();
				if(seld.getElearnDilg() != null){
					dilgList.add(seld);
				}
			}
		}
		
		return dilgList;
		
	}
	
	public List findPeriodDilgByStudentNo(String studentNo, String dateBegin, String dateEnd, String mode) {
		String hql ="";
		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Dilg dilg;
			
		if(mode.equalsIgnoreCase("subject")) {
			Map cbMap = new HashMap();
			cbMap.put("studentNo", studentNo);
			List cbList = new ArrayList();
			
			cbList.add(cbMap);
			
			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
			
			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid;
			int[] tfsum;
			int tfLimit, tfwarn;
			
			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();
				
				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);
				
				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}


				tfsum = this.calPeriodTimeOffBySubject(cbList, dateBegin, dateEnd, seld.getDtimeOid());
				
				Map tfMap = new HashMap();
				tfMap.put("cscode", seld.getCscode());
				tfMap.put("dtimeOid", seld.getDtimeOid());
				tfMap.put("departClass", dtime.getDepartClass());
				tfMap.put("subjectName", seld.getCscodeName());
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum[0]);
				if(tfsum[0] > tfwarn && tfwarn != 0) {
					tfMap.put("warnning", "yes");
				} else {
					tfMap.put("warnning", "no");
				}
				dilgList.add(tfMap);
			}
		} else if(mode.equalsIgnoreCase("all")) {
			hql = "Select d From Dilg d Where studentNo='" + studentNo + "'";
			hql += " and ddate Between '" + dateBegin + "' and '" + dateEnd + "'";
			hql += " Order by ddate";
			
			dilgList = dao.submitQuery(hql);
			for(Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter.hasNext();) {
				dilg = dilgIter.next();
				dilg.setSddate(Toolket.printDate(dilg.getDdate()));
				dilg.setDeptClassName(Toolket.getClassFullName(dilg.getDepartClass()));
				dilg.setPdate(Global.defaultDateFormat.format(dilg.getDdate()));
				if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0)
					dilg.setAbsName0(Toolket.getTimeOff(dilg.getAbs0().toString()).substring(1,2));
				if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0)
					//log.debug("TimeOffQuery->Abs1:AbsName1:" + dilg.getAbs1() + ":" + Toolket.getTimeOff(dilg.getAbs1().toString()));
					dilg.setAbsName1(Toolket.getTimeOff(dilg.getAbs1().toString()).substring(1,2));
				if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0)
					dilg.setAbsName2(Toolket.getTimeOff(dilg.getAbs2().toString()).substring(1,2));
				if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0)
					dilg.setAbsName3(Toolket.getTimeOff(dilg.getAbs3().toString()).substring(1,2));
				if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0)
					dilg.setAbsName4(Toolket.getTimeOff(dilg.getAbs4().toString()).substring(1,2));
				if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0)
					dilg.setAbsName5(Toolket.getTimeOff(dilg.getAbs5().toString()).substring(1,2));
				if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0)
					dilg.setAbsName6(Toolket.getTimeOff(dilg.getAbs6().toString()).substring(1,2));
				if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0)
					dilg.setAbsName7(Toolket.getTimeOff(dilg.getAbs7().toString()).substring(1,2));
				if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0)
					dilg.setAbsName8(Toolket.getTimeOff(dilg.getAbs8().toString()).substring(1,2));
				if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0)
					dilg.setAbsName9(Toolket.getTimeOff(dilg.getAbs9().toString()).substring(1,2));
				if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0)
					dilg.setAbsName10(Toolket.getTimeOff(dilg.getAbs10().toString()).substring(1,2));
				if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0)
					dilg.setAbsName11(Toolket.getTimeOff(dilg.getAbs11().toString()).substring(1,2));
				if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0)
					dilg.setAbsName12(Toolket.getTimeOff(dilg.getAbs12().toString()).substring(1,2));
				if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0)
					dilg.setAbsName13(Toolket.getTimeOff(dilg.getAbs13().toString()).substring(1,2));
				if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0)
					dilg.setAbsName14(Toolket.getTimeOff(dilg.getAbs14().toString()).substring(1,2));
				if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0)
					dilg.setAbsName15(Toolket.getTimeOff(dilg.getAbs15().toString()).substring(1,2));

			}
		}
		
		return dilgList;
		
	}
	

	
	public List findDilgByStudentNoAndCscode(String studentNo, String cscode) {

		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Map cbMap = new HashMap();
		cbMap.put("studentNo", studentNo);
		List cbList = new ArrayList();
		cbList.add(cbMap);
		List<Seld> seldList = dao.findSeldByStudentNoAndCscode(studentNo,
				cscode, sterm);

		Seld seld;
		List<Dtime> dtimeList;
		Dtime dtime;
		int dtOid;
		int[] tfsum;
		int tfLimit, tfwarn;

		for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			dtOid = seld.getDtimeOid();

			dtimeList = (List<Dtime>) (dao.submitQuery("From Dtime Where Oid="
					+ dtOid));
			dtime = dtimeList.get(0);

			tfLimit = 0;

			if (Toolket.chkIsGraduateClass(dtime.getDepartClass())
					&& sterm.equals("2")) {
				if(dtime.getThour() * 14 % 3 > 0){
					tfLimit = dtime.getThour() * 14 / 3 + 1;
				}else{
					tfLimit = dtime.getThour() * 14 / 3;
				}
				tfwarn = (int) (tfLimit * 0.9);
			} else {
				tfLimit = (int) Math
						.round(Math.ceil(dtime.getThour() * 18 / 3));
				tfwarn = (int) (tfLimit * 0.9);
			}

			tfsum = this.calTimeOffBySubject(cbList, seld.getDtimeOid());

			Map tfMap = new HashMap();
			tfMap.put("cscode", seld.getCscode());
			tfMap.put("dtimeOid", seld.getDtimeOid());
			tfMap.put("departClass", dtime.getDepartClass());
			tfMap.put("subjectName", seld.getCscodeName());
			tfMap.put("period", dtime.getThour());
			tfMap.put("tfLimit", tfLimit);
			tfMap.put("timeOff", tfsum[0]);
			if (tfsum[0] > tfwarn && tfwarn != 0) {
				tfMap.put("warnning", "yes");
			} else {
				tfMap.put("warnning", "no");
			}
			dilgList.add(tfMap);
		}

		return dilgList;
	}
	
	public String chkStudentDepart(String studentNo) {
		char dyflag = '2';
		String daynite = "";
		
		if(studentNo.length() == 7)
			dyflag = studentNo.charAt(2);
		else if(studentNo.length() == 8)
			dyflag = studentNo.charAt(3);
		
		//log.debug("StudTimeOffQuery->dyflag:" + dyflag);
		switch (dyflag) {
			case '2': case '3': case '4': case '5':
				daynite = "1";
				break;
			
			case '0': case '1': case '8': case '9': case 'A':
				daynite = "2";
				break;
			case '6': case '7':
				daynite = "3";
				break;
			case 'G':
				if(Integer.parseInt(studentNo.substring(5, 6)) <= 4)
					daynite = "1";
				else
					daynite = "2";
				break;
			default:
				daynite = "1";
		
		}
		return daynite;
	}
	
	public List<Code2> findBonusPenaltyReason(String code) {
		return dao.findCode2List(code);
	}
	
	public ActionMessages createDesdByForm(Map formMap) {
		ActionMessages msgs = new ActionMessages();
		
  		String mode = formMap.get("mode").toString();
  		String bpYear = formMap.get("bpYear").toString();
  		String bpMonth = formMap.get("bpMonth").toString();
  		String bpDay = formMap.get("bpDay").toString();
  		String docNo = formMap.get("docNo").toString();
  		String reason = formMap.get("reason").toString();
  		String kind1 = formMap.get("kind1").toString();
  		String kind2 = formMap.get("kind2").toString();
  		short cnt1 = 0, cnt2 = 0;
  		if(!formMap.get("cnt1").toString().equals(""))
  			cnt1 = Short.parseShort(formMap.get("cnt1").toString());
  		if(!formMap.get("cnt2").toString().equals(""))
  			cnt2= Short.parseShort(formMap.get("cnt2").toString());
  		String[] studentNos = (String[])formMap.get("studentNo");
  		Date ddate = Toolket.parseDateSerial(bpYear + bpMonth + bpDay);
  		Student student;
  		try{
  			for(int i=0; i<studentNos.length; i++) {
  				Desd desd = new Desd();
  				List studList = scoredao.findStudentByStudentNO(studentNos[i]);
  				if(!studList.isEmpty()) {
  					student = (Student)studList.get(0);
  	  				desd.setDdate(ddate);
  	  				desd.setStudentNo(studentNos[i]);
  	  				desd.setDepartClass(student.getDepartClass());
  	  				desd.setNo(docNo);
  	  				desd.setReason(reason);
  	  				if(!kind1.equals("")) desd.setKind1(kind1);
  	  				if(!kind2.equals("")) desd.setKind2(kind2);
  	  				if(cnt1 > 0) desd.setCnt1(cnt1);
  	  				if(cnt2 > 0) desd.setCnt2(cnt2);
  	  				dao.saveObject(desd);

  				} else {
  		  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
  							new ActionMessage("Message.createDesdError"));

  		  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
  							new ActionMessage("Message.StudentNotFound",studentNos[i]));
  				}
  			}
  		}
  		catch(Exception e) {
  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.createDesdError"));
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Exception.generic", e.toString()));
  		}
		return msgs;
	}
	
	public ActionMessages createDesdByProperties(Map propMap) {
		ActionMessages msgs = new ActionMessages();
		
  		String bpYear = propMap.get("bpYear").toString();
  		String bpMonth = propMap.get("bpMonth").toString();
  		String bpDay = propMap.get("bpDay").toString();
  		String docNo = propMap.get("docNo").toString();
  		String reason = propMap.get("reason").toString();
  		String kind1 = propMap.get("kind1").toString();
  		String kind2 = propMap.get("kind2").toString();
  		short cnt1 = 0, cnt2 = 0;
  		if(!propMap.get("cnt1").toString().equals(""))
  			cnt1 = Short.parseShort(propMap.get("cnt1").toString());
  		if(!propMap.get("cnt2").toString().equals(""))
  			cnt2= Short.parseShort(propMap.get("cnt2").toString());
  		String studentNo = propMap.get("studentNo").toString();
  		Date ddate = Toolket.parseDate(bpYear + "-" + bpMonth + "-" + bpDay);
  		Student student;
  		try{
  				Desd desd = new Desd();
  				List studList = scoredao.findStudentByStudentNO(studentNo);
  				if(!studList.isEmpty()) {
  					student = (Student)studList.get(0);
  	  				desd.setDdate(ddate);
  	  				desd.setStudentNo(studentNo);
  	  				desd.setDepartClass(student.getDepartClass());
  	  				desd.setNo(docNo);
  	  				desd.setReason(reason);
  	  				if(!kind1.equals("")) desd.setKind1(kind1);
  	  				if(!kind2.equals("")) desd.setKind2(kind2);
  	  				if(cnt1 > 0) desd.setCnt1(cnt1);
  	  				if(cnt2 > 0) desd.setCnt2(cnt2);
  	  				dao.saveObject(desd);

  				} else {
  		  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
  							new ActionMessage("Message.createDesdError"));

  		  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
  							new ActionMessage("Message.StudentNotFound",studentNo));
  				}
  		}
  		catch(Exception e) {
  			e.printStackTrace();
  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.createDesdError"));
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Exception.generic", e.toString()));
  		}
		return msgs;
	}

	
	public ActionMessages createDesdByStudents(Map formMap, List<Student> students) {
		ActionMessages msgs = new ActionMessages();
		
  		String mode = formMap.get("mode").toString();
  		String bpYear = formMap.get("bpYear").toString();
  		String bpMonth = formMap.get("bpMonth").toString();
  		String bpDay = formMap.get("bpDay").toString();
  		String docNo = formMap.get("docNo").toString();
  		String reason = formMap.get("reason").toString();
  		String kind1 = formMap.get("kind1").toString();
  		String kind2 = formMap.get("kind2").toString();
  		short cnt1 = 0, cnt2 = 0;
  		if(!formMap.get("cnt1").toString().equals(""))
  			cnt1 = Short.parseShort(formMap.get("cnt1").toString());
  		if(!formMap.get("cnt2").toString().equals(""))
  			cnt2= Short.parseShort(formMap.get("cnt2").toString());
  		
  		Date ddate = Toolket.parseDateSerial(bpYear + bpMonth + bpDay);
  		Student student;
  		try{
  			for(Iterator<Student> studIter=students.iterator(); studIter.hasNext();) {
  				Desd desd = new Desd();
  				student = studIter.next();
  	  			desd.setDdate(ddate);
  	  			desd.setStudentNo(student.getStudentNo());
  	  			desd.setDepartClass(student.getDepartClass());
  	  			desd.setNo(docNo);
  	  			desd.setReason(reason);
  	  			if(!kind1.equals("")) desd.setKind1(kind1);
  	  			if(!kind2.equals("")) desd.setKind2(kind2);
  	  			if(cnt1 > 0) desd.setCnt1(cnt1);
  	  			if(cnt2 > 0) desd.setCnt2(cnt2);
  	  			dao.saveObject(desd);
  			}
  		}
  		catch(Exception e) {
  			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.createDesdError"));
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Exception.generic", e.toString()));
  		}
		return msgs;
	}
	
	public List<Desd> findDesdByFormInfo(String ddate, String studentNo, String classInCharge) {
		String hql = "Select d From Desd d";
		if(!ddate.equals("")) {
			hql = hql + " Where ddate='" + ddate +"'";
			if(!studentNo.equals("")) {
				hql = hql + " And studentNo like '" + studentNo + "%'";
				hql = hql + " And departClass in " + classInCharge;
			} else {
				hql = hql + " And departClass in " + classInCharge;
			}
		} else if(!studentNo.equals("")) {
			hql = hql + " Where studentNo like '" + studentNo + "%'";
			hql = hql + " And departClass in " + classInCharge;
		} else {
			hql = hql + " Where departClass in " + classInCharge;
		}
		hql = hql + " Order by ddate desc";
		log.debug("findDesdByBonusPenaltyInfo=====>" + classInCharge);
		log.debug("findDesdByBonusPenaltyInfo=====>" + hql);
		List<Desd> desdlist = (List<Desd>)dao.submitQuery(hql);
		
		return desdlist;
	}

	public List<Desd> delStudBonusPenalty(List<Desd> selDesds, ResourceBundle bundle) {
		List<Desd> undelDesds = new ArrayList<Desd>();
		Desd desd = new Desd();
		for(Iterator<Desd> desdIter = selDesds.iterator(); desdIter.hasNext();) {
			desd = desdIter.next();
			log.debug("====>Remove Desd:" + desd.getOid());
			dao.removeDesd(desd);
		}
		return undelDesds;

	}
	
	public ActionMessages modifyDesdByForm(List<Desd> desdInEdit, Map formMap){
		ActionMessages errors = new ActionMessages();
		
  		String[] bpYear = (String[])formMap.get("bpYear");
  		String[] bpMonth = (String[])formMap.get("bpMonth");
  		String[] bpDay = (String[])formMap.get("bpDay");
  		String[] docNo = (String[])formMap.get("docNo");
  		String[] reason = (String[])formMap.get("reason");
  		String[] kind1 = (String[])formMap.get("kind1");
  		String[] kind2 = (String[])formMap.get("kind2");
  		String[] cnt1 = (String[])formMap.get("cnt1");
  		String[] cnt2 = (String[])formMap.get("cnt2");
  		
  		int count = 0;
  		Desd desd;
  		try{
  	  		for(Iterator<Desd> desdIter = desdInEdit.iterator(); desdIter.hasNext();) {
  	  			desd = desdIter.next();
  	  			//log.debug("ModifyDesd:" + bpYear[count] + bpMonth[count] + bpDay[count] + "," + Toolket.parseDateSerial(bpYear[count] + bpMonth[count] + bpDay[count]));
  	  			desd.setDdate(Toolket.parseDate(bpYear[count] + "-" + bpMonth[count] + "-" + bpDay[count]));
  	  			desd.setNo(docNo[count]);
  	  			desd.setReason(reason[count]);
  	  			desd.setKind1(kind1[count]);
  	  			desd.setKind2(kind2[count]);
  	  			if(cnt1[count].equals("")) desd.setCnt1(null);
  	  			else if(Short.parseShort(cnt1[count]) > 0) desd.setCnt1(Short.parseShort(cnt1[count]));
  	  			if(cnt2[count].equals("")) desd.setCnt2(null);
  	  			else if(Short.parseShort(cnt2[count]) > 0) desd.setCnt2(Short.parseShort(cnt2[count]));
  	  			
  	  			dao.saveObject(desd);
  	  			count++;
  	  		}
  		} catch(Exception e){
  			e.printStackTrace();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));

  		}
  		return errors;
	}
	
	public double[] calConductScoreOfDilgDesd(String studentNo) {
		String debugs = "";
		double[] addelScore = new double[2];
		addelScore[0] = 0d;
		addelScore[1] = 0d;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		int tflen = tfTypeList.size();
		
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		double[][] addsub = new double[6][6];
			
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tflen; k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
		List<Student> studList = scoredao.findStudentByStudentNO(studentNo);
		if(!studList.isEmpty()){
			Student student = studList.get(0);
			String hql ="";
			
			//*** 1.process Dilg				
			List<Dilg> dilgList = dao.findDilgByStudentNo(studentNo);
			//int[] tf = new int[tfTypeList.size()-1];
			Dilg dilg;
			short tfType = 0;
			int iweek = 0;
			
			//將該學生曠缺資料填入timeoffWeek陣列
			for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
				dilg =dilgIter.next();
				iweek = Math.round(dilg.getWeekDay());
				//iweek = this.WhatIsTheWeek(dilg.getDdate());
				if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
					tfType = dilg.getAbs0();
					timeoffWeek[iweek][0][tfType]++;
				}
				if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
					tfType = dilg.getAbs1();
					timeoffWeek[iweek][1][tfType]++;
				}
				if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
					tfType = dilg.getAbs2();
					timeoffWeek[iweek][2][tfType]++;
				}
				if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
					tfType = dilg.getAbs3();
					timeoffWeek[iweek][3][tfType]++;
				}
				if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
					tfType = dilg.getAbs4();
					timeoffWeek[iweek][4][tfType]++;
				}
				if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
					tfType = dilg.getAbs5();
					timeoffWeek[iweek][5][tfType]++;
				}
				if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
					tfType = dilg.getAbs6();
					timeoffWeek[iweek][6][tfType]++;
				}
				if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
					tfType = dilg.getAbs7();
					timeoffWeek[iweek][7][tfType]++;
				}
				if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
					tfType = dilg.getAbs8();
					timeoffWeek[iweek][8][tfType]++;
				}
				if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
					tfType = dilg.getAbs9();
					timeoffWeek[iweek][9][tfType]++;
				}
				if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
					tfType = dilg.getAbs10();
					timeoffWeek[iweek][10][tfType]++;
				}
				if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
					tfType = dilg.getAbs11();
					timeoffWeek[iweek][11][tfType]++;
				}
				if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
					tfType = dilg.getAbs12();
					timeoffWeek[iweek][12][tfType]++;
				}
				if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
					tfType = dilg.getAbs13();
					timeoffWeek[iweek][13][tfType]++;
				}
				if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
					tfType = dilg.getAbs14();
					timeoffWeek[iweek][14][tfType]++;
				}
				if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
					tfType = dilg.getAbs15();
					timeoffWeek[iweek][15][tfType]++;
				}
			}
			
			//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
			//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
			//不同部制有不同之操行加減分標準
			List subjDilg = this.findDilgByStudentNo(studentNo, "subject");
			
			for(Iterator sDilgIter = subjDilg.iterator(); sDilgIter.hasNext();) {
				Map sdMap = new HashMap();
				sdMap = (Map)sDilgIter.next();
				int tfLimit = Integer.parseInt(sdMap.get("tfLimit").toString());
				int timeoff = Integer.parseInt(sdMap.get("timeOff").toString());
				String departClass = sdMap.get("departClass").toString();
				/*
				 * 				tfMap.put("cscode", seld.getCscode());
				tfMap.put("dtimeOid", seld.getDtimeOid());
				tfMap.put("departClass", dtime.getDepartClass());
				tfMap.put("subjectName", seld.getCscodeName());
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum[0]);

				 */
				
				//log.debug("扣考->depart_class:" + departClass + ",student_no:" + studentNo + ",subject:" + 
				//		sdMap.get("cscode") + ";" + sdMap.get("subjectName") + ",tfLimit:" + 
				//		sdMap.get("tfLimit")  + ",timeOff:" + sdMap.get("timeOff") + ",warnning:" + sdMap.get("warnning"));
				
				hql = "Select dc From DtimeClass dc Where dtimeOid=" + sdMap.get("dtimeOid");
				List<DtimeClass> dcList = (List<DtimeClass>)dao.submitQuery(hql);
				
				for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
					DtimeClass dclass = dcIter.next();
					iweek = dclass.getWeek();
					for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
						if(timeoff < tfLimit) {
							depClass[iweek][m] = sdMap.get("departClass").toString();
						}
						else {
							for(int n =0; n < tflen; n++){
								timeoffWeek[iweek][m][n] = 0;
							}
						}
					}
				}
			}
			
			String departClass = student.getDepartClass();
			String depart = this.chkStudentDepart(studentNo);
			short[] dilgSubtotal = new short[tflen]; 
			for(int z = 0; z < tflen; z++){
				dilgSubtotal[z] = 0;
			}

			//統計該學生曠缺種類及其次數
			for(int i = 1; i < 8; i++) {
				for(int j = 0; j <16; j++){
					for(int k = 1; k<tflen; k++ ){
						dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
					}
				}
			}
			
			/*
			debugs = "calConductScoreOfDilgDesd->dilg:" + studentNo + ": ";
			for(int x=1; x<8; x++){
				debugs = debugs + ", kind" + x + "=" + dilgSubtotal[x];
			}
			log.debug(debugs);
			*/
			
			//全勤加3分,有曠缺按部制規定扣分
			if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
					&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
					&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
				addelScore[0] = addelScore[0] + 3d;
			}else{
				if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
						addelScore[0] = addelScore[0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
						addelScore[0] = addelScore[0] + dilgSubtotal[2] * 0.5;
						addelScore[0] = addelScore[0] + dilgSubtotal[3] * 0.1;
						addelScore[0] = addelScore[0] + dilgSubtotal[4] * 0.2;
						addelScore[0] = addelScore[0] + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
						//addelScore[0] = addelScore[0] + dilgSubtotal[6] * 0.0;
						//addelScore[0] = addelScore[0] + dilgSubtotal[7] * 0.0;
						addelScore[0] = addelScore[0] * -1d;
				} else if(depart.equals("3")) {
						addelScore[0] = addelScore[0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
						addelScore[0] = addelScore[0] + dilgSubtotal[2] * 1.0;
						if(dilgSubtotal[3] > 26)
							addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[3]-26) * 0.1;
						if(dilgSubtotal[4] > 13)
							addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[4]-13) * 0.1;
						if(dilgSubtotal[5] > 4)
							addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[5]-4) * 0.1;
						addelScore[0] = addelScore[0] * -1d;
				}
			}
			
			//*** 2.process Desd
			List<Desd> desdList = this.findDesdByStudentNo(studentNo);
			List<Subs> subsList = this.findSubs("");
			
			Subs subs;
			int no;
			for(Iterator<Subs> subsIter = subsList.iterator(); subsIter.hasNext();){
				subs = subsIter.next();
				no = Integer.parseInt(subs.getNo()) - 1;
				addsub[no][0] = subs.getFirst();
				addsub[no][1] = subs.getSecond();
				addsub[no][2] = subs.getThird();
				addsub[no][3] = subs.getFourth();
				addsub[no][4] = subs.getFifth();
				addsub[no][5] = subs.getSixth();
			}
			
			Desd desd;
			String kind;
			short[] bonusPenalty = new short[7];
			for(int i=0; i<7; i++) bonusPenalty[i]=0;
			int cnt= 0;
			
			for(Iterator<Desd> desdIter = desdList.iterator(); desdIter.hasNext();){
				desd = desdIter.next();
				kind = desd.getKind1();
				if(kind != null) {
					if(!kind.equals("")) {
						if(kind.equals("1")){
							bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt1());
						}else if(kind.equals("2")){
							bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt1());
						}else if(kind.equals("3")){
							bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt1());
						}else if(kind.equals("4")){
							bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt1());
						}else if(kind.equals("5")){
							bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt1());
						}else if(kind.equals("6")){
							bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt1());
						}
					}
				}
				kind = desd.getKind2();
				if(kind != null) {
					if(!kind.equals("")) {
						if(kind.equals("1")){
							bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt2());
						}else if(kind.equals("2")){
							bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt2());
						}else if(kind.equals("3")){
							bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt2());
						}else if(kind.equals("4")){
							bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt2());
						}else if(kind.equals("5")){
							bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt2());
						}else if(kind.equals("6")){
							bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt2());
						}
					}
				}
			}
			for(int i = 1; i < 7; i++){
				if(bonusPenalty[i] > 0){
					//debugs = debugs + "[" + i + "]:[" + bonusPenalty[i] + "]";
					cnt =0;
					for(int k = 0; k < bonusPenalty[i]; k++) {
						if(cnt > 5) cnt = 5;
						addelScore[1] = addelScore[1] + addsub[i-1][cnt];
						cnt++;
					}
				}
			}
			
		}
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore[0] > 0d)
			addelScore[0] = Math.round(addelScore[0] * 100d) / 100d;
		else if(addelScore[0] < 0d)
			addelScore[0] = Math.round(addelScore[0] * 100d * -1d) / 100d * -1d;
		if(addelScore[1] > 0d)
			addelScore[1] = Math.round(addelScore[1] * 100d) / 100d;
		else if(addelScore[1] < 0d)
			addelScore[1] = Math.round(addelScore[1] * 100d * -1d) / 100d * -1d;
		
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		//release resources
		timeoffWeek = null;
		depClass = null;
		addsub = null;
		
		return addelScore;
	}

	public double[][] calConductScoreBatch(List studentNos) {
		String debugs = "";
		double[][] addelScore = new double[studentNos.size()][2];
		for(int i=0; i<studentNos.size(); i++){
			addelScore[i][0] = 0d;
			addelScore[i][1] = 0d;
		}
		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		int tflen = tfTypeList.size();
		
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		String[][] depClass = new String[8][16];
		double[][] addsub = new double[6][6];
		String studentNo = "";
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;
		int stucnt = 0;
		
		List<Subs> subsList = this.findSubs("");
		
		Subs subs;
		int no;
		for(Iterator<Subs> subsIter = subsList.iterator(); subsIter.hasNext();){
			subs = subsIter.next();
			no = Integer.parseInt(subs.getNo()) - 1;
			addsub[no][0] = subs.getFirst();
			addsub[no][1] = subs.getSecond();
			addsub[no][2] = subs.getThird();
			addsub[no][3] = subs.getFourth();
			addsub[no][4] = subs.getFifth();
			addsub[no][5] = subs.getSixth();
		}
		
		for(Iterator stuIter=studentNos.iterator(); stuIter.hasNext();){
			studentNo = stuIter.next().toString();
			// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j <16; j++){
					depClass[i][j] = "";
	
					for(int k = 0; k < tflen; k++){
						timeoffWeek[i][j][k] = 0;
					}
				}
			}
		
			List<Student> studList = scoredao.findStudentByStudentNO(studentNo);
			if(!studList.isEmpty()){
				Student student = studList.get(0);
				String hql ="";
			
				//*** 1.process Dilg				
				List<Dilg> dilgList = dao.findDilgByStudentNo(studentNo);
				//int[] tf = new int[tfTypeList.size()-1];
				tfType = 0;
				iweek = 0;
			
				//將該學生曠缺資料填入timeoffWeek陣列
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
					dilg =dilgIter.next();
					iweek = Math.round(dilg.getWeekDay());
					//iweek = this.WhatIsTheWeek(dilg.getDdate());
					if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
						tfType = dilg.getAbs0();
						timeoffWeek[iweek][0][tfType]++;
					}
					if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
						tfType = dilg.getAbs1();
						timeoffWeek[iweek][1][tfType]++;
					}
					if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
						tfType = dilg.getAbs2();
						timeoffWeek[iweek][2][tfType]++;
					}
					if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
						tfType = dilg.getAbs3();
						timeoffWeek[iweek][3][tfType]++;
					}
					if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
						tfType = dilg.getAbs4();
						timeoffWeek[iweek][4][tfType]++;
					}
					if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
						tfType = dilg.getAbs5();
						timeoffWeek[iweek][5][tfType]++;
					}
					if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
						tfType = dilg.getAbs6();
						timeoffWeek[iweek][6][tfType]++;
					}
					if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
						tfType = dilg.getAbs7();
						timeoffWeek[iweek][7][tfType]++;
					}
					if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
						tfType = dilg.getAbs8();
						timeoffWeek[iweek][8][tfType]++;
					}
					if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
						tfType = dilg.getAbs9();
						timeoffWeek[iweek][9][tfType]++;
					}
					if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
						tfType = dilg.getAbs10();
						timeoffWeek[iweek][10][tfType]++;
					}
					if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
						tfType = dilg.getAbs11();
						timeoffWeek[iweek][11][tfType]++;
					}
					if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
						tfType = dilg.getAbs12();
						timeoffWeek[iweek][12][tfType]++;
					}
					if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
						tfType = dilg.getAbs13();
						timeoffWeek[iweek][13][tfType]++;
					}
					if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
						tfType = dilg.getAbs14();
						timeoffWeek[iweek][14][tfType]++;
					}
					if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
						tfType = dilg.getAbs15();
						timeoffWeek[iweek][15][tfType]++;
					}
				}
			
				//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
				//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
				//不同部制有不同之操行加減分標準
				List subjDilg = this.findDilgByStudentNo(studentNo, "subject");
			
				for(Iterator sDilgIter = subjDilg.iterator(); sDilgIter.hasNext();) {
					Map sdMap = new HashMap();
					sdMap = (Map)sDilgIter.next();
					int tfLimit = Integer.parseInt(sdMap.get("tfLimit").toString());
					int timeoff = Integer.parseInt(sdMap.get("timeOff").toString());
					String departClass = sdMap.get("departClass").toString();
					
					hql = "Select dc From DtimeClass dc Where dtimeOid=" + sdMap.get("dtimeOid");
					List<DtimeClass> dcList = (List<DtimeClass>)dao.submitQuery(hql);
					
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = dclass.getWeek();
						for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
							if(timeoff < tfLimit) {
								depClass[iweek][m] = sdMap.get("departClass").toString();
							}
							else {
								for(int n =0; n < tfTypeList.size(); n++){
									timeoffWeek[iweek][m][n] = 0;
								}
							}
						}
					}
				}
			
				String departClass = student.getDepartClass();
				String depart = this.chkStudentDepart(studentNo);
				short[] dilgSubtotal = new short[tflen]; 
				for(int z = 0; z < tflen; z++){
					dilgSubtotal[z] = 0;
				}
	
				//統計該學生曠缺種類及其次數
				for(int i = 1; i < 8; i++) {
					for(int j = 0; j <16; j++){
						for(int k = 1; k<tflen; k++ ){
							dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
						}
					}
				}
			
				/*
				debugs = "calConductScoreOfDilgDesd->dilg:" + studentNo + ": ";
				for(int x=1; x<8; x++){
					debugs = debugs + ", kind" + x + "=" + dilgSubtotal[x];
				}
				log.debug(debugs);
				*/
				
				//全勤加3分,有曠缺按部制規定扣分
				if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
					&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
					&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
					addelScore[stucnt][0] = addelScore[stucnt][0] + 3d;
				}else{
					if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[2] * 0.5;
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[3] * 0.1;
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[4] * 0.2;
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
						//addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[6] * 0.0;
						//addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[7] * 0.0;
						addelScore[stucnt][0] = addelScore[stucnt][0] * -1d;
					} else if(depart.equals("3")) {
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
						addelScore[stucnt][0] = addelScore[stucnt][0] + dilgSubtotal[2] * 1.0;
						if(dilgSubtotal[3] > 26)
							addelScore[stucnt][0] = addelScore[stucnt][0] + 1 + (dilgSubtotal[3]-26) * 0.1;
						if(dilgSubtotal[4] > 13)
							addelScore[stucnt][0] = addelScore[stucnt][0] + 1 + (dilgSubtotal[4]-13) * 0.1;
						if(dilgSubtotal[5] > 4)
							addelScore[stucnt][0] = addelScore[stucnt][0] + 1 + (dilgSubtotal[5]-4) * 0.1;
						addelScore[stucnt][0] = addelScore[stucnt][0] * -1d;
					}
				}
			
				//*** 2.process Desd
				List<Desd> desdList = this.findDesdByStudentNo(studentNo);
				Desd desd;
				String kind;
				short[] bonusPenalty = new short[7];
				for(int i=0; i<7; i++) bonusPenalty[i]=0;
				int cnt= 0;
			
				for(Iterator<Desd> desdIter = desdList.iterator(); desdIter.hasNext();){
					desd = desdIter.next();
					kind = desd.getKind1();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt1());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt1());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt1());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt1());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt1());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt1());
							}
						}
					}
					kind = desd.getKind2();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt2());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt2());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt2());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt2());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt2());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt2());
							}
						}
					}
				}
				for(int i = 1; i < 7; i++){
					if(bonusPenalty[i] > 0){
						//debugs = debugs + "[" + i + "]:[" + bonusPenalty[i] + "]";
						cnt =0;
						for(int k = 0; k < bonusPenalty[i]; k++) {
							if(cnt > 5) cnt = 5;
							addelScore[stucnt][1] = addelScore[stucnt][1] + addsub[i-1][cnt];
							cnt++;
						}
					}
				}
			
			}
			//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
			//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
			if(addelScore[stucnt][0] > 0d)
				addelScore[stucnt][0] = Math.round(addelScore[stucnt][0] * 100d) / 100d;
			else if(addelScore[stucnt][0] < 0d)
				addelScore[stucnt][0] = Math.round(addelScore[stucnt][0] * 100d * -1d) / 100d * -1d;
			if(addelScore[stucnt][1] > 0d)
				addelScore[stucnt][1] = Math.round(addelScore[stucnt][1] * 100d) / 100d;
			else if(addelScore[stucnt][1] < 0d)
				addelScore[stucnt][1] = Math.round(addelScore[stucnt][1] * 100d * -1d) / 100d * -1d;
			
			stucnt++;
			
			//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		}		
		return addelScore;
	}

	
	
	public double[] calPeriodConductScoreOfDilgDesd(String studentNo, String dateBegin, String dateEnd, String Mode) {
		String debugs = "";
		double[] addelScore = new double[2];
		addelScore[0] = 0d;
		addelScore[1] = 0d;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		int tflen = tfTypeList.size();
		
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		double[][] addsub = new double[6][6];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tfTypeList.size(); k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
		List<Student> studList = scoredao.findStudentByStudentNO(studentNo);
		if(!studList.isEmpty()){
			if(Mode.equals("1")||Mode.equals("3")){
				Student student = studList.get(0);
				String hql ="";
				
				//*** 1.process Dilg
				hql = "Select d From Dilg d Where studentNo='" + studentNo + "'";
				hql += " and ddate Between '" + dateBegin + "' and '" + dateEnd + "'";
				hql += " Order by ddate";
				
				List<Dilg> dilgList = dao.submitQuery(hql);
				//int[] tf = new int[tfTypeList.size()-1];
				Dilg dilg;
				short tfType = 0;
				int iweek = 0;
				
				//將該學生曠缺資料填入timeoffWeek陣列
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
					dilg =dilgIter.next();
					iweek = Math.round(dilg.getWeekDay());
					//iweek = this.WhatIsTheWeek(dilg.getDdate());
					if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
						tfType = dilg.getAbs0();
						timeoffWeek[iweek][0][tfType]++;
					}
					if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
						tfType = dilg.getAbs1();
						timeoffWeek[iweek][1][tfType]++;
					}
					if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
						tfType = dilg.getAbs2();
						timeoffWeek[iweek][2][tfType]++;
					}
					if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
						tfType = dilg.getAbs3();
						timeoffWeek[iweek][3][tfType]++;
					}
					if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
						tfType = dilg.getAbs4();
						timeoffWeek[iweek][4][tfType]++;
					}
					if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
						tfType = dilg.getAbs5();
						timeoffWeek[iweek][5][tfType]++;
					}
					if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
						tfType = dilg.getAbs6();
						timeoffWeek[iweek][6][tfType]++;
					}
					if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
						tfType = dilg.getAbs7();
						timeoffWeek[iweek][7][tfType]++;
					}
					if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
						tfType = dilg.getAbs8();
						timeoffWeek[iweek][8][tfType]++;
					}
					if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
						tfType = dilg.getAbs9();
						timeoffWeek[iweek][9][tfType]++;
					}
					if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
						tfType = dilg.getAbs10();
						timeoffWeek[iweek][10][tfType]++;
					}
					if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
						tfType = dilg.getAbs11();
						timeoffWeek[iweek][11][tfType]++;
					}
					if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
						tfType = dilg.getAbs12();
						timeoffWeek[iweek][12][tfType]++;
					}
					if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
						tfType = dilg.getAbs13();
						timeoffWeek[iweek][13][tfType]++;
					}
					if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
						tfType = dilg.getAbs14();
						timeoffWeek[iweek][14][tfType]++;
					}
					if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
						tfType = dilg.getAbs15();
						timeoffWeek[iweek][15][tfType]++;
					}
				}
				
				//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
				//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
				//不同部制有不同之操行加減分標準
				List subjDilg = this.findPeriodDilgByStudentNo(studentNo, dateBegin, dateEnd, "subject");
				
				for(Iterator sDilgIter = subjDilg.iterator(); sDilgIter.hasNext();) {
					Map sdMap = new HashMap();
					sdMap = (Map)sDilgIter.next();
					int tfLimit = Integer.parseInt(sdMap.get("tfLimit").toString());
					int timeoff = Integer.parseInt(sdMap.get("timeOff").toString());
					String departClass = sdMap.get("departClass").toString();
					
					hql = "Select dc From DtimeClass dc Where dtimeOid=" + sdMap.get("dtimeOid");
					List<DtimeClass> dcList = (List<DtimeClass>)dao.submitQuery(hql);
					
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = dclass.getWeek();
						for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
							if(timeoff < tfLimit) {
								depClass[iweek][m] = sdMap.get("departClass").toString();
							}
							else {
								for(int n =0; n < tfTypeList.size(); n++){
									timeoffWeek[iweek][m][n] = 0;
								}
							}
						}
					}
				}
				
				String departClass = student.getDepartClass();
				String depart = this.chkStudentDepart(studentNo);
				short[] dilgSubtotal = new short[tflen]; 
				for(int z = 0; z < tflen; z++){
					dilgSubtotal[z] = 0;
				}

				//統計該學生曠缺種類及其次數
				for(int i = 1; i < 8; i++) {
					for(int j = 0; j <16; j++){
						for(int k = 1; k<tflen; k++ ){
							dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
						}
					}
				}
				
				/*
				debugs = "calConductScoreOfDilgDesd->dilg:" + studentNo + ": ";
				for(int x=1; x<8; x++){
					debugs = debugs + ", kind" + x + "=" + dilgSubtotal[x];
				}
				log.debug(debugs);
				*/
				
				//全勤加3分,有曠缺按部制規定扣分
				if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
						&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
						&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
					addelScore[0] = addelScore[0] + 3d;
				}else{
					if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
							addelScore[0] = addelScore[0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
							addelScore[0] = addelScore[0] + dilgSubtotal[2] * 0.5;
							addelScore[0] = addelScore[0] + dilgSubtotal[3] * 0.1;
							addelScore[0] = addelScore[0] + dilgSubtotal[4] * 0.2;
							addelScore[0] = addelScore[0] + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
							//addelScore[0] = addelScore[0] + dilgSubtotal[6] * 0.0;
							//addelScore[0] = addelScore[0] + dilgSubtotal[7] * 0.0;
							addelScore[0] = addelScore[0] * -1d;
					} else if(depart.equals("3")) {
							addelScore[0] = addelScore[0] + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
							addelScore[0] = addelScore[0] + dilgSubtotal[2] * 1.0;
							if(dilgSubtotal[3] > 26)
								addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[3]-26) * 0.1;
							if(dilgSubtotal[4] > 13)
								addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[4]-13) * 0.1;
							if(dilgSubtotal[5] > 4)
								addelScore[0] = addelScore[0] + 1 + (dilgSubtotal[5]-4) * 0.1;
							addelScore[0] = addelScore[0] * -1d;
					}
				}
								
			}
			if(Mode.equals("2")||Mode.equals("3")){
				//*** 2.process Desd
				List<Desd> desdList = this.findDesdByStudentNo(studentNo);
				List<Subs> subsList = this.findSubs("");
				
				Subs subs;
				int no;
				for(Iterator<Subs> subsIter = subsList.iterator(); subsIter.hasNext();){
					subs = subsIter.next();
					no = Integer.parseInt(subs.getNo()) - 1;
					addsub[no][0] = subs.getFirst();
					addsub[no][1] = subs.getSecond();
					addsub[no][2] = subs.getThird();
					addsub[no][3] = subs.getFourth();
					addsub[no][4] = subs.getFifth();
					addsub[no][5] = subs.getSixth();
				}
				
				Desd desd;
				String kind;
				short[] bonusPenalty = new short[7];
				for(int i=0; i<7; i++) bonusPenalty[i]=0;
				int cnt= 0;
				
				for(Iterator<Desd> desdIter = desdList.iterator(); desdIter.hasNext();){
					desd = desdIter.next();
					kind = desd.getKind1();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt1());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt1());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt1());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt1());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt1());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt1());
							}
						}
					}
					kind = desd.getKind2();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt2());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt2());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt2());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt2());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt2());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt2());
							}
						}
					}
				}
				for(int i = 1; i < 7; i++){
					if(bonusPenalty[i] > 0){
						//debugs = debugs + "[" + i + "]:[" + bonusPenalty[i] + "]";
						cnt =0;
						for(int k = 0; k < bonusPenalty[i]; k++) {
							if(cnt > 5) cnt = 5;
							addelScore[1] = addelScore[1] + addsub[i-1][cnt];
							cnt++;
						}
					}
				}
			}
			
		}
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore[0] > 0d)
			addelScore[0] = Math.round(addelScore[0] * 100d) / 100d;
		else if(addelScore[0] < 0d)
			addelScore[0] = Math.round(addelScore[0] * 100d * -1d) / 100d * -1d;
		if(addelScore[1] > 0d)
			addelScore[1] = Math.round(addelScore[1] * 100d) / 100d;
		else if(addelScore[1] < 0d)
			addelScore[1] = Math.round(addelScore[1] * 100d * -1d) / 100d * -1d;
		
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		//release resources
		timeoffWeek = null;
		depClass = null;
		addsub = null;
		
		return addelScore;
	}
	
	
	public double calDilgScore(List<Dilg> dilgList, String mode) {
		if(dilgList.isEmpty()){
			return 3d;
		}
		double addelScore = 0d;
		//System.out.println("Leo9999");
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		String studentNo = dilgList.get(0).getStudentNo();
		int tflen = tfTypeList.size();
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tflen; k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
		String hql ="";

		//*** 1.process Dilg
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;

		//將該學生曠缺資料填入timeoffWeek陣列
		for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
			dilg =dilgIter.next();
			iweek = Math.round(dilg.getWeekDay());
			//iweek = this.WhatIsTheWeek(dilg.getDdate());
			if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
				tfType = dilg.getAbs0();
				timeoffWeek[iweek][0][tfType]++;
			}
			if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
				tfType = dilg.getAbs1();
				timeoffWeek[iweek][1][tfType]++;
			}
			if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
				tfType = dilg.getAbs2();
				timeoffWeek[iweek][2][tfType]++;
			}
			if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
				tfType = dilg.getAbs3();
				timeoffWeek[iweek][3][tfType]++;
			}
			if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
				tfType = dilg.getAbs4();
				timeoffWeek[iweek][4][tfType]++;
			}
			if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
				tfType = dilg.getAbs5();
				timeoffWeek[iweek][5][tfType]++;
			}
			if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
				tfType = dilg.getAbs6();
				timeoffWeek[iweek][6][tfType]++;
			}
			if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
				tfType = dilg.getAbs7();
				timeoffWeek[iweek][7][tfType]++;
			}
			if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
				tfType = dilg.getAbs8();
				timeoffWeek[iweek][8][tfType]++;
			}
			if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
				tfType = dilg.getAbs9();
				timeoffWeek[iweek][9][tfType]++;
			}
			if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
				tfType = dilg.getAbs10();
				timeoffWeek[iweek][10][tfType]++;
			}
			if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
				tfType = dilg.getAbs11();
				timeoffWeek[iweek][11][tfType]++;
			}
			if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
				tfType = dilg.getAbs12();
				timeoffWeek[iweek][12][tfType]++;
			}
			if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
				tfType = dilg.getAbs13();
				timeoffWeek[iweek][13][tfType]++;
			}
			if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
				tfType = dilg.getAbs14();
				timeoffWeek[iweek][14][tfType]++;
			}
			if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
				tfType = dilg.getAbs15();
				timeoffWeek[iweek][15][tfType]++;
			}
		}

		if(mode.equals("0")){
			//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
			//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
			//不同部制有不同之操行加減分標準
			//List subjDilg = this.findPeriodDilgByStudentNo(studentNo, dateBegin, dateEnd, "subject");
			List subjDilg = new ArrayList();

			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);

			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid=0;
			int tfsum=0;
			int tfLimit=0, elearnDilg = 0, tfwarn=0;
			int begin = 0, end = 0, status = 0;
			iweek = 0;

			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();

				if(seld.getElearnDilg() != null){
					elearnDilg = seld.getElearnDilg();
				}else{
					elearnDilg = 0;
				}
				tfsum = elearnDilg;

				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);

				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}

				hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
				List<DtimeClass> dcList = dao.submitQuery(hql);
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext(); ) {
					dilg = dilgIter.next();
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = WhatIsTheWeek(dilg.getDdate());

						begin = Integer.parseInt(dclass.getBegin().trim());
						end = Integer.parseInt(dclass.getEnd().trim());
						//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
						if(iweek == dclass.getWeek()) {
							for(int j=begin; j <= end; j++){
								status = 0;
								switch (j){
								case 0:
									if(dilg.getAbs0() != null)
										status = dilg.getAbs0();
									break;
								case 1:
									if(dilg.getAbs1() != null)
										status = dilg.getAbs1();
									break;
								case 2:
									if(dilg.getAbs2() != null)
										status = dilg.getAbs2();
									break;
								case 3:
									if(dilg.getAbs3() != null)
										status = dilg.getAbs3();
									break;
								case 4:
									if(dilg.getAbs4() != null)
										status = dilg.getAbs4();
									break;
								case 5:
									if(dilg.getAbs5() != null)
										status = dilg.getAbs5();
									break;
								case 6:
									if(dilg.getAbs6() != null)
										status = dilg.getAbs6();
									break;
								case 7:
									if(dilg.getAbs7() != null)
										status = dilg.getAbs7();
									break;
								case 8:
									if(dilg.getAbs8() != null)
										status = dilg.getAbs8();
									break;
								case 9:
									if(dilg.getAbs9() != null)
										status = dilg.getAbs9();
									break;
								case 10:
									if(dilg.getAbs10() != null)
										status = dilg.getAbs10();
									break;
								case 11:
									if(dilg.getAbs11() != null)
										status = dilg.getAbs11();
									break;
								case 12:
									if(dilg.getAbs12() != null)
										status = dilg.getAbs12();
									break;
								case 13:
									if(dilg.getAbs13() != null)
										status = dilg.getAbs13();
									break;
								case 14:
									if(dilg.getAbs14() != null)
										status = dilg.getAbs14();
									break;
								case 15:
									if(dilg.getAbs15() != null)
										status = dilg.getAbs15();
									break;

								}
								if(!(status == 0 || status == 5 || status == 6)) {
									tfsum++;
								}

							}
						}
					}	//End for DtimeClass
				}	//End for Dilg

				if(tfsum>=tfLimit){							
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = dclass.getWeek();
						for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
							if(tfsum < tfLimit) {
								depClass[iweek][m] = dtime.getDepartClass();
							}
							else {
								for(int n =0; n < tflen; n++){
									timeoffWeek[iweek][m][n] = 0;
								}
							}
						}
					}

				}
			}	//End for seld

		}	//End if

		String depart = this.chkStudentDepart(studentNo);
		short[] dilgSubtotal = new short[tflen]; 
		for(int z = 0; z < tflen; z++){
			dilgSubtotal[z] = 0;
		}

		//統計該學生曠缺種類及其次數
		for(int i = 1; i < 8; i++) {
			for(int j = 0; j <16; j++){
				for(int k = 1; k<tflen; k++ ){
					dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
				}
			}
		}

		//全勤加3分,有曠缺按部制規定扣分
		if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
				&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
				&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
			addelScore = addelScore + 3d;
		}else{
			if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
				addelScore = addelScore + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
				addelScore = addelScore + dilgSubtotal[2] * 0.5;
				addelScore = addelScore + dilgSubtotal[3] * 0.1;
				addelScore = addelScore + dilgSubtotal[4] * 0.2;
				addelScore = addelScore + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
				//addelScore = addelScore + dilgSubtotal[6] * 0.0;
				//addelScore = addelScore + dilgSubtotal[7] * 0.0;
				addelScore = addelScore * -1d;
			} else if(depart.equals("3")) {
				addelScore = addelScore + dilgSubtotal[1] * 0.05;
				addelScore = addelScore + dilgSubtotal[2] * 1.0;
				if(dilgSubtotal[3] > 26)
					addelScore = addelScore + 1 + (dilgSubtotal[3]-26) * 0.1;
				if(dilgSubtotal[4] > 13)
					addelScore = addelScore + 1 + (dilgSubtotal[4]-13) * 0.1;
				if(dilgSubtotal[5] > 4)
					addelScore = addelScore + 1 + (dilgSubtotal[5]-4) * 0.1;
				addelScore = addelScore * -1d;
			}
		}
											
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore > 0d)
			addelScore = Math.round(addelScore * 100d) / 100d;
		else if(addelScore < 0d)
			addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
		
		//System.out.println(addelScore);
		timeoffWeek = null;
		depClass = null;
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		return addelScore;
	}
	
	public double calDilgScoreNew(List<Dilg> dilgList, String mode) {
		if(dilgList.isEmpty()){
			return 3d;
		}
		double addelScore = 0d;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		String studentNo = dilgList.get(0).getStudentNo();
		int tflen = tfTypeList.size();
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tflen; k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
				String hql ="";
				
				//*** 1.process Dilg
				Dilg dilg;
				short tfType = 0;
				int iweek = 0;
				
				//將該學生曠缺資料填入timeoffWeek陣列
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
					dilg =dilgIter.next();
					iweek = Math.round(dilg.getWeekDay());
					//iweek = this.WhatIsTheWeek(dilg.getDdate());
					if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
						tfType = dilg.getAbs0();
						timeoffWeek[iweek][0][tfType]++;
					}
					if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
						tfType = dilg.getAbs1();
						timeoffWeek[iweek][1][tfType]++;
					}
					if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
						tfType = dilg.getAbs2();
						timeoffWeek[iweek][2][tfType]++;
					}
					if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
						tfType = dilg.getAbs3();
						timeoffWeek[iweek][3][tfType]++;
					}
					if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
						tfType = dilg.getAbs4();
						timeoffWeek[iweek][4][tfType]++;
					}
					if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
						tfType = dilg.getAbs5();
						timeoffWeek[iweek][5][tfType]++;
					}
					if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
						tfType = dilg.getAbs6();
						timeoffWeek[iweek][6][tfType]++;
					}
					if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
						tfType = dilg.getAbs7();
						timeoffWeek[iweek][7][tfType]++;
					}
					if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
						tfType = dilg.getAbs8();
						timeoffWeek[iweek][8][tfType]++;
					}
					if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
						tfType = dilg.getAbs9();
						timeoffWeek[iweek][9][tfType]++;
					}
					if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
						tfType = dilg.getAbs10();
						timeoffWeek[iweek][10][tfType]++;
					}
					if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
						tfType = dilg.getAbs11();
						timeoffWeek[iweek][11][tfType]++;
					}
					if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
						tfType = dilg.getAbs12();
						timeoffWeek[iweek][12][tfType]++;
					}
					if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
						tfType = dilg.getAbs13();
						timeoffWeek[iweek][13][tfType]++;
					}
					if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
						tfType = dilg.getAbs14();
						timeoffWeek[iweek][14][tfType]++;
					}
					if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
						tfType = dilg.getAbs15();
						timeoffWeek[iweek][15][tfType]++;
					}
				}
				
				if(mode.equals("0")){
				//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
				//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
				//不同部制有不同之操行加減分標準
					List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
					
					Seld seld;
					List<Dtime> dtimeList;
					Dtime dtime;
					int dtOid=0;
					int tfsum=0;
					int tfLimit=0;
					int begin = 0, end = 0, status = 0;
					iweek = 0;
					
					for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
						seld = seldIter.next();
						dtOid = seld.getDtimeOid();
						
						dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
						dtime = dtimeList.get(0);
						
						tfLimit = 0;

						if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
							if(dtime.getThour() * 14 % 3 > 0){
								tfLimit = dtime.getThour() * 14 / 3 + 1;
							}else{
								tfLimit = dtime.getThour() * 14 / 3;
							}
						}
						else {
							tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
						}

						hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
						List<DtimeClass> dcList = dao.submitQuery(hql);
						tfsum = 0;
						for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
							DtimeClass dclass = dcIter.next();
									
							begin = Integer.parseInt(dclass.getBegin().trim());
							end = Integer.parseInt(dclass.getEnd().trim());
							//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
							iweek = dclass.getWeek();
							for(int j=begin; j <= end; j++){
								tfsum = tfsum + timeoffWeek[iweek][j][2] + timeoffWeek[iweek][j][3] +
								timeoffWeek[iweek][j][4] + timeoffWeek[iweek][j][7] +
								timeoffWeek[iweek][j][8] + timeoffWeek[iweek][j][9];
							}
								
						}
						
						if(tfsum>=tfLimit){							
							for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
								DtimeClass dclass = dcIter.next();
								iweek = dclass.getWeek();
								for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
									if(tfsum < tfLimit) {
										depClass[iweek][m] = dtime.getDepartClass();
									}
									else {
										for(int n =0; n < tflen; n++){
											timeoffWeek[iweek][m][n] = 0;
										}
									}
								}
							}
							
						}
					}
					
				}
				
				String depart = this.chkStudentDepart(studentNo);
				short[] dilgSubtotal = new short[tflen]; 
				for(int z = 0; z < tflen; z++){
					dilgSubtotal[z] = 0;
				}
				
				//統計該學生曠缺種類及其次數
				for(int i = 1; i < 8; i++) {
					for(int j = 0; j <16; j++){
						for(int k = 1; k<tflen; k++ ){
							dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
						}
					}
				}
								
				//全勤加3分,有曠缺按部制規定扣分
				if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
						&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
						&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
					addelScore = addelScore + 3d;
				}else{
					if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
							addelScore = addelScore + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
							addelScore = addelScore + dilgSubtotal[2] * 0.5;
							addelScore = addelScore + dilgSubtotal[3] * 0.1;
							addelScore = addelScore + dilgSubtotal[4] * 0.2;
							addelScore = addelScore + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
							//addelScore = addelScore + dilgSubtotal[6] * 0.0;
							//addelScore = addelScore + dilgSubtotal[7] * 0.0;
							addelScore = addelScore * -1d;
					} else if(depart.equals("3")) {
							addelScore = addelScore + dilgSubtotal[1] * 0.05;
							addelScore = addelScore + dilgSubtotal[2] * 1.0;
							if(dilgSubtotal[3] > 26)
								addelScore = addelScore + 1 + (dilgSubtotal[3]-26) * 0.1;
							if(dilgSubtotal[4] > 13)
								addelScore = addelScore + 1 + (dilgSubtotal[4]-13) * 0.1;
							if(dilgSubtotal[5] > 4)
								addelScore = addelScore + 1 + (dilgSubtotal[5]-4) * 0.1;
							addelScore = addelScore * -1d;
					}
				}
											
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore > 0d)
			addelScore = Math.round(addelScore * 100d) / 100d;
		else if(addelScore < 0d)
			addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
		timeoffWeek = null;
		depClass = null;
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		return addelScore;
	}
	
	public double[] calDilgScoreOfStuds(List studs, String mode) {
		double[] score = new double[studs.size()];
		double addelScore = 0d;
		List<Dilg> dilgList = new ArrayList();
		List<Seld> seldList = new ArrayList();
		String hql ="";
		String studentNo = "";
		int cnt = 0;
		
		Seld seld;
		List<Dtime> dtimeList = new ArrayList();;
		Dtime dtime;
		int dtOid=0;
		int tfsum=0;
		int elearnDilg = 0;
		int tfLimit=0;
		int begin = 0, end = 0, status = 0;

		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		int tflen = tfTypeList.size();
		String depart = "";
		
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		String[][] depClass = new String[8][16];
		short[] dilgSubtotal = new short[tflen]; 
		
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;
		
		for(Iterator stuIter=studs.iterator(); stuIter.hasNext();){
			studentNo=((Map)stuIter.next()).get("studentNo").toString();
			hql = "Select d From Dilg d";
			hql = hql + " Where studentNo='" + studentNo + "'";
			hql = hql + " Order by ddate";
			dilgList = dao.submitQuery(hql);
			
			if(dilgList.isEmpty()){
				score[cnt] = 3d;
			}else{
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j <16; j++){
						depClass[i][j] = "";

						for(int k = 0; k < tflen; k++){
							timeoffWeek[i][j][k] = 0;
						}
					}
				}
				addelScore = 0d;
				
				//將該學生曠缺資料填入timeoffWeek陣列
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
					dilg =dilgIter.next();
					iweek = Math.round(dilg.getWeekDay());
					//iweek = this.WhatIsTheWeek(dilg.getDdate());
					if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
						tfType = dilg.getAbs0();
						timeoffWeek[iweek][0][tfType]++;
					}
					if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
						tfType = dilg.getAbs1();
						timeoffWeek[iweek][1][tfType]++;
					}
					if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
						tfType = dilg.getAbs2();
						timeoffWeek[iweek][2][tfType]++;
					}
					if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
						tfType = dilg.getAbs3();
						timeoffWeek[iweek][3][tfType]++;
					}
					if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
						tfType = dilg.getAbs4();
						timeoffWeek[iweek][4][tfType]++;
					}
					if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
						tfType = dilg.getAbs5();
						timeoffWeek[iweek][5][tfType]++;
					}
					if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
						tfType = dilg.getAbs6();
						timeoffWeek[iweek][6][tfType]++;
					}
					if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
						tfType = dilg.getAbs7();
						timeoffWeek[iweek][7][tfType]++;
					}
					if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
						tfType = dilg.getAbs8();
						timeoffWeek[iweek][8][tfType]++;
					}
					if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
						tfType = dilg.getAbs9();
						timeoffWeek[iweek][9][tfType]++;
					}
					if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
						tfType = dilg.getAbs10();
						timeoffWeek[iweek][10][tfType]++;
					}
					if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
						tfType = dilg.getAbs11();
						timeoffWeek[iweek][11][tfType]++;
					}
					if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
						tfType = dilg.getAbs12();
						timeoffWeek[iweek][12][tfType]++;
					}
					if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
						tfType = dilg.getAbs13();
						timeoffWeek[iweek][13][tfType]++;
					}
					if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
						tfType = dilg.getAbs14();
						timeoffWeek[iweek][14][tfType]++;
					}
					if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
						tfType = dilg.getAbs15();
						timeoffWeek[iweek][15][tfType]++;
					}
				}
				
				if(mode.equals("0")){
				//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
				//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
				//不同部制有不同之操行加減分標準
					seldList = dao.findSeldByStudentNo(studentNo, sterm);
					
					for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
						seld = seldIter.next();
						dtOid = seld.getDtimeOid();
						
						if(seld.getElearnDilg() != null){
							elearnDilg = seld.getElearnDilg();
						}else{
							elearnDilg = 0;
						}
						tfsum = elearnDilg;

						dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
						dtime = dtimeList.get(0);
						
						tfLimit = 0;

						if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
							if(dtime.getThour() * 14 % 3 > 0){
								tfLimit = dtime.getThour() * 14 / 3 + 1;
							}else{
								tfLimit = dtime.getThour() * 14 / 3;
							}
						}
						else {
							tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
						}

						hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
						List<DtimeClass> dcList = dao.submitQuery(hql);
						for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
							DtimeClass dclass = dcIter.next();
									
							begin = Integer.parseInt(dclass.getBegin().trim());
							end = Integer.parseInt(dclass.getEnd().trim());
							//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
							iweek = dclass.getWeek();
							for(int j=begin; j <= end; j++){
								tfsum = tfsum + timeoffWeek[iweek][j][2] + timeoffWeek[iweek][j][3] +
								timeoffWeek[iweek][j][4] + timeoffWeek[iweek][j][7] +
								timeoffWeek[iweek][j][8] + timeoffWeek[iweek][j][9];
							}
								
						}
						
						if(tfsum>=tfLimit){							
							for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
								DtimeClass dclass = dcIter.next();
								iweek = dclass.getWeek();
								for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
									if(tfsum < tfLimit) {
										depClass[iweek][m] = dtime.getDepartClass();
									}
									else {
										for(int n =0; n < tflen; n++){
											timeoffWeek[iweek][m][n] = 0;
										}
									}
								}
							}
							
						}
					}
					
				}
				
				depart = this.chkStudentDepart(studentNo);
				for(int z = 0; z < tflen; z++){
					dilgSubtotal[z] = 0;
				}
				
				//統計該學生曠缺種類及其次數
				for(int i = 1; i < 8; i++) {
					for(int j = 0; j <16; j++){
						for(int k = 1; k<tflen; k++ ){
							dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
						}
					}
				}
								
				//全勤加3分,有曠缺按部制規定扣分
				if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
						&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
						&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
					addelScore = addelScore + 3d;
				}else{
					if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
							addelScore = addelScore + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
							addelScore = addelScore + dilgSubtotal[2] * 0.5;
							addelScore = addelScore + dilgSubtotal[3] * 0.1;
							addelScore = addelScore + dilgSubtotal[4] * 0.2;
							addelScore = addelScore + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
							//addelScore = addelScore + dilgSubtotal[6] * 0.0;
							//addelScore = addelScore + dilgSubtotal[7] * 0.0;
							addelScore = addelScore * -1d;
					} else if(depart.equals("3")) {
							addelScore = addelScore + dilgSubtotal[1] * 0.05;
							addelScore = addelScore + dilgSubtotal[2] * 1.0;
							if(dilgSubtotal[3] > 26)
								addelScore = addelScore + 1 + (dilgSubtotal[3]-26) * 0.1;
							if(dilgSubtotal[4] > 13)
								addelScore = addelScore + 1 + (dilgSubtotal[4]-13) * 0.1;
							if(dilgSubtotal[5] > 4)
								addelScore = addelScore + 1 + (dilgSubtotal[5]-4) * 0.1;
							addelScore = addelScore * -1d;
					}
				}
											
				//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
				//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
				
				if(addelScore > 0d)
					addelScore = Math.round(addelScore * 100d) / 100d;
				else if(addelScore < 0d)
					addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
				//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
				score[cnt] = addelScore;
			}
			
			cnt++;
		}
		return score;
	}
	
	public double[] calDesdScoreOfStuds(List studs){
		double[] score = new double[studs.size()];
		List<Desd> desdList = new ArrayList();
		int cnt = 0;
		String studentNo = "";
		String hql = "";
		double addelScore = 0d;
		List<Subs> subsList = this.findSubs("");
		double[][] addsub = new double[6][6];

		Subs subs;
		int no;
		for(Iterator<Subs> subsIter = subsList.iterator(); subsIter.hasNext();){
			subs = subsIter.next();
			no = Integer.parseInt(subs.getNo()) - 1;
			addsub[no][0] = subs.getFirst();
			addsub[no][1] = subs.getSecond();
			addsub[no][2] = subs.getThird();
			addsub[no][3] = subs.getFourth();
			addsub[no][4] = subs.getFifth();
			addsub[no][5] = subs.getSixth();
		}

		Desd desd;
		String kind;
		short[] bonusPenalty = new short[7];

		for(Iterator stIter=studs.iterator(); stIter.hasNext();){
			studentNo=((Map)stIter.next()).get("studentNo").toString();
			hql = "Select d from Desd d Where studentNo='" + studentNo + "'";
			desdList = dao.submitQuery(hql);
			
			if(desdList.isEmpty()){
				score[cnt] = 0d;
			}else{
				addelScore = 0d;
				
				for(int i=0; i<7; i++) bonusPenalty[i]=0;
				int knt= 0;
				
				for(Iterator<Desd> desdIter = desdList.iterator(); desdIter.hasNext();){
					desd = desdIter.next();
					kind = desd.getKind1();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt1());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt1());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt1());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt1());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt1());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt1());
							}
						}
					}
					kind = desd.getKind2();
					if(kind != null) {
						if(!kind.equals("")) {
							if(kind.equals("1")){
								bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt2());
							}else if(kind.equals("2")){
								bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt2());
							}else if(kind.equals("3")){
								bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt2());
							}else if(kind.equals("4")){
								bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt2());
							}else if(kind.equals("5")){
								bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt2());
							}else if(kind.equals("6")){
								bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt2());
							}
						}
					}
				}
				for(int i = 1; i < 7; i++){
					if(bonusPenalty[i] > 0){
						//debugs = debugs + "[" + i + "]:[" + bonusPenalty[i] + "]";
						knt =0;
						for(int k = 0; k < bonusPenalty[i]; k++) {
							if(knt > 5) knt = 5;
							addelScore = addelScore + addsub[i-1][knt];
							knt++;
						}
					}
				}
				
				//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
				//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
			
				if(addelScore > 0d)
					addelScore = Math.round(addelScore * 100d) / 100d;
				else if(addelScore < 0d)
					addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;

				score[cnt] = addelScore;
			}
			cnt++;
		}
		return score;
	}
	
	public double calDilgScoreByStudent(String studentNo, String mode){
		String hql = "Select d From Dilg d";
		hql = hql + " Where studentNo='" + studentNo + "'";
		hql = hql + " Order by ddate";
		List<Dilg> dilgList = dao.submitQuery(hql);

		return this.calDilgScore(dilgList, mode);
	}

	
	public double calDesdScore(List<Desd> desdList){
		if(desdList.isEmpty()){
			return 0d;
		}
		//System.out.println("Leo0315_01");
		double addelScore = 0d;
		List<Subs> subsList = this.findSubs("");
		double[][] addsub = new double[6][6];

		Subs subs;
		int no;
		for(Iterator<Subs> subsIter = subsList.iterator(); subsIter.hasNext();){
			subs = subsIter.next();
			no = Integer.parseInt(subs.getNo()) - 1;
			addsub[no][0] = subs.getFirst();
			addsub[no][1] = subs.getSecond();
			addsub[no][2] = subs.getThird();
			addsub[no][3] = subs.getFourth();
			addsub[no][4] = subs.getFifth();
			addsub[no][5] = subs.getSixth();
		}
		//System.out.println("Leo0315_02");
		Desd desd;
		String kind;
		short[] bonusPenalty = new short[7];
		for(int i=0; i<7; i++) bonusPenalty[i]=0;
		int cnt= 0;
		
		for(Iterator<Desd> desdIter = desdList.iterator(); desdIter.hasNext();){
			desd = desdIter.next();
			kind = desd.getKind1();
			if(kind != null) {
				if(!kind.equals("")) {
					if(kind.equals("1")){
						bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt1());
					}else if(kind.equals("2")){
						bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt1());
					}else if(kind.equals("3")){
						bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt1());
					}else if(kind.equals("4")){
						bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt1());
					}else if(kind.equals("5")){
						bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt1());
					}else if(kind.equals("6")){
						bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt1());
					}
				}
			}
			kind = desd.getKind2();
			//System.out.println("Leo0315_03="+kind);
			if(kind != null) {
				if(!kind.equals("")) {
					if(kind.equals("1")){
						bonusPenalty[1] = (short)(bonusPenalty[1] + desd.getCnt2());
					}else if(kind.equals("2")){
						bonusPenalty[2] = (short)(bonusPenalty[2] + desd.getCnt2());
					}else if(kind.equals("3")){
						bonusPenalty[3] = (short)(bonusPenalty[3] + desd.getCnt2());
					}else if(kind.equals("4")){
						bonusPenalty[4] = (short)(bonusPenalty[4] + desd.getCnt2());
					}else if(kind.equals("5")){
						bonusPenalty[5] = (short)(bonusPenalty[5] + desd.getCnt2());
					}else if(kind.equals("6")){
						bonusPenalty[6] = (short)(bonusPenalty[6] + desd.getCnt2());
					}
				}
			}
		}
		for(int i = 1; i < 7; i++){
			if(bonusPenalty[i] > 0){
				//debugs = debugs + "[" + i + "]:[" + bonusPenalty[i] + "]";
				cnt =0;
				for(int k = 0; k < bonusPenalty[i]; k++) {
					if(cnt > 5) cnt = 5;
					addelScore = addelScore + addsub[i-1][cnt];
					cnt++;
				}
			}
		}
		//System.out.println("Leo0315_04");
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
	
		if(addelScore > 0d)
			addelScore = Math.round(addelScore * 100d) / 100d;
		else if(addelScore < 0d)
			addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
	
		//System.out.println("Leo0315_05="+addelScore);
		return addelScore;
	}

	public double calDesdScoreByStudent(String studentNo){
		List<Desd> desdList = new ArrayList();
		String hql = "Select d from Desd d Where studentNo='" + studentNo + "'";
		desdList = dao.submitQuery(hql);
		return this.calDesdScore(desdList);
	}
	
	public List<Desd> findDesdByStudentNo(String studentNo) {
		List<Desd> desdList = new ArrayList();
		if(!studentNo.equals("")){
			String hql = "Select d From Desd d Where studentNo='" + studentNo + "' Order By no,ddate";
			desdList = dao.submitQuery(hql);
			
		}
		return desdList;
	}
	
	public List<Code1> findConductMark(String no) {
		String hql = "";
		if(no.equals("")) {
			hql = "Select c From Code1 c Order By no";
		}else{
			hql = "Select c From Code1 c Where no='" + no + "' Order By no";
		}
		return (List<Code1>)dao.submitQuery(hql);
	}
	
	public List<Subs> findSubs(String no) {
		String hql = "Select s From Subs s";
		if(!no.equals("")) {
			hql = hql + " Where no='" + no + "'";
		}
		hql = hql + " Order by no";
		return dao.submitQuery(hql);
	}
	
	public List<Just> findJustByStudentNo(String studentNo, String classInCharge){
		String hql = "";
		List<Just> justList = new ArrayList();
		Just just = new Just();
		
		if(studentNo.trim().equals("")) {
			return justList;
		} else {
			hql = "Select s From Student s Where studentNo='" + studentNo + "' And departClass in " + classInCharge;
			List<Student> studs = dao.submitQuery(hql);
			if(studs.isEmpty()) {
				//查無此學生
				return justList;
			}else{
				Student student = studs.get(0);
				hql = "Select j From Just j Where studentNo='" + studentNo + "'";
				justList = dao.submitQuery(hql);
				if(!justList.isEmpty()) {
					just = justList.get(0);
					
				
					just.setStudentName(student.getStudentName());
					just.setDeptClassName(Toolket.getClassFullName(student.getDepartClass()));
					
					//TODO : Process 曠缺及獎懲加減分及評語
					double[] ddScore = this.calConductScoreOfDilgDesd(studentNo);
					just.setDilgScore(ddScore[0]);
					just.setDesdScore(ddScore[1]);
					double totalScore = 82d + just.getTeacherScore() + just.getDeptheaderScore() + just.getMilitaryScore()
					+ just.getMeetingScore() + ddScore[0] + ddScore[1];
					
					if(totalScore > 0d)
						totalScore = Math.round(totalScore * 100d) / 100d;
					else if(totalScore < 0d)
						totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

					if(totalScore > 95.0d) {
						totalScore = 95d;
					}
					just.setTotalScore(totalScore);
					String jcode = "", jname = "";
					List<Code1> code1List;

					jcode = just.getComCode1();
					if(jcode!=null) {
						if(!jcode.equals("")) {
							code1List = findConductMark(jcode);
							if(!code1List.isEmpty()) {
								just.setComName1(code1List.get(0).getName());
							}else{
								just.setComName1("");
							}
						} else {
							just.setComName1("");
						}
					}else {
						just.setComName1("");
					}

					
					jcode = just.getComCode2();
					if(jcode!=null) {
						if(!jcode.equals("")) {
							code1List = findConductMark(jcode);
							if(!code1List.isEmpty()) {
								just.setComName2(code1List.get(0).getName());
							}else{
								just.setComName2("");
							}
						} else {
							just.setComName2("");
						}
					}else {
						just.setComName2("");
					}
					
					jcode = just.getComCode3();
					if(jcode!=null) {
						if(!jcode.equals("")) {
							code1List = findConductMark(jcode);
							if(!code1List.isEmpty()) {
								just.setComName3(code1List.get(0).getName());
							}else{
								just.setComName3("");
							}
						} else {
							just.setComName3("");
						}
					}else {
						just.setComName3("");
					}
					
				}else{
					return justList;
				}
				/*
				else {
					just.setStudentNo(studentNo);
					just.setDepartClass(studs.get(0).getDepartClass());
					just.setTeacherScore(0.0d);
					just.setDeptheaderScore(0.0d);
					just.setMilitaryScore(0.0d);
					just.setMeetingScore(0.0d);
				}
				*/
			}
		}
		List<Just> justs = new ArrayList();
		justs.add(just);
		return justs;
	}
	
	public ActionMessages delStudConduct(List<Just> selJusts, ResourceBundle Bundle) {
		ActionMessages errs = new ActionMessages();
		Just just = new Just();
		try{
			for(Iterator<Just> justIter = selJusts.iterator(); justIter.hasNext();) {
				just = justIter.next();
				log.debug("====>Remove Just:" + just.getOid());
				dao.removeJust(just);
			}
		} catch(Exception e) {
			e.printStackTrace();
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.StudConduct.deleteFail"));;
		}
		return errs;
		
	}
	
	public ActionMessages createJustByFormProp(DynaActionForm form, String classInCharge) {
		ActionMessages errs = new ActionMessages();
		
		String studentNo = form.getString("studentNo");
		String teacherScore = form.getString("teacherScore").trim();
		String deptheaderScore = form.getString("deptheaderScore").trim();
  		String militaryScore = form.get("militaryScore").toString().trim();
  		String meetingScore = form.get("meetingScore").toString().trim();
  		//String dilgScore = form.get("dilgScore").toString().trim();
  		//String desdScore = form.get("desdScore").toString().trim();
  		String comCode1 = form.get("comCode1").toString();
  		String comCode2 = form.get("comCode2").toString();
  		String comCode3 = form.get("comCode3").toString();
  		double totalScore = 82.0d;
  		double[] scores = this.calConductScoreOfDilgDesd(studentNo);
  		
		String hql = "Select s From Student s Where studentNo='" + studentNo + "' And departClass in " + classInCharge;
		List<Student> studs = dao.submitQuery(hql);
  		Student student;
  		
		if(studs.isEmpty()) {
			errs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.StudentNotFound",studentNo));
			return errs;
		} else {
			student = studs.get(0);
		}
		
 		Just just = new Just();
		
		hql = "Select j From Just j Where studentNo='" + studentNo + "'";
		List<Just> justs = dao.submitQuery(hql);
		if(!justs.isEmpty()){
			just = justs.get(0);
		}
 
  		just.setStudentNo(studentNo);
  		just.setDepartClass(student.getDepartClass());
  		if(!teacherScore.trim().equals("")){
  			just.setTeacherScore(Double.parseDouble(teacherScore));
  			totalScore = totalScore + Double.parseDouble(teacherScore);
  		}else{
  			just.setTeacherScore(0.0d);
  		}
  		
  		if(!deptheaderScore.trim().equals("")){
  			just.setDeptheaderScore(Double.parseDouble(deptheaderScore));
  			totalScore = totalScore + Double.parseDouble(deptheaderScore);
  		}else{
  			just.setDeptheaderScore(0.0d);
  		}
  			
  		if(!militaryScore.trim().equals("")){
  			just.setMilitaryScore(Double.parseDouble(militaryScore));
  			totalScore = totalScore + Double.parseDouble(militaryScore);
  		}else{
  			just.setMilitaryScore(0.0d);
  		}
  		
  		if(!meetingScore.trim().equals("")){
  			just.setMeetingScore(Double.parseDouble(meetingScore));
  			totalScore = totalScore + Double.parseDouble(meetingScore);
  		}else{
  			just.setMeetingScore(0.0d);
  		}
  		
  		//Add dilg score
  		totalScore = totalScore + scores[0];
  		//Add desd score
  		totalScore = totalScore + scores[1];

		if(totalScore > 0d)
			totalScore = Math.round(totalScore * 100d) / 100d;
		else if(totalScore < 0d)
			totalScore = 0d;
			//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

  		
  		if(totalScore > 95d) {
  			totalScore = 95.0d;
  		}
  		
  		just.setTotalScore(totalScore);
  		
  		List<Code1> code1List;
  		if(!comCode1.trim().equals("")){
  			just.setComCode1(comCode1);
  			code1List = this.findConductMark(comCode1);
  			if(!code1List.isEmpty())
  				just.setComName1(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}

  		if(!comCode2.trim().equals("")){
  			just.setComCode2(comCode2);
  			code1List = this.findConductMark(comCode2);
  			if(!code1List.isEmpty())
  				just.setComName2(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}

  		if(!comCode3.trim().equals("")){
  			just.setComCode3(comCode3);
  			code1List = this.findConductMark(comCode3);
  			if(!code1List.isEmpty())
  				just.setComName3(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}

  		try {
  			dao.saveObject(just);
  		}catch(Exception e){
  			e.printStackTrace();
  			errs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.generic", e.toString()));
  		}
  		
  		return errs;
	}
	
	public ActionMessages modifyJustByFormProp(DynaActionForm form, Just just, String classInCharge){
		ActionMessages errs = new ActionMessages();
		
		String studentNo = form.getString("studentNo");
		String teacherScore = form.getString("teacherScore").trim();
		String deptheaderScore = form.getString("deptheaderScore").trim();
  		String militaryScore = form.get("militaryScore").toString().trim();
  		String meetingScore = form.get("meetingScore").toString().trim();
  		String comCode1 = form.get("comCode1").toString();
  		String comCode2 = form.get("comCode2").toString();
  		String comCode3 = form.get("comCode3").toString();
  		double dilgScore = just.getDilgScore();
  		double desdScore = just.getDesdScore();
  		double totalScore = 82.0d;
  		
		String hql = "Select s From Student s Where studentNo='" + studentNo + "' And departClass in " + classInCharge;
		List<Student> studs = dao.submitQuery(hql);
  		Student student;
  		
		if(studs.isEmpty()) {
			errs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.StudentNotFound",studentNo));
			return errs;
		} else {
			student = studs.get(0);
		}

  		if(!teacherScore.trim().equals("")){
  			just.setTeacherScore(Double.parseDouble(teacherScore));
  			totalScore = totalScore + Double.parseDouble(teacherScore);
  		}else{
  			just.setTeacherScore(0.0d);
  		}
  		
  		if(!deptheaderScore.trim().equals("")){
  			just.setDeptheaderScore(Double.parseDouble(deptheaderScore));
  			totalScore = totalScore + Double.parseDouble(deptheaderScore);
  		}else{
  			just.setDeptheaderScore(0.0d);
  		}
  			
  		if(!militaryScore.trim().equals("")){
  			just.setMilitaryScore(Double.parseDouble(militaryScore));
  			totalScore = totalScore + Double.parseDouble(militaryScore);
  		}else{
  			just.setMilitaryScore(0.0d);
  		}
  		
  		if(!meetingScore.trim().equals("")){
  			just.setMeetingScore(Double.parseDouble(meetingScore));
  			totalScore = totalScore + Double.parseDouble(meetingScore);
  		}else{
  			just.setMeetingScore(0.0d);
  		}
  		
  		totalScore = totalScore + dilgScore;
   		
  		totalScore = totalScore + desdScore;

		if(totalScore > 0d)
			totalScore = Math.round(totalScore * 100d) / 100d;
		else if(totalScore < 0d)
			totalScore = 0d;
			//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

  		
  		if(totalScore > 95d) {
  			totalScore = 95.0d;
  		}
  		
  		just.setTotalScore(totalScore);
  		
  		List<Code1> code1List;
  		if(!comCode1.trim().equals("")){
  			just.setComCode1(comCode1);
  			code1List = this.findConductMark(comCode1);
  			if(!code1List.isEmpty())
  				just.setComName1(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}else{
  			just.setComCode1(null);
  			just.setComName1(null);
  		}

  		if(!comCode2.trim().equals("")){
  			just.setComCode2(comCode2);
  			code1List = this.findConductMark(comCode2);
  			if(!code1List.isEmpty())
  				just.setComName2(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}else{
  			just.setComCode2(null);
  			just.setComName2(null);
  		}

  		if(!comCode3.trim().equals("")){
  			just.setComCode3(comCode3);
  			code1List = this.findConductMark(comCode3);
  			if(!code1List.isEmpty())
  				just.setComName3(code1List.get(0).getName());
  			else {
  				errs.add(ActionMessages.GLOBAL_MESSAGE,
  						new ActionMessage("Message.ConductMarkErr"));
  				return errs;
  			}
  		}else{
  			just.setComCode3(null);
  			just.setComName3(null);
  		}

  		try {
  			dao.saveObject(just);
  		}catch(Exception e){
  			e.printStackTrace();
  			errs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.generic", e.toString()));
  		}
  		
  		return errs;
	}
	
	public List<Just> findJustByClass(String departClass) {
		List<Just> justList = new ArrayList();
		String hql = "", subhql = "";
		String studentNo = "";
		String jcode = "", jname = "";
		List<Code1> code1List;
		
				
		hql = "select j From Just j Where departClass='" + departClass + "'";
		justList = dao.submitQuery(hql);
		if(!justList.isEmpty()) {
			double[] scores = new double[2];
			double totalScore;
			int cnt = 0;
			String codeName = "";
			
			Student student = new Student();

			for(ListIterator<Just> justIter = justList.listIterator(); justIter.hasNext();){
				Just just = justIter.next();
				studentNo = just.getStudentNo();
				hql = "Select s From Student s Where studentNo='" + studentNo + "'";
				List<Student> studs = dao.submitQuery(hql);
				
				if(studs.isEmpty()){
					justIter.remove();
					continue;
				}else{
					student = studs.get(0);
				}
				
				scores = this.calConductScoreOfDilgDesd(studentNo);
				
				totalScore = 82d + just.getTeacherScore() + just.getDeptheaderScore() + just.getMilitaryScore()
				+ just.getMeetingScore() + scores[0] + scores[1];
				
				if(totalScore > 0d)
					totalScore = Math.round(totalScore * 100d) / 100d;
				else if(totalScore < 0d)
					totalScore = 0d;
					//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

				
				if(totalScore > 95.0d) {
					totalScore = 95d;
				}
								
				just.setTotalScore(totalScore);
				
				just.setDilgScore(scores[0]);
				just.setDesdScore(scores[1]);
				just.setStudentName(student.getStudentName());
				just.setDeptClassName(Toolket.getClassFullName(just.getDepartClass()));
				
				jcode = just.getComCode1();
				if(jcode!=null) {
					if(!jcode.equals("")) {
						code1List = findConductMark(jcode);
						if(!code1List.isEmpty()) {
							codeName = code1List.get(0).getName();
							just.setComName1(codeName);
							if(codeName.length() > 6)
								just.setComShortName1(codeName.substring(0, 6));
							else
								just.setComShortName1(codeName);
						}else{
							just.setComName1("");
						}
					} else {
						just.setComName1("");
					}
				}else {
					just.setComName1("");
				}
	
				jcode = just.getComCode2();
				if(jcode!=null) {
					if(!jcode.equals("")) {
						code1List = findConductMark(jcode);
						if(!code1List.isEmpty()) {
							codeName = code1List.get(0).getName();
							just.setComName2(codeName);
							if(codeName.length() > 6)
								just.setComShortName2(codeName.substring(0, 6));
							else
								just.setComShortName2(codeName);
						}else{
							just.setComName2("");
						}
					} else {
						just.setComName2("");
					}
				}else {
					just.setComName2("");
				}
	
				jcode = just.getComCode3();
				if(jcode!=null) {
					if(!jcode.equals("")) {
						code1List = findConductMark(jcode);
						if(!code1List.isEmpty()) {
							codeName = code1List.get(0).getName();
							just.setComName3(codeName);
							if(codeName.length() > 6)
								just.setComShortName3(codeName.substring(0, 6));
							else
								just.setComShortName3(codeName);
						}else{
							just.setComName3("");
						}
					} else {
						just.setComName3("");
					}
				}else {
					just.setComName3("");
				}
	
			}
		}
		return justList;
	}
	
	
	public ActionMessages studCreateOrUpdateJust(String campus, String mode){
		ActionMessages err = new ActionMessages();
		List<Just> justList = new ArrayList();
		List tmpList = new ArrayList();
		String hql = "";
		String studentNo = "";
		Student student;
		double[] scores = new double[2];
		double total = 0.0d;
		long runtime = 0;
		
		//計算處理資料筆數
		int pcnt = 0, percentUnit = 0;
		String sql;
		if(mode.equalsIgnoreCase("create")) {
			sql = "Select count(*) From stmd s Where depart_class like '" + campus + "%'";
			pcnt = pcnt + jdbcDao.getRecordsCount(sql);
		}else if(mode.equalsIgnoreCase("update")){
			sql = "Select count(*) From just j Where depart_class like '" + campus + "%'";
			pcnt = pcnt + jdbcDao.getRecordsCount(sql);
		}
		
		if(pcnt > 100) {
			percentUnit = (int)(Math.floor(pcnt / 100));
		}else{
			percentUnit = 1;
		}
		
		int inProcess = 0;
		double percentage = 0d;
		int recnt = 0;
		
		if(mode.equalsIgnoreCase("create")) {
			hql = "Select s From Student s Where departClass like '" + campus + "%' Order by studentNo";
			
			try{
				List<Student> studs = dao.submitQuery(hql);
				//log.debug("StudConductCreateAll->Students:" + campus + " :" + studs.size());
						
				if(studs.isEmpty()) {
					//查無此學生
					/*
					err.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.generic", "建立全校學生操行成績失敗(查無學生)!"));
					return err;
					*/
				}else{
							
					for(Iterator<Student> studIter = studs.iterator(); studIter.hasNext();) {
						student = studIter.next();
						sql = "delete from just Where student_no='" + student.getStudentNo() + "'";
						jdbcDao.executesql(sql);
					}
					
					List stList = new ArrayList();
					for(Student stud:studs){
						Map stMap = new HashMap();
						stMap.put("studentNo", stud.getStudentNo());
						stList.add(stMap);
					}
					
					/*
					setRunStatus("createJustAll", "depart:" + campus + ";計算曠缺筆數:" + stList.size() , inProcess, pcnt, percentage, "no");
					double[] dilgScore = this.calDilgScoreOfStuds(stList, "0");
					setRunStatus("createJustAll", "depart:" + campus + ";計算獎懲筆數:" + stList.size() , inProcess, pcnt, percentage, "no");
					double[] desdScore = this.calDesdScoreOfStuds(stList);
					*/
					recnt = 0;
					for(Iterator<Student> studIter = studs.iterator(); studIter.hasNext();) {
						//runtime = (new Date()).getTime(); 

						student = studIter.next();
						studentNo = student.getStudentNo();
						//scores[0] = this.calDilgScoreByStudent(studentNo, "0");
						//scores[1] = this.calDesdScoreByStudent(studentNo);
						Just just = new Just();
						just.setDepartClass(student.getDepartClass());
						just.setStudentNo(studentNo);
						just.setTeacherScore(0.0d);
						just.setDeptheaderScore(0.0d);
						just.setMilitaryScore(0.0d);
						just.setMeetingScore(0.0d);
						just.setDilgScore(3.0d);
						just.setDesdScore(0.0d);
						total = 85.0d ;
						/*
						just.setDilgScore(dilgScore[recnt]);
						just.setDesdScore(desdScore[recnt]);
						total = 82.0d + dilgScore[recnt] + desdScore[recnt];
						
						if(total > 0d)
							total = Math.round(total * 100d) / 100d;
						else if(total < 0d)
							total = 0d;							
	
						if(total > 95.0d) {
							total = 95d;
						}
						recnt++;
						*/
						
						just.setTotalScore(total);
						
						dao.saveObject(just);
						/*
						inProcess++;
						if((inProcess % 10) == 0){
							percentage = (double)(inProcess/percentUnit);
							setRunStatus("createJustAll", "depart:" + campus, inProcess, pcnt, percentage, "no");
						}
						*/
						//log.debug("createorUpdateJust time:" + studentNo + ":" + ((new Date()).getTime()-runtime));
						//log.debug("StudConductCreateAll->create:studentNo" + student.getStudentNo());
					}
				}
			}catch(Exception e) {
				err.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.generic", e.toString()));
				e.printStackTrace();
				return err;
			}
			
		}else if(mode.equalsIgnoreCase("update")){
			hql = "select j from Just j Where departClass like '" + campus + "%'";
			Just just = new Just();
			
			try{
				justList = dao.submitQuery(hql);
				/*
				List stuList = new ArrayList();
				double[][] stscores =new double[justList.size()][2];
				for(Iterator<Just> justIter = justList.iterator(); justIter.hasNext();){
					stuList.add(justIter.next().getStudentNo());
				}
				stscores = this.calConductScoreBatch(stuList);
				
				int knt = 0;
				*/
				
				List stList = new ArrayList();
				for(Just myjust:justList){
					Map stMap = new HashMap();
					stMap.put("studentNo", myjust.getStudentNo());
					stList.add(stMap);
				}
				
				//setRunStatus("updateJustAll", "depart:" + campus + ";計算曠缺筆數:" + stList.size() , inProcess, pcnt, percentage, "no");
				double[] dilgScore = this.calDilgScoreOfStuds(stList, "0");
				//setRunStatus("updateJustAll", "depart:" + campus + ";計算獎懲筆數:" + stList.size() , inProcess, pcnt, percentage, "no");
				double[] desdScore = this.calDesdScoreOfStuds(stList);
				
				recnt = 0;

				for(Iterator<Just> justIter = justList.iterator(); justIter.hasNext();){
					just = justIter.next();
					//scores = stscores[knt];
					studentNo = just.getStudentNo();
					//scores[0] = this.calDilgScoreByStudent(studentNo, "0");
					//scores[1] = this.calDesdScoreByStudent(studentNo);
					total = 82.0d + just.getTeacherScore() + just.getDeptheaderScore() + just.getMilitaryScore()
							+ just.getMeetingScore() + dilgScore[recnt] + desdScore[recnt];
	
					if(total > 0d)
						total = Math.round(total * 100d) / 100d;
					else if(total < 0d)
						total = 0d;
						//total = Math.round(total * 100d * -1d) / 100d * -1d;
					
					if(total > 95.0d) {
						total = 95d;
					}
							
					just.setDilgScore(dilgScore[recnt]);
					just.setDesdScore(desdScore[recnt]);
					just.setTotalScore(total);
							
					dao.saveObject(just);
					/*
					inProcess++;
					if((inProcess % 10) == 0){
						percentage = (double)(inProcess/percentUnit);
						setRunStatus("updateJustAll", "depart:" + campus, inProcess, pcnt, percentage,"no");
					}
					*/
					recnt++;
				}
			}catch(Exception e){
				/*
				if(just.equals(null)) {
					err.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.generic", "|JustOid|0|" ));
				} else {
					err.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.generic", "|JustOid|" + just.getOid()));
				}
				*/
				e.printStackTrace();
				err.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.generic", e.toString()));
				
				return err;
			}
		}
				
		//log.debug("StudConductCreateAll->done!!!");
		return err;
	}
	
	public ActionMessages conductUpdate(String depart){
		ActionMessages msgs = new ActionMessages();
		String sql = "";
		String campSchool = "";
		String runlogs = "";
		int inProcess = 0;
		double percentage = 0d;
		
		setRunStatus("updateJustAll", "start!", 0, 0, 0d, "no");
		/*
		hql = "select c From Code5 c Where category='SchoolType' And name like '" + campus + "%'";
		List<Code5> clazzList = dao.submitQuery(hql);
		log.debug("StudConductUpdateAll->clazzList:" + campus + " :" + clazzList.size());
		*/
		//if(!clazzList.isEmpty()){
			//for(Iterator<Code5> clazzIter=clazzList.iterator(); clazzIter.hasNext();){
				//campSchool = clazzIter.next().getIdno();
				sql = "Select Distinct depart_class From stmd where depart_class like '" + depart + "%'";
				//List cList = getDepartClassByType(depart);
				List cList = jdbcDao.findAnyThing(sql);
				
				int percentUnit = 1;
				int pcnt = cList.size();
				if(pcnt > 100) {
					percentUnit = (int)(Math.floor(pcnt / 100));
				}else{
					percentUnit = 1;
				}

				for(Iterator cIter=cList.iterator(); cIter.hasNext();){
					String clazz = ((Map)cIter.next()).get("depart_class").toString();
					sql ="select count(*) from stmd where depart_class='" + clazz +"'";
					int studknt = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
					
					log.debug("StudConductUpdateAll->clazz:" + clazz + " , size:" + studknt + " ," + pcnt + "/" + inProcess);
					//可重覆試做3次
					for(int i=0; i<3; i++) {
						//setRunStatus("updateJustAll", "depart:" + clazz + " start!", 0, 0, 0d, "no");
						msgs = this.studCreateOrUpdateJust(clazz, "update");
						if(msgs.isEmpty()) {
							break;
						}else {
							log.debug("StudConductUpdateAll->fail on depart:" + clazz + ", retry:" + i);
							setRunStatus("updateJustAll", "depart:" + clazz + " retry!", inProcess, pcnt, percentage, "no");
							sleeping(3000);	//休息3秒
						}
					}
					if(!msgs.isEmpty()) {
						setRunStatus("updateJustAll", "depart:" + clazz + " fail!", 0, 0, 0d, "yes");
						break;
					}
					runlogs = "class:" + clazz + " update " + studknt + " records!\n" + runlogs; 
					inProcess++;
					if((inProcess % 10) == 0){
						percentage = (double)(inProcess/percentUnit);
						setRunStatus("updateJustAll", "depart:" + clazz, inProcess, pcnt, percentage, runlogs);
					}

				}
			//}
		//}
		if(msgs.isEmpty()) {
			setRunStatus("updateJustAll", "finished!", 100, 100, 100d, "yes");
		}

		return msgs;
	}
	
	public ActionMessages conductCreate(String depart){
		ActionMessages msgs = new ActionMessages();
		String sql = "";
		String campSchool = "";
		String runlogs = "";
		int inProcess = 0;
		double percentage = 0d;
		
		setRunStatus("createJustAll", "", 0, 0, 0d, "no");
		sql = "Select ClassNo From Class where ClassNo like '" + depart + "%'";
		//List cList = getDepartClassByType(depart);
		List cList = jdbcDao.findAnyThing(sql);
		
		int percentUnit = 1;
		int pcnt = cList.size();
		if(pcnt > 100) {
			percentUnit = (int)(Math.floor(pcnt / 100));
		}else{
			percentUnit = 1;
		}

		for(Iterator cIter=cList.iterator(); cIter.hasNext();){
			String clazz = ((Map)cIter.next()).get("ClassNo").toString();
			sql ="select count(*) from stmd where depart_class='" + clazz +"'";
			int studknt = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			
			//log.debug("StudConductCreateAll->clazz:" + clazz + " , size:" + studknt + " ," + pcnt + "/" + inProcess);
			//可重覆試做3次
			for(int i=0; i<3; i++) {
				//setRunStatus("updateJustAll", "depart:" + clazz + " start!", 0, 0, 0d, "no");
				msgs = this.studCreateOrUpdateJust(clazz, "create");
				if(msgs.isEmpty()) {
					break;
				}else {
					log.debug("StudConductCreateAll->fail on depart:" + clazz + ", retry:" + i);
					setRunStatus("createJustAll", "depart:" + clazz + " retry!", inProcess, pcnt, percentage, "no");
					sleeping(3000);	//休息3秒
				}
			}
			if(!msgs.isEmpty()) {
				setRunStatus("createJustAll", "depart:" + clazz + " fail!", 0, 0, 0d, "yes");
				break;
			}
			runlogs = "class:" + clazz + " update " + studknt + " records!\n" + runlogs; 
			inProcess++;
			if((inProcess % 10) == 0){
				percentage = (double)(inProcess/percentUnit);
				setRunStatus("createJustAll", "depart:" + clazz, inProcess, pcnt, percentage, runlogs);
			}

		}
		if(msgs.isEmpty()) {
			setRunStatus("createJustAll", "finished!", 100, 100, 100d, "yes");
		}

		return msgs;
	}
	
	public ActionMessages modifyClassJustByFormProp(DynaActionForm form, List justList, String classInCharge) {
		ActionMessages msgs = new ActionMessages();
		String[] teacherScores = form.getStrings("teacherScore");
		String[] deptheaderScores = form.getStrings("deptheaderScore");
  		String[] militaryScores = form.getStrings("militaryScore");
  		String[] meetingScores = form.getStrings("meetingScore");
  		String[] comCode1 = form.getStrings("comCode1");
  		String[] comCode2 = form.getStrings("comCode2");
  		String[] comCode3 = form.getStrings("comCode3");
		double teacherScore = 0.0d;
		double deptheaderScore = 0.0d;
		double militaryScore = 0.0d;
		double meetingScore = 0.0d;
		double dilgScore = 0.0d;
		double desdScore = 0.0d;
		double totalScore = 0.0d;
		double[] scores = new double[2];
		
  		int i =0;
  		Just just;
	  	List<Code1> code1List;
  		for(Iterator<Just> justIter=justList.iterator(); justIter.hasNext();){
  			just = justIter.next();
  			
  			if(!teacherScores[i].trim().equals("")){
  				teacherScore = Double.parseDouble(teacherScores[i]);
  			}else if(teacherScores[i].trim().equals("")){
  				teacherScore = 0.0d;
  			}
  			
  			if(!deptheaderScores[i].trim().equals("")){
  				deptheaderScore = Double.parseDouble(deptheaderScores[i]);
  			}else if(deptheaderScores[i].trim().equals("")){
  				deptheaderScore = 0.0d;
  			}
  			
  			if(!militaryScores[i].trim().equals("")){
  				militaryScore = Double.parseDouble(militaryScores[i]);
  			}else if(militaryScores[i].trim().equals("")){
  				militaryScore = 0.0d;
  			}
  			
  			if(!meetingScores[i].trim().equals("")){
  				meetingScore = Double.parseDouble(meetingScores[i]);
  			}else if(meetingScores[i].trim().equals("")){
  				meetingScore = 0.0d;
  			}
  			
  			scores = this.calConductScoreOfDilgDesd(just.getStudentNo());
  			dilgScore = scores[0];
  			desdScore = scores[1];
  			
  			if(teacherScore != just.getTeacherScore() || deptheaderScore != just.getDeptheaderScore()
  					|| militaryScore != just.getMilitaryScore() || meetingScore != just.getMeetingScore()
  					|| dilgScore != just.getDilgScore() || desdScore != just.getDesdScore() 
  					|| !comCode1[i].equals(just.getComCode1()) || !comCode2[i].equals(just.getComCode2())
  					|| !comCode3[i].equals(just.getComCode3())) {
  			
	  			totalScore = 82d + teacherScore + deptheaderScore + militaryScore + meetingScore + dilgScore + desdScore;

				if(totalScore > 0d)
					totalScore = Math.round(totalScore * 100d) / 100d;
				else if(totalScore < 0d)
					totalScore = 0d;
					//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;
	  			
	  			if(totalScore > 95.0d) totalScore = 95.0d;
	  			
	  			try{
	  			just.setTeacherScore(teacherScore);
	  			just.setDeptheaderScore(deptheaderScore);
	  			just.setMilitaryScore(militaryScore);
	  			just.setMeetingScore(meetingScore);
	  			just.setDilgScore(dilgScore);
	  			just.setDesdScore(desdScore);
	  			just.setTotalScore(totalScore);
	  			
	  	  		if(!comCode1[i].trim().equals("")){
	  	  			just.setComCode1(comCode1[i]);
	  	  			code1List = this.findConductMark(comCode1[i]);
	  	  			if(!code1List.isEmpty())
	  	  				just.setComName1(code1List.get(0).getName());
	  	  			else {
	  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  	  						new ActionMessage("Message.ConductMarkErr"));
	  	  				return msgs;
	  	  			}
	  	  		}else{
	  	  			just.setComCode1(null);
	  	  			just.setComName1(null);
	  	  		}

	  	  		if(!comCode2[i].trim().equals("")){
	  	  			just.setComCode2(comCode2[i]);
	  	  			code1List = this.findConductMark(comCode2[i]);
	  	  			if(!code1List.isEmpty())
	  	  				just.setComName2(code1List.get(0).getName());
	  	  			else {
	  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  	  						new ActionMessage("Message.ConductMarkErr"));
	  	  				return msgs;
	  	  			}
	  	  		}else{
	  	  			just.setComCode2(null);
	  	  			just.setComName2(null);
	  	  		}

	  	  		if(!comCode3[i].trim().equals("")){
	  	  			just.setComCode3(comCode3[i]);
	  	  			code1List = this.findConductMark(comCode3[i]);
	  	  			if(!code1List.isEmpty())
	  	  				just.setComName3(code1List.get(0).getName());
	  	  			else {
	  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
	  	  						new ActionMessage("Message.ConductMarkErr"));
	  	  				return msgs;
	  	  			}
	  	  		}else{
	  	  			just.setComCode3(null);
	  	  			just.setComName3(null);
	  	  		}

	  			dao.saveObject(just);
	  			}catch(Exception e) {
	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.generic", "更改全班學生操行成績失敗!"));
					return msgs;
	  			}
  			}
  			i++;
  		}
  		return msgs;
	}

	public ActionMessages modifyUploadJustByFormProp(DynaActionForm form, List justList, String classInCharge, String uploadMode){
		ActionMessages msgs = new ActionMessages();
		
		String[] dilgScores = form.getStrings("dilgScore");
		String[] desdScores = form.getStrings("desdScore");
		double teacherScore = 0.0d;
		double deptheaderScore = 0.0d;
		double militaryScore = 0.0d;
		double meetingScore = 0.0d;
		double dilgScore = 0.0d;
		double desdScore = 0.0d;
		double totalScore = 0.0d;
		double[] scores = new double[2];
  		int i =0;
  		Just just;

		if(uploadMode.equalsIgnoreCase("Teacher")) {
			String[] teacherScores = form.getStrings("teacherScore");
	  		String[] comCode1 = form.getStrings("comCode1");
	  		String[] comCode2 = form.getStrings("comCode2");
	  		String[] comCode3 = form.getStrings("comCode3");
		  	List<Code1> code1List;
	  		
	  		for(Iterator<Just> justIter=justList.iterator(); justIter.hasNext();){
	  			just = justIter.next();
	  			
	  			if(!teacherScores[i].trim().equals("")){
	  				teacherScore = Double.parseDouble(teacherScores[i]);
	  			}else if(teacherScores[i].trim().equals("")){
	  				teacherScore = 0.0d;
	  			}
	  				  			
	  			if(teacherScore != just.getTeacherScore() || !comCode1[i].equals(just.getComCode1())
	  					|| !comCode2[i].equals(just.getComCode2()) || !comCode3[i].equals(just.getComCode3())) {
	  				
	  				dilgScore = Double.parseDouble(dilgScores[i]);
	  				desdScore = Double.parseDouble(desdScores[i]);
	  				deptheaderScore = just.getDeptheaderScore();
	  				militaryScore = just.getMilitaryScore();
	  				meetingScore = just.getMeetingScore();
	  				
		  			totalScore = 82d + teacherScore + deptheaderScore + militaryScore + meetingScore + dilgScore + desdScore;

					if(totalScore > 0d)
						totalScore = Math.round(totalScore * 100d) / 100d;
					else if(totalScore < 0d)
						totalScore = 0d;
						//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

		  			if(totalScore > 95.0d) totalScore = 95.0d;
		  			
		  			try{
			  			just.setTeacherScore(teacherScore);
			  			just.setTotalScore(totalScore);
			  	  		if(!comCode1[i].trim().equals("")){
			  	  			just.setComCode1(comCode1[i]);
			  	  			code1List = this.findConductMark(comCode1[i]);
			  	  			if(!code1List.isEmpty())
			  	  				just.setComName1(code1List.get(0).getName());
			  	  			else {
			  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
			  	  						new ActionMessage("Message.ConductMarkErr"));
			  	  				return msgs;
			  	  			}
			  	  		}else{
			  	  			just.setComCode1(null);
			  	  			just.setComName1(null);
			  	  		}

			  	  		if(!comCode2[i].trim().equals("")){
			  	  			just.setComCode2(comCode2[i]);
			  	  			code1List = this.findConductMark(comCode2[i]);
			  	  			if(!code1List.isEmpty())
			  	  				just.setComName2(code1List.get(0).getName());
			  	  			else {
			  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
			  	  						new ActionMessage("Message.ConductMarkErr"));
			  	  				return msgs;
			  	  			}
			  	  		}else{
			  	  			just.setComCode2(null);
			  	  			just.setComName2(null);
			  	  		}

			  	  		if(!comCode3[i].trim().equals("")){
			  	  			just.setComCode3(comCode3[i]);
			  	  			code1List = this.findConductMark(comCode3[i]);
			  	  			if(!code1List.isEmpty())
			  	  				just.setComName3(code1List.get(0).getName());
			  	  			else {
			  	  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
			  	  						new ActionMessage("Message.ConductMarkErr"));
			  	  				return msgs;
			  	  			}
			  	  		}else{
			  	  			just.setComCode3(null);
			  	  			just.setComName3(null);
			  	  		}
			  			//log.debug("modifyUploadJustByFormProp->studentno:" + just.getStudentNo());
			  			dao.saveObject(just);
		  			}catch(Exception e) {
		  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.generic", "更改全班學生操行成績失敗!"));
						return msgs;
		  			}
	  			}
	  			i++;
	  		}

	  		
	  		
		}else if(uploadMode.equalsIgnoreCase("Chairman")) {
			String[] deptheaderScores = form.getStrings("deptheaderScore");
	  		for(Iterator<Just> justIter=justList.iterator(); justIter.hasNext();){
	  			just = justIter.next();
	  			
	  			if(!deptheaderScores[i].trim().equals("")){
	  				deptheaderScore = Double.parseDouble(deptheaderScores[i]);
	  			}else if(deptheaderScores[i].trim().equals("")){
	  				deptheaderScore = 0.0d;
	  			}
	  				  			
	  			if(deptheaderScore != just.getDeptheaderScore()) {
	  				teacherScore = just.getTeacherScore();
	  				militaryScore = just.getMilitaryScore();
	  				meetingScore = just.getMeetingScore();
	  				dilgScore = Double.parseDouble(dilgScores[i]);
	  				desdScore = Double.parseDouble(desdScores[i]);
	  				
		  			totalScore = 82d + teacherScore + deptheaderScore + militaryScore + meetingScore + dilgScore + desdScore;

					if(totalScore > 0d)
						totalScore = Math.round(totalScore * 100d) / 100d;
					else if(totalScore < 0d)
						totalScore = 0d;
						//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

		  			if(totalScore > 95.0d) totalScore = 95.0d;
		  			
		  			try{
		  			just.setDeptheaderScore(deptheaderScore);
		  			just.setTotalScore(totalScore);
		  			dao.saveObject(just);
		  			}catch(Exception e) {
		  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.generic", "更改全班學生操行成績失敗!"));
						return msgs;
		  			}
	  			}
	  			i++;
	  		}

			
		}else if(uploadMode.equalsIgnoreCase("Drillmaster")){
	  		String[] militaryScores = form.getStrings("militaryScore");
	  		for(Iterator<Just> justIter=justList.iterator(); justIter.hasNext();){
	  			just = justIter.next();
	  			
	  			if(!militaryScores[i].trim().equals("")){
	  				militaryScore = Double.parseDouble(militaryScores[i]);
	  			}else if(militaryScores[i].trim().equals("")){
	  				militaryScore = 0.0d;
	  			}
	  			//log.debug("Military_score:orig:" + just.getMilitaryScore() + "change:" + militaryScore);	  			
	  			if(militaryScore != just.getMilitaryScore()) {
	  				teacherScore = just.getTeacherScore();
	  				deptheaderScore = just.getDeptheaderScore();
	  				meetingScore = just.getMeetingScore();
	  				dilgScore = Double.parseDouble(dilgScores[i]);
	  				desdScore = Double.parseDouble(desdScores[i]);
	  				
		  			totalScore = 82d + teacherScore + deptheaderScore + militaryScore + meetingScore + dilgScore + desdScore;

					if(totalScore > 0d)
						totalScore = Math.round(totalScore * 100d) / 100d;
					else if(totalScore < 0d)
						totalScore = 0d;
						//totalScore = Math.round(totalScore * 100d * -1d) / 100d * -1d;

		  			if(totalScore > 95.0d) totalScore = 95.0d;
		  			
		  			try{
		  			just.setMilitaryScore(militaryScore);
		  			just.setTotalScore(totalScore);
		  			dao.saveObject(just);
		  			}catch(Exception e) {
		  				msgs.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.generic", "更改全班學生操行成績失敗!"));
						return msgs;
		  			}
	  			}
	  			i++;
	  		}
			
		}
		
  		return msgs;
	}

	
	public List<String> findCampusDepartment(){
		List<String> retList;
		String hql = "Select Distinct c5.name From Code5 c5 Where c5.category='SchoolType'";
		retList = dao.submitQuery(hql);
		return retList;
	}

	public List findDilgRangeByStudentNo(String studentNo, int range) {
		String hql ="";
		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Dilg dilg;
		
		/*
		Map cbMap = new HashMap();
		cbMap.put("studentNo", studentNo);
		List cbList = new ArrayList();

		cbList.add(cbMap);
		*/
		
		List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);

		Seld seld;
		List<Dtime> dtimeList;
		Dtime dtime;
		int dtOid;
		int tfsum;
		int tfLimit, tfwarn, thour;

		for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			dtOid = seld.getDtimeOid();

			dtimeList = (List<Dtime>) (dao.submitQuery("From Dtime Where Oid="
					+ dtOid));
			dtime = dtimeList.get(0);

			tfLimit = 0;

			if (Toolket.chkIsGraduateClass(dtime.getDepartClass())
					&& sterm.equals("2")) {
				if(dtime.getThour() * 14 % 3 > 0){
					tfLimit = dtime.getThour() * 14 / 3 + 1;
				}else{
					tfLimit = dtime.getThour() * 14 / 3;
				}
				tfwarn = (int)Math.round(tfLimit * 0.9);
			} else {
				tfLimit = dtime.getThour() * 18 / 3;
				tfwarn = (int)Math.round(tfLimit * 0.9);
			}
			tfsum = seld.getDilgPeriod();
			//if(studentNo.equals("95145013")) log.debug("cscode:" + dtime.getCscode() + ",1:" + tfsum);
			if(seld.getElearnDilg() != null){
				//tfsum = tfsum + seld.getElearnDilg();
				tfsum += seld.getElearnDilg();
				//if(studentNo.equals("95145013")) log.debug("2:" + tfsum);
			}
			
			//log.debug("findDilgRangeByStudentNo->" + studentNo + ",subject:" + seld.getCscode()+":"+seld.getCscodeName()+":"+tfLimit+":"+tfsum);
			if (tfsum+range >= tfLimit && tfLimit != 0) {
				log.debug("findDilgRangeByStudentNo->subject:" + seld.getCscode()+":"+seld.getCscodeName()+":"+tfLimit+":"+tfsum);
				Map tfMap = new HashMap();
				tfMap.put("cscode", dtime.getCscode());
				tfMap.put("dtimeOid", seld.getDtimeOid());
				tfMap.put("departClass", dtime.getDepartClass());
				tfMap.put("subjectName", Toolket.getClassFullName(dtime.getDepartClass()) + " " + seld.getCscodeName());
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum);
				tfMap.put("warnning", "yes");
				dilgList.add(tfMap);
			}
		}
		seldList = null;
		dtimeList = null;
		//System.gc();

		return dilgList;
		
	}

	public List findDilgRangeBySubject(Dtime dtime, int range){
		String hql ="";
		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Dilg dilg;
		
		hql = "Select s From Seld s Where s.dtimeOid=" + dtime.getOid();
		List<Seld> seldList = dao.submitQuery(hql);
		Seld seld;
		int dtOid = dtime.getOid();
		String cscode = dtime.getCscode();
		String departClass = dtime.getDepartClass();
		String deptClassName = Toolket.getClassFullName(departClass);
		String cscodeName="";
		short period = dtime.getThour();
		Csno csno=dao.findNameOfCourse(cscode);
		if(csno != null) {
			cscodeName = csno.getChiName();
		}
		int tfsum;
		int tfLimit, tfwarn;

		if (Toolket.chkIsGraduateClass(dtime.getDepartClass())
				&& sterm.equals("2")) {
			if(dtime.getThour() * 14 % 3 > 0){
				tfLimit = dtime.getThour() * 14 / 3 + 1;
			}else{
				tfLimit = dtime.getThour() * 14 / 3;
			}
			tfwarn = (int)Math.round(tfLimit * 0.9);
		} else {
			tfLimit = dtime.getThour() * 18 / 3;
			tfwarn = (int)Math.round(tfLimit * 0.9);
		}
		
		//log.debug("findDilgRangeBySubject->number of student select:" + cscode+":"+cscodeName+":"+seldList.size());
		
		for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
			seld = seldIter.next();
			
			/*
			Map cbMap = new HashMap();
			cbMap.put("studentNo", seld.getStudentNo());
			List cbList = new ArrayList();
			cbList.add(cbMap);
			*/
			
			tfsum = seld.getDilgPeriod();
			if(seld.getElearnDilg() != null){
				tfsum += seld.getElearnDilg();
			}
			
			if (tfsum+range >= tfLimit && tfLimit != 0) {
				log.debug("findDilgRangeBySubject->subject:" + cscode+":"+cscodeName+":"+tfLimit+":"+tfsum);
				Map tfMap = new HashMap();
				tfMap.put("studentNo", seld.getStudentNo());
				tfMap.put("cscode", cscode);
				tfMap.put("dtimeOid", dtOid);
				tfMap.put("departClass", departClass);
				tfMap.put("subjectName", deptClassName + " " + cscodeName);
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum);
				tfMap.put("warnning", "yes");
				dilgList.add(tfMap);
			}
		}
		seldList = null;
		//System.gc();
		return dilgList;
		
	}
	
	public List findCantExamStudByDepartClass(String departClass, int range, String scope, String sorttype){
		List<Map> noExam = new ArrayList();
		String hql = "";
		List<Map> SubjDilg = new ArrayList();
		List<Clazz> clazzList = new ArrayList();
		List<Seld> seldList = new ArrayList();
		List<Dtime> dtimeList = new ArrayList();
		String studentNo = "";
		String studentName = "";
		String stDeptClass = "";
		String stDeptClassName = "";
		String clazzes = "";
		String tmp = "";
		String sterm = Toolket.getSysParameter("School_term");
		
		//try{
		if(!departClass.trim().equals("")) {
			if(scope.equals("0")){			//全部
				if(sorttype.equals("0")) {	//以學制開課為主
					hql = "select d from Dtime d Where d.sterm='" + sterm +
							"' and d.departClass like '" + departClass + "' order by d.departClass";
					
				}else if(sorttype.equals("1")){	//以學制學生為主
					hql = "select s from Student s Where departClass like '" + departClass + "' order by departClass";
				}
				
			}else if(scope.equals("1")) {	//畢業班
				hql = "select c from Clazz c Where c.classNo like '" + departClass + "'";
				clazzList = dao.submitQuery(hql);
				if(clazzList.isEmpty()){
					clazzes = "('')";
				}else{
					clazzes = "";
					for(ListIterator<Clazz> clazzIter=clazzList.listIterator(); clazzIter.hasNext();){
						Clazz clazz = clazzIter.next();
						//log.debug("findCantExam->educate clazz " + clazz.getClassNo() + ":" + clazz.getClassFullName());
						if(Toolket.chkIsGraduateClass(clazz.getClassNo())){
							clazzes = clazzes + ",'" + clazz.getClassNo() + "'";	
						}
					}
					if(!clazzes.equals("")){
						clazzes = clazzes.substring(1);
						clazzes = "(" + clazzes + ")";
					}else {
						clazzes = "('')";
					}
				}
				if(sorttype.equals("0")) {	
					hql = "select d from Dtime d Where d.sterm='" + sterm +
							"' and d.departClass in " + clazzes + " order by d.departClass";
					//log.debug("hql=>" + hql);
				}else if(sorttype.equals("1")){
					hql = "select s from Student s Where departClass in " + clazzes;
				}
								
			}else if(scope.equals("2")) {	//除畢業班外
				hql = "select c from Clazz c Where c.classNo like '" + departClass + "'";
				clazzList = dao.submitQuery(hql);
				if(clazzList.isEmpty()){
					clazzes = "('')";
				}else{
					clazzes = "";
					for(Iterator<Clazz> clazzIter=clazzList.iterator(); clazzIter.hasNext();){
						Clazz clazz = clazzIter.next();
						if(!Toolket.chkIsGraduateClass(clazz.getClassNo())){
							clazzes = clazzes + ",'" + clazz.getClassNo() + "'";	
						}
					}
					if(!clazzes.equals("")){
						clazzes = clazzes.substring(1);
						clazzes = "(" + clazzes + ")";
					}else {
						clazzes = "('')";
					}
				}
				if(sorttype.equals("0")) {	
					hql = "select d from Dtime d Where d.sterm='" + sterm +
							"' and d.departClass in " + clazzes + " order by d.departClass";
				}else if(sorttype.equals("1")){
					hql = "select s from Student s Where departClass in " + clazzes;
				}
				
			}
			long runtime = System.currentTimeMillis(); 
			
			if(sorttype.equals("0")) {	//以學制開課為主
				dtimeList = dao.submitQuery(hql);
				for(Dtime dtime: dtimeList){
					//log.debug("findCantExam->departclass: " + dtime.getDepartClass() + ",subject:" + dtime.getCscode());
					SubjDilg = this.findDilgRangeBySubject(dtime, range);
					if(!SubjDilg.isEmpty()){
						for(Map dilgMap: SubjDilg){
							hql = "select s From Student s Where s.studentNo='" + dilgMap.get("studentNo") +"'";
							List<Student> studList = dao.submitQuery(hql);
							if(!studList.isEmpty()) {
								Student student = studList.get(0);
								//studentNo = student.getStudentNo();
								//studentName = student.getStudentName();
								//stDeptClass = student.getDepartClass();
								//stDeptClassName = Toolket.getClassFullName(student.getDepartClass());	

								Map neMap = new HashMap();
								//neMap.putAll(dilgMap);
								neMap.put("subjectName", dilgMap.get("subjectName"));
								neMap.put("period", dilgMap.get("period"));
								neMap.put("timeOff", dilgMap.get("timeOff"));
								neMap.put("tfLimit", dilgMap.get("tfLimit"));
								neMap.put("studentNo", student.getStudentNo());
								neMap.put("studentName", student.getStudentName());
								neMap.put("stDeptClass", student.getDepartClass());
								neMap.put("stDeptClassName", Toolket.getClassFullName(student.getDepartClass()));
								noExam.add(neMap);
							}
						}
					}
				}
			}else if(sorttype.equals("1")){
				List<Student> studList = dao.submitQuery(hql);
				//log.debug("findCantExam->total student in " + departClass + " =" + studList.size());
				for(Student student: studList){
					runtime = System.currentTimeMillis();
					SubjDilg = this.findDilgRangeByStudentNo(student.getStudentNo(), range);
					
					if(!SubjDilg.isEmpty()){
						//log.debug("findCantExamStudByDepartClass->studentNo:" + student.getStudentNo()+":"+SubjDilg.size());
						//log.debug("findDilg spent time:" + (System.currentTimeMillis()-runtime)) ;
						studentNo = student.getStudentNo();
						studentName = student.getStudentName();
						stDeptClass = student.getDepartClass();
						stDeptClassName = Toolket.getClassFullName(student.getDepartClass());	
						
						for(Map dilgMap: SubjDilg){
							Map neMap = new HashMap();
							//neMap.putAll(dilgMap);
							neMap.put("subjectName", dilgMap.get("subjectName"));
							neMap.put("period", dilgMap.get("period"));
							neMap.put("timeOff", dilgMap.get("timeOff"));
							neMap.put("tfLimit", dilgMap.get("tfLimit"));
							neMap.put("studentNo", studentNo);
							neMap.put("studentName", studentName);
							neMap.put("stDeptClass", stDeptClass);
							neMap.put("stDeptClassName", stDeptClassName);
							noExam.add(neMap);
						}
					}
					//log.debug("spent time:" + (System.currentTimeMillis()-runtime)) ;
				}
			}
		}
		/*
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		SubjDilg = null;
		clazzList = null;
		seldList = null;
		dtimeList = null;
		System.gc();
		return noExam;
	}
	
	public List createInspectedStudents(String campus, String sYear, String sTerm){
		List<Keep> retList = new ArrayList();
		String hql = "";
		String sql = "";
		String campSchool = "";
		
		hql = "select c From Code5 c Where category='SchoolType' And name like '" + campus + "%'";
		List campList = dao.submitQuery(hql);

		if(!campList.isEmpty()){
			for(Iterator<Code5> campIter=campList.iterator(); campIter.hasNext();){		
				campSchool = campIter.next().getIdno();
				
				sql = "delete from keep where down_year='" + sYear +"' and down_term='" + sTerm +
						"' and depart_class like '" + campSchool + "%'";
				jdbcDao.updateAnyThing(sql);
				
				hql = "select j from Just j where totalScore=60.0 and departClass like '" + campSchool + "%'";
				List<Just> justList = dao.submitQuery(hql);
				for(Iterator<Just> justIter=justList.iterator(); justIter.hasNext();){
					Just just = justIter.next();
					sql = "select * from keep where down_year is not null and down_term is not null " +
							"and up_year is null and up_term is null and student_no='" + just.getStudentNo() + "'";
					List tmpList = jdbcDao.findAnyThing(sql);
					if(tmpList.isEmpty()){
						Keep keep = new Keep();
						keep.setDepartClass(just.getDepartClass());
						keep.setStudentNo(just.getStudentNo());
						keep.setDownYear(Short.parseShort(sYear));
						keep.setDownTerm(Short.parseShort(sTerm));
						dao.saveObject(keep);
						List<Student> studs = scoredao.findStudentByStudentNO(just.getStudentNo());
						if(!studs.isEmpty()){
							keep.setStudentName(studs.get(0).getStudentName());
						}
						keep.setDeptClassName(Toolket.getClassFullName(just.getDepartClass()));
						retList.add(keep);
					}
				}

			}
		}
		
		return retList;
	}
	
	public List<Keep> findStudInspectedByForm(Map formMap){
		List<Keep> keepList;
		String studentNo  = formMap.get("studentNo").toString();
		String studentName  = formMap.get("studentName").toString();
		String clazz  = formMap.get("clazz").toString();
		String downYear  = formMap.get("downYear").toString();
		String hql = "";
		
		if(!studentNo.trim().equals("")){
			hql = "select k From Keep k Where studentNo='" + studentNo + "'";
		}else if(!studentName.trim().equals("")){
			hql = "select s From Student s Where studentName like '%" + studentName + "%'";
			List<Student> studs = dao.submitQuery(hql);
			
			hql = "select g From Graduate g Where studentName like '%" + studentName + "%'";
			List<Graduate> grads = dao.submitQuery(hql);
			
			String studFilter = "";
			Student stud;
			if(!studs.isEmpty()){
				for(Iterator<Student> studIter=studs.iterator(); studIter.hasNext();){
					stud = studIter.next();
					studFilter = studFilter + ",'" + stud.getStudentNo() + "'";
				}
			}
			
			Graduate grad;
			if(!grads.isEmpty()){
				for(Iterator<Graduate> gradIter=grads.iterator(); gradIter.hasNext();){
					grad = gradIter.next();
					studFilter = studFilter + ",'" + grad.getStudentNo() + "'";
				}
			}
			
			if(studFilter.trim().equals("")){
				studFilter = "('')";
			}else{
				studFilter = "(" + studFilter.substring(1) + ")";
			}
			
			//log.debug("studFilter->" + studFilter);
			hql = "select k From Keep k Where studentNo in " + studFilter;
		}else if(!clazz.trim().equals("")){
			hql = "select k From Keep k Where departClass like '" + clazz + "%'";
		}else if(!downYear.trim().equals("")){
			hql = "select k From Keep k Where downYear=" + downYear;
		}
		
		keepList = dao.submitQuery(hql);
		for(Keep keep:keepList){
			Student stud = this.findStudentByStudentNo(keep.getStudentNo());
			Graduate grad= this.findGraduateByStudentNo(keep.getStudentNo());
			if(stud!=null){
				keep.setStudentName(stud.getStudentName());
			}else if(grad!=null){
				keep.setStudentName(grad.getStudentName());
			}
			keep.setDeptClassName(Toolket.getClassFullName(keep.getDepartClass()));
		}
		
		return keepList;
	}
	
	public ActionMessages delStudInspected(List<Keep> selKeeps, ResourceBundle bundle){
		ActionMessages msgs = new ActionMessages();
		
		Keep keep = new Keep();
		try{
			for(Iterator<Keep> keepIter = selKeeps.iterator(); keepIter.hasNext();) {
				keep = keepIter.next();
				dao.removeObject(keep);
			}
		}catch (Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
			  	  		new ActionMessage("Message.DelInspectedErr"));
		}
		
		return msgs;
	}
	
	public ActionMessages createInspectedByForm(Map dForm){
		String studentNo  = dForm.get("studentNo").toString();
		String studentName  = "";
		String clazz  = "";
		String downYear  = dForm.get("downYear").toString();
		String downTerm  = dForm.get("downTerm").toString();
		ActionMessages msgs = new ActionMessages();
		
		List<Student> studs = scoredao.findStudentByStudentNO(studentNo);
		if(!studs.isEmpty()){
			String sql = "select * from keep where down_year is not null and down_term is not null " +
			"and up_year is null and up_term is null and student_no='" + studentNo + "'";
			List tmpList = jdbcDao.findAnyThing(sql);
			if(tmpList.isEmpty()){
				studentName = studs.get(0).getStudentName();
				clazz = studs.get(0).getDepartClass();
				Keep keep = new Keep();
				keep.setDepartClass(clazz);
				keep.setStudentNo(studentNo);
				keep.setDownYear(Short.parseShort(downYear));
				keep.setDownTerm(Short.parseShort(downTerm));
				dao.saveObject(keep);
			}else{
				msgs.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.StudentHasInspected", studentNo));				
			}
		}else {
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.StudentNotFound", studentNo));
		}
		
		return msgs;
	}
	
	public ActionMessages updateInspectedByForm(Keep keep, Map dForm){
		//String clazz  = dForm.get("clazz").toString();
		String downYear  = dForm.get("downYear").toString();
		String downTerm  = dForm.get("downTerm").toString();
		String upYear  = dForm.get("upYear").toString();
		String upTerm  = dForm.get("upTerm").toString();
		ActionMessages msgs = new ActionMessages();
		/* 在 hibernate 3中不可同一session查詢同一筆資料
		String hql = "select k From Keep k Where studentNo='" + keep.getStudentNo() + 
					"' and departClass ='" + keep.getDepartClass() +
					"' and downYear=" + keep.getDownYear() + " and downTerm=" + keep.getDownTerm();
		List<Keep> keepList = dao.submitQuery(hql);
		*/
		String sql = "select * From keep Where student_no='" + keep.getStudentNo() + 
		"' and depart_class ='" + keep.getDepartClass() +
		"' and down_year=" + keep.getDownYear() + " and down_term=" + keep.getDownTerm();
		
		//
		List<Keep> keepList = jdbcDao.findAnyThing(sql);
		if(keepList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("Message.Removed", "定察資料"));
		}else{
			log.debug("Update keep Oid=" + keepList.size());
			//keep.setDepartClass(clazz);
			if(downYear.equals(""))
				keep.setDownYear(null);
			else
				keep.setDownYear(Short.parseShort(downYear));
			if(downTerm.equals(""))	
				keep.setDownTerm(null);
			else
				keep.setDownTerm(Short.parseShort(downTerm));
			if(upYear.equals(""))
				keep.setUpYear(null);
			else
				keep.setUpYear(Short.parseShort(upYear));
			if(upTerm.equals(""))					
				keep.setUpTerm(null);
			else
				keep.setUpTerm(Short.parseShort(upTerm));
			dao.saveObject(keep);
		}
		
		return msgs;
	}
	
	/**
	 * 以學號查詢操行資料
	 * 
	 * @param studentNo 學號
	 * @return tw.edu.chit.model.Just Object
	 */
	public Just findJustByStudentNo(String studentNo) {
		String hql = "FROM Just j WHERE j.studentNo = ?";
		//List ret = dao.submitQuery(hql, new Object[] { studentNo });
		//if (ret.isEmpty())
			return null;
		//else
			//return (Just) ret.get(0);
		
	}
	
	public List<StdOpinionSuggestion> findStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos) {
		return dao.getStdOpinionSuggestionsBy(sos);
	}
	
	public List<StdOpinionSuggestion> findStdOpinionSuggestionsBy(
			StdOpinionSuggestion sos, Date d) throws DataAccessException {
		return dao.getStdOpinionSuggestionsBy(sos, d);
	}
	
	/**
	 * 儲存StdOpinionSuggestion物件
	 * 
	 * @param sos tw.edu.chit.model.StdOpinionSuggestion Object
	 */
	public void txAddStdOpinionSuggestion(StdOpinionSuggestion sos) {
		dao.saveObject(sos);
	}
	
	/**
	 * 儲存EmplOpinionSuggestion物件
	 * 
	 * @param eos tw.edu.chit.model.EmplOpinionSuggestion Object
	 */
	public void txAddEmplOpinionSuggestion(EmplOpinionSuggestion eos) {
		dao.saveObject(eos);
	}

	public List findDilg4Summary(String DateStart, String DateEnd, String clazzFilter,
			String ClassInChargeSAF){
		
		List<Map> result = new ArrayList();
		int tflen = Global.TimeOffList.size();
		
		Date sDate = new Date();
		Date eDate = new Date();
		Date dDate = new Date();
		
		Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		Calendar dDateCal = Calendar.getInstance();
		
		sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		sDateCal.setTime(sDate);
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		eDateCal.setTime(eDate);
		
		sDateCal.set(Calendar.MILLISECOND, 0);
		eDateCal.set(Calendar.MILLISECOND, 999);
		eDateCal.set(Calendar.HOUR_OF_DAY, 23);
		eDateCal.set(Calendar.MINUTE, 59);
		eDateCal.set(Calendar.SECOND, 59);
		//log.debug("sDateCal=" + sDateCal);
		//log.debug("eDateCal=" + eDateCal);
		
		String[] tDate = DateStart.split("/");
		tDate[0] = "" + (Integer.parseInt(tDate[0]) + 1911);
		DateStart = tDate[0] + "-" + tDate[1] + "-" + tDate[2];
		tDate = DateEnd.split("/");
		tDate[0] = "" + (Integer.parseInt(tDate[0]) + 1911);
		DateEnd = tDate[0] + "-" + tDate[1] + "-" + tDate[2];

		
		
		String hql = "Select d, s From Dilg d, Student s";
		hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
		//hql = hql + " And departClass in " +  ClassInChargeSAF;
		hql = hql + " And d.ddate <= '" + DateEnd +"'";
		hql = hql + " And d.studentNo=s.studentNo";
		hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
		List dsList = dao.submitQuery(hql);
		//List<Dilg> dilgList = dao.submitQuery(hql);
		
		//log.debug("hql = " + hql);
		//log.debug("result size = " + dsList.size());
		
		if(!dsList.isEmpty()){
			Object[] ds0 = (Object[])dsList.get(0);
			Dilg prevDilg = (Dilg)ds0[0];
			Student prevstudent = (Student)ds0[1];
			String prevStudNo = prevDilg.getStudentNo();
			String prevClazz = prevDilg.getDepartClass();
			
			Student student = new Student();
			Dilg dilg = new Dilg();
			String departClass = "";
			String studentName = "";
			int count = 0;
			Map dMap = new HashMap();
			double[] dsum = new double[tflen];
			List stuDilg = new ArrayList();	//紀錄分析過的某學生該期間之曠缺記錄
			List allDilg = new ArrayList();	//紀錄分析過的全部學生之曠缺記錄
			List<Dilg> myDilg = new ArrayList<Dilg>();	//紀錄某學生之原始曠缺記錄
			double sumScore = 0d;
			
			for(int j=0; j<tflen; j++){
				dsum[j] = 0;
			}
				
			for(Iterator dsIter=dsList.iterator(); dsIter.hasNext();){
				Object[] dsObj = (Object[])dsIter.next();
				dilg = (Dilg)dsObj[0];
				student = (Student)dsObj[1];
				Map dAnalysis = dilgAnalysis(dilg);		//當天請假資料之分析
				List dilgs = (List)dAnalysis.get("dList");		//請假分類
				int[] dilgsum = (int[])dAnalysis.get("sum");	//請假別之統計
				
				dDate = dilg.getDdate();
				dDateCal.setTime(dDate);
				/*
				if(dilg.getStudentNo().equals("96131001")){
					log.debug(dDate);
					log.debug(""+dDateCal.compareTo(sDateCal)+":" + dDateCal.compareTo(eDateCal));
					log.debug(dDateCal);
					log.debug(sDateCal);
					log.debug(eDateCal);
				}
				*/
				if(dilg.getStudentNo().equals(prevStudNo)){
					myDilg.add(dilg);
					if(dDateCal.compareTo(sDateCal) >= 0 && dDateCal.compareTo(eDateCal) <= 0){
						stuDilg.addAll(dilgs);
					}
					/* 在考慮扣考的節數必須從統計資料中移除後,該段程式已不使用
					for(int j=1; j<tflen; j++){
						dsum[j] += dilgsum[j];
					}
					*/
					//log.debug("allDilg same student:" + prevStudNo + ", stuDilg size=" + stuDilg.size());
					//if(!dilgIter.hasNext()){
					//}
				}else{
					//long runtime = (new Date()).getTime(); 
					//put data into result list
					if(!prevStudNo.equals("")){
						
						//log.debug("Toolket.getStudentByNo time:"+((new Date()).getTime()-runtime));
						//sumScore = this.calPeriodConductScoreOfDilgDesd(prevStudNo, DateStart, DateEnd, "1");
						//dsum = this.calDilgSummary(myDilg, "0");
						dsum = this.calDilgOneSummary(prevStudNo, "1");

						//log.debug("calPeriodConductScoreOfDilgDesd time:"+((new Date()).getTime()-runtime));
						//log.debug("sumScore:" + prevStudNo + ", sumScore=" + sumScore[0]);
						
						
						if(prevstudent!=null) studentName = prevstudent.getStudentName();
						else studentName = "";
						/*
						else {
							Graduate graduate = Toolket.getGraduateByNo(prevStudNo);
							if(graduate!=null) studentName=graduate.getStudentName();
							else studentName ="";
						}
						*/
						
						Map sdMap = new HashMap();
						sdMap.put("departClass", prevClazz);
						sdMap.put("student", prevStudNo + studentName);
						sdMap.put("dilgList", stuDilg);
						sdMap.put("dilgSum", dsum);
						sdMap.put("sumScore", dsum[0]>=0?"":""+(-dsum[0]));
						allDilg.add(sdMap);
						log.debug("allDilg:" + student.getStudentNo() + ", stuDilg size=" + stuDilg.size());
						myDilg = new ArrayList<Dilg>();
						myDilg.add(dilg);
						stuDilg =  new ArrayList();
						if(dDateCal.compareTo(sDateCal) >= 0 && dDateCal.compareTo(eDateCal) <= 0)
							stuDilg.addAll(dilgs);
						
						prevstudent = student;
						prevStudNo = dilg.getStudentNo();
						prevClazz = dilg.getDepartClass();
						
					}
				}
			}
			//將最後一筆資料加入
			/*
			Student student = Toolket.getStudentByNo(prevStudNo);
			if(student!=null) studentName = student.getStudentName();
			else {
				Graduate graduate = Toolket.getGraduateByNo(prevStudNo);
				if(graduate!=null) studentName=graduate.getStudentName();
				else studentName ="";
			}
			*/
			//dsum = this.calDilgSummary(myDilg, "0");
			dsum = this.calDilgOneSummary(prevStudNo, "1");
			//sumScore = this.calDilgScore(myDilg, "1");
			//log.debug("sumScore:" + prevStudNo + ", sumScore=" + sumScore[0]);
			studentName = student.getStudentName();
			Map sdMap = new HashMap();
			sdMap.put("departClass", prevClazz);
			sdMap.put("student", prevStudNo + studentName);
			sdMap.put("dilgList", stuDilg);
			sdMap.put("dilgSum", dsum);
			sdMap.put("sumScore", dsum[0]>=0?"":""+(-dsum[0]));
			allDilg.add(sdMap);
			

			//log.debug("allDilg size:" + allDilg.size());
			
			for(Iterator sdIter=allDilg.iterator(); sdIter.hasNext();){
				Map sMap = (Map)sdIter.next();
				double[] dscores = (double[])sMap.get("dilgSum");
				List dList = (List<Map>)sMap.get("dilgList");
				String clazz = (String)sMap.get("departClass");
				String stud = (String)sMap.get("student");
				count = 0;
				
				for(Iterator<Map> mIter=dList.iterator(); mIter.hasNext();){
					Map dgMap = mIter.next();
					if(count%3 == 0){
						dMap = new HashMap();
						dMap.put("clazz", Toolket.getClassFullName(clazz));
						dMap.put("Student", stud);
						dMap.put("Date1", dgMap.get("date"));
						dMap.put("Week1", dgMap.get("week"));
						dMap.put("Period1", dgMap.get("start") + "-" + dgMap.get("end"));
						dMap.put("Kind1", dgMap.get("kind"));
						if(count == 0){
							if(dscores[1]>0) dMap.put("SumK1", "" + (int)dscores[1]);
							else dMap.put("SumK1", "");
							if(dscores[2]>0) dMap.put("SumK2", "" + (int)dscores[2]);
							else dMap.put("SumK2", "");
							if(dscores[3]>0) dMap.put("SumK3", "" + (int)dscores[3]);
							else dMap.put("SumK3", "");
							if(dscores[4]>0) dMap.put("SumK4", "" + (int)dscores[4]);
							else dMap.put("SumK4", "");
							if(dscores[5]>0) dMap.put("SumK5", "" + (int)dscores[5]);
							else dMap.put("SumK5", "");
							if(dscores[6]>0) dMap.put("SumK6", "" + (int)dscores[6]);
							else dMap.put("SumK6", "");
							if(dscores[7]>0) dMap.put("SumK7", "" + (int)dscores[7]);
							else dMap.put("SumK7", "");
							if(dscores[8]>0) dMap.put("SumK8", "" + (int)dscores[8]);
							else dMap.put("SumK8", "");
							dMap.put("SumScore", sMap.get("sumScore"));
							dMap.put("Memo", "");							
						}
						if(!mIter.hasNext()){
							dMap.put("Date2", "");
							dMap.put("Week2", "");
							dMap.put("Period2", "");
							dMap.put("Kind2", "");
							dMap.put("Date3", "");
							dMap.put("Week3", "");
							dMap.put("Period3", "");
							dMap.put("Kind3", "");		
							/*
							log.debug("findDilg4Summary Data Detail:" + dMap.get("clazz") + ":" + dMap.get("Student") + ":" +
									dMap.get("Date1") + ":" +dMap.get("Week1") + ":" +dMap.get("Period1") + ":" +
									dMap.get("Kind1") + ":" +dMap.get("Date2") + ":" +dMap.get("Week2") + ":" +
									dMap.get("Period2") + ":" +dMap.get("Kind2") + ":" +dMap.get("Date3") + ":" +
									dMap.get("Week3") + ":" +dMap.get("Period3") + ":" +dMap.get("Kind3") + ":" +
									dMap.get("SumK1") + ":" +dMap.get("SumK2") + ":" +dMap.get("SumK3") + ":" +
									dMap.get("SumK4") + ":" +dMap.get("SumK5") + ":" +dMap.get("SumK6") + ":" +
									dMap.get("SumK7") + ":" +dMap.get("SumScore") + ":" +dMap.get("Memo"));
							*/
						}
						result.add(dMap);

						
					}else if(count%3 == 1){
						dMap.put("Date2", dgMap.get("date"));
						dMap.put("Week2", dgMap.get("week"));
						dMap.put("Period2", dgMap.get("start") + "-" + dgMap.get("end"));
						dMap.put("Kind2", dgMap.get("kind"));
						if(!mIter.hasNext()){
							dMap.put("Date3", "");
							dMap.put("Week3", "");
							dMap.put("Period3", "");
							dMap.put("Kind3", "");
							/*
							log.debug("findDilg4Summary Data Detail:" + dMap.get("clazz") + ":" + dMap.get("Student") + ":" +
									dMap.get("Date1") + ":" +dMap.get("Week1") + ":" +dMap.get("Period1") + ":" +
									dMap.get("Kind1") + ":" +dMap.get("Date2") + ":" +dMap.get("Week2") + ":" +
									dMap.get("Period2") + ":" +dMap.get("Kind2") + ":" +dMap.get("Date3") + ":" +
									dMap.get("Week3") + ":" +dMap.get("Period3") + ":" +dMap.get("Kind3") + ":" +
									dMap.get("SumK1") + ":" +dMap.get("SumK2") + ":" +dMap.get("SumK3") + ":" +
									dMap.get("SumK4") + ":" +dMap.get("SumK5") + ":" +dMap.get("SumK6") + ":" +
									dMap.get("SumK7") + ":" +dMap.get("SumScore") + ":" +dMap.get("Memo"));
							*/
						}

					}else if(count%3 == 2){
						dMap.put("Date3", dgMap.get("date"));
						dMap.put("Week3", dgMap.get("week"));
						dMap.put("Period3", dgMap.get("start") + "-" + dgMap.get("end"));
						dMap.put("Kind3", dgMap.get("kind"));
						/*
						if(!mIter.hasNext()){
							log.debug("findDilg4Summary Data Detail:" + dMap.get("clazz") + ":" + dMap.get("Student") + ":" +
									dMap.get("Date1") + ":" +dMap.get("Week1") + ":" +dMap.get("Period1") + ":" +
									dMap.get("Kind1") + ":" +dMap.get("Date2") + ":" +dMap.get("Week2") + ":" +
									dMap.get("Period2") + ":" +dMap.get("Kind2") + ":" +dMap.get("Date3") + ":" +
									dMap.get("Week3") + ":" +dMap.get("Period3") + ":" +dMap.get("Kind3") + ":" +
									dMap.get("SumK1") + ":" +dMap.get("SumK2") + ":" +dMap.get("SumK3") + ":" +
									dMap.get("SumK4") + ":" +dMap.get("SumK5") + ":" +dMap.get("SumK6") + ":" +
									dMap.get("SumK7") + ":" +dMap.get("SumScore") + ":" +dMap.get("Memo"));
						}
						*/
					}
					count++;
				}
				
			}
			
		}
		log.debug(result.size());
		return result;

	}

	public List findDilg4SummaryT(String clazzFilter, String pmode){
		List allDilg = new ArrayList();
		List dsList = new ArrayList();
		String hql = "", sql="";
		String sterm = Toolket.getSysParameter("School_term");
		if(pmode.equals("1")){
			if(sterm.equals("2")){
				String clazzs = "";
				sql = "Select DISTINCT depart_class From stmd Where depart_class like '" +
				clazzFilter + "%'";
				List clazzList = jdbcDao.findAnyThing(sql);
				if(!clazzList.isEmpty()){
					for(Iterator cIter=clazzList.iterator(); cIter.hasNext();){
						String clazz = cIter.next().toString();
						if(Toolket.chkIsGraduateClass(clazz))
							clazzs = "," + clazzs;
					}
					if(!clazzs.equals("")){
						clazzs = "(" + clazzs.substring(1) + ")";					
					}else{
						return allDilg;
					}
					
					hql = "Select d, s From Dilg d, Student s";
					hql = hql + " Where d.departClass in " + clazzs;
					hql = hql + " And d.studentNo=s.studentNo";
					hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
					dsList = dao.submitQuery(hql);

				}else{
					return allDilg;
				}
				
			}else{
				return allDilg;
			}
		}else if(pmode.equals("0")){
			hql = "Select d, s From Dilg d, Student s";
			hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
			hql = hql + " And d.studentNo=s.studentNo";
			hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
			dsList = dao.submitQuery(hql);
		}
		
		//log.debug("hql = " + hql);
		//log.debug("result size = " + dsList.size());
		
		if(!dsList.isEmpty()){
			List<Code5> tfTypeList = Global.TimeOffList;
			int tflen = tfTypeList.size();
			
			Object[] ds0 = (Object[])dsList.get(0);
			Dilg prevDilg = (Dilg)ds0[0];
			Student prevstudent = (Student)ds0[1];
			String prevStudNo = prevDilg.getStudentNo();
			String prevClazz = prevDilg.getDepartClass();
			double total = 0d;
			List cntList = new ArrayList();
			//List<Dilg> myDilg = new ArrayList<Dilg>();
			
			Dilg dilg = new Dilg();
			String departClass = "";
			String studentName = "";
			int count = 0;
			int bstus = 0;
			Map dMap = new HashMap();
			int[] dsum = new int[tflen];
			double[] kind = new double[tflen];
			int[] abs = new int[tflen];
			double sumScore = 0d;
			
			for(int j=1; j<tflen; j++){
				dsum[j] = 0;
			}
				
			for(Iterator dsIter=dsList.iterator(); dsIter.hasNext();){
				Object[] dsObj = (Object[])dsIter.next();
				dilg = (Dilg)dsObj[0];
				
				for(int i=0; i<tflen; i++){
					kind[i]=0;
				}
				
				if(dilg.getDepartClass().equals(prevClazz)){
					if(!dilg.getStudentNo().equalsIgnoreCase(prevStudNo)){
						count++;
						//kind = this.calDilgSummary(myDilg, "0");
						//kind = this.calDilgOneSummary(prevStudNo, "1");
						
						for(int k = 1; k<tflen; k++){
							sql = "Select count(*) From Dilg_One Where student_no='" + prevStudNo + "'";
							sql += " And abs=" + k + " And no_exam=0";
							kind[k] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
						}


						
						if(kind[1]!=0d || kind[2]!=0d || kind[3]!=0d || kind[4]!=0d || kind[5]!=0d || kind[7]!=0d)
							bstus++;
						
						for(int j=1; j<tflen; j++){
							dsum[j] += kind[j];
						}
						
						//myDilg = new ArrayList<Dilg>();
						//myDilg.add(dilg);
						prevStudNo = dilg.getStudentNo();
					}else{
						//myDilg.add(dilg);
					}
													
				}else{
					//long runtime = (new Date()).getTime(); 
					//put data into result list
					
					//kind = this.calDilgOneSummary(prevStudNo, "1");
					for(int k = 1; k<tflen; k++){
						sql = "Select count(*) From Dilg_One Where student_no='" + prevStudNo + "'";
						sql += " And abs=" + k + " And no_exam=0";
						kind[k] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
					}

					if(kind[1]!=0d || kind[2]!=0d || kind[3]!=0d || kind[4]!=0d || kind[5]!=0d || kind[7]!=0d)
						bstus++;
					
					for(int j=1; j<tflen; j++){
						dsum[j] += kind[j];
					}
					
					//myDilg = new ArrayList<Dilg>();
					//myDilg.add(dilg);
					prevStudNo = dilg.getStudentNo();
						
					//log.debug("Toolket.getStudentByNo time:"+((new Date()).getTime()-runtime));
					sql = "select * from stmd Where depart_class='" + prevClazz + "'";
					cntList = jdbcDao.findAnyThing(sql);
					total = cntList.size();
					Map sdMap = new HashMap();
					sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
					sdMap.put("totalStu", ""+(int)total);
					sdMap.put("kind2", ""+dsum[2]);
					sdMap.put("kind2Avg", ""+(Math.round(dsum[2]/total*10d)/10d));
					sdMap.put("kind3", ""+dsum[3]);
					sdMap.put("kind3Avg", ""+(Math.round(dsum[3]/total*10d)/10d));
					sdMap.put("kind4", ""+dsum[4]);
					sdMap.put("kind4Avg", ""+(Math.round(dsum[4]/total*10d)/10d));
					sdMap.put("kind6", ""+dsum[6]);
					sdMap.put("kind6Avg", ""+(Math.round(dsum[6]/total*10d)/10d));
					sdMap.put("kind1", ""+dsum[1]);
					sdMap.put("kind1Avg", ""+(Math.round(dsum[1]/total*10d)/10d));
						
					sdMap.put("goodStuNo", ""+(int)(total-bstus));
					allDilg.add(sdMap);
					log.debug("allDilg:total:" + total + ", count=" + count + ", bstus=" + bstus);
					
					for(int j=1; j<tflen; j++){
						dsum[j] = 0;
					}
						
					prevClazz = dilg.getDepartClass();
					count = 0;
					bstus = 0;
						
				}
			}
			//處理最後一筆資料
			count++;
			//kind = this.calDilgSummary(myDilg, "0");
			//kind = this.calDilgOneSummary(prevStudNo, "1");
			for(int k = 1; k<tflen; k++){
				sql = "Select count(*) From Dilg_One Where student_no='" + prevStudNo + "'";
				sql += " And abs=" + k + " And no_exam=0";
				kind[k] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			}

			if(kind[1]!=0d || kind[2]!=0d || kind[3]!=0d || kind[4]!=0d || kind[5]!=0d || kind[7]!=0d)
				bstus++;
			
			for(int j=1; j<tflen; j++){
				dsum[j] += kind[j];
			}
			sql = "select * from stmd Where depart_class='" + prevClazz + "'";
			cntList = jdbcDao.findAnyThing(sql);
			total = cntList.size();
			Map sdMap = new HashMap();
			sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
			sdMap.put("totalStu", ""+(int)total);
			sdMap.put("kind2", ""+dsum[2]);
			sdMap.put("kind2Avg", ""+(Math.round(dsum[2]/total*10d)/10d));
			//log.debug("dsum" + dsum[2] + "," + dsum[2]/(double)total);
			sdMap.put("kind3", ""+dsum[3]);
			sdMap.put("kind3Avg", ""+(Math.round(dsum[3]/total*10d)/10d));
			sdMap.put("kind4", ""+dsum[4]);
			sdMap.put("kind4Avg", ""+(Math.round(dsum[4]/total*10d)/10d));
			sdMap.put("kind6", ""+dsum[6]);
			sdMap.put("kind6Avg", ""+(Math.round(dsum[6]/total*10d)/10d));
			sdMap.put("kind1", ""+dsum[1]);
			sdMap.put("kind1Avg", ""+(Math.round(dsum[1]/total*10d)/10d));
				
			sdMap.put("goodStuNo", ""+(int)(total-bstus));
			allDilg.add(sdMap);
			log.debug("allDilg:total:" + total + ", count=" + count + ", bstus=" + bstus);
			
		}
		log.debug(allDilg.size());
		return allDilg;

		
	}

	/*
	 * 給"本學期況缺人次統計表"用
	 * 因考慮扣考因素,這段程式暫時保留不用
	public List findDilg4SummaryT(String clazzFilter, String pmode){
		List allDilg = new ArrayList();
		List dsList = new ArrayList();
		String hql = "", sql="";
		String sterm = Toolket.getSysParameter("School_term");
		if(pmode.equals("1")){
			if(sterm.equals("2")){
				String clazzs = "";
				sql = "Select DISTINCT depart_class From stmd Where depart_class like '" +
				clazzFilter + "%'";
				List clazzList = jdbcDao.findAnyThing(sql);
				if(!clazzList.isEmpty()){
					for(Iterator cIter=clazzList.iterator(); cIter.hasNext();){
						String clazz = cIter.next().toString();
						if(Toolket.chkIsGraduateClass(clazz))
							clazzs = "," + clazzs;
					}
					if(!clazzs.equals("")){
						clazzs = "(" + clazzs.substring(1) + ")";					
					}else{
						return allDilg;
					}
					
					hql = "Select d, s From Dilg d, Student s";
					hql = hql + " Where d.departClass in " + clazzs;
					hql = hql + " And d.studentNo=s.studentNo";
					hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
					dsList = dao.submitQuery(hql);

				}else{
					return allDilg;
				}
				
			}else{
				return allDilg;
			}
		}else if(pmode.equals("0")){
			hql = "Select d, s From Dilg d, Student s";
			hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
			hql = hql + " And d.studentNo=s.studentNo";
			hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
			dsList = dao.submitQuery(hql);
		}
		
		log.debug("hql = " + hql);
		log.debug("result size = " + dsList.size());
		
		if(!dsList.isEmpty()){
			List<Code5> tfTypeList = Global.TimeOffList;
			int tflen = tfTypeList.size();
			
			Object[] ds0 = (Object[])dsList.get(0);
			Dilg prevDilg = (Dilg)ds0[0];
			Student prevstudent = (Student)ds0[1];
			String prevStudNo = prevDilg.getStudentNo();
			String prevClazz = prevDilg.getDepartClass();
			double total = 0d;
			List cntList = new ArrayList();
			
			Dilg dilg = new Dilg();
			String departClass = "";
			String studentName = "";
			int count = 0;
			int bstus = 0;
			Map dMap = new HashMap();
			int[] dsum = new int[tflen];
			int[] kind = new int[tflen];
			int[] abs = new int[tflen];
			double sumScore = 0d;
			
			for(int j=1; j<tflen; j++){
				dsum[j] = 0;
			}
				
			for(Iterator dsIter=dsList.iterator(); dsIter.hasNext();){
				Object[] dsObj = (Object[])dsIter.next();
				dilg = (Dilg)dsObj[0];
				
				for(int i=0; i<tflen; i++){
					kind[i]=0;
				}
				
				int pkind = 0, pindex = 0;

				if(dilg.getAbs0()!= null) kind[1]++;
				if(dilg.getAbs1()!= null) kind[dilg.getAbs1()]++;
				if(dilg.getAbs2()!= null) kind[dilg.getAbs2()]++;
				if(dilg.getAbs3()!= null) kind[dilg.getAbs3()]++;
				if(dilg.getAbs4()!= null) kind[dilg.getAbs4()]++;
				if(dilg.getAbs5()!= null) kind[dilg.getAbs5()]++;
				if(dilg.getAbs6()!= null) kind[dilg.getAbs6()]++;
				if(dilg.getAbs7()!= null) kind[dilg.getAbs7()]++;
				if(dilg.getAbs8()!= null) kind[dilg.getAbs8()]++;
				if(dilg.getAbs9()!= null) kind[dilg.getAbs9()]++;
				if(dilg.getAbs10()!= null) kind[dilg.getAbs10()]++;
				if(dilg.getAbs11()!= null) kind[dilg.getAbs11()]++;
				if(dilg.getAbs12()!= null) kind[dilg.getAbs12()]++;
				if(dilg.getAbs13()!= null) kind[dilg.getAbs13()]++;
				if(dilg.getAbs14()!= null) kind[dilg.getAbs14()]++;
				if(dilg.getAbs15()!= null) kind[dilg.getAbs15()]++;
				
								
				if(dilg.getDepartClass().equals(prevClazz)){
					for(int j=1; j<tflen; j++){
						dsum[j] += kind[j];
					}
					if(!dilg.getStudentNo().equals(prevStudNo)){
						count++;
						
						//log.debug(prevStudNo + ",1:" + abs[1] + ",2:" + abs[2]+ ",3:" + abs[3]+ ",4:" + abs[4] +
						//          ",5:" + abs[5]+ ",6:" + abs[6]+ ",7:" + abs[7]+ ",8:" + abs[8]+ ",9:" + abs[9]
						//          + ",bstu:" + bstus);
						
						if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0 || abs[5]!=0 || abs[7]!=0)
							bstus++;
						for(int i=1; i<tflen; i++){
							abs[i] = kind[i];
						}
						
						prevStudNo = dilg.getStudentNo();
					}else{
						for(int i=1; i<tflen; i++){
							abs[i] += kind[i];
						}
					}
				}else{
					//long runtime = (new Date()).getTime(); 
					//put data into result list
						
					//log.debug("Toolket.getStudentByNo time:"+((new Date()).getTime()-runtime));
					sql = "select * from stmd Where depart_class='" + prevClazz + "'";
					cntList = jdbcDao.findAnyThing(sql);
					total = cntList.size();
					if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0 || abs[5]!=0 || abs[7]!=0)
						bstus++;
					
					count++;
					Map sdMap = new HashMap();
					sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
					sdMap.put("totalStu", ""+(int)total);
					sdMap.put("kind2", ""+dsum[2]);
					sdMap.put("kind2Avg", ""+(Math.round(dsum[2]/total*10d)/10d));
					sdMap.put("kind3", ""+dsum[3]);
					sdMap.put("kind3Avg", ""+(Math.round(dsum[3]/total*10d)/10d));
					sdMap.put("kind4", ""+dsum[4]);
					sdMap.put("kind4Avg", ""+(Math.round(dsum[4]/total*10d)/10d));
					sdMap.put("kind6", ""+dsum[6]);
					sdMap.put("kind6Avg", ""+(Math.round(dsum[6]/total*10d)/10d));
					sdMap.put("kind1", ""+dsum[1]);
					sdMap.put("kind1Avg", ""+(Math.round(dsum[1]/total*10d)/10d));
						
					sdMap.put("goodStuNo", ""+(int)(total-bstus));
					allDilg.add(sdMap);
					log.debug("allDilg:total:" + total + ", count=" + count + ", bstus=" + bstus);
					for(int j=1; j<tflen; j++){
						dsum[j] = 0;
						abs[j] = 0;
						dsum[j] += kind[j];
						abs[j] += kind[j];
					}
						
					prevStudNo = dilg.getStudentNo();
					prevClazz = dilg.getDepartClass();
					count = 0;
					bstus = 0;
						
				}
			}
			sql = "select * from stmd Where depart_class='" + prevClazz + "'";
			cntList = jdbcDao.findAnyThing(sql);
			total = cntList.size();
			if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0 || abs[5]!=0 || abs[7]!=0)
				bstus++;
			Map sdMap = new HashMap();
			sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
			sdMap.put("totalStu", ""+(int)total);
			sdMap.put("kind2", ""+dsum[2]);
			sdMap.put("kind2Avg", ""+(Math.round(dsum[2]/total*10d)/10d));
			//log.debug("dsum" + dsum[2] + "," + dsum[2]/(double)total);
			sdMap.put("kind3", ""+dsum[3]);
			sdMap.put("kind3Avg", ""+(Math.round(dsum[3]/total*10d)/10d));
			sdMap.put("kind4", ""+dsum[4]);
			sdMap.put("kind4Avg", ""+(Math.round(dsum[4]/total*10d)/10d));
			sdMap.put("kind6", ""+dsum[6]);
			sdMap.put("kind6Avg", ""+(Math.round(dsum[6]/total*10d)/10d));
			sdMap.put("kind1", ""+dsum[1]);
			sdMap.put("kind1Avg", ""+(Math.round(dsum[1]/total*10d)/10d));
				
			sdMap.put("goodStuNo", ""+(int)(total-bstus));
			allDilg.add(sdMap);
			log.debug("allDilg:total:" + total + ", count=" + count + ", bstus=" + bstus);
			
		}
		log.debug(allDilg.size());
		return allDilg;

		
	}
	 */
	
	public List findDilgAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String pmode){
		//pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)

		List retList = new ArrayList();
		int th1=0,th2=0,th3=0;
		
		if(!threshold1.equals("")) th1 = Integer.parseInt(threshold1);
		if(!threshold2.equals("")) th2 = Integer.parseInt(threshold2);
		if(!threshold3.equals("")) th3 = Integer.parseInt(threshold3);
		Date sDate = new Date();
		Date eDate = new Date();
		Date dDate = new Date();
		
		Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		Calendar dDateCal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();

		sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		sDateCal.setTime(sDate);
		eDateCal.setTime(eDate);
		sDateCal.set(Calendar.MILLISECOND, 0);
		eDateCal.set(Calendar.MILLISECOND, 999);
		eDateCal.set(Calendar.HOUR_OF_DAY, 23);
		eDateCal.set(Calendar.MINUTE, 59);
		eDateCal.set(Calendar.SECOND, 59);
		
		String hql = "Select d From Dilg d Where departClass like '" + clazzFilter +"%'";
		hql = hql + " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " Order by departClass, studentNo, ddate";
		//log.debug("hql-->" + hql);
		//String hql = "Select d, s From Dilg d, Student s";
		//hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
		//hql = hql + " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		//hql = hql + " And d.studentNo=s.studentNo";
		//hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
		//log.debug("hql-->" + hql);
		
		
		List<Dilg> dilgList = dao.submitQuery(hql);
		//log.debug("dilgList size-->" + dilgList.size());
		Student student = new Student();
		
		if(!dilgList.isEmpty()){
			short[] abs = new short[16];
			for(int i=0; i<16; i++){
				abs[i]=0;
			}
			int[] kind = new int[9];
			for(int i=0; i<9; i++){
				kind[i]=0;
			}
			List wdList = new ArrayList();
			List<Dilg> myDilg = new ArrayList<Dilg>();
			
			String prevStu = dilgList.get(0).getStudentNo();
			int[] timeoff = new int[2];
			timeoff[0] = 0;	//升旗缺席次數
			timeoff[1] = 0;	//曠課次數,不含事病公喪等假
			
			int[] wtimeoff = new int[2];
			
			int serialNo = 1;
			boolean putData = false;

			int period = 0, prevperiod = 0;
			int raiseflag = 0, prevraise = 0;
			int threshold = 0;
			String mailLevel = "0";
			DilgMail dmail;
			String sql = "", sql_1 = "", sql_0 = "";
			
			for(ListIterator<Dilg> dIter = dilgList.listIterator(); dIter.hasNext();){
				Dilg dilg = dIter.next();
				wtimeoff[0] = 0;
				wtimeoff[1] = 0;
				
				if(dilg.getStudentNo().equals(prevStu)){
					wtimeoff = dilgSum(dilg);
					/*考慮扣考的因素,曠課總節數需另行計算,以下兩行程式碼廢除
					timeoff[0] += wtimeoff[0];
					timeoff[1] += wtimeoff[1];
					*/
					myDilg.add(dilg);
					dDate = dilg.getDdate();
					dDateCal.setTime(dDate);
					if(dDateCal.compareTo(sDateCal) >= 0  && dDateCal.compareTo(eDateCal) <= 0 &&
							(wtimeoff[0]>0 || wtimeoff[1]>0)){
						Map wdMap = new HashMap();
						wdMap.put("tfDate", Toolket.Date2Str(dDate));
						wdMap.put("raiseFlag", "" + wtimeoff[0]);
						wdMap.put("timeOff", "" + wtimeoff[1]);
						wdList.add(wdMap);
					}
					
				}else{
					//排除扣考之曠缺統計資料
					//timeoff = dilgSummary(myDilg, "0");
					sql ="Select count(*) From Dilg_One Where student_no='" + prevStu + "'";
					sql += " And abs=2 And no_exam=0";
					timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
					timeoff[0] = 0;
					
					log.debug("studentNo:" + prevStu + ", timeoff:" + timeoff[1]);
					hql = "Select m From DilgMail m Where studentNo='" + prevStu + "'";
					List<DilgMail> dmList = dao.submitQuery(hql);
					period = 0;
					prevperiod=0;
					prevraise = 0;
					raiseflag = 0;
					threshold = 0;
					mailLevel = "0";
					
					if(!dmList.isEmpty()){
						dmail = dmList.get(0);
						if(pmode.equals("0")){	//第一次列印
							if(dmail.getPeriod()!=null)
								period = dmail.getPeriod();
							if(dmail.getRaiseflag()!=null)
								raiseflag = dmail.getRaiseflag();							
							if(dmail.getPrevPeriod()!=null)
								prevperiod = dmail.getPrevPeriod();
							if(dmail.getPrevRaiseflag()!=null)
								prevraise = dmail.getPrevRaiseflag();							
						}else{
							if(dmail.getPrevPeriod()!=null){
								period = dmail.getPrevPeriod();
								prevperiod = period;
							}
							/*
							else if(dmail.getPeriod()!=null){
								period = dmail.getPeriod();
								prevperiod = period;
							}
							*/
							if(dmail.getPrevRaiseflag()!=null){
								raiseflag = dmail.getPrevRaiseflag();	
								prevraise = raiseflag;
							}
							/*
							else if(dmail.getRaiseflag()!=null){
								raiseflag = dmail.getRaiseflag();	
								prevraise = raiseflag;
							}
							*/
						}
					}
					
					student = Toolket.getStudentByNo(prevStu);
					if(period < 45 && raiseflag < 45 ){	//上一次曠缺少於45節之處理
						//List<Student> studList = jdbcDao.findStudentsByStudentNo(prevStu);
						if(student != null){
							//TODO
							/*
							if(student.getStudentNo().equals("95132023")){
								log.debug("timeOff:"+timeoff[1]+",period:"+period+",th1:"+th1+",th2:"+th2+",th3:"+th3);								
							}
							*/
							if((timeoff[0]>=45 && raiseflag<45) || (timeoff[1]>=45 && period<45)){
								//45節以上曠缺
								putData = true;
								threshold = 45;
								mailLevel = "1";
								
							}else if((timeoff[0]>=35 || timeoff[1]>=35)){
								if(raiseflag<35 && period<35){
									//第一次達到35節以上
									putData = true;
									threshold = 35;
									mailLevel = "1";
									
								}else if(timeoff[0]-raiseflag>=5 || timeoff[1]-period>=5){
									//35節以上,每增加5節,且未達45節
									putData = true;
									threshold = 40;
									mailLevel = "1";
									
								}
							}else if(th1>0 && ((timeoff[0]>=th1 && raiseflag<th1) ||
										(timeoff[1]>=th1 && period<th1))){
								putData = true;
								threshold = th1;
								
							}else if(th2>0 && ((timeoff[0]>=th2 && raiseflag<th2) ||
										(timeoff[1]>=th2 && period<th2))){
								putData = true;
								threshold = th2;
								
							}else if(th3>0 && ((timeoff[0]>=th3 && raiseflag<th3) ||
										(timeoff[1]>=th3 && period<th3))){
								putData = true;
								threshold = th3;
							}
							
							if(putData && !wdList.isEmpty()){
								//將該學生曠缺資料加入並記錄在DilgMail table中
								Map tfMap = new HashMap();
								tfMap.put("serialNo", "" + serialNo);
								tfMap.put("studentNo", prevStu);
								tfMap.put("studentName", student.getStudentName());
								tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
								if(student.getParentName()!= null)
									tfMap.put("parentName", student.getParentName());
								else
									tfMap.put("parentName", "");
								if(student.getCurrPost()!=null)
									tfMap.put("postNo", student.getCurrPost());
								else
									tfMap.put("postNo", "");
								if(student.getCurrAddr()!=null)
									tfMap.put("address", student.getCurrAddr());
								else
									tfMap.put("address", "");
								tfMap.put("raiseFlagSum", "" + timeoff[0]);
								tfMap.put("timeOffSum", "" + timeoff[1]);
								tfMap.put("wdilgList", wdList);
								tfMap.put("mailLevel", mailLevel);
								tfMap.put("threshold", "" + threshold);
								if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
								else tfMap.put("Memo", "平信");
								retList.add(tfMap);
								serialNo++;
								
								//新增或更新DilgMail-->(改在列印時才做)
								/*if(pmode.equals("0")){
									dmail.setPeriod(timeoff[1]);
									dmail.setPrevPeriod(period);
									dmail.setPrevRaiseflag(raiseflag);
									dmail.setRaiseflag(timeoff[0]);
									dmail.setReportDate(reportDate);
									dmail.setStudentNo(prevStu);
									dmail.setThreshold(threshold);
								}
								*/
							}
						}
						
					}else{	//之前寄出資料已經達到退學標準45節,每增加10節再寄一次
						if(student != null){
							if((timeoff[0]-raiseflag>=10) || (timeoff[1]-period>=10)){
								if(!wdList.isEmpty()){
									putData = true;
									threshold = 45;
									mailLevel = "1";
									Map tfMap = new HashMap();
									tfMap.put("serialNo", "" + serialNo);
									tfMap.put("studentNo", prevStu);
									tfMap.put("studentName", student.getStudentName());
									tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
									if(student.getParentName()!= null)
										tfMap.put("parentName", student.getParentName());
									else
										tfMap.put("parentName", "");
									if(student.getCurrPost()!=null)
										tfMap.put("postNo", student.getCurrPost());
									else
										tfMap.put("postNo", "");
									if(student.getCurrAddr()!=null)
										tfMap.put("address", student.getCurrAddr());
									else
										tfMap.put("address", "");
									tfMap.put("raiseFlagSum", "" + timeoff[0]);
									tfMap.put("timeOffSum", "" + timeoff[1]);
									tfMap.put("wdilgList", wdList);
									tfMap.put("mailLevel", mailLevel);
									tfMap.put("threshold", "" + threshold);
									if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
									else tfMap.put("Memo", "平信");
									retList.add(tfMap);
									serialNo++;
									
								}
							}
						}
					}
					
					prevStu = dilg.getStudentNo();
					wdList = new ArrayList();
					myDilg = new ArrayList<Dilg>();
					myDilg.add(dilg);
					timeoff[0] = 0;
					timeoff[1] = 0;
					putData = false;
					wtimeoff = dilgSum(dilg);
					timeoff[0] += wtimeoff[0];
					timeoff[1] += wtimeoff[1];
					
					dDate = dilg.getDdate();
					dDateCal.setTime(dDate);
					if(dDateCal.compareTo(sDateCal) >= 0  && dDateCal.compareTo(eDateCal) <= 0 &&
							(wtimeoff[0]>0 || wtimeoff[1]>0)){
						Map wdMap = new HashMap();
						wdMap.put("tfDate", Toolket.Date2Str(dDate));
						wdMap.put("raiseFlag", "" + wtimeoff[0]);
						wdMap.put("timeOff", "" + wtimeoff[1]);
						wdList.add(wdMap);
					}
				}					
			}	//End of for
			
			//插入最後一筆資料
			//timeoff = dilgSummary(myDilg, "0");
			sql ="Select count(*) From Dilg_One Where student_no='" + prevStu + "'";
			sql += " And abs=2 And no_exam=0";
			timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			timeoff[0] = 0;

			hql = "Select m From DilgMail m Where studentNo='" + prevStu + "'";
			List<DilgMail> dmList = dao.submitQuery(hql);
			period = 0;
			raiseflag = 0;
			threshold = 0;
			mailLevel = "0";
			
			if(!dmList.isEmpty()){
				dmail = dmList.get(0);
				if(pmode.equals("0")){
					if(dmail.getPeriod()!=null)
						period = dmail.getPeriod();
					if(dmail.getRaiseflag()!=null)
						raiseflag = dmail.getRaiseflag();							
				}else{
					if(dmail.getPrevPeriod()!=null)
						period = dmail.getPrevPeriod();
					if(dmail.getPrevRaiseflag()!=null)
						raiseflag = dmail.getPrevRaiseflag();														
				}
			}
			
			student = Toolket.getStudentByNo(prevStu);
			if(period < 45 && raiseflag < 45){	//曠缺少於45節之處理
				//List<Student> studList = jdbcDao.findStudentsByStudentNo(prevStu);
				if(student != null){
					//TODO
					if((timeoff[0]>=45 && raiseflag<45) || (timeoff[1]>=45 && period<45)){
						//45節以上曠缺
						putData = true;
						threshold = 45;
						mailLevel = "1";
						
					}else if((timeoff[0]>=35 || timeoff[1]>=35)){
						if(raiseflag<35 && period<35){
							//第一次達到35節以上
							putData = true;
							threshold = 35;
							mailLevel = "1";
							
						}else if(timeoff[0]-raiseflag>=5 || timeoff[1]-period>=5){
							//35節以上,每增加5節,且未達45節
							putData = true;
							threshold = 40;
							mailLevel = "1";
							
						}
					}else if(th1>0 && ((timeoff[0]>=th1 && raiseflag<th1) ||
								(timeoff[1]>=th1 && period<th1))){
						putData = true;
						threshold = th1;
						
					}else if(th2>0 && ((timeoff[0]>=th2 && raiseflag<th2) ||
								(timeoff[1]>=th2 && period<th2))){
						putData = true;
						threshold = th2;
						
					}else if(th3>0 && ((timeoff[0]>=th3 && raiseflag<th3) ||
								(timeoff[1]>=th3 && period<th3))){
						putData = true;
						threshold = th3;
					}
										
					if(putData && !wdList.isEmpty()){
						//將該學生曠缺資料加入
						Map tfMap = new HashMap();
						tfMap.put("serialNo", "" + serialNo);
						tfMap.put("studentNo", prevStu);
						tfMap.put("studentName", student.getStudentName());
						tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
						if(student.getParentName()!= null)
							tfMap.put("parentName", student.getParentName());
						else
							tfMap.put("parentName", "");
						if(student.getCurrPost()!=null)
							tfMap.put("postNo", student.getCurrPost());
						else
							tfMap.put("postNo", "");
						if(student.getCurrAddr()!=null)
							tfMap.put("address", student.getCurrAddr());
						else
							tfMap.put("address", "");
						tfMap.put("raiseFlagSum", "" + timeoff[0]);
						tfMap.put("timeOffSum", "" + timeoff[1]);
						tfMap.put("wdilgList", wdList);
						tfMap.put("mailLevel", mailLevel);
						tfMap.put("threshold", "" + threshold);
						if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
						else tfMap.put("Memo", "平信");
						retList.add(tfMap);
						serialNo++;
					}
				}
				
			}else{	//之前寄出資料已經達到退學標準45節,每增加10節再寄一次
				if(student != null){
					if((timeoff[0]-raiseflag>=10) || (timeoff[1]-period>=10)){
						if(!wdList.isEmpty()){
							putData = true;
							threshold = 45;
							mailLevel = "1";
							Map tfMap = new HashMap();
							tfMap.put("serialNo", "" + serialNo);
							tfMap.put("studentNo", prevStu);
							tfMap.put("studentName", student.getStudentName());
							tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
							if(student.getParentName()!= null)
								tfMap.put("parentName", student.getParentName());
							else
								tfMap.put("parentName", "");
							if(student.getCurrPost()!=null)
								tfMap.put("postNo", student.getCurrPost());
							else
								tfMap.put("postNo", "");
							if(student.getCurrAddr()!=null)
								tfMap.put("address", student.getCurrAddr());
							else
								tfMap.put("address", "");
							tfMap.put("raiseFlagSum", "" + timeoff[0]);
							tfMap.put("timeOffSum", "" + timeoff[1]);
							tfMap.put("wdilgList", wdList);
							tfMap.put("mailLevel", mailLevel);
							tfMap.put("threshold", "" + threshold);
							if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
							else tfMap.put("Memo", "平信");
							retList.add(tfMap);
							serialNo++;
							
						}
					}
				}
			}
			
		}
		
		return retList;
	}
	
	
	public List findDilgAlert4T(String DateStart, String DateEnd, String clazzFilter,
			String pmode){
		List retList = new ArrayList();
		
		Date sDate = new Date();
		Date eDate = new Date();
		Date dDate = new Date();
		
		Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		Calendar dDateCal = Calendar.getInstance();
		
		sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		sDateCal.setTime(sDate);
		eDateCal.setTime(eDate);
		sDateCal.set(Calendar.MILLISECOND, 0);
		eDateCal.set(Calendar.MILLISECOND, 999);
		eDateCal.set(Calendar.HOUR_OF_DAY, 23);
		eDateCal.set(Calendar.MINUTE, 59);
		eDateCal.set(Calendar.SECOND, 59);
		
		/*
		String hql = "Select d From Dilg d Where departClass like '" + clazzFilter +"%'";
		hql = hql + " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " Order by departClass, studentNo, ddate";
		*/
		
		String hql = "Select d, s From Dilg d, Student s";
		hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
		hql = hql + " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " And d.studentNo=s.studentNo";
		hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
		//log.debug("hql-->" + hql);
		
		List dsList = dao.submitQuery(hql);
		//log.debug("dilgList size-->" + dilgList.size());
		if(!dsList.isEmpty()){
			short[] abs = new short[16];
			for(int i=0; i<16; i++){
				abs[i]=0;
			}
			Object[] obj = new Object[2];
			obj = (Object[])dsList.get(0);
			Dilg d0 = (Dilg)obj[0];
			Student s0 = (Student)obj[1];
			
			Student prevStu = s0;
			String excStu = "";
			String stuNo = "";
			int[] timeoff = new int[2];
			timeoff[0] = 0;	//升旗缺席次數
			timeoff[1] = 0;	//曠課次數,不含事病公喪等假
			List<DilgMail> dmList = new ArrayList<DilgMail>();
			List<Dilg> myDilg = new ArrayList<Dilg>();
			
			int[] wtimeoff = new int[2];
			int[] atimeoff = new int[2];
			int catTimeoff = 0;
			boolean putData = false;
			String sql = "";
			
			for(ListIterator dIter = dsList.listIterator(); dIter.hasNext();){
				obj = (Object[])dIter.next();
				Dilg dilg = (Dilg)obj[0];
				Student student = (Student)obj[1];
				stuNo = dilg.getStudentNo();
				
				atimeoff[0] = 0;
				atimeoff[1] = 0;
						
				if(stuNo.equals(prevStu.getStudentNo())){
					atimeoff = dilgSum(dilg);
					/*考慮扣考的因素,曠課總節數需另行計算,以下兩行程式碼廢除
					timeoff[0] += atimeoff[0];
					timeoff[1] += atimeoff[1];
					*/
					myDilg.add(dilg);
							
					dDate = dilg.getDdate();
					dDateCal.setTime(dDate);
					if(dDateCal.compareTo(sDateCal) >= 0  && dDateCal.compareTo(eDateCal) <= 0 &&
							(atimeoff[0]>0 || atimeoff[1]>0)){
						wtimeoff[0] += atimeoff[0];
						wtimeoff[1] += atimeoff[1];								
					}
							
							
				}else{
					//排除扣考之曠缺統計資料
					//timeoff = dilgSummary(myDilg, "0");
					sql ="Select count(*) From Dilg_One Where student_no='" + prevStu.getStudentNo() + "'";
					sql += " And abs=2 And no_exam=0";
					timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
					timeoff[0] = 0;

					hql = "Select m From DilgMail m Where studentNo='" + prevStu.getStudentNo() + "'";
					dmList = dao.submitQuery(hql);
					if(!dmList.isEmpty()){
						if(pmode.equals("0")){
							if(dmList.get(0).getTutorContact() != null){
								catTimeoff = dmList.get(0).getTutorContact();
							}							
						}else{
							if(dmList.get(0).getPrevTutor()!= null){
								catTimeoff = dmList.get(0).getPrevTutor();
							}							
						}
					}else{
						catTimeoff = 0;
					}
					
					if(timeoff[1]>29 && student.getStudentNo().substring(0, 4).equals("9314")){
						log.debug(student.getStudentNo() +":timeOff:"+timeoff[1]+",wtimeOff:"+wtimeoff[1]+",catTimeoff:"+catTimeoff);								
					}
		
					//排除已經曠缺超過30節且通知過的學生記錄
					if(((timeoff[0]-wtimeoff[0])<30 && timeoff[0]>29 && catTimeoff<30) ||
						((timeoff[0]-wtimeoff[0])>29 && wtimeoff[0]>=5 && catTimeoff<timeoff[0]) ||
						(((timeoff[1]-wtimeoff[1])<30||catTimeoff==0) && timeoff[1]>29 && catTimeoff<30) ||
						((timeoff[1]-wtimeoff[1])>29 && wtimeoff[1]>=5 && catTimeoff<timeoff[1])){
						//將該學生曠缺資料加入並記錄在DilgMail table中
						Map tfMap = new HashMap();
						tfMap.put("studentNo", prevStu.getStudentNo());
						tfMap.put("studentName", prevStu.getStudentName());
						tfMap.put("deptClassName", Toolket.getClassFullName(prevStu.getDepartClass()));
						if(prevStu.getParentName()!= null)
							tfMap.put("parentName", prevStu.getParentName());
						else
							tfMap.put("parentName", "");
						if(prevStu.getTelephone()!=null)
							tfMap.put("TEL", prevStu.getTelephone());
						else
							tfMap.put("TEL", "");
						tfMap.put("raiseFlagSum", "" + timeoff[0]);
						tfMap.put("timeOffSum", "" + timeoff[1]);
						retList.add(tfMap);
					}
					prevStu = student;
					timeoff[0] = 0;
					timeoff[1] = 0;
					wtimeoff[0] = 0;
					wtimeoff[1] = 0;								
							
					atimeoff = dilgSum(dilg);
					//timeoff[0] += atimeoff[0];
					//timeoff[1] += atimeoff[1];
								
					myDilg = new ArrayList<Dilg>();
					myDilg.add(dilg);

					dDate = dilg.getDdate();
					dDateCal.setTime(dDate);
					if(dDateCal.compareTo(sDateCal) >= 0  && dDateCal.compareTo(eDateCal) <= 0 &&
						(atimeoff[0]>0 || atimeoff[1]>0)){
						wtimeoff[0] += atimeoff[0];
						wtimeoff[1] += atimeoff[1];								
					}
				}
			}	//End of for
			
			//插入最後一筆資料
			//timeoff = dilgSummary(myDilg, "0");
			sql ="Select count(*) From Dilg_One Where student_no='" + prevStu.getStudentNo() + "'";
			sql += " And abs=2 And no_exam=0";
			timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			timeoff[0] = 0;

			hql = "Select m From DilgMail m Where studentNo='" + prevStu.getStudentNo() + "'";
			dmList = dao.submitQuery(hql);
			if(!dmList.isEmpty()){
				if(dmList.get(0).getTutorContact() != null){
					catTimeoff = dmList.get(0).getTutorContact();
				}
			}
					
			if(((timeoff[0]-wtimeoff[0])<30 && timeoff[0]>29 && catTimeoff<30) ||
					((timeoff[0]-wtimeoff[0])>29 && wtimeoff[0]>=5 && catTimeoff<timeoff[0]) ||
					((timeoff[1]-wtimeoff[1])<30 && timeoff[1]>29 && catTimeoff<30) ||
					((timeoff[1]-wtimeoff[1])>29 && wtimeoff[1]>=5 && catTimeoff<timeoff[1])){
				//將該學生曠缺資料加入並記錄在DilgMail table中
				Map tfMap = new HashMap();
				tfMap.put("studentNo", prevStu.getStudentNo());
				tfMap.put("studentName", prevStu.getStudentName());
				tfMap.put("deptClassName", Toolket.getClassFullName(prevStu.getDepartClass()));
				if(prevStu.getParentName()!= null)
					tfMap.put("parentName", prevStu.getParentName());
				else
					tfMap.put("parentName", "");
				if(prevStu.getTelephone()!=null)
					tfMap.put("TEL", prevStu.getTelephone());
				else
					tfMap.put("TEL", "");
				tfMap.put("raiseFlagSum", "" + timeoff[0]);
				tfMap.put("timeOffSum", "" + timeoff[1]);
				retList.add(tfMap);
			}
			
		}
		
		return retList;
	}

	
	public ActionMessages createOrModifyDilgMail(List dmList){
		ActionMessages msgs = new ActionMessages();
		Date now = new Date();
		
		String hql = "";
		try{
		for(Iterator dmIter=dmList.iterator(); dmIter.hasNext();){
			Map dmMap = (Map)dmIter.next();
			hql = "From DilgMail d Where studentNo='" + dmMap.get("studentNo").toString() + "'";
			List<DilgMail> dmrList = dao.submitQuery(hql);
			if(dmrList.isEmpty()){
				DilgMail dmail = new DilgMail();
				dmail.setStudentNo(dmMap.get("studentNo").toString());
				if(dmMap.get("period")!=null)
					dmail.setPeriod(Integer.parseInt(dmMap.get("period").toString()));
				if(dmMap.get("raiseflag")!=null)
					dmail.setRaiseflag(Integer.parseInt(dmMap.get("raiseflag").toString()));
				if(dmMap.get("threshold")!=null)
					dmail.setThreshold(Integer.parseInt(dmMap.get("threshold").toString()));
				if(dmMap.get("tutorContact")!=null)
					dmail.setTutorContact(Integer.parseInt(dmMap.get("tutorContact").toString()));
					
					dmail.setReportDate(now);
				dao.saveObject(dmail);
			}else{
				DilgMail dmail = dmrList.get(0);
				if(dmail.getPeriod()!=null)
					dmail.setPrevPeriod(dmail.getPeriod());
				
				if(dmMap.get("raiseflag")!=null)
					dmail.setPrevRaiseflag(dmail.getRaiseflag());
				if(dmMap.get("period")!=null)
					dmail.setPeriod(Integer.parseInt(dmMap.get("period").toString()));
				if(dmMap.get("raiseflag")!=null)
					dmail.setRaiseflag(Integer.parseInt(dmMap.get("raiseflag").toString()));
				if(dmMap.get("threshold")!=null)
					dmail.setThreshold(Integer.parseInt(dmMap.get("threshold").toString()));
				
				if(dmail.getTutorContact()!=null)
					dmail.setPrevTutor(dmail.getTutorContact());
				if(dmMap.get("tutorContact")!=null)
					dmail.setTutorContact(Integer.parseInt(dmMap.get("tutorContact").toString()));
				
				dmail.setReportDate(now);
				dao.saveObject(dmail);
			}
		}
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage(e.toString()));
		}
		return msgs;
	}

	public List findTimeOffSummaryExt(String clazzFilter, String DateEnd, 
			String period, String qscope){
		List retList = new ArrayList();
		int iperiod = Integer.parseInt(period);
				
		//Date sDate = new Date();
		Date eDate = new Date();
		//Date dDate = new Date();
		
		//Calendar sDateCal = Calendar.getInstance();
		//Calendar eDateCal = Calendar.getInstance();
		//Calendar dDateCal = Calendar.getInstance();
		
		//sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		//sDateCal.setTime(sDate);
		//eDateCal.setTime(eDate);
		
		List allDilg = new ArrayList();
		List dsList = new ArrayList();
		String hql = "", sql="";
		String sterm = Toolket.getSysParameter("School_term");
		if(qscope.equals("1")){
			if(sterm.equals("2")){
				String clazzs = "";
				sql = "Select DISTINCT depart_class From stmd Where depart_class like '" +
				clazzFilter + "%'";
				List clazzList = jdbcDao.findAnyThing(sql);
				if(!clazzList.isEmpty()){
					for(Iterator cIter=clazzList.iterator(); cIter.hasNext();){
						String clazz = cIter.next().toString();
						if(Toolket.chkIsGraduateClass(clazz))
							clazzs = "," + clazzs;
					}
					if(!clazzs.equals("")){
						clazzs = "(" + clazzs.substring(1) + ")";					
					}else{
						return allDilg;
					}
					
					hql = "Select d, s From Dilg d, Student s";
					hql = hql + " Where d.departClass in " + clazzs;
					hql = hql + " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
					hql = hql + " And d.studentNo=s.studentNo";
					hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
					dsList = dao.submitQuery(hql);

				}else{
					return allDilg;
				}
				
			}else{
				return allDilg;
			}
		}else if(qscope.equals("0")){
			hql = "Select d, s From Dilg d, Student s";
			hql = hql + " Where d.departClass like '" + clazzFilter + "%'";
			hql = hql + " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
			hql = hql + " And d.studentNo=s.studentNo";
			hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
			dsList = dao.submitQuery(hql);
		}
		
		log.debug("hql = " + hql);
		log.debug("result size = " + dsList.size());
		
		if(!dsList.isEmpty()){
			List<Code5> tfTypeList = Global.TimeOffList;
			int tflen = tfTypeList.size();
			
			Object[] ds0 = (Object[])dsList.get(0);
			Dilg prevDilg = (Dilg)ds0[0];
			Student prevstudent = (Student)ds0[1];
			String prevStudNo = prevDilg.getStudentNo();
			String prevClazz = prevDilg.getDepartClass();
			double total = 0d;
			List cntList = new ArrayList();
			
			Dilg dilg = new Dilg();
			String departClass = "";
			String studentName = "";
			int count = 0;
			int bstus = 0;
			Map dMap = new HashMap();
			int[] dsum = new int[tflen];
			int[] kind = new int[tflen];
			int[] abs = new int[tflen];
			double sumScore = 0d;
			
			for(int j=1; j<tflen; j++){
				dsum[j] = 0;
			}
				
			for(Iterator dsIter=dsList.iterator(); dsIter.hasNext();){
				Object[] dsObj = (Object[])dsIter.next();
				dilg = (Dilg)dsObj[0];
				
				for(int i=0; i<tflen; i++){
					kind[i]=0;
				}
				
				int pkind = 0, pindex = 0;

				if(dilg.getAbs0()!= null) kind[1]++;
				if(dilg.getAbs1()!= null) kind[dilg.getAbs1()]++;
				if(dilg.getAbs2()!= null) kind[dilg.getAbs2()]++;
				if(dilg.getAbs3()!= null) kind[dilg.getAbs3()]++;
				if(dilg.getAbs4()!= null) kind[dilg.getAbs4()]++;
				if(dilg.getAbs5()!= null) kind[dilg.getAbs5()]++;
				if(dilg.getAbs6()!= null) kind[dilg.getAbs6()]++;
				if(dilg.getAbs7()!= null) kind[dilg.getAbs7()]++;
				if(dilg.getAbs8()!= null) kind[dilg.getAbs8()]++;
				if(dilg.getAbs9()!= null) kind[dilg.getAbs9()]++;
				if(dilg.getAbs10()!= null) kind[dilg.getAbs10()]++;
				if(dilg.getAbs11()!= null) kind[dilg.getAbs11()]++;
				if(dilg.getAbs12()!= null) kind[dilg.getAbs12()]++;
				if(dilg.getAbs13()!= null) kind[dilg.getAbs13()]++;
				if(dilg.getAbs14()!= null) kind[dilg.getAbs14()]++;
				if(dilg.getAbs15()!= null) kind[dilg.getAbs15()]++;
				
								
				if(dilg.getDepartClass().equals(prevClazz)){
					for(int j=1; j<tflen; j++){
						dsum[j] += kind[j];
					}
					if(!dilg.getStudentNo().equals(prevStudNo)){
						count++;
						if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0 
								|| abs[5]!=0 || abs[7]!=0 || abs[8]!=0 || abs[9]!=0)
							bstus++;
						for(int i=1; i<tflen; i++){
							abs[i] = kind[i];
						}
						prevStudNo = dilg.getStudentNo();
					}else{
						for(int i=1; i<tflen; i++){
							abs[i] += kind[i];
						}
					}
				}else{
					//long runtime = (new Date()).getTime(); 
					//put data into result list
						
					//log.debug("Toolket.getStudentByNo time:"+((new Date()).getTime()-runtime));
					sql = "select * from stmd Where depart_class='" + prevClazz + "'";
					cntList = jdbcDao.findAnyThing(sql);
					total = cntList.size();
					if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0
							|| abs[5]!=0 || abs[7]!=0 || abs[8]!=0 || abs[9]!=0)
						bstus++;

					Map sdMap = new HashMap();
					sdMap.put("departClass", prevClazz);
					sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
					sdMap.put("totalStu", ""+(int)total);
					for(int i=1; i<tflen; i++){
						sdMap.put("kind"+i, ""+dsum[i]);
						sdMap.put("kind"+ i + "Avg", ""+(Math.round(dsum[i]/total*10d)/10d));						
					}
						
					sdMap.put("goodStuNo", ""+(int)(total-bstus));
					allDilg.add(sdMap);
					//log.debug("allDilg:" + student + ", stuDilg size=" + stuDilg.size());
					for(int j=1; j<tflen; j++){
						dsum[j] = 0;
						abs[j] = 0;
						dsum[j] += kind[j];
						abs[j] += kind[j];
					}
						
					prevStudNo = dilg.getStudentNo();
					prevClazz = dilg.getDepartClass();
					count = 0;
					bstus = 0;
						
				}
			}
			
			sql = "select * from stmd Where depart_class='" + prevClazz + "'";
			cntList = jdbcDao.findAnyThing(sql);
			total = cntList.size();
			if(abs[1]!=0 || abs[2]!=0 || abs[3]!=0 || abs[4]!=0
					|| abs[5]!=0 || abs[7]!=0 || abs[8]!=0 || abs[9]!=0)
				bstus++;
			Map sdMap = new HashMap();
			sdMap.put("departClass", prevClazz);
			sdMap.put("deptClassName", Toolket.getClassFullName(prevClazz));
			sdMap.put("totalStu", ""+(int)total);
			for(int i=1; i<tflen; i++){
				sdMap.put("kind"+i, ""+dsum[i]);
				sdMap.put("kind"+ i + "Avg", ""+(Math.round(dsum[i]/total*10d)/10d));						
			}
				
			sdMap.put("goodStuNo", ""+(int)(total-bstus));
			allDilg.add(sdMap);
			
		}
		log.debug(allDilg.size());
		return allDilg;

	}
	
	public List findTimeOffSerious(String departClass, String DateStart, String DateEnd, 
			String period, String qscope){
		List retList = new ArrayList();
		int iperiod = Integer.parseInt(period);
		log.debug("departClass:" + departClass);
		if(qscope.equals("1") && !Toolket.chkIsGraduateClass(departClass)){
			return retList;
		}
		
		//Date sDate = new Date();
		Date eDate = new Date();
		//Date dDate = new Date();
		
		//Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		//Calendar dDateCal = Calendar.getInstance();
		
		//sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		//sDateCal.setTime(sDate);
		eDateCal.setTime(eDate);
		
		/*
		String hql = "Select d From Dilg d Where departClass like '" + clazzFilter +"%'";
		hql = hql + " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " Order by departClass, studentNo, ddate";
		*/
		
		String hql = "Select d, s From Dilg d, Student s";
		hql = hql + " Where d.departClass like '" + departClass + "%'";
		//hql = hql + " And d.ddate >= '" + Toolket.FullDate2Str(sDate) +"'";
		hql = hql + " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " And d.studentNo=s.studentNo";
		hql = hql + " Order by d.departClass,d.studentNo,d.ddate";
		//log.debug("hql-->" + hql);
		
		List dsList = dao.submitQuery(hql);
		log.debug("dilgList size-->" + dsList.size());
		if(!dsList.isEmpty()){
			Object[] obj = new Object[2];
			obj = (Object[])dsList.get(0);
			Dilg d0 = (Dilg)obj[0];
			Student s0 = (Student)obj[1];
			
			Student prevStu = s0;
			String excStu = "";
			String stuNo = "";
			int[] timeoff = new int[2];
			timeoff[0] = 0;	//升旗缺席次數
			timeoff[1] = 0;	//曠課次數,不含事病公喪等假
			List<Dilg> myDilg = new ArrayList<Dilg>();
			
			int[] atimeoff = new int[2];
			int catTimeoff = 0;
			boolean putData = false;
			String sql = "";
			
			for(ListIterator dIter = dsList.listIterator(); dIter.hasNext();){
				obj = (Object[])dIter.next();
				Dilg dilg = (Dilg)obj[0];
				Student student = (Student)obj[1];
				stuNo = dilg.getStudentNo();
						
				if(stuNo.equals(prevStu.getStudentNo())){
					myDilg.add(dilg);
				}else{							
					//處理曠缺超過period節的學生記錄
					//timeoff = this.dilgSummary(myDilg, "0");
					sql ="Select count(*) From Dilg_One Where student_no='" + prevStu.getStudentNo() + "'";
					sql += " And abs=2 And no_exam=0";
					timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
					timeoff[0] = 0;
					if(timeoff[0]>= iperiod || timeoff[1]>=iperiod ){
						//將該學生曠缺資料加入並記錄在DilgMail table中
						Map tfMap = new HashMap();
						tfMap.put("deptClassName", Toolket.getClassFullName(prevStu.getDepartClass()));
						tfMap.put("studentNo", prevStu.getStudentNo());
						tfMap.put("studentName", prevStu.getStudentName());
						if(prevStu.getParentName()!= null)
							if(!prevStu.getParentName().trim().equals(""))
								tfMap.put("parentName", prevStu.getParentName());
							else
								tfMap.put("parentName", "N/A");
						else
							tfMap.put("parentName", "N/A");
						if(prevStu.getTelephone()!=null)
							if(!prevStu.getTelephone().trim().equals(""))
								tfMap.put("TEL", prevStu.getTelephone());
							else
								tfMap.put("TEL", "N/A");
						else
							tfMap.put("TEL", "N/A");
						tfMap.put("raiseFlag", "" + timeoff[0]);
						tfMap.put("period", "" + timeoff[1]);
						retList.add(tfMap);
					}
					myDilg = new ArrayList<Dilg>();
					myDilg.add(dilg);
					prevStu = student;
				}
				
			}	//End of for
			
			//插入最後一筆資料
			//timeoff = this.dilgSummary(myDilg, "0");
			sql ="Select count(*) From Dilg_One Where student_no='" + prevStu.getStudentNo() + "'";
			sql += " And abs=2 And no_exam=0";
			timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			timeoff[0] = 0;

			if(timeoff[0]>= iperiod || timeoff[1]>=iperiod ){
				Map tfMap = new HashMap();
				tfMap.put("deptClassName", Toolket.getClassFullName(prevStu.getDepartClass()));
				tfMap.put("studentNo", prevStu.getStudentNo());
				tfMap.put("studentName", prevStu.getStudentName());
				if(prevStu.getParentName()!= null)
					if(!prevStu.getParentName().trim().equals(""))
						tfMap.put("parentName", prevStu.getParentName());
					else
						tfMap.put("parentName", "N/A");
				else
					tfMap.put("parentName", "N/A");
				if(prevStu.getTelephone()!=null)
					if(!prevStu.getTelephone().trim().equals(""))
						tfMap.put("TEL", prevStu.getTelephone());
					else
						tfMap.put("TEL", "N/A");
				else
					tfMap.put("TEL", "N/A");
				tfMap.put("raiseFlag", "" + timeoff[0]);
				tfMap.put("period", "" + timeoff[1]);
				retList.add(tfMap);
			}
			
		}
		
		return retList;		
	}
	
	public List findNoTimeOffStudents(String clazzFilter){
		List retList = new ArrayList();
		Object[] obj = new Object[2];
		String stuNo;
		String hql = "Select j, s From Just j, Student s Where j.departClass like '" +
					clazzFilter + "%' and j.studentNo=s.studentNo and j.dilgScore=3" +
					" order by j.departClass, j.studentNo";
		List justList = dao.submitQuery(hql);
		for(Iterator justIter = justList.iterator(); justIter.hasNext();){
			obj = (Object[])justIter.next();
			Just just = (Just)obj[0];
			Student student = (Student)obj[1];
			stuNo = just.getStudentNo();
			
			Map tfMap = new HashMap();
			tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
			tfMap.put("student", stuNo + " " +student.getStudentName());
			tfMap.put("studentNo", stuNo);
			tfMap.put("studentName", student.getStudentName());
			retList.add(tfMap);
		}
		return retList;
	}
	
	public Map<String, List> findJustThisTerm(String clazzFilter, String cmode, String totalint) throws Exception{
		Map<String, List> retMap = new HashMap();
		List retList = new ArrayList();
		List sumList = new ArrayList();
		String prevClass = "";
		String comments = "";
		String memo = "";
		String stdClass = "";
		String csClass = "";
		
		String hql = "Select j, s From Just j, Student s Where j.departClass like '" + clazzFilter + "%'";
		hql = hql + " and j.studentNo=s.studentNo";
		hql = hql + " order by j.departClass, j.studentNo";
		log.debug("hql:"+hql);
		List rList = dao.submitQuery(hql);
		//log.debug("size:"+rList.size());
		if(!rList.isEmpty()){
			Object[] objs = new Object[2];
		
			List<Keep> keepList = new ArrayList();
			Map sumMap = new HashMap();
			Just just = new Just();
			Student stud = new Student();
			int chFlag = 0;
			double justScore = 0d;
			int excellent=0, levelA=0, levelB=0, levelC=0, levelD=0;
			objs = (Object[])rList.get(0);
			
			prevClass = ((Just)objs[0]).getDepartClass();
			
			for(Iterator rIter = rList.iterator(); rIter.hasNext();){
				objs = (Object[])rIter.next();
				just = (Just)objs[0];
				stud = (Student)objs[1];
				
				Map justMap = new HashMap();
				justMap.put("clazz", Toolket.getClassFullName(just.getDepartClass()));
				justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
				if(just.getTeacherScore() != null){
					justMap.put("teacherScore", just.getTeacherScore().toString());
				}else{
					justMap.put("teacherScore", "");
				}
				if(just.getDeptheaderScore() != null){
					justMap.put("deptheaderScore", just.getDeptheaderScore().toString());
				}else{
					justMap.put("deptheaderScore", "");
				}
				if(just.getMilitaryScore() != null){
					justMap.put("militaryScore", just.getMilitaryScore().toString());
				}else{
					justMap.put("militaryScore", "");
				}
				if(just.getDilgScore() != null){
					justMap.put("dilgScore", just.getDilgScore().toString());
				}else{
					justMap.put("dilgScore", "");
				}
				if(just.getDesdScore() != null){
					justMap.put("desdScore", just.getDesdScore().toString());
				}else{
					justMap.put("desdScore", "");
				}
				if(just.getMeetingScore() != null){
					justMap.put("meetingScore", just.getMeetingScore().toString());
				}else{
					justMap.put("meetingScore", "");
				}
				
				if(totalint.equals("1")){
					justMap.put("totalScore", "" + Math.round(just.getTotalScore()));
				}else{
					justMap.put("totalScore", just.getTotalScore().toString());
				}
				
				//justScore = just.getTotalScore();
				justScore = (int)(Math.round(just.getTotalScore()));
				if(justScore >= 90){
					justMap.put("level", "9");
				}else if(justScore >= 80){
					justMap.put("level", "1");
				}else if(justScore >= 70){
					justMap.put("level", "2");
				}else if(justScore >= 60){
					justMap.put("level", "3");
				}else{
					justMap.put("level", "4");
				}
					
				comments = "";
				if(cmode.equalsIgnoreCase("0")){
					if(just.getComCode1()!=null){
						comments = comments + just.getComCode1();
					}
					if(just.getComCode2()!=null){
						comments = comments + just.getComCode2();
					}
					if(just.getComCode3()!=null){
						comments = comments + just.getComCode3();
					}
				}else if(cmode.equalsIgnoreCase("1")){
					if(just.getComName1()!=null){
						comments = comments + just.getComName1();
					}
					if(just.getComName2()!=null){
						comments = comments + just.getComName2();
					}
					if(just.getComName3()!=null){
						comments = comments + just.getComName3();
					}
				}
				justMap.put("comments", comments);

				//檢查是否為定察學生
				memo = "";
				hql = "Select k From Keep k where studentNo='" + just.getStudentNo() + "'";
				keepList = dao.submitQuery(hql);
				if(!keepList.isEmpty()){
					for(Keep keep: keepList){
						if(keep.getUpYear() == null || keep.getUpTerm()==null){
							memo = memo + "定察";
							break;
						}
					}
				}
								
				//檢查是否為高選低年級課程
				hql = "Select a, d From Adcd a, Dtime d Where a.studentNo='" + just.getStudentNo() + "'";
				hql = hql + " and a.dtimeOid=d.oid And a.adddraw='A'"; 
				List tList = dao.submitQuery(hql);
				if(!tList.isEmpty()){
					for(Iterator objIter=tList.iterator(); objIter.hasNext();){
						objs = (Object[])objIter.next();
						Adcd adcd = (Adcd)objs[0];
						Dtime dtime = (Dtime)objs[1];
						
						if(adcd.getAdddraw().equals("D")){
							continue;
						}
						else {
							stdClass = adcd.getStudepartClass();
							csClass = dtime.getDepartClass();
							//log.error("student_no:" + just.getStudentNo() + ", DtimeOid:" + dtime.getOid() + ", stdClass:" + stdClass + ", csClass:" + csClass);
							chFlag = isHighChoiceLow(stdClass, csClass);
							if(chFlag == -1){
								throw new Exception("Table code5 GradYear -> 部制:" + 
										stdClass + "," + csClass +
										" 畢業年限不存在,請通知電算中心!");
							}
							else if(chFlag == 1){
								memo = memo + "加選";
								break;
							}
						}
					}
					
				}
				justMap.put("memo", memo);
				retList.add(justMap);
				
				if(prevClass.equalsIgnoreCase(just.getDepartClass())){
					justScore = (int)(Math.round(just.getTotalScore()));
					if(justScore >= 90){
						excellent++;
					}else if(justScore >= 80){
						levelA++;
					}else if(justScore >= 70){
						levelB++;
					}else if(justScore >= 60){
						levelC++;
					}else{
						levelD++;
					}

				}else{
					Map lvlMap = new HashMap();
					lvlMap.put("total", excellent+levelA+levelB+levelC+levelD);
					lvlMap.put("excellent", excellent);
					lvlMap.put("levelA", levelA);
					lvlMap.put("levelB", levelB);
					lvlMap.put("levelC", levelC);
					lvlMap.put("levelD", levelD);
					sumList.add(lvlMap);
					prevClass=just.getDepartClass();
				}
			}
			
		}
		log.debug("justList:" + retList.size()+", sumList:"+sumList.size());
		retMap.put("justList", retList);
		retMap.put("sumList", sumList);
		return retMap;
	}

	public List findJustLevel(String clazzFilter, String level) throws Exception{
		List retList = new ArrayList();
		String prevClass = "";
		String comments = "";
		String memo = "";
		
		String hql = "Select j, s From Just j, Student s Where j.departClass like '" + clazzFilter + "%'";
		hql = hql + " and j.studentNo=s.studentNo";
		hql = hql + " order by j.departClass, j.studentNo";
		List rList = dao.submitQuery(hql);
		log.debug("hql:"+hql + ", size:" + rList.size());		
		
		if(!rList.isEmpty()){
			Object[] objs = new Object[2];
			List<Keep> keepList = new ArrayList();
			Map sumMap = new HashMap();
			Just just = new Just();
			Student stud = new Student();
			int chFlag = 0;
			int justScore = 0;
			double rjustScore = 0d;
			String stdClass = "";
			String csClass = "";
			short[] bpCnt = new short[6];
			boolean isKeep = false;
			int[] sum = new int[6];
			for(int ck=0; ck<6; ck++){
				sum[ck] = 0;
			}
			
			objs = (Object[])rList.get(0);
			prevClass = ((Just)objs[0]).getDepartClass();
			Map justMap = new HashMap();
			
			for(Iterator rIter = rList.iterator(); rIter.hasNext();){
				objs = (Object[])rIter.next();
				just = (Just)objs[0];
				stud = (Student)objs[1];
				rjustScore = just.getTotalScore();
				justScore = (int)(Math.round(rjustScore));
				
				if(level.equals("S")){	//Summary
					if(just.getDepartClass().equals(prevClass)){
						sum[0]++;
						if(justScore >= 90){
							sum[1]++;
						}else if(justScore >= 80){
							sum[2]++;
						}else if(justScore >= 70){
							sum[3]++;
						}else if(justScore >= 60){
							sum[4]++;
						}else if(justScore < 60){
							sum[5]++;
						}
					}else{
						justMap = new HashMap();
						justMap.put("clazz", Toolket.getClassFullName(prevClass));
						justMap.put("totalStu", ""+sum[0]);
						justMap.put("excellent", ""+sum[1]);
						justMap.put("levelA", ""+sum[2]);
						justMap.put("levelB", ""+sum[3]);
						justMap.put("levelC", ""+sum[4]);
						justMap.put("levelD", ""+sum[5]);
						retList.add(justMap);
						
						for(int ck=0; ck<6; ck++){
							sum[ck] = 0;
						}
						sum[0]++;
						if(justScore >= 90){
							sum[1]++;
						}else if(justScore >= 80){
							sum[2]++;
						}else if(justScore >= 70){
							sum[3]++;
						}else if(justScore >= 60){
							sum[4]++;
						}else if(justScore < 60){
							sum[5]++;
						}
						prevClass = just.getDepartClass();
					}
				}else{
					justMap = new HashMap();
					justMap.put("clazz", Toolket.getClassFullName(just.getDepartClass()));
					
					if(level.equals("0")||level.equals("1")){
						if(justScore >= 90 && level.equals("0")){
							justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
							log.debug("student:"+justScore+","+ just.getStudentNo() + " " + stud.getStudentName());
							retList.add(justMap);
						}else if(justScore >= 60 && justScore < 70 && level.equals("1")){
							justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
							retList.add(justMap);
						}
						
					}else if(level.equals("4")||level.equals("5")||level.equals("A")){
						
						if(level.equals("4")){
							if(justScore >= 60){
								continue;
							}
						}else if(level.equals("5")){
							if(rjustScore != 60d){
								continue;
							}
						}else if(level.equals("A")){
							if(justScore < 95){
								continue;
							}
						}
						if(just.getTeacherScore() != null){
							justMap.put("teacherScore", just.getTeacherScore().toString());
						}else{
							justMap.put("teacherScore", "");
						}
						if(just.getDeptheaderScore() != null){
							justMap.put("deptheaderScore", just.getDeptheaderScore().toString());
						}else{
							justMap.put("deptheaderScore", "");
						}
						if(just.getMilitaryScore() != null){
							justMap.put("militaryScore", just.getMilitaryScore().toString());
						}else{
							justMap.put("militaryScore", "");
						}
						if(just.getDilgScore() != null){
							justMap.put("dilgScore", just.getDilgScore().toString());
						}else{
							justMap.put("dilgScore", "");
						}
						if(just.getDesdScore() != null){
							justMap.put("desdScore", just.getDesdScore().toString());
						}else{
							justMap.put("desdScore", "");
						}
						if(just.getMeetingScore() != null){
							justMap.put("meetingScore", just.getMeetingScore().toString());
						}else{
							justMap.put("meetingScore", "");
						}
						
						if(level.equals("4")){
							justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("totalScore", "" + just.getTotalScore());
						}else{
							justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("totalScore", "" + justScore);
						}
						
						comments = "";
						if(just.getComName1()!=null){
							comments = comments + just.getComName1();
						}
						if(just.getComName2()!=null){
							comments = comments + just.getComName2();
						}
						if(just.getComName3()!=null){
							comments = comments + just.getComName3();
						}
						justMap.put("comments", comments);

						isKeep = false;
						hql = "Select k From Keep k where studentNo='" + just.getStudentNo() + "'";
						keepList = dao.submitQuery(hql);
						if(!keepList.isEmpty()){
							for(Keep keep: keepList){
								if(keep.getUpYear() == null && keep.getUpTerm()==null){
									//該學生為定察
									justMap.put("isKeep", "定察");
									isKeep = true;
									break;
								}
							}
							if(!isKeep) justMap.put("isKeep", "");	//非定察
						}else{
							justMap.put("isKeep", "");	//非定察
						}

						List tList = new ArrayList();
						//檢查是否為高選低年級課程
						if(!level.equals("A")){
							hql = "Select a, d From Adcd a, Dtime d Where a.studentNo='" + just.getStudentNo() + "'";
							hql = hql + " and a.dtimeOid=d.oid"; 
							tList = dao.submitQuery(hql);
							if(!tList.isEmpty()){
								objs = (Object[]) rList.get(0);
								for(Iterator objIter=tList.iterator(); objIter.hasNext();){
									objs = (Object[])objIter.next();
									Adcd adcd = (Adcd)objs[0];
									Dtime dtime = (Dtime)objs[1];
									
									if(adcd.getAdddraw().equals("D")){
										continue;
									}
									else {
										stdClass = adcd.getStudepartClass();
										csClass = dtime.getDepartClass();
										chFlag = isHighChoiceLow(stdClass, csClass);
										if(chFlag == -1){
											throw new Exception("Table code5 GradYear -> 部制:" + 
													stdClass + "," + csClass +
													" 畢業年限不存在,請通知電算中心!");
										}
										else if(chFlag == 1){
											break;
										}
									}
								}
								if(chFlag == 1){
									justMap.put("Student", "*"+just.getStudentNo() + " " + stud.getStudentName());
									justMap.put("student", "*"+just.getStudentNo() + " " + stud.getStudentName());
								}else{
									justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
									justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
								}
							}
						}else{
							justMap.put("Student", just.getStudentNo() + " " + stud.getStudentName());
							justMap.put("student", just.getStudentNo() + " " + stud.getStudentName());
						}
						
						//檢查獎懲紀錄
						for(int b=0; b<bpCnt.length; b++){
							bpCnt[b] = 0;
						}
						hql = "Select d From Desd d Where d.studentNo='" + just.getStudentNo() + "'";
						tList = dao.submitQuery(hql);
						if(!tList.isEmpty()){
							int bpKind = 0;

							for(Iterator bpIter = tList.iterator(); bpIter.hasNext();){
								Desd desd = (Desd)bpIter.next();
								if(desd.getKind1() != null){
									bpKind = Integer.parseInt(desd.getKind1());
									bpCnt[bpKind-1] += desd.getCnt1();
								}
								if(desd.getKind2() != null){
									if(!desd.getKind2().trim().equals("")){
										bpKind = Integer.parseInt(desd.getKind2());
										bpCnt[bpKind-1] += desd.getCnt2();
									}
								}
							}
							for(int c=0; c<bpCnt.length; c++){
								justMap.put("merit"+(c+1), ""+bpCnt[c]);
							}
							
						}
						
						retList.add(justMap);					
					}
				}
			}
			if(level.equals("S")){
				justMap = new HashMap();
				justMap.put("clazz", Toolket.getClassFullName(prevClass));
				justMap.put("totalStu", ""+sum[0]);
				justMap.put("excellent", ""+sum[1]);
				justMap.put("levelA", ""+sum[2]);
				justMap.put("levelB", ""+sum[3]);
				justMap.put("levelC", ""+sum[4]);
				justMap.put("levelD", ""+sum[5]);
				retList.add(justMap);
			}

		}
		log.debug("justList:" + retList.size());
		return retList;
	}
	
	public List findKeepStudents(String clazzFilter, String syear, String sterm){
		List retList = new ArrayList();
		
		//新增定察名單
		String hql = "Select k, s From Keep k, Student s Where k.studentNo=s.studentNo" + 
						" and k.downYear=" + syear + " and k.downTerm=" + sterm + 
						" and k.upYear is null and k.upTerm is null" +
						" and s.departClass like '" + clazzFilter + "%'";
		hql = hql + " order by s.departClass, s.studentNo";
		//log.debug("hql:"+hql);
		List rList = dao.submitQuery(hql);
		
		if(!rList.isEmpty()){
			Object[] objs = new Object[2];
			List<Keep> keepList = new ArrayList();
			Map kMap = new HashMap();
			Keep keep = new Keep();
			Student stud = new Student();
			
			//String sex="男";
			for(Iterator rIter = rList.iterator(); rIter.hasNext();){
				
				objs = (Object[])rIter.next();
				keep = (Keep)objs[0];
				stud = (Student)objs[1];
				
				kMap = new HashMap();
				kMap.put("clazz", Toolket.getClassFullName(stud.getDepartClass()));
				if(stud.getSex().equals("1")){
					
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    男");
				}else{
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    女");
				}
				
				kMap.put("keepDown", syear + "  " + sterm);
				kMap.put("keepUp", " ");
				kMap.put("types", "新增");
				retList.add(kMap);
			}
		}
		log.debug("keepList:" + retList.size());
		
		//註銷定察名單
		hql = "Select k, s From Keep k, Student s Where k.studentNo=s.studentNo" + 
						" and k.upYear=" + syear + " and k.upTerm=" + sterm + 
						" and s.departClass like '" + clazzFilter + "%'";
		hql = hql + " order by s.departClass, s.studentNo";
		//log.debug("hql:"+hql);
		rList = dao.submitQuery(hql);

		if(!rList.isEmpty()){
			Object[] objs = new Object[2];
			List<Keep> keepList = new ArrayList();
			Map kMap = new HashMap();
			Keep keep = new Keep();
			Student stud = new Student();
			
			for(Iterator rIter = rList.iterator(); rIter.hasNext();){
				objs = (Object[])rIter.next();
				keep = (Keep)objs[0];
				stud = (Student)objs[1];
				
				kMap = new HashMap();
				kMap.put("clazz", Toolket.getClassFullName(stud.getDepartClass()));
				if(stud.getSex().equals("1")){
					
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    男");
				}else{
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    女");
				}
				kMap.put("keepDown", keep.getDownYear() + "  " + keep.getDownTerm());
				kMap.put("keepUp", syear + "  " + sterm);
				kMap.put("types", "註銷");
				retList.add(kMap);
			}
		}
		log.debug("keepList:" + retList.size());
		
		//所有定察名單
		hql = "Select k, s From Keep k, Student s Where k.studentNo=s.studentNo" + 
						" and k.upYear is null and k.upTerm is null" +
						" and s.departClass like '" + clazzFilter + "%'";
		hql = hql + " order by s.departClass, s.studentNo";
		//log.debug("hql:"+hql);
		rList = dao.submitQuery(hql);

		if(!rList.isEmpty()){
			Object[] objs = new Object[2];
			List<Keep> keepList = new ArrayList();
			Map kMap = new HashMap();
			Keep keep = new Keep();
			Student stud = new Student();
			
			for(Iterator rIter = rList.iterator(); rIter.hasNext();){
				objs = (Object[])rIter.next();
				keep = (Keep)objs[0];
				stud = (Student)objs[1];
				
				kMap = new HashMap();
				kMap.put("clazz", Toolket.getClassFullName(stud.getDepartClass()));
				if(stud.getSex().equals("1")){
					
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    男");
				}else{
					kMap.put("Student", keep.getStudentNo() + " " + stud.getStudentName()+"    女");
				}
				kMap.put("keepDown", keep.getDownYear() + "  " + keep.getDownTerm());
				kMap.put("keepUp", " ");
				kMap.put("types", "所有");
				retList.add(kMap);
			}
		}
		
		log.debug("keepList:" + retList.size());
		return retList;		
	}
	
	public List BonusPenaltySum(String clazzFilter, String mode){
		List retList = new ArrayList();

		String hql = "Select d From Desd d Where d.departClass like '" + clazzFilter + "%'";
		hql = hql + " order by d.departClass";
		//log.debug("hql:"+hql);
		List<Desd> dList = dao.submitQuery(hql);
		
		if(!dList.isEmpty()){			
			String prevClazz = dList.get(0).getDepartClass();
			int[][] merit = new int[6][2];	//[0]:次數 [1]:人次
			int[] count = new int[2];
			int totalStu = 0;
			double num =0.0d;
			
			int kind = 0;
			for(int i=0; i<6;i++){
				merit[i][0] = 0;
				merit[i][1] = 0;
			}
			
			for(Iterator<Desd> dIter = dList.iterator(); dIter.hasNext();){
				Desd desd = dIter.next();
				if(desd.getDepartClass().equals(prevClazz)){
					if(desd.getCnt1()!=null && desd.getKind1()!=null){
						kind = Integer.parseInt(desd.getKind1())-1;
						merit[kind][0] += desd.getCnt1();
						merit[kind][1]++;
					}
					if(desd.getCnt2()!=null && desd.getKind2()!=null){
						kind = Integer.parseInt(desd.getKind2())-1;
						merit[kind][0] += desd.getCnt2();
						merit[kind][1]++;
					}
				}else{
					if(mode.equals("0")){
						hql = "Select s From Student s Where departClass='" + prevClazz + "'";
						List sList = dao.submitQuery(hql);
						totalStu = sList.size();
					}
					Map kMap = new HashMap();
					kMap.put("clazz", Toolket.getClassFullName(prevClazz));
					if(mode.equals("0")){
						kMap.put("totalStu", totalStu);
					}
					
					count[0] = 0;
					count[1] = 0;
					for(int j=0; j<3; j++){
						kMap.put("merit"+(j+1), merit[j][0]);
						if(mode.equals("0")){
							num = ((double)merit[j][0])/totalStu;
							num = Math.round(num*10d)/10d;
							kMap.put("meritP"+(j+1), num);
						}else if(mode.equals("1")){
							kMap.put("meritP"+(j+1), merit[j][1]);
						}
						count[0] += merit[j][0];
						count[1] += merit[j][1];
					}
					
					if(mode.equals("1")){
						kMap.put("meritSum", count[0]);
						kMap.put("meritP", count[1]);
					}
					
					count[0] = 0;
					count[1] = 0;
					for(int j=3; j<6; j++){
						kMap.put("merit"+(j+1), merit[j][0]);
						if(mode.equals("0")){
							num = ((double)merit[j][0])/totalStu;
							num = Math.round(num*10d)/10d;
							kMap.put("meritP"+(j+1), num);
						}else if(mode.equals("1")){
							kMap.put("meritP"+(j+1), merit[j][1]);
						}
						count[0] += merit[j][0];
						count[1] += merit[j][1];
					}
					if(mode.equals("1")){
						kMap.put("offenseSum", count[0]);
					}
					kMap.put("offenseP", count[1]);
					retList.add(kMap);
					
					//統計下一班該筆資料
					for(int i=0; i<6;i++){
						merit[i][0] = 0;
						merit[i][1] = 0;
					}
					if(desd.getCnt1()!=null && desd.getKind1()!=null){
						kind = Integer.parseInt(desd.getKind1())-1;
						merit[kind][0] += desd.getCnt1();
						merit[kind][1]++;
					}
					if(desd.getCnt2()!=null && desd.getKind2()!=null){
						kind = Integer.parseInt(desd.getKind2())-1;
						merit[kind][0] += desd.getCnt2();
						merit[kind][1]++;
					}
					prevClazz = desd.getDepartClass();
				}
			}
			
			//增加最後一筆資料
			if(mode.equals("0")){
				hql = "Select s From Student s Where departClass='" + prevClazz + "'";
				List sList = dao.submitQuery(hql);
				totalStu = sList.size();
			}
			Map kMap = new HashMap();
			kMap.put("clazz", Toolket.getClassFullName(prevClazz));
			if(mode.equals("0")){
				kMap.put("totalStu", totalStu);
			}
			
			count[0] = 0;
			count[1] = 0;
			for(int j=0; j<3; j++){
				kMap.put("merit"+(j+1), merit[j][0]);
				if(mode.equals("0")){
					num = ((double)merit[j][0])/totalStu;
					num = Math.round(num*10d)/10d;
					kMap.put("meritP"+(j+1), num);
				}else if(mode.equals("1")){
					kMap.put("meritP"+(j+1), merit[j][1]);
				}
				count[0] += merit[j][0];
				count[1] += merit[j][1];
			}
			
			if(mode.equals("1")){
				kMap.put("meritSum", count[0]);
				kMap.put("meritP", count[1]);
			}
			
			count[0] = 0;
			count[1] = 0;
			for(int j=3; j<6; j++){
				kMap.put("merit"+(j+1), merit[j][0]);
				if(mode.equals("0")){
					num = ((double)merit[j][0])/totalStu;
					num = Math.round(num*10d)/10d;
					kMap.put("meritP"+(j+1), num);
				}else if(mode.equals("1")){
					kMap.put("meritP"+(j+1), merit[j][1]);
				}
				count[0] += merit[j][0];
				count[1] += merit[j][1];
			}
			if(mode.equals("1")){
				kMap.put("offenseSum", count[0]);
			}
			kMap.put("offenseP", count[1]);
			retList.add(kMap);
		}
		log.debug("desdList:" + retList.size());
		return retList;
	}
	
	public ActionMessages UpdateDtimeDilgPeriod(){
		ActionMessages msgs = new ActionMessages();
		
		String sterm = Toolket.getSysParameter("School_term");
		String hql = "Select d From Dtime d Where sterm='" + 
		sterm + "'";
		
		List<Dtime> dtList = dao.submitQuery(hql);
		log.debug("Update:" + dtList.size() + " records");
		try{
			for(Dtime dtime:dtList){
				if(sterm.equals("2") && Toolket.chkIsGraduateClass(dtime.getDepartClass())){
					if((dtime.getThour() * 14) % 3 > 0){
						dtime.setPeriodLimit(Math.round(dtime.getThour() * 14f / 3f) + 1);
					}else{
						dtime.setPeriodLimit(Math.round(dtime.getThour() * 14f / 3f));
					}
				}else{
					dtime.setPeriodLimit(Math.round(dtime.getThour() * 18f / 3f));
				}
				dao.saveObject(dtime);
			}
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
		}
		
		return msgs;
	}
	
	private void sleeping(int msec) {
		long startTime=System.currentTimeMillis();
		for(;;){
			if((System.currentTimeMillis() - startTime) > msec) break;
		}
		return;
	}
	
	private boolean IsOpenSubject(String dtimeOid) {
		boolean isOpen = false;
		String hql = "Select o From Opencs o Where dtimeOid=" + dtimeOid ;
		List tmplist = dao.submitQuery(hql);
		if(tmplist.size() > 0) isOpen = true;
		else if(tmplist.isEmpty()) isOpen = false;
		return isOpen;
	}
	
	public int WhatIsTheWeek(Date ddate) {
		Calendar cdate = Calendar.getInstance(); 
		cdate.setTime(ddate);
		
		int iweek = cdate.get(Calendar.DAY_OF_WEEK);
		if(iweek == 1) iweek = 7;
		else iweek--;

		return iweek;
	}
	
	private boolean isNaN(String s) {
		
		boolean isNum = true;
		int dotcnt = 0;
		for(int i =0; i < s.length(); i++){
			if(i == 0) {
				if(s.charAt(i) == '-' || s.charAt(i)=='.') {
					isNum = true;
					if(s.charAt(i)=='.') dotcnt++;
				}
			} else if(!(s.charAt(i)=='0' || s.charAt(i)=='1' || s.charAt(i)=='2' || s.charAt(i)=='3' ||
					s.charAt(i)=='4' || s.charAt(i)=='5' || s.charAt(i)=='6' || s.charAt(i)=='7' ||
					s.charAt(i)=='8' || s.charAt(i)=='9' || s.charAt(i)=='.')) {
				isNum = false;
				break;
			} else {
				if(s.charAt(i)=='.') dotcnt++;
				if(dotcnt > 1) {
					isNum = false;
					break;
				}
			}
		}
		return isNum;
	}

	
	private Map dilgAnalysis(Dilg dilg){
		int tflen = Global.TimeOffList.size();
		List retList = new ArrayList();	//當天請假分類資料
		int[] kind = new int[tflen];	//假別之統計
		for(int i=0; i<tflen; i++){
			kind[i]=0;
		}
		
		int pkind = 0, pindex = 0;
		short[] abs = new short[16];
		for(int i=0; i<16; i++){
			abs[i]=0;
		}

		if(dilg.getAbs0()!= null) abs[0] = dilg.getAbs0();
		if(dilg.getAbs1()!= null) abs[1] = dilg.getAbs1();
		if(dilg.getAbs2()!= null) abs[2] = dilg.getAbs2();
		if(dilg.getAbs3()!= null) abs[3] = dilg.getAbs3();
		if(dilg.getAbs4()!= null) abs[4] = dilg.getAbs4();
		if(dilg.getAbs5()!= null) abs[5] = dilg.getAbs5();
		if(dilg.getAbs6()!= null) abs[6] = dilg.getAbs6();
		if(dilg.getAbs7()!= null) abs[7] = dilg.getAbs7();
		if(dilg.getAbs8()!= null) abs[8] = dilg.getAbs8();
		if(dilg.getAbs9()!= null) abs[9] = dilg.getAbs9();
		if(dilg.getAbs10()!= null) abs[10] = dilg.getAbs10();
		if(dilg.getAbs11()!= null) abs[11] = dilg.getAbs11();
		if(dilg.getAbs12()!= null) abs[12] = dilg.getAbs12();
		if(dilg.getAbs13()!= null) abs[13] = dilg.getAbs13();
		if(dilg.getAbs14()!= null) abs[14] = dilg.getAbs14();
		if(dilg.getAbs15()!= null) abs[15] = dilg.getAbs15();
		
		for(int j=0; j<16; j++){
			if(abs[j]!= 0){
				kind[abs[j]]+=1;
				if(abs[j]!=pkind){
					if(pkind ==0){
						pkind = abs[j];
						pindex = j;
					}else{
						Map retMap = new HashMap();
						retMap.put("date", Toolket.Date2Str(dilg.getDdate()));
						retMap.put("week", "" + dilg.getWeekDay().intValue());
						retMap.put("start", "" + pindex);
						retMap.put("end", "" + (j-1));
						retMap.put("kind", Toolket.getTimeOff("" + pkind).substring(1));
						retList.add(retMap);
						pkind = abs[j];
						pindex = j;
					}
				}
				
			}else{
				if(pkind!=0){
					Map retMap = new HashMap();
					retMap.put("date", Toolket.Date2Str(dilg.getDdate()));
					retMap.put("week", "" + dilg.getWeekDay().intValue());
					retMap.put("start", "" + pindex);
					retMap.put("end", "" + (j-1));
					retMap.put("kind",  Toolket.getTimeOff("" + pkind).substring(1));
					retList.add(retMap);
					pkind = 0;
					pindex = 0;
				}
			}
			
		}
		
		/*
		for(Iterator aIter=retList.iterator(); aIter.hasNext();){
			Map aMap=(Map)aIter.next();
			log.debug("dAnalysis:" + aMap.get("date") + ", " + aMap.get("week") 
					+ ", " + aMap.get("start") + ", " + aMap.get("end") + 
					", " + aMap.get("kind"));
		}
		*/
		
		Map rMap = new HashMap();
		rMap.put("dList", retList);
		rMap.put("sum", kind);
		
		return rMap;
	}

	private int[] dilgSum(Dilg dilg){
		int[] wtimeoff = new int[2];
		
		if(dilg.getAbs0()!= null) {wtimeoff[0]++;}
		if(dilg.getAbs1()!= null) {
			if(dilg.getAbs1()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs2()!= null){
			if(dilg.getAbs2()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs3()!= null){
			if(dilg.getAbs3()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs4()!= null){
			if(dilg.getAbs4()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs5()!= null){
			if(dilg.getAbs5()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs6()!= null){
			if(dilg.getAbs6()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs7()!= null){
			if(dilg.getAbs7()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs8()!= null){
			if(dilg.getAbs8()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs9()!= null){
			if(dilg.getAbs9()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs10()!= null){
			if(dilg.getAbs10()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs11()!= null){
			if(dilg.getAbs11()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs12()!= null){
			if(dilg.getAbs12()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs13()!= null){
			if(dilg.getAbs13()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs14()!= null){
			if(dilg.getAbs14()== 2) {wtimeoff[1]++;}
		}
		if(dilg.getAbs15()!= null){
			if(dilg.getAbs15()== 2) {wtimeoff[1]++;}
		}
		return wtimeoff;
	}
	
	private int isHighChoiceLow(String stdeptClass, String csdeptClass){
		String stidno = "";
		String csidno = "";
		boolean extend = false;
		int isChoose = 0;
		int stClass = 0, stGrad = 0;
		int csClass = 0, csGrad = 0;
		if(stdeptClass.length()<6||csdeptClass.length()<6){
			return isChoose;
		}
		if(stdeptClass.substring(1, 4).equals("125")){
			stidno = "125";
		}else{
			stidno = stdeptClass.substring(1, 3);
		}
		
		if(csdeptClass.substring(1, 4).equals("125")){
			csidno = "125";
		}else{
			csidno = csdeptClass.substring(1, 3);
		}
		
		String hql = "Select c From Code5 c Where category='GradYear' and idno='" + stidno + "'";
		List rList = dao.submitQuery(hql);
		if(!rList.isEmpty()){
			Code5 c5 = (Code5)rList.get(0);
			stClass = Integer.parseInt(stdeptClass.substring(4, 5));
			stGrad = Integer.parseInt(c5.getName());
			//判斷是否為延修班學生
			if(stClass > stGrad){
				stClass = stGrad;
			}
		}else{
			return -1;	//沒有該部制畢業年限資料
		}
		
		hql = "Select c From Code5 c Where category='GradYear' and idno='" + csidno + "'";
		rList = dao.submitQuery(hql);
		if(!rList.isEmpty()){
			Code5 c5 = (Code5)rList.get(0);
			csClass = Integer.parseInt(csdeptClass.substring(4, 5));
			csGrad = Integer.parseInt(c5.getName());
		}else{
			return -1;	//沒有該部制畢業年限資料
		}
		
		if(stGrad > csGrad){	//學生畢業年限大於開課班及畢業年限
			csClass = csClass + (stGrad - csGrad);
			if(stClass > csClass) isChoose = 1;
		}else if(stGrad < csGrad){
			stClass = stClass + (csGrad - stGrad);
			if(stClass > csClass) isChoose = 1;
		}
		return isChoose;
	}
	
	/**
	 * 取得某部制的班
	 * 11, 12, 13, 21, 22 ,23
	 */
	public List getDepartClassByType(String schoolType) {
		String sql = "SELECT idno FroM code5 WHERE category='SchoolType' AND name LIKE'"+schoolType+"%'";
		List<Code5> c5List = jdbcDao.findAnyThing(sql);
		Iterator it=c5List.iterator();
		
		StringBuilder sb=new StringBuilder("SELECT ClassNo FROM Class WHERE ClassNo LIKE");
		while(it.hasNext()){
			sb.append("'"+((Map)it.next()).get("idno")+"%' OR ClassNo LIKE " );
		}
		sb.delete(sb.length()-16, sb.length());
		//log.debug(sb.toString());
		return jdbcDao.findAnyThing(sb.toString());
	}
	
	public List findAnyThing(String sql){
		return jdbcDao.findAnyThing(sql);
	}

	public ActionMessages seldExamUpdate(String depart){
		ActionMessages msgs = new ActionMessages();
		String sql = "";
		String campSchool = "";
		String runlogs = "";
		String sterm = Toolket.getSysParameter("School_term");
		int inProcess = 0;
		double percentage = 0d;
		List tList = new ArrayList();
		
		setRunStatus("updateSeldExamAll", "start!", 0, 0, 0d, "no");
				sql = "Select Distinct depart_class From stmd where depart_class like '" + depart + "%'";
		List cList = jdbcDao.findAnyThing(sql);

		int percentUnit = 1;
		int pcnt = cList.size();
		if (pcnt > 100) {
			percentUnit = (int) (Math.floor(pcnt / 100));
		} else {
			percentUnit = 1;
		}

		for (Iterator cIter = cList.iterator(); cIter.hasNext();) {
			String clazz = ((Map) cIter.next()).get("depart_class").toString();
			sql = "select Distinct s.student_no from Seld s, stmd d Where s.student_no=d.student_no";
			sql = sql + " and d.depart_class='" + clazz + "'";
			sql = sql + " order by s.student_no";
			tList = jdbcDao.findAnyThing(sql);
			int studknt = tList.size();
			
			log.debug("sql:" + sql);
			log.debug("StudSeldExamUpdateAll->clazz:" + clazz + " , size:"
					+ studknt + " ," + pcnt + "/" + inProcess);
			// 可重覆試做3次
			for (int i = 0; i < 3; i++) {
				// setRunStatus("updateJustAll", "depart:" + clazz + " start!",
				// 0, 0, 0d, "no");
				msgs = this.updateSeldExam(tList);
				if (msgs.isEmpty()) {
					break;
				} else {
					log.debug("StudSeldExamUpdateAll->fail on depart:" + clazz
							+ ", retry:" + i);
					setRunStatus("updateSeldExamAll", "depart:" + clazz
							+ " retry!", inProcess, pcnt, percentage, "no");
					Toolket.sleeping(3000); // 休息3秒
				}
			}
			if (!msgs.isEmpty()) {
				setRunStatus("updateSeldExamAll", "depart:" + clazz + " fail!",
						0, 0, 0d, "yes");
				break;
			}
			runlogs = "class:" + clazz + " update " + studknt + " records!\n"
					+ runlogs;
			inProcess++;
			if ((inProcess % 10) == 0) {
				percentage = (double) (inProcess / percentUnit);
				setRunStatus("updateSeldExamAll", "depart:" + clazz, inProcess,
						pcnt, percentage, runlogs);
			}

		}
		if(msgs.isEmpty()) {
			setRunStatus("updateSeldExamAll", "finished!", 100, 100, 100d, "yes");
		}
		tList = null;
		cList = null;
		Runtime.getRuntime().gc();
		return msgs;
	}

	/**
	 * 取得生活教育競賽[規定傳達]成績資料
	 * 
	 * @return List
	 */
	public List<RuleTran> findRuleTran(){
		List<RuleTran> rtList = new ArrayList();
		String hql = "select rt From RuleTran rt order by departClass";
		rtList = dao.submitQuery(hql);
		
		for(RuleTran rt:rtList){
			rt.setDepartClassName(Toolket.getClassFullName(rt.getDepartClass()));
		}
		return rtList;
	}
	
	public List<RuleTran> createRuleTran(){
		String sql = "delete From RuleTran";
		int recno = jdbcDao.updateAnyThing(sql);
		String clazz = "";
		String hql = "";
		List<RuleTran> rtList = new ArrayList(); 
		
		List<Clazz> clazzes = new ArrayList();

		sql = "select distinct depart_class From stmd";
		sql = sql + " Where (depart_class like '164%' or depart_class like'112%' or depart_class like '142%') " +
					" and (depart_class not like '164_5%' and depart_class not like '112_3%' and depart_class not like '142_3%')" +
					" order by depart_class";
		List classes = jdbcDao.findAnyThing(sql);
		//log.debug(classes.size());

		sql = "select distinct depart_class From stmd";
		sql = sql + " Where depart_class like '11253%'";
		List tmpList = jdbcDao.findAnyThing(sql);
		if(!tmpList.isEmpty())
			classes.addAll(tmpList);
		
		//log.debug(classes.size());
		for(Iterator cIter=classes.iterator(); cIter.hasNext();){
			clazz = ((Map)cIter.next()).get("depart_class").toString();
			hql = "Select c From Clazz c Where classNo='" + clazz.trim() + "'";
			clazzes = dao.submitQuery(hql);
			if(!clazzes.get(0).getShortName().trim().equals("延修")){
				RuleTran rt = new RuleTran();
				rt.setDepartClass(clazzes.get(0).getClassNo());
				rt.setScore(0f);
				rt.setMscore(0f);
				rt.setDepartClassName(Toolket.getClassFullName(clazzes.get(0).getClassNo()));
				dao.saveObject(rt);
				rtList.add(rt);
			}
		}
				
		return rtList;
	}

	public List<Clazz> findClass4Racing(){
		String sql = "select distinct depart_class From stmd";
		sql = sql + " Where (depart_class like '_A4%' or depart_class like '_64%' or depart_class like'_12%' or depart_class like '_42%') " +
					" and (depart_class not like '_64_5%' and depart_class not like '_12_3%' and depart_class not like '_42_3%')" +
					" order by depart_class";
		List clazzes = jdbcDao.findAnyThing(sql);
		
		sql = "select distinct depart_class From stmd";
		sql = sql + " Where depart_class like '_1253%'";
		List tmpList = jdbcDao.findAnyThing(sql);
		if(!tmpList.isEmpty())
			clazzes.addAll(tmpList);
		
		String tmp = "";
		for(Iterator mapIter=clazzes.iterator(); mapIter.hasNext();){
			tmp = tmp + ",'" + ((Map)mapIter.next()).get("depart_class").toString() + "'";
		}
		tmp = "(" + tmp.substring(1) + ")";
		
		String hql = "Select c From Clazz c Where classNo in " + tmp + " order by classNo";
		
		List<Clazz> classes = dao.submitQuery(hql);
		
		for(Clazz clazz:classes){
			clazz.setClassFullName(Toolket.getClassFullName(clazz.getClassNo()));
		}
		return classes;
	}
	
	public ActionMessages createCleans(String weekNo, String[] clazzes, String[] scores){
		ActionMessages errs = new ActionMessages();
		
		String hql = "Select c From Clean c Where weekNo=" + weekNo;
		List<Clean> cleans = dao.submitQuery(hql);
		
		try{
			if(!cleans.isEmpty()){
				String sql = "delete From clean Where week_no=" + weekNo;
				jdbcDao.updateAnyThing(sql);
			}
			
			for(int i=0; i<clazzes.length; i++){
				Clean clean = new Clean();
				clean.setWeekNo(Short.parseShort(weekNo));
				clean.setDepartClass(clazzes[i]);
				if(!scores[i].trim().equals(""))
					clean.setScore(Float.parseFloat(scores[i]));
				dao.saveObject(clean);
			}
		}catch(Exception e){
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
		}
		return errs;
	}
	
	public List<Clean> findClean(String weekNo){
		List<Clean> cleans = new ArrayList();
		String hql = "Select c From Clean c Where weekNo=" + weekNo + " Order by departClass";
		cleans = dao.submitQuery(hql);
		for(Clean clean:cleans){
			clean.setDepartClassName(Toolket.getClassFullName(clean.getDepartClass()));
		}
		return cleans;
	}
	
	public ActionMessages updateCleans(String[] weekNos, String[] clazzes, String[] scores){
		ActionMessages errs = new ActionMessages();
		String hql = "";
		List<Clean> clList = new ArrayList();
		
		try{
		for(int i=0; i<clazzes.length; i++){
			hql = "Select c From Clean c Where weekNo=" + weekNos[i] +
			" and departClass='" + clazzes[i] + "'";
			clList = dao.submitQuery(hql);
			if(!clList.isEmpty()){
				Clean clean = clList.get(0);
				if(!scores[i].trim().equals(""))
					clean.setScore(Float.parseFloat(scores[i]));
				else
					clean.setScore(null);
				dao.saveObject(clean);
			}else{
				Clean clean = new Clean();
				clean.setWeekNo(Short.parseShort(weekNos[i]));
				clean.setDepartClass(clazzes[i]);
				clean.setScore(Float.parseFloat(scores[i]));
				dao.saveObject(clean);
			}
		}
		}catch(Exception e){
			errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
		}
		
		return errs;
	}
	
	
	public ActionMessages updateRuleTrans(String[] clazzes, String[] scores, String[] mscores){
		ActionMessages errors = new ActionMessages();
		String hql = "";
		
		for(int i=0; i<clazzes.length; i++){
			hql = "Select rt From RuleTran rt Where departClass='" + clazzes[i] + "'";
			List<RuleTran> rtList = dao.submitQuery(hql);
			if(!rtList.isEmpty()){
				Float score = Float.parseFloat(scores[i]);
				Float mscore = Float.parseFloat(mscores[i]);
				RuleTran rt = rtList.get(0);
				rt.setScore(score);
				rt.setMscore(mscore);
				dao.saveObject(rt);
			}else{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "找不到[" + clazzes[i] + "]該班的規定傳達成績!"));
			}
		}
		
		return errors;
	}
	
	
	public List<Desd> findDesdByDateRange(String startDate, String endDate){
		List<Desd> retList = new ArrayList<Desd>();
		
		String[] startd = startDate.split("/");
		String[] endd = endDate.split("/");
		String starts = (Integer.parseInt(startd[0]) + 1911) +"-"+startd[1]+"-"+startd[2];
		String ends = (Integer.parseInt(endd[0]) + 1911) +"-"+endd[1]+"-"+endd[2];
		String tmp = "";
		log.debug("endDate:" + endDate + " , " + endd.length);
		
		String hql = "Select d From Desd d Where ddate >= '" + starts + "' And ddate <= '" + ends +
		"' Order by ddate, departClass, studentNo";
		
		log.debug("hql:" + hql);
		
		retList = dao.submitQuery(hql);
		for(Desd desd:retList){
			desd.setSddate(Toolket.printDate(desd.getDdate()));
			desd.setStudentName(Toolket.getStudentName(desd.getStudentNo()));
			desd.setDeptClassName(Toolket.getClassFullName(desd.getDepartClass()));
			desd.setReasonName(getBonusPenaltyCodeName(desd.getReason()));
			if(desd.getKind1() != null){
				tmp = desd.getKind1().trim();
				if(tmp.equals("1")){
					desd.setDesdName1("大功");
				}else if(tmp.equals("2")){
					desd.setDesdName1("小功");
				}else if(tmp.equals("3")){
					desd.setDesdName1("嘉獎");
				}else if(tmp.equals("4")){
					desd.setDesdName1("大過");
				}else if(tmp.equals("5")){
					desd.setDesdName1("小過");
				}else if(tmp.equals("6")){
					desd.setDesdName1("申誡");
				}else{
					desd.setDesdName1("申誡");
				}
			}
			if(desd.getKind2() != null){
				tmp = desd.getKind2().trim();
				if(tmp.equals("1")){
					desd.setDesdName2("大功");
				}else if(tmp.equals("2")){
					desd.setDesdName2("小功");
				}else if(tmp.equals("3")){
					desd.setDesdName2("嘉獎");
				}else if(tmp.equals("4")){
					desd.setDesdName2("大過");
				}else if(tmp .equals("5")){
					desd.setDesdName2("小過");
				}else if(tmp.equals("6")){
					desd.setDesdName2("申誡");
				}else{
					desd.setDesdName2("");
				}
			}
		}
		return retList;
	}
	
	public ActionMessages updateDesd4Racing(List<Desd> desdList, String[] sels){
		ActionMessages msgs = new ActionMessages();
		
		if(desdList.size() != sels.length){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","生活教育競賽獎懲記錄更新錯誤,筆數不相等!"));
		}else{
			int i = 0;
			for(Desd desd:desdList){
				if(sels[i].equalsIgnoreCase("Y"))
					desd.setActIllegal("Y");
				else if(sels[i].equalsIgnoreCase("N"))
					desd.setActIllegal("N");
				else if(sels[i].trim().equalsIgnoreCase(""))
					desd.setActIllegal(null);
				else{
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1","生活教育競賽獎懲記錄更新錯誤,必需輸入Y或N!"));
					break;
				}
				dao.saveObject(desd);
				i++;
			}
		}
		return msgs;
		
	}
	
	public String getBonusPenaltyCodeName(String code){
		String c2Name = "";
		List<Code2> c2List = dao.findCode2List(code);
		if(!c2List.isEmpty()){
			c2Name = c2List.get(0).getName();
		}
		
		return c2Name;
	}
	
	private ActionMessages updateSeldExam(List studs){
		ActionMessages msgs = new ActionMessages();
		String hql = "";
		/*
		hql = "select s from Student s Where s.departClass like '" + clazz + "%'";
		hql = hql + " order by s.studentNo";
		Student student = new Student();
		List<Student> studs = dao.submitQuery(hql);
		*/
		
		List<Dilg> dilgList = new ArrayList();
		List<Seld> seldList = new ArrayList();
		List<Dtime> dtimeList = new ArrayList();;
		String studentNo = "";
		
		Seld seld;
		Dtime dtime;
		int dtOid=0;
		int tfsum=0;
		int tfLimit=0;
		int begin = 0, end = 0, status = 0;

		
		List<Code5> tfTypeList = Global.TimeOffList;
		String sterm = Toolket.getSysParameter("School_term");
		int tflen = tfTypeList.size();
		String depart = "";
		
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		//String[][] depClass = new String[8][16];
		
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;
		try{
			for(Iterator stuIter=studs.iterator(); stuIter.hasNext();){
				studentNo=((Map)stuIter.next()).get("student_no").toString();
				hql = "Select d From Dilg d";
				hql = hql + " Where studentNo='" + studentNo + "'";
				hql = hql + " Order by ddate";
				dilgList = dao.submitQuery(hql);
				
				seldList = dao.findSeldByStudentNo(studentNo, sterm);
				
				if(dilgList.isEmpty()){
					for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
						seld = seldIter.next();
						seld.setDilgPeriod(0);
					}
				}else{
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j <16; j++){
							//depClass[i][j] = "";
							for(int k = 0; k < tflen; k++){
								timeoffWeek[i][j][k] = 0;
							}
						}
					}
					
					//將該學生曠缺資料填入timeoffWeek陣列
					for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
						dilg =dilgIter.next();
						iweek = Math.round(dilg.getWeekDay());
						//iweek = this.WhatIsTheWeek(dilg.getDdate());
						if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
							tfType = dilg.getAbs0();
							timeoffWeek[iweek][0][tfType]++;
						}
						if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
							tfType = dilg.getAbs1();
							timeoffWeek[iweek][1][tfType]++;
						}
						if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
							tfType = dilg.getAbs2();
							timeoffWeek[iweek][2][tfType]++;
						}
						if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
							tfType = dilg.getAbs3();
							timeoffWeek[iweek][3][tfType]++;
						}
						if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
							tfType = dilg.getAbs4();
							timeoffWeek[iweek][4][tfType]++;
						}
						if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
							tfType = dilg.getAbs5();
							timeoffWeek[iweek][5][tfType]++;
						}
						if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
							tfType = dilg.getAbs6();
							timeoffWeek[iweek][6][tfType]++;
						}
						if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
							tfType = dilg.getAbs7();
							timeoffWeek[iweek][7][tfType]++;
						}
						if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
							tfType = dilg.getAbs8();
							timeoffWeek[iweek][8][tfType]++;
						}
						if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
							tfType = dilg.getAbs9();
							timeoffWeek[iweek][9][tfType]++;
						}
						if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
							tfType = dilg.getAbs10();
							timeoffWeek[iweek][10][tfType]++;
						}
						if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
							tfType = dilg.getAbs11();
							timeoffWeek[iweek][11][tfType]++;
						}
						if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
							tfType = dilg.getAbs12();
							timeoffWeek[iweek][12][tfType]++;
						}
						if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
							tfType = dilg.getAbs13();
							timeoffWeek[iweek][13][tfType]++;
						}
						if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
							tfType = dilg.getAbs14();
							timeoffWeek[iweek][14][tfType]++;
						}
						if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
							tfType = dilg.getAbs15();
							timeoffWeek[iweek][15][tfType]++;
						}
					}
					
					//計算該學生每一門課程是否已達扣考門檻,填入 Seld 中的dilg_period 曠缺資料
					//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
					//不同部制有不同之操行加減分標準					
					for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
						seld = seldIter.next();
						dtOid = seld.getDtimeOid();
							
						dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
						dtime = dtimeList.get(0);
							
						tfLimit = 0;

						if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
							if(dtime.getThour() * 14 % 3 > 0){
								tfLimit = dtime.getThour() * 14 / 3 + 1;
							}else{
								tfLimit = dtime.getThour() * 14 / 3;
							}
						}
						else {
							tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
						}

						hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
						List<DtimeClass> dcList = dao.submitQuery(hql);
						tfsum = 0;
						for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
							DtimeClass dclass = dcIter.next();
										
							begin = Integer.parseInt(dclass.getBegin().trim());
							end = Integer.parseInt(dclass.getEnd().trim());
							//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
							iweek = dclass.getWeek();
							for(int j=begin; j <= end; j++){
								tfsum = tfsum + timeoffWeek[iweek][j][2] + timeoffWeek[iweek][j][3] +
								timeoffWeek[iweek][j][4] + timeoffWeek[iweek][j][7] +
								timeoffWeek[iweek][j][8] + timeoffWeek[iweek][j][9];
							}
									
						}
						seld.setDilgPeriod(tfsum);
						/*
						if(tfsum>=tfLimit){							
							for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
								DtimeClass dclass = dcIter.next();
								iweek = dclass.getWeek();
								for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
									if(tfsum < tfLimit) {
										depClass[iweek][m] = dtime.getDepartClass();
									}
									else {
										for(int n =0; n < tflen; n++){
											timeoffWeek[iweek][m][n] = 0;
										}
									}
								}
							}
								
						}
						*/
						dao.saveObject(seld);
					}
				}
			}
			
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					e.toString()));
		}
		timeoffWeek = null;
		dilgList = null;
		seldList = null;
		dtimeList = null;
		Runtime.getRuntime().gc();
		
		return msgs;
	}

	/**
	 * 查詢轉帳資料
	 * 
	 * @param dipost tw.edu.chit.models.Dipost Object
	 * @return java.util.List List of tw.edu.chit.models.Dipost Objects
	 */
	public List<Dipost> findDipostsBy(Dipost dipost) {
		return dao.getDipostsBy(dipost);
	}
	
	/**
	 * 操行成績轉歷史檔
	 * 
	 * @param String schoolYear
	 * @param String schoolTerm
	 * @param String depart
	 * 
	 * @return org.apache.struts.action.ActionMessages msgs
	 * @see tw.edu.chit.service.ScoreManager#termScore2ScoreHist(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ActionMessages txJustScore2History(String schoolYear, String schoolTerm, String clazzFilter) {
		
		ActionMessages msgs = new ActionMessages();
		
		int i;
		String sql = "", subsql = "";
		String[] departs;
		String subhql = "";
		String hql = "";
		List<Just> justlist = new ArrayList<Just>();
		List<Desd> desdlist = new ArrayList<Desd>();
		List studs = new ArrayList();
		List<Student> students = new ArrayList();
		List<Csno> phyList = new ArrayList<Csno>();
		List<Csno> milList = new ArrayList<Csno>();
		List<Just> justs;
		List<Desd> desds;
		//leo read 20120131
		hql = "Select s From Student s Where s.departClass like '" + clazzFilter + "%'" + " Order By s.studentNo";
		
		students = dao.submitQuery(hql);
		String nojust = "";
		for(Student stud:students) {
			sql = "Select * From just Where student_no='" + stud.getStudentNo() + "'";
			if(jdbcDao.findAnyThing(sql).isEmpty()){
				nojust = nojust + stud.getStudentNo() + ", ";
			}
		}
		if(!nojust.equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("MessageN1", "找不到:" + nojust + "的操行成績!"));
		}
		
		hql = "Select j From Just j Where j.departClass like '" + clazzFilter + "%'" + " Order By j.studentNo";
		//log.debug("hql : " + hql);
		justs = dao.submitQuery(hql);			
		if(justs.isEmpty() && students.isEmpty()){
			//虛擬班級沒有學生
			return msgs;
		}

		//找出所有屬於該部制學生之所有獎懲資料
		hql = "select d From Desd d Where d.departClass like '" + clazzFilter + "%'" + " Order By d.studentNo";
		desds = dao.submitQuery(hql);
			
		
		//刪除該部制班級之歷史操行成績資料
		//cond:操行及全勤, comb1:評語及體育軍訓成績, comb2:獎懲資料
		sql = "Delete From cond Where school_year=" + schoolYear 
		+ " And school_term='" + schoolTerm +"' And depart_class like '" + clazzFilter + "%'";
		jdbcDao.getJdbcTemplate().update(sql);
		
		sql = "Delete From comb1 Where school_year=" + schoolYear 
		+ " And school_term='" + schoolTerm +"' And depart_class like '" + clazzFilter + "%'";
		jdbcDao.getJdbcTemplate().update(sql);
		
		sql = "Delete From comb2 Where school_year=" + schoolYear 
		+ " And school_term='" + schoolTerm +"' And depart_class like '" + clazzFilter + "%'";
		jdbcDao.getJdbcTemplate().update(sql);

		
		log.debug("Class:" + clazzFilter + ", Students:" + students.size() +", Just size:" + justs.size());
		//將學生操行Just資料新增至Cond,Comb1
		hql = "Select c From Csno c Where chiName like '體育%'";
		phyList = dao.submitQuery(hql);
		String phySql = "", milSql = "";
		if(!phyList.isEmpty()){
			for(Csno csno:phyList){
				phySql = phySql + ",'" + csno.getCscode() + "'";
			}
			phySql = "(" + phySql.substring(1) + ")";
		}
		
		hql = "Select c From Csno c Where chiName like '軍訓%'";
		milList = dao.submitQuery(hql);
		if(!milList.isEmpty()){
			for(Csno csno:milList){
				milSql = milSql + ",'" + csno.getCscode() + "'";
			}
			milSql = "(" + milSql.substring(1) + ")";
			//System.out.println(milSql);
		}
		
		Just just = new Just();
		Student student = new Student();
		Graduate graduate = new Graduate(); 
		String csdepartClass="", stdepartClass="";
		int progress = 0;
		int count = 0;
		int len = 0;
		String comName = "";
		Double physicalScore = 0d, militaryScore = 0d;
		List<Seld> selds = new ArrayList<Seld>();
		
		if(!justs.isEmpty()) {
			// 確認該成績是屬於正常或隨班附讀,新增至ScoreHist
			for(Iterator<Just> justsIter = justs.iterator(); justsIter.hasNext();) {
				just = justsIter.next();
				Cond cond = new Cond();
				Comb1 comb1 = new Comb1();
				comName = "";
				
				cond.setSchoolYear(Short.parseShort(schoolYear));
				cond.setSchoolTerm(Short.parseShort(schoolTerm));
				cond.setStudentNo(just.getStudentNo());
				cond.setDepartClass(just.getDepartClass());
				cond.setScore(just.getTotalScore().floatValue());
				if(just.getDilgScore() == 3.0d){
					cond.setNoabsent("Y");
				}else{
					cond.setNoabsent("N");
				}
				dao.saveObject(cond);
				
				//Process comb1
				physicalScore = -1d;
				militaryScore = -1d;
				if(!phySql.equals("")){
					hql = "Select s From Seld s, Dtime d  Where s.studentNo='" + just.getStudentNo() +
					"' And s.dtimeOid=d.oid And d.sterm='" + schoolTerm + "' And d.cscode in " + phySql;
					selds = dao.submitQuery(hql);
					if(!selds.isEmpty()){
						for(Seld seld:selds){
							if(seld.getScore() != null)
								physicalScore = seld.getScore();
						}
					}
				}
				if(!milSql.equals("")){
					hql = "Select s From Seld s, Dtime d  Where s.studentNo='" + just.getStudentNo() +
					"' And s.dtimeOid=d.oid And d.sterm='" + schoolTerm + "' And d.cscode in " + milSql;
					selds = dao.submitQuery(hql);
					if(!selds.isEmpty()){
						for(Seld seld:selds){
							if(seld.getScore() != null)
								militaryScore = seld.getScore();
						}
					}
				}
				
				comb1.setSchoolYear(Short.parseShort(schoolYear));
				comb1.setSchoolTerm(Short.parseShort(schoolTerm));
				comb1.setStudentNo(just.getStudentNo());
				comb1.setDepartClass(just.getDepartClass());

				if(just.getComName1() != null)
					if(!just.getComName1().trim().equals(""))
						comName = comName + just.getComName1().trim();
				if(just.getComName2() != null)
					if(!just.getComName2().trim().equals(""))
						comName = comName + ", " + just.getComName2().trim();
				if(just.getComName3() != null)
					if(!just.getComName3().trim().equals(""))
						comName = comName + ", " + just.getComName3().trim();
				
				comb1.setComName(comName);
				if(physicalScore != -1d){
					comb1.setPhysicalScore(physicalScore.floatValue());
				}
				if(militaryScore != -1d){
					comb1.setMilitaryScore(militaryScore.floatValue());
				}
				dao.saveObject(comb1);
			}
			
		}

		//將學生獎懲Desd資料新增至Comb2
		if(!desds.isEmpty()){
			for (Iterator<Desd> desdIter = desds.iterator(); desdIter.hasNext();) {
				Desd desd = desdIter.next();
								
				Comb2 comb2 = new Comb2();

				comb2.setSchoolYear(Short.parseShort(schoolYear));
				comb2.setSchoolTerm(Short.parseShort(schoolTerm));
				comb2.setDepartClass(desd.getDepartClass());
				comb2.setStudentNo(desd.getStudentNo());
				comb2.setDdate(desd.getDdate());
				comb2.setNo(desd.getNo());
				comb2.setReason(desd.getReason());
				comb2.setKind1(desd.getKind1());
				comb2.setKind2(desd.getKind2());
				comb2.setCnt1(desd.getCnt1());
				comb2.setCnt2(desd.getCnt2());
				dao.saveObject(comb2);
			}
		}
		
		return msgs;
	}
	
	public Map findGraduateJustSort(String clazzFilter, int number, boolean haveLast){
		Map retMap = new HashMap();
		List sortCond = new ArrayList();
		List noAbsent = new ArrayList();
		DecimalFormat df = new DecimalFormat(",##0.0");

		String sql = "select distinct depart_class From stmd Where depart_class like '" + clazzFilter + "%' order by depart_class";
		List clazzes = jdbcDao.findAnyThing(sql);
		
		//找出本屆畢業班學生
		String tmp = "", clazz = "", studentNo="";
		for(Iterator mapIter=clazzes.iterator(); mapIter.hasNext();){
			clazz = ((Map)mapIter.next()).get("depart_class").toString();
			log.debug(clazz);
			if(Toolket.chkIsGraduateClass(clazz)){
				String hql = "Select s From Student s Where s.departClass='" + clazz + "' order by studentNo";

				List<Student> studs = dao.submitQuery(hql);

				if(!studs.isEmpty()){
					log.debug(clazz + ":" + studs.size());
					int terms = Toolket.getGraduateYear(clazz) * 2;
					if(!haveLast) terms--;
					List<Cond> conds = new ArrayList();
					List origCond = new ArrayList();
					short syear=0, sterm=0;
					boolean isAbsent = false;
					int count = 0;
					float avgScore = 0f;
					tmp = "";
					for(Student student:studs){
						hql = "select c From Cond c Where studentNo='" + student.getStudentNo() +
								"' Order by schoolYear,schoolTerm";
						conds = dao.submitQuery(hql);

						avgScore = 0f;
						isAbsent = false;
						count = 0;
						for(Cond cond:conds){
							if(count >= terms) break;
							avgScore = avgScore + cond.getScore();
							if(cond.getNoabsent().equalsIgnoreCase("N")){
								isAbsent = true;
							}
							count++;
						}

						Map condScore = new HashMap();
						condScore.put("departClass", student.getDepartClass());
						condScore.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
						condScore.put("studentNo", student.getStudentNo());
						condScore.put("studentName", student.getStudentName());
						condScore.put("score", df.format(avgScore/terms));
						
						if(conds.size() != terms){
							if(haveLast){
								condScore.put("notSort", true);
								condScore.put("noAbsent", false);
							}else if((conds.size()-1) == terms){
								condScore.put("notSort", false);
								condScore.put("noAbsent", !isAbsent);
								tmp = tmp + ",'" + student.getStudentNo() + "'";
							}else{
								condScore.put("notSort", true);
								condScore.put("noAbsent", false);
							}
						}else{
							condScore.put("notSort", false);
							condScore.put("noAbsent", !isAbsent);
							tmp = tmp + ",'" + student.getStudentNo() + "'";
						}
						

						/*
						if(haveLast && (conds.size() < terms || conds.size() > terms)){
							condScore.put("notSort", true);
						}else if(!haveLast && (conds.size() < (terms-1) || conds.size() > (terms-1))){
							condScore.put("notSort", true);
						*/
						origCond.add(condScore);
					}
					
					if(!tmp.equals("")){
						//排名
						tmp = "(" + tmp.substring(1) + ")";
						sql = "Select c.student_no As studentNo, sum(c.score) As total From cond c Where c.student_no in " + tmp +
								" Group by c.student_no order by total DESC";
						List orderList = jdbcDao.findAnyThing(sql);
						log.debug("orderList size:" + orderList.size());
						
						//取出前N名
						count = 1;
						for(Iterator orIter=orderList.iterator(); orIter.hasNext();){
							Map orMap = (Map)orIter.next();
							//log.debug("Map:" + orMap);
							studentNo = orMap.get("studentNo").toString();
							if(count > number) break;
							for(Iterator origIter=origCond.iterator(); origIter.hasNext();){
								Map condMap = (Map)origIter.next();
								Map sortMap = new HashMap();
								sortMap.putAll(condMap);
								
								//log.debug("condMap:" + condMap);
								//log.debug("noSort:" + condMap.get("noSort"));
								if(condMap.get("studentNo").toString().equalsIgnoreCase(studentNo) && condMap.get("notSort").equals(false)){
									sortMap.put("order", "第 " + count + " 名");
									log.debug("condMap:" + sortMap);
									sortCond.add(sortMap);
									count++;
								}
							}
						}
						
						//取出全勤名單
						for(Iterator origIter=origCond.iterator(); origIter.hasNext();){
							Map condMap = (Map)origIter.next();
							if(condMap.get("noAbsent").equals(true))
								noAbsent.add(condMap);
						}
					}
				}
			}	//if isGraduateClass
		}	//for clazz
		
		retMap.put("sortCond", sortCond);
		retMap.put("noAbsent", noAbsent);
		return retMap;
	}
	
	public List<Map> findRacingScore(String GroupNo, String WeekStart, String WeekEnd){
		List<Map> retList = new ArrayList();
		String hql = "";
		DecimalFormat df = new DecimalFormat(",##0.0");
		Calendar[] cals = Toolket.getDateBetweenWeeks(Integer.parseInt(WeekStart), Integer.parseInt(WeekEnd));
		//log.debug("start:" + cals[0] + "\n end:" + cals[1]);
		
		//used for totalScore compare
		class scoreComp implements Comparator {
			public int compare(Object obj1, Object obj2){
				if(obj1 instanceof Map && obj2 instanceof Map){
					float score1 = Float.parseFloat(((Map)obj1).get("SumScore").toString());
					float score2 = Float.parseFloat(((Map)obj2).get("SumScore").toString());
					
					if(score1<score2)	return 1;
					else if(score1==score2) return 0;
					else return -1;
				}
				return 0;
			}
			
			public boolean equals(Object obj){
				return super.equals(obj);
			}
		}

		hql = "Select Distinct departClass As clazz From Student ";
		
		if(GroupNo.equals("1")){
			hql = hql + "Where departClass like '164_1%' Or departClass like '112_1%' ";
		}else if(GroupNo.equals("2")){
			hql = hql + "Where departClass like '164_2%' Or departClass like '112_2%' ";
		}else if(GroupNo.equals("3")){
			hql = hql + "Where departClass like '164_3%' Or departClass like '142_1%' Or departClass like '11253%' ";
		}else if(GroupNo.equals("4")){
			hql = hql + "Where departClass like '164_4%' Or departClass like '142_2%' ";
		}
		
		
		if(GroupNo.equals("5")){
			hql = hql + "Where departClass like '264%' ";
		}
		
		
		
		
		
		
		
		
		hql = hql + " order by departClass";
		List<String> clazzList = dao.submitQuery(hql);
		
		int[] dilgcnt = new int[6];
		int[] desdcnt = new int[6];
		float rtScore = 0f;
		float cnScore = 0f;
		float lnScore = 0f;
		float absAvg = 0f;
		float lifeScore = 0f;
		float healthScore = 0f;
		float publicScore = 0f;
		float totalScore = 0f;
		float meetingScore = 0f;
		
		log.debug(clazzList.get(0));
		for(String clazz:clazzList){
			rtScore = 0f;
			cnScore = 0f;
			lnScore = 0f;
			absAvg = 0f;
			lifeScore = 0f;
			healthScore = 0f;
			totalScore = 0f;
			
			//String clazz = clazzMap.get("clazz").toString();
			Map scoreMap = new HashMap();
			scoreMap.put("departClass", clazz);
			scoreMap.put("deptClassName", Toolket.getClassFullName(clazz));
			//規定傳達成績
			hql = "From RuleTran Where departClass='" + clazz + "'";
			List<RuleTran> rtList = dao.submitQuery(hql);
			if(rtList.isEmpty()){
				scoreMap.put("ruleTranScore", "0");
				scoreMap.put("lifeScore", "0");
			}else{
				rtScore = rtList.get(0).getScore();
				meetingScore = rtList.get(0).getMscore();
				scoreMap.put("ruleTranScore", df.format(rtScore));
				
				/*
				if(rtScore >= 100f){
					scoreMap.put("lifeScore", "15");
					lifeScore = 15f;
				}else{
					scoreMap.put("lifeScore", df.format(rtScore * 0.15f));
					lifeScore = rtScore * 0.15f;
				}
				*/
				//由學務自行輸入比例成績,系統不需要再計算*0.15 2010-01-11
				scoreMap.put("lifeScore", df.format(rtScore));
				lifeScore = rtScore;

			}
			
			//整潔競賽成績
			hql = "Select c From Clean c Where departClass='" + clazz +
					"' And weekNo Between " + WeekStart + " And " + WeekEnd + " order by weekNo";
			List<Clean> cnList = dao.submitQuery(hql);
			if(!cnList.isEmpty()){
				for(Clean clean:cnList){
					try{
						cnScore += clean.getScore();
					}catch(Exception e){
						cnScore =0;
					}
					
				}
				scoreMap.put("cleanScore", df.format(cnScore/cnList.size()));
				scoreMap.put("healthScore", df.format(cnScore/cnList.size() * 0.3f));
				healthScore = cnScore/cnList.size() * 0.3f;
			}
			
			//礦缺病統計
			/*
			 * dilgcnt[0]:曠課少於10節, dilgcnt[1]:曠課11~20節, dilgcnt[2]:曠課21~30節,
			 * dilgcnt[3]:曠課31~40節, dilgcnt[4]:曠課40節以上, dilgcnt[5]:病假總節數
			 */
			for(int i = 0; i <6; i++){
				dilgcnt[i] = 0;
			}
			int count = 0;
			hql = "Select s From Student s Where s.departClass='" + clazz + "'";
			List<Student> studs = dao.submitQuery(hql);
			
			for(Student student:studs){
				count = 0;
				hql = "Select d From Dilg d Where d.studentNo=? And ddate between ? And ? order by ddate";
				Object[] objs = {student.getStudentNo(), cals[0].getTime(), cals[1].getTime()};
				List<Dilg> dilgs = dao.submitQuery(hql, objs);
				
				if(!dilgs.isEmpty()){
					for(Dilg dilg:dilgs){
						if(dilg.getAbs1()!=null){
							if(dilg.getAbs1() == 2){
								count++;
							}else if(dilg.getAbs1() == 3 || dilg.getAbs1() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs2()!=null){
							if(dilg.getAbs2() == 2){
								count++;
							}else if(dilg.getAbs2() == 3 || dilg.getAbs2() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs3()!=null){
							if(dilg.getAbs3() == 2){
								count++;
							}else if(dilg.getAbs3() == 3 || dilg.getAbs3() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs4()!=null){
							if(dilg.getAbs4() == 2){
								count++;
							}else if(dilg.getAbs4() == 3 || dilg.getAbs4() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs5()!=null){
							if(dilg.getAbs5() == 2){
								count++;
							}else if(dilg.getAbs5() == 3 || dilg.getAbs5() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs6()!=null){
							if(dilg.getAbs6() == 2){
								count++;
							}else if(dilg.getAbs6() == 3 || dilg.getAbs6() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs7()!=null){
							if(dilg.getAbs7() == 2){
								count++;
							}else if(dilg.getAbs7() == 3 || dilg.getAbs7() == 4){
								dilgcnt[5]++;
							}
						}
						if(dilg.getAbs8()!=null){
							if(dilg.getAbs8() == 2){
								count++;
							}else if(dilg.getAbs8() == 3 || dilg.getAbs8() == 4){
								dilgcnt[5]++;
							}
						}
					}	//for dilgs
					if(count > 40)
						dilgcnt[4]++;
					else
						dilgcnt[(int)(count-1)/10]++;
				}
			}	//for studs
			lnScore = 30f;
			//if(dilgcnt[1] == 0 && dilgcnt[1] == 0 && dilgcnt[2] == 0 && dilgcnt[3] == 0 && dilgcnt[4] == 0){
			//	scoreMap.put("timeOff10", ""+dilgcnt[0]);
			//	scoreMap.put("learnScore", "35.0");
			//	lnScore = 35f;
			//}else{
				lnScore -= dilgcnt[1] * 0.5;
				lnScore -= dilgcnt[2] * 1;
				lnScore -= dilgcnt[3] * 1.5;
				lnScore -= dilgcnt[4] * 2;
				absAvg = dilgcnt[5]/studs.size();
				if(absAvg <= 5.0f){
					//lnScore += 5;
				}else{
					lnScore -= (Math.round(absAvg)-5);	//超過5.1(四捨五入)節,每增加1節扣1分
				}
				scoreMap.put("timeOff10", "");
				scoreMap.put("learnScore", df.format(lnScore));
			//}
			scoreMap.put("timeOff20", "" + dilgcnt[1]);
			scoreMap.put("timeOff30", "" + dilgcnt[2]);
			scoreMap.put("timeOff40", "" + dilgcnt[3]);
			scoreMap.put("timeOffmuch", "" + dilgcnt[4]);
			scoreMap.put("absent", df.format(absAvg));
			
			//處理獎懲公民生活得分
			desdcnt[0] = 0;	//大功
			desdcnt[1] = 0;	//小功
			desdcnt[2] = 0;	//嘉獎
			desdcnt[3] = 0;	//大過
			desdcnt[4] = 0;	//小過
			desdcnt[5] = 0;	//申誡
			
			hql = "Select d From Desd d Where d.departClass=? And d.actIllegal='Y' And ddate between ? And ? order by ddate";
			Object[] objs = {clazz, cals[0].getTime(), cals[1].getTime()};
			List<Desd> desds = dao.submitQuery(hql, objs);
			
			int knd = 0;
			for(Desd desd:desds){
				if(desd.getKind1() != null){
					knd = Integer.parseInt(desd.getKind1())-1;
					desdcnt[knd] += desd.getCnt1();
				}
				if(desd.getKind2() != null){
					//System.out.println(desd.getKind2());
					try{
						knd = Integer.parseInt(desd.getKind2())-1;
						desdcnt[knd] += desd.getCnt2();
					}catch(Exception e){
						knd = 0;
					}
					
					
					/*
					switch(desd.getKind2().charAt(0)){
					case '1':
						desdcnt[0]+=desd.getCnt2();
						break;
					case '2':
						desdcnt[1]+=desd.getCnt2();
						break;
					case '4':
						desdcnt[2]+=desd.getCnt2();
						break;
					case '5':
						desdcnt[3]+=desd.getCnt2();
						break;
					}
					*/
				}
			}
			//100.16.17 學務需求更改
			//publicScore = 25.0f + meetingScore + desdcnt[0] * 1f + desdcnt[1] * 0.5f - desdcnt[2] * 1f - desdcnt[3] * 0.5f;
			publicScore = 25.0f + meetingScore + desdcnt[0] * 2f + desdcnt[1] * 0.5f + desdcnt[2] * 0.1f;
			publicScore += - desdcnt[3] * 2f - desdcnt[4] * 0.5f - desdcnt[5] * 0.1f;
			scoreMap.put("meeting", "" + meetingScore);
			scoreMap.put("bigBonus", "" + desdcnt[0]);
			scoreMap.put("smallBonus", "" + desdcnt[1]);
			scoreMap.put("miniBonus", "" + desdcnt[2]);
			scoreMap.put("bigPenalty", "" + desdcnt[3]);
			scoreMap.put("smallPenalty", "" + desdcnt[4]);
			scoreMap.put("miniPenalty", "" + desdcnt[5]);
			scoreMap.put("publicScore", df.format(publicScore));
			scoreMap.put("order", "");
			
			totalScore = lifeScore + healthScore + lnScore + publicScore;
			scoreMap.put("SumScore", df.format(totalScore));
			
			retList.add(scoreMap);
		}	//for clazz
	
		Collections.sort(retList, new scoreComp());
		int i = 1;
		for(Map scmap:retList){
			scmap.put("order", "第" + i++ +"名");
		}
		return retList;
	}
	
	public List getStudentsCompistData(String schoolYear, String schoolTerm, String departClass, String studentNo){
		List retList = new ArrayList();
		
		String hql = "Select c From Comb1 c Where schoolYear=" + schoolYear +" And schoolTerm=" + schoolTerm;
		if(!departClass.equals("")){
			hql = hql + " And departClass like '" + departClass +"%'";
		}
		if(!studentNo.equals("")){
			hql = hql + " And studentNo='" + studentNo +"'";
		}
		hql = hql + " Order by departClass, studentNo, schoolYear, schoolTerm";
		
		List<Comb1> comb1s = dao.submitQuery(hql);
		
		if(!comb1s.isEmpty()){
			DecimalFormat df = new DecimalFormat(",##0.0");
			DateFormat dayf = new SimpleDateFormat("yy/MM/dd");
			String studNo = "";
			List<Comb1> c1List = new ArrayList();
			List<Comb2> c2List = new ArrayList();
			List<Cond> cnList = new ArrayList();
			List<Stavg> stavgList = new ArrayList();
			List<Code2> code2List = new ArrayList();
			Student student = new Student();
			Graduate graduate = new Graduate();
			int grade = 0, arrpos = 0;
			String deptName = "";
			String[] termName = {"一上","一下","二上","二下","三上","三下","四上","四下","五上","五下"};
			String[] bpName = {"大功","小功","嘉獎","大過","小過","警告"};
			String bp = "";
			
			hql = "From Code5 Where category='Dept' order by idno";
			List<Code5> deptList = dao.submitQuery(hql);
			hql = "From Code5 Where category='School' order by idno";
			List<Code5> schoolList = dao.submitQuery(hql);
			
			for(Comb1 comb1:comb1s){
				Map retMap = new HashMap();
				studNo = comb1.getStudentNo();
				List<Map> bpList = new ArrayList();
				String[][] score = new String[10][4];
				String[] comment = new String[10];

				for(int i=0; i<10; i++){
					comment[i] = "";
					for(int j=0; j<4; j++)
						score[i][j] = "";
				}
				
				student = Toolket.getStudentByNo(studentNo);
				if(student == null){
					graduate = Toolket.getGraduateByNo(studentNo);
					if(graduate == null){
						return retList;
					}else{
						deptName = "";
						for(Code5 dept:deptList){
							if(dept.getIdno().trim().equalsIgnoreCase(graduate.getDepartClass().substring(3, 4))){
								deptName = dept.getName();
								break;
							}
						}
						for(Code5 school:schoolList){
							if(school.getIdno().trim().equalsIgnoreCase(graduate.getDepartClass().substring(1, 3))){
								deptName = deptName + "(" + school.getName() + ")";
								break;
							}
						}
						log.debug(deptName);
						retMap.put("deptClassName", deptName);
						retMap.put("studentNo", graduate.getStudentNo());
						retMap.put("studentName", graduate.getStudentName());
						retMap.put("id", graduate.getIdno());
						retMap.put("birthday", Toolket.printNativeDate(graduate.getBirthday(), dayf));
					}
				}else{
					deptName = "";
					for(Code5 dept:deptList){
						if(dept.getIdno().trim().equalsIgnoreCase(student.getDepartClass().substring(3, 4))){
							deptName = dept.getName();
							break;
						}
					}
					for(Code5 school:schoolList){
						if(school.getIdno().trim().equalsIgnoreCase(student.getDepartClass().substring(1, 3))){
							deptName = deptName + "(" + school.getName() + ")";
							break;
						}
					}
					log.debug(deptName);
					retMap.put("deptClassName", deptName);
					retMap.put("studentNo", student.getStudentNo());
					retMap.put("studentName", student.getStudentName());
					retMap.put("id", student.getIdno());
					retMap.put("birthday", Toolket.printNativeDate(student.getBirthday(), dayf));
				}
				
				hql = "From Cond Where studentNo='" + studNo + "' Order by schoolYear,schoolTerm";
				cnList = dao.submitQuery(hql);
				for(Cond cond:cnList){
					grade = Integer.parseInt(cond.getDepartClass().trim().substring(4, 5));
					arrpos = (grade-1)*2 + cond.getSchoolTerm()-1;
					if(cond.getScore() != null)
						score[arrpos][0] = df.format(cond.getScore());
					hql = "From Stavg Where studentNo='" + studNo + "' And schoolYear='" +
							cond.getSchoolYear() + "' And schoolTerm='" + cond.getSchoolTerm() + "' Order by schoolYear,schoolTerm";
					stavgList = dao.submitQuery(hql);
					if(!stavgList.isEmpty()){
						score[arrpos][1] = df.format(stavgList.get(0).getScore());
					}else{
						score[arrpos][1] = "";
					}
				}
				
				/*
				 * 因為 Stavg在修改歷史成績而且又找不到Stavg記錄時會被新增一筆班級是空白的資料，所以...
				 * 主要是有些特殊的班級，例如產攜班，在下學期開學時才完成上一學期課程，才有成績，???
				hql = "From Stavg Where studentNo='" + studNo + "' Order by schoolYear,schoolTerm";
				stavgList = dao.submitQuery(hql);
				for(Stavg avgscore:stavgList){
					grade = Integer.parseInt(avgscore.getDepartClass().trim().substring(4, 5));
					arrpos = (grade-1)*2 + Integer.parseInt(avgscore.getSchoolTerm())-1;
					if(avgscore.getScore() != null)
						score[arrpos][1] = df.format(avgscore.getScore());
				}
				*
				*/
				
				hql = "From Comb1 Where studentNo='" + studNo + "' Order by schoolYear,schoolTerm";
				c1List = dao.submitQuery(hql);
				for(Comb1 comb:c1List){
					grade = Integer.parseInt(comb.getDepartClass().trim().substring(4, 5));
					arrpos = (grade-1)*2 + comb.getSchoolTerm()-1;
					if(comb.getPhysicalScore() != null)
						score[arrpos][2] = df.format(comb.getPhysicalScore());
					if(comb.getMilitaryScore() != null)
						score[arrpos][3] = df.format(comb.getMilitaryScore());
					comment[arrpos] = comb.getComName();
				}
				retMap.put("score", score);
				retMap.put("comment", comment);
				
				hql = "From Comb2 Where studentNo='" + studNo + "' Order by schoolYear,schoolTerm";
				c2List = dao.submitQuery(hql);
				for(Comb2 comb2:c2List){
					Map<String, String> bpMap = new HashMap();
					grade = Integer.parseInt(comb2.getDepartClass().trim().substring(4, 5));
					arrpos = (grade-1)*2 + comb2.getSchoolTerm()-1;
					bpMap.put("occDate", termName[arrpos] + " " + Toolket.printNativeDate(comb2.getDdate(),dayf));
					
					/*
					bp = bpName[Integer.parseInt(comb2.getKind1())-1] + " " + comb2.getCnt1() + " 次";
					if(comb2.getKind2() != null && comb2.getCnt2() != null){
						if(!comb2.getKind2().trim().equals("")){
							bp = bp + ", " + bpName[Integer.parseInt(comb2.getKind2())-1] + " " 
							+ comb2.getCnt2() + " 次";
						}
					}
					*/
					
					if(comb2.getKind1() != null && comb2.getCnt1() != null && !comb2.getKind1().trim().equals("") && !comb2.getCnt1().equals("")){	
						bp = bpName[Integer.parseInt(comb2.getKind1())-1] + " " + comb2.getCnt1() + " 次";
					}
					if(comb2.getKind2() != null && comb2.getCnt2() != null && !comb2.getKind2().trim().equals("") && !comb2.getCnt2().equals("")){
						if(comb2.getKind1() != null && comb2.getCnt1() != null && !comb2.getKind1().trim().equals("") && !comb2.getCnt1().equals("")){	
							bp = bp + ", " + bpName[Integer.parseInt(comb2.getKind2())-1] + " " + comb2.getCnt2() + " 次";
						}else{
							bp = bpName[Integer.parseInt(comb2.getKind2())-1] + " " + comb2.getCnt2() + " 次";
						}
					}
					
					bpMap.put("kindcnt", bp);
					
					hql = "From Code2 Where no='" + comb2.getReason() + "'";
					code2List = dao.submitQuery(hql);
					if(!code2List.isEmpty()){
						bpMap.put("reason", code2List.get(0).getName());
					}else{
						bpMap.put("reason", "");
					}
					
					bpList.add(bpMap);
				}
				retMap.put("bpList", bpList);
				
				retList.add(retMap);
			}	//end for comb1
		}	// end if
		
		return retList;
	}
	
	public List findBP4Racing(String clazzFilter){
		List retList = new ArrayList();
		DateFormat dayf = new SimpleDateFormat("yy/MM/dd");
		String hql = "From Desd Where departClass like '" + clazzFilter + "%' And actIllegal='Y'" +
						" Order by departClass, studentNo";
		List<Desd> desdList = dao.submitQuery(hql);
		List<Student> studs = new ArrayList();
		List<Code2> c2List = new ArrayList();
		Student student = new Student();
		String[] kind = {"大功","小功","嘉獎","大過","小過","警告"};
		
		for(Desd desd:desdList){
			Map<String, String> dMap = new HashMap();
			hql ="From Student Where studentNo='" + desd.getStudentNo() + "'";
			studs = dao.submitQuery(hql);
			if(!studs.isEmpty()){
				student = studs.get(0);
			}else{
				continue;
			}
			String[] fields1 = {"clazz", "student", "ddate","kind1", "cnt1",
					 "kind2", "cnt2", "reason",	"actIllegal"};
			dMap.put("clazz",Toolket.getClassFullName(student.getDepartClass()));
			dMap.put("student",student.getStudentNo() + " " + student.getStudentName());
			dMap.put("ddate",Toolket.printNativeDate(desd.getDdate(),dayf));
			dMap.put("kind1",kind[Integer.parseInt(desd.getKind1())-1]);
			dMap.put("cnt1","" + desd.getCnt1());
			if(desd.getKind2() != null){
				dMap.put("kind2",kind[Integer.parseInt(desd.getKind2())-1]);
				dMap.put("cnt2","" + desd.getCnt2());
			}
			hql = "From Code2 Where no='" + desd.getReason() + "'";
			c2List = dao.submitQuery(hql);
			if(!c2List.isEmpty()){
				dMap.put("reason",c2List.get(0).getName());
			}else{
				dMap.put("reason","Error!!! Error!!!");
			}
			dMap.put("actIllegal","是");
			
			retList.add(dMap);
		}
		return retList;
	}
	
	public ActionMessages modifySeldElearnDilg(List<Map> edilgs){
		ActionMessages err = new ActionMessages();
		int dtimeOid = 0;
		String studentNo = "";
		int elearnDilg = 0;
		String hql = "";
		String noUpdate = "";
		int totalDilg = 0;
		int tfLimit = 0;
		String sterm = Toolket.getSysParameter("School_term");
		
		try{
			for(Map dMap:edilgs){
				dtimeOid = Integer.parseInt(dMap.get("dtOid").toString());
				studentNo = dMap.get("studentNo").toString();
				elearnDilg = Integer.parseInt(dMap.get("dilgs").toString());
				hql = "From Seld Where studentNo=? And dtimeOid=?";
				Object[] param = {studentNo, dtimeOid};
				List<Seld> seldList = dao.submitQuery(hql, param);
				
				//更新Seld遠距教學曠缺節數
				if(!seldList.isEmpty()){
					Seld seld = seldList.get(0);
					if(seld.getDilgPeriod() != null){
						totalDilg = seld.getDilgPeriod() + elearnDilg;
					}
					seld.setElearnDilg(elearnDilg);
					dao.saveObject(seld);
					log.debug("DtimeOid:" + dtimeOid + ", studentNo:" + studentNo + ", dilgs:" + elearnDilg);
				}else{
					noUpdate = noUpdate + studentNo + ":" + dtimeOid + ", ";
				}
			}
		}catch(Exception e){
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.MessageN1", e.toString()));
			e.printStackTrace();
		}
		
		/*if(!noUpdate.equals("")){
			noUpdate = noUpdate.substring(0, noUpdate.length()-2);
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "無法更新記錄： " + noUpdate));
		}*/ 
		
		return err;
	}
	
	public double[] calDilgSummary(List<Dilg> dilgList, String mode) {
		double addelScore = 0d;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		int tflen = tfTypeList.size();
		
		double[] dilgSum = new double[tflen];
		for(int k = 0; k < tflen; k++){
			dilgSum[k] = 0d;
		}
		if(dilgList.isEmpty()){
			dilgSum[0] = 3d;
			return dilgSum;
		}
		
		String sterm = Toolket.getSysParameter("School_term");
		String studentNo = dilgList.get(0).getStudentNo();
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tflen; k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
		String hql ="";

		//*** 1.process Dilg
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;

		//將該學生曠缺資料填入timeoffWeek陣列
		for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
			dilg =dilgIter.next();
			iweek = Math.round(dilg.getWeekDay());
			//iweek = this.WhatIsTheWeek(dilg.getDdate());
			if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
				tfType = dilg.getAbs0();
				timeoffWeek[iweek][0][tfType]++;
			}
			if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
				tfType = dilg.getAbs1();
				timeoffWeek[iweek][1][tfType]++;
			}
			if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
				tfType = dilg.getAbs2();
				timeoffWeek[iweek][2][tfType]++;
			}
			if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
				tfType = dilg.getAbs3();
				timeoffWeek[iweek][3][tfType]++;
			}
			if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
				tfType = dilg.getAbs4();
				timeoffWeek[iweek][4][tfType]++;
			}
			if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
				tfType = dilg.getAbs5();
				timeoffWeek[iweek][5][tfType]++;
			}
			if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
				tfType = dilg.getAbs6();
				timeoffWeek[iweek][6][tfType]++;
			}
			if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
				tfType = dilg.getAbs7();
				timeoffWeek[iweek][7][tfType]++;
			}
			if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
				tfType = dilg.getAbs8();
				timeoffWeek[iweek][8][tfType]++;
			}
			if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
				tfType = dilg.getAbs9();
				timeoffWeek[iweek][9][tfType]++;
			}
			if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
				tfType = dilg.getAbs10();
				timeoffWeek[iweek][10][tfType]++;
			}
			if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
				tfType = dilg.getAbs11();
				timeoffWeek[iweek][11][tfType]++;
			}
			if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
				tfType = dilg.getAbs12();
				timeoffWeek[iweek][12][tfType]++;
			}
			if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
				tfType = dilg.getAbs13();
				timeoffWeek[iweek][13][tfType]++;
			}
			if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
				tfType = dilg.getAbs14();
				timeoffWeek[iweek][14][tfType]++;
			}
			if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
				tfType = dilg.getAbs15();
				timeoffWeek[iweek][15][tfType]++;
			}
		}

		if(mode.equals("0")){
			//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
			//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
			//不同部制有不同之操行加減分標準
			//List subjDilg = this.findPeriodDilgByStudentNo(studentNo, dateBegin, dateEnd, "subject");
			List subjDilg = new ArrayList();

			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);

			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid=0;
			int tfsum=0;
			int tfLimit=0, elearnDilg = 0, tfwarn=0;
			int begin = 0, end = 0, status = 0;
			iweek = 0;

			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();

				if(seld.getElearnDilg() != null){
					elearnDilg = seld.getElearnDilg();
				}else{
					elearnDilg = 0;
				}
				tfsum = elearnDilg + seld.getDilgPeriod();

				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);

				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}

				hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
				List<DtimeClass> dcList = dao.submitQuery(hql);
				/*	扣考計算改由  seld.getElearnDilg() + seld.getDilgPeriod()
				 * 
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext(); ) {
					dilg = dilgIter.next();
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = WhatIsTheWeek(dilg.getDdate());

						begin = Integer.parseInt(dclass.getBegin().trim());
						end = Integer.parseInt(dclass.getEnd().trim());
						//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
						if(iweek == dclass.getWeek()) {
							for(int j=begin; j <= end; j++){
								status = 0;
								switch (j){
								case 0:
									if(dilg.getAbs0() != null)
										status = dilg.getAbs0();
									break;
								case 1:
									if(dilg.getAbs1() != null)
										status = dilg.getAbs1();
									break;
								case 2:
									if(dilg.getAbs2() != null)
										status = dilg.getAbs2();
									break;
								case 3:
									if(dilg.getAbs3() != null)
										status = dilg.getAbs3();
									break;
								case 4:
									if(dilg.getAbs4() != null)
										status = dilg.getAbs4();
									break;
								case 5:
									if(dilg.getAbs5() != null)
										status = dilg.getAbs5();
									break;
								case 6:
									if(dilg.getAbs6() != null)
										status = dilg.getAbs6();
									break;
								case 7:
									if(dilg.getAbs7() != null)
										status = dilg.getAbs7();
									break;
								case 8:
									if(dilg.getAbs8() != null)
										status = dilg.getAbs8();
									break;
								case 9:
									if(dilg.getAbs9() != null)
										status = dilg.getAbs9();
									break;
								case 10:
									if(dilg.getAbs10() != null)
										status = dilg.getAbs10();
									break;
								case 11:
									if(dilg.getAbs11() != null)
										status = dilg.getAbs11();
									break;
								case 12:
									if(dilg.getAbs12() != null)
										status = dilg.getAbs12();
									break;
								case 13:
									if(dilg.getAbs13() != null)
										status = dilg.getAbs13();
									break;
								case 14:
									if(dilg.getAbs14() != null)
										status = dilg.getAbs14();
									break;
								case 15:
									if(dilg.getAbs15() != null)
										status = dilg.getAbs15();
									break;

								}
								if(!(status == 0 || status == 5 || status == 6)) {
									tfsum++;
								}

							}
						}
					}	//End for DtimeClass
				}	//End for Dilg
				
				*/
				if(tfsum>=tfLimit){							
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = dclass.getWeek();
						for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
							for(int n =0; n < tflen; n++){
								timeoffWeek[iweek][m][n] = 0;
							}
						}
					}

				}
				
			}	//End for seld

		}	//End if

		String depart = this.chkStudentDepart(studentNo);
		short[] dilgSubtotal = new short[tflen]; 
		for(int z = 0; z < tflen; z++){
			dilgSubtotal[z] = 0;
		}

		//統計該學生曠缺種類及其次數
		for(int i = 1; i < 8; i++) {
			for(int j = 0; j <16; j++){
				for(int k = 1; k<tflen; k++ ){
					dilgSubtotal[k] = (short)(dilgSubtotal[k] + timeoffWeek[i][j][k]);
				}
			}
		}

		//全勤加3分,有曠缺按部制規定扣分
		if(dilgSubtotal[1] == 0 && dilgSubtotal[2] == 0 && dilgSubtotal[3] == 0 
				&& dilgSubtotal[4] == 0 && dilgSubtotal[5] == 0 && dilgSubtotal[7] == 0 
				&& dilgSubtotal[8] == 0 && dilgSubtotal[9] == 0) {
			addelScore = addelScore + 3d;
		}else{
			if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
				addelScore = addelScore + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
				addelScore = addelScore + dilgSubtotal[2] * 0.5;
				addelScore = addelScore + dilgSubtotal[3] * 0.1;
				addelScore = addelScore + dilgSubtotal[4] * 0.2;
				addelScore = addelScore + dilgSubtotal[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
				//addelScore = addelScore + dilgSubtotal[6] * 0.0;
				//addelScore = addelScore + dilgSubtotal[7] * 0.0;
				addelScore = addelScore * -1d;
			} else if(depart.equals("3")) {
				addelScore = addelScore + dilgSubtotal[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
				addelScore = addelScore + dilgSubtotal[2] * 1.0;
				if(dilgSubtotal[3] > 26)
					addelScore = addelScore + 1 + (dilgSubtotal[3]-26) * 0.1;
				if(dilgSubtotal[4] > 13)
					addelScore = addelScore + 1 + (dilgSubtotal[4]-13) * 0.1;
				if(dilgSubtotal[5] > 4)
					addelScore = addelScore + 1 + (dilgSubtotal[5]-4) * 0.1;
				addelScore = addelScore * -1d;
			}
		}
											
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore > 0d)
			addelScore = Math.round(addelScore * 100d) / 100d;
		else if(addelScore < 0d)
			addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
		timeoffWeek = null;
		depClass = null;
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		for(int k=1; k<tflen; k++){
			dilgSum[k] = dilgSubtotal[k];
		}
		dilgSum[0] = addelScore;
		return dilgSum;
	}

	public double[] calDilgOneSummary(String studentNo, String mode) {
		double addelScore = 0d;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		int tflen = tfTypeList.size();
		
		double[] dilgSum = new double[tflen];
		for(int k = 0; k < tflen; k++){
			dilgSum[k] = 0d;
		}
		
		String hql = "From DilgOne Where studentNo='" + studentNo + "' order by ddate, period" ;
		List<DilgOne> dilgList = dao.submitQuery(hql);
		if(dilgList.isEmpty()){
			dilgSum[0] = 3d;
			return dilgSum;
		}
		
		String sterm = Toolket.getSysParameter("School_term");
		String sql = "", subsql = "";
		if(mode.equals("1")){
			subsql = " And no_exam=0";
		}
		
		for(int k = 1; k<tflen; k++){
			sql = "Select count(*) From Dilg_One Where student_no='" + studentNo + "'";
			sql += " And abs=" + k;
			sql = sql + subsql;
			dilgSum[k] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
			//if(studentNo.equalsIgnoreCase("97144059")){
				//log.debug(sql);
				//log.debug("dilgSum[" + k + "]:" +dilgSum[k]);
			//}
		}

		String depart = this.chkStudentDepart(studentNo);

		//全勤加3分,有曠缺按部制規定扣分
		if(dilgSum[1] == 0 && dilgSum[2] == 0 && dilgSum[3] == 0 
				&& dilgSum[4] == 0 && dilgSum[5] == 0 && dilgSum[7] == 0 
				&& dilgSum[8] == 0 && dilgSum[9] == 0) {
			addelScore = addelScore + 3d;
		}else{
			if(depart.equals("1")||depart.equals("2")) {	//日間部及進修部計算曠缺操行扣分標準相同
				addelScore = addelScore + dilgSum[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
				addelScore = addelScore + dilgSum[2] * 0.5;
				addelScore = addelScore + dilgSum[3] * 0.1;
				addelScore = addelScore + dilgSum[4] * 0.2;
				addelScore = addelScore + dilgSum[5] * 0.1;	//96.12.10學務會議更改 0.25 -> 0.1
				//addelScore = addelScore + dilgSubtotal[6] * 0.0;
				//addelScore = addelScore + dilgSubtotal[7] * 0.0;
				addelScore = addelScore * -1d;
			} else if(depart.equals("3")) {
				addelScore = addelScore + dilgSum[1] * 0.05;	//99.01.20 升旗改為重大傷病,扣0.05分
				addelScore = addelScore + dilgSum[2] * 1.0;
				if(dilgSum[3] > 26)
					addelScore = addelScore + 1 + (dilgSum[3]-26) * 0.1;
				if(dilgSum[4] > 13)
					addelScore = addelScore + 1 + (dilgSum[4]-13) * 0.1;
				if(dilgSum[5] > 4)
					addelScore = addelScore + 1 + (dilgSum[5]-4) * 0.1;
				addelScore = addelScore * -1d;
			}
		}
											
		//log.debug("calConductScoreOfDilgDesd->desd" + debugs);
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		
		if(addelScore > 0d)
			addelScore = Math.round(addelScore * 100d) / 100d;
		else if(addelScore < 0d)
			addelScore = Math.round(addelScore * 100d * -1d) / 100d * -1d;
		//log.debug("calConductScoreOfDilgDesd->studentNo:dilgScore:desdScore:[" + studentNo + "][" + addelScore[0] + "][" + addelScore[1] + "]");
		dilgSum[0] = addelScore;
		
		return dilgSum;
	}

	public int[] dilgSummary(List<Dilg> dilgList, String mode){
		log.debug("dilgSummary start:" + new Date());
		int tflen = Global.TimeOffList.size();
		int[] timeoff = new int[2];
		timeoff[0] = 0;
		timeoff[1] = 0;
		
		if(dilgList.isEmpty()){
			return timeoff;
		}
		
		String sterm = Toolket.getSysParameter("School_term");
		String studentNo = dilgList.get(0).getStudentNo();
		// timeoffWeek[星期1~7][0~15節][曠缺種類統計0:(nonuse)1:升旗,2:曠課...]
		byte[][][] timeoffWeek = new byte[8][16][tflen];
		
		String[][] depClass = new String[8][16];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j <16; j++){
				depClass[i][j] = "";

				for(int k = 0; k < tflen; k++){
					timeoffWeek[i][j][k] = 0;
				}
			}
		}
		
		String hql ="";

		//*** 1.process Dilg
		Dilg dilg;
		short tfType = 0;
		int iweek = 0;

		//將該學生曠缺資料填入timeoffWeek陣列
		for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext();) {
			dilg =dilgIter.next();
			iweek = Math.round(dilg.getWeekDay());
			//iweek = this.WhatIsTheWeek(dilg.getDdate());
			if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0){
				tfType = dilg.getAbs0();
				timeoffWeek[iweek][0][tfType]++;
			}
			if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0){
				tfType = dilg.getAbs1();
				//log.debug("tflen:" + tflen + ", tftype:" + tfType + ", iweek:" + iweek);
				timeoffWeek[iweek][1][tfType]++;
			}
			if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0){
				tfType = dilg.getAbs2();
				timeoffWeek[iweek][2][tfType]++;
			}
			if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0){
				tfType = dilg.getAbs3();
				timeoffWeek[iweek][3][tfType]++;
			}
			if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0){
				tfType = dilg.getAbs4();
				timeoffWeek[iweek][4][tfType]++;
			}
			if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0){
				tfType = dilg.getAbs5();
				timeoffWeek[iweek][5][tfType]++;
			}
			if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0){
				tfType = dilg.getAbs6();
				timeoffWeek[iweek][6][tfType]++;
			}
			if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0){
				tfType = dilg.getAbs7();
				timeoffWeek[iweek][7][tfType]++;
			}
			if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0){
				tfType = dilg.getAbs8();
				timeoffWeek[iweek][8][tfType]++;
			}
			if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0){
				tfType = dilg.getAbs9();
				timeoffWeek[iweek][9][tfType]++;
			}
			if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0){
				tfType = dilg.getAbs10();
				timeoffWeek[iweek][10][tfType]++;
			}
			if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0){
				tfType = dilg.getAbs11();
				timeoffWeek[iweek][11][tfType]++;
			}
			if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0){
				tfType = dilg.getAbs12();
				timeoffWeek[iweek][12][tfType]++;
			}
			if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0){
				tfType = dilg.getAbs13();
				timeoffWeek[iweek][13][tfType]++;
			}
			if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0){
				tfType = dilg.getAbs14();
				timeoffWeek[iweek][14][tfType]++;
			}
			if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0){
				tfType = dilg.getAbs15();
				timeoffWeek[iweek][15][tfType]++;
			}
		}

		if(mode.equals("0")){
			//計算該學生每一門課程是否已達扣考門檻,決定是否消去 timeoffWeek 中的曠缺資料
			//在 String[][] depClass 中填入該節次所上課程之開課班級,以確定如何計算操行扣分
			//不同部制有不同之操行加減分標準
			//List subjDilg = this.findPeriodDilgByStudentNo(studentNo, dateBegin, dateEnd, "subject");
			List subjDilg = new ArrayList();

			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);

			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid=0;
			int tfsum=0;
			int tfLimit=0, elearnDilg = 0, tfwarn=0;
			int begin = 0, end = 0, status = 0;
			iweek = 0;

			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();

				if(seld.getElearnDilg() != null){
					elearnDilg = seld.getElearnDilg();
				}else{
					elearnDilg = 0;
				}
				tfsum = elearnDilg + seld.getDilgPeriod();

				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);

				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}

				hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
				List<DtimeClass> dcList = dao.submitQuery(hql);
				/*
				for(Iterator<Dilg> dilgIter=dilgList.iterator(); dilgIter.hasNext(); ) {
					dilg = dilgIter.next();
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = WhatIsTheWeek(dilg.getDdate());

						begin = Integer.parseInt(dclass.getBegin().trim());
						end = Integer.parseInt(dclass.getEnd().trim());
						//log.debug("calTimeOffBySubject->DtimeClass->:begin,end:[" + begin + ","+ end + "]");
						if(iweek == dclass.getWeek()) {
							for(int j=begin; j <= end; j++){
								status = 0;
								switch (j){
								case 0:
									if(dilg.getAbs0() != null)
										status = dilg.getAbs0();
									break;
								case 1:
									if(dilg.getAbs1() != null)
										status = dilg.getAbs1();
									break;
								case 2:
									if(dilg.getAbs2() != null)
										status = dilg.getAbs2();
									break;
								case 3:
									if(dilg.getAbs3() != null)
										status = dilg.getAbs3();
									break;
								case 4:
									if(dilg.getAbs4() != null)
										status = dilg.getAbs4();
									break;
								case 5:
									if(dilg.getAbs5() != null)
										status = dilg.getAbs5();
									break;
								case 6:
									if(dilg.getAbs6() != null)
										status = dilg.getAbs6();
									break;
								case 7:
									if(dilg.getAbs7() != null)
										status = dilg.getAbs7();
									break;
								case 8:
									if(dilg.getAbs8() != null)
										status = dilg.getAbs8();
									break;
								case 9:
									if(dilg.getAbs9() != null)
										status = dilg.getAbs9();
									break;
								case 10:
									if(dilg.getAbs10() != null)
										status = dilg.getAbs10();
									break;
								case 11:
									if(dilg.getAbs11() != null)
										status = dilg.getAbs11();
									break;
								case 12:
									if(dilg.getAbs12() != null)
										status = dilg.getAbs12();
									break;
								case 13:
									if(dilg.getAbs13() != null)
										status = dilg.getAbs13();
									break;
								case 14:
									if(dilg.getAbs14() != null)
										status = dilg.getAbs14();
									break;
								case 15:
									if(dilg.getAbs15() != null)
										status = dilg.getAbs15();
									break;

								}
								if(!(status == 0 || status == 5 || status == 6)) {
									tfsum++;
								}

							}
						}
					}	//End for DtimeClass
				}	//End for Dilg
				*/
				if(tfsum>=tfLimit){							
					for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
						DtimeClass dclass = dcIter.next();
						iweek = dclass.getWeek();
						for(int m = Integer.parseInt(dclass.getBegin()); m <= Integer.parseInt(dclass.getEnd()); m++) {
							for(int n =0; n < tflen; n++){
								timeoffWeek[iweek][m][n] = 0;
							}
							//if(studentNo.equalsIgnoreCase("97144033"))
							//	log.debug("扣考:studentNo:" + studentNo + ",subject:" + dclass.getDtimeOid() + ", week:" + iweek + ", period:" + m );
						}
					}

				}
			}	//End for seld

		}	//End if
		
		
		//將資料填入timeoff曠課紀錄中
		for(int i = 1; i < 8; i++) {
			for(int j = 0; j <16; j++){
				if(j==0){
					if(timeoffWeek[i][j][0] != 0){
						timeoff[0]++;
					}
				}else{
					if(timeoffWeek[i][j][2]!=0){
						timeoff[1] += timeoffWeek[i][j][2];
						//if(studentNo.equalsIgnoreCase("97144033"))
						//	log.debug("studentNo:" + studentNo + ", week:" + i + ", period:" + j );
					}
				}
			}
		}
		log.debug("dilgSummary return:" + new Date());
		return timeoff;
	}
	
	public List<Progress> findProgress(String name){
		/* 只取得兩天內的Progress
		Calendar today = Calendar.getInstance();
		Calendar yesterday = today;
		yesterday.add(Calendar.DATE, -1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		yesterday.set(Calendar.MILLISECOND, 0);
		*/
		String hql = "From Progress Where name='" + name + "' order by ddate DESC";
		List<Progress> prgList = dao.submitQuery(hql);
		
		//if(!prgList.isEmpty()){
		//	msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
		//}
		
		return prgList;
	}
	
	public Progress setTF4PProgress(String name, String parameter){
		//ActionMessages msgs = new ActionMessages();
		Progress progress = null;
		boolean isRunning = false;
		
		String hql = "From Progress Where name='" + name + "' and running>0 order by ddate DESC";
		List<Progress> prgList = dao.submitQuery(hql);
		
		if(!prgList.isEmpty()){
			String[] parameters = parameter.split("\\|");

			for(Progress prg:prgList){
				String[] pars = prg.getParameter().split("\\|");
				//相同部制不能同時執行
				if(parameters[0].startsWith(pars[0]) || pars[0].startsWith(parameters[0])){
					isRunning = true;
					//msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
				}
			}
		}
		if(!isRunning){
			progress = new Progress();
			progress.setName(name);
			progress.setParameter(parameter);
			progress.setDdate(Calendar.getInstance().getTime());
			progress.setRunning((byte)1);
			dao.saveObject(progress);
		}
		dao.reload(progress);
		return progress;
	}
	
	public ActionMessages resetTF4PProgress(Progress prgs){
		ActionMessages msgs = new ActionMessages();
		try{
			//dao.reload(prgs);
			//prgs.setRunning((byte)0);
			//dao.saveObject(prgs);
			String sql = "Update Progress set running=0 Where Oid=" + prgs.getOid();
			jdbcDao.updateAnyThing(sql);
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.toString()));
		}
		return msgs;
	}
	
	public List<Code1> findConductMarkCode(String code){
		List<Code1> codeList = new ArrayList<Code1>();
		String hql = "From Code1 Where no like '" + code + "%'";
		codeList = dao.submitQuery(hql);
		return codeList;
	}
	
	public ActionMessages modifyConductMarkCode(Code1 code, Map formMap){
		ActionMessages msgs = new ActionMessages();
		//String no = formMap.get("no").toString();
		String name = formMap.get("name").toString();
		
		try{
			//code.setNo(no);
			code.setName(name);
			dao.saveObject(code);
		}catch(Exception e){
			e.printStackTrace();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1",e.toString()));
		}
		return msgs;
	}
	
	
	public ActionMessages createConductMarkCode(Map formMap){
		ActionMessages msgs = new ActionMessages();
		String no = formMap.get("no").toString();
		String name = formMap.get("name").toString();
		
		String sql = "select * from code1 Where no='" + no + "'";
		List<Code1> codeList = jdbcDao.findAnyThing(sql);
		if(!codeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","代碼:" + no + ",資料已存在!"));
		}else{
			Code1 code = new Code1();
			code.setNo(no);
			code.setName(name);
			dao.saveObject(code);
		}
		return msgs;
	}
	
	public ActionMessages delConductMarkCode(List<Code1> selCodes, ResourceBundle bundle){
		ActionMessages msgs = new ActionMessages();
		
		for(Code1 code:selCodes){
			try{
				dao.removeObject(code);
			}catch(Exception e){
				e.printStackTrace();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1",e.toString()));
			}
		}
		return msgs;
	}
	
	public List<Code2> findBonusPenaltyReasonCode(String code){
		List<Code2> codeList = new ArrayList<Code2>();
		String hql = "From Code2 Where no like '" + code + "%'";
		codeList = dao.submitQuery(hql);
		return codeList;
	}
	
	public ActionMessages modifyBonusPenaltyReasonCode(Code2 code, Map formMap){
		ActionMessages msgs = new ActionMessages();
		//String no = formMap.get("no").toString();
		String name = formMap.get("name").toString();
		
		try{
			//code.setNo(no);
			code.setName(name);
			dao.saveObject(code);
		}catch(Exception e){
			e.printStackTrace();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1",e.toString()));
		}
		return msgs;
	}
	
	
	public ActionMessages createBonusPenaltyReasonCode(Map formMap){
		ActionMessages msgs = new ActionMessages();
		String no = formMap.get("no").toString();
		String name = formMap.get("name").toString();
		
		String sql = "select * from code2 Where no='" + no + "'";
		List<Code1> codeList = jdbcDao.findAnyThing(sql);
		if(!codeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","代碼:" + no + ",資料已存在!"));
		}else{
			Code2 code = new Code2();
			code.setNo(no);
			code.setName(name);
			dao.saveObject(code);
		}
		return msgs;
	}
	
	public ActionMessages delBonusPenaltyReasonCode(List<Code2> selCodes, ResourceBundle bundle){
		ActionMessages msgs = new ActionMessages();
		
		for(Code2 code:selCodes){
			try{
				dao.removeObject(code);
			}catch(Exception e){
				e.printStackTrace();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1",e.toString()));
			}
		}
		return msgs;
	}
	
	public ActionMessages timeOffDataExchange(String depart, String DateEnd, String pmode){
		ActionMessages msgs = new ActionMessages();
		String sql = "";
		String campSchool = "";
		String runlogs = "";
		String sterm = Toolket.getSysParameter("School_term");
		int inProcess = 0;
		double percentage = 0d;
		List tList = new ArrayList();

		Date eDate = new Date();
		Calendar eDateCal = Calendar.getInstance();
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		eDateCal.setTime(eDate);
		
		/*All timeoff data Insert into table Dilg_Temp */
		try{
			sql = "Delete From Dilg_One Where depart_class like '" + depart + "%'";
			jdbcDao.executesql(sql);
			
			String subsql = "Select d.depart_class,d.student_no,d.week_day,d.ddate,daynite,";
			String[] cond = new String[16];
			for(int q=1; q<=15; q++){
				cond[q] = q + ",d.abs" + q + ",0 From Dilg d, stmd s Where (d.abs" + q + " in(2,3,4,5,7))";
			}
			String subsql_1 = " And d.depart_class like '" + depart + "%'";
			subsql_1 += " And d.ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
			subsql_1 += " And d.student_no=s.student_no";
			subsql_1 += " Order by d.depart_class,d.student_no,d.ddate";
			
			sql =  "Insert into Dilg_One(depart_class,student_no,week_day,ddate,daynite,period,abs,no_exam) (" + subsql + 
					"0,abs0,0 From Dilg d, stmd s Where d.abs0 is not null" + subsql_1 + ")";
			jdbcDao.executesql(sql);
			int lbegin = 1, lend = 15;
			String daynite = Toolket.chkDayNite(depart);
			if(daynite.equals("1")){
				//lend = 10; 日間部學生有修進修部和進修學院課程
				lend = 15;
			}else if(daynite.equals("2")){
				lend = 15;
			}else if(daynite.equals("3")){
				lend = 15;
			}
			for(int q=lbegin; q<=lend; q++){
				sql = "Insert into Dilg_One(depart_class,student_no,week_day,ddate,daynite,period,abs,no_exam) (" + subsql + cond[q] + subsql_1 + ")";
				jdbcDao.executesql(sql);
			}
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.toString()));
			return msgs;
		}
		/*End --- All timeoff data Insert into table Dilg_One */
		

		//TODO:
		if(pmode.equals("1")){	//扣考科目之曠缺註記
			setRunStatus("timeOffDataExchange", "start!", 0, 0, 0d, "no");
			
			List<Clazz> cList = Toolket.findAllClasses(depart);
		
			int percentUnit = 1;
			int pcnt = cList.size();
			if (pcnt > 100) {
				percentUnit = (int) (Math.floor(pcnt / 100));
			} else {
				percentUnit = 1;
			}
		
			String studentNo = "";
			int dtOid=0;
			int tfsum=0;
			int tfLimit=0, elearnDilg = 0, tfwarn=0;
			int begin = 0, end = 0, knt = 0;
			int iweek = 0;
			for (Clazz clazz:cList) {
				String classNo = clazz.getClassNo();
				sql = "select Distinct s.student_no from Seld s, stmd d Where s.student_no=d.student_no";
				sql = sql + " and d.depart_class='" + classNo + "'";
				sql = sql + " order by s.student_no";
				tList = jdbcDao.findAnyThing(sql);
				int studknt = tList.size();
				
				log.debug("sql:" + sql);
				log.debug("timeOffDataExchange->clazz:" + classNo + " , size:"
						+ studknt + " ," + pcnt + "/" + inProcess);
				
				for(Iterator stIter=tList.iterator(); stIter.hasNext();){
					studentNo = ((Map)stIter.next()).get("student_no").toString();
					List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);

					Seld seld;
					List<Dtime> dtimeList;
					Dtime dtime;

					for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
						seld = seldIter.next();
						dtOid = seld.getDtimeOid();

						if(seld.getElearnDilg() != null){
							elearnDilg = seld.getElearnDilg();
						}else{
							elearnDilg = 0;
						}
						tfsum = elearnDilg;

						dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
						dtime = dtimeList.get(0);

						tfLimit = 0;

						if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
							if(dtime.getThour() * 14 % 3 > 0){
								tfLimit = dtime.getThour() * 14 / 3 + 1;
							}else{
								tfLimit = dtime.getThour() * 14 / 3;
							}
						}
						else {
							tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
						}

						String hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
						List<DtimeClass> dcList = dao.submitQuery(hql);
						for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
							DtimeClass dclass = dcIter.next();
							iweek = dclass.getWeek();
							begin = Integer.parseInt(dclass.getBegin());
							end = Integer.parseInt(dclass.getEnd());
							sql = "select count(*) From Dilg_One Where student_no='" + studentNo + "'";
							sql += " And depart_class='" + classNo + "'";
							sql += " And week_day=" + iweek;
							sql += " And  period between " + begin + " And " + end;
							sql += " And (abs=2 or abs=3 or abs=4 or abs=5 or abs=7)";
							knt = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
							tfsum += knt;
						}
						if(tfsum>=tfLimit){	
							for(Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter.hasNext();) {
								try{
									DtimeClass dclass = dcIter.next();
									iweek = dclass.getWeek();
									begin = Integer.parseInt(dclass.getBegin());
									end = Integer.parseInt(dclass.getEnd());
									sql ="Update Dilg_One set no_exam=1 Where student_no='" + studentNo + "'";
									sql += " And depart_class='" + classNo + "'";
									sql += " And week_day=" + iweek;
									sql += " And  period between " + begin + " And " + end;
									//sql += " And (abs=2 or abs=3 or abs=4 or abs=5 or abs=7)";
									jdbcDao.executesql(sql);
								}catch(Exception e){
									msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1",
											"Update Dilg_One:no_exam error->" + studentNo + 
											"Dtime_oid" + dtOid + " And week_day=" + iweek + 
											" And  period between " + begin + " And " + end));
									return msgs;
								}
							}
						}

					}	//End for seld
				}	//End for students in clazz

				runlogs = "class:" + clazz + " update " + studknt + " records!\n"
						+ runlogs;
				inProcess++;
				if ((inProcess % 10) == 0) {
					percentage = (double) (inProcess / percentUnit);
					setRunStatus("timeOffDataExchange", "depart:" + clazz, inProcess,
							pcnt, percentage, runlogs);
				}
		
			}
			if(msgs.isEmpty()) {
				setRunStatus("timeOffDataExchange", "finished!", 100, 100, 100d, "yes");
			}
			tList = null;
			cList = null;
			Runtime.getRuntime().gc();
		}
		return msgs;
	}
	
	public List<Dtimes> findTeachSubjectByTeacherIdno(UserCredential credential){
		
		ScoreManager scm = (ScoreManager) Global.context.getBean("scoreManager");
		List<Dtimes> dtsList = new ArrayList<Dtimes>();
		String teacherId = credential.getMember().getIdno().trim();
		String classInCharge = credential.getClassInChargeSqlFilterT();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
						
		
		List dtimeoids = jdbcDao.findAnyThing("(select dt.Dtime_oid As oid From Dtime_teacher dt, Dtime d Where dt.teach_id='"
						+ teacherId + "' And d.Sterm='" + sterm 
						+ "' And dt.Dtime_oid=d.Oid) UNION "
						+ "(select d.Oid As oid from Dtime d Where d.techid='"
						+ teacherId + "' And d.Sterm='" + sterm + "') UNION "
						+ "(select d.Oid As oid from Dtime d Where d.depart_class in "
						+ classInCharge + " And d.cscode in ('50000','T0001')" + " And d.Sterm='" + sterm + "')"
						);
		/*
		log.debug("(select dt.Dtime_oid As oid From Dtime_teacher dt, Dtime d Where dt.teach_id='"
				+ teacherId + "' And d.Sterm='" + sterm 
				+ "' And dt.Dtime_oid=d.Oid) UNION "
				+ "(select d.Oid As oid from Dtime d Where d.techid='"
				+ teacherId + "' And d.Sterm='" + sterm + "') UNION "
				+ "(select d.Oid As oid from Dtime d Where d.depart_class in "
				+ classInCharge + " And d.cscode in ('50000','T0001')" + " And d.Sterm='" + sterm + "')");
		*/
		//cscode 班會:50000, 系時間:T0001
		/*
		List dtimeoids = jdbcDao.findAnyThing("(select d.Oid As oid from Dtime d Where d.techid='"
				+ teacherId + "' And d.Sterm='" + sterm + "') UNION "
				+ "(select d.Oid As oid from Dtime d Where d.depart_class in "
				+ classInCharge + " And d.cscode in ('50000','T0001')" + " And d.Sterm='" + sterm + "')"
				);
		*/
		
		if(dtimeoids.size() > 0) {
			//String hql = "select distinct d from Dtime d, DtimeClass dc Where dc.dtimeOid=d.oid And d.oid In (";
			String sql = "select d.Oid As oid, CAST(dc.begin As UNSIGNED) As period from Dtime d, Dtime_class dc Where dc.Dtime_oid=d.Oid And d.Oid In (";
			for (Iterator oids = dtimeoids.iterator(); oids.hasNext();) {
				sql = sql + ((Map)oids.next()).get("oid");
				if(oids.hasNext()) sql = sql + ", ";
			}
			//hql = hql + ") Order by dc.week, dc.begin, d.oid";
			sql = sql + ") Group by d.Oid Order by dc.week, period, d.Oid";
			log.debug(sql);
			List<Map> srtList = jdbcDao.findAnyThing(sql);

			List<Dtime> dtList = new ArrayList<Dtime>();
			int preOid = 0, srtOid = 0;
			for(Iterator<Map> dts=srtList.iterator(); dts.hasNext();){
				srtOid = Integer.parseInt(dts.next().get("oid").toString());
				if(preOid != srtOid){
					Dtime dt = this.getDtimeByOid(srtOid);
					if(dt != null){
						dtList.add(dt);
					}
					preOid = srtOid;
				}
			}
			
			log.debug("=======> Find DtimeTeacher by TeachedID ='" + teacherId + "', Count:" + dtList.size());
			
			List<DtimeClass> dcList = new ArrayList<DtimeClass>();
			//DtimeClass[] dcArray;
			for (Dtime dtimet:dtList) {
				//碩士班不點名
				if(dtimet.getDepartClass().substring(2, 3).equalsIgnoreCase("G")){
					continue;
				}
				
				dcList = dao.submitQuery("From DtimeClass Where dtimeOid=" + dtimet.getOid()+ " Order by week,begin");
								
				dtimet.setChiName2(scm.findCourseName(dtimet.getCscode()));
				dtimet.setClassName2(scm.findClassName(dtimet.getDepartClass()));
				Dtimes dts = new Dtimes();
				dts.setDtime(dtimet);
				dts.setPeriods(dcList.toArray(new DtimeClass[dcList.size()]));
				dtsList.add(dts);
			}
		}
		
		return dtsList;
	}
	
	public List<Dtimes> findTeachSubject4TchClassbook(UserCredential credential){
		
		ScoreManager scm = (ScoreManager) Global.context.getBean("scoreManager");
		List<Dtimes> dtsList = new ArrayList<Dtimes>();
		String teacherId = credential.getMember().getIdno().trim();
		String classInCharge = credential.getClassInChargeSqlFilterT();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
						
		
		List dtimeoids = jdbcDao.findAnyThing("(select dt.Dtime_oid As oid From Dtime_teacher dt, Dtime d Where dt.teach_id='"
						+ teacherId + "' And d.Sterm='" + sterm 
						+ "' And dt.Dtime_oid=d.Oid) UNION "
						+ "(select d.Oid As oid from Dtime d Where d.techid='"
						+ teacherId + "' And d.Sterm='" + sterm + "') UNION "
						+ "(select d.Oid As oid from Dtime d Where d.depart_class in "
						+ classInCharge + " And d.cscode in ('50000','T0001')" + " And d.Sterm='" + sterm + "')"
						);
		
		if(dtimeoids.size() > 0) {
			String sql = "select d.Oid As oid, CAST(dc.begin As UNSIGNED) As period from Dtime d, Dtime_class dc Where dc.Dtime_oid=d.Oid And d.Oid In (";
			for (Iterator oids = dtimeoids.iterator(); oids.hasNext();) {
				sql = sql + ((Map)oids.next()).get("oid");
				if(oids.hasNext()) sql = sql + ", ";
			}
			//hql = hql + ") Order by dc.week, dc.begin, d.oid";
			sql = sql + ") Group by d.Oid Order by dc.week, period, d.Oid";
			log.debug(sql);
			List<Map> srtList = jdbcDao.findAnyThing(sql);

			List<Dtime> dtList = new ArrayList<Dtime>();
			int preOid = 0, srtOid = 0;
			for(Iterator<Map> dts=srtList.iterator(); dts.hasNext();){
				srtOid = Integer.parseInt(dts.next().get("oid").toString());
				if(preOid != srtOid){
					Dtime dt = this.getDtimeByOid(srtOid);
					if(dt != null){
						dtList.add(dt);
					}
					preOid = srtOid;
				}
			}
			
			//處理沒有排上課時間的課程
			boolean isfind = false;
			int oid = 0;
			for (Iterator oids = dtimeoids.iterator(); oids.hasNext();) {
				oid = Integer.parseInt(((Map)oids.next()).get("oid").toString());
				isfind = false;
				for(Dtime dtime:dtList){
					if(dtime.getOid()==oid){
						isfind = true;
						break;
					}
				}
				if(!isfind){
					Dtime dt = this.getDtimeByOid(oid);
					if(dt != null){
						dtList.add(dt);
					}
				}
			}
			
			log.debug("=======> Find DtimeTeacher by TeachedID ='" + teacherId + "', Count:" + dtList.size());
			
			List<DtimeClass> dcList = new ArrayList<DtimeClass>();
			//DtimeClass[] dcArray;
			for (Dtime dtimet:dtList) {
				dcList = dao.submitQuery("From DtimeClass Where dtimeOid=" + dtimet.getOid()+ " Order by week,begin");
				dtimet.setChiName2(scm.findCourseName(dtimet.getCscode()));
				dtimet.setClassName2(scm.findClassName(dtimet.getDepartClass()));
				Dtimes dts = new Dtimes();
				dts.setDtime(dtimet);
				dts.setPeriods(dcList.toArray(new DtimeClass[dcList.size()]));
				dtsList.add(dts);
			}
		}
		
		return dtsList;
	}
	

	
	public Time[] getDtimeStamp(String clazz, int weekd, int period){
		Time[] reTime = new Time[2];
		reTime[0] = null;
		reTime[1] = null;
		String sql = "Select * from Dtimestamp Where Cidno='" + clazz.charAt(0) +
						"' And Sidno='" + clazz.substring(1, 3) + "' And DSweek=" + weekd +
						" And DSreal=" + period;
		
		List stampList = jdbcDao.findAnyThing(sql);
		if(!stampList.isEmpty()){
			reTime[0] = (Time)(((Map)stampList.get(0)).get("DSbegin"));
			reTime[1] = (Time)(((Map)stampList.get(0)).get("DSend"));			
		}
		return reTime;
	}
	
	public Dtime getDtimeByOid(int dtOid){
		Dtime dtime = null;
		List<Dtime> dtList = dao.submitQuery("From Dtime Where oid=" + dtOid);
		if(!dtList.isEmpty()) dtime = dtList.get(0);
		return dtime;
	}
	
	public List<DtimeClass> getDtimeClassByDtimeOid(int dtOid, int iweek){
		Session session = dao.getHibernateSession();
		List<DtimeClass> dtcList = new ArrayList<DtimeClass>();
		String sql = "Select * From Dtime_class dc Where dc.Dtime_oid=" + dtOid;
		if(iweek >0 && iweek<=7) sql += " And dc.week=" + iweek;
		sql += " Order by dc.week, CAST(dc.begin As UNSIGNED)";
		dtcList = session.createSQLQuery(sql).addEntity(DtimeClass.class).list();
		
		/*
		String hql = "From DtimeClass Where dtimeOid=" + dtOid;
		if(iweek >0 && iweek<=7) hql += " And week=" + iweek;
		hql += " Order by week, begin)";
		List<DtimeClass> dtcList = dao.submitQuery(hql);
		*/
		return dtcList;
	}
	
	
	public Map createOrUpdateDilg4Tch(Map formMap) {
		Map retMap = new HashMap();
		ActionMessages msgs = new ActionMessages();
		
		String dtOid = formMap.get("dtOid").toString();
		String tdate = formMap.get("tdate").toString();
		String[] adate = tdate.split("-");
		String[] studentNo = (String[])formMap.get("studentNo");
		String[] abs0 = (String[])formMap.get("abs0");
		String[] abs1 = (String[])formMap.get("abs1");
		String[] abs2 = (String[])formMap.get("abs2");
		String[] abs3 = (String[])formMap.get("abs3");
		String[] abs4 = (String[])formMap.get("abs4");
		String[] abs5 = (String[])formMap.get("abs5");
		String[] abs6 = (String[])formMap.get("abs6");
		String[] abs7 = (String[])formMap.get("abs7");
		String[] abs8 = (String[])formMap.get("abs8");
		String[] abs9 = (String[])formMap.get("abs9");
		String[] abs10 = (String[])formMap.get("abs10");
		String[] abs11 = (String[])formMap.get("abs11");
		String[] abs12 = (String[])formMap.get("abs12");
		String[] abs13 = (String[])formMap.get("abs13");
		String[] abs14 = (String[])formMap.get("abs14");
		String[] abs15 = (String[])formMap.get("abs15");
		
		int tfYear = Integer.parseInt(adate[0]);
		int tfMonth = Integer.parseInt(adate[1]);
		int tfDay = Integer.parseInt(adate[2]);
		
		Dtime dtime = this.getDtimeByOid(Integer.parseInt(dtOid));
		String daynite = Toolket.chkDayNite(dtime.getDepartClass());
		log.debug("daynite=" + daynite);

		Calendar ddate = Calendar.getInstance(); 
		ddate.set(tfYear, tfMonth-1, tfDay, 0, 0, 0);
		int iweek = ddate.get(Calendar.DAY_OF_WEEK);
		if(iweek == 1) iweek = 7;
		else iweek--;
		Date sddate = ddate.getTime();
		
		String hql = "From DtimeClass Where dtimeOid=" + dtime.getOid();
		List<DtimeClass> dtcList = dao.submitQuery(hql);
		int pLen = dtcList.size();
		int[] begin = new int[pLen], end = new int[pLen];
		int cnt = 0;
		for(DtimeClass dtc:dtcList){
			begin[cnt] = Integer.parseInt(dtc.getBegin());
			end[cnt] = Integer.parseInt(dtc.getEnd());
			cnt++;
		}
		
		boolean isNewDilg = false;
		int tfCnt = 0;
		
		hql = "From Dilg Where studentNo=? And ddate=?";
		List<Dilg> dilgs = new ArrayList<Dilg>();
		Dilg dilg = null;
		Student student = null;
		int pbegin = 0, pend = 0;
		Object[] params = new Object[2];
		String status = "";
		short st = 0;
		boolean isChange = false;
		Map chgMap = new HashMap();
		List chgList = new ArrayList();
		for(int k=0; k<studentNo.length; k++){
			student = this.findStudentByStudentNo(studentNo[k]);
			isNewDilg = false;
			if(student != null){
				params[0] = studentNo[k];
				params[1] = sddate;
				dilgs = dao.submitQuery(hql, params);
				if(dilgs.isEmpty()){
					dilg = new Dilg();
					dilg.setDaynite(daynite);
					dilg.setDdate(sddate);
					dilg.setStudentNo(studentNo[k]);
					dilg.setDepartClass(student.getDepartClass());
					dilg.setWeekDay((float)iweek);
					for(int j=0; j<16; j++){
						dilg.setAbs(j, null);
					}
					isNewDilg = true;
				}else{
					dilg = dilgs.get(0);
				}
				chgMap = new HashMap();
				isChange = false;
				for(int p=0; p<pLen; p++){
					for(int q=begin[p]; q<=end[p]; q++){
						if(dilg.getAbs(q) != null){
							st = dilg.getAbs(q);
						}
						switch (q) {
						case 0:
							status = abs0[k];
							break;
						case 1:
							status = abs1[k];
							break;
						case 2:
							status = abs2[k];
							break;
						case 3:
							status = abs3[k];
							break;
						case 4:
							status = abs4[k];
							break;
						case 5:
							status = abs5[k];
							break;
						case 6:
							status = abs6[k];
							break;
						case 7:
							status = abs7[k];
							break;
						case 8:
							status = abs8[k];
							break;
						case 9:
							status = abs9[k];
							break;
						case 10:
							status = abs10[k];
							break;
						case 11:
							status = abs11[k];
							break;
						case 12:
							status = abs12[k];
							break;
						case 13:
							status = abs13[k];
							break;
						case 14:
							status = abs14[k];
							break;
						case 15:
							status = abs15[k];
							break;
						}
						//只有該學生當日該節課缺曠為[無,遲到,曠課]等三種狀態時老師才可以更改
						if(dilg.getAbs(q)==null || dilg.getAbs(q)==(short)2 || dilg.getAbs(q)==(short)5){
							if(status.trim().equals("")){
								if(dilg.getAbs(q) != null){
									dilg.setAbs(q, null);
									isChange = true;
								}
							}else if(status.trim().equals("2")){
								if(dilg.getAbs(q) != null){
									if(dilg.getAbs(q) != (short)2){
										dilg.setAbs(q, (short)2);
										isChange = true;
									}
								}else{
									dilg.setAbs(q, (short)2);
									isChange = true;
								}
							}else if(status.trim().equals("5")){
								if(dilg.getAbs(q) != null){
									if(dilg.getAbs(q) != (short)5){
										dilg.setAbs(q, (short)5);
										isChange = true;
									}
								}else{
									dilg.setAbs(q, (short)5);
									isChange = true;
								}
							}
						}	//End if
					}	//end for q
				}	//end for p
				//如果 dilg abs0~abs15 皆為 null 則刪除或不新增dilg
				tfCnt = 0;
				for(int j=0; j<16; j++){
					if(dilg.getAbs(j) == null){
						tfCnt++;
					}
				}
				if(tfCnt == 16){	//abs0~abs15 皆為 null
					if(!isNewDilg){	//刪除舊紀錄
						dao.removeObject(dilg);
					}
				}else{
					dao.saveObject(dilg);
				}
				
				//更新操行成績及Seld扣考節數
				if(isChange){
					chgMap.put("studentNo", studentNo[k]);
					chgMap.put("isDataChg", "Y");
					chgList.add(chgMap);
				}
			}	//End if(student!=null)
		}	//End for
		retMap.put("message", msgs);
		retMap.put("changes", chgList);
		
		return retMap;
	}

	/**
	 * 查詢Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 * @return java.util.List List of tw.edu.chit.models.Dilg Objects
	 */
	public List<Dilg> findDilgBy(Dilg dilg) {
		return dao.getDilgBy(dilg);
	}
	
	/**
	 * 
	 * @param studCounseling
	 * @return
	 */
	public List<StudCounseling> findStudCounselingBy(
			StudCounseling studCounseling) throws DataAccessException {
		return dao.getStudCounselingBy(studCounseling);
	}
	
	/**
	 * 儲存Dilg物件
	 * 
	 * @param dilg tw.edu.chit.models.Dilg Objects
	 */
	public void txSaveDilg(Dilg dilg) {
		dao.saveObject(dilg);
	}

	public ActionMessages createOrUpdateTimeOffUpload(String teachId, int dtOid, String tdate, String modifierId){
		ActionMessages msgs = new ActionMessages();
		String hql = "From TimeOffUpload Where teachId='" + teachId + "' And dtimeOid=" + dtOid
					+ " And teachDate='" + tdate + "'";
		List<TimeOffUpload> tfups = dao.submitQuery(hql);
		
		try{
			String[] adate = tdate.split("-");
			Calendar now = Calendar.getInstance();
			Calendar thdate = Calendar.getInstance();
			thdate.set(Calendar.YEAR, Integer.parseInt(adate[0]));
			thdate.set(Calendar.MONTH, Integer.parseInt(adate[1])-1);
			thdate.set(Calendar.DATE, Integer.parseInt(adate[2]));
			int iweek = this.WhatIsTheWeek(thdate.getTime());
			
			TimeOffUpload tfup = null;
			if(!tfups.isEmpty()){
				tfup = tfups.get(0);
			}else{
				tfup = new TimeOffUpload();
				tfup.setTeachId(teachId);
				tfup.setDtimeOid(dtOid);
				tfup.setTeachDate(thdate.getTime());
				tfup.setTeachWeek((short)iweek);
			}
			
			if(!modifierId.equals("")){
				tfup.setModifierId(modifierId);
			}
			tfup.setUploadTime(now.getTime());
			dao.saveObject(tfup);
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.MessageN1", e.toString()));
		}
		return msgs;
	}
	
	public Map findTimeOffUploadRecord(UserCredential credential){
		List<Map> notupList = new ArrayList<Map>();		//全部未上傳
		List<Map> notup8List = new ArrayList<Map>();	//8日內未上傳
		List<Map> before8List = new ArrayList<Map>();	//8日前未上傳
		ScoreManager scm = (ScoreManager) Global.context.getBean("scoreManager");
		List<Dtimes> dtsList = new ArrayList<Dtimes>();
		String teacherId = credential.getMember().getIdno().trim();
		String teacherName = credential.getMember().getName().trim();
		String classInCharge = credential.getClassInChargeSqlFilterT();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		Calendar nowCal = Calendar.getInstance();
		nowCal.add(Calendar.DAY_OF_MONTH, -1);
		Calendar start8Cal = Calendar.getInstance();
		Calendar end8Cal = Calendar.getInstance();
		start8Cal.add(Calendar.DAY_OF_MONTH, -7);
		start8Cal.set(Calendar.HOUR_OF_DAY, 0);
		start8Cal.set(Calendar.MINUTE, 0);
		start8Cal.set(Calendar.SECOND, 0);
		start8Cal.set(Calendar.MILLISECOND, 0);
		end8Cal.set(Calendar.HOUR_OF_DAY, 23);
		end8Cal.set(Calendar.MINUTE, 59);
		end8Cal.set(Calendar.SECOND, 59);
		end8Cal.set(Calendar.MILLISECOND, 999);
		
		final String[] weekp = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"};
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		List dtimeoids = jdbcDao.findAnyThing("(select dt.Dtime_oid As oid From Dtime_teacher dt, Dtime d Where dt.teach_id='"
						+ teacherId + "' And d.Sterm='" + sterm 
						+ "' And dt.Dtime_oid=d.Oid) UNION "
						+ "(select d.Oid As oid from Dtime d Where d.techid='"
						+ teacherId + "' And d.Sterm='" + sterm + "') UNION "
						+ "(select d.Oid As oid from Dtime d Where d.depart_class in "
						+ classInCharge + " And d.cscode in ('50000','T0001')" + " And d.Sterm='" + sterm + "')"
						);
		
		
		int cnt = 0;	//cnt:應點名總節次
		if(dtimeoids.size() > 0) {
			String hql = "select distinct d from Dtime d, DtimeClass dc Where dc.dtimeOid=d.oid And d.oid In (";
			for (Iterator oids = dtimeoids.iterator(); oids.hasNext();) {
				hql = hql + ((Map)oids.next()).get("oid");
				if(oids.hasNext()) hql = hql + ", ";
			}
			hql = hql + ") Order by dc.week,dc.begin";
			log.debug("=======> Find DtimeTeacher by TeachedID Hql ='" + hql);
		
			List<Dtime> dtList = dao.submitQuery(hql);
			log.debug("=======> Find DtimeTeacher by TeachedID ='" + teacherId + "', Count:" + dtList.size());
			
			List<DtimeClass> dcList = new ArrayList<DtimeClass>();
			//DtimeClass[] dcArray;
			Calendar startCal = Calendar.getInstance();
			Date tdate = null;
			int preWeek = 0;
			Object[] parameters = new Object[3];
			List<TimeOffUpload> tfups;
			int iweek = 0, dweek = 0, offset = 0;

			String hql2 = "From DtimeStudaffair Where isRollCall=0 And dtimeOid=?";
			Object[] parameters2 = new Object[1];
			List<DtimeStudaffair> dtSAFs = new ArrayList<DtimeStudaffair>();
			String clazz = "";
			String hdept = "";
			Calendar myStartCal = null;
			Calendar termEndCal = null;
			
			for (Dtime dtimet:dtList) {
				clazz = dtimet.getDepartClass();
				//排除不點名課程
				parameters2[0] = dtimet.getOid();
				dtSAFs = dao.submitQuery(hql2, parameters2);
				//碩士班不點名
				if(dtimet.getDepartClass().substring(2, 3).equalsIgnoreCase("G")){
					continue;
				}
				//System.out.println("leo_0306");
				//weeks : 該開課班級所屬學制開課日期
				hdept = clazz.substring(0, 2);
				myStartCal = Toolket.getDateOfWeek(hdept, 1)[0];
				//termEndCal = Toolket.getDateOfWeek(hdept, 17)[1];
				if(Toolket.chkIsGraduateClass(clazz) && sterm.equals("2")){
					termEndCal = Toolket.getDateOfWeek(hdept, 13)[1];
				}else{
					//TODO:check !
					termEndCal = Toolket.getDateOfWeek(hdept, Toolket.getTimeOffUploadDeadline())[1];
				}
				
				dcList = dao.submitQuery("From DtimeClass Where dtimeOid=" + dtimet.getOid()+ " Order by week,begin");
				parameters[0] = dtimet.getOid();
				preWeek = 0;
				for(DtimeClass dtc:dcList){
					dweek = dtc.getWeek();
					if(dtc.getWeek()==preWeek){	//同一天上課在不同節次則不重覆計算點名
						//do nothing
					}else{
						startCal.setTimeInMillis(myStartCal.getTimeInMillis());
						//計算該課程上課第一天的正確日期
						iweek = startCal.get(Calendar.DAY_OF_WEEK);
						if (iweek == 1)
							iweek = 7;
						else
							iweek--;

						offset = dweek-iweek >= 0 ? dweek-iweek : dweek-iweek+7;
						startCal.add(Calendar.DAY_OF_MONTH, offset);
						iweek = startCal.get(Calendar.DAY_OF_WEEK);
						if (iweek == 1)
							iweek = 7;
						else
							iweek--;
						//End 計算該課程上課第一天的正確日期
						
						DtimeStudaffair ds = new DtimeStudaffair();
						Calendar tCal = Calendar.getInstance();
						//System.out.println(tCal);
						boolean isRollCall = true;
						while(startCal.compareTo(nowCal) < 0 && startCal.compareTo(termEndCal)<=0){
							//排除不點名的上課日期
							isRollCall = true;
							if(!dtSAFs.isEmpty()){
								for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
									ds = dsIter.next();
									tCal.setTime(ds.getTdate());
									if(Toolket.isSameDay(tCal, startCal)){
										isRollCall = false;
										break;
									}
								}
							}


							if(!Toolket.isHoliday(startCal) && isRollCall){
								cnt++;
								parameters[1] =  startCal.getTime();
								parameters[2] = teacherId;
								hql = "From TimeOffUpload Where dtimeOid=? And teachDate=? And teachId=?";
								tfups = dao.submitQuery(hql, parameters);
								if(tfups.isEmpty()){
									Map tfupMap = new HashMap();
									tfupMap.put("teacherId", teacherId);
									tfupMap.put("teacherName", teacherName);
									tfupMap.put("dtimeOid", dtimet.getOid());
									tfupMap.put("deptClassName", scm.findClassName(dtimet.getDepartClass()));
									tfupMap.put("subjectName", scm.findCourseName(dtimet.getCscode()));
									tfupMap.put("teachDate", df.format(startCal.getTime()));
									tfupMap.put("teachWeek", weekp[iweek-1]);
									notupList.add(tfupMap);
									if(startCal.compareTo(start8Cal)>=0 && startCal.compareTo(end8Cal)<=0){
										notup8List.add(tfupMap);
									}else{
										before8List.add(tfupMap);
									}
								}
							}
							startCal.add(Calendar.DATE, 7);
						}
					}
					preWeek = dtc.getWeek();
				}
			}
		}
		Map retMap = new HashMap();
		retMap.put("teacherName", credential.getMember().getName());
		retMap.put("total", cnt);
		retMap.put("uploaded", cnt-notupList.size());
		retMap.put("notUpload", notupList.size());
		retMap.put("notUploadList", notupList);
		retMap.put("notUpload8List", notup8List);
		retMap.put("notUploadBefore8List", before8List);
		return retMap;
		
	}
	
	//use Dilg_One for TimeOff Alert for Parent
	public List findDilgOneAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String letterType1,
			String letterType2, String letterType3, String pmode){
		//pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)

		List retList = new ArrayList();
		int th1=0,th2=0,th3=0;
		
		if(!threshold1.equals("")) th1 = Integer.parseInt(threshold1);
		if(!threshold2.equals("")) th2 = Integer.parseInt(threshold2);
		if(!threshold3.equals("")) th3 = Integer.parseInt(threshold3);
		Date sDate = new Date();
		Date eDate = new Date();
		Date dDate = new Date();
		
		Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		Calendar dDateCal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();

		sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		sDateCal.setTime(sDate);
		eDateCal.setTime(eDate);
		sDateCal.set(Calendar.MILLISECOND, 0);
		eDateCal.set(Calendar.MILLISECOND, 999);
		eDateCal.set(Calendar.HOUR_OF_DAY, 23);
		eDateCal.set(Calendar.MINUTE, 59);
		eDateCal.set(Calendar.SECOND, 59);
				
		String hql = "Select d From DilgOne d Where departClass like '" + clazzFilter +"%'";
		hql = hql + " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " Group by studentNo Order by departClass, studentNo, ddate";
		
		List<DilgOne> dilg1List = dao.submitQuery(hql);
		if(!dilg1List.isEmpty()){
			List wdList = new ArrayList();
			List<Dilg> myDilg = new ArrayList<Dilg>();
			List<DilgOne> d1List = new ArrayList<DilgOne>();
			int[] timeoff = new int[2];
			timeoff[0] = 0;	//升旗缺席次數
			timeoff[1] = 0;	//曠課次數,不含事病公喪等假
			
			int[] wtimeoff = new int[2];
			
			int serialNo = 1;
			boolean putData = false;

			int period = 0, prevperiod = 0;
			int raiseflag = 0, prevraise = 0;
			int threshold = 0;
			String mailLevel = "0";
			DilgMail dmail;
			String sql = "", sql_1 = "", sql_0 = "";
			Student student = new Student();
			Object[] parameters = new Object[1];
			
			List<String> studs = new ArrayList<String>();
			for(DilgOne d1:dilg1List){
				studs.add(d1.getStudentNo());
			}
			
			for(String studentNo:studs){
				//排除扣考之曠缺統計資料
				//timeoff = dilgSummary(myDilg, "0");
				sql ="Select count(*) From Dilg_One Where student_no='" + studentNo + "'";
				sql += " And abs=2 And no_exam=0";
				sql += " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
				timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
				timeoff[0] = 0;
				
				//log.debug("studentNo:" + studentNo + ", timeoff:" + timeoff[1]);
				hql = "Select dm From DilgMail dm Where studentNo='" + studentNo + "'";
				List<DilgMail> dmList = dao.submitQuery(hql);
				period = 0;
				prevperiod=0;
				prevraise = 0;
				raiseflag = 0;
				threshold = 0;
				mailLevel = "0";
				wdList = new ArrayList();
				putData = false;
				
				if(!dmList.isEmpty()){
					dmail = dmList.get(0);
					if(dmail.getThreshold()!=null){
						threshold = dmail.getThreshold();
					}
					if(pmode.equals("0")){	//第一次列印
						if(dmail.getPeriod()!=null)
							period = dmail.getPeriod();
						if(dmail.getRaiseflag()!=null)
							raiseflag = dmail.getRaiseflag();							
						if(dmail.getPrevPeriod()!=null)
							prevperiod = dmail.getPrevPeriod();
						if(dmail.getPrevRaiseflag()!=null)
							prevraise = dmail.getPrevRaiseflag();							
					}else{
						if(dmail.getPrevPeriod()!=null){
							period = dmail.getPrevPeriod();
							prevperiod = period;
						}
						/*
						else if(dmail.getPeriod()!=null){
							period = dmail.getPeriod();
							prevperiod = period;
						}
						*/
						if(dmail.getPrevRaiseflag()!=null){
							raiseflag = dmail.getPrevRaiseflag();	
							prevraise = raiseflag;
						}
						/*
						else if(dmail.getRaiseflag()!=null){
							raiseflag = dmail.getRaiseflag();	
							prevraise = raiseflag;
						}
						*/
					}
				}
				
				student = Toolket.getStudentByNo(studentNo);
				if(period < 45 && raiseflag < 45 ){	//上一次曠缺少於45節之處理
					//List<Student> studList = jdbcDao.findStudentsByStudentNo(prevStu);
					if(student != null){
						/*
						if(student.getStudentNo().equals("95132023")){
							log.debug("timeOff:"+timeoff[1]+",period:"+period+",th1:"+th1+",th2:"+th2+",th3:"+th3);								
						}
						*/
						if((timeoff[0]>=45 && raiseflag<45) || (timeoff[1]>=45 && period<45)){
							//45節以上曠缺
							putData = true;
							threshold = 45;
							mailLevel = "1";
							
						/* 2010/02/28 取消35節及以上每增加5節寄掛號之規定,並將30節以上寄掛號之新規定
						 * 改採由使用者在畫面上自行選定寄件型態為平信或掛號,即 letterType1~3
						 * 
						}else if((timeoff[0]>=35 || timeoff[1]>=35)){
							if(raiseflag<35 && period<35){
								//第一次達到35節以上
								putData = true;
								threshold = 35;
								mailLevel = "1";
								
							}else if(timeoff[0]-raiseflag>=5 || timeoff[1]-period>=5){
								//35節以上,每增加5節,且未達45節
								putData = true;
								threshold = 40;
								mailLevel = "1";
								
							}
						*/
						}else if(th3>0 && ((timeoff[0]>=th3 && raiseflag<th3) ||
									(timeoff[1]>=th3 && period<th3))){
							putData = true;
							threshold = th3;
							mailLevel = letterType3;
						}else if(th2>0 && ((timeoff[0]>=th2 && raiseflag<th2) ||
									(timeoff[1]>=th2 && period<th2))){
							putData = true;
							threshold = th2;
							mailLevel = letterType2;
						}else if(th1>0 && ((timeoff[0]>=th1 && raiseflag<th1) ||
									(timeoff[1]>=th1 && period<th1))){
							putData = true;
							threshold = th1;
							mailLevel = letterType1;
						}
						
							//將該學生曠缺資料加入並記錄在DilgMail table中
							//1. 將本星期曠課資料放入 wdList
							hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
							hql += " And abs=2 And noExam=0 And ddate>='" + Toolket.FullDate2Str(sDate) + "'";
							hql += " And ddate<='" + Toolket.FullDate2Str(eDate) + "'";
							hql += " Group by ddate Order by ddate";
							d1List = dao.submitQuery(hql);
							for(DilgOne d1:d1List){
								parameters[0] = d1.getDdate();
								hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
								hql += " And abs=2 And noExam=0 And ddate=?";
								d1List = dao.submitQuery(hql, parameters);
								
								wtimeoff[1] = d1List.size();
								wtimeoff[0] = 0;
								
								Map wdMap = new HashMap();
								wdMap.put("tfDate", Toolket.Date2Str(d1.getDdate()));
								wdMap.put("raiseFlag", "" + wtimeoff[0]);
								wdMap.put("timeOff", "" + wtimeoff[1]);
								wdList.add(wdMap);
							}
							

							Map tfMap = new HashMap();
							if(putData){
								tfMap.put("printable", "1");
								tfMap.put("serialNo", "" + serialNo);
								serialNo++;
								tfMap.put("studentNo", studentNo);
								tfMap.put("studentName", student.getStudentName());
								tfMap.put("departClass", student.getDepartClass());
								tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
								if(student.getParentName()!= null)
									tfMap.put("parentName", student.getParentName());
								else
									tfMap.put("parentName", "");
								if(student.getCurrPost()!=null)
									tfMap.put("postNo", student.getCurrPost());
								else
									tfMap.put("postNo", "");
								if(student.getCurrAddr()!=null)
									tfMap.put("address", student.getCurrAddr());
								else
									tfMap.put("address", "");
								tfMap.put("raiseFlagSum", "" + timeoff[0]);
								tfMap.put("timeOffSum", "" + timeoff[1]);
								tfMap.put("wdilgList", wdList);
								tfMap.put("mailLevel", mailLevel);
								tfMap.put("threshold", "" + threshold);
								if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
								else tfMap.put("Memo", "平信");
							}else{
								tfMap.put("printable", "0");
								tfMap.put("studentNo", studentNo);
								tfMap.put("raiseFlagSum", "" + timeoff[0]);
								tfMap.put("timeOffSum", "" + timeoff[1]);
								tfMap.put("threshold", "" + threshold);
							}

							retList.add(tfMap);
					}
					
				}else{	//之前寄出資料已經達到退學標準45節,每增加10節再寄一次
					if(student != null){
						Map tfMap = new HashMap();
						if((timeoff[0]-raiseflag>=10) || (timeoff[1]-period>=10)){
							//1. 將本星期曠課資料放入 wdList
							hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
							hql += " And abs=2 And noExam=0 And ddate>='" + Toolket.FullDate2Str(sDate) + "'";
							hql += " And ddate<='" + Toolket.FullDate2Str(eDate) + "'";
							hql += " Group by ddate Order by ddate";
							d1List = dao.submitQuery(hql);
							for(DilgOne d1:d1List){
								parameters[0] = d1.getDdate();
								hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
								hql += " And abs=2 And noExam=0 And ddate=?";
								d1List = dao.submitQuery(hql, parameters);
								
								wtimeoff[1] = d1List.size();
								wtimeoff[0] = 0;
								
								Map wdMap = new HashMap();
								wdMap.put("tfDate", Toolket.Date2Str(d1.getDdate()));
								wdMap.put("raiseFlag", "" + wtimeoff[0]);
								wdMap.put("timeOff", "" + wtimeoff[1]);
								wdList.add(wdMap);
							}
							
							tfMap.put("printable", "1");
							tfMap.put("serialNo", "" + serialNo);
							serialNo++;
							tfMap.put("studentNo", studentNo);
							tfMap.put("studentName", student.getStudentName());
							tfMap.put("departClass", student.getDepartClass());
							tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
							if(student.getParentName()!= null)
								tfMap.put("parentName", student.getParentName());
							else
								tfMap.put("parentName", "");
							if(student.getCurrPost()!=null)
								tfMap.put("postNo", student.getCurrPost());
							else
								tfMap.put("postNo", "");
							if(student.getCurrAddr()!=null)
								tfMap.put("address", student.getCurrAddr());
							else
								tfMap.put("address", "");
							tfMap.put("raiseFlagSum", "" + timeoff[0]);
							tfMap.put("timeOffSum", "" + timeoff[1]);
							tfMap.put("wdilgList", wdList);
							tfMap.put("mailLevel", "1");
							tfMap.put("threshold", "45");
							tfMap.put("Memo", "掛號");
						}else{
							tfMap.put("printable", "0");
							tfMap.put("studentNo", studentNo);
							tfMap.put("raiseFlagSum", "" + timeoff[0]);
							tfMap.put("timeOffSum", "" + timeoff[1]);
							tfMap.put("threshold", "45");
						}
							retList.add(tfMap);
							
					}	//if(student != null)
				}	//if(period < 45 && raiseflag < 45 )
			}	//for(String studentNo:studs)
		}	//if(!dilg1List.isEmpty())
		
		return retList;
	}
	
	public List findDilgOneAlert4P(String DateStart, String DateEnd, String clazzFilter,
			String threshold1,String threshold2,String threshold3,String pmode){
		//pmode 操作模式：0:第一次列印(更新通知紀錄) 1:再次列印(不更新通知紀錄)

		List retList = new ArrayList();
		int th1=0,th2=0,th3=0;
		
		if(!threshold1.equals("")) th1 = Integer.parseInt(threshold1);
		if(!threshold2.equals("")) th2 = Integer.parseInt(threshold2);
		if(!threshold3.equals("")) th3 = Integer.parseInt(threshold3);
		Date sDate = new Date();
		Date eDate = new Date();
		Date dDate = new Date();
		
		Calendar sDateCal = Calendar.getInstance();
		Calendar eDateCal = Calendar.getInstance();
		Calendar dDateCal = Calendar.getInstance();
		Calendar nowCal = Calendar.getInstance();

		sDate = Toolket.parseDate(DateStart.replace('/', '-'));
		eDate = Toolket.parseDate(DateEnd.replace('/', '-'));
		sDateCal.setTime(sDate);
		eDateCal.setTime(eDate);
		sDateCal.set(Calendar.MILLISECOND, 0);
		eDateCal.set(Calendar.MILLISECOND, 999);
		eDateCal.set(Calendar.HOUR_OF_DAY, 23);
		eDateCal.set(Calendar.MINUTE, 59);
		eDateCal.set(Calendar.SECOND, 59);
				
		String hql = "Select d From DilgOne d Where departClass like '" + clazzFilter +"%'";
		hql = hql + " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
		hql = hql + " Group by studentNo Order by departClass, studentNo, ddate";
		
		List<DilgOne> dilg1List = dao.submitQuery(hql);
		if(!dilg1List.isEmpty()){
			List wdList = new ArrayList();
			List<Dilg> myDilg = new ArrayList<Dilg>();
			List<DilgOne> d1List = new ArrayList<DilgOne>();
			int[] timeoff = new int[2];
			timeoff[0] = 0;	//升旗缺席次數
			timeoff[1] = 0;	//曠課次數,不含事病公喪等假
			
			int[] wtimeoff = new int[2];
			
			int serialNo = 1;
			boolean putData = false;

			int period = 0, prevperiod = 0;
			int raiseflag = 0, prevraise = 0;
			int threshold = 0;
			String mailLevel = "0";
			DilgMail dmail;
			String sql = "", sql_1 = "", sql_0 = "";
			Student student = new Student();
			Object[] parameters = new Object[1];
			
			List<String> studs = new ArrayList<String>();
			for(DilgOne d1:dilg1List){
				studs.add(d1.getStudentNo());
			}
			
			for(String studentNo:studs){
				//排除扣考之曠缺統計資料
				//timeoff = dilgSummary(myDilg, "0");
				sql ="Select count(*) From Dilg_One Where student_no='" + studentNo + "'";
				sql += " And abs=2 And no_exam=0";
				sql += " And ddate <= '" + Toolket.FullDate2Str(eDate) +"'";
				timeoff[1] = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
				timeoff[0] = 0;
				
				//log.debug("studentNo:" + studentNo + ", timeoff:" + timeoff[1]);
				hql = "Select dm From DilgMail dm Where studentNo='" + studentNo + "'";
				List<DilgMail> dmList = dao.submitQuery(hql);
				period = 0;
				prevperiod=0;
				prevraise = 0;
				raiseflag = 0;
				threshold = 0;
				mailLevel = "0";
				wdList = new ArrayList();
				putData = false;
				
				if(!dmList.isEmpty()){
					dmail = dmList.get(0);
					if(dmail.getThreshold()!=null){
						threshold = dmail.getThreshold();
					}
					if(pmode.equals("0")){	//第一次列印
						if(dmail.getPeriod()!=null)
							period = dmail.getPeriod();
						if(dmail.getRaiseflag()!=null)
							raiseflag = dmail.getRaiseflag();							
						if(dmail.getPrevPeriod()!=null)
							prevperiod = dmail.getPrevPeriod();
						if(dmail.getPrevRaiseflag()!=null)
							prevraise = dmail.getPrevRaiseflag();							
					}else{
						if(dmail.getPrevPeriod()!=null){
							period = dmail.getPrevPeriod();
							prevperiod = period;
						}
						if(dmail.getPrevRaiseflag()!=null){
							raiseflag = dmail.getPrevRaiseflag();	
							prevraise = raiseflag;
						}
					}
				}
				
				student = Toolket.getStudentByNo(studentNo);
				if(period < 45 && raiseflag < 45 ){	//上一次曠缺少於45節之處理
					//List<Student> studList = jdbcDao.findStudentsByStudentNo(prevStu);
					if(student != null){
						//if(student.getStudentNo().equals("95132023")){
						//	log.debug("timeOff:"+timeoff[1]+",period:"+period+",th1:"+th1+",th2:"+th2+",th3:"+th3);								
						//}
						if((timeoff[0]>=45 && raiseflag<45) || (timeoff[1]>=45 && period<45)){
							//45節以上曠缺
							putData = true;
							threshold = 45;
							mailLevel = "1";
							
						}else if((timeoff[0]>=35 || timeoff[1]>=35)){
							if(raiseflag<35 && period<35){
								//第一次達到35節以上
								putData = true;
								threshold = 35;
								mailLevel = "1";
								
							}else if(timeoff[0]-raiseflag>=5 || timeoff[1]-period>=5){
								//35節以上,每增加5節,且未達45節
								putData = true;
								threshold = 40;
								mailLevel = "1";
								
							}
						}else if(th1>0 && ((timeoff[0]>=th1 && raiseflag<th1) ||
									(timeoff[1]>=th1 && period<th1))){
							putData = true;
							threshold = th1;
							
						}else if(th2>0 && ((timeoff[0]>=th2 && raiseflag<th2) ||
									(timeoff[1]>=th2 && period<th2))){
							putData = true;
							threshold = th2;
							
						}else if(th3>0 && ((timeoff[0]>=th3 && raiseflag<th3) ||
									(timeoff[1]>=th3 && period<th3))){
							putData = true;
							threshold = th3;
						}
						
							//將該學生曠缺資料加入並記錄在DilgMail table中
							//1. 將本星期曠課資料放入 wdList
							hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
							hql += " And abs=2 And noExam=0 And ddate>='" + Toolket.FullDate2Str(sDate) + "'";
							hql += " And ddate<='" + Toolket.FullDate2Str(eDate) + "'";
							hql += " Group by ddate Order by ddate";
							d1List = dao.submitQuery(hql);
							for(DilgOne d1:d1List){
								parameters[0] = d1.getDdate();
								hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
								hql += " And abs=2 And noExam=0 And ddate=?";
								d1List = dao.submitQuery(hql, parameters);
								
								wtimeoff[1] = d1List.size();
								wtimeoff[0] = 0;
								
								Map wdMap = new HashMap();
								wdMap.put("tfDate", Toolket.Date2Str(d1.getDdate()));
								wdMap.put("raiseFlag", "" + wtimeoff[0]);
								wdMap.put("timeOff", "" + wtimeoff[1]);
								wdList.add(wdMap);
							}
							

							Map tfMap = new HashMap();
							if(putData){
								tfMap.put("printable", "1");
								tfMap.put("serialNo", "" + serialNo);
								serialNo++;
								tfMap.put("studentNo", studentNo);
								tfMap.put("studentName", student.getStudentName());
								tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
								if(student.getParentName()!= null)
									tfMap.put("parentName", student.getParentName());
								else
									tfMap.put("parentName", "");
								if(student.getCurrPost()!=null)
									tfMap.put("postNo", student.getCurrPost());
								else
									tfMap.put("postNo", "");
								if(student.getCurrAddr()!=null)
									tfMap.put("address", student.getCurrAddr());
								else
									tfMap.put("address", "");
								tfMap.put("raiseFlagSum", "" + timeoff[0]);
								tfMap.put("timeOffSum", "" + timeoff[1]);
								tfMap.put("wdilgList", wdList);
								tfMap.put("mailLevel", mailLevel);
								tfMap.put("threshold", "" + threshold);
								if(mailLevel.equals("1")) tfMap.put("Memo", "掛號");
								else tfMap.put("Memo", "平信");
							}else{
								tfMap.put("printable", "0");
								tfMap.put("studentNo", studentNo);
								tfMap.put("raiseFlagSum", "" + timeoff[0]);
								tfMap.put("timeOffSum", "" + timeoff[1]);
								tfMap.put("threshold", "" + threshold);
							}

							retList.add(tfMap);
					}
					
				}else{	//之前寄出資料已經達到退學標準45節,每增加10節再寄一次
					if(student != null){
						Map tfMap = new HashMap();
						if((timeoff[0]-raiseflag>=10) || (timeoff[1]-period>=10)){
							//1. 將本星期曠課資料放入 wdList
							hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
							hql += " And abs=2 And noExam=0 And ddate>='" + Toolket.FullDate2Str(sDate) + "'";
							hql += " And ddate<='" + Toolket.FullDate2Str(eDate) + "'";
							hql += " Group by ddate Order by ddate";
							d1List = dao.submitQuery(hql);
							for(DilgOne d1:d1List){
								parameters[0] = d1.getDdate();
								hql = "select d From DilgOne d Where studentNo='" + studentNo + "'";
								hql += " And abs=2 And noExam=0 And ddate=?";
								d1List = dao.submitQuery(hql, parameters);
								
								wtimeoff[1] = d1List.size();
								wtimeoff[0] = 0;
								
								Map wdMap = new HashMap();
								wdMap.put("tfDate", Toolket.Date2Str(d1.getDdate()));
								wdMap.put("raiseFlag", "" + wtimeoff[0]);
								wdMap.put("timeOff", "" + wtimeoff[1]);
								wdList.add(wdMap);
							}
							
							tfMap.put("printable", "1");
							tfMap.put("serialNo", "" + serialNo);
							serialNo++;
							tfMap.put("studentNo", studentNo);
							tfMap.put("studentName", student.getStudentName());
							tfMap.put("deptClassName", Toolket.getClassFullName(student.getDepartClass()));
							if(student.getParentName()!= null)
								tfMap.put("parentName", student.getParentName());
							else
								tfMap.put("parentName", "");
							if(student.getCurrPost()!=null)
								tfMap.put("postNo", student.getCurrPost());
							else
								tfMap.put("postNo", "");
							if(student.getCurrAddr()!=null)
								tfMap.put("address", student.getCurrAddr());
							else
								tfMap.put("address", "");
							tfMap.put("raiseFlagSum", "" + timeoff[0]);
							tfMap.put("timeOffSum", "" + timeoff[1]);
							tfMap.put("wdilgList", wdList);
							tfMap.put("mailLevel", "1");
							tfMap.put("threshold", "45");
							tfMap.put("Memo", "掛號");
						}else{
							tfMap.put("printable", "0");
							tfMap.put("studentNo", studentNo);
							tfMap.put("raiseFlagSum", "" + timeoff[0]);
							tfMap.put("timeOffSum", "" + timeoff[1]);
							tfMap.put("threshold", "45");
						}
							retList.add(tfMap);
							
					}	//if(student != null)
				}	//if(period < 45 && raiseflag < 45 )
			}	//for(String studentNo:studs)
		}	//if(!dilg1List.isEmpty())
		
		return retList;
	}

	
	public List<Map> getTeacherTimeOffNotUpload(String clazzFilter, String teacherId, Calendar mystart, Calendar myend, String pmode, String sortBy){
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTimeInMillis(mystart.getTimeInMillis());
		end.setTimeInMillis(myend.getTimeInMillis());
		
		List<Map> retList = new ArrayList<Map>();
		int iweek = 0;
		String hql = "";
		String sql = "";
		Dtime dtime = new Dtime();
		DtimeClass dc = new DtimeClass();
		DtimeTeacher dt  = new DtimeTeacher();
		List<Dtime> dtime4tch = new ArrayList<Dtime>();
		List<Clazz> tutorClazz = new ArrayList<Clazz>();
		List<Dtime> tutorDtime = new ArrayList<Dtime>();
		List<TimeOffUpload> tfUploaded = new ArrayList<TimeOffUpload>();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String subjectName = "";
		
		/*
		//org.hibernate.SessionFactory factory = (org.hibernate.SessionFactory) Global.context.getBean("sessionFactory");
		Configuration cfg = new Configuration()
		.addResource("tw/edu/chit/model/Dtime.hbm.xml")
		.addResource("tw/edu/chit/model/DtimeTeacher.hbm.xml")
		.addResource("tw/edu/chit/model/DtimeClass.hbm.xml")
		//.addResource("tw/edu/chit/model/Empl.hbm.xml")
	    //.addClass(tw.edu.chit.model.Dtime.class)
	    //.addClass(tw.edu.chit.model.DtimeTeacher.class)
	    //.addClass(tw.edu.chit.model.DtimeClass.class)
	    //.addClass(tw.edu.chit.model.Empl.class)
	    //.addClass(tw.edu.chit.model.TeacherStayTime.class)
	    //.addClass(tw.edu.chit.model.LifeCounseling.class)
	    //.addClass(tw.edu.chit.model.ClassCadre.class)
	    //.addClass(tw.edu.chit.model.TeacherOfficeLocation.class)
	    //.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/test") //JNDI use
	    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    .setProperty("hibernate.connection.url", "jdbc:mysql://172.16.240.1:3306/CIS?useUnicode=true&amp;characterEncoding=utf-8")
	    .setProperty("hibernate.connection.username", "root")
	    .setProperty("hibernate.connection.password", "spring")
	    ;
		SessionFactory sessions = cfg.buildSessionFactory();
		//org.hibernate.SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessions.openSession();
		*/
		
		Session session = dao.getHibernateSession();
		
		//used for teacher idno compare
		class idnoComp implements Comparator {
			public int compare(Object obj1, Object obj2){
				if(obj1 instanceof Map && obj2 instanceof Map){
					String idno1 = ((Map)obj1).get("teacherId").toString();
					String idno2 = ((Map)obj2).get("teacherId").toString();
					int cmp = idno1.compareToIgnoreCase(idno2);
					if( cmp > 0)	return 1;
					else if(cmp == 0) return 0;
					else return -1;
				}
				return 0;
			}
			
			public boolean equals(Object obj){
				return super.equals(obj);
			}
		}

		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		//log.debug("out of while , start:" + start.getTime() + ", end:"+end.getTime() + ", week:" + iweek);
		if(!teacherId.equals("")){
			Map user = new HashMap();
			sql = "select e.* From empl e Where e.idno='" + teacherId + "'";
			List<Map> empls = jdbcDao.findAnyThing(sql);
			if(!empls.isEmpty()){
				user = empls.get(0);
				Calendar[] termStart = null;
				Calendar[] termEnd = null;
				String hdept = "";
				
				if(pmode.equals("1")){	//明細表
					while(start.compareTo(end) <= 0){
						if(Toolket.isHoliday(start)){
							start.add(Calendar.DATE, +1);
							continue;
						}
						iweek = this.WhatIsTheWeek(start.getTime());
						String idno=teacherId;
							//找出當日老師的授課課程
							sql = "Select d.* From Dtime d, Dtime_class dc Where d.Oid=dc.Dtime_oid";
							sql += " And d.techid='" + idno + "' And d.sterm='" + sterm;
							sql += "' And dc.week='" + iweek + "'";
							sql += " And d.depart_class like '" + clazzFilter + "%'";
							sql += " UNION ";
							sql += " Select d.* From Dtime d, Dtime_teacher dt, Dtime_class dc";
							sql += " Where dt.Dtime_oid=d.Oid And dc.Dtime_oid=d.Oid And dc.week='" + iweek +"'";
							sql += " And dt.teach_id='" + idno + "'";
							sql += " And d.depart_class like '" + clazzFilter + "%'";
							sql += " And d.sterm='" + sterm + "'";
							
							dtime4tch = session.createSQLQuery(sql).addEntity(Dtime.class).list();
							
							//導師應增加班會及系時間
							hql = " select c from Clazz c, ClassInCharge cic "
							+ "where c.classNo = cic.classNo "
							+ "  and cic.empOid = ? " 
							+ "  and cic.classNo like ? " 
							+ "  and cic.moduleOids like ? "
							+ "order by c.campusNo, c.schoolNo, c.deptNo, c.classNo";
							tutorClazz = dao.submitQuery(hql, 
									new Object[] {Integer.parseInt(user.get("Oid").toString()), clazzFilter + "%",
									"%|" + UserCredential.AuthorityOnTutor + "|%"});
							//tutorClazz = memberdao.findClassesInChargeByMemberModuleOids(
							//		Integer.parseInt(user.get("Oid").toString()),  UserCredential.AuthorityOnTutor);
							if(tutorClazz != null){
								if(!tutorClazz.isEmpty()){
									for(Clazz clazz:tutorClazz){
										hql = "Select d From Dtime d, DtimeClass dc Where d.oid=dc.dtimeOid ";
										hql += " And d.departClass=? And (d.cscode='50000' OR d.cscode='T0001')";
										hql += " And sterm=? And dc.week=?";
										tutorDtime = dao.submitQuery(hql, new Object[]{clazz.getClassNo(), sterm, iweek});
										if(!tutorDtime.isEmpty()){
											dtime4tch.addAll(tutorDtime);
										}
									}
								}
							}
							
							if(!dtime4tch.isEmpty()){
								Map tfupMap = new HashMap();
								tfupMap.put("teacherId", idno);
								tfupMap.put("teacherName", user.get("cname").toString());
								if(user.get("email") != null){
									tfupMap.put("email", user.get("email").toString());
								}else{
									tfupMap.put("email", "");
								}
								tfupMap.put("teachDate", Toolket.FullDate2Str(start.getTime()));
								tfupMap.put("teachWeek", iweek);
								
								List<Map> notupList = new ArrayList<Map>();
								hql = "From TimeOffUpload Where teachId=? And dtimeOid=? And teachDate=?";
								
								String hql2 = "From DtimeStudaffair Where isRollCall=0 And dtimeOid=?";
								Object[] parameters2 = new Object[1];
								List<DtimeStudaffair> dtSAFs = new ArrayList<DtimeStudaffair>();
								String clazz = "";
								
								DtimeStudaffair ds = new DtimeStudaffair();
								Calendar tCal = Calendar.getInstance();
								boolean isRollCall = true;
								for(Dtime dt4t:dtime4tch){
									clazz = dt4t.getDepartClass();
									//排除不點名課程
									parameters2[0] = dt4t.getOid();
									dtSAFs = dao.submitQuery(hql2, parameters2);
									//碩士班不點名名
									if(clazz.substring(2, 3).equalsIgnoreCase("G")){
										continue;
									}
									
									hdept = clazz.substring(0, 2);
									termStart = Toolket.getDateOfWeek(hdept , 1);
									if(Toolket.chkIsGraduateClass(clazz) && sterm.equals("2")){
										termEnd = Toolket.getDateOfWeek(hdept, 13);
									}else{
										termEnd = Toolket.getDateOfWeek(hdept, 16);
									}
									if(start.compareTo(termStart[0])<0 || start.compareTo(termEnd[1])>0){
										continue;
									}
									
									//排除不點名的上課日期
									isRollCall = true;
									if(!dtSAFs.isEmpty()){
										for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
											ds = dsIter.next();
											tCal.setTime(ds.getTdate());
											if(Toolket.isSameDay(tCal, start)){
												isRollCall = false;
												break;
											}
										}
									}


									if(isRollCall){
										tfUploaded = dao.submitQuery(hql, new Object[]{idno, dt4t.getOid(), start.getTime()});
										if(tfUploaded.isEmpty()){
											Map notupMap = new HashMap();
											Csno csno = dao.findNameOfCourse(dt4t.getCscode());
											if(csno != null){
												subjectName = csno.getChiName();
											}else{
												subjectName = "";
											}
											notupMap.put("dtOid", dt4t.getOid());
											notupMap.put("cscode", dt4t.getCscode());
											notupMap.put("subjectName", subjectName);
											notupMap.put("departClass", dt4t.getDepartClass());
											notupMap.put("deptClassName", Toolket.getClassFullName(dt4t.getDepartClass()));
											notupList.add(notupMap);
										}
									}
								}
								if(!notupList.isEmpty()){
									tfupMap.put("notUploadList", notupList);
									retList.add(tfupMap);
								}
							}
						start.add(Calendar.DATE, +1);
					}	//End of while(start.compareTo(end) <= 0)
				}else if(pmode.equals("2")){	//統計表
					
				}
			}
		}else{
			if(pmode.equals("1")){	//明細表
				Calendar[] termStart = null;
				Calendar[] termEnd = null;
				while(start.compareTo(end) <= 0){
					if(Toolket.isHoliday(start)){
						start.add(Calendar.DATE, +1);
						continue;
					}
					iweek = this.WhatIsTheWeek(start.getTime());
					//log.debug("start:" + start.getTime() + ", end:"+end.getTime() + ", week:" + iweek);
					//找出當日所有有授課的老師
					sql = "select e.* From empl e Where e.idno in(";
					sql += "Select d.techid From Dtime d, Dtime_class dc Where";
					sql += " d.Oid=dc.Dtime_oid And d.sterm='" + sterm + "' And dc.week='" + iweek +"'";
					sql += " And d.depart_class like '" + clazzFilter + "%'";
					sql += " UNION ";
					sql += " Select dt.teach_id From Dtime d, Dtime_teacher dt, Dtime_class dc";
					sql += " Where dt.Dtime_oid=d.Oid And dc.Dtime_oid=d.Oid And dc.week='" + iweek +"'";
					sql += " And d.depart_class like '" + clazzFilter + "%'";
					sql += " And d.sterm='" + sterm + "')";
					
					List<Map> empls = jdbcDao.findAnyThing(sql);
					String idno = "";
					
					for(Map user:empls){
						//找出當日某位老師的授課課程
						idno = user.get("idno").toString();
						sql = "Select d.* From Dtime d, Dtime_class dc Where d.Oid=dc.Dtime_oid";
						sql += " And d.techid='" + idno + "' And d.sterm='" + sterm;
						sql += "' And dc.week='" + iweek + "'";
						sql += " And d.depart_class like '" + clazzFilter + "%'";
						sql += " UNION ";
						sql += " Select d.* From Dtime d, Dtime_teacher dt, Dtime_class dc";
						sql += " Where dt.Dtime_oid=d.Oid And dc.Dtime_oid=d.Oid And dc.week='" + iweek +"'";
						sql += " And dt.teach_id='" + idno + "'";
						sql += " And d.depart_class like '" + clazzFilter + "%'";
						sql += " And d.sterm='" + sterm + "'";
						
						dtime4tch = session.createSQLQuery(sql).addEntity(Dtime.class).list();
						
						//導師應增加班會及系時間
						hql = " select c from Clazz c, ClassInCharge cic "
						+ "where c.classNo = cic.classNo "
						+ "  and cic.empOid = ? " 
						+ "  and cic.classNo like ? " 
						+ "  and cic.moduleOids like ? "
						+ "order by c.campusNo, c.schoolNo, c.deptNo, c.classNo";
						tutorClazz = dao.submitQuery(hql, 
								new Object[] {Integer.parseInt(user.get("Oid").toString()), clazzFilter + "%",
								"%|" + UserCredential.AuthorityOnTutor + "|%"});
						//tutorClazz = memberdao.findClassesInChargeByMemberModuleOids(
						//		Integer.parseInt(user.get("Oid").toString()),  UserCredential.AuthorityOnTutor);
						if(tutorClazz != null){
							if(!tutorClazz.isEmpty()){
								for(Clazz clazz:tutorClazz){
									hql = "Select d From Dtime d, DtimeClass dc Where d.oid=dc.dtimeOid ";
									hql += " And d.departClass=? And (d.cscode='50000' OR d.cscode='T0001')";
									hql += " And sterm=? And dc.week=?";
									tutorDtime = dao.submitQuery(hql, new Object[]{clazz.getClassNo(), sterm, iweek});
									if(!tutorDtime.isEmpty()){
										dtime4tch.addAll(tutorDtime);
									}
								}
							}
						}
						
						if(!dtime4tch.isEmpty()){
							Map tfupMap = new HashMap();
							tfupMap.put("teacherId", idno);
							tfupMap.put("teacherName", user.get("cname").toString());
							if(user.get("email") != null){
								tfupMap.put("email", user.get("email").toString());
							}else{
								tfupMap.put("email", "");
							}
							tfupMap.put("teachDate", Toolket.FullDate2Str(start.getTime()));
							tfupMap.put("teachWeek", iweek);
							
							List<Map> notupList = new ArrayList<Map>();
							hql = "From TimeOffUpload Where teachId=? And dtimeOid=? And teachDate=?";
							
							String hql2 = "From DtimeStudaffair Where isRollCall=0 And dtimeOid=?";
							Object[] parameters2 = new Object[1];
							List<DtimeStudaffair> dtSAFs = new ArrayList<DtimeStudaffair>();
							String clazz = "";
							String hdept = "";
							
							DtimeStudaffair ds = new DtimeStudaffair();
							Calendar tCal = Calendar.getInstance();
							boolean isRollCall = true;
							for(Dtime dt4t:dtime4tch){
								clazz = dt4t.getDepartClass();
								//排除不點名課程
								parameters2[0] = dt4t.getOid();
								dtSAFs = dao.submitQuery(hql2, parameters2);
								//碩士班不點名名
								if(dt4t.getDepartClass().substring(2, 3).equalsIgnoreCase("G")){
									continue;
								}
								
								hdept = clazz.substring(0, 2);
								termStart = Toolket.getDateOfWeek(hdept , 1);
								if(Toolket.chkIsGraduateClass(clazz) && sterm.equals("2")){
									termEnd = Toolket.getDateOfWeek(hdept, 13);
								}else{
									termEnd = Toolket.getDateOfWeek(hdept, 16);
								}
								if(start.compareTo(termStart[0])<0 || start.compareTo(termEnd[1])>0){
									continue;
								}
								
								//排除不點名的上課日期
								isRollCall = true;
								if(!dtSAFs.isEmpty()){
									for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
										ds = dsIter.next();
										tCal.setTime(ds.getTdate());
										if(Toolket.isSameDay(tCal, start)){
											isRollCall = false;
											break;
										}
									}
								}

								if(isRollCall){
									tfUploaded = dao.submitQuery(hql, new Object[]{idno, dt4t.getOid(), start.getTime()});
									if(tfUploaded.isEmpty()){
										Map notupMap = new HashMap();
										Csno csno = dao.findNameOfCourse(dt4t.getCscode());
										if(csno != null){
											subjectName = csno.getChiName();
										}else{
											subjectName = "";
										}
										notupMap.put("dtOid", dt4t.getOid());
										notupMap.put("cscode", dt4t.getCscode());
										notupMap.put("subjectName", subjectName);
										notupMap.put("departClass", dt4t.getDepartClass());
										notupMap.put("deptClassName", Toolket.getClassFullName(dt4t.getDepartClass()));
										notupList.add(notupMap);
									}
									
								}
							}
							if(!notupList.isEmpty()){
								tfupMap.put("notUploadList", notupList);
								retList.add(tfupMap);
							}
						}
					}	// End of for(Empl user:empls)
					start.add(Calendar.DATE, +1);
				}	//End of while(start.compareTo(end) <= 0)
			}else if(pmode.equals("2")){	//統計表
				Calendar[] termStart = null;
				Calendar[] termEnd = null;
				while(start.compareTo(end) <= 0){
					if(Toolket.isHoliday(start)){
						start.add(Calendar.DATE, +1);
						continue;
					}
					iweek = this.WhatIsTheWeek(start.getTime());
					//log.debug("start:" + start.getTime() + ", end:"+end.getTime() + ", week:" + iweek);
					//找出當日所有有授課的老師
					sql = "select e.* From empl e Where e.idno in(";
					sql += "Select d.techid From Dtime d, Dtime_class dc Where";
					sql += " d.Oid=dc.Dtime_oid And d.sterm='" + sterm + "' And dc.week='" + iweek +"'";
					sql += " And d.depart_class like '" + clazzFilter + "%'";
					sql += " UNION ";
					sql += " Select dt.teach_id From Dtime d, Dtime_teacher dt, Dtime_class dc";
					sql += " Where dt.Dtime_oid=d.Oid And dc.Dtime_oid=d.Oid And dc.week='" + iweek +"'";
					sql += " And d.depart_class like '" + clazzFilter + "%'";
					sql += " And d.sterm='" + sterm + "')";
					
					List<Map> empls = jdbcDao.findAnyThing(sql);
					String idno = "";
					
					for(Map user:empls){
						//找出當日某位老師的授課課程
						idno = user.get("idno").toString();
						sql = "Select d.* From Dtime d, Dtime_class dc Where d.Oid=dc.Dtime_oid";
						sql += " And d.techid='" + idno + "' And d.sterm='" + sterm;
						sql += "' And dc.week='" + iweek + "'";
						sql += " And d.depart_class like '" + clazzFilter + "%'";
						sql += " UNION ";
						sql += " Select d.* From Dtime d, Dtime_teacher dt, Dtime_class dc";
						sql += " Where dt.Dtime_oid=d.Oid And dc.Dtime_oid=d.Oid And dc.week='" + iweek +"'";
						sql += " And dt.teach_id='" + idno + "'";
						sql += " And d.depart_class like '" + clazzFilter + "%'";
						sql += " And d.sterm='" + sterm + "'";
						
						dtime4tch = session.createSQLQuery(sql).addEntity(Dtime.class).list();
						
						//導師應增加班會及系時間
						hql = " select c from Clazz c, ClassInCharge cic "
						+ "where c.classNo = cic.classNo "
						+ "  and cic.empOid = ? " 
						+ "  and cic.classNo like ? " 
						+ "  and cic.moduleOids like ? "
						+ "order by c.campusNo, c.schoolNo, c.deptNo, c.classNo";
						tutorClazz = dao.submitQuery(hql, 
								new Object[] {Integer.parseInt(user.get("Oid").toString()), clazzFilter + "%",
								"%|" + UserCredential.AuthorityOnTutor + "|%"});
						//tutorClazz = memberdao.findClassesInChargeByMemberModuleOids(
						//		Integer.parseInt(user.get("Oid").toString()),  UserCredential.AuthorityOnTutor);
						if(tutorClazz != null){
							if(!tutorClazz.isEmpty()){
								for(Clazz clazz:tutorClazz){
									hql = "Select d From Dtime d, DtimeClass dc Where d.oid=dc.dtimeOid ";
									hql += " And d.departClass=? And (d.cscode='50000' OR d.cscode='T0001')";
									hql += " And sterm=? And dc.week=?";
									tutorDtime = dao.submitQuery(hql, new Object[]{clazz.getClassNo(), sterm, iweek});
									if(!tutorDtime.isEmpty()){
										dtime4tch.addAll(tutorDtime);
									}
								}
							}
						}
						
						if(!dtime4tch.isEmpty()){
							Map tfupMap = new HashMap();
							tfupMap.put("teacherId", idno);
							tfupMap.put("teacherName", user.get("cname").toString());
							
							List<Map> notupList = new ArrayList<Map>();
							hql = "From TimeOffUpload Where teachId=? And dtimeOid=? And teachDate=?";
							
							String hql2 = "From DtimeStudaffair Where isRollCall=0 And dtimeOid=?";
							Object[] parameters2 = new Object[1];
							List<DtimeStudaffair> dtSAFs = new ArrayList<DtimeStudaffair>();
							String clazz = "";
							String hdept = "";
							
							DtimeStudaffair ds = new DtimeStudaffair();
							Calendar tCal = Calendar.getInstance();
							boolean isRollCall = true;
							int ncnt = 0;
							for(Dtime dt4t:dtime4tch){
								clazz = dt4t.getDepartClass();
								//排除不點名課程
								parameters2[0] = dt4t.getOid();
								dtSAFs = dao.submitQuery(hql2, parameters2);
								//碩士班不點名名
								if(dt4t.getDepartClass().substring(2, 3).equalsIgnoreCase("G")){
									continue;
								}
								
								hdept = clazz.substring(0, 2);
								termStart = Toolket.getDateOfWeek(hdept , 1);
								if(Toolket.chkIsGraduateClass(clazz) && sterm.equals("2")){
									termEnd = Toolket.getDateOfWeek(hdept, 13);
								}else{
									termEnd = Toolket.getDateOfWeek(hdept, 16);
								}
								if(start.compareTo(termStart[0])<0 || start.compareTo(termEnd[1])>0){
									continue;
								}
								
								//排除不點名的上課日期
								isRollCall = true;
								if(!dtSAFs.isEmpty()){
									for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
										ds = dsIter.next();
										tCal.setTime(ds.getTdate());
										if(Toolket.isSameDay(tCal, start)){
											isRollCall = false;
											break;
										}
									}
								}

								if(isRollCall){
									tfUploaded = dao.submitQuery(hql, new Object[]{idno, dt4t.getOid(), start.getTime()});
									if(tfUploaded.isEmpty()){
										ncnt++;
									}
									
								}
							}
							if(ncnt > 0){
								tfupMap.put("count", ncnt);
								retList.add(tfupMap);
							}
						}
					}	// End of for(Empl user:empls)
					start.add(Calendar.DATE, +1);
				}	//End of while(start.compareTo(end) <= 0)
				List<Map> uniRet = new ArrayList<Map>();
				if(!retList.isEmpty()){
					Collections.sort(retList,new idnoComp());
					String preidno = retList.get(0).get("teacherId").toString();
					Map preRet = retList.get(0);
					int count = (Integer)preRet.get("count");
					for(ListIterator<Map> retIter=retList.listIterator(); retIter.hasNext();){
						Map ret = retIter.next();
						if(ret.get("teacherId").toString().equals(preidno)){
							count += (Integer)ret.get("count");
						}else{
							Map uMap = new HashMap();
							uMap.putAll(preRet);
							uMap.put("count", count);
							uniRet.add(uMap);
							
							preRet = ret;
							preidno = ret.get("teacherId").toString();
							count = (Integer)ret.get("count");
						}
					}
					Map uMap = new HashMap();
					uMap.putAll(preRet);
					uMap.put("count", count);
					uniRet.add(uMap);
				}
				
				return uniRet;
			}
		}
		/*
		 * 
		//自建 session 必須關閉
		session.close();
		sessions.close();
		*/
		if(sortBy.equals("1") && pmode.equals("1")){
			Collections.sort(retList,new idnoComp());
		}
		return retList;
	}
	
	
	public List<Dtimes> findDtimesByDtimeOid(int dtOid){
		ScoreManager scm = (ScoreManager) Global.context.getBean("scoreManager");
		List<Dtimes> dtsList = new ArrayList<Dtimes>();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
								
		Dtime dt = this.getDtimeByOid(dtOid);
		
		if(dt != null){
			List<DtimeClass> dcList = new ArrayList<DtimeClass>();
			//DtimeClass[] dcArray;
			dcList = dao.submitQuery("From DtimeClass Where dtimeOid=" + dt.getOid()+ " Order by week,begin");
			dt.setChiName2(scm.findCourseName(dt.getCscode()));
			dt.setClassName2(scm.findClassName(dt.getDepartClass()));
			Dtimes dts = new Dtimes();
			dts.setDtime(dt);
			dts.setPeriods(dcList.toArray(new DtimeClass[dcList.size()]));
			dtsList.add(dts);
		}
		
		return dtsList;
	}
	
	public Map getTimeOffBySubject(String studentNo, int oid) {
		int timeOff = 0;
		int tflen = Global.TimeOffList.size();
		int absType[] = new int[tflen];
		Map retMap = new HashMap();
		int iweek = 0, begin = 0, end = 0, status = 0;
		String hql = "";
		
		for(int i=0; i<absType.length; i++) {
			absType[i] = 0;
		}
		
			hql = "Select d From Dilg d Where ";
		hql = hql + "d.studentNo='" + studentNo + "'";
		List<Dilg> dilgList = dao.submitQuery(hql);
		// log.debug("calTimeOffBySubject->dilgList:size:hql:[" +
		// dilgList.size() + "]"+ hql);

		hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + oid;
		List<DtimeClass> dcList = dao.submitQuery(hql);
		// log.debug("calTimeOffBySubject->DtimeClassList:size:hql:[" +
		// dcList.size() + "]"+ hql);
		if (dilgList.size() > 0) {
			for (Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter
					.hasNext();) {
				Dilg dilg = dilgIter.next();
				for (Iterator<DtimeClass> dcIter = dcList.iterator(); dcIter
						.hasNext();) {
					DtimeClass dclass = dcIter.next();
					iweek = WhatIsTheWeek(dilg.getDdate());

					begin = Integer.parseInt(dclass.getBegin().trim());
					end = Integer.parseInt(dclass.getEnd().trim());
					// log.debug("calTimeOffBySubject->DtimeClass->:begin,end:["
					// + begin + ","+ end + "]");
					if (iweek == dclass.getWeek()) {
						for (int j = begin; j <= end; j++) {
							status = 0;
							switch (j) {
							case 0:
								if (dilg.getAbs0() != null) {
									status = dilg.getAbs0();
								}
								break;
							case 1:
								if (dilg.getAbs1() != null) {
									status = dilg.getAbs1();
								}
								break;
							case 2:
								if (dilg.getAbs2() != null) {
									status = dilg.getAbs2();
								}
								break;
							case 3:
								if (dilg.getAbs3() != null) {
									status = dilg.getAbs3();
								}
								break;
							case 4:
								if (dilg.getAbs4() != null) {
									status = dilg.getAbs4();
								}
								break;
							case 5:
								if (dilg.getAbs5() != null) {
									status = dilg.getAbs5();
								}
								break;
							case 6:
								if (dilg.getAbs6() != null) {
									status = dilg.getAbs6();
								}
								break;
							case 7:
								if (dilg.getAbs7() != null) {
									status = dilg.getAbs7();
								}
								break;
							case 8:
								if (dilg.getAbs8() != null) {
									status = dilg.getAbs8();
								}
								break;
							case 9:
								if (dilg.getAbs9() != null) {
									status = dilg.getAbs9();
								}
								break;
							case 10:
								if (dilg.getAbs10() != null) {
									status = dilg.getAbs10();
								}
								break;
							case 11:
								if (dilg.getAbs11() != null) {
									status = dilg.getAbs11();
								}
								break;
							case 12:
								if (dilg.getAbs12() != null) {
									status = dilg.getAbs12();
								}
								break;
							case 13:
								if (dilg.getAbs13() != null) {
									status = dilg.getAbs13();
								}
								break;
							case 14:
								if (dilg.getAbs14() != null) {
									status = dilg.getAbs14();
								}
								break;
							case 15:
								if (dilg.getAbs15() != null) {
									status = dilg.getAbs15();
								}
								break;

							}
							if (!(status == 0 || status == 5 || status == 6)) {
								absType[status]++;
								timeOff++;
							}

						}
					}
				}
			}
		} else {
			timeOff = 0;
		}
		retMap.put("timeOff", timeOff);
		retMap.put("absType", absType);
		return retMap;
	}

	public List getDilgByStudentNo(String studentNo, String mode) {
		String hql ="";
		String sterm = Toolket.getSysParameter("School_term");
		List dilgList = new ArrayList();
		Dilg dilg;
			
		if(mode.equalsIgnoreCase("subject")) {
			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
			List<Code5> tfType = Global.TimeOffList;
			int tflen = Global.TimeOffList.size();
			String[] tfs = new String[tflen];
			for(Code5 code5:tfType){
				if(code5.getIdno().trim().equals("0")){
					tfs[0] = "";
				}else{
					tfs[Integer.parseInt(code5.getIdno().trim())] =
						code5.getName().substring(code5.getIdno().trim().length());
				}
			}
			
			
			Seld seld;
			List<Dtime> dtimeList;
			Dtime dtime;
			int dtOid;
			int tfsum;
			int tfLimit, tfwarn, elearnDilg = 0;
			Map tfsMap = new HashMap();
			int[] absType = new int[tflen];
			String[] weeks = {"一", "二", "三", "四", "五", "六", "日"};
			for(Iterator<Seld> seldIter=seldList.iterator(); seldIter.hasNext();) {
				seld = seldIter.next();
				dtOid = seld.getDtimeOid();
				if(seld.getElearnDilg() != null){
					elearnDilg = seld.getElearnDilg();
				}else{
					elearnDilg = 0;
				}
				
				dtimeList = (List<Dtime>)(dao.submitQuery("From Dtime Where Oid=" + dtOid));
				dtime = dtimeList.get(0);
				
				hql = "Select ds From DtimeClass ds Where ds.dtimeOid=" + dtOid;
				List<DtimeClass> dcList = dao.submitQuery(hql);
				String dcStr = "課程時間："; 
				for(Iterator<DtimeClass> dcIter=dcList.iterator(); dcIter.hasNext();){
					DtimeClass dc = dcIter.next();
					dcStr += "星期(" +  weeks[dc.getWeek()-1] + "):" + dc.getBegin() + "~" + dc.getEnd() + "節";
					if(dcIter.hasNext()) dcStr += ", ";
				}
				
				tfLimit = 0;

				if (Toolket.chkIsGraduateClass(dtime.getDepartClass()) && sterm.equals("2")) {
					if(dtime.getThour() * 14 % 3 > 0){
						tfLimit = dtime.getThour() * 14 / 3 + 1;
					}else{
						tfLimit = dtime.getThour() * 14 / 3;
					}
					tfwarn = (int)(tfLimit* 0.9);
				}
				else {
					tfLimit = (int)Math.round(Math.ceil(dtime.getThour() * 18 / 3));
					tfwarn = (int)(tfLimit * 0.9);
				}

				tfsMap = this.getTimeOffBySubject(studentNo, seld.getDtimeOid());
				tfsum = Integer.parseInt(tfsMap.get("timeOff").toString());
				absType = (int[])tfsMap.get("absType");
				
				String absStr = "";
				for(int k=2; k<absType.length; k++){
					absStr += tfs[k] + ":" + absType[k] + "節";
					if((k+1)<absType.length){
						absStr += ", ";
					}
				}
				
				Map tfMap = new HashMap();
				tfMap.put("cscode", seld.getCscode());
				tfMap.put("dtimeOid", seld.getDtimeOid());
				tfMap.put("departClass", dtime.getDepartClass());
				tfMap.put("subjectName", seld.getCscodeName());
				tfMap.put("period", dtime.getThour());
				tfMap.put("tfLimit", tfLimit);
				tfMap.put("timeOff", tfsum+elearnDilg);
				tfMap.put("elearnDilg", elearnDilg);
				tfMap.put("absType", absStr);
				tfMap.put("dtimeClass", dcStr);
				
				if((tfsum+elearnDilg) >= tfLimit && tfLimit != 0) {
					tfMap.put("warnning", "yes");
				} else {
					tfMap.put("warnning", "no");
				}
				//if(studentNo.equalsIgnoreCase("96107025")){
				//	log.debug("tfsum:" + tfsum[0] + ", elearnning:" + elearnDilg);
				//}
				dilgList.add(tfMap);
			}
		} else if(mode.equalsIgnoreCase("all")) {
			dilgList = dao.findDilgByStudentNo(studentNo);
			for(Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter.hasNext();) {
				dilg = dilgIter.next();
				dilg.setSddate(Toolket.printDate(dilg.getDdate()));
				dilg.setDeptClassName(Toolket.getClassFullName(dilg.getDepartClass()));
				dilg.setPdate(Global.defaultDateFormat.format(dilg.getDdate()));
				if(dilg.getAbs0()!= null && dilg.getAbs0()!= 0)
					dilg.setAbsName0(Toolket.getTimeOff(dilg.getAbs0().toString()).substring(1,2));
				if(dilg.getAbs1()!= null && dilg.getAbs1()!= 0)
					//log.debug("TimeOffQuery->Abs1:AbsName1:" + dilg.getAbs1() + ":" + Toolket.getTimeOff(dilg.getAbs1().toString()));
					dilg.setAbsName1(Toolket.getTimeOff(dilg.getAbs1().toString()).substring(1,2));
				if(dilg.getAbs2()!= null && dilg.getAbs2()!= 0)
					dilg.setAbsName2(Toolket.getTimeOff(dilg.getAbs2().toString()).substring(1,2));
				if(dilg.getAbs3()!= null && dilg.getAbs3()!= 0)
					dilg.setAbsName3(Toolket.getTimeOff(dilg.getAbs3().toString()).substring(1,2));
				if(dilg.getAbs4()!= null && dilg.getAbs4()!= 0)
					dilg.setAbsName4(Toolket.getTimeOff(dilg.getAbs4().toString()).substring(1,2));
				if(dilg.getAbs5()!= null && dilg.getAbs5()!= 0)
					dilg.setAbsName5(Toolket.getTimeOff(dilg.getAbs5().toString()).substring(1,2));
				if(dilg.getAbs6()!= null && dilg.getAbs6()!= 0)
					dilg.setAbsName6(Toolket.getTimeOff(dilg.getAbs6().toString()).substring(1,2));
				if(dilg.getAbs7()!= null && dilg.getAbs7()!= 0)
					dilg.setAbsName7(Toolket.getTimeOff(dilg.getAbs7().toString()).substring(1,2));
				if(dilg.getAbs8()!= null && dilg.getAbs8()!= 0)
					dilg.setAbsName8(Toolket.getTimeOff(dilg.getAbs8().toString()).substring(1,2));
				if(dilg.getAbs9()!= null && dilg.getAbs9()!= 0)
					dilg.setAbsName9(Toolket.getTimeOff(dilg.getAbs9().toString()).substring(1,2));
				if(dilg.getAbs10()!= null && dilg.getAbs10()!= 0)
					dilg.setAbsName10(Toolket.getTimeOff(dilg.getAbs10().toString()).substring(1,2));
				if(dilg.getAbs11()!= null && dilg.getAbs11()!= 0)
					dilg.setAbsName11(Toolket.getTimeOff(dilg.getAbs11().toString()).substring(1,2));
				if(dilg.getAbs12()!= null && dilg.getAbs12()!= 0)
					dilg.setAbsName12(Toolket.getTimeOff(dilg.getAbs12().toString()).substring(1,2));
				if(dilg.getAbs13()!= null && dilg.getAbs13()!= 0)
					dilg.setAbsName13(Toolket.getTimeOff(dilg.getAbs13().toString()).substring(1,2));
				if(dilg.getAbs14()!= null && dilg.getAbs14()!= 0)
					dilg.setAbsName14(Toolket.getTimeOff(dilg.getAbs14().toString()).substring(1,2));
				if(dilg.getAbs15()!= null && dilg.getAbs15()!= 0)
					dilg.setAbsName15(Toolket.getTimeOff(dilg.getAbs15().toString()).substring(1,2));

			}
		} else if(mode.equalsIgnoreCase("elearn")) {
			List<Seld> seldList = dao.findSeldByStudentNo(studentNo, sterm);
			
			for(Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();){
				Seld seld = seldIter.next();
				if(seld.getElearnDilg() != null){
					dilgList.add(seld);
				}
			}
		}
		
		return dilgList;
		
	}
	
	public List<DtimeStudaffair> getTermRollCallInfo(String myclass, String cscode){
		
		String term = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String hdept = myclass.substring(0, 2);
		
		Calendar[] termStart = Toolket.getDateOfWeek(hdept , 1);
		Calendar[] termEnd = null;
		if(Toolket.chkIsGraduateClass(myclass) && term.equals("2")){
			termEnd = Toolket.getDateOfWeek(hdept, 13);
		}else{
			termEnd = Toolket.getDateOfWeek(hdept, 16);
		}

		Calendar start = termStart[0];
		Calendar end = termEnd[1];
		
		List<DtimeStudaffair> retList = new ArrayList<DtimeStudaffair>();
		String hql = "Select dc From Dtime d, DtimeClass dc Where dc.dtimeOid=d.oid And d.sterm=? And d.departClass=? and d.cscode=?";
		List<DtimeClass> dtList = dao.submitQuery(hql, new Object[]{term, myclass, cscode});
		
		if(!dtList.isEmpty()){
			List<DtimeStudaffair> dsList = new ArrayList<DtimeStudaffair>();
			int dtimeOid = dtList.get(0).getDtimeOid();
			hql = "From DtimeStudaffair Where dtimeOid=" + dtimeOid + " Order by tDate";
			dsList = dao.submitQuery(hql);
			
			List<Integer> weeks = new ArrayList<Integer>();
			weeks.add(dtList.get(0).getWeek());
			int preWeek = dtList.get(0).getWeek();
			
			for(DtimeClass dc:dtList){
				if(dc.getWeek().intValue() != preWeek){
					weeks.add(dc.getWeek());
					preWeek = dc.getWeek();
				}
			}
			
			int weekn = 0, cnt = 0;
			Calendar tCal = Calendar.getInstance();
			while(start.compareTo(end) <= 0){
				weekn = start.get(Calendar.DAY_OF_WEEK);
				if(weekn==1) weekn=7;
				else weekn--;
				for(Integer weekv:weeks){
					if(weekn==(weekv.intValue())){
						DtimeStudaffair dts = new DtimeStudaffair();
						dts.setOid(cnt);
						dts.setDtimeOid(dtimeOid);
						dts.setTdate(start.getTime());
						dts.setIsRollCall((short) 0);
						for(DtimeStudaffair ds:dsList){
							tCal.setTime(ds.getTdate());
							if(tCal.compareTo(start) == 0){
								dts.setIsRollCall(ds.getIsRollCall());
							}
						}
						cnt++;
						retList.add(dts);
					}
				}
				start.add(Calendar.DATE, 1);
			}
		}
		return retList;
	}
	
	public ActionMessages txModifyRollCall(List<DtimeStudaffair> selDS, String idno){
		ActionMessages msgs = new ActionMessages();
		int dtimeOid = selDS.get(0).getDtimeOid();
		try{
			String sql = "Delete From Dtime_studaffair Where Dtime_oid=" + dtimeOid;
			jdbcDao.executesql(sql);
			
			for(DtimeStudaffair ds:selDS){
				DtimeStudaffair dsnew = new DtimeStudaffair();
				dsnew.setDtimeOid(ds.getDtimeOid());
				dsnew.setIsRollCall(ds.getIsRollCall());
				dsnew.setModifierId(idno);
				dsnew.setTdate(ds.getTdate());
				dao.saveObject(dsnew);
			}
		}catch(Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.toString()));
		}
		return msgs;
	}
	
	public List<DtimeStudaffair> getDtimeStudaffair(int dtimeOid){
		String hql = "From DtimeStudaffair Where isRollCall=0 And dtimeOid=? order by tdate";
		Object[] parameters = new Object[]{dtimeOid};
		List<DtimeStudaffair> dtSAFs = dao.submitQuery(hql, parameters);
		
		return dtSAFs;
	}
	
	public List<CounselingCode> findCounselingCode(String type){
		String hql = "From CounselingCode Where itype='" + type + "' order by sequence";
		return dao.submitQuery(hql);
	}

	public CounselingCode findCounselingCodeByNo(String type, int itemNo){
		String hql = "From CounselingCode Where itype=? And itemNo=? order by sequence";
		CounselingCode code = null;
		List<CounselingCode> codeList = dao.submitQuery(hql, new Object[]{type, itemNo});
		if(!codeList.isEmpty()){
			code = codeList.get(0);
		}
		return code;
	}

	public List<StudCounseling> findCounselingByIdno(String type, String idno){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		String hql = "From StudCounseling Where ctype=? And teacherId=? order by cdate";
		List<StudCounseling> counselingList = dao.submitQuery(hql, new Object[]{type, idno});
		CounselingCode code = null;
		if(!counselingList.isEmpty()){
			String codeType = "";
			for(StudCounseling counsel:counselingList){
				counsel.setSimpleCdate(df.format(counsel.getCdate()));
				counsel.setStudentName(Toolket.getStudentName(counsel.getStudentNo()));
				counsel.setClassName(Toolket.getClassFullName(counsel.getDepartClass()));
				codeType = counsel.getCtype();
				if(codeType.equals("U")) codeType = "L";
				code = this.findCounselingCodeByNo(codeType, counsel.getItemNo());
				if(code != null){
					counsel.setItemName(code.getItemName());
				}else{
					counsel.setItemName("輔導項目代碼錯誤!");
				}
			}
		}
		return dao.submitQuery(hql, new Object[]{type, idno});
	}

	public List<StudCounseling> findCounselingByStudentNo(String studentNo){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String hql = "From StudCounseling Where studentNo=? order by cdate";
		List<StudCounseling> counselingList = dao.submitQuery(hql);
		CounselingCode code = null;
		if(!counselingList.isEmpty()){
			String codeType = "";
			for(StudCounseling counsel:counselingList){
				counsel.setSimpleCdate(df.format(counsel.getCdate()));
				counsel.setStudentName(Toolket.getStudentName(counsel.getStudentNo()));
				counsel.setClassName(Toolket.getClassFullName(counsel.getDepartClass()));
				codeType = counsel.getCtype();
				if(codeType.equals("U")) codeType = "L";
				code = this.findCounselingCodeByNo(codeType, counsel.getItemNo());
				if(code != null){
					counsel.setItemName(code.getItemName());
				}else{
					counsel.setItemName("輔導項目代碼錯誤!");
				}
			}
		}
		return dao.submitQuery(hql, new Object[]{studentNo});
	}
	
	public List<StudCounseling> findCounselingByInput(String schoolYear, String schoolTerm, String type,  
			String idno, String studentNo,String departClass, Calendar sCal, Calendar eCal){
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		Object[] parameters = null;
		List<StudCounseling> counselList = new ArrayList<StudCounseling>();
		
		String hql = "From StudCounseling Where ctype='" + type + "'";
		
		if(!schoolYear.equals("") && !schoolTerm.equals("")){
			hql += " And schoolYear=" + schoolYear + " And schoolTerm=" + schoolTerm;
		}else if(!schoolYear.equals("") && schoolTerm.equals("")){
			hql += " And schoolYear=" + schoolYear;
		}
		if(!idno.equals("") && idno != null){
			hql += " And teacherId='" + idno + "'";
		}
		if(!studentNo.equals("") && studentNo != null){
			hql += " And studentNo='" + studentNo + "'";
		}
		
		if(!departClass.equals("")){
			if(type.equalsIgnoreCase("T")|| type.equalsIgnoreCase("U")){
				hql += " And departClass like '" + departClass + "%' ";
			}else if(type.equalsIgnoreCase("L")){
				hql += " And courseClass like '" + departClass + "%' ";
			}
		}
		
		if(sCal != null && eCal != null){
			parameters = new Object[]{sCal.getTime(), eCal.getTime()};
			hql += " And cdate between ? And ? order by schoolYear, schoolTerm, departClass, studentNo, cdate";
			counselList = dao.submitQuery(hql, parameters);
		}else if(sCal != null && eCal == null){
			if(sCal.compareTo(today) <= 0){
				parameters = new Object[]{sCal.getTime(), today.getTime()};
				hql += " And cdate between ? And ? order by schoolYear, schoolTerm, departClass, studentNo, cdate";
				counselList = dao.submitQuery(hql, parameters);
			}else{
				//起始日大於今天??? nothing to do 
			}
		}else if(sCal == null && eCal != null){
			if(eCal.compareTo(today) <= 0){
				parameters = new Object[]{sCal.getTime(), today.getTime()};
				hql += " order by schoolYear, schoolTerm, departClass, studentNo, cdate";
				counselList = dao.submitQuery(hql, parameters);
			}else{
				hql += " order by schoolYear, schoolTerm, departClass, studentNo, cdate";
				counselList = dao.submitQuery(hql, parameters);
			}
		}else{
			hql += " order by schoolYear, schoolTerm, departClass, studentNo, cdate";
			counselList = dao.submitQuery(hql, parameters);
		}

		if(!counselList.isEmpty()){
			CounselingCode code = null;
			String codeType = "";
			for(StudCounseling counsel:counselList){
				counsel.setSimpleCdate(df.format(counsel.getCdate()));
				counsel.setStudentName(Toolket.getStudentName(counsel.getStudentNo()));
				counsel.setClassName(Toolket.getClassFullName(counsel.getDepartClass()));
				codeType = counsel.getCtype();
				counsel.setTeacherName(Toolket.getEmplName(counsel.getTeacherId()));
				if(codeType.equals("U")) codeType = "L";
				code = this.findCounselingCodeByNo(codeType, counsel.getItemNo());
				if(code != null){
					counsel.setItemName(code.getItemName());
				}else{
					counsel.setItemName("輔導項目代碼錯誤!");
				}
			}
		}

		return counselList;
	}

	public List<Map> findCounselingReport(String schoolYear, String schoolTerm, String type,  
			String departClass, String teacherId){
		List<Map> counselList = new ArrayList<Map>();
		String thisYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String thisTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		
		String sql = "";
		if(type.equalsIgnoreCase("T")){
			sql = "Select teacherId, depart_class From StudCounseling Where (ctype='T' OR ctype='U')";
		}else if(type.equalsIgnoreCase("L")){
			sql = "Select teacherId, courseClass, cscode, courseName From StudCounseling Where ctype='L'";
		}
		if(!teacherId.equals("")){
			sql += " And teacherId='" + teacherId + "'";
		}
		sql += " And schoolYear=" + schoolYear + " And schoolTerm=" + schoolTerm;
		if(type.equalsIgnoreCase("T")){
			sql += " And depart_class like '" + departClass + "%' ";
			sql += " group by CONCAT(teacherId, depart_class) order by depart_class";
		}else if(type.equalsIgnoreCase("L")){
			sql += " And courseClass like '" + departClass + "%' ";
			sql += " group by CONCAT(teacherId, courseClass, cscode) order by courseClass";
		}
		//System.out.println(sql);
		List<Map> maps = jdbcDao.findAnyThing(sql);
		
		String idno = "";
		String clazz = "";
		String oid="";
		String cscode = "";
		String courseName = "";
		String teacherName = "";
		String subSql = "";
		int countT = 0;
		int countU = 0;
		int countL = 0;
		int countL_UT = 0;
		int countStudent = 0;
		int countStudent_UT = 0;
		for(Map course:maps){
			idno = course.get("teacherId").toString();
			teacherName = Toolket.getEmplName(idno);
			subSql = " And schoolYear=" + schoolYear + " And schoolTerm=" + schoolTerm;
			if(type.equalsIgnoreCase("T")){
				clazz = course.get("depart_class").toString();
				subSql += " And teacherId='" + idno + "'";
				subSql += " And depart_class='" + clazz + "'";
				
				sql = "Select count(*) From StudCounseling Where ctype='T'" + subSql;				
				countT = jdbcDao.getRecordsCount(sql);
				
				sql = "Select count(*) From StudCounseling Where ctype='U'" + subSql;
				countU = jdbcDao.getRecordsCount(sql);
				
				if(schoolYear.equals(thisYear) && schoolTerm.equals(thisTerm)){
					sql = "select count(*) From stmd Where depart_class='" + clazz + "'";
					countStudent = jdbcDao.getRecordsCount(sql);
				}else{
					sql = "select count(*) From ScoreHist Where stdepart_class='" + clazz + "'";
					sql += " And cscode='99999' And school_year='" + schoolYear + "'";
					sql += " And school_term='" + schoolTerm + "'";
					//sql += " Group by student_no";
					//System.out.println(sql);
					countStudent = jdbcDao.getRecordsCount(sql);
				}
				//計算已輔導過的學生人數
				sql=" Select count(*) From(" +
						" Select studentNo, count(*) From StudCounseling " +
						" Where schoolYear="+schoolYear+" And schoolTerm="+schoolTerm+ 
						"   And teacherId='"+idno+"' " +
						"   And depart_class='"+clazz+"' " +
						" Group by studentNo) c";
				countStudent_UT = jdbcDao.getRecordsCount(sql);
				
				
				Map cMap = new HashMap();
				cMap.put("schoolYear", schoolYear);
				cMap.put("schoolTerm", schoolTerm);
				cMap.put("departClass", clazz);
				cMap.put("className", Toolket.getClassFullName(clazz));
				cMap.put("teacherId", idno);
				cMap.put("teacherName", teacherName);
				cMap.put("countStudent", "" + countStudent);
				cMap.put("countStudent_UT", "" + countStudent_UT);
				cMap.put("countT", "" + countT);
				cMap.put("countU", "" + countU);
				counselList.add(cMap);
			}else if(type.equalsIgnoreCase("L")){
				clazz = course.get("courseclass").toString();
				oid = course.get("oid").toString();
				cscode = course.get("cscode").toString();
				courseName = course.get("courseName").toString();
				
				subSql += " And teacherId='" + idno + "'";
				subSql += " And courseClass='" + clazz + "' And cscode='" + cscode +"'";

				sql = "Select count(*) From StudCounseling Where ctype='L'" + subSql;
				countL = jdbcDao.getRecordsCount(sql);
				//計算已輔導過的學生人數
				sql=" Select count(*) From(" +
						" Select studentNo, count(*) From StudCounseling " +
						" Where schoolYear="+schoolYear+
						"   And schoolTerm="+schoolTerm+ 
						"   And teacherId='"+idno+"' " +
						"   And courseClass='" + clazz + "' " +
						"   And cscode='" + cscode +"' " +
						//"   And depart_class='"+clazz+"' " +
						" Group by studentNo) c ";
				countL_UT = jdbcDao.getRecordsCount(sql);
				
				Map cMap = new HashMap();
				cMap.put("schoolYear", schoolYear);
				cMap.put("schoolTerm", schoolTerm);
				cMap.put("departClass", clazz);
				cMap.put("className", Toolket.getClassFullName(clazz));
				cMap.put("teacherId", idno);
				cMap.put("teacherName", teacherName);
				cMap.put("courseName", courseName);
				cMap.put("countL", "" + countL);
				cMap.put("countL_UT", "" + countL_UT);
				counselList.add(cMap);

			}
		}

		return counselList;
	}

	public ActionMessages delStudCounselings(List<StudCounseling> counselList){
		ActionMessages err = new ActionMessages();
		
		try{
			for(StudCounseling counsel:counselList){
				dao.removeObject(counsel);
			}
		}catch (Exception e){
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.getStackTrace()));
		}
		
		return err;
	}
	
	public ActionMessages saveStudCounselingByForm(String idno, DynaActionForm aForm, String ctype){
		ActionMessages err = new ActionMessages();
		
		short schoolYear = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR));
		short schoolTerm = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM));
		String departClass = "";
		String studentNo  = aForm.getString("studentNo").trim();
		String cscode = aForm.getString("cscode").trim();
		String courseName = aForm.getString("courseName").trim();
		String courseClass = aForm.getString("courseClass").trim();
		String cdate = aForm.getString("cdate").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();
		Student student = this.findStudentByStudentNo(studentNo);
		if(student != null){
			departClass = student.getDepartClass();
		}else{
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "輔導記錄存檔失敗,無此學生!"));
			return err;
		}
		if(cdate.indexOf('/') >= 0){
			cdate.replaceAll("/", "-");
		}
		Date datec = Toolket.parseFullDate(cdate);
		
		try{
			StudCounseling counsel = new StudCounseling();
			counsel.setSchoolYear(schoolYear);
			counsel.setSchoolTerm(schoolTerm);
			counsel.setCdate(datec);
			counsel.setContent(content);
			counsel.setCtype(ctype);
			counsel.setDepartClass(departClass);
			counsel.setItemNo(Integer.parseInt(itemNo));
			counsel.setMdate(Calendar.getInstance().getTime());
			counsel.setStudentNo(studentNo);
			counsel.setTeacherId(idno);
			counsel.setCscode(cscode);
			counsel.setCourseName(courseName);
			counsel.setCourseClass(courseClass);
			dao.saveObject(counsel);
		}catch (Exception e){
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.getStackTrace()));
		}
		
		return err;
	}
	
	public ActionMessages saveStudCounselingBatchByForm(String idno, List<Student> students, 
			DynaActionForm aForm, String ctype){
		ActionMessages err = new ActionMessages();
		
		short schoolYear = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR));
		short schoolTerm = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM));
		String departClass = "";
		String studentNo  = "";
		String cscode = aForm.getString("cscode").trim();
		String courseName = aForm.getString("courseName").trim();
		String courseClass = aForm.getString("courseClass").trim();
		String cdate = aForm.getString("cdate").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();
		Student student = null;
		if(cdate.indexOf('/') >= 0){
			cdate.replaceAll("/", "-");
		}
		Date datec = Toolket.parseFullDate(cdate);
		
		for(Student stud:students){
			studentNo=stud.getStudentNo();
			departClass = stud.getDepartClass();
			try{
				StudCounseling counsel = new StudCounseling();
				counsel.setSchoolYear(schoolYear);
				counsel.setSchoolTerm(schoolTerm);
				counsel.setCdate(datec);
				counsel.setContent(content);
				counsel.setCtype(ctype);
				counsel.setDepartClass(departClass);
				counsel.setItemNo(Integer.parseInt(itemNo));
				counsel.setMdate(Calendar.getInstance().getTime());
				counsel.setStudentNo(studentNo);
				counsel.setTeacherId(idno);
				counsel.setCscode(cscode);
				counsel.setCourseName(courseName);
				counsel.setCourseClass(courseClass);
				dao.saveObject(counsel);
			}catch (Exception e){
				err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.getStackTrace()));
				break;
			}
		}
		return err;
	}
	
	public ActionMessages saveStudCounselingBatchLByForm(String idno, String stEcts, 
			DynaActionForm aForm){
		ActionMessages err = new ActionMessages();
		
		short schoolYear = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR));
		short schoolTerm = Short.parseShort(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM));
		String departClass = "";
		String cscode = aForm.getString("cscode").trim();
		String courseName = "";
		String courseClass = "";
		String cdate = aForm.getString("cdate").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();
		Student student = null;
		if(cdate.indexOf('/') >= 0){
			cdate.replaceAll("/", "-");
		}
		Date datec = Toolket.parseFullDate(cdate);
		
		String[] stCourse = stEcts.split("\\|");
		String[] sts = new String[stCourse.length];
		String[] clazzs = new String[stCourse.length];
		String[] cscodes = new String[stCourse.length];
		String[] ectcode = null;
		int cnt = 0;
		for(String ects:stCourse){
			ectcode = ects.split("_");
			sts[cnt] = ectcode[0];
			clazzs[cnt] = ectcode[1];
			cscodes[cnt] = ectcode[2];
			cnt++;
		}

		cnt = 0;
		for(String studentNo:sts){
			student = findStudentByStudentNo(studentNo);
			departClass = student.getDepartClass();
			try{
				StudCounseling counsel = new StudCounseling();
				counsel.setSchoolYear(schoolYear);
				counsel.setSchoolTerm(schoolTerm);
				counsel.setCdate(datec);
				counsel.setContent(content);
				counsel.setCtype("L");
				counsel.setDepartClass(departClass);
				counsel.setItemNo(Integer.parseInt(itemNo));
				counsel.setMdate(Calendar.getInstance().getTime());
				counsel.setStudentNo(studentNo);
				counsel.setTeacherId(idno);
				counsel.setCscode(cscodes[cnt]);
				counsel.setCourseName(dao.findNameOfCourse(cscodes[cnt]).getChiName());
				counsel.setCourseClass(clazzs[cnt]);
				dao.saveObject(counsel);
			}catch (Exception e){
				err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.getStackTrace()));
				break;
			}
			cnt++;
		}
		return err;
	}
	
	public ActionMessages saveStudCounselingModify(StudCounseling counsel, DynaActionForm aForm){
		ActionMessages err = new ActionMessages();
		String cdate = aForm.getString("cdate").trim();
		String itemNo = aForm.getString("itemNo").trim();
		String content = aForm.getString("content").trim();
		
		if(cdate.indexOf('/') >= 0){
			cdate.replaceAll("/", "-");
		}
		Date datec = Toolket.parseFullDate(cdate);
		
		try{
			counsel.setCdate(datec);
			counsel.setContent(content);
			counsel.setItemNo(Integer.parseInt(itemNo));
			counsel.setMdate(Calendar.getInstance().getTime());
			dao.saveObject(counsel);
		}catch (Exception e){
			err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", e.getStackTrace()));
		}
		
		return err;
	}
	
	public List<Map> findCounselingStudentInClass(String ctype, UserCredential credential){
		List<Map> retList = new ArrayList<Map>();
		String idno = credential.getMember().getIdno();
		ScoreManager sm = (ScoreManager) Global.context
				.getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String sql = "";
		ClassScoreSummary summary = null;
		StudCounseling counseling = null;
		List<ClassScoreSummary> csss = null;
		Set<StdScore> scores = null;
		String[] studentNos = new String[0];
		
		if(ctype.equals("T")){
			Clazz[] classes = credential.getClassInChargeAryT();
			if(classes.length > 0){
				for(int i=0; i<classes.length; i++){
					Map subjMap = new HashMap();	//存放該課程及學生輔導資料, subject:課程資料, students:學生輔導資料
					sql = "Select * From stmd Where depart_class='" + classes[i].getClassNo() + "'";
					List<Map> studs = jdbcDao.findAnyThing(sql);
					
					// 取得期中1/2或2/3學生資料
					summary = new ClassScoreSummary(syear, sterm, classes[i]
							.getClassNo(), Examine.MID);
					csss = sm.findClassScoreSummaryByLikeExpression(summary);
					for (ClassScoreSummary css : csss) {
						scores = css.getStdScoreSet();
						for (StdScore score : scores) {
							if (score.getStatus() == ScoreStatus.STATUS_2
									|| score.getStatus() == ScoreStatus.STATUS_3)
								studentNos = (String[]) ArrayUtils.add(
										studentNos, score.getStudentNo());
						}
					}
					
					counseling = new StudCounseling();
					counseling.setSchoolYear(Short.valueOf(syear));
					counseling.setSchoolTerm(Short.valueOf(sterm));
					counseling.setTeacherId(credential.getMember().getIdno()
							.trim());
					counseling.setCtype("U"); // U:learn(Tutor)
					counseling.setItemNo(29); // 預警補救教學
					
					if(!studs.isEmpty()){
						Map dtimeMap = new HashMap();	//存放課程資料
						dtimeMap.put("className", Toolket.getClassFullName(classes[i].getClassNo()));
						dtimeMap.put("courseName", "");
						dtimeMap.put("departClass", classes[i].getClassNo());
						dtimeMap.put("cscode", "");
						dtimeMap.put("Oid", 0);
						subjMap.put("subject", dtimeMap);
						
						int count = 0;
						String studentName = "";
						String studentNo = "";
						String departClass = "";
						String className = "";
						String subsql = "";
						List<Map> studList = new ArrayList<Map>();
						for(Map stud:studs){
							studentNo = (String)stud.get("student_no");
							studentName = (String)stud.get("student_name");
							departClass = (String)stud.get("depart_class");
							className = Toolket.getClassFullName(departClass);
							sql = "Select count(*) From StudCounseling ";
							sql += " Where schoolYear=" + syear + " And schoolTerm=" + sterm;
							sql += " And teacherId='" + idno + "'";
							sql += " And studentNo='" + (String)stud.get("student_no") + "'";
							subsql = " And ctype='T' And (cscode is null OR cscode='')";
							
							count = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql + subsql).get(0)).get("count(*)").toString());
							Map studMap = new HashMap();
							studMap.put("studentNo", studentNo);
							studMap.put("studentName", studentName);
							studMap.put("departClass", departClass);
							studMap.put("className", className);
							studMap.put("countT", count);
							
							subsql = " And ctype='U' And (cscode is null OR cscode='')";
							count = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql + subsql).get(0)).get("count(*)").toString());
							studMap.put("countU", count);
							
							studMap.put("fail", false);
							studMap.put("failCount", 0);
							if (ArrayUtils.contains(studentNos, studentNo)) {
								studMap.put("fail", true);
								counseling.setStudentNo(studentNo);
								studMap.put("failCount", findStudCounselingBy(
										counseling).size());
							}
							studList.add(studMap);
						}
						subjMap.put("students", studList);
						retList.add(subjMap);
					}
				}	//end of for classes
			}
		}else if(ctype.equals("L")){
			sql = "Select d.Oid as Oid, d.depart_class as departClass, d.cscode as cscode From Dtime d left outer join Dtime_teacher t ";
			sql += " On t.Dtime_oid=d.Oid Where (d.techid='" + idno + "' OR t.teach_id='" + idno + "')";
			sql += " And d.sterm='" + sterm + "' group by d.Oid order by d.depart_class";
			
			List<Map> dtimes = jdbcDao.findAnyThing(sql);	//老師授課課程
			
			if(!dtimes.isEmpty()){
				for(Map dtimep:dtimes){
					Map subjMap = new HashMap();	//存放該課程及學生輔導資料, subject:課程資料, students:學生輔導資料
					Map dtimeMap = new HashMap();	//存放課程資料
					dtimeMap.put("className", Toolket.getClassFullName((String)dtimep.get("departClass")));
					dtimeMap.put("courseName", dao.findNameOfCourse((String)dtimep.get("cscode")).getChiName());
					dtimeMap.put("departClass", (String)dtimep.get("departClass"));
					dtimeMap.put("cscode", (String)dtimep.get("cscode"));
					dtimeMap.put("Oid", (Integer)dtimep.get("Oid"));
					
					subjMap.put("subject", dtimeMap);
					
					sql = "Select student_no From Seld Where Dtime_oid=" + ((Integer)dtimep.get("Oid"));
					sql += " Order by student_no";
					
					List<Map> selds = jdbcDao.findAnyThing(sql);
					
					// 取得期中1/2或2/3學生資料
					summary = new ClassScoreSummary(syear, sterm,
							(String) dtimep.get("departClass"), Examine.MID);
					csss = sm.findClassScoreSummaryByLikeExpression(summary);
					for (ClassScoreSummary css : csss) {
						scores = css.getStdScoreSet();
						for (StdScore score : scores) {
							if (score.getStatus() == ScoreStatus.STATUS_2
									|| score.getStatus() == ScoreStatus.STATUS_3)
								studentNos = (String[]) ArrayUtils.add(
										studentNos, score.getStudentNo());
						}
					}
					
					counseling = new StudCounseling();
					counseling.setSchoolYear(Short.valueOf(syear));
					counseling.setSchoolTerm(Short.valueOf(sterm));
					counseling.setTeacherId(credential.getMember().getIdno()
							.trim());
					counseling.setCtype("L"); // L:learn(Teacher)
					counseling.setItemNo(29); // 預警補救教學
					
					List<Map> studList = new ArrayList<Map>();
					if(!selds.isEmpty()){
						int count = 0;
						Student student = null;
						Graduate graduate = null;
						String studentName = "";
						String studentNo = "";
						String departClass = "";
						String className = "";
						
						for(Map seld:selds){
							studentNo = ((String)seld.get("student_no")).toUpperCase();
							studentName = "";
							departClass = "";
							className = "";
							student = this.findStudentByStudentNo(studentNo);
							if(student != null){
								studentName = student.getStudentName();
								departClass = student.getDepartClass();
								className = Toolket.getClassFullName(departClass);
							}else{
								graduate = this.findGraduateByStudentNo(studentNo);
								if(graduate != null){
									studentName = graduate.getStudentName();
									departClass = graduate.getDepartClass();
									className = Toolket.getClassFullName(departClass);
								}
							}
							if(student != null || graduate != null){
								Map studMap = new HashMap();
								sql = "Select count(*) From StudCounseling ";
								sql += " Where studentNo='" + studentNo + "'";
								sql += " And schoolYear=" + syear + " And schoolTerm=" + sterm;
								sql += " And teacherId='" + idno + "'";
								sql += " And ctype='L' And cscode='" + (String)dtimep.get("cscode") + "'";
								
								count = Integer.parseInt(((Map)jdbcDao.findAnyThing(sql).get(0)).get("count(*)").toString());
								studMap.put("studentNo", studentNo);
								studMap.put("studentName", studentName);
								studMap.put("departClass", departClass);
								studMap.put("className", className);
								studMap.put("count", count);
								
								studMap.put("fail", false);
								studMap.put("failCount", 0);
								if (ArrayUtils.contains(studentNos, studentNo)) {
									studMap.put("fail", true);
									counseling.setStudentNo(studentNo);
									studMap.put("failCount",
											findStudCounselingBy(counseling)
													.size());
								}
								studList.add(studMap);
							}
						}
					}
					subjMap.put("students", studList);
					
					retList.add(subjMap);
				}
			}
			//非授課班之被輔導學生
			sql = "Select studentNo, count(*) as count From StudCounseling ";
			sql += " Where schoolYear=" + syear + " And schoolTerm=" + sterm;
			sql += " And teacherId='" + idno + "'";
			sql += " And ctype='L' And (cscode is null OR cscode='')";
			sql += " Group by studentNo order by studentNo";
			
			List<Map> others = jdbcDao.findAnyThing(sql);
			if(!others.isEmpty()){
				Map subjMap = new HashMap();	//存放該課程及學生輔導資料, subject:課程資料, students:學生輔導資料
				Map dtimeMap = new HashMap();	//存放課程資料
				dtimeMap.put("className", "其他");
				dtimeMap.put("courseName", "");
				dtimeMap.put("departClass", "xxxxxx");
				dtimeMap.put("cscode", "");
				dtimeMap.put("Oid", 0);
				subjMap.put("subject", dtimeMap);
				
				List<Map> studList = new ArrayList<Map>();
				Student student = null;
				Graduate graduate = null;
				String studentName = "";
				String studentNo = "";
				String departClass = "";
				String className = "";
				
				for(Map other:others){
					studentNo = (String)other.get("studentNo");
					studentName = "";
					departClass = "";
					className = "";
					student = this.findStudentByStudentNo(studentNo);
					if(student != null){
						studentName = student.getStudentName();
						departClass = student.getDepartClass();
						className = Toolket.getClassFullName(departClass);
					}else{
						graduate = this.findGraduateByStudentNo(studentNo);
						if(graduate != null){
							studentName = graduate.getStudentName();
							departClass = graduate.getDepartClass();
							className = Toolket.getClassFullName(departClass);
						}
					}
					if(student != null || graduate != null){
						Map studMap = new HashMap();
						studMap.put("studentNo", studentNo);
						studMap.put("studentName", studentName);
						studMap.put("departClass", departClass);
						studMap.put("className", className);
						studMap.put("count", Integer.parseInt(other.get("count").toString()));
						studList.add(studMap);
					}
				}
				subjMap.put("students", studList);
				retList.add(subjMap);
			}
		}
		return retList;
	}
	
	public List<Map> findSeldInPeriod(String studentNo, Calendar sDate, Calendar eDate){
		//used for begin period compare
		class periodComp implements Comparator {
			public int compare(Object ob1, Object ob2){
				Object[] obj1 = (Object[])ob1;
				Object[] obj2 = (Object[])ob2;
				int begin1 = Integer.parseInt(obj1[4].toString());
				int begin2 = Integer.parseInt(obj2[4].toString());
				if( begin1 > begin2)	return 1;
				else if(begin1 == begin2) return 0;
				else return -1;
			}

			public boolean equals(Object obj){
				return super.equals(obj);
			}
		}
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(sDate.getTimeInMillis());
		Calendar endDate = Calendar.getInstance();
		endDate.setTimeInMillis(eDate.getTimeInMillis());
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		
		String clazz = this.findStudentByStudentNo(studentNo).getDepartClass();
		String term = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		List<Map> retList = new ArrayList<Map>();
		List seldlist = new ArrayList();
		String hql = "SELECT d.oid, d.cscode, s.studentNo, dc.week, dc.begin, dc.end" +
			" FROM Seld s, Dtime d, DtimeClass dc WHERE s.dtimeOid = d.oid "
			+ "AND  dc.dtimeOid = d.oid AND d.sterm = '" + term + "' AND s.studentNo = '"
			+ studentNo + "' AND dc.week=? ORDER BY dc.begin";
		
		String hql2 = "SELECT d.oid, d.cscode, '" + studentNo + "' As studentNo, dc.week, dc.begin, dc.end " +
				"From Dtime d, DtimeClass dc Where d.oid=dc.dtimeOid ";
		hql2 += " And d.departClass='" + clazz + "' And (d.cscode='50000' OR d.cscode='T0001')";
		hql2 += " And sterm='" + term  + "' And dc.week=?";
		List tutorDtime = new ArrayList();
		
		int week = startDate.get(Calendar.DAY_OF_WEEK);
		while(endDate.compareTo(startDate) >= 0){
			week = startDate.get(Calendar.DAY_OF_WEEK);
			if(week==1){
				week = 7;
			}else{
				week--;
			}
			seldlist = dao.submitQuery(hql, new Object[]{week});
			tutorDtime = dao.submitQuery(hql2, new Object[]{week});
			if(!seldlist.isEmpty() || !tutorDtime.isEmpty()){
				if(!tutorDtime.isEmpty()){
					seldlist.addAll(tutorDtime);
					Collections.sort(seldlist,new periodComp());
				}
				for(Object info:seldlist){
					Object[] infoA = (Object[])info;
					Map sMap = new HashMap();
					sMap.put("oid", infoA[0]);
					sMap.put("cscode", infoA[1]);
					sMap.put("studentNo", infoA[2].toString());
					sMap.put("week", infoA[3]);
					sMap.put("begin", infoA[4]);
					sMap.put("end", infoA[5]);
					sMap.put("date", Toolket.FullDate2Str(startDate.getTime()));
					retList.add(sMap);
				}
			}
			startDate.add(Calendar.DATE, 1);
		}
		return retList;

	}
	
	public List<StudDocApply> findStudDocApplyByNo(String studentNo){
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		String hql = "From StudDocApply Where studentNo='" + studentNo + "'";
		hql += " order by startDate DESC";
		return dao.submitQuery(hql);
	}
	
	public StudDocApply findStudDocApplyByOid(int docOid){
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		StudDocApply doc = null;
		String hql = "From StudDocApply Where oid=" + docOid ;
		docs = dao.submitQuery(hql);
		if(!docs.isEmpty()){
			doc = docs.get(0);
		}
		return doc;
	}
	
	public List<StudDocUpload> getAbsenceDocAttach(int oid){
		//Session session = dao.getCISBLOBSession(new String[]{});
		String hql = "From StudDocUpload Where docOid=" + oid;
		//Transaction tx  = session.beginTransaction();
		//List<StudDocUpload> files = session.createQuery(hql).list();
		List<StudDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//dao.closeCISBLOBSession(session);
		return files;
	}

	public StudDocUpload getAbsenceDocUpload(int oid){
		StudDocUpload upload = null;
		//Session session = dao.getCISBLOBSession(new String[]{});
		String hql = "From StudDocUpload Where oid=" + oid;
		//Transaction tx  = session.beginTransaction();
		List<StudDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//dao.closeCISBLOBSession(session);
		if(!files.isEmpty()){
			upload =  files.get(0);
		}
		return upload;
	}

	public void delAbsenceDocAttach(StudDocUpload upload){
		try {
			//dao.removeStudDocUpload(upload);
			blobdao.removeObject(upload);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveAbsenceDocAttach(StudDocUpload upload) throws Exception {
		//Session session = dao.getCISBLOBSession(new String[]{});
		try{
			//Transaction tx  = session.beginTransaction();
			//session.saveOrUpdate(upload);
			blobdao.saveObject(upload);
			//tx.commit();
		}catch (HibernateException he){
			//dao.closeCISBLOBSession(session);
			he.printStackTrace();
			throw new Exception("saveOrupdate attach file of student Absence doc error!");
		}
		//dao.closeCISBLOBSession(session);
	}
	

	public ActionMessages saveStudAbsenceApplyByForm(DynaActionForm form, int days) throws Exception{
		ActionMessages msg = new ActionMessages();
		String ruleNo = "";
		String opmode = form.getString("opmode");
		String reason = form.getString("reason");
		String askLeaveType = form.getString("askLeaveType");
		String sdate = form.getString("startDate");
		String edate = form.getString("endDate");
		String fileOids = form.getString("fileOids");
		//String[] adate = sdate.split("-");
		String memo = form.getString("memo");
		String[] studentNo = form.getStrings("studentNo");
		String[] tdate = form.getStrings("tdate");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");

		Student student = this.findStudentByStudentNo(studentNo[0]);
		String depart = Toolket.getDepartNo(student.getDepartClass()).substring(0, 2);
		if(student == null){
			msg.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("messageN1", "抱歉!系統查不到您的學生資料,請稍後重試!"));
			return msg;
		}
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(Toolket.parseDate(sdate));
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(Toolket.parseDate(edate));
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 0);

		if(opmode.equals("add")){
			StudDocApply doc = new StudDocApply();
			doc.setStudentName(student.getStudentName());
			doc.setStudentNo(student.getStudentNo());
			doc.setStartDate(startCal.getTime());
			doc.setEndDate(endCal.getTime());
			doc.setAskLeaveType(askLeaveType);
			doc.setCreateDate(Calendar.getInstance().getTime());
			doc.setDepartClass(student.getDepartClass());
			doc.setReason(reason);
			doc.setMemo(memo);
			doc.setStatus(IConstants.STUDDocCodeNotSend);
			if(days==1){
				ruleNo = "SD111";
			/* 系教官審查廢除 2010/06/15 學務通知
			}else if(days==2){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					ruleNo = "SD112";
				}else{
					ruleNo = "SD115";
				}
			*/
			}else if(days==2 || days==3){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					ruleNo = "SD113";
				}else{
					ruleNo = "SD115";
				}
			}else if(days>3){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					ruleNo = "SD119";
				}else{
					ruleNo = "SD118";
				}
			}
			doc.setRuleNo(ruleNo);

			doc.setTotalDay((short)days);
			dao.saveObject(doc);
			dao.reload(doc);
			int docOid = doc.getOid();

			//save detail absence periods
			StudDocDetail docd = null;
			for(int i=0; i<abs1.length; i++){
				Date ndate = Toolket.parseFullDate(tdate[i]);
				if(abs1[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)1);
					dao.saveObject(docd);
				}
				if(abs2[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)2);
					dao.saveObject(docd);
				}
				if(abs3[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)3);
					dao.saveObject(docd);
				}
				if(abs4[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)4);
					dao.saveObject(docd);
				}
				if(abs5[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)5);
					dao.saveObject(docd);
				}
				if(abs6[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)6);
					dao.saveObject(docd);
				}
				if(abs7[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)7);
					dao.saveObject(docd);
				}
				if(abs8[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)8);
					dao.saveObject(docd);
				}
				if(abs9[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)9);
					dao.saveObject(docd);
				}
				if(abs10[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)10);
					dao.saveObject(docd);
				}
				if(abs11[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)11);
					dao.saveObject(docd);
				}
				if(abs12[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)12);
					dao.saveObject(docd);
				}
				if(abs13[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)13);
					dao.saveObject(docd);
				}
				if(abs14[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(startCal.getTime());
					docd.setPeriod((short)14);
					dao.saveObject(docd);
				}
				if(abs15[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)15);
					dao.saveObject(docd);
				}
			}
			
			//update fileuploads
			if(!fileOids.trim().equals("")){
				int foid = 0;
				boolean isNew = false;
				//Session session = dao.getCISBLOBSession(new String[]{});
				
				StudDocUpload upload = null;
				String[] foids = fileOids.split("\\|");
				for(int k=0; k<foids.length; k++){
					foid = Integer.parseInt(foids[k]);
					upload = this.getAbsenceDocUpload(foid);
					if(upload != null){
						upload.setDocOid(docOid);
						upload.setAttNo((short)k);
						this.saveAbsenceDocAttach(upload);
					}
				}
			}
			
			//update doc  examine status
			StudDocExamine doce = new StudDocExamine();
			doce.setDocOid(docOid);
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(studentNo[0]);
			doce.setExamStatus(IConstants.STUDDocCodeNotSend);
			doce.setRuleNo("");		//新增假單時 不賦予 Rule number
			doce.setDescription("新增");
			dao.saveObject(doce);

		}else if(opmode.equals("modify")){
			int docOid = Integer.parseInt(form.getString("oid"));
			StudDocApply doc = this.findStudDocApplyByOid(docOid);
			doc.setStartDate(startCal.getTime());
			doc.setEndDate(endCal.getTime());
			doc.setAskLeaveType(askLeaveType);
			doc.setCreateDate(Calendar.getInstance().getTime());
			doc.setReason(reason);
			doc.setStatus(IConstants.STUDDocCodeNotSend);
			doc.setMemo(memo);
			if(days==1){
				doc.setRuleNo("SD111");
			/* 系教官審查廢除 2010/06/15 學務通知
			}else if(days==2){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					ruleNo = "SD112";
				}else{
					ruleNo = "SD115";
				}
			*/
			}else if(days==2 || days==3){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					doc.setRuleNo("SD113");
				}else{
					doc.setRuleNo("SD115");
				}
			}else if(days>3){
				if(depart.equals("11") || depart.equals("21") || depart.equals("22") 
						|| depart.equals("23")){
					doc.setRuleNo("SD119");
				}else{
					doc.setRuleNo("SD118");
				}
			}
			doc.setTotalDay((short)days);
			dao.saveObject(doc);
			dao.reload(doc);
			
			//update detail absence periods
			String sql = "Delete From STUD_DocDetail Where docOid=" + docOid;
			jdbcDao.executesql(sql);
			StudDocDetail docd = null;
			for(int i=0; i<abs1.length; i++){
				Date ndate = Toolket.parseFullDate(tdate[i]);
				if(abs1[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)1);
					dao.saveObject(docd);
				}
				if(abs2[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)2);
					dao.saveObject(docd);
				}
				if(abs3[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)3);
					dao.saveObject(docd);
				}
				if(abs4[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)4);
					dao.saveObject(docd);
				}
				if(abs5[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)5);
					dao.saveObject(docd);
				}
				if(abs6[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)6);
					dao.saveObject(docd);
				}
				if(abs7[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)7);
					dao.saveObject(docd);
				}
				if(abs8[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)8);
					dao.saveObject(docd);
				}
				if(abs9[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)9);
					dao.saveObject(docd);
				}
				if(abs10[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)10);
					dao.saveObject(docd);
				}
				if(abs11[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)11);
					dao.saveObject(docd);
				}
				if(abs12[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)12);
					dao.saveObject(docd);
				}
				if(abs13[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)13);
					dao.saveObject(docd);
				}
				if(abs14[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)14);
					dao.saveObject(docd);
				}
				if(abs15[i].equals("1")) {
					docd = new StudDocDetail();
					docd.setDocOid(docOid);
					docd.setAdate(ndate);
					docd.setPeriod((short)15);
					dao.saveObject(docd);
				}
			}
			
			//update fileuploads(新增刪除檔案的工作在AjaxGlobal中已經做完了!)
			if(!fileOids.trim().equals("")){
				int foid = 0;
				String[] foids = fileOids.split("\\|");
				StudDocUpload upload = null;
				for(int k=0; k<foids.length; k++){
					foid = Integer.parseInt(foids[k]);
					upload = this.getAbsenceDocUpload(foid);
					if(upload != null){
						upload.setDocOid(docOid);
						upload.setAttNo((short)k);
						this.saveAbsenceDocAttach(upload);
					}
				}
			}
			
			//update doc  examine status
			StudDocExamine doce = new StudDocExamine();
			doce.setDocOid(docOid);
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(studentNo[0]);
			doce.setExamStatus(IConstants.STUDDocCodeNotSend);
			doce.setRuleNo("");	//假單修改後不賦予 Rule number
			doce.setDescription("修改");
			dao.saveObject(doce);
		}
		return msg;
	}
	
	public List<StudDocApply> delStudDocApply(List<StudDocApply> selDocs){
		List<StudDocApply> unDels = new ArrayList<StudDocApply>();
		String status = "";
		for(StudDocApply doc:selDocs){
			status = doc.getStatus();
			if(status.equalsIgnoreCase("A") || status.equalsIgnoreCase("1")){
				unDels.add(doc);
			}else{
				doc.setStatus("D");
				dao.saveObject(doc);
			}
		}
		return unDels;
	}
	
	public StudDocExamine getStudDocLastExamine(int docOid){
		StudDocExamine examine = null;
		String hql = "From StudDocExamine Where docOid=" + docOid;
		hql += " Order by examDate DESC";
		List<StudDocExamine> examines = dao.submitQuery(hql);
		if(!examines.isEmpty()){
			examine = examines.get(0);
		}
		return examine;
	}
	
	public List<StudDocExamine> getStudDocExamines(int docOid){
		String hql = "From StudDocExamine Where docOid=" + docOid;
		hql += " Order by examDate DESC";
		List<StudDocExamine> examines = dao.submitQuery(hql);
		return examines;
	}
	
	public String getExamineStatusName(String statusCode){
		String statusName = "";
		for(int i = 0; i < IConstants.STUDDocProcess.length; i++){
			if(statusCode.equalsIgnoreCase(IConstants.STUDDocProcess[i][0])){
				statusName = IConstants.STUDDocProcess[i][1];
				break;
			}
		}
		return statusName;
	}

	public List<StudDocDetail> getStudDocDetailByDate(String studentNo,
			String dDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(Toolket.parseFullDate(dDate));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date tdate = cal.getTime();
		List<StudDocDetail> docds = new ArrayList<StudDocDetail>();
		String hql = "Select sd From StudDocDetail sd, StudDocApply d Where sd.docOid=d.oid And d.studentNo=? And sd.adate=?";
		docds =  dao.submitQuery(hql, new Object[]{studentNo, tdate});
		return docds;
	}
	
	public List<StudDocDetail> getStudDocDetailByDocOid(int docOid){
		List<StudDocDetail> docds = new ArrayList<StudDocDetail>();
		String hql = "From StudDocDetail Where docOid=" + docOid + " Order by adate, period";
		docds =  dao.submitQuery(hql);
		return docds;
	}
	
	public Map checkAbsenceApplyDuplicate(String studentNo, List<StudDocDetail> docds, int oid){
		Map result = new HashMap();
		StringBuffer msg = new StringBuffer();
		boolean isDupl = false;
		List<StudDocDetail> docdList = null;
		for(StudDocDetail docd:docds){
			docdList = getAbsenceDocDetailBy(studentNo, docd.getAdate(), docd.getPeriod(), false);
			if(docdList.isEmpty()){
				continue;
			}else{
				for(StudDocDetail docdn:docdList){
					if(oid>0){
						if(docdn.getDocOid()==oid){
							continue;
						}else{
							isDupl = true;
							msg.append(Toolket.Date2Str(docd.getAdate()));
							msg.append("第").append(docd.getPeriod()).append("節, ");
							break;
						}
					}else if(oid==0){
						isDupl = true;
						msg.append(Toolket.Date2Str(docd.getAdate()));
						msg.append("第").append(docd.getPeriod()).append("節, ");
						break;
					}
				}
				if(isDupl) break;
			}
		}
		result.put("msg", msg.toString());
		result.put("isDupl", isDupl);
		
		return result;
	}
	
	public List<StudDocApply> getAbsenceApplyByPeriod(String studentNo, Date startDate, Date endDate){
		Calendar scal = Calendar.getInstance();
		scal.setTime(startDate);
		scal.set(Calendar.HOUR_OF_DAY, 0);
		scal.set(Calendar.MINUTE, 0);
		scal.set(Calendar.SECOND, 0);
		scal.set(Calendar.MILLISECOND, 0);
		Calendar ecal = Calendar.getInstance();
		ecal.setTime(endDate);
		ecal.set(Calendar.HOUR_OF_DAY, 0);
		ecal.set(Calendar.MINUTE, 0);
		ecal.set(Calendar.SECOND, 0);
		ecal.set(Calendar.MILLISECOND, 0);
		
		String hql = "From StudDocApply Where studentNo='" + studentNo + "'";
		hql += " And ( ? between startDate and endDate Or ? between startDate and endDate)";
		hql += " order by startDate";
		List<StudDocApply> docs = dao.submitQuery(hql, new Object[]{scal.getTime(), ecal.getTime()});
		return docs;
	}
	
	public List<StudDocDetail> getAbsenceDocDetailBy(String studentNo, Date adate, short period, boolean incDel){
		String hql = "Select d From StudDocApply a, StudDocDetail d Where d.docOid=a.oid";
		hql += " And a.studentNo='" + studentNo + "' And d.adate=?";
		if(!incDel){
			//不包含(排除)已刪除的假單
			hql += " And a.status<>'D'";
		}
		if(period>0){
			hql += " And d.period=?";
		}
		List<StudDocDetail> docds = dao.submitQuery(hql, new Object[]{adate, period});
		return docds;
	}
	
	public ActionMessages setStudDoc4Examine(StudDocApply doc){
		ActionMessages msg = new ActionMessages();
		try{
			StudDocExamine doce = new StudDocExamine();
			doce.setDocOid(doc.getOid());
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(doc.getStudentNo());
			doce.setExamStatus(IConstants.STUDDocCodeSendOut);
			doce.setRuleNo("");
			doce.setDescription("送出");
			dao.saveObject(doce);
			doc.setStatus(IConstants.STUDDocCodeSendOut);
			dao.saveObject(doc);
		}catch(Exception e){
			e.printStackTrace();
			msg.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "學生假單送審錯誤:Oid=" + doc.getOid()));
		}
		return msg;
	}

	public ActionMessages resetStudDoc4Examine(StudDocApply doc){
		ActionMessages msg = new ActionMessages();
		try{
			StudDocExamine doce = new StudDocExamine();
			doce.setDocOid(doc.getOid());
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(doc.getStudentNo());
			doce.setExamStatus(IConstants.STUDDocCodeNotSend);
			doce.setRuleNo("");
			doce.setDescription("取消送核");
			dao.saveObject(doce);
			doc.setStatus(IConstants.STUDDocCodeNotSend);
			dao.saveObject(doc);
		}catch(Exception e){
			e.printStackTrace();
			msg.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "學生假單取消送核錯誤:Oid=" + doc.getOid()));
		}
		return msg;
	}

	public List<StudDocApply> getAbsenceApply4ExamByClazz(String classNo, String examMode){
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		
		Calendar startDate = Toolket.getDateOfWeek(classNo.substring(0, 2), 1)[0];
		startDate.add(Calendar.DATE, -7);
		String sdate = startDate.get(Calendar.YEAR) + "-" + (startDate.get(Calendar.MONTH)+1) + 
						"-" + startDate.get(Calendar.DATE);
		String hql = "From StudDocApply Where departClass='" + classNo + "'";
		hql += " And status='A' And startDate>='" + sdate + "' ";
		hql += " order by startDate, departClass, studentNo";
		docs = dao.submitQuery(hql);
		
		List<StudDocExamine> doces = new ArrayList<StudDocExamine>();
		StudDocApply doc = null;
		for(ListIterator<StudDocApply> docIter=docs.listIterator(); docIter.hasNext();){
			doc = docIter.next();
			if(!checkIsInExamStep(doc, examMode)){
				docIter.remove();
			}else if(doc.getAskLeaveType().equals("6")){
				//公假單不在一般審查範圍內
				docIter.remove();
			}
		}
		return docs;
	}
	
	public List<StudDocExamine> getStudDocExamNearest(int docOid){
		List<StudDocExamine> rets = new ArrayList<StudDocExamine>();
		List<StudDocExamine> doces = this.getStudDocExamines(docOid);
		for(StudDocExamine doce:doces){
			if(doce.getExamStatus().equalsIgnoreCase("A")){
				break;
			}else{
				rets.add(doce);
			}
		}
		return rets;
	}
	
	public ExamineRule getExamRuleByOid(int myOid){
		ExamineRule exam = null;
		String hql = "From ExamineRule Where oid=" + myOid;
		List<ExamineRule> exams = dao.submitQuery(hql);
		if(!exams.isEmpty()){
			exam = exams.get(0);
		}
		return exam;
	}

	public ExamineRule getExamRuleByRuleNo(String ruleNo){
		ExamineRule exam = null;
		String hql = "From ExamineRule Where ruleNo='" + ruleNo + "'";
		List<ExamineRule> exams = dao.submitQuery(hql);
		if(!exams.isEmpty()){
			exam = exams.get(0);
		}
		return exam;
	}

	public List<ExamineRule> getExamRuleSequence(String ruleNo){
		List<ExamineRule> rules = new ArrayList<ExamineRule>();
		ExamineRule exam = getExamRuleByRuleNo(ruleNo);
		int pOid = 0;
		if(exam != null){
			rules.add(exam);
			while(exam.getPoid() != null){
				exam = getExamRuleByOid(exam.getPoid());
				rules.add(exam);
			}
		}
		return rules;
	}
	
	public boolean checkIsInExamStep(StudDocApply doc, String ruleNo){
		boolean inStep = false;
		List<ExamineRule> rules = getExamRuleSequence(ruleNo);
		List<StudDocExamine> exams = this.getStudDocExamNearest(doc.getOid());
		if(!rules.isEmpty()){
			int rsize = rules.size();
			//ExamineRule thisRule = rules.get(0);
			List<ExamineRule> restRules = new ArrayList<ExamineRule>();
			if(rsize == 1){
				//我自己就是最終的審核規則
				inStep = true;
				for(StudDocExamine exam:exams){
					if(exam.getRuleNo().equals(ruleNo)){
						//已經做過審查了
						inStep = false;
						break;
					}
				}
			}else{
				restRules = rules.subList(1, rules.size());
				// ExamineRule parentRule = rules.get(1);
				inStep = true;
				boolean parentDone = false;
				String status = "";
				for(ExamineRule rule:restRules){
					parentDone = false;
					for(StudDocExamine exam:exams){
						//已經做過審查了
						if(exam.getRuleNo().equals(rule.getRuleNo())){
							status = exam.getExamStatus();
							if(status.equals(IConstants.STUDDocCodeDrillmasterOK) ||
									status.equals(IConstants.STUDDocCodeTutorOK) || 
									status.equals(IConstants.STUDDocCodeStudaffairOK)){
								parentDone = true;
								break;
							}else{	//前一關的審查未通過
								parentDone = false;
								break;
							}
						}
					}
					if(!parentDone){
						inStep = false;
						break;
					}
				}
				
				for(StudDocExamine exam:exams){
					if(exam.getRuleNo().equals(ruleNo)){
						//已經做過審查了
						inStep = false;
						break;
					}
				}
				
			}
			
		}
		return inStep;
	}
	
	public List<StudDocApply> getAbsenceApplyExamedByRule(String ruleNo, String idno){
		Calendar start = getFirtStartWeek();
		start.add(Calendar.DATE, -7);
		String sdate = start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + 
					start.get(Calendar.DATE);
		
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		String hql = "Select a From StudDocApply a, StudDocExamine d Where d.docOid=a.oid ";
		hql += " And d.ruleNo='" + ruleNo + "' And a.startDate>='" + sdate + "' ";
		if(!idno.equals("")){
			hql += " And d.examId='" + idno + "'";
		}
		hql += " Order by d.examDate DESC ";
		hql += " Group by a.oid";
		
		/*
		List<Object[]> tmpList = dao.submitQuery(hql);
		for(Object[] objs:tmpList){
			StudDocApply doc = (StudDocApply)objs[0];
			StudDocExamine doce = (StudDocExamine)objs[1];
			doce.setExamerName(Toolket.getEmplName(doce.getExamId()));
			doc.setExam(doce);
			docs.add(doc);
		}
		*/
		List<StudDocApply> docList = dao.submitQuery(hql);
		hql = "From StudDocExamine Where docOid=? And examId='" + idno + "' Order by examDate DESC";
		List<StudDocExamine> exams = new ArrayList<StudDocExamine>();
		StudDocExamine exam = new StudDocExamine();
		for(StudDocApply doc:docList){
			exams = dao.submitQuery(hql, new Object[]{doc.getOid()});
			exam = exams.get(0);
			exam.setStatusName(getExamineStatusName(exam.getExamStatus()));
			doc.setExam(exam);
			docs.add(doc);
		}
		return docs;
	}
	
	public ActionMessages setStudDocExamStatus(StudDocApply doc, String ruleNo, String status, String description, String idno){
		//doc, examCode, status, description, user.getMember().getIdno()
		ActionMessages msgs = new ActionMessages();
		String endRule = doc.getRuleNo();
		
		String msg  = doc.getStudentName() + ":" + Toolket.Date2Str(doc.getStartDate()) + "~";
		msg += Toolket.Date2Str(doc.getEndDate()) + "的假單(oid=" + doc.getOid() + "),";
		dao.reload(doc);
		if(!doc.getStatus().equals(IConstants.STUDDocCodeSendOut)){
			if(doc.getStatus().equals(IConstants.STUDDocCodeDeleted)){
				msg += "已刪除!";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeNotSend)){
				msg += "尚未送核(可能學生正在修改中)!";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeProcessOK)){
				msg += "已經完成請假審核流程,無須再審核!(系統有誤!請通知電算中心)";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeProcessReject)){
				msg += "該假單審核未通過,學生未修改重新送核前無須再審核!(系統有誤!請通知電算中心)";
			}
			msgs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "學生假單審核錯誤:Oid=" + doc.getOid() + ", " + msg));

		}else{
			if(checkIsInExamStep(doc, ruleNo)){	//可否執行此審核步驟
				String examStatus = "";
				if(status.equals("0")){
					if(ruleNo.equals("SD111")){
						examStatus = IConstants.STUDDocCodeTutorReject;
					}else if(ruleNo.equals("SD112")){
						examStatus = IConstants.STUDDocCodeDrillmasterReject;
					}else if(ruleNo.equals("SD113") || ruleNo.equals("SD115")){
						examStatus = IConstants.STUDDocCodeStudaffairReject;
					}else if(ruleNo.equals("SD118") || ruleNo.equals("SD119")){
						examStatus = IConstants.STUDDocCodeSAFChiefReject;
					}
				}else if(status.equals("1")){
					if(ruleNo.equals("SD111")){
						examStatus = IConstants.STUDDocCodeTutorOK;
					}else if(ruleNo.equals("SD112")){
						examStatus = IConstants.STUDDocCodeDrillmasterOK;
					}else if(ruleNo.equals("SD113") || ruleNo.equals("SD115")){
						examStatus = IConstants.STUDDocCodeStudaffairOK;
					}else if(ruleNo.equals("SD118") || ruleNo.equals("SD119")){
						examStatus = IConstants.STUDDocCodeSAFChiefOK;
					}
				}

				if(ruleNo.equalsIgnoreCase(endRule)){
					if(status.equals("0")){
		 				doc.setStatus(IConstants.STUDDocCodeProcessReject);
		 				dao.saveObject(doc);
		 				
		 				StudDocExamine doce = new StudDocExamine();
		 				doce.setDocOid(doc.getOid());
		 				doce.setExamDate(Calendar.getInstance().getTime());
		 				doce.setExamId(idno);
		 				doce.setExamStatus(examStatus);
		 				doce.setRuleNo(ruleNo);
		 				doce.setDescription(description);
		 				dao.saveObject(doce);						
					}else if(status.equals("1")){
						//最後的查核通過
						List<StudDocDetail> docds = getStudDocDetailByDocOid(doc.getOid());
						String studentNo = doc.getStudentNo();
						String departClass = doc.getDepartClass();
						String daynite = Toolket.chkDayNite(departClass);
						short altype = Short.parseShort(doc.getAskLeaveType());
						Calendar preDate = Calendar.getInstance();
						preDate.setTime(docds.get(0).getAdate());
						Calendar now = Calendar.getInstance();
						Dilg dilg = this.findDilgByStdDate(studentNo, Toolket.FullDate2Str(docds.get(0).getAdate()));
 						if(dilg == null){
 							dilg = new Dilg();
 							dilg.setDepartClass(departClass);
 							dilg.setStudentNo(studentNo);
 							dilg.setWeekDay((float)this.WhatIsTheWeek(docds.get(0).getAdate()));
 							dilg.setDdate(docds.get(0).getAdate());
 							dilg.setDaynite(daynite);
 						}
						DilgOne dilgone = null;
						
						String hql = "From DilgOne Where studentNo='" + studentNo + "' And ddate=? And period=?";
						Object[] parameters = new Object[2];
						
						//登錄請假紀錄
		 				for(StudDocDetail docd:docds){
		 					now.setTime(docd.getAdate());
		 					if(now.compareTo(preDate)>0){
								dao.saveObject(dilg);
								preDate.setTime(docd.getAdate());
		 						dilg = this.findDilgByStdDate(studentNo, Toolket.FullDate2Str(docd.getAdate()));
		 						if(dilg == null){
		 							dilg = new Dilg();
		 							dilg.setDepartClass(departClass);
		 							dilg.setStudentNo(studentNo);
		 							dilg.setWeekDay((float)this.WhatIsTheWeek(docd.getAdate()));
		 							dilg.setDdate(docd.getAdate());
		 							dilg.setDaynite(daynite);
		 						}
	 							dilg.setAbs(docd.getPeriod(), altype);
		 					}else{
		 						if(dilg.getAbs(docd.getPeriod()) != null){
		 							if(dilg.getAbs(docd.getPeriod()) != 6){	//公假不可被覆寫
		 								dilg.setAbs(docd.getPeriod(), altype);
		 							}
		 						}else{
		 							dilg.setAbs(docd.getPeriod(), altype);
		 						}
		 						
		 						/* 2010.06.10 暫時沒有功能可以直接新增DilgOne,因為 noExam要計算扣考,很多程式尚未支援
		 						 * 
		 						List<DilgOne> dilgOnes = dao.submitQuery(hql,  new Object[]{docd.getAdate(), docd.getPeriod()});
		 						if(!dilgOnes.isEmpty()){
		 							dilgone = dilgOnes.get(0);
		 							if(dilgone.getAbs() != 6){	//公假不可被覆寫
		 	 							dilgone.setAbs(altype);
		 							}
		 						}else{
		 							dilgone = new DilgOne();
		 							dilgone.setAbs(altype);
		 							dilgone.setDdate(docd.getAdate());
		 							dilgone.setDepartClass(departClass);
		 							dilgone.setPeriod(docd.getPeriod());
		 							dilgone.setStudentNo(studentNo);
		 							dilgone.setWeekDay(this.WhatIsTheWeek(docd.getAdate()));
		 							dilgone.setDaynite(Toolket.chkDayNite(departClass));
		 						}
								dao.saveObject(dilgone);
								*/
		 					}
						}
		 				//save last one record
						dao.saveObject(dilg);

		 				//更動學生操性成績
						dao.getHibernateSession().flush();
						//2011-05-13 暫停使用因為審核過程會很慢
						//double dilgScore = calDilgScoreByStudent(studentNo, "0");
						//msgs.add(modifyJustDilgScore(studentNo, dilgScore));
						
		 				//登陸審核完成資料
		 				doc.setStatus(IConstants.STUDDocCodeProcessOK);
		 				dao.saveObject(doc);
		 				
		 				StudDocExamine doce = new StudDocExamine();
		 				doce.setDocOid(doc.getOid());
		 				doce.setExamDate(Calendar.getInstance().getTime());
		 				doce.setExamId(idno);
		 				doce.setExamStatus(examStatus);
		 				doce.setRuleNo(ruleNo);
		 				doce.setDescription(description);
		 				dao.saveObject(doce);
		 				
					}
				}else{
					if(status.equals("0")){
		 				doc.setStatus(IConstants.STUDDocCodeProcessReject);
		 				dao.saveObject(doc);
					}
	 				//登錄審核資料
	 				StudDocExamine doce = new StudDocExamine();
	 				doce.setDocOid(doc.getOid());
	 				doce.setExamDate(Calendar.getInstance().getTime());
	 				doce.setExamId(idno);
	 				doce.setExamStatus(examStatus);
	 				doce.setRuleNo(ruleNo);
	 				doce.setDescription(description);
	 				dao.saveObject(doce);
	 				
				}
			}
		}
		return msgs;
	}
	
	public List<StudPublicLeave> findStudPublicLeavesByIdno(String idno){
		Calendar start = getFirtStartWeek();
		start.add(Calendar.DATE, -7);
		String sdate = start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + 
					start.get(Calendar.DATE);
		
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		String hql = "From StudPublicLeave Where applyId='" + idno + "'";
		hql += " And startDate>='" + sdate + "' ";
		hql += " order by startDate DESC";
		docs = dao.submitQuery(hql);
		return docs;
	}
	
	public StudPublicDocExam getStudPublicDocLastExam(int docOid){
		StudPublicDocExam examine = null;
		String hql = "From StudPublicDocExam Where docOid=" + docOid;
		hql += " Order by examDate DESC";
		List<StudPublicDocExam> examines = dao.submitQuery(hql);
		if(!examines.isEmpty()){
			examine = examines.get(0);
		}
		return examine;
	}
	
	public List<StudPublicDocUpload> getPublicDocAttach(int oid){
		//Session session = dao.getCISBLOBSession(new String[]{"tw/edu/chit/model/StudPublicDocUpload.hbm.xml"});
		String hql = "From StudPublicDocUpload Where docOid=" + oid;
		//Transaction tx  = session.beginTransaction();
		//List<StudPublicDocUpload> files = session.createQuery(hql).list();
		List<StudPublicDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//dao.closeCISBLOBSession(session);
		return files;
	}

	public StudPublicDocUpload getPublicDocUpload(int oid){
		StudPublicDocUpload upload = null;
		//Session session = dao.getCISBLOBSession(new String[]{"tw/edu/chit/model/StudPublicDocUpload.hbm.xml"});
		String hql = "From StudPublicDocUpload Where oid=" + oid;
		//Transaction tx  = session.beginTransaction();
		List<StudPublicDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//dao.closeCISBLOBSession(session);
		if(!files.isEmpty()){
			upload =  files.get(0);
		}
		return upload;
	}

	public void delPublicDocAttach(StudPublicDocUpload upload){
		try {
			//dao.removeStudPublicDocUpload(upload);
			blobdao.removeObject(upload);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ActionMessages saveStudPublicDocApplyByForm(String idno, DynaActionForm form, int days) throws Exception{
		ActionMessages msg = new ActionMessages();
		String ruleNo = "";
		
		String opmode = form.getString("opmode");
		String oid = form.getString("oid");
		String reason = form.getString("reason");
		//String askLeaveType = "6"; //公假
		String department = form.getString("department");
		String sdate = form.getString("startDate");
		String edate = form.getString("endDate");
		String startPeriod = form.getString("startPeriod");
		String endPeriod = form.getString("endPeriod");
		String fileOids = form.getString("fileOids");
		//String[] adate = sdate.split("-");
		String memo = form.getString("memo");
		String[] studentNos = form.getStrings("studentNo");

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(Toolket.parseDate(sdate));
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(Toolket.parseDate(edate));
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 0);
		Calendar createDate = Calendar.getInstance();
		
		if(opmode.equals("add")){
			//公假單依學生所在班級拆單
			StringBuffer buf = new StringBuffer();
			for(String studentNo:studentNos){
				buf.append("'").append(studentNo).append("'").append(",");
			}
			String hql = "Select departClass From Student Where studentNo in (";
			hql += buf.toString().substring(0, buf.length()-1) + ") Group by departClass";
			
			List<Object> clazzs = dao.submitQuery(hql);
			
			String classNo = "";
			
			hql = "From Student Where departClass=? And studentNo in (";
			hql += buf.toString().substring(0, buf.length()-1) + ") order by studentNo";
			
			for(Object clazz:clazzs){
				classNo = clazz.toString();
				List<Student> studs = dao.submitQuery(hql, new Object[]{classNo});
				
				//建立公假單檔
				StudPublicLeave docp = new StudPublicLeave();
				docp.setApplyId(idno);
				docp.setDepartment(classNo);
				docp.setReason(reason);
				docp.setStartDate(startCal.getTime());
				docp.setEndDate(endCal.getTime());
				docp.setRuleNo("PB113");
				docp.setStatus(IConstants.STUDDocCodeNotSend);
				docp.setCreateDate(createDate.getTime());
				docp.setStartPeriod(Short.parseShort(startPeriod));
				docp.setEndPeriod(Short.parseShort(endPeriod));
				docp.setMemo(memo);
				dao.saveObject(docp);
				dao.reload(docp);
				int docpOid = docp.getOid();
				
				//建立學生公假紀錄
				for(Student student:studs){
					/*
					student = this.findStudentByStudentNo(studentNo);
					if(student == null){
						msg.add(ActionMessages.GLOBAL_MESSAGE, 
								new ActionMessage("messageN1", "抱歉!系統查不到 " + studentNo + " 的學生資料!"));
						return msg;
					}
					*/
					StudDocApply doc = new StudDocApply();
					doc.setStudentName(student.getStudentName());
					doc.setStudentNo(student.getStudentNo());
					doc.setStartDate(startCal.getTime());
					doc.setEndDate(endCal.getTime());
					doc.setAskLeaveType("6");
					doc.setCreateDate(createDate.getTime());
					doc.setDepartClass(student.getDepartClass());
					doc.setReason(reason);
					doc.setMemo(memo);
					doc.setStatus(docp.getStatus());
					doc.setRuleNo(docp.getRuleNo());
					doc.setTotalDay((short)days);
					doc.setPublicDocOid(docpOid);
					dao.saveObject(doc);
					dao.reload(doc);
					int docOid = doc.getOid();
					
					//儲存詳細公假節次資料
					int sno = 0;
					int eno = 0;
					String tdate = "";
					StudDocDetail docd = null;
					int speriod = Integer.parseInt(startPeriod);
					int eperiod = Integer.parseInt(endPeriod);
					if(startCal.compareTo(endCal) == 0){
						List<Map> periods = findSeldInPeriod(student.getStudentNo(), startCal, startCal);
						for(Map period : periods){
							sno = Integer.parseInt(period.get("begin").toString());
							eno = Integer.parseInt(period.get("end").toString());
							if(sno<=eperiod && eno>=speriod){
								if(sno<speriod){
									sno = speriod;
								}
								if(eno>eperiod){
									eno = eperiod;
								}
								for(int j = sno; j <= eno; j++){
									docd = new StudDocDetail();
									docd.setDocOid(docOid);
									docd.setAdate(startCal.getTime());
									docd.setPeriod((short)j);
									dao.saveObject(docd);
								}
							}
						}
					}else{
						Calendar ctlCal = Calendar.getInstance();
						ctlCal.setTimeInMillis(startCal.getTimeInMillis());
						while(ctlCal.compareTo(endCal) <= 0){
							List<Map> periods = findSeldInPeriod(student.getStudentNo(), ctlCal, ctlCal);
							//調整起始及結束節次
							if(ctlCal.compareTo(startCal) == 0){
								speriod = Integer.parseInt(startPeriod);
								eperiod = 15;
							}else if(ctlCal.compareTo(endCal) == 0){
								eperiod = Integer.parseInt(endPeriod);
								speriod = 1;
							}else{
								speriod = 1;
								eperiod = 15;
							}
							
							for(Map period : periods){
								sno = Integer.parseInt(period.get("begin").toString());
								eno = Integer.parseInt(period.get("end").toString());
								if(sno<=eperiod && eno>=speriod){
									if(sno<speriod){
										sno = speriod;
									}
									if(eno>eperiod){
										eno = eperiod;
									}
									for(int j = sno; j <= eno; j++){
										docd = new StudDocDetail();
										docd.setDocOid(docOid);
										docd.setAdate(ctlCal.getTime());
										docd.setPeriod((short)j);
										dao.saveObject(docd);
									}
								}
							}
							ctlCal.add(Calendar.DATE, 1);
						}
					}
				}
				
				//update fileuploads
				if(!fileOids.trim().equals("")){
					int foid = 0;
					boolean isNew = false;
					//Session session = dao.getCISBLOBSession(new String[]{"tw/edu/chit/model/StudPublicDocUpload.hbm.xml"});
					
					StudPublicDocUpload upload = null;
					String[] foids = fileOids.split("\\|");
					for(int k=0; k<foids.length; k++){
						foid = Integer.parseInt(foids[k]);
						upload = this.getPublicDocUpload(foid);
						if(upload != null){
							StudPublicDocUpload branch = new StudPublicDocUpload();
							branch.setAttContent(upload.getAttContent());
							branch.setAttNo((short)k);
							branch.setDocOid(docpOid);
							branch.setFileName(upload.getFileName());
							this.savePublicDocAttach(branch);
						}
					}
				}
				
				//update doc  examine status
				StudPublicDocExam doce = new StudPublicDocExam();
				doce.setDocOid(docpOid);
				doce.setExamDate(Calendar.getInstance().getTime());
				doce.setExamId(idno);
				doce.setExamStatus(IConstants.STUDDocCodeNotSend);
				doce.setRuleNo("");		//新增假單時 不賦予 Rule number
				doce.setDescription("新增");
				dao.saveObject(doce);
			}
			

		}else if(opmode.equals("modify")){
			int docpOid = Integer.parseInt(form.getString("oid"));
			StudPublicLeave docp = this.findStudPublicLeaveByOid(docpOid);
			if(docp != null){
				docp.setDepartment(department);
				docp.setReason(reason);
				docp.setStartDate(startCal.getTime());
				docp.setEndDate(endCal.getTime());
				docp.setRuleNo("PB113");
				docp.setStatus(IConstants.STUDDocCodeNotSend);
				docp.setCreateDate(createDate.getTime());
				docp.setStartPeriod(Short.parseShort(startPeriod));
				docp.setEndPeriod(Short.parseShort(endPeriod));
				docp.setMemo(memo);
				dao.saveObject(docp);
			}else{
				msg.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("messageN1", "抱歉!系統查不到 該筆公假資料!"));
				return msg;
			}
			dao.reload(docp);
			
			//建立學生公假紀錄
			//刪除公假詳細節次資料
			String sql = "Delete From STUD_DocDetail Where docOid in ";
			sql += "(Select d.Oid From STUD_DocApply d, STUD_PublicLeave p Where ";
			sql += "d.publicDocOid=p.Oid And p.Oid=" + docpOid + ")";
			jdbcDao.executesql(sql);
			//刪除學生公假資料
			sql = "Delete From STUD_DocApply Where publicDocOid=" + docpOid;
			jdbcDao.executesql(sql);
			
			Student student = new Student();
			for(String studentNo:studentNos){
				student = this.findStudentByStudentNo(studentNo);
				if(student == null){
					msg.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("messageN1", "抱歉!系統查不到 " + studentNo + " 的學生資料!"));
					return msg;
				}
				
				StudDocApply doc = new StudDocApply();
				doc.setStudentName(student.getStudentName());
				doc.setStudentNo(student.getStudentNo());
				doc.setStartDate(startCal.getTime());
				doc.setEndDate(endCal.getTime());
				doc.setAskLeaveType("6");
				doc.setCreateDate(createDate.getTime());
				doc.setDepartClass(student.getDepartClass());
				doc.setReason(reason);
				doc.setMemo(memo);
				doc.setStatus(docp.getStatus());
				doc.setRuleNo(docp.getRuleNo());
				doc.setTotalDay((short)days);
				doc.setPublicDocOid(docpOid);
				dao.saveObject(doc);
				dao.reload(doc);
				int docOid = doc.getOid();
				
				//儲存詳細公假節次資料
				int sno = 0;
				int eno = 0;
				String tdate = "";
				StudDocDetail docd = null;
				int speriod = Integer.parseInt(startPeriod);
				int eperiod = Integer.parseInt(endPeriod);
				if(startCal.compareTo(endCal) == 0){
					List<Map> periods = findSeldInPeriod(studentNo, startCal, startCal);
					for(Map period : periods){
						sno = Integer.parseInt(period.get("begin").toString());
						eno = Integer.parseInt(period.get("end").toString());
						if(sno<=eperiod && eno>=speriod){
							if(sno<speriod){
								sno = speriod;
							}
							if(eno>eperiod){
								eno = eperiod;
							}
							for(int j = sno; j <= eno; j++){
								docd = new StudDocDetail();
								docd.setDocOid(docOid);
								docd.setAdate(startCal.getTime());
								docd.setPeriod((short)j);
								dao.saveObject(docd);
							}
						}
					}
				}else{
					Calendar ctlCal = Calendar.getInstance();
					ctlCal.setTimeInMillis(startCal.getTimeInMillis());
					while(ctlCal.compareTo(endCal) <= 0){
						List<Map> periods = findSeldInPeriod(studentNo, ctlCal, ctlCal);
						//調整起始及結束節次
						if(ctlCal.compareTo(startCal) == 0){
							speriod = Integer.parseInt(startPeriod);
							eperiod = 15;
						}else if(ctlCal.compareTo(endCal) == 0){
							eperiod = Integer.parseInt(endPeriod);
							speriod = 1;
						}else{
							speriod = 1;
							eperiod = 15;
						}
						
						for(Map period : periods){
							sno = Integer.parseInt(period.get("begin").toString());
							eno = Integer.parseInt(period.get("end").toString());
							if(sno<=eperiod && eno>=speriod){
								if(sno<speriod){
									sno = speriod;
								}
								if(eno>eperiod){
									eno = eperiod;
								}
								for(int j = sno; j <= eno; j++){
									docd = new StudDocDetail();
									docd.setDocOid(docOid);
									docd.setAdate(ctlCal.getTime());
									docd.setPeriod((short)j);
									dao.saveObject(docd);
								}
							}
						}
						ctlCal.add(Calendar.DATE, 1);
					}
				}
			}
			
			//update fileuploads(新增刪除檔案的工作在AjaxGlobal中已經做完了!)
			if(!fileOids.trim().equals("")){
				int foid = 0;
				boolean isNew = false;
				//Session session = dao.getCISBLOBSession(new String[]{"tw/edu/chit/model/StudPublicDocUpload.hbm.xml"});
				
				StudPublicDocUpload upload = null;
				String[] foids = fileOids.split("\\|");
				for(int k=0; k<foids.length; k++){
					foid = Integer.parseInt(foids[k]);
					upload = this.getPublicDocUpload(foid);
					if(upload != null){
						upload.setDocOid(docpOid);
						upload.setAttNo((short)k);
						this.savePublicDocAttach(upload);
					}
				}
			}
			
			//update doc  examine status
			StudPublicDocExam doce = new StudPublicDocExam();
			doce.setDocOid(docpOid);
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(idno);
			doce.setExamStatus(IConstants.STUDDocCodeNotSend);
			doce.setRuleNo("");		//假單修改後不賦予 Rule number
			doce.setDescription("修改");
			dao.saveObject(doce);
			
		}
		return msg;
	}
	
	public void savePublicDocAttach(StudPublicDocUpload upload) throws Exception {
		//Session session = dao.getCISBLOBSession(new String[]{"tw/edu/chit/model/StudPublicDocUpload.hbm.xml"});
		try{
			//Transaction tx  = session.beginTransaction();
			blobdao.saveObject(upload);
			//session.saveOrUpdate(upload);
			//tx.commit();
		}catch (HibernateException he){
			//dao.closeCISBLOBSession(session);
			he.printStackTrace();
			throw new Exception("saveOrupdate attach file of student Public doc error!");
		}
		//dao.closeCISBLOBSession(session);
	}
	
	public StudPublicLeave findStudPublicLeaveByOid(int docOid){
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		StudPublicLeave doc = null;
		String hql = "From StudPublicLeave Where oid=" + docOid ;
		docs = dao.submitQuery(hql);
		if(!docs.isEmpty()){
			doc = docs.get(0);
		}
		return doc;
	}
	
	public List<StudDocApply> getPublicDocStuds(int docOid){
		String hql = "From StudDocApply Where publicDocOid=" + docOid;
		hql += " Order by studentNo";
		
		return dao.submitQuery(hql);
	}

	public List<StudPublicLeave> delStudPublicDocApply(List<StudPublicLeave> selDocs){
		List<StudPublicLeave> unDels = new ArrayList<StudPublicLeave>();
		String status = "";
		for(StudPublicLeave doc:selDocs){
			status = doc.getStatus();
			if(status.equalsIgnoreCase("A") || status.equalsIgnoreCase("1")){
				unDels.add(doc);
			}else{
				doc.setStatus("D");
				dao.saveObject(doc);
				
				List<StudDocApply> stdocs = this.getPublicDocStuds(doc.getOid());
				for(StudDocApply stdoc:stdocs){
					stdoc.setStatus("D");
					dao.saveObject(stdoc);
				}
			}
		}
		return unDels;
	}
	
	public ActionMessages setStudPublicDoc4Examine(StudPublicLeave doc){
		ActionMessages msg = new ActionMessages();
		try{
			StudPublicDocExam doce = new StudPublicDocExam();
			doce.setDocOid(doc.getOid());
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(doc.getApplyId());
			doce.setExamStatus(IConstants.STUDDocCodeSendOut);
			doce.setRuleNo("");
			doce.setDescription("送出");
			dao.saveObject(doce);
			doc.setStatus(IConstants.STUDDocCodeSendOut);
			dao.saveObject(doc);
		}catch(Exception e){
			e.printStackTrace();
			msg.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "公假單送審錯誤:Oid=" + doc.getOid()));
		}
		return msg;
	}

	public ActionMessages resetStudPublicDoc4Examine(StudPublicLeave doc){
		ActionMessages msg = new ActionMessages();
		try{
			StudPublicDocExam doce = new StudPublicDocExam();
			doce.setDocOid(doc.getOid());
			doce.setExamDate(Calendar.getInstance().getTime());
			doce.setExamId(doc.getApplyId());
			doce.setExamStatus(IConstants.STUDDocCodeNotSend);
			doce.setRuleNo("");
			doce.setDescription("取消送核");
			dao.saveObject(doce);
			doc.setStatus(IConstants.STUDDocCodeNotSend);
			dao.saveObject(doc);
		}catch(Exception e){
			e.printStackTrace();
			msg.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "公假單取消送核錯誤:Oid=" + doc.getOid()));
		}
		return msg;
	}

	public List<StudPublicLeave> getPublicDoc4ExamByClazz(String classNo, String examMode){
		Calendar start = getFirtStartWeek();
		start.add(Calendar.DATE, -7);
		String sdate = start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + 
					start.get(Calendar.DATE);
		
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		
		String hql = "From StudPublicLeave Where department='" + classNo + "'";
		hql += " And status='A' And startDate>='" + sdate + "' ";
		hql += " order by startDate, department";
		docs = dao.submitQuery(hql);
		
		List<StudPublicDocExam> doces = new ArrayList<StudPublicDocExam>();
		StudPublicLeave doc = null;
		for(ListIterator<StudPublicLeave> docIter=docs.listIterator(); docIter.hasNext();){
			doc = docIter.next();
			if(!checkIsInPublicExamStep(doc, examMode)){
				docIter.remove();
			}
		}
		return docs;
	}
	
	public boolean checkIsInPublicExamStep(StudPublicLeave doc, String ruleNo){
		boolean inStep = false;
		List<ExamineRule> rules = getExamRuleSequence(ruleNo);
		List<StudPublicDocExam> exams = this.getStudPublicDocExamNearest(doc.getOid());
		if(!rules.isEmpty()){
			int rsize = rules.size();
			//ExamineRule thisRule = rules.get(0);
			List<ExamineRule> restRules = new ArrayList<ExamineRule>();
			if(rsize == 1){
				//我自己就是最終的審核規則
				inStep = true;
				for(StudPublicDocExam exam:exams){
					if(exam.getRuleNo().equals(ruleNo)){
						//已經做過審查了
						inStep = false;
						break;
					}
				}
			}else{
				restRules = rules.subList(1, rules.size());
				// ExamineRule parentRule = rules.get(1);
				inStep = true;
				boolean parentDone = false;
				String status = "";
				for(ExamineRule rule:restRules){
					parentDone = false;
					for(StudPublicDocExam exam:exams){
						//已經做過審查了
						if(exam.getRuleNo().equals(rule.getRuleNo())){
							status = exam.getExamStatus();
							/*  2010/07/05 全校學務會議修改,公假單原由系主任審核改由導師初審
							if(status.equals(IConstants.STUDDocCodeChairmanOK) || 
									status.equals(IConstants.STUDDocCodeStudaffairOK)){
								parentDone = true;
								break;
							}else{	//前一關的審查未通過
								parentDone = false;
								break;
							}
							*/
							if(status.equals(IConstants.STUDDocCodeTutorOK) || 
									status.equals(IConstants.STUDDocCodeStudaffairOK)){
								parentDone = true;
								break;
							}else{	//前一關的審查未通過
								parentDone = false;
								break;
							}
						}
					}
					if(!parentDone){
						inStep = false;
						break;
					}
				}
				
				for(StudPublicDocExam exam:exams){
					if(exam.getRuleNo().equals(ruleNo)){
						//已經做過審查了
						inStep = false;
						break;
					}
				}
				
			}
			
		}
		return inStep;
	}
	
	public List<StudPublicDocExam> getStudPublicDocExamNearest(int docOid){
		List<StudPublicDocExam> rets = new ArrayList<StudPublicDocExam>();
		List<StudPublicDocExam> doces = this.getStudPublicDocExamines(docOid);
		for(StudPublicDocExam doce:doces){
			if(doce.getExamStatus().equalsIgnoreCase("A")){
				break;
			}else{
				rets.add(doce);
			}
		}
		return rets;
	}
	
	public List<StudPublicDocExam> getStudPublicDocExamines(int docOid){
		String hql = "From StudPublicDocExam Where docOid=" + docOid;
		hql += " Order by examDate DESC";
		List<StudPublicDocExam> examines = dao.submitQuery(hql);
		return examines;
	}
	
	public List<Map> findDeptInCharge(int empOid){
		List<Map> depts = Toolket.getCollegeDepartment(false);

		String hql = "select cic from Clazz c, ClassInCharge cic where c.classNo = cic.classNo and cic.empOid=?";
		hql += " and cic.moduleOids like '%|87|%' group by c.deptNo order by c.campusNo, c.schoolNo, c.deptNo, c.classNo";
		
		List<ClassInCharge> charges = dao.submitQuery(hql, new Object[]{empOid});
		
		List<Map> ret = new ArrayList<Map>();
		for(ClassInCharge clazz:charges){
			for(Map dept:depts){
				if(clazz.getClassNo().substring(3, 4).equalsIgnoreCase(dept.get("idno").toString())){
					ret.add(dept);
					break;
				}
			}
		}
		return ret;
	}

	public List<StudPublicLeave> getPublicDocExamedByRule(String ruleNo, String idno){
		Calendar start = getFirtStartWeek();
		start.add(Calendar.DATE, -7);
		String sdate = start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + 
					start.get(Calendar.DATE);
		
		List<StudPublicLeave> docs = new ArrayList<StudPublicLeave>();
		String hql = "Select a From StudPublicLeave a, StudPublicDocExam d Where d.docOid=a.oid ";
		hql += " And d.ruleNo='" + ruleNo + "' And a.startDate>='" + sdate + "' ";
		if(!idno.equals("")){
			hql += " And d.examId='" + idno + "'";
		}
		hql += " Order by d.examDate DESC ";
		hql += " Group by a.oid";
		
		/*
		List<Object[]> tmpList = dao.submitQuery(hql);
		for(Object[] objs:tmpList){
			StudDocApply doc = (StudDocApply)objs[0];
			StudDocExamine doce = (StudDocExamine)objs[1];
			doce.setExamerName(Toolket.getEmplName(doce.getExamId()));
			doc.setExam(doce);
			docs.add(doc);
		}
		*/
		List<StudPublicLeave> docList = dao.submitQuery(hql);
		hql = "From StudPublicDocExam Where docOid=? And examId='" + idno + "' Order by examDate DESC";
		List<StudPublicDocExam> exams = new ArrayList<StudPublicDocExam>();
		StudPublicDocExam exam = new StudPublicDocExam();
		for(StudPublicLeave doc:docList){
			exams = dao.submitQuery(hql, new Object[]{doc.getOid()});
			exam = exams.get(0);
			exam.setStatusName(getExamineStatusName(exam.getExamStatus()));
			doc.setExam(exam);
			docs.add(doc);
		}
		return docs;
	}
	
	public ActionMessages setStudPublicDocExamStatus(StudPublicLeave doc, String ruleNo, String status, String description, String idno){
		//doc, examCode, status, description, user.getMember().getIdno()
		ActionMessages msgs = new ActionMessages();
		String endRule = doc.getRuleNo();
		
		String msg  = Toolket.getEmplName(idno) + ":您所申請, " + Toolket.Date2Str(doc.getStartDate()) + "~";
		msg += Toolket.Date2Str(doc.getEndDate()) + "的公假單(oid=" + doc.getOid() + "),";
		dao.reload(doc);
		//System.out.println("Leo01");
		if(!doc.getStatus().equals(IConstants.STUDDocCodeSendOut)){
			if(doc.getStatus().equals(IConstants.STUDDocCodeDeleted)){
				msg += "已刪除!";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeNotSend)){
				msg += "尚未送核(可能正在修改中)!";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeProcessOK)){
				msg += "已經完成請假審核流程,無須再審核!(系統有誤!請通知電算中心)";
			}else if(doc.getStatus().equals(IConstants.STUDDocCodeProcessReject)){
				msg += "該假單審核未通過,未修改重新送核前無須再審核!(或系統有誤!請通知電算中心)";
			}
			//System.out.println("Leo02");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("MessageN1", "公假單審核錯誤:Oid=" + doc.getOid() + ", " + msg));

		}else{
			//System.out.println("Leo03");
			if(checkIsInPublicExamStep(doc, ruleNo)){	//可否執行此審核步驟
				//System.out.println("Leo04");
				String examStatus = "";
				if(status.equals("0")){
					if(ruleNo.equals("PB111")){
						examStatus = IConstants.STUDDocCodeTutorReject;
					}else if(ruleNo.equals("PB112")){
						examStatus = IConstants.STUDDocCodeStudaffairReject;
					}else if(ruleNo.equals("ＰＢ11３")){
						examStatus = IConstants.STUDDocCodeSAFChiefReject;
					}
				}else if(status.equals("1")){
					//System.out.println("Leo05");
					if(ruleNo.equals("PB111")){
						examStatus = IConstants.STUDDocCodeTutorOK;
					}else if(ruleNo.equals("PB112")){
						examStatus = IConstants.STUDDocCodeStudaffairOK;
					}else if(ruleNo.equals("ＰＢ11３")){
						examStatus = IConstants.STUDDocCodeSAFChiefOK;
					}
				}

				if(ruleNo.equalsIgnoreCase(endRule)){
					//System.out.println("Leo06");
					List<StudDocApply> stdocs = this.getPublicDocStuds(doc.getOid());
					if(status.equals("0")){
						//System.out.println("Leo07");
		 				doc.setStatus(IConstants.STUDDocCodeProcessReject);
		 				dao.saveObject(doc);
		 				
		 				for(StudDocApply stdoc:stdocs){
		 					stdoc.setStatus(IConstants.STUDDocCodeProcessOK);
			 				dao.saveObject(stdoc);
		 				}
		 				
		 				StudPublicDocExam doce = new StudPublicDocExam();
		 				doce.setDocOid(doc.getOid());
		 				doce.setExamDate(Calendar.getInstance().getTime());
		 				doce.setExamId(idno);
		 				doce.setExamStatus(examStatus);
		 				doce.setRuleNo(ruleNo);
		 				doce.setDescription(description);
		 				dao.saveObject(doce);						
					}else if(status.equals("1")){
						//System.out.println("Leo08");
						//最後的查核通過
						for(StudDocApply stdoc:stdocs){
							List<StudDocDetail> docds = getStudDocDetailByDocOid(stdoc.getOid());
							if(!docds.isEmpty()){
								//System.out.println("Leo01");
								String studentNo = stdoc.getStudentNo();
								String departClass = stdoc.getDepartClass();
								String daynite = Toolket.chkDayNite(departClass);
								short altype = Short.parseShort(stdoc.getAskLeaveType());
								Calendar preDate = Calendar.getInstance();
								preDate.setTime(docds.get(0).getAdate());
								Calendar now = Calendar.getInstance();
								Dilg dilg = this.findDilgByStdDate(studentNo, Toolket.FullDate2Str(docds.get(0).getAdate()));
		 						if(dilg == null){
		 							//System.out.println("Leo02");
		 							dilg = new Dilg();
		 							dilg.setDepartClass(departClass);
		 							dilg.setStudentNo(studentNo);
		 							dilg.setWeekDay((float)this.WhatIsTheWeek(docds.get(0).getAdate()));
		 							dilg.setDdate(docds.get(0).getAdate());
		 							dilg.setDaynite(daynite);
		 						}
								DilgOne dilgone = null;
								
								String hql = "From DilgOne Where studentNo='" + studentNo + "' And ddate=? And period=?";
								Object[] parameters = new Object[2];
								
								//登錄請假紀錄
				 				for(StudDocDetail docd:docds){
				 					now.setTime(docd.getAdate());
				 					if(now.compareTo(preDate)>0){ 
				 						//System.out.println("Leo03");
										dao.saveObject(dilg);
										preDate.setTime(docd.getAdate());
				 						dilg = this.findDilgByStdDate(studentNo, Toolket.FullDate2Str(docd.getAdate()));
				 						if(dilg == null){
				 							dilg = new Dilg();
				 							dilg.setDepartClass(departClass);
				 							dilg.setStudentNo(studentNo);
				 							dilg.setWeekDay((float)this.WhatIsTheWeek(docd.getAdate()));
				 							dilg.setDdate(docd.getAdate());
				 							dilg.setDaynite(daynite);
				 						}
			 							dilg.setAbs(docd.getPeriod(), altype);
				 					}else{
				 						//System.out.println("Leo04");
				 						if(dilg.getAbs(docd.getPeriod()) != null){
				 							if(dilg.getAbs(docd.getPeriod()) != 6){	//公假不可被覆寫
				 								dilg.setAbs(docd.getPeriod(), altype);
				 							}
				 						}else{
				 							dilg.setAbs(docd.getPeriod(), altype);
				 						}
				 						
				 						/* 2010.06.10 暫時沒有功能可以直接新增DilgOne,因為 noExam要計算扣考,很多程式尚未支援
				 						 * 
				 						List<DilgOne> dilgOnes = dao.submitQuery(hql,  new Object[]{docd.getAdate(), docd.getPeriod()});
				 						if(!dilgOnes.isEmpty()){
				 							dilgone = dilgOnes.get(0);
				 							if(dilgone.getAbs() != 6){	//公假不可被覆寫
				 	 							dilgone.setAbs(altype);
				 							}
				 						}else{
				 							dilgone = new DilgOne();
				 							dilgone.setAbs(altype);
				 							dilgone.setDdate(docd.getAdate());
				 							dilgone.setDepartClass(departClass);
				 							dilgone.setPeriod(docd.getPeriod());
				 							dilgone.setStudentNo(studentNo);
				 							dilgone.setWeekDay(this.WhatIsTheWeek(docd.getAdate()));
				 							dilgone.setDaynite(Toolket.chkDayNite(departClass));
				 						}
										dao.saveObject(dilgone);
										*/
				 					}
								}
				 				//save last one record
								dao.saveObject(dilg);   //============>>>>>>>>

				 				//更動學生操性成績
								//System.out.println("Leo09");
								dao.getHibernateSession().flush();
								//System.out.println("Leo11");
								//double dilgScore = calDilgScoreByStudent(studentNo, "0");
								//System.out.println("Leo12"+dilgScore);
								//System.out.println("Leo02");========================>>>>>
								//msgs.add(modifyJustDilgScore(studentNo, dilgScore));
								//System.out.println("Leo03");
								
							}
							//System.out.println("Leo04");
							stdoc.setStatus(IConstants.STUDDocCodeProcessOK);
							//System.out.println("Leo05");
			 				dao.saveObject(stdoc);

						}
						
		 				//登陸審核完成資料
						//System.out.println("Leo06");
		 				doc.setStatus(IConstants.STUDDocCodeProcessOK);		 				
		 				dao.saveObject(doc);		 				
		 				StudPublicDocExam doce = new StudPublicDocExam();
		 				doce.setDocOid(doc.getOid());
		 				doce.setExamDate(Calendar.getInstance().getTime());
		 				doce.setExamId(idno);
		 				doce.setExamStatus(examStatus);
		 				doce.setRuleNo(ruleNo);
		 				doce.setDescription(description);
		 				dao.saveObject(doce);
		 				
					}
				}else{
					if(status.equals("0")){
		 				doc.setStatus(IConstants.STUDDocCodeProcessReject);
		 				dao.saveObject(doc);
					}
	 				//登錄審核資料
					StudPublicDocExam doce = new StudPublicDocExam();
	 				doce.setDocOid(doc.getOid());
	 				doce.setExamDate(Calendar.getInstance().getTime());
	 				doce.setExamId(idno);
	 				doce.setExamStatus(examStatus);
	 				doce.setRuleNo(ruleNo);
	 				doce.setDescription(description);
	 				dao.saveObject(doce);
	 				
				}
			}
		}
		//System.out.println("Leo07");
		return msgs;
	}

	public List<Map> getAbsenceApplyNotConfirm(String clazzFilter, String teacherId, Calendar mystart, Calendar myend, String pmode){
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTimeInMillis(mystart.getTimeInMillis());
		end.setTimeInMillis(myend.getTimeInMillis());
		
		List<Map> retList = new ArrayList<Map>();
		int iweek = 0;
		String hql = "";
		String sql = "";
		StudDocApply doc = new StudDocApply();
		List<StudDocApply> docs = new ArrayList<StudDocApply>();
		List<StudDocApply> docList = new ArrayList<StudDocApply>();
		StudPublicLeave docp = new StudPublicLeave();
		List<StudPublicLeave> docps = new ArrayList<StudPublicLeave>();
		List<StudPublicLeave> docpList = new ArrayList<StudPublicLeave>();
		List<Clazz> tutorClazz = new ArrayList<Clazz>();
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String subjectName = "";
		
		Session session = dao.getHibernateSession();
		
		//used for teacher idno compare
		class idnoComp implements Comparator {
			public int compare(Object obj1, Object obj2){
				if(obj1 instanceof StudDocApply && obj2 instanceof StudDocApply){
					String idno1 = ((StudDocApply)obj1).getDepartClass();
					String idno2 = ((StudDocApply)obj2).getDepartClass();
					int cmp = idno1.compareToIgnoreCase(idno2);
					if( cmp > 0)	return 1;
					else if(cmp == 0) return 0;
					else return -1;
				}if(obj1 instanceof StudDocApply && obj2 instanceof StudPublicLeave){
					String idno1 = ((StudDocApply)obj1).getDepartClass();
					String idno2 = ((StudPublicLeave)obj2).getDepartment();
					int cmp = idno1.compareToIgnoreCase(idno2);
					if( cmp > 0)	return 1;
					else if(cmp == 0) return 0;
					else return -1;
				}if(obj1 instanceof StudPublicLeave && obj2 instanceof StudDocApply){
					String idno1 = ((StudPublicLeave)obj1).getDepartment();
					String idno2 = ((StudDocApply)obj2).getDepartClass();
					int cmp = idno1.compareToIgnoreCase(idno2);
					if( cmp > 0)	return 1;
					else if(cmp == 0) return 0;
					else return -1;
				}if(obj1 instanceof StudPublicLeave && obj2 instanceof StudPublicLeave){
					String idno1 = ((StudPublicLeave)obj1).getDepartment();
					String idno2 = ((StudPublicLeave)obj2).getDepartment();
					int cmp = idno1.compareToIgnoreCase(idno2);
					if( cmp > 0)	return 1;
					else if(cmp == 0) return 0;
					else return -1;
				}
				return 0;
			}
			
			public boolean equals(Object obj){
				return super.equals(obj);
			}
		}

		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		//log.debug("out of while , start:" + start.getTime() + ", end:"+end.getTime() + ", week:" + iweek);
		if(!teacherId.equals("")){
			Map user = new HashMap();
			sql = "select e.* From empl e Where e.idno='" + teacherId + "'";
			List<Map> empls = jdbcDao.findAnyThing(sql);
			if(!empls.isEmpty()){
				user = empls.get(0);
				Calendar[] termStart = null;
				Calendar[] termEnd = null;
				String hdept = "";
				
				if(pmode.equals("1")){	//明細表
					String idno=teacherId;
					//找出未審查的假單
					//導師負責班級
					hql = " select c from Clazz c, ClassInCharge cic "
						+ "where c.classNo = cic.classNo "
						+ "  and cic.empOid = ? " 
						+ "  and cic.classNo like ? " 
						+ "  and cic.moduleOids like ? "
						+ "order by c.campusNo, c.schoolNo, c.deptNo, c.classNo";
					tutorClazz = dao.submitQuery(hql, 
							new Object[] {Integer.parseInt(user.get("Oid").toString()), clazzFilter + "%",
							"%|" + UserCredential.AuthorityOnTutor + "|%"});
					//tutorClazz = memberdao.findClassesInChargeByMemberModuleOids(
					//		Integer.parseInt(user.get("Oid").toString()),  UserCredential.AuthorityOnTutor);
					if(tutorClazz != null){
						if(!tutorClazz.isEmpty()){
							for(Clazz clazz:tutorClazz){
								//hql = "From StudDocApply Where departClass=? And status=?";
								docList = getAbsenceApply4ExamByClazz(clazz.getClassNo(), "SD111");
								docs.addAll(docList);
								docpList = this.getPublicDoc4ExamByClazz(clazz.getClassNo(), "PB111");
								docps.addAll(docpList);
							}
						}
					}
							
					List<Object> notConfirm = new ArrayList<Object>();
					if (!docs.isEmpty()) {
						Calendar examCal = Calendar.getInstance();
						for (ListIterator docsIter = docs.listIterator(); docsIter
								.hasNext();) {
							doc = (StudDocApply) docsIter.next();
							StudDocExamine examd = this
									.getStudDocLastExamine(doc.getOid());
							if (examd != null) {
								examCal.setTime(examd.getExamDate());
								if (examCal.compareTo(start) >= 0
										&& examCal.compareTo(end) <= 0) {
									notConfirm.add(doc);
								}
							}else{
								notConfirm.add(doc);
							}
						}
					}
					if (!docps.isEmpty()) {
						Calendar examCal = Calendar.getInstance();
						for (ListIterator docpsIter = docps.listIterator(); docpsIter
								.hasNext();) {
							docp = (StudPublicLeave) docpsIter.next();
							StudPublicDocExam examd = this.getStudPublicDocLastExam(docp.getOid());
							if (examd != null) {
								examCal.setTime(examd.getExamDate());
								if (examCal.compareTo(start) >= 0
										&& examCal.compareTo(end) <= 0) {
									notConfirm.add(docp);
								}
							}else{
								notConfirm.add(docp);
							}
						}
					}
					
					Map cfMap = new HashMap();
					cfMap.put("docs", notConfirm);
					cfMap.put("teacherId", idno);
					cfMap.put("tel", "市話:" +user.get("telephone").toString() + ",行動:" + user.get("CellPhone").toString());
					cfMap.put("teacherName", user.get("cname").toString());
					if(user.get("email") != null){
						cfMap.put("email", user.get("email").toString());
					}else{
						cfMap.put("email", "");
					}
					retList.add(cfMap);
					
				}else if(pmode.equals("2")){	//統計表
					
				}
			}
		}else{	//依照classFilter查全部導師
			if(pmode.equals("1")){	//明細表
				//找出未審查的假單
				//導師負責班級
				hql = "From Clazz where classNo like ? " 
					+ " order by campusNo, schoolNo, deptNo, classNo";
				tutorClazz = dao.submitQuery(hql, new Object[] {clazzFilter + "%"});
				if(tutorClazz != null){
					if(!tutorClazz.isEmpty()){
						for(Clazz clazz:tutorClazz){
							//hql = "From StudDocApply Where departClass=? And status=?";
							docList = getAbsenceApply4ExamByClazz(clazz.getClassNo(), "SD111");
							docs.addAll(docList);
							docpList = this.getPublicDoc4ExamByClazz(clazz.getClassNo(), "PB111");
							docps.addAll(docpList);
						}
					}
				}
				
				List<Object> notConfirm = new ArrayList<Object>();
				Empl tutor = null;		
				if (!docs.isEmpty()) {
					Calendar examCal = Calendar.getInstance();
					for (ListIterator docsIter = docs.listIterator(); docsIter
							.hasNext();) {
						doc = (StudDocApply) docsIter.next();
						StudDocExamine examd = this
								.getStudDocLastExamine(doc.getOid());
						if (examd != null) {
							examCal.setTime(examd.getExamDate());
							if (examCal.compareTo(start) >= 0
									&& examCal.compareTo(end) <= 0) {
								notConfirm.add(doc);
							}
						}else{
							notConfirm.add(doc);
						}
					}
				}
				if (!docps.isEmpty()) {
					Calendar examCal = Calendar.getInstance();
					for (ListIterator docpsIter = docps.listIterator(); docpsIter
							.hasNext();) {
						docp = (StudPublicLeave) docpsIter.next();
						StudPublicDocExam examd = this.getStudPublicDocLastExam(docp.getOid());
						if (examd != null) {
							examCal.setTime(examd.getExamDate());
							if (examCal.compareTo(start) >= 0
									&& examCal.compareTo(end) <= 0) {
								notConfirm.add(docp);
							}
						}else{
							notConfirm.add(docp);
						}
					}
				}
				
				if(!notConfirm.isEmpty()){
					//依照班級排序
					Collections.sort(notConfirm,new idnoComp());

					String preClazz = "", clazz = "";
					Object obj1 = notConfirm.get(0);
					if(obj1 instanceof StudDocApply){
						preClazz = ((StudDocApply)obj1).getDepartClass();
					}else if(obj1 instanceof StudPublicLeave){
						preClazz = ((StudPublicLeave)obj1).getDepartment();
					}
					List<Object> nfs = new ArrayList<Object>();
					for(Object obj:notConfirm){
						if(obj instanceof StudDocApply){
							clazz = ((StudDocApply)obj).getDepartClass();
						}else if(obj instanceof StudPublicLeave){
							clazz = ((StudPublicLeave)obj).getDepartment();
						}
						if(clazz.equalsIgnoreCase(preClazz)){
							nfs.add(obj);
						}else{
							Map cfMap = new HashMap();
							cfMap.put("docs", nfs);
							tutor = Toolket.findTutorOfClass(preClazz);
							if(tutor != null){
								cfMap.put("teacherId", tutor.getIdno());
								cfMap.put("teacherName", tutor.getCname());
								cfMap.put("tel", "市話:" +tutor.getTelephone() + ",行動:" + tutor.getCellPhone());
								if(tutor.getEmail() != null){
									cfMap.put("email", tutor.getEmail());
								}else{
									cfMap.put("email", "");
								}
							}else{
								cfMap.put("teacherId", null);
							}
							retList.add(cfMap);
							preClazz = clazz;
							nfs = new ArrayList<Object>();
							nfs.add(obj);
						}
					}
					Map cfMap = new HashMap();
					cfMap.put("docs", nfs);
					tutor = Toolket.findTutorOfClass(preClazz);
					if(tutor != null){
						cfMap.put("teacherId", tutor.getIdno());
						cfMap.put("teacherName", tutor.getCname());
						cfMap.put("tel", "市話:" +tutor.getTelephone() + ",行動:" + tutor.getCellPhone());
						if(tutor.getEmail() != null){
							cfMap.put("email", tutor.getEmail());
						}else{
							cfMap.put("email", "");
						}
					}else{
						cfMap.put("teacherId", null);
					}
					retList.add(cfMap);
				}

			}else if(pmode.equals("2")){	//統計表
				//找出未審查的假單
				//導師負責班級
				hql = "From Clazz where classNo like ? " 
					+ " order by campusNo, schoolNo, deptNo, classNo";
				tutorClazz = dao.submitQuery(hql, new Object[] {clazzFilter + "%"});
				if(tutorClazz != null){
					if(!tutorClazz.isEmpty()){
						for(Clazz clazz:tutorClazz){
							//hql = "From StudDocApply Where departClass=? And status=?";
							docList = getAbsenceApply4ExamByClazz(clazz.getClassNo(), "SD111");
							docs.addAll(docList);
							docpList = this.getPublicDoc4ExamByClazz(clazz.getClassNo(), "PB111");
							docps.addAll(docpList);
						}
					}
				}
				
				List<Object> notConfirm = new ArrayList<Object>();
				Empl tutor = null;		
				if (!docs.isEmpty()) {
					Calendar examCal = Calendar.getInstance();
					for (ListIterator docsIter = docs.listIterator(); docsIter
							.hasNext();) {
						doc = (StudDocApply) docsIter.next();
						StudDocExamine examd = this
								.getStudDocLastExamine(doc.getOid());
						if (examd != null) {
							examCal.setTime(examd.getExamDate());
							if (examCal.compareTo(start) >= 0
									&& examCal.compareTo(end) <= 0) {
								notConfirm.add(doc);
							}
						}else{
							notConfirm.add(doc);
						}
					}
				}
				if (!docps.isEmpty()) {
					Calendar examCal = Calendar.getInstance();
					for (ListIterator docpsIter = docps.listIterator(); docpsIter
							.hasNext();) {
						docp = (StudPublicLeave) docpsIter.next();
						StudPublicDocExam examd = this.getStudPublicDocLastExam(docp.getOid());
						if (examd != null) {
							examCal.setTime(examd.getExamDate());
							if (examCal.compareTo(start) >= 0
									&& examCal.compareTo(end) <= 0) {
								notConfirm.add(docp);
							}
						}else{
							notConfirm.add(docp);
						}
					}
				}
				
				if(!notConfirm.isEmpty()){
					//依照班級排序
					Collections.sort(notConfirm,new idnoComp());

					String preClazz = "", clazz = "";
					Object obj1 = notConfirm.get(0);
					if(obj1 instanceof StudDocApply){
						preClazz = ((StudDocApply)obj1).getDepartClass();
					}else if(obj1 instanceof StudPublicLeave){
						preClazz = ((StudPublicLeave)obj1).getDepartment();
					}
					List<Object> nfs = new ArrayList<Object>();
					int dcount = 0, pcount = 0;
					for(Object obj:notConfirm){
						if(obj instanceof StudDocApply){
							clazz = ((StudDocApply)obj).getDepartClass();
							dcount++;
						}else if(obj instanceof StudPublicLeave){
							clazz = ((StudPublicLeave)obj).getDepartment();
							pcount++;
						}
						if(clazz.equalsIgnoreCase(preClazz)){
							//nfs.add(obj);
						}else{
							Map cfMap = new HashMap();
							if(obj instanceof StudDocApply){
								cfMap.put("pcount", pcount);
								cfMap.put("dcount", dcount-1);
								pcount = 0;
								dcount = 1;
							}else if(obj instanceof StudPublicLeave){
								cfMap.put("pcount", pcount-1);
								cfMap.put("dcount", dcount);
								pcount = 1;
								dcount = 0;
							}
							tutor = Toolket.findTutorOfClass(preClazz);
							if(tutor != null){
								cfMap.put("teacherId", tutor.getIdno());
								cfMap.put("teacherName", tutor.getCname());
								cfMap.put("tel", "市話:" +tutor.getTelephone() + ",行動:" + tutor.getCellPhone());
								if(tutor.getEmail() != null){
									cfMap.put("email", tutor.getEmail());
								}else{
									cfMap.put("email", "");
								}
							}else{
								cfMap.put("teacherId", null);
							}
							retList.add(cfMap);
							preClazz = clazz;
						}
					}
					Map cfMap = new HashMap();
					cfMap.put("pcount", pcount);
					cfMap.put("dcount", dcount);
					tutor = Toolket.findTutorOfClass(preClazz);
					if(tutor != null){
						cfMap.put("teacherId", tutor.getIdno());
						cfMap.put("teacherName", tutor.getCname());
						if(tutor.getEmail() != null){
							cfMap.put("email", tutor.getEmail());
						}else{
							cfMap.put("email", "");
						}
					}else{
						cfMap.put("teacherId", null);
					}
					retList.add(cfMap);
				}
			}
		}
		
		return retList;
	}
	
	public List<int[][]> findBonusPenalty4edu(String schoolYear, String schoolTerm){
		List<int[][]> retList = new ArrayList<int[][]>();
		
		/*
		 * 	日大:(c.depart_class like '164%' Or c.depart_class like '142%' Or c.depart_class like '1A4%' Or c.depart_class like '1A3%' Or c.depart_class like '1F2%')
			夜大:(c.depart_class like '154%' Or c.depart_class like '152%' Or c.depart_class like '182%' Or c.depart_class like '172%' Or c.depart_class like '1B4%' 
 				Or c.depart_class like '1B3%' Or c.depart_class like '1D2%' Or c.depart_class like '1H2%')
			日專:(c.depart_class like '112%' Or c.depart_class like '115%' Or c.depart_class like '1A2%')
			夜專:(c.depart_class like '122%' Or c.depart_class like '192%' Or c.depart_class like '132%' Or c.depart_class like '1B2%')
			日碩:(c.depart_class like '11G%' Or c.depart_class like '1CG%' Or c.depart_class like '1FG%')
			夜碩:(c.depart_class like '18G%' Or c.depart_class like '1DG%' Or c.depart_class like '1HG%')
		 */
		String[][] dept = {
				{
					"(c.depart_class like '11G%' Or c.depart_class like '1CG%' Or c.depart_class like '1FG%')",
					"(c.depart_class like '164%' Or c.depart_class like '142%' Or c.depart_class like '1A4%' Or c.depart_class like '1A3%' Or c.depart_class like '1F2%')",
					"(c.depart_class like '112%' Or c.depart_class like '115%' Or c.depart_class like '1A2%')"
				},
				
				{
					"(c.depart_class like '18G%' Or c.depart_class like '1DG%' Or c.depart_class like '1HG%')",
					"(c.depart_class like '154%' Or c.depart_class like '152%' Or c.depart_class like '182%' Or c.depart_class like '172%' Or c.depart_class like '1B4%' Or c.depart_class like '1B3%' Or c.depart_class like '1D2%' Or c.depart_class like '1H2%')",
					"(c.depart_class like '122%' Or c.depart_class like '192%' Or c.depart_class like '132%' Or c.depart_class like '1B2%')"
				},
				
				{
					"(c.depart_class like '21G%' Or c.depart_class like '2CG%' Or c.depart_class like '2FG%')",
					"(c.depart_class like '264%' Or c.depart_class like '242%' Or c.depart_class like '2A4%' Or c.depart_class like '2A3%' Or c.depart_class like '2F2%')",
					"(c.depart_class like '212%' Or c.depart_class like '215%' Or c.depart_class like '2A2%')"
				},
				
				{
					"(c.depart_class like '28G%' Or c.depart_class like '2DG%' Or c.depart_class like '2HG%')",
					"(c.depart_class like '254%' Or c.depart_class like '252%' Or c.depart_class like '282%' Or c.depart_class like '272%' Or c.depart_class like '2B4%' Or c.depart_class like '2B3%' Or c.depart_class like '2D2%' Or c.depart_class like '2H2%')",
					"(c.depart_class like '222%' Or c.depart_class like '292%' Or c.depart_class like '232%' Or c.depart_class like '2B2%')"
				}
				}; 
		
		String sql = "";
		int pos = 3;
		int p = 0, n = 0, kp = 0;
		for(int i=0; i<dept.length; i++){
			int[][] qty = new int[16][6];
			for(int bp=1; bp<=8; bp++){	//獎懲種類1~6, 7:定察 8:退學 
				for(int k=0; k<dept[i].length; k++){
					for(int s=1; s<=2; s++){
						switch(bp){
						case 1: case 2: case 3: case 4: case 5: case 6:
							//計算人數
							sql = "select count(*) From comb2 c, stmd s Where c.school_year='" + schoolYear + "' And c.school_term='" + schoolTerm + 
							"' And (c.kind1='" + bp + "' Or c.kind2='" + bp + "') And c.student_no=s.student_no And s.sex='" + s + "'" + " And " + dept[i][k];
							p = jdbcDao.getRecordsCount(sql);
							sql = "select count(*) From comb2 c, Gstmd s Where c.school_year='" + schoolYear + "' And c.school_term='" + schoolTerm + 
							"' And (c.kind1='" + bp + "' Or c.kind2='" + bp + "') And c.student_no=s.student_no And s.sex='" + s + "'" + " And " + dept[i][k];
							p += jdbcDao.getRecordsCount(sql);
							
							//計算次數
							sql = "select sum(IF(c.kind1='" + bp + "', c.cnt1, 0))+ sum(IF(c.kind2='" + bp + "',c.cnt2,0)) From comb2 c, stmd s Where c.school_year='" + 
							schoolYear + "' And c.school_term='" + schoolTerm + "' And (c.kind1='" + bp + "' Or c.kind2='" + bp + "') And c.student_no=s.student_no " + 
							" And s.sex='" + s + "'" + " And " + dept[i][k];
							n = jdbcDao.getRecordsCount(sql);
							sql = "select sum(IF(c.kind1='" + bp + "', c.cnt1, 0))+ sum(IF(c.kind2='" + bp + "',c.cnt2,0)) From comb2 c, Gstmd s Where c.school_year='" + 
							schoolYear + "' And c.school_term='" + schoolTerm + "' And (c.kind1='" + bp + "' Or c.kind2='" + bp + "') And c.student_no=s.student_no " + 
							" And s.sex='" + s + "'" + " And " + dept[i][k];
							n += jdbcDao.getRecordsCount(sql);
							
						break;
						
						case 7:
							//定察學生
							sql = "Select count(*) From keep k, stmd c Where k.student_no=c.student_no" + 
							" and k.down_year=" + schoolYear + " and k.down_term=" + schoolTerm + 
							" and k.up_year is null and k.up_term is null" +
							" and c.sex='" + s + "' And " + dept[i][k];
							p = jdbcDao.getRecordsCount(sql);
							n = p;
							
						break;
						
						case 8:
							//退學學生
							sql = "select count(*) From Gstmd c Where c.occur_year='" + schoolYear + "' And c.occur_term='" + schoolTerm + "' And c.occur_status='2'" +
							" And c.occur_cause in ('002', '011', '012', '013', '014', '015') And c.sex='" + s + "'" + " And " + dept[i][k];
							p = jdbcDao.getRecordsCount(sql);
							n = p;
						break;
						}

						qty[(bp-1)*2][k*2+s-1]= p;
						qty[(bp-1)*2 +1][k*2+s-1]= n;

					}
				}	//end dept-kind
			}	//end bp
			retList.add(qty);
		}	//end dept
		
		return retList;
	}
	
	
	/**
	 * 取得本學期最早開學部制的開學日期
	 * @return Calendar
	 */
	private Calendar getFirtStartWeek(){
		Calendar first = Calendar.getInstance();
		first.clear();
		
		String sql = "Select * From week order by wdate";
		List tmpList = jdbcDao.findAnyThing(sql);
		String sdate = ((Map) tmpList.get(0)).get("wdate").toString();
		
		String[] dates = sdate.split("-");
		first.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2]), 0, 0, 0);
		return first;

	}
}	//end of class