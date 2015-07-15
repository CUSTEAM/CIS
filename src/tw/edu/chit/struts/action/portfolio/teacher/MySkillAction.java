package tw.edu.chit.struts.action.portfolio.teacher;

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
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class MySkillAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		String userid=user.getMember().getIdno();
		List mySkill=manager.ezGetBy("SELECT * FROM Empl_skills WHERE idno='"+userid+"'");
		List list=manager.ezGetBy("SELECT * FROM Empl_skill_categorys es WHERE es.parent_idno IS NULL");
		List skill1;
		
		for(int i=0; i<list.size(); i++){
			skill1=manager.ezGetBy("SELECT * FROM Empl_skill_categorys es WHERE es.parent_idno='"+((Map)list.get(i)).get("idno")+"'");			
			skill1=putShit(mySkill, skill1);
				for(int j=0; j<skill1.size(); j++){				
			}	
			
			((Map)list.get(i)).put("someone", manager.ezGetInt("SELECT COUNT(*) FROM Empl_skill_categorys esc, Empl_skills es WHERE es.skillOid=esc.Oid AND esc.parent_idno='"+((Map)list.get(i)).get("idno")+"' AND es.idno='"+userid+"'"));
			((Map)list.get(i)).put("skill1", skill1);		
		}
		
		request.setAttribute("list", list);		
		setContentPage(request.getSession(false), "portfolio/teacher/MySkill.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aform = (DynaActionForm) form;
		
		String skillOid[]=aform.getStrings("skillOid");
		String level[]=aform.getStrings("level");
		String idno=user.getMember().getIdno();
		manager.executeSql("DELETE FROM Empl_skills WHERE idno='"+idno+"'");
		
		for(int i=0; i<skillOid.length; i++){
			if(!skillOid[i].equals("")){
				manager.executeSql("INSERT INTO Empl_skills(level, idno, skillOid)VALUES('"+level[i]+"', '"+user.getMember().getIdno()+"', "+skillOid[i]+");");
			}			
		}		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}
	
	private List putShit(List mySkill, List skills){
		
		for(int i=0; i<skills.size(); i++){			
			for(int j=0; j<mySkill.size(); j++){
				
				if(((Map)mySkill.get(j)).get("skillOid").equals(((Map)skills.get(i)).get("Oid"))){
					//System.out.println(mySkill.get(j)+", "+((Map)skills.get(i)).get("Oid"));
					((Map)skills.get(i)).put("igot", "1");
					((Map)skills.get(i)).put("level", ((Map)mySkill.get(j)).get("level"));
					//((Map)mySkill.get(i)).put("someone", "1");
				}				
			}
		}
		
		return skills;
	}

}
