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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

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

import tw.edu.chit.model.StudDocApply;
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

public class Elearn2SeldTimeoffAction  extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","transfer");
		map.put("Cancel", "cancel");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
				
		setContentPage(session, "studaffair/Elearn2SeldTimeoff.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward transfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		ServletContext context = this.servlet.getServletContext();
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		session.setMaxInactiveInterval(7200);	//2Hr. for session timeout
		
		String dtOid = "";
		String studentNo = "";
		String dilgs = "";
		
		FormFile trFile = (FormFile)aForm.get("trFile");
		log.debug(trFile.getContentType());
		if(trFile==null || trFile.getFileSize() == 0 ||
				trFile.getContentType().indexOf("excel") == -1){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", "無檔案上傳或上傳的不是Excel檔案,轉換失敗!"));
			saveMessages(request, messages);
		}else{
			try{
				List<Map> edilgs = new ArrayList<Map>();
				String fname = context.getRealPath("/WEB-INF/reports/temp") + trFile.getFileName();
				FileOutputStream fileOutput = new FileOutputStream(fname); 

				fileOutput.write(trFile.getFileData()); 
				fileOutput.flush(); 
				fileOutput.close(); 
				trFile.destroy() ;  // destroy temperaty file

				File xlsf = new File(fname);

				HSSFWorkbook wb = Toolket.getHSSFWorkbook(xlsf);

				HSSFSheet sheet = wb.getSheetAt(0);
				int rows  = sheet.getPhysicalNumberOfRows();
				List cbList = new ArrayList();
				String prevStu = "";
				int dilgi = 0;
				
				for (int r = 1; r < rows; r++){
					Map<String, String> dMap = new HashMap<String, String>();
					HSSFRow row = sheet.getRow(r);
					int cells = row.getPhysicalNumberOfCells();

					// System.out.println("ROW " + row.getRowNum());
					dilgi = (int)row.getCell((short)2).getNumericCellValue();
					log.debug(dilgi);
					
					//if(dilgi > 0){
						dtOid = row.getCell((short)0).getStringCellValue();
						dtOid = dtOid.substring(dtOid.length()-5);
						if(dtOid.startsWith("0")){
							dtOid = dtOid.substring(1);
						}
						studentNo = row.getCell((short)1).getStringCellValue().toUpperCase();

						dMap.put("dtOid", dtOid);
						dMap.put("studentNo", studentNo);
						dMap.put("dilgs", "" + dilgi);
						
						edilgs.add(dMap);
						
						if(!studentNo.equalsIgnoreCase(prevStu)){
							dMap.put("isDataChg", "Y");	//for update just Score use only 
							cbList.add(dMap);
							prevStu = studentNo;
						}
					//}

				}
				ActionMessages err = sam.modifySeldElearnDilg(edilgs);
				if(!err.isEmpty()){
					saveMessages(request, err);
				}
				
				//更新操行成績
				ActionMessages err2 = sam.updateJustDilgScoreBatch(cbList);
				
				//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120308				
				Map cbMap = new HashMap();
				for(Iterator cbIter = cbList.iterator(); cbIter.hasNext();){	
					cbMap = (Map)cbIter.next();
					studentNo = cbMap.get("studentNo").toString().toUpperCase();
					double dilgScore = sam.calDilgScoreByStudent(studentNo, "0");
					CourseManager manager = (CourseManager) getBean("courseManager");
					String sqlstudent_no = manager.ezGetString(
							"Select student_no From just Where student_no='" + studentNo + "' ");
					double SeltotalScore = Double.parseDouble(
							manager.ezGetString("Select total_score From just Where student_no='" + studentNo + "' "));
					double totalScore = SeltotalScore+dilgScore;	
					if(sqlstudent_no.equals("")){
						err2.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
					}else{		
						manager.executeSql(
								"Update just Set total_score="+totalScore+" Where student_no='"+studentNo+"'");
					}
				//System.out.println("Leo_OK");
				}
				//=======================================================>>>>>>>>>>>>>>>>>>
				if(!err2.isEmpty()){
					saveErrors(request, err2);
				}
				
				xlsf.delete();
			}catch(Exception e){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1", e.toString()));
				saveErrors(request, messages);
			}
	        
		}
				
		setContentPage(request.getSession(false), "studaffair/Elearn2SeldTimeoff.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward cancel(ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response){

		return mapping.findForward("Main");
	}


}
