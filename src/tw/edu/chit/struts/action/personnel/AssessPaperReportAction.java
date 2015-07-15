package tw.edu.chit.struts.action.personnel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
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

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.HibernateQueryResultDataSource;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class AssessPaperReportAction  extends BaseLookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",			"print");
		map.put("Cancel", 			"cancel");
		return map;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		UserCredential user = (UserCredential)session.getAttribute("Credential");

		String idno = user.getMember().getIdno();
		CodeEmpl allUnit = new CodeEmpl();
		allUnit.setIdno("0");
		allUnit.setName("全校");
		allUnit.setSequence((short)1);
		List<CodeEmpl> units = mm.getEmplUnits();
		CodeEmpl unit = new CodeEmpl();
		for(Iterator<CodeEmpl> unitIter = units.iterator(); unitIter.hasNext();){
			unit= unitIter.next();
			if(unit.getIdno().equals("17") || unit.getIdno().equals("01")){
				//remove 17:董事會 , 01:校長室
				unitIter.remove();
			}
		}
		units.add(0, allUnit);
		
		session.setAttribute("emplUnits", units);
		session.removeAttribute("AssessPaperReport");
		session.removeAttribute("assessReport");
		session.removeAttribute("assessReportType");
		session.removeAttribute("assessReportsdate");
		session.removeAttribute("assessReportedate");
		
		setContentPage(session, "personnel/AssessPaperReport.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException, ServletException{
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairDAO dao = (StudAffairDAO) getBean("studAffairDAO");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential)session.getAttribute("Credential");

		String reportType = aForm.getString("reportType").trim();	//1:統計， 2:優劣
		String unit = aForm.getString("unit").trim();
		String sdate = aForm.getString("sdate").trim();
		String edate = aForm.getString("edate").trim();
		boolean setupPrinter = false;
		boolean isPrint = false;
		
		ActionMessages messages = new ActionMessages();
		
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		if(sdate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("messageN1", "請輸入起始日期！"));
		}else{
			sCal.setTime(Toolket.parseFullDate(sdate));
		}
		if(edate.equals("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("messageN1", "請輸入結束日期！"));
		}else{
			eCal.setTime(Toolket.parseFullDate(edate));
		}
		if(!(sdate.equals("") || edate.equals(""))){
			if(sCal.compareTo(eCal) > 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("messageN1", "起始日期必須小於結束日期！"));
			}
		}
		
		session.setAttribute("AssessPaperReport", aForm.getMap());
		if(messages.isEmpty()){
			List reports = new ArrayList();
			reports = mm.getAssessPaperReportbyForm(aForm);
			
			if(reportType.equals("1")){
				DecimalFormat df = new DecimalFormat(",###.00");
				int total = 0, replied = 0, totalScrs = 0;
				float avg = 0f;
				int[][] scrReplis = {{0,0},{0,0},{0,0},{0,0},{0,0}};	//統計教職員、學生各種滿意度的回覆人次
				int[] scrRepli, scrs;
				for(Iterator paperIter=reports.iterator(); paperIter.hasNext();){
					Map paper = (Map)paperIter.next();
					
					if(!paper.get("total").toString().equals("")){
						total += Float.parseFloat(paper.get("total").toString());
						avg += Float.parseFloat(paper.get("average").toString());
						replied++;
					}
					
					//加總教職員、學生、不明人士各種滿意度的回覆人次
					scrRepli = (int[])paper.get("scrRepli");
					scrs = (int[])paper.get("scores");
					for(int i=0; i<5; i++){
						scrReplis[i][0] += scrRepli[i];
						scrReplis[i][1] += scrs[i] - scrRepli[i];
						totalScrs += scrs[i];
					}
				}
				Map reportSum = new HashMap();
				reportSum.put("totalEmp", reports.size());
				reportSum.put("totalSum", Math.round(total));
				reportSum.put("totalScrs", totalScrs);
				reportSum.put("avgSum", df.format(Float.parseFloat("" + total)/totalScrs));
				reportSum.put("replied", replied);
				reportSum.put("scoreRepli", scrReplis);
				session.setAttribute("assessReportSum", reportSum);
			}
			
			session.setAttribute("assessReportType", reportType);
			session.setAttribute("assessReportsdate", sdate);
			session.setAttribute("assessReportedate", edate);
			session.setAttribute("assessReport", reports);
		}else{
			saveErrors(request, messages);
		}
		
		setContentPage(request, "personnel/AssessPaperReport.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		HttpSession session = request.getSession(false);
		session.removeAttribute("AssessPaperList");
		session.removeAttribute("AssessPaperTotal");
		session.removeAttribute("AssessPaperAvg");
		return mapping.findForward("back");
		
	}
	
}
