package tw.edu.chit.struts.action.studaffair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.HibernateQueryResultDataSource;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class TimeOffSummaryExtAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query","query");
		//map.put("Cancel", "cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		session.removeAttribute("TimeOffSummaryExt");
		setContentPage(session, "studaffair/TimeOffSummaryExt.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List tfList = new ArrayList();
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInCharge");
		String school = aForm.getString("schoolInCharge");
		String dept = aForm.getString("deptInCharge");
		String departClass  = aForm.getString("classInCharge");
		//String WeekNo  = aForm.getString("WeekNo");
		//String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
		String period  = aForm.getString("period");
		String qscope  = aForm.getString("qscope");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		//String printOpt = (String) aForm.get("printOpt");
		//boolean setupPrinter = "on".equalsIgnoreCase(aForm
		//		.getString("setupPrinter"));
		
		//if ("All".equals(campus)||"All".equals(school)) {
		if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大!"));
		}
		/*
		if(!Toolket.isNumeric(WeekNo)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字!"));
		}
		if(!Toolket.isValidDate(DateStart)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "起始日期輸入錯誤!"));
		}
		*/
		if(!Toolket.isValidDate(DateEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "結束日期輸入錯誤!"));
		}
			
		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			UserCredential credential = getUserCredential(session);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			String clazzFilter = "";
			
			if(departClass.equalsIgnoreCase("All")){
				if("All".equals(dept)) clazzFilter = campus + school;
				else clazzFilter = campus + school + dept;
			}else if("All".equals(dept)){
				clazzFilter = campus + school;
			}else{
				clazzFilter = departClass;
			}
			
			tfList = sm.findTimeOffSummaryExt(clazzFilter, DateEnd, 
					period, qscope);
		}
		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		//initMap.put("WeekNo", WeekNo);
		//initMap.put("DateStart", DateStart);
		initMap.put("DateEnd", DateEnd);
		initMap.put("period", period);
		initMap.put("qscope", qscope);
		session.setAttribute("TFSummaryExtInit", initMap);
		session.setAttribute("TimeOffSummaryExt", tfList);
		
		setContentPage(request.getSession(false), "studaffair/TimeOffSummaryExt.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
