package tw.edu.chit.struts.action.studaffair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

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

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
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

public class TimeOffSummary01Action extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/TimeOffSummary01.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		String WeekStart  = aForm.getString("WeekStart");
		String WeekEnd  = aForm.getString("WeekEnd");
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
		
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
		if(!Toolket.isNumeric(WeekStart)|| !Toolket.isNumeric(WeekEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}else{
			if(Integer.parseInt(WeekStart) > Integer.parseInt(WeekEnd)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "開始週次必須小於或等於結束週次"));					
			}
		}
			
		if(!Toolket.isValidDate(DateStart)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "起始日期輸入錯誤!"));					
		}
		if(!Toolket.isValidDate(DateEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "結束日期輸入錯誤!"));					
		}

		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-TimeOffSummary01.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-TimeOffSummary01.jasper";
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
			
			List tfList = sm.findDilg4Summary(DateStart, DateEnd, clazzFilter,
					credential.getClassInChargeSqlFilterSAF());
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			Map tfMap = (Map)tfList.get(0);
			/*
			log.debug("Data Detail:" + tfMap.get("clazz") + ":" + tfMap.get("Student") + ":" +
					tfMap.get("Date1") + ":" +tfMap.get("Week1") + ":" +tfMap.get("Period1") + ":" +
					tfMap.get("Kind1") + ":" +tfMap.get("Date2") + ":" +tfMap.get("Week2") + ":" +
					tfMap.get("Period2") + ":" +tfMap.get("Kind2") + ":" +tfMap.get("Date3") + ":" +
					tfMap.get("Week3") + ":" +tfMap.get("Period3") + ":" +tfMap.get("Kind3") + ":" +
					tfMap.get("SumK1") + ":" +tfMap.get("SumK2") + ":" +tfMap.get("SumK3") + ":" +
					tfMap.get("SumK4") + ":" +tfMap.get("SumK5") + ":" +tfMap.get("SumK6") + ":" +
					tfMap.get("SumK7") + ":" +tfMap.get("SumScore") + ":" +tfMap.get("Memo"));
			*/
			if (!tfList.isEmpty()) {
				printData = fillPrintData(tfList);

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
								"查無\"SAF-TimeOffSummary01.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				// JRField[] jrField = jasperReport.getFields();
				// for (JRField field : jrField) {
				// System.out.println("Name:" + field.getName());
				// JRPropertiesMap x = field.getPropertiesMap();
				// System.out.println();
				// }
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters.put("WeekStart", WeekStart);
				parameters.put("WeekEnd", WeekEnd);
				parameters.put("DateStart", DateStart);
				parameters.put("DateEnd", DateEnd);
				
				String[] fields = { "clazz", "Student",
						"Date1", "Week1", "Period1", "Kind1",
						"Date2", "Week2", "Period2", "Kind2",
						"Date3", "Week3", "Period3", "Kind3",
						"SumK1", "SumK2", "SumK3", "SumK4", "SumK5", "SumK6", "SumK7",
						"SumScore", "Memo"};
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					log.debug("Print Report records size:" + tfList.size());
					JasperPrintManager.printReport(jasperPrint,
							setupPrinter);						
				} else {
					// JRBaseTextField textField = (JRBaseTextField) jasperReport
					// .getDetail().getElementByKey("textField-20");
					// textField.setForecolor(new Color(204, 0, 51));
					byte[] bytes = JasperRunManager.runReportToPdf(
							jasperReport, parameters,
							new HibernateQueryResultDataSource(printData,
									fields));
					
					Calendar td = Calendar.getInstance();
					String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
								(td.get(Calendar.MILLISECOND));
					String ranDir = "/WEB-INF/reports/temp/" + ran;
					
					File tempdir = new File(context
							.getRealPath(ranDir));
					if(!tempdir.exists()) tempdir.mkdirs();

					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath(ranDir + "/TimeOffSum01.pdf"))));
					JasperExportManager.exportReportToPdfStream(
							jasperPrint, os);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					os.close();
					Toolket.deleteDIR(tempdir);
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

				// Document doc = new Document();
				// doc.addTitle("");
				// doc.addSubject("");
				// doc.addAuthor("");
				// ByteArrayOutputStream baosPDF = new
				// ByteArrayOutputStream();
				// PdfWriter docWriter = PdfWriter.getInstance(doc,
				// baosPDF);
				// PdfCopy copy = new PdfCopy(doc, new
				// FileOutputStream("HelloCopy123.pdf"));
				// copy.

			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				saveMessages(request, messages);
				
				if(!isPrint){
					return mapping.findForward("NoData");
				}
			}
		}
		
		setContentPage(request.getSession(false), "studaffair/TimeOffSummary01.jsp");
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
		String WeekStart  = aForm.getString("WeekStart");
		String WeekEnd  = aForm.getString("WeekEnd");
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = false;
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if ("All".equals(campus)||"All".equals(school)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
		if(!Toolket.isNumeric(WeekStart)|| !Toolket.isNumeric(WeekEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}else{
			if(Integer.parseInt(WeekStart) > Integer.parseInt(WeekEnd)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "開始週次必須小於或等於結束週次"));					
			}
		}
			
		if(!Toolket.isValidDate(DateStart)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "起始日期輸入錯誤!"));					
		}
		if(!Toolket.isValidDate(DateEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "結束日期輸入錯誤!"));					
		}

		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-TimeOffSummary01.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-TimeOffSummary01.jasper";
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
			
			List tfList = sm.findDilg4Summary(DateStart, DateEnd, clazzFilter,
					credential.getClassInChargeSqlFilterSAF());
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			if (!tfList.isEmpty()) {
				Map tfMap = (Map)tfList.get(0);
				printData = fillPrintData(tfList);

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
								"查無\"SAF-TimeOffSummary01.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters.put("WeekStart", WeekStart);
				parameters.put("WeekEnd", WeekEnd);
				parameters.put("DateStart", DateStart);
				parameters.put("DateEnd", DateEnd);
				
				String[] fields = { "clazz", "Student",
						"Date1", "Week1", "Period1", "Kind1",
						"Date2", "Week2", "Period2", "Kind2",
						"Date3", "Week3", "Period3", "Kind3",
						"SumK1", "SumK2", "SumK3", "SumK4", "SumK5", "SumK6", "SumK7",
						"SumScore", "Memo"};
				
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
				
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir +"/TimeOffSum01.pdf"))));
				JasperExportManager.exportReportToPdfStream(
						jasperPrint, os);
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
				os.close();
				Toolket.deleteDIR(tempdir);

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

	private List<Object> fillPrintData(List iList){
		List<Object> rtList = new ArrayList();
		for(Iterator pIter=iList.iterator(); pIter.hasNext();){
			Map pMap = (Map)pIter.next();
			Object[] obj = new Object[23];
			obj[0] = pMap.get("clazz");
			obj[1] = pMap.get("Student");
			obj[2] = pMap.get("Date1");
			obj[3] = pMap.get("Week1");
			obj[4] = pMap.get("Period1");
			obj[5] = pMap.get("Kind1");
			obj[6] = pMap.get("Date2");
			obj[7] = pMap.get("Week2");
			obj[8] = pMap.get("Period2");
			obj[9] = pMap.get("Kind2");
			obj[10] = pMap.get("Date3");
			obj[11] = pMap.get("Week3");
			obj[12] = pMap.get("Period3");
			obj[13] = pMap.get("Kind3");
			obj[14] = pMap.get("SumK1");
			obj[15] = pMap.get("SumK2");
			obj[16] = pMap.get("SumK3");
			obj[17] = pMap.get("SumK4");
			obj[18] = pMap.get("SumK5");
			obj[19] = pMap.get("SumK6");
			obj[20] = pMap.get("SumK7");
			obj[21] = pMap.get("SumScore");
			obj[22] = pMap.get("Memo");
			rtList.add(obj);
		}
		return rtList;
	}

}
