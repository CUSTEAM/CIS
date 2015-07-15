package tw.edu.chit.midp.teacher;

import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_SCHOOL_TERM;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TeacherPointListClassServlet extends HttpServlet {

	private static final long serialVersionUID = -3865763159057249489L;

	protected static final String[] DAY_OF_WEEK = { "", "日", "一", "二", "三",
			"四", "五", "六" };

	@Override
	public void init() throws ServletException {
	}

	/**
	 * 依據資訊取得教學班級資料
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
	 * 依據資訊取得教學班級資料
	 * 
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @exception javax.servlet.ServletException
	 * @exception java.io.IOException
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String no = request.getParameter("no");
		ApplicationContext ac = Global.context;
		CourseManager cm = (CourseManager) ac.getBean(COURSE_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) ac.getBean(ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) ac
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String term = am.findTermBy(PARAMETER_SCHOOL_TERM);
		Calendar cal = Calendar.getInstance();
		Calendar copyCal = null;
		int dayOfWeek = Toolket.processWeek(cal.get(Calendar.DAY_OF_WEEK));
		int distance = 0;

		Member member = mm.findMemberByAccount(no);
		List<Object[]> aList = cm.getDtimeByTeacher(member, term);
		List<Object[]> T0001And50000 = process50000AndT0001(cm, term, mm
				.findClassInChargeByMemberAuthority(member.getOid(),
						UserCredential.AuthorityOnTutor)); // 導師班級
		aList.addAll(T0001And50000);
		// TODO 目前只有台北日間部與新竹參與
		response.setContentType("application/octet-stream");
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());
		if (!aList.isEmpty()) {
			// 0: Dtime Oid
			// 1: Class Name
			// 2: Course Name
			// 3: DtimeClass數量
			// 4: Y代表今天有課,N則否
			// 5: This Week or Last Week
			// 6: DtimeClass Week
			// 7: DtimeClass Begin
			// 8: DtimeClass End
			// 9: 其他多時段的DtimeClass...
			StringBuffer buffer = new StringBuffer("OK:");
			List dcs = null;
			DtimeClass dc = null;
			for (Object[] o : aList) {
				buffer.append(o[2]).append(","); // Dtime Oid
				buffer.append(o[0]).append(","); // Class Name
				buffer.append(o[1]).append(","); // Course Name
				dcs = cm.getDtimeClassListForOpenCourse(((Integer) o[2])
						.toString());
				if (!dcs.isEmpty()) {
					buffer.append(String.valueOf(dcs.size())).append(","); // DtimeClass數量
					// 多DtimeClass但只是上課位置不同怎辦?
					for (int i = 0; i < dcs.size(); i++) {
						dc = (DtimeClass) dcs.get(i);
						distance = dayOfWeek - dc.getWeek().intValue();
						// 今天上課
						if (dayOfWeek == dc.getWeek().intValue()) {
							buffer.append("Y,");
							buffer
									.append(df.format(cal.getTime()))
									.append(" (")
									.append(
											DAY_OF_WEEK[cal
													.get(Calendar.DAY_OF_WEEK)])
									.append(")|");
							copyCal = (Calendar) cal.clone();
							copyCal.add(Calendar.DAY_OF_MONTH, -7);
							buffer.append(df.format(copyCal.getTime())).append(
									" (").append(
									DAY_OF_WEEK[copyCal
											.get(Calendar.DAY_OF_WEEK)])
									.append(")|,");
						} else {
							buffer.append("N,");
							if (distance > 0) { // 這星期已上過的課
								copyCal = (Calendar) cal.clone();
								copyCal.add(Calendar.DAY_OF_MONTH, -distance);
								buffer
										.append(df.format(copyCal.getTime()))
										.append(" (")
										.append(
												DAY_OF_WEEK[copyCal
														.get(Calendar.DAY_OF_WEEK)])
										.append(")|,");
							} else if (distance <= -1) { // 這星期未上過的課
								copyCal = (Calendar) cal.clone();
								copyCal.add(Calendar.DAY_OF_MONTH, -7
										- distance);
								buffer
										.append(df.format(copyCal.getTime()))
										.append(" (")
										.append(
												DAY_OF_WEEK[copyCal
														.get(Calendar.DAY_OF_WEEK)])
										.append(")|,");
							}
						}
						buffer.append(dc.getWeek()).append(","); // 星期n
						buffer.append(dc.getBegin()).append(",");
						buffer.append(dc.getEnd()).append(",");
					}

				} else
					buffer.append("0,");

				buffer.append(":"); // 放在if外面可避免查不到時忘了加":"
			}
			// Toolket.sendPlainMessageForMIDP(request, response,
			// buffer.toString());
			System.out.println(buffer.toString());
			dos.writeUTF(buffer.toString());
			dos.flush();
			dos.close();
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

	@SuppressWarnings("unchecked")
	private List<Object[]> process50000AndT0001(CourseManager cm, String term,
			List<Clazz> clazzs) {
		List<Object[]> ret = new LinkedList<Object[]>();
		Object[] array = null;
		List<Map> T0001And50000 = null;
		Student student = null;
		for (Clazz c : clazzs) {
			// 以下的方法實作以學生與學期查詢所屬班會與系時間,只是用到Student的DepartClass,所以...
			student = new Student();
			student.setDepartClass(c.getClassNo());
			T0001And50000 = cm.findT0001And50000ByStudent(student, term);
			for (Map m : T0001And50000) {
				array = new Object[3];
				array[0] = (String) m.get("ClassName");
				array[1] = (String) m.get("chi_name");
				array[2] = (Integer) m.get("oid");
				ret.add(array);
			}
		}
		return ret;
	}

}
