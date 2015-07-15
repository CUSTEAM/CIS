package tw.edu.chit.struts.action.deptassist;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class InvestigationSearchAction extends BaseDispatchAction {

	/**
	 * 系助出路統計
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Clazz[] clazzes = credential.getClassAryC();
		List<DynaBean> ret = new ArrayList<DynaBean>();
		DynaBean bean = null;
		for (Clazz c : clazzes) {
			if (Toolket.chkIsGraduateClass(c.getClassNo())) {
				if (!mm.findStudentsByClassNo(c.getClassNo()).isEmpty()) {
					bean = new LazyDynaBean();
					bean.set("no", c.getClassNo());
					bean.set("name", Toolket.getClassFullName(c.getClassNo()));
					ret.add(bean);
				}
			}
		}

		session.setAttribute("invesList", ret);
		setContentPage(session, "assistant/InvestigationSearch.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
