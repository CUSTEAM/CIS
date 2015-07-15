package tw.edu.chit.struts.action.chairman;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Empl;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class DtimeInfoAction extends BaseLookupDispatchAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("sterm", sterm);

		setContentPage(session, "teacher/DtimeInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String sterm = aForm.getString("sterm");
		List<Clazz> clazzes = sm.findClassBy(
				new Clazz(processClassInfo(aForm)), null, true);

		List<DynaBean> beans = new LinkedList<DynaBean>();
		List<Dtime> dtimes = null;
		List csnos = null;
		Empl empl = null;
		DynaBean bean = null;
		Dtime dtime = new Dtime();
		dtime.setSterm(sterm);
		for (Clazz clazz : clazzes) {

			if (Toolket.isDelayClass(clazz.getClassName())
					|| "O".equalsIgnoreCase(clazz.getType())) // 廢止班級
				continue;

			dtime.setDepartClass(clazz.getClassNo());
			dtimes = cm.findDtimesBy(dtime);

			for (Dtime d : dtimes) {
				if (!"50000".equalsIgnoreCase(d.getCscode())
						&& !"T0001".equalsIgnoreCase(d.getCscode())
						&& !"T0002".equalsIgnoreCase(d.getCscode())) {

					bean = new LazyDynaBean();
					bean.set("classNo", d.getDepartClass());
					bean.set("className", Toolket.getClassFullName(d
							.getDepartClass()));
					bean.set("cscode", d.getCscode());
					csnos = cm.getCsnameBy(d.getCscode());
					if (!csnos.isEmpty())
						bean.set("csname", ((Csno) csnos.get(0)).getChiName());
					else
						bean.set("csname", "");

					empl = mm
							.findEmplByIdno(d.getTechid().toUpperCase().trim());
					bean
							.set("teacherName", empl == null ? "" : empl
									.getCname());
					bean.set("opt", Toolket.getCourseOpt(d.getOpt()));
					bean.set("credit", d.getCredit());
					bean.set("hour", d.getThour());
					bean.set("stdSelect", cm
							.findSeldCountByDtimeOid(d.getOid()));
					beans.add(bean);
				}
			}
		}

		session.setAttribute("dtimeInfos", beans);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Search", "search");
		return map;
	}

	private String processClassInfo(DynaActionForm form) {
		String campusInCharge = (String) form.get("campusInCharge");
		String schoolInCharge = (String) form.get("schoolInCharge");
		String deptInCharge = (String) form.get("deptInCharge");
		String departClass = (String) form.get("classInCharge");
		schoolInCharge = schoolInCharge.equalsIgnoreCase("All") ? ""
				: schoolInCharge;
		deptInCharge = deptInCharge.equalsIgnoreCase("All") ? "" : deptInCharge;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 ? departClass : campusInCharge
				+ schoolInCharge + deptInCharge;
	}

}
