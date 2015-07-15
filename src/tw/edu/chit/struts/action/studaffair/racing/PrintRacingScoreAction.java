package tw.edu.chit.struts.action.studaffair.racing;

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

public class PrintRacingScoreAction extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/racing/PrintRacingScore.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String WeekStart  = aForm.getString("WeekStart");
		String WeekEnd  = aForm.getString("WeekEnd");
		String GroupNo  = aForm.getString("GroupNo");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = true;
		
		//String printOpt = (String) aForm.get("printOpt");
		//boolean setupPrinter = "on".equalsIgnoreCase(aForm
		//		.getString("setupPrinter"));
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if(!Toolket.isNumeric(WeekStart)|| !Toolket.isNumeric(WeekEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}else{
			if(Integer.parseInt(WeekStart) > Integer.parseInt(WeekEnd)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "開始週次必須小於或等於結束週次"));					
			}
		}
			
		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-RacingScore.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-RacingScore.jasper";
			HttpSession session = request.getSession(false);

			UserCredential credential = getUserCredential(session);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			String clazzFilter = "";
						
			List scoreList = sm.findRacingScore(GroupNo, WeekStart, WeekEnd);
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + scoreList.size());
			Map scoreMap = (Map)scoreList.get(0);
			if (!scoreList.isEmpty()) {
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
								"查無\"SAF-RacingScore.jasper\"檔案，請電洽電算中心，謝謝！！");
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
				parameters.put("GroupNo", GroupNo);
				
				String[] fields = { "deptClassName","ruleTranScore","lifeScore","cleanScore"
						,"healthScore","timeOff10","timeOff20","timeOff30","timeOff40",
						"timeOffmuch","absent","learnScore","bigBonus","smallBonus"
						,"bigPenalty","smallPenalty","publicScore","SumScore","order"};
				
				String fID = credential.getMember().getIdno().substring(1);
				printData = fillPrintData(scoreList,fields);

				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					log.debug("Print Report records size:" + scoreList.size());
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
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath("/WEB-INF/reports/temp/RacingScore" + fID + ".pdf"))));
					JasperExportManager.exportReportToPdfStream(
							jasperPrint, os);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					return null;
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
		
		setContentPage(request.getSession(false), "studaffair/racing/RacingScore.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String WeekStart  = aForm.getString("WeekStart");
		String WeekEnd  = aForm.getString("WeekEnd");
		String GroupNo  = aForm.getString("GroupNo");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = false;
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if(!Toolket.isNumeric(WeekStart)|| !Toolket.isNumeric(WeekEnd)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}else{
			if(Integer.parseInt(WeekStart) > Integer.parseInt(WeekEnd)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "開始週次必須小於或等於結束週次"));					
			}
		}
			
		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-RacingScore.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-RacingScore.jasper";
			HttpSession session = request.getSession(false);

			UserCredential credential = getUserCredential(session);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			String clazzFilter = "";
						
			List scoreList = sm.findRacingScore(GroupNo, WeekStart, WeekEnd);
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + scoreList.size());
			Map scoreMap = (Map)scoreList.get(0);
			if (!scoreList.isEmpty()) {
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
								"查無\"SAF-RacingScore.jasper\"檔案，請電洽電算中心，謝謝！！");
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
				parameters.put("GroupNo", GroupNo);
				
				String[] fields = { "deptClassName","ruleTranScore","lifeScore","cleanScore"
						,"healthScore","timeOff10","timeOff20","timeOff30","timeOff40",
						"timeOffmuch","absent","learnScore","bigBonus","smallBonus","miniBonus"
						,"bigPenalty","smallPenalty","miniPenalty","publicScore","SumScore","order"};
				
				String fID = credential.getMember().getIdno().substring(1);
				printData = fillPrintData(scoreList,fields);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					log.debug("Print Report records size:" + scoreList.size());
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
					OutputStream os = new BufferedOutputStream(
							new FileOutputStream(new File(context.getRealPath("/WEB-INF/reports/temp/RacingScore" + fID + ".pdf"))));
					JasperExportManager.exportReportToPdfStream(
							jasperPrint, os);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					return null;
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
