package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.TimeOffUpload;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class TimeOffNotUploadQueryAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK", "query");
		map.put("Back", "cancel");
		return map;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String user_id = credential.getMember().getIdno().trim();
		String method = dynForm.getString("method");
		
		Map TimeoffInit = new HashMap();

		if(session.getAttribute("TFnotUploadQueryInit")!=null && method.equals("")){
			TimeoffInit = (Map) session.getAttribute("TFnotUploadQueryInit");
		}else{
			Calendar now = Calendar.getInstance();
			//now.add(Calendar.DATE, -8);
			//DateFormat df = new SimpleDateFormat("yy-mm-dd");
			//String nows = df.format(now.getTime());
			String nows = "" + (now.get(Calendar.YEAR)-1911) + "/";
			nows += now.get(Calendar.MONTH)>=9?(now.get(Calendar.MONTH) + 1):"0" + (now.get(Calendar.MONTH) + 1);
			nows += "/";
			nows += now.get(Calendar.DATE)>9?(now.get(Calendar.DATE)):"0" + (now.get(Calendar.DATE));

			TimeoffInit.put("DateStart", nows);			
			TimeoffInit.put("DateEnd", nows);			

			String classInCharge = credential.getClassInChargeSqlFilterSAF();

			String campus = dynForm.getString("campusInChargeSAF").trim();
			String school = dynForm.getString("schoolInChargeSAF").trim();
			String dept = dynForm.getString("deptInChargeSAF").trim();
			String myclass = dynForm.getString("classInChargeSAF").trim();
			
			TimeoffInit.put("campus", campus);
			TimeoffInit.put("school", school);
			TimeoffInit.put("dept", dept);
			TimeoffInit.put("clazz", myclass);		
		}
		
		session.removeAttribute("TFnotUpload");
		session.setAttribute("TFnotUploadQueryInit", TimeoffInit);
		//if(IConstants.TimeOffPatchUsers.indexOf(user_id) >= 0){
			session.setAttribute("allowPatch", "yes");
		//}
		setContentPage(session, "studaffair/TimeOffNotUploadQuery.jsp");
		return mapping.findForward("Main");

	}
		
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		ActionMessages msgs = new ActionMessages();
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		Map TimeoffInit = new HashMap();

		String DateStart = dynForm.getString("DateStart").trim();
		String DateEnd = dynForm.getString("DateEnd").trim();
		String campus = dynForm.getString("campusInChargeSAF").trim();
		String school = dynForm.getString("schoolInChargeSAF").trim();
		String dept = dynForm.getString("deptInChargeSAF").trim();
		String departClass = dynForm.getString("classInChargeSAF").trim();
		String teacherId = dynForm.getString("teacherId").trim();
		String pmode = dynForm.getString("pmode").trim();	//1:明細表, 2:統計表
		
		//TimeoffInit.put("teachId", teachId);
		TimeoffInit.put("campus", campus);
		TimeoffInit.put("school", school);
		TimeoffInit.put("dept", dept);
		TimeoffInit.put("clazz", departClass);		
		TimeoffInit.put("DateStart", DateStart);			
		TimeoffInit.put("DateEnd", DateEnd);						
		TimeoffInit.put("pmode", pmode);						
		TimeoffInit.put("teacherId", teacherId);						
		
		session.setAttribute("TFnotUploadQueryInit", TimeoffInit);
		
		if(!Toolket.isValidDate(DateStart) || !Toolket.isValidDate(DateEnd)){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "輸入的日期格式不正確!"));
		}
		if(teacherId.equals("") && ("All".equals(campus)||"All".equals(school))) {
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
		if(!msgs.isEmpty()){
			saveErrors(request, msgs);
			return mapping.findForward("Main");
		}
		
		String clazzFilter = "";
		Calendar[] termStart = null;
		Calendar[] termEnd = null;
		Calendar mystart = null;
		Calendar myend = null;
		
		mystart = Calendar.getInstance();
		mystart.setTime(Toolket.parseDate(DateStart.replace('/', '-')));
		myend = Calendar.getInstance();
		myend.setTime(Toolket.parseDate(DateEnd.replace('/', '-')));
		
		if(!("All".equals(campus)||"All".equals(school))){
			if(departClass.equalsIgnoreCase("All")){
				if("All".equals(dept)) clazzFilter = campus + school;
				else clazzFilter = campus + school + dept;
			}else if("All".equals(dept)){
				clazzFilter = campus + school;
			}else{
				clazzFilter = departClass;
			}
		}
		
		if(teacherId.equals("")){
			String hdept = campus + school.substring(0, 1);
			termStart = Toolket.getDateOfWeek(hdept , 1);
			//TODO:check !
			termEnd = Toolket.getDateOfWeek(hdept , Toolket.getTimeOffUploadDeadline());
			
			
			if(mystart.compareTo(termStart[0])<0){
				mystart.setTimeInMillis(termStart[0].getTimeInMillis());
			}
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DATE, -1);
			if(myend.compareTo(termEnd[1])>0){
				myend.setTimeInMillis(termEnd[1].getTimeInMillis());
			}else if(myend.compareTo(Calendar.getInstance())>0){
				myend.setTimeInMillis(yesterday.getTimeInMillis());
			}
		}
				
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		List<Map> tfnuList = new ArrayList<Map>();
		
		List<Map> tfups = new ArrayList<Map>();
		tfups = sm.getTeacherTimeOffNotUpload(clazzFilter, teacherId, mystart, myend, pmode, "0");
		if(pmode.equals("1")){
			String email = "";
			String teacherName = "";
			String teacherIdno = "";
			String memo = "";
			String teachDate = "";
			String teachWeek = "";
			for(Map notupMap:tfups){
				List<Map> notupList = (List<Map>)notupMap.get("notUploadList");
				//System.out.println("notupList"+notupList);
				teacherName = notupMap.get("teacherName").toString();
				teacherIdno = notupMap.get("teacherId").toString();
				teachDate = notupMap.get("teachDate").toString();
				teachWeek = notupMap.get("teachWeek").toString();
				
				if(notupMap.get("email") != null){
					email = notupMap.get("email").toString();
				}else{
					email = "";
				}

				if(notupMap.get("email") == null){
					memo= "沒有E-mail";
				}else if(!Toolket.isValidEmail(email)){
					memo = "無效的E-mail";
				}else{
					memo = "";
				}
				
				for(Map ntMap:notupList){
					Map tfMap = new HashMap();
					tfMap.put("teacherName", teacherName);
					tfMap.put("teacherId", teacherIdno);
					tfMap.put("email", email);
					tfMap.put("memo", memo);
					tfMap.put("teachDate", teachDate);
					tfMap.put("teachWeek", teachWeek);
					tfMap.putAll(ntMap);
					tfnuList.add(tfMap);
				}
				
			}
			session.setAttribute("TFnotUploadList", tfnuList);
		}else{
			//nothing to do !
			session.setAttribute("TFnotUploadList", tfups);
		}
		
		
		setContentPage(session, "studaffair/TimeOffNotUploadQuery.jsp");
		return mapping.findForward("Main");

	}
			
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("TFnotUploadList");
		setContentPage(session, "studaffair/TimeOffNotUploadQuery.jsp");
		return mapping.findForward("Main");
	}
	
}
