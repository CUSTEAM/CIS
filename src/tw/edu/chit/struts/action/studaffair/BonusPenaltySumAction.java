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

public class BonusPenaltySumAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("PrintOption","print");
		map.put("PreviewPrint","print");
		map.put("Cancel", "cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		session.removeAttribute("BPSumInit");
		session.removeAttribute("BPSPrintReport");
		
		setContentPage(session, "studaffair/BonusPenaltySum.jsp");
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
		String mode = aForm.getString("mode");
		
		String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
		String syear = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR);

		boolean setupPrinter = false;
		boolean isPrint = false;
		
		HttpSession session = request.getSession(false);
		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		session.setAttribute("BPSumInit", initMap);
		
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
			String rptPath = "";
			String reportSourceFile1="";
			String reportCompiledFile1="";
			String ranDir = "/pages/report_temp/";
			String basePath = "";
			basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+ranDir;
			
			reportSourceFile1 = "/WEB-INF/reports/SAF-BonusPenaltySum" + mode + ".jrxml";
			reportCompiledFile1 = "/WEB-INF/reports/SAF-BonusPenaltySum" + mode + ".jasper";
			
			UserCredential credential = getUserCredential(session);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			String clazzFilter = "";
			List desdList = new ArrayList();
			if(departClass.equalsIgnoreCase("All")){
				if("All".equals(dept)) clazzFilter = campus + school;
				else clazzFilter = campus + school + dept;
			}else if("All".equals(dept)){
				clazzFilter = campus + school;
			}else{
				clazzFilter = departClass;
			}
			
			try{
				desdList = sm.BonusPenaltySum(clazzFilter, mode);
			}catch(Exception e){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", e.toString()));
				saveErrors(request, messages);
				setContentPage(request.getSession(false), "studaffair/BonusPenaltySum.jsp");
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			log.debug("Print Report records size:" + desdList.size());
			
			if (!desdList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				
				// 需要時再編譯即可
				// if (!reportFile.exists()) {
					JasperReportUtils.compileJasperReports(context
							.getRealPath(reportSourceFile1));
					reportFile1 = new File(context
							.getRealPath(reportCompiledFile1));
					
					if (!reportFile1.exists()){
							throw new JRRuntimeException(
							"查無\"SAF-BonusPenaltySum" + mode +".jasper\"檔案，請電洽電算中心，謝謝！！");
					}
				// }

				// new Boolean(Float.parseFloat($F{score}) >= 60.0f);
				JasperReport jasperReport1 = (JasperReport) JRLoader
						.loadObject(reportFile1.getPath());
				
				
				// JRField[] jrField = jasperReport.getFields();
				// for (JRField field : jrField) {
				// System.out.println("Name:" + field.getName());
				// JRPropertiesMap x = field.getPropertiesMap();
				// System.out.println();
				// }
				List<Object> printData1 = new ArrayList<Object>();
				Map<String, String> parameters1 = new HashMap<String, String>();
				String[] fields1 = null;
				if(mode.equals("1")){
					String[] fields = {"clazz", 
							"merit1", "merit2", "merit3", "merit4", "merit5", "merit6",
							"meritP1", "meritP2", "meritP3", "meritP4", "meritP5", "meritP6",
							"meritP", "meritSum", "offenseP", "offenseSum"};
					fields1 = fields;
				}else if(mode.equals("0")){
					String[] fields = {"clazz", "totalStu",
							"merit1", "merit2", "merit3", "merit4", "merit5", "merit6",
							"meritP1", "meritP2", "meritP3", "meritP4", "meritP5", "meritP6"};
					fields1 = fields;
				}
				String fID = credential.getMember().getIdno().substring(1);
				
				parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters1.put("SchoolYear", syear);
				parameters1.put("SchoolTerm", sterm);
				
				printData1 = fillPrintData(desdList,fields1);
							
				JasperPrint jasperPrint1 = JasperFillManager.fillReport(
						jasperReport1, parameters1,
						new HibernateQueryResultDataSource(printData1,
								fields1));
				
				// jasperPrint.
				// 列印或預覽
				if (isPrint) {
					//log.debug("Print Report records size:" + tfList.size());
					
					JasperPrintManager.printReport(jasperPrint1,
							setupPrinter);						
				} else {
					//Merge all PDF file
					
					byte[] bytes1 = JasperRunManager.runReportToPdf(
							jasperReport1, parameters1,
							new HibernateQueryResultDataSource(printData1,
									fields1));
					
					rptPath = context.getRealPath(ranDir + "BonusPenaltySum" + mode + fID + ".pdf");
					log.debug(rptPath);
					OutputStream os1 = new BufferedOutputStream(
							new FileOutputStream(new File(rptPath)));
					
					JasperExportManager.exportReportToPdfStream(
							jasperPrint1, os1);
					
					//JasperReportUtils.printPdfToFrontEnd(response, bytes1);
					if(os1!=null) os1.close();
					
					Map rptMap = new HashMap();
					rptMap.put("reportPath", basePath + "BonusPenaltySum" + mode  + fID + ".pdf");
					rptMap.put("reportTitle", "期末獎懲人次統計表");
					session.setAttribute("BPSPrintReport", rptMap);
				}
				
			}else{
				if (isPrint) {
					session.removeAttribute("BPSPrintReport");
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "查無符合的資料!!!"));
					saveMessages(request, messages);
				}else{
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "查無符合的資料!!!"));
					saveMessages(request, messages);
					/*
					rptPath = context.getRealPath("/pages/report_temp/NoData.pdf");
					Map rptMap = new HashMap();
					rptMap.put("reportPath", basePath + "NoData.pdf");
					rptMap.put("reportTitle", "操行優丙等名單");
					session.setAttribute("JLDPrintReport", rptMap);
					*/
				}
			}
		}
		
		setContentPage(request.getSession(false), "studaffair/BonusPenaltySum.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
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
