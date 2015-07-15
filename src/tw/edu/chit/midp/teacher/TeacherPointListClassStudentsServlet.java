package tw.edu.chit.midp.teacher;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherPointListClassStudentsServlet extends HttpServlet {

	private static final long serialVersionUID = 8402631104064604134L;

	@Override
	public void init() throws ServletException {
	}

	/**
	 * 依據資訊取得教學班級學生資料
	 * 
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception javax.servlet.ServletException
	 * @exception java.io.IOException
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * 依據資訊取得教學班級學生資料
	 * 
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception javax.servlet.ServletException
	 * @exception java.io.IOException
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dtimeOid = request.getParameter("oid");
		Date dt = java.sql.Date.valueOf(request.getParameter("dt").replaceAll(
				"/", "-"));
		ApplicationContext ac = Global.context;
		CourseManager cm = (CourseManager) ac
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) ac
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) ac
				.getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		List<Student> students = null;
		Dtime dtime = cm.findDtimeBy(Integer.valueOf(dtimeOid));
		// 班會及系時間
		if ("50000".equals(dtime.getCscode())
				|| "T00001".equals(dtime.getCscode()))
			students = mm.findStudentsByClassNo(dtime.getDepartClass());
		else
			students = cm.findSeldStudentByDtimeOid(Integer.valueOf(dtimeOid));

		List<Dilg> dilgs = null;
		Dilg dilg = null;
		String dayOfWeek = null;
		if (!students.isEmpty()) {
			StringBuffer buffer = new StringBuffer("OK:");
			for (Student student : students) {
				buffer.append(student.getOid()).append(",");
				buffer.append(student.getStudentNo()).append(",");
				buffer.append(
						student.getStudentName().replaceAll("　", "").trim())
						.append(",");
				buffer.append(student.getSex()).append(",");

				// 查詢曠缺資料
				dilg = new Dilg();
				dilg.setStudentNo(student.getStudentNo());
				dilg.setDdate(dt);
				dilgs = sam.findDilgBy(dilg);
				if (!dilgs.isEmpty()) {
					// 1:升旗 , 2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 ,
					// 8:婚假 , 9:產假
					dilg = dilgs.get(0);
					dayOfWeek = StringUtils.substringBefore(String.valueOf(dilg
							.getWeekDay()), ".0");
					buffer.append(dayOfWeek).append(","); // 星期n
					buffer
							.append(
									dilg.getAbs0() == null ? (short) 0 : dilg
											.getAbs0()).append(",");
					buffer
							.append(
									dilg.getAbs1() == null ? (short) 0 : dilg
											.getAbs1()).append(",");
					buffer
							.append(
									dilg.getAbs2() == null ? (short) 0 : dilg
											.getAbs2()).append(",");
					buffer
							.append(
									dilg.getAbs3() == null ? (short) 0 : dilg
											.getAbs3()).append(",");
					buffer
							.append(
									dilg.getAbs4() == null ? (short) 0 : dilg
											.getAbs4()).append(",");
					buffer
							.append(
									dilg.getAbs5() == null ? (short) 0 : dilg
											.getAbs5()).append(",");
					buffer
							.append(
									dilg.getAbs6() == null ? (short) 0 : dilg
											.getAbs6()).append(",");
					buffer
							.append(
									dilg.getAbs7() == null ? (short) 0 : dilg
											.getAbs7()).append(",");
					buffer
							.append(
									dilg.getAbs8() == null ? (short) 0 : dilg
											.getAbs8()).append(",");
					buffer
							.append(
									dilg.getAbs9() == null ? (short) 0 : dilg
											.getAbs9()).append(",");
					buffer.append(
							dilg.getAbs10() == null ? (short) 0 : dilg
									.getAbs10()).append(",");
					buffer.append(
							dilg.getAbs11() == null ? (short) 0 : dilg
									.getAbs11()).append(",");
					buffer.append(
							dilg.getAbs12() == null ? (short) 0 : dilg
									.getAbs12()).append(",");
					buffer.append(
							dilg.getAbs13() == null ? (short) 0 : dilg
									.getAbs13()).append(",");
					buffer.append(
							dilg.getAbs14() == null ? (short) 0 : dilg
									.getAbs14()).append(",");
					buffer.append(
							dilg.getAbs15() == null ? (short) 0 : dilg
									.getAbs15()).append(",:");
				} else
					buffer.append(",:");

			}
			System.out.println(buffer.toString());
			Toolket.sendPlainMessageForMIDP(request, response, buffer
					.toString());
		} else
			Toolket.sendPlainMessageForMIDP(request, response, "NORESULT");
	}

	/**
	 * 
	 */
	@Override
	public void destroy() {
		super.destroy();
	}

	Date parseString(String dt) {
		String tmp = StringUtils.substring(dt, 0, 4) + "-"
				+ StringUtils.substring(dt, 4, 6) + "-"
				+ StringUtils.substring(dt, 6);
		return java.sql.Date.valueOf(tmp);
	}

}
