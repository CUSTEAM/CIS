package tw.edu.chit.struts.action.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.service.CourseManager;

public class AjaxGetStudentTimeOff extends HttpServlet {

	private Logger log = Logger.getLogger(AjaxGetStudentTimeOff.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());

		StudAffairManager sm = (StudAffairManager) wac
				.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager) wac.getBean("scoreManager");

		HttpSession session = request.getSession(false);

		CourseManager manager = (CourseManager) wac.getBean("courseManager");
		// UserCredential credential =
		// (UserCredential)session.getAttribute("Credential");

		// String studentNo = credential.getMember().getAccount();

		String studentNo = request.getParameter("studentno");
		String sterm = Toolket.getSysParameter("School_term");
		/*
		 * if(studentNo == null) { studentNo =
		 * credential.getStudent().getStudentNo(); } else
		 * if(studentNo.trim().equals("")) { studentNo =
		 * credential.getStudent().getStudentNo(); }
		 */
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (!studentNo.trim().equals("")) {
			Student student = scm.findStudentByStudentNo(studentNo);
			if (student != null) {
				String studentName = student.getStudentName();
				String departClass = student.getDepartClass();
				String deptClassName = student.getDepartClass2();
				String daynite = sm.chkStudentDepart(studentNo);

				String mode = request.getParameter("mode");
				if (mode.equals(""))
					mode = "all";
				log.info("GetTimeOff has been called! and Mode=" + mode);

				
				// Leo 20120321 將下列79~83行遮蔽，改取85~90行程式執行
				//int elearnSum = 0;
				//List<Seld> elearnList= sm.findDilgByStudentNo(studentNo,"elearn");
				//for (Seld seld : elearnList) {
				//	elearnSum += seld.getElearnDilg();
				//}				
				String elcnt;
				
				 elcnt = manager.ezGetString(
						"Select SUM(s.`elearn_dilg`) " +
						"From Seld s, Dtime d " +
						"Where s.`student_no` like '"+studentNo+"' " +
						"  And s.`Dtime_oid`=d.`Oid` " +
						"  And d.`Sterm`='"+sterm+"' ");
				 
				 int elearnSum;
				 
				 if(elcnt==null){ elearnSum =0; }
				 
				 else{ elearnSum=Integer.parseInt(elcnt); }
					

				 
				List dilgList = sm.getDilgByStudentNo(studentNo, mode);
				Dilg dilg;
				// ===========================Leo
				// Start====================================================================================================
				List desdList = sm.findDesdByStudentNo(studentNo);
				Desd desd;
				// ===========================Leo
				// End======================================================================================================
				String weekday = "";

				// Map result = new HashMap();
				out.println("<dilgPrompt>");
				out.println("<mode>" + mode + "</mode>");
				out.println("<studentNo>" + studentNo + "</studentNo>");
				out.println("<studentName>" + studentName + "</studentName>");
				out.println("<departClass>" + departClass + "</departClass>");
				out.println("<deptClassName>" + deptClassName
						+ "</deptClassName>");
				out.println("<daynite>" + daynite + "</daynite>");
				out.println("<elearnSum>" + elearnSum + "</elearnSum>");

				if (mode.equals("all")) {
					if (dilgList.size() > 0) {
						for (Iterator<Dilg> dilgIter = dilgList.iterator(); dilgIter
								.hasNext();) {
							dilg = dilgIter.next();
							out.println("<dilgInfo>");
							out.println("<ddate>"
									+ Toolket.printNativeDate(dilg.getDdate())
									+ "(" + weekTran(dilg.getWeekDay()) + ")"
									+ "</ddate>");
							out.println("<abs0>" + dilg.getAbsName0()
									+ "</abs0>");
							out.println("<abs1>" + dilg.getAbsName1()
									+ "</abs1>");
							out.println("<abs2>" + dilg.getAbsName2()
									+ "</abs2>");
							out.println("<abs3>" + dilg.getAbsName3()
									+ "</abs3>");
							out.println("<abs4>" + dilg.getAbsName4()
									+ "</abs4>");
							out.println("<abs5>" + dilg.getAbsName5()
									+ "</abs5>");
							out.println("<abs6>" + dilg.getAbsName6()
									+ "</abs6>");
							out.println("<abs7>" + dilg.getAbsName7()
									+ "</abs7>");
							out.println("<abs8>" + dilg.getAbsName8()
									+ "</abs8>");
							out.println("<abs9>" + dilg.getAbsName9()
									+ "</abs9>");
							out.println("<abs10>" + dilg.getAbsName10()
									+ "</abs10>");
							out.println("<abs11>" + dilg.getAbsName11()
									+ "</abs11>");
							out.println("<abs12>" + dilg.getAbsName12()
									+ "</abs12>");
							out.println("<abs13>" + dilg.getAbsName13()
									+ "</abs13>");
							out.println("<abs14>" + dilg.getAbsName14()
									+ "</abs14>");
							out.println("<abs15>" + dilg.getAbsName15()
									+ "</abs15>");
							out.println("</dilgInfo>");

						}
					} else {
						out.println("<dilgInfo></dilgInfo>");
					}

				} else if (mode.equals("subject")) {
					if (dilgList.size() > 0) {
						for (Iterator dilgIter = dilgList.iterator(); dilgIter
								.hasNext();) {
							Map dilgMap = (Map) dilgIter.next();
							out.println("<dilgInfo>");
							out.println("<subjectName>"
									+ dilgMap.get("subjectName")
									+ "</subjectName>");
							out.println("<period>" + dilgMap.get("period")
									+ "</period>");
							out.println("<tfLimit>" + dilgMap.get("tfLimit")
									+ "</tfLimit>");
							out.println("<timeOff>" + dilgMap.get("timeOff")
									+ "</timeOff>");
							out.println("<elearnDilg>"
									+ dilgMap.get("elearnDilg")
									+ "</elearnDilg>");
							out.println("<warnning>" + dilgMap.get("warnning")
									+ "</warnning>");
							out.println("<dtimeClass>"
									+ dilgMap.get("dtimeClass")
									+ "</dtimeClass>");
							out.println("<absType>" + dilgMap.get("absType")
									+ "</absType>");
							out.println("</dilgInfo>");

						}
					} else {
						out.println("<dilgInfo></dilgInfo>");
					}

					// ===========================Leo
					// Start====================================================================================================
				} else if (mode.equals("desd")) {
					if (desdList.size() > 0) {
						// System.out.println(desdList.size());

						for (Iterator<Desd> desdIter = desdList.iterator(); desdIter
								.hasNext();) {
							desd = desdIter.next();
							Map Reason = manager
									.ezGetMap("SELECT name FROM code2 WHERE no='"
											+ desd.getReason() + "'");
							// System.out.println(Reason.get("name"));
							out.println("<dilgInfo>");
							out.println("<ddate>" + desd.getDdate()
									+ "</ddate>");
							out.println("<no>" + desd.getNo() + "</no>");
							out.println("<reason>" + Reason.get("name")
									+ "</reason>");
							out.println("<kind1>" + desd.getKind1()
									+ "</kind1>");
							out.println("<cnt1>" + desd.getCnt1() + "</cnt1>");
							out.println("<kind2>" + desd.getKind2()
									+ "</kind2>");
							out.println("<cnt2>" + desd.getCnt2() + "</cnt2>");
							out.println("</dilgInfo>");
						}
					} else {
						out.println("<dilgInfo></dilgInfo>");
					}
				}
				// ===========================Leo
				// End======================================================================================================

				out.println("</dilgPrompt>");
				out.close();
			} else {
				out.println("<dilgPrompt>");
				out.println("<dilgInfo>");
				out.println("<mode>notfound</mode>");
				out.println("</dilgInfo>");
				out.println("</dilgPrompt>");
				out.close();
			}
		} else {
			out.println("<dilgPrompt>");
			out.println("<dilgInfo>");
			out.println("<mode>notfound</mode>");
			out.println("</dilgInfo>");
			out.println("</dilgPrompt>");
			out.close();
		}
	}

	private String weekTran(float fweek) {
		int i = Math.round(fweek);
		String sweek = "";

		switch (i) {
		case 1:
			sweek = "一";
			break;
		case 2:
			sweek = "二";
			break;
		case 3:
			sweek = "三";
			break;
		case 4:
			sweek = "四";
			break;
		case 5:
			sweek = "五";
			break;
		case 6:
			sweek = "六";
			break;
		case 7:
			sweek = "日";
			break;
		default:
			sweek = "";
		}
		return sweek;
	}
}
