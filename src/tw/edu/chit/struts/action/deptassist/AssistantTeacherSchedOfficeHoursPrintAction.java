package tw.edu.chit.struts.action.deptassist;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.util.CollectionUtils;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class AssistantTeacherSchedOfficeHoursPrintAction extends BaseAction {

	/**
	 * 列印教師課表(包括OFfice Hours)
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = this.servlet.getServletContext();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		Integer oid = Integer.valueOf(request.getParameter("Oid"));
		Short colorForStayTime = HSSFColor.AUTOMATIC.index;
		Short colorForLifeCounseling = HSSFColor.LIGHT_GREEN.index;

		Empl empl = mm.findEmplByOid(oid);
		if (empl == null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無教師資料"));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/TeachSched.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("標楷體");
			HSSFSheet sheet = workbook.getSheetAt(0);
			Integer year = Integer
					.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
			Integer term = Integer
					.valueOf(am.findTermBy(PARAMETER_SCHOOL_TERM));
			Toolket.setCellValue(sheet, 0, 1, year + "學年度" + term + "學期 "
					+ empl.getCname() + " 老師授課時間表");

			List<Map> map = cm.findCourseByTeacherTermWeekdaySched(empl
					.getIdno(), term.toString());
			Map content = null;
			
			for (int i = 0; i < 14; i++) {
				for (int j = 0; j < 7; j++) {
					content = map.get(j * 15 + i);
					if (!CollectionUtils.isEmpty(content)) {
						Toolket.setCellValue(sheet, i + 2, j + 2,
								(String) content.get("ClassName") + "\n"
										+ (String) content.get("chi_name")
										+ "\n" + (String) content.get("place"));
					}
				}
			}

			
			
			List<TeacherStayTime> myTsts= cm.ezGetBy(
					" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
					"        Node11, Node12, Node13, Node14 " +
					" From TeacherStayTime "+
					" Where SchoolYear='"+ year +"'" +
					"   And SchoolTerm='"+ term +"' " +
					"   And parentOid='"+ empl.getOid() +"'");			
			
			List myTsts2 = new ArrayList();			
			
			int colOffset = 1, col = 0;
			for(int i=0; i<myTsts.size();i++){
			//for (TeacherStayTime tst : tsts) {				
				myTsts2.add(myTsts.get(i));
				String s = myTsts2.get(i).toString();
				col = Integer.parseInt(s.substring(6, 7)) + colOffset; //Week				
				//if (tst.getNode1() != null && tst.getNode1() == 1) {				
				if (Integer.parseInt(s.substring(15, 16)) == 1) {        //Node1
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 2, col)))
						Toolket.setCellValue(workbook, sheet, 2, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(24, 25)) == 1) {        //Node2
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 3, col)))
						Toolket.setCellValue(workbook, sheet, 3, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);					
				}
				if (Integer.parseInt(s.substring(33, 34)) == 1) {        //Node3
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 4, col)))
						Toolket.setCellValue(workbook, sheet, 4, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(42, 43)) == 1) {        //Node4
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 5, col)))
						Toolket.setCellValue(workbook, sheet, 5, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(51, 52)) == 1) {        //Node5
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 6, col)))
						Toolket.setCellValue(workbook, sheet, 6, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(60, 61)) == 1) {        //Node6
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 7, col)))
						Toolket.setCellValue(workbook, sheet, 7, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(69, 70)) == 1) {        //Node7
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 8, col)))
						Toolket.setCellValue(workbook, sheet, 8, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(78, 79)) == 1) {        //Node8
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 9, col)))
						Toolket.setCellValue(workbook, sheet, 9, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(87, 88)) == 1) {        //Node9
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 10, col)))
						Toolket.setCellValue(workbook, sheet, 10, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(97, 98)) == 1) {        //Node10
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 11, col)))
						Toolket.setCellValue(workbook, sheet, 11, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(107, 108)) == 1) {        //Node11
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 12, col)))
						Toolket.setCellValue(workbook, sheet, 12, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(117, 118)) == 1) {        //Node12
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 13, col)))
						Toolket.setCellValue(workbook, sheet, 13, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(127, 128)) == 1) {        //Node13
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 14, col)))
						Toolket.setCellValue(workbook, sheet, 14, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
				if (Integer.parseInt(s.substring(137, 138)) == 1) {        //Node14
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 15, col)))
						Toolket.setCellValue(workbook, sheet, 15, col, "留校時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForStayTime);
				}
			}

			//List<LifeCounseling> lcs = empl.getLifeCounseling();
			List<LifeCounseling> myLcs= cm.ezGetBy(
					" Select Week, Node1, Node2, Node3, Node4, Node5, Node6, Node7, Node8, Node9, Node10, " +
					"        Node11, Node12, Node13, Node14 " +
					" From LifeCounseling Where ParentOid='"+ empl.getOid() +"'");			
			
			List myLcs2 = new ArrayList();	
			colOffset = 1;
			col = 0;
			//for (LifeCounseling lc : lcs) {
			for(int y=0; y<myLcs.size();y++){
				
				myLcs2.add(myLcs.get(y));
				String st = myLcs2.get(y).toString();
				col = Integer.parseInt(st.substring(6, 7)) + colOffset;	
				//col = lc.getWeek() + colOffset;
				//if (lc.getNode1() != null && lc.getNode1() == 1) {
				if (Integer.parseInt(st.substring(15, 16)) == 1) {        //Node1
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 2, col)))
						Toolket.setCellValue(workbook, sheet, 2, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode2() != null && lc.getNode2() == 1) {
				if (Integer.parseInt(st.substring(24, 25)) == 1) {        //Node2
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 3, col)))
						Toolket.setCellValue(workbook, sheet, 3, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode3() != null && lc.getNode3() == 1) {
				if (Integer.parseInt(st.substring(33, 34)) == 1) {        //Node3
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 4, col)))
						Toolket.setCellValue(workbook, sheet, 4, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode4() != null && lc.getNode4() == 1) {
				if (Integer.parseInt(st.substring(42, 43)) == 1) {        //Node4
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 5, col)))
						Toolket.setCellValue(workbook, sheet, 5, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode5() != null && lc.getNode5() == 1) {
				if (Integer.parseInt(st.substring(51, 52)) == 1) {        //Node5
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 6, col)))
						Toolket.setCellValue(workbook, sheet, 6, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode6() != null && lc.getNode6() == 1) {
				if (Integer.parseInt(st.substring(60, 61)) == 1) {        //Node6
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 7, col)))
						Toolket.setCellValue(workbook, sheet, 7, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode7() != null && lc.getNode7() == 1) {
				if (Integer.parseInt(st.substring(69, 70)) == 1) {        //Node7
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 8, col)))
						Toolket.setCellValue(workbook, sheet, 8, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);					
				}
				//if (lc.getNode8() != null && lc.getNode8() == 1) {
				if (Integer.parseInt(st.substring(78, 79)) == 1) {        //Node8
					if (StringUtils
							.isEmpty(Toolket.getCellValue(sheet, 9, col)))
						Toolket.setCellValue(workbook, sheet, 9, col, "生活輔導時間",
								fontSize12, HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode9() != null && lc.getNode9() == 1) {
				if (Integer.parseInt(st.substring(87, 88)) == 1) {        //Node9
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 10, col)))
						Toolket.setCellValue(workbook, sheet, 10, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode10() != null && lc.getNode10() == 1) {
				if (Integer.parseInt(st.substring(97, 98)) == 1) {        //Node10
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 11, col)))
						Toolket.setCellValue(workbook, sheet, 11, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode11() != null && lc.getNode11() == 1) {
				if (Integer.parseInt(st.substring(107, 108)) == 1) {        //Node11
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 12, col)))
						Toolket.setCellValue(workbook, sheet, 12, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode12() != null && lc.getNode12() == 1) {
				if (Integer.parseInt(st.substring(117, 118)) == 1) {        //Node12
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 13, col)))
						Toolket.setCellValue(workbook, sheet, 13, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode13() != null && lc.getNode13() == 1) {
				if (Integer.parseInt(st.substring(127, 128)) == 1) {        //Node13
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 14, col)))
						Toolket.setCellValue(workbook, sheet, 14, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
				//if (lc.getNode14() != null && lc.getNode14() == 1) {
				if (Integer.parseInt(st.substring(137, 138)) == 1) {        //Node14
					if (StringUtils.isEmpty(Toolket
							.getCellValue(sheet, 15, col)))
						Toolket.setCellValue(workbook, sheet, 15, col,
								"生活輔導時間", fontSize12,
								HSSFCellStyle.ALIGN_CENTER, true,
								colorForLifeCounseling);
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "TeachSched.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		}
	}
}
