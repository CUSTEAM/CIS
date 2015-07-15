package tw.edu.chit.struts.action.course.cscore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 個人核心
 * @author JOHN
 *
 */
public class StudentCoreAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//若有點選學生
		if(request.getParameter("student_no")!=null){
			request.setAttribute("student_no", request.getParameter("student_no"));
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			String student_no=request.getParameter("student_no");
			Map astudent=manager.ezGetMap("SELECT s.*, c.DeptNo, c.ClassNo FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.student_no='"+student_no+"'");
			
			
			//通識
			List cores=manager.ezGetBy("SELECT * FROM CsCore WHERE (deptNo='"+astudent.get("DeptNo")+"' OR deptNo='0')");
			
			
			int pass;//及格標準
			if(astudent.get("ClassNo").toString().substring(2, 3).trim().equals("G")){//研究所
				pass=70;
			}else{
				pass=60;
			}
			List list=manager.ezGetBy("SELECT c.chi_name, s.*, cl.ClassName FROM ScoreHist s, Csno c, Class cl WHERE " +
					"cl.ClassNo=s.stdepart_class AND c.cscode=s.cscode AND s.student_no='"+student_no+"' AND s.score>="+pass);
			List scores=new ArrayList();
			Map map;
			for(int i=0; i<list.size(); i++){
				
				for(int j=0; j<cores.size(); j++){
					
					if( ((Map)list.get(i)).get("chi_name").toString().indexOf(((Map)cores.get(j)).get("key_word").toString())>-1 ){
						
						map=new HashMap();
						map.putAll(((Map)list.get(i)));
						map.putAll(((Map)cores.get(j)));
						scores.add(map);
					}
				}				
			}
			request.setAttribute("scores", scores);
			request.setAttribute("astudent", astudent);
		}
		
		setContentPage(request.getSession(false), "course/CsCore/StudentCore.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm cForm = (DynaActionForm) form;		
		
		String classLess=cForm.getString("classLess");
		
		
		if(classLess.length()<4){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "範圍太廣將影響效率, 至少選擇1個科系"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		
		List list=manager.ezGetBy("SELECT s.student_no, s.student_name, c.ClassName FROM stmd s, Class c WHERE c.ClassNo=s.depart_class AND s.depart_class LIKE '"+classLess+"%'");
		int pass;//及格標準
		if(classLess.substring(2, 3).trim().equals("G")){//研究所
			pass=70;
		}else{
			pass=60;
		}
		//Map map=manager.ezGetMap("SELECT * FROM StmdGrdeCredit WHERE SchoolNo='"+classLess.substring(1, 3)+"' AND DeptNo='"+classLess.substring(3, 4)+"' LIMIT 1");
		
		for(int i=0; i<list.size(); i++){
			((Map)list.get(i)).put("credit", manager.ezGetInt("SELECT DISTINCT SUM(credit)FROM ScoreHist WHERE student_no='"+((Map)list.get(i)).get("student_no")+"' AND score>="+pass));
		}
		
		request.setAttribute("students", list);
		request.setAttribute("grade", manager.ezGetMap("SELECT * FROM StmdGrdeCredit WHERE SchoolNo='"+classLess.substring(1, 3)+"' AND DeptNo='"+classLess.substring(3, 4)+"' LIMIT 1"));
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}
	
}