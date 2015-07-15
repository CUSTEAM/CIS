package tw.edu.chit.struts.action.teacher;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TutorAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = getUserCredential(session);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<Clazz> clazz = mm.findClassInChargeByMemberAuthority(credential
				.getMember().getOid(), UserCredential.AuthorityOnTutor);
		boolean bFlag = true;
		if (!clazz.isEmpty()) {
			String departClassInTutor = clazz.get(0).getClassNo();
			bFlag = Toolket.chkIsGraduateClass(departClassInTutor);
		}
		// 排除非畢業班導師功能選項
		// Teacher/Tutor/GraduateInfoInput-導生電子郵件??
		List<Module> modules = dao.findModulesByParentNameMember(
				"Teacher/Tutor", credential.getMember().getOid());
		if (!bFlag) {
			for (Module module : modules) {
				if ("Teacher/Tutor/GraduateInfoInput".equals(module.getName())) {
					modules.remove(module);
					break;
				}
			}
		}
		session.setAttribute("ModuleList", processDuplicated(modules));
		session.setAttribute("DirectoryBanner", "導 師 作 業");
		setContentPage(session, "Directory1.jsp");
		return mapping.findForward("Main");
	}

	private List<Module> processDuplicated(List<Module> modules) {
		List<Module> ret = new LinkedList<Module>();
		Integer[] oids = new Integer[0];
		for (Module m : modules) {
			Integer oid = m.getOid();
			if (Arrays.binarySearch(oids, oid) >= 0) {
				continue;
			} else {
				oids = (Integer[]) ArrayUtils.add(oids, oid);
				Arrays.sort(oids);
				ret.add(m);
			}
		}
		return ret;
	}

}