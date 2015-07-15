package tw.edu.chit.struts.action.literacy;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;

public class DeptCourseInfoAction extends BaseAction {

	private static final String DTIME_RESULT = "dtimeResult";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);

		String term = am.findTermBy(IConstants.PARAMETER_NEXT_TERM);
		List<Object[]> rets = null;
		// CodeEmpl之UnitTeach的idno=68
		List<Empl> employees = mm.findTeacherByUnit(IConstants.UNIT_LITERACY);
		List<DynaBean> beans = new LinkedList<DynaBean>();
		DynaBean bean = null;

		Member member = new Member();
		for (Empl e : employees) {
			member.setAccount(e.getIdno().toUpperCase().trim());
			rets = cm.getDtimeByTeacher(member, term);
			if (!rets.isEmpty()) {
				for (Object[] data : rets) {
					bean = new LazyDynaBean();
					bean.set("departClass2", data[0]);
					bean.set("departClass", data[4]);
					bean.set("chiName2", data[1].toString());
					bean.set("cscode", data[3]);
					bean.set("techName", e.getCname().trim());
					bean.set("opt2", data[5]);
					bean.set("credit", data[7]);
					bean.set("thour", data[8]);
					bean.set("stuSelect", data[9] == null ? "" : data[9]);
					beans.add(bean);
				}
			}
		}

		session.setAttribute(DTIME_RESULT, beans);
		setContentPage(session, "iteracy/DeptCourseInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
