package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Savedesd;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.ScoreHistNote;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 畢業資格審查
 * @author JOHN
 * 2007/10
 */
public class CheckGraduateAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		if(request.getParameter("index")==null){//沒有順序			
			removeSession(request);
		}		
		setContentPage(request.getSession(false), "registration/CheckGraduate.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 設定審查範圍
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm chForm = (DynaActionForm) form;
		int SchoolYear=manager.getSchoolYear();
		String schoolYear=String.valueOf(SchoolYear);
		String schoolTerm=String.valueOf(manager.getSchoolTerm());
		boolean filed=false;
		//若本學年本學期產生的成績大於3000筆代表本學期已建檔
		if(manager.ezGetInt("SELeCT COUNT(*)FROM ScoreHist WHERE school_year='"+schoolYear+"' AND school_term='"+schoolTerm+"'")>3000){
			filed=true;
			//session.setAttribute("thisTerm", manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE student_no='"+student_no+"' AND Sterm='"+schoolTerm+"'"));
		}
		//System.out.println(filed);
		
		String classNo = (String) chForm.get("classNo");
		
		String studentNo = (String) chForm.get("studentNo");
		String studentName = (String) chForm.get("studentName");	
				
		//沒有輸入的情形
		if(classNo.length()<4&& studentName.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "至少要有1個完整學號匹配姓名, 或1個完整班級代碼匹配班級名稱"));
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}		
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<classes.length; i++){
			sb.append("'"+classes[i].getClassNo()+"', ");
		}
		sb.delete(sb.length()-2, sb.length());		
		
		//單一學生的情型
		if(!studentName.trim().equals("")){
			if(summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+studentNo+"' AND depart_class IN("+sb+")")>0){
				classNo=((Map)manager.ezGetBy("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'").get(0)).get("depart_class").toString();
			}else{
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", studentNo+""+studentName+", 已經離校"));
				saveErrors(request, error);	
				return mapping.findForward("Main");
			}
		}		
		//System.out.println("SELECT COUNT(*)FROM Class WHERE ClassNo IN("+sb+")");
		if(manager.ezGetInt("SELECT COUNT(*)FROM Class WHERE ClassNo='"+classNo+"' AND ClassNo IN("+sb+")")<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您沒有管理這個班級的權限"));
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}		
		//Map map;
		//本班應修課程
		List classNos=manager.getStudyPath(classNo, SchoolYear);
		List allDtimes=manager.getSaveDtime4One(classNo, classNos, SchoolYear, filed);		
		//List allDtime=manager.getSaveDtime4One(classNo, filed);		
		//製做歷年應修
		sb=new StringBuilder("SELECT school_year, school_term FROM Savedtime WHERE school_year IN(");
		for(int i=0; i<allDtimes.size(); i++){
			sb.append("'"+((Map)allDtimes.get(i)).get("school_year")+"', ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(")GROUP BY school_year, school_term");
		
		List allDtime4check=new ArrayList();	
		try{
			allDtime4check.addAll(manager.ezGetBy(sb.toString()));
			for(int i=0; i<classNos.size(); i++){
				((Map)allDtime4check.get(i)).put("savedtime", manager.ezGetBy("SELECT s.*, c.chi_name FROM Savedtime s, Csno c WHERE " +
						"s.school_year='"+((Map)allDtime4check.get(i)).get("school_year") +"' AND s.cscode=c.cscode AND " +
						"s.school_term='"+((Map)allDtime4check.get(i)).get("school_term")+"'AND " +
						"s.depart_class='"+((Map)classNos.get(i)).get("ClassNo")+"' AND s.cscode NOT IN('50000', 'T0001', '99999') GROUP BY c.cscode ORDER BY c.cscode"));			
			}
		}catch(Exception e){
			//allDtime4check=manager.ezGetBy(sb.toString());	
		}
			
				
		session.setAttribute("allDtime", allDtime4check);		
		session.setAttribute("filed", filed);
		//過濾
		List graList=manager.getMyBadIdea(allDtimes, classNo, studentNo, schoolYear, schoolTerm, filed);
		session.setAttribute("graList", graList);
		//if(session.getAttribute("begin")==null){
		session.setAttribute("begin", 0);
		session.setAttribute("end", 4);
		//}
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共計 "+graList.size()+"位同學"));
		saveMessages(request, msg);
		return mapping.findForward("Main");
	}
	
	public ActionForward queryForNex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm chForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String schoolYear=String.valueOf(manager.getSchoolYear());
		String schoolTerm=String.valueOf(manager.getSchoolTerm());
		String classNo = (String) chForm.get("classNo");
		String studentNo = (String) chForm.get("studentNo");
		
		boolean filed=false;
		if(manager.ezGetInt("SELeCT COUNT(*)FROM ScoreHist WHERE school_year='"+schoolYear+"' AND school_term='"+schoolTerm+"'")>3000){
			filed=true;
		}
		
		String studentName = (String) chForm.get("studentName");	
		
		//沒有輸入的情形
		if(classNo.length()<4&& studentName.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "至少要有1個完整學號匹配姓名, 或1個完整班級代碼匹配班級名稱"));
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}
		//單一學生的情型
		if(!studentName.trim().equals("")){
			if(summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+studentNo+"'")>0){
				classNo=((Map)manager.ezGetBy("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'").get(0)).get("depart_class").toString();
			}else{
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "無此學生"));
				saveErrors(request, error);	
				return mapping.findForward("Main");
			}
		}
		
		//Map map;
		int SchoolYear=manager.getSchoolYear();
		//本班應修課程	
		List allDtimes=manager.getSaveDtime4One(classNo, manager.getStudyPath(classNo, SchoolYear), SchoolYear, filed);		
		session.setAttribute("allDtime", allDtimes);
		//session.setAttribute("filed", filed);
		//過濾		
		session.setAttribute("graList", manager.getMyBadIdea(allDtimes, classNo, studentNo, schoolYear, schoolTerm, filed));
		
		if(session.getAttribute("begin")==null){
			session.setAttribute("begin", 0);
			session.setAttribute("end", 4);
		}
		//return mapping.findForward("Main");
		response.sendRedirect("/CIS/Registration/CheckGraduate.do?index="+session.getAttribute("end"));
		return null;
	}
	
	/**
	 * 清除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//SummerManager summerManager = (SummerManager) getBean("summerManager");
		//DynaActionForm chForm = (DynaActionForm) form;
		
		removeSession(request);
		
		return mapping.findForward("Main");
	}
	

	/**
	 * 取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm chForm = (DynaActionForm) form;
		*/
		removeSession(request);
		return mapping.findForward("Main");
	}
	
	
	/**
	 * 繼續
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward Continue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm chForm = (DynaActionForm) form;
		String docNo[]=(String[])chForm.get("docNo");
		String aGrade[]=(String[])chForm.get("aGrade");
		String ident_remark[]=chForm.getStrings("ident_remark");
		String ident_remarkNo[]=chForm.getStrings("ident_remarkNo");
		
		//沒有勾選任何人的情形
		if(aGrade.length<1&& docNo.length<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有人需要繼續"));
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}
		
		List ready=new ArrayList();
		List goNow=new ArrayList();
		
		for(int i=0; i<aGrade.length; i++){
			//有勾選的人才做
			if(!aGrade[i].trim().equals("")){
				Map map=manager.ezGetMap("SELECT c.ClassName, s.student_no, s.student_name, s.occur_status FROM stmd s, Class c " +
						"WHERE c.ClassNo=s.depart_class AND s.student_no ='"+aGrade[i]+"'");				
				if(!docNo[i].equals("")){					
					map.put("occur_graduate_no", docNo[i]);
					goNow.add(map);
				}else{					
					ready.add(map);
				}
			}

			//立即處理備註
			if(!ident_remark[i].equals("")){
				manager.executeSql("UPDATE stmd SET ident_remark='"+ident_remark[i]+"' WHERE student_no='"+ident_remarkNo[i]+"'");
			}
		}
		
		//處理快速記號		
		String simNote[]=chForm.getStrings("simNote");
		String simSHOid[]=chForm.getStrings("simSHOid");
		String simSHNOid[]=chForm.getStrings("simSHNOid");
		String SavedtimeOid[]=chForm.getStrings("SavedtimeOid");
		String simDiscount[]=chForm.getStrings("simDiscount");//折免學號
		String simDisDtime[]=chForm.getStrings("simDisDtime");//折免課程
		String simThisTerm[]=chForm.getStrings("simThisTerm");//折免課程是否本學期？Y=本學期
		
		ScoreHistNote shn;
		String schoolYear=manager.getSchoolYear().toString();
		String schoolTerm=manager.getSchoolTerm().toString();
		
		for(int i=0; i<simDiscount.length; i++){

			//有抵免字樣的
			if(!simDiscount[i].equals("")){//產生抵免成績
				
				ScoreHist sh=new ScoreHist();
				if(simThisTerm[i].equals("")){//折歷年
					//System.out.println("FROM Savedtime WHERE Oid='"+simDisDtime+"'");
					Savedtime sd=(Savedtime)manager.hqlGetBy("FROM Savedtime WHERE Oid='"+simDisDtime[i]+"'").get(0);
					sh.setCredit(sd.getCredit());
					sh.setCscode(sd.getCscode());
					sh.setEvgrType("6");
					sh.setOpt(sd.getOpt());
					sh.setSchoolYear(sd.getSchoolYear());
					sh.setSchoolTerm(sd.getSchoolTerm().toString());
					sh.setStudentNo(simDiscount[i]);
					sh.setStdepartClass(sd.getDepartClass());
					manager.updateObject(sh);
					
					shn=new ScoreHistNote();
					shn.setNote("辦理抵免");
					shn.setSavedtimeOid(Integer.parseInt(simDisDtime[i]));
					shn.setScoreHistOid(sh.getOid());
					manager.updateObject(shn);
					
				}else{//折本學期
					Dtime d=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+simDisDtime[i]+"'").get(0);
					sh.setCredit(d.getCredit());
					sh.setCscode(d.getCscode());
					sh.setEvgrType("6");
					sh.setOpt(d.getOpt());
					sh.setSchoolYear(Short.parseShort(schoolYear));
					sh.setSchoolTerm(schoolTerm);
					sh.setStudentNo(simDiscount[i]);
					sh.setStdepartClass(d.getDepartClass());
					
					manager.updateObject(sh);
					
					shn=new ScoreHistNote();
					shn.setNote("辦理抵免");
					shn.setSavedtimeOid(Integer.parseInt(simDisDtime[i]));
					shn.setScoreHistOid(sh.getOid());
					manager.updateObject(shn);
				}
			}			
		}
		
		for(int i=0; i<simSHOid.length; i++){
			
			if(!simNote[i].equals("")){
				
				if(simNote[i].equals("DEL")){//del的清況
					//System.out.println("DELETE FROM ScoreHistNote WHERE Oid='"+simSHNOid[i]+"'");
					manager.executeSql("DELETE FROM ScoreHistNote WHERE Oid='"+simSHNOid[i]+"'");
				}else{
					if(simSHNOid[i].equals("")){
						shn=new ScoreHistNote();
					}else{
						shn=(ScoreHistNote)manager.hqlGetBy("FROM ScoreHistNote WHERE Oid="+simSHNOid[i]).get(0);
					}					
					shn.setNote(simNote[i]);
					shn.setSavedtimeOid(Integer.parseInt(SavedtimeOid[i]));
					//System.out.println("simSHOid[i]="+simSHOid[i]);
					try{
						shn.setScoreHistOid(Integer.parseInt(simSHOid[i]));
						manager.updateObject(shn);
					}catch(Exception e){
						
					}
				}
			}
		}
		
		//處理進階記號
		String exNote[]=chForm.getStrings("exNote");
		String exSHOid[]=chForm.getStrings("exSHOid");
		String exSHNOid[]=chForm.getStrings("exSHNOid");
		saveExNote(exNote, exSHOid, exSHNOid);
		/*
		for(int i=0; i<exSHOid.length; i++){
			if(exSHNOid[i].equals("")){//如果exSHNOid是空的
				if(!exNote[i].equals("")){//如果note不是空的
					shn=new ScoreHistNote();
					shn.setNote(exNote[i]);
					shn.setScoreHistOid(Integer.parseInt(exSHOid[i]));
					manager.updateObject(shn);
				}
			}else{				
				try{
					if(!exSHNOid[i].equals("")){
						shn=(ScoreHistNote) manager.hqlGetBy("FROM ScoreHistNote WHERE Oid='"+exSHNOid[i]+"'").get(0);
						shn.setNote(exNote[i]);
						shn.setScoreHistOid(Integer.parseInt(exSHOid[i]));
						manager.updateObject(shn);
					}else{
						manager.executeSql("DELETE FROM ScoreHistNote WHERE Oid='"+exSHNOid[i]+"'");
					}
				}catch(Exception e){
					
				}				
			}
		}
		*/
		
		session.setAttribute("goNow", goNow);
		session.setAttribute("ready", ready);
		session.setAttribute("mode", "gra");
		
		return mapping.findForward("Main");
	}
	
	
	/**
	 * 完成
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String schoolYear=manager.getSchoolYear().toString();
		String schoolTerm=manager.getSchoolTerm().toString();
		
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		
		if(schoolTerm.equals("1")){			
			c.set(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 30);
		}else{
			c.set(Calendar.MONTH, 6);
			c.set(Calendar.DAY_OF_MONTH, 31);
		}		
		
		int begin=Integer.parseInt(session.getAttribute("begin").toString());
		int end=Integer.parseInt(session.getAttribute("end").toString());
		
		List ready=(List)session.getAttribute("ready");// 改變狀態的
		List goNow=(List)session.getAttribute("goNow");// 立即畢業的
		
		List graList=(List)session.getAttribute("graList");
		//這個動作是要重新設定畢業狀況吧, 為防止有人被變回不能畢業, 因此要將整頁人先變為預設值
		for(int i=begin; i<=end; i++){
			if(graList.size()>=i){
				try{
					manager.executeSql("UPDATE stmd SET occur_status=null WHERE student_no='"+((Map)graList.get(i)).get("student_no")+"' AND occur_status='6'");
				}catch(Exception e){
					//e.printStackTrace();
				}				
			}			
		}				
		
		//立即畢業迴圈
		DynaActionForm cForm = (DynaActionForm) form;
		String docNo[]=cForm.getStrings("docNo");
		String aGrade[]=cForm.getStrings("aGrade");
		String studentNo;
		for(int i=0; i<docNo.length; i++){			
			studentNo=aGrade[i];
			if(!docNo[i].trim().equals("")){
				delStuProperty(studentNo, schoolYear, schoolTerm);			
				Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+studentNo+"'").get(0);
				Graduate gstmd=chStmd(stmd);
				gstmd.setOccurDate(c.getTime());
				gstmd.setOccurYear(Short.parseShort(schoolYear));
				gstmd.setOccurTerm(schoolTerm);
				gstmd.setOccurGraduateNo( ((Map)goNow.get(i)).get("occur_graduate_no").toString());
				gstmd.setOccurDate(c.getTime());
				gstmd.setOccurStatus("6");			
				
				manager.updateObject(gstmd);
				manager.executeSql("DELETE FROM stmd WHERE student_no='"+studentNo+"'");//幹掉
				//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除 
				/*
				if(manager.testOnlineServer()){
					try {
						Toolket.sendStudentInfoByQueue(stmd, "D"); // 同步stmd
						Toolket.sendGraduateInfoByQueue(gstmd, "I");
					} catch (Exception e) {
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map) ready.get(i)).get("student_no")+"同步發生錯誤 , 請連絡電算中心!"));
						saveMessages(request, msg);
					}
				}
				*/
				stmd=null; //釋放掉			
				gstmd=null;
			}			
		}
		
		//若不是批次來的要做改變狀態(不能畢的)迴圈
		if(!session.getAttribute("mode").equals("graBatch")){
			for(int i=0; i<ready.size(); i++){
				Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+((Map)ready.get(i)).get("student_no")+"'").get(0);
				stmd.setOccurStatus("6");
				stmd.setOccurYear(Short.parseShort(schoolYear));
				stmd.setOccurTerm(schoolTerm);			
				stmd.setOccurDate(c.getTime());
				/*
				if(((Map)ready.get(i)).get("student_no")!=null){
					stmd.setIdentRemark(((Map)ready.get(i)).get("ident_remark").toString());
				}
				*/
				//System.out.println(stmd.getOccurTerm());			
				manager.updateObject(stmd);
				//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除 
				/*
				if(manager.testOnlineServer()){
					try {
						Toolket.sendStudentInfoByQueue(stmd, "U"); // 同步stmd
					} catch (Exception e) {
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ((Map) ready.get(i)).get("student_no")+ " 同步發生錯誤 , 請連絡電算中心!"));
						saveMessages(request, msg);
					}
				}
				*/
			}
		}else{
			//若是批次來的不必做直接離開
			removeSession(request);
			return query(mapping, form, request, response);
		}
		removeSession(request);		
		
		if(graList.size()-1<=end){			
			session.removeAttribute("begin");
			session.removeAttribute("end");
			//return mapping.findForward("Main");
		}else{
			//為彌平畢掉的人, 必須將畢業人數扣除
			session.setAttribute("begin", Integer.parseInt(session.getAttribute("begin").toString())+5-goNow.size());
			session.setAttribute("end", Integer.parseInt(session.getAttribute("end").toString())+5-goNow.size());			
		}
		return queryForNex(mapping, form, request,  response);
	}
	
	/**
	 * 清垃圾
	 * @param request
	 */
	private void removeSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		session.removeAttribute("allDtime");
		session.removeAttribute("graList");
		session.removeAttribute("goNow");
		session.removeAttribute("ready");
		session.removeAttribute("mode");
	}
	

	/**
	 * 清當學期相關資料
	 * @param studentNo 要畢業的人
	 * @param schoolyear 進歷史的學年
	 * @param schoolterm 學期
	 */
	//TODO 2007/9/20尚無任何獎懲記錄因此無法測試正確性
	private void delStuProperty(String studentNo, String schoolYear, String schoolTerm){
		CourseManager manager = (CourseManager) getBean("courseManager");		
		manager.executeSql("DELETE FROM AddDelCourseData WHERE Student_no='"+studentNo+"'"); //清加退
		
		//成績建歷史
		List myCs=manager.ezGetBy("SELECT d.depart_class, d.cscode, d.opt, d.credit, s.score " +//尋找本學期已選課程
				"FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND s.student_no='"+studentNo+"' AND d.Sterm='"+schoolTerm+"'");
		
		ScoreHist evgr=new ScoreHist();
		String stDepartClass=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'").toString();
		
		
		for(int i=0; i<myCs.size(); i++){
			try{
				evgr.setCredit(Float.parseFloat(((Map)myCs.get(i)).get("credit").toString()));
				evgr.setCscode((String)((Map)myCs.get(i)).get("cscode"));
				
				//evgrType: 2007/10/11 向註冊組確認非本班修得的課均為隨班附讀
				String csDepartClass=((Map)myCs.get(i)).get("depart_class").toString();
				if(stDepartClass.equals(csDepartClass)){
					evgr.setEvgrType("1");
				}else{
					evgr.setEvgrType("2");
				}
				evgr.setOpt(((Map)myCs.get(i)).get("opt").toString());
				evgr.setSchoolTerm(schoolTerm);
				evgr.setSchoolYear(Short.parseShort(schoolYear));
				if(((Map)myCs.get(i)).get("score")!=null){
					if(!((Map)myCs.get(i)).get("score").toString().trim().equals(""))
					evgr.setScore(Float.parseFloat(((Map)myCs.get(i)).get("score").toString()));
				}
				
				evgr.setStdepartClass(stDepartClass);
				evgr.setStudentNo(studentNo);
				
				if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE " +
						"school_year='"+evgr.getSchoolYear()+"' AND school_term='"+evgr.getSchoolTerm()+"' AND " +
						"cscode='"+evgr.getCscode()+"' AND student_no='"+evgr.getStudentNo()+"'")<1){//查本學期資料尚未建立歷史成績
					manager.updateObject(evgr);
				}else{
					evgr=null;//放掉
				}
			}catch(Exception e){
				
			}			
		}		
		
		manager.executeSql("DELETE FROM Seld WHERE student_no='"+studentNo+"'"); //清選課
		//manager.executeSql("DELETE FROM Regs WHERE student_no='"+studentNo+"'"); //清成績
		manager.executeSql("DELETE FROM wwpass WHERE username='"+studentNo+"'");//清帳號
		manager.executeSql("DELETE FROM Dilg WHERE student_no='"+studentNo+"'"); //清請假
		// 獎懲進歷史
		List desdList=manager.hqlGetBy("FROM Desd WHERE studentNo='"+studentNo+"'");
		for(int i=0; i<desdList.size(); i++){
			Desd desd=(Desd)desdList.get(i);
			Savedesd savedesd=new Savedesd();// 搬
			savedesd.setCnt1(desd.getCnt1());// 塞
			savedesd.setCnt2(desd.getCnt2());
			savedesd.setDdate(desd.getDdate());
			savedesd.setDepartClass(desd.getDepartClass());
			savedesd.setKind1(desd.getKind1());
			savedesd.setKind2(desd.getKind2());
			savedesd.setNo(desd.getNo());
			savedesd.setReason(desd.getReason());
			savedesd.setSchoolYear(Short.parseShort(schoolYear));
			savedesd.setSchoolTerm(Short.parseShort(schoolTerm));
			savedesd.setStudentNo(studentNo);
			manager.updateObject(savedesd); // 存
		}
		manager.executeSql("DELETE FROM desd WHERE student_no='"+studentNo+"'"); //刪
	}
	
	/**
	 * 回復
	 * @param stmd
	 * @return
	 */
	private Graduate chStmd(Student stmd){
		Graduate gstmd=new Graduate();
		gstmd.setBirthday(stmd.getBirthday());
		gstmd.setBirthCounty(stmd.getBirthCounty());
		gstmd.setBirthProvince(stmd.getBirthProvince());
		gstmd.setCellPhone(stmd.getCellPhone());
		gstmd.setCurrAddr(stmd.getCurrAddr());
		gstmd.setCurrPost(stmd.getCurrPost());
		gstmd.setDepartClass(stmd.getDepartClass());
		gstmd.setDivi(stmd.getDivi());
		gstmd.setEmail(stmd.getEmail());
		gstmd.setEntrance(stmd.getEntrance());
		gstmd.setExtraDept(stmd.getExtraDept());
		gstmd.setExtraStatus(stmd.getExtraStatus());
		gstmd.setGradDept(stmd.getGradDept());
		gstmd.setGraduStatus(stmd.getGraduStatus());
		gstmd.setGradyear(stmd.getGradyear());
		gstmd.setGradyearOrigin(stmd.getGradyearOrigin());
		gstmd.setIdent(stmd.getIdent());
		gstmd.setIdentRemark(stmd.getIdentRemark());
		gstmd.setIdno(stmd.getIdno());
		gstmd.setName(stmd.getName());
		gstmd.setOccurCause(stmd.getOccurCause());
		gstmd.setOccurDate(stmd.getOccurDate());
		gstmd.setOccurDocno(stmd.getOccurDocno());
		gstmd.setOccurGraduateNo(stmd.getOccurGraduateNo());
		gstmd.setOccurStatus(stmd.getOccurStatus());
		gstmd.setOccurTerm(stmd.getOccurTerm());
		gstmd.setOccurYear(stmd.getOccurYear());
		gstmd.setParentName(stmd.getParentName());
		gstmd.setPermAddr(stmd.getPermAddr());
		gstmd.setPermPost(stmd.getPermPost());
		gstmd.setSchlCode(stmd.getSchlCode());
		gstmd.setSchlName(stmd.getSchlName());
		gstmd.setSex(stmd.getSex());
		gstmd.setStudentEname(stmd.getStudentEname());
		gstmd.setStudentName(stmd.getStudentName());
		gstmd.setStudentNo(stmd.getStudentNo());
		gstmd.setTelephone(stmd.getTelephone());
		
		return gstmd;
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		map.put("Cancel", "cancel");
		map.put("Continue", "Continue");
		map.put("Complete", "complete");
		map.put("CheckGraduate", "checkGraduate");
		return map;
	}
	
	private void saveExNote(String exNote[], String exSHOid[], String exSHNOid[]){
		CourseManager manager = (CourseManager) getBean("courseManager");
		ScoreHistNote shn;
		for(int i=0; i<exSHOid.length; i++){			
			//若有note
			if(!exNote[i].equals("")){
				//若已存在紀錄
				if(!exSHNOid[i].equals("")){
					//查一條ScoreHistNote出來改
					//System.out.println("FROM ScoreHistNote WHERE Oid='"+exSHNOid+"'");
					shn=(ScoreHistNote)manager.hqlGetBy("FROM ScoreHistNote WHERE Oid='"+exSHNOid[i]+"'").get(0);
					shn.setNote(exNote[i]);					
					
				}else{//若原本沒有就新一條記錄
					shn=new ScoreHistNote();
					shn.setNote(exNote[i]);
					shn.setScoreHistOid(Integer.parseInt(exSHOid[i]));
				}
				manager.updateObject(shn);
			}else{//若沒有note
				//若已存在紀錄
				if(!exSHNOid[i].equals("")){//立即刪除
					manager.executeSql("DELETE fROM ScoreHistNote WHERE Oid='"+exSHNOid[i]+"'");			
					//System.out.println("DELETE fROM ScoreHistNote WHERE Oid='"+exSHNOid[i]+"'");
				}
			}
		}		
	}
	
	/**
	 * 應屆畢業
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkGraduate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		/*
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//DynaActionForm chForm = (DynaActionForm) form;		
		
		List graList=(List)session.getAttribute("graList");
		String schoolYear=manager.getSchoolYear().toString();
		String schoolTerm=manager.getSchoolTerm().toString();
		String studentNo;
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		
		if(schoolTerm.equals("1")){			
			c.set(Calendar.MONTH, 7);
			c.set(Calendar.DAY_OF_MONTH, 30);
		}else{
			c.set(Calendar.MONTH, 2);
			c.set(Calendar.DAY_OF_MONTH, 31);
		}
		
		Student stmd;
		Graduate gstmd;
		for(int i=0; i<graList.size(); i++){			
			
			studentNo=((Map)graList.get(i)).get("student_no").toString();
			delStuProperty(studentNo, schoolYear, schoolTerm);
			
			stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+studentNo+"'").get(0);
			gstmd=chStmd(stmd);
			gstmd.setOccurDate(c.getTime());
			gstmd.setOccurYear(Short.parseShort(schoolYear));
			gstmd.setOccurTerm(schoolTerm);
			gstmd.setOccurGraduateNo(studentNo);
			gstmd.setOccurStatus("6");			
			
			manager.updateObject(gstmd);
			manager.executeSql("DELETE FROM stmd WHERE student_no='"+studentNo+"'");//幹掉
			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除 
			if(manager.testOnlineServer()){
				try {
					Toolket.sendStudentInfoByQueue(stmd, "D"); // 同步stmd
					Toolket.sendGraduateInfoByQueue(gstmd, "I");
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", studentNo
									+ " 同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}
			}
			stmd=null; //釋放掉			
			gstmd=null;			
		}
		*/		
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		List graList=(List)session.getAttribute("graList");
		
		List ready=new ArrayList();
		List goNow=new ArrayList();
		
		for(int i=0; i<graList.size(); i++){
			//有勾選的人才做
			if(((Map)graList.get(i)).get("occur_status")!=null)
			if(((Map)graList.get(i)).get("occur_status").equals("6")){				
				((Map)graList.get(i)).put("ClassName", "<img src='images/tux.gif' />");
				((Map)graList.get(i)).put("occur_graduate_no", ((Map)graList.get(i)).get("student_no"));
				goNow.add(((Map)graList.get(i)));
						
			}else{
				((Map)graList.get(i)).put("ClassName", "<img src='images/tu.png' />");
				ready.add(((Map)graList.get(i)));		
			}else{
				((Map)graList.get(i)).put("student_name", ((Map)graList.get(i)).get("student_name")+"(無畢業資格)");
				((Map)graList.get(i)).put("ClassName", "<img src='images/tu.png' />");
				ready.add(((Map)graList.get(i)));
			}
		}
		
		session.setAttribute("goNow", goNow);
		session.setAttribute("ready", ready);
		session.setAttribute("mode", "graBatch");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共計 "+goNow.size()+"位同學立即進入畢業檔"));
		saveMessages(request, msg);
		
		return mapping.findForward("Main");
	}	

}
