package tw.edu.chit.struts.action.studaffair;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class BonusPenaltySum4eduAction  extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
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
			
		setContentPage(session, "studaffair/BonusPenaltySum4edu.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm) form;
		String schoolYear = aForm.getString("schoolYear");
		String schoolTerm = aForm.getString("schoolTerm");

		boolean setupPrinter = false;
		boolean isPrint = false;
		

		if(!messages.isEmpty()){
			saveMessages(request, messages);
		}else{
			HttpSession session = request.getSession(false);

			UserCredential credential = getUserCredential(session);
			String idno = credential.getMember().getIdno();
			StudAffairManager sm = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
			
			//
			List<int[][]> bpList =  new ArrayList<int[][]>();
			List<int[][]> bpList2 =  new ArrayList<int[][]>();
			if(schoolTerm.equals("0")){
				bpList = sm.findBonusPenalty4edu(schoolYear, "1");
				bpList2 = sm.findBonusPenalty4edu(schoolYear, "2");
			}else{
				bpList = sm.findBonusPenalty4edu(schoolYear, schoolTerm);
			}
			
			ServletContext context = request.getSession().getServletContext();
			//Map tfMap = (Map)tfList.get(0);
			if (!bpList.isEmpty()) {
				File templateXLS = new File(context
						.getRealPath("/WEB-INF/reports/BonusPenalty4edu.xls"));
				

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
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
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
				
				
				int[][] qty = null;
				int[][] qty2 = null;
				int[] seq = {2,1,0,5,4,3,6,7};
				for(int sheetCnt=0; sheetCnt<=3; sheetCnt++){
					qty = bpList.get(sheetCnt);
					
					HSSFSheet sheet = workbook.getSheetAt(sheetCnt);
					sheet.setAutobreaks(false);// 自動換頁 - 關閉

					// 列印設置
					HSSFPrintSetup hps = sheet.getPrintSetup();
					hps.setPaperSize((short) 9); // 9=a4紙？
					hps.setLandscape(false); // 直印
										
					for(int row=0; row<=7; row++){
						for(int col=0; col<=5; col++){
							Toolket.setCellValueInt(sheet, row*2+5, col+6, qty[seq[row]*2][col]);
							Toolket.setCellValueInt(sheet, row*2+6, col+6, qty[seq[row]*2 +1][col]);
						}
					}

					if(schoolTerm.equals("1")){
						Toolket.setCellValue(sheet , 2, 3,	"第     1     學     期");
						Toolket.setCellValue(sheet , 2, 12,	"");
					}else if(schoolTerm.equals("2")){
						Toolket.setCellValue(sheet , 2, 3,	"第     2     學     期");
						Toolket.setCellValue(sheet , 2, 12,	"");
					}else if(schoolTerm.equals("0")){
						Toolket.setCellValue(sheet , 2, 3,	"第     1     學     期");
						Toolket.setCellValue(sheet , 2, 12,	"第     2     學     期");
						qty2 = bpList2.get(sheetCnt);
						
						for(int row=0; row<=7; row++){
							for(int col=0; col<=5; col++){
								Toolket.setCellValueInt(sheet, row*2+5, col+15, qty2[seq[row]*2][col]);
								Toolket.setCellValueInt(sheet, row*2+6, col+15, qty2[seq[row]*2 +1][col]);
							}
						}

					}
					
				}
				

				workbook.setDisplayedTab((short)0);
				
				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "SAF_BP4edu_" 
						+ idno
						+ (new SimpleDateFormat("yyyyMMddhhmmss")
						.format(new Date())) + ".xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				
				
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



}
