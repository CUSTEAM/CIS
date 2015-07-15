package tw.edu.chit.struts.action.course;

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
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class ForeignExchangeStudentScoreHistoryAction extends BaseAction {

	/**
	 * 查詢交換學生歷年成績表
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
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
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
			String[] excep = { "11G332" }; // 春季班學年要+1
			boolean isSpringClass = false;
			short firstYear = Short.parseShort(scoreHistList.get(0)
					.getSchoolYear().toString());
			short lastYear = Short.parseShort(scoreHistList.get(
					scoreHistList.size() - 1).getSchoolYear().toString());
			boolean isOver = (lastYear - firstYear + 1) > 4;

			Student student = mm.findStudentByNo(studentNo);
			if (student == null) {
				Graduate graduate = mm.findGraduateByStudentNo(studentNo);
				student = new Student();
				BeanUtils.copyProperties(student, graduate);
			}

			String departClass = student.getDepartClass();
			// 9608前入學成績單要Show完整平均成績及操行,但之後入學的只Show'抵'字,且平均成績及操行不Show
			// boolean is96Entrance = student.getEntrance() >= (short) 9608;
			boolean is96Entrance = true;
			boolean isMaster = Toolket.isMasterClass(departClass);
			// 碩士班70分及格
			float passScore = Toolket.getPassScoreByDepartClass(departClass);
			DecimalFormat df = new DecimalFormat(",##0.0");
			File templateXLS = null;
			String title = "";
			if (!isMaster) {
				title = Toolket.getSchoolName(departClass).replaceAll("日",
						"日間部").replaceAll("夜", "進修部");
				if (isOver) {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistoryOver.xls"));
				} else {
					templateXLS = new File(
							context
									.getRealPath("/WEB-INF/reports/StudentScoreHistory.xls"));
				}
			} else {
				String masterId = StringUtils.substring(departClass, 1, 3);
				if ("1G".equals(masterId))
					title = "日間部碩士班";
				else
					title = "進修部碩士在職專班";
				templateXLS = new File(
						context
								.getRealPath("/WEB-INF/reports/StudentScoreHistoryMaster.xls"));
			}
			HSSFWorkbook workbook = Toolket.getHSSFWorkbook(templateXLS);
			HSSFFont fontSize12 = workbook.createFont();
			fontSize12.setFontHeightInPoints((short) 12);
			fontSize12.setFontName("標楷體");
			HSSFSheet sheet = workbook.getSheetAt(0);
			Toolket.setCellValue(sheet, 0, 0, "中華科技大學" + title + "學生歷年成績表");
			Toolket.setCellValue(sheet, 1, 1, "學號：" + studentNo);
			Toolket.setCellValue(sheet, 1, 7, "姓名：" + student.getStudentName());
			Toolket.setCellValue(sheet, 1, 13, "系所科別："
					+ (isMaster ? Toolket.getMasterDepartName(departClass)
							: Toolket.getDepartName(departClass)));
			Toolket.setCellValue(sheet, 1, 19, "身分證字號：" + student.getIdno());
			if (isOver) {
				Toolket
						.setCellValue(sheet, 43, 0, "中華科技大學" + title
								+ "學生歷年成績表");
				Toolket.setCellValue(sheet, 44, 1, "學號：" + studentNo);
				Toolket.setCellValue(sheet, 44, 7, "姓名："
						+ student.getStudentName());
				Toolket.setCellValue(sheet, 44, 13, "系所科別："
						+ (isMaster ? Toolket.getMasterDepartName(departClass)
								: Toolket.getDepartName(departClass)));
				Toolket.setCellValue(sheet, 44, 19, "身分證字號："
						+ student.getIdno());
			}

			if (isMaster) {
				MasterData md = sm.findMasterByStudentNo(studentNo);
				if (md != null) {
					Toolket.setCellValue(sheet, 38, 0, "論文題目："
							+ md.getThesesChiname());
					Toolket.setCellValue(sheet, 39, 0, "          "
							+ md.getThesesEngname());
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

			int k = 0, j = 0;
			int rowIndex = 6;
			int nextPageRowIndex = 43;
			Float passCreditsSum = 0.0F;
			boolean nextPage = false, hasPassRecord = false, rowOver = false;
			List<Stavg> stavgs = null;
			Just just = null;
			String[] formYear = { "一", "二", "三", "四", "五", "六", "七", "八" };
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

				if (isSpringClass && year == lastYear) {
					just = sam.findJustByStudentNo(studentNo);
					double justScore = 0.0D;
					if (just == null || just.getTotalScore() == 0.0D) {
						ScoreHist target = new ScoreHist(studentNo);
						target.setSchoolYear(new Short(year));
						target.setSchoolTerm("2");
						target.setCscode("99999");
						scoreHistList = sm.findScoreHistBy(target);
						if (!scoreHistList.isEmpty()) {
							scoreHist = sm.findScoreHistBy(scoreHist).get(0);
							justScore = scoreHist.getScore();
						}
					} else
						justScore = just.getTotalScore();

					Toolket.setCellValue(sheet, 35 + (isMaster ? -2 : 0),
							k * 6 + 3, justScore == 0.0D ? "" : String
									.valueOf(Math.round(justScore)));
				}

				Toolket.setCellValue(sheet, 2 + (nextPage ? nextPageRowIndex
						: 0), k * 6, "第    " + formYear[(nextPage ? k + 4 : k)]
						+ "    學    年");
				Map<String, String> position = new HashMap<String, String>();
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
						Float totalPassCredits = 0.0F;
						for (ScoreHist hist : scoreHistList) {

							if (rowIndex == 32) {
								rowIndex = 6;
								k++;
								Toolket.setCellValue(sheet,
										2 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "第    " + formYear[k - 1]
												+ "    學    年");
								Toolket.setCellValue(sheet,
										3 + (nextPage ? nextPageRowIndex : 0),
										k * 6, "" + year + " 年  9  月  至  "
												+ (year + 1) + " 年  7  月");
								rowOver = true;
							}

							String evgrType = hist.getEvgrType();
							boolean isNotExemptAndMend = !"6".equals(evgrType)
									&& !"5".equals(evgrType); // 判斷不是為抵免或待補?
							boolean isExempt = "6".equals(evgrType); // 判斷是否為抵免?
							boolean isMend = "5".equals(evgrType); // 判斷是否為待補?
							String opt = "";
							if ("3".equals(evgrType))
								opt = "暑";
							else
								opt = StringUtils.substring(Toolket
										.getCourseOpt(hist.getOpt()), 0, 1)
										+ (isMend ? "待"
												: (!isNotExemptAndMend ? "抵"
														: ""));

							String cscode = hist.getCscode();
							if ("GA035".equals(cscode) || "".equals("GB033"))
								continue; // 論文與技術報告2科目要排除

							if (!"99999".equals(cscode)) { // 排除操行
								String courseName = cm
										.findCourseInfoByCscode(cscode) == null ? "查無科目名稱"
										: cm.findCourseInfoByCscode(cscode)
												.getChiName().trim();
								Float credit = hist.getCredit();
								Float score = hist.getScore();
								boolean pass = (score == null ? 0.0F : score) >= passScore;
								// ((正常選課&&及格)||是抵免)&&不能是待補
								if (((isNotExemptAndMend && pass) || isExempt)
										&& !isMend)
									// if (!isNotExemptAndMend || !isMend ||
									// bFlag1)
									// if (!isMend && bFlag1)
									totalPassCredits += credit;

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
									Toolket.setCellValue(sheet, rowIndex,
											k * 6 + 1, courseName);
									position.put(courseName, String
											.valueOf(rowIndex));

									if (isNotExemptAndMend) {
										// 各科學分
										String creditStr = credit.toString();
										if (!isMend && !isExempt)
											creditStr = pass ? creditStr
													: creditStr + "*";
										Toolket.setCellValue(sheet, rowIndex,
												k * 6 + 2, creditStr);
										// 各科成績
										Toolket
												.setCellValue(
														sheet,
														rowIndex++,
														k * 6 + 3,
														(isMend ? "待"
																: (score == null ? ""
																		: StringUtils
																				.substringBefore(
																						score
																								.toString(),
																						"."))));
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
															score == null ? ""
																	: StringUtils
																			.substringBefore(
																					score
																							.toString(),
																					"."));
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
										String row = "", maybeSameCourseNameOptInfo = "";
										if (duplicatedCourseName) {
											row = position.get(courseIndex
													+ "一");
											if (row != null)
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
										} else if (duplicatedCourseName1) {
											row = position.get(courseIndex
													+ "三");
											if (row != null)
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
										} else if (duplicatedCourseName2) {
											row = position.get(courseIndex
													+ "上");
											if (row != null)
												maybeSameCourseNameOptInfo = Toolket
														.getCellValue(
																sheet,
																Integer
																		.parseInt(row),
																(rowOver ? k - 1
																		: k) * 6);
										}

										// 必須相同選別才可合併
										boolean isAllowMerge = (StringUtils
												.contains(opt, "必") && StringUtils
												.contains(
														maybeSameCourseNameOptInfo,
														"必"))
												|| (StringUtils.contains(opt,
														"選") && StringUtils
														.contains(
																maybeSameCourseNameOptInfo,
																"選"));
										if (row != null && isAllowMerge) {
											int rowNum = Integer.parseInt(row);
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
												Toolket
														.setCellValue(
																sheet,
																rowNum,
																(rowOver ? k - 1
																		: k) * 6 + 4,
																creditStr);
												Toolket
														.setCellValue(
																sheet,
																rowNum,
																(rowOver ? k - 1
																		: k) * 6 + 5,
																(isMend ? "待"
																		: (score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								"."))));
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
													Toolket
															.setCellValue(
																	sheet,
																	32 + (isMaster ? -2
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
																	score == null ? ""
																			: StringUtils
																					.substringBefore(
																							score
																									.toString(),
																							"."));
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
												Toolket.setCellValue(sheet,
														rowIndex, k * 6 + 4,
														creditStr);
												// 各科成績
												Toolket
														.setCellValue(
																sheet,
																rowIndex++,
																k * 6 + 5,
																(isMend ? "待"
																		: (score == null ? ""
																				: StringUtils
																						.substringBefore(
																								score
																										.toString(),
																								"."))));
												// 歷年成績
												Toolket
														.setCellValue(
																sheet,
																32 + (isMaster ? -2
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
													// 歷年成績
													Toolket
															.setCellValue(
																	sheet,
																	32 + (isMaster ? -2
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
																	score == null ? ""
																			: StringUtils
																					.substringBefore(
																							score
																									.toString(),
																							"."));
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
										Toolket.setCellValue(sheet, rowIndex,
												k * 6 + 1, courseName);
										// 各科學分
										String creditStr = credit.toString();
										if (!isMend && !isExempt)
											creditStr = pass ? creditStr
													: creditStr + "*";
										Toolket.setCellValue(sheet, rowIndex,
												k * 6 + 4, creditStr);
										if (isNotExemptAndMend) {
											Toolket
													.setCellValue(
															sheet,
															rowIndex++,
															k * 6 + 5,
															(isMend ? "待"
																	: (score == null ? ""
																			: StringUtils
																					.substringBefore(
																							score
																									.toString(),
																							"."))));
											Toolket
													.setCellValue(
															sheet,
															32 + (isMaster ? -2
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
												Toolket
														.setCellValue(
																sheet,
																32 + (isMaster ? -2
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
																score == null ? ""
																		: StringUtils
																				.substringBefore(
																						score
																								.toString(),
																						"."));
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
															35 + (isMaster ? -2
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
															35 + (isMaster ? -2
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

						// 處理當學期馬上暑修之成績
						// scoreHist.setEvgrType("3");
						// scoreHistList = sm.findScoreHistBy(scoreHist);
						// if (!scoreHistList.isEmpty()) {
						// for (ScoreHist hist : scoreHistList) {
						// String cscode = hist.getCscode();
						// String courseName = cm.getCourseInfoByCscode(
						// cscode).getChiName().trim();
						// Float credit = hist.getCredit();
						// Float score = hist.getScore() == null ? 0.0F
						// : hist.getScore();
						// boolean bFlag1 = score >= passScore;
						// if (bFlag1)
						// totalPassCredits += credit;
						// setCellValue(sheet, rowIndex, k * 6, "暑");
						// setCellValue(sheet, rowIndex, k * 6 + 1,
						// courseName);
						// setCellValue(sheet, rowIndex, k * 6 + 4, credit
						// .toString());
						// setCellValue(sheet, rowIndex++, k * 6 + 5,
						// StringUtils.substringBefore(score
						// .toString(), "."));
						// }
						// }

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

				if (rowOver) {
					Toolket.setCellValue(sheet, 32, (k - 1) * 6 + 5, "");
					Toolket.setCellValue(sheet, 35, k * 6 + 5, Toolket
							.getCellValue(sheet, 35, (k - 1) * 6 + 5));
					Toolket.setCellValue(sheet, 35, (k - 1) * 6 + 5, "");
				}

				k++;
				j++;
				rowIndex = j >= 4 ? 49 : 6;
				if (j == 4) {
					// rowIndex = 49;
					k = 0;
					nextPage = true;
				}
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
