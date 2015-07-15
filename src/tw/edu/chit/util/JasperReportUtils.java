package tw.edu.chit.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

public class JasperReportUtils {

	/**
	 * 動態設定JasperReports之ClassPath
	 * 
	 * @param request javax.servlet.http.HttpServletResponse object
	 * @throws IOException
	 * @throws ServletException
	 */
	public static void initJasperReportsClasspath(HttpServletRequest request)
			throws IOException, ServletException {
		ServletContext context = request.getSession().getServletContext();
		JRProperties.setProperty(JRProperties.COMPILER_CLASSPATH, context
				.getRealPath("/WEB-INF/lib/jasperreports-1.3.4.jar")
				+ System.getProperty("path.separator")
				+ context.getRealPath("/WEB-INF/classes/"));
		JRProperties.setProperty(JRProperties.COMPILER_TEMP_DIR, context
				.getRealPath("/WEB-INF/reports/"));
	}

	public static JasperReport getNoResultReport(ServletContext context)
			throws JRException {
		File reportFile = new File(context
				.getRealPath("/WEB-INF/reports/NoResultSet.jasper"));
		JasperReportUtils.compileJasperReports(context
				.getRealPath("/WEB-INF/reports/NoResultSet.jrxml"));
		reportFile = new File(context
				.getRealPath("/WEB-INF/reports/NoResultSet.jasper"));
		if (!reportFile.exists())
			throw new JRRuntimeException(
					"查無\"NoResultSet.jasper\"檔案，請電洽電算中心，謝謝！！");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());
		return jasperReport;
	}

	public static JasperReport getProcessOkReport(ServletContext context)
			throws JRException {
		File reportFile = new File(context
				.getRealPath("/WEB-INF/reports/ProcessOK.jasper"));
		JasperReportUtils.compileJasperReports(context
				.getRealPath("/WEB-INF/reports/ProcessOK.jrxml"));
		reportFile = new File(context
				.getRealPath("/WEB-INF/reports/ProcessOK.jasper"));
		if (!reportFile.exists())
			throw new JRRuntimeException(
					"查無\"ProcessOK.jasper\"檔案，請電洽電算中心，謝謝！！");
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());
		return jasperReport;
	}

	/**
	 * 動態編譯JasperReports定義檔
	 * 
	 * @param path *.jrxml檔案路徑
	 * @throws JRException
	 */
	public static void compileJasperReports(String path) throws JRException {
		JasperCompileManager.compileReportToFile(path);
	}

	/**
	 * 將PDF二進位內容轉為頁面
	 * 
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @param data PDF Byte Array Data
	 * @throws IOException
	 */
	public static void printPdfToFrontEnd(HttpServletResponse response,
			byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		response.setContentType("application/pdf");
		response.setContentLength(data.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		int position = -1;
		byte[] buffers = new byte[4096];
		while ((position = bais.read(buffers)) != -1)
			ouputStream.write(buffers, 0, position);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 將PDF二進位內容轉為頁面
	 * 
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @param data PDF Byte Array Data
	 * @param filename XLS Filename
	 * @throws IOException
	 */
	public static void printXlsToFrontEnd(HttpServletResponse response,
			byte[] data, String filename) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename);
		response.setContentLength(data.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		int position = -1;
		byte[] buffers = new byte[4096];
		while ((position = bais.read(buffers)) != -1)
			ouputStream.write(buffers, 0, position);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 將PDF檔案內容轉為頁面
	 * 
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @param data PDF File
	 * @throws IOException
	 */
	public static void printPdfToFrontEnd(HttpServletResponse response, File pdf)
			throws IOException {

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				pdf));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while ((b = bis.read()) != -1) {
			baos.write(b);
		}
		printPdfToFrontEnd(response, baos.toByteArray());
	}

	/**
	 * 將XLS檔案內容轉為頁面
	 * 
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @param xls XLS File
	 * @throws IOException
	 */
	public static void printXlsToFrontEnd(HttpServletResponse response, File xls)
			throws IOException {

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				xls));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while ((b = bis.read()) != -1) {
			baos.write(b);
		}
		printXlsToFrontEnd(response, baos.toByteArray(), xls.getName());
	}

	/**
	 * 將多個PDF檔案合併為一個PDF檔案
	 * 
	 * @param files PDF Files Array
	 * @param dir Destination Directory
	 * @param filename Merged File's Name
	 * @return java.io.File Merged File
	 * @throws Exception
	 */
	public static File mergePdfs(File[] files, File dir, String filename)
			throws Exception {

		if (files.length == 0)
			return null;

		Document document = new Document();
		File returnFile = new File(dir, filename);
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(returnFile));
		document.open();
		for (File pdf : files) {
			PdfReader reader = new PdfReader(pdf.getAbsolutePath(), null);
			for (int i = 1; i <= reader.getNumberOfPages(); i++)
				copy.addPage(copy.getImportedPage(reader, i));
		}
		document.close();
		return returnFile;
	}

}
