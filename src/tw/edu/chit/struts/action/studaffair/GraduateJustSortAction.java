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

public class GraduateJustSortAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("PrintOption","print");
		map.put("PrintPreview","print");
		map.put("Cancel", "cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		session.removeAttribute("GradJustSortInit");
		session.removeAttribute("GJSPrintReport");
		
		setContentPage(session, "studaffair/GraduateJustSort.jsp");
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
		String snumber  = aForm.getString("sortNumber"); //列印幾名
		String lastTerm = aForm.getString("haveLast"); //包含最後一學期
		int number = 0;
		boolean haveLast = false;
		
		if(!snumber.trim().equals("") && Toolket.isNumeric(snumber)){
			number = Integer.parseInt(snumber);
		}
		
		if(!lastTerm.trim().equals("")){
			if(lastTerm.equals("0")){
				haveLast = false;
			}else if(lastTerm.equals("1")){
				haveLast = true;
			}
		}
		
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
		session.setAttribute("GradJustSortInit", initMap);
		
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
			//SAF-TimeOffAlert4T1  導師聯絡家長記錄
			//SAF-TimeOffAlert4T2  導師聯絡家長核對記錄
			String reportSourceFile1 = "/WEB-INF/reports/SAF-GraduateJustSort.jrxml";
			String reportCompiledFile1 = "/WEB-INF/reports/SAF-GraduateJustSort.jasper";
			String reportSourceFile2 = "/WEB-INF/reports/SAF-GraduateNoAbsent.jrxml";
			String reportCompiledFile2 = "/WEB-INF/reports/SAF-GraduateNoAbsent.jasper";

			UserCredential credential = getUserCredential(session);
			MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
			CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
			ScoreManager scm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			String clazzFilter = "";
			Map condMap = new HashMap();
			List justList = new ArrayList();
			List naList = new ArrayList();
			
			if(departClass.equalsIgnoreCase("All")){
				if("All".equals(dept)) clazzFilter = campus + school;
				else clazzFilter = campus + school + dept;
			}else if("All".equals(dept)){
				clazzFilter = campus + school;
			}else{
				clazzFilter = departClass;
			}
			
			try{
				condMap = sm.findGraduateJustSort(clazzFilter, number, haveLast);
				justList = (ArrayList)condMap.get("sortCond");
				naList = (ArrayList)condMap.get("noAbsent");
				
			}catch(Exception e){
				e.printStackTrace();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", e.toString()));
				saveErrors(request, messages);
				setContentPage(request.getSession(false), "studaffair/GraduateJustSort.jsp");
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
			
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);
			//log.debug("No Absent size:" + naList.size());
			
			if (!justList.isEmpty() || !naList.isEmpty()) {
				DecimalFormat df = new DecimalFormat(",##0.0");
				File reportFile1 = new File(context.getRealPath(reportCompiledFile1));
				File reportFile2 = new File(context.getRealPath(reportCompiledFile2));
				
				// 需要時再編譯即可
				//if (!reportFile1.exists() || !reportFile2.exists()) {
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
								"查無\"SAF-GraduateJustSort.jasper\"檔案，請電洽電算中心，謝謝！！");
					if (!reportFile2.exists())
						throw new JRRuntimeException(
								"查無\"SAF-GraduateNoAbsent.jasper\"檔案，請電洽電算中心，謝謝！！");
				//}

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

				String[] fields1 = {"deptClassName", "order",
						"studentNo", "studentName", "score"};
				String[] fields2 =  {"deptClassName","studentName"};
				
				String fID = credential.getMember().getIdno().substring(1);
				
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				parameters1.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters1.put("SchoolYear", syear);
				parameters1.put("SchoolTerm", sterm);
				parameters1.put("sortNumber", snumber);

				parameters2.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
				parameters2.put("SchoolYear", syear);
				parameters2.put("SchoolTerm", sterm);
				
				//printData2 只列印掛號郵件執據
				printData1 = fillPrintData(justList,fields1);
							
				printData2 = fillPrintData(naList,fields2);			
								
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
								.getRealPath(ranDir + "GraduateJust" + fID + "2.pdf"))));
				OutputStream os1 = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "GraduateJust" + fID + "1.pdf"))));
					
				JasperExportManager.exportReportToPdfStream(
						jasperPrint2, os2);
				JasperExportManager.exportReportToPdfStream(
						jasperPrint1, os1);
					
				if(os2!=null) os2.close();
				if(os1!=null) os1.close();
				Toolket.deleteDIR(tempdir);
				//Merge pdf files 
				try {
					List<InputStream> pdfs = new ArrayList<InputStream>();
					pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "GraduateJust"+fID+"1.pdf")));
					pdfs.add(new FileInputStream(context
							.getRealPath(ranDir + "GraduateJust"+fID+"2.pdf")));
					    	
					File of1 = new File(context
							.getRealPath(ranDir + "GraduateJustSort" + fID + ".pdf"));
					if(of1.exists()) {
						of1.delete();
						of1.createNewFile();
					}
					else of1.createNewFile();
							
					OutputStream output = new FileOutputStream(context
							.getRealPath(ranDir + "GraduateJustSort" + fID + ".pdf"));
					this.concatPDFs(pdfs, output, true);
					byte[] bytes = file2byte(of1);
					JasperReportUtils.printPdfToFrontEnd(response, bytes);
					output.close();
				} catch (Exception e) {
					System.out.println("Merge PDFs Fail!");
				    e.printStackTrace();
				}
									
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "查無符合的資料!!!"));
				saveMessages(request, messages);
				return mapping.findForward("NoData");
			}

		}
		
		setContentPage(request.getSession(false), "studaffair/JustLevelD.jsp");
		//return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		return null;
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
