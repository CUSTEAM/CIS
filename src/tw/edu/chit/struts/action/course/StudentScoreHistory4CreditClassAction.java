package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_YEAR;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
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

import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentScoreHistory4CreditClassAction extends BaseAction {

	/**
	 * 查詢學生歷年成績表
	 * 
	 * @comment 專為學分班學生設計(將抵免科目集中)
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
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		// boolean isShowCredit = true; // 是否顯示抵免成績(96以後入學生不show抵免成績)

		String studentNo = request.getParameter("no");
		if (studentNo == null) {
			List pageList = (List) session.getAttribute("students");
			studentNo = (String) ((Map) pageList.get(0)).get("student_no");
		}
		ScoreHist scoreHist = new ScoreHist(studentNo);
		List<ScoreHist> scoreHistList = sm.findScoreHistBy(scoreHist);
		if (scoreHistList.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無歷年成績資料"));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {

			String[] excep = { "11G332" }; // 春季班學年要+1
			boolean isSpringClass = false;
			short firstYear = Short.parseShort(scoreHistList.get(0)
					.getSchoolYear().toString());
			short lastYear = Short.parseShort(scoreHistList.get(
					scoreHistList.size() - 1).getSchoolYear().toString());
			boolean isOver = (lastYear - firstYear + 1) > 4;
			// isOver = true;
			String departCode = "";

			Student student = mm.findStudentByNo(studentNo);
			if (student == null) {
				Graduate graduate = mm.findGraduateByStudentNo(studentNo);
				student = new Student();
				BeanUtils.copyProperties(student, graduate);
			}

			boolean isShowDoubleMajorOrAssist = "1".equals(student
					.getExtraStatus())
					|| "2".equals(student.getExtraStatus()); // 要修畢才顯示雙主修輔修
			boolean isDoubleMajor = "2".equals(student.getExtraStatus()); // 雙主修
			boolean isAssist = "1".equals(student.getExtraStatus()); // 輔修

			String departClass = student.getDepartClass();
			// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
			boolean is96Entrance = student.getEntrance() == null ? false
					: student.getEntrance() >= (short) 9608;
			boolean isMaster = Toolket.isMasterClass(departClass);
			// 碩士班70分及格
			float passScore = Toolket.getPassScoreByDepartClass(departClass);
			DecimalFormat df = new DecimalFormat(",##0.0");
			File templateXLS = null;
			String title = "";
			if (!isMaster) {
				title = Toolket.getSchoolFormalName(departClass);
				if (isOver) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryOver.xls"));
				} else {
					// 93148066剛好第4學年成績過多,需要到第2頁才行
					if ("93148066".equals(studentNo)
							|| "912A005".equals(studentNo))
						templateXLS = new File(
								context
										.getRealPath("/WEB-INF/reports/StudentScoreHistoryOver.xls"));
					else
						templateXLS = new File(
								context
										.getRealPath("/WEB-INF/reports/StudentScoreHistory.xls"));
				}
			} else {
				String masterId = StringUtils.substring(departClass, 1, 3);
				if ("1G".equals(masterId))
					title = "日間部碩士班";
				else if ("FG".equals(masterId))
					title = "日間部產業研發碩士專班春季班";
				else if ("HG".equals(masterId))
					title = "進修部產業研發碩士專班春季班";
				else
					title = "進修部碩士在職專班";
				templateXLS = new File(
						context
								.getRealPath("/WEB-INF/reports/StudentScoreHistoryMaster.xls"));
			}
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);

			HSSFFont colorFont = workbook.createFont();
			colorFont.setColor(HSSFColor.RED.index);
			colorFont.setFontHeightInPoints((short) 12);
			colorFont.setFontName("標楷體");

			HSSFFont fontSize10 = workbook.createFont();
			fontSize10.setFontHeightInPoints((short) 10);
			fontSize10.setFontName("標楷體");

			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("標楷體");

			HSSFFont fontSize13 = workbook.createFont();
			fontSize13.setFontHeightInPoints((short) 13);
			fontSize13.setFontName("標楷體");

			HSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, studentNo.toUpperCase());
			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + title + "學生歷年成績表");
			Toolket.setCellValue(sheet, 1, 1, "學號：" + studentNo);
			Toolket.setCellValue(sheet, 1, 7, "姓名：" + student.getStudentName());
			Toolket
					.setCellValue(
							sheet,
							1,
							9,
							"系所科別："
									+ (isMaster ? Toolket
											.getMasterDepartName(departClass)
											: Toolket
													.getDepartName(departClass))
									+ (isShowDoubleMajorOrAssist
											&& (isDoubleMajor || isAssist) ? "("
											+ ("1".equals(student
													.getExtraStatus()) ? "輔修"
													: "雙主修")
											+ ":"
											+ Toolket.getDept(student
													.getExtraDept()) + ")"
											: ""));
			Toolket.setCellValue(sheet, 1, 19, "身分證字號：" + student.getIdno());
			// if (isDoubleMajor || isAssist) {
			Toolket.setCellValue(sheet, 42, 0,
					" 符號說明 => *:不及格   #:輔系   &:雙主修   抵:抵免  ");
			departCode = StringUtils.substring(student.getDepartClass(), 3, 4);
			// }

			if (isOver) {
				Toolket
						.setCellValue(sheet, 43, 0, "中華科技大學" + title
								+ "學生歷年成績表");
				Toolket.setCellValue(sheet, 44, 1, "學號：" + studentNo);
				Toolket.setCellValue(sheet, 44, 7, "姓名："
						+ student.getStudentName());
				Toolket.setCellValue(sheet, 44, 9, "系所科別："
						+ (isMaster ? Toolket.getMasterDepartName(departClass)
								: Toolket.getDepartName(departClass)));
				Toolket.setCellValue(sheet, 44, 19, "身分證字號："
						+ student.getIdno());
				// if (isDoubleMajor || isAssist)
				Toolket.setCellValue(sheet, 85, 0,
						" 符號說明 => *:不及格   #:輔系   &:雙主修    抵:抵免  ");
			}

			if (isMaster) {
				MasterData md = sm.findMasterByStudentNo(studentNo);
				if (md != null) {
					Toolket.setCellValue(sheet, 38, 0, "論文題目："
							+ md.getThesesChiname());
					Toolket.setCellValueForEngName(workbook, sheet, 39, 0, md
							.getThesesEngname(), fontSize13,
							HSSFCellStyle.ALIGN_LEFT, true, null);
					Toolket.setCellValue(sheet, 38, 17, md.getRemark());
					Toolket.setCellValue(sheet, 40, 0, "學位考試成績："
							+ md.getThesesScore());
					Toolket.setCellValue(sheet, 40, 4, "學位考試成績(50%)："
							+ md.getThesesScore() / 2);
					Toolket.setCellValue(sheet, 40, 8, "學業平均成績："
							+ md.getEvgr1Score());
					Toolket.setCellValue(sheet, 40, 14, "學業平均成績(50%)："
							+ md.getEvgr1Score() / 2);
					Toolket.setCellValue(sheet, 40, 19, "總成績："
							+ md.getGraduateScore());
				}
			}

			if (Arrays.binarySearch(excep, departClass) >= 0) {
				lastYear++;
				isSpringClass = true;
				Toolket.setCellValue(sheet, 39, 17, "日期：" + (lastYear + 1)
						+ ".1");
			}

			int k = 0, j = 0, l = 0, m = 0;
			int rowIndex = 6;
			int nextPageRowIndex = 43; // 第二頁起始Row Index
			double justScore = 0.0D;
			Float passCreditsSum = 0.0F, totalPassCredits = 0.0F, credit = null, score = null;
			boolean nextPage = false, hasPassRecord = false, rowOver = false;
			boolean isNotExemptAndMend = false, isExempt = false, isMend = false, pass = false;
			boolean isAppend = false, isCross = false, isNotSameDepartCode = false, isOver10Char = false, isSameDoubleMajorDeptCode = false;
			List<Stavg> stavgs = null;
			List<Gmark> gmarks = null;
			Just just = null;
			Gmark gmark = null;
			String[] gmarkOids = null;
			ScoreHist hist = null, target = null;
			String[] formYear = { "一", "二", "三", "四", "五", "六", "七", "八" };
			String evgrType = null, historyDepartCode = null, opt = "", cscode = null, courseName = null;
			Map<String, String> position = null; // 儲存Opt資訊
			Map<String, String> position1 = null; // 儲存cscode資訊

			// 先將所有抵免的Show在第一年
			scoreHist.setEvgrType("6");
			scoreHistList = sm.findScoreHistBy(scoreHist);
			Float creditSum1 = 0.0F, creditSum2 = 0.0F;
			if (!scoreHistList.isEmpty()) {

				rowIndex = 6;
				position = new HashMap<String, String>();
				position1 = new HashMap<String, String>();

				Toolket.setCellValue(sheet, 2 + (nextPage ? nextPageRowIndex
						: 0), k * 6, "抵  免  成  績");
				for (int i = 0; i < scoreHistList.size(); i++) {
					hist = scoreHistList.get(i);
					Toolket.setCellValue(sheet, rowIndex, 0, StringUtils
							.substring(Toolket.getCourseOpt(hist.getOpt()), 0,
									1));
					courseName = cm.findCourseInfoByCscode(hist.getCscode()) == null ? "查無科目名稱"
							: cm.findCourseInfoByCscode(hist.getCscode())
									.getChiName().trim();
					if (courseName.length() > 10)
						Toolket.setCellValue(workbook, sheet, rowIndex, 1,
								courseName, fontSize10,
								HSSFCellStyle.ALIGN_LEFT, true, null);
					else
						Toolket.setCellValue(sheet, rowIndex, 1, courseName);

					if (hist.getSchoolTerm().equals("1")) {
						Toolket.setCellValue(sheet, rowIndex, 2, hist
								.getCredit().toString());
						creditSum1 += hist.getCredit();
						Toolket.setCellValue(sheet, rowIndex, 3, "抵");
					} else {
						Toolket.setCellValue(sheet, rowIndex, 4, hist
								.getCredit().toString());
						creditSum2 += hist.getCredit();
						Toolket.setCellValue(sheet, rowIndex, 5, "抵");
					}
					rowIndex++;
				}
				Toolket.setCellValue(sheet, 33, 2, String.valueOf(creditSum1));
				Toolket.setCellValue(sheet, 33, 4, String.valueOf(creditSum2));
				Toolket.setCellValue(sheet, 34, 2, String.valueOf(creditSum1));
				Toolket.setCellValue(sheet, 34, 4, String.valueOf(creditSum1
						+ creditSum2));
			}

			rowIndex = 6;
			passCreditsSum = creditSum1 + creditSum2;
			scoreHist.setEvgrType(null); // 免得只查到抵免的
			k = 1; // 歷年成績從第2大欄開始
			for (short year = firstYear; year <= lastYear; year++) {
				// 判斷是否有歷年資料?可以9316D078為例測試
				// 不做判斷會出現歷年中間有空白資料
				scoreHist.setSchoolYear(year);
				scoreHist.setSchoolTerm("1");
				hasPassRecord = !sm.findScoreHistBy(scoreHist).isEmpty();
				if (!hasPassRecord) {
					scoreHist.setSchoolTerm("2");
					hasPassRecord = !sm.findScoreHistBy(scoreHist).isEmpty();
					if (!hasPassRecord)
						continue;
				}

				if (k >= 4) {
					k = 0;
					nextPage = true;
					rowIndex = 49;
				}

				if (isSpringClass && year == lastYear) {
					just = sam.findJustByStudentNo(studentNo);
					justScore = 0.0D;
					if (just == null || just.getTotalScore() == 0.0D) {
						target = new ScoreHist(studentNo);
						target.setSchoolYear(new Short(year));
						target.setSchoolTerm("2");
						target.setCscode(IConstants.CSCODE_BEHAVIOR);
						scoreHistList = sm.findScoreHistBy(target);
						if (!scoreHistList.isEmpty()) {
							scoreHist = sm.findScoreHistBy(scoreHist).get(0);
							justScore = scoreHist.getScore();
						}
					} else
						justScore = just.getTotalScore();

					Toolket.setCellValue(sheet, 35 + (isMaster ? -2 : 0)
							+ (nextPage ? nextPageRowIndex : 0), k * 6 + 3,
							justScore == 0.0D ? "" : String.valueOf(Math
									.round(justScore)));
				}

				// formYear內用j是希望可以正確顯示,用k+4會錯誤(以914C018測試)
				Toolket.setCellValue(sheet, 2 + (nextPage ? nextPageRowIndex
						: 0), k * 6, "第    " + formYear[(nextPage ? j : m)]
						+ "    學    年");
				m++; // m完全是為formYear設計,避免與k相衝突
				position = new HashMap<String, String>();
				position1 = new HashMap<String, String>();
				for (int term = 1; term <= 2; term++) {
					scoreHist.setSchoolYear(year);
					scoreHist.setSchoolTerm(String.valueOf(term));
					// scoreHist.setEvgrType("1"); // 正常選課
					scoreHistList = sm.findScoreHistBy(scoreHist);
					if (!scoreHistList.isEmpty()) {
						Toolket.setCellValue(sheet,
								3 + (nextPage ? nextPageRowIndex : 0), k * 6,
								"" + year + " 年  9  月  至  " + (year + 1)
										+ " 年  7  月");
						stavgs = sm.findStavgBy(new Stavg(studentNo, year,
								String.valueOf(term)));
						totalPassCredits = 0.0F;
						// for (ScoreHist hist : scoreHistList) {
						for (int i = 0; i < scoreHistList.size(); i++) {

							hist = scoreHistList.get(i);
							if (hist.getEvgrType().equals("6"))
								continue; // 不印抵免的

							// 判斷i != scoreHistList.size() - 1是避免剛好一學年印完,
							// 結果只判斷rowIndex=32會多印一個全空的學年
							if (rowIndex == 32 && i != scoreHistList.size()) {
								k++;
								if (k % 4 != 0) {
									rowIndex = 6;
									Toolket.setCellValue(sheet,
											2 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "第    "
													+ formYear[k - 1]
													+ "    學    年");
									Toolket.setCellValue(sheet,
											3 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "" + year
													+ " 年  9  月  至  "
													+ (year + 1) + " 年  7  月");
									rowOver = true;
								} else {
									// 93148066第四學期成績過多,導致一頁不夠印
									// 但要如何新增一頁去印????
									l = k; // 避免k=0時,下面的Code會OutOfIndex
									k = 0;
									nextPage = true;
									Toolket.setCellValue(sheet, 43, 0, "中華科技大學"
											+ title + "學生歷年成績表");
									Toolket.setCellValue(sheet, 44, 1, "學號："
											+ studentNo);
									Toolket.setCellValue(sheet, 44, 7, "姓名："
											+ student.getStudentName());
									Toolket
											.setCellValue(
													sheet,
													44,
													9,
													"系所科別："
															+ (isMaster ? Toolket
																	.getMasterDepartName(departClass)
																	: Toolket
																			.getDepartName(departClass)));
									Toolket.setCellValue(sheet, 44, 19,
											"身分證字號：" + student.getIdno());
									Toolket
											.setCellValue(sheet, 85, 0,
													" 符號說明 => *:不及格   #:輔系   &:雙主修    抵:抵免  ");
									Toolket.setCellValue(sheet,
											2 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "第    "
													+ formYear[l - 1]
													+ "    學    年");
									Toolket.setCellValue(sheet,
											3 + (nextPage ? nextPageRowIndex
													: 0), k * 6, "" + year
													+ " 年  9  月  至  "
													+ (year + 1) + " 年  7  月");
									rowIndex = 49;
									rowOver = true;
								}
							}

							evgrType = hist.getEvgrType();
							isNotExemptAndMend = !"6".equals(evgrType)
									&& !"5".equals(evgrType); // 判斷不是為抵免或待補?
							isExempt = "6".equals(evgrType); // 判斷是否為抵免?
							isMend = "5".equals(evgrType); // 判斷是否為待補?
							isAppend = "2".equals(evgrType); // 判斷是否為隨班?
							isCross = "4".equals(evgrType); // 判斷是否為跨校?
							historyDepartCode = StringUtils.isBlank(hist
									.getStdepartClass()) ? "" : StringUtils
									.substring(hist.getStdepartClass(), 3, 4);
							isNotSameDepartCode = !departCode
									.equals(historyDepartCode)
									&& !historyDepartCode.equals("0");
							isSameDoubleMajorDeptCode = student.getExtraDept() != null
									&& student.getExtraDept().equals(
											historyDepartCode);

							opt = "";
							if ("3".equals(evgrType))
								opt = "暑";
							else
								opt = StringUtils.substring(Toolket
										.getCourseOpt(hist.getOpt()), 0, 1)
										+ (isCross ? "跨"
												: (isAppend ? "隨"
														: (isMend ? "待"
																: (!isNotExemptAndMend ? "抵"
																		: ""))));

							cscode = hist.getCscode();
							if ("GA035".equals(cscode) || "".equals("GB033"))
								continue; // 論文與技術報告2科目要排除

							isOver10Char = false;
							if (!IConstants.CSCODE_BEHAVIOR.equals(cscode)) { // 排除操行
								courseName = cm.findCourseInfoByCscode(cscode) == null ? "查無科目名稱"
										: cm.findCourseInfoByCscode(cscode)
												.getChiName().trim();
								isOver10Char = courseName.length() > 10;
								credit = hist.getCredit();
								score = hist.getScore();
								pass = (score == null ? 0.0F : score) >= passScore;
								// ((正常選課&&及格)||是抵免)&&不能是待補
								if (((isNotExemptAndMend && pass) || isExempt)
										&& !isMend) {
									// if (!isNotExemptAndMend || !isMend ||
									// bFlag1)
									// if (!isMend && bFlag1)
									if (isMaster
											&& "G"
													.equalsIgnoreCase(StringUtils
															.substring(
																	hist
																			.getStdepartClass(),
																	2, 3))) {
										// 如果碩士班學生去修其他非碩士班科目,不于加總學分數
										totalPassCredits += credit;
									} else
										totalPassCredits += credit;
								}

								if (1 == term) {
									if (opt.length() == 1 || is96Entrance)
										Toolket.setCellValue(sheet, rowIndex,
												k * 6,
												(is96Entrance ? StringUtils
														.substring(opt, 0, 1)
														: opt));
									else
										Toolket.setCellValue(workbook, sheet,
												rowIndex, k * 6, opt,
												fontSize12,
												HSSFCellStyle.ALIGN_CENTER,
												true, null);
									if (isOver10Char)
										Toolket.setCellValue(workbook, sheet,
												rowIndex, k * 6 + 1,
												courseName, fontSize10,
												HSSFCellStyle.ALIGN_LEFT, true,
												null);
									else
										Toolket.setCellValue(sheet, rowIndex,
												k * 6 + 1, courseName);
									position.put(courseName, String
											.valueOf(rowIndex));
									position1.put(courseName, cscode);

									if (isNotExemptAndMend) {
										// 各科學分
										String creditStr = credit.toString();
										if (!isMend && !isExempt)
											creditStr = pass ? creditStr
													: creditStr + "*";
										if (isDoubleMajor
												&& isNotSameDepartCode
												&& isSameDoubleMajorDeptCode)
											creditStr += "&";
										if (isAssist && isNotSameDepartCode)
											creditStr += "#";
										if (StringUtils.indexOf(creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
											Toolket.setCellValue(sheet,
													rowIndex, k * 6 + 2,
													creditStr);
										else
											Toolket.setCellValue(workbook,
													sheet, rowIndex, k * 6 + 2,
													creditStr, colorFont,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);
										// 各科成績
										Toolket
												.setCellValue(
														sheet,
														rowIndex++,
														k * 6 + 3,
														(isMend ? "待"
																: (isExempt ? "抵"
																		: (score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								".")))));
										// 歷年平均成績
										Toolket
												.setCellValue(
														sheet,
														32
																+ (isMaster ? -2
																		: 0)
																+ (nextPage ? nextPageRowIndex
																		: 0),
														k * 6 + 3,
														(stavgs.isEmpty() ? ""
																: df
																		.format(stavgs
																				.get(
																						0)
																				.getScore() + 0.001D)));
									} else {
										// 各科學分
										String creditStr = credit.toString();
										Toolket.setCellValue(sheet, rowIndex,
												k * 6 + 2, creditStr);
										if (is96Entrance)
											Toolket.setCellValue(sheet,
													rowIndex++, k * 6 + 3,
													(isMend ? "待" : "抵"));
										else {
											// 歷年平均成績
											Toolket
													.setCellValue(
															sheet,
															32
																	+ (isMaster ? -2
																			: 0)
																	+ (nextPage ? nextPageRowIndex
																			: 0),
															k * 6 + 3,
															(stavgs.isEmpty() ? ""
																	: df
																			.format(stavgs
																					.get(
																							0)
																					.getScore() + 0.001D)));
											Toolket
													.setCellValue(
															sheet,
															rowIndex++,
															k * 6 + 3,
															(isMend ? ""
																	: score == null ? ""
																			: StringUtils
																					.substringBefore(
																							score
																									.toString(),
																							".")));
										}
									}
								} else {

									String endString = StringUtils
											.substring(courseName, courseName
													.length() - 1);
									boolean duplicatedCourseName = "二"
											.equals(endString);
									boolean duplicatedCourseName1 = "四"
											.equals(endString);
									boolean duplicatedCourseName2 = "下"
											.equals(endString);

									if (duplicatedCourseName
											|| duplicatedCourseName1
											|| duplicatedCourseName2) {

										String courseIndex = StringUtils
												.substring(courseName, 0,
														courseName.length() - 1);
										String row = "", maybeSameCourseNameOptInfo = "", maybeSameCourseNameCscodeInfo = "";
										if (duplicatedCourseName) {
											row = position.get(courseIndex
													+ "一");
											if (row != null) {
												maybeSameCourseNameCscodeInfo = position1
														.get(courseIndex + "一");
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
											}

										} else if (duplicatedCourseName1) {
											row = position.get(courseIndex
													+ "三");
											if (row != null) {
												maybeSameCourseNameCscodeInfo = position1
														.get(courseIndex + "三");
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
											}
										} else if (duplicatedCourseName2) {
											row = position.get(courseIndex
													+ "上");
											if (row != null) {
												maybeSameCourseNameCscodeInfo = position1
														.get(courseIndex + "上");
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
											}
										}

										// 必須相同選別才可合併(有部分學生因為是跨選,所以判斷失誤,故再加入跨選)
										boolean isAllowMerge = !isCross
												&& ((StringUtils.contains(opt,
														"必") && StringUtils
														.contains(
																maybeSameCourseNameOptInfo,
																"必")) || (StringUtils
														.contains(opt, "選") && StringUtils
														.contains(
																maybeSameCourseNameOptInfo,
																"選")));

										int rowNum = -1;
										boolean bFlag = false;
										if (row != null && isAllowMerge) {
											// 94108052同學期有修2們體育下,需要處理
											rowNum = Integer.parseInt(row);
											String t = Toolket
													.getCellValue(
															sheet,
															rowNum,
															(rowOver ? k - 1
																	: k) * 6 + 1);
											bFlag = t
													.indexOf(duplicatedCourseName ? "二"
															: duplicatedCourseName1 ? "四"
																	: "下") == StringUtils.INDEX_NOT_FOUND;
										}

										if (bFlag) {
											// int rowNum =
											// Integer.parseInt(row);
											Toolket
													.setCellValue(
															sheet,
															rowNum,
															(rowOver ? k - 1
																	: k) * 6 + 1,
															Toolket
																	.getCellValue(
																			sheet,
																			rowNum,
																			(rowOver ? k - 1
																					: k) * 6 + 1)
																	+ "、"
																	+ (duplicatedCourseName ? "二"
																			: duplicatedCourseName1 ? "四"
																					: "下"));
											if (isNotExemptAndMend) {
												// 各科學分
												String creditStr = credit
														.toString();
												if (!isMend && !isExempt)
													creditStr = pass ? creditStr
															: creditStr + "*";
												if (isDoubleMajor
														&& isNotSameDepartCode
														&& isSameDoubleMajorDeptCode)
													creditStr += "&";
												if (isAssist
														&& isNotSameDepartCode)
													creditStr += "#";
												if (StringUtils.indexOf(
														creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
													Toolket
															.setCellValue(
																	sheet,
																	rowNum,
																	(rowOver ? k - 1
																			: k) * 6 + 4,
																	creditStr);
												else
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowNum,
																	(rowOver ? k - 1
																			: k) * 6 + 4,
																	creditStr,
																	colorFont,
																	HSSFCellStyle.ALIGN_CENTER,
																	true, null);

												Toolket
														.setCellValue(
																sheet,
																rowNum,
																(rowOver ? k - 1
																		: k) * 6 + 5,
																(isMend ? "待"
																		: (isExempt ? "抵"
																				: (score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										".")))));
												// 歷年平均成績
												Toolket
														.setCellValue(
																sheet,
																32
																		+ (isMaster ? -2
																				: 0)
																		+ (nextPage ? nextPageRowIndex
																				: 0),
																(rowOver ? k - 1
																		: k) * 6 + 5,
																(stavgs
																		.isEmpty() ? ""
																		: df
																				.format(stavgs
																						.get(
																								0)
																						.getScore() + 0.001D)));
											} else {
												// 各科學分
												String creditStr = credit
														.toString();
												Toolket
														.setCellValue(
																sheet,
																rowNum,
																(rowOver ? k - 1
																		: k) * 6 + 4,
																creditStr);
												if (is96Entrance)
													Toolket
															.setCellValue(
																	sheet,
																	rowNum,
																	(rowOver ? k - 1
																			: k) * 6 + 5,
																	(isMend ? "待"
																			: "抵"));
												else {
													// 歷年平均成績
													Toolket
															.setCellValue(
																	sheet,
																	32
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	(rowOver ? k - 1
																			: k) * 6 + 5,
																	(stavgs
																			.isEmpty() ? ""
																			: df
																					.format(stavgs
																							.get(
																									0)
																							.getScore() + 0.001D)));
													Toolket
															.setCellValue(
																	sheet,
																	rowNum,
																	(rowOver ? k - 1
																			: k) * 6 + 5,
																	(isMend ? ""
																			: score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									".")));
												}
											}
										} else {
											if (opt.length() == 1
													|| is96Entrance)
												Toolket
														.setCellValue(
																sheet,
																rowIndex,
																k * 6,
																(is96Entrance ? StringUtils
																		.substring(
																				opt,
																				0,
																				1)
																		: opt));
											else
												Toolket
														.setCellValue(
																workbook,
																sheet,
																rowIndex,
																k * 6,
																opt,
																fontSize12,
																HSSFCellStyle.ALIGN_CENTER,
																true, null);
											if (isOver10Char)
												Toolket
														.setCellValue(
																workbook,
																sheet,
																rowIndex,
																k * 6 + 1,
																courseName,
																fontSize10,
																HSSFCellStyle.ALIGN_LEFT,
																true, null);
											else
												Toolket.setCellValue(sheet,
														rowIndex, k * 6 + 1,
														courseName);
											position.put(courseName, String
													.valueOf(rowIndex));

											if (isNotExemptAndMend) {
												// 各科學分
												String creditStr = credit
														.toString();
												if (!isMend && !isExempt)
													creditStr = pass ? creditStr
															: creditStr + "*";
												if (isDoubleMajor
														&& isNotSameDepartCode
														&& isSameDoubleMajorDeptCode)
													creditStr += "&";
												if (isAssist
														&& isNotSameDepartCode)
													creditStr += "#";
												if (StringUtils.indexOf(
														creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
													Toolket.setCellValue(sheet,
															rowIndex,
															k * 6 + 4,
															creditStr);
												else
													Toolket
															.setCellValue(
																	workbook,
																	sheet,
																	rowIndex,
																	k * 6 + 4,
																	creditStr,
																	colorFont,
																	HSSFCellStyle.ALIGN_CENTER,
																	true, null);

												// 各科成績
												Toolket
														.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 5,
																(isMend ? "待"
																		: (isExempt ? "抵"
																				: (score == null ? ""
																						: StringUtils
																								.substringBefore(
																										score
																												.toString(),
																										".")))));
												// 歷年平均成績
												Toolket
														.setCellValue(
																sheet,
																32
																		+ (isMaster ? -2
																				: 0)
																		+ (nextPage ? nextPageRowIndex
																				: 0),
																k * 6 + 5,
																(stavgs
																		.isEmpty() ? ""
																		: df
																				.format(stavgs
																						.get(
																								0)
																						.getScore() + 0.001D)));
											} else {
												// 各科學分
												String creditStr = credit
														.toString();
												Toolket.setCellValue(sheet,
														rowIndex, k * 6 + 4,
														creditStr);
												if (is96Entrance)
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex++,
																	k * 6 + 5,
																	(isMend ? "待"
																			: "抵"));
												else {
													// 歷年平均成績
													Toolket
															.setCellValue(
																	sheet,
																	32
																			+ (isMaster ? -2
																					: 0)
																			+ (nextPage ? nextPageRowIndex
																					: 0),
																	k * 6 + 5,
																	(stavgs
																			.isEmpty() ? ""
																			: df
																					.format(stavgs
																							.get(
																									0)
																							.getScore() + 0.001D)));
													Toolket
															.setCellValue(
																	sheet,
																	rowIndex++,
																	k * 6 + 5,
																	(isMend ? ""
																			: score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									".")));
												}
											}
										}
									} else {
										if (opt.length() == 1 || is96Entrance)
											Toolket.setCellValue(sheet,
													rowIndex, k * 6,
													(is96Entrance ? StringUtils
															.substring(opt, 0,
																	1) : opt));
										else
											Toolket.setCellValue(workbook,
													sheet, rowIndex, k * 6,
													opt, fontSize12,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);
										if (isOver10Char)
											Toolket.setCellValue(workbook,
													sheet, rowIndex, k * 6 + 1,
													courseName, fontSize10,
													HSSFCellStyle.ALIGN_LEFT,
													true, null);
										else
											Toolket.setCellValue(sheet,
													rowIndex, k * 6 + 1,
													courseName);
										// 各科學分
										String creditStr = credit.toString();
										if (!isMend && !isExempt)
											creditStr = pass ? creditStr
													: creditStr + "*";
										if (isDoubleMajor
												&& isNotSameDepartCode
												&& isSameDoubleMajorDeptCode)
											creditStr += "&";
										if (isAssist && isNotSameDepartCode)
											creditStr += "#";
										if (StringUtils.indexOf(creditStr, "*") == StringUtils.INDEX_NOT_FOUND)
											Toolket.setCellValue(sheet,
													rowIndex, k * 6 + 4,
													creditStr);
										else
											Toolket.setCellValue(workbook,
													sheet, rowIndex, k * 6 + 4,
													creditStr, colorFont,
													HSSFCellStyle.ALIGN_CENTER,
													true, null);

										if (isNotExemptAndMend) {
											Toolket
													.setCellValue(
															sheet,
															rowIndex++,
															k * 6 + 5,
															(isMend ? "待"
																	: (isExempt ? "抵"
																			: (score == null ? ""
																					: StringUtils
																							.substringBefore(
																									score
																											.toString(),
																									".")))));
											// 歷年平均成績(會重複跑下面Code,要改)
											Toolket
													.setCellValue(
															sheet,
															32
																	+ (isMaster ? -2
																			: 0)
																	+ (nextPage ? nextPageRowIndex
																			: 0),
															k * 6 + 5,
															(stavgs.isEmpty() ? ""
																	: df
																			.format(stavgs
																					.get(
																							0)
																					.getScore() + 0.001D)));
										} else {
											if (is96Entrance)
												Toolket.setCellValue(sheet,
														rowIndex++, k * 6 + 5,
														(isMend ? "待" : "抵"));
											else {
												// 歷年平均成績
												Toolket
														.setCellValue(
																sheet,
																32
																		+ (isMaster ? -2
																				: 0)
																		+ (nextPage ? nextPageRowIndex
																				: 0),
																k * 6 + 5,
																(stavgs
																		.isEmpty() ? ""
																		: df
																				.format(stavgs
																						.get(
																								0)
																						.getScore() + 0.001D)));
												Toolket
														.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 5,
																(isMend ? ""
																		: score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								".")));
											}
										}
									}

								}
							} else {
								// 操行
								if (1 == term) {
									if (isNotExemptAndMend) {
										Toolket.setCellValue(sheet, 35
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 3, String
												.valueOf(Math.round(hist
														.getScore())));
									} else {
										if (!is96Entrance)
											Toolket
													.setCellValue(
															sheet,
															35
																	+ (isMaster ? -2
																			: 0)
																	+ (nextPage ? nextPageRowIndex
																			: 0),
															k * 6 + 3,
															hist.getScore() == null ? ""
																	: String
																			.valueOf(Math
																					.round((hist
																							.getScore()))));
									}
								} else {
									if (isNotExemptAndMend) {
										Toolket.setCellValue(sheet, 35
												+ (isMaster ? -2 : 0)
												+ (nextPage ? nextPageRowIndex
														: 0), k * 6 + 5, String
												.valueOf(Math.round(hist
														.getScore())));
									} else {
										if (!is96Entrance)
											Toolket
													.setCellValue(
															sheet,
															35
																	+ (isMaster ? -2
																			: 0)
																	+ (nextPage ? nextPageRowIndex
																			: 0),
															k * 6 + 5,
															hist.getScore() == null ? ""
																	: String
																			.valueOf(Math
																					.round(hist
																							.getScore())));
									}
								}
							}

						}

						// 處理學分數與累計學分數
						if (1 == term) {
							Toolket.setCellValue(sheet, 33
									+ (isMaster ? -2 : 0)
									+ (nextPage ? nextPageRowIndex : 0),
									k * 6 + 2, totalPassCredits.toString());
							Toolket.setCellValue(sheet, 34
									+ (isMaster ? -2 : 0)
									+ (nextPage ? nextPageRowIndex : 0),
									k * 6 + 2,
									(passCreditsSum += totalPassCredits)
											.toString());
						} else {
							Toolket.setCellValue(sheet, 33
									+ (isMaster ? -2 : 0)
									+ (nextPage ? nextPageRowIndex : 0),
									k * 6 + 4, totalPassCredits.toString());
							Toolket.setCellValue(sheet, 34
									+ (isMaster ? -2 : 0)
									+ (nextPage ? nextPageRowIndex : 0),
									k * 6 + 4,
									(passCreditsSum += totalPassCredits)
											.toString());
						}
					}
					// k--;
				}

				gmark = new Gmark();
				gmark.setSchoolYear(year);
				gmark.setStudentNo(studentNo);
				gmarks = cm.findGMarkBy(gmark);
				if (!gmarks.isEmpty()) {
					StringBuffer buffer = new StringBuffer();
					for (Gmark gm : gmarks) {
						gmarkOids = (String[]) ArrayUtils.add(gmarkOids, gm
								.getOid().toString());
						buffer
								.append(
										gm.getSchoolYear()
												+ "-"
												+ gm.getSchoolTerm()
												+ " "
												+ (StringUtils.isBlank(gm
														.getRemark()) ? "" : gm
														.getRemark())).append(
										Toolket.getStatus(gm.getOccurStatus(),
												false)).append("\n    ");
					}
					int remarkCols = 0;
					switch (k) {
						case 0:
							remarkCols = 0;
							break;
						case 1:
							remarkCols = 6;
							break;
						case 2:
							remarkCols = 12;
							break;
						case 3:
							remarkCols = 18;
							break;
					}
					Toolket.setCellValue(sheet, (nextPage ? 79 : 36),
							remarkCols, "附註：" + buffer.toString());
				}

				if (rowOver) {
					if (l != 0) {
						// 將操行成績處理一下
						Toolket.setCellValue(sheet, 32, (l - 1) * 6 + 5, "");
						Toolket.setCellValue(sheet, 78, k * 6 + 5, Toolket
								.getCellValue(sheet, 35, (l - 1) * 6 + 5));
						Toolket.setCellValue(sheet, 35, (l - 1) * 6 + 5, "");
					} else {
						Toolket.setCellValue(sheet, 32, k * 6 + 5, Toolket
								.getCellValue(sheet, 32, (k - 1) * 6 + 5));
						Toolket.setCellValue(sheet, 32, (k - 1) * 6 + 5, "");
						// 避免將已經正確的資料被空白覆蓋,如96167019
						if (!"".equals(Toolket.getCellValue(sheet, 35,
								(k - 1) * 6 + 5))) {
							Toolket.setCellValue(sheet, 35, k * 6 + 5, Toolket
									.getCellValue(sheet, 35, (k - 1) * 6 + 5));
							Toolket
									.setCellValue(sheet, 35, (k - 1) * 6 + 5,
											"");
						}
					}
					rowOver = false;
				}

				k++;
				j++;
				rowIndex = j >= 4 ? 49 : 6;
				// 如果沒加&& !nextPage,學號914C018會印錯位置
				if (j == 4 && !nextPage) {
					// rowIndex = 49;
					k = 0;
					nextPage = true;
				}
			}

			// 處理無歷年成績,但有歷年備註資料(如93146044, 95147063)
			AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
			Short year = Short.valueOf(am.findTermBy(PARAMETER_SCHOOL_YEAR));
			// String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
			gmark = new Gmark();
			gmark.setSchoolYear(year);
			gmark.setStudentNo(studentNo);
			gmarks = cm.findGMarkBy(gmark);
			// 用term!=1有點牽強(例9414H010)
			// if (!"1".equals(term) && !gmarks.isEmpty()) {
			if (!gmarks.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (Gmark gm : gmarks) {
					// 如果有重複就不用加了
					if (gmarkOids != null
							&& !ArrayUtils.contains(gmarkOids, gm.getOid()
									.toString())) {
						buffer
								.append(
										gm.getSchoolYear()
												+ "-"
												+ gm.getSchoolTerm()
												+ " "
												+ (StringUtils.isBlank(gm
														.getRemark()) ? "" : gm
														.getRemark())).append(
										Toolket.getStatus(gm.getOccurStatus(),
												false)).append("\n      ");
					} else {
						if (gmarkOids == null) {
							gmarkOids = (String[]) ArrayUtils.add(gmarkOids, gm
									.getOid().toString());
							buffer.append("附註：").append(
									gm.getSchoolYear()
											+ "-"
											+ gm.getSchoolTerm()
											+ " "
											+ (StringUtils.isBlank(gm
													.getRemark()) ? "" : gm
													.getRemark())).append(
									Toolket.getStatus(gm.getOccurStatus(),
											false)).append("\n      ");
						} else if (!ArrayUtils.contains(gmarkOids, gm.getOid()
								.toString())) {
							buffer.append("附註：").append(
									gm.getSchoolYear()
											+ "-"
											+ gm.getSchoolTerm()
											+ " "
											+ (StringUtils.isBlank(gm
													.getRemark()) ? "" : gm
													.getRemark())).append(
									Toolket.getStatus(gm.getOccurStatus(),
											false)).append("\n      ");
						}
					}
				}
				int remarkCols = 0;
				boolean bFlag = false;
				if (nextPage && k == 0) {
					k = 3;
					bFlag = true;
				}

				switch (k) {
					case 0:
						remarkCols = 0;
						break;
					case 1:
						remarkCols = 0;
						break;
					case 2:
						remarkCols = 6;
						break;
					case 3:
						remarkCols = 12;
						break;
				}

				String tmp = "";
				if (Toolket.getCellValue(sheet,
						(nextPage ? (!bFlag ? 79 : 36) : 36), remarkCols)
						.length() > 3) {
					tmp = Toolket.getCellValue(sheet, (nextPage ? (!bFlag ? 79
							: 36) : 36), remarkCols);
				}

				String tempString = ""; // 為了9424C053,因為只有最後一年有備註,導致"附註："會出現n次,需重新編排
				boolean hasDuplicated = false;
				if (StringUtils.indexOf(buffer.toString(), "附註：") != StringUtils.INDEX_NOT_FOUND) {
					tempString = buffer.toString().trim();
					tempString = "附註：" + tempString.replaceAll("附註：", "");
					hasDuplicated = true;
				} else {
					tempString = "附註：" + buffer.toString();
				}

				Toolket.setCellValue(sheet,
						(nextPage ? (!bFlag ? 79 : 36) : 36), remarkCols, tmp
								+ (hasDuplicated ? "" : "  ") + tempString);
			}

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
			return null;
		}
	}
}
