package tw.edu.chit.struts.action.studaffair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.CounselingCode;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.student.AjaxGetStudentTimeOff;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class CounselingReportAction  extends BaseLookupDispatchAction{

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("PreviewPrint", "print");
		map.put("Query", "query");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterT();
		String idenType = aForm.getString("idenType").trim();
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		MemberManager mm = (MemberManager)getBean("memberManager");
		
		Map<String, String> counselType = new HashMap<String, String>();
		counselType.put("L", "學習輔導");
		counselType.put("T", "職涯輔導");
		
		String schoolYear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		String schoolTerm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		Map<String, String> CounselingReportInit = new HashMap<String, String>();
		CounselingReportInit.put("idenType", idenType);
		CounselingReportInit.put("schoolYear", schoolYear);
		CounselingReportInit.put("schoolTerm", schoolTerm);
		
		List<CounselingCode> codesL = sm.findCounselingCode("L");
		List<CounselingCode> codesT = sm.findCounselingCode("T");
		
		session.setAttribute("StudCounselCodeL", codesL);
		session.setAttribute("StudCounselCodeT", codesT);
		session.setAttribute("StudCounselType", counselType);
		session.setAttribute("CounselingReportInit", CounselingReportInit);
		
		String[][] repType = new String[][]{
				{"1", "學習輔導記錄(導師)"},
				{"2", "職涯輔導記錄(導師)"},
				{"3", "學習輔導記錄(老師)"},
				{"4", "學生接受輔導記錄"},
				{"5", "輔導次數統計(導師)"},
				{"6", "輔導次數統計(老師)"}
		};
		session.setAttribute("CounselReportType", repType);
		
		List<Map> depts = Toolket.getCollegeDepartment(true);
		session.setAttribute("depts", depts);

		/*
		if(idenType.equals("T")){	//Tutor
			setContentPage(session, "studaffair/CounselingReportT.jsp");			
		}else if(idenType.equals("L")){		//Teacher
			setContentPage(session, "studaffair/CounselingReportL.jsp");			
		}else if(idenType.equals("C")){		//department chief or assistance
			setContentPage(session, "studaffair/CounselingReportC.jsp");			
		}else if(idenType.equals("A")){		//student affair officer
			setContentPage(session, "studaffair/CounselingReportA.jsp");			
		}else if(idenType.equals("S")){		//execution manager
			//int empOid = credential.getMember().getOid();
			List<Empl> empls = mm.findEmplByGroup("S1");
			for(Empl empl:empls){
				if(empl.getIdno().equalsIgnoreCase(credential.getMember().getIdno())){
					setContentPage(session, "studaffair/CounselingReportS.jsp");
					break;
				}
			}
						
		}
		*/
		session.removeAttribute("CounselingReoport");
		setContentPage(session, "studaffair/CounselingReport.jsp");
		return mapping.findForward("Main");
	}
		
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Toolket.resetCheckboxCookie(response, "CounselingsL");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		MemberManager mm = (MemberManager)getBean("memberManager");
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String schoolYear = aForm.getString("schoolYear").trim();
		String schoolTerm = aForm.getString("schoolTerm").trim();
		String depart = aForm.getString("department").trim();
		String idenType = aForm.getString("idenType").trim();
		String reportType = aForm.getString("reportType").trim();
		
		String campusInCharge = "";
		String schoolInCharge = "";
		String deptInCharge = "";
		String departClass = "";
		if(idenType.equalsIgnoreCase("C")){
			campusInCharge = aForm.getString("campusInChargeC").trim();
			schoolInCharge = aForm.getString("schoolInChargeC").trim();
			deptInCharge = aForm.getString("deptInChargeC").trim();
			departClass = aForm.getString("classInChargeC").trim();
		}else if(idenType.equalsIgnoreCase("A")){
			campusInCharge = aForm.getString("campusInChargeSAF").trim();
			schoolInCharge = aForm.getString("schoolInChargeSAF").trim();
			deptInCharge = aForm.getString("deptInChargeSAF").trim();
			departClass = aForm.getString("classInChargeSAF").trim();
		}else if(idenType.equalsIgnoreCase("S")){
			campusInCharge = aForm.getString("campusInCharge").trim();
			schoolInCharge = aForm.getString("schoolInCharge").trim();
			deptInCharge = aForm.getString("deptInCharge").trim();
			departClass = aForm.getString("classInCharge").trim();
		}

		//String studentNo  = aForm.getString("studentNo").trim();
		
		Map<String, String> CounselingReportInit = (Map<String, String>)(session.getAttribute("CounselingReportInit"));
		CounselingReportInit.put("schoolYear", schoolYear);
		CounselingReportInit.put("schoolTerm", schoolTerm);
		CounselingReportInit.put("campusInCharge", campusInCharge);
		CounselingReportInit.put("schoolInCharge", schoolInCharge);
		CounselingReportInit.put("deptInCharge", deptInCharge);
		CounselingReportInit.put("classInCharge", departClass);
		CounselingReportInit.put("reportType", reportType);
		
		ActionMessages messages = new ActionMessages();
		
		if(idenType.equalsIgnoreCase("C") || idenType.equalsIgnoreCase("A") ||
				idenType.equalsIgnoreCase("S")){
			if(schoolInCharge.equalsIgnoreCase("All")){
				if(campusInCharge.equalsIgnoreCase("All")){
					departClass = "";
				}else{
					departClass = campusInCharge;
				}
			}else if(deptInCharge.equalsIgnoreCase("All")){
				departClass = campusInCharge + schoolInCharge;
			}else if(departClass.equalsIgnoreCase("All")){
				departClass = campusInCharge + schoolInCharge + deptInCharge;
			}
		}
		if(!depart.equals("")){
			departClass = "___" + depart + "__";
		}
		
		if(schoolYear.equals("") || schoolTerm.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1","請輸入學年及學期!"));
			saveMessages(request, messages);
			setContentPage(session, "studaffair/CounselingReport.jsp");
			return mapping.findForward("Main");
		}
		
		List counselingList = new ArrayList();
		
		/*
		if(!studentNo.equals("")) {
			departClass = "";
		}
		*/
		
		/*
		 * "1":學習輔導記錄(導師)
		   "2":職涯輔導記錄(導師)
		   "3":學習輔導記錄(老師)
		   "4":學生接受輔導記錄
		   "5":輔導次數統計(導師)
		   "6":輔導次數統計(老師)

		 */
		char iden = idenType.charAt(0);
		if(reportType.equalsIgnoreCase("1")){
			switch(iden){
			case 'T':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "U", credential.getMember().getIdno(), "", departClass, null, null);
				break;
			case 'C':
			case 'A':
			case 'S':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "U", "", "", departClass, null, null);
				break;
			case 'L':
				
			}
		}else if(reportType.equalsIgnoreCase("2")){
			switch(iden){
			case 'T':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "T", credential.getMember().getIdno(), "", departClass, null, null);
				break;
			case 'C':
			case 'A':
			case 'S':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "T", "", "", departClass, null, null);
				break;
			case 'L':
				
			}
		}else if(reportType.equalsIgnoreCase("3")){
			switch(iden){
			case 'L':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "L", credential.getMember().getIdno(), "", departClass, null, null);
				break;
			case 'C':
			case 'A':
			case 'S':
				counselingList = sm.findCounselingByInput(schoolYear, schoolTerm, "L", "", "", departClass, null, null);
				break;
			case 'T':
				
			}
		}else if(reportType.equalsIgnoreCase("4")){
			//學生接受輔導記錄
			switch(iden){
			case 'C':
			case 'A':
			case 'S':
				List<StudCounseling> counselsT = sm.findCounselingByInput(schoolYear, schoolTerm, "T", "", "", departClass, null, null);
				List<StudCounseling> counselsU = sm.findCounselingByInput(schoolYear, schoolTerm, "U", "", "", departClass, null, null);
				List<StudCounseling> counselsL = sm.findCounselingByInput(schoolYear, schoolTerm, "L", "", "", departClass, null, null);
				counselingList.addAll(counselsT);
				counselingList.addAll(counselsU);
				counselingList.addAll(counselsL);
				Collections.sort(counselingList, new counselComp());
				break;
			case 'L':
			case 'T':
				
			}
		}else if(reportType.equalsIgnoreCase("5")){
			//輔導次數統計(導師)
			switch(iden){
			case 'C':
			case 'A':
			case 'S':
				counselingList = sm.findCounselingReport(schoolYear, schoolTerm, "T", departClass, "");
				int countT = 0, countU = 0, countStudent_UT=0;
				for(Object counsel:counselingList){
					countT += Integer.parseInt(((Map)counsel).get("countT").toString());
					countU += Integer.parseInt(((Map)counsel).get("countU").toString());
					countStudent_UT += Integer.parseInt(((Map)counsel).get("countStudent_UT").toString());
				}
				CounselingReportInit.put("countT", "" + countT);
				CounselingReportInit.put("countU", "" + countU);
				CounselingReportInit.put("countStudent_UT", "" + countStudent_UT);				
				CounselingReportInit.put("total", "" + (countT + countU));
				
				break;
			case 'L':
			case 'T':
				System.out.println(schoolYear);
				counselingList = sm.findCounselingReport(schoolYear, schoolTerm, "T", "", credential.getMember().getIdno());
				int cntT = 0, cntU = 0, countStudentUT=0;
				for(Object counsel:counselingList){
					cntT += Integer.parseInt(((Map)counsel).get("countT").toString());
					cntU += Integer.parseInt(((Map)counsel).get("countU").toString());
					countStudentUT += Integer.parseInt(((Map)counsel).get("countStudent_UT").toString());
				}
				CounselingReportInit.put("countT", "" + cntT);
				CounselingReportInit.put("countU", "" + cntU);
				CounselingReportInit.put("countStudent_UT", "" + countStudentUT);	
				CounselingReportInit.put("total", "" + (cntT + cntU));
				
				break;
			}
		}else if(reportType.equalsIgnoreCase("6")){
			//輔導次數統計(老師)
			//依照開課班級,老師,科目排序,一個老師可能教授多個科目
			switch(iden){
			case 'C':
			case 'A':
			case 'S':
				counselingList = sm.findCounselingReport(schoolYear, schoolTerm, "L", departClass, "");
				int countL = 0, countL_UT=0;
				for(Object counsel:counselingList){
					countL += Integer.parseInt(((Map)counsel).get("countL").toString());
					countL_UT += Integer.parseInt(((Map)counsel).get("countL_UT").toString());
				}
				CounselingReportInit.put("countL", "" + countL);
				CounselingReportInit.put("countL_UT", "" + countL_UT);
				break;
			case 'L':
				counselingList = sm.findCounselingReport(schoolYear, schoolTerm, "L", "", credential.getMember().getIdno());
				int cntL = 0, countLUT=0;
				for(Object counsel:counselingList){
					cntL += Integer.parseInt(((Map)counsel).get("countL").toString());
					countLUT += Integer.parseInt(((Map)counsel).get("countL_UT").toString());
				}
				CounselingReportInit.put("countL", "" + cntL);
				CounselingReportInit.put("countL_UT", "" + countLUT);
				break;
			case 'T':
				
			}
		}
		
		//session.setAttribute("StudCounselingInit", StudCounselingInit);
		session.setAttribute("CounselingReportInit", CounselingReportInit);
				
		session.setAttribute("CounselingReoport", counselingList);
		setContentPage(session, "studaffair/CounselingReport.jsp");
		return mapping.findForward("Main");
	}

	private class counselComp implements Comparator {
		public int compare(Object obj1, Object obj2){
			if(obj1 instanceof StudCounseling && obj2 instanceof StudCounseling){
				String stud1 = ((StudCounseling)obj1).getStudentNo();
				String stud2 = ((StudCounseling)obj2).getStudentNo();
				
				return stud1.compareToIgnoreCase(stud2);
			}
			return 0;
		}
		
		public boolean equals(Object obj){
			return super.equals(obj);
		}
	}


}
