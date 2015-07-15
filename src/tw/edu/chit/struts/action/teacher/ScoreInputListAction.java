package tw.edu.chit.struts.action.teacher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.Optime1;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ScoreInputListAction extends BaseAction {
	public static final String MODULE_LIST_NAME = "ModuleList";

	/**
	 * @comment 負責將教師輸入成績系統內子Module清單取出並顯示
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		ScoreManager sm = (ScoreManager) getBean("scoreManager");
		UserCredential user = this.getUserCredential(session);
		List<Module> modules = dao.findModulesByParentNameMember(
				"Teacher/ScoreInput", user.getMember().getOid());
		List<Optime1> optime = sm.findOptime("0");
		if(Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM).equals("1")){
			for(Optime1 opt:optime){
				if(opt.getLevel().equals("3")){
					opt.setBeginDate("");
					opt.setBeginTime("");
					opt.setEndDate("");
					opt.setEndTime("");
				}
			}
		}
		session.setAttribute(MODULE_LIST_NAME, modules);
		session.setAttribute("optime1", optime);
		session.removeAttribute("TchScoreUploadPrint");
		session.removeAttribute("TchScoreBlankPrint");
		session.setAttribute("DirectoryBanner", "教 師 輸 入 成 績 系 統");
		setContentPage(session, "DirectoryScoreInput.jsp");

		return mapping.findForward("Main");
	}
}
