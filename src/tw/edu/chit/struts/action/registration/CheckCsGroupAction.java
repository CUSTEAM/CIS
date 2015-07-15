package tw.edu.chit.struts.action.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CheckCsGroupAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allDept", manager.ezGetBy("SELECT name, idno FROM code5 WHERE category='Dept' AND idno <>'0' ORDER BY sequence"));
		
		setContentPage(request.getSession(false), "registration/CheckCsGroup.jsp");
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
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		HttpServletResponse response)throws Exception {
		DynaActionForm sForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String student_no=sForm.getString("student_no"); //學號
		String depart_class=sForm.getString("classLess"); //班級
		String type=sForm.getString("type");
		String searchType=sForm.getString("searchType");
		
		if(searchType.trim().equals("")||searchType.equals("normal")){
			type="C";
		}
		
		if(student_no.trim().equals("")&& depart_class.trim().length()<3){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請至少選擇1位學生，或是1個部制"));
			saveErrors(request, error);
			return mapping.findForward("Main");			
		}
		
		List stds=manager.ezGetBy("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
				"((stmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
				"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
				"c5.category='School' AND c51.category='Dept' AND st.student_no LIKE'"+student_no+"%' AND " +
				"depart_class LIKE'"+depart_class+"%'");
		
		Map std;
		List biGroup=new ArrayList();//大圈
		for(int i=0; i<stds.size(); i++){
			std=manager.ezGetMap("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
			"((stmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
			"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
			"c5.category='School' AND c51.category='Dept' AND st.student_no='"+((Map)stds.get(i)).get("student_no")+"'");
			std.put("myGroup", manager.CsGroup4One(student_no, std, false));
			biGroup.add(std);
		}
		
		List myGroup;
		
		float opt1;
		float opt2;
		float optOut;
		
		float MyOpt1;
		float MyOpt2;
		float MyOptOut;
		
		
		List list=new ArrayList();
		Map map;
		for(int i=0; i<biGroup.size(); i++){
			
			myGroup=(List)((Map)biGroup.get(i)).get("myGroup");
			for(int j=0; j<myGroup.size(); j++){
				opt1=Float.parseFloat(((Map)myGroup.get(j)).get("major").toString());
				opt2=Float.parseFloat(((Map)myGroup.get(j)).get("minor").toString());
				optOut=Float.parseFloat(((Map)myGroup.get(j)).get("outdept").toString());
				
				MyOpt1=Float.parseFloat(((Map)myGroup.get(j)).get("opt1").toString());
				MyOpt2=Float.parseFloat(((Map)myGroup.get(j)).get("opt2").toString());
				MyOptOut=Float.parseFloat(((Map)myGroup.get(j)).get("optOut").toString());	
				
				if(MyOpt1>0 || MyOpt2>0 || MyOptOut>0){					
					((Map)myGroup.get(j)).put("student_no", ((Map)biGroup.get(i)).get("student_no"));
					((Map)myGroup.get(j)).put("student_name", ((Map)biGroup.get(i)).get("student_name"));					
										
					//符合資格
					if(type.equals("C")){
						if(MyOpt1>=opt1 && MyOpt2>=opt2 && MyOptOut>=optOut){
							((Map)myGroup.get(j)).put("igot", true);
							list.add(myGroup.get(j));
						}
					}else{
						if(MyOpt1>=opt1 && MyOpt2>=opt2 && MyOptOut>=optOut){
							((Map)myGroup.get(j)).put("igot", true);
							list.add(myGroup.get(j));
						}else{
							((Map)myGroup.get(j)).put("igot", false);
							list.add(myGroup.get(j));
						}
										
					}
				}
			}			
		}
		
		session.setAttribute("relult", list);	
		setContentPage(request.getSession(false), "registration/CheckCsGroup.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		setContentPage(request.getSession(false), "registration/CheckCsGroup.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		return map;
	}
}