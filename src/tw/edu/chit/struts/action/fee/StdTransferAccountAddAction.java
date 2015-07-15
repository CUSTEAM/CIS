package tw.edu.chit.struts.action.fee;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import tw.edu.chit.model.Dipost;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class StdTransferAccountAddAction extends BaseLookupDispatchAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();

		String year = am.findTermBy(IConstants.PARAMETER_SCHOOL_YEAR);
		String term = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
		String studentNo = request.getParameter("txtUserInput").split(" ")[0]
				.toUpperCase();

		Student student = mm.findStudentByNo(studentNo);
		Graduate graduate = null;
		if (student == null)
			graduate = mm.findGraduateByStudentNo(studentNo);

		if (student == null && graduate == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無學號" + studentNo + "之學生基本資料"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);

		} else {
			Dipost dipost = new Dipost();
			dipost.setSchoolYear(year);
			dipost.setSchoolTerm(term);
			dipost.setStudentNo(studentNo);
			dipost.setOfficeNo(request.getParameter("officeNo").trim()
					.toUpperCase());
			dipost.setAcctNo(request.getParameter("accountNo").trim()
					.toUpperCase());
			dipost.setMoney(Integer.parseInt(request.getParameter("money")
					.replaceAll(",", "").trim()));
			dipost.setKind(request.getParameter("type"));
			dipost.setModifier(getUserCredential(request.getSession(false))
					.getMember().getIdno());
			dipost.setLastModified(new Date());

			try {

				am.saveObject(dipost);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增成功"));
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增失敗,請檢查是否重複輸入,或請電洽電算中心,謝謝"));
				saveErrors(request, messages);
			}
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setContentPage(request.getSession(false), "fee/StdTransferAccount.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.back", "back");
		return map;
	}

}
