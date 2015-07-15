package tw.edu.chit.struts.action.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 畢業證書
 * 
 * @author JOHN
 * 
 */
public class DiplomaReissue extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);
		ServletContext context = request.getSession().getServletContext();
		String schoolName = (String) context.getAttribute("SchoolName_ZH");
		// String year=manager.getNowBy("School_year");//現在學年
		// String term=manager.getSchoolTerm().toString();//現在學期

		response.setHeader("Content-Disposition",
				"attachment;filename=Diploma.doc");
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String team;

		List list = (List) session.getAttribute("students");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar n = Calendar.getInstance();
		n.setTime(new Date());

		boolean holiday;
		String dept;

		out.println("<html xmlns='http://www.w3.org/TR/REC-html40'>");
		out.println("  <head>");
		out
				.println("    <meta http-equiv=Content-Type content='text/html; charset=UTF-8'>");
		out.println("  </head>");
		out.println("  <body bgcolor='#FFFFFF' lang=ZH-TW>");

		for (int i = 0; i < list.size(); i++) {

			Map map = manager
					.ezGetMap("SELECT c5.name, s.occur_term, s.birthday, c.SchoolNo, c.DeptNo, s.divi, s.ExtraStatus, s.ExtraDept, s.student_name, "
							+ "s.depart_class, s.student_no, s.idno "
							+ "FROM stmd s, Class c, code5 c5 "
							+ "WHERE c5.category='Dept' AND c5.idno=c.DeptNo AND c.ClassNo=s.depart_class AND student_no='"
							+ ((Map) list.get(i)).get("student_no") + "'");
			if (map == null) {
				map = manager
						.ezGetMap("SELECT c5.name, s.occur_term, s.birthday, c.SchoolNo, c.DeptNo, s.divi, s.ExtraStatus, s.ExtraDept, s.student_name, "
								+ "s.depart_class, s.student_no, s.idno "
								+ "FROM Gstmd s, Class c, code5 c5 "
								+ "WHERE c5.category='Dept' AND c5.idno=c.DeptNo AND c.ClassNo=s.depart_class AND student_no='"
								+ ((Map) list.get(i)).get("student_no") + "'");
			}
			String schoolNo = manager
					.ezGetString("SELECT name FROM code5 WHERE category='School' AND idno='"
							+ map.get("SchoolNo") + "'");// 所屬學制
			Map myTitle = getTitle(schoolNo);// 存入所屬學制

			// out.println (" <br>");
			out.println("    <br>");
			out.println("    <br>");
			out.println("    <br>");
			out.println("    <br>");
			out.println("    <br>");
			out.println("    <br>");
			out.println("    <br>");

			holiday = false;

			// 進專
			if (schoolNo.indexOf("專校") != -1) {

				holiday = true;
				out
						.println("    <table width=100%><tr><td align='center' colspan='2' nowrap>");
				out
						.println("            <span style='font-size:28.0pt;font-family:標楷體;'>");
				out.println("              <b>" + schoolName + "附設專科進修學校</b>");
				out.println("            </span>");
				out.println("    </td></tr>");
				out
						.println("    <tr><td align='right'><span style='font-size:28.0pt;font-family:標楷體;' nowrap><b>副學士學位證明書</b></span></td><td>");
				out
						.println("        <span style='font-size:11.0pt;font-family:標楷體;'>");
				out.println("              （" + (n.get(Calendar.YEAR) - 1912)
						+ "）華科大進專字第" + map.get("student_no") + "號");
				out
						.println("              <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身分證字號"
								+ map.get("idno"));
				out.println("        </span>");
				out.println("</td></tr></table>");
			}

			// 進院
			if (schoolNo.indexOf("學院") != -1) {
				holiday = true;
				out
						.println("    <table width=100%><tr><td align='center' colspan='2' nowrap>");
				out
						.println("            <span style='font-size:28.0pt;font-family:標楷體;'>");
				out.println("              <b>" + schoolName + "附設進修學院</b>");
				out.println("            </span>");
				out.println("    </td></tr>");
				out
						.println("    <tr><td align='right'><span style='font-size:30.0pt;font-family:標楷體;' nowrap><b>學士學位證明書</b></span></td><td>");
				out
						.println("        <span style='font-size:11.0pt;font-family:標楷體;'>");
				out.println("              （" + (n.get(Calendar.YEAR) - 1912)
						+ "）中華進院字第" + map.get("student_no") + "號");
				out
						.println("              <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身分證字號"
								+ map.get("idno"));
				out.println("        </span>");
				out.println("</td></tr></table>");
			}

			// 一般
			if (!holiday) {

				out
						.println("    <div align='center' style='layout-grid:18.0pt'>");
				out
						.println("      <p align=center style='text-align:center; '>");
				out.println("        <span style='text-fit:347.8pt;'>");
				out.println("          <b>");
				out
						.println("            <span style='font-size:28.0pt;font-family:標楷體;'>");

				out.println("              " + schoolName
						+ myTitle.get("title") + "學位證明書");

				out.println("              </span>");
				out.println("            </span></b>");
				out.println("        </span>");
				out.println("      </p>");
				out.println("    </div>");

				out.println("      <div align='right'>");
				out.println("        <span style='text-fit:30.0pt;'>");
				out.println("          <b>");
				out
						.println("            <span style='font-size:11.0pt;font-family:標楷體;'>");

				if (schoolNo.indexOf("技") != -1) {
					out.println("              （"
							+ (n.get(Calendar.YEAR) - 1912) + "）華科大字第"
							+ map.get("student_no") + "號");
				}

				if (schoolNo.indexOf("專") != -1) {
					out.println("              （"
							+ (n.get(Calendar.YEAR) - 1912) + "）華科大專字第"
							+ map.get("student_no") + "號");
				}

				if (schoolNo.indexOf("碩") != -1) {
					out.println("              （"
							+ (n.get(Calendar.YEAR) - 1912) + "）華科大碩字第"
							+ map.get("student_no") + "號");
				}

				out.println("              <br>身分證字號" + map.get("idno"));
				out.println("          </span></b>");
				out.println("      </div>");
			}

			out.println("      <div align='left'>");
			out.println("      <p>");
			out.println("        <span style='text-fit: 44.0pt;'>");
			out.println("          <b>");
			out
					.println("            <span style='font-size:22.0pt; font-family:標楷體;'>");
			out.println("              學生 " + map.get("student_name"));
			out.println("          </span></b>");
			out.println("        <b>");
			out
					.println("          <span style='font-size:22.0pt;font-family:標楷體;letter-spacing:2.7pt'>");
			out.println("          </span></b>");
			out.println("        <span style='text-fit:88.0pt;'>");
			out.println("          <b>");
			out
					.println("            <span style='font-size: 22.0pt;font-family:標楷體;'>");
			Date d;
			try {
				d = sf.parse(map.get("birthday").toString());
				c.setTime(d);
			} catch (Exception e) {
				// d = sf.parse(map.get("birthday").toString());
				c.setTime(new Date());
			}

			// 專校不同
			dept = map.get("name").toString();

			if (schoolNo.indexOf("專") != -1) {
				out.println("              生於中華民國"
						+ manager.numtochinese(
								c.get(Calendar.YEAR) - 1911 + "", false)
						+ "年"
						+ manager.numtochinese(c.get(Calendar.MONTH) + 1 + "",
								false)
						+ "月"
						+ manager.numtochinese(c.get(Calendar.DAY_OF_MONTH)
								+ "", false) + "日 在本校" + myTitle.get("year"));
				out.println("            </span>");
				out.println("          </span></b>");
				out.println("      </p>");
				dept = dept.replace("系", "科");
			} else {
				out.println("              中華民國"
						+ manager.numtochinese(
								c.get(Calendar.YEAR) - 1911 + "", false)
						+ "年"
						+ manager.numtochinese(c.get(Calendar.MONTH) + 1 + "",
								false)
						+ "月"
						+ manager.numtochinese(c.get(Calendar.DAY_OF_MONTH)
								+ "", false) + "日生 在本校");
				// TODO 機電光特列
				if (schoolNo.indexOf("碩") == -1) {
					out.println(myTitle.get("year"));
				} else {
					dept = dept.replace("系", "研究所");
					if (dept.indexOf("機械") > 0) {
						dept = "機電光研究所";
					}
					if (dept.indexOf("土") > -1) {
						dept = "土木防災工程研究所";
					}
				}
				out.println("            </span>");
				out.println("          </span></b>");
				out.println("      </p>");
			}

			// 組
			team = "&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;組";
			if (map.get("divi") != null && !map.get("divi").equals("")) {
				team = getDivi(map.get("divi").toString());
			}

			out.println("      <p>");
			out.println("        <b>");

			out
					.println("          <span style='font-size:22.0pt;font-family:標楷體;letter-spacing: 2.7pt'>"
							+ dept);

			// out.println (" <span lang=EN-US>");
			out.println(team);
			// out.println (" </span>");
			out.println("          </span></b>");
			out.println("      </p>");
			out.println("      <p>");
			out.println("        <span style='text-fit:436.0pt;'>");
			out.println("          <b>");
			out
					.println("            <span style='font-size:22.0pt;font-family:標楷體;letter-spacing:.75pt;'>");
			out.println("              修業期滿成績及格准予畢業依學位授予法");
			out.println("            </span></b>");
			out.println("        </span>");
			out.println("        <b>");
			out
					.println("          <span style='font-size:22.0pt;font-family:標楷體;letter-spacing:2.7pt'>");

			// TODO 1327 為特例屬於管理學
			if (map.get("depart_class").toString().indexOf("1327") > -1) {
				out.println("            之規定授予 管理學副學士 學位據該生申明前領"
						+ myTitle.get("title") + "學位證書遺失經查屬實  特予證明");
			} else {
				// 一般企管為 商學
				out.println("            之規定授予 "
						+ getDegreeType(map.get("DeptNo").toString().charAt(0))
						+ myTitle.get("title") + " 學位據該生申明前領"
						+ myTitle.get("title") + "學位證書遺失經查屬實  特予證明");
			}

			out.println("          </span></b>");
			out.println("      </p>");
			out.println("      <p>");
			out.println("        <b>");
			out
					.println("          <span style='font-size:22.0pt;font-family:標楷體;letter-spacing: 3.0pt'>");
			out.println("            此證");
			out.println("          </span></b>");
			out.println("      </p>");

			out.println("    <br>");
			out.println("    <br>");

			out.println("      <div align='right'>");
			// 輔系

			try {
				if (map.get("ExtraStatus").equals("1")) {

					out
							.println("                          <b><span style='font-size:10.0pt;font-family:標楷體;'>");
					out
							.println("                              持證人選修"
									+ manager
											.ezGetString("SELECT name FROM code5 WHERE idno='"
													+ map.get("ExtraDept")
													+ "' AND category='Dept'")
									+ "為輔系");
					out.println("                          </span></b>");
					out.println("    <br>");
					out.println("    <br>");
				}

				// 雙修
				if (map.get("ExtraStatus").equals("2")) {
					out.println("                          <b>");
					out
							.println("                            <span     style='font-size:10.0pt;font-family:標楷體;'>");
					out
							.println("                              持證人修習"
									+ manager
											.ezGetString("SELECT name FROM code5 WHERE idno='"
													+ map.get("ExtraDept")
													+ "' AND category='Dept'")
									+ "為雙主修");
					out.println("                            </span></b>");
					out.println("                        </p>");
					out.println("                        <p>");
					out.println("                          <b>");
					out
							.println("                            <span     style='font-size:10.0pt;font-family:標楷體;'>");

					out.println("                              授予"
							+ getDegreeType(map.get("ExtraDept").toString()
									.charAt(0)) + myTitle.get("title")
							+ "學位據該生申明前領" + myTitle.get("title")
							+ "學位證書遺失經查屬實  特予證明");

					out.println("                              </span>");
					out.println("                            </span></b>");
					out.println("    <br>");
					// out.println (" <br>");
					// out.println (" <br>");
					// out.println (" <br>");

				}

			} catch (Exception e) {

				out
						.println("      <span style='position:absolute;z-index:-2;width:242px; height:86px'>");
				out.println("        <table cellpadding=0 cellspacing=0>");
				out.println("          <tr>");
				out
						.println("            <td width=242 height=80 align=left valign=top>");
				out
						.println("              <span   style='position:absolute;z-index:-1'>");
				out
						.println("                <table cellpadding=0 cellspacing=0 width='100%'>");
				out.println("                  <tr>");
				out.println("                    <td>");
				out
						.println("                      <div v:shape='_x0000_s1037' style='padding:3.6pt 7.2pt 3.6pt 7.2pt' class=shape>");
				out.println("                        <p>");
				out.println("                          <b>");
				out
						.println("                            <span style='font-size:10.0pt;font-family:標楷體;'>");
				// out.println (" 持證人選修–––––系為輔系");
				out.println("                            </span></b>");
				out.println("                        </p>");
				out.println("                        <p>");
				out.println("                          <b>");
				out
						.println("                            <span style='font-size:10.0pt;font-family:標楷體;'>");
				// out.println (" 持證人修習–––––系為雙主修");
				out.println("                            </span></b>");
				out.println("                        </p>");
				out.println("                        <p>");
				out.println("                          <b>");
				out
						.println("                            <span style='font-size:10.0pt;font-family:標楷體;'>");
				// out.println (" 授");
				out.println("                              <span  lang=EN-US>");
				// out.println (" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 予–––––學位");
				out.println("                              </span>");
				out.println("                            </span></b>");
				out.println("                        </p>");
				out.println("                      </div></td>");
				out.println("                  </tr>");
				out.println("                </table>");
				out.println("              </span>");
				out.println("              &nbsp;</td>");
				out.println("          </tr>");
				out.println("        </table>");
				out.println("      </span>");
				// out.println (" </p>");

			}

			// 什麼也沒有但要佔空間
			// if(map.get("ExtraStatus").equals("")||
			// map.get("ExtraStatus")==null){
			// }
			out.println("      </div>");

			out.println("      <p>");
			out
					.println("        <span style='font-size:22.0pt;font-family:標楷體;letter-spacing: 3.0pt'><b>");

			out.println("          中華民國 "
					+ manager.numtochinese(n.get(Calendar.YEAR) - 1911 + "",
							false) + " 年 ");

			// 有建發生學期才
			if (map.get("occur_term") != null) {

				if (map.get("occur_term").equals("1")) {
					out.println("壹 月　 日");
				} else {
					out.println("陸 月　 日");
				}
			} else {
				out.println("   月　 日");
			}

			out.println("        </b></span>");
			out.println("      </p>");
			out.println("    </div>");

			if (i < list.size() - 1) {
				out.println("<br clear='all' style='page-break-before:always;'/>");
			}

		}

		out.println("  </body>");
		out.println("</html>");
		out.close();
	}

	/**
	 * 取學制
	 * 
	 * @param classNo
	 * @return
	 */
	private Map getTitle(String schoolNo) {
		Map map = new HashMap();
		if (schoolNo.indexOf("四技") != -1) {
			map.put("title", "學士");
			map.put("year", "四年制");
		}
		if (schoolNo.indexOf("二技") != -1) {
			map.put("title", "學士");
			map.put("year", "二年制");
		}
		if (schoolNo.indexOf("二專") != -1) {
			map.put("title", "副學士");
			map.put("year", "附設專科部二年制");
		}
		if (schoolNo.indexOf("碩") != -1) {
			map.put("title", "碩士");
			map.put("year", "碩士班");
		}
		if (schoolNo.indexOf("學院") != -1) {
			map.put("title", "學士");
			map.put("year", "二年制");
		}
		if (schoolNo.indexOf("專校") != -1) {
			map.put("title", "副學士");
			// map.put("year", "附設專科部貳年制");
			map.put("year", "二年制");
		}

		return map;
	}

	/**
	 * 取學位
	 * 
	 * @param dept
	 * @return
	 */
	private String getDegreeType(char dept) {

		switch (dept) {
		case '1':
			return "工學";
		case '2':
			return "工學";
		case '3':
			return "工學";
		case '4':
			return "管理學";
		case '5':
			return "工學";
		case '6':
			return "工學";
		case '7':
			return "商學";
		case '8':
			return "商學";
		case '9':
			return "商學";
		case 'A':
			return "工學";
		case 'B':
			return "工學";
		case 'C':
			return "商學";
		case 'D':
			return "商學";
		case 'E':
			return "工學";
		case 'F':
			return "農學";
		case 'G':
			return "教育學";
		case 'H':
			return "工學";
		case 'I':
			return "管理學";
		case 'J':
			return "管理學";
		case 'K':
			return "商學";
		default:
			return "未定義";
		}
	}

	/**
	 * 取組別
	 * 
	 * @param divi
	 * @return
	 */
	private String getDivi(String divi) {
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		String result = "";
		if (divi != null && divi.trim().length() > 0) {

			result = manager.ezGetString("SELECT name FROM code5 WHERE idno='"
					+ divi + "' AND category='Group'");
		}
		return result;
	}

}