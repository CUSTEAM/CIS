package tw.edu.chit.struts.action.tutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

public class GraduateInfoInput extends BaseLookupDispatchAction {

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<Clazz> clazz = mm.findClassInChargeByMemberAuthority(credential
				.getMember().getOid(), UserCredential.AuthorityOnTutor);
		List<Student> students = mm.findStudentsByClassNo(clazz.get(0)
				.getClassNo());
		for (Student student : students) {
			
		}
		setContentPage(session, "score/ReportPrint.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

}
