package tw.edu.chit.struts.action.course.ajax;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.SummerManager;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 暑修點名表
 * 
 * @author JOHN 沒有經過 doEncode()的 "中文字串/元" 均是用來塞空間的 否則會沒有框線...
 */
public class RollCallBook extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		// CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		SummerManager summerManager = (SummerManager) ctx
				.getBean("summerManager");
		HttpSession session = request.getSession(false);

		Document document = new Document(PageSize.A4, 9, 9, 8, 8);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		List SdtimeList = (List) session.getAttribute("SdtimeList");

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				"attachment;filename=callstudents.pdf");
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.open();
			for (int i = 0; i < SdtimeList.size(); i++) {

				// 取得開課部制ex 16=日四、14=日二、15=夜四、12=夜二....13=進專
				String depTmp = ((Map) SdtimeList.get(i)).get("depart_class")
						.toString().substring(0, 2);
				// 取第1天
				List dateTmp = summerManager
						.ezGetList("SELECT wdate FROM Sweek WHERE daynite='"
								+ depTmp + "' AND seqno='"
								+ ((Map) SdtimeList.get(i)).get("seqno") + "'");
				Calendar sDate = Calendar.getInstance();
				// *********** 第一天 ***********
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date gyDay;

				try {
					gyDay = format.parse(((Map) dateTmp.get(0)).get("wdate")
							.toString());
					sDate.setTime(gyDay);
				} catch (Exception e) {
					e.printStackTrace();
					sDate.setTime(new Date());
				}

				Calendar cal = new GregorianCalendar();

				int weekTmp = sDate.get(Calendar.WEEK_OF_YEAR);

				// 查每天上課的節次為何
				List classes = summerManager
						.ezGetList("SELECT day1, day2, day3, day4, day5, day6, day7 FROM Sdtime WHERE Oid='"
								+ ((Map) SdtimeList.get(i)).get("Oid") + "'");
				// 每週固定天數
				String classes1[] = new String[7];

				String classOfday[] = new String[11];

				String everyday = null;// 節數計數器
				// 總時數
				Integer thourTmp = Integer.parseInt(((Map) SdtimeList.get(i))
						.get("thour").toString());

				for (int c = 0; c < classes1.length; c++) {
					for (int d = 0; d < classes.size(); d++) {
						if (((Map) classes.get(d)).get("day" + (c + 1)) != null
								&& !((Map) classes.get(d)).get("day" + (c + 1))
										.equals("")) {
							classes1[c] = ((Map) classes.get(0)).get(
									"day" + (c + 1)).toString();
							// 裝入每天的節數
							if (!((Map) classes.get(d)).get("day" + (c + 1))
									.toString().trim().equals("")) {
								everyday = ((Map) classes.get(d)).get(
										"day" + (c + 1)).toString().trim();
							}
						}
					}
				}
				
				if(everyday!=null)
				for (int e = 0; e < everyday.length(); e++) {
					String strTmp = everyday.substring(e, e + 1);
					// TODO 暑修每天綁定[11]節 (遵照已往 第10節為'0'，而從未定義的第11節暫定為'a'
					if (strTmp.equals("0")) {
						strTmp = "10";
					}
					if (strTmp.equals("a")) {
						strTmp = "11";
					}
					classOfday[Integer.parseInt(strTmp) - 1] = ((Map) SdtimeList
							.get(i)).get("chi_name").toString();
				}

				int n = 0;
				if(everyday!=null)
				for (int j = 0; j < (thourTmp / everyday.length()); j++) {

					int week = sDate.get(Calendar.WEEK_OF_YEAR) + 1;
					if (!checkDay(classes1, sDate.getTime().getDay())) {
						j--;
						sDate = plusDay(sDate);
					} else {
						j++;
						// 表頭部份 = 100%
						Table table = new Table(2);
						table.setWidth(100);
						table.setBorderWidth(0);
						table.setPadding(0);
						// table.setSpacing(0);

						Cell mcell = new Cell(doEncode(" ※  缺席ο 遲到φ 早退♁"));
						mcell.setBorderColor(new Color(255, 255, 255));
						table.addCell(mcell);
						table.addCell(mcell);

						mcell = new Cell(doEncode(" ※  缺席改遲到或其它更正者請任課老師簽名"));
						mcell.setBorderColor(new Color(255, 255, 255));
						table.addCell(mcell);
						table.addCell(mcell);

						List csname = summerManager
								.ezGetList("SELECT c.chi_name FROM Sdtime d, Csno c WHERE d.cscode=c.cscode AND "
										+ "d.cscode='"
										+ ((Map) SdtimeList.get(i))
												.get("cscode")
										+ "' AND d.depart_class='"
										+ ((Map) SdtimeList.get(i))
												.get("depart_class") + "'");

						List className = summerManager
								.ezGetList("SELECT name FROM Sabbr WHERE no='"
										+ ((Map) SdtimeList.get(i))
												.get("depart_class") + "'");

						n++;
						mcell = new Cell(doEncode(" "
								+ ((Map) className.get(0)).get("name") + "("
								+ ((Map) SdtimeList.get(i)).get("depart_class")
								+ ") " + ((Map) csname.get(0)).get("chi_name")
								+ " ("
								+ ((Map) SdtimeList.get(i)).get("cscode")
								+ ")\t第" + (week - weekTmp) + "週 頁次" + n));

						mcell.setBorderColor(new Color(255, 255, 255));
						table.addCell(mcell);
						table.addCell(mcell);

						mcell = new Cell("幹");
						mcell.setBorderColor(new Color(255, 255, 255));
						table.addCell(mcell);

						document.add(table);

						table = new Table(2);
						table.setWidth(100);
						table.setBorderWidth(0);
						table.setPadding(0);
						// table.setSpacing(0);

						// 第一筆-----------------------------------------------------------------------------------------------------

						// 建立表格
						Table supertable = new Table(16);
						supertable.setWidth(95);
						supertable.setBorderWidth(1);
						supertable.setBorderColor(new Color(0, 0, 255));
						supertable.setPadding(0);
						// supertable.setSpacing(0);

						// 第1層================================
						/*
						 * 可以玩條碼喔~ PdfContentByte cb =
						 * writer.getDirectContent(); BarcodeEAN codeEAN = new
						 * BarcodeEAN(); codeEAN.setCodeType(BarcodeEAN.EAN13);
						 * codeEAN.setCode("9780201615883"); Image imageEAN =
						 * codeEAN.createImageWithBarcode(cb, null, null); Cell
						 * supercell=new Cell(imageEAN);
						 */
						Cell supercell = new Cell("嗨");
						supercell.setLeading(14f);
						// supercell.setColspan(2);
						supertable.setBorderColor(new Color(255, 255, 255));

						supertable.addCell(supercell);

						supercell = new Cell(doEncode("月份/日期", 9));
						supercell.setLeading(10f);
						supercell.setColspan(4);
						supercell
								.setHorizontalAlignment(supercell.ALIGN_CENTER);
						supercell.setBorderColorLeft(new Color(255, 255, 255));
						supertable.addCell(supercell);

						supercell = new Cell(doEncode(sDate.getTime()
								.getMonth()
								+ 1
								+ " 月 "
								+ sDate.getTime().getDate()
								+ " 日 星期" + weekDay(sDate.getTime().getDay()),
								9));
						supercell.setColspan(11);
						supercell
								.setHorizontalAlignment(supercell.ALIGN_CENTER);
						supercell.setLeading(10f);
						// supercell.ALIGN_MIDDLE;
						supertable.addCell(supercell);

						// 第2層=================================
						supercell = new Cell("節次");
						supercell.setLeading(12f);
						supercell.setColspan(5);
						supercell
								.setHorizontalAlignment(supercell.ALIGN_CENTER);
						Cell cell = new Cell(supertable);
						cell.setBorderColor(new Color(0, 0, 0));

						supertable.addCell(supercell);

						// 節次迴圈
						for (int r = 0; r < 11; r++) {
							supercell = new Cell(doEncode((r + 1) + "", 6));
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);
						}

						// 第3層
						supercell = new Cell(doEncode("\n\n科目名稱\n\n\n"));
						supercell
								.setHorizontalAlignment(supercell.ALIGN_CENTER);
						supercell.setColspan(5);
						cell = new Cell(supertable);
						cell.setBorderColor(new Color(0, 0, 0));

						supertable.addCell(supercell);

						// 課程所在節次迴圈
						for (int r = 0; r < classOfday.length; r++) {

							if (classOfday[r] == null) {
								supercell = new Cell("嗨什麼屁也沒有");
							} else {
								supercell = new Cell(doEncode(classOfday[r]));

								// supercell.setHorizontalAlignment(Cell.ALIGN_TOP);
								// supercell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));
							}

							supertable.addCell(supercell);
						}

						// 第4層
						/*
						 * TODO pdf創建表格內的斜線出現一個問題 Graphic grx = new Graphic();
						 * grx.moveTo((float)36, (float)705);
						 * grx.lineTo((float)167, (float)670); supercell=new
						 * Cell(grx);
						 */
						supercell = new Cell(doEncode("教授簽名\n"
								+ ((Map) SdtimeList.get(i)).get("cname")
								+ "老師\n"));
						supercell
								.setHorizontalAlignment(supercell.ALIGN_CENTER);
						supercell.setColspan(5);
						cell = new Cell(supertable);
						cell.setBorderColor(new Color(0, 0, 0));

						supertable.addCell(supercell);

						// 節次迴圈
						for (int r = 0; r < 11; r++) {
							supercell = new Cell("嗨");
							// supercell.setColspan(9);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);
						}

						// 第五層
						List list = summerManager
								.ezGetList("SELECT s.student_no, st.student_name FROM Sdtime d, Sseld s, stmd st "
										+ "WHERE d.depart_class=s.csdepart_class AND d.cscode=s.cscode AND "
										+ "s.student_no=st.student_no AND s.cscode ='"
										+ ((Map) SdtimeList.get(i))
												.get("cscode")
										+ "' "
										+ "AND s.csdepart_class='"
										+ ((Map) SdtimeList.get(i))
												.get("depart_class")
										+ "' ORDER BY s.student_no");

						for (int z = 0; z < list.size(); z++) {
							supercell = new Cell(doEncode(((Map) list.get(z))
									.get("student_no").toString()
									+ ((Map) list.get(z)).get("student_name")
											.toString()));

							supercell.setLeading(10f);
							supercell.setColspan(5);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);

							for (int y = 0; y < 11; y++) {
								supercell = new Cell("塞");

								supercell.setLeading(12f);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);
							}
						}

						for (int z = 0; z < 46 - list.size(); z++) {
							supercell = new Cell("塞");
							supercell.setLeading(12f);
							supercell.setColspan(5);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);

							for (int y = 0; y < 11; y++) {
								supercell = new Cell("塞");

								// supercell.bottom(1f);
								supercell.setBottom(1f);

								supercell.setLeading(12f);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);
							}
						}

						table.addCell(cell);

						Calendar plusDay = plusDay(sDate);
						sDate = plusDay;

						// 第二筆-------------------------------------------------------------------------------------------------------
						if (!checkDay(classes1, sDate.getTime().getDay())) {
							j--;
							sDate = plusDay(sDate);
						} else {

							// 第1層
							supertable = new Table(16);
							supertable.setWidth(95);
							supertable.setBorderWidth(1);
							supertable.setBorderColor(new Color(0, 0, 0));
							supertable.setPadding(0);

							supercell = new Cell("假的");
							supercell.setLeading(14f);
							supertable.setBorderColor(new Color(255, 255, 255));

							supertable.addCell(supercell);

							supercell = new Cell(doEncode("月份/日期", 9));
							supercell.setLeading(10f);
							supercell.setColspan(4);
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);

							supertable.addCell(supercell);

							supercell = new Cell(doEncode(sDate.getTime()
									.getMonth()
									+ 1
									+ " 月 "
									+ sDate.getTime().getDate()
									+ " 日 星期"
									+ weekDay(sDate.getTime().getDay()), 9));
							supercell.setColspan(11);
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);
							supercell.setLeading(10f);
							supertable.addCell(supercell);

							// 第2層=================================
							supercell = new Cell("節次");
							supercell.setLeading(12f);
							supercell.setColspan(5);
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);

							// 節次迴圈
							for (int r = 0; r < 11; r++) {
								supercell = new Cell(doEncode((r + 1) + "", 6));
								supercell
										.setHorizontalAlignment(supercell.ALIGN_CENTER);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);
							}

							// 第3層
							supercell = new Cell(doEncode("\n\n科目名稱\n\n\n"));
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);
							supercell.setColspan(5);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);

							// 課程所在節次迴圈
							for (int r = 0; r < classOfday.length; r++) {

								if (classOfday[r] == null) {
									supercell = new Cell("嗨什麼屁也沒有");
								} else {
									supercell = new Cell(
											doEncode(classOfday[r]));

									// supercell.setHorizontalAlignment(Cell.ALIGN_TOP);
									// supercell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
									cell = new Cell(supertable);
									cell.setBorderColor(new Color(0, 0, 0));
								}

								supertable.addCell(supercell);
							}

							// 第4層
							/*
							 * TODO pdf創建表格內的斜線出現一個問題 Graphic grx = new
							 * Graphic(); grx.moveTo((float)36, (float)705);
							 * grx.lineTo((float)167, (float)670); supercell=new
							 * Cell(grx);
							 */
							supercell = new Cell(doEncode("\n教授簽名\n\n"));
							supercell
									.setHorizontalAlignment(supercell.ALIGN_CENTER);
							supercell.setColspan(5);
							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));

							supertable.addCell(supercell);

							// 節次迴圈
							for (int r = 0; r < 11; r++) {
								supercell = new Cell("嗨");
								// supercell.setColspan(9);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);
							}

							// 第五層
							for (int z = 0; z < list.size(); z++) {
								supercell = new Cell(doEncode(((Map) list
										.get(z)).get("student_no").toString()
										+ ((Map) list.get(z)).get(
												"student_name").toString()));

								supercell.setLeading(10f);
								supercell.setColspan(5);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);

								for (int y = 0; y < 11; y++) {
									supercell = new Cell("嗨");

									// supercell.bottom(1f);
									supercell.setBottom(1f);

									supercell.setLeading(12f);
									cell = new Cell(supertable);
									cell.setBorderColor(new Color(0, 0, 0));

									supertable.addCell(supercell);
								}
							}

							for (int z = 0; z < 46 - list.size(); z++) {
								supercell = new Cell("塞");
								supercell.setLeading(12f);
								supercell.setColspan(5);
								cell = new Cell(supertable);
								cell.setBorderColor(new Color(0, 0, 0));

								supertable.addCell(supercell);

								for (int y = 0; y < 11; y++) {
									supercell = new Cell("塞");
									supercell.setLeading(12f);
									cell = new Cell(supertable);
									cell.setBorderColor(new Color(0, 0, 0));

									supertable.addCell(supercell);
								}
							}

							cell = new Cell(supertable);
							cell.setBorderColor(new Color(0, 0, 0));
							table.addCell(cell);

							plusDay = plusDay(sDate);
							sDate = plusDay;
						}

						document.add(table);
						document.newPage();
					}

				}

			}

			document.close();
			// response.setContentType("application/pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			ba.close();
			out.flush();
			out.close();
		} catch (DocumentException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 轉碼 一般字級 (10號)
	 */
	private Phrase doEncode(String nowBy) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {

			bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			font = new Font(bf, 10, 0);
			p = new Phrase(nowBy, font);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 轉碼 直書 (自定義字級)
	 */
	private Phrase doEncode_V(String nowBy, int fontSize) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {

			bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-V",
					BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize, 0);
			p = new Phrase(nowBy, font);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 轉碼 特殊字級 (自定義)
	 */
	private Phrase doEncode(String nowBy, int fontSize) {

		BaseFont bf;
		Font font = null;
		Phrase p = null;
		try {

			bf = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H",
					BaseFont.NOT_EMBEDDED);
			font = new Font(bf, fontSize, 0);
			p = new Phrase(nowBy, font);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 加1天
	 */
	private Calendar plusDay(Calendar date) {
		date.add(Calendar.DAY_OF_MONTH, 1);
		return date;
	}

	/**
	 * 上課日
	 */
	private boolean checkDay(String[] classes, int today) {
		for (int i = 0; i < classes.length; i++) {
			if (classes[i] != null) {
				if (today == 0) {
					today = 7;
				}

				if (today == (i + 1)) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 換算星期幾
	 */
	private String weekDay(int weekDay) {

		switch (weekDay) {
		case 1:
			return "一";
		case 2:
			return "二";
		case 3:
			return "三";
		case 4:
			return "四";
		case 5:
			return "五";
		case 6:
			return "六";
		case 0:
			return "日";
		default:
			return "";
		}
	}
}
