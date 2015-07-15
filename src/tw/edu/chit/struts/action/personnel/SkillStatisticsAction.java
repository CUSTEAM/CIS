package tw.edu.chit.struts.action.personnel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SkillStatisticsAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=manager.ezGetBy("SELECT * FROM Empl_skill_categorys es WHERE es.parent_idno IS NULL");
		List skill1;
		//List skill2;
		
		
		//int listcount;
		//int skill1count;
		//int skill2count;
		for(int i=0; i<list.size(); i++){			
			((Map)list.get(i)).put("count", manager.ezGetInt("SELECT COUNT(*) FROM Empl_skills es, Empl_skill_categorys esc WHERE " +
					"es.skillOid=esc.Oid AND esc.parent_idno='"+((Map)list.get(i)).get("idno")+"'"));
		}
		
		for(int i=0; i<list.size(); i++){
			skill1=manager.ezGetBy("SELECT * FROM Empl_skill_categorys es WHERE es.parent_idno='"+((Map)list.get(i)).get("idno")+"'");			
			
			
			for(int j=0; j<skill1.size(); j++){				
				//skill2=new ArrayList();				
				//if(((Map)skill1.get(j)).get("directory").equals("1")){			
					//kill2=manager.ezGetBy("SELECT * FROM Empl_skill_categorys es WHERE es.parent_idno='"+((Map)skill1.get(j)).get("idno")+"'");					
					
				//}
				
				//skill1=countShit(skill1);
				//skill2=countShit(skill2);
				//((Map)skill1.get(j)).put("skill2", skill2);
				
			}
			skill1=countShit(skill1);
			((Map)list.get(i)).put("skill1", skill1);		
		}
		
		
		request.setAttribute("list", list);
		
		setContentPage(request.getSession(false), "personnel/SkillStatistics.jsp");
		return mapping.findForward("Main");
	}
	
	private List countShit(List list){
		CourseManager manager = (CourseManager) getBean("courseManager");
		for(int i=0; i<list.size(); i++){
			
			((Map)list.get(i)).put("guys", manager.ezGetBy("SELECT e.cname FROM empl e, Empl_skills es, Empl_skill_categorys esc WHERE " +
					"esc.Oid=es.skillOid AND e.idno=es.idno AND esc.idno='"+((Map)list.get(i)).get("idno")+"'"));
			
			((Map)list.get(i)).put("count", manager.ezGetInt("SELECT COUNT(*) FROM Empl_skills es, Empl_skill_categorys esc WHERE " +
					"es.skillOid=esc.Oid AND es.skillOid='"+((Map)list.get(i)).get("Oid")+"'"));
			
		}
		
		return list;
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
