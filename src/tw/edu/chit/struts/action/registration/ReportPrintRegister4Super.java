package tw.edu.chit.struts.action.registration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.dao.StudAffairJdbcDAO;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ReportPrintRegister4Super extends BaseAction {
	/**
	 * 未啟動註冊資料統計表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @param sterm 學期
	 */
	@SuppressWarnings("unchecked")
		public ActionForward execute(ActionMapping mapping, ActionForm form, 
		HttpServletRequest request, HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);	

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairJdbcDAO dao = (StudAffairJdbcDAO)getBean("studAffairJdbcDAO");
		ServletContext context = request.getSession().getServletContext();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String newReg = request.getParameter("nr");
		boolean fillRegCard = true;
		
		if(newReg.equals("0")){
			fillRegCard = false;
		}
		
		int totalAll = 0;	//全校應註冊總人數(舊生)
		int total1D = 0;	//台北日間應註冊總人數(舊生)
		int total1N = 0;
		int total1H = 0;
		int total2A = 0;
		int noRegAll = 0, noReg1D = 0, noReg1N = 0, noReg1H = 0, noReg2A = 0;
		int newAll = 0, new1D = 0, new1N = 0, new1H = 0, new2A = 0;
		int newnoAll = 0, newno1D = 0, newno1N = 0, newno1H = 0, newno2A = 0;
		
		String subsql = " AND (TuitionAmount IS NULL OR TuitionAmount = 0) "
			+ "AND (AgencyAmount IS NULL OR AgencyAmount = 0) "
			+ "AND (ReliefTuitionAmount IS NULL OR ReliefTuitionAmount = 0) "
			+ "AND (LoanAmount IS NULL OR LoanAmount = 0) "
			+ "AND (VulnerableAmount IS NULL OR VulnerableAmount = 0) ";

		String subsql2 = subsql + " AND NewStudentReg IS NULL";
			
		
		totalAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='O'");
		total1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='O'");
		total1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='O'");
		total1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='O' ");
		total2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='O'");
		
		noRegAll = dao.getRecordsCount("Select count(*) From Register Where  SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='O'"
				+ subsql);
		
		noReg1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='O' " + subsql);
		noReg1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='O' " + subsql);
		noReg1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='O' " + subsql);
		noReg2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='O'" + subsql);
		
		newAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='N'");
		new1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='N'");
		new1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='N'");
		new1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='N'");
		new2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='N'");
		
		newnoAll = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + "' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1D = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
				"' AND CampusCode='1' AND SchoolType='D' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1N = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='N' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno1H = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='1' AND SchoolType='H' And Type='N'" + (fillRegCard?subsql2:subsql));
		newno2A = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
		"' AND CampusCode='2' And Type='N'" + (fillRegCard?subsql2:subsql));
		
		totalAll += newAll;
		total1D += new1D;
		total1N += new1N;
		total1H += new1H;
		total2A += new2A;
		noRegAll += newnoAll;
		noReg1D += newno1D;
		noReg1N += newno1N;
		noReg1H += newno1H;
		noReg2A += newno2A;
		
		List<Map> depts = Toolket.getCollegeDepartment(false);
		int deptAll[] = new int[depts.size() + 1];
		int deptNo[] = new int[depts.size() + 1];
		int idx = 0, depnewNo = 0, depoldNo = 0 ;
		for(Map dept:depts){
			idx = depts.indexOf(dept);
			deptAll[idx] = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__'");
			depnewNo = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__' And Type='N' " + (fillRegCard?subsql2:subsql));
			depoldNo = dao.getRecordsCount("Select count(*) From Register Where SchoolYear='" + year + "' And SchoolTerm='" + sterm + 
					"' And VirClassNo like '___" + dept.get("idno").toString() + "__' And Type='O'" + subsql);
			deptNo[idx] = depnewNo + depoldNo;
		}
		
		File templateXLS = new File(context
				.getRealPath("/WEB-INF/reports/RegisterReportListSum.xls"));
		HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
		HSSFFont fontSize10 = workbook.createFont();
		fontSize10.setFontHeightInPoints((short) 10);
		fontSize10.setFontName("Arial Unicode MS");

		HSSFSheet sheet = workbook.getSheetAt(0);

		Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + year + "學年度第" + sterm
				+ "學期未啟動註冊程序統計表");


		NumberFormat nf = new DecimalFormat("###0.00%");
		Map rep = new HashMap();
		rep.put("schoolYear", year);
		rep.put("schoolTerm", sterm);
		rep.put("total1D", total1D);
		rep.put("noReg1D", noReg1D);
		rep.put("reg1D", total1D - noReg1D);
		rep.put("noRegPcn1D", nf.format((noReg1D/Double.parseDouble("" + total1D))));
		
		rep.put("new1D", new1D);
		rep.put("newno1D", newno1D);
		rep.put("newReg1D", new1D - newno1D);
		rep.put("newNoRegPcn1D", nf.format((newno1D/Double.parseDouble("" + new1D))));
		
		rep.put("total1N", total1N);
		rep.put("noReg1N", noReg1N);
		rep.put("reg1N", total1N - noReg1N);
		rep.put("noRegPcn1N", nf.format((noReg1N/Double.parseDouble("" + total1N))));
		
		rep.put("new1N", new1N);
		rep.put("newno1N", newno1N);
		rep.put("newReg1N", new1N - newno1N);
		rep.put("newNoRegPcn1N", nf.format((newno1N/Double.parseDouble("" + new1N))));
		
		rep.put("total1H", total1H);
		rep.put("noReg1H", noReg1H);
		rep.put("reg1H", total1H - noReg1H);
		rep.put("noRegPcn1H", nf.format((noReg1H/Double.parseDouble("" + total1H))));
		
		rep.put("new1H", new1H);
		rep.put("newno1H", newno1H);
		rep.put("newReg1H", new1H - newno1H);
		rep.put("newNoRegPcn1H", nf.format((newno1H/Double.parseDouble("" + new1H))));
		
		rep.put("total2A", total2A);
		rep.put("noReg2A", noReg2A);
		rep.put("reg2A", total2A - noReg2A);
		rep.put("noRegPcn2A", nf.format((noReg2A/Double.parseDouble("" + total2A))));
		
		rep.put("new2A", new2A);
		rep.put("newno2A", newno2A);
		rep.put("newReg2A", new2A - newno2A);
		rep.put("newNoRegPcn2A", nf.format((newno2A/Double.parseDouble("" + new2A))));
		
		rep.put("totalAll", totalAll);
		rep.put("noRegAll", noRegAll);
		rep.put("regAll", totalAll - noRegAll);
		rep.put("noRegPcnAll", nf.format((noRegAll/Double.parseDouble("" + totalAll))));
		
		rep.put("newAll", newAll);
		rep.put("newnoAll", newnoAll);
		rep.put("newRegAll", newAll - newnoAll);
		rep.put("newNoRegPcnAll", nf.format((newnoAll/Double.parseDouble("" + newAll))));
		
		List<Map> deptRegs = new ArrayList<Map>();
		idx = 0;
		int sum = depts.size();
		for(Map dept:depts){
			idx = depts.indexOf(dept);
			Map dep = new HashMap();
			dep.put("name", dept.get("name").toString());
			dep.put("totalAll", deptAll[idx]);
			dep.put("noRegAll", deptNo[idx]);
			dep.put("regAll", (deptAll[idx] - deptNo[idx]));
			
			
			if(deptAll[idx] > 0)
				dep.put("noRegPcnAll", nf.format((deptNo[idx]/Double.parseDouble("" + deptAll[idx]))));
			else
				dep.put("noRegPcnAll", "0.00%");
			deptAll[sum] += deptAll[idx];
			deptNo[sum] += deptNo[idx];
			deptRegs.add(dep);
		}
		Map dep = new HashMap();
		dep.put("name", "系所總計");
		dep.put("totalAll", deptAll[sum]);
		dep.put("noRegAll", deptNo[sum]);
		dep.put("regAll", (deptAll[sum] - deptNo[sum]));
		dep.put("noRegPcnAll", nf.format((deptNo[sum]/Double.parseDouble("" + deptAll[sum]))));
		deptRegs.add(dep);

		session.setAttribute("RegsReport", rep);
		session.setAttribute("DeptRegs", deptRegs);
		setContentPage(session, "registration/RepotrPrintRegister4C.jsp");
		
		return mapping.findForward("Main");
	}


}
