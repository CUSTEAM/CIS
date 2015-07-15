package tw.edu.chit.struts.action.studaffair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

import tw.edu.chit.model.Empl;
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

public class TimeOffAlert4PAction  extends BaseLookupDispatchAction{
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
			
		setContentPage(session, "studaffair/TimeOffAlert4P.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		String WeekNo  = aForm.getString("WeekNo");
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
		String threshold1  = aForm.getString("threshold1");
		String threshold2  = aForm.getString("threshold2");
		String threshold3  = aForm.getString("threshold3");
		String letterType1 = aForm.getString("letterType1");
		String letterType2 = aForm.getString("letterType2");
		String letterType3 = aForm.getString("letterType3");
		String pmode  = aForm.getString("pmode");
		boolean forceMail = false;
		if(pmode.equalsIgnoreCase("0")){
			forceMail = true;
		}
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);
		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		
		boolean setupPrinter = false;
		boolean isPrint = false;
		
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
			
		if(!Toolket.isNumeric(threshold1)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次1只能輸入數字"));
		}
		
		if(!Toolket.isNumeric(threshold2)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次2只能輸入數字"));
		}
		
		if(!Toolket.isNumeric(threshold3)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次3只能輸入數字"));
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
			//SAF-TimeOffAlert1  曠缺嚴重家長通知
			//SAF-TimeOffAlert2  曠缺通知郵寄單
			//SAF-TimeOffAlert3  曠缺通知存根
			String reportSourceFile1 = "/WEB-INF/reports/SAF-TimeOffAlert1.jrxml";
			String reportCompiledFile1 = "/WEB-INF/reports/SAF-TimeOffAlert1.jasper";
			String reportSourceFile2 = "/WEB-INF/reports/SAF-TimeOffAlert2.jrxml";
			String reportCompiledFile2 = "/WEB-INF/reports/SAF-TimeOffAlert2.jasper";
			String reportSourceFile3 = "/WEB-INF/reports/SAF-TimeOffAlert3.jrxml";
			String reportCompiledFile3 = "/WEB-INF/reports/SAF-TimeOffAlert3.jasper";
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
			String parameter = clazzFilter + "|" + DateStart + "|" + DateEnd + "|" + threshold1 +
								"|" + threshold2 + "|" + threshold3;
			List<Progress> prgList = sm.findProgress(this.getClass().getName());
			
			//自動判斷是否為第一次列印 , 修改日:2008/12/03
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
								if(parameters[6].trim().equals("0")){
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
			
			//List tfList = sm.findDilgAlert4P(DateStart, DateEnd, clazzFilter,
			//				threshold1,threshold2,threshold3,pmode);
			List tfList = new ArrayList();
			try{
			tfList = sm.findDilgOneAlert4P(DateStart, DateEnd, clazzFilter,
					threshold1, threshold2, threshold3, letterType1,
					letterType2, letterType3, pmode);
			}catch (Exception e){
				e.printStackTrace();
			}
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print preview Report records size:" + tfList.size());
			List tfList2 = new ArrayList();
			for(ListIterator<Map> tfIter=tfList.listIterator(); tfIter.hasNext();){
				Map tMap = new HashMap();
				tMap = tfIter.next();
				if(tMap.get("printable").toString().trim().equals("1")){
					tfList2.add(tMap);
				}
			}
			
			if (!tfList2.isEmpty()) {
				String studentNo="";
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				File reportFile2 = new File(context.getRealPath(reportCompiledFile2));
				File reportFile3 = new File(context.getRealPath(reportCompiledFile3));
				
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile1));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile2));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile3));
					reportFile1 = new File(context
							.getRealPath(reportCompiledFile1));
					reportFile2 = new File(context
							.getRealPath(reportCompiledFile2));
					reportFile3 = new File(context
							.getRealPath(reportCompiledFile3));
					
					if (!reportFile1.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert1.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile2.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert2.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile3.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert3.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport1 = (JasperReport) JRLoader
						.loadObject(reportFile1.getPath());
				
				JasperReport jasperReport2 = (JasperReport) JRLoader
						.loadObject(reportFile2.getPath());
				
				JasperReport jasperReport3 = (JasperReport) JRLoader
						.loadObject(reportFile3.getPath());
				
				List<Object> printData1 = new ArrayList<Object>();
				List<Object> printData2 = new ArrayList<Object>();
				List<Object> printData3 = new ArrayList<Object>();
				Map<String, String> parameters1 = new HashMap<String, String>();
				Map<String, String> parameters2 = new HashMap<String, String>();
				Map<String, String> parameters3 = new HashMap<String, String>();

				String[] fields1 = { "tfDate", "raiseFlag",
						"timeOff"};
				String[] fields2 = { "serialNo", "parentName",
						"address"};
				String[] fields3 = { "serialNo", "deptClassName",
						"studentNo", "studentName", "parentName", "timeOffSum",
						"raiseFlagSum", "Memo"};
				
				String schoolName = "";
				String schoolAddress = "";
				String schoolPost = "";
				String schoolTEL = "";
				
				switch(campus.charAt(0)){
				case '1':
					schoolName = Toolket.getSysParameter("SchoolName_Taipei");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Taipei");
					schoolPost = Toolket.getSysParameter("SchoolPost_Taipei");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Taipei");
					break;
				case '2':
					schoolName = Toolket.getSysParameter("SchoolName_Hsinchu");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Hsinchu");
					schoolPost = Toolket.getSysParameter("SchoolPost_Hsinchu");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Hsinchu");
					break;
				default:
					schoolName = Toolket.getSysParameter("SchoolName_Taipei");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Taipei");
					schoolPost = Toolket.getSysParameter("SchoolPost_Taipei");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Taipei");
					break;
				}
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				//String ranDir2 = "/WEB-INF/reports/temp/" + ran;
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				String fID = credential.getMember().getIdno().substring(1) + "_" + ran + "_";
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				File oft = new File(context
						.getRealPath(ranDir + "TimeOffAlert" + fID + "1.pdf"));
				if(oft.exists()) {
					oft.delete();
					oft.createNewFile();
				}
				else oft.createNewFile();
					
				int pcount = 0;
				
				
				String[] stuNos = new String[tfList2.size()];
				int t =0;
				//處理曠缺嚴重家長通知
				for(ListIterator<Map> tfIter=tfList2.listIterator(); tfIter.hasNext();){
					Map tMap = new HashMap();
					tMap = tfIter.next();
					//if(tMap.get("printable").toString().trim().equals("1")){
						studentNo = tMap.get("studentNo").toString();
						parameters1.put("schoolName", schoolName);
						parameters1.put("schoolAddress", schoolAddress);
						//parameters1.put("schoolPost", schoolPost);
						//parameters1.put("schoolTEL", schoolTEL);
						parameters1.put("studentName", tMap.get("studentName").toString());
						parameters1.put("deptClassName", tMap.get("deptClassName").toString());
						parameters1.put("studentNo", studentNo);
						parameters1.put("timeOffSum", tMap.get("timeOffSum").toString());
						parameters1.put("raiseFlagSum", tMap.get("raiseFlagSum").toString());
						//parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
						parameters1.put("weekNo", WeekNo);
						//parameters1.put("DateStart", DateStart);
						//parameters1.put("DateEnd", DateEnd);
						parameters1.put("mailLevel", tMap.get("mailLevel").toString());
						parameters1.put("postNo", tMap.get("postNo").toString());
						parameters1.put("address", tMap.get("address").toString());
						parameters1.put("parentName", tMap.get("parentName").toString());
						parameters1.put("serialNo", tMap.get("serialNo").toString());
						
						//log.debug("Type of Map(wdilgList):" + tMap.get("wdilgList"));
						List wdList = (List)tMap.get("wdilgList");
						printData1 = fillPrintData(wdList, fields1);
						
						JasperPrint jasperPrint1 = JasperFillManager.fillReport(
								jasperReport1, parameters1,
								new HibernateQueryResultDataSource(printData1,
										fields1));
						byte[] bytes1 = JasperRunManager.runReportToPdf(
								jasperReport1, parameters1,
								new HibernateQueryResultDataSource(printData1,
										fields1));
						
						File outf = File.createTempFile(studentNo, ".pdf");
						stuNos[t]=outf.getAbsolutePath();
						//log.debug(stuNos[t]);
						t++;
						
						OutputStream os1 = new BufferedOutputStream(
								new FileOutputStream(outf));
						JasperExportManager.exportReportToPdfStream(
								jasperPrint1, os1);
						if(os1!=null) os1.close();
					//}
				}
				/*
					if(pcount > 0){
						FileInputStream fis = new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert"+fID+"1.pdf"));
						FileOutputStream fos = new FileOutputStream(context
								.getRealPath(ranDir + "TimeOffAlertTmp"+fID+".pdf"));
						this.cp(fis, fos);
						fis.close();
						fos.close();
							
					}
				*/	
					try {
					    List<InputStream> pdfs = new ArrayList<InputStream>();
						
					    /*
					    FilenameFilter filter = new nameFilter(tempdir, ".pdf");
						File[] stuFile = tempdir.listFiles(filter);
					    for(int i=0; i<stuFile.length; i++){
						    pdfs.add(new FileInputStream(stuFile[i].getAbsolutePath()));
					    }
						*/
						for(int i=0; i<stuNos.length; i++){
						    pdfs.add(new FileInputStream(stuNos[i]));
							
						}
					    /*
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlertStu"+fID+".pdf")));
					    if(pcount > 0){
						    pdfs.add(new FileInputStream(context
									.getRealPath(ranDir + "TimeOffAlertTmp"+fID+".pdf")));
					    }
					    */	
						File of1 = new File(context
								.getRealPath(ranDir + "TimeOffAlert" + fID + "1.pdf"));
						if(of1.exists()) {
							of1.delete();
							of1.createNewFile();
						}
						else of1.createNewFile();
							
					    OutputStream output = new FileOutputStream(context
								.getRealPath(ranDir + "TimeOffAlert" + fID + "1.pdf"));
					    this.concatPDFs(pdfs, output, true);
					    
					    output.close();
				        
					} catch (Exception e) {
					    System.out.println("Merge PDFs Fail!");
				        e.printStackTrace();
					}	
					//pcount++;
					
				    //刪除暫存檔及目錄
				    for(int i=0; i<stuNos.length; i++){
				    	File delFile = new File(stuNos[i]);
				    	delFile.deleteOnExit();
				    }
					/*
				    for(int i=0; i<stuNos.length; i++){
				    	File delFile = new File(tempdir.getPath() + File.separator +
					    		"TimeOffAlertStu"+stuNos[i]+".pdf");
				    	if(delFile.exists()) 
				    		if(!delFile.delete()) log.debug("File not delete:" + delFile.getPath());
				    }
			        if(tempdir.exists()) tempdir.delete();
					*/
					
				//依照列印模式決定是否更改 DilgMail 紀錄, 並發送 E-mail給導師及學務單位
				log.debug("updateDilgMail?=" + pmode);
				if(pmode.equals("0") || forceMail){
					List dmList = new ArrayList();
					for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
						Map tfMap = (Map)tfIter.next();
						Map dmMap = new HashMap();
						dmMap.put("studentNo", tfMap.get("studentNo"));
						dmMap.put("period", tfMap.get("timeOffSum"));
						dmMap.put("raiseflag", tfMap.get("raiseFlagSum"));
						dmMap.put("threshold", tfMap.get("threshold"));
						dmList.add(dmMap);
					}

					if(pmode.equals("0")){
						ActionMessages message = sm.createOrModifyDilgMail(dmList);
						if(!message.isEmpty()){
							messages.add(message);
						}
					}
					
					//TODO: 發送 E-mail給導師及學務單位
					String prevClass = ((Map)tfList2.get(0)).get("departClass").toString();
					Empl tutor = new Empl();
					String clazz = "";
					String prevClazzName = ((Map)tfList2.get(0)).get("deptClassName").toString();
					String email = "";
					Map dmMap = new HashMap();
					dmList = new ArrayList();
					List<Map> samList = new ArrayList<Map>();
					for(Iterator tfIter=tfList2.iterator(); tfIter.hasNext();){
						Map tfMap = (Map)tfIter.next();
						clazz = tfMap.get("departClass").toString();
						if(!clazz.equals(prevClass)){
							email = "";
							Map samMap = new HashMap();
							tutor = Toolket.findTutorOfClass(prevClass);
							if(tutor != null){
								//Send e-mail to Tutor
								if(tutor.getEmail()!=null){
									email = tutor.getEmail().trim();
									if(!email.equals("")){
										new SendEmail2Tutor(null, WeekNo, DateStart, DateEnd, tutor, dmList, false).run();
										//this.sendEmail2Tutor(WeekNo, DateStart, DateEnd, tutor, dmList);
										samMap.put("sendEmail", true);
									}else{
										samMap.put("sendEmail", false);
									}
								}else{
									samMap.put("sendEmail", false);
								}
								//record email content for StudAffair department
								samMap.put("teacherName", tutor.getCname());
							}else{
								samMap.put("teacherName", "沒班導!");
							}
							samMap.put("deptClassName", prevClazzName);
							samMap.put("email", email);
							samMap.put("students", dmList.size());
							samList.add(samMap);
							
							prevClass = clazz;
							prevClazzName = tfMap.get("deptClassName").toString();
							dmList = new ArrayList();
							dmMap = new HashMap();
							dmMap.put("deptClassName", tfMap.get("deptClassName"));
							dmMap.put("studentNo", tfMap.get("studentNo"));
							dmMap.put("studentName", tfMap.get("studentName"));
							dmMap.put("timeOffSum", tfMap.get("timeOffSum"));
							dmMap.put("mailLevel", tfMap.get("mailLevel"));
							dmList.add(dmMap);
						}else{
							dmMap = new HashMap();
							dmMap.put("deptClassName", tfMap.get("deptClassName"));
							dmMap.put("studentNo", tfMap.get("studentNo"));
							dmMap.put("studentName", tfMap.get("studentName"));
							dmMap.put("timeOffSum", tfMap.get("timeOffSum"));
							dmMap.put("mailLevel", tfMap.get("mailLevel"));
							dmList.add(dmMap);
						}
					}
					email = "";
					Map samMap = new HashMap();
					tutor = Toolket.findTutorOfClass(prevClass);
					if(tutor != null){
						//Send e-mail to Tutor
						if(tutor.getEmail()!=null){
							email = tutor.getEmail().trim();
							if(!email.equals("")){
								new SendEmail2Tutor(null, WeekNo, DateStart, DateEnd, tutor, dmList, false).run();
								//this.sendEmail2Tutor(WeekNo, DateStart, DateEnd, tutor, dmList);
								samMap.put("sendEmail", true);
							}else{
								samMap.put("sendEmail", false);
							}
						}else{
							samMap.put("sendEmail", false);
						}
						//record email content for StudAffair department
						samMap.put("teacherName", tutor.getCname());
					}else{
						samMap.put("teacherName", "沒班導!");
					}
					samMap.put("deptClassName", prevClazzName);
					samMap.put("email", email);
					samMap.put("students", dmList.size());
					samList.add(samMap);

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
						for(String mail:recs){
							if(!mail.trim().equals("")){
								receipts += mail + ",";
							}
						}
						receipts = receipts + IConstants.SAFAdminEmail;
					}
					new SendEmail2SAF(null, WeekNo, DateStart, DateEnd, samList, receipts, false).run();
					//this.sendEmail2SAF(WeekNo, DateStart, DateEnd, samList, receipts);
				}
				
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()) messages.add(clrTF4P);
				
				parameters2.put("schoolName", schoolName);
				parameters2.put("schoolAddress", schoolAddress);
				parameters2.put("schoolTEL", schoolTEL);
				
				parameters3.put("weekNo", WeekNo);
				parameters3.put("dateStart", DateStart);
				parameters3.put("dateEnd", DateEnd);
				parameters3.put("PrintDate", Toolket.Date2Str(new Date()));
					
				//printData2 只列印掛號郵件執據
				List tmpList = new ArrayList();
				List tmpList3 = new ArrayList();
				
				for(Iterator<Map> tfIter=tfList.iterator(); tfIter.hasNext();){
					Map tfMap = tfIter.next();
					if(tfMap.get("printable").toString().trim().equals("1")){
						tmpList3.add(tfMap);
						if(tfMap.get("mailLevel").toString().equals("1")){
							tmpList.add(tfMap);
						}
					}
				}
				printData2 = fillPrintData(tmpList,fields2);
				
				printData3 = fillPrintData(tmpList3,fields3);			
								
				JasperPrint jasperPrint2 = JasperFillManager.fillReport(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
				
				JasperPrint jasperPrint3 = JasperFillManager.fillReport(
						jasperReport3, parameters3,
						new HibernateQueryResultDataSource(printData3,
								fields3));
				// jasperPrint.
				//Merge all PDF file
					
				byte[] bytes2 = JasperRunManager.runReportToPdf(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
					
				byte[] bytes3 = JasperRunManager.runReportToPdf(
						jasperReport3, parameters3,
						new HibernateQueryResultDataSource(printData3,
								fields3));
					
				OutputStream os2 = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "TimeOffAlert" + fID + "2.pdf"))));
				OutputStream os3 = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "TimeOffAlert" + fID + "3.pdf"))));
					
				JasperExportManager.exportReportToPdfStream(
						jasperPrint2, os2);
				JasperExportManager.exportReportToPdfStream(
						jasperPrint3, os3);
					
				if(os2!=null) os2.close();
				if(os3!=null) os3.close();
					
				//Merge TimeOffAlert[999999999]1.pdf, 
				//TimeOffAlert[999999999]2.pdf,TimeOffAlert[999999999]3.pdf
				//to TimeOffAlert4P[999999999].pdf
				try {
				    List<InputStream> pdfs = new ArrayList<InputStream>();
				    pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "TimeOffAlert"+fID+"1.pdf")));
				    pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "TimeOffAlert"+fID+"2.pdf")));
				    pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "TimeOffAlert"+fID+"3.pdf")));
				    	
					File of1 = new File(context
							.getRealPath(ranDir + "TimeOffAlert4P" + fID + ".pdf"));
					if(of1.exists()) {
						of1.delete();
						of1.createNewFile();
					}
					else of1.createNewFile();
						
				    OutputStream output = new FileOutputStream(context
							.getRealPath(ranDir + "TimeOffAlert4P" + fID + ".pdf"));
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
			}else{
				if(pmode.equals("0")){
					List dmList = new ArrayList();
					for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
						Map tfMap = (Map)tfIter.next();
						Map dmMap = new HashMap();
						dmMap.put("studentNo", tfMap.get("studentNo"));
						dmMap.put("period", tfMap.get("timeOffSum"));
						dmMap.put("raiseflag", tfMap.get("raiseFlagSum"));
						dmMap.put("threshold", tfMap.get("threshold"));
						dmList.add(dmMap);
					}

					ActionMessages message = sm.createOrModifyDilgMail(dmList);
					if(!message.isEmpty()){
						messages.add(message);
					}
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()){
					messages.add(clrTF4P);
					return mapping.findForward("Main");
				}

				saveMessages(request, messages);
				return mapping.findForward("NoData");
			}
		}
		
		return mapping.findForward(null);
		//return null;		
	}


	/*
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
				
		DynaActionForm aForm = (DynaActionForm) form;
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		String WeekNo  = aForm.getString("WeekNo");
		String DateStart  = aForm.getString("DateStart");
		String DateEnd  = aForm.getString("DateEnd");
		String threshold1  = aForm.getString("threshold1");
		String threshold2  = aForm.getString("threshold2");
		String threshold3  = aForm.getString("threshold3");
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
			
		if(!Toolket.isNumeric(threshold1)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次1只能輸入數字"));
		}
		
		if(!Toolket.isNumeric(threshold2)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次2只能輸入數字"));
		}
		
		if(!Toolket.isNumeric(threshold3)){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"MessageN1", "節次3只能輸入數字"));
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
			//SAF-TimeOffAlert1  曠缺嚴重家長通知
			//SAF-TimeOffAlert2  曠缺通知郵寄單
			//SAF-TimeOffAlert3  曠缺通知存根
			String reportSourceFile1 = "/WEB-INF/reports/SAF-TimeOffAlert1.jrxml";
			String reportCompiledFile1 = "/WEB-INF/reports/SAF-TimeOffAlert1.jasper";
			String reportSourceFile2 = "/WEB-INF/reports/SAF-TimeOffAlert2.jrxml";
			String reportCompiledFile2 = "/WEB-INF/reports/SAF-TimeOffAlert2.jasper";
			String reportSourceFile3 = "/WEB-INF/reports/SAF-TimeOffAlert3.jrxml";
			String reportCompiledFile3 = "/WEB-INF/reports/SAF-TimeOffAlert3.jasper";

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
			String parameter = clazzFilter + "|" + DateStart + "|" + DateEnd + "|" + threshold1 +
								"|" + threshold2 + "|" + threshold3;
			List<Progress> prgList = sm.findProgress(this.getClass().getName());
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
								pmode = "1";
							}else if(parameters[0].startsWith(clazzFilter)){
								messages.add(ActionMessages.GLOBAL_MESSAGE, 
										new ActionMessage("MessageN1", "您之前已處理過部制:" + parameters[0] +" !請電洽電算中心處理"));
								saveMessages(request, messages);
								return mapping.findForward("NoData");
							}
						}
					}
				}
				//no progress is running with parameter clazzFilter
				
			}
			parameter = parameter + "|" + pmode;
			//register this progress in DB
			Progress tf4p = sm.setTF4PProgress(this.getClass().getName(), parameter);
			if(tf4p == null){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "程式正在執行中,請稍後再使用!或請電洽電算中心處理"));
				saveMessages(request, messages);
				return mapping.findForward("Main");
			}

			//List tfList = sm.findDilgAlert4P(DateStart, DateEnd, clazzFilter,
			//				threshold1,threshold2,threshold3,pmode);
			List tfList = sm.findDilgOneAlert4P(DateStart, DateEnd, clazzFilter,
					threshold1,threshold2,threshold3,pmode);
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + tfList.size());
			Map fMap = (Map)tfList.get(0);
			if (!tfList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				File reportFile2 = new File(context.getRealPath(reportCompiledFile2));
				File reportFile3 = new File(context.getRealPath(reportCompiledFile3));
				
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile1));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile2));
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile3));
					reportFile1 = new File(context
							.getRealPath(reportCompiledFile1));
					reportFile2 = new File(context
							.getRealPath(reportCompiledFile2));
					reportFile3 = new File(context
							.getRealPath(reportCompiledFile3));
					
					if (!reportFile1.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert1.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile2.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert2.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile3.exists())
						throw new JRRuntimeException(
								"查無\"SAF-TimeOffAlert3.jasper\"檔案，請電洽電算中心，謝謝！！");
				// }

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport1 = (JasperReport) JRLoader
						.loadObject(reportFile1.getPath());
				
				JasperReport jasperReport2 = (JasperReport) JRLoader
						.loadObject(reportFile2.getPath());
				
				JasperReport jasperReport3 = (JasperReport) JRLoader
						.loadObject(reportFile3.getPath());
				
				// JRField[] jrField = jasperReport.getFields();
				// for (JRField field : jrField) {
				// System.out.println("Name:" + field.getName());
				// JRPropertiesMap x = field.getPropertiesMap();
				// System.out.println();
				// }
				List<Object> printData1 = new ArrayList<Object>();
				List<Object> printData2 = new ArrayList<Object>();
				List<Object> printData3 = new ArrayList<Object>();
				Map<String, String> parameters1 = new HashMap<String, String>();
				Map<String, String> parameters2 = new HashMap<String, String>();
				Map<String, String> parameters3 = new HashMap<String, String>();

				String[] fields1 = { "tfDate", "raiseFlag",
						"timeOff"};
				String[] fields2 = { "serialNo", "parentName",
						"address"};
				String[] fields3 = { "serialNo", "deptClassName",
						"studentNo", "studentName", "parentName", "timeOffSum",
						"raiseFlagSum", "Memo"};
				
				String schoolName = "";
				String schoolAddress = "";
				String schoolPost = "";
				String schoolTEL = "";
				
				switch(campus.charAt(0)){
				case '1':
					schoolName = Toolket.getSysParameter("SchoolName_Taipei");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Taipei");
					schoolPost = Toolket.getSysParameter("SchoolPost_Taipei");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Taipei");
					break;
				case '2':
					schoolName = Toolket.getSysParameter("SchoolName_Hsinchu");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Hsinchu");
					schoolPost = Toolket.getSysParameter("SchoolPost_Hsinchu");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Hsinchu");
					break;
				default:
					schoolName = Toolket.getSysParameter("SchoolName_Taipei");
					schoolAddress = Toolket.getSysParameter("SchoolAddress_Taipei");
					schoolPost = Toolket.getSysParameter("SchoolPost_Taipei");
					schoolTEL = Toolket.getSysParameter("SchoolTEL_Taipei");
					break;
				}

				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				//String ranDir2 = "/WEB-INF/reports/temp/" + ran;
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				String fID = credential.getMember().getIdno().substring(1) + "_" + ran + "_";
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();
				
				int pcount = 0;
				//處理曠缺嚴重家長通知
				for(Iterator<Map> tfIter=tfList.iterator(); tfIter.hasNext();){
					Map tMap = tfIter.next();
					if(tMap.get("printable").toString().trim().equals("1")){
						parameters1.put("schoolName", schoolName);
						parameters1.put("schoolAddress", schoolAddress);
						parameters1.put("schoolPost", schoolPost);
						parameters1.put("schoolTEL", schoolTEL);
						parameters1.put("studentName", tMap.get("studentName").toString());
						parameters1.put("deptClassName", tMap.get("deptClassName").toString());
						parameters1.put("studentNo", tMap.get("studentNo").toString());
						parameters1.put("timeOffSum", tMap.get("timeOffSum").toString());
						parameters1.put("raiseFlagSum", tMap.get("raiseFlagSum").toString());
						parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
						parameters1.put("weekNo", WeekNo);
						parameters1.put("DateStart", DateStart);
						parameters1.put("DateEnd", DateEnd);
						parameters1.put("mailLevel", tMap.get("mailLevel").toString());
						parameters1.put("postNo", tMap.get("postNo").toString());
						parameters1.put("address", tMap.get("address").toString());
						parameters1.put("parentName", tMap.get("parentName").toString());
						parameters1.put("serialNo", tMap.get("serialNo").toString());
						
						List wdList = (List)tMap.get("wdilgList");
						printData1 = fillPrintData(wdList, fields1);
						
						JasperPrint jasperPrint1 = JasperFillManager.fillReport(
								jasperReport1, parameters1,
								new HibernateQueryResultDataSource(printData1,
										fields1));
						if (isPrint) {
							//log.debug("Print Report records size:" + tfList.size());
							
							JasperPrintManager.printReport(jasperPrint1,
									setupPrinter);						
						} else {
							byte[] bytes1 = JasperRunManager.runReportToPdf(
									jasperReport1, parameters1,
									new HibernateQueryResultDataSource(printData1,
											fields1));
								
							OutputStream os1 = new BufferedOutputStream(
									new FileOutputStream(new File(context
											.getRealPath(ranDir + "TimeOffAlertStu"+fID+".pdf"))));
							JasperExportManager.exportReportToPdfStream(
									jasperPrint1, os1);
							if(os1!=null) os1.close();
							if(pcount > 0){
								FileInputStream fis = new FileInputStream(context
										.getRealPath(ranDir + "TimeOffAlert"+fID+"1.pdf"));
								FileOutputStream fos = new FileOutputStream(context
										.getRealPath(ranDir + "TimeOffAlertTmp"+fID+".pdf"));
								this.cp(fis, fos);
								fis.close();
								fos.close();
									
							}
								
							try {
							    List<InputStream> pdfs = new ArrayList<InputStream>();
							    pdfs.add(new FileInputStream(context
										.getRealPath(ranDir + "TimeOffAlertStu"+fID+".pdf")));
							    if(pcount > 0){
								    pdfs.add(new FileInputStream(context
											.getRealPath(ranDir + "TimeOffAlertTmp"+fID+".pdf")));
							    }
							    	
								File of1 = new File(context
										.getRealPath(ranDir + "TimeOffAlert" + fID + "1.pdf"));
								if(of1.exists()) {
									of1.delete();
									of1.createNewFile();
								}
								else of1.createNewFile();
									
							    OutputStream output = new FileOutputStream(context
										.getRealPath(ranDir + "TimeOffAlert" + fID + "1.pdf"));
							    this.concatPDFs(pdfs, output, true);
							    output.close();
							    
							} catch (Exception e) {
							    System.out.println("Merge PDFs Fail!");
						        e.printStackTrace();
							}	
							pcount++;
						    //JasperPrintManager.
							//JasperReportUtils.printPdfToFrontEnd(response, bytes1);
							
						}
					}
					
				}
				
				parameters2.put("schoolName", schoolName);
				parameters2.put("schoolAddress", schoolAddress);
				parameters2.put("schoolTEL", schoolTEL);
				
				parameters3.put("weekNo", WeekNo);
				parameters3.put("dateStart", DateStart);
				parameters3.put("dateEnd", DateEnd);
				parameters3.put("PrintDate", Toolket.Date2Str(new Date()));
								
				//printData2 只列印掛號郵件執據
				List tmpList = new ArrayList();
				List tmpList3 = new ArrayList();
				
				for(Iterator<Map> tfIter=tfList.iterator(); tfIter.hasNext();){
					Map tfMap = tfIter.next();
					if(tfMap.get("mailLevel").toString().equals("1")){
						tmpList.add(tfMap);
					}
					if(tfMap.get("printable").toString().trim().equals("1")){
						tmpList3.add(tfMap);
					}
				}
				printData2 = fillPrintData(tmpList,fields2);
				
				printData3 = fillPrintData(tmpList3,fields3);			
								
				JasperPrint jasperPrint2 = JasperFillManager.fillReport(
						jasperReport2, parameters2,
						new HibernateQueryResultDataSource(printData2,
								fields2));
				
				JasperPrint jasperPrint3 = JasperFillManager.fillReport(
						jasperReport3, parameters3,
						new HibernateQueryResultDataSource(printData3,
								fields3));
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					//log.debug("Print Report records size:" + tfList.size());
					
					JasperPrintManager.printReport(jasperPrint2,
							setupPrinter);						
					JasperPrintManager.printReport(jasperPrint3,
							setupPrinter);						
				} else {
					//Merge all PDF file
					
					byte[] bytes2 = JasperRunManager.runReportToPdf(
							jasperReport2, parameters2,
							new HibernateQueryResultDataSource(printData2,
									fields2));
					
					byte[] bytes3 = JasperRunManager.runReportToPdf(
							jasperReport3, parameters3,
							new HibernateQueryResultDataSource(printData3,
									fields3));
					
					OutputStream os2 = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath(ranDir + "TimeOffAlert" + fID + "2.pdf"))));
					OutputStream os3 = new BufferedOutputStream(
							new FileOutputStream(new File(context
									.getRealPath(ranDir + "TimeOffAlert" + fID + "3.pdf"))));
					
					JasperExportManager.exportReportToPdfStream(
							jasperPrint2, os2);
					JasperExportManager.exportReportToPdfStream(
							jasperPrint3, os3);
					
					if(os2!=null) os2.close();
					if(os3!=null) os3.close();
					
					//Merge TimeOffAlert[999999999]1.pdf, 
					//TimeOffAlert[999999999]2.pdf,TimeOffAlert[999999999]3.pdf
					//to TimeOffAlert4P[999999999].pdf
					try {
					    List<InputStream> pdfs = new ArrayList<InputStream>();
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert"+fID+"1.pdf")));
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert"+fID+"2.pdf")));
					    pdfs.add(new FileInputStream(context
								.getRealPath(ranDir + "TimeOffAlert"+fID+"3.pdf")));
					    	
						File of1 = new File(context
								.getRealPath(ranDir + "TimeOffAlert4P" + fID + ".pdf"));
						if(of1.exists()) {
							of1.delete();
							of1.createNewFile();
						}
						else of1.createNewFile();
							
					    OutputStream output = new FileOutputStream(context
								.getRealPath(ranDir + "TimeOffAlert4P" + fID + ".pdf"));
					    this.concatPDFs(pdfs, output, true);
						byte[] bytes = file2byte(of1);
						JasperReportUtils.printPdfToFrontEnd(response, bytes);
						output.close();
						Toolket.deleteDIR(tempdir);
					} catch (Exception e) {
					    System.out.println("Merge PDFs Fail!");
				        e.printStackTrace();
					}
					
					return null;
				}
				
				if(isPrint){
					//依照列印模式決定是否更改 DilgMail 紀錄
					log.debug("updateDilgMail?=" + pmode);
					if(pmode.equals("0")){
						List dmList = new ArrayList();
						for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
							Map tfMap = (Map)tfIter.next();
							Map dmMap = new HashMap();
							dmMap.put("studentNo", tfMap.get("studentNo"));
							dmMap.put("period", tfMap.get("timeOffSum"));
							dmMap.put("raiseflag", tfMap.get("raiseFlagSum"));
							dmMap.put("threshold", tfMap.get("threshold"));
							dmList.add(dmMap);
						}

						ActionMessages message = sm.createOrModifyDilgMail(dmList);
						if(!message.isEmpty()){
							messages.add(message);
						}
					}
					
					ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
					if(!clrTF4P.isEmpty()) messages.add(clrTF4P);
					
				}
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "查無符合的資料!!!"));
				ActionMessages clrTF4P = sm.resetTF4PProgress(tf4p);
				if(!clrTF4P.isEmpty()){
					messages.add(clrTF4P);
					return mapping.findForward("Main");
				}

				saveMessages(request, messages);
				return mapping.findForward("NoData");
			}			
		}
		
		setContentPage(request.getSession(false), "studaffair/TimeOffAlert4P.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}
	*/

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
	
	class nameFilter implements FilenameFilter {
		String fileExtention;
		File dir;
		public nameFilter(File dir, String fileExtention){
			this.dir = dir;
			this.fileExtention = fileExtention;
		}
		
		public boolean accept(File path, String filename){
			if(path.getAbsolutePath().equalsIgnoreCase(dir.getAbsolutePath()) && filename.endsWith(fileExtention))
				return true;
			else
				return false;
		}
	}
	
	private void sendEmail2Tutor_old(String WeekNo, String DateStart, String DateEnd, Empl tutor, List tfmsgs){
		String err = "";
		String teacherName = tutor.getCname();
		
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String to = tutor.getEmail();
		//String to = "jason034@cc.cust.edu.tw";
		String sysAdmin = "jason034@cc.cust.edu.tw";
		Map tMap = new HashMap();
		
		String className = ((Map)tfmsgs.get(0)).get("deptClassName").toString();
		
		String Subject = "中華科技大學 " + className + " 學生曠缺嚴重通知! 日期:" + DateStart + " ~ " + DateEnd;
		String type = "text/html";
		
		String message = "<b>" + teacherName + "&nbsp;導師  您好:<br>";
		message += "以下是 " + className + " 第 " + WeekNo + " 週 ，自:" +  DateStart + " 至 " + DateEnd + " 曠缺較多,需輔導學生名單:<br><br>";
		message += "<table><tr><th>學號</th><th>姓名</th><th>曠缺節次</th></tr>";
		for(ListIterator<Map> tfIter=tfmsgs.listIterator(); tfIter.hasNext();){
			tMap = tfIter.next();
			message += "<tr>";
			message += "<td>" + tMap.get("studentNo").toString() + "</td>";
			message += "<td>" + tMap.get("studentName").toString() + "</td>";
			message += "<td>" + tMap.get("timeOffSum").toString() + "</td>";
			message += "</tr>";
		}
		message += "</table>";
		message += "<br><font color=red>請您務必記得加以輔導並填寫輔導記錄!!!</font><br><br>";
		message += "<br><font color=blue>這封E-mail是由曠缺報表系統發送的，請勿回覆!!!</font><br>";
		message += "<font color=\"#0000FF\" face=\"新細明體\" style=\"font-weight:500;\">中華科技大學 學務單位</font>";
		
		try{
			//log.debug("send email from:" + From + "\n To:" + to + "\n: mesg:" + message);
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailserver);
			props.put("mail.debug", true);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			
			javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, auth);
			mailSession.setDebug(sessionDebug);
			MimeMessage msg = new MimeMessage(mailSession);
			
			msg.setFrom(new InternetAddress(From));
			address = InternetAddress.parse(to, false);
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(Subject);
			msg.setSentDate(new Date());
			//msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			//mbp.setContent(message, type+";charset=MS950");
			mbp.setContent(message, type+";charset=UTF-8");
			mp.addBodyPart(mbp);
			//msg.setContent(mp, type+";charset=MS950");
			msg.setContent(mp, type+";charset=UTF-8");
			//Store store = mailSession.getStore("pop3");
			//store.connect("seastar.com.tw", "mailer", "emai168mvc");
			Transport transport = mailSession.getTransport();
	        transport.connect();
			Transport.send(msg);
	        transport.close();
			//store.close();
			
		}catch(MessagingException mex){
			err = mex.toString();
			mex.printStackTrace();
		}
	}

	private static class SendEmail2Tutor implements Runnable {

		Logger log = Logger.getLogger(SendEmail2Tutor.class);

		private List<File> files = null;
		private Empl tutor = null;
		private String RETURN = "\n";
		private String WeekNo = "";
		private String DateStart = "";
		private String DateEnd = "";
		private List tfmsgs = null;
		// private String SPACE = " ";
		private boolean isDebug = false;

		SendEmail2Tutor(List<File> files, String WeekNo, String DateStart, String DateEnd,  Empl tutor, List tfmsgs, boolean isDebug) {
			this.files = files;
			this.DateStart = DateStart;
			this.DateEnd = DateEnd;
			this.tfmsgs = tfmsgs;
			this.tutor = tutor;
			this.isDebug = isDebug;
		}

		public void run() {
			String err = "";
			String teacherName = tutor.getCname();
			
			InternetAddress[] address = null;
			boolean sessionDebug = false;
			String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
			String From = "cc@www.cust.edu.tw";
			String to = tutor.getEmail();
			//String to = "jason034@cc.cust.edu.tw";
			String sysAdmin = "jason034@cc.cust.edu.tw";
			Map tMap = new HashMap();
			
			String className = ((Map)tfmsgs.get(0)).get("deptClassName").toString();
			
			String Subject = "中華科技大學 " + className + " 學生曠缺嚴重通知! 日期:" + DateStart + " ~ " + DateEnd;
			String type = "text/html";
			
			String message = "<b>" + teacherName + "&nbsp;導師  您好:<br>";
			message += "以下是 " + className + " 第 " + WeekNo + " 週 ，自:" +  DateStart + " 至 " + DateEnd + " 曠缺較多,需輔導學生名單:<br><br>";
			message += "<table><tr><th>學號</th><th>姓名</th><th>曠缺節次</th></tr>";
			for(ListIterator<Map> tfIter=tfmsgs.listIterator(); tfIter.hasNext();){
				tMap = tfIter.next();
				message += "<tr>";
				message += "<td>" + tMap.get("studentNo").toString() + "</td>";
				message += "<td>" + tMap.get("studentName").toString() + "</td>";
				message += "<td>" + tMap.get("timeOffSum").toString() + "</td>";
				message += "</tr>";
			}
			message += "</table>";
			message += "<br><font color=red>請您務必記得加以輔導並填寫輔導記錄!!!</font><br><br>";
			message += "<br><font color=blue>這封E-mail是由曠缺報表系統發送的，請勿回覆!!!</font><br>";
			message += "<font color=\"#0000FF\" face=\"新細明體\" style=\"font-weight:500;\">中華科技大學 學務單位</font>";

			
			MultiPartEmail email = new MultiPartEmail();
			email.setCharset("UTF-8");
			email.setSentDate(new Date());
			email.setHostName(IConstants.MAILSERVER_DOMAIN_NAME_WWW);
			email.setAuthentication("cc@www.cust.edu.tw", "577812");
			email.setSubject(Subject);
			email.setDebug(isDebug);

			EmailAttachment attachment = null;
			try {

				email.addTo(to);
				email.addBcc(sysAdmin, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", "中華科技大學 學務單位");
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


	
	private static class SendEmail2SAF implements Runnable {

		Logger log = Logger.getLogger(SendEmail2SAF.class);

		private List<File> files = null;
		private String receipts = null;
		private String RETURN = "\n";
		private String WeekNo = "";
		private String DateStart = "";
		private String DateEnd = "";
		private List<Map> samList = null;
		// private String SPACE = " ";
		private boolean isDebug = false;

		SendEmail2SAF(List<File> files, String WeekNo, String DateStart, String DateEnd,  List<Map> samList, String receipts, boolean isDebug) {
			this.files = files;
			this.DateStart = DateStart;
			this.DateEnd = DateEnd;
			this.samList = samList;
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
			
			String className = ((Map)samList.get(0)).get("deptClassName").toString();
			
			String Subject = "中華科技大學  曠缺嚴重學生 導師聯絡單 第 " + WeekNo + " 週, 日期:" + DateStart + " ~ " + DateEnd;
			String type = "text/html";
			
			String message = "<b>中華科技大學  曠缺嚴重學生 導師聯絡單 第 " + WeekNo + " 週, 日期:" + DateStart + " ~ " + DateEnd + "<br>";
			message += "詳細資料如下:<br><br>";
			message += "<table><tr><th>班級</th><th>導師</th><th>電子郵件帳號</th><th>應輔導人次</th></tr>";
			for(ListIterator<Map> tfIter=samList.listIterator(); tfIter.hasNext();){
				tMap = tfIter.next();
				message += "<tr>";
				message += "<td>" + tMap.get("deptClassName").toString() + "</td>";
				message += "<td>" + tMap.get("teacherName").toString() + "</td>";
				message += "<td>" + tMap.get("email").toString() + "</td>";
				message += "<td>" + tMap.get("students").toString() + "</td>";
				message += "</tr>";
			}
			message += "</table>";
			message += "<br><font color=blue>這封E-mail是由曠缺報表系統發送的!!!</font><br>";

			
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
				//email.addBcc(sysAdmin, "電算中心");
				// 避免被SpamMail過濾掉
				email.setFrom("cc@www.cust.edu.tw", "中華科技大學 學務單位");
				//email.addReplyTo(sysAdmin, "電算中心軟體開發組");
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

	
	private void sendEmail2SAF_old(String WeekNo, String DateStart, String DateEnd,  List<Map> samList, String receipts){
		String err = "";
		String teacherName = "";
		
		InternetAddress[] address = null;
		boolean sessionDebug = false;
		String mailserver = IConstants.MAILSERVER_DOMAIN_NAME_WWW;
		String From = "cc@www.cust.edu.tw";
		String to = receipts;
		String sysAdmin = "jason034@cc.cust.edu.tw";
		Map tMap = new HashMap();
		
		String className = ((Map)samList.get(0)).get("deptClassName").toString();
		
		String Subject = "中華科技大學  曠缺嚴重學生 導師聯絡單 第 " + WeekNo + " 週, 日期:" + DateStart + " ~ " + DateEnd;
		String type = "text/html";
		
		String message = "<b>中華科技大學  曠缺嚴重學生 導師聯絡單 第 " + WeekNo + " 週, 日期:" + DateStart + " ~ " + DateEnd + "<br>";
		message += "詳細資料如下:<br><br>";
		message += "<table><tr><th>班級</th><th>導師</th><th>電子郵件帳號</th><th>應輔導人次</th></tr>";
		for(ListIterator<Map> tfIter=samList.listIterator(); tfIter.hasNext();){
			tMap = tfIter.next();
			message += "<tr>";
			message += "<td>" + tMap.get("deptClassName").toString() + "</td>";
			message += "<td>" + tMap.get("teacherName").toString() + "</td>";
			message += "<td>" + tMap.get("email").toString() + "</td>";
			message += "<td>" + tMap.get("students").toString() + "</td>";
			message += "</tr>";
		}
		message += "</table>";
		message += "<br><font color=blue>這封E-mail是由曠缺報表系統發送的!!!</font><br>";
		
		try{
			//log.debug("send email from:" + From + "\n To:" + to + "\n: mesg:" + message);
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailserver);
			props.put("mail.debug", true);
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			Authenticator auth = new SMTPAuthenticator();
			
			javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, auth);
			mailSession.setDebug(sessionDebug);
			MimeMessage msg = new MimeMessage(mailSession);
			
			msg.setFrom(new InternetAddress(From));
			address = InternetAddress.parse(to, false);
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(Subject);
			msg.setSentDate(new Date());
			//msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sysAdmin, false));
			
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			//mbp.setContent(message, type+";charset=MS950");
			mbp.setContent(message, type+";charset=UTF-8");
			mp.addBodyPart(mbp);
			//msg.setContent(mp, type+";charset=MS950");
			msg.setContent(mp, type+";charset=UTF-8");
			//Store store = mailSession.getStore("pop3");
			//store.connect("seastar.com.tw", "mailer", "emai168mvc");
			Transport transport = mailSession.getTransport();
	        transport.connect();
			Transport.send(msg);
	        transport.close();
			//store.close();
			
		}catch(MessagingException mex){
			err = mex.toString();
			mex.printStackTrace();
		}
	}
	
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication SMTPAuthenticator(){
			return getPasswordAuthentication();
		}
        public PasswordAuthentication getPasswordAuthentication() {
           String username = "cc@www.cust.edu.tw";
           String password = "577812";
           return new PasswordAuthentication(username, password);
        }
    }


}
