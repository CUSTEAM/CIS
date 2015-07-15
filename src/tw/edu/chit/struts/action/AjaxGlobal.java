package tw.edu.chit.struts.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.orm.toplink.SessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Jpeg;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.Barcode39;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import tw.edu.chit.dao.BlobDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StudCounseling;
import tw.edu.chit.model.StudDocDetail;
import tw.edu.chit.model.StudDocUpload;
import tw.edu.chit.model.StudPublicDocUpload;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.student.AjaxGetStudentTimeOff;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.HibernateQueryResultDataSource;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class AjaxGlobal extends HttpServlet{
	
	private Logger log = Logger.getLogger(AjaxGlobal.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method =  request.getParameter("method");
		log.debug("method->" + method + " requested!");
		
		if(method.equals("GetTimeOffSerious")){
			GetTimeOffSerious(request, response);
		}else if(method.equals("DocReport")){
			AMSDocReporter(request, response);
		}else if(method.equals("MeetingAskLeave")){  //Leo-20091211
			AMSMeetingAskLeave(request, response);
		}else if(method.equals("getCounselingReport")){
			try {
				getCounselingReport(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(method.equals("deleteStudDocAttach")){
			StudDocAttachDelete(request, response);
		}else if(method.equals("getDocAttachUpload")){
			//取得學生假單上傳附件圖檔
			getDocAttachUpload(request, response);
		}else if(method.equals("delStudpublicDocAttach")){
			StudPublicDocAttachDelete(request, response);
		}else if(method.equals("getPublicDocAttachUpload")){
			//取得學生假單上傳附件圖檔
			getPublicDocAttachUpload(request, response);
		}
		
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){
			try {
				StudDocAttachUpload(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//log.debug("AjaxGlobal requested!");
			String json = Toolket.readJSONStringFromRequestBody(request);
			JSONObject jsonObj = null;
			String method = null;
			
			try{
				jsonObj = new JSONObject(json);
				method = jsonObj.getString("method");
				log.debug("method->" + method + " requested!");
				
			}catch(JSONException je){
				je.printStackTrace();
			}

			if(!session.isNew()){
				if(method.equals("getTchTimeOffInputData")){
					GetTchTimeOffInputData(request, response, jsonObj);
				}else if(method.equals("getAgent")){
					GetAgent(request, response, jsonObj);
				}else if(method.equals("getStudents")){
					GetStudentsInClazz(request, response, jsonObj);
				}else if(method.equals("getStudent")){
					GetStudent(request, response, jsonObj);
				}else if(method.equals("getCourseByName")){
					getCourseByName(request, response, jsonObj);
				}else if(method.equals("getCourseByCode")){
					getCourseByCode(request, response, jsonObj);
				}else if(method.equals("getStudentPeriodData")){
					getStudentPeriodData(request, response, jsonObj);
				}else if(method.equals("chkStudentInDepart")){
					chkStudentInDepart(request, response, jsonObj);
				}else if(method.equals("chkStudentInClass")){
					chkStudentInClass(request, response, jsonObj);
				}else if(method.equals("getAssessPaper")){
					//Used by AssessPaperReply.jsp
					getAssessPaper(request, response, jsonObj);
				}
				
			}
			
		}
		
	}


	private void getCourseByName(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		ScoreManager sm = (ScoreManager)wac.getBean("scoreManager");
		
		
		String sname = "";
		try{
			sname = jsonObj.getString("sname");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		List<Csno> courses = sm.findCourseByName(sname);
		JSONArray csArray = new JSONArray();
		if(!courses.isEmpty()){
			for(Csno course:courses){
				JSONObject csJson = new JSONObject();
				try {
					csJson.put("cscode", course.getCscode());
					csJson.put("chi_name", course.getChiName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				csArray.put(csJson);
			}
			//log.debug(emplArray.toString());
			out.print(csArray.toString());
			//JSONArray jArray = new JSONArray(empls, true);
			//out.print(jArray.toString());
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}
	
	private void getCourseByCode(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		ScoreManager sm = (ScoreManager)wac.getBean("scoreManager");
		
		
		String sname = "";
		try{
			sname = jsonObj.getString("sname");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		List<Csno> courses = sm.findCourseByCode(sname);
		JSONArray csArray = new JSONArray();
		if(!courses.isEmpty()){
			for(Csno course:courses){
				JSONObject csJson = new JSONObject();
				try {
					csJson.put("cscode", course.getCscode());
					csJson.put("chi_name", course.getChiName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				csArray.put(csJson);
			}
			//log.debug(emplArray.toString());
			out.print(csArray.toString());
			//JSONArray jArray = new JSONArray(empls, true);
			//out.print(jArray.toString());
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}
	
	private void getCounselingReport (HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean setupPrinter = false;
		boolean isPrint = false;
		String reportType = request.getParameter("reportType").trim();
		
		String reportSourceFile = "";
		String reportCompiledFile = "";
		
		if(reportType.equals("1")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingTutorLearn.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingTutorLearn.jasper";
		}else if(reportType.equals("2")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingTutorCareer.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingTutorCareer.jasper";
		}else if(reportType.equals("3")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingTeacherLearn.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingTeacherLearn.jasper";
		}else if(reportType.equals("4")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingStudent.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingStudent.jasper";
		}else if(reportType.equals("5")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingTutorSummary.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingTutorSummary.jasper";
		}else if(reportType.equals("6")){
			reportSourceFile = "/WEB-INF/reports/SAF-CounselingTeacherSummary.jrxml";
			reportCompiledFile = "/WEB-INF/reports/SAF-CounselingTeacherSummary.jasper";
		}
		
		HttpSession session = request.getSession(false);

		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		StudAffairManager sm = (StudAffairManager) wac.getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		
		List counselReport = new ArrayList();
		if(session.getAttribute("CounselingReoport") != null){
			counselReport = (List) session.getAttribute("CounselingReoport");
			
			if(!counselReport.isEmpty()){
				List<Object> printData = new ArrayList<Object>();
				
				ServletContext context = request.getSession().getServletContext();
				JasperReportUtils.initJasperReportsClasspath(request);
				//Map tfMap = (Map)counselReport.get(0);
				//printData.addAll(counselReport);
				printData = fillCounselPrintData(reportType, counselReport);

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
								"查無" + reportCompiledFile + "檔案，請電洽電算中心，謝謝！！");
				// }

				Map<String, String> parameters = new HashMap<String, String>();

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				
				//parameters.put("SchoolYear", "");
				//parameters.put("SchoolTerm", "");
				parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				
				String[] fields = null;
				if(reportType.equals("1")|| reportType.equals("2") || reportType.equals("3")
						|| reportType.equals("4")){
					String[] fs = { "schoolYear", "schoolTerm",
							"className", "teacherName", "courseName", "studentNo",
							"studentName", "cdate", "itemName", "content"};
					fields = fs.clone();
				}else if(reportType.equals("5")){
					String[] fs = { "schoolYear", "schoolTerm",
							"className", "studCount", "teacherName", "countStudent", "countU",
							"countT", "total"};
					fields = fs.clone();
				}else if(reportType.equals("6")){
					String[] fs = { "schoolYear", "schoolTerm",
							"className", "teacherName", "courseName", "countL",
							};
					fields = fs.clone();
				}
				
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

				String pdfPath = "";
				if(reportType.equals("1")){
					pdfPath = ranDir + "/SAF-CounselingTutorLearn.pdf";
				}else if(reportType.equals("2")){
					pdfPath = ranDir + "/SAF-CounselingTutorCareer.pdf";
				}else if(reportType.equals("3")){
					pdfPath = ranDir + "/SAF-CounselingTeacherLearn.pdf";
				}else if(reportType.equals("4")){
					pdfPath = ranDir + "/SAF-CounselingStudent.pdf";
				}else if(reportType.equals("5")){
					pdfPath = ranDir + "/SAF-CounselingTutorSummary.pdf";
				}else if(reportType.equals("6")){
					pdfPath = ranDir + "/SAF-CounselingTeacherSummary.pdf";
				}
				
				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(pdfPath))));
				JasperExportManager.exportReportToPdfStream(
						jasperPrint, os);
				os.close();
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
				//JasperReportUtils.printPdfToFrontEnd(response, new File(context
				//		.getRealPath(pdfPath)));
				//Toolket.deleteDIR(tempdir);

			}else{
				
			}
		}

	}
	
	private List<Object> fillCounselPrintData(String reportType, List iList){
		List<Object> rtList = new ArrayList();
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		if(reportType.equals("1") || reportType.equals("2") || reportType.equals("3") ||
				reportType.equals("4")){
			List<StudCounseling> counselings = (List<StudCounseling>)iList;
			for(StudCounseling counsel:counselings){
				Object[] obj = new Object[10];
				obj[0] = "" + counsel.getSchoolYear();
				obj[1] = "" + counsel.getSchoolTerm();
				obj[2] = counsel.getClassName();
				obj[3] = counsel.getTeacherName();
				obj[4] = counsel.getCourseName();
				obj[5] = counsel.getStudentNo();
				obj[6] = counsel.getStudentName();
				obj[7] = df.format(counsel.getCdate());
				obj[8] = counsel.getItemName();
				obj[9] = counsel.getContent();
				rtList.add(obj);
			}
		}else if(reportType.equals("5")){
			for(Iterator pIter=iList.iterator(); pIter.hasNext();){
				Map pMap = (Map)pIter.next();
				Object[] obj = new Object[23];
				obj[0] = pMap.get("schoolYear");
				obj[1] = pMap.get("schoolTerm");
				obj[2] = pMap.get("className");
				obj[3] = pMap.get("studCount");
				obj[4] = pMap.get("teacherName");
				obj[5] = pMap.get("countStudent");
				obj[6] = pMap.get("countU");
				obj[7] = pMap.get("countT");
				obj[8] = "" + (Integer.parseInt(pMap.get("countU").toString()) + 
							Integer.parseInt(pMap.get("countT").toString()));
				rtList.add(obj);
			}
		}else if(reportType.equals("6")){
			for(Iterator pIter=iList.iterator(); pIter.hasNext();){
				Map pMap = (Map)pIter.next();
				Object[] obj = new Object[23];
				obj[0] = pMap.get("schoolYear");
				obj[1] = pMap.get("schoolTerm");
				obj[2] = pMap.get("className");
				obj[3] = pMap.get("teacherName");
				obj[4] = pMap.get("courseName");
				obj[5] = pMap.get("countL");
				rtList.add(obj);
			}
		}
		return rtList;
	}

	
	private void AMSDocReporter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		AmsManager ams = (AmsManager)wac.getBean("amsManager");
		MemberManager mm = (MemberManager)wac.getBean("memberManager");

		HttpSession session = request.getSession(false);
		ServletContext context = this.getServletContext();
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		
		System.out.println("work?"+request.getParameter("Oid"));
		
		
		String Oid = request.getParameter("Oid").trim();
		//String DateStart = request.getParameter("DateStart");
		String idno = credential.getMember().getIdno();
		if(!Oid.equals("")){
			AmsDocApply doc = ams.getDocApplyByOid(Integer.parseInt(Oid));

			if(doc.getIdno().equals(idno) && !ams.isExpireDoc(doc)){
				ams.setDocExtraData(doc);
				DateFormat dayf = new SimpleDateFormat("yy/MM/dd");
				DateFormat dayflong = new SimpleDateFormat("yyyy/MM/dd");
				String printDate = dayflong.format(Calendar.getInstance().getTime());
				//log.debug("print Date:" + printDate + ", Calendar:" + Calendar.getInstance().getTime());
				List<Map> bpList = new ArrayList();
				int i=0, j=0, sheetCnt=0;
				String title = "";

				File templateXLS = null;
				if(doc.getDocType().equals(IConstants.AMSDocAskLeave)){
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/AMS_timeoff.xls"));
				}else if(doc.getDocType().equals(IConstants.AMSDocOverTime)){
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/AMS_overtime.xls"));
				}else if(doc.getDocType().equals(IConstants.AMSDocRepair)){
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/AMS_repair.xls"));
				}else if(doc.getDocType().equals(IConstants.AMSDocRevoke)){
					templateXLS = new File(context
							.getRealPath("/WEB-INF/reports/AMS_revoke.xls"));
				}
				HSSFWorkbook workbook = null;
				try {
					workbook = Toolket.getHSSFWorkbook(templateXLS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				HSSFFont colorFont = workbook.createFont();
				colorFont.setColor(HSSFColor.RED.index);
				colorFont.setFontHeightInPoints((short) 12);
				//colorFont.setFontName("標楷體");

				HSSFFont font12 = workbook.createFont();
				font12.setFontHeightInPoints((short) 12);
				HSSFFont font10 = workbook.createFont();
				font10.setFontHeightInPoints((short) 10);
				HSSFFont font9 = workbook.createFont();
				font9.setFontHeightInPoints((short) 9);
				//fontSize12.setFontName("標楷體");
				short align =  HSSFCellStyle.ALIGN_LEFT + HSSFCellStyle.VERTICAL_CENTER;
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle.setFont(font12);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				HSSFCellStyle cellStyle10 = workbook.createCellStyle();
				cellStyle10.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle10.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle10.setFont(font10);
				cellStyle10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle10.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle10.setBorderRight(HSSFCellStyle.BORDER_THIN);
				HSSFCellStyle cellStyleUp = workbook.createCellStyle();
				cellStyleUp.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyleUp.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
				cellStyleUp.setFont(font12);
				cellStyleUp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyleUp.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyleUp.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyleUp.setBorderRight(HSSFCellStyle.BORDER_THIN);
				
				
				HSSFSheet sheet = workbook.getSheetAt(sheetCnt);
				workbook.setSheetName(sheetCnt, "單據");
				// .createSheet(studNo);
				sheet.setAutobreaks(false);// 自動換頁 - 關閉

				// 列印設置
				HSSFPrintSetup hps = sheet.getPrintSetup();
				hps.setPaperSize((short) 9); // 9=a4紙？
				hps.setLandscape(false); // 直印
				workbook.setPrintArea(0, "$A$1:$V$43");
				
				//byte[] barcode = this.getBarCodeArray(context, doc.getSn(), "EAN");
				//Jpeg bcJpeg = getBarcodeJpeg(context, doc.getSn(), "EAN", 80, 20);
				//byte[] jpeg = bcJpeg.getOriginalData();
				byte[] awImage = getBarCode(doc.getSn(), "39");
				Employee empl = mm.findEmployeeByIdno(idno);
				if(doc.getDocType().equals(IConstants.AMSDocAskLeave)){
					String stmp = empl.getSname();
					Toolket.setCellValue(workbook, sheet, 1, 1, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 13, 1, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 25, 1, stmp, cellStyle);
					stmp = credential.getMember().getName();
					Toolket.setCellValue(workbook, sheet, 1, 7, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 13, 7, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 25, 7, stmp, cellStyle);
					stmp = doc.getReason();
					try{
						
					}catch(Exception e){
						
					}
					try{
						if(stmp.length()>10){
							if(stmp.length()>20){
								stmp = stmp.substring(0, 19);
							}
							Toolket.setCellValue(workbook, sheet, 1, 11, stmp, cellStyle10);
							Toolket.setCellValue(workbook, sheet, 13, 11, stmp, cellStyle10);
							Toolket.setCellValue(workbook, sheet, 25, 11, stmp, cellStyle10);
						}else{
							Toolket.setCellValue(workbook, sheet, 1, 11, stmp, cellStyle);
							Toolket.setCellValue(workbook, sheet, 13, 11, stmp, cellStyle);
							Toolket.setCellValue(workbook, sheet, 25, 11, stmp, cellStyle);
						}
					}catch(Exception e){
						
					}
					
					stmp = doc.getAskLeaveName();
					Toolket.setCellValue(workbook, sheet, 1, 20, stmp, cellStyle10);
					Toolket.setCellValue(workbook, sheet, 13, 20, stmp, cellStyle10);
					Toolket.setCellValue(workbook, sheet, 25, 20, stmp, cellStyle10);
					stmp = "自" + doc.getStartMonth() + "月" + doc.getStartDay() + "日" + 
							doc.getStartHour();
					if(doc.getStartMinute() != null){
						if(!doc.getStartMinute().equals("") && !doc.getStartMinute().equals("0")){
							stmp += ":" + doc.getStartMinute();
						}
					}
					stmp += "時起至" + doc.getEndMonth() + "月" + doc.getEndDay() + "日" + 
						doc.getEndHour();
					if(doc.getEndMinute() != null){
						if(!doc.getEndMinute().equals("") && !doc.getEndMinute().equals("0")){
							stmp += ":" + doc.getEndMinute();
						}
					}
					stmp +=  "時止";
					Toolket.setCellValue(workbook, sheet, 3, 3, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 15, 3, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 27, 3, stmp, cellStyle);
					stmp = "共";
					if(doc.getTotalDay() != null){
						if(!doc.getTotalDay().equals("")){
							stmp +=  doc.getTotalDay() + "日";
						}else{
							stmp += "0日";
						}
					}
					if(doc.getTotalHour() != null){
						if(doc.getTotalHour() != 0){
							stmp += doc.getTotalHour();
							if(doc.getTotalMinute() != null){
								if(doc.getTotalMinute() != 0){
									stmp += ".5時";
								}else{
									stmp +=  "時";
								}
							}else{
								stmp +=  "時";
							}
						}else{	//Hour=0
							if(doc.getTotalMinute() != null){
								if(doc.getTotalMinute() != 0){
									stmp +=  "0.5時";
								}
							}
						}
					}else{
						if(doc.getTotalMinute() != null){
							if(doc.getTotalMinute() != 0){
								stmp +=  "0.5時";
							}
						}
					}
					Toolket.setCellValue(workbook, sheet, 3, 13, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 15, 13, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 27, 13, stmp, cellStyle);
					stmp = "";
					if(doc.getTeachPeriod() != null){
						if(!doc.getTeachPeriod().equals("")){
							stmp +=  "共 " + doc.getTeachPeriod() + "節";
							Toolket.setCellValue(sheet, 3, 21, stmp);
							Toolket.setCellValue(sheet, 15, 21, stmp);
							Toolket.setCellValue(sheet, 27, 21, stmp);
						}
					}
					stmp = Toolket.getEmplName(doc.getAgent());
					Toolket.setCellValue(workbook, sheet, 4, 5, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 16, 5, stmp, cellStyle);
					Toolket.setCellValue(workbook, sheet, 28, 5, stmp, cellStyle);
					
					Toolket.setCellValue(sheet, 43, 18, printDate);
					
				}else if(doc.getDocType().equals(IConstants.AMSDocOverTime)){
					String stmp = Toolket.getEmpUnit(empl.getUnit());
					Toolket.setCellValue(workbook, sheet, 1, 2, stmp, cellStyle);
					stmp = empl.getSname();
					Toolket.setCellValue(workbook, sheet, 1, 4, stmp, cellStyle);
					stmp = credential.getMember().getName();
					Toolket.setCellValue(workbook, sheet, 1, 7, stmp, cellStyle);
					stmp = "開始時間：" + doc.getStartYear() + " 年 " + doc.getStartMonth() + " 月 " + 
							doc.getStartDay() + " 日 " + doc.getStartHour() + " 時 " + doc.getStartMinute() + " 分 ";
					Toolket.setCellValue(workbook, sheet, 2, 2, stmp, cellStyle);
					stmp = "結束時間：" + doc.getEndYear() + " 年 " + doc.getEndMonth() + " 月 " +
							doc.getEndDay() + " 日 " + doc.getEndHour()+ " 時 " + doc.getEndMinute() + " 分 ";;
					Toolket.setCellValue(workbook, sheet, 3, 2, stmp, cellStyle);
					stmp = "共 ";
					/*if(doc.getTotalDay() != null){
						if(!doc.getTotalDay().equals("")){
							stmp +=  doc.getTotalDay() + " 日 ";
						}
					}*/
					if(doc.getTotalHour() != null){
						if(doc.getTotalHour() == 4){
							stmp += " 4 小時";
						}
						if(doc.getTotalHour() == 8){
							stmp += " 8 小時";
						}
					}
					Toolket.setCellValue(workbook, sheet, 4, 2, stmp, cellStyle);
					stmp = doc.getReason();
					Toolket.setCellValue(workbook, sheet, 5, 2, stmp, cellStyleUp);
					Toolket.setCellValue(sheet, 17, 7, printDate);
					
				}else if(doc.getDocType().equals(IConstants.AMSDocRepair)){
					String stmp = Toolket.getEmpUnit(empl.getUnit());
					Toolket.setCellValue(workbook, sheet, 1, 2, stmp, cellStyle);
					stmp = empl.getSname();
					Toolket.setCellValue(workbook, sheet, 1, 4, stmp, cellStyle);
					stmp = credential.getMember().getName();
					Toolket.setCellValue(workbook, sheet, 1, 7, stmp, cellStyle);
					if(doc.getStartYear() != null){
						if(!doc.getStartYear().equals("")){
							stmp = "上班時間： " + doc.getStartYear() + " 年 " + doc.getStartMonth() + " 月 " + 
							doc.getStartDay() + " 日 " + doc.getStartHour() + " 時 " + doc.getStartMinute() + " 分 ";
							Toolket.setCellValue(workbook, sheet, 2, 2, stmp, cellStyle);
						}
					}
					if(doc.getEndYear() != null){
						if(!doc.getEndYear().equals("")){
							stmp = "下班時間：" + doc.getEndYear() + " 年 " + doc.getEndMonth() + " 月 " +
							doc.getEndDay() + " 日 " + doc.getEndHour()+ " 時 " + doc.getEndMinute() + " 分 ";;
							Toolket.setCellValue(workbook, sheet, 3, 2, stmp, cellStyle);
						}
					}
					stmp = doc.getReason();
					Toolket.setCellValue(workbook, sheet, 4, 2, stmp, cellStyleUp);
					Toolket.setCellValue(sheet, 15, 7, printDate);
					
					
				}else if(doc.getDocType().equals(IConstants.AMSDocRevoke)){
					String stmp = Toolket.getEmpUnit(empl.getUnit());
					Toolket.setCellValue(workbook, sheet, 1, 2, stmp, cellStyle);
					stmp = empl.getSname();
					Toolket.setCellValue(workbook, sheet, 1, 4, stmp, cellStyle);
					stmp = credential.getMember().getName();
					Toolket.setCellValue(workbook, sheet, 1, 7, stmp, cellStyle);
					ams.setDocExtraData(doc);
					AmsDocApply revoked = doc.getRevokedDoc();
					ams.setDocExtraData(revoked);
					stmp = "開始時間：" + revoked.getStartYear() + " 年 " + revoked.getStartMonth() + " 月 " + 
					revoked.getStartDay() + " 日 " + revoked.getStartHour() + " 時 " + revoked.getStartMinute() + " 分 ";
					Toolket.setCellValue(workbook, sheet, 2, 2, stmp, cellStyle);
					stmp = "結束時間：" + revoked.getEndYear() + " 年 " + revoked.getEndMonth() + " 月 " +
					revoked.getEndDay() + " 日 " + revoked.getEndHour()+ " 時 " + revoked.getEndMinute() + " 分 ";;
					Toolket.setCellValue(workbook, sheet, 3, 2, stmp, cellStyle);
					stmp = "共";
					if(revoked.getTotalDay() != null){
						if(!revoked.getTotalDay().equals("")){
							stmp +=  revoked.getTotalDay() + "日";
						}else{
							stmp += "0日";
						}
					}
					if(revoked.getTotalHour() != null){
						if(revoked.getTotalHour() != 0){
							stmp += revoked.getTotalHour();
							if(revoked.getTotalMinute() != null){
								if(revoked.getTotalMinute() != 0){
									stmp +=  revoked.getTotalHour() + ".5時";
								}else{
									stmp +=  "時";
								}
							}else{
								stmp +=  "時";
							}
						}else{	//Hour=0
							if(revoked.getTotalMinute() != null){
								if(revoked.getTotalMinute() != 0){
									stmp +=  "0.5時";
								}
							}
						}
					}else{
						if(revoked.getTotalMinute() != null){
							if(revoked.getTotalMinute() != 0){
								stmp +=  "0.5時";
							}
						}
					}
					Toolket.setCellValue(workbook, sheet, 4, 2, stmp, cellStyle);
					stmp = doc.getReason();
					Toolket.setCellValue(workbook, sheet, 5, 2, stmp, cellStyleUp);
					Toolket.setCellValue(sheet, 17, 7, printDate);
					
				}
				
				
				//列印條碼
				int pictureIdx = workbook.addPicture(awImage, HSSFWorkbook.PICTURE_TYPE_JPEG);
				CreationHelper helper =  workbook.getCreationHelper();
				// Create the drawing patriarch.  This is the top level container for all shapes. 
			    HSSFPatriarch drawing = sheet.createDrawingPatriarch();
			    //add a picture shape
			    ClientAnchor anchor = helper.createClientAnchor();
			    anchor.setDx1(3);
			    anchor.setDy1(3);
			    //set top-left corner of the picture,
			    //subsequent call of Picture#resize() will operate relative to it
			    if(doc.getDocType().equals(IConstants.AMSDocAskLeave)){
					Toolket.setCellValue(sheet, 43, 11, doc.getSn());
					anchor.setCol1(2);
				    anchor.setRow1(42);
			    }else if(doc.getDocType().equals(IConstants.AMSDocOverTime)){
					Toolket.setCellValue(sheet, 17, 4, doc.getSn());
					anchor.setCol1(1);
				    anchor.setRow1(17);
				}else if(doc.getDocType().equals(IConstants.AMSDocRepair)){
					Toolket.setCellValue(sheet, 15, 4, doc.getSn());
					anchor.setCol1(1);
				    anchor.setRow1(15);
				}else if(doc.getDocType().equals(IConstants.AMSDocRevoke)){
					Toolket.setCellValue(sheet, 17, 4, doc.getSn());
					anchor.setCol1(1);
				    anchor.setRow1(17);
				}
			    Picture pict = drawing.createPicture(anchor, pictureIdx);

			    //auto-size picture relative to its top-left corner
			    pict.resize(0.5d);
				

				
				workbook.setDisplayedTab((short)0);
				
				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "AMS_Doc_" 
						+ idno
						+ (new SimpleDateFormat("yyyyMMddhhmmss")
						.format(new Date())) + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				
			}
			
		}
	}
	
	private void StudDocAttachUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException{
		HttpSession httpSession = request.getSession(false);

		//response.setContentType("text/xml; charset=UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		StudAffairManager sm = (StudAffairManager) wac.getBean("studAffairManager");
		BlobDAO blobdao = (BlobDAO) wac.getBean("blobDAO");
		JSONObject csJson = new JSONObject();

		//org.hibernate.SessionFactory factory = (org.hibernate.SessionFactory) Global.context.getBean("sessionFactory");
		//Configuration cfg = new Configuration()
		//.addResource("tw/edu/chit/model/StudDocUpload.hbm.xml")
		//.addResource("tw/edu/chit/model/StudPublicDocUpload.hbm.xml")
	    //.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    //.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    //.setProperty("hibernate.connection.url", IConstants.photoDB)
	    //.setProperty("hibernate.connection.username", "root")
	    //.setProperty("hibernate.connection.password", "spring")
	    //;
		//org.hibernate.SessionFactory sessions = cfg.buildSessionFactory();
		//org.hibernate.SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		//Session session = sessions.openSession();
		
		//session.saveOrUpdate(arg0)
		
		String filePath = request.getRealPath("/")+"upload";
		
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String imgType = request.getParameter("imgType");
		Enumeration paras = request.getParameterNames();
		while(paras.hasMoreElements()){
			String para = (String)paras.nextElement();
		}
		
		if(isMultipart){
			request.setCharacterEncoding("UTF-8");
			DiskFileItemFactory factory=new DiskFileItemFactory();
			//factory.setSizeThreshold(4096);
			//factory.setRepository(new File("basepath"+"\\temp\\"));
			ServletFileUpload fileUpload=new ServletFileUpload(factory);
			fileUpload.setFileSizeMax(1024*1024*1*100);
			try {
				 List<FileItem> list =fileUpload.parseRequest(request);
				 Iterator<FileItem> iter = list.iterator();
				 while(iter.hasNext()){
					 FileItem fileItem = iter.next();
					 if(!fileItem.isFormField()){
						 String name=fileItem.getName();
						 //byte[] bb = name.getBytes("8859_1");
						 //String name2 = new String(bb, "UTF-8");
						 int fsize = (int) fileItem.getSize();
						 if(fsize<=1024*500){
							 //name=URLDecoder.decode(URLDecoder.decode(name,"UTF-8"), "UTF-8");
							 name=name.substring(name.lastIndexOf("\\")+1);
							 name=URLDecoder.decode(name,"utf-8");
							 //System.out.println(request.getSession().getId()+name);
							 //File file=new File(filePath, request.getSession().getId()+"_"+name);//加上sessionId用于區別
							 //fileItem.write(file);
							 //write file to db
							 InputStream uploadedStream = fileItem.getInputStream();
							 byte[] bf = new byte[fsize];
							 uploadedStream.read(bf, 0, fsize);
							 if(imgType.equalsIgnoreCase("studDoc")){
								 StudDocUpload doc = new StudDocUpload();
								 doc.setFileName(name);
								 doc.setAttContent(Hibernate.createBlob(bf));
								 //Transaction tx  = session.beginTransaction();
								 blobdao.saveObject(doc);
								 //session.saveOrUpdate(doc);
								 //tx.commit();
								 //session.flush();
								 //session.refresh(doc);
								 blobdao.reload(doc);
								 csJson.put("oid", doc.getOid());
							 }else if(imgType.equalsIgnoreCase("publicDoc")){
								 StudPublicDocUpload doc = new StudPublicDocUpload();
								 doc.setFileName(name);
								 doc.setAttContent(Hibernate.createBlob(bf));
								 //Transaction tx  = session.beginTransaction();
								 //session.saveOrUpdate(doc);
								 blobdao.saveObject(doc);
								 //tx.commit();
								 //session.flush();
								 //session.refresh(doc);
								 blobdao.reload(doc);
								 csJson.put("oid", doc.getOid());
							 }
						 }else{
							 csJson.put("fail", "檔案須小於500K!");
						 }
					 }
				 }
			 } catch (FileUploadException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 csJson.put("fail", "FileUploadException!請重新上傳!");
				 out.print(csJson.toString());
			 }  catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					csJson.put("fail", "JSONException!請重新上傳!");
					 out.print(csJson.toString());
			 }catch (Exception e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 csJson.put("fail", "Normal Exception!請重新上傳!");
				 out.print(csJson.toString());
			 }finally{
			 }
			 out.print(csJson.toString());

		}else{
			csJson.put("fail", "傳輸錯誤,請重新上傳!");
			out.print(csJson.toString());
		}
		//session.close();
		out.flush();
		out.close();
	}
	
	private void getDocAttachUpload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession httpSession = request.getSession(false);

		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BlobDAO blobdao = (BlobDAO) wac.getBean("blobDAO");

		//create hibernate session
		//Configuration cfg = new Configuration()
		//.addResource("tw/edu/chit/model/StudDocUpload.hbm.xml")
	    //.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    //.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    //.setProperty("hibernate.connection.url", IConstants.photoDB)
	    //.setProperty("hibernate.connection.username", "root")
	    //.setProperty("hibernate.connection.password", "spring")
	    //;
		//org.hibernate.SessionFactory factory = cfg.buildSessionFactory();
		//Session session = factory.openSession();

		response.setContentType("image/jpeg"); 
		
		String oid = request.getParameter("oid");
		String hql = "From StudDocUpload Where oid=" + oid;
		//Transaction tx  = session.beginTransaction();
		//List<StudDocUpload> files = session.createQuery(hql).list();
		List<StudDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//session.flush();
		//session.clear();
		//session.close();
		//factory.close();

		if(!files.isEmpty()){
			StudDocUpload attach  = files.get(0);
			String ext = attach.getFileName();
			ext = ext.substring(ext.lastIndexOf('.'));
			if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpe")){
				response.setContentType("image/jpeg");
			}else if(ext.equalsIgnoreCase("gif")){
				response.setContentType("image/gif");
			}else if(ext.equalsIgnoreCase("tif") || ext.equalsIgnoreCase("tiff")){
				response.setContentType("image/tiff");
			}else if(ext.equalsIgnoreCase("png")){
				response.setContentType("image/png");
			}else if(ext.equalsIgnoreCase("bmp")){
				response.setContentType("image/bmp");
			}
			Blob img=attach.getAttContent();
			long size;
			try {
				size = img.length();
				byte[] bs = img.getBytes(1, (int)size);
				OutputStream outs = response.getOutputStream(); 
				outs.write(bs);
				outs.flush();
			} catch (IllegalStateException e) {
				response.sendRedirect("/CIS/pages/images/notFound.gif");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			response.sendRedirect("/CIS/pages/images/notFound.gif");
		}
		response.setContentType("text/xml; charset=UTF-8");

	}
	
	private void StudDocAttachDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out=response.getWriter();
		
		 JSONObject csJson = new JSONObject();

		HttpSession httpSession = request.getSession(false);

		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BlobDAO blobdao = (BlobDAO) wac.getBean("blobDAO");

		//create hibernate session
		//Configuration cfg = new Configuration()
		//.addResource("tw/edu/chit/model/StudDocUpload.hbm.xml")
	    //.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    //.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    //.setProperty("hibernate.connection.url", IConstants.photoDB)
	    //.setProperty("hibernate.connection.username", "root")
	    //.setProperty("hibernate.connection.password", "spring")
	    //;
		//org.hibernate.SessionFactory factory = cfg.buildSessionFactory();
		//Session session = factory.openSession();
		
		String oid = request.getParameter("fileName");
		String hql = "Delete From StudDocUpload Where oid=" + oid;
		if(!oid.equals("")){
			 //Transaction tx  = session.beginTransaction();
			 //session.createQuery(hql).executeUpdate();
			blobdao.removeAnyThing(hql);
			 //tx.commit();
			 try {
				csJson.put("result", "success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			 try {
					csJson.put("result", "找不到檔案:" + oid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//session.flush();
		//session.clear();
		//session.close();
		//factory.close();
		out.print(csJson.toString());
		out.flush();
		out.close();
	}
	
	private void getPublicDocAttachUpload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession httpSession = request.getSession(false);
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BlobDAO blobdao = (BlobDAO) wac.getBean("blobDAO");

		//create hibernate session
		//Configuration cfg = new Configuration()
		//.addResource("tw/edu/chit/model/StudPublicLeave.hbm.xml")
		//.addResource("tw/edu/chit/model/StudPublicDocUpload.hbm.xml")
	    //.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    //.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    //.setProperty("hibernate.connection.url", IConstants.photoDB)
	    //.setProperty("hibernate.connection.username", "root")
	    //.setProperty("hibernate.connection.password", "spring")
	    //;
		//org.hibernate.SessionFactory factory = cfg.buildSessionFactory();
		//Session session = factory.openSession();

		response.setContentType("image/jpeg"); 
		
		String oid = request.getParameter("oid");
		String hql = "From StudPublicDocUpload Where oid=" + oid;
		//Transaction tx  = session.beginTransaction();
		//List<StudPublicDocUpload> files = session.createQuery(hql).list();
		List<StudPublicDocUpload> files = blobdao.submitQuery(hql);
		//tx.commit();
		//session.flush();
		//session.clear();
		//session.close();
		//factory.close();

		if(!files.isEmpty()){
			StudPublicDocUpload attach  = files.get(0);
			String ext = attach.getFileName();
			ext = ext.substring(ext.lastIndexOf('.'));
			if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpe")){
				response.setContentType("image/jpeg");
			}else if(ext.equalsIgnoreCase("gif")){
				response.setContentType("image/gif");
			}else if(ext.equalsIgnoreCase("tif") || ext.equalsIgnoreCase("tiff")){
				response.setContentType("image/tiff");
			}else if(ext.equalsIgnoreCase("png")){
				response.setContentType("image/png");
			}else if(ext.equalsIgnoreCase("bmp")){
				response.setContentType("image/bmp");
			}
			Blob img=attach.getAttContent();
			long size;
			try {
				size = img.length();
				byte[] bs = img.getBytes(1, (int)size);
				OutputStream outs = response.getOutputStream(); 
				outs.write(bs);
				outs.flush();
			} catch (IllegalStateException e) {
				response.sendRedirect("/CIS/pages/images/notFound.gif");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			response.sendRedirect("/CIS/pages/images/notFound.gif");
		}
		response.setContentType("text/xml; charset=UTF-8");

	}
	
	private void StudPublicDocAttachDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8");

		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BlobDAO blobdao = (BlobDAO) wac.getBean("blobDAO");

		PrintWriter out=response.getWriter();
		
		 JSONObject csJson = new JSONObject();

		HttpSession httpSession = request.getSession(false);

		//create hibernate session
		//Configuration cfg = new Configuration()
		//.addResource("tw/edu/chit/model/StudPublicLeave.hbm.xml")
		//.addResource("tw/edu/chit/model/StudPublicDocUpload.hbm.xml")
	    //.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
	    //.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
	    //.setProperty("hibernate.connection.url", IConstants.photoDB)
	    //.setProperty("hibernate.connection.username", "root")
	    //.setProperty("hibernate.connection.password", "spring")
	    //;
		//org.hibernate.SessionFactory factory = cfg.buildSessionFactory();
		//Session session = factory.openSession();
		
		String oid = request.getParameter("fileName");
		String hql = "Delete From StudPublicDocUpload Where oid=" + oid;
		if(!oid.equals("")){
			 //Transaction tx  = session.beginTransaction();
			 //session.createQuery(hql).executeUpdate();
			blobdao.removeAnyThing(hql);
			 //tx.commit();
			 try {
				csJson.put("result", "success");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			 try {
					csJson.put("result", "找不到檔案:" + oid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		//session.flush();
		//session.clear();
		//session.close();
		//factory.close();
		out.print(csJson.toString());
		out.flush();
		out.close();
	}
	
	//Leo-20091211
	private void AMSMeetingAskLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		AmsManager ams = (AmsManager)wac.getBean("amsManager");
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		CourseManager manager = (CourseManager) wac.getBean("courseManager");

		HttpSession session = request.getSession(false);
		ServletContext context = this.getServletContext();
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String Oid = request.getParameter("Oid").trim();		
		String idno = credential.getMember().getIdno();
		if(!Oid.equals("")){
			AmsDocApply doc = ams.getDocApplyByOid(Integer.parseInt(Oid));
			Map mal = manager.ezGetMap("Select * From AMS_MeetingAskLeave Where Oid='"+Oid+"'");

			
				DateFormat dayflong = new SimpleDateFormat("yyyy/MM/dd");
				String printDate = dayflong.format(Calendar.getInstance().getTime());
				
				int i=0, j=0, sheetCnt=0;
				String title = "";

				File templateXLS = null;
				templateXLS = new File(context.getRealPath("/WEB-INF/reports/AMS_MeetingAskLeave.xls"));
				
				HSSFWorkbook workbook = null;
				try {
					workbook = Toolket.getHSSFWorkbook(templateXLS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				HSSFFont colorFont = workbook.createFont();
				colorFont.setColor(HSSFColor.RED.index);
				colorFont.setFontHeightInPoints((short) 12);
				//colorFont.setFontName("標楷體");

				HSSFFont font12 = workbook.createFont();
				font12.setFontHeightInPoints((short) 12);
				HSSFFont font10 = workbook.createFont();
				font10.setFontHeightInPoints((short) 10);
				HSSFFont font9 = workbook.createFont();
				font9.setFontHeightInPoints((short) 9);
				//fontSize12.setFontName("標楷體");
				short align =  HSSFCellStyle.ALIGN_LEFT + HSSFCellStyle.VERTICAL_CENTER;
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle.setFont(font12);
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				HSSFCellStyle cellStyle10 = workbook.createCellStyle();
				cellStyle10.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyle10.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				cellStyle10.setFont(font10);
				cellStyle10.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle10.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle10.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				cellStyle10.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				HSSFCellStyle cellStyleUp = workbook.createCellStyle();
				cellStyleUp.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				cellStyleUp.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
				cellStyleUp.setFont(font12);
				cellStyleUp.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				cellStyleUp.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				cellStyleUp.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				cellStyleUp.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				
				
				HSSFSheet sheet = workbook.getSheetAt(sheetCnt);
				workbook.setSheetName(sheetCnt, "單據");
				// .createSheet(studNo);
				sheet.setAutobreaks(false);// 自動換頁 - 關閉

				// 列印設置
				HSSFPrintSetup hps = sheet.getPrintSetup();
				hps.setPaperSize((short) 9); // 9=a4紙？
				hps.setLandscape(false); // 直印
				workbook.setPrintArea(0, "$A$1:$V$43");				
				
				byte[] awImage = getBarCode(mal.get("askCode").toString(), "39");
				Employee empl = mm.findEmployeeByIdno(idno);
				
				String stmp = Toolket.getEmpUnit(empl.getUnit());
				Toolket.setCellValue(workbook, sheet, 1, 2, stmp, cellStyle);
				stmp = empl.getSname();
				Toolket.setCellValue(workbook, sheet, 1, 4, stmp, cellStyle);
				stmp = credential.getMember().getName();
				Toolket.setCellValue(workbook, sheet, 1, 7, stmp, cellStyle);
				stmp = manager.ezGetString("Select Name From AMS_Meeting Where Oid='"+mal.get("meetingOid").toString()+"'");
				Toolket.setCellValue(workbook, sheet, 2, 2, stmp, cellStyle);			
				stmp = manager.ezGetString("Select Name From AMS_AskLeave Where ID='"+mal.get("askleaveId").toString()+"'");
				Toolket.setCellValue(workbook, sheet, 3, 2, stmp, cellStyle);			
				stmp = mal.get("Temp").toString();
				Toolket.setCellValue(workbook, sheet, 4, 2, stmp, cellStyleUp);
				Toolket.setCellValue(sheet, 15, 7, printDate);				
				
				//列印條碼
				int pictureIdx = workbook.addPicture(awImage, HSSFWorkbook.PICTURE_TYPE_JPEG);
				CreationHelper helper =  workbook.getCreationHelper();
				// Create the drawing patriarch.  This is the top level container for all shapes. 
			    HSSFPatriarch drawing = sheet.createDrawingPatriarch();
			    //add a picture shape
			    ClientAnchor anchor = helper.createClientAnchor();
			    anchor.setDx1(3);
			    anchor.setDy1(3);
			    //set top-left corner of the picture,
			    //subsequent call of Picture#resize() will operate relative to it
			    
					Toolket.setCellValue(sheet, 15, 4, mal.get("askCode").toString());
					anchor.setCol1(1);
				    anchor.setRow1(15);
				
			    Picture pict = drawing.createPicture(anchor, pictureIdx);

			    //auto-size picture relative to its top-left corner
			    pict.resize(0.5d);
				

				
				workbook.setDisplayedTab((short)0);
				
				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "AMS_MAL_" 
						+ idno
						+ (new SimpleDateFormat("yyyyMMddhhmmss")
						.format(new Date())) + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				
			
			
		}
	}

	
	private void GetTimeOffSerious(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		StudAffairManager sm = (StudAffairManager) wac.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)wac.getBean("scoreManager");

		HttpSession session = request.getSession(false);
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		String departClass = request.getParameter("departClass");
		//String DateStart = request.getParameter("DateStart");
		String DateEnd = request.getParameter("DateEnd");
		String period = request.getParameter("period");
		String qscope = request.getParameter("qscope");
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();

		//log.debug("TimeoffseriousQuery!:" + departClass + ":" + DateStart + ":" + DateEnd + ":" + period );
		if(!departClass.trim().equals("")) {
			List tfList = sm.findTimeOffSerious(departClass, "//", DateEnd, period, qscope);
			log.debug("TimeOffSerious:" + tfList.size());
			out.println("<dilgPrompt>");
			out.println("<departClass>" + departClass + "</departClass>");
			out.println("<recnum>" + tfList.size() + "</recnum>");

			if(!tfList.isEmpty()){
				for(Iterator tfIter=tfList.iterator(); tfIter.hasNext();){
					Map dilgMap = (Map)tfIter.next();
					//log.debug("studentNo:" + dilgMap.get("studentNo") + ",parentName:'" + dilgMap.get("parentName")+"'");
					out.println("<dilgInfo>");
					out.println("<studentNo>" + dilgMap.get("studentNo") + "</studentNo>");
					out.println("<studentName>" + dilgMap.get("studentName") + "</studentName>");
					out.println("<parentName>" + dilgMap.get("parentName") + "</parentName>");
					out.println("<TEL>" + dilgMap.get("TEL") + "</TEL>");
					out.println("<period>" + dilgMap.get("period") + "</period>");
					out.println("</dilgInfo>");
					
				}		
			}else{
				out.println("<dilgInfo>");
				out.println("<mode>notfound</mode>");
				out.println("</dilgInfo>");
			}
			out.println("</dilgPrompt>");
		} else {
			out.println("<dilgPrompt>");
			out.println("<departClass>" + departClass + "</departClass>");
			out.println("<recnum>0</recnum>");
			out.println("<dilgInfo>");
			out.println("<mode>notfound</mode>");
			out.println("</dilgInfo>");
			out.println("</dilgPrompt>");
		}
		out.close();
	}

	private void GetTchTimeOffInputData(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj)
					throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		StudAffairManager sm = (StudAffairManager)wac.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)wac.getBean("scoreManager");
		
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		Map TimeoffInit = new HashMap();
		
		String dtOid = "";
		String tdate = "";
		try{
			dtOid = jsonObj.getString("dtOid");
			tdate = jsonObj.getString("tdate");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		Dtime dtime = sm.getDtimeByOid(Integer.parseInt(dtOid));
		
		String courseType = dtime.getOpt();	//課程類別:1=必修, 2=選修, 3=..., 4=...
		int isOpen = dtime.getOpen();		//是否開放選修: 0=不開放, 1=開放, 2=..., 3=..
		String daynite;
		String myclass = dtime.getDepartClass();
		String cscode = dtime.getCscode();
		String[] tdateArray = tdate.split("-");
		
		// 由班級代碼判斷部制為:日間部,進修部(夜間),進修學院專校
		char dyflag = myclass.toUpperCase().charAt(1);

		switch (dyflag) {
		case '1':
		case '4':
		case '6':
		case 'A':
		case 'C':
		case 'F':
			daynite = "1";
			break;
		case '2':
		case '5':
		case '8':
		case '9':
		case 'B':
		case 'D':
		case 'H':
			daynite = "2";
			break;
		case '3':
		case '7':
			daynite = "3";
			break;
		default:
			daynite = "1";
		}

		List baseClassBook = new ArrayList();
		List classBookList = new ArrayList();
		List dtimelist = new ArrayList();
		List subjects = new ArrayList(); 	//存放所有課程之資訊
		List subjectsInUse = new ArrayList(); 	//存放classBook課程之資訊
		
		Dtime subject;
		int dtime_begin = 0;
		int dtime_end = 0;
		String dtime_chiName = "";
		String sddate = tdate;
		Calendar tfdate = Calendar.getInstance();
		tfdate.set(Calendar.YEAR, Integer.parseInt(tdateArray[0]));
		tfdate.set(Calendar.MONTH, Integer.parseInt(tdateArray[1])-1);
		tfdate.set(Calendar.DATE, Integer.parseInt(tdateArray[2]));
		
		//1:SUNDAY, 2:MONDAY,...
		int iweek = tfdate.get(Calendar.DAY_OF_WEEK);
		if(iweek == 1) iweek = 7;
		else iweek--;
		TimeoffInit.put("tfWeek", ("" + iweek));
		TimeoffInit.put("daynite", daynite);
		TimeoffInit.put("clazz", myclass);
		TimeoffInit.put("depClassName", Toolket.getClassFullName(myclass));
		TimeoffInit.put("cscode", cscode);
		TimeoffInit.put("cscodeName",scm.findCourseName(cscode));
		TimeoffInit.put("tfYear",tdateArray[0]);
		TimeoffInit.put("tfMonth",tdateArray[1]);
		TimeoffInit.put("tfDay",tdateArray[2]);
		JSONObject initJson = new JSONObject();
		try {
			initJson.put("tfWeek", ("" + iweek));
			initJson.put("daynite", daynite);
			initJson.put("clazz", myclass);
			initJson.put("depClassName", Toolket.getClassFullName(myclass));
			initJson.put("cscode", cscode);
			initJson.put("cscodeName", scm.findCourseName(cscode));
			initJson.put("tfYear", tdateArray[0]);
			initJson.put("tfMonth", tdateArray[1]);
			initJson.put("tfDay", tdateArray[2]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<DtimeClass> subjList = sm.getDtimeClassByDtimeOid(Integer.parseInt(dtOid), iweek);
		//TimeoffInit.put("TimeOffSubjInUse", subjList);
		//JSONArray subjArray = new JSONArray(subjList, false);
		/*
		DtimeClass[] dcsArray = subjList.toArray(new DtimeClass[subjList.size()]);
		
		JSONArray subjArray = new JSONArray();
		try {
			subjArray = new JSONArray(dcsArray, true);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		JSONArray subjArray = new JSONArray();
		for(DtimeClass dc:subjList){
			try {
				StringBuffer strbf = new StringBuffer();
				strbf.append("{");
				strbf.append("\"begin\":" + Integer.parseInt(dc.getBegin())).append(",");
				strbf.append("\"end\":" + Integer.parseInt(dc.getEnd())).append(",");
				strbf.append("\"Oid\":" + dc.getOid()).append(",");
				strbf.append("\"place\":\"" + dc.getPlace() + "\"").append(",");
				strbf.append("\"dtimeOid\":" + dc.getDtimeOid()).append(",");
				strbf.append("\"week\":" + dc.getWeek());
				strbf.append("}");
				//System.out.println(strbf.toString());
				subjArray.put(new JSONObject(strbf.toString()));
				/*
				JSONArray dcArray = new JSONArray();
				dcArray.put(new JSONObject("{\"begin\":" + dc.getBegin()+ "}"));
				dcArray.put(new JSONObject("{\"end\":" + dc.getEnd()+ "}"));
				dcArray.put(new JSONObject("{\"Oid\":" + dc.getOid()+ "}"));
				dcArray.put(new JSONObject("{\"place\":" + dc.getPlace()+ "}"));
				dcArray.put(new JSONObject("{\"dtimeOid\":" + dc.getDtimeOid()+ "}"));
				dcArray.put(new JSONObject("{\"week\":" + dc.getWeek()+ "}"));
				subjArray.put(dcArray);
				*/
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Student> studlist = sm.findStudentsByClass(myclass);
		
		Map classBookMap = new HashMap();	//存放classbook點名簿資訊
		List<Map> classBook = new ArrayList<Map>();;
		JSONArray cbArray = new JSONArray();
		
		if (subjList.size() > 0) {
			int[] period = new int[16];
			for (int j = 0; j < 16; j++)
				period[j] = 0;

			int period_begin = 16;
			int period_end = 16;

			// 同一天同一門選修課可能分散在不同節次上課,
			// 所以先統計一下該科目的第一及最後一節的上課區間
			for (DtimeClass dtimeClass : subjList) {
				dtime_begin = Integer.parseInt(dtimeClass.getBegin());
				dtime_end = Integer.parseInt(dtimeClass.getEnd());

				if (period_begin == 16)
					period_begin = dtime_begin;
				for (int i = dtime_begin; i <= dtime_end; i++) {
					period[i] = 1;
				}
			}
			period_end = dtime_end;

			// 處理點名簿資訊,無論該科目同一天分散在幾節上課,都只會執行for loop一次
			//
			// for(Iterator subjIter = subjList.iterator(); subjIter.hasNext();)
			// {
			// dtimeObj = (Object[])subjList.get(0);
			
			

			Map subjMap = new HashMap();

			if(dtime.getCscode().equalsIgnoreCase("50000") || dtime.getCscode().equalsIgnoreCase("T0001")){
				for (Iterator<Student> stuIter = studlist.iterator(); stuIter
						.hasNext();) {
					Student student = stuIter.next();
					Map stuMap = new HashMap();
					stuMap.put("studentNo", student.getStudentNo());
					stuMap.put("studentName", student.getStudentName());
					for (int k = 0; k < 16; k++) {
						if (period[k] == 1) {
							stuMap.put("status" + k, "");
						} else if (period[k] == 0) {
							stuMap.put("status" + k, "x");
						}
					}
					classBook.add(stuMap);
				}

			}else{
				//System.out.println("D.Oid="+dtime.getOid()+" ,D.Class="+dtime.getDepartClass());
				List<Seld> seldList = sm.findSeldForClassBook(dtime.getOid(), dtime.getDepartClass(), 0);
				
				

				for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
					Seld seld = seldIter.next();
					Map stuMap = new HashMap();

					stuMap.put("studentNo", seld.getStudentNo());
					stuMap.put("studentName", seld.getStudentName());
					for (int k = 0; k < 16; k++) {
						if (period[k] == 1) {
							stuMap.put("status" + k, "");
						} else if (period[k] == 0) {
							stuMap.put("status" + k, "x");
						}
					}
					classBook.add(stuMap);
				}
			}

			List<Map> Dilg4ClassBook = sm.findDilg4ClassBook(classBook, sddate,
					period_begin, period_end);
			// log.debug("Dilg4ClassBook.size()->" + Dilg4ClassBook.size());

			String studNo;
			for (Iterator cbIter = classBook.iterator(); cbIter.hasNext();) {
				Map cbMap = (Map) cbIter.next();
				studNo = cbMap.get("studentNo").toString();

				if (!Dilg4ClassBook.isEmpty()) {
					for (ListIterator<Map> dilg4cbIter = Dilg4ClassBook
							.listIterator(); dilg4cbIter.hasNext();) {
						// if(dilg4cbIter.nextIndex() < dIndex)
						// continue;
						Map d4cbMap = dilg4cbIter.next();
						if (studNo.equals(d4cbMap.get("studentNo").toString())) {
							if (period[Integer.parseInt(d4cbMap.get("period").toString())] == 1) {
								cbMap.put("status"
									+ d4cbMap.get("period").toString(), d4cbMap
									.get("status").toString());
							}

							
							// dIndex = dilg4cbIter.nextIndex();
						}
					}
				}
			}
			/*
			 * classBookMap.put("cscode", dtime.getOid());
			 * classBookMap.put("cscodeName", ""); classBookMap.put("begin",
			 * period_begin); classBookMap.put("end", period_end);
			 * classBookMap.put("classBook", classBook);
			 * classBookMap.put("classBookInSubject", subjList);
			 * classBookList.add(classBookMap);
			 */
		} else {
			// 該班今日無任何課程, return null JSON Object
			out.print(JSONObject.NULL);
			out.flush();
			out.close();
		}
		// TimeoffInit.put("classBook", classBook);
		// cbArray = new JSONArray(classBook, true);

		
		if(classBook.isEmpty()) {
			// 沒有查到任何開課課程及相關選課學生, return null JSONObject
			out.print(JSONObject.NULL);
			out.flush();
			out.close();
		}else{
			for(Map cbMap:classBook){
				JSONObject cbJson = new JSONObject();
				try {
					cbJson.put("studentNo", cbMap.get("studentNo").toString());
					cbJson.put("studentName", cbMap.get("studentName").toString());
					for(int k=0; k<16; k++) {
						cbJson.put("status" + k , cbMap.get("status" + k).toString());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cbArray.put(cbJson);
			}
		}
		
		JSONArray rArray = new JSONArray();
		rArray.put(initJson);
		rArray.put(subjArray);
		rArray.put(cbArray);
		//log.debug(subjArray.toString());
		//log.debug(cbArray.toString());
		//log.debug(rArray.toString());
		out.print(rArray.toString());
		//log.debug("return json:" + jArray.toString());
			
		out.flush();
		out.close();
	}

	
	private void GetAgent(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String sname = "";
		try{
			sname = jsonObj.getString("sname");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		List<Empl> empls = mm.findEmplByNameLike(sname);
		JSONArray emplArray = new JSONArray();
		if(!empls.isEmpty()){
			for(Empl empl:empls){
				JSONObject emplJson = new JSONObject();
				try {
					emplJson.put("idno", empl.getIdno());
					emplJson.put("cname", empl.getCname());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				emplArray.put(emplJson);
			}
			//log.debug(emplArray.toString());
			out.print(emplArray.toString());
			//JSONArray jArray = new JSONArray(empls, true);
			//out.print(jArray.toString());
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}
	
	private void getStudentPeriodData(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out = response.getWriter();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		StudAffairManager sm = (StudAffairManager) wac
				.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager) wac.getBean("scoreManager");

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");

		String studentNo = credential.getStudent().getStudentNo();
		Map TimeoffInit = new HashMap();

		String sdate = "";
		String edate = "";
		String docOid = "";
		try {
			sdate = jsonObj.getString("startDate");
			edate = jsonObj.getString("endDate");
			docOid = jsonObj.getString("docOid").trim();
		} catch (JSONException je) {
			je.printStackTrace();
		}
		int oid = 0; 
		if(!docOid.equals("")){
			oid = Integer.parseInt(docOid);
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();

		if(Toolket.isValidDate(sdate) && Toolket.isValidDate(edate)){
			startDate.setTime(Toolket.parseDate(sdate));
			endDate.setTime(Toolket.parseDate(edate));
			List<Map> periods = sm.findSeldInPeriod(studentNo, startDate, endDate);

			if(!periods.isEmpty()){
				JSONArray pArray = new JSONArray();
				Map pMap = periods.get(0);
				String preDate = periods.get(0).get("date").toString();
				Dilg dilg = null;
				dilg = sm.findDilgByStdDate(studentNo, preDate);
				String tdate = "";
				String[] periodd = new String[16];
				String[] absName = new String[16];
				String[] csName = new String[16];
				String[] applied = new String[16];
				for(int i = 0; i < 16; i++){
					periodd[i] = "n";
					absName[i] = "";
					csName[i] = "";
					applied[i] = "";
				}
				int sno = 0;
				int eno = 0;
				String courseName = "";
				
				List<StudDocDetail> docds = null;
				if(oid > 0){
					//註記oid所指定假單之日期節次
					docds = sm.getStudDocDetailByDate(studentNo, preDate);
					if(!docds.isEmpty()){
						for(StudDocDetail docd:docds){
							if(docd.getDocOid()==oid){
								applied[docd.getPeriod()] = "1";
							}
						}
					}
				}
				
				for (Map period : periods) {
					tdate = period.get("date").toString();
					if(tdate.equals(preDate)){
						sno = Integer.parseInt(period.get("begin").toString());
						eno = Integer.parseInt(period.get("end").toString());
						courseName = scm.findCourseName(period.get("cscode").toString());
						for(int j = sno; j <= eno; j++){
							//check period has applied for absence
							if(applied[j].equals("1")){
								periodd[j] = "1";
							}else{
								periodd[j] = "y";
							}
							
							absName[j] = "";
							csName[j] = courseName;
							
							
							if(dilg!=null){
								if(dilg.getAbs(j) != null){
									if(dilg.getAbs(j) != 0){
										absName[j] = Toolket.getTimeOff(dilg.getAbs(j).toString()).substring(1,2);
									}
								}
							}
						}
					}else{
						try {
							JSONObject initJson = new JSONObject();
							initJson.put("studentNo", pMap.get("studentNo").toString());
							initJson.put("tdate", pMap.get("date").toString());
							initJson.put("courseName", new JSONArray(csName));
							initJson.put("periods", new JSONArray(periodd));
							initJson.put("absName", new JSONArray(absName));
							pArray.put(initJson);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pMap = period;
						preDate = pMap.get("date").toString();
						dilg = sm.findDilgByStdDate(studentNo, preDate);
						for(int i = 0; i < 15; i++){
							periodd[i] = "n";
							absName[i] = "";
							csName[i] = "";
							applied[i] = "";
						}
						sno = Integer.parseInt(pMap.get("begin").toString());
						eno = Integer.parseInt(pMap.get("end").toString());
						if(oid > 0){
							//註記oid所指定假單之日期節次
							docds = sm.getStudDocDetailByDate(studentNo, preDate);
							if(!docds.isEmpty()){
								for(StudDocDetail docd:docds){
									if(docd.getDocOid()==oid){
										applied[docd.getPeriod()] = "1";
									}
								}
							}
						}
						
						for(int j = sno; j <= eno; j++){
							//check period has applied for absence
							if(applied[j].equals("1")){
								periodd[j] = "1";
							}else{
								periodd[j] = "y";
							}

							absName[j] = "";
							
							if(dilg!=null){
								if(dilg.getAbs(j) != null){
									if(dilg.getAbs(j) != 0){
										absName[j] = Toolket.getTimeOff(dilg.getAbs(j).toString()).substring(1,2);
									}
								}
							}
						}

					}
				}
				//Add last record data
				try {
					JSONObject initJson = new JSONObject();
					initJson.put("studentNo", pMap.get("studentNo").toString());
					initJson.put("tdate", pMap.get("date").toString());
					initJson.put("cscode", pMap.get("cscode").toString());
					initJson.put("courseName", scm.findCourseName(pMap.get("cscode").toString()));
					initJson.put("periods", new JSONArray(periodd));
					initJson.put("absName", new JSONArray(absName));
					pArray.put(initJson);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				out.print(pArray.toString());
				
			}else{
				out.print(JSONObject.NULL);				
			}

		}
		out.flush();
		out.close();
	}

	
	private void GetTchTimeOffInputData_old(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out = response.getWriter();
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		StudAffairManager sm = (StudAffairManager) wac
				.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager) wac.getBean("scoreManager");

		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");

		Map TimeoffInit = new HashMap();

		String dtOid = "";
		String tdate = "";
		try {
			dtOid = jsonObj.getString("dtOid");
			tdate = jsonObj.getString("tdate");
		} catch (JSONException je) {
			je.printStackTrace();
		}

		Dtime dtime = sm.getDtimeByOid(Integer.parseInt(dtOid));

		String courseType = dtime.getOpt(); // 課程類別:1=必修, 2=選修, 3=..., 4=...
		int isOpen = dtime.getOpen(); // 是否開放選修: 0=不開放, 1=開放, 2=..., 3=..
		String daynite;
		String myclass = dtime.getDepartClass();
		String cscode = dtime.getCscode();
		String[] tdateArray = tdate.split("-");

		// 由班級代碼判斷部制為:日間部,進修部(夜間),進修學院專校
		char dyflag = myclass.toUpperCase().charAt(1);

		switch (dyflag) {
		case '1':
		case '4':
		case '6':
		case 'A':
		case 'C':
		case 'F':
			daynite = "1";
			break;
		case '2':
		case '5':
		case '8':
		case '9':
		case 'B':
		case 'D':
		case 'H':
			daynite = "2";
			break;
		case '3':
		case '7':
			daynite = "3";
			break;
		default:
			daynite = "1";
		}

		List baseClassBook = new ArrayList();
		List classBookList = new ArrayList();
		List dtimelist = new ArrayList();
		List subjects = new ArrayList(); // 存放所有課程之資訊
		List subjectsInUse = new ArrayList(); // 存放classBook課程之資訊

		Dtime subject;
		int dtime_begin = 0;
		int dtime_end = 0;
		String dtime_chiName = "";
		String sddate = tdate;
		Calendar tfdate = Calendar.getInstance();
		tfdate.set(Calendar.YEAR, Integer.parseInt(tdateArray[0]));
		tfdate.set(Calendar.MONTH, Integer.parseInt(tdateArray[1]) - 1);
		tfdate.set(Calendar.DATE, Integer.parseInt(tdateArray[2]));

		// 1:SUNDAY, 2:MONDAY,...
		int iweek = tfdate.get(Calendar.DAY_OF_WEEK);
		if (iweek == 1)
			iweek = 7;
		else
			iweek--;
		TimeoffInit.put("tfWeek", ("" + iweek));
		TimeoffInit.put("daynite", daynite);
		TimeoffInit.put("clazz", myclass);
		TimeoffInit.put("depClassName", Toolket.getClassFullName(myclass));
		TimeoffInit.put("cscode", cscode);
		TimeoffInit.put("cscodeName", scm.findCourseName(cscode));
		TimeoffInit.put("tfYear", tdateArray[0]);
		TimeoffInit.put("tfMonth", tdateArray[1]);
		TimeoffInit.put("tfDay", tdateArray[2]);
		JSONObject initJson = new JSONObject();
		try {
			initJson.put("tfWeek", ("" + iweek));
			initJson.put("daynite", daynite);
			initJson.put("clazz", myclass);
			initJson.put("depClassName", Toolket.getClassFullName(myclass));
			initJson.put("cscode", cscode);
			initJson.put("cscodeName", scm.findCourseName(cscode));
			initJson.put("tfYear", tdateArray[0]);
			initJson.put("tfMonth", tdateArray[1]);
			initJson.put("tfDay", tdateArray[2]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<DtimeClass> subjList = sm.getDtimeClassByDtimeOid(Integer
				.parseInt(dtOid), iweek);
		// TimeoffInit.put("TimeOffSubjInUse", subjList);
		JSONArray subjArray = new JSONArray(subjList, true);

		List<Student> studlist = sm.findStudentsByClass(myclass);

		Map classBookMap = new HashMap(); // 存放classbook點名簿資訊
		List<Map> classBook = new ArrayList<Map>();
		;
		JSONArray cbArray = new JSONArray();

		if (courseType.equals("2") || courseType.equals("3")) { // 選修及通識課程直接抓Seld選修的學生
			if (subjList.size() > 0) {
				int[] period = new int[16];
				for (int j = 0; j < 16; j++)
					period[j] = 0;

				int period_begin = 16;
				int period_end = 16;

				// 同一天同一門選修課可能分散在不同節次上課,
				// 所以先統計一下該科目的第一及最後一節的上課區間
				for (DtimeClass dtimeClass : subjList) {
					dtime_begin = Integer.parseInt(dtimeClass.getBegin());
					dtime_end = Integer.parseInt(dtimeClass.getEnd());

					if (period_begin == 16)
						period_begin = dtime_begin;
					for (int i = dtime_begin; i <= dtime_end; i++) {
						period[i] = 1;
					}
				}
				period_end = dtime_end;

				// 處理點名簿資訊,無論該科目同一天分散在幾節上課,都只會執行for loop一次
				//
				// for(Iterator subjIter = subjList.iterator();
				// subjIter.hasNext();) {
				// dtimeObj = (Object[])subjList.get(0);
				Map subjMap = new HashMap();

				List<Seld> seldList = sm.findSeldForClassBook(dtime.getOid(),
						dtime.getDepartClass(), 0);

				for (Iterator<Seld> seldIter = seldList.iterator(); seldIter
						.hasNext();) {
					Seld seld = seldIter.next();
					Map stuMap = new HashMap();

					stuMap.put("studentNo", seld.getStudentNo());
					stuMap.put("studentName", seld.getStudentName());
					for (int k = 0; k < 16; k++) {
						if (period[k] == 1) {
							stuMap.put("status" + k, "");
						} else if (period[k] == 0) {
							stuMap.put("status" + k, "x");
						}
					}
					classBook.add(stuMap);
				}

				List<Map> Dilg4ClassBook = sm.findDilg4ClassBook(classBook,
						sddate, period_begin, period_end);
				// log.debug("Dilg4ClassBook.size()->" + Dilg4ClassBook.size());

				String studNo;
				for (Iterator cbIter = classBook.iterator(); cbIter.hasNext();) {
					Map cbMap = (Map) cbIter.next();
					studNo = cbMap.get("studentNo").toString();

					if (!Dilg4ClassBook.isEmpty()) {
						for (ListIterator<Map> dilg4cbIter = Dilg4ClassBook
								.listIterator(); dilg4cbIter.hasNext();) {
							// if(dilg4cbIter.nextIndex() < dIndex)
							// continue;
							Map d4cbMap = dilg4cbIter.next();
							if (studNo.equals(d4cbMap.get("studentNo")
									.toString())) {
								cbMap.put("status"
										+ d4cbMap.get("period").toString(),
										d4cbMap.get("status").toString());
								// dIndex = dilg4cbIter.nextIndex();
							}
						}
					}
				}
				/*
				 * classBookMap.put("cscode", dtime.getOid());
				 * classBookMap.put("cscodeName", ""); classBookMap.put("begin",
				 * period_begin); classBookMap.put("end", period_end);
				 * classBookMap.put("classBook", classBook);
				 * classBookMap.put("classBookInSubject", subjList);
				 * classBookList.add(classBookMap);
				 */
			} else {
				// 該班今日無任何課程, return null JSON Object
				out.print(JSONObject.NULL);
				out.flush();
				out.close();
			}
			// TimeoffInit.put("classBook", classBook);
			// cbArray = new JSONArray(classBook, true);

		} else if (courseType.equals("1")) { // 必修課程抓該班的學生,加入選修(如果開放選修)及註記退選學生
			if (subjList.size() > 0) {
				int[] period = new int[16];
				for (int j = 0; j < 16; j++)
					period[j] = 0;

				int period_begin = 16;
				int period_end = 16;

				// 同一天同一門選修課可能分散在不同節次上課,
				// 所以先統計一下該科目的第一及最後一節的上課區間
				for (DtimeClass dtimeClass : subjList) {
					dtime_begin = Integer.parseInt(dtimeClass.getBegin());
					dtime_end = Integer.parseInt(dtimeClass.getEnd());

					if (period_begin == 16)
						period_begin = dtime_begin;
					for (int i = dtime_begin; i <= dtime_end; i++) {
						period[i] = 1;
					}
				}
				period_end = dtime_end;

				// 處理點名簿資訊
				// *** STEP 1: 將學生加入 classBook
				classBookMap = new HashMap(); // 存放classbook點名簿資訊
				classBook = new ArrayList<Map>();
				// dtimeObj = (Object[])subjList.get(0);
				Map subjMap = new HashMap();

				for (Iterator<Student> stuIter = studlist.iterator(); stuIter
						.hasNext();) {
					Student student = stuIter.next();
					Map stuMap = new HashMap();
					stuMap.put("studentNo", student.getStudentNo());
					stuMap.put("studentName", student.getStudentName());

					classBook.add(stuMap);
				}

				// 加入必修課程非開課班之選修學生
				List<Seld> seldNotInClass = sm.findSeldForClassBook(dtime
						.getOid(), dtime.getDepartClass(), 2);
				for (Iterator<Seld> seldIter = seldNotInClass.iterator(); seldIter
						.hasNext();) {
					Seld seld = seldIter.next();
					Map stuMap = new HashMap();

					stuMap.put("studentNo", seld.getStudentNo());
					stuMap.put("studentName", seld.getStudentName());
					classBook.add(stuMap);
				}

				// 加入每節課之基本狀態資訊, x:表示非本課程之上課節次
				for (Map stMap : classBook) {
					for (int k = 0; k < 16; k++) {
						if (period[k] == 1) {
							stMap.put("status" + k, "");
						} else if (period[k] == 0) {
							stMap.put("status" + k, "x");
						}
					}
				}

				// *** STEP 2:註記曠缺課請假等狀態資料及退選學生
				List<Map> Dilg4ClassBook = sm.findDilg4ClassBook(classBook,
						sddate, period_begin, period_end);
				// log.debug("Dilg4ClassBook.size()->" + Dilg4ClassBook.size());
				List<Adcd> adcdlist = sm.findAdcdForClassBook(dtime.getOid(),
						"D"); // 找出退選學生

				int kk = 0;
				String studNo;
				boolean isFound = false;

				for (Iterator cbIter = classBook.iterator(); cbIter.hasNext();) {
					Map cbMap = (Map) cbIter.next();
					studNo = cbMap.get("studentNo").toString();

					if (!Dilg4ClassBook.isEmpty()) {
						for (ListIterator<Map> dilg4cbIter = Dilg4ClassBook
								.listIterator(); dilg4cbIter.hasNext();) {
							// if(dilg4cbIter.nextIndex() < dIndex)
							// continue;
							Map d4cbMap = dilg4cbIter.next();
							if (studNo.equals(d4cbMap.get("studentNo")
									.toString())) {
								if (!cbMap.get(
										"status"
												+ d4cbMap.get("period")
														.toString()).toString()
										.equalsIgnoreCase("x")) {
									cbMap.put("status"
											+ d4cbMap.get("period").toString(),
											d4cbMap.get("status").toString());
									// dIndex = dilg4cbIter.nextIndex();
								}
							}
						}
					}
					if (adcdlist.size() > 0) {
						for (Iterator<Adcd> adcdIter = adcdlist.iterator(); adcdIter
								.hasNext();) {
							Adcd adcd = adcdIter.next();
							if (studNo.equals(adcd.getStudentNo())) {
								for (kk = period_begin; kk <= period_end; kk++) {
									if (!cbMap.get("status" + kk).toString()
											.equalsIgnoreCase("x")) {
										cbMap.put("status" + kk, "w");
									}
								}
							}
						}

					}

				}

				// *** STEP 2: 處理扣考資料
				/*
				 * 目前不用, 保留此功能 if(dtime.getCredit() >0){ int limitHours = 0;
				 * String sterm = Toolket.getSysParameter("School_term");
				 *  // 1.計算該課程全學期應上課時數(一般:學分數*18週, 畢業班:學分數*14週) if
				 * (Toolket.chkIsGraduateClass(dtime.getDepartClass()) &&
				 * sterm.equals("2")){ if(dtime.getThour() * 14 % 3 > 0)
				 * limitHours = dtime.getThour() * 14 / 3 + 1; else limitHours =
				 * dtime.getThour() * 14 / 3; }else limitHours =
				 * dtime.getThour() * 18 / 3;
				 *  // 2.計算該課程學生曠缺記錄-->該班學生 int[] timeOffSum =
				 * sm.calTimeOffBySubject(classBook, dtime.getOid());
				 * //log.debug("timeOffSum.size()->" + timeOffSum.length);
				 * 
				 * int tfIndex = 0; for (Iterator cbIter = classBook.iterator();
				 * cbIter .hasNext();) { Map cbMap = (Map) cbIter.next();
				 *  // 退選不計算扣考 for(kk=dtime_begin; kk<=dtime_end; kk++) {
				 * 
				 * if (cbMap.get("status" + kk).toString().equals("w")) { break; }
				 * else if (timeOffSum[tfIndex] >= limitHours) {
				 * //log.debug("StudTimeOffInputBatch->limitHours:timeOffSum:"
				 * +limitHours+":"+timeOffSum[tfIndex]); //cbMap.put("status" +
				 * kk, "n"); //cbMap.put("status" + kk, ""); } } tfIndex++; } }
				 */
				/*
				 * classBookMap.put("cscode", dtime.getOid());
				 * classBookMap.put("cscodeName", ""); classBookMap.put("begin",
				 * period_begin); classBookMap.put("end", period_end);
				 * classBookMap.put("classBook", classBook);
				 * classBookMap.put("classBookInSubject", subjList);
				 * classBookList.add(classBookMap);
				 */
			} else {
				// 該班今日無任何課程, return null JSON Object
				out.print(JSONObject.NULL);
				out.flush();
				out.close();
			}
			//TimeoffInit.put("classBook", classBook);
			//cbArray = new JSONArray(classBook, true);
		}

		if (classBook.isEmpty()) {
			// 沒有查到任何開課課程及相關選課學生, return null JSONObject
			out.print(JSONObject.NULL);
			out.flush();
			out.close();
		} else {
			for (Map cbMap : classBook) {
				JSONObject cbJson = new JSONObject();
				try {
					cbJson.put("studentNo", cbMap.get("studentNo").toString());
					cbJson.put("studentName", cbMap.get("studentName")
							.toString());
					for (int k = 0; k < 16; k++) {
						cbJson.put("status" + k, cbMap.get("status" + k)
								.toString());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cbArray.put(cbJson);
			}
		}

		JSONArray rArray = new JSONArray();
		rArray.put(initJson);
		rArray.put(subjArray);
		rArray.put(cbArray);
		//log.debug(subjArray.toString());
		//log.debug(cbArray.toString());
		//log.debug(rArray.toString());
		out.print(rArray.toString());
		//log.debug("return json:" + jArray.toString());

		out.flush();
		out.close();
	}

	private byte[] getBarCode(String code, String type) {
		
		Barcode barcode = null;
		try {
			barcode = BarcodeFactory.createCode39(code, false);
			//barcode.setBarWidth(600);
			barcode.setBarHeight(80);

		} catch (BarcodeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// We are created an image from scratch here, but for printing in Java, your
		// print renderer should have a Graphics internally anyway
		log.debug("barcode width:" + barcode.getWidth() + " , height:" + barcode.getHeight());
		BufferedImage image = new BufferedImage(barcode.getWidth(), barcode.getHeight(), BufferedImage.TYPE_INT_RGB);
		// We need to cast the Graphics from the Image to a Graphics2D - this is OK
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		// Barcode supports a direct draw method to Graphics2D that lets you position the
		// barcode on the canvas
		try {
			barcode.draw(g2, 0, 0);
		} catch (OutputException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		try {
			ImageIO.write(image,"jpeg", bas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data = bas.toByteArray();

		return data;
	}

	private Image getBarCodeImage(ServletContext context, String code, String type) {
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		Image image = null;
		java.awt.Image awImage = null;
		try {
			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"));
			File output = new File(tempDir, "barcodes.pdf");
			FileOutputStream fos = new FileOutputStream(output);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			if(type.equals("39")){
				//39
				Barcode39 code39 = new Barcode39();
				code39.setCode(code);
				code39.setStartStopText(false);
				image = code39.createImageWithBarcode(cb, null, Color.blue);
			}else if(type.equals("EAN")){
				//EAN
				BarcodeEAN codeEAN = new BarcodeEAN();
				codeEAN.setBarHeight(20f);
				codeEAN.setCodeType(codeEAN.EAN13);
				codeEAN.setCode(code);
				image = codeEAN.createImageWithBarcode(cb, null, null);
				awImage = codeEAN.createAwtImage(Color.white, Color.black);
				document.add(new Phrase(new Chunk(image, 0 ,0)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		System.out.println("barcode image type:" + image.getOriginalType());
		log.debug("barcode image type:" + image.getOriginalType());
		document.close();
		return image;
	}

	private byte[] getBarCodeAwImage(ServletContext context, String code, String type) {
		
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		File tempDir = new File(context
				.getRealPath("/WEB-INF/reports/temp/"));
		File pdf = new File(tempDir, "barcodes.pdf");
		FileOutputStream fospdf = null;
		try {
			fospdf = new FileOutputStream(pdf);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, fospdf);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		document.open();
		PdfContentByte cb = writer.getDirectContent();
		
		java.awt.Image awImage = null;
		com.lowagie.text.Image image = null;
		try {
			if(type.equals("128")){
				//128
				Barcode128 code128 = new Barcode128();
				//code39.setCode(code);
				code128.setCode("A120862034");
				code128.setBarHeight(35); 
				code128.setX(0.95f);
				
				code128.setStartStopText(false);
				code128.setGuardBars(false);
				code128.setExtended(false);
				code128.setChecksumText(false);
				//code39.setSize(-1f);
				awImage = code128.createAwtImage(Color.white, Color.black);
			}else if(type.equals("39")){
				//39
				Barcode39 code39 = new Barcode39();
				//code39.setCode(code);
				code39.setCode("A120862034");
				code39.setBarHeight(35); 
				code39.setX(0.95f);
				
				code39.setStartStopText(false);
				code39.setGuardBars(false);
				code39.setExtended(false);
				code39.setChecksumText(false);
				code39.setFont(code39.getFont());
				//code39.setSize(-1f);
				awImage = code39.createAwtImage(Color.white, Color.black);
				image = code39.createImageWithBarcode(cb, null, null);
				//image.getOriginalData();
				document.add(new Phrase(new Chunk(image, 0 ,0)));
				document.close();
			}else if(type.equals("EAN")){
				//EAN
				BarcodeEAN codeEAN = new BarcodeEAN();
				codeEAN.setBarHeight(20f);
				codeEAN.setCodeType(codeEAN.EAN13);
				codeEAN.setCode(code);
				awImage = codeEAN.createAwtImage(Color.white, Color.black);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
		BufferedImage bu = new BufferedImage(awImage.getWidth(null), awImage.getHeight(null) ,BufferedImage.TYPE_INT_RGB);
		Graphics g = bu.getGraphics();
		g.drawImage(awImage,0,0,null);
		g.dispose();
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		try {
			ImageIO.write(bu,"jpeg", bas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data = bas.toByteArray();

		
		//test to save image
		File output = new File(tempDir, "barcode.jpeg");
		try {
			FileOutputStream fos = new FileOutputStream(output);
			try {
				fos.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return data;
	}

	private byte[] getBarCodeArray(ServletContext context, String code, String type) {

		byte[] ret = null;
		try {
			Image image = getBarCodeImage(context, code, type);
			ret = image.getOriginalData();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		return ret;
	}

	private Jpeg getBarcodeJpeg(ServletContext context, String code, String type, int width, int height){
		Jpeg jpeg = null;
		try {
			Image image = getBarCodeImage(context, code, type);
			jpeg = new Jpeg(image.getOriginalData(), width, height);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}

		return jpeg;
	}

	
	private void GetStudentsInClazz(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String clazz = "";
		try{
			clazz = jsonObj.getString("clazz");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		List<Student> students = mm.findStudentsByClassNo(clazz);
		JSONArray stArray = new JSONArray();
		if(!students.isEmpty()){
			for(Student student:students){
				JSONObject stJson = new JSONObject();
				try {
					stJson.put("studentNo", student.getStudentNo());
					stJson.put("studentNameNo", student.getStudentNo() + " " + student.getStudentName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stArray.put(stJson);
			}
			//log.debug(emplArray.toString());
			out.print(stArray.toString());
			//JSONArray jArray = new JSONArray(empls, true);
			//out.print(jArray.toString());
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}
	
	private void GetStudent(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String studentNo = "";
		try{
			studentNo = jsonObj.getString("studentNo");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		Student student = Toolket.getStudentByNo(studentNo);
		if(student != null){
				JSONObject stJson = new JSONObject();
				try {
					stJson.put("studentNo", student.getStudentNo());
					stJson.put("studentName", student.getStudentName());
					stJson.put("departClass", student.getDepartClass());
					stJson.put("departClassName", Toolket.getClassFullName(student.getDepartClass()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			out.print(stJson.toString());
			//JSONArray jArray = new JSONArray(empls, true);
			//out.print(jArray.toString());
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}

	private void chkStudentInDepart(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String studentNo = "";
		String department = "";
		try{
			studentNo = jsonObj.getString("studentNo");
			department = jsonObj.getString("depart");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		Student student = Toolket.getStudentByNo(studentNo);
		JSONObject stJson = new JSONObject();
		if(student != null){
			List<Clazz> clazzes = Toolket.findAllClasses(student.getDepartClass());
			if(!clazzes.isEmpty()){
				Clazz clazz = clazzes.get(0);
				if(clazz.getDeptNo().equalsIgnoreCase(department)){
					try {
						stJson.put("studentNo", student.getStudentNo());
						stJson.put("studentName", student.getStudentName());
						stJson.put("departClass", student.getDepartClass());
						stJson.put("departClassName", Toolket.getClassFullName(student.getDepartClass()));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					out.print(stJson.toString());
					//JSONArray jArray = new JSONArray(empls, true);
					//out.print(jArray.toString());
				}else{
					//student not in department
					try {
						stJson.put("studentNo", "");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					out.print(stJson.toString());
				}
			}else{
				//system error: can't find clazz
				try {
					stJson.put("studentNo", "");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.print(stJson.toString());
			}
		}else{
			try {
				stJson.put("studentNo", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(stJson.toString());
		}
		
		out.flush();
		out.close();
		
	}

	private void chkStudentInClass(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String studentNo = "";
		String clazz = "";
		try{
			studentNo = jsonObj.getString("studentNo");
			clazz = jsonObj.getString("clazz");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		Student student = Toolket.getStudentByNo(studentNo);
		JSONObject stJson = new JSONObject();
		if(student != null){
				if(student.getDepartClass().trim().equalsIgnoreCase(clazz)){
					try {
						stJson.put("studentNo", student.getStudentNo());
						stJson.put("studentName", student.getStudentName());
						stJson.put("departClass", student.getDepartClass());
						stJson.put("departClassName", Toolket.getClassFullName(student.getDepartClass()));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					out.print(stJson.toString());
					//JSONArray jArray = new JSONArray(empls, true);
					//out.print(jArray.toString());
				}else{
					//student not in department
					try {
						stJson.put("studentNo", "");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					out.print(stJson.toString());
				}
		}else{
			try {
				stJson.put("studentNo", "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			out.print(stJson.toString());
		}
		
		out.flush();
		out.close();
		
	}
	
	private void getAssessPaper(HttpServletRequest request,
			HttpServletResponse response, JSONObject jsonObj)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		response.setContentType("text/xml; charset=UTF-8");

		PrintWriter out=response.getWriter();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		MemberManager mm = (MemberManager)wac.getBean("memberManager");
		
		
		String serviceNo = "";
		try{
			serviceNo = jsonObj.getString("serviceNo");
		}catch(JSONException je){
			je.printStackTrace();
		}
		
		AssessPaper paper = mm.findAssessPaperByNo(serviceNo);
		if(paper != null){
			JSONObject stJson = new JSONObject();
			try {
				stJson.put("paper", new JSONObject(paper));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Empl empl = mm.findEmplByIdno(paper.getIdno());
			if(empl != null){
				try {
					stJson.put("cname", empl.getCname());
					stJson.put("unit", empl.getUnit());
					stJson.put("unitName", Toolket.getUnitName(empl.getUnit()));
					stJson.put("reporterKind", paper.getReporterKind());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			out.print(stJson.toString());
			}else{
				out.print(JSONObject.NULL);
			}
		}else{
			out.print(JSONObject.NULL);
		}
		
		out.flush();
		out.close();
		
	}



}
