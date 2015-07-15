package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class IntrosPrintAction extends BaseAction {

	/**
	 * 簡介下載
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

		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ServletContext context = this.servlet.getServletContext();
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		Integer dtimeOid = Integer.valueOf(request.getParameter("Oid"));
		String mode = request.getParameter("mode");
		boolean isInMode = StringUtils.isNotBlank(mode)&& "in".equalsIgnoreCase("in");
		Integer year = null;
		boolean isHistory = false;
		try {
			year = Integer.valueOf((String) session.getAttribute("YearForSyllabus"));
			// isNeedCheck = false;
		} catch (Exception e) {
			try{
				year = cm.getSchoolYear();
			}catch(Exception e1){
				year = cm.getSchoolYear();
			}
			
		}

		Integer term = null;
		try {
			term = Integer.valueOf(aForm.getString("sterm"));
		} catch (Exception e) {
			try{
				term = Integer.valueOf((String) session
						.getAttribute("TermForSyllabus"));
			}catch(Exception e1){
				term=cm.getSchoolTerm();
			}
		}
		// FIXME School Year and Term
		// if (1 == term && isNeedCheck)
		// year++;

		CourseIntroduction ci = cm.getCourseIntrosByDtimeOid(dtimeOid, year, term);
		Savedtime sd = null;
		// isInMode是課程管理內使用的
		if (ci == null && isInMode) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無課程簡介資料"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);

		} else if (ci != null && isInMode) {
			File templateXLS = new File(context.getRealPath("/WEB-INF/reports/CourseIntrosPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Dtime d = null;
			if (!isHistory)
				d = cm.findDtimeBy(dtimeOid);
			else {
				d = new Dtime();
				d.setCscode(sd.getCscode());
				d.setTechid(sd.getTechid());
				d.setDepartClass(sd.getDepartClass());
				d.setCredit(sd.getCredit());
				d.setSterm(sd.getSchoolTerm().toString());
			}
			Csno csno = cm.findCourseInfoByCscode(d.getCscode());
			DEmpl de = null;
			String emplName = "";
			Employee employee = mm.findEmployeeByIdno(d.getTechid());
			if (employee == null) {
				if (StringUtils.isBlank(d.getTechid())) {
					emplName = "";
				} else {
					de = mm.findDEmplBy(new DEmpl(d.getTechid())).get(0);
					emplName = de.getCname();
				}
			} else
				emplName = employee.getName();

			Toolket.setCellValue(sheet, 2, 0, Toolket.getCellValue(sheet, 2, 0)
					.replaceAll("TECH", emplName).replaceAll("DEPT",
							Toolket.getClassFullName(d.getDepartClass())));
			Toolket.setCellValue(sheet, 3, 0, Toolket.getCellValue(sheet, 3, 0)
					.replaceAll("TITLE", csno.getChiName()));
			Toolket
					.setCellValue(
							sheet,
							4,
							0,
							Toolket
									.getCellValue(sheet, 4, 0)
									.replaceAll(
											"ENG",
											StringUtils.isBlank(csno
													.getEngName()) ? (StringUtils
													.isBlank(ci.getEngName()) ? ""
													: ci.getEngName())
													: csno.getEngName()));
			Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(sheet, 5, 0)
					.replaceAll("YEAR", ci.getSchoolYear().toString())
					.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
							d.getCredit().toString()));
			Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet, 7, 0)
					.replaceAll(
							"CHIINTRO",
							ci.getChiIntro() == null ? "" : ci.getChiIntro()
									.trim()));
			Toolket.setCellValue(sheet, 20, 0, Toolket.getCellValue(sheet, 20,
					0).replaceAll("ENGINTRO",
					ci.getEngIntro() == null ? "" : ci.getEngIntro().trim()));

			File tempDir = new File(context.getRealPath("/WEB-INF/reports/temp/"+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "CourseIntrosPrint.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		} else if (ci != null) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/CourseIntrosPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Dtime d = null;
			if (!isHistory)
				d = cm.findDtimeBy(dtimeOid);
			else {
				d = new Dtime();
				d.setCscode(sd.getCscode());
				d.setTechid(sd.getTechid());
				d.setDepartClass(sd.getDepartClass());
				d.setCredit(sd.getCredit());
				d.setSterm(sd.getSchoolTerm().toString());
			}
			Csno csno = cm.findCourseInfoByCscode(d.getCscode());
			DEmpl de = null;
			String emplName = "";
			Employee employee = mm.findEmployeeByIdno(d.getTechid());
			if (employee == null) {
				de = mm.findDEmplBy(new DEmpl(d.getTechid())).get(0);
				emplName = de.getCname();
			} else
				emplName = employee.getName();

			Toolket.setCellValue(sheet, 2, 0, Toolket.getCellValue(sheet, 2, 0)
					.replaceAll("TECH", emplName).replaceAll("DEPT",
							Toolket.getClassFullName(d.getDepartClass())));
			Toolket.setCellValue(sheet, 3, 0, Toolket.getCellValue(sheet, 3, 0)
					.replaceAll("TITLE", csno.getChiName()));
			Toolket
					.setCellValue(
							sheet,
							4,
							0,
							Toolket
									.getCellValue(sheet, 4, 0)
									.replaceAll(
											"ENG",
											StringUtils.isBlank(csno
													.getEngName()) ? (StringUtils
													.isBlank(ci.getEngName()) ? ""
													: ci.getEngName())
													: csno.getEngName()));
			Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(sheet, 5, 0)
					.replaceAll("YEAR", ci.getSchoolYear().toString())
					.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
							d.getCredit().toString()));
			Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet, 7, 0)
					.replaceAll(
							"CHIINTRO",
							ci.getChiIntro() == null ? "" : ci.getChiIntro()
									.trim()));
			Toolket.setCellValue(sheet, 20, 0, Toolket.getCellValue(sheet, 20,
					0).replaceAll("ENGINTRO",
					ci.getEngIntro() == null ? "" : ci.getEngIntro().trim()));

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "CourseIntrosPrint.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		} else {
			isHistory = true;
			sd = cm.findSavedtimeBy(dtimeOid);
			List<CourseIntroduction> css = cm.getCourseIntroHistBy(year, term,
					sd.getDepartClass(), sd.getCscode());
			if (!css.isEmpty())
				ci = css.get(0);

			if (ci != null) {
				File templateXLS = new File(context
						.getRealPath("/WEB-INF/reports/CourseIntrosPrint.xls"));
				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
				HSSFSheet sheet = workbook.getSheetAt(0);
				Dtime d = null;
				if (!isHistory)
					d = cm.findDtimeBy(dtimeOid);
				else {
					d = new Dtime();
					d.setCscode(sd.getCscode());
					d.setTechid(sd.getTechid());
					d.setDepartClass(sd.getDepartClass());
					d.setCredit(sd.getCredit());
					d.setSterm(sd.getSchoolTerm().toString());
				}
				Csno csno = cm.findCourseInfoByCscode(d.getCscode());
				DEmpl de = null;
				String emplName = "";
				Employee employee = mm.findEmployeeByIdno(d.getTechid());
				if (employee == null) {
					de = mm.findDEmplBy(new DEmpl(d.getTechid())).get(0);
					emplName = de.getCname();
				} else
					emplName = employee.getName();

				Toolket.setCellValue(sheet, 2, 0, Toolket.getCellValue(sheet,
						2, 0).replaceAll("TECH", emplName).replaceAll("DEPT",
						Toolket.getClassFullName(d.getDepartClass())));
				Toolket.setCellValue(sheet, 3, 0, Toolket.getCellValue(sheet,
						3, 0).replaceAll("TITLE", csno.getChiName()));
				Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(sheet,
						4, 0).replaceAll(
						"ENG",
						StringUtils.isBlank(csno.getEngName()) ? "" : csno
								.getEngName()));
				Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(sheet,
						5, 0).replaceAll("YEAR", ci.getSchoolYear().toString())
						.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
								d.getCredit().toString()));
				Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet,
						7, 0)
						.replaceAll(
								"CHIINTRO",
								ci.getChiIntro() == null ? "" : ci
										.getChiIntro().trim()));
				Toolket.setCellValue(sheet, 20, 0, Toolket.getCellValue(sheet,
						20, 0)
						.replaceAll(
								"ENGINTRO",
								ci.getEngIntro() == null ? "" : ci
										.getEngIntro().trim()));

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "CourseIntrosPrint.xls");
				FileOutputStream fos = new FileOutputStream(output);
				workbook.write(fos);
				fos.close();

				JasperReportUtils.printXlsToFrontEnd(response, output);
				output.delete();
				tempDir.delete();
				return null;
			} else {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "查無課程簡介資料"));
				saveMessages(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}
	}
}
