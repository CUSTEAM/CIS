package tw.edu.chit.struts.action.course;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DepartmentInfo;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentScoreHistoryEnglishAction extends StudentScoreHistoryAction {

	/**
	 * 查詢學生英文歷年成績表
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = this.servlet.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.servlet
						.getServletContext());
		MemberManager mm = (MemberManager) ctx
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ScoreManager sm = (ScoreManager) ctx
				.getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) ctx
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		List pageList = (List) session.getAttribute("students");
		String studentNo = (String) ((Map) pageList.get(0)).get("student_no");
		ScoreHist scoreHist = new ScoreHist(studentNo);
		List<ScoreHist> scoreHistList = sm.findScoreHistBy(scoreHist);
		if (scoreHistList.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無歷年成績資料"));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			short firstYear = Short.parseShort(scoreHistList.get(0)
					.getSchoolYear().toString());
			short lastYear = Short.parseShort(scoreHistList.get(
					scoreHistList.size() - 1).getSchoolYear().toString());
			Student student = mm.findStudentByNo(studentNo);
			String graduStr = "----------";
			String enroll = "August " + (1911 + firstYear) + " to "
					+ "January " + (1911 + lastYear + 1);
			if (student == null) {
				Graduate graduate = mm.findGraduateByStudentNo(studentNo);
				student = new Student();
				BeanUtils.copyProperties(student, graduate);
				graduStr = "June " + (1911 + lastYear + 1);
				enroll = enroll.replaceAll("January", "June");
			}

			boolean isMasterStudent = Toolket.isMasterStudent(studentNo);
			MasterData md = null;
			boolean hasMasterData = false; // 碩士要加英文論文名稱
			boolean isThessEngNameOver86Chars = false; // 英文論文名稱過長要換行
			if (isMasterStudent) {
				md = sm.findMasterByStudentNo(studentNo);
				hasMasterData = md != null;
				isThessEngNameOver86Chars = !hasMasterData ? false : md
						.getThesesEngname().length() > 86;
			}

			String departClass = student.getDepartClass();
			// 要改成以dept資料表為主
			DepartmentInfo di = cm.findDepartmentInfoByCategory(StringUtils
					.substring(departClass, 3, 4));
			String diEngName = di == null ? "" : StringUtils.substringAfter(di
					.getEngName(), "Department of ");

			// 碩士班70分及格
			float passScore = Toolket.getPassScoreByDepartClass(departClass);
			Float totalPassCredits = 0.0F;
			File templateXLS = null;
			boolean is2Pages = false, is3Pages = false, is4Pages = false;
			boolean is3272 = false; // Title會多一Row
			String dept = StringUtils.substring(departClass, 1, 3);
			if ("32".equals(dept) || "72".equals(dept)) {
				if (scoreHistList.size() + (2 + (lastYear - firstYear + 1) - 2) >= 120) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish4For3272.xls"));
					is2Pages = true;
					is3Pages = true;
					is4Pages = true;
				} else if (scoreHistList.size()
						+ (2 + (lastYear - firstYear + 1) - 2) >= 80) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish3For3272.xls"));
					is2Pages = true;
					is3Pages = true;
				} else if (scoreHistList.size()
						+ (2 + (lastYear - firstYear + 1) - 2) >= 40) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish2For3272.xls"));
					is2Pages = true;
				} else
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish1For3272.xls"));

				is3272 = true;
			} else {

				// (hasMasterData ? 2 : 0)是因為碩士會加2行字
				if (scoreHistList.size()
						+ (2
								+ (hasMasterData ? (isThessEngNameOver86Chars ? 3
										: 2)
										: 0) + (lastYear - firstYear + 1) - 2) >= 120) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish4.xls"));
					is2Pages = is3Pages = is4Pages = true;
				} else if (scoreHistList.size()
						+ (2
								+ (hasMasterData ? (isThessEngNameOver86Chars ? 3
										: 2)
										: 0) + (lastYear - firstYear + 1) - 2) >= 80) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish3.xls"));
					is2Pages = is3Pages = true;
				} else if (scoreHistList.size()
						+ (2
								+ (hasMasterData ? (isThessEngNameOver86Chars ? 3
										: 2)
										: 0) + (lastYear - firstYear + 1) - 2) >= 40) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish2.xls"));
					is2Pages = true;
				} else
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryEnglish1.xls"));
			}

			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFSheet sheet = workbook.getSheetAt(0);
			if (is3272) {
				if ("32".equals(dept)) {
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學附設專科進修學校");
					Toolket
							.setCellValue(sheet, 2, 0,
									"AFFILIATED JUNIOR COLLEGE OF CONTINUING EDUCATION");
					if (is2Pages || is3Pages) {
						Toolket.setCellValue(sheet, 60, 0, "中華科技大學附設專科進修學校");
						Toolket
								.setCellValue(sheet, 62, 0,
										"AFFILIATED JUNIOR COLLEGE OF CONTINUING EDUCATION");
						if (is3Pages) {
							Toolket.setCellValue(sheet, 120, 0,
									"中華科技大學附設專科進修學校");
							Toolket
									.setCellValue(sheet, 122, 0,
											"AFFILIATED JUNIOR COLLEGE OF CONTINUING EDUCATION");
						}
					}
				} else if ("72".equals(dept)) {
					Toolket.setCellValue(sheet, 0, 0, "中華科技大學附設進修學院");
					Toolket.setCellValue(sheet, 2, 0,
							"AFFILIATED INSTITUTE OF CONTINUING EDUCATION");
					if (is2Pages || is3Pages) {
						Toolket.setCellValue(sheet, 60, 0, "中華科技大學附設進修學院");
						Toolket.setCellValue(sheet, 62, 0,
								"AFFILIATED INSTITUTE OF CONTINUING EDUCATION");
						if (is3Pages) {
							Toolket.setCellValue(sheet, 120, 0, "中華科技大學附設進修學院");
							Toolket
									.setCellValue(sheet, 122, 0,
											"AFFILIATED INSTITUTE OF CONTINUING EDUCATION");
						}
					}
				}
			}
			
			Toolket.setCellValue(sheet, 5 + (is3272 ? 1 : 0), 0, Toolket
					.getCellValue(sheet, 5 + (is3272 ? 1 : 0), 0).replaceAll(
							"CHINAME", student.getStudentName().trim()));
			Toolket.setCellValue(sheet, 5 + (is3272 ? 1 : 0), 7, Toolket
					.getCellValue(sheet, 5 + (is3272 ? 1 : 0), 7).replaceAll(
							"SIDNUM", student.getStudentNo()));
			Toolket.setCellValue(sheet, 6 + (is3272 ? 1 : 0), 0, Toolket
					.getCellValue(sheet, 6 + (is3272 ? 1 : 0), 0).replaceAll(
							"BIRTH",
							new SimpleDateFormat("MMMM d, yyyy", Locale.US)
									.format(student.getBirthday())));
			Toolket
					.setCellValue(
							sheet,
							8 + (is3272 ? 1 : 0),
							0,
							Toolket
									.getCellValue(sheet, 8 + (is3272 ? 1 : 0),
											0).replaceAll("MAJOR", diEngName)
									+ (StringUtils
											.isBlank(Toolket
													.getEnglishSchoolFormalName(departClass)) ? ""
											: " ("
													+ Toolket
															.getEnglishSchoolFormalName(departClass)
													+ ")")); // 可以參考dept資料表
			Toolket.setCellValue(sheet, 9 + (is3272 ? 1 : 0), 0, Toolket
					.getCellValue(sheet, 9 + (is3272 ? 1 : 0), 0).replaceAll(
							"ENROLL", enroll));
			Toolket.setCellValue(sheet, 10 + (is3272 ? 1 : 0), 0, Toolket
					.getCellValue(sheet, 10 + (is3272 ? 1 : 0), 0).replaceAll(
							"GRADU", graduStr));
			Toolket.setCellValue(sheet, 55 + (is3272 ? 1 : 0), 0, Toolket
					.getCellValue(sheet, 55 + (is3272 ? 1 : 0), 0).replaceAll(
							"PASS",
							StringUtils.substringBefore(String
									.valueOf(passScore), ".")));
			
			if (is2Pages || is3Pages || is4Pages) {
				Toolket
						.setCellValue(sheet, 64 + (is3272 ? 2 : 0), 0, Toolket
								.getCellValue(sheet, 5 + (is3272 ? 1 : 0), 0)
								.replaceAll("CHINAME",
										student.getStudentName().trim()));
				Toolket.setCellValue(sheet, 64 + (is3272 ? 2 : 0), 7, Toolket
						.getCellValue(sheet, 5 + (is3272 ? 1 : 0), 7)
						.replaceAll("SIDNUM", student.getStudentNo()));
				Toolket.setCellValue(sheet, 65 + (is3272 ? 2 : 0), 0, Toolket
						.getCellValue(sheet, 6 + (is3272 ? 1 : 0), 0)
						.replaceAll(
								"BIRTH",
								new SimpleDateFormat("MMMM d, yyyy", Locale.US)
										.format(student.getBirthday())));
				Toolket.setCellValue(sheet, 67 + (is3272 ? 2 : 0), 0, Toolket
						.getCellValue(sheet, 8 + (is3272 ? 1 : 0), 0)
						.replaceAll("MAJOR", diEngName));
				Toolket.setCellValue(sheet, 68 + (is3272 ? 2 : 0), 0, Toolket
						.getCellValue(sheet, 9 + (is3272 ? 1 : 0), 0)
						.replaceAll("ENROLL", enroll));
				Toolket.setCellValue(sheet, 69 + (is3272 ? 2 : 0), 0, Toolket
						.getCellValue(sheet, 10 + (is3272 ? 1 : 0), 0)
						.replaceAll("GRADU", graduStr));
				Toolket.setCellValue(sheet, 114 + (is3272 ? 2 : 0), 0, Toolket
						.getCellValue(sheet, 55 + (is3272 ? 1 : 0), 0)
						.replaceAll(
								"PASS",
								StringUtils.substringBefore(String
										.valueOf(passScore), ".")));
				if (is3Pages) {
					Toolket.setCellValue(sheet, 123 + (is3272 ? 3 : 0), 0,
							Toolket
									.getCellValue(sheet, 5 + (is3272 ? 1 : 0),
											0).replaceAll("CHINAME",
											student.getStudentName().trim()));
					Toolket.setCellValue(sheet, 123 + (is3272 ? 3 : 0), 7,
							Toolket
									.getCellValue(sheet, 5 + (is3272 ? 1 : 0),
											7).replaceAll("SIDNUM",
											student.getStudentNo()));
					Toolket.setCellValue(sheet, 124 + (is3272 ? 3 : 0), 0,
							Toolket
									.getCellValue(sheet, 6 + (is3272 ? 1 : 0),
											0).replaceAll(
											"BIRTH",
											new SimpleDateFormat(
													"MMMM d, yyyy", Locale.US)
													.format(student
															.getBirthday())));
					Toolket.setCellValue(sheet, 126 + (is3272 ? 3 : 0), 0,
							Toolket
									.getCellValue(sheet, 8 + (is3272 ? 1 : 0),
											0).replaceAll("MAJOR", diEngName));
					Toolket.setCellValue(sheet, 127 + (is3272 ? 3 : 0), 0,
							Toolket
									.getCellValue(sheet, 9 + (is3272 ? 1 : 0),
											0).replaceAll("ENROLL", enroll));
					Toolket.setCellValue(sheet, 128 + (is3272 ? 3 : 0), 0,
							Toolket.getCellValue(sheet, 10 + (is3272 ? 1 : 0),
									0).replaceAll("GRADU", graduStr));
					Toolket.setCellValue(sheet, 173 + (is3272 ? 3 : 0), 0,
							Toolket.getCellValue(sheet, 55 + (is3272 ? 1 : 0),
									0).replaceAll(
									"PASS",
									StringUtils.substringBefore(String
											.valueOf(passScore), ".")));
				}

				if (is4Pages) {
					Toolket.setCellValue(sheet, 182 + (is3272 ? 4 : 0), 0,
							Toolket
									.getCellValue(sheet, 5 + (is3272 ? 1 : 0),
											0).replaceAll("CHINAME",
											student.getStudentName().trim()));
					Toolket.setCellValue(sheet, 182 + (is3272 ? 4 : 0), 7,
							Toolket
									.getCellValue(sheet, 5 + (is3272 ? 1 : 0),
											7).replaceAll("SIDNUM",
											student.getStudentNo()));
					Toolket.setCellValue(sheet, 183 + (is3272 ? 4 : 0), 0,
							Toolket
									.getCellValue(sheet, 6 + (is3272 ? 1 : 0),
											0).replaceAll(
											"BIRTH",
											new SimpleDateFormat(
													"MMMM d, yyyy", Locale.US)
													.format(student
															.getBirthday())));
					Toolket.setCellValue(sheet, 185 + (is3272 ? 4 : 0), 0,
							Toolket
									.getCellValue(sheet, 8 + (is3272 ? 1 : 0),
											0).replaceAll("MAJOR", diEngName));
					Toolket.setCellValue(sheet, 186 + (is3272 ? 4 : 0), 0,
							Toolket
									.getCellValue(sheet, 9 + (is3272 ? 1 : 0),
											0).replaceAll("ENROLL", enroll));
					Toolket.setCellValue(sheet, 187 + (is3272 ? 4 : 0), 0,
							Toolket.getCellValue(sheet, 10 + (is3272 ? 1 : 0),
									0).replaceAll("GRADU", graduStr));
					Toolket.setCellValue(sheet, 232 + (is3272 ? 4 : 0), 0,
							Toolket.getCellValue(sheet, 55 + (is3272 ? 1 : 0),
									0).replaceAll(
									"PASS",
									StringUtils.substringBefore(String
											.valueOf(passScore), ".")));
				}

			}

			int k = 0;
			int rowIndex = 14 + (is3272 ? 1 : 0);
			boolean isExempt = false; // 判斷是否為抵免?
			Csno csno = null;
			String courseName = null, cscode = null;
			for (short year = firstYear; year <= lastYear; year++) {
				if (rowIndex == (54 + (is3272 ? 1 : 0)))
					rowIndex = 73 + (is3272 ? 2 : 0);
				if (rowIndex == (113 + (is3272 ? 2 : 0)))
					rowIndex = (132 + (is3272 ? 3 : 0));
				if (rowIndex == (172 + (is3272 ? 3 : 0)))
					rowIndex = (191 + (is3272 ? 4 : 0));
				Toolket.setCellValue(sheet, rowIndex++, 0, yearIndex(k, year));
				for (int term = 1; term <= 2; term++) {
					scoreHist.setSchoolYear(year);
					scoreHist.setSchoolTerm(String.valueOf(term));
					scoreHistList = sm.findScoreHistBy(scoreHist);
					if (!scoreHistList.isEmpty()) {
						for (ScoreHist hist : scoreHistList) {
							cscode = hist.getCscode();
							isExempt = "6".equals(hist.getEvgrType());
							if (!IConstants.CSCODE_BEHAVIOR.equals(cscode)) { // 排除操行
								csno = cm.findCourseInfoByCscode(cscode);
								try{
									courseName = StringUtils.isNotBlank(csno
											.getEngName()) ? csno.getEngName()
											.trim() : "***";
								}catch(Exception e){
									
								}
								
								if (rowIndex == (54 + (is3272 ? 1 : 0)))
									rowIndex = 73 + (is3272 ? 2 : 0);
								if (rowIndex == (113 + (is3272 ? 2 : 0)))
									rowIndex = (132 + (is3272 ? 3 : 0));
								if (rowIndex == (172 + (is3272 ? 3 : 0)))
									rowIndex = (191 + (is3272 ? 4 : 0));

								Toolket.setCellValue(sheet, rowIndex, 0,
										courseName);
								if (1 == term) {
									Toolket.setCellValue(sheet, rowIndex, 5,
											StringUtils.substringBefore(hist
													.getCredit().toString(),
													"."));
									Toolket
											.setCellValue(
													sheet,
													rowIndex++,
													6,
													hist.getScore() == null ? (isExempt ? "T"
															: "")
															: hist.getScore()
																	.toString());
									if (isExempt
											|| (hist == null
													|| hist.getScore() == null ? 0.0F
													: hist.getScore()) >= passScore)
										totalPassCredits += hist.getCredit();
								} else {
									Toolket.setCellValue(sheet, rowIndex, 7,
											StringUtils.substringBefore(hist
													.getCredit().toString(),
													"."));
									Toolket
											.setCellValue(
													sheet,
													rowIndex++,
													8,
													(hist == null
															|| hist.getScore() == null ? ""
															: hist.getScore()
																	.toString()));
									if (hist != null && hist.getScore() != null
											&& hist.getScore() >= passScore)
										totalPassCredits += hist.getCredit();
								}
							}
						}
					}
				}
				k++;
			}

			if (rowIndex == (113 + (is3272 ? 2 : 0)))
				rowIndex = (132 + (is3272 ? 3 : 0));
			if (rowIndex == (172 + (is3272 ? 3 : 0)))
				rowIndex = (191 + (is3272 ? 4 : 0));
			if (hasMasterData) {
				HSSFFont fontSize8 = workbook.createFont();
				fontSize8.setFontHeightInPoints((short) 8);
				fontSize8.setFontName("標楷體");
				Toolket.setCellValue(sheet, rowIndex++, 0, "Dissertation : ");
				if (isThessEngNameOver86Chars) {
					Toolket.setCellValue(workbook, sheet, rowIndex++, 0,
							StringUtils.substring(md.getThesesEngname()
									.replaceAll("\r", "").replaceAll("\n", ""),
									0, 86)
									+ "-", fontSize8, HSSFCellStyle.ALIGN_LEFT,
							true, null);
					Toolket.setCellValue(workbook, sheet, rowIndex, 0,
							StringUtils.substring(md.getThesesEngname()
									.replaceAll("\r", "").replaceAll("\n", ""),
									86), fontSize8, HSSFCellStyle.ALIGN_LEFT,
							true, null);
				} else if (md.getThesesEngname().length() > 76)
					Toolket.setCellValue(workbook, sheet, rowIndex, 0, md
							.getThesesEngname().replaceAll("\r", "")
							.replaceAll("\n", ""), fontSize8,
							HSSFCellStyle.ALIGN_LEFT, true, null);
				else
					Toolket.setCellValue(sheet, rowIndex, 0, md
							.getThesesEngname().replaceAll("\r", "")
							.replaceAll("\n", ""));

				Toolket.setCellValue(sheet, rowIndex, 7, "6");
				Toolket.setCellValue(sheet, rowIndex++, 8, md.getThesesScore()
						.toString());
				totalPassCredits += 6;
			}
			Toolket.setCellValue(sheet, rowIndex++, 0,
					"*** End of Transcript ***");
			Toolket.setCellValue(sheet, rowIndex, 0,
					"*** TOTAL CREDITS PRESENTED ***");
			Toolket.setCellValue(sheet, rowIndex++, 5, StringUtils
					.substringBefore(totalPassCredits.toString(), "."));

			File tempDir = new File(context
					.getRealPath("/WEB-INF/reports/temp/"
							+ getUserCredential(session).getMember().getIdno()
							+ (new SimpleDateFormat("yyyyMMdd")
									.format(new Date()))));
			if (!tempDir.exists())
				tempDir.mkdirs();

			File output = new File(tempDir, studentNo + ".xls");
			FileOutputStream fos = new FileOutputStream(output);
			workbook.write(fos);
			fos.close();

			JasperReportUtils.printXlsToFrontEnd(response, output);
			output.delete();
			tempDir.delete();

		}
		return null;
	}

	private String yearIndex(int index, short year) {
		year = (short) (1911 + year);
		StringBuffer ret = new StringBuffer(" (");
		ret.append(year).append("-").append(++year).append(")");
		switch (index) {
			case 0:
				return "First Year" + ret.toString();
			case 1:
				return "Second Year" + ret.toString();
			case 2:
				return "Third Year" + ret.toString();
			case 3:
				return "Fourth Year" + ret.toString();
			case 4:
				return "fifth Year" + ret.toString();
			case 5:
				return "Sixth Year" + ret.toString();
			case 6:
				return "Seventh Year" + ret.toString();
			default:
				return "*** Year";
		}
	}

}
