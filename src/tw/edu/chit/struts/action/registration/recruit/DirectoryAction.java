package tw.edu.chit.struts.action.registration.recruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Toolket;

public class DirectoryAction extends BaseAction{

	public static final String MODULE_LIST_NAME = "ModuleList";


	/**
	 * 
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/*
		String name="Regstration/Recruit/Directory";		
		HttpSession session = request.getSession(false);
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = this.getUserCredential(session);
		
		List<Module> modules = dao.findModulesByParentNameMember(name, user.getMember().getOid());
		
		Module module=(Module) manager.hqlGetBy("FROM Module WHERE name='"+name+"'").get(0);
		
		Map map;
		List list=new ArrayList();
		for(int i=0; i<modules.size(); i++){
			map=new HashMap();
			map.put("Icon", ((Module)modules.get(i)).getIcon());
			map.put("Label", ((Module)modules.get(i)).getLabel());
			map.put("Action", ((Module)modules.get(i)).getAction());
			System.out.println(((Module)modules.get(i)).getLabel());
			list.add(map);
		}
		
		request.setAttribute(MODULE_LIST_NAME, list);
		request.setAttribute("title", module.getLabel());
		request.setAttribute("icon", module.getIcon());
		setContentPage(session, "Directory1_1.jsp");
		return mapping.findForward("Main");
		*/
		
		HttpSession session = request.getSession(false);
		if (Toolket.TriggerMenuCollapse(session, "Regstration/Recruit/Directory")) {
			setContentPage(session, "BulletinBoard.jsp");
			request.setAttribute("directory", "Regstration/Recruit/Directory");
		}
		
		AdminManager manager = (AdminManager)getBean("adminManager");
		List messageList = manager.findMessagesByCategory("Regstration/Recruit/Directory");
		request.setAttribute("MessageList", messageList);
		return mapping.findForward("Main");
	}

}