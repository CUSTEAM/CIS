package tw.edu.chit.struts.action.teacher;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Parameters;

public class TeacherMainAction extends BaseAction {

	public static final String MODULE_LIST_NAME = "ModuleList";

	/**
	 * @comment 負責取得教師資料維護子Modules並顯示
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
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		UserCredential user = this.getUserCredential(session);
		// Level 2之uri為Score/ScoreMain
		List<Module> modules = dao.findModulesByParentNameMember(
				"Teacher/TeacherMain", user.getMember().getOid());
		// 判斷教師可輸入課程中英文簡介與大綱時間
		Date now = new Date();
		boolean bTeachIntroAndSyllabus = now.after(Parameters.TEACH_INTRO_BEGIN
				.getTime())
				&& now.before(Parameters.TEACH_INTRO_END.getTime());

		if (!bTeachIntroAndSyllabus) {
			List<Module> subModules = new LinkedList<Module>();
			for (Module module : modules) {
				String moduleName = module.getName();
				if (!"Teacher/TeachIntroduction".equals(moduleName)
						&& !"Teacher/TeachSyllabus".equals(moduleName)
						&& !"Teacher/TeachBookSuggest".equals(moduleName)) {
					// modules.remove(module);
					// break;
					subModules.add(module);
				}
			}
			session.setAttribute(MODULE_LIST_NAME, subModules);
		} else
			session.setAttribute(MODULE_LIST_NAME, modules);
		session.setAttribute("DirectoryBanner", " 教 師 資 料 維 護 ");
		setContentPage(session, "Directory1.jsp");

		return mapping.findForward("Main");
	}

}
