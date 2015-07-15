package tw.edu.chit.struts.action.studaffair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.SimpleBookmark;

import tw.edu.chit.model.Progress;
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

public class TimeOffAlert4TAction  extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/TimeOffAlert4T.jsp");
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
		String WeekNo  = aForm.getString("WeekNo");
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
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
			//SAF-TimeOffAlert4T1  導師聯絡家長記錄
			//SAF-TimeOffAlert4T2  導師聯絡家長核對記錄
			String reportSourceFile1 = "/WEB-INF/reports/SAF-TimeOffAlert4T1.jrxml";
			String reportCompiledFile1 = "/WEB-INF/reports/SAF-TimeOffAlert4T1.jasper";
			String reportSourceFile2 = "/WEB-INF/reports/SAF-TimeOffAlert4T2.jrxml";
			String reportCompiledFile2 = "/WEB-INF/reports/SAF-TimeOffAlert4T2.jasper";
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
			
			String prgName = this.getClass().getName();
			String parameter = clazzFilter + "|" + DateStart + "|" + DateEnd ;
			List<Progress> prgList = sm.findProgress(this.getClass().getName());
			
			//自動判斷是否為第一次列印 , 修改日:2009/03/26
			pmode = "0";
			if(!prgList.isEmpty()){
				Calendar start1 = Calendar.getInstance();
				Calendar end1 = Calendar.getInstance();
				Calendar start2 = Calendar.getInstance();
				Calendar end2 = Calendar.getInstance();
				start1.setTime(Toolket.parseDate(DateStart.replace('/', '-')));
				end1.setTime(Toolket.parseDate(DateEnd.replace('/', '-')));
				
				for(Progress prg:prgList){
					String[] parameters = prg.getParameter().split("\\|");
					log.debug(parameters[0] + ", " + parameters[1] + ", " + parameters[2]);
					if(prg.getRunning() != 0){
						if(parameters[0].startsWith(clazzFilter) || clazzFilter.startsWith(parameters[0])){
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
							saveMessages(request, messages);
							return mapping.findForward("NoData");
						}
					}else{
						//test progress have run with parameter DateStart and DateEnd; this will change pmode
						start2.setTime(Toolket.parseDate(parameters[1].replace('/', '-')));
						end2.setTime(Toolket.parseDate(parameters[2].replace('/', '-')));
						if((start2.compareTo(start1) >= 0 && start2.compareTo(end1)<=0) ||
								(end2.compareTo(start1)>=0 && end2.compareTo(end1)<=0)){
							if(clazzFilter.startsWith(parameters[0])){
								if(parameters[3].trim().equals("0")){
									pmode = "1";
									break;
								}
							}else if(parameters[0].startsWith(clazzFilter)){
								//messages.add(ActionMessages.GLOBAL_MESSAGE, 
										//new ActionMessage("MessageN1", "您之前已處理過部制:" + parameters[0] +" !請電洽電算中心處理"));
								//saveMessages(request, messages);
								//return mapping.findForward("NoData");
							}else{
								
							}
						}
					}
				}
				//no progress is running with parameter clazzFilter
				
			}
			parameter = parameter + "|" + pmode + "|" + credential.getMember().getIdno();
			//register this progress in DB
			Progress tf4p = sm.setTF4PProgress(this.getClass().getName(), parameter);
			if(tf4p == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
				saveMessages(request, messages);
				return mapping.findForward("Main");
			}
			
			List tfList = sm.findDilgAlert4T(DateStart, DateEnd, clazzFilter
							,pmode);
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			Map fMap = (Map)tfList.get(0);
			/*
			log.debug("Data Detail:" + fMap.get("clazz") + ":" + fMap.get("Student") + ":" +
					fMap.get("Date1") + ":" +fMap.get("Week1") + ":" +fMap.get("Period1") + ":" +
					fMap.get("Kind1") + ":" +fMap.get("Date2") + ":" +fMap.get("Week2") + ":" +
					fMap.get("Period2") + ":" +fMap.get("Kind2") + ":" +fMap.get("Date3") + ":" +
					fMap.get("Week3") + ":" +fMap.get("Period3") + ":" +fMap.get("Kind3") + ":" +
					fMap.get("SumK1") + ":" +fMap.get("SumK2") + ":" +fMap.get("SumK3") + ":" +
					fMap.get("SumK4") + ":" +fMap.get("SumK5") + ":" +fMap.get("SumK6") + ":" +
					fMap.get("SumK7") + ":" +fMap.get("SumScore") + ":" +fMap.get("Memo"));
			*/
			if (!tfList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				File reportFile2 = new File(context.getRealPath(reportCompiledFile2));
				
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile1));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile2));
					reportFile1 = new File(context
							.getRealPath(reportCompiledFile1));
					reportFile2 = new File(context
							.getRealPath(reportCompiledFile2));
					
					if (!reportFile1.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert4T1.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile2.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert4T2.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport1 = (JasperReport) JRLoader
						.loadObject(reportFile1.getPath());
				
				JasperReport jasperReport2 = (JasperReport) JRLoader
						.loadObject(reportFile2.getPath());
				
				
				// JRField[] jrField = jasperReport.getFields();
				// for (JRField field : jrField) {
				// System.out.println("Name:" + field.getName());
				// JRPropertiesMap x = field.getPropertiesMap();
				// System.out.println();
				// }
				List<Object> printData1 = new ArrayList<Object>();
				List<Object> printData2 = new ArrayList<Object>();
				Map<String, String> parameters1 = new HashMap<String, String>();
				Map<String, String> parameters2 = new HashMap<String, String>();

				String[] fields1 = {"deptClassName",
						"studentNo", "studentName", "parentName", "TEL",
						"timeOffSum","raiseFlagSum"};
				String[] fields2 =  {"deptClassName",
						"studentNo", "studentName", "parentName", "TEL",
						"timeOffSum","raiseFlagSum"};
								
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				String fID = credential.getMember().getIdno().substring(1) + "_" + ran + "_";
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();				
				
				parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters1.put("weekNo", WeekNo);
				parameters1.put("dateStart", DateStart);
				parameters1.put("dateEnd", DateEnd);

				parameters2.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters2.put("weekNo", WeekNo);
				parameters2.put("dateStart", DateStart);
				parameters2.put("dateEnd", DateEnd);
				
				printData1 = fillPrintData(tfList,fields1);
							
				printData2 = fillPrintData(tfList,fields2);			
								
				JasperPrint jasperPrint1 = JasperFillManager.fillReport(
						jasperReport1, parameters1,
						new HibernateQueryResultDataSource(printData1,
								fields1));
				
				JasperPrint jasperPrint2 = JasperFillManager.fillReport(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					//log.debug("Print Report records size:" + tfList.size());
					
					JasperPrintManager.printReport(jasperPrint1,
							setupPrinter);						
					JasperPrintManager.printReport(jasperPrint2,
							setupPrinter);						
				} else {
					//Merge all PDF file
					
					byte[] bytes1 = JasperRunManager.runReportToPdf(
							jasperReport1, parameters1,
							new HibernateQueryResultDataSource(printData1,
									fields1));
					
					byte[] bytes2 = JasperRunManager.runReportToPdf(
							jasperReport2, parameters2,
							new HibernateQueryResultDataSource(printData2,
									fields2));
					
					OutputStream os2 = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath(ranDir + "TimeOffAlert4T" + fID + "2.pdf"))));
					OutputStream os1 = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath(ranDir + "TimeOffAlert4T" + fID + "1.pdf"))));
					
					JasperExportManager.exportReportToPdfStream(
							jasperPrint2, os2);
					JasperExportManager.exportReportToPdfStream(
							jasperPrint1, os1);
					
					if(os2!=null) os2.close();
					if(os1!=null) os1.close();
					
					//Merge TimeOffAlert[999999999]1.pdf, 
					//TimeOffAlert[999999999]2.pdf,TimeOffAlert[999999999]3.pdf
					//to TimeOffAlert4P[999999999].pdf
					try {
					    List<InputStream> pdfs = new ArrayList<InputStream>();
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert4T"+fID+"1.pdf")));
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert4T"+fID+"2.pdf")));
					    	
						File of1 = new File(context
								.getRealPath(ranDir + "TimeOffAlert4T" + fID + ".pdf"));
						if(of1.exists()) {
							of1.delete();
							of1.createNewFile();
						}
						else of1.createNewFile();
							
					    OutputStream output = new FileOutputStream(context
								.getRealPath(ranDir + "TimeOffAlert4T" + fID + ".pdf"));
					    this.concatPDFs(pdfs, output, true);
						byte[] bytes = file2byte(of1);
						JasperReportUtils.printPdfToFrontEnd(response, bytes);
				        output.close();
				        Toolket.deleteDIR(tempdir);
					} catch (Exception e) {
						Toolket.deleteDIR(tempdir);
					    System.out.println("Merge PDFs Fail!");
				        e.printStackTrace();
					}
					return null;
				}
				
				if(isPrint){
					//依照列印模式決定是否更改 DilgMail 紀錄
					if(pmode.equals("0")){
						List dmList = new ArrayList();
						for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
							Map tfMap = (Map)tfIter.next();
							Map dmMap = new HashMap();
							dmMap.put("studentNo", tfMap.get("studentNo"));
							dmMap.put("tutorContact", "1");
							dmList.add(dmMap);
						}

						ActionMessages message = sm.createOrModifyDilgMail(dmList); 
					}
				}
				
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()) messages.add(clrTF4P);
				
				
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));

				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()) messages.add(clrTF4P);

				saveMessages(request, messages);
				
				return mapping.findForward("NoData");
			}
			
		}
		
		setContentPage(request.getSession(false), "studaffair/TimeOffAlert4T.jsp");
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
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
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
			//SAF-TimeOffAlert4T1  導師聯絡家長記錄
			//SAF-TimeOffAlert4T2  導師聯絡家長核對記錄
			String reportSourceFile1 = "/WEB-INF/reports/SAF-TimeOffAlert4T1.jrxml";
			String reportCompiledFile1 = "/WEB-INF/reports/SAF-TimeOffAlert4T1.jasper";
			String reportSourceFile2 = "/WEB-INF/reports/SAF-TimeOffAlert4T2.jrxml";
			String reportCompiledFile2 = "/WEB-INF/reports/SAF-TimeOffAlert4T2.jasper";
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

			String prgName = this.getClass().getName();
			String parameter = clazzFilter + "|" + DateStart + "|" + DateEnd ;
			List<Progress> prgList = sm.findProgress(this.getClass().getName());
			
			//自動判斷是否為第一次列印 , 修改日:2009/03/26
			pmode = "0";
			if(!prgList.isEmpty()){
				Calendar start1 = Calendar.getInstance();
				Calendar end1 = Calendar.getInstance();
				Calendar start2 = Calendar.getInstance();
				Calendar end2 = Calendar.getInstance();
				start1.setTime(Toolket.parseDate(DateStart.replace('/', '-')));
				end1.setTime(Toolket.parseDate(DateEnd.replace('/', '-')));
				
				for(Progress prg:prgList){
					String[] parameters = prg.getParameter().split("\\|");
					log.debug(parameters[0] + ", " + parameters[1] + ", " + parameters[2]);
					if(prg.getRunning() != 0){
						if(parameters[0].startsWith(clazzFilter) || clazzFilter.startsWith(parameters[0])){
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
							saveMessages(request, messages);
							return mapping.findForward("NoData");
						}
					}else{
						//test progress have run with parameter DateStart and DateEnd; this will change pmode
						start2.setTime(Toolket.parseDate(parameters[1].replace('/', '-')));
						end2.setTime(Toolket.parseDate(parameters[2].replace('/', '-')));
						if((start2.compareTo(start1) >= 0 && start2.compareTo(end1)<=0) ||
								(end2.compareTo(start1)>=0 && end2.compareTo(end1)<=0)){
							if(clazzFilter.startsWith(parameters[0])){
								if(parameters[3].trim().equals("0")){
									pmode = "1";
									break;
								}
							}else if(parameters[0].startsWith(clazzFilter)){
								//messages.add(ActionMessages.GLOBAL_MESSAGE, 
										//new ActionMessage("MessageN1", "您之前已處理過部制:" + parameters[0] +" !請電洽電算中心處理"));
								//saveMessages(request, messages);
								//return mapping.findForward("NoData");
							}else{
								
							}
						}
					}
				}
				//no progress is running with parameter clazzFilter
				
			}
			parameter = parameter + "|" + pmode + "|" + credential.getMember().getIdno();
			//register this progress in DB
			Progress tf4p = sm.setTF4PProgress(this.getClass().getName(), parameter);
			if(tf4p == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
				saveMessages(request, messages);
				return mapping.findForward("Main");
			}
			

			
			
			List tfList = sm.findDilgAlert4T(DateStart, DateEnd, clazzFilter
							,pmode);
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			
			if (!tfList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				File reportFile2 = new File(context.getRealPath(reportCompiledFile2));
				
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile1));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile2));
					reportFile1 = new File(context
							.getRealPath(reportCompiledFile1));
					reportFile2 = new File(context
							.getRealPath(reportCompiledFile2));
					
					if (!reportFile1.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert4T1.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile2.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert4T2.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport1 = (JasperReport) JRLoader
						.loadObject(reportFile1.getPath());
				
				JasperReport jasperReport2 = (JasperReport) JRLoader
						.loadObject(reportFile2.getPath());
				
				
				// JRField[] jrField = jasperReport.getFields();
				// for (JRField field : jrField) {
				// System.out.println("Name:" + field.getName());
				// JRPropertiesMap x = field.getPropertiesMap();
				// System.out.println();
				// }
				List<Object> printData1 = new ArrayList<Object>();
				List<Object> printData2 = new ArrayList<Object>();
				Map<String, String> parameters1 = new HashMap<String, String>();
				Map<String, String> parameters2 = new HashMap<String, String>();

				String[] fields1 = {"deptClassName",
						"studentNo", "studentName", "parentName", "TEL",
						"timeOffSum","raiseFlagSum"};
				String[] fields2 =  {"deptClassName",
						"studentNo", "studentName", "parentName", "TEL",
						"timeOffSum","raiseFlagSum"};
				
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				String fID = credential.getMember().getIdno().substring(1) + "_" + ran + "_";
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters1.put("weekNo", WeekNo);
				parameters1.put("dateStart", DateStart);
				parameters1.put("dateEnd", DateEnd);

				parameters2.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters2.put("weekNo", WeekNo);
				parameters2.put("dateStart", DateStart);
				parameters2.put("dateEnd", DateEnd);
				
				//printData2 只列印掛號郵件執據
				printData1 = fillPrintData(tfList,fields1);
							
				printData2 = fillPrintData(tfList,fields2);			
								
				JasperPrint jasperPrint1 = JasperFillManager.fillReport(
						jasperReport1, parameters1,
						new HibernateQueryResultDataSource(printData1,
								fields1));
				
				JasperPrint jasperPrint2 = JasperFillManager.fillReport(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
				// jasperPrint.
				// 列印或預覽
					//Merge all PDF file
					
				byte[] bytes1 = JasperRunManager.runReportToPdf(
						jasperReport1, parameters1,
						new HibernateQueryResultDataSource(printData1,
								fields1));
					
				byte[] bytes2 = JasperRunManager.runReportToPdf(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
					
				OutputStream os2 = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "TimeOffAlert4T" + fID + "2.pdf"))));
				OutputStream os1 = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "TimeOffAlert4T" + fID + "1.pdf"))));
					
				JasperExportManager.exportReportToPdfStream(
						jasperPrint2, os2);
				JasperExportManager.exportReportToPdfStream(
						jasperPrint1, os1);
					
				if(os2!=null) os2.close();
				if(os1!=null) os1.close();
					
				//Merge pdf files 
				try {
					List<InputStream> pdfs = new ArrayList<InputStream>();
					pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "TimeOffAlert4T"+fID+"1.pdf")));
					pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "TimeOffAlert4T"+fID+"2.pdf")));
					    	
					File of1 = new File(context
							.getRealPath(ranDir + "TimeOffAlert4T" + fID + ".pdf"));
					if(of1.exists()) {
						of1.delete();
						of1.createNewFile();
					}
					else of1.createNewFile();
							
					OutputStream output = new FileOutputStream(context
							.getRealPath(ranDir + "TimeOffAlert4T" + fID + ".pdf"));
					this.concatPDFs(pdfs, output, true);
					byte[] bytes = file2byte(of1);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					output.close();
				    Toolket.deleteDIR(tempdir);
				} catch (Exception e) {
					Toolket.deleteDIR(tempdir);
					System.out.println("Merge PDFs Fail!");
				    e.printStackTrace();
				}
				//依照列印模式決定是否更改 DilgMail 紀錄
				if(pmode.equals("0")){
					List dmList = new ArrayList();
					for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
						Map tfMap = (Map)tfIter.next();
						Map dmMap = new HashMap();
						dmMap.put("studentNo", tfMap.get("studentNo"));
						if(Integer.parseInt(tfMap.get("timeOffSum").toString()) >=
								Integer.parseInt(tfMap.get("raiseFlagSum").toString())){
							dmMap.put("tutorContact", tfMap.get("timeOffSum"));
						}else{
							dmMap.put("tutorContact", tfMap.get("raiseFlagSum"));
						}
						dmList.add(dmMap);
					}

					ActionMessages message = sm.createOrModifyDilgMail(dmList); 
				}
				
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()) messages.add(clrTF4P);

			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()) messages.add(clrTF4P);
				
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

	
	private void concatPDFs(List<InputStream> streamOfPDFFiles,
			OutputStream outputStream, boolean paginate) {
		Document document = new Document();
		try {
			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();
			// Create Readers for the pdfs.      
			while (iteratorPDFs.hasNext()) {
				InputStream pdf = iteratorPDFs.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				totalPages += pdfReader.getNumberOfPages();
			}
			// Create a writer for the outputstream      
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF      
			// data      
			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();
			// Loop through the PDF files and add to the output.      
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				// Create a new page in the target for each source page.       
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					document.newPage();
					pageOfCurrentReaderPDF++;
					currentPageNumber++;
					page = writer.getImportedPage(pdfReader,
							pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);
					// Code for pagination.          
					if (paginate) {
						cb.beginText();
						cb.setFontAndSize(bf, 9);
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
								+ currentPageNumber + " of " + totalPages, 520,
								5, 0);
						cb.endText();
					}
				}
				pageOfCurrentReaderPDF = 0;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	private void cp(FileInputStream src, FileOutputStream dst){
		byte[] tbyte = new byte[1];
		try{
			while(src.read(tbyte)!=-1){
				dst.write(tbyte);
			}
		}catch(FileNotFoundException ecp){
			System.out.println("檔案不存在!");
		}catch(IOException ecp){
			System.out.println("I/O錯誤!");
		}
	}
	
	private byte[] file2byte(File src) throws Exception{
		long len = src.length();
		int leni = 0;
		if(len < Integer.MAX_VALUE) {
			leni = (int)len;
		}else{
				throw new Exception("檔案長度超過2GB!");
		}
		byte[] rbyte = new byte[leni];
		byte[] tbyte = new byte[1];
		int cnt = 0;
		
		try{
			FileInputStream fis = new FileInputStream(src);
			while(fis.read(tbyte)!=-1){
				rbyte[cnt] = tbyte[0];
				cnt++;
			}
		}catch(FileNotFoundException ecp){
			System.out.println("檔案不存在!");
		}catch(IOException ecp){
			System.out.println("I/O錯誤!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rbyte;
	}
	
}
