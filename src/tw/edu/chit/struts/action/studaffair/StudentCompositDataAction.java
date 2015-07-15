package tw.edu.chit.struts.action.studaffair;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentCompositDataAction  extends BaseLookupDispatchAction{
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
		session.removeAttribute("CompositDataInit");
		session.removeAttribute("CompositDataReport");
		
			
		setContentPage(session, "studaffair/StudentCompositData.jsp");
		return mapping.findForward("Main");

	}

	/**
	 * 學生綜合資料表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @throws Exception
	 */
	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = this.servlet.getServletContext();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		ActionMessages msg = new ActionMessages();
		DecimalFormat df = new DecimalFormat(",##0.0");
		
		String schoolYear = aForm.getString("schoolYear");
		String schoolTerm = aForm.getString("schoolTerm");
		String campus = aForm.getString("campusInChargeSAF");
		String school = aForm.getString("schoolInChargeSAF");
		String dept = aForm.getString("deptInChargeSAF");
		String departClass  = aForm.getString("classInChargeSAF");
		String studentNo = aForm.getString("studentNo");
		
		Map initMap = new HashMap();
		initMap.put("campus", campus);
		initMap.put("school", school);
		initMap.put("dept", dept);
		initMap.put("departClass", departClass);
		session.setAttribute("CompositDataInit", initMap);
		
		if (studentNo.trim().equals("") && ("All".equals(campus)||"All".equals(school))) {
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "班級選擇範圍過大"));
		}
		
		if(!msg.isEmpty()){
			saveMessages(request, msg);
			return mapping.findForward("Error");
		}
		
		msg = validateInput(request);
		if(! msg.isEmpty()){
			ActionMessages err = new ActionMessages();
			err.add(msg);
			saveErrors(request, err);
			return mapping.findForward("Error");
		}

		String clazzFilter = "";
		
		if(departClass.equalsIgnoreCase("All")){
			if("All".equals(dept)) clazzFilter = campus + school;
			else clazzFilter = campus + school + dept;
		}else if("All".equals(dept)){
			clazzFilter = campus + school;
		}else{
			clazzFilter = departClass;
		}

		List<Map> comList = sam.getStudentsCompistData(schoolYear, schoolTerm, clazzFilter, studentNo);
		if (comList.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "查無學生綜合資料!"));
			saveErrors(request, messages);
			return mapping.findForward("NoData");
		} else {
			log.debug(comList.size());
			String[][] score = new String[10][4];
			String[] comment = new String[10];
			String deptClassName = "";
			String studNo = "";
			String studName = "";
			String studId = "";
			String sex = "";
			String birthday = "";
			DateFormat dayf = new SimpleDateFormat("yy/MM/dd");
			DateFormat dayflong = new SimpleDateFormat("yyyy/MM/dd");
			String printDate = dayflong.format(Calendar.getInstance().getTime());
			log.debug("print Date:" + printDate + ", Calendar:" + Calendar.getInstance().getTime());
			List<Map> bpList = new ArrayList();
			int i=0, j=0, sheetCnt=0;
			String title = "";

			File templateXLS = null;
			templateXLS = new File(context
									.getRealPath("/WEB-INF/reports/StudentCompositData.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			
			HSSFFont colorFont = workbook.createFont();
			colorFont.setColor(HSSFColor.RED.index);
			colorFont.setFontHeightInPoints((short) 12);
			//colorFont.setFontName("標楷體");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			//fontSize12.setFontName("標楷體");
			
			for(Map comMap:comList){
				deptClassName = comMap.get("deptClassName").toString();
				studNo = comMap.get("studentNo").toString();
				studName = comMap.get("studentName").toString();
				studId = comMap.get("id").toString();
				sex = (studId.charAt(1)=='1'?"男":"女");
				birthday = comMap.get("birthday").toString();
				
				score = (String[][])comMap.get("score");
				comment = (String[])comMap.get("comment");
				bpList = (List<Map>)comMap.get("bpList");
				
				HSSFSheet sheet = workbook.getSheetAt(sheetCnt);
				workbook.setSheetName(sheetCnt, studNo);
				//.createSheet(studNo);
		       sheet.setAutobreaks(false);//自動換頁 - 關閉
		       
		        //列印設置
		       HSSFPrintSetup hps = sheet.getPrintSetup();        
		       hps.setPaperSize((short) 9); // 9=a4紙？
		       hps.setLandscape(false); //直印
		       //workbook.setPrintArea(0, "$A$1:$C$2");

				Toolket.setCellValue(sheet, 2, 2, deptClassName);
				Toolket.setCellValue(sheet, 2, 6, studNo);
				Toolket.setCellValue(sheet, 2, 9, studName);
				Toolket.setCellValue(sheet, 3, 2, studId);
				Toolket.setCellValue(sheet, 3, 6, sex);
				Toolket.setCellValue(sheet, 3, 9, birthday);
				if(bpList.size() > 28) {
					Toolket.setCellValue(sheet, 4, 8, "第一頁");
				}
				
				for(i=0; i<4; i++){
					for(j=0; j<10; j++){
						Toolket.setCellValue(sheet, i+6, j+2, score[j][i]);
					}
				}
				for(i=0; i<10; i++){
					Toolket.setCellValue(sheet, i+11, 2, comment[i]);
				}
				i=0;
				for(Map<String, String> bpMap:bpList){
					if(i<=27){
						Toolket.setCellValue(sheet, i+23, 1, bpMap.get("occDate"));
						Toolket.setCellValue(sheet, i+23, 3, bpMap.get("kindcnt"));
						Toolket.setCellValue(sheet, i+23, 5, bpMap.get("reason"));
					}else{
						if(i==28){
							Toolket.setCellValue(sheet, 54, 2, deptClassName);
							Toolket.setCellValue(sheet, 54, 6, studNo);
							Toolket.setCellValue(sheet, 54, 9, studName);
							Toolket.setCellValue(sheet, 55, 2, studId);
							Toolket.setCellValue(sheet, 55, 6, sex);
							Toolket.setCellValue(sheet, 55, 9, birthday);
							Toolket.setCellValue(sheet, 56, 8, "第二頁");
						}
						Toolket.setCellValue(sheet, i+30, 1, bpMap.get("occDate"));
						Toolket.setCellValue(sheet, i+30, 3, bpMap.get("kindcnt"));
						Toolket.setCellValue(sheet, i+30, 5, bpMap.get("reason"));
					}
					i++;
				}
				Toolket.setCellValue(sheet, 51, 9, printDate);
				
				//沒有第二頁則刪除				
				if(bpList.size() > 28) {
					sheet.setRowBreak(51);
					Toolket.setCellValue(sheet, 97, 9, printDate);
				}else{
					for(i=52; i<=97; i++){
						HSSFRow row = sheet.getRow(i);
						if(row != null)
							sheet.removeRow(row);
					}
				}
				
			}
			//remove unused sheet
			for(i=comList.size(); i<60; i++){
				workbook.removeSheetAt(comList.size());
			}
			//List picList = workbook.getAllPictures();
			
			workbook.setDisplayedTab((short)0);
			
			log.debug(getUserCredential(session));
			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "StudentCompositData" 
					+ getUserCredential(session).getMember().getIdno()
					+ (new SimpleDateFormat("yyyyMMddhhmmss")
					.format(new Date())) + ".xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return mapping.findForward(null);
		}
	}

	private ActionMessages validateInput(HttpServletRequest request){
		ActionMessages msg = new ActionMessages();
		String schoolYear = request.getParameter("schoolYear");
		String schoolTerm = request.getParameter("schoolTerm");
		String studentNo = request.getParameter("studentNo");
		
		if(schoolYear.trim().equals("")){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "必須輸入學年!"));
		}else if(!StringUtils.isNumeric(schoolYear)){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "學年必須為數字!"));
		}
		if(schoolTerm.trim().equals("")){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "必須輸入學期!"));
		}else if(!StringUtils.isNumeric(schoolTerm)){
			msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1", "學期必須為數字!"));
		}
		return msg;
	}
}
