package tw.edu.chit.struts.action.course;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Csno;
import tw.edu.chit.model.LiteracyType;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class LiteracyTypeAction extends BaseLookupDispatchAction {

	/**
	 * 進入通識科目分類管理
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
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);

		String[] typeCodes = new String[0], typeNames = new String[0];
		List<LiteracyType> lts = cm.findLiteracyTypesBy(new LiteracyType());
		for (LiteracyType lt : lts) {
			typeCodes = (String[]) ArrayUtils.add(typeCodes, lt.getCode());
			typeNames = (String[]) ArrayUtils.add(typeNames, lt.getName()
					.trim());
		}
		aForm.set("typeCodes", typeCodes);
		aForm.set("typeNames", typeNames);

		String hql = "SELECT COUNT(*), c.cscode, c.chiName, c.literacyType "
				+ "FROM Dtime d, Csno c WHERE d.cscode = c.cscode "
				+ "AND d.opt = ? GROUP BY c.cscode";
		List<Object[]> os = am.find(hql, new Object[] { "3" });
		List<DynaBean> beans = new LinkedList<DynaBean>();
		DynaBean bean = null;
		for (Object[] o : os) {
			bean = new LazyDynaBean();
			bean.set("cscode", String.valueOf(o[1]).trim());
			bean.set("chiName", String.valueOf(o[2]).trim());
			bean.set("literacyType", String.valueOf(o[3]).trim());
			beans.add(bean);
		}
		session.setAttribute("cscodes", beans);

		setContentPage(session, "course/LiteracyType.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		UserCredential credential = getUserCredential(session);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		try {
			List<DynaBean> beans = (List<DynaBean>) session
					.getAttribute("cscodes");
			String[] types = aForm.getStrings("types");
			Csno csno = null;
			int i = 0;

			for (DynaBean bean : beans) {
				csno = cm.findCourseInfoByCscode((String) bean.get("cscode"));
				if (csno != null) {
					csno.setLiteracyType(types[i]);
					csno.setLastModified(new Date());
					csno.setModifier(credential.getMember().getIdno()
							.toUpperCase());
					cm.updateCourseNameBy(csno);
				}
				i++;
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "請重新登入,或電洽電算中心,謝謝"));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.update.choose", "update");
		return map;
	}

}
