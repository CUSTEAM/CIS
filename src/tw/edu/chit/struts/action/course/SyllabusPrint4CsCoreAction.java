package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

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

import tw.edu.chit.model.CourseIntroduction;
import tw.edu.chit.model.CourseSyllabus;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Savedtime;
import tw.edu.chit.model.Syllabus;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class SyllabusPrint4CsCoreAction extends BaseAction {

	/**
	 * 綱要下載
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
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ServletContext context = this.servlet.getServletContext();
		HttpSession session = request.getSession(false);
		Integer dtimeOid = Integer.valueOf(request.getParameter("Oid"));
		Integer year = cm.getSchoolYear();
		Integer term = cm.getSchoolTerm();

		CourseSyllabus cs = cm
				.getCourseSyllabusByDtimeOid(dtimeOid, year, term);
		CourseIntroduction ci = cm.getCourseIntrosByDtimeOid(dtimeOid, year,
				term);
		Savedtime sd = null;
		if (cs == null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無課程綱要資料"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);

		} else if (cs != null) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/CourseSyllabusPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Dtime d = cm.findDtimeBy(dtimeOid);
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
					.replaceAll("HOUR", cs.getOfficeHours()));
			Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(sheet, 4, 0)
					.replaceAll("TITLE", csno.getChiName()));
			Toolket
					.setCellValue(
							sheet,
							5,
							0,
							Toolket
									.getCellValue(sheet, 5, 0)
									.replaceAll(
											"ENG",
											StringUtils.isBlank(csno
													.getEngName()) ? (StringUtils
													.isBlank(ci.getEngName()) ? ""
													: ci.getEngName())
													: csno.getEngName()));
			Toolket.setCellValue(sheet, 6, 0, Toolket.getCellValue(sheet, 6, 0)
					.replaceAll("YEAR", cs.getSchoolYear().toString())
					.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
							d.getCredit().toString()));
			Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet, 7, 0)
					.replaceAll("PRE", cs.getPrerequisites().trim()));
			Toolket.setCellValue(sheet, 9, 0, Toolket.getCellValue(sheet, 9, 0)
					.replaceAll("OBJ", cs.getObjectives().trim()));

			List<Syllabus> syllabus = cs.getSyllabuses();
			if (!syllabus.isEmpty()) {
				int index = 12;
				for (Syllabus s : syllabus) {
					Toolket
							.setCellValue(sheet, index, 0, (s == null
									|| s.getTopic() == null ? "" : s.getTopic()
									.trim()));
					Toolket.setCellValue(sheet, index, 1, (s == null
							|| s.getContent() == null ? "" : s.getContent()
							.trim()));
					Toolket
							.setCellValue(sheet, index, 2, (s == null
									|| s.getHours() == null ? "" : s.getHours()
									.trim()));
					Toolket.setCellValue(sheet, index, 3, (s == null
							|| s.getWeek() == null ? "" : s.getWeek().trim()));
					Toolket.setCellValue(sheet, index++, 4, (s == null
							|| s.getRemarks() == null ? "" : s.getRemarks()
							.trim()));
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "CourseSyllabus.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		} else if (cs != null) {
			File templateXLS = new File(context
					.getRealPath("/WEB-INF/reports/CourseSyllabusPrint.xls"));
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Dtime d = cm.findDtimeBy(dtimeOid);
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
					.replaceAll("HOUR", cs.getOfficeHours()));
			Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(sheet, 4, 0)
					.replaceAll("TITLE", csno.getChiName()));
			Toolket
					.setCellValue(
							sheet,
							5,
							0,
							Toolket
									.getCellValue(sheet, 5, 0)
									.replaceAll(
											"ENG",
											StringUtils.isBlank(csno
													.getEngName()) ? (StringUtils
													.isBlank(ci.getEngName()) ? ""
													: ci.getEngName())
													: csno.getEngName()));
			Toolket.setCellValue(sheet, 6, 0, Toolket.getCellValue(sheet, 6, 0)
					.replaceAll("YEAR", cs.getSchoolYear().toString())
					.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
							d.getCredit().toString()));
			Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet, 7, 0)
					.replaceAll("PRE", cs.getPrerequisites().trim()));
			Toolket.setCellValue(sheet, 9, 0, Toolket.getCellValue(sheet, 9, 0)
					.replaceAll("OBJ", cs.getObjectives().trim()));

			List<Syllabus> syllabus = cs.getSyllabuses();
			if (!syllabus.isEmpty()) {
				int index = 12;
				for (Syllabus s : syllabus) {
					Toolket
							.setCellValue(sheet, index, 0, (s == null
									|| s.getTopic() == null ? "" : s.getTopic()
									.trim()));
					Toolket.setCellValue(sheet, index, 1, (s == null
							|| s.getContent() == null ? "" : s.getContent()
							.trim()));
					Toolket
							.setCellValue(sheet, index, 2, (s == null
									|| s.getHours() == null ? "" : s.getHours()
									.trim()));
					Toolket.setCellValue(sheet, index, 3, (s == null
							|| s.getWeek() == null ? "" : s.getWeek().trim()));
					Toolket.setCellValue(sheet, index++, 4, (s == null
							|| s.getRemarks() == null ? "" : s.getRemarks()
							.trim()));
				}
			}

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, "CourseSyllabus.xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();
			return null;
		} else {
			sd = cm.findSavedtimeBy(dtimeOid);
			CourseSyllabus tmpCs = new CourseSyllabus();
			tmpCs.setSchoolYear(year);
			tmpCs.setSchoolTerm(term);
			tmpCs.setDepartClass(sd.getDepartClass());
			tmpCs.setCscode(sd.getCscode());
			cs = cm.getCourseSyllabusBy(tmpCs);
			if (cs != null) {
				File templateXLS = new File(
						context
								.getRealPath("/WEB-INF/reports/CourseSyllabusPrint.xls"));
				HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
				HSSFSheet sheet = workbook.getSheetAt(0);
				Dtime d = cm.findDtimeBy(dtimeOid);
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
						3, 0).replaceAll("HOUR", cs.getOfficeHours()));
				Toolket.setCellValue(sheet, 4, 0, Toolket.getCellValue(sheet,
						4, 0).replaceAll("TITLE", csno.getChiName()));
				Toolket.setCellValue(sheet, 5, 0, Toolket.getCellValue(sheet,
						5, 0).replaceAll(
						"ENG",
						StringUtils.isBlank(csno.getEngName()) ? "" : csno
								.getEngName()));
				Toolket.setCellValue(sheet, 6, 0, Toolket.getCellValue(sheet,
						6, 0).replaceAll("YEAR", cs.getSchoolYear().toString())
						.replaceAll("TERM", d.getSterm()).replaceAll("CREDIT",
								d.getCredit().toString()));
				Toolket.setCellValue(sheet, 7, 0, Toolket.getCellValue(sheet,
						7, 0).replaceAll("PRE", cs.getPrerequisites().trim()));
				Toolket.setCellValue(sheet, 9, 0, Toolket.getCellValue(sheet,
						9, 0).replaceAll("OBJ", cs.getObjectives().trim()));

				List<Syllabus> syllabus = cs.getSyllabuses();
				if (!syllabus.isEmpty()) {
					int index = 12;
					for (Syllabus s : syllabus) {
						Toolket.setCellValue(sheet, index, 0, (s == null
								|| s.getTopic() == null ? "" : s.getTopic()
								.trim()));
						Toolket.setCellValue(sheet, index, 1, (s == null
								|| s.getContent() == null ? "" : s.getContent()
								.trim()));
						Toolket.setCellValue(sheet, index, 2, (s == null
								|| s.getHours() == null ? "" : s.getHours()
								.trim()));
						Toolket.setCellValue(sheet, index, 3, (s == null
								|| s.getWeek() == null ? "" : s.getWeek()
								.trim()));
						Toolket.setCellValue(sheet, index++, 4, (s == null
								|| s.getRemarks() == null ? "" : s.getRemarks()
								.trim()));
					}
				}

				File tempDir = new File(context
						.getRealPath("/WEB-INF/reports/temp/"
								+ getUserCredential(session).getMember()
										.getIdno()
								+ (new SimpleDateFormat("yyyyMMdd")
										.format(new Date()))));
				if (!tempDir.exists())
					tempDir.mkdirs();

				File output = new File(tempDir, "CourseSyllabus.xls");
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
						"Course.messageN1", "查無課程綱要資料"));
				saveMessages(request, messages);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}
	}
}
