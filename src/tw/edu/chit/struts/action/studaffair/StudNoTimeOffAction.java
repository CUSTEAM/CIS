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

public class StudNoTimeOffAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("PrintOption","print");
		map.put("PrintPreview","preview");
		map.put("Cancel", "cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
			
		setContentPage(session, "studaffair/StudNoTimeOff.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = true;
		
		//String printOpt = (String) aForm.get("printOpt");
		//boolean setupPrinter = "on".equalsIgnoreCase(aForm
		//		.getString("setupPrinter"));
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if ("All".equals(campus)||"All".equals(school)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
			
		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-NoTimeOffStudents.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-NoTimeOffStudents.jasper";

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
			
			List tfList = sm.findNoTimeOffStudents(clazzFilter);
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			if (!tfList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile = null;
				reportFile = new File(context.getRealPath(reportCompiledFile));
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile));
					reportFile = new File(context
							.getRealPath(reportCompiledFile));
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"查無\"SAF-NoTimeOffStudents.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				
				String[] fields = { "deptClassName", "student"};
				
				String fID = credential.getMember().getIdno().substring(1);
				printData = fillPrintData(tfList,fields);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					//log.debug("Print Report records size:" + tfList.size());
					JasperPrintManager.printReport(jasperPrint,
							setupPrinter);						
				} else {
					byte[] bytes = JasperRunManager.runReportToPdf(
							jasperReport, parameters,
							new HibernateQueryResultDataSource(printData,
									fields));
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath("/WEB-INF/reports/temp/NoTimeOffStudents" + fID + ".pdf"))));
					JasperExportManager.exportReportToPdfStream(
							jasperPrint, os);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					return null;
					// JasperViewer.viewReport(jasperPrint);
					// JRXlsExporter exporter = new JRXlsExporter();
					// exporter.setParameter(JRExporterParameter.JASPER_PRINT,
					// jasperPrint);
					// exporter.setParameter(
					// JRExporterParameter.OUTPUT_FILE_NAME,
					// "export.xls");
					// exporter.exportReport();
				}
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				saveMessages(request, messages);
				
				if(!isPrint){
					return mapping.findForward("NoData");
				}
			}
		}
		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		session.setAttribute("NTFStudentInit", initMap);
		
		setContentPage(request.getSession(false), "studaffair/StudNoTimeOff.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = false;
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if ("All".equals(campus)||"All".equals(school)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}

		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-NoTimeOffStudents.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-NoTimeOffStudents.jasper";
			HttpSession session = request.getSession(false);

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
			
			List tfList = sm.findNoTimeOffStudents(clazzFilter);
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			//Map tfMap = (Map)tfList.get(0);
			if (!tfList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile = null;
				reportFile = new File(context.getRealPath(reportCompiledFile));
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile));
					reportFile = new File(context
							.getRealPath(reportCompiledFile));
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"查無\"SAF-NoTimeOffStudents.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				
				String[] fields = { "deptClassName", "student"};
				
				String fID = credential.getMember().getIdno().substring(1);
				printData = fillPrintData(tfList,fields);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				// jasperPrint.
				// 列印或預覽
				byte[] bytes = JasperRunManager.runReportToPdf(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath("/WEB-INF/reports/temp/NoTimeOffStudents" +  fID + ".pdf"))));
				JasperExportManager.exportReportToPdfStream(
						jasperPrint, os);
				JasperReportUtils.printPdfToFrontEnd(response, bytes);

			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				saveMessages(request, messages);
				return mapping.findForward("NoData");
			}
		}
		return mapping.findForward(null);
		//return null;		
	}

	private List<Object> fillPrintData(List iList, String[] fields){
		List<Object> rtList = new ArrayList();
		for(Iterator pIter=iList.iterator(); pIter.hasNext();){
			Map pMap = (Map)pIter.next();
			Object[] obj = new Object[fields.length];
			for(int i=0; i<fields.length; i++){
				obj[i] = pMap.get(fields[i]);
				//log.debug("PrintData:obj[" + i  + "]=" + obj[i]);
			}
			rtList.add(obj);
		}
		return rtList;
	}


}
