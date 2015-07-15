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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.mail.internet.InternetAddress;
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

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
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

public class TimeOffSummary02Action  extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/TimeOffSummary02.jsp");
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
		String WeekNo  = aForm.getString("WeekNo");
		String pmode  = aForm.getString("pmode");
		
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
		if(!Toolket.isNumeric(WeekNo)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}
			
		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-TimeOffSummary02.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-TimeOffSummary02.jasper";

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
			
			List tfList = sm.findDilg4SummaryT(clazzFilter, pmode);
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
								"查無\"SAF-TimeOffSummary02.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters.put("weekNo", WeekNo);
				
				String[] fields = { "deptClassName", "totalStu",
						"kind2", "kind2Avg", "kind4", "kind4Avg",
						"kind3", "kind3Avg", "kind6", "kind6Avg",
						"kind1", "kind1Avg", "goodStuNo"};
				
				String fID = credential.getMember().getIdno().substring(1);
				printData = fillPrintData(tfList,fields);
				
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
									.getRealPath(ranDir + "/TimeOffSum02" + fID + ".pdf"))));
					JasperExportManager.exportReportToPdfStream(
							jasperPrint, os);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					if(os!=null)os.close();
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
		session.setAttribute("TFSummaryInit", initMap);
		
		setContentPage(request.getSession(false), "studaffair/TimeOffSummary02.jsp");
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
		String WeekNo  = aForm.getString("WeekNo");
		String pmode  = aForm.getString("pmode");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = false;
		
		//if ("All".equals(campus)||"All".equals(school)||"All".equals(dept)) {
		if ("All".equals(campus)||"All".equals(school)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
		if(!Toolket.isNumeric(WeekNo)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "週次只能輸入數字"));
		}

		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			String reportSourceFile = "/WEB-INF/reports/SAF-TimeOffSummary02.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/SAF-TimeOffSummary02.jasper";
			HttpSession session = request.getSession(false);

			UserCredential credential = getUserCredential(session);
			/* 20120709 Mark BY yichen  ===========begin========================*/
			/*MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
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
			
			List tfList = sm.findDilg4SummaryT(clazzFilter, pmode);*/
			/* 20120709 Mark BY yichen  ============end=========================*/
			/* 20120709 Add BY yichen  ===========begin========================*/
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			if (campus.equals("All") || campus.equals("")) campus="_";
			if (school.equals("All") || school.equals("")) school="__";
			if (dept.equals("All") || dept.equals("")) dept="_";
			String condition="";
			if (departClass.equals("All") || departClass.equals("")) 
			  condition=campus+school+dept+"__";
			else
			  condition=departClass;		
			String sql="select classno as deptClassName,total as totalStu,abs2 as kind2,round(abs2/total,1) as kind2Avg,"+
			  "abs4 as kind4,round(abs4/total,1) as kind4Avg,abs3 as kind3,round(abs3/total,1) as kind3Avg,"+ 
			  "abs6 as kind6,round(abs6/total,1) as kind6Avg,abs1 as kind1,round(abs1/total,1) as kind1Avg,total-noabs as goodStuNo "+
			  "from (select depart_class as dp,ClassNo as classno,ClassName as classname,count(if (abs=2,1,NULL)) abs2,"+ 
			  "count(if (abs=3,1,NULL)) abs3, count(if (abs=4,1,NULL)) abs4,count(if (abs=6,1,NULL)) abs6,count(if (abs=1,1,NULL)) abs1, "+ 
			  "(select count(*) from stmd where stmd.depart_class=Dilg_One.depart_class) as total, "+
			  "(select count(distinct student_no) from Dilg_One where depart_class=ClassNo) as noabs "+
			  "from Dilg_One,Class where no_exam=0 and depart_class like '"+condition+"' and depart_class=ClassNo "+  
			  "group  by depart_class) t";
			List tempfList=cm.ezGetBy(sql);			
			/* 20120709 Add BY yichen  ============end=========================*/
			List<Object> printData = new ArrayList<Object>();
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			//log.debug("Print Report records size:" + tfList.size()); // 20120709 Mark BY yichen
			//Map tfMap = (Map)tfList.get(0);
			//if (!tfList.isEmpty()) {  // 20120709 Mark BY yichen
			if (!tempfList.isEmpty()) { // 20120709 Modi BY yichen
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
								"查無\"SAF-TimeOffSummary02.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				parameters.put("SchoolYear", syear);
				parameters.put("SchoolTerm", sterm);
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters.put("weekNo", WeekNo);
				
				String[] fields = { "deptClassName", "totalStu",
						"kind2", "kind2Avg", "kind4", "kind4Avg",
						"kind3", "kind3Avg", "kind6", "kind6Avg",
						"kind1", "kind1Avg", "goodStuNo"};
				
				String fID = credential.getMember().getIdno().substring(1);
                /* 20120709 Add BY yichen  ===========begin========================*/
				List tfList = new ArrayList();
				for(Iterator pIter=tempfList.iterator(); pIter.hasNext();){
					Map temp_pMap = (Map)pIter.next();
					Map temp_sdMap = new HashMap();
					//temp_pMap.get("deptClassName")
					temp_sdMap.put("deptClassName", Toolket.getClassFullName(temp_pMap.get(fields[0]).toString()));
					temp_sdMap.put("totalStu", ""+temp_pMap.get(fields[1]));
					temp_sdMap.put("kind2", ""+temp_pMap.get(fields[2]));
					temp_sdMap.put("kind2Avg", ""+temp_pMap.get(fields[3]));
					temp_sdMap.put("kind3", ""+temp_pMap.get(fields[6]));
					temp_sdMap.put("kind3Avg", ""+temp_pMap.get(fields[7]));
					temp_sdMap.put("kind4", ""+temp_pMap.get(fields[4]));
					temp_sdMap.put("kind4Avg", ""+temp_pMap.get(fields[5]));
					temp_sdMap.put("kind6", ""+temp_pMap.get(fields[8]));
					temp_sdMap.put("kind6Avg", ""+temp_pMap.get(fields[9]));
					temp_sdMap.put("kind1", ""+temp_pMap.get(fields[10]));
					temp_sdMap.put("kind1Avg", ""+temp_pMap.get(fields[11]));				
					temp_sdMap.put("goodStuNo", ""+temp_pMap.get(fields[12]));
					tfList.add(temp_sdMap);		
				}
                /* 20120709 Add BY yichen  ============end=========================*/
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
				
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				File of = new File(context
						.getRealPath(ranDir + "/TimeOffSum02" +  fID + ".pdf"));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(of));
				JasperExportManager.exportReportToPdfStream(
						jasperPrint, os);
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
				
				//Send e-mail to StudAffair department
				String SAFdept = Toolket.getDepartNo(campus + school);
				String receipts = "";
				if(SAFdept!=null){
					String[] recs;
					if(SAFdept.subSequence(0, 2).equals("11")){
						recs = IConstants.SAF11Emails;
					}else if(SAFdept.subSequence(0, 2).equals("12")){
						recs = IConstants.SAF12Emails;
					}else if(SAFdept.subSequence(0, 2).equals("13")){
						recs = IConstants.SAF12Emails;
					}else if(SAFdept.subSequence(0, 2).equals("21")){
						recs = IConstants.SAF12Emails;
					}else if(SAFdept.subSequence(0, 2).equals("22")){
						recs = IConstants.SAF12Emails;
					}else if(SAFdept.subSequence(0, 2).equals("23")){
						recs = IConstants.SAF12Emails;
					}else{
						recs = new String[0];
					}
					receipts = recs[0];
					receipts = receipts + IConstants.SAFAdminEmail;
				}
				List<File> pdfs = new ArrayList<File>();
				pdfs.add(of);
				//new SendEmail2SAF(pdfs, WeekNo, "", "", receipts, false).run();
				
				if(os!=null)os.close();
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

	
	/*
	private static class SendEmail2SAF implements Runnable {

		Logger log = Logger.getLogger(SendEmail2SAF.class);

		private List<File> files = null;
		private String receipts = null;
		private String RETURN = "\n";
		private String WeekNo = "";
		private String DateStart = "";
		private String DateEnd = "";
		// private String SPACE = " ";
		private boolean isDebug = false;

		SendEmail2SAF(List<File> files, String WeekNo, String DateStart, String DateEnd, String receipts, boolean isDebug) {
			this.files = files;
			this.DateStart = DateStart;
			this.DateEnd = DateEnd;
			this.receipts = receipts;
			this.isDebug = isDebug;
		}

		
		
		public void run() {
			String err = "";
			String teacherName = "";
			
			InternetAddress[] address = null;
			boolean sessionDebug = false;
			String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
			String From = "cc@www.cust.edu.tw";
			String to = receipts;
			String sysAdmin = "jason034@cc.cust.edu.tw";
			Map tMap = new HashMap();
			
			String Subject = "中華科技大學 第 " + WeekNo + " 週  曠缺人次統計表";
			String type = "text/html";
			
			String message = "<b>中華科技大學 第 " + WeekNo + " 週  曠缺人次統計表, 詳如附件!<br>";
			
			MultiPartEmail email = new MultiPartEmail();
			email.setCharset("UTF-8");
			email.setSentDate(new Date());
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			email.setAuthentication("cc@www.cust.edu.tw", "577812");
			email.setSubject(Subject);
			email.setDebug(isDebug);

			EmailAttachment attachment = null;
			try {
				String[] emails = to.split(",");
				for(String receipt:emails){
					email.addTo(receipt);
				}
				email.addBcc(sysAdmin, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", "中華科技大學 電算中心軟體開發組");
				email.addReplyTo(sysAdmin, "電算中心軟體開發組");
				//StringBuffer content = new StringBuffer();
				//content.append("財政部扣繳稅額資料:").append(RETURN).append(RETURN);
				email.setContent(message, type+";charset=UTF-8");
				// set the html message
				//email.setHtmlMsg("<html>The apache logo - <img src=\"cid:\"></html>");
				if(files != null){
					for (File file : files) {
						attachment = new EmailAttachment();
						attachment.setPath(file.getAbsolutePath());
						attachment.setDisposition(EmailAttachment.ATTACHMENT);
						attachment.setDescription("");
						attachment.setName(file.getName());
						email.attach(attachment);
					}
				}

				email.send();
			} catch (EmailException ee) {
				log.error(ee.getMessage(), ee);
			}
		}
		
		
		
	}
	*/
	

}