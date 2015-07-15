package tw.edu.chit.struts.action.research;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class InvestigationSearchAction extends BaseDispatchAction {

	/**
	 * 研發處出路統計
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		String hql = "SELECT s.departClass FROM Investigation i, Student s "
				+ "WHERE i.studentNo = s.studentNo GROUP BY s.departClass ORDER BY s.departClass";
		List<String> departClasses = am.find(hql, null);
		List<DynaBean> ret = new ArrayList<DynaBean>();
		DynaBean bean = null;

		if (!departClasses.isEmpty()) {
			for (String deprtClass : departClasses) {
				bean = new LazyDynaBean();
				bean.set("no", deprtClass);
				bean.set("deptName", Toolket.getDepartName(deprtClass));
				bean.set("name", Toolket.getClassFullName(deprtClass));
				ret.add(bean);
			}
		}

		session.setAttribute("invesList", ret);
		setContentPage(session, "research/InvestigationSearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
