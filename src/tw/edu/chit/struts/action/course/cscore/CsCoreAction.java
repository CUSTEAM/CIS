package tw.edu.chit.struts.action.course.cscore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.CsCore;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

public class CsCoreAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");	
		request.setAttribute("DeptList", Global.DeptList);
		
		//驗證已存在的
		if(request.getParameter("Oid")!=null){
			
			Map dtime=manager.ezGetMap("SELeCT d.*, c.DeptNo, c.ClassName, cs.chi_name FROM Dtime d, Class c, Csno cs WHERE " +
					"cs.cscode=d.cscode AND " +
					"d.depart_class=c.ClassNo AND d.Oid="+request.getParameter("Oid"));
			
			List CsCore=manager.hqlGetBy("FROM CsCore WHERE deptNo='"+dtime.get("DeptNo")+"'");		
			String chi_name=dtime.get("chi_name").toString();
			for(int i=0; i<CsCore.size(); i++){
				
				if(chi_name.indexOf(((CsCore)CsCore.get(i)).getKeyWord())>-1){
					request.setAttribute("detail", CsCore.get(i));
				}
				
			}
			
			
		}
		
		setContentPage(request.getSession(false), "course/CsCore/CsCore.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm cForm = (DynaActionForm) form;
		
		String cscode=cForm.getString("courseNumber");
		String chi_name=cForm.getString("courseName");
		String deptNo=cForm.getString("deptNo");
		String Sterm=manager.getSchoolTerm().toString();
		
		List CsCore=manager.hqlGetBy("FROM CsCore WHERE deptNo='"+deptNo+"'");		
		List list=manager.ezGetBy("SELECT d.Oid, d.cscode, c.chi_name, cl.ClassName, cl.DeptNo, cl.ClassNo FROM Dtime d, Csno c, Class cl WHERE " +
				"d.depart_class LIKE '___"+deptNo+"%' AND d.depart_class=cl.ClassNo AND "+
				"d.cscode=c.cscode AND Sterm='"+Sterm+"' AND d.cscode LIKE '"+cscode+"%' AND c.chi_name LIKE '"+chi_name+"%'");
		
		List cores=new ArrayList();
		for(int i=0; i<CsCore.size(); i++){			
			for(int j=0; j<list.size(); j++){				
				if(((Map)list.get(j)).get("chi_name").toString().indexOf(((CsCore)CsCore.get(i)).getKeyWord())>-1){
					cores.add(((Map)list.get(j)));
				}				
			}
		}
		
		
		request.setAttribute("cores", cores);
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
