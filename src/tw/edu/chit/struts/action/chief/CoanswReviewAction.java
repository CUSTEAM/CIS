package tw.edu.chit.struts.action.chief;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 系主任回覆教學評量
 * @author JOHN
 *
 */
public class CoanswReviewAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
		   HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		session.setAttribute("allSchoolType", manager.getAllSchoolType());
		session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' ORDER BY idno"));
		setContentPage(request.getSession(false), "chief/CoanswReview.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm reviewForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		
		String searchType=(String)reviewForm.get("searchType");
		String classLess=(String)reviewForm.get("classLess");
		String dept=(String)reviewForm.get("dept");
		String schoolType=(String)reviewForm.get("schoolType");
		String techid=(String)reviewForm.get("teacherId");		
		
		
		if(searchType.equals("sim")){//快速搜
			session.setAttribute("allCoansw", manager.getCoansw4Empl(techid, classLess, manager.getSchoolTerm().toString()));
		}
			
		
		
		if(searchType.equals("exp")){//進階搜
			
			if(!schoolType.equals("")){//以學制
				
				try{
					session.setAttribute("allCoansw", manager.getCoansw4Empl(manager.getDepartClassByType(schoolType), manager.getSchoolTerm().toString()));
				}catch(Exception e){
					session.setAttribute("allCoansw", null);
				}
			}
			
			if(!dept.equals("")){//以科系
			
				try{
					session.setAttribute("allCoansw", manager.getCoansw4Empl(techid, "___"+dept+"%", manager.getSchoolTerm().toString()));
				}catch(Exception e){
					session.setAttribute("allCoansw", null);
				}
			}
			
			if(!techid.equals("")){//以教師
				
				try{
					session.setAttribute("allCoansw", manager.getCoansw4Empl(techid, classLess, manager.getSchoolTerm().toString()));
				}catch(Exception e){
					session.setAttribute("allCoansw", null);
				}
			}
		}
		
		
		
		
		
		
		
		return mapping.findForward("Main");
	}
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}

}
