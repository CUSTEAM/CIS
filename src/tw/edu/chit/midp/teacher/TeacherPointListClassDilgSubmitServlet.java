package tw.edu.chit.midp.teacher;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
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

public class TeacherPointListClassDilgSubmitServlet extends HttpServlet {

	private static final long serialVersionUID = 7246176158146751830L;

	@Override
	public void init() throws ServletException {
	}

	/**
	 * 
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
	 * 
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
		String[] studentNos = request.getParameterValues("sn");
		String[] abs = request.getParameterValues("ab");
		String node = request.getParameter("nd");
		Date dt = java.sql.Date.valueOf(request.getParameter("dt").replaceAll(
				"/", "-"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		Float dayOfWeek = Float.valueOf(Toolket.processWeek(cal
				.get(Calendar.DAY_OF_WEEK)));

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
		int index = -1;
		String daynite = null, dept = null;
		if (!students.isEmpty()) {
			for (Student student : students) {
				// 處理有被記曠缺學生
				if ((index = ArrayUtils.indexOf(studentNos, student
						.getStudentNo())) != StringUtils.INDEX_NOT_FOUND) {
					// 查詢曠缺資料
					dilg = new Dilg();
					dilg.setDdate(dt);
					dilg.setWeekDay(dayOfWeek);
					dilg.setStudentNo(student.getStudentNo());
					dilgs = sam.findDilgBy(dilg);
					if (!dilgs.isEmpty()) {
						// 1:升旗 , 2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 ,
						// 8:婚假 , 9:產假
						dilg = dilgs.get(0);
						dilg.setAbs(Integer.parseInt(node), Short
								.valueOf(abs[index]));
					} else {
						dilg = new Dilg();
						dilg.setDdate(dt);
						dilg.setWeekDay(dayOfWeek);
						dilg.setDepartClass(student.getDepartClass());
						dilg.setStudentNo(student.getStudentNo());
						dept = StringUtils.substring(student.getDepartClass(),
								1, 3);
						daynite = StringUtils.indexOfAny(dept,
								IConstants.DEPT_DAY) != StringUtils.INDEX_NOT_FOUND ? "1"
								: (StringUtils.indexOfAny(dept,
										IConstants.DEPT_NIGHT) != StringUtils.INDEX_NOT_FOUND ? "2"
										: (StringUtils.indexOfAny(dept,
												IConstants.DEPT_HOLIDAY) != StringUtils.INDEX_NOT_FOUND ? "3"
												: null));
						dilg.setDaynite(daynite);
						dilg.setAbs(Integer.parseInt(node), Short
								.valueOf(abs[index]));
					}
					sam.txSaveDilg(dilg);
				} else {
					// 查詢曠缺資料
					dilg = new Dilg();
					dilg.setDdate(dt);
					dilg.setWeekDay(dayOfWeek);
					dilg.setStudentNo(student.getStudentNo());
					dilgs = sam.findDilgBy(dilg);
					if (!dilgs.isEmpty()) {
						dilg = dilgs.get(0);
						dilg.setAbs(Integer.parseInt(node), null);
						sam.txSaveDilg(dilg);
					}
				}
				
			}

		}

	}

	/**
	 * 
	 */
	@Override
	public void destroy() {
		super.destroy();
	}

}
