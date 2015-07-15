package tw.edu.chit.struts.action.AMS;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.AmsMeeting;
import tw.edu.chit.model.AmsMeetingAskLeave;
import tw.edu.chit.model.AmsMeetingData;
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.AmsManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class AmsMeetingAction extends BaseLookupDispatchAction {

	/**
	 * 首次進入
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "amsMeetingData");

		String schoolYear = cm.getNowBy("School_year");
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		AmsMeeting meeting = new AmsMeeting();
		meeting.setSchoolYear(schoolYear);
		meeting.setSchoolTerm(schoolTerm);
		List<AmsMeeting> meetings = ams.findAmsMeetingBy(meeting);
		Map<String, Integer> nextYearTerm = Toolket.getNextYearTerm();
		meeting.setSchoolYear(nextYearTerm
				.get(IConstants.PARAMETER_SCHOOL_YEAR).toString());
		meeting.setSchoolTerm(nextYearTerm
				.get(IConstants.PARAMETER_SCHOOL_TERM).toString());
		meetings.addAll(ams.findAmsMeetingBy(meeting));
		session.setAttribute("AmsMeetings", meetings);
		setContentPage(session, "AMS/AmsMeeting.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 選擇新增
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward chooseCreate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		String schoolYear = cm.getNowBy("School_year");
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("schoolTerm", schoolTerm);
		aForm.set("years", getYearArray(schoolYear));
		setContentPage(session, "AMS/AmsMeetingAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 刪除集會資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				"amsMeetingData");
		String[] meetingOids = oids.split("\\|");
		AmsMeetingAskLeave amal = null;
		ActionMessages messages = new ActionMessages();

		try {
			for (String oid : meetingOids)
				if (StringUtils.isNotBlank(oid) && StringUtils.isNumeric(oid)) {
					amal = new AmsMeetingAskLeave();
					amal.setMeetingOid(Integer.valueOf(oid));
					if (ams.findAmsMeetingAskLeavesBy(amal).isEmpty()) {
						ams.txDeleteAmsMeeting(Integer.valueOf(oid));
					} else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Course.errorN1",
										"欲刪除的集會,已有職員工請假紀錄,所以不得刪除,謝謝!"));
						saveMessages(request, messages);
						return unspecified(mapping, form, request, response);
					}
				}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除完成!"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", "刪除作業發生錯誤,請洽電算中心,謝謝!"));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	/**
	 * 選擇修改集會資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				"amsMeetingData");
		String[] meetingOids = oids.split("\\|");
		AmsMeeting meeting = null;
		String chooseOid = null;
		for (String oid : meetingOids)
			if (StringUtils.isNotBlank(oid) && StringUtils.isNumeric(oid))
				meeting = ams.findAmsMeetingBy(Integer
						.valueOf((chooseOid = oid)));

		if (meeting != null) {
			session.setAttribute("chooseOid", chooseOid);
			String schoolYear = cm.getNowBy("School_year");
			String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
			aForm.set("schoolYear", schoolYear);
			aForm.set("schoolTerm", schoolTerm);
			aForm.set("years", getYearArray(schoolYear));
			aForm.set("startNode", meeting.getStartNode().toString());
			aForm.set("endNode", meeting.getEndNode().toString());
			aForm.set("meetingName", meeting.getName());
			aForm.set("emplType", meeting.getEmplType());
			aForm.set("base", meeting.getBase().toString());
			aForm
					.set("meetingDate", Toolket.Date2Str(meeting
							.getMeetingDate()));
		} else {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無資料,請重新選取,謝謝!"));
			saveMessages(request, messages);
		}

		setContentPage(request, "AMS/AmsMeetingModify.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 確定修改集會資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward makeSure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		String oid = (String) session.getAttribute("chooseOid");
		
		AmsMeeting meeting = (AmsMeeting) ams.findObject(AmsMeeting.class,
				Integer.valueOf(oid));
		meeting = processMeeting(meeting, aForm);
		Empl empl = new Empl();
		if (!"A".equals(aForm.getString("emplType")))
			empl.setCategory(aForm.getString("emplType"));

		List<Empl> empls = mm.findEmplsBy(empl);
		

		try {
			ams.txSaveAmsMeeting(meeting, empls);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增完成!"));
			saveMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", "新增作業發生錯誤,請洽電算中心,謝謝!"));
			saveErrors(request, messages);
		}
		return unspecified(mapping, form, request, response);
	}

	/**
	 * 儲存集會資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;

		// Toolket.getDateOfWeek() 計算開學時間
		AmsMeeting meeting = processMeeting(null, aForm);
		Empl empl = new Empl();
		
		if (!"A".equals(aForm.getString("emplType")))
			empl.setCategory(aForm.getString("emplType"));

		List<Empl> empls = mm.findEmplsBy(empl);
		
		/*
		CourseManager manager = (CourseManager) getBean("courseManager");
		String SqlEmpltype = "";
		if (!"A".equals(aForm.getString("emplType"))){
			SqlEmpltype = "Where category='"+aForm.getString("emplType")+"' ";
		}
		List<Empl> empls = manager.ezGetBy("Select * From empl "+SqlEmpltype+" Order by unit");
		
		for(int i=0; i<empls.size(); i++){
			System.out.println(empls.get(i).getCname()+empls.get(i).getUnit());
		}*/

		try {
			ams.txSaveAmsMeeting(meeting, empls);
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增完成!"));
			saveMessages(request, messages);
		} catch (Exception e) {
			e.printStackTrace();
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", "新增作業發生錯誤,請洽電算中心,謝謝!"));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	/**
	 * 返回
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward emplList1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		String oid = request.getParameter("oid");
		AmsMeeting meeting = ams.findAmsMeetingBy(Integer.valueOf(oid));

		if (meeting != null && !meeting.getMeetingData().isEmpty()) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("出席人員名冊");
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 2000);
			sheet.setColumnWidth(2, 5000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 2000);
			sheet.setColumnWidth(5, 5000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

			HSSFFont fontSize16 = workbook.createFont();
			fontSize16.setFontHeightInPoints((short) 16);
			fontSize16.setFontName("標楷體");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("標楷體");
			// MeetingEmplList.xls
			// Header
			Toolket.setCellValue(workbook, sheet, 0, 0, "中華科技大學"
					+ meeting.getSchoolYear() + "學年度第"
					+ meeting.getSchoolTerm() + "學期" + meeting.getName()
					+ "出席人員名冊", fontSize16, HSSFCellStyle.ALIGN_CENTER, false,
					35.0F, null);
			// Column Header
			Toolket.setCellValue(workbook, sheet, 1, 0, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 1, "狀態", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 2, "簽名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 3, "姓名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 4, "狀態", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			Toolket.setCellValue(workbook, sheet, 1, 5, "簽名", fontSize12,
					HSSFCellStyle.ALIGN_CENTER, true, null);
			int index = 2;
			// Empl empl = null;
			AmsMeetingData[] data = meeting.getMeetingData().toArray(
					new AmsMeetingData[0]);
			AmsMeetingData amsMeetingData = null;

			for (int i = 0; i < data.length; i++) {

				amsMeetingData = data[i];
				// empl = mm.findEmplByIdno(amsMeetingData.getIdno());
				Toolket.setCellValue(workbook, sheet, index, 0, amsMeetingData
						.getEmplName(), fontSize12, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, StringUtils
						.defaultString(amsMeetingData.getStatus(), ""),
						fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, "", fontSize12,
						HSSFCellStyle.ALIGN_CENTER, true, null);

				if (i < data.length - 1) {
					amsMeetingData = data[++i];
					// empl = mm.findEmplByIdno(amsMeetingData.getIdno());
					Toolket.setCellValue(workbook, sheet, index, 3,
							amsMeetingData.getEmplName(), fontSize12,
							HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 4, StringUtils
							.defaultString(amsMeetingData.getStatus(), ""),
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					Toolket.setCellValue(workbook, sheet, index, 5, "",
							fontSize12, HSSFCellStyle.ALIGN_CENTER, true, null);
					index++;
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "MeetingList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager
					.runReportToPdf(JasperReportUtils
							.getNoResultReport(context), param,
							new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward emplList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		AmsManager ams = (AmsManager) getBean(IConstants.AMS_MANAGER_BEAN_NAME);
		String oid = request.getParameter("oid");
		//AmsMeeting meeting = ams.findAmsMeetingBy(Integer.valueOf(oid));
		//=========================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		CourseManager manager = (CourseManager) getBean("courseManager");
		List meeting = manager.ezGetBy("Select * From AMS_Meeting Where Oid='"+oid+"'");
		List meetingData = manager.ezGetBy("Select * From AMS_MeetingData md Where md.MeetingOid='"+oid+"' Order By Unit");

		if (meeting != null && !meetingData.isEmpty()) {   // && !meeting.getMeetingData().isEmpty()

			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/MeetingEmplList.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = null;

			HSSFFont fontSize18 = workbook.createFont();
			fontSize18.setFontHeightInPoints((short) 18);
			fontSize18.setFontName("標楷體");

			int sheetIndex = 0, index = 3;
			String unitCode = null;
			
			for(int j=0; j<meetingData.size(); j++){
				Map myMap=(Map) meetingData.get(j);
				//System.out.println(myMap+";unitCode="+unitCode);
				
				String myUnit = myMap.get("Unit").toString();
				String myEmplName = myMap.get("EmplName").toString();
				String myStatus="";
				if (myMap.get("Status")!=null){
					myStatus=myMap.get("Status").toString();
				}
				String myCategory = myMap.get("Category").toString();
				//unitCode = myMap.get("Unit").toString().trim();
				
				if (j == 0) {					
					//System.out.println("J=0--"+unitCode);
					unitCode = myUnit.trim();  //amsMeetingData.getUnit().trim();
					//System.out.println(myMap+";unitCode="+unitCode);
					
					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, StringUtils
							.isBlank(unitCode) ? "其他" : Toolket
							.getEmpUnit(unitCode));
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
							+ manager.ezGetString("Select SchoolYear From AMS_Meeting Where Oid='"+oid+"'") + "學年度第"
							+ manager.ezGetString("Select SchoolTerm From AMS_Meeting Where Oid='"+oid+"'") + "學期"
							+ manager.ezGetString("Select Name From AMS_Meeting Where Oid='"+oid+"'") + "出席人員名冊");
					Toolket.setCellValue(sheet, 1, 0, StringUtils
							.isBlank(unitCode) ? "其他" : Toolket
							.getEmpUnit(unitCode));
					index = 3;
				//} else if (!unitCode.equalsIgnoreCase(myUnit)) {
				}else if (!unitCode.equals(myUnit)) {	
					//System.out.println("J=3--"+unitCode);
					unitCode = myUnit.trim();
					//System.out.println(myMap+";unitCode="+unitCode);
					//String Sel_Unit = manager.ezGetString("Select name From CodeEmpl Where category in('Unit','UnitTeach') And idno='"+myUnit+"'");
					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, Toolket.getEmpUnit(myUnit));
					//workbook.setSheetName(sheetIndex++, Sel_Unit);
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學" +  manager.ezGetString("Select SchoolYear From AMS_Meeting Where Oid='"+oid+"'") + "學年度第" + 
					                                                manager.ezGetString("Select SchoolTerm From AMS_Meeting Where Oid='"+oid+"'") + "學期" + 
					                                                manager.ezGetString("Select Name From AMS_Meeting Where Oid='"+oid+"'") + "出席人員名冊");
					Toolket.setCellValue(sheet, 1, 0, Toolket.getEmpUnit(unitCode));
					index = 3;
				}
				//System.out.println(j+"-------------------------");

				Toolket.setCellValue(workbook, sheet, index, 0, myMap.get("EmplName").toString(), 
						fontSize18, HSSFCellStyle.ALIGN_CENTER,	true, null);
				Toolket.setCellValue(workbook, sheet, index, 4,	StringUtils.defaultString(myStatus, ""), 
						fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);		
				Toolket.setCellValue(workbook, sheet, index, 2, Toolket.getEmpCategory(myCategory),
						fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);

				
				//if (j < meetingData.size() - 1) {
					//myMap=(Map) meetingData.get(++j);					
					//if (!unitCode.equalsIgnoreCase(myUnit)) {
						//--j;
						//continue;
					//} else {

						Toolket.setCellValue(workbook, sheet, index, 0, myMap.get("EmplName").toString(), 
								fontSize18, HSSFCellStyle.ALIGN_CENTER,	true, null);
						Toolket.setCellValue(workbook, sheet, index, 4,	StringUtils.defaultString(myStatus, ""), 
								fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);		
						Toolket.setCellValue(workbook, sheet, index, 2, Toolket.getEmpCategory(myCategory),
								fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);
						index++;
					//}
				//}
				
				
			}
			//=========================================================>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// Leo20120328 因執行下列程式，系統無法運作，故以上列程式取代
			/*
			AmsMeetingData[] data = meeting.getMeetingData().toArray(
					new AmsMeetingData[0]);
			AmsMeetingData amsMeetingData = null;			

			for (int i = 0; i < data.length; i++) {

				amsMeetingData = data[i];
				if (i == 0) {
					unitCode = amsMeetingData.getUnit().trim();
					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, StringUtils
							.isBlank(unitCode) ? "其他" : Toolket
							.getEmpUnit(unitCode));
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
							+ meeting.getSchoolYear() + "學年度第"
							+ meeting.getSchoolTerm() + "學期"
							+ meeting.getName() + "出席人員名冊");
					Toolket.setCellValue(sheet, 1, 0, StringUtils
							.isBlank(unitCode) ? "其他" : Toolket
							.getEmpUnit(unitCode));
					index = 3;
				} else if (!unitCode.equalsIgnoreCase(amsMeetingData.getUnit())) {
					unitCode = amsMeetingData.getUnit().trim();
					sheet = workbook.getSheetAt(sheetIndex);
					workbook.setSheetName(sheetIndex++, Toolket
							.getEmpUnit(unitCode));
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學"
							+ meeting.getSchoolYear() + "學年度第"
							+ meeting.getSchoolTerm() + "學期"
							+ meeting.getName() + "出席人員名冊");
					Toolket.setCellValue(sheet, 1, 0, Toolket
							.getEmpUnit(unitCode));
					index = 3;
				}

				Toolket.setCellValue(workbook, sheet, index, 0, amsMeetingData
						.getEmplName(), fontSize18, HSSFCellStyle.ALIGN_CENTER,
						true, null);
				Toolket.setCellValue(workbook, sheet, index, 1, StringUtils
						.defaultString(amsMeetingData.getStatus(), ""),
						fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);
				Toolket.setCellValue(workbook, sheet, index, 2, Toolket
						.getEmpCategory(amsMeetingData.getCategory()),
						fontSize18, HSSFCellStyle.ALIGN_CENTER, true, null);

				if (i < data.length - 1) {
					amsMeetingData = data[++i];
					if (!unitCode.equalsIgnoreCase(amsMeetingData.getUnit())) {
						--i;
						continue;
					} else {

						Toolket.setCellValue(workbook, sheet, index, 3,
								amsMeetingData.getEmplName(), fontSize18,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 4,
								StringUtils.defaultString(amsMeetingData
										.getStatus(), ""), fontSize18,
								HSSFCellStyle.ALIGN_CENTER, true, null);
						Toolket.setCellValue(workbook, sheet, index, 5, Toolket
								.getEmpCategory(amsMeetingData.getCategory()),
								fontSize18, HSSFCellStyle.ALIGN_CENTER, true,
								null);
						index++;
					}
				}

			}*/

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "MeetingEmplList.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
		} else {
			Map<String, String> param = new HashMap<String, String>();
			File image = new File(context
					.getRealPath("/pages/images/2002chitS.jpg"));
			param.put("IMAGE", image.getAbsolutePath());
			byte[] bytes = JasperRunManager
					.runReportToPdf(JasperReportUtils
							.getNoResultReport(context), param,
							new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}
		return null;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "chooseCreate");
		map.put("Save", "save");
		map.put("Modify", "modify");
		map.put("makeSure", "makeSure");
		map.put("Delete", "delete");
		map.put("Back", "back");
		map.put("emplList", "emplList");
		return map;
	}

	private String[] getYearArray(String schoolYear) {
		int yearCal = 3;
		int year = Integer.parseInt(schoolYear);
		String[] years = new String[yearCal];
		years[0] = schoolYear;
		for (int i = 0; i < yearCal; i++)
			years[i] = String.valueOf((year++));
		return years;
	}

	private AmsMeeting processMeeting(AmsMeeting meeting, DynaActionForm form) {
		if (meeting == null)
			meeting = new AmsMeeting();
		meeting.setSchoolYear(form.getString("schoolYear"));
		meeting.setSchoolTerm(form.getString("schoolTerm"));
		meeting.setStartNode(Integer.valueOf(form.getString("startNode")));
		meeting.setEndNode(Integer.valueOf(form.getString("endNode")));
		meeting.setName(form.getString("meetingName").trim());
		meeting.setEmplType(form.getString("emplType"));
		meeting.setBase(Integer.valueOf(form.getString("base")));
		String date = form.getString("meetingDate");
		meeting.setMeetingDate(Toolket.parseNativeDate(date));
		return meeting;
	}

}
